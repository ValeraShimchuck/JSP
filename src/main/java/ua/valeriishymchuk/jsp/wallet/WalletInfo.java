package ua.valeriishymchuk.jsp.wallet;

import ua.valeriishymchuk.jsp.interfaces.wallet.IWalletInfo;

public class WalletInfo implements IWalletInfo {

    private final int balance;

    public WalletInfo(int balance) {
        this.balance = balance;
    }

    @Override
    public int getBalance() {
        return balance;
    }
}
