package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Manage Projects 2.b.vi : Edit the audience.</p>
 *  <p> <b>Objective:</b>To verify edit functionality for audience</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8856.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 13 Jun 2016
 *
 */
public class ClientHomePage8856 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyEditAudience(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="";
		String strAudienceName="";
		
		
		try{						
			//****************Test step starts here************************************************
						
			//Step1-3: Login To Selecting Client
			loginToSelectClient();			
			
			//Step4: Select/Create project			
			strProjectName = clientListPage.createNewProject("");
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//Step5: Create audience
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
						
			strAudienceName = clientListPage.createNewAudience("");
			
			CustomReporter.log("Created new audience " + strAudienceName);
			
			//Step6: Click 'Edit' for newly created audience
			clientListPage.performActionOnAudience(strAudienceName, "edit");
			
			CustomReporter.log("Clicked on 'Edit' for newly created audience");
			
			//Verify the landing page
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			
			if(!abPage.isVisible("nosuccessmetrictext"))
				throw new IDIOMException("Not able to verify success metric page.###FailedToCheckSuccessMetricPage");
			
			CustomReporter.log("Verified success metric page after clicking 'Edit'.");
			
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				
				if(!strProjectDetails.equalsIgnoreCase("")){
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
