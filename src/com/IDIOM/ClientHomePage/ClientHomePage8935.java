package com.IDIOM.ClientHomePage;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.ReusableMethods;

/** <p> <b>Test Case Name:</b>ClientHomepage_Verify the project is visible to the user  with which its created [DTASIDIOM-2290]</p>
 *  <p> <b>Objective:</b>Verify the project is visible to the user with which its created </p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8935.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 03 Jun 2016
 *
 */
public class ClientHomePage8935 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyProjectIsUserSpecific(){		
			
		String strProjectName="";				
		String strMsg="";
		String strDeletionDetails="";		
		
		boolean bProjectCreated = false;
		WebDriver newDriver=null;
		
		try{
			String strGenUser = property.getProperty("GeneralUserEmail");
			String strGenUserPass = property.getProperty("GeneralUserPassword");
			//****************Test step starts here************************************************
			
			//Login To Selecting Client
			loginToSelectClient();			
			
			//Step3&4: Select/Create project			
			strProjectName = clientListPage.createNewProject("");
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			bProjectCreated = true;
			
			//Step5: Create new audience
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			String strAudi1 = clientListPage.createNewAudience("");
			String strAudi2 = clientListPage.createNewAudience("");
			
			CustomReporter.log("Created two new audience " +strAudi1 + " and " + strAudi2);
			//Launching project
			CustomReporter.log("Launching project");
			clientListPage.func_PerformAction("Launch Project");
			
			rm.webElementSync(pageHeader.projectName, "visibility");
			CustomReporter.log("Landed on project dashboard");
			
			
			//Step6&7: Opening new browser and login with different user
			newDriver = launchBrowser(browserName);
			CustomReporter.log("Launched new window to login with different user");
			
			//Step8: Selecting client
			Login_Page newLn = new Login_Page(newDriver);
			newLn.func_LoginToIDIOM(strGenUser, strGenUserPass);		    
			    
			CustomReporter.log("Launch IDIOM url and enter valid credentials, "+ strGenUser+", in new window");
	        
	        //Step 2: Select a client
			ClientList_Page newclPage = new ClientList_Page(newDriver);
	        
			Thread.sleep(5000);
			ReusableMethods newRm = new ReusableMethods(newDriver);
			newRm.webElementSync("pageload");
			newRm.webElementSync("jqueryload");
			
			if(newclPage.selectClient(property.getProperty("clientName"))){
				strMsg = "Selected '"+property.getProperty("clientName")+"' client successfully.";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
				
				Thread.sleep(5000);
				newRm.webElementSync("pageload");
				newRm.webElementSync("jqueryload");				
			}else{
				CustomReporter.log("Selected the client");
			}			
			
			//Step9: Verifying newly created project is visible to this user			
			if(newclPage.verfiyProjectIsExist(strProjectName)){
				BaseClass.testCaseStatus=false;
				CustomReporter.errorLog("Project "+ strProjectName+" created in super admin user found in " + strGenUser + " user");
				rm.captureScreenShotOnWithDifferentDriver("8935-ProjectFoundOnDiffUser", "fail", newDriver);
			}else{
				CustomReporter.log("Successfully verified, Project not found for different user");
			}					
			
		}catch(Exception e){
			exceptionCode(e);
		}finally{
			if(newDriver != null)
				newDriver.quit();
			
			try{
				
				//Deleting newly created project or Audience			
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project " + strProjectName);					
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
