package com.IDIOM.ClientHomePage;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;











import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>*2328_ClientHomepage_ManagingClient_Verify whether All clients that are appearing in my account page are appearing in client home page</p>
 *  <p> <b>Objective:</b>: To verify that all clients which are listed in My Account page are displayed in client home page.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8972.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Amrutha Nair
 * @since 14 Jun 2016
 *
 */
public class ClientHomePage8972 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyClientsInClientHmePage() throws Exception{		
			
		
		String strMsg="";
		
		
		try{
			//****************Variables declaration and Initialization****************************
			
			String emailid = property.getProperty("SuperAdminUser");
			String password = property.getProperty("SuperAdminpassword");			
			
			
			//****************Test step starts here************************************************
			
			//Step 1:	Login to the application with valid credentials
			CustomReporter.log("Step 1: Launched Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);	
			
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(emailid, password);	
		    CustomReporter.log("Step 1: Logged in successfully");
		    
		  
		    ClientList_Page cl = new ClientList_Page(driver);
			//Step 2:Check the list of Client and note down what all Clients appear on this page
		    
		    Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			boolean status=true;
			List<String> clientsExpected=cl.returnAllClientsForUser();
			
			strMsg="The clients present in client home page are '"+clientsExpected+"'";
			CustomReporter.log(strMsg);
			
			
			//Step 3:Navigate to my account page and Check the clients there
			PageHeader ph= new PageHeader(driver);
			ph.performAction("myaccount");
			
			UserAdminMyAccount_Page myaccount= new UserAdminMyAccount_Page(driver);
			if(myaccount.isVisible("clientssection")){
				strMsg="The my account page is landed'";
				CustomReporter.log(strMsg);
				
			}
			
			Thread.sleep(3000);
			ArrayList<String> clientsAssigned=new ArrayList<>();
			clientsAssigned=myaccount.func_getList("userclients");
			
			strMsg="The clients present in my account page are '"+clientsAssigned+"'";
			CustomReporter.log(strMsg);
			
			//Step 4:Now compare list of client that appear on both the pages
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
				rm.captureScreenShot("8972_ClientsPresentAreNotMatching", "fail");
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
			rm.captureScreenShot("8972", "fail");
		}finally{
			
				
				//Click on logout				
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}
		
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	
	}
	
	
	
}
