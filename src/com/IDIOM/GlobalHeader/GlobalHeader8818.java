package com.IDIOM.GlobalHeader;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Global Header - Verify Click of Menu Sandwich Icon </p>
 * <p> <b>Objective:</b>Verify Click of Menu Sandwich Icon </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8818.aspx </p>
 * <p> <b>Module:</b> Global Header </p>
 * @author Rohan Macwan
 * @since 17 June 2016
 *
 */
public class GlobalHeader8818 extends BaseClass {

	ProjectDashboardPage pdp=null;
	String strProjectName="";
	@Test
	public void	verifyExpandingAndCollapsingOfMenuIconInAllPages(){
		String strMsg = null;		
		boolean bProjectCreate = false;
		
		String strDetails ="";
		String strText="";
		
				
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
			
			CustomReporter.log("Launch Project");

			clientListPage.launchProject(strProjectName);
			
			pdp = new ProjectDashboardPage(driver);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			CustomReporter.log("Project dashboard page is open");
			
			//Step 4 - Click on IDIOM Logo
			func_VerifyMenuCollapsingExpanding(strText);
			
			//#########Success Metrics############
			strText="";
			strText=property.getProperty("successMetrics");
			pageHeader.megaMenuLinksClick(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

			CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");
					
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###8818_SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			func_VerifyMenuCollapsingExpanding(strText);
			
			//###############
			
			//Audience Definition
			strText="";
			strText=property.getProperty("audienceDefinition");
			pageHeader.megaMenuLinksClick(property.getProperty("audienceDefinition"));
						
			rm.webElementSync(audienceBuilder.addNewGroupLink,"visibility");
			CustomReporter.log("Navigated to audience definition page");
			
			func_VerifyMenuCollapsingExpanding(strText);
			
			//Profile
			strText="";
			strText=property.getProperty("profile");
			pageHeader.megaMenuLinksClick(property.getProperty("profile"));
		    CustomReporter.log("Profile page loaded successfully");

		    rm.webElementSync("idiomspinningcomplete");
		    rm.webElementSync("pageload");
		    Thread.sleep(2000);
			
		    func_VerifyMenuCollapsingExpanding(strText);
		    
		    //Webnet
		    strText="";
			strText=property.getProperty("webnet");
		    CustomReporter.log("Navigate to Webnet page");
			pageHeader.megaMenuLinksClick(property.getProperty("webnet"));
			
			
			Analyse_Webnet_Page webNet = new Analyse_Webnet_Page(driver);
			
			webNet.isVisible("webnetchart");
						
			if(!rm.webElementSync(webNet.chart,"visibility"))
				throw new IDIOMException("Webnet Page has not loaded successfully. Please refer screen shot for more information ###8818_WebnetPageNotLoadedSuccessfully");
			
			rm.webElementSync(pageHeader.clientLogo,"visibility");
			
			CustomReporter.log("Webnet page has loaded successfully");
		    
			func_VerifyMenuCollapsingExpanding(strText);
			
			//############## HVA ##################
			strText="";
			strText=property.getProperty("hva");
			CustomReporter.log("Navigate to HVA page");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));;
			
			
			HVA_Page hva = new HVA_Page(driver);
			
			hva.isVisible("slowdown");
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###8818_BehaviorsDropdownNotVisible");
							
			if(!rm.webElementSync(pageHeader.clientLogo,"visibility"))
				throw new IDIOMException("Client logo is not visible or there might be other issues on the page. ###8818_ClientLogo_Not_visible");

			CustomReporter.log("Verify Global Header");
			func_VerifyMenuCollapsingExpanding(strText);
			
			//Pathing
			strText="";
			strText=property.getProperty("pathing");
			pageHeader.megaMenuLinksClick(property.getProperty("pathing"));
			try{
				rm.webElementSync("idiomspinningcomplete");
			}catch(Exception e){
				System.out.println("spinning bar not found");
			}
			
			Analyse_Pathing_Page paThing = new Analyse_Pathing_Page(driver);
			
			if(!rm.webElementSync(paThing.pathingWheel,"visibility"))
				throw new IDIOMException("Pathing page has not loaded successfully. There might be some issues present on it. ###8818_PathingWheelLoadingIssue");
						
			rm.webElementSync(pageHeader.clientLogo,"visibility");
			CustomReporter.log("Pathing page has loaded successfully");
			
			func_VerifyMenuCollapsingExpanding(strText);
			
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
					   throw new IDIOMException("Failed to open channel###MediaRanker8818-missingScatterPlot");
				
				//Click on Project name or client logo in header 
				CustomReporter.log("Click on Client logo at the top to navigate back to Project Dashboard page");
				
				rm.webElementSync(pageHeader.clientLogo,"visibility");
				
				func_VerifyMenuCollapsingExpanding(strText);
			}
			
						
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog(strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8818", "fail");
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
	
	public void func_VerifyMenuCollapsingExpanding(String strPage) throws Exception
	{
				
		CustomReporter.log("Verify Megamenu click for its Open State for page " + strPage);
		pageHeader.megaMenuIcon.click();
		System.out.println("Clicked on mega menu");
		Thread.sleep(1000);
		
		if(pageHeader.isVisible("megamenuopenfast")){
			CustomReporter.log("Mega menu is successfully opened");
		}else
		{
			CustomReporter.errorLog("Failed to open mega menu");
			BaseClass.testCaseStatus = false;
			rm.captureScreenShot("MegaMenuCouldNotBeOpenedForPage_" + strPage, "fail");
		}
		
		pageHeader.megaMenuIcon.click();
		
		System.out.println("Again clicked on Megamenu icon to check its collapsing");
		Thread.sleep(1000);
		
		if(pageHeader.isVisible("megamenuopenfast")){
			CustomReporter.errorLog("Collapsing has not worked and Menu is still open");
			BaseClass.testCaseStatus = false;
			rm.captureScreenShot("MegaMenuIsStillOpenForPage_" + strPage, "fail");
		}
		else
		{
			CustomReporter.log("Megamenu is successfully collapsed");
			
		}
		
	}
}
