package michael.plath.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {

    protected String organizationType;
    protected String name;
    protected String address; //possibly split
    protected String license;
    protected List<Transaction> transactions;
    protected double totalGallonsMisc;
    protected double totalGallonsLiquor;
    protected double totalGallonsRed;
    protected double totalGallonsRose;
    protected double totalGallonsWhite;
    protected double totalGallonsSparkling;
    protected double totalGallonsCider;
    protected double totalGallonsStill;


    public Organization(){
        transactions = new ArrayList<Transaction>();
    }

    public Organization(String name,String address, String license){
        this.name = name;
        this.address = address;
        this.license = license;
        transactions = new ArrayList<Transaction>();
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicense() {
        return license;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        updateTotals(transaction);
    }

    protected void updateTotals(Transaction transaction){
        totalGallonsMisc += transaction.gallonsMisc;
        totalGallonsLiquor += transaction.gallonsLiquor;
        totalGallonsRed += transaction.gallonsRed;
        totalGallonsRose += transaction.gallonsRose;
        totalGallonsWhite += transaction.gallonsWhite;
        totalGallonsSparkling += transaction.gallonsSparkling;
        totalGallonsCider += transaction.gallonsCider;
        totalGallonsStill += transaction.gallonsStill;
    }

    protected String calculateTotals(){

        String totals;

        for(Transaction transaction: transactions){
            totalGallonsMisc += transaction.gallonsMisc;
            totalGallonsLiquor += transaction.gallonsLiquor;
            totalGallonsRed += transaction.gallonsRed;
            totalGallonsRose += transaction.gallonsRose;
            totalGallonsWhite += transaction.gallonsWhite;
            totalGallonsSparkling += transaction.gallonsSparkling;
            totalGallonsCider += transaction.gallonsCider;
            totalGallonsStill += transaction.gallonsStill;
        }
        return "Name:" + name + "\n\tMisc:" + totalGallonsMisc + "\n\tLiquor:" + totalGallonsLiquor + "\n\tSparkling:" + totalGallonsSparkling
                + "\n\tStill:" + totalGallonsStill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!name.equals(that.name)) return false;
        return license.equals(that.license);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + license.hashCode();
        return result;
    }
}
