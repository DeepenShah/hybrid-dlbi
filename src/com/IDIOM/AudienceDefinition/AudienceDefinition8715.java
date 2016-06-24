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
 * <p> <b>Test Case Name:</b>2103: Verify blank category  is Not allowed </p>
 * <p> <b>Objective:</b> Verify blank category  is Not allowed </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8715.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 11 May 2016
 *
 */
public class AudienceDefinition8715 extends BaseClass {

	AudienceBuilderPage abPage=null;
		
	@Test
	void verifyBlankCategoryIsNotAllowedToAdd(){
		
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
			String strAudience = property.getProperty("audience8715");
			String strDeSelectAudi = property.getProperty("deselect8715");
			String strNoSelectionMessage = property.getProperty("noAttributeSelectedMessage");
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
			
			//Step7&8: Navigate to audience definition page
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			
			abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			Thread.sleep(2000);
			CustomReporter.log("Navigated to audience definition page");
						
			//Step9: Select the attribute
			abPage.selectAttributeOnAudienceDefinition(strAudience);
			Thread.sleep(2000);
			
			CustomReporter.log("Selected "+ strAudience);
			rm.captureScreenShot("8715-SelectedElement", "pass");
			
			//Step10: Deselecting element and clicking on add attribute
			abPage.selectAttributeOnAudienceDefinition(strDeSelectAudi);
			CustomReporter.log("De-Selected "+ strAudience);
			
			//Clicking on add attribute button
			abPage.performAction("addattribute");			
			CustomReporter.log("Clicked on 'Add Attribute' ");
			
			//Capturing visibility of error message
			//boolean bErrorMsg = abPage.isVisible("errormsg");			
			String strActualMsg = abPage.errorMessage.getText().trim();
			rm.captureScreenShot("8715-ErrorMessage", "pass");
			
			//Verifying query is added or not
			int iQueryCount = abPage.getQueryItemCountInGroup("1");
			
			if(iQueryCount >= 1)
				throw new IDIOMException("Blank query item get added to group.###BlankCategoryAdded");
			
			CustomReporter.log("Blank category is not added to group. Count is 0");
			
			/*if(!bErrorMsg)
				throw new IDIOMException("Error message is not visible.Also check "+
			"8715-ErrorMessage screenshot in success area.###ErrorNotVisible");*/			
			
			if(!strActualMsg.equalsIgnoreCase(strNoSelectionMessage))
				throw new IDIOMException("Error message is not matching. Found " + strActualMsg +
						" and Expected: " + strNoSelectionMessage +".###FailedToMatchMsg");
			
			CustomReporter.log("Error message appears properly, Message is '"+strActualMsg + "'.");
			
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8715-Exception", "fail");
			}else{
				rm.captureScreenShot("8715-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8715-Exception", "fail");
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
