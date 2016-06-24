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

/** <p> <b>Test Case Name:</b>*ClientHomepage_ManagingClient_Default look</p>
 *  <p> <b>Objective:</b>Verify that client page defaults to showing the client that was most recently viewed</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8837.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Amrutha Nair
 * @since 10 Jun 2016
 *
 */
public class ClientHomePage8837 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyManagingClientDefaultLook(){		
			
		
		String strMsg="";
		
		try{
			//****************Variables declaration and Initialization****************************
			
			String emailid = property.getProperty("SuperAdminUser");
			String password = property.getProperty("SuperAdminpassword");			
			
			String strSelectClient=property.getProperty("selectClient");
			
			String strClientName=property.getProperty("clientName");
			
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
				rm.captureScreenShot("8837_NoClientLogo", "fail");
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
				rm.captureScreenShot("8837_NoLogoutLink", "fail");
			}
			
			//Step 3:Verify client list in drop down
			
			//Gettng assigned clients for the user
			PH.performAction("myaccount");
			UserAdminMyAccount_Page myAccount=new UserAdminMyAccount_Page(driver);
			ArrayList<String> clientsAssigned=new ArrayList<>();
			myAccount.isVisible("clientssection");
			Thread.sleep(3000);
			clientsAssigned=myAccount.func_getList("userclients");
			
			System.out.println("My Account Clients "+clientsAssigned);
			PH.performAction("idiomlogo");
			
			  Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			boolean status=true;
			List<String> clientsExpected=cl.returnAllClientsForUser();
			cl.selectedClient.click();
			
			if(clientsAssigned.size()==clientsExpected.size()){
				for (String client:clientsAssigned){
					if(!(clientsExpected.contains(client))){
						status=false;
						break;
					}
										
				}
			}
			else{
				status=false;
			}
			
			if(status){
				strMsg="The client home page contains all the clients associated with user as mentioned in My Account Page. The associated clients are "+clientsExpected;
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The client home page doesn't contain  all the clients associated with user as mentioned in My Account Page.";
				CustomReporter.errorLog(strMsg);
				strMsg="As per My account page, the associated clients are :"+clientsAssigned+", but in client home page , the following clients are present :"+clientsExpected;
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8837_ClientsPresentAreNotMatching", "fail");
			}
				
		
			//Step 4:Click on the client 
			
			cl.selectedClient.click();
			strMsg="The user has clicked on the selected client in the dropdown";
			CustomReporter.log(strMsg);
		
			if(cl.isVisible("clientdropdownopen")){
				strMsg="On clicking on the client name, the drop down is getting opened";
				CustomReporter.log(strMsg);				
			}
			else{
				strMsg="On clicking on the client name, the drop down is not  getting opened";
				throw new IDIOMException(strMsg+"###8837_ClientDropDownNotGettingOpened");
				
			}
			
			
			//Step 4:		Select another client
			
			List<String> clients=cl.returnClientsInDropDown();
			String clientTobeSelected=null;
			
			cl.selectedClient.click();
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
			
			if(cl.isVisible("clientdropdownopen")){
				strMsg="On selecting a client , the drop down is not getting closed";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8814_ClientDropDownNotGettingClosed", "fail");
			}
			else{
				strMsg="On selecting a client , the drop down is  getting closed";
				CustomReporter.log(strMsg);
			}
			
			
			
			if(cl.getSelectedClientName().trim().toLowerCase().contentEquals(clientTobeSelected.trim().toLowerCase())){
				strMsg="On selecting the client '"+clientTobeSelected+"' ,the drop down is  getting updated with selected client name";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="On selecting the client '"+clientTobeSelected+"' ,the drop down is  not getting updated with selected client name";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8837_SelectedClientNameNotGettingUpdated", "fail");
			}
			
			if(cl.verifyProjectsInClientHomePage(clientTobeSelected).contains("There are no projects for selected project")){
				strMsg="There are no projects associated with the selected  client '"+clientTobeSelected+"'";
				CustomReporter.log(strMsg);
			}
			else{
				
				strMsg="There are  '"+cl.totalProject()+"' projects associated with the selected  client '"+clientTobeSelected+"'";
				CustomReporter.log(strMsg);
			}
			
			
			
			
			//Step 6:Click on Logout
			
			PH.performAction("logout");

			strMsg="The user has been logged out";
			CustomReporter.log(strMsg);
			
			//Step 7:Login to the application same user id and password	T
			
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(emailid, password);	
		    CustomReporter.log(" Logged in successfully with the same user logged in before");
		    
		    
		    //Step 8:Verify client home page
		    
		    Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			 selectedClientName=cl.getSelectedClientName();
			
			if(selectedClientName.trim().toLowerCase().contentEquals(clientTobeSelected.trim().toLowerCase())){
				strMsg="The client drop down is selected by default with the client which the user has selected in last login";
				CustomReporter.log(strMsg);
				
				
			}
			else{
				strMsg="The client drop down is not selected by default with the client which the user has selected in last login";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8837_ClientSelectedissue", "fail");
				BaseClass.testCaseStatus=false;
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
			rm.captureScreenShot("8837", "fail");
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
