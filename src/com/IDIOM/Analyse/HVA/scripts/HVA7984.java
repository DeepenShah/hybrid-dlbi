/********************************************************************
Test Case Name: *HVA_1.A.i_Verify bar chart sort by values
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/7984.aspx
Objective: Objecive: Verify sorting in HVA page
Module: Analyse>HVA
Created By: Amrutha Nair
Date: 22 September 2015
**********************************************************************/

package com.IDIOM.Analyse.HVA.scripts;

import java.util.List;

import org.testng.Assert;

import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.HVA_Page;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;

import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;


public class HVA7984 extends BaseClass {
	
	
	@Test
	public void test_HVA7984() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		AudienceBuilderPage abPage=null;
		
		//****************Test step starts here************************************************	
			try{
				//****************Variables declaration and Initialization****************************	
				String strEmailId = property.getProperty("SuperAdminUser");
				String strPassword = property.getProperty("SuperAdminpassword");
				
				String strSuccessMetrics = property.getProperty("CommonSelectingSuccessMetric");
				String strSuccessMetrics_2 = property.getProperty("CommonSelectingSuccessMetric2");
				String strSuccessMetrics_3 = property.getProperty("CommonSelectingSuccessMetric3");
				
				String strExpectedSuccessMetric1=property.getProperty("CommonExpectedSuccessMetric1");
				String strExpectedSuccessMetric2=property.getProperty("CommonExpectedSuccessMetric2");
				String strExpectedSuccessMetric3=property.getProperty("CommonExpectedSuccessMetric3");
				
				String strAudienceAttributes1=property.getProperty("commonAudienceAttribute4");
	
				String strClientName=property.getProperty("clientName");
				
				//****************Test step starts here************************************************
				
				clientListPage = new ClientList_Page(driver);
				ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
				Login_Page ln = new Login_Page(driver);
				
				//Step1: Launch browser and enter IDIOM Url
				//Step2: Enter valid username and password Click on 'login to idiom' button
				Thread.sleep(3000);
		        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
			    
				Thread.sleep(5000);
				rm.webElementSync("pageload");
				rm.webElementSync("jqueryload");
				
				//Step 3 In client home page, select a client from dropdown
				clientListPage.selectClient(strClientName);
				strMsg = "Selected '"+strClientName+"' client successfully.";
				
				//Step 4 - Click on new project button
				//Step 5 - Enter valid name and description for project and click on Save button
				strProjectName = clientListPage.createNewProject("");
				bProjectCreate=true;
				strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
				
				//step 6 - Click on launch project
			
				clientListPage.launchProject(strProjectName);
				
				rm.webElementSync(pdp.projectName, "visibility");
				Thread.sleep(2000);
				System.out.println("Project dashboard page is open");
				CustomReporter.log("Project dashboard page is open");
				
				//Step 7 - Click on destination link success metrics
				System.out.println("Open Success Metrics page");
				CustomReporter.log("Open Success Metrics page");
				
				pdp.navigateTo(property.getProperty("successMetrics"));
				
				AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

				CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");
						
				if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
					throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###8565_SuccessMetricsTabNotAvailable");
				
				CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
				
				//Step 8 - Verify after success metrics are added
				rm.webElementSync(audienceBuilder.projectedTab,"visibility");
				rm.webElementSync(audienceBuilder.percentagePopulationValue,"visibility");
				
				CustomReporter.log("Select few Success Metrics");
				audienceBuilder.selectMetricByName(strSuccessMetrics);
				
				audienceBuilder.selectMetricByName(strSuccessMetrics_2);
				
				audienceBuilder.selectMetricByName(strSuccessMetrics_3);
				
		
				List<String> strlistOfSelectedSuccessMetricsNames = audienceBuilder.getNameOfAllMetricCards();
				String strActualVal = strlistOfSelectedSuccessMetricsNames.toString().toLowerCase();
				System.out.println("Actual Val - " + strActualVal);
				
				if (!strActualVal.contains(strExpectedSuccessMetric1.trim()))
					throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric1 + "' has not been added successfully. ###7984_didNotAddSuccessMetric");

