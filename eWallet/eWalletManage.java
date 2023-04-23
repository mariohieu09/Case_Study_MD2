package eWallet;

import Account.Account;

public interface eWalletManage {
    boolean deposit(Account acc, double money);

    boolean exchange(Account p, String name);
}
