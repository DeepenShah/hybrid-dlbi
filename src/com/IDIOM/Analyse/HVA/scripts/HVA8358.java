/********************************************************************
Test Case Name: *HVA_VerifyWebAction
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8358.aspx
Objective: Verify web action functionality in hva page
Module: Analyse>HVA
Created By: Amrutha Nair
Date: 24 September 2015
**********************************************************************/

package com.IDIOM.Analyse.HVA.scripts;


import java.util.ArrayList;

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


public class HVA8358 extends BaseClass {
	
	
	@Test
	public void test_HVA8358() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		AudienceBuilderPage abPage=null;
		
		ArrayList<String> chartLabel=new ArrayList<String>();
		ArrayList<String> WebActionDrpDown=new ArrayList<String>();
		ArrayList<String> BehaviourDrpDown=new ArrayList<String>();
		String Behaviour=null;
		int numberOfItems=0;
		String Step10_SelectedData=null;
		
		try
		{
			//****************Test step starts here************************************************
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
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric1 + "' has not been added successfully. ###8358_didNotAddSuccessMetric");

			if (!strActualVal.contains(strExpectedSuccessMetric2.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric2 + "' has not been added successfully. ###8358_2_didNotAddSuccessMetric");
			
			if (!strActualVal.contains(strExpectedSuccessMetric3.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric3 + "' has not been added successfully. ###8358_3_didNotAddSuccessMetric");

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
			
			Thread.sleep(6000);
			
			rm.webElementSync(abPage.projectedTab,"visibility");
			rm.webElementSync(abPage.percentagePopulationValue,"visibility");
			
			//Step 11 - Navigate to HVA page
			CustomReporter.log("Navigate to HVA page");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));
			
			
			HVA_Page ah = new HVA_Page(driver);
			
			ah.isVisible("slowdown");
			
