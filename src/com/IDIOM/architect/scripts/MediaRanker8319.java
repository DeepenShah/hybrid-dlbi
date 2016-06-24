package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Media Ranker Overview_1.b.i.1Axis Labels_.b_Minimum_Maximum_Values_To_Be_Shown_On_X_Y_Axis </p>
 *  <p> <b>Objective:</b> Verify Minimum and Maximum Values displayed on X and Y Axis </p>
 *  <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8319.aspx </p>
 *  <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 * @since 01/02/2016
 *
 */
public class MediaRanker8319 extends BaseClass{
	
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyMinMaxForXYAxis(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		
		String strProjectName="";		
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;
		
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
				
				boolean bStatus = true;
				
				try{
					String strVal;
					//Verify minimum and maximum are visible for X and Y axis.
					if(arMediaRankerPage.func_VerifyVisibilityOfElement("xminmax")){
						strMsg="Min and Max values are visible for X-axis.";
						CustomReporter.log(strMsg);
						System.out.println(strMsg);
						
						strVal = arMediaRankerPage.func_GetValue("xmax");
						if(strVal.equalsIgnoreCase(" ") || strVal.equalsIgnoreCase("")){
							bStatus=false;
							strMsg="Max value is not displayed for X-axis. Found value: " + strVal;
							CustomReporter.errorLog(strMsg);
							System.out.println(strMsg);							
						}else{
							strMsg="Max value,"+ strVal +", is displayed for X-axis.";
							CustomReporter.log(strMsg);
							System.out.println(strMsg);
						}
						
						strVal = arMediaRankerPage.func_GetValue("xmin");
						if(strVal.equalsIgnoreCase(" ") || strVal.equalsIgnoreCase("")){
							bStatus=false;
							strMsg="Min value is not displayed for X-axis. Found value: " + strVal;
							CustomReporter.errorLog(strMsg);
							System.out.println(strMsg);							
						}else{
							strMsg="Min value,"+ strVal +", is displayed for X-axis.";
							CustomReporter.log(strMsg);
							System.out.println(strMsg);
						}
						
					}else{
						bStatus=false;
						strMsg="Min and Max values are  not visible for X-axis.";
						CustomReporter.errorLog(strMsg);
						System.out.println(strMsg);						
						
					}
					
					if(arMediaRankerPage.func_VerifyVisibilityOfElement("yminmax")){
						strMsg="Min and Max values are visible for Y-axis.";
						CustomReporter.log(strMsg);
						System.out.println(strMsg);
						
						
						strVal = arMediaRankerPage.func_GetValue("ymax");
						if(strVal.equalsIgnoreCase(" ") || strVal.equalsIgnoreCase("")){
							bStatus=false;
							strMsg="Max value is not displayed for Y-axis. Found value: " + strVal;
							CustomReporter.errorLog(strMsg);
							System.out.println(strMsg);							
						}else{
							strMsg="Max value,"+ strVal +", is displayed for Y-axis.";
							CustomReporter.log(strMsg);
							System.out.println(strMsg);
						}
						
						strVal = arMediaRankerPage.func_GetValue("ymin");
						if(strVal.equalsIgnoreCase(" ") || strVal.equalsIgnoreCase("")){
							bStatus=false;
							strMsg="Min value is not displayed for Y-axis. Found value: " + strVal;
							CustomReporter.errorLog(strMsg);
							System.out.println(strMsg);							
						}else{
							strMsg="Min value,"+ strVal +", is displayed for Y-axis.";
							CustomReporter.log(strMsg);
							System.out.println(strMsg);
						}
					}else{
						bStatus=false;
						strMsg="Min and Max values are not visible for Y-axis.";
						CustomReporter.errorLog(strMsg);
						System.out.println(strMsg);						
					}				
					
					if(!bStatus)
						exceptionCode(new IDIOMException("Some validations failed.###XYMinMaxFailed"));
				
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
			rm.captureScreenShot("8319-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8319-"+strMsgAndFileName[1], "fail");	
		}		
	}
	
}
