package ua.valeriishymchuk.jsp.wallet;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class WalletNumber {

    String walletID;

    public WalletNumber(String walletID) {
        if(!walletID.matches("\\d{5}")) throw new RuntimeException("walletID is invalid");
        this.walletID = walletID;
    }

    @Override
    public String toString() {
        return walletID;
    }
}
