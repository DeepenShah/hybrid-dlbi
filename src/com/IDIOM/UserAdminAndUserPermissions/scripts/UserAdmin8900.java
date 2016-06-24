/********************************************************************
Test Case Name:*2627: Forgot password email sends to another user
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8900.aspx
Objective: To check whether the password is rightly sent to an active user when we open multiple sessions.
Module: UserAdminAndUserPermissions
Created By:Sunil Nair
Date: 02 May 2016
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;



import java.awt.List;
import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
import common.BaseClass;

public class UserAdmin8900 extends BaseClass {
		
	
	@Test
	public void verifyforgotpwdemail() throws Exception{
	//Initialize the variables
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");
		
		
		
	//****************Test step starts here************************************************
		
		//Step1:Access the application url	
		//CustomReporter.log("Launched Browser and Enter URL");
		try{
		Login_Page ln = new Login_Page(driver);
		CustomReporter.log("Launched browser and passed the URL");
		Thread.sleep(3000);
		
		ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
	    Thread.sleep(15000);
	    String parentWindow= driver.getWindowHandle();
	    System.out.println(parentWindow);
	    /*driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
	    CustomReporter.log("New tab opened successfully");
	    Thread.sleep(15000);*/
	    
	    rm.func_OpenNewtab(property.getProperty("URL"));
	    CustomReporter.log("Opened a new tab");
	    Thread.sleep(5000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
	    /*Visibility components to be added*/
	    ln.func_PerformAction("Forget Pwd", "");
	    System.out.println("current window is "+driver.getWindowHandles());
	    CustomReporter.log("Clicked on forgot password link");
	    Thread.sleep(5000);
	    ln.func_PerformAction("Input Email",property.getProperty("SuperAdminUser"));
	    CustomReporter.log("Entered the user email");
	    //driver.switchTo().window(parentWindow);
	    Thread.sleep(5000);
	    //need to include the reset password button in the admin page-sunil.
	    driver.findElement(By.xpath("html/body/idiom/main/section/div/forgot/div/section/div/input[1]")).click();
	    Thread.sleep(5000);
	   
		WebElement Send_EmailID=driver.findElement(By.xpath("//*[@ value='Password Reset Email Sent']"));
	    System.out.println("Send_EmailID value is "+Send_EmailID);
	    //System.out.println("Element exists "+rm.elementExists(Send_EmailID));
	   if(!Send_EmailID.equals(""))
	    {		   
		   if(Send_EmailID.getAttribute("value").toUpperCase().equals("PASSWORD RESET EMAIL SENT"))
		   {
			   //System.out.println("Inner else:"+Send_EmailID.getAttribute("value"));
			   BaseClass.testCaseStatus=true ;
			   CustomReporter.log("Email sent");
		   }
		   else
		   {
			   //System.out.println("Inner else:"+Send_EmailID.getAttribute("value"));
			   BaseClass.testCaseStatus=false;
		   }
		   //String msg1=driver.findElement(By.xpath("//*[@ value='Send Password Reset Email']")).getAttribute("value");
		   //System.out.println("msg1"+ msg1);

	    }
	   else
	   {
		   System.out.println("Else:"+Send_EmailID.getAttribute("value"));
		   System.out.println("No such element");
		   BaseClass.testCaseStatus=false;
	   }		    	   	  
		}
		catch(Exception e){
			BaseClass.testCaseStatus = false;
			CustomReporter.errorLog(" The exception is generated, " + e.getMessage() + ". ");
			e.printStackTrace(System.out);
		}
	    	    
		
		 //****************Test step ends here************************************************
		  
	    		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}
	
	
}
		
		
	
		
		