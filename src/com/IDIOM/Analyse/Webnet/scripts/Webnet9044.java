package com.IDIOM.Analyse.Webnet.scripts;
import common.BaseClass;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *WebNet_Verify When Prject Logo/Label is clicked from top middle side of the page </p>
 * <p> <b>Objective:</b> Verify When Prject Logo/Label is clicked from top middle side of the page </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/9044.aspx </p>
 * <p> <b>Module:</b> HVA </p>
 * @author Rohan Macwan
 * @since 31 May 2016
 *
 */
public class Webnet9044 extends BaseClass{

	@Test
	
	public void	VerifyMiddleLogoClick(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
					
			String strClientName=property.getProperty("clientName");
			
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Launch browser and enter IDIOM Url. Enter valid username and password Click on 'login to idiom' button
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 2- Select any client from client drop down
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 3 - Click on new project button Enter valid project name and description and click on save button 
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			rm.webElementSync("pageload");
			
			//Now click on launch project
			CustomReporter.log("Launch Project");
			clientListPage.launchProject(strProjectName);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
								
			//Step 4 - Navigate to Webnet page
			CustomReporter.log("Navigate to Webnet page");
			pageHeader.megaMenuLinksClick(property.getProperty("webnet"));
			
			
			Analyse_Webnet_Page webNet = new Analyse_Webnet_Page(driver);
			
			webNet.isVisible("webnetchart");
						
			if(!rm.webElementSync(webNet.chart,"visibility"))
				throw new IDIOMException("Webnet Page has not loaded successfully. Please refer screen shot for more information ###9044_WebnetPageNotLoadedSuccessfully");
			
			CustomReporter.log("Webnet page has loaded successfully");
							
			if(!rm.webElementSync(pageHeader.clientLogo,"visibility"))
				throw new IDIOMException("Client logo is not visible or there might be other issues on the page. ###9044_ClientLogo_Not_visible");
			
			//Step 5 - Click on Project Logo/Label from top middle side of the page
			CustomReporter.log("Click on Logo in the middle");
			pageHeader.clientLogo.click();
			
			CustomReporter.log("Check whether Project Dashboard page has loaded successfully");
			
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, property.get("TV Ranker")));
						
			if (!(rm.webElementSync(pdp.projectName, "visibility")))
				throw new IDIOMException("Project Dashboard page has not loaded or there might be other issues. ###9044_ProjectDashboardNotLoaded");
			
			CustomReporter.log("Project Dashboard has loaded successfully");
			
						
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("" + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("9044", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step 6 - Click on logout
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
