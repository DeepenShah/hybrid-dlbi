/********************************************************************
Test Case Name: *HVA_2.A.ii & 2.A.iii_Verify all behaviors filter
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/7999.aspx
Objective:Verify the correct  behavior filter listing when all behaviors are selected from the audience page.
Module: Analyse>HVA
Created By: Amrutha Nair
Date: 22 September 2015
**********************************************************************/

package com.IDIOM.Analyse.HVA.scripts;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;

public class HVA7999 extends BaseClass {
	
	@Test
	public void test_HVA7999() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		AudienceBuilderPage abPage=null;
		
		try
		{
			//****************Test step starts here************************************************
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
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
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
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###7999_SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			//Step 8 - Verify after success metrics are added
			rm.webElementSync(audienceBuilder.projectedTab,"visibility");
			rm.webElementSync(audienceBuilder.percentagePopulationValue,"visibility");
			
			audienceBuilder.selectAllSuccessMetrics();
			
			List<String> strAllMetricCards = audienceBuilder.getNameOfAllMetricCards();
			
			
			//Step9: Click on Audience Definition link from project dashboard
			CustomReporter.log("Go to Audience Definition page");
			audienceBuilder.performAction("gotoaudiencedefinition");
			
			Thread.sleep(3000);
			
			abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			CustomReporter.log("Navigated to audience definition page");
							
			//Step 10 - Select all attributes -- it means no need to build any of the queries
			
			rm.webElementSync(abPage.projectedTab,"visibility");
			rm.webElementSync(abPage.percentagePopulationValue,"visibility");
			
			//Step 11 - Navigate to HVA page
			CustomReporter.log("Navigate to HVA page");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));
			
			
			HVA_Page ah = new HVA_Page(driver);
			
			ah.isVisible("slowdown");
			
			if(!rm.webElementSync(ah.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###7999_BehaviorsDropdownNotVisible");

			CustomReporter.log("HVA page has successfully loaded");			
			
			//Step 12 - Verify Behavior Filter is present
			if(ah.func_ElementExist("BehaviourFilter")){
				CustomReporter.log("Behavior Filter is present	");
				
			String strSelectedBehavior = ah.func_getText("Behaviour");
			
			List<String> strBehaviorsOnHVA = ah.func_CaptureList("Behaviour");
				
			//Step 13 Click  on Behavior Filter & verify the values displayed in behavior filter drop down
				ah.behaviour.click();
				//ah.func_ClickElement("Behaviour");
				CustomReporter.log("The user has clicked on behaviour");
				
				
				boolean bStatus=true;
				for (String key : strAllMetricCards) 
				{
					if(strBehaviorsOnHVA.contains(key.trim()))
					{
						CustomReporter.log("Behavior - '" + key.trim() + "' in Success Metrics is found in HVA Behavior dropdown" );
					}else
					{
						CustomReporter.errorLog("Behavior - '" + key.trim() + "' in Success Metrics is not found in HVA Behavior dropdown");
						bStatus=false;
					}
				}
				
				if (bStatus==false)
				{
					throw new IDIOMException("Some of the behaviors present on Success Metrics are not found on HAV behavior dropdown. ###8928_BehaviorsNotfoundInDropdown");
				}
				
		
				//Step 14 - Check the default value of Behaviour drop-down.
				
				bStatus=false;
				for (String key : strAllMetricCards) 
				{
					System.out.println("strSelectedBehavior.equals(key.trim() - " + strSelectedBehavior.equals(key.trim()));
					if(strSelectedBehavior.equals(key.trim()))
					{
						CustomReporter.log("Behavior - '" + key.trim() + "' in Success Metrics is by default selected in Behavior dropdown on HVA page" );
						bStatus=true;
					}
				}
				
				if (bStatus==false)
				{
					throw new IDIOMException("Behavior dropdown is not defaulted to the expected behavior. ###7999_DefaultValueInBehaviorDropdown");
				}
				
			}
		}
		//Step 15 - Check the Behavior drop down UI (It can't be automated)
		catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("7999", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step 15 - Click on logout
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