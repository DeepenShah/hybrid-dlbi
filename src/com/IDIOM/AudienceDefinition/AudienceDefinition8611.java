package com.IDIOM.AudienceDefinition;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> Audience Definition: Query Builder 1.b_1.b.i_1.b.ii - Add first attribute to create default group  </p>
 * <p> <b>Objective:</b> To check default group that will be created on adding first attribute.</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8611.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 04 May 2016
 *
 */
public class AudienceDefinition8611 extends BaseClass {

	@Test
	void verifyQueryIsAddedToDefaultGroup(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bAudienceCreated = false;
		boolean bProjectCreated = false;
		String strAudienceName="";		
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strAudienceAttributes=property.getProperty("cmmonAudienceAttribute");
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
			
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			//Navigate to audience tab
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. ###8611-AudienceTabNotFound");
			
			//Create audience and click edit
			strAudienceName=clientListPage.createNewAudience("");
			bAudienceCreated = true;
			CustomReporter.log("Created new audience in project " + strProjectName);
			
			clientListPage.performActionOnAudience(strAudienceName, "edit");
			Thread.sleep(3000);
			CustomReporter.log("Clicked 'Edit' for newly create audience");
			
			//Step4&5: Navigate to audience tab
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			abPage.performAction("gotoaudiencedefinition");
			
			Thread.sleep(2000);			
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			
			CustomReporter.log("Navigated to audience definition page");
			
			//Get count of attribute in default group
			int iBefore=abPage.getQueryItemCountInGroup("1");
			System.out.println("Count Before:" + iBefore);
			CustomReporter.log("Count is "+ iBefore + " before adding query");
			
			//Step6: Select an attribute
			abPage.selectAttributeOnAudienceDefinition(strAudienceAttributes);
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			
			int iAfter = abPage.getQueryItemCountInGroup("1");
			
			if(iAfter <= iBefore)
				throw new IDIOMException("Not able to add query to default group.###8611-FailToAddAttribute");
			
			CustomReporter.log("Count is "+ iAfter + " after adding query");
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8611-Exception", "fail");
			}else{
				rm.captureScreenShot("8611-"+strMsgAndFileName[1], "fail");	
			}		
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8611-Exception", "fail");
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
					CustomReporter.log("Deleted Audience " + strAudienceName);
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
