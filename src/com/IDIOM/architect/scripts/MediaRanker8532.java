package com.IDIOM.architect.scripts;

import java.text.MessageFormat;
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
 * <p> <b>Test Case Name:</b> 1041_MediaRanker_IndividualMetricPlot_Rank Row  </p>
 * <p> <b>Objective:</b> Verify the rank row contains all bubbles , not just one bubble</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8532.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * @author Deepen Shah
 * @since 20 May 2016
 *
 */
public class MediaRanker8532 extends BaseClass {

	@Test
	void verifyMetricPlotBubbleForEachCategory(){
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
			String strDigitalCategories = property.getProperty("DigitalCategories");	
			String strTVCategories = property.getProperty("TVCategories");	
			String[] strMetricPlotNames = property.getProperty("MetricplotValue").split(",");
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
			
			//Step6: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			
			//Step7,8,9&10: Navigate to Media Ranker Page
			String[] strChannels = strMediaRankerPages.split(",");
			ArchitectMediaRankerPage mediaRanker = new ArchitectMediaRankerPage(driver);

			boolean bStatus=true;
			for(String strChannel:strChannels){
				pdp.navigateTo(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				
				//Waiting if element is clickable
				rm.webElementSync(mediaRanker.viewTop10PropBtn, "clickable");
				
				//Looping through level
				String[] strLevels={};
				if(strChannel.toLowerCase().contains("digital")){
					strLevels = strDigitalCategories.split(",");
				}else{
					strLevels = strTVCategories.split(",");
				}
				
				
				try{
					
					for(int i=0;i<strLevels.length;i++){
					
						//Bubble count in scattered plot
						int iScatterPlotBubble = mediaRanker.func_GetCount("circlesinplot");
						
						//Step11: Checking metric plot bubbles
						for(String strMetric:strMetricPlotNames){
							strMetric = strMetric.split(":")[1];
							int iBubbleCount = mediaRanker.getMetricPlotBubbleCount(strMetric);
							if(iBubbleCount!=iScatterPlotBubble){
								CustomReporter.errorLog("Bubble count is not matching for " + strMetric + 
										" Expected " + iScatterPlotBubble + " and found " + iBubbleCount +
										" category=" + strLevels[i] + " and channel= " + strChannel);
								bStatus=false;
							}								
						}
						
						CustomReporter.log("Bubble count verified for all metric category=" + strLevels[i] + " and channel= " + strChannel);
											
						//Drill down to category
						if(i!=2){							
							if(i==0){
								mediaRanker.func_ClickCategoryByNumber(0);
								rm.webElementSync(mediaRanker.subCategory, "visibility");
							}else if(i==1){
								mediaRanker.func_ClickCategoryByNumber("subcategory",0);
								rm.webElementSync(mediaRanker.itemCategory, "visibility");
							}
						}
					}
					
					if(!bStatus)
						BaseClass.testCaseStatus=false;
					
				}catch(IDIOMException e){
					exceptionCode(e);
				}finally{	
					
					rm.webElementSync(pageHeader.clientLogo, "clickable");
					
					//Navigating back to project dashboard page.
					pageHeader.performAction("clientlogo");
					rm.webElementSync("visibiltiybyxpath",MessageFormat.format(pdp.strLinkContains, strChannel));
					Thread.sleep(2000);
				}
					
			}			
			
		}catch(IDIOMException ie){
			exceptionCode(ie);			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8532-Exception", "fail");
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
			rm.captureScreenShot("8532-Exception", "fail");
		}else{
			rm.captureScreenShot("8532-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
