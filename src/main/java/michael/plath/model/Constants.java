package michael.plath.model;

import java.io.IOException;
import java.util.Properties;

import static michael.plath.core.Main.readPropertiesFile;

public final class Constants {

    static String licenseeName = null;
    static String licenseeFederalIdNumber = null;
    static String licenseeStreetAddress = null;
    static String licenseeCityStateZip = null;
    static String licenseeCounty = null;
    static String licenseeType = null;
    static String licenseeNumber = null;
    static String warehouseName = null;
    static String warehouseStreetAddress = null;
    static String warehouseCityStateZip = null;

    public Constants(){

        Properties properties = null;
        try {
            properties = readPropertiesFile("src\\main\\resources\\config\\application.properties");

        } catch (IOException e) {
            e.printStackTrace();
        }
        licenseeName = properties.getProperty("licensee.name"); //load and read sensitive info
        licenseeFederalIdNumber = properties.getProperty("licensee.federalIdNumber");
        licenseeStreetAddress = properties.getProperty("licensee.streetAddress");
        licenseeCityStateZip = properties.getProperty("licensee.cityStateZip");
        licenseeCounty = properties.getProperty("licensee.county");
        licenseeType = properties.getProperty("licensee.type");
        licenseeNumber = properties.getProperty("licensee.number");
        warehouseName = properties.getProperty("warehouse.name");
        warehouseStreetAddress = properties.getProperty("warehouse.streetAddress");
        warehouseCityStateZip = properties.getProperty("warehouse.cityStateZip");

    }




}
