package com.IDIOM.ClientHomePage;

import java.util.ArrayList;
import java.util.HashSet;
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

/** <p> <b>Test Case Name:</b>*2315_ClientHomepage_ManagingClient_Verify whether duplicate of any client is displayed</p>
 *  <p> <b>Objective:</b>Verify whether duplicate of any client is displayed</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8901.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Amrutha Nair
 * @since 13 Jun 2016
 *
 */
public class ClientHomePage8901 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyClickingOnClient(){		
			
		
		String strMsg="";
		
		try{
			//****************Variables declaration and Initialization****************************
			
			String emailid = property.getProperty("SuperAdminUser");
			String password = property.getProperty("SuperAdminpassword");			
			
			String strSelectClient=property.getProperty("selectClient");
			
			String strClientName=null;
			
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
				rm.captureScreenShot("8901_NoClientLogo", "fail");
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
				rm.captureScreenShot("8901_NoLogoutLink", "fail");
			}
			
		
			//Step 3:Click on the client 
			strClientName=cl.getSelectedClientName().trim();
			cl.selectedClient.click();
			strMsg="The user has clicked on the selected client in the dropdown";
			CustomReporter.log(strMsg);
		
			if(cl.isVisible("clientdropdownopen")){
				strMsg="On clicking on the client name, the drop down is getting opened";
				CustomReporter.log(strMsg);				
			}
			else{
				strMsg="On clicking on the client name, the drop down is not  getting opened";
				throw new IDIOMException(strMsg+"###8901_ClientDropDownNotGettingOpened");
				
			}
			
			
		//Step 4:	Verify all clients/sub clients and also check that no client is displayed more than once
			List<String> clients = cl.returnClientsInDropDown();
		
		
			HashSet<String> uniqueValues = new HashSet<>(clients);
		
			if(clients.size()==uniqueValues.size()){
				strMsg="The clients are not getting duplicated in drop down";
				CustomReporter.log(strMsg);
			}
			else{
				int diff= clients.size()-uniqueValues.size();
				strMsg="The clients are  getting duplicated in drop down. In client drop down clients are shown as '"+clients+"' . So the total clients getting duplicated are:"+diff;
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8901_ClientsGettingDuplicated", "fail");
				BaseClass.testCaseStatus=false;
			}
			
			for (int i=0;i<clients.size();i++){
				
				List<String> subClients = cl.returnSubClientsforClient(clients.get(i));
				uniqueValues = new HashSet<>(subClients);
				if(subClients.size()>0){
					if(subClients.size()==uniqueValues.size()){
						strMsg="The sub clients for the client :"+clients.get(i)+"' are not getting duplicated";
						CustomReporter.log(strMsg);
					}else{
						strMsg="The sub clients for the client :"+clients.get(i)+"' are  getting duplicated . The sub clients are "+subClients;
						CustomReporter.errorLog(strMsg);
						rm.captureScreenShot("8901_SubClientsGettingDuplicated_"+i, "fail");
						BaseClass.testCaseStatus=false;
					}
					for(int j=0;j<subClients.size();j++){
						cl.selectsubClientByName(subClients.get(j).trim().toLowerCase());
						List<String> subsubCLients=cl.returnSubSubClient(subClients.get(j));
						System.out.println(subsubCLients);
						uniqueValues = new HashSet<>(subsubCLients);
						if(subsubCLients.size()>0){
							if(subsubCLients.size()==uniqueValues.size()){
									strMsg="The sub sub clients for the sub client :"+clients.get(i)+"'-'"+subClients.get(j)+"' are not getting duplicated";
									CustomReporter.log(strMsg);
								}else{
									strMsg="The sub sub clients for the sub client :"+clients.get(i)+"'-'"+subClients.get(j)+"' are  getting duplicated . The sub clients are "+subClients;
									CustomReporter.errorLog(strMsg);
									rm.captureScreenShot("8901_SubsubClientsGettingDuplicated_"+j, "fail");
									BaseClass.testCaseStatus=false;
								}
						}
						
					}
				}
				else{
					strMsg="There is no sub client for the client :"+clients.get(i);
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
			rm.captureScreenShot("8901", "fail");
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
