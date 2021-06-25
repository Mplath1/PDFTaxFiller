package michael.plath.core;

public class WholesaleCustomer extends Organization{

    public WholesaleCustomer(){
        super();
        this.organizationType = "Wholesale";
    }

    public WholesaleCustomer(String name, String address, String license) {
        super(name, address, license);
        this.organizationType = "Wholesale";
    }
}
