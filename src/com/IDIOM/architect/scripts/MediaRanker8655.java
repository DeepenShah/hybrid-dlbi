package com.IDIOM.architect.scripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Media Ranker_5 Top 10 Properties_Verify order on top 10 properties.</p>
 *  <p> <b>Objective:</b> To verify effect of order on top 10 properties. </p>
 *  <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8655.aspx </p>
 *  <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 * @since 01/02/2016
 *
 */
public class MediaRanker8655 extends BaseClass{
		
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyOrderEffectOnTop10Properties(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		
		String strProjectName="";
		String strAudienceName="";		
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;
		boolean bAudienceCreated = false;	
		
		try{
			String strChannels[] = property.getProperty("mediaRankerItems").split(",");
						
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
				
					//Step7: click on top 10 property icon.
					rm.webElementSync(arMediaRankerPage.viewTop10PropBtn, "clickable");
					arMediaRankerPage.func_ClickOnElement("viewscatteredplotprop");				
					Thread.sleep(2000);
					
					//Verifying current values are based on rank metric
					List<String> strTop10PropFromOverlay = arMediaRankerPage.func_GetTop10Properties();
					List<String> strCalculatedTop10Prop = arMediaRankerPage.func_GetTop10PropertiesOrderByMetric("rank", "category");
					
					if(rm.compareListWithOrder(strTop10PropFromOverlay, strCalculatedTop10Prop)){
						strMsg="Properties are correctly sorted on RANK for category";
						CustomReporter.log(strMsg);
						System.out.println(strMsg);
					}else{
						throw new IDIOMException("Properties are not sorted on RANK for category.Expected "+
					strTop10PropFromOverlay + " calculated " + strCalculatedTop10Prop +".###RankSortingFailed");					
					}
					
					//Step8:Change the order
					arMediaRankerPage.func_SelectValueFromDropdown("propertyorder", "index");
					Thread.sleep(3000);
					strMsg = "Change order by 'INDEX' from 'RANK'";
					CustomReporter.log(strMsg);
					System.out.println(strMsg);
					
					strTop10PropFromOverlay = arMediaRankerPage.func_GetTop10Properties();
					strCalculatedTop10Prop = arMediaRankerPage.func_GetTop10PropertiesOrderByMetric("index", "category");
					
					//Step9: Verifying change in prop order.
					if(rm.compareListWithOrder(strTop10PropFromOverlay, strCalculatedTop10Prop)){
						strMsg="Properties are correctly sorted on INDEX for category";
						CustomReporter.log(strMsg);
						System.out.println(strMsg);
					}else{
						throw new IDIOMException("Properties are not sorted on INDEX for category.Expected "+
								strTop10PropFromOverlay + " calculated " + strCalculatedTop10Prop +".###RankSortingFailed");
					}
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
			rm.captureScreenShot("8655-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8655-"+strMsgAndFileName[1], "fail");	
		}		
	}
	
	
	
}
