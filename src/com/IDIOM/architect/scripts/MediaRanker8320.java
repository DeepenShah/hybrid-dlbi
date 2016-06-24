package com.IDIOM.architect.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> MediaRanker_Weighted Ranker_SaveChanges  </p>
 *  <p> <b>Objective:</b> Verify the 'Save Changes' functionality in Weighted Ranker page </p>
 *  <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8320.aspx </p>
 *  <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 * @since 02/02/2016
 *
 */
public class MediaRanker8320 extends BaseClass{
	
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyWeightedRankerValuesAfterSave(){
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

			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				try{		
				
					//click on weighted ranker icon
					arMediaRankerPage.func_ClickOnElement("weightedranker");					
					Thread.sleep(2000);
					
					CustomReporter.log("Open weighted ranker");
									
					//Step8: Change the value
					HashMap<String,String> strBeforeVal = new HashMap<String,String>();					
					for(int j=0;j<strMetricPlotName.length;j++){
						if(j<2){
							arMediaRankerPage.func_EnterWeightedRankerPercentage(strMetricPlotName[j], "20");
							strBeforeVal.put(strMetricPlotName[j], "20");
						}else{
							arMediaRankerPage.func_EnterWeightedRankerPercentage(strMetricPlotName[j], "10");
							strBeforeVal.put(strMetricPlotName[j], "10");
						}
					}										
					
					CustomReporter.log("Entered new values in weighted ranker");
					
					//Click 'Save Changes'
					arMediaRankerPage.func_ClickOnElement("rankersavechanges");
					rm.webElementSync("idiomspinningcomplete");
					
					CustomReporter.log("Clicked 'Save Changes'");
					
					//Stpe10: Click again to check the value.
					rm.webElementSync(arMediaRankerPage.weightedRankerBtn, "clickable");
					/*arMediaRankerPage.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);*/
					arMediaRankerPage.openCloseWeightedRanker(true);
					
					CustomReporter.log("Open weighted ranker");
					
					HashMap<String,String> strAfterPerValue = arMediaRankerPage.func_GetWeightedRankerMetricAndValue();
					System.out.println("Before: " + strAfterPerValue);
					
					CustomReporter.log("Fetched the value from weighted ranker");
					
					//Comparing the value. Changes should be saved successfully.
					if(!strBeforeVal.equals(strAfterPerValue))
						throw new IDIOMException("Not able to verify saved value. Expected " + strBeforeVal +
								" and actual " + strAfterPerValue +".###FailedToSaveValue");
					
					CustomReporter.log("Successfully saved value and verified in weighted ranker.");
					/*for(int j=0;j<strMetricPlotName.length;j++){
						String strTempValue="";
						if(j<2){
							strTempValue="20";
						}else{
							strTempValue="10";
						}
						
						if(strAfterPerValue.get(strMetricPlotName[j]).equalsIgnoreCase(strTempValue)){
							strMsg="Successfully verified updated value:"+ strTempValue +" for "+ strMetricPlotName[j] +".";
							CustomReporter.log(strMsg);
							System.out.println(strClassName + ": " + strMsg);
						}else{
							exceptionCode(new IDIOMException("Failed to verify value for '"+ strMetricPlotName[j] +
									"'. Expected: " + strTempValue +" and found: " + strAfterPerValue.get(strMetricPlotName[j]) +
											".###NotAbleToSaveWeightedRankerValues"));
							BaseClass.testCaseStatus=false;
							bTempStatus = false;
							strMsg="Failed to verify value for '"+ strMetricPlotName[j] +"'. Expected: " + strTempValue +" and found: " + strAfterPerValue.get(strMetricPlotName[j]);
							CustomReporter.errorLog(strMsg);
							System.out.println(strClassName + ": " + strMsg);
						}					
					}*/			
					
					
					
				}catch(Exception e){
					exceptionCode(e);
				}finally{
					
					//Closing weighted ranker
					arMediaRankerPage.openCloseWeightedRanker(false);					
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
			rm.captureScreenShot("8320-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8320-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
