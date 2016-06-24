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
 * <p> <b>Test Case Name:</b> Audience Definition: Query Builder - Verify dragging and dropping of item</p>
 * <p> <b>Objective:</b>To verify item (attribute) can be move to any subgroup/group from any group/subgroup</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8679.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 13-May-2016
 *
 */
public class AudienceDefinition8679 extends BaseClass {
		
	@Test
	public void verifyDraggingAndDroppingItem(){
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
			String strAudience2 = property.getProperty("commonAudienceAttribute4");
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
			
			//Step5: Click on audience definition link
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			CustomReporter.log("Navigated to audience definition page");
			
			
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.addNewGroupLink, "visibility");
			rm.webElementSync(abPage.addNewGroupLink, "clickable");
			
			//Step6: Adding groups and subgroup
			
			//Adding group
			abPage.addNewGroup();
			Thread.sleep(1000);
			
			int iBeforeGroupCount = abPage.getGroupCount();
			
			
			if(iBeforeGroupCount != 2)
				throw new IDIOMException("Not able to add groups. Count is " +iBeforeGroupCount +".###FailedToAddGroup");
			
			CustomReporter.log("New group is added");
			
			//Adding sub group to 2nd group.
			abPage.addSubGroup("2");
			Thread.sleep(1000);			
			
			int iBeforeSubGroupCountInGroup2 = abPage.getSubGroupCount("2");			
			
			if(iBeforeSubGroupCountInGroup2 != 1)
				throw new IDIOMException("Not able to add subgroups in group 2. Count " + iBeforeSubGroupCountInGroup2 +".###FailedToAddSubgroup");
			
			//Query count in subgroup & group
			int iBeforeQueryCountSubGroup = abPage.getQueryItemCountInGroup("2.1");
			int iBeforeQueryCountGroup = abPage.getQueryItemCountInGroup("2");
			
			//Adding query to 1st group
			abPage.selectGroup("1");
			
			//Query1
			abPage.selectAttributeOnAudienceDefinition(strAudience);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			
			//Query2
			abPage.selectAttributeOnAudienceDefinition(strAudience2);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			
			//Verifying added queries
			int iBeforeQueryCount = abPage.getQueryItemCountInGroup("1");
			
			if(iBeforeQueryCount != 2)
				throw new IDIOMException("Not able to add query in group1. Query " + strAudience +" Count " + iBeforeQueryCount +".###FailedToAddQuery");
			
			CustomReporter.log("Added query " + strAudience + " to group1");
			
			//Dragging query to subgroup
			if(!abPage.performDragAndDrop("1.1", "2.1", false))
				throw new IDIOMException("Not able perform drag and drop.###FailedToDragAndDrop");
			
			CustomReporter.log("Performed drag and drop of query to subgroup, now verifying result");
			
			//Verifying result
			int iAfterQueryCount = abPage.getQueryItemCountInGroup("1");
			int iAfterQueryCountSubgroup = abPage.getQueryItemCountInGroup("2.1");
			
			if((iAfterQueryCount != (iBeforeQueryCount-1)) && (iAfterQueryCountSubgroup != (iBeforeQueryCountSubGroup+1)))
				throw new IDIOMException("Draggging and dropping query to subgroup was not successfull. Group2 query: Expected - " + 
						(iBeforeQueryCountSubGroup+1) + " Actual - " + iAfterQueryCountSubgroup +
									". Group1 query: Expected - " + (iBeforeQueryCount-1) + " Actual - " +
									iAfterQueryCount +".###DragNDropFailedQueryToSubgroup");
			
			CustomReporter.log("Drag and drop for query to subgroup was successfull. Query 1 is moved to subgroup of group 2." +
					"Group2 query: Earlier " + iBeforeQueryCountSubGroup + " and now " + iAfterQueryCountSubgroup +
					" Group1 query: Earlier " + iBeforeQueryCount + " and now " + iAfterQueryCount);			
			
			
			//Dragging dropping quer to group
			if(!abPage.performDragAndDrop("1.1", "2", false))
				throw new IDIOMException("Not able perform drag and drop.###FailedToDragAndDrop");
			
			CustomReporter.log("Performed drag and drop of query to group, now verifying result");
			
			//Verifying result
			iBeforeQueryCount = iAfterQueryCount;
			iAfterQueryCount = abPage.getQueryItemCountInGroup("1");
			int iAfterQueryCountGroup = abPage.getQueryItemCountInGroup("2");
			
			if((iAfterQueryCountGroup != (iBeforeQueryCountGroup+1)) && (iAfterQueryCount != (iBeforeQueryCount-1)))
				throw new IDIOMException("Dragging query to group was not successfull. Group1 query count was " +
			iBeforeQueryCount+" found " + iAfterQueryCount + ". Group2 query count was " + iBeforeQueryCountGroup +
			" and found " + iAfterQueryCountGroup +".###DragNDropFailedQueryToGroup");
			
			CustomReporter.log("Dragging query to group was successfull. Group1 query count was "+
					iBeforeQueryCount+" found " + iAfterQueryCount + ". Group2 query count was " + iBeforeQueryCountGroup +
					" and found " + iAfterQueryCountGroup);
			
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8679-Exception", "fail");
			}else{
				rm.captureScreenShot("8679-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8679-Exception", "fail");
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