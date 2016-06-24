package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Media Ranker_3.1 Date Set Builder_Verify channel & it's default state  </p>
 * <p> <b>Objective:</b> Verify channel & it's default state </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8307.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * 
 * 
 * @author Deepen Shah
 *
 */
public class MediaRanker8307 extends BaseClass{
	
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyChannelAndItsState(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		String strProjectName="";
		String strMsg="";		
		boolean bProjectCreated = false;
		String strDeletionDetails="";
		boolean bAudienceCreated = false;
		String strAudienceName="";
		
		try{
			
			String strMediaRankerPages = property.getProperty("mediaRankerItems");	
			String[] strExpDigTitle = property.getProperty("DigitalCategories").split(",");
			String[] strExpTvTitle = property.getProperty("TVCategories").split(",");
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
			String[] strChannels = strMediaRankerPages.split(",");
			arMediaRankerPage = new ArchitectMediaRankerPage(driver);
						
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				boolean bStatus = true;
				try{
					//Verify dataset builder
					if(!rm.webElementSync(arMediaRankerPage.func_GetLocalWebElement("mediaRankerScatteredPlot"), "visibility")){
						bStatus=false;
						strMsg="Dataset builder is not present on the page" ;
						CustomReporter.errorLog(strMsg);
						System.out.println(strClassName + ": " +strMsg);
						rm.captureScreenShot("8307-ScatterPlotNotPresent", "fail");
					}else{
						strMsg="Verified existence of dataset builder" ;
						CustomReporter.log(strMsg);
						System.out.println(strClassName + ": " +strMsg);
					}
										
					//Verifying all the categories/sub-categories/items should be checked by default				
					String[] strTitle={};
					if(strChannel.toLowerCase().contains("digital")){
						strTitle = strExpDigTitle;
					}else{
						strTitle=strExpTvTitle;
					}
					for(int j =0;j<strTitle.length;j++){			
						
						if(!arMediaRankerPage.func_VerifyAllCategoryChecked(j)){
							bStatus=false;
							strMsg="All " + strTitle[j] + " are not marked checked for " + strChannel + " channel. See console log for more details."  ;
							CustomReporter.errorLog(strMsg);
							System.out.println(strClassName + ": " +strMsg);
							rm.captureScreenShot("8307-"+strTitle[j], "fail");
						}else{
							strMsg="Successfully verified that all the "+ strTitle[j] +" are marked for " + strChannel;
							CustomReporter.log(strMsg);
							System.out.println(strClassName + ": " +strMsg);
						}
						
						//Drill down to category(Daypart)
						if(j!=2){							
							if(j==0){
								arMediaRankerPage.func_ClickCategoryByNumber("category",0);
								rm.webElementSync(arMediaRankerPage.func_GetLocalWebElement("subCategory"), "visibility");
							}else if(j==1){
								arMediaRankerPage.func_ClickCategoryByNumber("subcategory",0);
								rm.webElementSync(arMediaRankerPage.func_GetLocalWebElement("itemCategory"), "visibility");
							}
						}						
					}
					
					if(!bStatus)
						throw new IDIOMException("Failed to verify state for " + strChannel +".###FaileToCheckStateOfChannel");
						
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
			rm.captureScreenShot("8307-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8307-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
