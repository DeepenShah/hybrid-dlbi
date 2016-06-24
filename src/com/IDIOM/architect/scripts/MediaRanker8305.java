package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> MediaRanker_Verify_IndividualMetricPlot_Components  </p>
 * <p> <b>Objective:</b> Verify the components in Individual metric plot section </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8305.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * 
 * @author Deepen Shah
 *
 */
public class MediaRanker8305 extends BaseClass{

	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyIndividualMetricPlot(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		String strDeletionDetails="";		
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strMediaRankerPages = property.getProperty("mediaRankerItems");			
			String strMetrics = property.getProperty("MetricplotValue");
			
			//****************Test step starts here************************************************
			//Step1: Launch url
			strMsg = "Launched Browser and Enter URL";
			CustomReporter.log(strMsg);
			System.out.println(strClassName + ": " + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step2: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(strClassName + ": " + strMsg);
			
			//Step3: Select client
			clientListPage = new ClientList_Page(driver);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);
			System.out.println(strClassName + ": " + strMsg);
			
			
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
			
			//Step6: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(1000);
			
			//Step7,8,9&10: Navigate to Media Ranker Page
			String[] strChannels = strMediaRankerPages.split(",");
			arMediaRankerPage = new ArchitectMediaRankerPage(driver);
			
			boolean bStatus = true;
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				try{
						
			
				   // Verifying metrics are exist or not.
					if(!arMediaRankerPage.func_VerifyElementExist("metricslabel")){
						strMsg = "Metric deosn't exist on architect page for chanel: " + strChannel ;
						CustomReporter.errorLog(strMsg);
						System.out.println(strMsg);
						bStatus = false;
					}else{
						strMsg ="Successfully checked metrics existence for channel: " + strChannel;
						CustomReporter.log(strMsg);
						System.out.println(strMsg);
					}
					
					//Verifying metrics bubble plot are exist or not
					if(!arMediaRankerPage.func_VerifyElementExist("metricbubbleplot")){
						strMsg = "Metric bubble plot deosn't exist on architect page for chanel: " + strChannel ;
						CustomReporter.errorLog(strMsg);
						System.out.println(strMsg);
						bStatus = false;
						
					}else{
						strMsg ="Successfully checked metrics bubble plot existence for channel: " + strChannel;
						CustomReporter.log(strMsg);
						System.out.println(strMsg);
					}
					
					//Verifying text for metric label and its name
					if(!arMediaRankerPage.func_VerifyText("verifymetricslabelandname", strMetrics)){
						strMsg = "Text is not matching for label or name of metric on architect page for chanel: " + strChannel ;
						CustomReporter.errorLog(strMsg);
						System.out.println(strMsg);
						bStatus = false;
						
					}else{
						strMsg ="Successfully verified text for label and name of metrics for channel: " + strChannel;
						CustomReporter.log(strMsg);
						System.out.println(strMsg);
					}
					
					if(!bStatus){
						bStatus = true;
						throw new IDIOMException("Not able to verify metrics.###FailedToVerifyIndividualMetric");
					}
					
				}catch(Exception e){
					exceptionCode(e);
				}
			}			
		}catch(Exception ie){
			exceptionCode(ie);
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
			
			}catch(Exception e){
				e.printStackTrace(System.out);
				CustomReporter.errorLog("Some exception is occured, " + e.getMessage() + ", while deleting newly created project '"+strProjectName +"'. Please check the log for more details");
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
			rm.captureScreenShot("8305-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8305-"+strMsgAndFileName[1], "fail");	
		}		
	}

}
