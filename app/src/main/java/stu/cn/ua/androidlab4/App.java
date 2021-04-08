package stu.cn.ua.androidlab4;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import stu.cn.ua.androidlab4.logger.AndroidLogger;
import stu.cn.ua.androidlab4.logger.Logger;
import stu.cn.ua.androidlab4.model.BankService;
import stu.cn.ua.androidlab4.model.db.AppDatabase;
import stu.cn.ua.androidlab4.model.db.CountryDAO;
import stu.cn.ua.androidlab4.model.network.BankApi;

public class App extends Application {

    private static final String BASE_URL = "https://api.worldbank.org/v2/";

    private ViewModelProvider.Factory viewModelFactory;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();

        Logger logger = new AndroidLogger();

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        BankApi bankApi = retrofit.create(BankApi.class);

        ExecutorService executorService = Executors.newCachedThreadPool();

        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, "database.db")
                .build();

        CountryDAO countryDAO = appDatabase.getCountryDAO();

        BankService bankService = new BankService(bankApi, countryDAO, executorService, logger);
        viewModelFactory = new ViewModelFactory(bankService);

    }

    public ViewModelProvider.Factory getViewModelFactory(){
        return viewModelFactory;
    }
}
