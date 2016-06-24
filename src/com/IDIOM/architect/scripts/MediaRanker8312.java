package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> MediaRanker_Weighted Ranker_Verify Components </p>
 * <p> <b>Objective:</b> Verify the components in weighter ranker section </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8312.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * This test case will verify visibility of weighted ranker's components.<br/>
 * 	1. Name <br/>
 *	2. Grey graph in rank calculator <br/>
 *	3. Percentage value <br/>
 *	4. Total percentage which should be 100% always <br/>
 *	5. Cancel link <br/>
 *	6. Save changes link <br/>
 *  
 * @author Deepen Shah
 *
 */
public class MediaRanker8312 extends BaseClass{
	
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyWeightedRankerComponents(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		
		String strProjectName="";				
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;
			
		
		try{
			
			String strMediaRankerPages = property.getProperty("mediaRankerItems");	
			String strMetricplotName = property.getProperty("MetricplotName");
			String strWeightedMetricBarClass = property.getProperty("WeightedRankerMetricBarClass");
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
			String[] strChannels = strMediaRankerPages.split(",");
			arMediaRankerPage = new ArchitectMediaRankerPage(driver);
			
			//Checking other feature for Digital as well as TV			
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				
				boolean bStatus=true;		
				try{
					//Click on rank
					rm.webElementSync(arMediaRankerPage.weightedRankerBtn, "clickable");
					arMediaRankerPage.func_ClickOnElement("weightedranker");
					Thread.sleep(2000);
					
					//Checking weighted ranker plot
					if(!arMediaRankerPage.func_VerifyVisibilityOfElement("weightedrankermetrics")){
						bStatus = false;
						strMsg = "Not able to verify weighted ranker plot with 8 metrics on " + strChannel;
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.errorLog(strMsg);
					}else{
						strMsg = "Verified weighted ranker plot with 8 metrics on " + strChannel;
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.log(strMsg);
					}							
					
					//Comparing name of metric in weighted ranker plot				
					if(!arMediaRankerPage.func_VerifyText("weightedrankermetricname", strMetricplotName)){
						bStatus = false;
						strMsg = "Not able to verify metric name inside weighted ranker area";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.errorLog(strMsg);
					}else{
						strMsg = "Successfully verified metric name inside weighted ranker area";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.log(strMsg);
					}
					
					//Verifying % in metric value
					if(!arMediaRankerPage.func_VerifyText("weightedrankerpercentage", "")){
						bStatus = false;
						strMsg = "Not able to verify % inside weighted ranker area";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.errorLog(strMsg);
					}else{
						strMsg = "Successfully verified % inside weighted ranker area";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.log(strMsg);
					}
					
					//Verifying div class for grey bar for weighted ranker metric
					if(!arMediaRankerPage.func_VerifyText("weightedrankermetricbarclass", strWeightedMetricBarClass)){
						bStatus = false;
						strMsg = "Not able to verify grey bar inside weighted ranker area";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.errorLog(strMsg);
					}else{
						strMsg = "Successfully verified grey bar inside weighted ranker area";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.log(strMsg);
					}
					
					//Verifying total value text.
					if(!arMediaRankerPage.func_GetValue("weightedrankertotalvaluetext").equalsIgnoreCase("100%")){
						bStatus = false;
						strMsg = "Not able to verify total value. Exp 100% and found " + arMediaRankerPage.func_GetValue("weightedrankertotalvaluetext");
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.errorLog(strMsg);
					}else{
						strMsg = "Successfully verified 100% as total value";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.log(strMsg);
					}
					
					//Verify existence of 'Save Changes' and 'Cancel' button
					if(!arMediaRankerPage.func_VerifyVisibilityOfElement("weightedrankercancelbtn", null)){
						bStatus = false;
						strMsg = "Cancel button on weighted ranker is not visible";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.errorLog(strMsg);
					}else{
						strMsg = "Successfully verified visibility of cancel link weighted ranker";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.log(strMsg);
					}
					
					if(!arMediaRankerPage.func_VerifyVisibilityOfElement("weightedrankersavechangesbtn", null)){
						bStatus = false;
						strMsg = "Save changes is not visible on weighted ranker";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.errorLog(strMsg);
					}else{
						strMsg = "Successfully verify visibility of 'Save Changes' link";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.log(strMsg);
					}
					
					if(!bStatus)
						throw new IDIOMException("Verification failed.Check other messages.###VerificationFailed");
					
				}catch(Exception e){
					exceptionCode(e);
				}finally{
				
					//Click on rank to close panel
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
			rm.captureScreenShot("8312-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8312-"+strMsgAndFileName[1], "fail");	
		}		
	}
	
}