			if(!rm.webElementSync(ah.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###8358_BehaviorsDropdownNotVisible");

			
			
			//Capture chart labels
			chartLabel=ah.func_CaptureList("ChartLabels");
			BehaviourDrpDown=ah.func_CaptureList("Behaviour");
			Behaviour=BehaviourDrpDown.get(0);
		
		//Step 12 - Check the default values for 'Web Action'
			
			if(ah.func_getText("WebAction").contentEquals("Hide (0 items)")){
				CustomReporter.log("By default, the content in web action drop down link is coming as 'Hide (0 items)'");
			}
			else{
				if(ah.func_getText("WebAction").contentEquals("Hide (0 items)")){
					CustomReporter.errorLog("By default, the content in web action drop down link is coming as '"+ah.func_getText("WebAction")+"'which is unexpected");
					BaseClass.testCaseStatus=false;
					rm.captureScreenShot("ByDefaultContentIssue_8358", "fail");
				}
			}
			
		//Step 13 - Click on 'Hide ' drop down
			ah.func_ClickElement("WebAction");
			CustomReporter.log("The user has clicked on 'Web Action");
			
			//Check whether drop down is getting opened and values coming are proper
			if(ah.func_ElementExist("WebAction")){
				CustomReporter.log("The drop down is getting opened on clicking on web action");
				
				if(ah.isVisible("WebActionHeading")){
					if(ah.func_getText("WebAction_FirstRow").contentEquals("WEB ACTION")){
						CustomReporter.log("The first row in drop down is 'WebAction'");
					
					}
					else{
						CustomReporter.errorLog("The first row in drop down is NOT 'WebAction'. It is given as:"+ah.func_getText("WebAction_FirstRow"));
						rm.captureScreenShot("FirstRowDropdownAsWebAction_8358", "fail");
						BaseClass.testCaseStatus = false;
					}
				}
				else{
					CustomReporter.errorLog("Web Action Heading is not present in Dropdown");
					rm.captureScreenShot("WebActionHeadingNotPresent_8358", "fail");
					BaseClass.testCaseStatus = false;
				}
				//capture drop down values
				WebActionDrpDown=ah.func_CaptureList("WebActionDropDown");
				numberOfItems=WebActionDrpDown.size();
				
				if(rm.compareArray(chartLabel,WebActionDrpDown)){
					CustomReporter.log("The hva graph chart values are coming in drop down as expected");
				}
				else{
					CustomReporter.errorLog("The HVA graph chart values and drop down values are not matching");
					rm.captureScreenShot("ChartValuesAndDropdownValuesNotMatching_8358", "fail");
					BaseClass.testCaseStatus = false;
				}
		
		//Step 14 - Select One Web action value from drop down
				
				//WebActionDrpDown=(ArrayList<String>) Arrays.asList(WebActionDrpDown.get(1));
				
				ArrayList<String> TempWebActionDrpDown=new ArrayList<String>();
				TempWebActionDrpDown.add(WebActionDrpDown.get(0));
				ah.func_VerifySelectFromDropDown(TempWebActionDrpDown, "WebAction",0);
				CustomReporter.log("The user has clicked on the web action drop down item");
				
				if(ah.func_getText("WebActionNumberItem").contentEquals("(1 item)"))
				{
					CustomReporter.log("Number of item selected items is updated as :"+ah.func_getText("WebActionNumberItem"));
				}
				else{
					CustomReporter.errorLog("Number of  selected items is wrongly updated as :"+ah.func_getText("WebActionNumberItem"));
					rm.captureScreenShot("NumberOfSelectedItemsWronglyUpdated_8358", "fail");
					BaseClass.testCaseStatus = false;
				}
				
				//Check whether the selected card is there in the chart
				chartLabel=ah.func_CaptureList("ChartLabels");
				
				if(rm.compareArray(chartLabel,TempWebActionDrpDown)){
					CustomReporter.errorLog("The hva graph chart value  populate the selected card value");
					rm.captureScreenShot("CharValuePopulationIssu_8358", "fail");
					BaseClass.testCaseStatus = false;
				}
				else{
					CustomReporter.log("The hva graph chart value doesn't populate the selected card value");;
				}
				
				
		//Step 15 - Select  the drop down and click on another drop down value
				if(numberOfItems>1){
					//WebActionDrpDown=(ArrayList<String>) Arrays.asList(WebActionDrpDown.get(2));
					TempWebActionDrpDown=new ArrayList<String>();
					TempWebActionDrpDown.add(WebActionDrpDown.get(1));
					
					ah.func_ClickElement("WebAction");
					
					ah.func_VerifySelectFromDropDown(TempWebActionDrpDown, "WebAction",2);
					CustomReporter.log("The user has clicked on next web action link from drop down");
					
					if(ah.func_getText("WebActionNumberItem").contentEquals("(2 items)"))
					{
						CustomReporter.log("Number of  selected items is updated as :"+ah.func_getText("WebActionNumberItem"));
					}
					else{
						CustomReporter.errorLog("Number of item selected items is wrongly updated as :"+ah.func_getText("WebActionNumberItem"));
						rm.captureScreenShot("SelectedItemWronglyUpdated_8358", "fail");
						BaseClass.testCaseStatus = false;
					}
					
					
					
					//Check whether the selected card is there in the chart
					chartLabel=ah.func_CaptureList("ChartLabels");
					if(rm.compareArray(chartLabel,TempWebActionDrpDown)){
						
						CustomReporter.errorLog("The hva graph chart value  populate the selected card value");
						rm.captureScreenShot("SelectedItemWronglyUpdated1_8358", "fail");
						BaseClass.testCaseStatus = false;
					}
					else{
						CustomReporter.log("The hva graph chart value doesn't populate the selected card value");
					}
					
				}
				else{
					CustomReporter.log("There is only one chart value present, we  can't perform Step9");
				}
				
		//Step 16 - Deselect selcted drop down values
				//WebActionDrpDown=(ArrayList<String>) Arrays.asList(WebActionDrpDown.get(1),WebActionDrpDown.get(2));
				Thread.sleep(2000);
				
				TempWebActionDrpDown.add(WebActionDrpDown.get(0));
			
				ah.func_ClickElement("WebAction");				

				ah.func_VerifySelectFromDropDown(TempWebActionDrpDown, "WebAction",0);
				CustomReporter.log("The user has deslected both the drop down items from webaction drop down");
				
				if(ah.func_getText("WebActionNumberItem").contentEquals("(0 items)"))
				{
					CustomReporter.log("Number of item selected items is updated as :"+ah.func_getText("WebActionNumberItem")+" on deselecing all selected cards");
				}
				else{
					CustomReporter.errorLog("Number of item selected items is wrongly updated as :"+ah.func_getText("WebActionNumberItem")+" on deselecing all selected cards");
					rm.captureScreenShot("ItemSelectedWronglyUpdated_8358", "fail");
					BaseClass.testCaseStatus = false;
				}
				
				 Step10_SelectedData=ah.func_getText("WebActionNumberItem");
				
				//Check whether the selected card is there in the chart
				chartLabel=ah.func_CaptureList("ChartLabels");
				System.out.println("chartLabel:"+chartLabel);
				System.out.println("TempWebActionDrpDown:"+TempWebActionDrpDown);
				boolean status=true;
				for(int j=0;j<TempWebActionDrpDown.size();j++){
					if(!(chartLabel.contains(TempWebActionDrpDown.get(j)))){
						status=false;
						break;
					}
				}
				
				if(status){
					CustomReporter.log("The hva graph chart values are coming as expected on deselecting all web action drop down items");
				}
				else{
					CustomReporter.errorLog("The hva graph chart values are NOT coming as expected on deselecting all web action drop down items");
					rm.captureScreenShot("ChartValuesNotAsExpected_8358", "fail");
					BaseClass.testCaseStatus = false;
				}
			}
			else{
				CustomReporter.errorLog("The drop down is not getting opened on clicking on web action");
				rm.captureScreenShot("DropdownNotGettingOpened_8358", "fail");
				BaseClass.testCaseStatus = false;
			}
				
				
		//Step 17 - Select other behaviour filter from behviour drop down and check  the default values for 'Web Action'
			if(BehaviourDrpDown.size()>1){
				
				ArrayList<String> TempBehaviourDrpDown=new ArrayList<String>();
				TempBehaviourDrpDown.add(BehaviourDrpDown.get(1));
				ah.func_VerifySelectFromDropDown(TempBehaviourDrpDown, "Behaviour",0);
				CustomReporter.log("The behaviour card:"+TempBehaviourDrpDown.get(0)+" is selected from the drop down");
				
				
					
				chartLabel=ah.func_CaptureList("ChartLabels");
		//Step 18 - Select some  drop down values from web action drop down
				ah.func_ClickElement("WebAction");
				CustomReporter.log("The user has clicked on 'Web Action");
					
				//Check whether drop down is getting opened and values coming are proper
				if(ah.func_ElementExist("WebAction")){
					CustomReporter.log("The drop down is getting opened on clicking on web action");						
						
					//capture drop down values
					WebActionDrpDown=ah.func_CaptureList("WebActionDropDown");
					numberOfItems=WebActionDrpDown.size();
					ArrayList<String> TempWebActionDrpDown1=new ArrayList<String>();
					TempWebActionDrpDown1.add(WebActionDrpDown.get(0));
					if(numberOfItems>1){
					
						TempWebActionDrpDown1.add(WebActionDrpDown.get(1).trim());
					
					}
					ah.func_VerifySelectFromDropDown(TempWebActionDrpDown1, "WebAction",0);
						
					CustomReporter.log("The user has selected some web action drop down items");
				
					if(ah.func_getText("WebActionNumberItem").contentEquals("(2 items)"))
					{
						CustomReporter.log("Number of item selected items is updated as :"+ah.func_getText("WebActionNumberItem"));
					}
					else{
						CustomReporter.errorLog("Number of item selected items is wrongly updated as :"+ah.func_getText("WebActionNumberItem"));
						rm.captureScreenShot("WronglyUpdatedItems1_8358", "fail");
						BaseClass.testCaseStatus = false;
					}
						
					chartLabel=ah.func_CaptureList("ChartLabels");
					if(rm.compareArray(chartLabel,WebActionDrpDown)){
						CustomReporter.errorLog("The hva graph chart value  populate the selected card value");
						rm.captureScreenShot("WrongPopulationOfGraphChartValue_8358", "fail");
					}
					else{
						CustomReporter.log("The hva graph chart value doesn't populate the selected card value");;
					}
				}
				else{
					CustomReporter.errorLog("The drop down is not getting opened on clicking on web action");
					rm.captureScreenShot("DropdownIsNotGettingOpened_8358", "fail");
					BaseClass.testCaseStatus = false;
				}
				
		//Step 19 - Select the first behaviour which was there in step 10
				
				TempBehaviourDrpDown=new ArrayList<String>();
				
				TempBehaviourDrpDown.add(Behaviour);
				ah.func_VerifySelectFromDropDown(TempBehaviourDrpDown, "Behaviour",0);
				CustomReporter.log("The behaviour card:"+TempBehaviourDrpDown.get(0)+" is selected from the drop down");
				
				if(Step10_SelectedData.contentEquals(ah.func_getText("WebActionNumberItem"))){
					CustomReporter.log("The number of items selected in WebAction for "+Behaviour+"is as in Step 10");
				}
				else{
					throw new IDIOMException("The number of items selected in WebAction for "+Behaviour+"is Not as in Step 10. ###8358_ItemsDoNotMatch");
				}
				
				
			}
			else{
				CustomReporter.log("There is only one behaviout filter , so we can't preceed step 12 and 13");
			}	
			
		}
		catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8358", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step 20 - Click on logout
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
