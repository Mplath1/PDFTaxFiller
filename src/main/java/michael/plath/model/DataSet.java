package michael.plath.model;

import java.util.ArrayList;
import java.util.List;

public abstract class DataSet {

    static String startingMonth;
    static String endingMonth;
    static int year;
    DataSorter dataSorter;
    static List<Organization> organizationList;
    static List<Portfolio> portfolioList;

    public DataSet(DataSorter dataSorter){
        this.dataSorter = dataSorter;
        organizationList = new ArrayList<Organization>();
        portfolioList = new ArrayList<Portfolio>();
       dataSorter.sort();
    }

    public DataSorter getDataSorter() {
        return dataSorter;
    }

    public static List<Organization> getOrganizationList() {
        return organizationList;
    }

    public static List<Portfolio> getPortfolioList() {
        return portfolioList;
    }

    public static List<Organization> getOrganizationListByType(String type){
        List<Organization> list = new ArrayList<Organization>();
        switch(type){
            case "Retail":
                for(Organization organization: organizationList){
                    if(organization.getOrganizationType().equalsIgnoreCase("Retail")){
                        list.add(organization);
                    }
                }
                break;
            case "Wholesale":
                for(Organization organization: organizationList){
                    if(organization.getOrganizationType().equalsIgnoreCase("Wholesale")){
                        list.add(organization);
                    }
                }
                break;
            case "Sample":
                for(Organization organization: organizationList){
                    if(organization.getOrganizationType().equalsIgnoreCase("Sample")){
                        list.add(organization);
                    }
                }
                break;
            default:
                break;
        }
        return list;
    }
}
