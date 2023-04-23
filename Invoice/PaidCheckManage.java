package Invoice;

import Account.Account;

public interface PaidCheckManage {
    public Invoice createInvoice(Account acc, String productName, double price);
    public void display(Invoice invoice);
    public void displayThePaidHistory(Account acc);

}
