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
 * <p> <b>Test Case Name:</b>1980_Verify whether Add Attribute button is shown at Parent Level </p>
 * <p> <b>Objective:</b> Verify whether Add Attribute button is shown at Parent Level</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8705.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 11 May 2016
 *
 */
public class AudienceDefinition8705 extends BaseClass {

	AudienceBuilderPage abPage=null;
		
	@Test
	void verifyNoAddAttributeButtonForDemographic(){
		
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;	
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strAudience = property.getProperty("audience8705");
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
			
			
			//Step4,5&6: Select/Create project
			if(clientListPage.totalProject()>0){
				strProjectName = clientListPage.getProjectNameById(1);
			}else{
				strProjectName = clientListPage.createNewProject("");
				bProjectCreated = true;
			}	
			
			//Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			//Step7&8: Navigate to Audience Definition
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			
			abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			Thread.sleep(2000);
			CustomReporter.log("Navigated to audience definition page");
			
			
			//Step5: Drilling through category
			abPage.selectCategory(strAudience);
			Thread.sleep(2000);
			
			CustomReporter.log("Entered into 'Demographics' category. And verifing 'Add Attribute' button is not present");
			
			if(abPage.isVisible("addattributebtn", ""))
				throw new IDIOMException("Add attribute button found on the demographic page.###AddAttributeBtnFound");
			
			
			CustomReporter.log("Successfully verified 'Add Attribute' not present on the page");
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8705-Exception", "fail");
			}else{
				rm.captureScreenShot("8705-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8705-Exception", "fail");
		}finally{
			try{								
				//Deleting newly created project				
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
