package ua.valeriishymchuk.jsp.interfaces.wallet;

import ua.valeriishymchuk.jsp.simplehttp.HttpResponseResult;
import ua.valeriishymchuk.jsp.wallet.WalletNumber;

import java.util.concurrent.CompletableFuture;

public interface IWallet {

    CompletableFuture<HttpResponseResult> sendMoney(WalletNumber walletNumber, int amount, String comment);

    default CompletableFuture<HttpResponseResult> sendMoney(WalletNumber walletNumber, int amount) {
        return sendMoney(walletNumber, amount, "");
    }

    default CompletableFuture<HttpResponseResult> sendMoney(String wallet, int amount, String comment) {
        return sendMoney(new WalletNumber(wallet), amount, comment);
    }

    default CompletableFuture<HttpResponseResult> sendMoney(String wallet, int amount) {
        return sendMoney(wallet, amount, "");
    }

}
