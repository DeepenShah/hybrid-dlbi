package com.IDIOM.ProjectDashBoard;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Dashboard: Verify links after clicking 'Show Hidden Links' button</p>
 *  <p> <b>Objective:</b>To verify all the links that are hidden should be visible after clicking 'Show Hidden Links' buttons</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9189.aspx</p>
 *  <p> <b>Module:</b>Project Dashboard</p>
 *  
 * @author Deepen Shah
 * @since 17 Jun 2016
 *
 */
public class ProjectDashBoard9189 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyClickBehaviorOnShowHiddenLinks(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="";		
		
		try{
			
			String strProfileLink = property.getProperty("profile");
			String strRankerLink = property.getProperty("digitalRanker");
			//****************Test step starts here************************************************
						
			//Step1-3: Login To Selecting Client
			loginToSelectClient();			
			
			//Step4: Create Project
			strProjectName = clientListPage.createNewProject("");
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//Launch Project
			clientListPage.launchProject(strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			
			if(!rm.webElementSync(pdp.projectName, "visibility"))
				throw new IDIOMException("Failed to land on Project Dashboard page.###FailedToLandOnProjectDashboardPage");
			
			CustomReporter.log("Navigated to Project Dashboard page");
			
			//Step5: Hiding few links
			pdp.hideLink(strProfileLink);
			Thread.sleep(2000);
			CustomReporter.log("Clicked Hide for " + strProfileLink + " link");
			
			pdp.hideLink(strRankerLink);
			Thread.sleep(2000);
			CustomReporter.log("Clicked Hide for " + strRankerLink + " link");
			
			if(!pdp.isVisible("showhiddenlinkslabel", ""))
				throw new IDIOMException("Not able to see 'Show Hidden Links' button. ###ShowHiddenLinksBtnNotvisible");
			
			CustomReporter.log("Successfully verified 'Show Hidden Links' button");			
			
			//Verifying visibility of links
			if(pdp.isVisible("link", strProfileLink))
				throw new IDIOMException("Profile link is still visible.###ProfileLinkIsVisible");
			
			CustomReporter.log("Verified: Profile link is not visible");

			if(pdp.isVisible("link", strRankerLink))
				throw new IDIOMException("Digital Ranker link is still visible.###DigitalRankereLinkIsVisible");
			
			CustomReporter.log("Verified: Digital Ranker link is not visible");
			
			//Clicking on 'Show Hidden Links'
			pdp.performActionOnElement("showhiddenlinks");
			Thread.sleep(2000);
			
			if(pdp.isVisible("showhiddenlinkslabel", ""))
				throw new IDIOMException("'Show Hidden Links' button is still visible. ###ShowHiddenLinksBtnIsvisible");
			
			CustomReporter.log("Successfully verified 'Show Hidden Links' button is not visible now.");			
			
			//Verifying visibility of links
			if(!pdp.isVisible("link", strProfileLink))
				throw new IDIOMException("Profile link is not visible after clicking Show Hidden Link button.###ProfileLinkIsNotVisible");
			
			CustomReporter.log("Verified: Profile link is visible now");

			if(!pdp.isVisible("link", strRankerLink))
				throw new IDIOMException("Digital Ranker link is not visible after clicking Show Hidden Link button.###DigitalRankereLinkIsNotVisible");
			
			CustomReporter.log("Verified: Digital Ranker link is visible now");

		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				
				if(!strProjectDetails.equalsIgnoreCase("")){
					util.deleteProjectOrAudience(strProjectDetails,true);
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
}
