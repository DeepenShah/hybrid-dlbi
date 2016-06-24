package com.IDIOM.SuccessMetrics.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name</b> Success metrics: Verify optimal audience percentage </p>
<p>	<b>Objective</b> To verify audience switching is working correctly after navigating from project home page to Audience definition(AD) page. </p>
<p>	<b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/9039.aspx </p>
<p>	<b>Module:</b> Success Metrics </p>
@author: Shailesh Kava
@since: 06-May-2016
**********************************************************************/
public class SuccessMetrics8946 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder = null;
	
	@Test
	public void verifyOptimalAudiencePercentage(){
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		audienceBuilder = new AudienceBuilderPage(driver);
		
		try{
			//****************Variables declaration and Initialization****************************	
			String audienceName="New Audience";
			String selAttribute = property.getProperty("attribute8946").trim();
			String expectedQuery = property.getProperty("expectedQuery").trim();
			String defaultAud = property.getProperty("defaultaudience").trim();
			//****************Test step starts here************************************************
			
			System.out.println(loginToSelectClient());
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 3: Create/Select project and launch the same 
			CustomReporter.log("Creating new project");
			System.out.println("Creating new project");
			
			strProjectName = clientListPage.createNewProject("");
			
			clientListPage.performActionOnProject("edit", strProjectName);
		    clientListPage.findAndSaveProjectWindow(false, strProjectName);
		    
		    clientListPage.func_PerformAction("Audience Tab");
		    
		    clientListPage.createNewAudience(audienceName);
		    Thread.sleep(2000);
		    clientListPage.func_PerformAction("Launch Project");
		    Thread.sleep(2000);
		    
		    ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 4: Click on success metrics link and add few metrics 
			CustomReporter.log("Navigate to success metrics page");
			System.out.println("Navigate to success metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###SuccessMetrics8946-successMetricsNotOpen");
			
			//Step 5: Select few attributes
			audienceBuilder.selectMetricByName(property.getProperty("SelectingSuccessMetrics8685"));
			
			//Step 6: Click on Audience definition page and add attribute "Gender: Female"
			CustomReporter.log("Navigate to Audience Defination page");
			audienceBuilder.performAction("gotoaudiencedefinition");
			
			Thread.sleep(3000);
			audienceBuilder.selectAttributeOnAudienceDefinition(selAttribute);
			audienceBuilder.performAction("addattribute");
			
			String getQueryLogicFromPage = audienceBuilder.getQueryWithLogic("1.1").trim();
			System.out.println(getQueryLogicFromPage+"==="+expectedQuery);
			
			if(!getQueryLogicFromPage.equals(expectedQuery))
				throw new IDIOMException("Failed to add attribute###SuccessMetrics8946-attributesNotAdded");
			
			CustomReporter.log("Selected attribute is added in default group ["+expectedQuery+"]");
			System.out.println("Selected attribute is added in default group ["+expectedQuery+"]");
			
			//changing audience in drop down
			if(!pageHeader.selectAudienceFromDropDown(defaultAud))
			    throw new IDIOMException("Failed to change audience ["+defaultAud+"]###SuccessMetrics8946-changeAudienceIssue");
			
			Thread.sleep(5000);
			CustomReporter.log("Audience is changed successfully ["+defaultAud+"]");
			
			//Step 8: Switch back to 'aud2' and Click on client logo in page header
			pageHeader.clientLogo.click();
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			
			if(!rm.webElementSync(audienceBuilder.addNewGroupLink, "visibility"))
				throw new IDIOMException("Failed to open Audience Builder page###SuccessMetrics8946-audienceDefinitionNotOpen");
			
			CustomReporter.log("Audience builder page is open successfully");
			
			if(audienceBuilder.getTotalQueryItems()>=1)
				throw new IDIOMException("Query should not appear for Audience "+defaultAud+" ###SuccessMetrics8946-attributeShouldNotDisplay");
			
			CustomReporter.log("Added attribute for ["+audienceName+"] should not appear for ["+defaultAud+"]");
		
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8946", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");					
					Thread.sleep(4000);
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");
				}
				
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