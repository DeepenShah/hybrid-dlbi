package com.IDIOM.Analyse.Pathing.scripts;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
 * <p> <b>Test Case Name:</b>*2487: Pathing: Behavior dropdown should not get hide if Audience too narrow message appears</p>
 * <p> <b>Objective:</b>Verify that behavior drop down should not get hide if audience too narrow appears for any audience</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8933.aspx</p>
 * <p> <b>Module:</b> Pathing</p>
 * @author Amrutha Nair
 * @since 1-June-2016
 *
 */
public class Pathing8933 extends BaseClass {
		
	@Test
	public void verifyBehvaiourDropDownForTooNarrowAudience(){
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
				throw new IDIOMException("Not able to verify new project window###8933-CreateProjectWindow");
										
			//Step 4:Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8933-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			
			
			//Step 5:Click on launch project
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
				rm.captureScreenShot("8933_projectname", "fail");
			}
			
			
			
			//Step 5:Now click on success metrics link from project dashboard page
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			
			
			
			//Step 6:Navigate to audience definition page
			
			AD.performAction("successmetrics>arrow");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on '>' to navigate to audience definition tab");
			
			
			//Step 7:Now select few behaviors (Select in that way like it should return Audience too narrow) in pathing page, and click on pathing link from header mega menu (Reference issue id: #2487) 
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
			
			System.out.println(AD.getSelectedPopulationValue());
			if(AD.getSelectedPopulationValue()==0){
				strMsg="The population percentage has updated with zero percent";
				CustomReporter.log(strMsg);
			}
	
			
			PageHeader PH= new PageHeader(driver);
			
			//Select Pathing destination link from megamenu
			Analyse_Pathing_Page pathing = new Analyse_Pathing_Page(driver);
			PH.megaMenuLinksClick(property.getProperty("pathing"));
			rm.webElementSync("idiomspinningcomplete");	
			
			if(!rm.webElementSync(pathing.audienceTooNarrow, "visibility")){
				strMsg="The 'Audience Too narrow ' message is not coming on pathing page when population is zero percent";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8933_AudienceTooNarrowMissing", "fail");
			}
			else{
				strMsg="The 'Audience Too narrow ' message is  coming on pathing page when population is zero percent";
				CustomReporter.log(strMsg);
			
			}
			
			//Step 8:Change behavior and try to get Audience too narrow page
			if(pathing.isVisible("behaviourdropdown")){
				strMsg="The behaviour dropdown is visible in pathing page";
				CustomReporter.log(strMsg);
				
				ArrayList<String> behaviourLst=pathing.func_CaptureList("listBehavirs");
				for(int i=0;i<behaviourLst.size();i++){
					selectBehaviour(behaviourLst.get(i));
					CustomReporter.log("The user has selected '"+behaviourLst+"' from behaviour drop down in pathing page");
					rm.webElementSync("idiomspinningcomplete");	
				}
				
			}
			else{
				strMsg="The behaviour dropdown is not visible in pathing page when 'audience is  narrow'";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8933_NoBehaviourDropDown", "fail");
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
				rm.captureScreenShot("8933", "fail");
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
		
	public void selectBehaviour(String name){
		Analyse_Pathing_Page p=new Analyse_Pathing_Page(driver);
		Select elementDropdown = new Select(p.behaviourDropDown);
		int index = 0;
	    for (WebElement option :p.list_behaviours){
	        if (option.getText().equalsIgnoreCase(name))
	            break;
	        index++;
	    }
		elementDropdown.selectByIndex(index);
	}
		
	}
