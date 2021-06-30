package michael.plath.model;

public class Sample extends Transaction{

    public Sample(Portfolio portfolio) {
        super(portfolio);
        this.transactionType = "Sample";
    }
}
