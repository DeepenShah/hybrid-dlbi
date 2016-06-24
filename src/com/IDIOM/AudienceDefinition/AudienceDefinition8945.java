package com.IDIOM.AudienceDefinition;



import java.util.List;

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
 * <p> <b>Test Case Name:</b>*Def 2388_Audience definition page_To verify user can add attributes to the query quickly and population value is getting updated</p>
 * <p> <b>Objective:</b> To verify population value is changed quickly on adding attributes in audience definition page.</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8945.aspx </p>
 * <p> <b>Module:</b> Common Elements</p>
 * @author Amrutha Nair
 * @since 17-May-2016
 *
 */
public class AudienceDefinition8945 extends BaseClass {
		
	@Test
	public void verifyPopulationGetsAffectedImmediatelyAfterAddingQuery(){
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
			
			String strAudienceAttributes1=property.getProperty("commonaudienceScenario5");
			
			String strSuccessMetrics=property.getProperty("commonSuccessMetrics");
			//****************Test step starts here************************************************
			
			//Step1 :		Open IDIOM URL and login with valid credentials
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
		
			
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	       
	        clientListPage = new ClientList_Page(driver);
	        //Step 2:In client home page, select a client from dropdown
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
			
			//Step 3	Click on any project/Create a project and launch the same
			
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
				throw new IDIOMException("Not able to verify new project window###8945-CreateProjectWindow");
										
			//Step 5:Enter valid name and description for project and click on Save button
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8945-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			//Click on launch project
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
				rm.captureScreenShot("8945-ProjectNameissue", "fail");
			}
			
			
			//Step 4:Click on destination link success metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			//Step 5:Add few success metrics 
			AD.selectMetricByName(strSuccessMetrics);
			Thread.sleep(3000);
			
		
			strMsg="The user has added a few success metrics";
			CustomReporter.log(strMsg);
			
			
			
			//Step 5:	Click on Audience definition tab
			AD.performAction("gotoaudiencedefinition");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on Audience Definition tab");
			
			
			Thread.sleep(3000);
			//Get Population percentage , optimal audience before adding attributes
			int PopulationprojPercent1=AD.getPopulationPercentage();
		
			strMsg="The projected population percentage now is :"+PopulationprojPercent1;
			CustomReporter.log(strMsg);
			int PopulationActPercent1=AD.getPopulationPercentage();
			strMsg="The actual population percentage now is :"+PopulationActPercent1;
			CustomReporter.log(strMsg);
			
			//Step 6:Add few attributes
			
			
			AD.goToFirstLevelForMetricOrAttribute();
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(500);
			if(AD.verifyNoLoadingSymbolPresent()){
				rm.captureScreenShot("8945_PopulationIssue", "fail");
				strMsg="The the population is in loading phase even after half second after adding attributes";		
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				
			}
			else{
				strMsg="The the population is got loaded properly on adding  queries after 3o seconds";
				CustomReporter.log(strMsg);
			}
			strMsg="The user has added a few attributes in the first group";
			CustomReporter.log(strMsg);
			
			AD.performAction("projectedtab");
			Thread.sleep(2000);
			
			AD.goToFirstLevelForMetricOrAttribute();
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(500);
			if(AD.verifyNoLoadingSymbolPresent()){
				rm.captureScreenShot("8945_PopulationIssue", "fail");
				strMsg="The the population is in loading phase even after half second after adding second query attributes";		
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				
			}
			else{
				strMsg="The the population is got loaded properly on adding second query attributes  after 3o seconds";
				CustomReporter.log(strMsg);
			}
			strMsg="The user has added a second query attributes in the first group";
			CustomReporter.log(strMsg);
			
			AD.performAction("projectedtab");
			Thread.sleep(2000);
			
			
		
			
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
			rm.captureScreenShot("8945", "fail");
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