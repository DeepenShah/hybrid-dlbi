package common;

/*Excel class contains methods to fetch data from excel sheets 
 * to get the sheet name, to get the count of the sheets present in the workbook
 * to get the cell value */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

public class Excel {
	
	FileInputStream fis;
	String excelSheetPath;
	Workbook wb;

	public Excel() {
		
		excelSheetPath = "Test Data/data.xlsx";
		try {
			fis = new FileInputStream(excelSheetPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			wb = WorkbookFactory.create(fis);
		} catch (InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getSheetCount() {

		return wb.getNumberOfSheets();
		
	}

	public String getSheetName(int sheetIndex) {
		
		return wb.getSheetName(sheetIndex);
	}

	public int getRowCount(String sheetName) {

		return wb.getSheet(sheetName).getLastRowNum() + 1;

	}

	public String getCellValue(String sheetName, int rowNo, int colmnNo) {
		
		Cell c = wb.getSheet(sheetName).getRow(rowNo).getCell(colmnNo);
		// return c;
		if (c.getCellType() == 0) {
			return "" + c.getNumericCellValue();
		} else {
			return c.getStringCellValue();
		}		 

	}

	public void copySheet(int sheetIndex) {
		wb.cloneSheet(sheetIndex);
	}

	public void createSheet(String sheetName) {
		wb.createSheet(sheetName);
	}

	public int getCurntShtIndx() {
		return wb.getActiveSheetIndex();
	}

	public void delSheet(int sheetIndex) {
		wb.removeSheetAt(sheetIndex);
	}

}
