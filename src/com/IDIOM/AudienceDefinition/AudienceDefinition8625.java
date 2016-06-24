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
 * <p> <b>Test Case Name:</b> Audience Definition: Query Builder 1.b.ii.2.i - Verify 'New Group' and 'New Subgroup' link  </p>
 * <p> <b>Objective:</b> To verify two links 'New Group' and 'New Subgroup'</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8625.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 05 May 2016
 *
 */
public class AudienceDefinition8625 extends BaseClass {

	@Test
	void verifyAddGroupAndAddSubGroup(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;				
		
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
			
			//Step5&6: Click on audience definition link
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);			
			rm.webElementSync(abPage.addNewGroupLink,"visibility");
			CustomReporter.log("Navigated to audience definition page");			
						
			//Get a group count before adding a group
			int iBefore=abPage.getGroupCount();
			System.out.println("Count Before:" + iBefore);
			CustomReporter.log("Count is "+ iBefore + " before adding new group");
			
			//Step7: Add a group
			abPage.addNewGroup();
			CustomReporter.log("Added new group");
			
			int iAfter = abPage.getGroupCount();
			
			//Verifying the count now
			if(iAfter <= iBefore)
				throw new IDIOMException("Not able to add group.###8625-FailToAddGroup");
			
			CustomReporter.log("Count is "+ iAfter + " after adding group");
			
			//Step8: Add subgroup in that group			
			int iGroupId = iAfter;
				
			//Get a count before adding subgroup
			iBefore = abPage.getSubGroupCount(""+iGroupId);
			CustomReporter.log("Count is " + iBefore + " before adding subgroup inside " + iGroupId + " group.");
			
			CustomReporter.log("Adding subgroup inside newly created group");
			abPage.addSubGroup(""+iGroupId);
			Thread.sleep(2000);
			
			iAfter = abPage.getSubGroupCount(""+iGroupId);
			CustomReporter.log("Count is " + iAfter + " after adding subgroup to " + iGroupId + " group.");
			
			//Verifying the count now
			if(iAfter==(iBefore+1)){
				CustomReporter.log("Successfully added subgroup to the group");
			}else{
				throw new IDIOMException("Failed to add subroup.###8625-FailedToAddSubGroup");
			}
			
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8625-Exception", "fail");
			}else{
				rm.captureScreenShot("8625-"+strMsgAndFileName[1], "fail");	
			}
				
				
			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8625-Exception", "fail");
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
				
				//Step16: Logout
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
