package stu.cn.ua.androidlab4;

import androidx.lifecycle.ViewModel;

import stu.cn.ua.androidlab4.model.BankService;

public class BaseViewModel extends ViewModel {

    private BankService bankService;

    public BaseViewModel(BankService bankService) {
        this.bankService = bankService;
    }

    public BankService getBankService() {
        return bankService;
    }
}
