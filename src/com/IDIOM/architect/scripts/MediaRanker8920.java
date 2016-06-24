package com.IDIOM.architect.scripts;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:</b> Media Ranker_Verify Export Functionality[DTASIDIOM-2420] </p>
<p>	<b>Objective:</b> Verify the export functionality in media ranker page and size should be greater than 5KB. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8920.aspx </p>
<p>	<b>Module:</b> Media Ranker </p>
@author: Shailesh Kava
@since: 20-May-2016
**********************************************************************/
public class MediaRanker8920 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder;
	ArchitectMediaRankerPage mediaRanker;
	String strDigitalRanker;
	String strTVRanker;
	String arrMediaRankerChannels;
	String strDetails;
	List<String> selectedDates;
	String fileLocation;
	Actions action;
	String exportdatafilename;
	
	@Test
	public void mediaRanker_verifyExportData(){
		
		strDigitalRanker = property.getProperty("digitalRanker");
		strTVRanker = property.getProperty("tvRanker");
		arrMediaRankerChannels = property.getProperty("mediaRankerItems");
		exportdatafilename = property.getProperty("exportdatafilename"); 
				
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		audienceBuilder = new AudienceBuilderPage(driver);
		mediaRanker = new ArchitectMediaRankerPage(driver);
		fileLocation = System.getProperty("user.dir");
		
		action = new Actions(driver);
		
		try{
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 3: Create/Select project and launch the same 
			if(totalProjects == 0){
				
				CustomReporter.log("Creating new project as no project for this client");
				System.out.println("Creating new project as no project for this client");
				strProjectName = clientListPage.createNewProject("");
				
				//Getting project and client id to delete through REST service
				strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
				
				clientListPage.func_PerformAction("Launch Project");
				bProjectCreate = true;
			}else{
				CustomReporter.log("Selecting existing project");
				System.out.println("Selecting existing project");
				clientListPage.clickProjectById(1);
			}
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, strDigitalRanker.trim()));
			Thread.sleep(2000);
			
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			String[] channels = arrMediaRankerChannels.split(",");
			for(int i=0; i<channels.length; i++){
				CustomReporter.log("Navigating to ["+channels[i]+"]");
				System.out.println("Navigating to ["+channels[i]+"]");
				
				if(browserName.equalsIgnoreCase("ie") && i>=1){
					System.out.println("clicking through action");
					pdp.navigateToByActionClass(channels[i].trim());
				}else{
					System.out.println("clicking through link");
					pdp.navigateTo(channels[i].trim());
				}
				
				performAction(channels[i].trim());
				
				System.out.println("Clicking on IDIOM Logo");
				action.moveToElement(pageHeader.clientLogo).click().perform();
				System.out.println("Visibility tested-Client Logo");
				rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, channels[i].trim()));
				System.out.println("Visibility tested-End Client logo");
				Thread.sleep(2000);
			}
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8920", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the project");
				}
				
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		//****************Test step ends here************************************************
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	}
	
	public void performAction(String channel) throws Exception{
		
		if(channel.contains("TV Ranker"))
			Thread.sleep(20000);
		
		//Thread.sleep(10000);
		System.out.println(browserName);
		if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
			throw new IDIOMException("Failed to open media ranker###MediaRanker8500-missingScatterPlot");
		
		CustomReporter.log("Clicking on export data button on page ["+channel+"]");
		System.out.println("Clicking on export data button on page ["+channel+"]");
		
		rm.webElementSync(mediaRanker.exportData_button, "clickable");
		
		if(browserName.equalsIgnoreCase("ie") && channel.contains("TV Ranker")){
			System.out.println("IE browser in TV Channel");
			if(rm.webElementSync(mediaRanker.exportData_button, "clickable")){
				System.out.println("Export data is clickable");
				action.moveToElement(mediaRanker.exportData_button).click().perform();
				System.out.println("Clicked on exportdata, waiting for 15 sec.");
				Thread.sleep(15000);
				System.out.println("Waiting completed");
				//mediaRanker.exportData_button.click();
			}
			//mediaRanker.exportData_button.click();
			/*action.click(mediaRanker.exportData_button).perform();
			action.click(mediaRanker.exportData_button).perform();*/
			
		}else if(browserName.equalsIgnoreCase("ie") && channel.contains("Digital Ranker")){
			action.moveToElement(mediaRanker.exportData_button).click().perform();
		}else{
			System.out.println("Not IE browser");
			mediaRanker.exportData_button.click();
		}
		
		Thread.sleep(10000);
		
		//Calling method to perform download action
		rm.downloadFile();
		Thread.sleep(2000);
		
		String home = System.getProperty("user.home");
        System.out.println("Dir: "+home);
        File file = new File(home+"/Downloads/"+exportdatafilename);
        
        if(!file.exists()){
        	rm.captureScreenShot("8920downloadFail_"+browserName, "fail");
        	CustomReporter.errorLog("Failed to download file in browser ["+browserName+"] for ["+channel+"]");
        	System.out.println("Failed to download file in browser ["+browserName+"] for ["+channel+"]");
        	BaseClass.testCaseStatus = false;
        }else{
        	
        	CustomReporter.log("File is successfully download in Browser ["+browserName+"]");
        	System.out.println("File is successfully download in Browser ["+browserName+"]");
        	System.out.println("Checking file size");
        	Long filesize = file.length()/1024;
        	
        	System.out.println("File size is ["+filesize+"]");
        	if(filesize <= 10){
        		CustomReporter.errorLog("File size is not expected ["+filesize+" bytes], expected size is 10000 bytes");
        		System.out.println("File size is not expected ["+filesize+" bytes], expected size is 10000 bytes");
        		BaseClass.testCaseStatus = false;
        	}else{
        		CustomReporter.log("File is not emplty and size is ["+filesize+" bytes]");
        	}
        	System.out.println("Deleting file");
            file.delete();
            if(!file.exists())
            	CustomReporter.log("File is deleted successfully from download directory");
        }
     }
}