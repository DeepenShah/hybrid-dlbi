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

/** <p> <b>Test Case Name:</b>*ClientHomepage_ManagingClient_No Sub clients</p>
 *  <p> <b>Objective:</b>Verify the functionality when user clicks on the client which is having no sub clients</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8857.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Amrutha Nair
 * @since 03 Jun 2016
 *
 */
public class ClientHomePage8857 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyManagingNoSubClients(){		
			
		
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
				rm.captureScreenShot("8857_NoClientLogo", "fail");
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
				rm.captureScreenShot("8857_NoLogoutLink", "fail");
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
				throw new IDIOMException(strMsg+"###8857_ClientDropDownNotGettingOpened");
				
			}
			
			
			//Step 4:		Select another client which is not having any sub client
			
			List<String> clients=cl.returnClientsInDropDown();
			
			boolean status=false;
			
			List<String> subClients=new ArrayList<>();
			
			cl.selectedClient.click();
			
			if(clients.size()>0){
				for (int i=0;i<clients.size();i++){
					subClients=cl.returnSubClientsforClient(clients.get(i).trim());
					strMsg="The user has selected client '"+clients.get(i)+"'";
					CustomReporter.log(strMsg);
					if(subClients.size()==0){
						strMsg="There are NO sub clients present for the client '"+clients.get(i)+"'";
						CustomReporter.log(strMsg);
						
						if(cl.isVisible("clientdropdownopen")){
							strMsg="On selecting the client '"+clients.get(i)+"' , the drop down is not getting closed";
							CustomReporter.errorLog(strMsg);
							BaseClass.testCaseStatus=false;
							rm.captureScreenShot("8857_ClientDropDownNotGettingClosed", "fail");
						}
						else{
							strMsg="On selecting the client '"+clients.get(i)+"' ,the drop down is  getting closed";
							CustomReporter.log(strMsg);
						}
						
						if(cl.getSelectedClientName().trim().toLowerCase().contentEquals(clients.get(i).trim().toLowerCase())){
							strMsg="On selecting the client '"+clients.get(i)+"' ,the drop down is  getting updated with selected client name";
							CustomReporter.log(strMsg);
						}
						else{
							strMsg="On selecting the client '"+clients.get(i)+"' ,the drop down is  not getting updated with selected client name";
							CustomReporter.errorLog(strMsg);
							BaseClass.testCaseStatus=false;
							rm.captureScreenShot("8857_SelectedClientNameNotGettingUpdated", "fail");
						}
						
						if(cl.verifyProjectsInClientHomePage(clients.get(i)).contains("There are no projects for selected project")){
							strMsg="There are no projects associated with the selected  client '"+clients.get(i)+"'";
							CustomReporter.log(strMsg);
						}
						else{
							
							strMsg="There are  '"+cl.totalProject()+"' projects associated with the selected  client '"+clients.get(i)+"'";
							CustomReporter.log(strMsg);
						}
						status=true;
						break;
						
						
					}
					else{
						strMsg="There are sub clients present with the  client '"+clients.get(i)+"'";
						CustomReporter.log(strMsg);
						subClients.clear();
					}
					
				}
				
				if(!status){
					strMsg="All the clients have sub clients associated . So can't proceed with this test case";
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
			rm.captureScreenShot("8857", "fail");
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
