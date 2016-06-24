package com.IDIOM.AudienceDefinition;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
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
 * <p> <b>Test Case Name:</b>*Def 2584_Validate "audience too narrow" message when population value is <1%</p>
 * <p> <b>Objective:</b> To validate "audience too narrow" message is shown for the audience having population value <1% after switching audience.</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8944.aspx</p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Amrutha Nair
 * @since 17-May-2016
 *
 */
public class AudienceDefinition8944 extends BaseClass {
		
	@Test
	public void verifyNavigationForZeroPercent(){
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
			String strAudienceAttributes1=property.getProperty("audienceDefinition8721yes");
			String strAudienceAttributes2=property.getProperty("audienceDefinition8721no");			
			String strdefaultaudience=property.getProperty("defaultaudience");
			String strSuccessMetrics=property.getProperty("commonSuccessMetrics");
			//****************Test step starts here************************************************
			
			//Step1 :	Open IDIOM URL and login with valid credentials
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 2:Select any existing client from client dropdown
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
			
			//Step 3:Click on 'New Project' button
			
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
				throw new IDIOMException("Not able to verify new project window###8944-CreateProjectWindow");
										
			//Step 4:Enter project name and click on Save button
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8944-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			
			//Step 5:Click on  "+ New Audience" to create new audience.
			//Step 6:Enter a name for new audience as 'aud2' and Click on "Create and Save" button.
			 String audienceName = clientListPage.createNewAudience("");
			strMsg = "The audience ' " + audienceName +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			Thread.sleep(2000);
			//Step 7:Click on 'Launch Project' button
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
				rm.captureScreenShot("8944_projectname", "fail");
			}
			
			
			//Step 8:Click on Success metrics link and add few success metrics.
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			AD.selectMetricByName(strSuccessMetrics);
			strMsg="The user has added a few success metrics in success metrics page";
			CustomReporter.log(strMsg);
			
			
			//sTEP 9:Click on 'Audience Definition' tab and add attributes in such a way that population value is <1%
			
			AD.performAction("successmetrics>arrow");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on '>' to navigate to audience definition tab");
			
			
			Thread.sleep(3000);
			
			//Step m6:Select few filters which returns <1%
			//To make the population <1(0%), we are providing two attributes from same category with logic AND which will for sure return 0 percentage
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
			AD.isVisible("populationpercentagevalue", "");
			System.out.println(AD.getSelectedPopulationValue());
			if(AD.getSelectedPopulationValue()==0){
				strMsg="The user has added a few attributes in such a way that , population became <1 (0%) for the audience :"+audienceName;
				CustomReporter.log(strMsg);
			}
		
			
			//Step 7:	Click on Mega menu button and click on link Pathing
			PageHeader PH= new PageHeader(driver);
			PH.megaMenuLinksClick("Pathing");
			strMsg="The user has clicked on 'Pathing ' link";
			CustomReporter.log(strMsg);
			Analyse_Pathing_Page AP = new Analyse_Pathing_Page(driver);
			
			rm.webElementSync("idiomspinningcomplete");
			if(!rm.webElementSync(AP.audienceTooNarrow, "visibility")){
				strMsg="Failed to land on pathing page with message audienc too narrow";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8944Audienctoonarrow", "fail");
			}
				
			else{
				strMsg="The user has navigated to pathing page with message 'Audience too narrow'";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 8:Go back to Client home page and select another audience 'Total population'.Click on Launch project
			
			PH.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo for navigating to client home page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			clientListPage.launchProject(strProjectName);
			
			strMsg="The user launched project '"+strProjectName+"' from client home page";
			CustomReporter.log(strMsg);
			
			Thread.sleep(3000);
			
			PH.selectAudienceFromDropDown(strdefaultaudience);
			strMsg="The user selected default audience '"+strdefaultaudience+"' from the drop down";
			CustomReporter.log(strMsg);
			
			Thread.sleep(3000);
			
			
			//Stp 12:Click on Mega menu and click on link 'Pathing'.
			PH.megaMenuLinksClick("Pathing");
			strMsg="The user has clicked on 'Pathing ' link for audience :"+strdefaultaudience;
			CustomReporter.log(strMsg);
			
			
			rm.webElementSync("idiomspinningcomplete");
			
			if(AP.isVisible("pathing_wheel")){
				strMsg="The pathing wheel is visible in pathing page for the audience '"+strdefaultaudience+"' as expected";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The pathing wheel is not  visible in pathing page for the audience :"+strdefaultaudience;
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8944PathingWheelNotVisible", "fail");
			}
			
			
			//Step 13:User should switch audience from 'Total population' to 'aud2'
			PH.selectAudienceFromDropDown(audienceName);
			strMsg="The user selected  audience '"+audienceName+"' from the drop down";
			CustomReporter.log(strMsg);
			
			Thread.sleep(3000);
			if(!rm.webElementSync(AP.audienceTooNarrow, "visibility")){
				strMsg="The 'Audience Too Narrow ' message is not visible for the audience '"+audienceName+"'";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8944Audienctoonarrow", "fail");
			}
				
			else{
				strMsg="The 'Audience Too Narrow ' message is visible for the audience '"+audienceName+"'";
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
			rm.captureScreenShot("8944", "fail");
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