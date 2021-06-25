package michael.plath.core;

import java.util.Date;

public class Sample extends Transaction{

    public Sample(Portfolio portfolio) {
        super(portfolio);
        this.transactionType = "Sample";
    }
}
