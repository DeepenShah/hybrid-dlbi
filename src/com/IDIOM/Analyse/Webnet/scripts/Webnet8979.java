package com.IDIOM.Analyse.Webnet.scripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

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
 * <p> <b>Test Case Name:</b>*Web Net: Verify values in transition window drop down</p>
 * <p> <b>Objective:</b> Verify values in Transition window should match with requirement</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8979.aspx</p>
 * <p> <b>Module:</b> webnet</p>
 * @author Amrutha Nair
 * @since 31-May-2016
 *
 */
public class Webnet8979 extends BaseClass {
		
	@Test
	public void verifyTransitionWindow(){
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
			String transitionWindow=property.getProperty("transitionwindowDefault");
			String transitionWindowItems=property.getProperty("transitionWindowItems");
			//****************Test step starts here************************************************
			
			//Step1 :Launch browser and point it to the Idiom URL
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
			//Step 2:Type valid username and password; clicks on the "Log into Idiom" button.
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 3:Land in clients home page and select a client.
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
				throw new IDIOMException("Not able to verify new project window###8979-CreateProjectWindow");
										
			//Step5:Enter valid name and description for project and click on Save button
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8979-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
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
				rm.captureScreenShot("8979_projectname", "fail");
			}
			
			//Step 7:Click on Audience Definition link from project dashboard
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			AD.performAction("successmetrics>arrow");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on '>' to navigate to audience definition tab");
			
			
			//Step9:Select few attributes and add them
			Thread.sleep(2000);
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			
			
			strMsg="The user has added an atribute in default group ";
			CustomReporter.log(strMsg);
			
			//Step 9"Click on Web Net link from header mega menu
			Analyse_Webnet_Page webnet = new Analyse_Webnet_Page(driver);
			PageHeader PH= new PageHeader(driver);
			PH.megaMenuLinksClick(property.getProperty("webnet"));
			rm.webElementSync("idiomspinningcomplete");	
			
			strMsg="Navigated to webnet page";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");			
			if(webnet.isVisible("webnetchart")){
				strMsg="The webnet page is loaded properly. The chart is visible";
				CustomReporter.log(strMsg);
			
			}
			else{
				strMsg="The webnet page is not loaded properly. The chart is not visible";
				CustomReporter.log(strMsg);
			}
			
			//Step 10:Verify default selected value transition window drop down
			if(webnet.getSelectedTransitionWindowItem(1).trim().toLowerCase().contentEquals(transitionWindow.toLowerCase())){
				strMsg="The by default selected value in transition window is '"+transitionWindow+"' as expected";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The by default selected value in transition window is NOT '"+transitionWindow+"'";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8979_DefaultDataTranistionWindow", "fail");
				BaseClass.testCaseStatus=false;
			}
			
			
			//Step 11:Click on Transition window and verify drop down values
			webnet.func_ClickElement("transitionwindow");
			strMsg="The user has clicked on transition window drop down";
			CustomReporter.log(strMsg);
			
			String[] transitionWindowExpected=transitionWindowItems.split(",");
			
			List<String> transitionActual=webnet.returnTransitionWindowItems();
			boolean status=true;
			if(transitionWindowExpected.length==transitionActual.size()){
				for(int i=0;i<transitionWindowExpected.length;i++){
					if(!(transitionActual.contains(transitionWindowExpected[i].toLowerCase()))){
						status=false;
						break;
					}
					
				}
				if(status){
					strMsg="The transition window contains expected items ie ' "+transitionWindowItems+"'";
					CustomReporter.log(strMsg);
				}
				else{
					strMsg="The transition window doesn't contain expected items . The expected is ' "+transitionWindowItems+"' But actual is '"+transitionActual+"'";
					throw new IDIOMException(strMsg+"###8979_TransitionWindowItemsIssue");
				}
			}
			else{
				strMsg="The transition window doesn't contain expected items . The expected is ' "+transitionWindowItems+"' But actual is '"+transitionActual+"'";
				throw new IDIOMException(strMsg+"###8979_TransitionWindowItemsIssue");
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
				rm.captureScreenShot("8979", "fail");
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
