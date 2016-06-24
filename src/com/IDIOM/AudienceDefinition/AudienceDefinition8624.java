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
 * <p> <b>Test Case Name:</b> Audience Definition - a (Categories).i.3 - Verify click of "Add Attribute" </p>
 * <p> <b>Objective:</b> Verify click of "Add Attribute"</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8624.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 06 May 2016
 *
 */
public class AudienceDefinition8624 extends BaseClass {

	@Test
	void verifyClickOnAddAttribute(){
		
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		boolean bGroupCreated = false;
		
		AudienceBuilderPage abPage=null;
		int iBefore,iAfter=0;
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strAudienceDef1 = property.getProperty("audience8624");
			
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
			CustomReporter.log("Navigated to audience definition page");
			
			
			abPage = new AudienceBuilderPage(driver);
			
			//Get count of total group before adding any group
			iBefore = abPage.getGroupCount();
			
			//Step6: Add group		
			abPage.addNewGroup();
				
			
			//Verify group is added.
			iAfter = abPage.getGroupCount();
			
			if(iAfter!=(iBefore+1))
				throw new IDIOMException("Failed to add new group. Group count should be " + (iBefore+1) + " and found "+ iAfter + ".###FailedToAddGroup");
			
			bGroupCreated = true;
			CustomReporter.log("Added new group");
			
			//Select 2nd group
			abPage.selectGroup(""+iAfter);
			
			//Add query to newly created group
			abPage.selectAttributeOnAudienceDefinition(strAudienceDef1);
			CustomReporter.log("Selected attribute ");
			
			abPage.performAction("addattribute");
			CustomReporter.log("Clicked on add attribute button");
			
			Thread.sleep(2000);
			
			//Verifying query is added to group
			int iCount = abPage.getQueryItemCountInGroup("" + iAfter);
			
			if(iCount!=1)
				throw new IDIOMException("Not able to add attribute.###FailedToAddAttributeTo2ndGroup");
			
			CustomReporter.log("Successfully verified clicking on 'Add Attribute' button");			
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8624-Exception", "fail");
			}else{
				rm.captureScreenShot("8624-"+strMsgAndFileName[1], "fail");	
			}
				
				
			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8624-Exception", "fail");
		}finally{
			try{
				
				//Deleting group
				if(bGroupCreated)
					abPage.deleteGroup(""+iAfter);
				
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
