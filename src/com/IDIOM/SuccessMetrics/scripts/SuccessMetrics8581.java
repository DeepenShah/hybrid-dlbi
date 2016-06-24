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
<p>	<b>Test Case Name:<b> Success Matrix: Filtering Menu: b. Click on sub category of any success metrics category
<p>	<b>Objective:<b> Verify selected sub category is appearing in center area after selecting it from right hand side.
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8581.aspx
<p>	<b>Module:<b> Success Metrics
@author:<b> Abhishek Deshpande
@since:<b> 05-May-2016
**********************************************************************/

public class SuccessMetrics8581 extends BaseClass {
	
	@Test
	public void verifysubcategory(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		String strProjectName="Test Case 8574";
		boolean bProjectCreate = false;
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			String strProjectDescription=property.getProperty("projectDescription");			
			String subcategoryName=property.getProperty("successmetricname").toLowerCase();
			String successMetricName=property.getProperty("successMetricScenario3");
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
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);			
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//count total number of projects
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			System.out.println("Create new project");
			CustomReporter.log("Create new project");	
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
			
			//Step4: Click on destination link success metrics
			CustomReporter.log("Navigate to success metrics page");
			pdp.navigateTo("Success Metrics");
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			CustomReporter.log("Verify success metrics page is open");
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###testcase8580-pageIsNotVisible");
			
			//Verify success metric page.
			successMetricPage.isVisible("nosuccessmetrictext","");
			CustomReporter.log("Successfully landed on Success Metric page");
			Thread.sleep(2000);		
			
			//Click on success metrics
			audienceBuilder.selectMetricByName(successMetricName);
			CustomReporter.log("Added success metric "+successMetricName);
			Thread.sleep(4000);
			
			//Selected success metric should show 'X' icon with RED background
			if(!(audienceBuilder.getCountForSubCategoriesHavingCorssIcon()==1))
				 throw new IDIOMException("Croos Icon is not shown for sub category###testcase8581-crossicon");
			
			CustomReporter.log("Selected success metric show icon 'X' ");
			
			
			//Success metric should be available in center area
			if(!(audienceBuilder.getCountOfSuccessMetricsAddedInCenter()==1)) //We should find only one success metrics in the center area as per this test scenario.
				throw new IDIOMException("Success metrics count is not correct in center area###testcase8581-NoofSuccessMetricsAddedInCenter");
			
			CustomReporter.log("Verified count of Success metrics in the center area");
			
			//Selected success metric should appear in the center area of the page
			if(!audienceBuilder.verifySuccessMetricNameInCenterArea(subcategoryName))
				throw new IDIOMException("Success metric name is not correct in center area###testcase8581-NameofSuccessmetric");
			
			CustomReporter.log("Correct success metric name is displayed in center area");
			
			
			
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
		
	