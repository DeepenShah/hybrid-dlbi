package com.IDIOM.AudienceDefinition;

import java.text.MessageFormat;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> Def 2327_Audience Definition_Verify project name and audience drop down are proper in header  </p>
 * <p> <b>Objective:</b> Verify that the project name and audience drop down is coming proper in header</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8956.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 03 May 2016
 *
 */
public class AudienceDefinition8956 extends BaseClass {

	@Test
	void verifyProjectTitleAndAudienceDropDown(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		
		String strAudienceName="";
		String strEditProjectName="";
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");			
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
			
			
			//Step3&4: Create project
			if(clientListPage.totalProject()>0)
				strEditProjectName = clientListPage.getProjectNameById(1);				
			
			strProjectName = clientListPage.createNewProject("");
			bProjectCreated = true;
			
			
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			//Navigate to audience tab
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. ###8956-AudienceTabNotFound");
			
			//Step5: Create audience and click edit
			strAudienceName=clientListPage.createNewAudience("");			
			clientListPage.performActionOnAudience(strAudienceName, "edit");
			Thread.sleep(3000);
			CustomReporter.log("Clicked 'Edit' for newly create audience");
			
			//Step6: Verifying header
			if(!pageHeader.isVisible("projectname"))
				throw new IDIOMException("Project name is not coming on header###NoProjectTitle");
			
			if(!pageHeader.getProjectName().equalsIgnoreCase(strProjectName))
				throw new IDIOMException("Project name is not matching. Exp: " + strProjectName+", Actual: " + pageHeader.getProjectName()+".###NoProjectTitle");
			
			if(!pageHeader.isVisible("audiencedropdown"))
				throw new IDIOMException("Project name is not coming on header###NoProjectTitle");
			
			CustomReporter.log("Verified project name and audience drop down on header");
			
			//Step7: Click on IDIOM logo
			pageHeader.performAction("idiomlogo");
			//rm.webElementSync(clientListPage.clientListDropDown, "visibility");
			Thread.sleep(5000);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(clientListPage.strProjectRowContains, strProjectName));
			
			CustomReporter.log("Clicked on idiom logo to go back to project home page");
			
			//Step8: Launch the project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project again");
			
			
			//Step9: Navigate to Success Metric
			ProjectDashboardPage prjDashBoard = new ProjectDashboardPage(driver);
			prjDashBoard.navigateTo(property.getProperty("audienceDefinition"));
			
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			CustomReporter.log("Navigated to Audience Definition page");
			
			//Step10: Verifying header
			if(!pageHeader.isVisible("projectname"))
				throw new IDIOMException("Project name is not coming on header###NoProjectTitle");
			
			if(!pageHeader.getProjectName().equalsIgnoreCase(strProjectName))
				throw new IDIOMException("Project name is not matching. Exp: " + strProjectName+", Actual: " + pageHeader.getProjectName()+".###NoProjectTitle");
			
			if(!pageHeader.isVisible("audiencedropdown"))
				throw new IDIOMException("Project name is not coming on header###NoProjectTitle");
			
			CustomReporter.log("Verified project name and audience drop down on header");
			
			//Step11: Click on IDIOM logo
			pageHeader.performAction("idiomlogo");
			Thread.sleep(5000);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(clientListPage.strProjectRowContains, strEditProjectName));
			
			CustomReporter.log("Clicked on idiom logo to go back to project home page");
			
			//Step12,13,14: Launch any other project
			clientListPage.launchProject(strEditProjectName);
			CustomReporter.log("Launching another project, "+ strEditProjectName);
			
			prjDashBoard.navigateTo(property.getProperty("successMetrics"));
			rm.webElementSync(abPage.noSuccessMetricText, "visibility");
			CustomReporter.log("Navigated to Success Metrics page");
			
			//Step15: Verifying header
			if(!pageHeader.isVisible("projectname"))
				throw new IDIOMException("Project name is not coming on header###NoProjectTitle");
			
			if(!pageHeader.getProjectName().equalsIgnoreCase(strEditProjectName))
				throw new IDIOMException("Project name is not matching. Exp: " + strEditProjectName+", Actual: " + pageHeader.getProjectName()+".###NoProjectTitle");
			
			if(!pageHeader.isVisible("audiencedropdown"))
				throw new IDIOMException("Project name is not coming on header###NoProjectTitle");
			
			CustomReporter.log("Verified project name and audience drop down on header");
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8956-Exception", "fail");
			}else{
				rm.captureScreenShot("8956-"+strMsgAndFileName[1], "fail");	
			}
				
				
			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8956-Exception", "fail");
		}finally{
			try{
				
				//Deleting newly created project
				if(bProjectCreated){
					pageHeader.performAction("idiomlogo");;
					rm.webElementSync(pageHeader.personMenu,"visibility");
										
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");
				}
				
				//Step16: Logout
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
