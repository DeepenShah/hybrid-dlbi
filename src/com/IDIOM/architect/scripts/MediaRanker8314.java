package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/**<p> <b>Test Case Name:</b> Media Ranker Overview_1.b.i.1Axis Labels_.a_Verify_X_Y_Axis</p>
 * <p> <b>Objective:</b> Verify X and Y Axis </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8314.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 * @since 22/01/2016
 *
 */
public class MediaRanker8314 extends BaseClass{
	
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyXYAxisLabel(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		
		String strProjectName="";				
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;	
		
		try{
			String strChannels[] = property.getProperty("mediaRankerItems").split(",");
			String strXaxis = property.getProperty("X-axis");
			String strYaxis = property.getProperty("Y-axis");
			
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
								
				//Verifying X,Y co-oridnate label.
				//X-Axis label
				String strAppXaxis = arMediaRankerPage.func_GetValue("xaxis");
				String strAppYaxis = arMediaRankerPage.func_GetValue("yaxis");
				
				if(strAppXaxis.equalsIgnoreCase(strXaxis)){
					if(strAppYaxis.equalsIgnoreCase(strAppYaxis)){
						strMsg = "Successfully verified X: " + strAppXaxis + " and Y: " + strAppYaxis + " value.";
						CustomReporter.log(strMsg);
						System.out.println(strClassName + ": " + strMsg);
					}else{
						exceptionCode(new IDIOMException("Failed to verify Y-Axis value.Expected Y: "+ strYaxis+"and actual Y: " + strAppYaxis + ".###FailedToVerifyAxisLabel"));						
					}
				}else{
					exceptionCode(new IDIOMException("Failed to verify X-Axis value.Expected X: "+ strXaxis+"and actual X: " + strAppXaxis +".###FailedToVerifyAxisLabel"));
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
			rm.captureScreenShot("8314-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8314-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
