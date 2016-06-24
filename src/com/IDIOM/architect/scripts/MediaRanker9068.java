package com.IDIOM.architect.scripts;

import java.util.ArrayList;
import java.util.List;

import common.BaseClass;
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
<p>	<b>Test Case Name:</b> *Media Ranker_Verify_Navigation_from Project Name and Client Logo from header </p>
<p>	<b>Objective:</b> To verify optimal audience arrow points to less optimal when population is less than one and success metrics value is zero </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/9068.aspx </p>
<p>	<b>Module:</b> Media Ranker </p>
@author: Rohan Macwan
@since: 18-May-2016
**********************************************************************/
public class MediaRanker9068 extends BaseClass{

	@Test
	
	public void	MediaRanker9068(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		AudienceBuilderPage abPage=null;
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			
			String strAudienceAttributes1=property.getProperty("commonAudienceAttribute4");
			String strMediaRankerItems=property.getProperty("mediaRankerItems");
			
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
			
			//Step 7 - Click on Audience Definition link from project dashboard
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

			CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");
					
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###9068_SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			//Step 8 - Select few attributes and add them 
			rm.webElementSync(audienceBuilder.successMetricsOraudienceDefinitionArrowLink,"visibility");
			audienceBuilder.arrowLinkClickForSuccessMetricsOrAudienceDefinition();
				
			rm.webElementSync(audienceBuilder.allMetricsLabel,"visibility");
			CustomReporter.log("Navigated to audience definition page");
			
			CustomReporter.log("Select attribute");
			audienceBuilder.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			audienceBuilder.performAction("addattribute");
			
			rm.webElementSync(audienceBuilder.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			
			
			ArchitectMediaRankerPage AM= new ArchitectMediaRankerPage(driver);
			String [] strMediaRanker =pageHeader.getSplittedItems(strMediaRankerItems);
			for (int i=0;i<strMediaRanker.length;i++){
				
				System.out.println(strMediaRanker[i]);
				Thread.sleep(3000);
				
				//Step 9 - Click on mega menu and click on digital ranker/tv ranker link
				CustomReporter.log("Click on channel - '" + strMediaRanker[i] +"' from Mega Menu");
				pageHeader.megaMenuLinksClick(strMediaRanker[i]);
				
				rm.webElementSync("idiomspinningcomplete");
								
				if(!AM.func_VerifyVisibilityOfElement("datepicker"))
					   throw new IDIOMException("Failed to open channel###MediaRanker9068-missingScatterPlot");
				
				//Step 10 - Click on Project name or client logo in header 
				CustomReporter.log("Click on Client logo at the top to navigate back to Project Dashboard page");
				
				if(!rm.webElementSync(pageHeader.clientLogo,"visibility"))
					throw new IDIOMException("Client logo is not visible or there might be other issues on the page. ###9068_ClientLogo_Not_visible");
				
				pageHeader.clientLogo.click();
				
				CustomReporter.log("Check whether Project Dashboard page has loaded successfully");
							
				if (!(rm.webElementSync(pdp.projectName, "visibility")))
					throw new IDIOMException("Project Dashboard page has not loaded or there might be other issues. ###9068_ProjectDashboardNotLoaded");
				
				CustomReporter.log("Project Dashboard has loaded successfully");
				
			}
			
						
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
			rm.captureScreenShot("9068", "fail");
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
				
				//Step 11 - Click on logout
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
