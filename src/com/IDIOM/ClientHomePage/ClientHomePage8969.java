package com.IDIOM.ClientHomePage;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;













import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.msat.frameworks.data_driven.base.Base;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>*2331,2330_ClientHomepage_ManagingClient_Verify No Projects present functionality</p>
 *  <p> <b>Objective:</b>Verify the client home page when no projects are available for the selected client</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8969.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Amrutha Nair
 * @since 14 Jun 2016
 *
 */
public class ClientHomePage8969 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyClientWithNoProject(){		
			
		
		String strMsg="";
		
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
			
			
			//Step 2:	Select a client with no project
			cl.selectedClient.click();
			
			List<String> clients=cl.returnClientsInDropDown();
			boolean status=false;
			List<String> subClients=new ArrayList<>();
			List<String> subSubClients=new ArrayList<>();
			cl.selectedClient.click();
			if(clients.size()>0){
				for (int i=0;i<clients.size();i++){
					cl.selectClient(clients.get(i));
					Thread.sleep(5000);
				
					rm.webElementSync("pageload");
					rm.webElementSync("jqueryload");
					strMsg="The user has selected client '"+clients.get(i)+"'";
					CustomReporter.log(strMsg);
					if(cl.verifyProjectsInClientHomePage(clients.get(i)).contains("There are no projects for selected project")){
						strMsg="There are no projects  associated with client '"+clients.get(i)+"' ";
						CustomReporter.log(strMsg);
						status=true;
						
						
									
					}
					
					else{
						strMsg="There are  projects  associated with client '"+clients.get(i)+"' ";
						CustomReporter.log(strMsg);
						subClients=cl.returnSubClientsforClient(clients.get(i).trim());
						if(subClients.size()>0){							
							strMsg="There are sub clients present for the client '"+clients.get(i)+"'";
							CustomReporter.log(strMsg);
							for (int j=0;j<subClients.size();j++){
								cl.selectsubClientByName(subClients.get(j).toLowerCase().trim());
								Thread.sleep(5000);
								rm.webElementSync("pageload");
								rm.webElementSync("jqueryload");
								strMsg="The user has selected sub client '"+subClients.get(j)+"' associated with client '"+clients.get(i)+"'";
								CustomReporter.log(strMsg);
								if(cl.verifyProjectsInClientHomePage(subClients.get(j)).contains("There are no projects for selected project")){
									strMsg="There are no projects  associated with sub client '"+subClients.get(j)+"' : '"+clients.get(i)+"'";
									CustomReporter.log(strMsg);
									status=true;									
									
														
								}
								else{
									strMsg="There are  projects  associated with sub client '"+subClients.get(j)+"' : '"+clients.get(i)+"'";
									CustomReporter.log(strMsg);
									
									subSubClients=cl.returnSubSubClient(subClients.get(j));
									if(subSubClients.size()>0){
										strMsg="There are sub sub clients present for the client '"+clients.get(i)+"':' sub client '"+subClients.get(j)+"'";
										CustomReporter.log(strMsg);
										
										for (int k=0;k<subSubClients.size();k++){
											cl.selectsubClientByName(subSubClients.get(k).toLowerCase().trim());
											Thread.sleep(5000);
											rm.webElementSync("pageload");
											rm.webElementSync("jqueryload");
											strMsg="The user has selected sub sub client '"+subSubClients.get(k)+"' associated with '"+subClients.get(j)+"':'"+clients.get(i)+"'";
											CustomReporter.log(strMsg);
											if(cl.verifyProjectsInClientHomePage(subSubClients.get(k)).contains("There are no projects for selected project")){
												strMsg="There are no projects  associated with sub sub client '"+subSubClients.get(k)+"' associated with '"+subClients.get(j)+"':'"+clients.get(i)+"'";
												CustomReporter.log(strMsg);
												status=true;
												
												
																	
											}
											else{
												strMsg="There are  projects  associated with sub sub client '"+subSubClients.get(k)+"' associated with '"+subClients.get(j)+"':'"+clients.get(i)+"'";
												CustomReporter.log(strMsg);
											}
											if(status){
												break;
											}
									}
										
										
								}
							}
								if(status){
									break;
								}
						}
							
						
						}
					}
					if(status)
					{
						break;
					}
					
				}
				
				if(!status){
					strMsg="Cannot Proceed with this test case as all the clients are having associated  projects";
					CustomReporter.log(strMsg);
				}
				else{
				//step 3: Verify the message in client home page
					if(cl.isVisible("noprojectmessage")){
						strMsg="The proper message is coming in client home page when there is no projects associated. The message is :"+cl.noProjectMsg.getText();
						CustomReporter.log(strMsg);
					}
					else{
						strMsg="The proper message is not  coming in client home page when there is no projects associated. ";
						CustomReporter.errorLog(strMsg);
						rm.captureScreenShot("8969_NoProjectMsgIssue", "fail");
						BaseClass.testCaseStatus=false;
						}
					
				
				//Step 4:From client list drop down , try to select another client
				
				String clientTobeSelected=null;
				String strClientName=cl.getSelectedClientName();
				
				Thread.sleep(5000);
				if(clients.size()>1){
					for (int i=0;i<clients.size();i++){
						if(!(clients.get(i).trim().toLowerCase().contentEquals(strClientName.toLowerCase()))){
							
							clientTobeSelected =clients.get(i).trim();
							break;
						}
					}
				}
				else{
				
					clientTobeSelected =strClientName;
				}
				
			
				
				cl.selectClient(clientTobeSelected);
				strMsg="The user has selected '"+clientTobeSelected+"' from the drop down";
				
				CustomReporter.log(strMsg);
				Thread.sleep(5000);
				rm.webElementSync("pageload");
				rm.webElementSync("jqueryload");
				strMsg=cl.verifyProjectsInClientHomePage(clientTobeSelected);
				CustomReporter.log(strMsg);
				
				strMsg="The user is able to select another client from client drop down";
				CustomReporter.log(strMsg);
				
			}
				
			}
				
			
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot("8969_execption", "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8969", "fail");
		}finally{
			try{
				
				
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
