package michael.plath.model;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import static michael.plath.model.DataSet.organizationList;
import static michael.plath.model.DataSet.portfolioList;

public class NJDataSorter extends DataSorter{
    FileInputStream fileInputStream;
    OrganizationFactory organizationFactory;


    public NJDataSorter(FileInputStream fileInputStream){
        this.fileInputStream = fileInputStream;
        organizationFactory = new OrganizationFactory();
    }


    public void sort(){
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            /*
            0-customerType
            1-customerName
            2-clientId
            3-license
            4-address
            5-city
            6-state
            7-zip
            8-shiptoState
            9-empty
            10-liquor
            11-red
            12-rose
            13-sparkling
            14-tax exempt
            15-white
             */
            String customerType = row.getCell(0).toString();
            String customerName = row.getCell(1).toString();
            String clientID = row.getCell(2).toString();
            String license = row.getCell(3).toString();
            String address = row.getCell(4).toString();
            String city = row.getCell(5).toString();
            String state = row.getCell(6).toString();
            int zip = NumberUtils.toInt(row.getCell(7).toString());

            if(customerName.contains("USA Wine Imports") || customerName.contains("FDL")){
                customerType = "Samples";
            }
            //need to round using Math.round(). must account for decimals so that min value = 1
            double liquorGallons = NumberUtils.toDouble(row.getCell(10).toString());
            double redGallons = NumberUtils.toDouble(row.getCell(11).toString());
            double whiteGallons = NumberUtils.toDouble(row.getCell(15).toString());
            double roseGallons = NumberUtils.toDouble(row.getCell(12).toString());
            double sparklingGallons = NumberUtils.toDouble(row.getCell(13).toString());

            //rounding. should be improved/ if not 0 add .5 and get floor
            liquorGallons = Math.round(liquorGallons);
            redGallons = Math.round(redGallons);
            whiteGallons = Math.round(whiteGallons);
            roseGallons = Math.round(roseGallons);
            sparklingGallons = Math.round(sparklingGallons);



            Portfolio portfolio = new PortfolioImpl(clientID);
            //must add to portfolioList if does not exist
            if(!portfolioList.contains(portfolio)) {
                portfolioList.add(portfolio);
                System.out.println(portfolio.getId() + " added");
            }

            Transaction transaction = new Invoice(portfolio); //need to fix to allow sample transactions
            transaction.setGallonsLiquor(liquorGallons);
            transaction.setGallonsRed(redGallons);
            transaction.setGallonsRose(roseGallons);
            transaction.setGallonsWhite(whiteGallons);
            transaction.setGallonsSparkling(sparklingGallons);

            //must add to organization list if does not exist
            Organization customer = organizationFactory.getOrganization(customerType);
            customer.setName(customerName);
            customer.setLicense(license);
            customer.setAddress(address + "\n" + city + "," + state + "," + zip);

            if(!organizationList.contains(customer)) { //needs tweaking
                customer.addTransaction(transaction);
                organizationList.add(customer);
            }else{
                int index = organizationList.indexOf(customer);
                organizationList.get(index).addTransaction(transaction);
            }
        }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
