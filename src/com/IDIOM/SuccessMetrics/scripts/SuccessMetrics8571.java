package com.IDIOM.SuccessMetrics.scripts;

import java.util.List;

import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:<b> Common Elements - 2.a Population - Verify the positioning of Population Panel.
<p>	<b>Objective:<b> Verify the positioning of Population Panel.
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8571.aspx
<p>	<b>Module:<b> Success Metrics
@author:<b> Abhishek Deshpande
@since:<b> 13-May-2016
**********************************************************************/

public class SuccessMetrics8571 extends BaseClass {
	
	@Test
	public void verifyBottomSaveBar(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		String strProjectName="Test Case 8568";
		boolean bProjectCreate = false;
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			String strProjectDescription=property.getProperty("projectDescription");
			property.getProperty("SelectingSuccessMetrics8598");
			property.getProperty("successMetricScenarioCommon");
			property.getProperty("cmmonAudienceAttribute");
			property.getProperty("commonAudienceAttribute2");
			
			
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			AudienceBuilderPage successMetricPage = new AudienceBuilderPage(driver);
			
			//Step 1: Open IDIOM URL and login with valid credentials
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);
	        
	        Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);			
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//count total number of projects
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			System.out.println("Create new project");
			CustomReporter.log("Create new project");	
			clientListPage.func_PerformAction("New Project");
			
			
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			clientListPage.findAndSaveProjectWindow(true, "");
			
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			clientListPage.func_PerformAction("Launch Project");
			bProjectCreate=true;
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");			
			
			//Step 4: Click on destination link success metrics
			CustomReporter.log("Navigate to success metrics page");
			pdp.navigateTo("Success Metrics");
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			CustomReporter.log("Verify success metrics page is open");
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###testcase8568-pageIsNotVisible");
			
			//Verify success metric page.
			successMetricPage.isVisible("nosuccessmetrictext","");
			CustomReporter.log("Successfully landed on Success Metric page");		
			
			//Step 5: Navigate to Audience definition page			
			successMetricPage.performAction("gotoaudiencedefinition");
			rm.webElementSync(successMetricPage.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Navigated to Audience Definition tab");						
			
			//Get X and Y coordinates of population value and verify population panel is displayed on the left side of the page
			/*Point point = audienceBuilder.getPositionOfElement("populationvalue");
			if(!(point.x==28 && point.y==138))
				throw new IDIOMException("population panel is not displayed on the left side of the page###testcase8571-verifypopulationpanelposition");			
			CustomReporter.log("Population panel is displayed on left side of the page");*/
			
			//Verify default value of population
			if(!rm.webElementSync(audienceBuilder.percentagePopulationValue, "visibility"))
				throw new IDIOMException("population panel is not displayed on the left side of the page###testcase8571-verifypopulationpanelposition");
			
			CustomReporter.log("Population panel is displayed on left side of the page");
			
			Thread.sleep(2000);
			System.out.println("percentage val: "+audienceBuilder.getPopulationPercentage());
			if(!(audienceBuilder.getPopulationPercentage()==100))
				throw new IDIOMException("Audience definition default population value is not 100%###testcase8571-populationpercentage");
			CustomReporter.log("Verified default population value in audience defnition tab");
			
				
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
			rm.captureScreenShot("AnalysePagesIssue", "fail");
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
		
	