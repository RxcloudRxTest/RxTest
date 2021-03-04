package com.qa.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.util.CustomReport;
import com.qa.util.CustomReportManager;

public class CustomReportListner implements ITestListener{
	
	public void onTestStart(ITestResult result) {}

	public void onTestSuccess(ITestResult result) {
		String desc = result.getMethod().getDescription();
		String methodName = result.getMethod().getMethodName();
		String path = "";
		
		System.out.println("on Success :" + methodName);
		
		try {
			path = TestBase.takeScreenshot(result.getMethod().getMethodName());
		}
		catch (Exception e) {
			System.out.println("Test passed can not take screenshot");
		}
		String startTime = new SimpleDateFormat("MMM dd yyyy hh:mm:ss a").format(new Date(result.getStartMillis()));
		CustomReportManager.reportArray.add(new CustomReport(methodName, "Pass", "Actual val", desc, path, ++CustomReportManager.testCount, startTime));
		CustomReportManager.passedTestCount++;
	}

	public void onTestFailure(ITestResult result) {
		String desc = result.getMethod().getDescription();
		String methodName = result.getMethod().getMethodName();
		String path = "";

		try {
			path = TestBase.takeScreenshot(result.getMethod().getMethodName());
		}
		catch (Exception e) {
			System.out.println("Test failed can not take screenshot");
		}
		String startTime = new SimpleDateFormat("MMM dd yyyy hh:mm:ss a").format(new Date(result.getStartMillis()));
		CustomReportManager.reportArray.add(new CustomReport(methodName, "Fail", "Actual val", desc, path, ++CustomReportManager.testCount, startTime));
		CustomReportManager.failedTestCount++;
	}

	public void onTestSkipped(ITestResult result) {
		String desc = result.getMethod().getDescription();
		String methodName = result.getMethod().getMethodName();
		String path = "";
		
		try {
			path = TestBase.takeScreenshot(result.getMethod().getMethodName());
		}
		catch (Exception e) {
			System.out.println("Test skipped can not take screenshot");
		}
		String startTime = new SimpleDateFormat("MMM dd yyyy hh:mm:ss a").format(new Date(result.getStartMillis()));
		CustomReportManager.reportArray.add(new CustomReport(methodName, "Skipped", "Actual val", desc, path, ++CustomReportManager.testCount, startTime));		
		CustomReportManager.skippedTestCount++;
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

	public void onTestFailedWithTimeout(ITestResult result) {}

	public void onStart(ITestContext context) {
		CustomReportManager.suiteStartTime = System.currentTimeMillis();
	}

	public void onFinish(ITestContext context) {
		CustomReportManager.suiteEndTime = System.currentTimeMillis();
		CustomReportManager.writeHTMLreport();
	}
	
}