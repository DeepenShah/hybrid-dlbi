package com.IDIOM.ClientHomePage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.internal.junit.ArrayAsserts;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.msat.frameworks.data_driven.base.Base;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>*ClientHomepage_ManagingClient_SubClient With a SubClient_With Data</p>
 *  <p> <b>Objective:</b>Objective:Verify how the user can view a sub client with a sub client which is having project associated to it</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8853.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Amrutha Nair
 * @since 02 Jun 2016
 *
 */
public class ClientHomePage8853 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifySubClientWithSubClient(){		
			
		
		String strMsg="";
		boolean bProjectCreate=false;
		String strDetails=null;
		
		try{
			//****************Variables declaration and Initialization****************************
			
			String emailid = property.getProperty("SuperAdminUser");
			String password = property.getProperty("SuperAdminpassword");			
			
			String strSelectClient=property.getProperty("selectClient");
			
		
			
			//****************Test step starts here************************************************
			
			//Step 1:	Login to the application with valid credentials
			CustomReporter.log("Step 1: Launched Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);	
			
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(emailid, password);	
		    CustomReporter.log("Step 1: Logged in successfully");
		    
		  
		    ClientList_Page cl = new ClientList_Page(driver);
			//Step 2:Verify client home page
		    
		    Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			String selectedClientName=cl.getSelectedClientName();
			
			if(selectedClientName.trim().toLowerCase().contentEquals(strSelectClient.toLowerCase())){
				strMsg="The user has logged in first time , and the drop down contains 'Select Client 'message";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The client which is selected before login is coming by default in drop down. The selected client is '"+selectedClientName+"'";
				CustomReporter.log(strMsg);
			}
			
			
			if(cl.isVisible("logo")){
				strMsg="The idiom logo is present in client home page";
				CustomReporter.log(strMsg);
			}
			
			else{
				strMsg="The idiom logo is not present in client home page";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8853_NoClientLogo", "fail");
			}
		
			PageHeader PH=new PageHeader(driver);
			if(PH.isVisible("logoutlink")){
				strMsg="The logout link is present in client home page";
				CustomReporter.log(strMsg);
			}
			
			else{
				strMsg="The logout link is not present in client home page";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8853_NoLogoutLink", "fail");
			}
			
			
			
			//Step 3:Click on the client 
			
			cl.selectedClient.click();
			strMsg="The user has clicked on the selected client in the dropdown";
			CustomReporter.log(strMsg);
			String url=null;
			if(cl.isVisible("clientdropdownopen")){
				strMsg="On clicking on the client name, the drop down is getting opened";
				CustomReporter.log(strMsg);				
			}
			else{
				strMsg="On clicking on the client name, the drop down is not  getting opened";
				throw new IDIOMException(strMsg+"###8853_ClientDropDownNotGettingOpened");
				
			}
			
			
			//Step 4:	Select another client which has sub clients
			List<String> clients=cl.returnClientsInDropDown();
			boolean status=false;
			List<String> subClients=new ArrayList<>();
			List<String> subSubClients=new ArrayList<>();
			cl.selectedClient.click();
			if(clients.size()>0){
				for (int i=0;i<clients.size();i++){
					subClients=cl.returnSubClientsforClient(clients.get(i).trim());
					if(subClients.size()>0){

						strMsg="The user has selected client '"+clients.get(i)+"'";
						CustomReporter.log(strMsg);
						strMsg="There are sub clients present for the client '"+clients.get(i)+"'";
						CustomReporter.log(strMsg);
						for (int j=0;j<subClients.size();j++){
							
				//Step 5:Select a sub client with sub clients associated to it
				//Step 6:Expand sub client which is having sub client
							
				//Step 7:Select a sub client which is having data	
							cl.selectsubClientByName(subClients.get(j).trim().toLowerCase());
							rm.webElementSync("pageload");
							rm.webElementSync("jqueryload");
							strMsg="The user has selected sub client '"+subClients.get(j)+"' under client'"+clients.get(i)+"'";
							CustomReporter.log(strMsg);
							subSubClients=cl.returnSubSubClient(subClients.get(j).trim().toLowerCase());
							if(subSubClients.size()>0){
								
								strMsg="There are sub clients for the selected sub client '"+subClients.get(j)+"'  under client'"+clients.get(i)+"'";
								CustomReporter.log(strMsg);
								cl.selectsubClientByName(subSubClients.get(0).trim().toLowerCase());
								strMsg="The user has selected sub sub  client '"+subSubClients.get(0)+"under  '"+clients.get(i)+"':'"+subClients.get(j)+"'";
								CustomReporter.log(strMsg);
								rm.webElementSync("pageload");
								rm.webElementSync("jqueryload");
								status=true;
								break;
							}
							else{
								strMsg="There are no sub clients for the selected sub client '"+subClients.get(j)+"'  under client'"+clients.get(i)+"'";
								CustomReporter.log(strMsg);
							}
						}
						if(status){
							break;
						}
							
					}
					else{
						strMsg="There are no sub clients present for the client '"+clients.get(i)+"'";
						CustomReporter.log(strMsg);
					}
				}
				if(!status){
					strMsg="There are no sub sub clients present with any  clients. So can't proceed with this test case";
					CustomReporter.log(strMsg);
				}
				else{
					
					//Step 7:Select a sub client which is having data
				
					strMsg=cl.verifyProjectsInClientHomePage(subSubClients.get(0));
					CustomReporter.log(strMsg);
					
					//Step 8:Create Project for the sub sub client selected and launch the same
					cl.func_PerformAction("New Project");
					rm.webElementSync(cl.newProjectWindow, "visibility");
					strMsg = "Create New Project Window appeared successfully";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					
					//Fill the project
					cl.findAndSaveProjectWindow(true, "");
				
					String strProjectName="ClientHomepage_8853";
					String strProjectDescription="Test Project";
					cl.fillProject(strProjectName,strProjectDescription);				
					cl.func_PerformAction("Save Project");
					
					
					strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
					CustomReporter.log(strMsg);
					bProjectCreate=true;
					Thread.sleep(2000);
					 
					 strDetails=cl.getAudienceProjectClientId(strProjectName, "");
					
				
					cl.func_PerformAction("Launch Project");
					strMsg = "Clicked on Launch Project";
					CustomReporter.log(strMsg);
					Thread.sleep(2000);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					
					//Waiting till page get loaded
					ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
					rm.webElementSync(pdp.projectName, "visibility");
					Thread.sleep(2000);
					
					if(pdp.isVisible("projectname", strProjectName)){
						strMsg="The project home page has been launched and it is getting updated with selected project name";
						CustomReporter.log(strMsg);
					}
					else{
						strMsg="The seleced project name is not coming in project home page";
						 throw new IDIOMException(strMsg+"###8853_projectname");
						
					}
					strMsg="The project has been successfully created and launched for sub client '"+subSubClients.get(0)+"'";
					CustomReporter.log(strMsg);
				}
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
			rm.captureScreenShot("8853", "fail");
		}finally{
			try{
				
				//Deleting newly created project
				if(bProjectCreate){
					///////////pageHeader.navigateTo("projecthomepage");
					rm.deleteProjectOrAudience(strDetails, true);
					Thread.sleep(2000);
					
					CustomReporter.log("Deleted the project");
				
				}
				
				//Step 13:Click on logout				
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
