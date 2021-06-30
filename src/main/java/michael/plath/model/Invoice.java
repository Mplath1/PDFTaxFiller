package michael.plath.model;

public class Invoice extends Transaction{

    public Invoice(Portfolio portfolio) {
        super(portfolio);
        this.transactionType = "Invoice";
    }
}
