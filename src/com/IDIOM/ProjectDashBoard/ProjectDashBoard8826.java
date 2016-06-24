package com.IDIOM.ProjectDashBoard;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Dashboard: By default, users will see all steps on the dashboard</p>
 *  <p> <b>Objective:</b>Dashboard: By default, users will see all steps on the dashboard</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8826.aspx</p>
 *  <p> <b>Module:</b>Project Dashboard</p>
 *  
 * @author Abhishek Deshpande
 * @since 16 June 2016
 *
 */
public class ProjectDashBoard8826 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyDefaultViewOfPDP(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="";
		String strText="";
		
		try{
			
			//****************Test step starts here************************************************
						
			//Step1-3: Login To Selecting Client
			loginToSelectClient();			
			
			//Step4: Create Project
			strProjectName = clientListPage.createNewProject("");
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//Launch Project
			clientListPage.launchProject(strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			
			if(!rm.webElementSync(pdp.projectName, "visibility"))
				throw new IDIOMException("Failed to land on Project Dashboard page.###FailedToLandOnProjectDashboardPage");
			
			CustomReporter.log("Navigated to Project Dashboard page");
			
			//Verifying visibility of links
						
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
			
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				
				if(!strProjectDetails.equalsIgnoreCase("")){
					util.deleteProjectOrAudience(strProjectDetails,true);
					CustomReporter.log("Deleted the project");
				}
				
				//Logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(strClassName + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		
		if(!BaseClass.testCaseStatus){
			  CustomReporter.errorLog("Test case failed");
			  Assert.assertTrue(false);
		  }else{
			  CustomReporter.log("Test case passed");
		  }	
	}
}
