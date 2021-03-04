package com.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.util.Constants;
import com.qa.util.CustomReport;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public static Properties properties;
	public static WebDriver driver;
	
	
	public TestBase() {
		properties = new Properties();

		try {
			InputStream fis = new FileInputStream(System.getProperty("user.dir") + Constants.configFilePath);
			properties.load(fis);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialize() {
		//Read browser name from config file
		String browserName = properties.getProperty("browser");

		//Setup browser
		if(browserName.equalsIgnoreCase("chrome")) {
			System.out.println("Chrome browser is selected!!");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			System.out.println("Firefox browser is selected");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		//Maximize window
		driver.manage().window().maximize();

		//Delete all cookies
		driver.manage().deleteAllCookies();

		//Load test URL
		driver.get(Constants.testURL);
	}

	public static String readExcelColumnName(String ColumnName, int rowNumber) throws Exception {
		File testDataFile = new File(Constants.testDataExcelFile);
		FileInputStream testDataFIS = new FileInputStream(testDataFile);
		XSSFWorkbook workBook = new XSSFWorkbook(testDataFIS);
		XSSFSheet sheet = workBook.getSheet(Constants.testDataExcelSheet);
		int lastUsedRow = sheet.getLastRowNum();

		if(lastUsedRow == -1) {
			workBook.close();
			throw new EmptyFileException();
		}
		else if(rowNumber > lastUsedRow) {
			workBook.close();
			throw new Exception("Invalid row number");
		}

		Row sheetRow = sheet.getRow(0);
		short lastUsedColumn = sheetRow.getLastCellNum();
		int column = -1;

		//Get the column number
		for (int i = 0; i < lastUsedColumn; i++) {
			if(sheetRow.getCell(i).getStringCellValue().equalsIgnoreCase(ColumnName)) {
				column = i;
				break;
			}
		}

		if(column == -1) {
			workBook.close();
			throw new Exception("Invalid column name");
		}
		
		//Get cell value
		sheetRow = sheet.getRow(rowNumber);
		Cell rowCell = sheetRow.getCell(column);
		String cellValue = rowCell.getStringCellValue();

		workBook.close();
		return cellValue;
	}

	public static void quitBrowser() {
		if(driver != null) {
			driver.quit();
		}
	}

	/**
	 * Capture screenshot
	 * @param methodName name of method where we want to take screenshot
	 * @return path path of screenshot
	 */
	public static String takeScreenshot(String methodName) {
		String fileName = getScreenshotName(methodName);
		String directory = System.getProperty("user.dir")+Constants.screenshotsPath;
		new File(directory).mkdir();
		String path = directory + fileName;
		
		try {
			//Capture screenshot using Selenium webDriver
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	        FileUtils.copyFile(screenshot, new File(path));
	        System.out.println("====================================");
			System.out.println("Screenshot saved at:" + path);
			System.out.println("====================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return path;
	}
	
	/**
	 * Provide Screenshot name on runtime
	 * @param methodName name of method screenshot taken for
	 * @return name name of screenshot
	 */
	private static String getScreenshotName(String methodName) {
		Date date = new Date();
		String name = methodName + "_"+date.toString().replace(":", "_").replace(" ", "_")+".png";
		return name;
	}
	
	public static String getReportName() {
		String date = new SimpleDateFormat("MMddyyyyhhmm").format(new Date());
		String fileName = date.toString().replace(":", "_").replace(" ", "_")+"Automation_Report.html";
		return fileName;
	}
}