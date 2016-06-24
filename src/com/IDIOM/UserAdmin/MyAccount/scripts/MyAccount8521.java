/********************************************************************
Test Case Name: *1207_My Account_Page LoadingCorrectly
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8521.aspx
Objective:  Verify 'My account page' is getting loaded on clicking on 'My account link ' from drop down
Module: User Admin - My Account
Created By: Shailesh Kava
Date: 30 December 2015
**********************************************************************/

package com.IDIOM.UserAdmin.MyAccount.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class MyAccount8521 extends BaseClass {

	@Test
	public void test_MyAccount8521() throws Exception{
		/******************************Variables********************************/
		String adminUser = property.getProperty("SuperAdminUser").trim();
		String adminPass = property.getProperty("SuperAdminpassword").trim();
		String client = property.getProperty("clientName").trim();
		/****************************Variables End******************************/
		
		CustomReporter.log("Step 1: Open URL and login to IDIOM");
		
		Login_Page lp = new Login_Page(driver);
		
		lp.func_LoginToIDIOM(adminUser, adminPass);
		
		Thread.sleep(15000);
		
		CustomReporter.log("Step 1: Logged in with super admin user");
		
		UserAdminMyAccount_Page myAcct = new UserAdminMyAccount_Page(driver);
		
		myAcct.func_onHover("headericon");
		
		Thread.sleep(10000);
		
		if(myAcct.func_elementExist("dropdownopen")){
			
			CustomReporter.log("drop down opened");
			
			myAcct.func_click("myaccount");
			
			Thread.sleep(3000);
			
			//Verifying if my account page is appearing
			if(myAcct.func_elementExist("profilesetting")){
				CustomReporter.log("My profile page is opened");
				
				CustomReporter.log("Step 3: Click on browse back button");
				
				myAcct.func_browserBack();
				
				Thread.sleep(3000);
				
				ClientList_Page CL = new ClientList_Page(driver);
				
				if(CL.func_ClientListPageElementExist("Missing a client?")){
					CustomReporter.log("Step 3: User back to client list page");
					
					CustomReporter.log("Step 4: Select a client");
					
					CL.func_SelectClient(client);
					
					Audience_Page AP = new Audience_Page(driver);
					
					if(AP.func_AudiencePageElementExist("AudienceLabel")){
						
						CustomReporter.log("Step 4: Manage IDIOM page is appearing");
						
						CustomReporter.log("Step 5: Clicking on my account from header");
						
						Thread.sleep(7000);
						
						myAcct.func_onHover("headericon");
						
						Thread.sleep(5000);
						
						myAcct.func_click("myaccount");
						
						if(myAcct.func_elementExist("profilesetting")){
							CustomReporter.log("Step 5: User my pofile page is appearing");
							
							CustomReporter.log("Step 6: Clicking on back button");
							
							myAcct.func_browserBack();
							
							Thread.sleep(5000);
							
							if(AP.func_AudiencePageElementExist("AudienceLabel")){
								CustomReporter.log("Step 6: Audience page is appearing");
								
								CustomReporter.log("Step 7: Select an IDIOM");
								
								ManageIdiom_Page MI = new ManageIdiom_Page(driver);
								
								if(MI.func_ElementExist("CreateNewIdiom")){
									CustomReporter.log("Manage idiom page is opened");
								}else{
									CustomReporter.errorLog("Failed to open manage idiom page");
									BaseClass.testCaseStatus = false;
								}
								
								String genereateIdiomName = MI.func_CreateIdiomName("myAccount8521", "New");
								
								MI.func_CreateNewIdiomOrRename(genereateIdiomName, "NotBlank");
								
								if(AP.func_AudiencePageElementExist("AudienceLabel")){
									CustomReporter.log("Step 7: Audience is appearing");
									
									CustomReporter.log("Step 8: Clicking on my account from header");
									
									Thread.sleep(10000);
									
									myAcct.func_onHover("headericon");
									
									Thread.sleep(5000);
									
									myAcct.func_click("myaccount");
									
									if(myAcct.func_elementExist("profilesetting")){
										CustomReporter.log("Step 8: User my pofile page is appearing");
										
										CustomReporter.log("Step 9: Clicking on back button");
										
										myAcct.func_browserBack();
										
										Thread.sleep(5000);
										rm.webElementSync("idiomspinningcomplete");
										
										if(AP.func_AudiencePageElementExist("AudienceLabel")){
											CustomReporter.log("Step 9: Audience is appearing");
											
											if(rm.webElementSync(AP.func_GetLocalWebElement("btn_AnalyzeAudience"), "visibility")){
												CustomReporter.log("Step 10: Click on Analyze Tab");
												AP.func_ClickElement("AnalyseIcon");
											}else{
												CustomReporter.errorLog("Analyze button is not visible");
												BaseClass.testCaseStatus=false;
											}
											
											rm.webElementSync("idiomspinningcomplete");
											
											Analyse_Profile_Page AnalysePage = new Analyse_Profile_Page(driver);
											
											if(AnalysePage.func_ElementExist("ProfilePage") || AnalysePage.func_ElementExist("AnalysePageExists")){
												CustomReporter.log("Step 10: Analyze page is appearing");
												
												CustomReporter.log("Step 11: Clicking on my account");
												
												myAcct.func_onHover("headericon");
												
												Thread.sleep(5000);
												
												myAcct.func_click("myaccount");
												
												if(myAcct.func_elementExist("profilesetting")){
													CustomReporter.log("Step 11: User my pofile page is appearing");
													
													CustomReporter.log("Step 12: Clicking on back button");
													
													myAcct.func_browserBack();
													
													Thread.sleep(20000);
													
													if(AnalysePage.func_ElementExist("ProfilePage") || AnalysePage.func_ElementExist("AnalysePageExists")){
														CustomReporter.log("Step 12: Analyze page is appearing");
														
														CustomReporter.log("Step 13: Clicking on Architect tab");
														
														AnalysePage.func_ClickElement("Architect");
														
														Thread.sleep(15000);
														
														ArchitectMediaRankerPage architectPage = new ArchitectMediaRankerPage(driver);
														
														if(architectPage.func_VerifyElementExist("mediarankertextinmenu")){
															
															CustomReporter.log("Step 13: Architect page is appearing");
															
															CustomReporter.log("Step 14: Clicking on my account");
															
															myAcct.func_onHover("headericon");
															
															Thread.sleep(5000);
															
															myAcct.func_click("myaccount");
															
															if(myAcct.func_elementExist("profilesetting")){
																
																CustomReporter.log("Step 14: User my pofile page is appearing");
																
																Thread.sleep(5000);
																
																myAcct.func_onHover("headericon");
																
																myAcct.func_click("logout");
																
																CustomReporter.log("Step 15: Log out from application");
															}
														}else{
															CustomReporter.errorLog("Step 13: Failed to open Architext page");
															BaseClass.testCaseStatus = false;
														}
														
														Thread.sleep(20000);
														
													}else{
														CustomReporter.errorLog("Step 12: Failed to open Analyze page");
													}
												}else{
													CustomReporter.errorLog("Step 11: failed to open my profile page");
													BaseClass.testCaseStatus = false;
												}
												
											}else{
												CustomReporter.errorLog("Step 10: Failed to open Analyze page");
												BaseClass.testCaseStatus = false;
											}
											
										}else{
											CustomReporter.errorLog("Step 9: Failed to open Audience page");
											BaseClass.testCaseStatus = false;
										}
										
									}else{
										CustomReporter.errorLog("Step 8: Failed to open user my profile page");
										BaseClass.testCaseStatus = false;
									}
									
								
								}else{
									CustomReporter.log("Step 7: Audience page is not appearing");
									BaseClass.testCaseStatus = false;
								}
								
							}else{
								CustomReporter.errorLog("Step 6: Failed to open audience page");
								BaseClass.testCaseStatus = false;
							}
							
						}else{
							CustomReporter.errorLog("Step 5: Failed to Open User - My Profile page");
							BaseClass.testCaseStatus = false;
						}
					}else{
						CustomReporter.errorLog("Step 4: Failed to open manage IDIOM page");
						BaseClass.testCaseStatus = false;
					}
					
				}else{
					CustomReporter.errorLog("Step 3: Failed to come back to client list page");
					BaseClass.testCaseStatus = false;
				}
				
			}else{
				CustomReporter.errorLog("Step 2: Failed to open my profile page");
				BaseClass.testCaseStatus = false;
			}
			
		}else{
			CustomReporter.errorLog("failed to open drop down");
			BaseClass.testCaseStatus = false;
		}
	
		/***************************Test step ends here******************************/
		if(BaseClass.testCaseStatus == false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case has Passed");
		}
		
	}
}
