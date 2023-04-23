package eWallet;

import java.io.Serializable;

public class eWallet implements Serializable {
    private static final long serialVersionUID = 9045863543269746292L;
    private  double amount;

    public eWallet() {
        this.amount = 0;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "eWallet{" +
                "amount=" + amount +
                '}';
    }
}
