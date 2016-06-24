package com.IDIOM.Analyse.HVA.scripts;


import java.util.ArrayList;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;


import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>**HVA_VerifyswitchingAudience is proper</p>
 * <p> <b>Objective:</b> Verify that data is changing accordingly when we switch the audiences from HVA page</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9063.aspx</p>
 * <p> <b>Module:</b> HVA</p>
 * @author Amrutha Nair
 * @since 25-May-2016
 *
 */
public class HVA9063 extends BaseClass {
		
	@Test
	public void verifySwitchingAudienceIssue(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
	
		String strDetails=null;
		try{
			
			
		
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			String strAudienceAttributes1=property.getProperty("commonAudienceAttribute2");
			String strdefaultaudience=property.getProperty("defaultaudience");
			String strAudienceAttributes2=property.getProperty("cmmonAudienceAttribute");
			Hashtable<String, String> ChartData1 = new Hashtable<String, String>();
			Hashtable<String, String> ChartData2 = new Hashtable<String, String>();
			//****************Test step starts here************************************************
			
			//Step1 :	Launch browser and enter IDIOM Url
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
			//Step 2:	Enter valid username and password Click on 'login to idiom' button
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
			
			//Step 3:Click edit for any project/Create a project
			
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
				throw new IDIOMException("Not able to verify new project window###9063-CreateProjectWindow");
										
			//Step 4:Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###9063-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			 //Step 5:Click on new audience link ,provide name and click on 'create and save'
			String audienceName = clientListPage.createNewAudience("");
			strMsg = "The audience ' " + audienceName +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");				
			
				 
			//Step 6:Click on launch project
			clientListPage.func_PerformAction("Launch Project");
			strMsg = "Clicked on Launch Project";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
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
				rm.captureScreenShot("9063_projectname", "fail");
			}
			
			
			//Step 7:Click on Success Metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			
			
			
			//sTEP 8:Click on Audience Definition tab
			
			AD.performAction("successmetrics>arrow");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on '>' to navigate to audience definition tab");
			
			
			Thread.sleep(3000);
			
			//Step 9:Select few attributes and add them
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(2000);
		
			AD.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(2000);
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes2);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			if(AD.getQueryLogic("1.2").toLowerCase().contentEquals("or")){
				AD.clickToChangeQueryLogic("1.1");
			}
			strMsg="The user has added filters in audience definition page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			System.out.println(AD.getSelectedPopulationValue());
			
			
			//Step 9:Switch the audience from audience drop down
			PageHeader PH=new PageHeader(driver);
			
			PH.selectAudienceFromDropDown(strdefaultaudience);
			
			strMsg="The user has switched the audience";
			CustomReporter.log(strMsg);
			
			Thread.sleep(5000);
			//Step 10:Select HVA destination link from Mega Menu
			
			PH.megaMenuLinksClick(property.getProperty("hva"));
			
			
			HVA_Page hva = new HVA_Page(driver);
			
			rm.webElementSync("idiomspinningcomplete");			
			if(rm.webElementSync(hva.behaviour,"visibility")){
				strMsg="The HVA page is getting loaded successfully";
				CustomReporter.log(strMsg);
			}
			
			//Step 11:Check the default audience selected in HVA page

			if(PH.verifySelectedAudienceInDropDown(strdefaultaudience)){
				strMsg="The auidence which the user selected before is coming by default in hva page";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The auidence which the user selected before is not coming by default in hva page";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("9063_SelectedAudience", "fail");
			}
			Thread.sleep(5000);
			PH.performAction("audiencedropdown");
			Thread.sleep(3000);					
			
			//Step 12:Click on audience drop down and verify the audiences present
			
			ArrayList<String> audienceList = PH.returnAudiencesInDropDown();
			
			if(audienceList.size()==2){
				if(audienceList.contains(strdefaultaudience) && audienceList.contains(audienceName) ){
					strMsg="Both the audiences are present in drop down in hVA header";
					CustomReporter.log(strMsg);
				}
				else{
				strMsg="Both the audiences are not present in drop down in hVA header";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("9063_AudienceInDropDown", "fail");
				}
			}
			else{
				strMsg="The audience drop down does n't have all created audiences";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("9063_AudienceInDropDown", "fail");
			}
			Thread.sleep(5000);
			PH.performAction("audiencedropdown");
			Thread.sleep(3000);					
			
			
			boolean status=false;
			ArrayList<String> behaviours= hva.func_CaptureList("Behaviour");
			for(int i=0;i<behaviours.size();i++){
				hva.selectBehaviorWithID(i);
				rm.webElementSync("idiomspinningcomplete");
				Thread.sleep(3000);
				strMsg="The user has selected behaviour  '"+behaviours.get(i)+"' from drop down";
				CustomReporter.log(strMsg);
				ChartData1=hva.func_CaptureChartValues(behaviours.get(i));
				
				//Step 13:Switch the audience 
				System.out.println(audienceName);
				PH.selectAudienceFromDropDown(audienceName);
				rm.webElementSync("idiomspinningcomplete");
				Thread.sleep(3000);
				rm.webElementSync(hva.behaviour,"visibility");
				ChartData2=hva.func_CaptureChartValues(behaviours.get(i));
				if(!(hva.func_CompareCharts(ChartData2, ChartData1))){
					CustomReporter.log("The chart data is not changing for behaviour'"+behaviours.get(i)+"' on switching audience filters");
				}
				else{
					CustomReporter.log("The chart data is  changing for behaviour'"+behaviours.get(i)+"' on switching audience filters");
					status=true;
				}
				Thread.sleep(1000);
				PH.selectAudienceFromDropDown(strdefaultaudience);
				rm.webElementSync("idiomspinningcomplete");
				Thread.sleep(3000);
				rm.webElementSync(hva.behaviour,"visibility");
			}
			if(status){
				strMsg="The chart data in HVA page is changing on switching filters with different queries added";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The chart data in HVA page is not changing on switching filters with different queries added";
				throw new IDIOMException(strMsg+"###9063-DataChangingOnSwitchingAudience");
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
				rm.captureScreenShot("9063", "fail");
			}finally{
				try{
					
					//Deleting newly created project
					if(bProjectCreate){
						///////////pageHeader.navigateTo("projecthomepage");
						rm.deleteProjectOrAudience(strDetails, true);
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
