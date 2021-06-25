package michael.plath.core;

public class RetailCustomer extends Organization{

    public RetailCustomer(){
        super();
        this.organizationType = "Retail";
    }

    public RetailCustomer(String name, String address, String license) {
        super(name, address, license);
        this.organizationType = "Retail";
    }
}
