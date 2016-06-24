package com.IDIOM.SuccessMetrics.scripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Common Elements - 1.c.i Navigation - Clicking back and forth freely between Success Metrics and Audience Definition and the currently viewed tab marking </p>
 * <p> <b>Objective:</b> Clicking back and forth freely between Success Metrics and Audience Definition and the currently viewed tab marking </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8566.aspx </p>
 * <p> <b>Module:</b> Audience Builder - Success Metrics</p>
 * @author Rohan Macwan
 * @since 11 May 2016
 *
 */
public class SuccessMetrics8566 extends BaseClass {
@Test
	
	public void	SuccessMetrics8566(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Launch browser and enter IDIOM Url
			//Step2: Enter valid username and password Click on 'login to idiom' button
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 3 In client home page, select a client from dropdown
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 4 - Click on new project button
			//Step 5 - Enter valid name and description for project and click on Save button
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			
			//step 6 - Click on launch project
			clientListPage.launchProject(strProjectName);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 7 - Click on destination link success metrics
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

			CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");
					
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###8566_SuccessMetricsTabNotAvailable1");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			CustomReporter.log("Click on Audience Definition Tab");
			
			audienceBuilder.performAction("gotoaudiencedefinition");
			
			CustomReporter.log("Check whether Audience Definition Tab is Active");
			
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Audience Definition")))))
				throw new IDIOMException("Audience Definition Tab seems to be not available or there may be some issues on the page. ###8566_AudiecneDefinitionTabNotAvailable1");
			
			CustomReporter.log("Audiecne Definition Tab is available and visible on the page and currently Active");
			
			CustomReporter.log("Click on Success Metrics Tab");
			audienceBuilder.performAction("gotosuccessmetric");
			
			CustomReporter.log("Check whether Success Metrics Tab is Active");
			
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Audience Definition Tab seems to be not available or there may be some issues on the page. ###8566_SuccessMetricsTabNotAvailable2");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			CustomReporter.log("Click on Audience Definition Tab");
			audienceBuilder.performAction("gotoaudiencedefinition");
			
			CustomReporter.log("Check whether Audience Definition Tab is Active");
			
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Audience Definition")))))
				throw new IDIOMException("Audience Definition Tab seems to be not available or there may be some issues on the page. ###8566_AudiecneDefinitionTabNotAvailable2");
			
			CustomReporter.log("Audiecne Definition Tab is available and visible on the page and currently Active");
			
			
			
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
			rm.captureScreenShot("8566", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");					
					Thread.sleep(3000);
					clientListPage.func_PerformAction("Close Project");
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");
				}
				
				//Step 8 - Click on logout
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
