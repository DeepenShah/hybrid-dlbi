package com.IDIOM.ClientHomePage;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Def 2372_Client HomePage_Verify Audience is not getting saved on not clicking on 'Create and Save' </p>
 *  <p> <b>Objective:</b>Verify that the audience is not getting saved if the user is not clicking on 'Create and Save' in new project overlay</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8976.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Abhishek Deshpande
 * @since 01 Jun 2016
 *
 */
public class ClientHomePage8976 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifySaveAudience(){		
			
		String strProjectName="Automation Project - 8976";
		String strProjectDesc = "This project is created to verify Audience is not getting saved if user don't click on 'Create and Save' button";
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
			
            //Step5: Create new audience			
			clientListPage.func_PerformAction("Audience Tab");					
			clientListPage.func_PerformAction("New Audience");
			CustomReporter.log("Clicked on '+New Audience' link");			
			
			//Step7: Enter the audience name and don't click on 'Create and Save' button
			strAudienceName = "Auto Audi-8976 ";	
			clientListPage.fillAudience(strAudienceName,"");
			CustomReporter.log("Enter the Audience name "+strAudienceName+" and don't click on 'Create and Save' button");	
			
			//Close the overlay
			clientListPage.func_PerformAction("Close Project");	
			Thread.sleep(4000);
			CustomReporter.log("Clicked on close 'X' icon");			
			
			//Select the project and verify the new audience is not created
			ArrayList<String> audiences = clientListPage.func_getListOfAudiencesForSelectedProject(strProjectName);
			
			boolean found = false;
			for (int i=0; i<audiences.size(); i++){	
				System.out.println("The audience size is "+audiences.size());
				CustomReporter.log("The project "+strProjectName+" has audience "+audiences.get(i));
				
				if (audiences.get(i)==strAudienceName){
					found = true;
					CustomReporter.errorLog("The new audience is displayed in the dropdown list even though user has not saved it");				
			       }
			   }
			
			if(found)
				throw new IDIOMException("Failed at verification of audiences in the drop down list ###8976_FailedToVerifyAudiences");
			
			CustomReporter.log("The new audience "+strAudienceName+" is Not displayed in the drop down list as expected");
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("" + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8976", "fail");
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
