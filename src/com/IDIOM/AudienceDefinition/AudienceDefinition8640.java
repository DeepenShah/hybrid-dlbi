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
 * <p> <b>Test Case Name:</b> Audience Definition: Query Builder - Verify dragging and dropping of the group</p>
 * <p> <b>Objective:</b>To verify group should be successfully added as subgroup in another group or changing order of the group using drag and drop.</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8640.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 13-May-2016
 *
 */
public class AudienceDefinition8640 extends BaseClass {
		
	@Test
	public void verifyDraggingAndDroppingGroup(){
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
			String strAudience = property.getProperty("commonAudienceAttribute5");
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
			rm.webElementSync(abPage.addNewGroupLink, "visibility");
			
			//Step6: Adding groups and subgroup
			abPage.addNewGroup();
			Thread.sleep(1000);
			
			abPage.addNewGroup();
			Thread.sleep(1000);
			
			int iBeforeGroupCount = abPage.getGroupCount();
			String strGroup3Name = abPage.getGroupName("3");
			
			if(iBeforeGroupCount != 3)
				throw new IDIOMException("Not able to add groups. Count is " +iBeforeGroupCount +".###FailedToAddGroup");
			
			CustomReporter.log("Two more groups are created");
			
			//Adding sub group to 2nd group.
			abPage.addSubGroup("2");
			Thread.sleep(1000);			
			
			int iBeforeSubGroupCountInGroup2 = abPage.getSubGroupCount("2");			
			
			if(iBeforeSubGroupCountInGroup2 != 1)
				throw new IDIOMException("Not able to add subgroups in group 2. Count " + iBeforeSubGroupCountInGroup2 +".###FailedToAddSubgroup");
			
			//Adding query to 3rd group
			abPage.selectGroup("3");
			abPage.selectAttributeOnAudienceDefinition(strAudience);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			
			//Verifying add queries
			int iBeforeQueryCount = abPage.getQueryItemCountInGroup("3");
			
			if(iBeforeQueryCount != 1)
				throw new IDIOMException("Not able to add query in group3. Query " + strAudience +" Count " + iBeforeQueryCount +".###FailedToAddQuery");
			
			CustomReporter.log("Added query " + strAudience + " to group3");
			
			//Dragging 3rd group inside 2nd group.
			if(!abPage.performDragAndDrop("3", "2", true))
				throw new IDIOMException("Not able perform drag and drop.###FailedToDragAndDrop");
			
			CustomReporter.log("Performed drag and drop from group to as subgroup in other group, now verifying result");
			
			//Verifying result
			int iAfterGroupCount = abPage.getGroupCount();
			int iAfterSubGroupCountInGroup2 = abPage.getSubGroupCount("2");
			
			if((iAfterGroupCount != (iBeforeGroupCount-1)) && (iAfterSubGroupCountInGroup2 != (iBeforeSubGroupCountInGroup2+1)))
				throw new IDIOMException("Draggging and dropping was not successfull. Group2: Expected - " + 
						(iBeforeSubGroupCountInGroup2+1) + " Actual - " + iAfterSubGroupCountInGroup2 +
									". Total Group: Expected - " + (iBeforeGroupCount-1) + " Actual - " +
									iAfterGroupCount +".###DragNDropFailedGroupToSubgroup");
			
			CustomReporter.log("Drag and drop was successfull. Group 3 is moved as subgroup of group 2." +
					"Group2: Earlier " + iBeforeSubGroupCountInGroup2 + " and now " + iAfterSubGroupCountInGroup2 +
					" Total Group: Earlier " + iBeforeGroupCount + " and now " + iAfterGroupCount);			
			
			
			//Matching query from group3 which is now subgroup
			abPage.selectGroupByName(strGroup3Name);
			int iAfterQueryCount = abPage.getTotalQueriesForSelectedGroup();
			
			if(iBeforeQueryCount != iAfterQueryCount)
				throw new IDIOMException("Dragging group with query was not successful."+
			" Query count before " + iBeforeQueryCount + " and after " + iAfterQueryCount +".###Fail-DragingWithQuery");
			
			CustomReporter.log("Successfully dragged group with query. Query count earlier " + iBeforeQueryCount + " now " + iAfterQueryCount);
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8640-Exception", "fail");
			}else{
				rm.captureScreenShot("8640-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8640-Exception", "fail");
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