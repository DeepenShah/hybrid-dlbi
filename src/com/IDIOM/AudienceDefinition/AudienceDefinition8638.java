package com.IDIOM.AudienceDefinition;

import java.util.concurrent.TimeUnit;

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
 * <p> <b>Test Case Name:</b> Audience Definition: Query Builder - Change and/or logic of attribute, group and subgroup  </p>
 * <p> <b>Objective:</b> Check the ability to change and/or logic for attribute, group or subgroup</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8638.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 06 May 2016
 *
 */
public class AudienceDefinition8638 extends BaseClass {

	@Test
	void verifChangeLogicForGroupAndSubGroup(){
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
			String strDefaultGroupLogic = property.getProperty("defaultGroupLogic");
			String strDefaultQueryLogic = property.getProperty("defaultQueryOrSubGroupLogic");
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
			rm.webElementSync("pageload");
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			
			//Step6: Add new group and sub group inside.
			abPage.addNewGroup();
			Thread.sleep(2000);
			
			int iGroupCount = abPage.getGroupCount();
			if(iGroupCount!=2)
				throw new IDIOMException("Not able to add the group.###FailedToAddGroup");
			
			CustomReporter.log("Added new group");
									
			abPage.addSubGroup("2");
			Thread.sleep(2000);
			
			abPage.addSubGroup("2");
			Thread.sleep(2000);
			
			CustomReporter.log("Added two subgroups in 2nd group");
			
			//Selecting 2nd Group
			abPage.selectGroup("2");
			
			//Step7&8: Adding query to second group
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
			int iAfter = abPage.getQueryItemCountInGroup("2");
			
			if(iAfter!=2)
				throw new IDIOMException("Not able to add queries in the group. Count: " +iAfter +" ###FailedToAddQuery");
			
			CustomReporter.log("Added two attributes in 2nd Group");
			
			String strActualSubGroup1Logic = abPage.getGroupLogic("2.1");
			String strActualSubGroup2Logic = abPage.getGroupLogic("2.2");
			
			if(!(strActualSubGroup1Logic.equalsIgnoreCase(strActualSubGroup2Logic) && strActualSubGroup1Logic.equalsIgnoreCase(strDefaultQueryLogic)))
					throw new IDIOMException("Both the subgroup logic is either not same or logic is not "+ strDefaultQueryLogic+". Found: " + strActualSubGroup1Logic + ".###DefaultLogicNotSame");
			
			CustomReporter.log("Both the subgroup logic is same and it is " + strDefaultQueryLogic);
			
			//Step9: Change group logic by clicking on query
			abPage.clickToChangeQueryLogic("2.2");
			Thread.sleep(2000);
			
			CustomReporter.log("Clicked to change query logic");
			
			strActualSubGroup1Logic = abPage.getGroupLogic("2.1");
			strActualSubGroup2Logic = abPage.getGroupLogic("2.2");
			
			String strExpLogic="";
			if(strDefaultQueryLogic.equalsIgnoreCase("or")){
				strExpLogic= "and";
			}else{
				strExpLogic= "or";
			}
			
			if(!(strActualSubGroup1Logic.equalsIgnoreCase(strActualSubGroup2Logic) && strActualSubGroup1Logic.equalsIgnoreCase(strExpLogic)))
				throw new IDIOMException("Both the subgroup logic is either not same or logic is not " + strExpLogic+". Found: " + strActualSubGroup1Logic + ".###FailedToChangeSubgroupLogic");
			
			CustomReporter.log("Successfully changed subgroup logic to " + strActualSubGroup1Logic);
			
			//Step10: change back to original
			abPage.clickToChangeQueryLogic("2.2");
			Thread.sleep(2000);
			
			strActualSubGroup1Logic = abPage.getGroupLogic("2.1");
			strActualSubGroup2Logic = abPage.getGroupLogic("2.2");
			
			if(!(strActualSubGroup1Logic.equalsIgnoreCase(strActualSubGroup2Logic) && strActualSubGroup1Logic.equalsIgnoreCase(strDefaultQueryLogic)))
				throw new IDIOMException("Both the subgroup logic is either not same or logic is not " + strDefaultQueryLogic+". Found: " + strActualSubGroup1Logic + ".###FailedToChangeSubgroupLogic");
			
			CustomReporter.log("Again changed subgroup logic back to default-" + strDefaultQueryLogic);
			
			//Fetching Group logic
			String strActualGroupLogic = abPage.getGroupLogic("2");
			
			if(!strActualGroupLogic.equalsIgnoreCase(strDefaultGroupLogic))
				throw new IDIOMException("Default group logic is not " + strDefaultGroupLogic +" and found " + strActualGroupLogic + ".###DefaultGroupLogicNotVerified");
					
			CustomReporter.log("Verified default group logic " + strActualGroupLogic);			
			
			//Step11: Change group logic
			abPage.clickToChangeGroupLogic("2");
			Thread.sleep(2000);
			
			strActualGroupLogic = abPage.getGroupLogic("2");
			
			if(strDefaultGroupLogic.equalsIgnoreCase("or")){
				strExpLogic= "and";
			}else{
				strExpLogic= "or";
			}
			
			if(!strActualGroupLogic.equalsIgnoreCase(strExpLogic))
				throw new IDIOMException("Not able to change group logic. Found: " + strActualGroupLogic + " Exp: "+ strExpLogic+".###FailedToChangeGroupLogic");
			
			CustomReporter.log("Successfully verified group logic change. It is now " + strActualGroupLogic);
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8638-Exception", "fail");
			}else{
				rm.captureScreenShot("8638-"+strMsgAndFileName[1], "fail");	
			}
				
				
			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8638-Exception", "fail");
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
