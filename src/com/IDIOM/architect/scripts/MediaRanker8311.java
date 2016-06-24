package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Media Ranker Overview_1_b_Check_Availability_Of_Primary_Features  </p>
 * <p> <b>Objective:</b> To verify the availability of primary features </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8311.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 *
 */
public class MediaRanker8311 extends BaseClass{
		
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyMajorFeatureOfMediaRanker(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		
		String strProjectName="";				
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;
		
		try{
			
			String strMediaRankerPages = property.getProperty("mediaRankerItems");	
			
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
			
			boolean bStatus = true;
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");				
				
				//Checking other feature for Digital as well as TV				
				
				//Verify Scattered Plot
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("scatteredplot", null)){
					bStatus = false;
					strMsg = "Not able to verify scattered plot for ." + strChannel;
					System.out.println(strClassName +": " + strMsg);
					CustomReporter.errorLog(strMsg);
				}else{
					strMsg = "Verified scattered plot for " + strChannel;
					System.out.println(strClassName +": " + strMsg);
					CustomReporter.log(strMsg);
				}
				
				//Verify right side navigation (Cateogry)
				if(!arMediaRankerPage.func_VerifyElementExist("categorylist")){
					bStatus = false;
					strMsg = "Not able to verify category list for further navigation on " + strChannel;
					System.out.println(strClassName +": " + strMsg);
					CustomReporter.errorLog(strMsg);
				}else{
					strMsg = "Verified category list for further navigation on " + strChannel;
					System.out.println(strClassName +": " + strMsg);
					CustomReporter.log(strMsg);
				}
				
				//Verify individual metric plot
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("metricplot", null)){
					bStatus = false;
					strMsg = "Not able to verify individual metric plot for " + strChannel;
					System.out.println(strClassName +": " + strMsg);
					CustomReporter.errorLog(strMsg);
				}else{
					strMsg = "Verified individual metric plot for " + strChannel;
					System.out.println(strClassName +": " + strMsg);
					CustomReporter.log(strMsg);
				}
				
				//Verify rank metric plot
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("rankplot", null)){
					bStatus = false;
					strMsg = "Not able to verify rank plot for " + strChannel;
					System.out.println(strClassName +": " + strMsg);
					CustomReporter.errorLog(strMsg);
				}else{
					strMsg = "Verified rank plot for " + strChannel;
					System.out.println(strClassName +": " + strMsg);
					CustomReporter.log(strMsg);
				}
				
				if(!bStatus)
					exceptionCode(new IDIOMException("Verification failed for " + strChannel +".###NotAbleToVerifySection"));
								
				//Click on rank
				arMediaRankerPage.func_ClickOnElement("weightedranker");
				Thread.sleep(2000);
				
				//Checking weighted ranker plot
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("weightedrankermetrics", null)){
					exceptionCode(new IDIOMException("Not able to verify weighted ranker plot.###WeightedRankerSectionMissing"));
				}else{
					strMsg = "Verified weighted ranker plot with 8 metrics on " + strChannel;
					System.out.println(strClassName +": " + strMsg);
					CustomReporter.log(strMsg);
				}
				
				//Click on rank to close panel
				arMediaRankerPage.func_ClickOnElement("weightedranker");
				Thread.sleep(2000);
				
				//Clicking on view top 10 property button
				arMediaRankerPage.func_ClickOnElement("viewscatteredplotprop");
				Thread.sleep(2000);
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("top10proplist")){
					exceptionCode(new IDIOMException("Not able to verify top 10 properties on scattered plot.###FailedToVerifyTop10Prop"));					
				}else{
					strMsg = "Verified top 10 properties on scattered plot on " + strChannel;
					System.out.println(strClassName +": " + strMsg);
					CustomReporter.log(strMsg);
				}
				
				bStatus = true;
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
			rm.captureScreenShot("8311-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8311-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
