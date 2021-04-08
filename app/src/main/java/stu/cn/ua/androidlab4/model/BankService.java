package stu.cn.ua.androidlab4.model;

import com.annimon.stream.Stream;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import stu.cn.ua.androidlab4.logger.Logger;
import stu.cn.ua.androidlab4.model.db.CountryDAO;
import stu.cn.ua.androidlab4.model.db.CountryDbEntity;
import stu.cn.ua.androidlab4.model.network.CountryNetworkEntity;
import stu.cn.ua.androidlab4.model.network.BankApi;

public class BankService {

    private static final String FORMAT = "json";
    private static final int PER_PAGE = 300;

    private ExecutorService executorService;
    private CountryDAO countryDAO;
    private BankApi bankApi;
    private Logger logger;

    private Gson gson;
    private List<Country> countries;
    private List<CountryDbEntity> entities;

    public BankService(BankApi bankApi, CountryDAO countryDAO,
                       ExecutorService executorService, Logger logger) {
        this.bankApi = bankApi;
        this.countryDAO = countryDAO;
        this.executorService = executorService;
        this.logger = logger;
        gson = new Gson();
    }

    public Cancellable getCountries(String name, boolean hasInternet, Callback<List<Country>> callback){
        Future<?> future = executorService.submit(() -> {
            try {

                if(hasInternet){
                    List<CountryNetworkEntity> countryNetworkEntities = new ArrayList<>();

                    String str = bankApi.getCountries(FORMAT, PER_PAGE).execute().body().string();

                    str = str.substring(51,str.length()-1);
                    JSONArray jsonArray = new JSONArray(str);

                    for(int i = 0; i < jsonArray.length(); i++){
                        CountryNetworkEntity entity = gson.fromJson(jsonArray.getString(i), CountryNetworkEntity.class);
                        countryNetworkEntities.add(entity);
                    }
                    List<CountryDbEntity> newCountries = networkToDbEntity(countryNetworkEntities);
                    countryDAO.updateShips(newCountries);
                    callback.onResult(convertToShip(newCountries));
                }

                if(name.equals("") || name.equals("%%")){
                    entities = countryDAO.getShips();
                } else {
                    entities = countryDAO.getByName(name);
                }
                countries = convertToShip(entities);
                callback.onResult(countries);

                if(countries.isEmpty()){
                    RuntimeException runtimeException = new RuntimeException("Something happened");
                    logger.e(runtimeException);
                    callback.onError(runtimeException);
                }
            } catch (Exception e) {
                logger.e(e);
                callback.onError(e);
            }
        });

        return new FutureCancellable(future);
    }

    public Cancellable getCountryById(String id, Callback<Country> callback){
        Future<?> future = executorService.submit(() -> {
            try {
                CountryDbEntity dbEntity = countryDAO.getById(id);
                Country country = new Country(dbEntity);
                callback.onResult(country);
            } catch (Exception e){
                logger.e(e);
                callback.onError(e);
            }
        });

        return new FutureCancellable(future);
    }

    private List<Country> convertToShip(List<CountryDbEntity> entities){
        return Stream.of(entities).map(Country::new).toList();
    }

    private List<CountryDbEntity> networkToDbEntity(List<CountryNetworkEntity> entities){
        return Stream.of(entities)
                .map(CountryDbEntity::new)
                .toList();
    }

    static class FutureCancellable implements Cancellable {

        private Future<?> future;

        public FutureCancellable(Future<?> future) {
            this.future = future;
        }

        @Override
        public void cancel() {
            future.cancel(true);
        }
    }
}
