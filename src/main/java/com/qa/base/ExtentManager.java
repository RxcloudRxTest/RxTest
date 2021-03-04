package com.qa.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.util.Constants;

public class ExtentManager {
	public static ExtentReports extentReports;
	public static ExtentSparkReporter extentSparkReporter;
	
	public static ExtentReports createInstance() {
		String fileName = getReportName();
		String directory = System.getProperty("user.dir")+Constants.reportPath;
		new File(directory).mkdirs();
		String path = directory + fileName;

		extentSparkReporter = new ExtentSparkReporter(path);
		extentSparkReporter.config().setTheme(Theme.STANDARD);
		extentSparkReporter.config().setDocumentTitle("MyReport");
		
		extentReports = new ExtentReports();
		extentReports.setSystemInfo("Organization", "My Test Org");
		extentReports.setSystemInfo("Browser", TestBase.properties.getProperty("browser"));
		extentReports.attachReporter(extentSparkReporter);
		
		return extentReports;
	}
	
	public static String getReportName() {
		String date = new SimpleDateFormat("MMddyyyyhhmmss").format(new Date());
		String fileName = date.toString().replace(":", "_").replace(" ", "_")+"Automation_Report.html";
		return fileName;
	}
}
