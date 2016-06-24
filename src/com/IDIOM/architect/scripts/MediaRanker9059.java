package com.IDIOM.architect.scripts;



import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>verify that data is changing accordingly when we switch the audiences from media ranker page</p>
 * <p> <b>Objective:</b> Media ranker_VerifyswitchingAudience is proper</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9059.aspx</p>
 * <p> <b>Module:</b> Architect</p>
 * @author Amrutha Nair
 * @since 18-May-2016
 *
 */
public class MediaRanker9059 extends BaseClass {
		
	@Test
	public void verifyAudienceSwitchingInMediaRanker(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			String strAudienceAttributes1=property.getProperty("commonAudienceAttribute2");
			String strAudienceAttributes2=property.getProperty("commonaudienceScenario5");			
			String strdefaultaudience=property.getProperty("defaultaudience");
			String strSuccessMetrics=property.getProperty("commonSuccessMetrics");
			String strMediaRankerItems=property.getProperty("mediaRankerItems");
			//****************Test step starts here************************************************
			
			//Step1 :	Open IDIOM URL and login with valid credentials
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
			
			//Step 3:Click edit for any project/Create a project
			
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
				throw new IDIOMException("Not able to verify new project window###9059-CreateProjectWindow");
										
			//Step 4:	Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###9059-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			
			//Step 5	Click on new audience link,provide name and click on 'create and save'
			 String audienceName = clientListPage.createNewAudience("");
			strMsg = "The audience ' " + audienceName +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			Thread.sleep(2000);
			//Step 7:Click on 'Launch Project' button
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
				rm.captureScreenShot("9059_projectname", "fail");
			}
			
			
			//Step 8:Click on Success metrics link and add few success metrics.
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			AD.selectMetricByName(strSuccessMetrics);
			strMsg="The user has added a few success metrics in success metrics page";
			CustomReporter.log(strMsg);
			
			
			//sTEP 9:Click on 'Audience Definition' tab and add attributes in such a way that population value is <1%
			
			AD.performAction("successmetrics>arrow");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on '>' to navigate to audience definition tab");
			
			
			Thread.sleep(3000);
			
			//Step m6:Add a few filters in audience drop down
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			AD.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(2000);
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes2);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			strMsg="The user has added two queries in defaulr group for the audience '"+audienceName+"'";
			CustomReporter.log(strMsg);

			//step 10:	Switch the attribute from audience drop down
			
			PageHeader PH= new PageHeader(driver);
			PH.selectAudienceFromDropDown(strdefaultaudience);
			strMsg="The user selected default audience '"+strdefaultaudience+"' from the drop down";
			CustomReporter.log(strMsg);
			
			Thread.sleep(3000);
			
			if(AD.getTotalQueryItems()==0){
				strMsg="The default audience '"+strdefaultaudience+"' doesn't contain any query as expected";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The default audience '"+strdefaultaudience+"'  contains  queries eventhough we didn't select any";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("9059_DefaultAudienceContainsQueries", "fail");
			}
			
			
			//Step 11:Select digital raner/tv ranker destination link from megamenu
			ArchitectMediaRankerPage AM= new ArchitectMediaRankerPage(driver);
			String [] strMediaRanker =PH.getSplittedItems(strMediaRankerItems);
			for (int i=0;i<strMediaRanker.length;i++){
				
				System.out.println(strMediaRanker[i]);
				Thread.sleep(3000);
				PH.megaMenuLinksClick(strMediaRanker[i]);
				strMsg="The user has clicked on '"+strMediaRanker[i]+"' link from mega menu for the audience '"+strdefaultaudience+"'";
				CustomReporter.log(strMsg);
				rm.webElementSync("idiomspinningcomplete");
				
				if(AM.func_VerifyVisibilityOfElement("datepicker", "")){
					strMsg="The '"+strMediaRanker[i]+"' page is visible  ";
					CustomReporter.log(strMsg);
				}
				
					//Step 12:Check the default audience selected in  digital raner/tv ranker page
					
					if(PH.verifySelectedAudienceInDropDown(strdefaultaudience)){
						strMsg="The audience '"+strdefaultaudience+"' is coming as selected in audience drop down for '"+strMediaRanker[i]+"' as expected";
						CustomReporter.log(strMsg);
					}
					else{
						
						strMsg="The audience '"+strdefaultaudience+"' is not coming as selected in audience drop down for '"+strMediaRanker[i]+"'";
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("9059_SelectedAudience", "fail");
					}
					
					
					//Step 13:Click on audience drop down and verify the audiences present
					Thread.sleep(10000);
					PH.performAction("audiencedropdown");
					strMsg="The user has clicked on audience drop down link from page header";
					CustomReporter.log(strMsg);
					
					ArrayList<String> audienceList=PH.returnAudiencesInDropDown();
					
					if(audienceList.size()==2){
						if(audienceList.contains(strdefaultaudience) && audienceList.contains(audienceName) ){
							strMsg="Both the audiences are present in drop down for " +strMediaRanker[i];
							CustomReporter.log(strMsg);
						}
						else{
						strMsg="Both the audiences are not present in drop down for " +strMediaRanker[i]+". The present audiences are '"+audienceList.get(0)+ "' and '"+audienceList.get(0)+"'" ;
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("9059_AudienceInDropDown", "fail");
						}
					}
					else{
						strMsg="The audience drop down does n't have all created audiences";
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("9059_AudienceInDropDown", "fail");
					}
					
					
					//Step 14:Switch the audience 
					PH.performAction("audiencedropdown");
					Thread.sleep(3000);
					
					
					PH.selectAudienceFromDropDown(audienceName);
					rm.webElementSync("idiomspinningcomplete");
					
					AM.func_VerifyVisibilityOfElement("datepicker", "");
					Thread.sleep(10000);
					strMsg="The audience '"+audienceName+"' is selected in audience drop down for '"+strMediaRanker[i]+"'";
					CustomReporter.log(strMsg);
					
					if(PH.verifySelectedAudienceInDropDown(audienceName)){
						strMsg="The audience '"+audienceName+"' is coming as selected in audience drop down for '"+strMediaRanker[i]+"' as expected";
						CustomReporter.log(strMsg);
					}
					else{
						
						strMsg="The audience '"+audienceName+"' is not coming as selected in audience drop down for '"+strMediaRanker[i]+"'";
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("9059_SelectedAudience", "fail");
					}
					
					PH.performAction("audiencedropdown");
					Thread.sleep(3000);					
					
					
					audienceList=PH.returnAudiencesInDropDown();
					
					if(audienceList.size()==2){
						if(audienceList.contains(strdefaultaudience) && audienceList.contains(audienceName) ){
							strMsg="Both the audiences are present in drop down for " +strMediaRanker[i];
							CustomReporter.log(strMsg);
						}
						else{
						strMsg="Both the audiences are not present in drop down for " +strMediaRanker[i]+". The present audiences are '"+audienceList.get(0)+ "' and '"+audienceList.get(0)+"'" ;
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("9059_AudienceInDropDown", "fail");
						}
					}
					else{
						strMsg="The audience drop down does n't have all created audiences";
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("9059_AudienceInDropDown", "fail");
					}
					Thread.sleep(5000);
					PH.performAction("audiencedropdown");
					Thread.sleep(3000);					
					
					PH.selectAudienceFromDropDown(strdefaultaudience);
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
			rm.captureScreenShot("9059", "fail");
		}finally{
			try{
				
				//Deleting newly created project
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");
					
					clientListPage.func_PerformAction("Close Project");
					
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
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