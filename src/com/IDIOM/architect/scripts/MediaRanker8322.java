package com.IDIOM.architect.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> MediaRanker_Weighted Ranker_Cancel  </p>
 *  <p> <b>Objective:</b> Verify the 'Cancel' functionality in Weighted Ranker page </p>
 *  <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8322.aspx </p>
 *  <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 * @since 04/02/2016
 *
 */
public class MediaRanker8322 extends BaseClass{
	
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyCancelOnWeightedRanker(){
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
					rm.webElementSync(arMediaRankerPage.weightedRankerBtn, "clickable");
					arMediaRankerPage.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);
					CustomReporter.log("Clicked on icon beside ranker metric");
					
					//Getting value before updating percentage
					HashMap<String,String> beforeRankerMetricValue = arMediaRankerPage.func_GetWeightedRankerMetricAndValue();
					CustomReporter.log(strClassName + ": " + "Ranker metric value Before:"+ beforeRankerMetricValue);
					
					//Clearing all value to get real time update.
					arMediaRankerPage.func_ClearAllRankerPercentage();
					CustomReporter.log("Cleared all values");
					
					//Step8: Change the value
					CustomReporter.log("Updating % value for various metrics");
					arMediaRankerPage.func_EnterWeightedRankerPercentage(strMetricPlotName[0], "20");
					arMediaRankerPage.dummyClick.click(); //Added this click for IE
					arMediaRankerPage.func_EnterWeightedRankerPercentage(strMetricPlotName[1], "20");
					arMediaRankerPage.dummyClick.click(); //Added this click for IE
					
					//Checking bar and total percentage are updated real time.				
					boolean bTempStatus = true;
					
					HashMap<String,String> afterRankerMetricValue = arMediaRankerPage.func_GetWeightedRankerMetricAndValue();
					if(afterRankerMetricValue.get(strMetricPlotName[0]).equalsIgnoreCase("20")){
						strMsg="Successfully verified real time update on grey graph for " + strMetricPlotName[0] +" metric.";
						CustomReporter.log(strMsg);
						System.out.println(strClassName + ": " + strMsg);
					}else{						
						strMsg="Grey graph is not updated in real time for " + strMetricPlotName[0] +" metric.";
						CustomReporter.errorLog(strMsg);
						System.out.println(strClassName + ": " + strMsg);
						bTempStatus = false;
					}
					
					if(afterRankerMetricValue.get(strMetricPlotName[1]).equalsIgnoreCase("20")){
						strMsg="Successfully verified real time update on grey graph for " + strMetricPlotName[1] +" metric.";
						CustomReporter.log(strMsg);
						System.out.println(strClassName + ": " + strMsg);
					}else{						
						strMsg="Grey graph is not updated in real time for " + strMetricPlotName[1] +" metric.";
						CustomReporter.errorLog(strMsg);
						System.out.println(strClassName + ": " + strMsg);
						bTempStatus = false;
					}
					
					if(arMediaRankerPage.func_GetValue("weightedrankertotalvaluetext").equalsIgnoreCase("40%")){
						strMsg="Successfully verified real time update on total % as 40. ";
						CustomReporter.log(strMsg);
						System.out.println(strClassName + ": " + strMsg);
					}else{						
						strMsg="Total % is not updated in real time. Found: " + arMediaRankerPage.func_GetValue("weightedrankertotalvaluetext") + " Expected: 40%"  ;
						CustomReporter.errorLog(strMsg);
						System.out.println(strClassName + ": " + strMsg);
						bTempStatus = false;
					}
					
					if(!bTempStatus)
						exceptionCode(new IDIOMException("Failed real time update on weighted ranker.###RealTimeUpdateFailed"));				
					
					for(int j=2;j<strMetricPlotName.length;j++)
						arMediaRankerPage.func_EnterWeightedRankerPercentage(strMetricPlotName[j], "10");				
					
					CustomReporter.log("Filled all metric values");
					
					//Step9: Click 'Cancel'
					arMediaRankerPage.func_ClickOnElement("rankercancel");
					CustomReporter.log("Clicked Cancel");
					
					//Stpe10: Click again to check the value.
					arMediaRankerPage.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);
					CustomReporter.log("Opened weighted ranker ");
					
					afterRankerMetricValue = arMediaRankerPage.func_GetWeightedRankerMetricAndValue();
					System.out.println(strClassName + ": " + "Ranker metric value after:"+ afterRankerMetricValue);
					
					//Step11: Comparing the value. Changes should be saved successfully.
					bTempStatus = true;
					for(int j=0;j<strMetricPlotName.length;j++){
						
						if(afterRankerMetricValue.get(strMetricPlotName[j]).equalsIgnoreCase(beforeRankerMetricValue.get(strMetricPlotName[j]))){
							strMsg="Successfully verified value is not updated for "+ strMetricPlotName[j] +".";
							CustomReporter.log(strMsg);
							System.out.println(strClassName + ": " + strMsg);
						}else{							
							bTempStatus = false;
							strMsg="Failed to verify value for '"+ strMetricPlotName[j] +"'. Expected: " + beforeRankerMetricValue.get(strMetricPlotName[j]) +" and found: " + afterRankerMetricValue.get(strMetricPlotName[j]);
							CustomReporter.errorLog(strMsg);
							System.out.println(strClassName + ": " + strMsg);
						}				
					}
					
					if(!bTempStatus)
						throw new IDIOMException("Refer screenshot 'MR8322-ValuesAreUpdated' for above failures.###FailedToCancelUpdate");
					
					CustomReporter.log("Successfully verified cancellation updated");
				}catch(Exception e){
					exceptionCode(e);
				}finally{
					//Closing weighted ranker
					arMediaRankerPage.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);
					CustomReporter.log("Closed ranker plot");
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
			rm.captureScreenShot("8322-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8322-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
