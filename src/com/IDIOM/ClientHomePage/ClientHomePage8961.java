package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Def 2306_Client HomePage_Verify duplicating duplicated audience </p>
 *  <p> <b>Objective:</b>Verify that the user is able to duplicate the duplicated audience</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8961.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 01 Jun 2016
 *
 */
public class ClientHomePage8961 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyDuplicatingDuplicatedAudience(){		
			
		String strProjectName="";
		String strAudienceName="";		
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;	
		
		try{
			String strNewAudienceWaterMark = property.getProperty("newAudienceWaterMark");
			//****************Test step starts here************************************************
			
			//Login To Selecting Client
			loginToSelectClient();			
			
			//Step3&4: Select/Create project			
			strProjectName = clientListPage.createNewProject("");
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			bProjectCreated = true;
			
			
			//Step5: Create new audience
			
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			strAudienceName = "Auto Audi-8961 ";
			
			clientListPage.func_PerformAction("New Audience");
			CustomReporter.log("Clicked on '+New Audience' link");
						
			//Verify new elements for create new audience
			if(!clientListPage.isVisible("newaudiencerow"))
				throw new IDIOMException("Failed to verify new row for audience###NoNewAudienceRowFound");
			
			CustomReporter.log("Verified visibility of new audience row");
			
			//Step6: Verifying water mark
			String strActualWaterMark = clientListPage.getNewAudienceTextFieldWaterMark();
			
			if(!strActualWaterMark.equalsIgnoreCase(strNewAudienceWaterMark))
				throw new IDIOMException("New audience water mark is not matching. Expected " +
			strNewAudienceWaterMark +" and found " + strActualWaterMark +".###WaterMarkTextIsNotMatching");
			
			CustomReporter.log("Verified water mark text " + strNewAudienceWaterMark);
			
			//Step7: Fill the name
			clientListPage.fillAudience(strAudienceName,"");
			
			//Save the audience
			clientListPage.performActionOnAudience("", "createandsave");
			Thread.sleep(3000);					
			
			CustomReporter.log("Created new audiences "+ strAudienceName +" in project " + strProjectName);
			
			//Step8: Click on duplicate
			clientListPage.performActionOnAudience(strAudienceName, "duplicate");
			Thread.sleep(1000);
			
			if(clientListPage.getAudienceCount(strAudienceName) != 2)
				throw new IDIOMException("Not able to duplicate the audience " +strAudienceName +".###FailedToDuplicateAudience");
			
			CustomReporter.log("Successfully duplicated the newly created audience");
			CustomReporter.log("Total audiences " + clientListPage.func_getListOfAudiencesForEditedProject());
			
			//Step9: Duplicating duplicated audience
			clientListPage.performActionOnDuplicateAudience("duplicate", strAudienceName, 2);
			Thread.sleep(1000);
			
			if(clientListPage.getAudienceCount(strAudienceName) != 3)
				throw new IDIOMException("Not able to duplicate the duplicated audience " +strAudienceName +".###FailedToDuplicateDuplicatedAudience");
			
			CustomReporter.log("Successfully duplicated the duplicated audience");
			CustomReporter.log("Total audiences " + clientListPage.func_getListOfAudiencesForEditedProject());
			
			clientListPage.func_PerformAction("Close Project");
			Thread.sleep(2000);			
			
					
			
		}catch(Exception e){
			exceptionCode(e);
		}finally{
			try{
				
				//Deleting newly created project or Audience			
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project");					
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
		
	public void exceptionCode(Exception ie){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(strClassName + ": " + strMsgAndFileName[0]);
		ie.printStackTrace(System.out);
		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot("8961-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8961-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
