package com.IDIOM.architect.scripts;

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

/********************************************************************
<p>	<b>Test Case Name:<b> Media Ranker Overview_1.b.i.3_Audience & Universe Tabs_.b_Verify_Universe_Tab_Click.
<p>	<b>Objective:<b> Media Ranker Overview_1.b.i.3_Audience & Universe Tabs_.b_Verify_Universe_Tab_Click.
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8347.aspx
<p>	<b>Module:<b> Media Ranker
@author:<b> Abhishek Deshpande
@since:<b> 23-May-2016
**********************************************************************/
public class MediaRanker8347 extends BaseClass {
	
	@Test
	public void verifyyDefaultTab(){
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
			String strAudienceAttributes1=property.getProperty("commonAudienceAttribute2");
			String strAudienceAttributes2=property.getProperty("commonaudienceScenario5");		
			String strDefaultTab=property.getProperty("mediaRankerDefaultTab");	
			String strMediaRankerItems=property.getProperty("mediaRankerItems");
			//****************Test step starts here************************************************
			
			//Step1 :	Launch browser and enter IDIOM Url
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
			//Step 2:	Enter valid username and password Click on 'login to idiom' button
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 3:In client home page, select a client from dropdown
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
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Fill the project
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###8347-CreateProjectWindow");
										
			//Step 5:Enter valid name and description for project and click on Save button		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8347-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
		
			//Step 7:Click on launch project
			clientListPage.func_PerformAction("Launch Project");
			strMsg = "Clicked on Launch Project";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Waiting till page get loaded
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			if(!pdp.isVisible("projectname", strProjectName))
				throw new IDIOMException("The selected project name is not displayed in project home page###8347-ProjectNotFound");
			
			CustomReporter.log("The project home page has been launched and it is getting updated with selected project name");		
			
			
			//Step 7:Click on Success Metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);				
			
			
			//Step 9:Click on Audience Definition tab			
			AD.performAction("successmetrics>arrow");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on '>' to navigate to audience definition tab");			
			Thread.sleep(3000);
			
			//Step 9:Select few attributes and add them			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			AD.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(2000);
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes2);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			strMsg="The user has added two queries in defaulr group for the project  '"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			
			//step 10:	Select any channel from Media Rankers from Mega Menu			
			PageHeader PH= new PageHeader(driver);			
			ArchitectMediaRankerPage AM= new ArchitectMediaRankerPage(driver);
			String [] strMediaRanker =PH.getSplittedItems(strMediaRankerItems);
			for (int k=0;k<strMediaRanker.length;k++){
				
				System.out.println(strMediaRanker[k]);
				Thread.sleep(3000);
				PH.megaMenuLinksClick(strMediaRanker[k]);
				strMsg="The user has clicked on '"+strMediaRanker[k]+"' link from mega menu for the project: "+strProjectName;
				CustomReporter.log(strMsg);
				rm.webElementSync("idiomspinningcomplete");
				
				if(AM.func_VerifyVisibilityOfElement("datepicker", "")){
					strMsg="The '"+strMediaRanker[k]+"' page is visible  ";
					CustomReporter.log(strMsg);
				}
				
					//Step 11: Verify the Plot is defaulted to which tab
					Thread.sleep(5000);
					
					if(AM.defaultTab.getText().trim().toLowerCase().contentEquals(strDefaultTab)){
						strMsg="The default tab selected on landing '"+strMediaRanker[k]+"' page is '"+strDefaultTab+"'";
						CustomReporter.log(strMsg);
					}
					
					else{
						strMsg="The default tab selected on landing '"+strMediaRanker[k]+"' page is NOT '"+strDefaultTab+"'";
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("8346_DefaultTab_"+k, "fail");
					}
					
				//Verify Universe tab click
				AM.func_ClickOnElement("chartheaderunirversetab");
				CustomReporter.log("Clicked on Universe tab");	
				
				//Verify Scatter plot
				AM.func_VerifyVisibilityOfElement("scatteredplot","");
				
				//Bubbles are displayed in scatter plot
				int count = AM.getMetricPlotBubbleCount("universe");
				if(!(AM.func_VerifyElementExist("metricbubbleplot") && count<1))
					throw new IDIOMException("Bubbles are not displayed in Universe tab###8347-BubblesNotDisplayed");
				
				CustomReporter.log("The number of Bubbles displayed in scatter plot are - "+count);
				
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
			rm.captureScreenShot("8347", "fail");
		}finally{
			try{
				
				//Deleting newly created project
				if(bProjectCreate){
					///////////pageHeader.navigateTo("projecthomepage");
					rm.deleteProjectOrAudience(strDetails, true);
					Thread.sleep(2000);
					
					CustomReporter.log("Deleted the projects");
				
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
