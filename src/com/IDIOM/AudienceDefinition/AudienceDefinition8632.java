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
 * <p> <b>Test Case Name:</b> Audience Definition - a (Categories).ii.1.b - Verify addition of multiple attributes for each of the local markets categories </p>
 * <p> <b>Objective:</b> Verify addition of multiple attributes for each of the local markets categories </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8632.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 09 May 2016
 *
 */
public class AudienceDefinition8632 extends BaseClass {

	@Test
	void addMultipleAttributeFromLocalMarket(){
		
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
			String strAudienceDef1 = property.getProperty("audienceDropDown8632");
			String strExpRes = property.getProperty("expRes8632");
			
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
			
			abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			CustomReporter.log("Navigated to audience definition page");
			
			//Get A Group Count
			iBefore=abPage.getGroupCount();	
			
			//Add a group
			abPage.addNewGroup();		
			
			iAfter = abPage.getGroupCount();
			
			//Verifying the count now
			if(iAfter <= iBefore)
				throw new IDIOMException("Not able to add group.###8632-FailToAddGroup");
			
			bGroupCreated = true;
			
			CustomReporter.log("Added new group");
			
			//Select a group
			abPage.selectGroup(""+iAfter);
			
			//Step6: Navigate to Local market
			abPage.selectAttributeOnAudienceDefinition(strAudienceDef1);
			
			CustomReporter.log("Selected multiple element from drop down. " + strAudienceDef1);
			
			abPage.performAction("addattribute");
			Thread.sleep(2000);
			
			//Step: Verify multiple attribute
			String strQuery = abPage.getQueryWithLogic(""+iAfter+".1");
			
			if(!strQuery.toLowerCase().equalsIgnoreCase(strExpRes))
				throw new IDIOMException("Not able to add multiple values. Found " + strQuery + " and Exp: " + strExpRes + ".###FailedToVerifyData");
			
			CustomReporter.log("Successfully verified multiple added values in Local Market. " + strQuery);
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8632-Exception", "fail");
			}else{
				rm.captureScreenShot("8632-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8632-Exception", "fail");
		}finally{
			try{
				
				//Group deleted
				if(bGroupCreated)
					abPage.deleteGroup(""+iAfter);
				
				CustomReporter.log("Deleted the group");
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
