package michael.plath.core;

import java.util.Date;

public class Invoice extends Transaction{

    public Invoice(Portfolio portfolio) {
        super(portfolio);
        this.transactionType = "Invoice";
    }
}
