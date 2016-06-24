package com.IDIOM.ClientHomePage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Manage Projects 1.a.ii : Verify except 'modified' all the fields should be present for project. </p>
 * <p> <b>Objective:</b> For Projects, mainly 5 columns are displayed. 1. Project Name 2. Audience 3. Created 4. Modified 5. (Toggle Option) </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8817.aspx </p>
 * <p> <b>Module:</b> Client Home Page </p>
 * @author Rohan Macwan
 * @since 03 June 2016
 *
 */
public class ClientHomePage8817 extends BaseClass {

	@Test
	public void	verifyWhetherValuesPresentForEachProject(){
		String strMsg = null;		
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		
		try{
			//****************Test step starts here************************************************
			
			//loginToSelectClient
			//Step1: Open appropriate environment URL.
			//Step 2 - Provide valid credential
			//Step 3 - Select a client if not selected in previous login
			loginToSelectClient();
			
			//Step 4 - Check the values for any of the project.
			//Step 5 - Repeat step 4  for any other project as well.
			if (clientListPage.projectList.size()==0)
			{	
				strProjectName = clientListPage.createNewProject("");
				bProjectCreate=true;
				strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
				
			}
				
			Thread.sleep(8000);
			if(!verifyWhetherValueIsPresentInAllFieldsForEachProject())
				throw new IDIOMException("Some of the Data is missing in Project list.");
									
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog(strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8817", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step 6 - Click on logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		//****************Test step ends here************************************************
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	}
	
	public boolean verifyWhetherValueIsPresentInAllFieldsForEachProject() throws Exception{
		boolean status=true;
		int i=1;
		String strProject="";
		String strCreated="";
		String strModified="";
		String strAudience="";
		
		for(WebElement aRow:clientListPage.projectRows)
		{
			strProject="";
			strCreated="";
			strModified="";
			strAudience="";
			
			strProject=aRow.findElement(By.xpath(clientListPage.strProjectNameButton)).getText().trim();
			strCreated=aRow.findElement(By.xpath(clientListPage.strCreatedDateValue)).getText().trim();
			strModified=aRow.findElement(By.xpath(clientListPage.strModifiedDateValue)).getText().trim();
			strAudience=aRow.findElement(By.xpath(clientListPage.strAudienceDropdownValue)).getText().trim();
						
			//Checking Project Name in particular Row
			if((strProject.equals(null) || strProject.equals("")))
			{
				CustomReporter.errorLog("Project Name is not present at Row " + i);
				BaseClass.testCaseStatus=false;
				status=false;
				rm.captureScreenShot("ProjectNameNotPresentAtRow" +i, "fail");
			}
			else
			{
				CustomReporter.log("Project Name - " + strProject + " is correctly present on Row " + i);
			}
			
			//Checking value in Audience dropdown in particular Row
			if((strAudience.equals(null) || strAudience.equals("")))
			{
				CustomReporter.errorLog("Value in Audience Dropdown is not present or Dropdown is missing at Row " + i);
				BaseClass.testCaseStatus=false;
				status=false;
				rm.captureScreenShot("AudienceDropdownValueNotPresentAtRow" +i, "fail");
			}
			else
			{
				CustomReporter.log("Value - " + strAudience + " in Audience Dropdown is correctly present on Row " + i);
			}
			
			//Checking Created Date in particular Row
			if((strCreated.equals(null) || strCreated.equals("")))
			{
				CustomReporter.errorLog("Created Date is not present at Row " + i);
				BaseClass.testCaseStatus=false;
				status=false;
				rm.captureScreenShot("CreatedDateNotPresentAtRow" +i, "fail");
			}
			else
			{
				CustomReporter.log("Created Date - " + strCreated + " is correctly present on Row " + i);
			}
			
			//Checking Modified Date in particular Row
			if((strModified.equals(null) || strModified.equals("")))
			{
				CustomReporter.errorLog("Modified Date is not present at Row " + i);
				BaseClass.testCaseStatus=false;
				status=false;
				rm.captureScreenShot("ModifiedDateNotPresentAtRow" +i, "fail");
			}
			else
			{
				CustomReporter.log("Modified Date - " + strModified + " is correctly present on Row " + i);
			}
			
			//Checking the presence of Toggle Option in a particular row
			if(!rm.webElementSync(aRow.findElement(By.xpath(clientListPage.strToggleOptionElement)), "visibilitynowait"))
			{
				CustomReporter.errorLog("Toggle option is not present on Row " + i);
				BaseClass.testCaseStatus=false;
				status=false;
				rm.captureScreenShot("ToggleOptionNotPresentAtRow" +i, "fail");
			}
			else
			{
				CustomReporter.log("Toggle Element is correctly present on Row " + i);
			}
						
			i++;
			
		}
		
		return status;
	}
}
