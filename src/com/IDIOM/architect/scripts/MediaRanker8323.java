package com.IDIOM.architect.scripts;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> MediaRanker_Weighted Ranker_reset  </p>
 * <p> <b>Objective:</b> Verify the Reset functionality in Weighted Ranker page </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8323.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * @author Deepen Shah
 * @since 24 May 2016
 *
 */
public class MediaRanker8323 extends BaseClass {	
	
	@Test
	void verifyResetOnWeightedRanker(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		String strDeletionDetails="";
		
		String strAudienceName="";
		boolean bAudienceCreated=false;		
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strMediaRankerPages = property.getProperty("mediaRankerItems");
			String strMetricPlotName[] = property.getProperty("MetricplotName").split(",");
			
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
				bAudienceCreated= true;
				System.out.println("Audience Name " + strAudienceName);
				strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, strAudienceName);
				System.out.println("Found details: " + strDeletionDetails);
			}
			
			
			//Step6: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(1000);
			
			//Step7,8,9&10: Navigate to Media Ranker Page
			String[] strChannels = strMediaRankerPages.split(",");
			ArchitectMediaRankerPage mediaRanker = new ArchitectMediaRankerPage(driver);

			boolean bStatus=true;
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);											
				if(!mediaRanker.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");			
				
				try{
					
					//Step11: Update weighted ranker and save changes.
					
					//Step12:Opening weighted ranker					
					rm.webElementSync(mediaRanker.weightedRankerBtn, "clickable");					
					mediaRanker.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);
					
					//Getting value before update.
					HashMap<String,String> strExpectedPercentage = mediaRanker.func_GetWeightedRankerMetricAndValue();
					
					//Step13: Update the percentage value					
					HashMap<String,String> strBeforeRankerPercentage = new HashMap<String,String>();					
					for(int j=0;j<strMetricPlotName.length;j++){
						if(j<2){
							mediaRanker.func_EnterWeightedRankerPercentage(strMetricPlotName[j], "20");
							strBeforeRankerPercentage.put(strMetricPlotName[j], "20");
						}else{
							mediaRanker.func_EnterWeightedRankerPercentage(strMetricPlotName[j], "10");
							strBeforeRankerPercentage.put(strMetricPlotName[j], "10");
						}
						
					}
					
					//Step14: Save the changes
					mediaRanker.func_ClickOnElement("rankersavechanges");
					rm.webElementSync("idiomspinningcomplete");
					
					//Step15: Open weighted ranker
					mediaRanker.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);
					
					//Step16: Verifying if saved properly
					HashMap<String,String> strAfterRankerPercentage = mediaRanker.func_GetWeightedRankerMetricAndValue();
										
					if(!strBeforeRankerPercentage.equals(strAfterRankerPercentage))
						throw new IDIOMException("Not able update value in weighted ranker. Expected " + 
					strBeforeRankerPercentage + " and found " + strAfterRankerPercentage + ".###FailedToUpdateRankerValue");
					
					CustomReporter.log("Successfully updated value in weighted ranker. " +strAfterRankerPercentage);
					
					//Step17: Click on Reset
					mediaRanker.func_ClickOnElement("resetdefault");
					Thread.sleep(2000);
					
					//Verifying values now
					HashMap<String,String> strActualPercentage = mediaRanker.func_GetWeightedRankerMetricAndValue();
					
					if(!strExpectedPercentage.equals(strActualPercentage))
						throw new IDIOMException("Reset is not working properly. Expected value " + strExpectedPercentage +
								" and found " + strActualPercentage +".###FailedToResetWeightedRanker");
						
					CustomReporter.log("Reset is working fine for " + strChannel);
					
				}catch(IDIOMException e){
					exceptionCode(e);
				}catch(Exception e){
					BaseClass.testCaseStatus = false;
					e.printStackTrace(System.out);
					CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
					rm.captureScreenShot("8323-Exception", "fail");
				}finally{				
					rm.webElementSync(pageHeader.clientLogo, "clickable");					
				}
					
			}			
			
			if(!bStatus)
				throw new IDIOMException("Test Case Failed.###FailedToVerify");
		}catch(IDIOMException ie){
			exceptionCode(ie);			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8323-Exception", "fail");
		}finally{
			try{
				
				//Deleting newly created project or Audience			
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project");					
				}
				
				if(bAudienceCreated){
					rm.deleteProjectOrAudience(strDeletionDetails, false);
					CustomReporter.log("Deleted the audience " + strAudienceName);	
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
			rm.captureScreenShot("8323-Exception", "fail");
		}else{
			rm.captureScreenShot("8323-"+strMsgAndFileName[1], "fail");	
		}		
	}	
	
}
