package com.IDIOM.SuccessMetrics.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> *Success Metric_2.e Bottom Save Bar - Check count of selected metrics. </p>
 * <p> <b>Objective:</b> To verify selected metric count on bottom save bar </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8567.aspx </p>
 * <p> <b>Module:</b> Audience Builder - Success Metrics</p>
 * @author Rohan Macwan
 * @since 03 May 2016
 *
 */
public class SuccessMetrics8567 extends BaseClass{

	@Test
	public void SuccessMetrics8567(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName=property.getProperty("projectName8567");
		String strNoSuccessMetricsMsg=property.getProperty("noSuccessMetricsMsg8567");
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			
			String strProjectDescription=property.getProperty("projectDescription");
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Open IDIOM URL and login with valid credentials
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 2, 3 - Select any existing client from client dropdown. Click on any project/Create a project and launch the same
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
		
			System.out.println("No project available for this client, ceating new project");
			CustomReporter.log("No project available for this client, ceating new project");	
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			clientListPage.findAndSaveProjectWindow(true, "");
			
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			clientListPage.func_PerformAction("Launch Project");
			bProjectCreate=true;
				
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 4 - Click on destination link success metrics
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			//Step5: Do no select any card in success metrics and check the message displayed at the bottom right of the page. 
			pdp.navigateTo(property.getProperty("successMetrics"));
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			String strNoSuccessMetricsSelectedMsgOnApplication="";
			
			strNoSuccessMetricsSelectedMsgOnApplication=audienceBuilder.getNoSuccessMetricsSelectedMessage();
			
			CustomReporter.log("The expected message is '"+ strNoSuccessMetricsMsg.toUpperCase() + "' and Acutal Message is '" + strNoSuccessMetricsSelectedMsgOnApplication.toUpperCase() + "'");
			
			if (!strNoSuccessMetricsMsg.trim().equalsIgnoreCase(strNoSuccessMetricsSelectedMsgOnApplication.trim()))
				throw new IDIOMException("They are not matching###8567_DefaultMessageWhenNoSuccessMetricsDefinedAtTheBottom");
			
			CustomReporter.log("Both are mathcing");
			
			
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
			rm.captureScreenShot("8567", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");
					
					clientListPage.func_PerformAction("Close Project");
					
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");
				}
				
				//Step 6 - Click on logout 
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		//****************Test step ends here************************************************
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	}

}
