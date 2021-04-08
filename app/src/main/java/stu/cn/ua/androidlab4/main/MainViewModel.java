package stu.cn.ua.androidlab4.main;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import stu.cn.ua.androidlab4.BaseViewModel;
import stu.cn.ua.androidlab4.model.Callback;
import stu.cn.ua.androidlab4.model.Cancellable;
import stu.cn.ua.androidlab4.model.BankService;
import stu.cn.ua.androidlab4.model.Country;
import stu.cn.ua.androidlab4.model.Result;

import static stu.cn.ua.androidlab4.model.Result.Status.EMPTY;
import static stu.cn.ua.androidlab4.model.Result.Status.ERROR;
import static stu.cn.ua.androidlab4.model.Result.Status.LOADING;
import static stu.cn.ua.androidlab4.model.Result.Status.SUCCESS;

public class MainViewModel extends BaseViewModel {

    private BankService bankService;

    private Result<List<Country>> listResult = Result.empty();
    private MutableLiveData<ViewState> stateLiveData = new MutableLiveData<>();

    {
        updateViewState(Result.empty());
    }

    private Cancellable cancellable;

    @Override
    protected void onCleared() {
        super.onCleared();
        if (cancellable != null) cancellable.cancel();
    }

    public MainViewModel(BankService bankService) {
        super(bankService);
    }

    public MutableLiveData<ViewState> getViewState() {
        return stateLiveData;
    }

    public void getCountries(String name, boolean hasInternet){
        updateViewState(Result.loading());
        cancellable = getBankService().getCountries(name, hasInternet, new Callback<List<Country>>() {
            @Override
            public void onError(Throwable error) {
                if(listResult.getStatus() != SUCCESS) {
                    updateViewState(Result.error(error));
                }
            }

            @Override
            public void onResult(List<Country> data) {
                updateViewState(Result.success(data));
            }
        });
    }

    private void updateViewState(Result<List<Country>> shipsResult){
        this.listResult = shipsResult;
        ViewState state = new ViewState();
        state.enableSearchButton = shipsResult.getStatus() != LOADING;
        state.showList = shipsResult.getStatus() == SUCCESS;
        state.showEmptyHint = shipsResult.getStatus() == EMPTY;
        state.showError = shipsResult.getStatus() == ERROR;
        state.showProgress = shipsResult.getStatus() == LOADING;
        state.countries = shipsResult.getData();
        stateLiveData.postValue(state);
    }

    static class ViewState {
        private boolean enableSearchButton;
        private boolean showList;
        private boolean showEmptyHint;
        private boolean showError;
        private boolean showProgress;
        private List<Country> countries;

        public boolean isEnableSearchButton() {
            return enableSearchButton;
        }

        public boolean isShowList() {
            return showList;
        }

        public boolean isShowEmptyHint() {
            return showEmptyHint;
        }

        public boolean isShowError() {
            return showError;
        }

        public boolean isShowProgress() {
            return showProgress;
        }

        public List<Country> getCountries() {
            return countries;
        }
    }
}
