package com.IDIOM.ClientHomePage;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;



import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>**ClientHomepage_ManagingClient_SubClient with No Data</p>
 *  <p> <b>Objective:</b>:Verify that when sub client which is not having data/project/audience is getting selected, how the client home page should look like</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8848.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Amrutha Nair
 * @since 03 Jun 2016
 *
 */
public class ClientHomePage8848 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifySubClientWithNoData(){		
			
		
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
				rm.captureScreenShot("8848_NoClientLogo", "fail");
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
				rm.captureScreenShot("8848_NoLogoutLink", "fail");
			}
			
			
			
			//Step 3:Click on the client 
			
			cl.selectedClient.click();
			strMsg="The user has clicked on the selected client in the dropdown";
			CustomReporter.log(strMsg);
		
			if(cl.isVisible("clientdropdownopen")){
				strMsg="On clicking on the client name, the drop down is getting opened";
				CustomReporter.log(strMsg);				
			}
			else{
				strMsg="On clicking on the client name, the drop down is not  getting opened";
				throw new IDIOMException(strMsg+"###8848_ClientDropDownNotGettingOpened");
				
			}
			
			
			//Step 4:	Select another client which has sub clients
			List<String> clients=cl.returnClientsInDropDown();
			boolean status=false;
			boolean bstatus=false;
			List<String> subClients=new ArrayList<>();
			
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
							
				//Step 5:Verify the Client home page when sub client got selected which is NOT having data/project/audience
					
							cl.selectsubClientByName(subClients.get(j).trim().toLowerCase());
							rm.webElementSync("pageload");
							rm.webElementSync("jqueryload");
							strMsg="The user has selected sub client '"+subClients.get(j)+"' under client'"+clients.get(i)+"'";
							CustomReporter.log(strMsg);
							//Verify whether data is present for the sub client-Subclient
							strMsg=cl.verifyProjectsInClientHomePage(subClients.get(j));
							CustomReporter.log(strMsg);
							
							if(strMsg.contains("There are no projects for selected project")){
								strMsg="No projects are present with   sub  client '"+subClients.get(j)+" 'under  '"+clients.get(i)+"'";
								CustomReporter.log(strMsg);
								
								if(cl.isVisible("newproject")){
									strMsg="The 'New Project ' CTA is present with  sub  client '"+subClients.get(j)+" 'under  '"+clients.get(i)+"'";
									CustomReporter.log(strMsg);
								}
								else{
									strMsg="The 'New Project ' CTA is NOT present with   sub  client '"+subClients.get(j)+" 'under  '"+clients.get(i)+"'";
									CustomReporter.errorLog(strMsg);
									rm.captureScreenShot("8848_NewProjectCTAAbsent", "fail");
									BaseClass.testCaseStatus=false;
								}
								
								if(cl.isVisible("noprojectmessage")){
									strMsg="The proper 'NO Projects message'  is present with   sub  client '"+subClients.get(j)+" 'under  '"+clients.get(i)+"'";
									CustomReporter.log(strMsg);
								}
								else{
									strMsg="The proper 'NO Projects message ' is absent with  sub  client '"+subClients.get(j)+" 'under  '"+clients.get(i)+"'";
									CustomReporter.errorLog(strMsg);
									rm.captureScreenShot("8848_NoProjectMsgAbsent", "fail");
									BaseClass.testCaseStatus=false;
								}
								bstatus=true;										
								break;
							}
							else{
								strMsg="The projects are present with  sub  client '"+subClients.get(j)+" 'under  '"+clients.get(i)+"'";
								CustomReporter.log(strMsg);
							}
						}
					status=true;
					if(bstatus){
						break;
					}
					subClients.clear();
								
				}
				else{
					
						strMsg="There are no sub clients present for the client '"+clients.get(i)+"'";
						CustomReporter.log(strMsg);
					
				}
			
				if(status&&bstatus){
					break;
				}
			}				
			if(!status){
				strMsg="There are no sub sub clients present with any  clients. So can't proceed with this test case";
				CustomReporter.log(strMsg);
			}
			else if(!bstatus){
				strMsg="All the sub clients are having projects. So can't proceed with this test case";
				CustomReporter.log(strMsg);
			}			
		}
		else{
			strMsg="There are no clients present with the provided user. So can't proceed with this test case";
			CustomReporter.log(strMsg);
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
			rm.captureScreenShot("8848", "fail");
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
