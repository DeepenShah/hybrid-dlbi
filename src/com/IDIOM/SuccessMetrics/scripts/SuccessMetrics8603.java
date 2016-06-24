package com.IDIOM.SuccessMetrics.scripts;

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
<p>	<b>Test Case Name:<b> Success Metrics - 2.d.i.1 - Verify what will be search bar text
<p>	<b>Objective:<b> Verify what will be search bar text
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8603.aspx
<p>	<b>Module:<b> Success Metrics
@author:<b> Shailesh Kava
@since:<b> 03-May-2016
**********************************************************************/
public class SuccessMetrics8603 extends BaseClass {
		
	@Test
	public void verifySearchComponentAtDepthLevel(){
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;

		try{
			//****************Variables declaration and Initialization****************************	
			String strProjectDescription=property.getProperty("projectDescription");
			//****************Test step starts here************************************************
			
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 2,3: Selected a client now selecting/creating new project 
			if(totalProjects == 0){
				strProjectName = clientListPage.createNewProject("");
				clientListPage.func_PerformAction("Launch Project");
				
				clientListPage.func_PerformAction("Launch Project");
				bProjectCreate = true;
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
			}else{
				int selectProjectId = totalProjects;
				System.out.println("Clicking on project id: "+selectProjectId);
				CustomReporter.log("Clicking on project id: "+selectProjectId);
				clientListPage.clickProjectById(selectProjectId);
			}
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			//Step4: Click on destination link success metrics
			CustomReporter.log("Navigate to success metrics page");
			pdp.navigateTo(property.getProperty("successMetrics"));
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			CustomReporter.log("Verify success metrics page is open");
			
			//AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###successMetricsPage-pageIsNotVisible");
			
			//Step 5.0: verify before adding any success metrics that search button should associated with "All Metrics" 
			if(audienceBuilder.isVisible("searchbutton", "")){
				CustomReporter.log("Search button is appearing at first level");
				System.out.println("Search button is appearing at first level");
				
				String selectedCategoryInSuccessMetrics = audienceBuilder.getSelectedCategoryName();
				
				if(!selectedCategoryInSuccessMetrics.toLowerCase().trim().equals("all metrics")){
					BaseClass.testCaseStatus = false;
					CustomReporter.errorLog("Either all metrics is not visible/problemetic###SuccessMetrics8603-problematicFirstDefaultLevel");
					System.out.println("Either all metrics is not visible/problemetic");
				}else{
					CustomReporter.log("Search icon is associated with [all metrics]");
					System.out.println("Search icon is associated with [all metrics]");
				}
				
			}else{
				throw new IDIOMException("Search button is not appearing###SuccessMetrics8603-searchIconNotWithCategory");
			}
			
			//Split property and get depth level property name 
			String[] successMetricsCategory = property.getProperty("addsuccessMetric").split(",");
			String selectedCategory = successMetricsCategory[successMetricsCategory.length-1];
			
			System.out.println("Category from propery: "+selectedCategory);
			
			//Select success metrics based on property
			audienceBuilder.selectMetricByName(property.getProperty("addsuccessMetric"));
			
			Thread.sleep(2000);
			//Step 5.1: Now verify selected category name from success metrics page which should associate with search icon
			if(audienceBuilder.isVisible("searchbutton", "")){
				CustomReporter.log("Search button is appearing");
				System.out.println("Search button is appearing");
				
				String selectedCategoryInSuccessMetrics = audienceBuilder.getSelectedCategoryName();
				CustomReporter.log("Category in property ["+selectedCategory+"], category from success metrics ["+selectedCategoryInSuccessMetrics+"]");

				if(!selectedCategory.toLowerCase().trim().equals(selectedCategoryInSuccessMetrics.toLowerCase().trim())){
					throw new IDIOMException("Category name is missing/problametic###SuccessMetrics8603-problematicCategoryName");
				}else{
					CustomReporter.log("Search icon is associated with ["+selectedCategory+"]");
					System.out.println("Search icon is associated with ["+selectedCategory+"]");
				}
				
			}else{
				throw new IDIOMException("Search button is not appearing###SuccessMetrics8603-searchIconNotWithCategory");
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
			rm.captureScreenShot("8603", "fail");
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