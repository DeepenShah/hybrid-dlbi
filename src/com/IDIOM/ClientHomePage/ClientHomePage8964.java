package com.IDIOM.ClientHomePage;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;














import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>*2364_Manage Projects_ManagingClient_Verify whether Project Description field is mandatory</p>
 * <p> <b>Objective:</b>Verify that error should not be generated while user navigating to manage projects from any other pages after deleting audience</p>
 * <p> <b>Test Case ID:</b>Verify whether Project Description field is mandatory</p>
 * <p> <b>Module:</b> Client Home Page</p>
 * @author Amrutha Nair
 * @since 15-June-2016
 *
 */
public class ClientHomePage8964 extends BaseClass {
		
	@Test
	public void verifyProjectDescriotionIsNotMandatory(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
	
		String strDetails=null;
		try{
			
			
		
			//****************Variables declaration and Initialization****************************	
		
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject8964_" + BaseClass.rm.getCurrentDateTime();
		
			//****************Test step starts here************************************************
			
			//Step1 :			Login with valid credentials
			//Step 2:	Select Client
			
			loginToSelectClient();
			clientListPage=new ClientList_Page(driver);
			//Verifies whether all project list or No project msg is coming for selected client
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			//Step 3:Click on New Project and enter the name in overlay and click on save
			//Do not enter anything in the description field
			
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Fill the project
			clientListPage.findAndSaveProjectWindow(true, "");
			
			
			//Input Project name and description as empty and click on Save
		
			clientListPage.fillProject(strProjectName,"");				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8964-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			 clientListPage.func_PerformAction("Close Project");
			 
			 
			 //Verify whether project created now got saved
			 
			 if(clientListPage.verfiyProjectIsExist(strProjectName)){
				 
				 strMsg = "Project '" + strProjectName +" ' saved successfully eventhough the project description is not provided. So the project desciption is not mandatory";
					CustomReporter.log(strMsg);
			 }
			 else{
				 strMsg = "Project '" + strProjectName +"' not getting saved when  project description is not provided";
				 throw new IDIOMException(strMsg+"###8964_ProjectNotGettingSavedWithEmptyDescription");
			 }
			 
			
			
			}catch(IDIOMException ie){
				BaseClass.testCaseStatus = false;
				String strMsgAndFileName[] = ie.getMessage().split("###");
				System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
				CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
				rm.captureScreenShot(strMsgAndFileName[1], "fail");
				
			}catch(Exception e){
				
				BaseClass.testCaseStatus = false;
				e.printStackTrace(System.out);
				CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
				rm.captureScreenShot("8964", "fail");
			}finally{
				try{
					
					//Deleting newly created project
					if(bProjectCreate){
						
						rm.deleteProjectOrAudience(strDetails, true);
						Thread.sleep(2000);
						
						CustomReporter.log("Deleted the project");
					
					}
					
					//Click on logout				
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
