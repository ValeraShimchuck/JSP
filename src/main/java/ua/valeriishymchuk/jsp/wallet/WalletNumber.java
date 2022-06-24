package ua.valeriishymchuk.jsp.wallet;

public final class WalletNumber {

    private final String walletID;

    public WalletNumber(String walletID) {
        if(!walletID.matches("\\d{5}")) throw new RuntimeException("walletID is invalid");
        this.walletID = walletID;
    }

    @Override
    public String toString() {
        return walletID;
    }

}
