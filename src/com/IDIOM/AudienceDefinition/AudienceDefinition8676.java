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
 * <p> <b>Test Case Name:</b> Audience Definition: Query Builder - Verify dragging and dropping of subgroup</p>
 * <p> <b>Objective:</b>To verify subgroup should be successfully added as subgroup in another group or moved as a group.</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8676.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 12-May-2016
 *
 */
public class AudienceDefinition8676 extends BaseClass {
		
	@Test
	public void verifyDraggingAndDroppingOfSubgroup(){
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
			
			//Step6: Adding groups and subgroup
			abPage.addNewGroup();
			Thread.sleep(1000);
			
			abPage.addNewGroup();
			Thread.sleep(1000);
			
			int iBeforeGroupCount = abPage.getGroupCount();
			
			if(iBeforeGroupCount != 3)
				throw new IDIOMException("Not able to add groups. Count is " +iBeforeGroupCount +".###FailedToAddGroup");
			
			CustomReporter.log("Two more groups are created");
			
			//Adding sub group to 2nd group.
			abPage.addSubGroup("2");
			Thread.sleep(1000);
			
			abPage.addSubGroup("2");
			Thread.sleep(1000);
			
			abPage.addSubGroup("2");
			Thread.sleep(1000);
			
			int iBeforeSubGroupCountInGroup2 = abPage.getSubGroupCount("2");
			int iBeforeSubGroupCountInGroup3 = abPage.getSubGroupCount("3");
			
			if(iBeforeSubGroupCountInGroup2 != 3)
				throw new IDIOMException("Not able to add subgroups in group 2. Count " + iBeforeSubGroupCountInGroup2 +".###FailedToAddSubgroup");
			
			if(iBeforeSubGroupCountInGroup3 != 0)
				throw new IDIOMException("Somehow subgroup found in newly created group.###SubgroupFoundInNewGroup");
			
			//Dragging 1st subgroup to outside.
			if(!abPage.performDragAndDrop("2.1", "outside", true))
				throw new IDIOMException("Not able perform drag and drop.###FailedToDragAndDrop");
			
			CustomReporter.log("Performed drag and drop from group to outside, now verifying result");
			
			//Verifying result
			int iAfterGroupCount = abPage.getGroupCount();
			int iAfterSubGroupCountInGroup2 = abPage.getSubGroupCount("2");
			
			if((iAfterGroupCount != (iBeforeGroupCount+1)) && (iAfterSubGroupCountInGroup2 != (iBeforeSubGroupCountInGroup2-1)))
				throw new IDIOMException("Draggging and dropping was not successfull. Group2: Expected - " + 
						(iBeforeSubGroupCountInGroup2-1) + " Actual - " + iAfterSubGroupCountInGroup2 +
									". Total Group: Expected - " + (iBeforeGroupCount+1) + " Actual - " +
									iAfterGroupCount +".###DragNDropFailedOutsideGroup");
			
			CustomReporter.log("Drag and drop was successfully. Subgroup from group2 is moved to outside." +
					"Group2: Earlier " + iBeforeSubGroupCountInGroup2 + " and now " + iAfterSubGroupCountInGroup2 +
					" Total Group: Earlier " + iBeforeGroupCount + " and now " + iAfterGroupCount);			
			
			
			//Dragging 1st subgroup (actually 2nd) to 3rd group
			if(!abPage.performDragAndDrop("2.1", "3", true))
				throw new IDIOMException("Not able perform drag and drop.###FailedToDragAndDrop");
			
			CustomReporter.log("Performed drag and drop from group to group, now verifying result");
			
			//Verifying result
			iBeforeSubGroupCountInGroup2 = iAfterSubGroupCountInGroup2;
			iAfterSubGroupCountInGroup2 = abPage.getSubGroupCount("2");
			int iAfterSubGroupCountInGroup3 = abPage.getSubGroupCount("3");
			
			
			if((iAfterSubGroupCountInGroup2 != (iBeforeSubGroupCountInGroup2-1)) && (iAfterSubGroupCountInGroup3 != (iBeforeSubGroupCountInGroup3+1)))
				throw new IDIOMException("Draggging and dropping was not successfull. Group2: Expected - " + 
			(iBeforeSubGroupCountInGroup2-1) + " Actual - " + iAfterSubGroupCountInGroup2 +
						". Group3: Expected - " + (iBeforeSubGroupCountInGroup3+1) + " Actual - " +
						iAfterSubGroupCountInGroup3 +".###DragNDropFailedInsideGroup");
			
			
			CustomReporter.log("Drag and drop was successfully. Subgroup from group2 is moved to group3." +
			" Group2: Earlier " + iBeforeSubGroupCountInGroup2 + " and now " + iAfterSubGroupCountInGroup2 +
			" Group3: Earlier " + iBeforeSubGroupCountInGroup3 + " and now " + iAfterSubGroupCountInGroup3);
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8676-Exception", "fail");
			}else{
				rm.captureScreenShot("8676-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8676-Exception", "fail");
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