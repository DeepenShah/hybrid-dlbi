package com.IDIOM.ClientHomePage;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.msat.frameworks.data_driven.base.Base;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Verify when we click on the client in drop down or click outside of the drop down, the drop down is getting closed </p>
 *  <p> <b>Objective:</b>*ClientHomepage_ManagingClient_Click On ClientName_CloseDropDown</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8814.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Amrutha Nair
 * @since 02 Jun 2016
 *
 */
public class ClientHomePage8814 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyClientDroDownGettingClosedOnClickingOutSide(){		
			
		
		String strMsg="";
	
		
		try{
			//****************Variables declaration and Initialization****************************
			
			String emailid = property.getProperty("SuperAdminUser");
			String password = property.getProperty("SuperAdminpassword");			
			
			String strSelectClient=property.getProperty("selectClient");
			String strClientName=property.getProperty("clientName");
		//****************Test step starts here************************************************	
			
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
				rm.captureScreenShot("8814_NoClientLogo", "fail");
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
				rm.captureScreenShot("8814_NoLogoutLink", "fail");
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
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8814_ClientDropDownNotGettingOpened", "fail");
			}
			
			//Step 4:Click on another client
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
			
			//step 5:Click on the client 
			cl.selectedClient.click();
			strMsg="The user has clicked on the selected client in the dropdown";
			CustomReporter.log(strMsg);
			
			if(cl.isVisible("clientdropdownopen")){
				strMsg="On clicking on the client name, the drop down is getting opened";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="On clicking on the client name, the drop down is not  getting opened";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8814_ClientDropDownNotGettingOpenedSecondTime", "fail");
			}
			
			
			//Step 6:Click outside the drop down
			cl.projectHeaderName.click();
			strMsg="The user has clicked outside the drop down";
			CustomReporter.log(strMsg);
			
			if(cl.isVisible("clientdropdownopen")){
				strMsg="On clicking outside , the drop down is not getting closed";
				throw new IDIOMException(strMsg+"###8814_ClientDropDownNotGettingClosedOnClickingOutside");
				
			}
			else{
				strMsg="On clicking outside , the drop down is  getting closed";
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
			rm.captureScreenShot("8814", "fail");
		}finally{
			try{
				
				
				//:Click on logout				
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
