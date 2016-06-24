package com.IDIOM.architect.scripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.IDIOM.pages.ShowAudiencePage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Media Ranker_ 4.1 a,b (i,ii)_Verify Show Audience section includes Population and Behaviors in Right Side Navigation  </p>
 * <p> <b>Objective:</b> Verify Population, Optimal Audience and Success Metrics in Left Side Navigation of Media Ranker page for Show Audience </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8302.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 *
 */
public class MediaRanker8302 extends BaseClass{
	
	
	@Test
	public void verifyShowAudiencePage(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		String strDeletionDetails="";
		
		boolean bAudienceCreated = false;
		String strAudienceName="";
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strMediaRankerPages = property.getProperty("mediaRankerItems");
			String strMetrics = property.getProperty("successMetricScenarioCommon");
			//****************Test step starts here************************************************
			
			//Step1: Launch url
			strMsg = "Launched Browser and Enter URL";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step2: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step3: Select client
			clientListPage = new ClientList_Page(driver);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");		
			
			
			//Step4&5: Select/Create project
			if(clientListPage.totalProject()>0){
				strProjectName = clientListPage.getProjectNameById(1);
			}else{
				strProjectName = clientListPage.createNewProject("");
				strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
				bProjectCreated = true;
			}			
			
			//Create new audience
			if(!bProjectCreated){
				clientListPage.performActionOnProject("edit", strProjectName);
				clientListPage.findAndSaveProjectWindow(false, strProjectName);
				
				clientListPage.func_PerformAction("Audience Tab");
				
				strAudienceName = clientListPage.createNewAudience("");
				clientListPage.func_PerformAction("Close Project");
				Thread.sleep(2000);
				strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, strAudienceName);
				bAudienceCreated= true;
			}
			
			
			//Step6: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(1000);
			
			//Step7: Navigate to success metric page and add some cards
			pdp.navigateTo(property.getProperty("successMetrics"));
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			
			//Adding metrics
			abPage.selectMetricByName(strMetrics);
			rm.webElementSync("pageload");
			
			//Storing details for future verification
			int iBeforeProjectedPercentage = abPage.getPopulationPercentage();
			int iBeforeProjectedUser = abPage.getSelectedPopulationValue();
			double iBeforeProjectedOptimalAudience = abPage.getOptimalAudiencePercentage();
			
			List<String> strBeforeProjectedSuccessMetrics = abPage.successMetricsLeftSideGetAllAttributes("projected");
			
			//Clicking on Actual tab.
			abPage.performAction("actualpopulationtab");
			Thread.sleep(3000);
			
			int iBeforeActualPercentage = abPage.getPopulationPercentage();
			int iBeforeActualUser = abPage.getSelectedPopulationValue();
			double iBeforeActualOptimalAudience = abPage.getOptimalAudiencePercentage();
			
			List<String> strBeforeActualSuccessMetrics = abPage.successMetricsLeftSideGetAllAttributes("actual");
			
			CustomReporter.log("Projected details: " + iBeforeProjectedPercentage +"-" + iBeforeProjectedUser + "--"+ iBeforeProjectedOptimalAudience);
			CustomReporter.log("Projected Metrics " + strBeforeProjectedSuccessMetrics);
			
			CustomReporter.log("Actual details: " + iBeforeActualPercentage +"-" + iBeforeActualUser + "--"+ iBeforeActualOptimalAudience);
			CustomReporter.log("Actual Metrics " + strBeforeActualSuccessMetrics);
						
			CustomReporter.log("Prepared all expected data");
			
			//Step8,9&10: Navigate to Media Ranker Page
			String[] strChannels = strMediaRankerPages.split(",");
			ArchitectMediaRankerPage mediaRanker = new ArchitectMediaRankerPage(driver);
			
			int iAfterProjectedPercentage;
			int iAfterProjectedUser;
			double iAfterProjectedOptimalAudience;			
			List<String> strAfterProjectedSuccessMetrics;
						
