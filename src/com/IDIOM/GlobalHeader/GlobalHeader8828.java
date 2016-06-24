package com.IDIOM.GlobalHeader;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Global Header - Changing Audiences - Verify what dropdown in Header shows </p>
 * <p> <b>Objective:</b>Global Header - Verify what dropdown in Header shows </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8828.aspx </p>
 * <p> <b>Module:</b> Global Header </p>
 * @author Rohan Macwan
 * @since 20 June 2016
 *
 */

public class GlobalHeader8828 extends BaseClass {

	ProjectDashboardPage pdp=null;
	String strProjectName="";
	String strText="";
	String strClassName= getClass().getSimpleName();
	String strAudienceName = "";
	
	@Test
	public void	verifySelectedAudienceInHeader(){
		String strMsg = null;		
		boolean bProjectCreate = false;
		
		String strDetails ="";
		
		String strMediaRankerItems=property.getProperty("mediaRankerItems");
		try{
						
			//****************Test step starts here************************************************
			
			//loginToSelectClient
			//Step1: Open appropriate environment URL. and login with valid credentials
			//Step 2 - Select a client
			loginToSelectClient();
			
			//Step 3 - Create / Select Project
			strProjectName = clientListPage.createNewProject("");
			
			bProjectCreate=true;
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			rm.webElementSync("pageload");
			
			clientListPage.performActionOnProject("edit", strProjectName);
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###AudienceTabIssue");
			
			strAudienceName= clientListPage.createNewAudience("");
			
			clientListPage.func_PerformAction("Close Project");
			
			
			CustomReporter.log("Launch Project");

			clientListPage.launchProject(strProjectName);
			
			pdp = new ProjectDashboardPage(driver);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			CustomReporter.log("Project dashboard page is open");
			
			//Step 4 - Verify the presence of Project Name and placement of Client Logo and Project Name
			CustomReporter.log("Verify whether selected Audience in Audience dropdown is " + strAudienceName);
						
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName))
				throw new IDIOMException("Default Audience does not match with the Audience '" + strAudienceName + "' which was created and selected. ###BehaviorsDropdownNotVisibleProjectDashboard");
			
			CustomReporter.log("Audience '" + strAudienceName + "' created on Client Home page is correctly selected in Header Dropdown on Project Dashboard Page");
			
