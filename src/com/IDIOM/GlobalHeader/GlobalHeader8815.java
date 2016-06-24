package com.IDIOM.GlobalHeader;

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
 * <p> <b>Test Case Name:</b> *Global Header - Verify IDIOM Logo click </p>
 * <p> <b>Objective:</b> Verify IDIOM Logo click </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8815.aspx </p>
 * <p> <b>Module:</b> Global Header </p>
 * @author Rohan Macwan
 * @since 16 June 2016
 *
 */
public class GlobalHeader8815 extends BaseClass {

	ProjectDashboardPage pdp=null;
	String strProjectName="";
	@Test
	public void	verifyIdiomLogoClickOnAllPages(){
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
			func_VerifyIdiomLogoClick(strText);
			
			//#########Success Metrics############
			strText="";
			strText=property.getProperty("successMetrics");
			pageHeader.megaMenuLinksClick(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

			CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");
					
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###8815_SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			func_VerifyIdiomLogoClick(strText);
			
			//###############
			
			//Audience Definition
			strText="";
			strText=property.getProperty("audienceDefinition");
			pageHeader.megaMenuLinksClick(property.getProperty("audienceDefinition"));
			
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			CustomReporter.log("Navigated to audience definition page");
			
			func_VerifyIdiomLogoClick(strText);
			
			//Profile
			strText="";
			strText=property.getProperty("profile");
			pageHeader.megaMenuLinksClick(property.getProperty("profile"));
		    CustomReporter.log("Profile page loaded successfully");

		    rm.webElementSync("idiomspinningcomplete");
		    rm.webElementSync("pageload");
		    Thread.sleep(2000);
			
		    func_VerifyIdiomLogoClick(strText);
		    
		    //Webnet
		    strText="";
			strText=property.getProperty("webnet");
		    CustomReporter.log("Navigate to Webnet page");
			pageHeader.megaMenuLinksClick(property.getProperty("webnet"));
			
			
			Analyse_Webnet_Page webNet = new Analyse_Webnet_Page(driver);
			
			webNet.isVisible("webnetchart");
						
			if(!rm.webElementSync(webNet.chart,"visibility"))
				throw new IDIOMException("Webnet Page has not loaded successfully. Please refer screen shot for more information ###8815_WebnetPageNotLoadedSuccessfully");
			
			rm.webElementSync(pageHeader.clientLogo,"visibility");
			
			CustomReporter.log("Webnet page has loaded successfully");
		    
			func_VerifyIdiomLogoClick(strText);
			
			//############## HVA ##################
			strText="";
			strText=property.getProperty("hva");
			CustomReporter.log("Navigate to HVA page");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));;
			
			
			HVA_Page hva = new HVA_Page(driver);
			
			hva.isVisible("slowdown");
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###8815_BehaviorsDropdownNotVisible");
							
			if(!rm.webElementSync(pageHeader.clientLogo,"visibility"))
				throw new IDIOMException("Client logo is not visible or there might be other issues on the page. ###8815_ClientLogo_Not_visible");

			CustomReporter.log("Verify Global Header");
			func_VerifyIdiomLogoClick(strText);
			
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
				throw new IDIOMException("Pathing page has not loaded successfully. There might be some issues present on it. ###8815_PathingWheelLoadingIssue");
						
			rm.webElementSync(pageHeader.clientLogo,"visibility");
			CustomReporter.log("Pathing page has loaded successfully");
			
			func_VerifyIdiomLogoClick(strText);
			
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
					   throw new IDIOMException("Failed to open channel###MediaRanker8815-missingScatterPlot");
				
				//Click on Project name or client logo in header 
				CustomReporter.log("Click on Client logo at the top to navigate back to Project Dashboard page");
				
				rm.webElementSync(pageHeader.clientLogo,"visibility");
				
				func_VerifyIdiomLogoClick(strText);
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
			rm.captureScreenShot("8815", "fail");
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
	
	public void func_VerifyIdiomLogoClick(String strPage) throws Exception
	{
				
		CustomReporter.log("Verify Idiom Logo click for page " + strPage);
		pageHeader.idiomLogo.click();
		Thread.sleep(5000);
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");	
		
		if (pageHeader.isVisible("idiomlogo"))
		{
			CustomReporter.log("Client Home Page has successfully loaded");
			CustomReporter.log("Launch Project");

			clientListPage.launchProject(strProjectName);
		
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			CustomReporter.log("Project dashboard page is open");
		}
		else
		{
			BaseClass.testCaseStatus = false;
			rm.captureScreenShot("IdiomLogoNotFoundFor" + strPage, "fail");
		}
		
	}
}
