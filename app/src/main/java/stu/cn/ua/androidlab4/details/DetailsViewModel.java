package stu.cn.ua.androidlab4.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import stu.cn.ua.androidlab4.BaseViewModel;
import stu.cn.ua.androidlab4.model.Callback;
import stu.cn.ua.androidlab4.model.Cancellable;
import stu.cn.ua.androidlab4.model.BankService;
import stu.cn.ua.androidlab4.model.Country;
import stu.cn.ua.androidlab4.model.Result;

public class DetailsViewModel extends BaseViewModel {

    private MutableLiveData<Result<Country>> countryLiveData = new MutableLiveData<>();

    {
        countryLiveData.setValue(Result.empty());
    }

    private Cancellable cancellable;

    public DetailsViewModel(BankService bankService) {
        super(bankService);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(cancellable != null) cancellable.cancel();
    }

    public void loadCountryById(String id){
        countryLiveData.setValue(Result.loading());
        cancellable = getBankService().getCountryById(id, new Callback<Country>() {
            @Override
            public void onError(Throwable error) {
                countryLiveData.postValue(Result.error(error));
            }

            @Override
            public void onResult(Country data) {
                countryLiveData.postValue(Result.success(data));
            }
        });
    }

    public LiveData<Result<Country>> getResults(){
        return countryLiveData;
    }
}
