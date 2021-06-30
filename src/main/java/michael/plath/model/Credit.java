package michael.plath.model;

public class Credit extends Transaction{

    public Credit(Portfolio portfolio) {
        super(portfolio);
        this.transactionType = "Credit";
    }
}
