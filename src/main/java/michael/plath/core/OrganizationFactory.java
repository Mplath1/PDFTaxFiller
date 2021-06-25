package michael.plath.core;

public class OrganizationFactory {

    public OrganizationFactory(){

    }

    public Organization getOrganization(String organizationType){

        Organization organization = null;

        if(organizationType.equalsIgnoreCase("Retail")){
            organization = new RetailCustomer();
        }else if(organizationType.equalsIgnoreCase("Wholesale")){
            organization = new WholesaleCustomer();
        }else if(organizationType.equalsIgnoreCase("Samples")){
            organization = new SampleCustomer();
        }else{
            organization = new RetailCustomer();
            organization.setOrganizationType("None");
        }

        return organization;
    }
}
