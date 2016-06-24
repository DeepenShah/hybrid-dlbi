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
 * <p> <b>Test Case Name:</b> Audience Definition: Query Builder 2.a - Remove first attribute and check and/or logic of next attribute  </p>
 * <p> <b>Objective:</b> To verify 2nd attribute and/or logic once first attribute is removed from the group. That attribute logic should be none.</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8635.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 05 May 2016
 *
 */
public class AudienceDefinition8635 extends BaseClass {

	@Test
	void verifQueryLogicAfterRemovingFirst(){
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
			String strCommonAudience1 = property.getProperty("cmmonAudienceAttribute");
			String strCommonAudience2 = property.getProperty("commonAudienceAttribute2");
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
			
			//Step5: Click on audience definition link
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			CustomReporter.log("Navigated to audience definition page");
			
			
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel, "visibility");
			
			//Step6: Adding attribute
			abPage.selectAttributeOnAudienceDefinition(strCommonAudience1);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			CustomReporter.log("Added " + strCommonAudience1 + " attribute");
			
			abPage.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(2000);
			
			abPage.selectAttributeOnAudienceDefinition(strCommonAudience2);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			CustomReporter.log("Added " + strCommonAudience2 + " attribute");
			
			//Getting count of query item
			int iAfter = abPage.getQueryItemCountInGroup("1");
			
			if(iAfter!=2)
				throw new IDIOMException("Not able to add queries in the group. Count: " +iAfter +" ###FailedToAddQuery");
			
			String firstLogic = abPage.getQueryLogic("1.1");
			String secondLogic = abPage.getQueryLogic("1.2");
			
			CustomReporter.log("Found logic for 1st: " + firstLogic + " and for 2nd: " + secondLogic);
			
			if(!firstLogic.equals(""))
				throw new IDIOMException("Logic is found for first query. Logic " + firstLogic +". ###LogicFoundForFirstAttr");
			
			if(secondLogic.equals(""))
				throw new IDIOMException("Logic is not found for second query. Logic " + secondLogic +". ###LogicNotFoundForSecondAttr");
			
			//Step7: Delete first query
			abPage.deleteQueryItem("1.1");
			Thread.sleep(2000);
			CustomReporter.log("Deleting query");
			
			int iAfterDelete = abPage.getQueryItemCountInGroup("1");
			
			if(iAfterDelete!=(iAfter-1))
				throw new IDIOMException("Failed to delte query. ###FailedToDeleteQuery");

			//Now second query would be first query.
			firstLogic = abPage.getQueryLogic("1.1");
			
			if(!firstLogic.equals(""))
				throw new IDIOMException("Logic is found for first query. Logic " + firstLogic +". ###LogicFoundForFirstAttr");
			
			CustomReporter.log("After deleting first query. Second becomes the first query. And no logic found for first query");
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8635-Exception", "fail");
			}else{
				rm.captureScreenShot("8635-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8635-Exception", "fail");
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
