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
 * <p> <b>Test Case Name:</b>1977 - Cancel edit Sub group name </p>
 * <p> <b>Objective:</b> Verify edit functionality through pencil icon and click cancel button for sub group. </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8703.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 11 May 2016
 *
 */
public class AudienceDefinition8703 extends BaseClass {

	AudienceBuilderPage abPage=null;
		
	@Test
	void verifyCancelEditSubGroupName(){
		
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		
		String strAudienceName="";
		boolean bAudienceCreated=false;
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strAudience = property.getProperty("commonAudienceAttribute4");
			//****************Test step starts here************************************************
						
			strMsg = "Launched Browser and Enter URL";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step1&2: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step3: Select client
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
			
			
			//Step4&: Select/Create project
			if(clientListPage.totalProject()>0){
				strProjectName = clientListPage.getProjectNameById(1);
			}else{
				strProjectName = clientListPage.createNewProject("");
				bProjectCreated = true;
			}
			
			//Create new audience if existing project is used
			if(!bProjectCreated){
				clientListPage.performActionOnProject("edit", strProjectName);
				clientListPage.findAndSaveProjectWindow(false, strProjectName);
				
				clientListPage.func_PerformAction("Audience Tab");
				
				strAudienceName = clientListPage.createNewAudience("");
				clientListPage.func_PerformAction("Close Project");
				Thread.sleep(2000);
				bAudienceCreated= true;
			}
			
			//Step6: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			//Step7: Navigate to success metric
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			Thread.sleep(2000);
			CustomReporter.log("Navigated to success metric page");
			
			//Navigating to Audience definition page by clicking bottom arrow
			abPage.arrowLinkClickForSuccessMetricsOrAudienceDefinition();
			rm.webElementSync("listsize", "1", abPage.groupList);
			
			CustomReporter.log("Moved to audience definition page");
			
			//Step8: Adding query to first group
			abPage.selectAttributeOnAudienceDefinition(strAudience);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			
			//Verifying query is added or not
			int iQueryCount = abPage.getQueryItemCountInGroup("1");
			
			if(iQueryCount < 1)
				throw new IDIOMException("Failed to add query " + strAudience + " in the group.###FaileToAddQuer");
			
			CustomReporter.log("Added " + strAudience + " query in the group");
			
			//Step9: Adding subgroup
			abPage.addSubGroup("1");
			Thread.sleep(2000);
			
			//Verifying added subgroup
			int iSubGroupCount = abPage.getSubGroupCount("1");
			
			if(iSubGroupCount < 1)
				throw new IDIOMException("Failed to add subgroup. Count is " +iSubGroupCount + ".###FailedToAddSubGroup");
			
			CustomReporter.log("Subgroup is added in the first group");
			
			//Now getting name of subgroup
			String strSubGroupNameBefore = abPage.getGroupName("1.1");
			
			CustomReporter.log("Subgroup name is " + strSubGroupNameBefore);
			
			//Step10: Click to edit the group
			abPage.clickToEditGroupName("1.1");
			
			//Check different components visibility
			if(!abPage.isVisible("editgroupnameinput") && !abPage.isVisible("editgroupnameok") && !abPage.isVisible("editgroupnamecancel"))
				throw new IDIOMException("Not possible to edit group name. ###EditModeNotVisibleForGroupName");
			
			CustomReporter.log("Text fields and buttons, Ok & Cancel, are available to edit the name");
			
			//Step11: Fill the name and click cancel
			abPage.fillGroupName(getClass().getSimpleName());
			CustomReporter.log("Fill the new name " + getClass().getSimpleName());
			rm.captureScreenShot("8703-FillGroupName", "pass");
			
			//Click on cancel
			abPage.performActionOnEditGroupName(false);
			CustomReporter.log("Clicked on cancel");
			
			//Check visibility of edit mode
			if(abPage.isVisible("editgroupnameinput") && abPage.isVisible("editgroupnameok") && abPage.isVisible("editgroupnamecancel"))
				throw new IDIOMException("Edit mode is still visible after clicking Cancel. ###EditModeStillVisibleForGroupName");
			
			//Get the group name again and verify
			String strSubGroupNameAfter = abPage.getGroupName("1.1");
			
			if(!strSubGroupNameBefore.equals(strSubGroupNameAfter))
				throw new IDIOMException("Failed cancel edit name. Found " +strSubGroupNameAfter +
						" and expected " + strSubGroupNameBefore + ".###FailedToCancelEditGroupName");
				
			CustomReporter.log("Successfully canceled edit group name");
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8703-Exception", "fail");
			}else{
				rm.captureScreenShot("8703-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8703-Exception", "fail");
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
					CustomReporter.log("Deleted audience " + strAudienceName);
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
