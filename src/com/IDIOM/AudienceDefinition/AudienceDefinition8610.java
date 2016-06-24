package com.IDIOM.AudienceDefinition;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.msat.frameworks.data_driven.base.Base;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
/** 
 * <p> <b>Test Case Name:</b> *Audience Definition - a (Categories).i.1.a - Verify the data (Population, Optimal Audience) once future behaviors are added</p>
 * <p> <b>Objective:</b> Objective: Verify the data (Population, Optimal Audience) once future behaviors are added</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8610.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Amrutha Nair
 * @since 03-May-2016
 *
 */
public class AudienceDefinition8610 extends BaseClass {
		
	@Test
	public void verifyPopulationOptimalAudience(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			String strAudienceAttributes=property.getProperty("audiencesScenario3");
			String strFuturebehaviour=property.getProperty("audienceCategory");
			String strAudienceAttributes1=property.getProperty("commonaudienceScenario5");
			
			String strSuccessMetrics=property.getProperty("successMetricScenario4");
			//****************Test step starts here************************************************
			
			//Step1 :	Login the application with valid credentials
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 2:Select a client
	        clientListPage = new ClientList_Page(driver);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Verifies whether all project list or No project msg is coming for selected client
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			//Step 3: Select a project/create new and launch the project
			
			//create New Project
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Fill the project
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###8610-CreateProjectWindow");
										
			//If audience tab is not found then everything is good
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8610-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			//Launch the project
			clientListPage.func_PerformAction("Launch Project");
			strMsg = "Clicked on Launch Project";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Waiting till page get loaded
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			if(pdp.isVisible("projectname", strProjectName)){
				strMsg="The project home page has been launched and it is getting updated with selected project name";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The seleced project name is not coming in project home page";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8610-ProjectNameissue", "fail");
			}
			
			
			//Step 4:Click on destination link success metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			AD.selectMetricByName(strSuccessMetrics);
			Thread.sleep(3000);
			//Step 5:Click on Audience definition tab
			AD.performAction("gotoaudiencedefinition");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on Audience Definition tab");
			
			
			Thread.sleep(3000);
			AD.isVisible("populationpercentagevalue", "");
			//Get Population percentage , optimal audience before adding attributes
			int PopulationPercent1=AD.getPopulationPercentage();
			double optProjectedAdudience1 =AD.getOptimalAudiencePercentage();
			AD.performAction("actualpopulationtab");
			double optActualAdudience1 =AD.getOptimalAudiencePercentage();
			strMsg="The population percentage now is :"+PopulationPercent1;
			CustomReporter.log(strMsg);
			
			//Step 6:Expand Future behavior section under All Attributes dropdown/search bar at the right hand side and check the future behaviors that are displayed			
			
			AD.selectAttributeOnAudienceDefinition(strFuturebehaviour);
			
			strMsg="The user has selected Future behaviours";
			CustomReporter.log(strMsg);
			
			
			
			//Step 7:Now add/remove few future behaviors and verify the data (Population, Optimal Audience)
			AD.performAction("projectedtab");
			AD.goToFirstLevelForMetricOrAttribute();
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes);
			AD.performAction("addattribute");
			
			Thread.sleep(5000);
			AD.isVisible("populationpercentagevalue", "");
			int PopulationPercent2=AD.getPopulationPercentage();
			double optProjectedAdudience2 =AD.getOptimalAudiencePercentage();
			AD.performAction("actualpopulationtab");
			double optActualAdudience2 =AD.getOptimalAudiencePercentage();
			strMsg="The population percentage now is :"+PopulationPercent2;
			CustomReporter.log(strMsg);
			
			if(PopulationPercent2 == PopulationPercent1){
				strMsg="The population percentage is not changing on adding future behaviours in audience definition";
				throw new IDIOMException(strMsg+" .###8610-PercnetageNotChanging");
			}
			else{
				
				strMsg="The population percentage is  changing on adding future behaviours in audience definition";
				CustomReporter.log(strMsg);
			}
			
			if(optProjectedAdudience2 ==optProjectedAdudience1 && optActualAdudience2== optActualAdudience1){
				strMsg="The optimal audience is not changing on adding future behaviours in audience definition";
				throw new IDIOMException(strMsg+" .###8610-OptimalAudienceNotChanging");
			}
			else{
				strMsg="The optimal audience is  changing on adding future behaviours in audience definition";
				CustomReporter.log(strMsg);
			}
			
			
			
			//Step 8:Create a group and another attribute into it. 
			AD.addNewGroup();
			strMsg="The new group has been added";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			AD.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(3000);
			
			AD.selectGroup("2");
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			
			AD.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(3000);
			
			AD.performAction("projectedtab");
			Thread.sleep(5000);
			
			AD.isVisible("populationpercentagevalue", "");
			Thread.sleep(2000);
			int PopulationPercent3=AD.getPopulationPercentage();
			
			double optProjectedAdudience3 =AD.getOptimalAudiencePercentage();
			AD.performAction("actualpopulationtab");
			double optActualAdudience3 =AD.getOptimalAudiencePercentage();
			
			if(PopulationPercent3 == PopulationPercent1){
				strMsg="The population percentage is not changing on adding new group and queries in audience definition";
				CustomReporter.errorLog(strMsg);
				throw new IDIOMException(strMsg+" .###8610-PercentageNotChanging");
				
			}
			else{
				
				strMsg="The population percentage is  changing on adding new group and queries in audience definition";
				CustomReporter.log(strMsg);
			}
			
			if(optActualAdudience3 == optActualAdudience2 && optProjectedAdudience2 == optProjectedAdudience3){
				strMsg="The optimal audience is not changing on adding new group and queries in audience definition";
				
				throw new IDIOMException(strMsg+" .###8610-OptimalAudienceNotChanging");
			}
			else{
				strMsg="The optimal audience is  changing on adding new group and queries in audience definition";
				CustomReporter.log(strMsg);
			}
			
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8610", "fail");
		}finally{
			try{
				
				//Deleting newly created project
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");
					
					clientListPage.func_PerformAction("Close Project");
					
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");
				}
				
				//Step 8:Click on logout				
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
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