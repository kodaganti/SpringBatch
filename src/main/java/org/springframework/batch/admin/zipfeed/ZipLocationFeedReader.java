package org.springframework.batch.admin.zipfeed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class ZipLocationFeedReader implements ItemReader<ZipLocationBean> {
	
	static private int COUNTRY = 0;
	static private int ZIP = 1;
	static private int CITY = 2;
	static private int STATE = 3;
	static private int LATITUDE = 8;
	static private int LONGITUDE = 9;
	
	static private HSSFSheet sheet = null;
	static private int MAX_SIZE = 0;
	static private int READ_INDEX = -1;
	static {
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File("C:\\Users\\Girish\\Desktop\\MS\\ZipFeed\\ZipFeed_US.xls"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Get the workbook instance for XLS file
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(file);
			sheet = workbook.getSheetAt(0);
			MAX_SIZE = sheet.getPhysicalNumberOfRows();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Get first sheet from the workbook
	}

	public ZipLocationBean read() throws Exception, UnexpectedInputException,
			ParseException {
		READ_INDEX++;
		if(READ_INDEX >= MAX_SIZE){
			return null;
		}
		HSSFRow row = sheet.getRow(READ_INDEX);
		if(row == null){
			return null;
		}
		ZipLocationBean bean = new ZipLocationBean();
		try{
			if(row.getCell(COUNTRY) != null){
				bean.setCountry(row.getCell(COUNTRY).getStringCellValue());
			}
			
			if(row.getCell(ZIP) != null){
				bean.setZipcode(row.getCell(ZIP).getNumericCellValue() +"");
			}
			
			if(row.getCell(CITY) != null){
				bean.setCity(row.getCell(CITY).getStringCellValue());
			}
			
			if(row.getCell(STATE) != null){
				bean.setState(row.getCell(STATE).getStringCellValue());
			}
			
			if(row.getCell(LONGITUDE) != null){
				bean.setLongitude(row.getCell(LONGITUDE).getNumericCellValue());
			}
			
			if(row.getCell(LATITUDE) != null){
				bean.setLatitude(row.getCell(LATITUDE).getNumericCellValue());
			}
		}catch(Exception e){
			
		}
		return bean;
	}
}