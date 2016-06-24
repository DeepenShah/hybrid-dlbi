package com.IDIOM.AudienceDefinition;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> Audience Definition - DTASIDIOM-2002: Queries should be saved properly on navigating to another page. </p>
 * <p> <b>Objective:</b> To verify save functionality on query builder. If user navigate to another page either it should ask to save query (dirty check) or it should save automatically. </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8680.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 09 May 2016
 *
 */
public class AudienceDefinition8680 extends BaseClass {

	@Test
	void verifyAutoSaveByNavigatingToAnotherPage(){
		
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;	
		boolean bAudienceCreated = false;
		String strAudienceName = "";
		
		AudienceBuilderPage abPage=null;		
	
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strAudienceDef1 = property.getProperty("commonAudienceAttribute3");
			String strAudienceDef2 = property.getProperty("cmmonAudienceAttribute");
			//****************Test step starts here************************************************
			
			strMsg = "Launched Browser and Enter URL";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step2: Select client
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
			
			
			//Step3: Select/Create project
			if(clientListPage.totalProject()>0){
				strProjectName = clientListPage.getProjectNameById(1);
			}else{
				strProjectName = clientListPage.createNewProject("");
				bProjectCreated = true;
			}
			
			//Create new audience
			if(!bProjectCreated){
				clientListPage.performActionOnProject("edit", strProjectName);
				clientListPage.findAndSaveProjectWindow(false, strProjectName);
				
				clientListPage.func_PerformAction("Audience Tab");
				
				strAudienceName = clientListPage.createNewAudience("");
				clientListPage.func_PerformAction("Close Project");
				Thread.sleep(2000);
				bAudienceCreated= true;
			}
			
			//Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			//Step4: Navigate to audience definition
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			
			abPage = new AudienceBuilderPage(driver);
			
			//Waiting for Audience Definition page to load
			rm.webElementSync(abPage.addNewGroupLink,"visibility");
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			
			CustomReporter.log("Navigated to audience definition page");
			
			//Step5: Add some queries to first group
			abPage.selectAttributeOnAudienceDefinition(strAudienceDef1);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			
			abPage.selectAttributeOnAudienceDefinition(strAudienceDef2);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			
			abPage.goToFirstLevelForMetricOrAttribute();
			
			//Get the data
			int iBefore=abPage.getTotalQueryItems();
			
			String strBeforeQuery1=abPage.getQueryWithLogic("1.1");
			String strBeforeQuery2=abPage.getQueryWithLogic("1.2");
			
			if(iBefore!=2)
				throw new IDIOMException("Not able to add queries in the group. Count found " + iBefore + ".###FailedToAddQuery");
			
			CustomReporter.log("Added two queries in the first group");
			Thread.sleep(2000);
			
			//Step6: Now clicking on bottom arrow and verifying that landed on profile page
			abPage.arrowLinkClickForSuccessMetricsOrAudienceDefinition();
			CustomReporter.log("Clicked on bottom arrow to navigate to profile page");
			
			Analyse_Profile_Page profPage = new Analyse_Profile_Page(driver);
			
			rm.webElementSync("idiomspinningcomplete");
			if(!rm.webElementSync(profPage.homeOwnerGraph, "visibility"))
				throw new IDIOMException("Failed to land on profile page.###NotAbleToNavigateToProfilePage");
			
			CustomReporter.log("Navigated to profile page. Now going back to audience definition page");
			
			//Step7: Click on 'Audience Definition' from mega menu
			pageHeader.megaMenuLinksClick(property.getProperty("audienceDefinition"));
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			
			//Get the data and verify
			int iAfter=abPage.getTotalQueryItems();
			
			String strAfterQuery1=abPage.getQueryWithLogic("1.1");
			String strAfterQuery2=abPage.getQueryWithLogic("1.2");
			
			if(iAfter != iBefore)
				throw new IDIOMException("Queries count is not matching. Before: "+iBefore +" and After:"+iAfter + ".###QueryCountNotMatching" );
			
			if(!strBeforeQuery1.equalsIgnoreCase(strAfterQuery1) && !strBeforeQuery2.equalsIgnoreCase(strAfterQuery2))
				throw new IDIOMException("Queries values are not matching. Query1: Before - " +
						strBeforeQuery1 + " and After - " + strAfterQuery2 + ". Query2: Before - " + strBeforeQuery2 + 
						" and After - " + strAfterQuery2 + ".###QueriesValuesAreNotMatching");
				
			CustomReporter.log("Successfully verified that queries are saved after navigating to other page. Query1: " + strAfterQuery1 +
					" and Quer2: " + strAfterQuery2);
			
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8680-Exception", "fail");
			}else{
				rm.captureScreenShot("8680-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8680-Exception", "fail");
		}finally{
			try{								
				//Deleting newly created project or Audience								
				pageHeader.performAction("idiomlogo");;
				rm.webElementSync(pageHeader.personMenu,"visibility");
				if(bProjectCreated){		
				
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");					
				}
				
				if(bAudienceCreated && !bProjectCreated){
					clientListPage.performActionOnProject("edit", strProjectName);
					clientListPage.findAndSaveProjectWindow(false, strProjectName);
					
					clientListPage.func_PerformAction("Audience Tab");
					clientListPage.performActionOnAudience(strAudienceName, "delete");
					
					CustomReporter.log("Deleted the audience " + strAudienceName);
				}						
				
				//Logout
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
