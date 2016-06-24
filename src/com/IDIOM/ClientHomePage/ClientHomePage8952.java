package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>2621_Manage Projects_ManagingClient_Verify 'Yes' and 'No' options are shown only when user select delete option.</p>
 *  <p> <b>Objective:</b>To verify whether after deleting one project while clicking on setting icon another time for any project shows "Yes" and "No" options also along with another options.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8952.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 16 Jun 2016
 *
 */
public class ClientHomePage8952 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyYesNoVisibleAfterClickingDelete(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="";		
		
		String strProjectDetails2="";
		String strProjectName2="";	
		
		boolean bStatus = true;
		try{
			
			//****************Test step starts here************************************************
						
			//Step1-2: Login To Selecting Client
			loginToSelectClient();			
			
			//Create two projects		
			strProjectName = clientListPage.createNewProject("");
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			strProjectName2 = clientListPage.createNewProject("");
			strProjectDetails2 = clientListPage.getAudienceProjectClientId(strProjectName2, "");
						
			//Step3: Select any project and click delete
			clientListPage.performActionOnProject("delete", strProjectName);
			CustomReporter.log("Clicked delete for " + strProjectName);
			
			//Step4: Clicked Yes
			clientListPage.performActionOnProject("yes", strProjectName);
			CustomReporter.log("Clicked on Yes");
			Thread.sleep(2000);
			
			if(clientListPage.verfiyProjectIsExist(strProjectName)){	
				bStatus = false;
				throw new IDIOMException("Not able to deleted the project " +strProjectName+".###FailedToDeleteProject");
			}
			
			CustomReporter.log("Deleted " + strProjectName);
			
			//Step5: Clicking toggle for other project.
			clientListPage.performActionOnProject("toggle", strProjectName2);
			CustomReporter.log("Clicked on toggle option for " +strProjectName2);
			
			if(clientListPage.isVisible("deleteyes"))
				throw new IDIOMException("Yes button visible without clicking delete option for project " + strProjectName2 +
						".###YesIsVisible");
			
			CustomReporter.log("Verified: Yes button is not visible without clicking delete");
			
			if(clientListPage.isVisible("deleteno"))
				throw new IDIOMException("No button visible without clicking delete option for project " + strProjectName2 +
						".###NoIsVisible");
			
			CustomReporter.log("Verified: No button is not visible without clicking delete");
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				
				if(!strProjectDetails.equalsIgnoreCase("") && !bStatus){
					util.deleteProjectOrAudience(strProjectDetails, true);
					CustomReporter.log("Deleted Project");
				}
				
				if(!strProjectDetails2.equalsIgnoreCase("")){
					util.deleteProjectOrAudience(strProjectDetails2, true);
					CustomReporter.log("Deleted 2nd Project");
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
