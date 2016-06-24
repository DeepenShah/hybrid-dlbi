package com.IDIOM.architect.scripts;



import java.util.ArrayList;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ArchitectMediaRankerPage;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>Media Ranker_Verify Audience drop down when switching audience from Another Page</p>
 * <p> <b>Objective:</b> Verify that the audience which is selected in another  page is selected in media ranker by default when we navigate to it from another page</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9069.aspx</p>
 * <p> <b>Module:</b> Architect</p>
 * @author Amrutha Nair
 * @since 20-May-2016
 *
 */
public class MediaRanker9069 extends BaseClass {
		
	@Test
	public void verifyAudienceSwitchingFromAnotherPage(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
	
		String strDetails=null;
		try{
			
			
		
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			String strdefaultaudience=property.getProperty("defaultaudience");
			String strMediaRankerItems=property.getProperty("mediaRankerItems");
			//****************Test step starts here************************************************
			
			//Step1 :	Open IDIOM URL and login with valid credential
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 2:Select any existing client from client dropdown
	        clientListPage = new ClientList_Page(driver);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Verifies whether all project list or No project msg is coming for selected client
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			//Step3:Click edit for any project/Create a project
			
			//create New Project
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Fill the project
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###9069-CreateProjectWindow");
										
			//Step 4:Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###9069-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			
			//Step 5:Click on new audience link ,provide name and click on 'create and save'
			 String audienceName = clientListPage.createNewAudience("");
			strMsg = "The audience ' " + audienceName +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
		
			 
			//Step 6:Click on 'Launch Project'
			clientListPage.func_PerformAction("Launch Project");
			strMsg = "Clicked on Launch Project";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Waiting till page get loaded
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			if(pdp.isVisible("projectname", strProjectName)){
				strMsg="The project home page has been launched and it is getting updated with selected project name";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The seleced project name is not coming in project home page";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("9069_projectname", "fail");
			}
			
			
			//Step 7:Select digital ranker /TV ranker destination link from here
			PageHeader PH= new PageHeader(driver);
		
			ArchitectMediaRankerPage AM= new ArchitectMediaRankerPage(driver);
			String [] strMediaRanker =PH.getSplittedItems(strMediaRankerItems);
			
			for (int k=0;k<strMediaRanker.length;k++){
				
				System.out.println(strMediaRanker[k]);
				Thread.sleep(3000);
				PH.megaMenuLinksClick(strMediaRanker[k]);
				
				CustomReporter.log(strMsg);
				
				
				strMsg="The user has clicked on '"+strMediaRanker[k]+"' link from project home page for the project: "+strProjectName;
				CustomReporter.log(strMsg);
				rm.webElementSync("idiomspinningcomplete");
				
				if(AM.func_VerifyVisibilityOfElement("datepicker", "")){
					strMsg="The '"+strMediaRanker[k]+"' page is visible  ";
					CustomReporter.log(strMsg);
				}
				
				//Step 8:	Navigate to any other page like ;HVA or webnet " from mega menu
			
				PH.megaMenuLinksClick(property.getProperty("hva"));
				strMsg="The user has clicked on 'HVA' link from mega menu for the audience";
				CustomReporter.log(strMsg);
				
				//Step 9:Switch the audience from drop down
				
				rm.webElementSync("idiomspinningcomplete");
				Thread.sleep(4000);
				
				
				PH.selectAudienceFromDropDown(strdefaultaudience);
				strMsg="The user has switched adience to default auidience from HVA page";
				CustomReporter.log(strMsg);
				rm.webElementSync("idiomspinningcomplete");   
				Thread.sleep(4000);
				//Step10:From megamenu , click on digital ranker /TV ranker 
				PH.megaMenuLinksClick(strMediaRanker[k]);
				strMsg="The user has clicked on '"+strMediaRanker[k]+"' link from HVA page megamenu";
				CustomReporter.log(strMsg);
				rm.webElementSync("idiomspinningcomplete");
				
				if(AM.func_VerifyVisibilityOfElement("datepicker", "")){
					strMsg="The '"+strMediaRanker[k]+"' page is visible  ";
					CustomReporter.log(strMsg);
				}
				
				//Step 11:Check the audience selected by default on digital ranker /TV ranker  page
				
				if(PH.verifySelectedAudienceInDropDown(strdefaultaudience)){
					strMsg="The auidence which the user selected before is coming by default for  '"+strMediaRanker[k]+"' ";
					CustomReporter.log(strMsg);
				}
				else{
					strMsg="The audience '"+strMediaRanker+"' is not coming as selected in audience drop down for '"+strMediaRanker[k]+"'";
					CustomReporter.errorLog(strMsg);
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("9069_SelectedAudience", "fail");
				}
				Thread.sleep(5000);
				PH.performAction("audiencedropdown");
				Thread.sleep(3000);					
				
				
				ArrayList<String> audienceList = PH.returnAudiencesInDropDown();
				
				if(audienceList.size()==2){
					if(audienceList.contains(strdefaultaudience) && audienceList.contains(audienceName) ){
						strMsg="Both the audiences are present in drop down for " +strMediaRanker[k];
						CustomReporter.log(strMsg);
					}
					else{
					strMsg="Both the audiences are not present in drop down for " +strMediaRanker[k]+". The present audiences are '"+audienceList.get(0)+ "' and '"+audienceList.get(0)+"'" ;
					CustomReporter.errorLog(strMsg);
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("9069_AudienceInDropDown", "fail");
					}
				}
				else{
					strMsg="The audience drop down does n't have all created audiences";
					CustomReporter.errorLog(strMsg);
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("9069_AudienceInDropDown", "fail");
				}
				Thread.sleep(5000);
				PH.performAction("audiencedropdown");
				Thread.sleep(3000);					
				System.out.println(audienceName);
				PH.selectAudienceFromDropDown(audienceName);
				rm.webElementSync("idiomspinningcomplete");
				
				AM.func_VerifyVisibilityOfElement("datepicker", "");
				Thread.sleep(3000);
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
			rm.captureScreenShot("9069", "fail");
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