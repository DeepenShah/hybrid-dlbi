package com.IDIOM.Analyse.Webnet.scripts;

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
 * <p> <b>Test Case Name:</b>*WebNet_Verity "Audience too narrow in WebNet</p>
 * <p> <b>Objective:</b>Audience too narrow message should come for 0 percent population</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/7987.aspx</p>
 * <p> <b>Module:</b> webnet</p>
 * @author Amrutha Nair
 * @since 1-June-2016
 *
 */
public class Webnet7987 extends BaseClass {
		
	@Test
	public void verifyAudienceTooNarrrowMessage(){
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
			String strAudienceAttributes1=property.getProperty("audienceDefinition8721yes");
			String strAudienceAttributes2=property.getProperty("audienceDefinition8721no");
			//****************Test step starts here************************************************
			
			//Step1 :	Launch Browser and enter URL
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
			//Step 2:Enter valid username and password.
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 3:Select any existing client from client dropdown
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
			
			//Step 4:Create a project
			
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
				throw new IDIOMException("Not able to verify new project window###7987-CreateProjectWindow");
										
			
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###7987-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			
			
			//Step 5:Launch the project
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
				rm.captureScreenShot("7987_projectname", "fail");
			}
			
			
			//Step 6:Navigate to success metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			

			//step 7:Click on Audience definition tab
			
			AD.performAction("successmetrics>arrow");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on '>' to navigate to audience definition tab");
			
			
			//Step 8:Select attributes in such a way that 0% poulation comes
			
			Thread.sleep(2000);
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
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
		
			
			//Step 9:	Click on webnet link from megamenu
			
			
			Analyse_Webnet_Page webnet = new Analyse_Webnet_Page(driver);
			PageHeader PH= new PageHeader(driver);
			PH.megaMenuLinksClick(property.getProperty("webnet"));
			rm.webElementSync("idiomspinningcomplete");	
			
			strMsg="Navigated to webnet page";
			CustomReporter.log(strMsg);
			
			
			//Step 10:Verify webnet page
			if(!rm.webElementSync(webnet.audienceTooNarrow, "visibility"))
				throw new IDIOMException("Failed to land on webnet page with message audienc too narrow.###7987Audienctoonarrow");
			
			
			strMsg="The webnet page is landed with message 'Audience Too Narrow ' as expected";
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
				rm.captureScreenShot("7987", "fail");
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
