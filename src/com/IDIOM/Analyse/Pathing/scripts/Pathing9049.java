package com.IDIOM.Analyse.Pathing.scripts;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;













import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>Webnet_Verify Audience drop down when navigating from Another Page[After switching audience from both the pages</p>
 * <p> <b>Objective:</b> Verify that proper auidence is selected in audience drop down after switching audiences from both the pages</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9049.aspx</p>
 * <p> <b>Module:</b> Pathing</p>
 * @author Amrutha Nair
 * @since 1-June-2016
 *
 */
public class Pathing9049 extends BaseClass {
		
	@Test
	public void verifySwitchingAudience(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
	
		String strDetails=null;
		try{
			
			
		
			//****************Variables declaration and Initialization****************************	
		
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			String strMetrics=property.getProperty("commonSuccessMetrics");
			String strAudienceAttributes1=property.getProperty("audienceDefinition8721yes");
			String strAudienceAttributes2=property.getProperty("audienceDefinition8721no");
			String strDefaultAudience=property.getProperty("defaultaudience");
			//****************Test step starts here************************************************
			
			//Step1 :	Open IDIOM URL and login with valid credentials
			//Step 2:Select any existing client from client dropdown
			loginToSelectClient();
			clientListPage=new ClientList_Page(driver);
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
				throw new IDIOMException("Not able to verify new project window###9049-CreateProjectWindow");
										
			//Step 4:Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###9049-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			 //Step 5:	Click on new audience link ,provide name and click on 'create and save'
			String audienceName = clientListPage.createNewAudience("");
			strMsg = "The first audience ' " + audienceName +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");				
			
			
			
			//Step 7:Click on launch project
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
				rm.captureScreenShot("9049_projectname", "fail");
			}
			
			
			
			//Step 8:Click on Success Metrics page
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			
			//Step 9: Select a few success metrices and navigate to audience definition
			
			AD.selectMetricByName(strMetrics);
			AD.performAction("successmetrics>arrow");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on '>' to navigate to audience definition tab");
			
			
			//Step 10:Add a few filters in audience drop down
			Thread.sleep(2000);
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			
			
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
			
			
			//Step 11:Switch the attribute from audience drop down
			
			PageHeader PH= new PageHeader(driver);
			PH.selectAudienceFromDropDown(strDefaultAudience);
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			strMsg="The user has switched audience to '"+strDefaultAudience+"' from audience definition page";
			CustomReporter.log(strMsg);
			Thread.sleep(3000);
			
			if(AD.getTotalQueryItems()==0){
				strMsg="As expected there is no queries present for the selected audience '"+strDefaultAudience+"' ";
				CustomReporter.log(strMsg);
				
				
			}
			else{
				strMsg="There are queries present for the selected audience '"+strDefaultAudience+"'  eventhough the user didn't add any query for the audience selected";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("9049_DefaultAudience_Queries", "fail");
			}
			
			//Step 12":Select Pathing destination link from megamenu
			Analyse_Pathing_Page pathing = new Analyse_Pathing_Page(driver);
			PH.megaMenuLinksClick(property.getProperty("pathing"));
			rm.webElementSync("idiomspinningcomplete");	
			
			
			
			if(pathing.isVisible("pathing_wheel")){
				strMsg="Navigated to pathing  page, Pathing wheel is visible";
				CustomReporter.log(strMsg);
			}
			//Step 13:Check the default audience selected in pathing page
			
			if(PH.verifySelectedAudienceInDropDown(strDefaultAudience)){
				strMsg="The auidence which the user selected before ie"+strDefaultAudience+"' is coming by default in pathing page";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The auidence which the user selected before is not coming by default in pathing page";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("9049_AudienceSelectedInPathingPage", "fail");
			}
			
			//step 14:Click on audience drop down and verify the audiences present
			PH.performAction("audiencedropdown");
			strMsg="The user has clicked on audience drop down link from page header";
			CustomReporter.log(strMsg);
			
			ArrayList<String> audienceList=PH.returnAudiencesInDropDown();
			
			if(audienceList.size()==2){
				if(audienceList.contains(strDefaultAudience) && audienceList.contains(audienceName) ){
					strMsg="Both the audiences are present in drop down " ;
					CustomReporter.log(strMsg);
				}
				else{
				strMsg="Both the audiences are not present in drop down." ;
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("9049_AudienceInDropDown", "fail");
				}
			}
			else{
				strMsg="The audience drop down does n't have all created audiences";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("9049_AudienceInDropDown", "fail");
			}
			PH.performAction("audiencedropdown");
			Thread.sleep(3000);
			
			
			//Step 15:witch the audience 
			
			PH.selectAudienceFromDropDown(audienceName);
			
			strMsg="The user vhas selected the auudience  '"+audienceName+"' from audience drop down" ;
			CustomReporter.log(strMsg);
			
			if(!rm.webElementSync(pathing.audienceTooNarrow, "visibility"))
				throw new IDIOMException("On switching audience from Pathing page, the data is not getting updated. ###9049_DateUpdatingOnSwitchingAudience");
			
			
			strMsg="The data is getting updated in pathing page on switching audience" ;
			CustomReporter.log(strMsg);
			
		
			
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
				rm.captureScreenShot("9049", "fail");
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
