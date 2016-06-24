package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>MediaRanker_Weighted Ranker_No_Save</p>
 *  <p> <b>Objective:</b> Verify that on clicking the icon next to "Rank" is opening the percentage builder and also collapse the weighted ranker without saving</p>
 *  <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8316.aspx </p>
 *  <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 * @since 29/01/2016
 *
 */
public class MediaRanker8316 extends BaseClass{
	
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyPercentageWithoutSavingAndClosingRanker(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		
		String strProjectName="";
		String strAudienceName="";		
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;
		boolean bAudienceCreated = false;		
		
		try{
			String strChannels[] = property.getProperty("mediaRankerItems").split(",");
			String strMetricPlotName[] = property.getProperty("MetricplotName").split(",");
			
			//****************Test step starts here************************************************
			//Login To Selecting Client
			loginToSelectClient();			
			
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
			
			//Step7,8,9&10: Navigate to Media Ranker Page
			arMediaRankerPage = new ArchitectMediaRankerPage(driver);
			
			//Checking behavior for both channel. Digital & TV
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				try{
					//Step7: click on weighted ranker icon
					arMediaRankerPage.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);
					
					//Getting values before changing it.
					String strBeforePerValue = arMediaRankerPage.func_GetValue("weightedrankermetricpercentagevalue");
					System.out.println("Before: " + strBeforePerValue);
					
					//Change the value				
					arMediaRankerPage.func_EnterWeightedRankerPercentage(strMetricPlotName[0], "20");
					arMediaRankerPage.func_EnterWeightedRankerPercentage(strMetricPlotName[1], "20");
					for(int j=2;j<strMetricPlotName.length;j++)
						arMediaRankerPage.func_EnterWeightedRankerPercentage(strMetricPlotName[j], "10");				
					
					//Click back to weighted ranker icon
					arMediaRankerPage.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);
					
					//Click again to check the value.
					arMediaRankerPage.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);
					
					String strAfterPerValue = arMediaRankerPage.func_GetValue("weightedrankermetricpercentagevalue");
					System.out.println("After: " + strAfterPerValue);
					
					//Comparing the value. It should not save the changes
					if(!strBeforePerValue.equalsIgnoreCase(strAfterPerValue))
						throw new IDIOMException("Clicking on weighted ranker icon saved the value. Before: " + strBeforePerValue +
								" and after: " + strAfterPerValue +".###WeightedRankerValueNotMatching");
					
					CustomReporter.log("Successfully verified that clicking weighted ranker icon does not save the value. Before: " + 
					strBeforePerValue +" and after: " + strAfterPerValue);
					
				}catch(Exception e){
					exceptionCode(e);
				}finally{
					//Closing weighted ranker
					arMediaRankerPage.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);
				}
			}
						
			
		}catch(Exception e){
			exceptionCode(e);
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
		
		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot("8316-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8316-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
