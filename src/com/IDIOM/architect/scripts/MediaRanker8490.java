package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Test Case Name</b></p> 1087_Verify invalid date range should not appear in calendar
<p><b>Test Case ID</b></p> http://qa.digitas.com/SpiraTest/523/TestCase/8490.aspx
<p><b>Objective</b></p> Verify invalid date range should not appear in calendar
<p><b>Module</b></p> MediaRanker
@author Shailesh Kava
@since 01 Feb 2016
**********************************************************************/
public class MediaRanker8490 extends BaseClass{
	boolean bProjectCreate;
	ClientList_Page clientListPage;
	String strMsg;
	String strProjectName;	
	@Test
	public void test_MediaRanker8490(){
		
		boolean bIdiomCreation = false;
		ManageIdiom_Page mi=null;
		ArchitectMediaRankerPage arMediaRankerPage=null;
		
		try{
			strProjectName="";
			strMsg="";
			bProjectCreate = false;
			ClientList_Page clientListPage=null;
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strFilters=property.getProperty("MRFilterCriteria8301");	
			String mediaRankerChannels = property.getProperty("mediaRankerItems");
			ArchitectMediaRankerPage mediaRanker;
			
			//****************Test step starts here************************************************
			//Step1: Open URL
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			mediaRanker = new ArchitectMediaRankerPage(driver);
			//Step1: login Launch IDIOM url and enter valid credentials 
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
	        CustomReporter.log("Launch IDIOM url and enter valid credentials ");
	        
	        //Step 2: Select a client
			clientListPage = new ClientList_Page(driver);
	        
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			CustomReporter.log("Selected client ["+strClientName+"]");
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 3: Create/Select project and launch the same 
			if(totalProjects == 0){
				CustomReporter.log("Creating new project as no project for this client");
				System.out.println("Creating new project as no project for this client");
				strProjectName = clientListPage.createNewProject("");
				clientListPage.func_PerformAction("Launch Project");
				bProjectCreate = true;
				
			}else{
				CustomReporter.log("Selecting existing project");
				System.out.println("Selecting existing project");
				int selectProjectId = totalProjects;
				clientListPage.clickProjectById(selectProjectId);
			}
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			String[] channels = mediaRankerChannels.split(",");
			
			for(int i=0; i<channels.length; i++){
				CustomReporter.log("Navigating to ["+channels[i]+"]");
				System.out.println("Navigating to ["+channels[i]+"]");
				
				if(i==0){
					System.out.println("clicking through action");
					pdp.navigateToByActionClass(channels[i].trim());
				}else{
					System.out.println("clicking through link");
					pageHeader.megaMenuLinksClick(channels[i].trim());
				}
				
				if(channels[i].trim().toLowerCase().contains("tv"))
					mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "");
				
				if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
					throw new IDIOMException("Failed to open channel###MediaRanker8535-missingScatterPlot");
				
				if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
					throw new IDIOMException("Failed to open channel###MediaRanker8535-missingScatterPlot");
				
				
				CustomReporter.log("Clicking on calender");
				System.out.println("Clicking on calender");
				arMediaRankerPage.func_ClickOnElement("datepicker");
				Thread.sleep(2000);
				
				if(arMediaRankerPage.func_VerifyVisibilityOfElement("calenderapplybtn")){
					CustomReporter.log("Calender is opened");
					System.out.println("Calender is opened");
					
					CustomReporter.log("Deselcting selected dates");
					System.out.println("Deselcting selected dates");
					
					arMediaRankerPage.func_ClickOnElement("calenderclearbtn");
					
					Thread.sleep(2000);
					
					arMediaRankerPage.func_ClickOnElement("calendercancelbtn");
					
					Thread.sleep(2000);
					
					String dateRangeValue = arMediaRankerPage.func_GetValue("datepickerrange");
					
					System.out.println("==="+dateRangeValue);
					
					if(dateRangeValue.toLowerCase().contains("invalid")){
						CustomReporter.errorLog("Invalid is word is available with date range");
						BaseClass.testCaseStatus=false;
					}else{
						CustomReporter.log("Date range is appearing properly ["+dateRangeValue+"]");
					}
					
				}else{
					CustomReporter.errorLog("Failed to open calender");
					BaseClass.testCaseStatus=false;
				}
			
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
			rm.captureScreenShot("8490", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");					
					Thread.sleep(4000);
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