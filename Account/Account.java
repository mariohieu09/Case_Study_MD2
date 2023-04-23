package Account;

import eWallet.eWallet;

import java.io.Serializable;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 9045863543269746292L;
    protected String name;
    protected String Role = "Default";
    protected String AccountName;
    protected String Password;
    protected eWallet eWallet;

    public Account(String name, String accountName, String password) {
        this.name = name;
//        Role = role;
        AccountName = accountName;
        Password = password;
    }

    public Account() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