			//#########Success Metrics############
			strText="";
			strText=property.getProperty("successMetrics");
			pageHeader.megaMenuLinksClick(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

			CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");

			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page.###SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			CustomReporter.log("Verify whether selected Audience in Audience dropdown is " + strAudienceName);
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName))
				throw new IDIOMException("Default Audience does not match with the Audience '" + strAudienceName + "' which was created and selected. ###BehaviorsDropdownNotVisibleSuccessMetrics");
			
			CustomReporter.log("Audience '" + strAudienceName + "' created on Client Home page is correctly selected in Header Dropdown on Success Metrics Page");
			
			//###############
			
			//Audience Definition
			strText="";
			strText=property.getProperty("audienceDefinition");
			pageHeader.megaMenuLinksClick(property.getProperty("audienceDefinition"));
						
			rm.webElementSync(audienceBuilder.addNewGroupLink,"visibility");
			CustomReporter.log("Navigated to audience definition page");
			
			CustomReporter.log("Verify whether selected Audience in Audience dropdown is " + strAudienceName);
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName))
				throw new IDIOMException("Default Audience does not match with the Audience '" + strAudienceName + "' which was created and selected. ###BehaviorsDropdownNotVisibleAudienceDefinition");
			
			CustomReporter.log("Audience '" + strAudienceName + "' created on Client Home page is correctly selected in Header Dropdown on Audience Definition Page");
			
			//Profile
			strText="";
			strText=property.getProperty("profile");
			pageHeader.megaMenuLinksClick(property.getProperty("profile"));
		    CustomReporter.log("Profile page loaded successfully");

		    rm.webElementSync("idiomspinningcomplete");
		    rm.webElementSync("pageload");
		    Thread.sleep(2000);
			
		    CustomReporter.log("Verify whether selected Audience in Audience dropdown is " + strAudienceName);
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName))
				throw new IDIOMException("Default Audience does not match with the Audience '" + strAudienceName + "' which was created and selected. ###BehaviorsDropdownNotVisibleProfile");
			
			CustomReporter.log("Audience '" + strAudienceName + "' created on Client Home page is correctly selected in Header Dropdown on Profile Page");
		    
		    //Webnet
		    strText="";
			strText=property.getProperty("webnet");
		    CustomReporter.log("Navigate to Webnet page");
			pageHeader.megaMenuLinksClick(property.getProperty("webnet"));
			
			
			Analyse_Webnet_Page webNet = new Analyse_Webnet_Page(driver);
			
			webNet.isVisible("webnetchart");
						
			if(!rm.webElementSync(webNet.chart,"visibility"))
				throw new IDIOMException("Webnet Page has not loaded successfully. Please refer screen shot for more information ###WebnetPageNotLoadedSuccessfully");
			
			rm.webElementSync(pageHeader.clientLogo,"visibility");
			
			CustomReporter.log("Webnet page has loaded successfully");
		    
			CustomReporter.log("Verify whether selected Audience in Audience dropdown is " + strAudienceName);
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName))
				throw new IDIOMException("Default Audience does not match with the Audience '" + strAudienceName + "' which was created and selected. ###BehaviorsDropdownNotVisibleWebnet");
			
			CustomReporter.log("Audience '" + strAudienceName + "' created on Client Home page is correctly selected in Header Dropdown on Webnet Page");
			
			//############## HVA ##################
			strText="";
			strText=property.getProperty("hva");
			CustomReporter.log("Navigate to HVA page");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));;
			
			
			HVA_Page hva = new HVA_Page(driver);
			
			hva.isVisible("slowdown");
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###BehaviorsDropdownNotVisible");
							
			if(!rm.webElementSync(pageHeader.clientLogo,"visibility"))
				throw new IDIOMException("Client logo is not visible or there might be other issues on the page. ###ClientLogo_Not_visible");

			CustomReporter.log("Verify Global Header");
			CustomReporter.log("Verify whether selected Audience in Audience dropdown is " + strAudienceName);
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName))
				throw new IDIOMException("Default Audience does not match with the Audience '" + strAudienceName + "' which was created and selected. ###BehaviorsDropdownNotVisibleHVA");
			
			CustomReporter.log("Audience '" + strAudienceName + "' created on Client Home page is correctly selected in Header Dropdown on HVA Page");
			
			//Pathing
			strText="";
			strText=property.getProperty("pathing");
			pageHeader.megaMenuLinksClick(property.getProperty("pathing"));
			
			rm.webElementSync("idiomspinningcomplete");		
			
			Analyse_Pathing_Page paThing = new Analyse_Pathing_Page(driver);
			
			if(!rm.webElementSync(paThing.pathingWheel,"visibility"))
				throw new IDIOMException("Pathing page has not loaded successfully. There might be some issues present on it. ###PathingWheelLoadingIssue");
						
			rm.webElementSync(pageHeader.clientLogo,"visibility");
			CustomReporter.log("Pathing page has loaded successfully");
			
			CustomReporter.log("Verify whether selected Audience in Audience dropdown is " + strAudienceName);
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName))
				throw new IDIOMException("Default Audience does not match with the Audience '" + strAudienceName + "' which was created and selected. ###BehaviorsDropdownNotVisiblePathing");
			
			CustomReporter.log("Audience '" + strAudienceName + "' created on Client Home page is correctly selected in Header Dropdown on Pathing Page");
			
			//Media Ranker
			
			ArchitectMediaRankerPage AM= new ArchitectMediaRankerPage(driver);
			String [] strMediaRanker =pageHeader.getSplittedItems(strMediaRankerItems);
			for (int i=0;i<strMediaRanker.length;i++){
				strText="";
				strText=strMediaRanker[i];
				System.out.println(strMediaRanker[i]);
				Thread.sleep(3000);
				
				//Click on mega menu and click on digital ranker/tv ranker link				
				CustomReporter.log("Click on channel - '" + strMediaRanker[i] +"' from Mega Menu");
				pageHeader.megaMenuLinksClick(strMediaRanker[i]);
				
				rm.webElementSync("idiomspinningcomplete");
								
				if(!AM.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MediaRankermissingScatterPlot");
								
				rm.webElementSync(pageHeader.clientLogo,"visibility");		
				
				CustomReporter.log("Verify whether selected Audience in Audience dropdown is " + strAudienceName);
				
				if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName))
					throw new IDIOMException("Default Audience does not match with the Audience '" + strAudienceName + "' which was created and selected. ###BehaviorsDropdownNotVisible" + strMediaRanker[i]);
				
				CustomReporter.log("Audience '" + strAudienceName + "' created on Client Home page is correctly selected in Header Dropdown on " + strMediaRanker[i] + " page");
			}
			
						
		}catch(IDIOMException ie){			
			exceptionCode(ie, strClassName);
		}catch(Exception e){
			exceptionCode(e, strClassName);
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project" + strProjectName);
					
				}
				
				//Step 5 - Click on logout
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
