package com.IDIOM.ProjectDashBoard;

import java.text.MessageFormat;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Objective</b> Verify Launching Project after changing Clients.</p>
<p><b>Test Case Name</b> 2622_Dashboard - Verify Launching Project after changing Clients</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8915.aspx</p>
<p><b>Module</b> Project Dashboard</p>
@author Shailesh Kava
@since 16 June 2016
**********************************************************************/
public class ProjectDashBoard8915 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;
	String anotherProjectName;
	String newProject;
	
	@Test
	public void verifyLaunchProjectAfterChangeClient() throws Exception{
	//****************Variables declaration and Initialization****************************
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8915_"+rm.getCurrentDateTime();
		String selectClient = property.getProperty("8917selectclient");
		String strAnotherProjectName = selectClient+"_"+rm.getCurrentDateTime();
	//****************Test step starts here************************************************	
	try{
		//Step1: Launch browser
		loginToSelectClient();
		CustomReporter.log("Creating new project as no project for this client");
		System.out.println("Creating new project as no project for this client");
		
		Thread.sleep(2000);
		clientListPage.createNewProject(strProjectName);
		
		//Getting project and client id to delete through REST service
		strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
		clientListPage.launchProject(strProjectName);
		bProjectCreate = true;
		
		ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
		rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, pathingLink.trim()));
		Thread.sleep(2000);
		
		String firstProjectName = pageHeader.projectName.getText().trim();
		
		//clicking on IDIOM logo to go back to client list page
		pageHeader.idiomLogo.click();
		Thread.sleep(5000);
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		
		//Changing the client
		if(!clientListPage.selectClient(selectClient))
			throw new IDIOMException("Failed to change client ["+selectClient+"] ###8915_failedToChangeClient");
		
		CustomReporter.log("Client ["+selectClient+"] is selected successfully.");
		
		Thread.sleep(5000);
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		//Creating project for this client
		System.out.println("another project name is ["+strAnotherProjectName+"]");
		clientListPage.createNewProject(strAnotherProjectName);
		
		newProject = clientListPage.getAudienceProjectClientId(strAnotherProjectName, "");
		clientListPage.launchProject(strAnotherProjectName);
		
		rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, pathingLink.trim()));
		Thread.sleep(2000);
		anotherProjectName = pageHeader.projectName.getText().trim();
		
		if(firstProjectName.equalsIgnoreCase(anotherProjectName))
			throw new IDIOMException("Previously created project name is appearing in header it should appear ["+strAnotherProjectName+"] ###8915_previousProjectNameIsAppearing");
		
		CustomReporter.log("Unique project names are appearing in header, project name for 1st project ["+firstProjectName+"] project name for 2nd project ["+anotherProjectName+"]");
		
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
		rm.captureScreenShot("8915", "fail");
	}finally{
		try{
			
			if(bProjectCreate){
				rm.deleteProjectOrAudience(strDetails, true);
				Thread.sleep(1000);
				rm.deleteProjectOrAudience(newProject, true);
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
}}
