package com.IDIOM.ClientHomePage;

import java.text.MessageFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Def 2379,2380_Client HomePage_Verify The Application with Lengthy Project Name and Description </p>
 *  <p> <b>Objective:</b>Verify that UI is looking proper when we provide lengthy project name and lengthy description in new project overlay and save the same</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8975.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 02 Jun 2016
 *
 */
public class ClientHomePage8975 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyApplicationWithLengthyProjectDetails(){		
			
		String strProjectName;	
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;
		
		try{
			strProjectName = property.getProperty("projectName8975");
			String strProjectDesc = property.getProperty("projectDesc8975");
			int iMaxCharProjectName = Integer.parseInt(property.getProperty("maxCharForProjectName"));
			int iMaxCharProjectDesc = Integer.parseInt(property.getProperty("maxCharForProjectDesc"));
			//****************Test step starts here************************************************
			
			//Login To Selecting Client
			loginToSelectClient();			
									
			//Step3: Click on New Project			
			String strNewName = clientListPage.createNewProject("");
			Thread.sleep(2000);
			
			//Storing detail for deletion
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strNewName, "");
			bProjectCreated = true;
			
			//Store drop down location
			Point beforeP = driver.findElement(By.xpath(MessageFormat.format(clientListPage.strAudienceDropdownOnHome, strNewName))).getLocation();
			
			//Step4&5: Fill lengthier project name and description
			clientListPage.performActionOnProject("edit", strNewName);
			clientListPage.findAndSaveProjectWindow(false, strNewName);
			clientListPage.func_PerformAction("Project Tab");
			
			//Step6: Save project
			clientListPage.fillAndSaveProject(strProjectName,strProjectDesc);			
			
			CustomReporter.log("Updated name " + strProjectName + " and description " + strProjectDesc);
			
			//Verifying the accepted characters
			String strProjDetails[] = clientListPage.getProjectNameAndDescriptionFromOverlay();
			int iProjectNameLength = strProjDetails[0].length();
			int iProjectDescLength = strProjDetails[1].length();
			if(iProjectNameLength!=iMaxCharProjectName && iProjectDescLength!=iMaxCharProjectDesc)
				throw new IDIOMException("Legth of project name and description are not matching. Project name length " + 
			iProjectNameLength +" and description length " + iProjectDescLength +".###ProjectDetailsMaxCharLengthNotMatching");
			
			CustomReporter.log("Successfully verified max char limit for project name and description. It is " + 
			iMaxCharProjectName +" for name and " + iMaxCharProjectDesc + " for description");
			
			//Go to audience tab
			clientListPage.func_PerformAction("Audience Tab");		
			
			//Step7: Launch project.
			clientListPage.func_PerformAction("Launch Project");
			rm.webElementSync(pageHeader.clientLogo, "visibility");
			CustomReporter.log("Launch the project and navigated to project dashboard");
			
			//Step8: Verify project name and description here.
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			String strNameOnPDP = pdp.getProjectName();
			String strDescOnPDP = pdp.getProjectDescription();
			
			if(!strNameOnPDP.equals(strProjDetails[0]) && !strDescOnPDP.equals(strProjDetails[1]))
				throw new IDIOMException("Project name and description is not matching on dashboard. Name expected " + 
						strProjDetails[0] + " and found " + strNameOnPDP + ". Description expected " + strProjDetails[1] + 
			" and found " + strDescOnPDP +".###ProjectDetailsAreNotMatchingOnDashboard");
			
			CustomReporter.log("Succesfully verified project name " +strNameOnPDP +" and description " + strDescOnPDP +" on project dashboard");
						
			//Step9: Click on idiom logo
			pageHeader.performAction("idiomlogo");
			rm.webElementSync(clientListPage.newProjectBtn, "visibility");
			CustomReporter.log("Clicked on idiom logo and returned back to client home page");
			
			//Checking if any overlay open then close it
			if(clientListPage.isVisible("projectwindow")){
				clientListPage.globalProjectWindow = clientListPage.projectWindow;
				clientListPage.func_PerformAction("Close Project");
			}
			
			//Step10: Verify ui due to length project name
			Point afterP = driver.findElement(By.xpath(MessageFormat.format(clientListPage.strAudienceDropdownOnHome, strProjDetails[0]))).getLocation();
			
			if(afterP.x != beforeP.x)
				throw new IDIOMException("UI is not proper after giving lengthy project name. Earlier " + beforeP.x + 
						" and after " + afterP.x+".###UIDistortedForLengthyProjectName");
			
			CustomReporter.log("UI is proper for lengthy project name");
			
		}catch(Exception e){
			exceptionCode(e);
		}finally{
			try{
				
				//Deleting newly created project or Audience			
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project");					
				}
				
				//Logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(strClassName + ": " + strMsg);
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
		
	public void exceptionCode(Exception ie){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(strClassName + ": " + strMsgAndFileName[0]);
		ie.printStackTrace(System.out);
		
		String strTestCaseId = strClassName.replaceAll("\\D+","");		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot(strTestCaseId+"-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot(strTestCaseId+"-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
