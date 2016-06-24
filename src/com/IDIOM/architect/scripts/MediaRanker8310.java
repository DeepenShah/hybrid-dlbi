package com.IDIOM.architect.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Media Ranker_4.4 a,b (i),c,d,e,f_Verify Reorder behaviour and actions in Right Side Navigation  </p>
 *  <p> <b>Objective:</b> Verify Reorder behaviour and actions in Right Side Navigation of Media Ranker page </p>
 *  <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8310.aspx </p>
 *  <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 *
 */
public class MediaRanker8310 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyReorderingMetric(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
			
		String strProjectName="";
		String strAudienceName="";		
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;
		boolean bAudienceCreated = false;
		boolean bCompare = true;
		
		try{
			String strChannels[] = property.getProperty("mediaRankerItems").split(",");
			String[] strMetrics = property.getProperty("MetricplotValue").split(",");
			String dragNDropVal1[] = property.getProperty("dragNDrop18310").split(":");
			String dragNDropVal2[] = property.getProperty("dragNDrop28310").split(":");
			String dragNDropVal3[] = property.getProperty("dragNDrop38310").split(":");
			String[] strMetricsInNewOrder = property.getProperty("metricsAfterDND8310").split(",");
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
									
			String[] strMetricLabel= new String[strMetrics.length];
			int i=0;
			for(String str:strMetrics){
				System.out.println(str);
				strMetricLabel[i]=str.split(":")[0].toUpperCase();
				i++;
			}			
			
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				try{
					
					//Get value of X, Y and Z co-ordinate and other metrics					
					HashMap<String,String> strBeforeMetricsValue = new HashMap<String,String>();
					
					for(String strMetric:strMetricLabel)
						strBeforeMetricsValue.put(strMetric, arMediaRankerPage.func_GetIndividualMetricName(strMetric));			
					
					//Click on re-order pane.
					arMediaRankerPage.func_ClickOnElement("reorderbtn");
					Thread.sleep(2000);
					
					//Getting value from that pannel.
					HashMap<String,String> strMetricsValueFromPane = new HashMap<String,String>();
					
					for(String strMetric:strMetricLabel)
						strMetricsValueFromPane.put(strMetric, arMediaRankerPage.func_GetIndividualMetricNameFromReOrderPane(strMetric));
						
					//Comparing both the values
					for(String strMetric:strMetricLabel){
						if(!strBeforeMetricsValue.get(strMetric).equalsIgnoreCase(strMetricsValueFromPane.get(strMetric))){
							bCompare = false;
							System.out.println(strClassName + ": Value is not matching for " + strMetric + ". Metric plot value: " + strBeforeMetricsValue.get(strMetric) + " and Reorder Panel value: " + strMetricsValueFromPane.get(strMetric) );
						}
					}
					
					if(!bCompare)
						throw new IDIOMException("Failed to compare value for metrics. See console log for more details.###FailedToMatchMetricName");
										
					CustomReporter.log("Successfully compared metric name from Metric plot with reorder pane.");
											
					//Draging and droping metric. Source: 
					arMediaRankerPage.func_DragAndDropMetric(dragNDropVal1[0], dragNDropVal1[1]);
					rm.webElementSync("idiomspinningcomplete");
					
					arMediaRankerPage.func_DragAndDropMetric(dragNDropVal2[0], dragNDropVal2[1]);
					rm.webElementSync("idiomspinningcomplete");
					
					arMediaRankerPage.func_DragAndDropMetric(dragNDropVal3[0], dragNDropVal3[1]);
					rm.webElementSync("idiomspinningcomplete");
					
					//Now getting metrics value and comparing it.					
					HashMap<String,String> strMetricsValueAfterReorder = new HashMap<String,String>();
					
					for(String strMetric:strMetricLabel)
						strMetricsValueAfterReorder.put(strMetric, arMediaRankerPage.func_GetIndividualMetricName(strMetric));
					
					i=0;bCompare=true;
					for(String strMetric:strMetricLabel){
						if(!strMetricsValueAfterReorder.get(strMetric).equalsIgnoreCase(strMetricsInNewOrder[i])){
							bCompare = false;
							System.out.println(strClassName + ": Value is not matching for " + strMetric + ". Exp value: " + strMetricsInNewOrder[i] + " and Actual value: " + strMetricsValueAfterReorder.get(strMetric) );
						}
						i++;
					}
					
					if(!bCompare)
						exceptionCode(new IDIOMException("Reordering metrics failed. See console log for more details.###DragNDropFailed"));
					
					CustomReporter.log("Successfully verified metrics reorder after dragging and dropping");
										
					//Also checking X & Y co-ordinate name from graph.
					if(!strMetricsInNewOrder[0].equalsIgnoreCase(arMediaRankerPage.func_GetValue("xaxis")))
						exceptionCode(new IDIOMException("Failed to match X-axis value.Exp: " + strMetricsInNewOrder[0] +
								" and actual: " + arMediaRankerPage.func_GetValue("xaxis") +".##XaxisValueIsNotMatching"));
					
					CustomReporter.log("Successfully verified graph's X axis name");
					
					
					if(!strMetricsInNewOrder[1].equalsIgnoreCase(arMediaRankerPage.func_GetValue("yaxis")))
						exceptionCode(new IDIOMException("Failed to match Y-axis value.Exp: " + strMetricsInNewOrder[1] +
								" and actual: " + arMediaRankerPage.func_GetValue("yaxis") +".##YaxisValueIsNotMatching"));
					
					CustomReporter.log("Successfully verified graph's Y axis name");					
					
				}catch(Exception e){
					exceptionCode(e);
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
			rm.captureScreenShot("8310-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8310-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
