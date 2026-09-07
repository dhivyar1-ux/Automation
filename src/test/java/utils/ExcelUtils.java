package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.testng.annotations.DataProvider;

public class ExcelUtils {
	public static String excelFileName="data_format_1.xlsx";

	@DataProvider(name = "excelData")
    public Object[][] excelDataProvider(Method m) throws IOException {
 
        // We are creating an object from the excel sheet data by calling a method that
        // reads data from the excel stored locally in our system
		String sheetname = "";
		
		switch (m.getName()) {
		case "CreateStudentAndVerifyDetails":
			sheetname = "student";
			break;
		case "CreateAppAdminAndVerifyDetails":
			sheetname = "appadmin";
			break;
		case "CreateFacultyAndVerifyDetails":
			sheetname = "faculty";
			break;
		case "CreateStaffAdminAndVerifyDetails":
			sheetname = "staffadmin";
			break;
		case "CreateContentCreatorAndVerifyDetails":
			sheetname = "contentcreator";
			break;
		case "CreateUserGroupAndVerifyDetails":
			sheetname = "usergroup";
			break;			
		case "CreateCourseAndVerifyDetails":
			sheetname = "course";
			break;
		case "CreateBatchAndVerifyDetails":
			sheetname = "batch";
			break;
		case "CreateGamesListAndVerifyDetails":
			sheetname = "gameslist";
			break;			
		case "AddTool":
			sheetname = "tool";
			break;
		case "CreateBatch":
			sheetname = "batch";
			break;
		case "CreateSessionPlan":
			sheetname = "sessionplan";
			break;
		case "EditSessionPlan":
			sheetname = "editsessionplan";
			break;
		case "CreateSessionRecord":
			sheetname = "sessionrecord";
			break;
		case "CreateBatchCoord":
			sheetname = "batchcoord";
			break;
		case "CreateBatchLabAlloc":
			sheetname = "batchlaballoc";
			break;		
		case "CreateInteracIndiAlloc":
			sheetname = "interaction";
			break;
		default:
			sheetname = "login";			
		}
		
        Object[][] arrObj = getExcelData(excelFileName,sheetname);
        return arrObj;
	}
	
    private String[][] getExcelData(String filename, String sheetname) throws IOException {
    	String[][] data = null;
    	try {
    	String Path_TestData = System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\" + filename;
	    //Create an object of File class to open xlsx file
		File file =    new File(Path_TestData);
		//Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		//Creating workbook instance that refers to .xlsx file
		XSSFWorkbook wb=new XSSFWorkbook(inputStream);
		//Creating a Sheet object using the sheet Name
		XSSFSheet sheet=wb.getSheet(sheetname);
		XSSFCell cell;
		XSSFRow row = sheet.getRow(0);
		
		int noOfRows = 2; //sheet.getPhysicalNumberOfRows();
        int noOfCols = row.getLastCellNum();
        data = new String[noOfRows - 1][noOfCols];
        
		String cellValue = "";
		
		for(int i = 1;i < noOfRows;i++) {
            for(int j = 0;j < noOfCols;j++) {
            	row = sheet.getRow(i);
            	cell = row.getCell(j);
				switch (cell.getCellType()) {
				case STRING:
					cellValue = cell.getStringCellValue();
					break;

				case FORMULA:
					cellValue = cell.getCellFormula();
					break;

				case NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						cellValue = cell.getDateCellValue().toString();
					} else {
						cellValue = String.format("%.0f", cell.getNumericCellValue());
					}
					break;

				case BLANK:
					cellValue = "";
					break;

				case BOOLEAN:
					cellValue = Boolean.toString(cell.getBooleanCellValue());
					break;
				default:
					break;
				}
				data[i - 1][j] = cellValue;
            }
        }
		
        //Close the workbook
        wb.close();
    	} catch (Exception e) {
            //System.out.println("The exception is: " + e.getMessage());
			throw new RuntimeException("Failed to read Excel data", e);
        }
    	return data;
	}
}
