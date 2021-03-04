package com.qa.base;

import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class TestListener implements ITestListener{
	private static ExtentReports extentReport = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	public void onTestStart(ITestResult result) {
		ExtentTest test = extentReport.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		
		try {
			String path = TestBase.takeScreenshot(result.getMethod().getMethodName());
			extentTest.get().pass(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		catch (Exception e) {
			extentTest.get().pass("Test passed can not take screenshot");
		}

		String logText = "<b>Verification of "+ methodName +" is successful</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTest.get().log(Status.PASS, markup);
	}

	public void onTestFailure(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		
		try {
			String path = TestBase.takeScreenshot(result.getMethod().getMethodName());
			extentTest.get().fail(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		catch (Exception e) {
			extentTest.get().fail("Test failed can not take screenshot");
		}
		
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		extentTest.get().fail("<details><summary><b><font color=red>"+ "Exception occured,click to see details:"
				+"</font></b></summary>"+ exceptionMessage.replaceAll(",", "<br>")+"</details> \n");

		String logText = "<b>Verification of "+methodName+" has failed</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, markup);
	}

	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		
		try {
			String path = TestBase.takeScreenshot(result.getMethod().getMethodName());
			extentTest.get().skip(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		catch (Exception e) {
			extentTest.get().skip("Test skipped can not take screenshot");
		}
		
		String logText = "<b>Test Method"+ methodName +"Skipped";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.AMBER);
		extentTest.get().log(Status.SKIP, markup);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

	public void onTestFailedWithTimeout(ITestResult result) {}

	public void onStart(ITestContext context) {}

	public void onFinish(ITestContext context) {
		if (extentReport != null) {
			extentReport.flush();
		}
	}
}