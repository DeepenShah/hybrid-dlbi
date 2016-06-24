/********************************************************************
Test Case Name:*Super User Admin-VerifySortFunctionality
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8661.aspx
Objective: This test case checks the sort  functionality in User Admin page
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 2 February 2016
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;



import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;

public class UserAdmin8661 extends BaseClass {
	
	
	@Test
	public void verifysortfunctionality() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");
		
		
		
	//****************Test step starts here************************************************
		
			//Step1: Load the URL and log in with valid Super User Admin Credentials
			CustomReporter.log("Launched Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);
			
			Thread.sleep(3000);
			
			
	        ln.func_LoginToIDIOM(emailid, password);	
		    CustomReporter.log("Logged in successfully");
		    
		   
		    ClientList_Page cl= new ClientList_Page(driver);
		    Thread.sleep(20000);
		   
		    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
		    
	    	//Step 2:		Click on menu icon at the top and click on Admin Access
	    	//cl.func_PerformAction("AdminAccess");
		    PageHeader ph=new PageHeader(driver);
		    ph.performAction("adminaccess");
		    CustomReporter.log("The user has clicked on 'Admin Access'");
		    
		    Thread.sleep(5000);
		    		    
		    if(up.func_ElementExist("Users List")){		    	
		    	CustomReporter.log(" User Admin page Got landed.The user listing is displayed");
		    	
		    	  Thread.sleep(3000);
		   	  
	    	  
		    //Step 3:Verify 'Ascending and Descending' icon is present with Name field
		    //Step 4:Click on " 'Ascending " for Name
		    //Step 5:	Click on " 'Descending" for Name
		    	  
		    	  if(up.func_ElementExist("Name Ascending Icon Exists")){
		    		CustomReporter.log("The ascending icon is present with Name field"); 

		    		if(up.func_VerifySortFunctionality("Name Ascending")){
		    	
		    			CustomReporter.log("Name fields are getting updated in Asceding order on clicking on the ascending icon");
		    		
		    		}
		    		else{
		    		
		    			CustomReporter.errorLog("The name fields are not getting updated in ascending order on clicking on the ascending icon");
		    			BaseClass.testCaseStatus=false;
		    		}
		    	  }
		    	  else{
		    		 CustomReporter.errorLog("The Ascending icon is NOT present with Name field");
		    		 BaseClass.testCaseStatus=false;
		    	  }
		    	  
		    	  if(up.func_ElementExist("Name Descending Icon Exists")){
			    		CustomReporter.log("The descending icon is present with Name field"); 

			    	if(up.func_VerifySortFunctionality("Name Descending")){
			    	
			    			CustomReporter.log("Name fields are getting updated in desceding order on clicking on the descending icon");
			    		
			    		}
			    		else{
			    		
			    			CustomReporter.errorLog("The name fields are not getting updated in descending order on clicking on the descending icon");
			    			BaseClass.testCaseStatus=false;
			    		}
			    	  }
			    	  else{
			    		 CustomReporter.errorLog("The descending icon is NOT present with Name field");
			    		 BaseClass.testCaseStatus=false;
			    	  }
		    	  
		    
		    
		    //Step 6:	Verify 'Ascending and Descending' icon is present with Date field"
		    //Step 7:Click on " 'Ascending " for Date
		    //Step 8:Click on " 'Descending" for Date
			    if(up.func_ElementExist("Date Ascending Icon Exists")){
		    		CustomReporter.log("The ascending icon is present with Date field"); 
	
		    		if(up.func_VerifySortFunctionality("Date Ascending")){
		    	
		    			CustomReporter.log("Date fields are getting updated in Asceding order on clicking on the ascending icon");
		    		
		    		}
		    		else{
		    		
		    			CustomReporter.errorLog("The date fields are not getting updated in ascending order on clicking on the ascending icon");
		    			BaseClass.testCaseStatus=false;
		    		}
		    	  }
		    	  else{
		    		 CustomReporter.errorLog("The Ascending icon is NOT present with Date field");
		    		 BaseClass.testCaseStatus=false;
		    	  }
		    	  
		    	  if(up.func_ElementExist("Date Descending Icon Exists")){
			    		CustomReporter.log("The descending icon is present with Date field"); 
	
			    	if(up.func_VerifySortFunctionality("Date Descending")){
			    	
			    			CustomReporter.log("Date fields are getting updated in desceding order on clicking on the descending icon");
			    		
			    		}
			    		else{
			    		
			    			CustomReporter.errorLog("The Date fields are not getting updated in descending order on clicking on the descending icon");
			    			BaseClass.testCaseStatus=false;
			    		}
			    	  }
			    	  else{
			    		 CustomReporter.errorLog("The descending icon is NOT present with Date field");
			    		 BaseClass.testCaseStatus=false;
			    	  }
		    	//Step 6:	Verify 'Ascending and Descending' icon is present with Date field"
				    //Step 7:Click on " 'Ascending " for Date
				    //Step 8:Click on " 'Descending" for Date
					    if(up.func_ElementExist("Access Ascending Icon Exists")){
				    		CustomReporter.log("The ascending icon is present with Access field"); 
			
				    		if(up.func_VerifySortFunctionality("Role Ascending")){
				    	
				    			CustomReporter.log("Access fields are getting updated in Ascending order on clicking on the ascending icon");
				    		
				    		}
				    		else{
				    		
				    			CustomReporter.errorLog("Access fields are not getting updated in ascending order on clicking on the ascending icon");
				    			BaseClass.testCaseStatus=false;
				    		}
				    	  }
				    	  else{
				    		 CustomReporter.errorLog("The Ascending icon is NOT present with access field");
				    		 BaseClass.testCaseStatus=false;
				    	  }
				    	  
				    	  if(up.func_ElementExist("Access Descending Icon Exists")){
					    		CustomReporter.log("The descending icon is present with Access field"); 
			
					    	if(up.func_VerifySortFunctionality("Access Descending")){
					    	
					    			CustomReporter.log("Access fields are getting updated in desceding order on clicking on the descending icon");
					    		
					    		}
					    		else{
					    		
					    			CustomReporter.errorLog("The Access fields are not getting updated in descending order on clicking on the descending icon");
					    			BaseClass.testCaseStatus=false;
					    		}
					    	  }
					    	  else{
					    		 CustomReporter.errorLog("The descending icon is NOT present with Access field");
					    		 BaseClass.testCaseStatus=false;
					    	  }  	  
					    	//Step 6:	Verify 'Ascending and Descending' icon is present with Date field"
						    //Step 7:Click on " 'Ascending " for Date
						    //Step 8:Click on " 'Descending" for Date
							    if(up.func_ElementExist("Email Ascending Icon Exists")){
						    		CustomReporter.log("The ascending icon is present with Email field"); 
					
						    		if(up.func_VerifySortFunctionality("Email Ascending")){
						    	
						    			CustomReporter.log("Email fields are getting updated in Ascending order on clicking on the ascending icon");
						    		
						    		}
						    		else{
						    		
						    			CustomReporter.errorLog("Email fields are not getting updated in ascending order on clicking on the ascending icon");
						    			BaseClass.testCaseStatus=false;
						    		}
						    	  }
						    	  else{
						    		 CustomReporter.errorLog("The Ascending icon is NOT present with email field");
						    		 BaseClass.testCaseStatus=false;
						    	  }
						    	  
						    	  if(up.func_ElementExist("Email Descending Icon Exists")){
							    		CustomReporter.log("The descending icon is present with Email field"); 
					
							    	if(up.func_VerifySortFunctionality("Email Descending")){
							    	
							    			CustomReporter.log("Email fields are getting updated in desceding order on clicking on the descending icon");
							    		
							    		}
							    		else{
							    		
							    			CustomReporter.errorLog("The Email fields are not getting updated in descending order on clicking on the descending icon");
							    			BaseClass.testCaseStatus=false;
							    		}
							    	  }
							    	  else{
							    		 CustomReporter.errorLog("The descending icon is NOT present with Email field");
							    		 BaseClass.testCaseStatus=false;
							    	  }  	  
						    	//Step 6:	Verify 'Ascending and Descending' icon is present with Date field"
								    //Step 7:Click on " 'Ascending " for Date
								    //Step 8:Click on " 'Descending" for Date
									    if(up.func_ElementExist("Clients Ascending Icon Exists")){
								    		CustomReporter.log("The ascending icon is present with Clients field"); 
							
								    		if(up.func_VerifySortFunctionality("Client Ascending")){
								    	
								    			CustomReporter.log("Client fields are getting updated in Ascending order on clicking on the ascending icon");
								    		
								    		}
								    		else{
								    		
								    			CustomReporter.errorLog("Client fields are not getting updated in ascending order on clicking on the ascending icon");
								    			BaseClass.testCaseStatus=false;
								    		}
								    	  }
								    	  else{
								    		 CustomReporter.errorLog("The Ascending icon is NOT present with client field");
								    		 BaseClass.testCaseStatus=false;
								    	  }
								    	  
								    	  if(up.func_ElementExist("Client Descending Icon Exists")){
									    		CustomReporter.log("The descending icon is present with Client field"); 
							
									    	if(up.func_VerifySortFunctionality("Client Descending")){
									    	
									    			CustomReporter.log("Client fields are getting updated in desceding order on clicking on the descending icon");
									    		
									    		}
									    		else{
									    		
									    			CustomReporter.errorLog("The Client fields are not getting updated in descending order on clicking on the descending icon");
									    			BaseClass.testCaseStatus=false;
									    		}
									    	  }
									    	  else{
									    		 CustomReporter.errorLog("The descending icon is NOT present with Client field");
									    		 BaseClass.testCaseStatus=false;
									    	  }  	  
				    	 		    	 
		    	  }
		  
		    	  
		    
	}
}

