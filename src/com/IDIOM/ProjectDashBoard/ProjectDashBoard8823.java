package com.IDIOM.ProjectDashBoard;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Dashboard: Verify links and menu in dashboard page </p>
 * <p> <b>Objective:</b> Verify links and menu in dashboard page </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8823.aspx </p>
 * <p> <b>Module:</b> Project Dashboard </p>
 * @author Rohan Macwan
 * @since 15 June 2016
 *
 */
public class ProjectDashBoard8823 extends BaseClass {

	@Test
	public void	verifyEachLinkAndTextPresence(){
		String strMsg = null;		
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		String strText="";
		
		try{
						
			//****************Test step starts here************************************************
			
			//loginToSelectClient
			//Step1: Open appropriate environment URL.
			//Step 2 - Provide valid credential
			//Step 3 - Select a client if not selected in previous login
			loginToSelectClient();
			
			//Step 4 - Click on new project button
			//Step 5 - Provide necessary details and click on 'Create' button or if project is already created click on 'Save' or 'Audience' tab. 
			strProjectName = clientListPage.createNewProject("");
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			bProjectCreate=true;
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			rm.webElementSync("pageload");

			clientListPage.launchProject(strProjectName);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			CustomReporter.log("Project dashboard page is open");
			
			// Header Text
						
			//Project Inputs
			strText=property.getProperty("projectInputs");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");
						
			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException((strText + " is not present ###8823_ProjectInputsIsNotPresent"));
									
			CustomReporter.log(strText + " is present");
					
			//Build Audience
			strText="";
			strText=property.getProperty("buildAudience");
			
			CustomReporter.log("Verify whether Header Text " + strText + " is present");
			
			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8823_buildAudienceIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//audienceProfiles
			strText="";
			strText=property.getProperty("audienceProfiles");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");
			
			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8823_audienceProfilesIsNotPresent");
									
			CustomReporter.log(strText + " is present");

						
			//audienceInsights
			strText="";
			strText=property.getProperty("audienceInsights");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");
			
			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8823_audienceInsightsIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//mediaTools
			strText="";
			strText=property.getProperty("mediaTools");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");
			
			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8823_mediaToolsIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//mediaRankers
			strText="";
			strText=property.getProperty("mediaRankers");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");
			
			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8823_mediaRankersIsNotPresent");
									
			CustomReporter.log(strText + " is present");
						
			//competitiveAnalysis
			strText="";
			strText=property.getProperty("competitiveAnalysis");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");
			
			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8823_competitiveAnalysisIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//mediaActivation
			strText="";
			strText=property.getProperty("mediaActivation");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");
			
			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8823_mediaActivationIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			

			
			//Project Description
			strText="";
			strText=property.getProperty("projectDescription");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_ProjectDescriptionIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//projectDocuments
			strText="";
			strText=property.getProperty("projectDocuments");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_projectDocumentsIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//successMetrics
			strText="";
			strText=property.getProperty("successMetrics");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_successMetricsIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//audienceDefinition
			strText="";
			strText=property.getProperty("audienceDefinition");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_audienceDefinitionIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//profile
			strText="";
			strText=property.getProperty("profile");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_profileIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//seGmentation
			strText="";
			strText=property.getProperty("seGmentation");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_seGmentationIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//webnet
			strText="";
			strText=property.getProperty("webnet");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_webnetIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//hva
			strText="";
			strText=property.getProperty("hva");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_hvaIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//pathing
			strText="";
			strText=property.getProperty("pathing");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_pathingIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//atTribution
			strText="";
			strText=property.getProperty("atTribution");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_atTributionIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//mediaMixedModeling
			strText="";
			strText=property.getProperty("mediaMixedModeling");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_mediaMixedModelingIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//digitalRanker
			strText="";
			strText=property.getProperty("digitalRanker");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_digitalRankerIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//tvRanker
			strText="";
			strText=property.getProperty("tvRanker");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_tvRankerIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//kantarStradegy
			strText="";
			strText=property.getProperty("kantarStradegy");
			CustomReporter.log("Verify whether " + strText + " is present");
			
			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_kantarStradegyIsNotPresent");
									
			CustomReporter.log(strText + " is present");
						
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
			rm.captureScreenShot("8823", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step 7 - Click on logout
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
