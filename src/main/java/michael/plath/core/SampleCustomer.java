package michael.plath.core;

public class SampleCustomer extends Organization{
    public SampleCustomer(){
        super();
        this.organizationType = "Sample";
    }

    public SampleCustomer(String name, String address, String license) {
        super(name, address, license);
        this.organizationType = "Sample";
    }
}
