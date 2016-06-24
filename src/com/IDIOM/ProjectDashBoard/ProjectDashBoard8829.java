package com.IDIOM.ProjectDashBoard;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Dashboard: Verify hide action on any items of project dashboard</p>
 *  <p> <b>Objective:</b>Hide items should be removed from project dashboard and hidden item count should appear at top right side</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8829.aspx</p>
 *  <p> <b>Module:</b>Project Dashboard</p>
 *  
 * @author Deepen Shah
 * @since 16 Jun 2016
 *
 */
public class ProjectDashBoard8829 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyHideLinksAndShowHiddenLinksCount(){		
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
			
			//Getting hidden links count
			int iCount = pdp.getHiddenLinksCount();
			
			if(iCount != 2)
				throw new IDIOMException("Hidden link coun is not matching. Expected 2 and found " + iCount + ".###HiddeLinkCountIsNotMatching");
			
			CustomReporter.log("Verified hidden link count. It is " + iCount);
			
			//Verifying visibility of links
			if(pdp.isVisible("link", strProfileLink))
				throw new IDIOMException("Profile link is still visible.###ProfileLinkIsVisible");
			
			CustomReporter.log("Verified: Profile link is not visible");

			if(pdp.isVisible("link", strRankerLink))
				throw new IDIOMException("Digital Ranker link is still visible.###DigitalRankereLinkIsVisible");
			
			CustomReporter.log("Verified: Digital Ranker link is not visible");

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