			int iAfterActualPercentage;
			int iAfterActualUser;
			double iAfterActualOptimalAudience;
			List<String> strAfterActualSuccessMetrics;
			boolean bProjectedStatus = true;
			boolean bActualStatus = true;
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!mediaRanker.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				try{
					//Step11: Click on 'Show Audience'
					rm.webElementSync(mediaRanker.showAudienceButton, "clickable");
					mediaRanker.func_ClickOnElement("showaudience");
					rm.webElementSync("idiomspinningcomplete");
					Thread.sleep(2000);
					
					//Step12: Verifying different components
					ShowAudiencePage saPage = new ShowAudiencePage(driver);
					
					//Population pane
					if(!saPage.isVisible("populationpane"))
						throw new IDIOMException("No population pane found.###PopulationPanelMissing");
					
					CustomReporter.log("Verified visibility of Population panel");
					
					//Optimal Audience pane
					if(!saPage.isVisible("optimalpane"))
						throw new IDIOMException("No Optimal Audience pane found.###OptimalAudiencePanelMissing");
					
					CustomReporter.log("Verified visibility of Optimal Audience panel");
					
					//Population pane
					if(!saPage.isVisible("metricspane"))
						throw new IDIOMException("No Metrics pane found.###MetricPanelMissing");
					
					CustomReporter.log("Verified visibility of Metric panel");
					
					//Fetching all projected data.
					iAfterProjectedPercentage = saPage.getPopulationPercentage();
					iAfterProjectedOptimalAudience = saPage.getOptimalAudiencePercentage();
					iAfterProjectedUser = saPage.getPopulationUserCount();
					strAfterProjectedSuccessMetrics = saPage.getSuccessMetricsDetails();
					
					CustomReporter.log("Projected details: " + iAfterProjectedPercentage +"--" + iAfterProjectedUser + "--"+ iAfterProjectedOptimalAudience);
					CustomReporter.log("Projected Metrics " + strAfterProjectedSuccessMetrics);
					
					//Comparing all data. 'Projected'
					if(iBeforeProjectedPercentage != iAfterProjectedPercentage){
						bProjectedStatus=false;
						CustomReporter.errorLog("Projected Population percentage is not matching");
					}else{					
						CustomReporter.log("Verified projected population percentage");
					}
						
					if(iBeforeProjectedUser != iAfterProjectedUser){
						bProjectedStatus=false;
						CustomReporter.errorLog("Projected user count is not matching");
					}else{					
						CustomReporter.log("Verified projected User count");
					}
						
					if(iBeforeProjectedOptimalAudience != iAfterProjectedOptimalAudience){
						bProjectedStatus=false;
						CustomReporter.errorLog("Projected optimal audience value is not matching");
					}else{					
						CustomReporter.log("Verified projected optimal audience value");
					}
						
					if(!strBeforeProjectedSuccessMetrics.equals(strAfterProjectedSuccessMetrics)){
						bProjectedStatus=false;
						CustomReporter.errorLog("Projected metrics details are not matching");
					}else{
						CustomReporter.log("Verified all added metrics details: Name, Percentage and Color , in Projected tab");
					}
									
					
					if(!bProjectedStatus)
						exceptionCode(new IDIOMException("Verification failed for Projected tab channel " + strChannel +".###FaileProjectedTab"));
					
					//Clicking on actual tab
					saPage.clickTab("actual");
					Thread.sleep(3000);
					rm.webElementSync("pageload");
					
					//Fetching Actual data.
					iAfterActualPercentage = saPage.getPopulationPercentage();
					iAfterActualOptimalAudience = saPage.getOptimalAudiencePercentage();
					iAfterActualUser = saPage.getPopulationUserCount();
					strAfterActualSuccessMetrics = saPage.getSuccessMetricsDetails();
					
					CustomReporter.log("Actual details: " + iAfterActualPercentage +"--" + iAfterActualUser + "--"+ iAfterActualOptimalAudience);
					CustomReporter.log("Actual Metrics " + strAfterActualSuccessMetrics);
					
					//Comparing 'Actual' data.
					if(iBeforeActualPercentage != iAfterActualPercentage){
						bActualStatus=false;
						CustomReporter.errorLog("Actual Population percentage is not matching");
					}else{
						CustomReporter.log("Verified Actual population percentage");
					}
						
					if(iBeforeActualUser != iAfterActualUser){
						bActualStatus=false;
						CustomReporter.errorLog("Actual user count is not matching");
					}else{
						CustomReporter.log("Verified Actual User count");
					}
						
					if(iBeforeActualOptimalAudience != iAfterActualOptimalAudience){
						bActualStatus=false;
						CustomReporter.errorLog("Actual optimal audience value is not matching");
					}else{
						CustomReporter.log("Verified Actual optimal audience value");
					}
						
					if(!strBeforeActualSuccessMetrics.equals(strAfterActualSuccessMetrics)){
						bActualStatus=false;
						CustomReporter.errorLog("Actual metrics details are not matching");
					}else{
						CustomReporter.log("Verified all added metrics details: Name, Percentage and Color, in actual tab");
					}
						
					if(!bActualStatus)
						exceptionCode(new IDIOMException("Verification failed for Actual tab channel " + strChannel +".###FaileActualTab"));
					
				}catch(IDIOMException ie){
					exceptionCode(ie);
				}catch(Exception e){
					BaseClass.testCaseStatus = false;
					e.printStackTrace(System.out);
					CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
					rm.captureScreenShot("8302-Exception", "fail");
				}
			}
			
			
		}catch(Exception e){
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8302-Exception", "fail");
		}finally{
			try{
				
				//Deleting newly created project or Audience			
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project");					
				}
				
				if(bAudienceCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, false);
					CustomReporter.log("Deleted the audience");					
				}
				
				//Logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
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
		
	public void exceptionCode(IDIOMException ie){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
		CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
		
		if(strMsgAndFileName.length==1){
			rm.captureScreenShot("8302-Exception", "fail");
		}else{
			rm.captureScreenShot("8302-"+strMsgAndFileName[1], "fail");	
		}		
	}

}
