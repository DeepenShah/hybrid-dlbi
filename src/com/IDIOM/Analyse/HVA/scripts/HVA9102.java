package com.IDIOM.Analyse.HVA.scripts;

import java.io.File;
import java.text.MessageFormat;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:</b> HVA_Verify Export data functionality </p>
<p>	<b>Objective:</b> Verify that export data functionality is downloading csv file and size should be greater than 5KB. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/9102.aspx </p>
<p>	<b>Module:</b> HVA </p>
@author: Shailesh Kava
@since: 20-May-2016
**********************************************************************/
public class HVA9102 extends BaseClass {
	
	HVA_Page hvaPage;
	String HVALink;
	String strDetails;
	String fileLocation;
	Actions action;
	String exportdatafilename;
	
	@Test
	public void HVA_verifyExportData(){
		
		HVALink = property.getProperty("hva");
		exportdatafilename = property.getProperty("hvaexportdatafile"); 
				
		String strMsg = null;
		String strProjectName="9102_"+rm.getCurrentDateTime();
		boolean bProjectCreate = false;
		
		hvaPage = new HVA_Page(driver);
		fileLocation = System.getProperty("user.dir");
		action = new Actions(driver);
		
		try{
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 3: Create/Select project and launch the same 
			CustomReporter.log("Creating new project as no project for this client");
			System.out.println("Creating new project as no project for this client");
			clientListPage.createNewProject(strProjectName);
			
			//Getting project and client id to delete through REST service
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			clientListPage.launchProject(strProjectName);
			bProjectCreate = true;
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, HVALink.trim()));
			Thread.sleep(2000);
			
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			CustomReporter.log("Navigating to HVA page");
			System.out.println("Navigating to HVA page");
			
			if(browserName.equalsIgnoreCase("ie")){
				System.out.println("clicking through action");
				pdp.navigateToByActionClass(HVALink.trim());
			}else{
				System.out.println("clicking through link");
				pdp.navigateTo(HVALink.trim());
			}
			
			if(!hvaPage.isVisible("hva_chart"))
				throw new IDIOMException("Failed to open HVA page###HVA9102-failureHVALoading");
			
			if(browserName.equalsIgnoreCase("ie")){
				System.out.println("IE browser");
				action.moveToElement(hvaPage.exportData_button).click().perform();
			}else{
				System.out.println("Not IE browser");
				hvaPage.exportData_button.click();
			}
			
			Thread.sleep(8000);
			
			//Calling method to perform download action
			rm.downloadFile();
			Thread.sleep(1000);
			
			String home = System.getProperty("user.home");
	        System.out.println("Dir: "+home);
	        File file = new File(home+"/Downloads/"+exportdatafilename);
	        
	        if(!file.exists()){
	        	rm.captureScreenShot("9102downloadFail_"+browserName, "fail");
	        	CustomReporter.errorLog("Failed to download file in browser ["+browserName+"]");
	        	System.out.println("Failed to download file in browser ["+browserName+"]");
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
			rm.captureScreenShot("9102", "fail");
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
}