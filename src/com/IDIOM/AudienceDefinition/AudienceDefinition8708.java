package com.IDIOM.AudienceDefinition;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>*2116 - Verify that query edit panel is  getting resetting to previous data on clicking on cancel</p>
 * <p> <b>Objective:</b> Verify that query edit panel is  getting resetting to previous data on clicking on cancel </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8708.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Amrutha Nair
 * @since 11 May 2016
 *
 */
public class AudienceDefinition8708 extends BaseClass {

	@Test
	public void verifyEditCancel(){
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
			String strAudienceAttributes=property.getProperty("attribute8708");
			String strEditAudienceAttributes=property.getProperty("editAttribute8708");
			String strAudienceAttributes1=property.getProperty("audiencesScenario3");
			String strSuccessMetrics=property.getProperty("successMetricScenario3");
			//****************Test step starts here************************************************
			
			//Step1 :	Launch browser and enter IDIOM Url
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
			
			//Step 2: Enter valid username and password Click on 'login to idiom' button
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
			
			//Step 3: Click on new project button
			
			//create New Project
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step 4:Enter valid name and description for project and click on Save button
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###8708-CreateProjectWindow");
										
			//If audience tab is not found then everything is good
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8708-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			
			//Step6:Click on launch project
			Thread.sleep(3000);
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
				rm.captureScreenShot("8708-ProjectNameissue", "fail");
				
			}
			
			
			//Step 7:Select some metrics in success metrics and click check mark (>)
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			Thread.sleep(2000);
			AD.selectMetricByName(strSuccessMetrics);
			strMsg="The user has selected few success metrices";
			CustomReporter.log(strMsg);
			
			Thread.sleep(3000);
			AD.performAction("successmetrics>arrow");
			Thread.sleep(3000);
			strMsg="The user has navigated to audience definition page";
			CustomReporter.log(strMsg);
			
			//Step 9:Select few attributes and add to a group
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			strMsg="The user has added one query in the default group";
			CustomReporter.log(strMsg);
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(2000);
		

			strMsg="The user has added one more query in the default group";
			CustomReporter.log(strMsg);
			
			//Step 9:Edit the query
			//Getting first query name before update
			String strFirstQuerybefore=AD.getQueryWithLogic("1.1");
			System.out.println("strFirstQuerybefore:"+strFirstQuerybefore);
		
			AD.clickEditQueryItemlcon("1.1","editquery");
			Thread.sleep(2000);
			strMsg="The user has clicked on edit button for the first query";
			CustomReporter.log(strMsg);
			
			
			
		
			//10:Deselect some of the attributes from edit panel and Click on cancel
			AD.editQueryItem("1.1",strEditAudienceAttributes);
			strMsg="The user has edited data  for the first query";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			
			AD.clickEditQueryItemlcon("1.1","cancelquery");
			Thread.sleep(2000);
			strMsg="The user has clicked on edit-cancel button for the first query";
			CustomReporter.log(strMsg);
			
			
			if(AD.getQueryWithLogic("1.1").contentEquals(strFirstQuerybefore)){
				strMsg="The first query is not changing on cancelling the editing done";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The query is getting updated eventhough we clicked on cancel for edit ";
				throw new IDIOMException(strMsg+".###8708-QueryeditCancel");
			}
			
			
			//Step 11:Now on click on Edit same query  again
			AD.clickEditQueryItemlcon("1.1","editquery");
			Thread.sleep(2000);
			strMsg="The user has clicked on edit button for the the same query";
			CustomReporter.log(strMsg);
			
			
			if(AD.verifyVisibilityOfEditedElementsInQuery("1.1", strEditAudienceAttributes)){
				strMsg="The edit panel is looking proper after cancelling the edit action ";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The edit panel is not looking proper after cancelling the edit action ";
				throw new IDIOMException(strMsg+".###QueryeditCancel");
			}
			
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8708-Exception", "fail");
			}else{
				rm.captureScreenShot("8708-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8708-Exception", "fail");
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
				
				//Step 13:Click on logout				
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