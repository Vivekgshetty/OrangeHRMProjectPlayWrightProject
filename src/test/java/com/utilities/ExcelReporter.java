package com.utilities;
 
 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class ExcelReporter {
    private static Workbook workbook = new XSSFWorkbook();
    private static Sheet sheet = workbook.createSheet("Test Results");
    private static int rowCount = 0;
    private static final String path = "./target/TestNG_Result.xlsx";
 
    static {
        Row header = sheet.createRow(rowCount++);
        header.createCell(0).setCellValue("Test Name");
        header.createCell(1).setCellValue("Status");
        header.createCell(2).setCellValue("Error Message");
        header.createCell(3).setCellValue("Duration");
        header.createCell(4).setCellValue("Screenshot Path");
    }
 
    public static void logResult(String testName, String status, String errorMsg, long duration, String screenshotPath) {
        Row row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue(testName);
        row.createCell(1).setCellValue(status);
        row.createCell(2).setCellValue(errorMsg != null ? errorMsg : "");
        row.createCell(3).setCellValue(duration + "ms");
        row.createCell(4).setCellValue(screenshotPath != null ? screenshotPath : "");
    }
 
    public static void saveReport() {
        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static String[][] getData() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            Workbook readWorkbook = new XSSFWorkbook(fileInputStream);
            Sheet readSheet = readWorkbook.getSheetAt(0);
 
            int rowCount = readSheet.getLastRowNum();
            int colCount = readSheet.getRow(0).getLastCellNum();
            String[][] loginData = new String[rowCount][colCount];
 
            for (int i = 1; i <= rowCount; i++) {
                Row row = readSheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    loginData[i - 1][j] = cell != null ? cell.toString() : "";
                }
            }
 
            return loginData;
        }
    }
}
 
 
 