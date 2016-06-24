package com.IDIOM.ShowAudience;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.IDIOM.pages.ShowAudiencePage;
import com.reports.CustomReporter;



import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>*Show_Audience_Widget_Verify it on Media Ranker Page</p>
 * <p> <b>Objective:</b>:Verify Show Audience Widget on Media Ranker Page</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9005.aspx/p>
 * <p> <b>Module:</b> Show Audience</p>
 * @author Amrutha Nair
 * @since 17-June-2016
 *
 */
public class ShowAudience9005 extends BaseClass {
		
	@Test
	public void verifyShowAudiencePanelInMediaRanker(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
	
		String strDetails=null;
		try{
			
			
		
			//****************Variables declaration and Initialization****************************	
		
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			String strMetrics=property.getProperty("commonSuccessMetrics");
			String strAudiences=property.getProperty("cmmonAudienceAttribute");
			String strMediaRankerItems=property.getProperty("mediaRankerItems");
			
			//****************Test step starts here************************************************
			
			//Step1 :			Launch browser and enter IDIOM Url
			//Step 2:Enter valid username and password Click on 'login to idiom' button
			
			//Step 3:In client home page, select a client from dropdown
			loginToSelectClient();
			clientListPage=new ClientList_Page(driver);
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
			
			//Fill the project
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###9005-CreateProjectWindow");
										
			//Step 5:Enter valid name and description for project and click on Save button
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###9005-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
						
			
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
				rm.captureScreenShot("9005_projectname", "fail");
			}
			
			
			
			//Step 7:Navigate to success metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			
			//Step8:Define few success metrics or do not define
			
			AD.selectMetricByName(strMetrics);
			strMsg="The user has added a few success metrics ";
			CustomReporter.log(strMsg);
			
			
			
			//Step 9:Click on Audience Definition link from project dashboard
			
			AD.performAction("gotoaudiencedefinition");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on Audience Definition tab");
			
			
			Thread.sleep(3000);
		
			
			//Step9:Select few attributes and add them
			AD.selectAttributeOnAudienceDefinition(strAudiences);
			AD.performAction("addattribute");
			
			strMsg="The user has selected a query in audience definition page";
			CustomReporter.log(strMsg);
			
			//Step 10:Verify data for following in both Projected and Actual Tabs and note down:- Population- Optimal Audience (Indicator position)- Success Metrics
			Thread.sleep(5000);
			AD.goToFirstLevelForMetricOrAttribute();
			AD.isVisible("populationpercentagevalue", "");
			ArrayList<String> metricsProjected= AD.successMetricsLeftSideGetAllAttributes("projected");
			int PopulationPercentProjected=AD.getPopulationPercentage();
			double optProjectedAdudience =AD.getOptimalAudiencePercentage();
			AD.performAction("actualpopulationtab");
			Thread.sleep(5000);
			double optActualAdudience =AD.getOptimalAudiencePercentage();
			strMsg="The projectedpopulation percentage now is :"+PopulationPercentProjected;
			CustomReporter.log(strMsg);
			int PopulationPercentActual=AD.getPopulationPercentage();
			ArrayList<String> metricsActual= AD.successMetricsLeftSideGetAllAttributes("actual");
			
			strMsg="The user has captured population, success metrics , and optimal auidence from both projected and actual tabs";
			CustomReporter.log(strMsg);
			
			
			//Step 11:	Select different Channels under Media Rankers section from Mega Menu
			//Step 12:Click on Show Audience to open Show Audience Widget
			//Step 13:Click on Show Audience to open Show Audience Widget
			
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
				
				if(AM.func_VerifyVisibilityOfElement("slowtvranker", "")){
					strMsg="The '"+strMediaRanker[k]+"' page is visible  ";
					CustomReporter.log(strMsg);
				}
				
					
				Thread.sleep(5000);
				//Click on the "show audience" button.
				ShowAudiencePage SH=new ShowAudiencePage(driver);
				Thread.sleep(5000);
				
				
				AM.func_ClickOnElement("showaudience");
				
				strMsg="The user has clicked on 'Show Audience Button from '"+strMediaRanker[k]+"'";
				CustomReporter.log(strMsg);
				
				
				if(SH.isVisible("populationpane")){
					strMsg="Show audience panel is visible in '"+strMediaRanker[k]+"'";
					CustomReporter.log(strMsg);
				}
				
				//Compare following data in both Projected and Actual Tabs with previously noted data in step 10:
				
				
				ArrayList<String> SHmetricsProjected= SH.getSuccessMetricsDetails();
				int SHPopulationPercentProjected=SH.getPopulationPercentage();
				double SHoptProjectedAdudience =SH.getOptimalAudiencePercentage();
				AD.performAction("actualpopulationtab");
				Thread.sleep(5000);
				double SHoptActualAdudience =SH.getOptimalAudiencePercentage();
				strMsg="The population percentage in Show audience panel for projected tab is :"+SHPopulationPercentProjected;
				CustomReporter.log(strMsg);
				int SHPopulationPercentActual=SH.getPopulationPercentage();
				ArrayList<String> SHmetricsActual= SH.getSuccessMetricsDetails();
				strMsg="The population percentage in Show audience panel for actual tab is :"+SHPopulationPercentActual;
				CustomReporter.log(strMsg);
				strMsg="The user has captured population, success metrics , and optimal auidence from both projected and actual tabs in '"+strMediaRanker[k]+"'";
				CustomReporter.log(strMsg);
				
				
				boolean status=true;
				if(PopulationPercentProjected==SHPopulationPercentProjected){
					strMsg="The projectedpopulation percentage in Show audience panel of page'"+strMediaRanker[k]+"' is matching with the same in success metrics right panel. .The value is:"+SHPopulationPercentProjected;
					CustomReporter.log(strMsg);
				}
				
				else{
					strMsg="The projectedpopulation percentage in Show audience panel of page'"+strMediaRanker[k]+"' is not matching with the same in success metrics right panel. Show audience value is '"+SHPopulationPercentProjected+"' success metrics value is '"+PopulationPercentProjected+"'";
					CustomReporter.errorLog(strMsg);
					status=false;
				}
				
				if(PopulationPercentActual==SHPopulationPercentActual){
					strMsg="The actual population percentage in Show audience panel of page'"+strMediaRanker[k]+"' is matching with the same in success metrics right panel.The value is:"+SHPopulationPercentActual;
					CustomReporter.log(strMsg);
				}
				
				else{
					strMsg="The actual population percentage in Show audience panel  of page'"+strMediaRanker[k]+"' is not matching with the same in success metrics right panel. Show audience value is '"+SHPopulationPercentActual+"' success metrics value is '"+PopulationPercentActual+"'";
					CustomReporter.errorLog(strMsg);
					status=false;
				}
				
				if(optActualAdudience==SHoptActualAdudience){
					strMsg="The actual optimal audience  in Show audience panel of page'"+strMediaRanker[k]+"' is matching with the same in success metrics right panel";
					CustomReporter.log(strMsg);
				}
				
				else{
					strMsg="The actual optimal audience  in Show audience panel of page'"+strMediaRanker[k]+"' is not matching with the same in success metrics right panel";
					CustomReporter.errorLog(strMsg);
					status=false;
				}
				
				if(optProjectedAdudience==SHoptProjectedAdudience){
					strMsg="The projected optimal audience  in Show audience panel of page'"+strMediaRanker[k]+"' is matching with the same in success metrics right panel";
					CustomReporter.log(strMsg);
				}
				
				else{
					strMsg="The projected optimal audience  in Show audience panel of page'"+strMediaRanker[k]+"' is not matching with the same in success metrics right panel";
					CustomReporter.errorLog(strMsg);
					status=false;
				}
				boolean flag=true;
				
				if(SHmetricsActual.size()==metricsActual.size()){
					for(String actual:SHmetricsActual){
						if(!(metricsActual.contains(actual))){
							status=false;
							flag=false;
							strMsg="The actual tab success metrics in Show audience panel of page'"+strMediaRanker[k]+"' are not  matching with the same in success metrics right panel";
							CustomReporter.errorLog(strMsg);
							break;
						}
					}
					if(flag){
						strMsg="The actual tab success metrics in Show audience panel of page'"+strMediaRanker[k]+"' are   matching with the same in success metrics right panel . value is :"+SHmetricsActual;
						CustomReporter.log(strMsg);
					}
					
					
				}
				else{
					strMsg="The actual tab success metrics in Show audience panel of page'"+strMediaRanker[k]+"' are not  matching with the same in success metrics right panel";
					CustomReporter.errorLog(strMsg);
				}
				flag=true;
				if(SHmetricsProjected.size()==metricsProjected.size()){
					for(String projected:SHmetricsProjected){
						if(!(metricsProjected.contains(projected))){
							status=false;
							flag=false;
							strMsg="The projected tab success metrics in Show audience panel  of page'"+strMediaRanker[k]+"' are not  matching with the same in success metrics right panel";
							CustomReporter.errorLog(strMsg);
							break;
						}
					}
					if(flag){
						strMsg="The projected tab success metrics in Show audience panel of page'"+strMediaRanker[k]+"' are   matching with the same in success metrics right panel . value is :"+SHmetricsProjected;
						CustomReporter.log(strMsg);
					}
				}
				else{
					strMsg="The projected tab success metrics in Show audience panel of page'"+strMediaRanker[k]+"' are not  matching with the same in success metrics right panel";
					CustomReporter.errorLog(strMsg);
				}
				
				
				if (status){
					strMsg="The population, optimal audience and success metrics in both projected and actual tabs in Show Audience panel of page'"+strMediaRanker[k]+"' are matching with the data in success metrics right panel";
					CustomReporter.log(strMsg);
				}
				else{
					strMsg="There is data mismatch in show audience panel in page '"+strMediaRanker[k]+"'";
					CustomReporter.errorLog(strMsg);
					rm.captureScreenShot("9005_DataMismatch_"+k, "fail");
					BaseClass.testCaseStatus=false;
					
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
				rm.captureScreenShot("9005", "fail");
			}finally{
				try{
					
					//Deleting newly created project
					if(bProjectCreate){
						
						rm.deleteProjectOrAudience(strDetails, true);
						Thread.sleep(2000);
						
						CustomReporter.log("Deleted the project");
					
					}
					
					//Click on logout				
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
