package com.qa.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.base.CustomReportListner;
import com.qa.base.TestBase;


@Listeners(CustomReportListner.class)
public class GoogleTest extends TestBase {
	
	public GoogleTest() {
		super();
	}
	
	@BeforeClass
	public void setup() {
		initialize();
	}
	
	@Test(priority = 1, description = "Google Home Page should dispaly")
	public void openGoogleHomePage(){
		//Google Home page dispalyed
	}
	
	@Test(priority = 2, description = "Search Result should display")
	public void search() throws Exception {
		WebElement searchBox = driver.findElement(By.xpath("//INPUT[@name=\"q\"]"));
		searchBox.sendKeys("RxCloud");
		searchBox.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
	}
	
	@Test(priority = 3, description = "First link should be open")
	public void openFirstResult() throws Exception {
		
		List<WebElement> links = driver.findElements(By.xpath("//DIV[@class='g']/div/div/a"));
		links.get(0).click();
	}
	
	@AfterClass
    public void teardown() {
		quitBrowser();
        //extentReports.flush();
    }
}

