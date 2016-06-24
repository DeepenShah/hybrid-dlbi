package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Def 2286_Client HomePage_Verify refreshing page</p>
 *  <p> <b>Objective:</b>Def 2286_Client HomePage_Verify refreshing page </p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8963.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Abhishek Deshpande
 * @since 01 Jun 2016
 *
 */
public class ClientHomePage8963 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyEditOverlay(){		
			
		String strProjectName="Automation Project - 8963";
		String strProjectDesc = "This project is created to verify the duplicate functionality";
		String strMsg="";
		String strDeletionDetails="";
		String strAudienceName="";	
		
		boolean bProjectCreated = false;	
		
		try{
			
			//****************Test step starts here************************************************
			
			//Step 1 and 2: Login to application with Valid credentials and Select a client
			loginToSelectClient();	
			
			//Step3: Click on New Project			
			clientListPage.func_PerformAction("New Project");
			if(!rm.webElementSync(clientListPage.newProjectWindow, "visibility"))
				throw new IDIOMException("Not able to find new project overlay.###FailedToVerifyNewProjectWindow");
			
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);							
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Step4: Add project name and description and click on Save button
			clientListPage.fillProject(strProjectName,strProjectDesc);				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			CustomReporter.log("Project " +strProjectName + " created successfully");
			
			//Storing detail for deletion
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			bProjectCreated = true;            	
			
			//Close the overlay
			clientListPage.func_PerformAction("Close Project");	
			Thread.sleep(4000);
			CustomReporter.log("Clicked on close 'X' icon");
			
			//Step 5: Refresh client home page
			ClientList_Page cl = new ClientList_Page(driver);
			driver.navigate().refresh();
			CustomReporter.log("Refresh client home page");
			Thread.sleep(3000);
			
			//Step 6: Client home page should load successfully
			if(!cl.isVisible("logo")&&cl.isVisible("newaudiencebtn")&&cl.verfiyProjectIsExist(strProjectName))
				throw new IDIOMException("Failed to load client home page after refresh.###FailedToClientHomePage");
			
			CustomReporter.log("Verified Client home page after page refresh");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8963", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project");					
				}
				
				//Step 10 - Click on logout 
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
}