package com.IDIOM.SuccessMetrics.scripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:<b> Success Metric_3.d. Success Metric Card - Remove metric by clicking 'X' on mouse hover.
<p>	<b>Objective:<b> Removal of card on mouse hover 'X' menu option.
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8597.aspx
<p>	<b>Module:<b> Success Metrics
@author:<b> Abhishek Deshpande
@since:<b> 10-May-2016
**********************************************************************/

public class SuccessMetrics8597 extends BaseClass {
	
	@Test
	public void removeSuccessMetric(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		String strProjectName="Test Case 8597";
		boolean bProjectCreate = false;
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			String strProjectDescription=property.getProperty("projectDescription");
			String strSuccessMetrics=property.getProperty("SelectingSuccessMetrics8598");
			String message=property.getProperty("nosuccessmetricsselected");
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			AudienceBuilderPage successMetricPage = new AudienceBuilderPage(driver);
			
			//Step1: Open IDIOM URL and login with valid credentials
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);
	        
	        Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			
			//Step2: Select any client
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);			
			
			
			//count total number of projects
			int totalProjects = clientListPage.totalProject();
			CustomReporter.log("Total projects: "+totalProjects);			
			
			
			//Step3: Create a new project and launch the same			
			CustomReporter.log("Create new project");	
			clientListPage.func_PerformAction("New Project");
			
			
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);	
			
            clientListPage.findAndSaveProjectWindow(true, "");			
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			clientListPage.func_PerformAction("Launch Project");
			bProjectCreate=true;
			
			//Step4: Click on Success metrics link
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");	
			
			CustomReporter.log("Navigate to success metrics page");
			pdp.navigateTo("Success Metrics");
			
			//Step5: Add few success metrics
            AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			CustomReporter.log("Verify success metrics page is open");
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###testcase8580-pageIsNotVisible");
			
			//Verify success metric page.
			successMetricPage.isVisible("nosuccessmetrictext","");
			CustomReporter.log("Successfully landed on Success Metric page");			
			
			//Add any metric
			successMetricPage.selectMetricByName(strSuccessMetrics);
			Thread.sleep(3000);		
			
			CustomReporter.log("Successfully added Success metrics");
			rm.captureScreenShot("Test8597MetricCardHasBeenAdded", "pass");
			
			
			//Step6: Select any one success metric
			//Step7: Click on 'X' icon					
			List<String> allSuccessMetrics = successMetricPage.getNameOfAllMetricCards(); //This will store all the success metrics names in List
			for(int i=0;i<allSuccessMetrics.size();i++){
				Thread.sleep(2000);
				successMetricPage.removeSuccessMetricCardByName(allSuccessMetrics.get(i));
				CustomReporter.log("Deleted " + allSuccessMetrics.get(i) + " success metric");
				Thread.sleep(2000);
			}
			
			
			//Verify no success metrics message
			 if(!(successMetricPage.getNoSuccessMetricsSelectedMessage().equals(message)))
				 throw new IDIOMException("Failed to verify no success metric message ###testcase8580-NoSuccessMetricmessage");			 
			 
			 CustomReporter.log("Successfully deleted all success metrics one by one & 'No success metrics selected message' is displayed");			
				
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
			rm.captureScreenShot("AnalysePagesIssue", "fail");
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
		
	