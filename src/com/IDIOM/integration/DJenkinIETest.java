package com.IDIOM.integration;

import org.testng.annotations.Test;

import com.reports.CustomReporter;
import common.BaseClass;

public class DJenkinIETest extends BaseClass {

	@Test
	public void googleVerification() throws Exception{
		
		rm.captureScreenShot("GoogleTest-IDIOMApp", "pass");
		
		CustomReporter.log("Launching google");
		driver.navigate().to("www.google.com");
		Thread.sleep(5000);
		
		rm.captureScreenShot("GoogleTest", "pass");
		
		
		
	}
}
