package com.IDIOM.architect.scripts;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
 * <p> <b>Test Case Name:</b> MediaRanker_Saving_refreshing_Logout and Login  </p>
 * <p> <b>Objective:</b> Verify that on refreshing the page or logging out and logging in, the  rank weights, section in data set builder, and order of metrics to what was most previously selected are getting saved </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8327.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * @author Deepen Shah
 * @since 20 May 2016
 *
 */
public class MediaRanker8327 extends BaseClass {

	String strCatName[] = {"",""};	
	HashMap<String,String> strAfterRankerPercentage;
	String strMetricSource[];
	String strMetricTarget[];
	
	@Test
	void verifyRankerStateAfterRefreshAndLogoutLogin(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		String strDeletionDetails="";
		
		String strAudienceName="";
		boolean bAudienceCreated=false;
		
		WebDriver newDriver=null;
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strMediaRankerPages = property.getProperty("mediaRankerItems");
			String strMetricPlotName[] = property.getProperty("MetricplotName").split(",");
			strMetricSource= property.getProperty("metricValueSource8327").split(":");
			strMetricTarget= property.getProperty("metricValueTarget8327").split(":");
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
				pdp.navigateTo(strChannel);							
				if(!mediaRanker.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");			
				
				try{
					
					
					//Step11: Update data in dataset
					
					strCatName[0] = mediaRanker.selectedVisibleCategories.get(0).findElement(By.xpath(mediaRanker.strCategoryName)).getText().trim();
					System.out.println("Found name1: " + strCatName[0]); 
					
					//Deselecting this category					
					rm.webElementSync(mediaRanker.selectedVisibleCategories.get(0), "clickable");
					mediaRanker.selectedVisibleCategories.get(0).findElement(By.xpath(mediaRanker.strCategoryToggleBtn)).click();
					Thread.sleep(2000);
					
					//Repeating For Other Category
					strCatName[1] = mediaRanker.selectedVisibleCategories.get(1).findElement(By.xpath(mediaRanker.strCategoryName)).getText().trim();
					System.out.println("Found name2: " + strCatName[1]); 
					
					rm.webElementSync(mediaRanker.selectedVisibleCategories.get(1), "clickable");
					mediaRanker.selectedVisibleCategories.get(0).findElement(By.xpath(mediaRanker.strCategoryToggleBtn)).click();
					Thread.sleep(2000);									
					
					if(mediaRanker.isCategorySelected(strCatName[0]) && mediaRanker.isCategorySelected(strCatName[1]))
						throw new IDIOMException("Not able to deselect the categories "+ Arrays.toString(strCatName) +".###FailedToUnCheckCat");
					
					CustomReporter.log("Successfully unchecked categories,"+Arrays.toString(strCatName) +", for " + strChannel);					
					
					
					//Step12: Update weighted ranker and save changes.
					
					//Opening weighted ranker
					
					rm.webElementSync(mediaRanker.weightedRankerBtn, "clickable");
					
					mediaRanker.openCloseWeightedRanker(true);
					Thread.sleep(2000);
					
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
					
					//Save the changes
					mediaRanker.func_ClickOnElement("rankersavechanges");
					rm.webElementSync("idiomspinningcomplete");
					
					//Verifying if saved properly
					mediaRanker.openCloseWeightedRanker(true);
					Thread.sleep(2000);
					
					strAfterRankerPercentage = mediaRanker.func_GetWeightedRankerMetricAndValue();
										
					if(!strBeforeRankerPercentage.equals(strAfterRankerPercentage))
						throw new IDIOMException("Not able enter value in weighted ranker. Expected " + 
					strBeforeRankerPercentage + " and found " + strAfterRankerPercentage + ".###FailedToUpdateRankerValue");
					
					CustomReporter.log("Successfully entere value in weighted ranker. " +strAfterRankerPercentage);
					
					//Closing Ranker
					mediaRanker.openCloseWeightedRanker(false);
					Thread.sleep(2000);
					
					//Step13: Update the metric order
					
					//Click on re-order pane.
					mediaRanker.func_ClickOnElement("reorderbtn");
					Thread.sleep(2000);
					
					//Dragging and dropping metric. Source: 
					mediaRanker.func_DragAndDropMetric(strMetricSource[1],strMetricTarget[0]);
					rm.webElementSync("idiomspinningcomplete");
					
					//Getting value after dragging and dropping
					String strMetricAfterSource = mediaRanker.func_GetIndividualMetricNameFromReOrderPane(strMetricSource[0]);
					String strMetricAfterTarget = mediaRanker.func_GetIndividualMetricNameFromReOrderPane(strMetricTarget[0]);
					
					if(!(strMetricAfterSource.equalsIgnoreCase(strMetricTarget[1])) && !(strMetricAfterTarget.equalsIgnoreCase(strMetricSource[1])))
						throw new IDIOMException("Not able to drag and drop properly. Source value found " +
					strMetricAfterSource + " and expected " + strMetricTarget[1] + " and Target value found " + strMetricAfterTarget + 
					" and expected " + strMetricSource[1] + ".###FailedToDragAndDrop");
					
					CustomReporter.log("Dragged and dropped value appropriately. " + strMetricSource[1] + " replaced with " + strMetricTarget[1]);
					
					//Closing Reorder panel
					mediaRanker.func_ClickOnElement("togglecheckbox");
					
					//Step14: Refresh the page and verify all details
					driver.navigate().refresh();
					rm.webElementSync(mediaRanker.datepicker, "visibility");
					rm.webElementSync(mediaRanker.datepicker, "clickable");
					
					bStatus = verifyDetails(strChannel, "Refresh", mediaRanker);
					
					//Step15: Launch new browser in new session
					newDriver = launchBrowser(browserName);
					
					strMsg = "Launched Browser and Enter URL in new session";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					Login_Page newln = new Login_Page(newDriver);
					
					//Login with valid credential
					Thread.sleep(3000);
					newln.func_LoginToIDIOM(strEmailId, strPassword);		    
				    strMsg = "Logged in successfully in new session";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					
					//Select client
					ClientList_Page clPage = new ClientList_Page(newDriver);
					
					Thread.sleep(5000);
					rm.webElementSync("pageload");
					rm.webElementSync("jqueryload");
					
					clPage.selectClient(strClientName);
					strMsg = "Selected '"+strClientName+"' client successfully in new session.";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					
					
					Thread.sleep(5000);
					rm.webElementSync("pageload");
					rm.webElementSync("jqueryload");
					
					//Launch project
					clPage.launchProject(strProjectName);
					strMsg = "Launched '"+strProjectName+"' project successfully in new session.";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					
					//Wait for project dashboard page
					ProjectDashboardPage newPDP = new ProjectDashboardPage(newDriver);
					rm.webElementSync(newPDP.projectName, "visibility");
					Thread.sleep(1000);
					strMsg = "Navigated to project dashboard page successfully in new session.";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					
					
					//Navigate to Media Ranker
					ArchitectMediaRankerPage arMRPage = new ArchitectMediaRankerPage(newDriver);
					newPDP.navigateTo(strChannel);
					
					if(!arMRPage.func_VerifyVisibilityOfElement("slowtvranker"))
						   throw new IDIOMException("Failed to open channel###MissingScatterPlotInNewSession");
					
					CustomReporter.log("Navigated to "+ strChannel +" page in new session");			
					
					bStatus = verifyDetails(strChannel, "Logout-Login", arMRPage);
					
					
				}catch(IDIOMException e){
					exceptionCode(e);
				}catch(Exception e){
					BaseClass.testCaseStatus = false;
					e.printStackTrace(System.out);
					CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
					rm.captureScreenShot("8327-Exception", "fail");
				}finally{
					if(newDriver !=null)
						newDriver.quit();
					
					CustomReporter.log("Navigating back to project dashboard");
					rm.webElementSync(pageHeader.clientLogo, "clickable");
					
					//Navigating back to project dashboard page.
					pageHeader.performAction("clientlogo");
					rm.webElementSync("visibiltiybyxpath",MessageFormat.format(pdp.strLinkContains, strChannel));
					Thread.sleep(2000);					
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
			rm.captureScreenShot("8327-Exception", "fail");
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
			rm.captureScreenShot("8327-Exception", "fail");
		}else{
			rm.captureScreenShot("8327-"+strMsgAndFileName[1], "fail");	
		}		
	}
	
	
	public boolean verifyDetails(String strChannel,String strEvent,ArchitectMediaRankerPage mediaRanker) throws Exception{
		boolean bRet = true;
		
		//1: Checking category selection status		
		for(String strCat:strCatName)
			if(mediaRanker.isCategorySelected(strCat)){
				bRet = false;
				CustomReporter.errorLog("Failed to verify "+ strCat+" category selection status for " + strChannel + ", found selected. After " + strEvent);
			}else{
				CustomReporter.log("Successfully verify categories selection state. Category=" + strCat +
						" channel=" + strChannel + " and after " + strEvent);
			}
				
		
		//2: Verifying raner plot value.
		mediaRanker.openCloseWeightedRanker(true);
		Thread.sleep(2000);
		
		HashMap<String, String> strRankerActualPercentage = mediaRanker.func_GetWeightedRankerMetricAndValue();
		
		if(!strRankerActualPercentage.equals(strAfterRankerPercentage)){
			bRet = false;
			CustomReporter.errorLog("Percenatage for ranker is not matching. Expected "+ strAfterRankerPercentage + " and found " +
			strRankerActualPercentage + " chanel " + strChannel + " after " + strEvent);
			
		}else{
			CustomReporter.log("Successfull tested weighted ranker percentage. Found " +strRankerActualPercentage +
					" chanel " + strChannel + " after " + strEvent);
		}
		
		mediaRanker.openCloseWeightedRanker(false);
		Thread.sleep(2000);
		
		//3: Verifying drag and drop value
		
		mediaRanker.func_ClickOnElement("reorderbtn");
		Thread.sleep(2000);
		
		String strMetricVal1 = mediaRanker.func_GetIndividualMetricNameFromReOrderPane(strMetricSource[0]);
		String strMetricVal2 = mediaRanker.func_GetIndividualMetricNameFromReOrderPane(strMetricTarget[0]);
		
		if(!strMetricSource[1].equalsIgnoreCase(strMetricVal2) && !strMetricTarget[1].equalsIgnoreCase(strMetricVal1)){
			bRet = false;
			CustomReporter.errorLog("Not able to drag and drop properly. Source value found " +
				strMetricVal1 + " and expected " + strMetricTarget[1] + " and Target value found " + strMetricVal2 + 
					" and expected " + strMetricSource[1] );
			
			
		}else{
			CustomReporter.log("Successfully verified dragged and dropped values. Source value " + strMetricSource[1] + 
					" is successfully swapped with " + strMetricTarget[1]);
			
		}
		
		mediaRanker.func_ClickOnElement("togglecheckbox");
		Thread.sleep(2000);
		
		return bRet;
	}
}
