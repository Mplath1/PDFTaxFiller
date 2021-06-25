package michael.plath.core;

import java.util.Date;

public class Credit extends Transaction{

    public Credit(Portfolio portfolio) {
        super(portfolio);
        this.transactionType = "Credit";
    }
}
