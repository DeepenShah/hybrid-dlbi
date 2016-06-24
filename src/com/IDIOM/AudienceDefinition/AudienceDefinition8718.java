package com.IDIOM.AudienceDefinition;

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
 * <p> <b>Test Case Name:</b> 2085_Audience Definition - Verify selected attribute value inside query builder  </p>
 * <p> <b>Objective:</b> To verify correctness of selected attribute value from filter.</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8718.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 06 May 2016
 *
 */
public class AudienceDefinition8718 extends BaseClass {

	@Test
	void verifyAbilityToAddDifferentValuedBehaviors(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		boolean bAudienceCreated = false;
		String strAudienceName = "";
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strAudienceDef1 = property.getProperty("audienceDef1_8718");
			String strAudienceDef2 = property.getProperty("audienceDef2_8718");
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
			
			//Step4: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			//Step5: Navigate to audience definition
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			CustomReporter.log("Navigated to audience definition page");
			
			
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			
			//Step6: Select any future behavior
			abPage.selectAttributeOnAudienceDefinition(strAudienceDef1);
			
			//Step7: add attribute to 1st group
			abPage.performAction("addattribute");
			
			//Verifying query is added to the group
			int iCount = abPage.getQueryItemCountInGroup("1");
			
			if(iCount!=1)
				throw new IDIOMException("Not able to add future behavior to 1st group. Count is " + iCount +".###FailedToAddAttribute");
			
			CustomReporter.log("Added attribute to first group. " + strAudienceDef1);
			
			//Step8: Create new group
			abPage.addNewGroup();
			
			//Verify group is added.
			int iGroupCount = abPage.getGroupCount();
			
			if(iGroupCount!=2)
				throw new IDIOMException("Failed to add new group. Group count should be 2 and found "+ iGroupCount + ".###FailedToAddGroup");
			
			CustomReporter.log("Added new group");
			
			//Select 2nd group
			abPage.selectGroup("2");
			
			//Add query to 2nd group
			abPage.selectAttributeOnAudienceDefinition(strAudienceDef2);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			
			//Verifying query is added to group
			iCount = abPage.getQueryItemCountInGroup("2");
			
			if(iCount!=1)
				throw new IDIOMException("Not able to add future behavior to 2nd group. Count is " + iCount +".###FailedToAddAttributeTo2ndGroup");
			
			CustomReporter.log("Added query to 2nd group");
			
			//Get query value of both the group
			String strQuery1 = abPage.getQueryWithLogic("1.1");
			String strQuery2 = abPage.getQueryWithLogic("2.1");
			
			System.out.println("Found: " + strQuery1 + " and " + strQuery2);
			
			if(strQuery1.equalsIgnoreCase(strQuery2))
				throw new IDIOMException("Not able to add same behaviors with different value. Found: " + strQuery1 + " and " +strQuery2 +
						".###Failed");
			
			CustomReporter.log("Successfully added same behavior with different value. Quer1: " + strQuery1 + " Query2: " + strQuery2);
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8718-Exception", "fail");
			}else{
				rm.captureScreenShot("8718-"+strMsgAndFileName[1], "fail");	
			}
				
				
			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8718-Exception", "fail");
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
				}		
				
				//Step8: Logout
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
