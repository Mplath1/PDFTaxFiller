package michael.plath.model;

import java.util.Date;

public abstract class Transaction {

    Portfolio portfolio;
    protected String transactionType;
    protected Date date;
    protected double value;
    protected double gallonsMisc;
    protected double gallonsLiquor;
    protected double gallonsRed;
    protected double gallonsRose;
    protected double gallonsWhite;
    protected double gallonsSparkling;
    protected double gallonsCider;
    protected double gallonsStill;

    public Transaction(Portfolio portfolio){
        this.portfolio = portfolio;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Date getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public double getGallonsMisc() {
        return gallonsMisc;
    }

    public double getGallonsLiquor() {
        return gallonsLiquor;
    }

    public double getGallonsRed() {
        return gallonsRed;
    }

    public double getGallonsRose() {
        return gallonsRose;
    }

    public double getGallonsWhite() {
        return gallonsWhite;
    }

    public double getGallonsSparkling() {
        return gallonsSparkling;
    }

    public double getGallonsCider() {
        return gallonsCider;
    }

    public double getGallonsStill() {
        return gallonsStill;
    }

    public void setGallonsMisc(double gallonsMisc) {
        this.gallonsMisc = gallonsMisc;
    }

    public void setGallonsLiquor(double gallonsLiquor) {
        this.gallonsLiquor = gallonsLiquor;
    }

    public void setGallonsRed(double gallonsRed) {
        this.gallonsRed = gallonsRed;
        setGallonsStill();
    }

    public void setGallonsRose(double gallonsRose) {
        this.gallonsRose = gallonsRose;
        setGallonsStill();
    }

    public void setGallonsWhite(double gallonsWhite) {
        this.gallonsWhite = gallonsWhite;
        setGallonsStill();
    }

    public void setGallonsSparkling(double gallonsSparkling) {
        this.gallonsSparkling = gallonsSparkling;
    }

    public void setGallonsCider(double gallonsCider) {
        this.gallonsCider = gallonsCider;
    }

    private void setGallonsStill() {
        gallonsStill = getGallonsRed() + getGallonsRose() + getGallonsWhite();
    }
}
