package com.IDIOM.ClientHomePage;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


















import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.msat.frameworks.data_driven.base.Base;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>*2658_ClientHomepage_ManagingClient_Verify Refreshing of Project List while selecting different Clients and Sub Clients</p>
 *  <p> <b>Objective:</b>Objective: Verify Refreshing of Project List while selecting different Clients and Sub Clients</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8948.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Amrutha Nair
 * @since 14 Jun 2016
 *
 */
public class ClientHomePage8948 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyProjectRefreshOnSelectingDifferentClients(){		
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
	
		String strDetails=null;
		
		String strMsg="";
		
		try{
			//****************Variables declaration and Initialization****************************
			
			String emailid = property.getProperty("SuperAdminUser");
			String password = property.getProperty("SuperAdminpassword");			
			
			String strSelectClient=property.getProperty("selectClient");
			
			strProjectName="TestProject8948 " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			//****************Test step starts here************************************************
			
			//Step 1:	Login into IDIOM
			CustomReporter.log("Step 1: Launched Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);	
			
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(emailid, password);	
		    CustomReporter.log("Step 1: Logged in successfully");
		    
		  
		    clientListPage = new ClientList_Page(driver);
			
		    Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			String selectedClientName=clientListPage.getSelectedClientName();
			
			if(selectedClientName.trim().toLowerCase().contentEquals(strSelectClient.toLowerCase())){
				strMsg="The user has logged in first time , and the drop down contains 'Select Client 'message";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The client which is selected before login is coming by default in drop down. The selected client is '"+selectedClientName+"'";
				CustomReporter.log(strMsg);
			}
			
			
			/* -------Creating a project with time stamp to verify this scenario... This is not in spira test steps  ---------- */
			int totalProjects=0;
			clientListPage.selectedClient.click();
			
			List<String> clients=clientListPage.returnClientsInDropDown();
			
			List<String> subClients=new ArrayList<>();
			List<String> subSubClients=new ArrayList<>();
			clientListPage.selectedClient.click();
			if(clients.size()>0){
				clientListPage.selectClient(clients.get(0));
				Thread.sleep(5000);
				clientListPage.func_PerformAction("New Project");
				rm.webElementSync(clientListPage.newProjectWindow, "visibility");
				strMsg = "Create New Project Window appeared successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
				
				//Fill the project
				clientListPage.findAndSaveProjectWindow(true, "");
				
				
				clientListPage.fillProject(strProjectName,strProjectDescription);				
				clientListPage.func_PerformAction("Save Project");
				
				Thread.sleep(2000);
				 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
				

				strMsg = "The user saved project '"+strProjectName+"' for client '"+clients.get(0)+"'";
				CustomReporter.log(strMsg);
				Thread.sleep(3000);
				clientListPage.func_PerformAction("Close Project");
				
				Thread.sleep(3000);
				
				 totalProjects=clientListPage.totalProject();
				 strMsg = "The client '"+clients.get(0)+"' has '"+totalProjects+"' projects associated";
				 CustomReporter.log(strMsg);
				 for (int i=1;i<clients.size();i++){
					 clientListPage.selectClient(clients.get(i));
						Thread.sleep(5000);
					
						rm.webElementSync("pageload");
						rm.webElementSync("jqueryload");
						strMsg="The user has selected client '"+clients.get(i)+"'";
						CustomReporter.log(strMsg);
						
						if(clientListPage.totalProject()!=totalProjects){
							strMsg="The total projects present for client '"+clients.get(i)+"' is '"+clientListPage.totalProject()+"' not same as the projects for '"+clients.get(i-1)+"'";
							CustomReporter.log(strMsg);
							strMsg="The  projects are getting refreshed on selecting client '"+clients.get(i)+"'";
							CustomReporter.log(strMsg);
						}
						else{
							if(clientListPage.verfiyProjectIsExist(strProjectName)){
								strMsg="The  projects are not getting refreshed on selecting client '"+clients.get(i)+"'. The project created for '"+clients.get(0)+"' is present with '"+clients.get(i)+"'.";
								throw new IDIOMException(strMsg+"###8948_ProojectRefreshIssue");
							}
							else{
								strMsg="The  projects are getting refreshed on selecting client '"+clients.get(i)+"'";
								CustomReporter.log(strMsg);
							}
						}
						
						totalProjects =clientListPage.totalProject();
						subClients=clientListPage.returnSubClientsforClient(clients.get(i).trim());
						if(subClients.size()>0){							
							strMsg="There are sub clients present for the client '"+clients.get(i)+"'";
							CustomReporter.log(strMsg);
							for (int j=0;j<subClients.size();j++){
								clientListPage.selectsubClientByName(subClients.get(j).toLowerCase().trim());
								Thread.sleep(5000);
								rm.webElementSync("pageload");
								rm.webElementSync("jqueryload");
								strMsg="The user has selected sub client '"+subClients.get(j)+"' associated with client '"+clients.get(i)+"'";
								CustomReporter.log(strMsg);
								
								if(clientListPage.totalProject()!=totalProjects){
									strMsg="The total projects present for client '"+subClients.get(j)+"' is '"+clientListPage.totalProject()+"' not same as the projects for '"+clients.get(i)+"'";
									CustomReporter.log(strMsg);
									strMsg="The  projects are getting refreshed on selecting client '"+subClients.get(j)+"'";
									CustomReporter.log(strMsg);
								}
								else{
									if(clientListPage.verfiyProjectIsExist(strProjectName)){
										strMsg="The  projects are not getting refreshed on selecting client '"+subClients.get(j)+"'. The project created for '"+clients.get(0)+"' is present with '"+subClients.get(j)+"'.";
										throw new IDIOMException(strMsg+"###8948_ProojectRefreshIssue");
									}
									else{
										strMsg="The  projects are getting refreshed on selecting client '"+subClients.get(j)+"'";
										CustomReporter.log(strMsg);
									}
								}
								
								totalProjects =clientListPage.totalProject();
								subSubClients=clientListPage.returnSubSubClient(subClients.get(j));
								if(subSubClients.size()>0){
									strMsg="There are sub sub clients present for the client '"+clients.get(i)+"':' sub client '"+subClients.get(j)+"'";
									CustomReporter.log(strMsg);
									
									for (int k=0;k<subSubClients.size();k++){
										clientListPage.selectsubClientByName(subSubClients.get(k).toLowerCase().trim());
										Thread.sleep(5000);
										rm.webElementSync("pageload");
										rm.webElementSync("jqueryload");
										strMsg="The user has selected sub sub client '"+subSubClients.get(k)+"' associated with '"+subClients.get(j)+"':'"+clients.get(i)+"'";
										CustomReporter.log(strMsg);
										
										if(clientListPage.totalProject()!=totalProjects){
											strMsg="The total projects present for client '"+subSubClients.get(k)+"' is '"+clientListPage.totalProject()+"' not same as the projects for '"+subClients.get(j)+"'";
											CustomReporter.log(strMsg);
											strMsg="The  projects are getting refreshed on selecting client '"+subSubClients.get(k)+"'";
											CustomReporter.log(strMsg);
										}
										else{
											if(clientListPage.verfiyProjectIsExist(strProjectName)){
												strMsg="The  projects are not getting refreshed on selecting client '"+subSubClients.get(k)+"'. The project created for '"+clients.get(0)+"' is present with '"+subSubClients.get(k)+"'.";
												throw new IDIOMException(strMsg+"###8948_ProojectRefreshIssue");
											}
											else{
												strMsg="The  projects are getting refreshed on selecting client '"+subSubClients.get(k)+"'";
												CustomReporter.log(strMsg);
											}
										}
									}
								}
							}
						
						}
						totalProjects =clientListPage.totalProject();
				 	}
				}
			else{
				strMsg="There are no clients present ";
				CustomReporter.log(strMsg);
			}			
			
			
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot("8948_execption", "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8948", "fail");
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
