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
 * <p> <b>Test Case Name:</b> *Audience Definition: Query Builder 2.a - Remove the attribute from the group</p>
 * <p> <b>Objective:</b>Mouse hover on attribute should show option to remove attribute</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8631.aspx</p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Amrutha Nair
 * @since 05-May-2016
 *
 */
public class AudienceDefinition8631 extends BaseClass {
		
	@Test
	public void verifyRemoveAttributeFromGroup(){
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
			String strAudienceAttributes1=property.getProperty("audienceScenario5");
			
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
			
			//Step 3: 	Click on any project/Create a project and launch the same
			
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
				throw new IDIOMException("Not able to verify new project window###8631-CreateProjectWindow");
										
			//If audience tab is not found then everything is good
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8631-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(3000);
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
				rm.captureScreenShot("8631-ProjectNAme", "fail");
			}
			
			
			//Step 4:Click on destination link success metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			
			//Step 5:Select some metrics in success metrics and click check mark (>)
			
			
			AD.performAction("successmetrics>arrow");
			strMsg="The user has clicked on '>' arrow from success metrics page to navigate to audience definition";
					
			CustomReporter.log(strMsg);
			
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			
			
			//Step 6:Select few attributes and add to a group
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			AD.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(2000);
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			strMsg="The user has selected two queries in default group";
			CustomReporter.log(strMsg); 
			
			//Capture query added
			String strQuery=AD.getQueryWithLogic("1.1");
			
			//Step 7:Take mouse on any of the attribute.
			//Step 8:Click on 'X'
			AD.deleteQueryItem("1.1");
			
			
			Thread.sleep(3000);
			String strQuery1=AD.getQueryWithLogic("1.1");
			strMsg="The user has clicked on 'delete' icon for the first query";
			CustomReporter.log(strMsg); 
			
			
			if(strQuery.contentEquals(AD.getQueryWithLogic("1.1"))){
				strMsg="The added query '"+strQuery+"' is not getting deleted on clicking on delete link";
				throw new IDIOMException(strMsg+" .###8631-QueryDeleteNotworking");
				
			}
			else{
				strMsg="The user has deleted the added query '"+strQuery+"' successfully";
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
			rm.captureScreenShot("8631-", "fail");
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
				
				//Step 9:Click on logout				
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