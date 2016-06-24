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
 * <p> <b>Test Case Name:</b>*Media Ranker Overview_1.b.i.2Display and Timeframe_.a.ii_Verify_Changing_The_Display_Order_Of_Groups</p>
 * <p> <b>Objective:</b>  Verify changing the display order of categories in drop down</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8330.aspx</p>
 * <p> <b>Module:</b> Architect</p>
 * @author Amrutha Nair
 * @since 18-May-2016
 *
 */
public class MediaRanker8330 extends BaseClass {
		
	@Test
	public void verifyDisplayOrderOfCategories(){
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
			String strDisplayItems=property.getProperty("displayItems");
			
			String strMediaRankerItems=property.getProperty("mediaRankerItems");
			//****************Test step starts here************************************************
			
			//Step1 :		Launch browser and enter IDIOM Url
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
			//Step 2:	Enter valid username and password Click on 'login to idiom' button
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 3: In client home page, select a client from dropdown
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
			
			//Step 4:Click on new project button
			
			//create New Project
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step 5:Enter valid name and description for project and click on Save button
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###8330-CreateProjectWindow");
										
		
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8330-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			
			
			Thread.sleep(2000);
			//Step 6:Click on launch project
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
				rm.captureScreenShot("8330_projectname", "fail");
			}
			
			
			//Step 7:	Navigate to success metrics page
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
		
			
			//sTEP 8:	Click on Audience Definition tab
			
			AD.performAction("successmetrics>arrow");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on '>' to navigate to audience definition tab");
			
			
			//Step9:Select few attributes and add them
			Thread.sleep(2000);
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			AD.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(2000);
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes2);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			strMsg="The user has added two queries in defaulr group ";
			CustomReporter.log(strMsg);
			
			
			//Step 10:Select any channel from Media Rankers from Mega Menu
			PageHeader PH= new PageHeader(driver);
			ArchitectMediaRankerPage AM= new ArchitectMediaRankerPage(driver);
			String [] strMediaRanker =PH.getSplittedItems(strMediaRankerItems);
			ArrayList<String> disPlayDropDown =new ArrayList<>();
			ArrayList<String> disPlayDropDownExpected =new ArrayList<>();
			//String tempDisplayItem=null;
			String [] strDisplayItem =strDisplayItems.split("\\|");
			for (int i=0;i<strMediaRanker.length;i++){
				
				System.out.println(strMediaRanker[i]);
				Thread.sleep(3000);
				PH.megaMenuLinksClick(strMediaRanker[i]);
				strMsg="The user has clicked on '"+strMediaRanker[i]+"' link from mega menu";
				CustomReporter.log(strMsg);
				rm.webElementSync("idiomspinningcomplete");
				
				if(AM.func_VerifyVisibilityOfElement("datepicker", "")){
					strMsg="The '"+strMediaRanker[i]+"' page is visible  ";
					CustomReporter.log(strMsg);
				}
				
				
				
				//Step 11:	Click on Display dropdown and Verify changing the display order of categories
				Thread.sleep(5000);
				AM.func_ClickOnElement("displaydropdown");
			
				strMsg="The user has clicked on display drop down for the page '"+strMediaRanker[i]+"' ";
				CustomReporter.log(strMsg);
				
				 disPlayDropDown= AM.getDisplayDropDownItems();
				 
				 for(int j=0;j<strDisplayItem.length;j++){
					 if(strDisplayItem[j].split("_")[0].contains(strMediaRanker[i])){
						 for(int k=0;k<strDisplayItem[j].split("_")[1].split(",").length;k++){
							 disPlayDropDownExpected.add(strDisplayItem[j].split("_")[1].split(",")[k]);
						 }
						 break;
					  }
					 }
				 
				 for(String tempDisplayItem : disPlayDropDown){
					    if(disPlayDropDownExpected.contains(tempDisplayItem.toLowerCase())){
					    	strMsg="The display item  '"+tempDisplayItem+"' is present in the application for '"+strMediaRanker[i]+"'";
							CustomReporter.log(strMsg);
					    }else{
					    	strMsg="The display item  '"+tempDisplayItem+"' is not present in the application for '"+strMediaRanker[i]+"'";
							CustomReporter.errorLog(strMsg);
							BaseClass.testCaseStatus=false;
							rm.captureScreenShot("8330_displayDropDown_"+i, "false");
					       
					    }
					}
				 
				 
				 
				 //Select the display drop down
				 for(int m=0;m<disPlayDropDown.size();m++)
				 {
					 AM.func_SelectValueFromDropdown("display",disPlayDropDown.get(m));
					 strMsg="The user has selected display drop down item:"+disPlayDropDown.get(m)+" for '"+strMediaRanker[i]+"'";
					 CustomReporter.log(strMsg);
					 rm.webElementSync("idiomspinningcomplete");
					
					 if(!AM.func_VerifyVisibilityOfElement("displaydropdown", ""))	{
							strMsg="Fails to select display drop down '"+disPlayDropDown.get(m)+" for '"+strMediaRanker[i]+"'";
							CustomReporter.errorLog(strMsg);
							BaseClass.testCaseStatus=false;
							rm.captureScreenShot("8330_displayDropDownIssue_"+i, "false");
					       
						
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
			rm.captureScreenShot("8330", "fail");
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