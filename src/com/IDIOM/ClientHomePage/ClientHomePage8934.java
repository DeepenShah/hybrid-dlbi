package com.IDIOM.ClientHomePage;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>2391_Manage Projects_Verify renaming of newly created Audience</p>
 *  <p> <b>Objective:</b>Verify renaming of newly created Audience</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8934.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 15 Jun 2016
 *
 */
public class ClientHomePage8934 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyRenameNewAudience(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="";
		String strAudienceName="";
		
		
		boolean bStatus = true;
		try{
			
			//****************Test step starts here************************************************
						
			//Step1-2: Login To Selecting Client
			loginToSelectClient();			
			
			//Step3: Select/Create project			
			strProjectName = clientListPage.createNewProject("");
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//Step4: Edit Project and Go to audience tab
			clientListPage.performActionOnProject("edit",strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			//Step5: Create new audience
			strAudienceName = clientListPage.createNewAudience("");
			CustomReporter.log("Created new audience with name " + strAudienceName);
			
			//Step6: Rename the audience
			clientListPage.performActionOnAudience(strAudienceName, "rename");
			clientListPage.fillAudience(strAudienceName+"_Rename", strAudienceName);
			clientListPage.performActionOnAudience(strAudienceName, "save");
			Thread.sleep(2000);
			
			CustomReporter.log("Rename the audience to " + strAudienceName +"_Rename");
			
			List<String> strAudiences = clientListPage.func_getListOfAudiencesForEditedProject();
			CustomReporter.log("Fetch all audiences. " + strAudiences);
			
			if(!strAudiences.contains(strAudienceName+"_Rename"))
				throw new IDIOMException("Failed to rename newly created audience. ###RenamedFailedForNewlyCreatedAudience");
			
			CustomReporter.log("Successfully renamed newly created audience");
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				
				if(!strProjectDetails.equalsIgnoreCase("") && bStatus){
					util.deleteProjectOrAudience(strProjectDetails, true);
					CustomReporter.log("Deleted Project");
				}
				
				//Logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(strClassName + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}		
	}
}
