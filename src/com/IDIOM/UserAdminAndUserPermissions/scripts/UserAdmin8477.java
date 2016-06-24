/********************************************************************
Test Case Name: *1006_Super_User_Admin_Verify_Key_Icon_Removal_After_Revoking_Rights
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8477.aspx
Objective: Verify whether key icon is getting removed in User List Grid after rights are revoked
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 22 December 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;

public class UserAdmin8477 extends BaseClass {
	
	@Test
	public void verifytabnavigation() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		 
	
		
	//****************Test step starts here************************************************
		try{
			//Step 1:Open URL 
			CustomReporter.log("Launched Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);
			
			Thread.sleep(3000);
			
			//Step2:Log in as a Super Admin User User
	        ln.func_LoginToIDIOM(emailid, password);	
		    CustomReporter.log("Logged in successfully");
		    
		    
		    //Step 3:Click on Admin Access from Menu
		    ClientList_Page cl= new ClientList_Page(driver);
		    PageHeader ph=new PageHeader(driver);
		    rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	       // rm.webElementSync(pageHeader.personMenu, "visibility");	    
		    ph.performAction("adminaccess");
		    CustomReporter.log("The user has clicked on 'Admin Access'");
		    
		    
		    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
		    Thread.sleep(3000);
		    rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
		    if(up.func_ElementExist("Users List")){
		    	
		    	CustomReporter.log("The user listing is displayed");
		    }
		    Thread.sleep(10000);
		    //driver.findElement(By.xpath("//*[@class='navigation-user-menu__chevron ion ion-chevron-down']")).click();
		    Thread.sleep(2000);
		    WebElement firstbutton=driver.findElement(By.xpath(".//*[@class='layout center-center horizontal wrap']//*[@class='user-selected ng-pristine ng-untouched ng-valid ng-empty']"));
		    firstbutton.sendKeys();
		  //Verify that we in the first field
		  if(driver.switchTo().activeElement().equals(firstbutton))
		      System.out.println("First element is in a focus");
		  else
		      //Add Assertion here - stop execution
		      System.out.println("ASSERTION - first element not in the focus");
		  List<WebElement> editcheckbox=driver.findElements(By.xpath(".//*[@class='layout center-center horizontal wrap']//*[@class='user-selected ng-pristine ng-untouched ng-valid ng-empty']"));
		  List<WebElement> editlink=driver.findElements(By.xpath(".//*[@class='td centered action-buttons']/*[@class='btn btn-link edit ng-scope']"));
		  int count=0;
		 for(WebElement editbutton:editcheckbox) {
			count=count+1; 
		 }
		 System.out.println("Edit checkbox count is "+count);
		 int count1=0;
		 for(WebElement editlinkbtn:editlink) {
				count1=count1+1; 
			 }
			 System.out.println("Edit link count is "+count1);
		 WebElement activeelement=firstbutton;
		 
		 for(int i=0;i<=(count+count1)-1;i++){
			 activeelement.sendKeys(Keys.TAB);
			 activeelement=driver.switchTo().activeElement();
		 }
		 CustomReporter.log("The user has navigated till the last element");
		 Thread.sleep(10000);
		  System.out.println(activeelement); 
		  
		  ph.performAction("logout");
		  CustomReporter.log("User has been logged out");
		}catch(Exception e){
			e.printStackTrace();
		}
}
	
}
	