package michael.plath.model;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

import static michael.plath.model.DataSet.*;

public class PortfolioPaymentReport extends Report {

    @Override
    public void produce() {
        XSSFWorkbook workbook = null;
        try {
            List<Portfolio> portfolioList = getPortfolioList();
            workbook = new XSSFWorkbook();
            CreationHelper createHelper = workbook.getCreationHelper();
            FormulaEvaluator formulaEvaluator = createHelper.createFormulaEvaluator();
            XSSFSheet sheet = workbook.createSheet();
            //create number of cells for portfolio total. totals should include retail sales and samples
            int rowNumber = 0;
            Row row = sheet.createRow(rowNumber);
            row.createCell(0).setCellValue(createHelper.createRichTextString("Portfolio Id"));
            row.createCell(1);
            row.createCell(2).setCellValue(createHelper.createRichTextString("Retail Liquor"));
            row.createCell(3);
            row.createCell(4).setCellValue(createHelper.createRichTextString("Retail Still Wine"));
            row.createCell(5);
            row.createCell(6).setCellValue(createHelper.createRichTextString("Retail Sparkling Wine"));
            row.createCell(7);
            row.createCell(8).setCellValue(createHelper.createRichTextString("Sample Liquor"));
            row.createCell(9);
            row.createCell(10).setCellValue(createHelper.createRichTextString("Sample Still Wine"));
            row.createCell(11);
            row.createCell(12).setCellValue(createHelper.createRichTextString("Sample Sparkling Wine"));
            row.createCell(13);
            row.createCell(14).setCellValue(createHelper.createRichTextString("Total"));
            rowNumber++;

            for(Portfolio portfolio: portfolioList){
                double[] totalRetailGallons = {0,0,0,0,0,0};
                List<Organization> organizationList = getOrganizationListByType("Retail");
                for(Organization organization: organizationList){
                    for(Transaction transaction: organization.getTransactions()){
                        if(portfolio.equals(transaction.portfolio)){
                            totalRetailGallons[1] += transaction.getGallonsLiquor();
                            totalRetailGallons[2] += transaction.getGallonsStill();
                            totalRetailGallons[4] += transaction.getGallonsSparkling();
                        }
                    }
                }
                double[] totalSampleGallons = {0,0,0,0,0,0};
                organizationList = getOrganizationListByType("Sample");
                for(Organization organization: organizationList){
                    for(Transaction transaction: organization.getTransactions()){
                        if(portfolio.equals(transaction.portfolio)){
                            totalSampleGallons[1] += transaction.getGallonsLiquor();
                            totalSampleGallons[2] += transaction.getGallonsStill();
                            totalSampleGallons[4] += transaction.getGallonsSparkling();
                        }
                    }
                }

                row = sheet.createRow(rowNumber);
                row.createCell(0).setCellValue(createHelper.createRichTextString(portfolio.getId()));
                row.createCell(1);
                row.createCell(2).setCellValue(totalRetailGallons[1]);//Retail Liquor
                row.createCell(3);
                row.createCell(4).setCellValue(totalRetailGallons[2]);//Retail Still Wine
                row.createCell(5);
                row.createCell(6).setCellValue(totalRetailGallons[4]);//Retail Sparkling Wine
                row.createCell(7);
                row.createCell(8).setCellValue(totalSampleGallons[1]); //Sample Liquor
                row.createCell(9);
                row.createCell(10).setCellValue(totalSampleGallons[2]); //Sample Still Wine
                row.createCell(11);
                row.createCell(12).setCellValue(totalSampleGallons[4]); //Sample Sparkling Wine
                row.createCell(13);
                String calculationFormula = "=((C" + (rowNumber +1) +" + I"+ (rowNumber + 1) + ") * 5.5) " +
                        "+ ((E" + (rowNumber +1) + "+ G" + (rowNumber +1) + " + K" + (rowNumber +1) + " + M" + (rowNumber +1) + ") * 0.875)";
                row.createCell(14).setCellValue(createHelper.createRichTextString(calculationFormula)); //Total (Liquor * 5.5) + (All Wine * 0.875)
                formulaEvaluator.evaluateInCell(row.getCell(14));
                rowNumber++;
            }
            //can create totals here using row number, columns and =SUM formula

            FileOutputStream fileOutputStream = new FileOutputStream("C:\\USAWine\\New Jersey\\Tester\\portfolioPayment.xlsx");
            workbook.write(fileOutputStream);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