				if (!strActualVal.contains(strExpectedSuccessMetric2.trim()))
					throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric2 + "' has not been added successfully. ###7984_2_didNotAddSuccessMetric");
				
				if (!strActualVal.contains(strExpectedSuccessMetric3.trim()))
					throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric3 + "' has not been added successfully. ###7984_3_didNotAddSuccessMetric");

				CustomReporter.log("Selected Success Metrics appear in centre area.");
							
				//Step9: Click on Audience Definition link from project dashboard
				CustomReporter.log("Go to Audience Definition page");
				audienceBuilder.performAction("gotoaudiencedefinition");
				
				Thread.sleep(3000);
				
				abPage = new AudienceBuilderPage(driver);
				rm.webElementSync(abPage.allMetricsLabel,"visibility");
				CustomReporter.log("Navigated to audience definition page");
								
				//Step 10 - Select few attributes and add them
				CustomReporter.log("Select an Attribute");
				abPage.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
				abPage.performAction("addattribute");
				
				rm.webElementSync(abPage.projectedTab,"visibility");
				rm.webElementSync(abPage.percentagePopulationValue,"visibility");
				
				//Step 11 - Navigate to HVA page
				CustomReporter.log("Navigate to HVA page");
				pageHeader.megaMenuLinksClick(property.getProperty("hva"));
				
				
				HVA_Page ah = new HVA_Page(driver);
							
				ah.isVisible("slowdown");
				
				if(!rm.webElementSync(ah.behaviour,"visibility"))
					throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###7984_BehaviorsDropdownNotVisible");
									
				//Step 12:Check default sort order for actions
					
				//Checking text present in Sort box is 'Highest Value ' by default
				if(ah.func_getText("Sort").contentEquals("Highest Value")){
					CustomReporter.log("The Sorting label is coming as 'Highest value' by default");
				}
				else{
					CustomReporter.errorLog("The Sorting label is NOT coming as 'Highest value' by default. But coming as :"+ah.func_getText("Sort"));
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("SortHighestValue7984", "fail");
				}
				
				//Checking Sort order by default in HVA Page
				if(ah.func_SortValues("Decending")){
					CustomReporter.log("The cards are sorted in Decending order by default");
				}
				else{
					CustomReporter.errorLog("The sort order is NOt proper be default. Cards are not sorted in decendig order");
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("SortDecendingByDefault7984", "fail");
				}
				
				//Checking background colour for up arrow and down arrow
				if(ah.func_CheckBGColour("UpArrowColor", "rgba(182, 180, 179, 1)")){
					CustomReporter.log("The Up arrow is in light grey colour");
				}
				else{
					CustomReporter.errorLog("The Up arrow is NOT in light grey colour");
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("UpArrowNotInLightGrey7984", "fail");
				}
				
				if(ah.func_CheckBGColour("DownArrowColor", "rgba(118, 116, 115, 1)")){
					CustomReporter.log("The down  arrow is in dark grey colour");
				}
				else{
					CustomReporter.errorLog("The down arrow is NOT in dark grey colour");
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("TheDownArrowNotInDarkGrey7984", "fail");
				}
				
				//Step 13:Click on the sorting button	
				ah.func_ClickElement("UpArrow");
				CustomReporter.log("The user has clicked on sort up arrow");
				
				Thread.sleep(2000);
				if(ah.func_SortValues("Acending")){
					CustomReporter.log("The cards are sorted in Acending order by default");
				}
				else{
					throw new IDIOMException("Cards are not sorted in Acending order. ###7984_CardsAreNotSorted");
				}
				
				//Checking text present in Sort box is 'Lowest Value ' by default
				if(ah.func_getText("Sort").contentEquals("Lowest Value")){
					CustomReporter.log("The Sorting label is coming as 'Lowest Value'");
				}
				else{
					CustomReporter.errorLog("The Sorting label is NOT coming as 'Lowest Value'");
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("NotAsLowestValue7984", "fail");
				}
			
				//Checking background colour for up arrow and down arrow
				if(ah.func_CheckBGColour("UpArrowColor", "rgba(118, 116, 115, 1)")){
					CustomReporter.log("The Up arrow is in dark grey colour");
				}
				else{
					CustomReporter.errorLog("The Up arrow is NOT in dark grey colour");
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("BGColorForUpArrow7984", "fail");
				}
				
				if(ah.func_CheckBGColour("DownArrowColor", "rgba(182, 180, 179, 1)")){
					CustomReporter.log("The down  arrow is in light grey colour");
				}
				else{
					CustomReporter.errorLog("The down arrow is NOT in light grey colour");
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("BGColorForDownArrow7984", "fail");
				}					
				
			}
				
				
			catch(IDIOMException ie){
				BaseClass.testCaseStatus = false;
				String strMsgAndFileName[] = ie.getMessage().split("###");
				System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
				CustomReporter.errorLog("" + strMsgAndFileName[0] + ".");
				rm.captureScreenShot(strMsgAndFileName[1], "fail");
				
			}catch(Exception e){
				
				BaseClass.testCaseStatus = false;
				e.printStackTrace(System.out);
				CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
				rm.captureScreenShot("7984", "fail");
			}finally{
				try{
					
					//Step for Deleting newly created project
					if(bProjectCreate){
						rm.deleteProjectOrAudience(strDetails, true);
						CustomReporter.log("Deleted the Project");
					}
					
					//Step 14 - Click on logout
					pageHeader.performAction("logout");
					strMsg = "Logged out successfully";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
				}catch(Exception ee){
					ee.printStackTrace();
				}
			}
			//****************Test step ends here************************************************
			
			if(!BaseClass.testCaseStatus){
				CustomReporter.errorLog("Test case failed");
				Assert.assertTrue(false);
			}else{
				CustomReporter.log("Test case passed");
			}	
		}
	}  		
			    	
					
					
					
				
				
				
