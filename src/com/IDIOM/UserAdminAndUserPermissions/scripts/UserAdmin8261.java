/********************************************************************
	Test Case Name: *User Admin_Search and Filtering_Bulk Client Assignment Menu_4.2.4-a(i,ii,iii). Verify Client assign action on bulk assignment screen
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8261.aspx
Objective: Verify Client assign action on bulk assignment screen
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 01 December 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class UserAdmin8261 extends BaseClass{

	
	@Test	
	public void test_UserAdmin8261() throws Exception {
		
	//****************Variables declaration and Initialization****************************	
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");
		String ClientName = property.getProperty("clientName");
		String ClientName1 = property.getProperty("clientName3");
		String Query=null;
		String connection=property.getProperty("dbinstance");
		String DBUserName=property.getProperty("dbuser");
		String DBPassword=property.getProperty("dbpass");
		
	//****************Test steps starts here************************************************
		
		//Step 1 - Open URL: http://172.19.50.32:8080/idiom/#/login
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		//Step 2 - Login with valid super Admin user
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");		
		ln.func_LoginToIDIOM(emailid, password);	
		CustomReporter.log("User should land in Client List page");
		rm.waitTime(6000);
				
		//Step 3 - Click on "Admin Access" link from header sandwich icon
		ClientList_Page CL = new ClientList_Page(driver);
		CustomReporter.log("Click on Admin Access menu option");
		CL.func_PerformAction("AdminAccess");
		//rm.waitTime(15000);
		
		//Step 4 - Select some users , who have client admin access for client say "Delta" 
		UserAdmin_UserPermissions_Page UA = new UserAdmin_UserPermissions_Page(driver);
		rm.waitTime(5000);
		CustomReporter.log("Select Client Admin Users");
		
		String [][] ClientUsers=null;
		ClientUsers=UA.func_RetrieveUsersAndClick("ClientAdmin");
		
		CustomReporter.log("Client Admin Users have been selected");
		//Step 5 - Click on Bulk Assignment menu button from left top of user admin listing
		CustomReporter.log("Click on Bulk Assignment menu button from left top of user admin listing");
		if (UA.func_ElementExist("Bulk Client Icon")){
			UA.func_ClickElement("Bulk Icon Click");
			rm.waitTime(5000);
			//Step 6 - Select "Delta"  and "Spring Boost" clients and click on Assign button 
			String[] ClNames = {ClientName1,ClientName};
			Query="Select name from Idiom.Client where Status = 'ACTIVE'";
			 if(UA.func_VerifyApplicationDataWithDB("Check Clients from DB",Query,connection,DBUserName,DBPassword))
			
			//if (UA.func_CheckClientsFromDB())//It should display a dropdown menu with a list of all clients. It will cover step 8 expected Result as well 
			{
				//Stept 6 - Search for any client name say: Victoria from "Search Bar" 
				CustomReporter.log("Clients from the Database and on Client Assignment Call Out match");
				rm.waitTime(5000);
				UA.func_AssignClients(0,0, "Send Client Names And Save", ClNames );
				rm.waitTime(5000);
				for (int i=0;i<ClientUsers.length;i++)
				{
					Query="SELECT users.id, users.email_address, groups.group_name, group_members.group_id, user_client.client_id as cid FROM idiom.users INNER JOIN user_client ON user_client.user_id = users.id INNER JOIN  idiom.group_members ON user_client.user_client_id = group_members.user_client_id INNER JOIN  idiom.groups ON group_members.group_id = groups.id WHERE  users.email_Address = '"+ ClientUsers[i][1] + "' and user_client.client_id=11 and groups.group_name='clientAdmin'";
					if(UA.func_VerifyApplicationDataWithDB("Verify Access Right for User",Query,connection,DBUserName,DBPassword))
					
						//if (UA.func_RetrieveAccessRights("ClientAdmin", ClientUsers[i][1]))
					{
						//1. Delta client should be re-assigned to users (A,B) and drop down should be closed
						//2. User A and B will remain client admin for Delta client as they are having Admin access for Delta client
						CustomReporter.log("Assginment has been verified from the database and it is perfectly done for - " + ClientUsers[i][1]);
					}
					else
					{
						BaseClass.testCaseStatus=false;
						CustomReporter.errorLog("Assginment has been verified from the database and it is not functioned properly for - " + ClientUsers[i][1]);
					}
				}	
				if(UA.func_SearchForKeyIcon(ClientUsers)){
					CustomReporter.log("Key icon found for all selected Client Admin Users");
				}
				else
				{
					BaseClass.testCaseStatus=false;
					CustomReporter.errorLog("Key Icons not found for Single/few/all Client Admin Users");
				}
			}
			else
			{
				CustomReporter.errorLog("Clients from the Database and on Client Assignment Call Out do not match");
				BaseClass.testCaseStatus=false;
			}
			
		}
		
		//Step7 - Select any users , who are normal users and do not have client admin access for client say "Delta" 
		String[] ClNames1 = {ClientName1};
		String[][] GeneralUsers = null;
		
		GeneralUsers=UA.func_RetrieveUsersAndClick("GeneralUser");
		CustomReporter.log("General Users have been selected");
		rm.waitTime(5000);
		//Step 8 - Click on Bulk Assignment menu button from left top of user admin listing
		if (UA.func_ElementExist("Bulk Client Icon")){
			UA.func_ClickElement("Bulk Icon Click");
			rm.waitTime(5000);
			UA.func_AssignClients(0,0, "Send Client Names And Save", ClNames1 );
			for (int i=0;i<GeneralUsers.length;i++)
			{
				Query="SELECT users.id, users.email_address, groups.group_name, group_members.group_id, user_client.client_id as cid FROM idiom.users INNER JOIN user_client ON user_client.user_id = users.id INNER JOIN  idiom.group_members ON user_client.user_client_id = group_members.user_client_id INNER JOIN  idiom.groups ON group_members.group_id = groups.id WHERE  users.email_Address = '"+ GeneralUsers[i][1] + "' and user_client.client_id=11 and groups.group_name='appUser'";
				if(UA.func_VerifyApplicationDataWithDB("Verify Access Right for User",Query,connection,DBUserName,DBPassword))
				//if (UA.func_RetrieveAccessRights("GeneralUser", GeneralUsers[i][1]))
				{
					//1. Delta client should be re-assigned to users (A,B) and drop down should be closed
					//2. User A and B will remain client admin for Delta client as they are having Admin access for Delta client
					CustomReporter.log("Assginment has been verified from the database and it is perfectly done for + " + GeneralUsers[i][1]);
				}
				else
				{
					BaseClass.testCaseStatus=false;
					CustomReporter.errorLog("Assginment has been verified from the database and it is not functioned properly for " + GeneralUsers[i][1]);
				}
			}
		}		
		//Step 7 - Click on log out link
		rm.waitTime(3000);
		CustomReporter.log("Click on Logout link");
		UA.func_ClickElement ("Logout");
		
		//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}
	
}
