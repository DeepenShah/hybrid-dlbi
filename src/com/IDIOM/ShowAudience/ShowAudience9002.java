package com.IDIOM.ShowAudience;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.IDIOM.pages.ShowAudiencePage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Show_Audience_Widget_Verify it on Pathing Page</p>
 *  <p> <b>Objective:</b>Verify Show Audience Widget on Pathing Page</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9002.aspx</p>
 *  <p> <b>Module:</b>Show Audience</p>
 *  
 * @author Deepen Shah
 * @since 17 Jun 2016
 *
 */
public class ShowAudience9002 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyShowAudiencePanelOnPathingPage(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="";		
		
		boolean bStatus=true;
		
		try{
			
			String strPathingLink = property.getProperty("pathing");
			String strSuccessMetricLink = property.getProperty("successMetrics");
			String strSuccessMetrics = property.getProperty("commonSuccessMetrics");
			String strAttribute1 = property.getProperty("cmmonAudienceAttribute");
			String strAttribute2 = property.getProperty("commonAudienceAttribute2");
			//****************Test step starts here************************************************
						
			//Step1-3: Login To Selecting Client
			loginToSelectClient();			
			
			//Step4-5: Create Project
			strProjectName = clientListPage.createNewProject("");
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//Step6: Launch Project
			clientListPage.launchProject(strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			if(!rm.webElementSync(pdp.projectName, "visibility"))
				throw new IDIOMException("Not able to land on project dashboard page. ###FailedToLaunchProject");
			
			CustomReporter.log("Navigated to project dashboard page");
			
			//Navigate to success metric page
			pdp.navigateTo(strSuccessMetricLink);
			
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			
			if(!rm.webElementSync(abPage.allMetricsLabel, "visibility"))
				throw new IDIOMException("Failed to land on Success Metric page. ###FailedToLandOnSuccessMetricPage");
			
			CustomReporter.log("Navigated to Success Metric Page");

			//step7: Select some metrics
			abPage.selectMetricByName(strSuccessMetrics);
			CustomReporter.log("Added some metrics " + strSuccessMetrics);
			
			//Step8: Move to audience def page
			abPage.performAction("gotoaudiencedefinition");
			
			if(!rm.webElementSync(abPage.addNewGroupLink, "visibility"))
				throw new IDIOMException("Failed to open audience definition page.###FailedToOpenAudienceDefPage");
			
			CustomReporter.log("Moved to audience definition page");
			
			//Step9: Add some attributes
			abPage.selectAttributeOnAudienceDefinition(strAttribute1);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			CustomReporter.log("Added " + strAttribute1 +" attribute to the group");
			
			abPage.selectAttributeOnAudienceDefinition(strAttribute2);
			abPage.performAction("addattribute");
			rm.webElementSync("tillelementisstale", abPage.strPopulationSpinningBar);
			Thread.sleep(1000);
			CustomReporter.log("Added " + strAttribute2 +" attribute to the group");
			
			
			//Step10: Get data for projected and actual tab
			int iExpProjectedPopUserCount = abPage.getSelectedPopulationValue();
			int iExpProjectedPopPercentage = abPage.getPopulationPercentage();
			double dExpProjectedOptimalAudPercentage = abPage.getOptimalAudiencePercentage();
			
			List<String> strExpProjectedSuccessMetrics = abPage.successMetricsLeftSideGetAllAttributes("projected");
			rm.captureScreenShot("Pathing-ProjectedTab", "pass");
			
			//Navigate to actual tab
			abPage.performAction("actualpopulationtab");
			Thread.sleep(3000);
			rm.webElementSync("pageload");
			
			int iExpActualPopUserCount = abPage.getSelectedPopulationValue();
			int iExpActualPopPercentage = abPage.getPopulationPercentage();
			double dExpActualOptimalAudPercentage = abPage.getOptimalAudiencePercentage();
			
			List<String> strExpActualSuccessMetrics = abPage.successMetricsLeftSideGetAllAttributes("actual");
			rm.captureScreenShot("Pathing-ActualTab", "pass");
			
			//Data
			CustomReporter.log("Projected Tab Data: " + iExpProjectedPopPercentage + "--" + iExpProjectedPopUserCount + "--" + dExpProjectedOptimalAudPercentage);
			CustomReporter.log("Projected Tab Metrics: " + strExpProjectedSuccessMetrics);
			
			
			CustomReporter.log("Actual Tab Data: " + iExpActualPopPercentage + "--" + iExpActualPopUserCount + "--" + dExpActualOptimalAudPercentage);
			CustomReporter.log("Actual Tab Metrics: " + strExpActualSuccessMetrics);
			
			//Step11: Navigate to Pathing page
			pageHeader.megaMenuLinksClick(strPathingLink);
			
			Analyse_Pathing_Page pathingPage = new Analyse_Pathing_Page(driver);
			rm.webElementSync("idiomspinningcomplete");
			
			if(!rm.webElementSync(pathingPage.pathingWheel, "visible"))
				throw new IDIOMException("Failed to land on Pathing page.###FailedToLandPathingPage");
			
			CustomReporter.log("Navigated to pathing page");
			
			//Step12: Open Show Audience window
			pathingPage.func_ClickElement("showaudience");
			rm.webElementSync("idiomspinningcomplete");
			
			ShowAudiencePage saPage = new ShowAudiencePage(driver);
			
			if(!rm.webElementSync(saPage.populationPane,"visibility"))
				throw new IDIOMException("Not able to open 'Show Audience' panel.###FailedToOpenShowAudiencePanel");
			
			CustomReporter.log("Open Show Audience panel");
			
			//Fetch all the data
			int iActualProjectedPopUserCount = saPage.getPopulationUserCount();
			int iActualProjectedPopPercentage = saPage.getPopulationPercentage();
			double dActualProjectedOptimalAudPercentage = saPage.getOptimalAudiencePercentage();
			
			List<String> strActualProjectedSuccessMetrics = saPage.getSuccessMetricsDetails();
			
			//Step13: Comparing data			
			
			//Comparing all data. 'Projected'
			if(iExpProjectedPopPercentage != iActualProjectedPopPercentage){
				bStatus=false;
				CustomReporter.errorLog("Projected Population percentage is not matching. Expected " + iExpProjectedPopPercentage +
						" and found " + iActualProjectedPopPercentage);
			}else{					
				CustomReporter.log("Verified projected population percentage");
			}
				
			if(iExpProjectedPopUserCount != iActualProjectedPopUserCount){
				bStatus=false;
				CustomReporter.errorLog("Projected user count is not matching. Expected " + iExpProjectedPopUserCount + 
						" and found " + iActualProjectedPopUserCount);
			}else{					
				CustomReporter.log("Verified projected User count");
			}
				
			if(dExpProjectedOptimalAudPercentage != dActualProjectedOptimalAudPercentage){
				bStatus=false;
				CustomReporter.errorLog("Projected optimal audience value is not matching. Expected " +dExpProjectedOptimalAudPercentage +
						" and found " + dActualProjectedOptimalAudPercentage);
			}else{					
				CustomReporter.log("Verified projected optimal audience value");
			}
				
			if(!strExpProjectedSuccessMetrics.equals(strActualProjectedSuccessMetrics)){
				bStatus=false;
				CustomReporter.errorLog("Projected metrics details are not matching. Expected " + strExpProjectedSuccessMetrics + 
						" and found " + strActualProjectedSuccessMetrics);
			}else{
				CustomReporter.log("Verified all added metrics details: Name, Percentage and Color , in Projected tab");
			}
			
			if(!bStatus)
				exceptionCode(new IDIOMException("Validation failed for projected tab.###ProjectedTab-ValidationFailed"), strClassName);
			
			bStatus = true;
			
			//clicking on actual tab
			saPage.clickTab("actual");
			Thread.sleep(3000);
			rm.webElementSync("pageload");
			
			int iActualActualPopUserCount = saPage.getPopulationUserCount();
			int iActualActualPopPercentage = saPage.getPopulationPercentage();
			double dActualActualOptimalAudPercentage = saPage.getOptimalAudiencePercentage();
			
			List<String> strActualActualSuccessMetrics = saPage.getSuccessMetricsDetails();
			
			//Comparing all data. 'Actual'
			if(iExpActualPopPercentage != iActualActualPopPercentage){
				bStatus=false;
				CustomReporter.errorLog("Actual Population percentage is not matching. Expected " + iExpActualPopPercentage +
						" and found " + iActualActualPopPercentage);
			}else{					
				CustomReporter.log("Verified Actual population percentage");
			}
				
			if(iExpActualPopUserCount != iActualActualPopUserCount){
				bStatus=false;
				CustomReporter.errorLog("Actual user count is not matching. Expected " + iExpActualPopUserCount + 
						" and found " + iActualActualPopUserCount);
			}else{					
				CustomReporter.log("Verified Actual User count");
			}
				
			if(dExpActualOptimalAudPercentage != dActualActualOptimalAudPercentage){
				bStatus=false;
				CustomReporter.errorLog("Actual optimal audience value is not matching. Expected " +dExpActualOptimalAudPercentage +
						" and found " + dActualActualOptimalAudPercentage);
			}else{					
				CustomReporter.log("Verified Actual optimal audience value");
			}
				
			if(!strExpActualSuccessMetrics.equals(strActualActualSuccessMetrics)){
				bStatus=false;
				CustomReporter.errorLog("Actual metrics details are not matching. Expected " + strExpActualSuccessMetrics + 
						" and found " + strActualActualSuccessMetrics);
			}else{
				CustomReporter.log("Verified all added metrics details: Name, Percentage and Color , in Actual tab");
			}
			
			if(!bStatus)
				exceptionCode(new IDIOMException("Validation failed for Actual tab.###ActualTab-ValidationFailed"), strClassName);		
			
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
