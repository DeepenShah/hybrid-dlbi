package com.IDIOM.AudienceDefinition;

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
 * <p> <b>Test Case Name:</b> 1933_AudienceDefinition_BottomNavigationBar_Present </p>
 * <p> <b>Objective:</b> Verify that bottom navigation bar is present with audience definition section</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8674.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 09 May 2016
 *
 */
public class AudienceDefinition8674 extends BaseClass {

	@Test
	void verifyBottomNavigationBarOnAudienceDefinition(){
		
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;	
		
		AudienceBuilderPage abPage=null;		
	
		
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
			
			
			//Step3: Select/Create project
			if(clientListPage.totalProject()>0){
				strProjectName = clientListPage.getProjectNameById(1);
			}else{
				strProjectName = clientListPage.createNewProject("");
				bProjectCreated = true;
			}			
			
			//Step4: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			//Step5: Navigate to audience definition
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			
			abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			CustomReporter.log("Navigated to audience definition page");
			
			//Step6: Verify bottom navigation bar is present.
			if(!abPage.isVisible("arrowlinkforsuccessmetricoraudiencedefinition", ""))
				throw new IDIOMException("No arrow link found in bottom navigation bar.###BottomArrowMissing");
			
			CustomReporter.log("Verified arrow (>) link in bottom navigation bar");
			
			boolean bRemoveAll = abPage.isVisible("removealllink", "");
			boolean bNoAttrMsg = abPage.isVisible("noattributeselectedmsg", "");
			
			if(bRemoveAll ^ bNoAttrMsg ){
				if(bRemoveAll){
					CustomReporter.log("Verified 'Remove All' link in bottom navigation bar");
				}else{
					CustomReporter.log("Verified no attribute selected message in bottom navigation bar");
				}
			}else{
				throw new IDIOMException("Failed to find 'Remova All' link or no attribute message in bottom navigation bar.###RemoveallOrMessageMissing");
			}		
			
			//Now clicking on bottom arrow and verifying that landed on profile page
			abPage.arrowLinkClickForSuccessMetricsOrAudienceDefinition();
			CustomReporter.log("Clicked on bottom arrow to navigate to profile page");
			
			Analyse_Profile_Page profPage = new Analyse_Profile_Page(driver);
			
			if(!profPage.func_ElementExist("ProfilePage"))
				throw new IDIOMException("Failed to land on profile page.###ArrowClickNoWorking");
			
			CustomReporter.log("Successfully verified navigation to profile page");
			rm.captureScreenShot("8674-ProfilePage", "pass");
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8674-Exception", "fail");
			}else{
				rm.captureScreenShot("8674-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8674-Exception", "fail");
		}finally{
			try{								
				//Deleting newly created project or Audience				
				if(bProjectCreated){		
					pageHeader.performAction("idiomlogo");;
					rm.webElementSync(pageHeader.personMenu,"visibility");
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");					
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
