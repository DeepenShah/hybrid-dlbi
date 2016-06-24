package com.IDIOM.AudienceDefinition;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.msat.frameworks.data_driven.base.Base;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
/** 
 * <p> <b>Test Case Name:</b>*Common Elements - 3.ii Saving - Verify when a success metric and/or audience attribute is added in the builder</p>
 * <p> <b>Objective:</b> Verify when a success metric and/or audience attribute is added in the builder</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8594.aspx</p>
 * <p> <b>Module:</b> Common Elements</p>
 * @author Amrutha Nair
 * @since 16-May-2016
 *
 */
public class AudienceDefinition8594 extends BaseClass {
		
	@Test
	public void verifyMetricsAndFiltersGetsSaved(){
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
			
			String strSuccessMetrics=property.getProperty("successMetrics8594");
			String strmetricCard=property.getProperty("metricCard8594");
			//****************Test step starts here************************************************
			
			//Step1 :		Launch browser and enter IDIOM Url
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
			
			//Step 2":Enter valid username and password Click on 'login to idiom' button
			
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 3:In client home page, select a client from dropdown
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
			
			//Step 4:Click on new project button
			
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
				throw new IDIOMException("Not able to verify new project window###8594-CreateProjectWindow");
										
			//Step 5:Enter valid name and description for project and click on Save button
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8594-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(3000);
			//Step 6"Click on launch project
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
				rm.captureScreenShot("8594-ProjectNameissue", "fail");
			}
			
			
			//Step7:	Click on Success Metrics to go to Audience Builder Section
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			//Step 8:Select a few success matrix and audience filters
			AD.selectMetricByName(strSuccessMetrics);
			Thread.sleep(3000);
			strMsg="The user has added success metrics '"+strSuccessMetrics+"'";
			CustomReporter.log(strMsg);
			//Step 5:	Click on Audience Definition link from project dashboard
			AD.performAction("gotoaudiencedefinition");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on Audience Definition tab");
			
			
			Thread.sleep(3000);
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			strMsg="The user has added a audience filter query to the default group";
			CustomReporter.log(strMsg);
			String strQuery=AD.getQueryWithLogic("1.1");
			//To verify whether thge success metrics and audience definition got saved, go back to project home page and come back to audience builder and verify
			
			//Go back to project home page
			PageHeader PH = new PageHeader(driver);
			PH.performAction("clientlogo");
			rm.webElementSync(pdp.projectName,"visibility");
			Thread.sleep(3000);
			pdp.navigateTo(property.getProperty("successMetrics"));
			Thread.sleep(6000);
			AD.isVisible("listSuccessmetricsleftside");
			if((AD.getCountOfSuccessMetricsAddedInCenter()==1)&&(AD.verifySuccessMetricNameInCenterArea(strmetricCard))){
				strMsg="The success metrics got saved successfully";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The success metrics has not got saved successfully";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8594-SuccessMetricsSaveIssue", "fail");
			}
								
			AD.performAction("gotoaudiencedefinition");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			Thread.sleep(3000);
			if((AD.getTotalQueryItems()==1)&&(strQuery.contentEquals(AD.getQueryWithLogic("1.1")))){
				strMsg="The audience definition filters got saved successfully";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The audience definition filters has not got saved successfully";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8594-AudienceiltersSaveIssue", "fail");
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
			rm.captureScreenShot("8594", "fail");
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