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

/** <p> <b>Test Case Name:</b>***ClientHomepage_ManagingClient_SubClientPresence</p>
 *  <p> <b>Objective:</b>Verify how the client home page will look like when client has sub clients</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8843.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Amrutha Nair
 * @since 03 Jun 2016
 *
 */
public class ClientHomePage8843 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyManagingSubClient(){		
			
		
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
				rm.captureScreenShot("8843_NoClientLogo", "fail");
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
				rm.captureScreenShot("8843_NoLogoutLink", "fail");
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
				throw new IDIOMException(strMsg+"###8843_ClientDropDownNotGettingOpened");
				
			}
			
			
			//Step 4:	Select another client which has sub clients
			List<String> clients=cl.returnClientsInDropDown();
			boolean status=false;
			List<String> subClients=new ArrayList<>();
			cl.selectedClient.click();
			if(clients.size()>0){
				for (int i=0;i<clients.size();i++){
					subClients=cl.returnSubClientsforClient(clients.get(i).trim());
					strMsg="The user has selected client '"+clients.get(i)+"'";
					CustomReporter.log(strMsg);
					if(subClients.size()>0){

						
						strMsg="There are sub clients present for the client '"+clients.get(i)+"'";
						CustomReporter.log(strMsg);
						strMsg="The sub clients present for the client '"+clients.get(i)+"' are '"+subClients+"'";
						CustomReporter.log(strMsg);
						
						status=true;
						break;
					}
					else{
						strMsg="There are no sub clients present for the client '"+clients.get(i)+"'";
						CustomReporter.log(strMsg);
					}
				}
				if(!status){
					strMsg="There are no sub clients present with any  clients. So can't proceed with this test case";
					CustomReporter.log(strMsg);
				}
				else{
					
					if(cl.isVisible("selectedsubclient")){
						if(cl.selectedSubClient.getText().trim().toLowerCase().contentEquals(subClients.get(0).trim().toLowerCase())){
							strMsg="The first sub client is selected by default";
							CustomReporter.log(strMsg);
						}
						else{
						
								strMsg="The first sub client is not selected by default";
								CustomReporter.errorLog(strMsg);
								rm.captureScreenShot("8843_SelectedSubClient", "fail");
								BaseClass.testCaseStatus=false;
							
						}
					}
					else{
						strMsg="No sub clients are  selected by default";
						CustomReporter.errorLog(strMsg);
						rm.captureScreenShot("8843_NoSelectedSubClient", "fail");
						BaseClass.testCaseStatus=false;
					}
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
			rm.captureScreenShot("8843", "fail");
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
