package com.IDIOM.HelpAndFAQs;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.HelpAndFAQs;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *2307: Tab should activated/highlighted for selected tab (FAQ/HELP) </p>
 * <p> <b>Objective:</b> Verify that opened tab should be activated in FAQ and Help page </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8937.aspx </p>
 * <p> <b>Module:</b> HelpAndFAQs </p>
 * @author Rohan Macwan
 * @since 22 June 2016
 *
 */
public class HelpAndFAQs8937 extends BaseClass {

	ProjectDashboardPage pdp=null;
	
	HelpAndFAQs helpFAQs=null;
	
	String strProjectName="";
	String strClassName= getClass().getSimpleName();
	@Test
	public void	verifyHelpAndFAQsSwitching(){
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
			
			//Create / Select Project
			strProjectName = clientListPage.createNewProject("");
			
			bProjectCreate=true;
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			rm.webElementSync("pageload");
			
			CustomReporter.log("Launch Project");

			clientListPage.launchProject(strProjectName);
			
			pdp = new ProjectDashboardPage(driver);
			helpFAQs = new HelpAndFAQs(driver);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			CustomReporter.log("Project dashboard page is open");
			
			strText="";
			strText=property.getProperty("projectDashboard");
			//Step 3 - Now click on Help link from header
			//Step 4 - Now change the tab as FAQ from the same help page
			func_VerifyHelpAndFAQsSwithcing(strText);
			
			//#########Success Metrics############
			strText="";
			strText=property.getProperty("successMetrics");
			pageHeader.megaMenuLinksClick(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

			CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");
					
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			//Step 4 - Verify the click of Project Name
			func_VerifyHelpAndFAQsSwithcing(strText);
			
			//###############
			
			//Audience Definition
			strText="";
			strText=property.getProperty("audienceDefinition");
			pageHeader.megaMenuLinksClick(property.getProperty("audienceDefinition"));
			
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			CustomReporter.log("Navigated to audience definition page");
			
			func_VerifyHelpAndFAQsSwithcing(strText);
			
			//Profile
			strText="";
			strText=property.getProperty("profile");
			pageHeader.megaMenuLinksClick(property.getProperty("profile"));
		    CustomReporter.log("Profile page loaded successfully");

		    rm.webElementSync("idiomspinningcomplete");
		    rm.webElementSync("pageload");
		    Thread.sleep(2000);
			
		    func_VerifyHelpAndFAQsSwithcing(strText);
		    
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
		    
			func_VerifyHelpAndFAQsSwithcing(strText);
			
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
			func_VerifyHelpAndFAQsSwithcing(strText);
			
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
				throw new IDIOMException("Pathing page has not loaded successfully. There might be some issues present on it. ###PathingWheelLoadingIssue");
						
			rm.webElementSync(pageHeader.clientLogo,"visibility");
			CustomReporter.log("Pathing page has loaded successfully");
			
			func_VerifyHelpAndFAQsSwithcing(strText);
			
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
					   throw new IDIOMException("Failed to open channel###MediaRanker-missingScatterPlot");
				
				//Click on Project name or client logo in header 
				CustomReporter.log("Click on Client logo at the top to navigate back to Project Dashboard page");
				
				rm.webElementSync(pageHeader.clientLogo,"visibility");
				
				func_VerifyHelpAndFAQsSwithcing(strText);
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
	
	public void func_VerifyHelpAndFAQsSwithcing(String strPage) throws Exception
	{
		
		CustomReporter.log("---------------------------------------------------------");
		rm.webElementSync(helpFAQs.personMenu,"clickable");
		
		CustomReporter.log("Click on Menu");
		helpFAQs.personMenu.click();
		System.out.println("Clicked on person menu");
		Thread.sleep(1000);
		
		helpFAQs.performAction("helpcenter");
		rm.webElementSync(helpFAQs.ActiveTabContent,"visibility");
		
		CustomReporter.log("Check which Tab is by default selected");
		
		if(!helpFAQs.isVisible("faqsactivetab"))
			   throw new IDIOMException("FAQs Tab is not selected and highlighted by default ###FAQsTabNotHighlighted");
		
		CustomReporter.log("FAQs Tab is correctly selected and highlighted by default");
		
		CustomReporter.log("Click on Definitions Tab");
		
		helpFAQs.performAction("definitionstab");
		rm.webElementSync(helpFAQs.ActiveTabContent,"visibility");
		
		if(!helpFAQs.isVisible("definitionsactivetab"))
			   throw new IDIOMException("Definitions Tab is not selected and highlighted ###DefinitionsTabNotHighlighted");
		
		CustomReporter.log("Definitions Tab is correctly selected and highlighted");
		
	}
	
	
}
