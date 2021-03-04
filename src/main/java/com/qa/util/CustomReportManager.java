package com.qa.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.qa.base.TestBase;

public class CustomReportManager {
	public static ArrayList<CustomReport> reportArray = new ArrayList<CustomReport>();
	public static int testCount = 0;
	public static int passedTestCount = 0;
	public static int failedTestCount = 0;
	public static int skippedTestCount = 0;
	public static long suiteStartTime;
	public static long suiteEndTime;
	
	public static void writeHTMLreport() {
		File input = new File(Constants.customReportTemplatePath);
		Document doc = null;
		try {
			doc = Jsoup.parse(input, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Elements resultTable = doc.select(Constants.reportTemplateTestList);
		Elements passedTests = doc.select(Constants.reportTemplatePassedTest);
		Elements failedTests = doc.select(Constants.reportTemplateFailedTest);
		Elements executionTime = doc.select(Constants.reportTemplateExecutionTime);
		
		resultTable.html(getTestsHTMLblocks());
		passedTests.html("&nbsp;: " + passedTestCount);
		failedTests.html("&nbsp;: " + failedTestCount);
		executionTime.html("Execution Duration: "+spentTime(suiteStartTime, suiteEndTime));
		
		String finalReport = doc.html();
		
		writeHTMLfile(finalReport);
	}

	private static String getTestsHTMLblocks() {
		String testsHTMLcode = "";
		
		for (int i = 0; i < reportArray.size(); i++) {
			testsHTMLcode = testsHTMLcode.concat(reportArray.get(i).htmlCodeBlock());
		}
		
		return testsHTMLcode;
	}
	
	private static void writeHTMLfile(String html) {
		File report = new File(
				System.getProperty("user.dir") +
				Constants.reportPath +
				TestBase.getReportName()
		);
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(report,"UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		writer.write(html) ;
		writer.flush();
		writer.close();
	}
	
	

	public static String spentTime(long startTime, long endTime) {
		long time = endTime - startTime;
		long min = TimeUnit.MILLISECONDS.toMinutes(time);
		
		if(min < 1) {
			return	TimeUnit.MILLISECONDS.toSeconds(time) + "Seconds";
		}
		else {
			return min + "Minutes";
		}
	}
}
