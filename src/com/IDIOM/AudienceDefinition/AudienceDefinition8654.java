package com.IDIOM.AudienceDefinition;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>Audience 2.0 : Normal Application User Journery</p>
 * <p> <b>Objective:</b> Verify user journey for normal app user </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8654.aspx</p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Amrutha Nair
 * @since 12 May 2016
 *
 */
public class AudienceDefinition8654 extends BaseClass {

	@Test
	public void verifyNormalUserJourney(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("GeneralUserEmail");
			String strPassword = property.getProperty("GeneralUserPassword");
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			String strCenterAreaExpectedMsg=property.getProperty("centerAreaExpectedMsg8572");
			String expectedCategories=property.getProperty("SubCategoryCommon");
			String strAudienceAttributes1=property.getProperty("audiencesScenario3");
			String strAudienceAttributes=property.getProperty("audienceScenario5");
			String strMetricsCategory=property.getProperty("successCategory");
			String strSuccessMetrics=property.getProperty("successMetricScenario3");
			String strSuccessMetrics1=property.getProperty("succeeMetric8658");
			String strmetricsToBeRemoved=property.getProperty("metricsToBeRemoved8658");
			String strSearchValue=property.getProperty("searchValue8658");
			String defaultQueryOrSubGroupLogic=property.getProperty("defaultQueryOrSubGroupLogic");
			//****************Test step starts here************************************************
			
			//Step1 :	Open IDIOM URL and login with valid credentials
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
			
			
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 2:	Select any existing client from client dropdown
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
			
			//Step 3: Click on any project/Create a project and launch the same
			
			//create New Project
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###SmokeScenario3-CreateProjectWindow");
										
			//If audience tab is not found then everything is good
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###SmokeScenario3-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			
			
			Thread.sleep(3000);
			clientListPage.func_PerformAction("Launch Project");
			strMsg = "Clicked on Launch Project";
			CustomReporter.log(strMsg);
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
				
			}
			
			
			//Step 4:Click on destination link success metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			Thread.sleep(2000);
			
			//Step 5:Success Metric: Verify default message
			
			if(AD.getSuccessMetricsMsgOfCenterArea().trim().contentEquals(strCenterAreaExpectedMsg.trim())){
				strMsg="The message coming in central area is as expected . ie:"+strCenterAreaExpectedMsg;
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The message coming in central area is not as expected .";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("successmetricscentralareamessage","fail");
				BaseClass.testCaseStatus=false;
			}
			
			
			
			//Step 6:Success Metric: Select any Metric
			AD.selectMetricByName(strMetricsCategory);
			Thread.sleep(2000);
			strMsg="The user has clicked on success metrics category:"+strMetricsCategory;
			CustomReporter.log(strMsg);
			
			List <String> strSubCategories=AD.returnSuccessMetricsSubCategories();
			if(AD.verifySubcategoriesForSelectedSuccessMetrics(strSubCategories, expectedCategories)){
				strMsg="The sub categories getting populated on selecting '"+strMetricsCategory+"' are expected. ie '"+expectedCategories+"'";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The sub categories getting populated on selecting '"+strMetricsCategory+"' are NOT expected. The expected sub categories are'"+expectedCategories+"'";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_SubCategoriesInSuccessMetrics", "fail");
			}
			
			
			//Step 7:Success Metric: Select any category of success metric
			
			AD.selectMetricByName(strSuccessMetrics);
			
			strMsg="The user has selected a success metrics category from the list";
			CustomReporter.log(strMsg);
			
			
			Thread.sleep(2000);
			if(AD.getCountForSuccessMetricsOrAudienceDefinition()==1){
				strMsg="The bottom navigation bar is getting updated properly with the number of cards selected";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The bottom navigation bar is not getting selected properly with the number of cards selected";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_NumberofslectedCards", "fail");
			}
			
			if(AD.isVisible("removealllink")){
				strMsg="'Remove all' link is visible in bottom navigation bar";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="'Remove all' link is NOT visible in bottom navigation bar";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_RemoveAll", "fail");
			}
			
			if(AD.getCountForSubCategoriesHavingCorssIcon()==1){
				strMsg="The selected  sub category is having cross sign";
				CustomReporter.log(strMsg);
			}
			else if( AD.getCountForSubCategoriesHavingCorssIcon()==0){
				strMsg="The selected  sub category is NOT having cross sign";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_CrossSignissue", "fail");
			}
			
			if(AD.getTotalMetricCards()==1){
				strMsg="The selected  metric card is coming in middle";
				CustomReporter.log(strMsg);
			}
			
			else if( AD.getTotalMetricCards()==0){
				strMsg="The selected  metric card is NOT coming in middle";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_SelectedMetricCard", "fail");
			}
			
			if(AD.getBgColorOfSuccessMetricCard_s()){
				
				strMsg="The selected  metric card getting updated with proper BG colour";
				CustomReporter.log(strMsg);
			}
			else{
				
				strMsg="The selected  metric card not getting updated with proper BG colour";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_SelectedMetricCard", "fail");
			}
			
			
			//Step 8:Success Metric: Click "Remove All" link
			AD.removeAllSuccessMetricsOrAudienceDefinitionQueries();
			strMsg="The user has clicked on remove all link";
			CustomReporter.log(strMsg);
			Thread.sleep(4000);
			//Step 9:Success Metric: Select one category from one Metric and another category from different category say "Transaction
			AD.selectMetricByName(strSuccessMetrics1);
			strMsg="The user has selected one card from one metric card and another category from a different metric card";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			if(AD.getTotalMetricCards()==2){
				strMsg="All the selected  metric card is coming in middle";
				CustomReporter.log(strMsg);
			}
			
			else if( AD.getTotalMetricCards()!=2){
				strMsg="All the selected  metric card is NOT coming in middle";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_SelectedMetricCard", "fail");
			}
			
			
			//Step 10:Success Metric: Click/On hover any card from Center area of Success Metric
			//Step 11:Click on "X" sign
			AD.removeSuccessMetricCardByName(strmetricsToBeRemoved);
			Thread.sleep(2000);
			strMsg="The user has clicked on 'X' sign on the card to remove the same";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			List<String> strlistOfSelectedSuccessMetricsNames = AD.getNameOfAllMetricCards();
			
			String strActualVal = strlistOfSelectedSuccessMetricsNames.toString().toLowerCase();	
			
		
			if (strActualVal.contains(strmetricsToBeRemoved.trim())){
				strMsg="Success Metric has not been removed successfully.";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_DidNotRemoveSuccessMetric", "fail");
			}
			else{
				strMsg="The selected success metrics is removed successfully";
				CustomReporter.log(strMsg);
			}
			
			//Step 12:Success Metric: Search name of success metric name like "Transaction"
			AD.performAction("searchbutton");
			strMsg="The user has clicked on search icon";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			
			//Step 13:Success Metric: Search for categories of any card 
			AD.inputSearchValue(strSearchValue);
			strMsg="The user input text '"+strSearchValue+"' in search text box";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			if(AD.verifySuccessMetricsSearchfunctionality(strSearchValue)){
				strMsg="The search functionality is working as expected in success metrics page";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The search functionality is NOT working as expected in success metrics page";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_SearchFunctionality", "fail");
			}
			
			//Step 14:Audience Definition: Click on Audience Definition
			AD.performAction("gotoaudiencedefinition");
			Thread.sleep(2000);
			strMsg="The user has clicked on audience definition tab";
			CustomReporter.log(strMsg);
			
			//Step 15:Audience Definition: default information on tab
			if(AD.isVisible("defaultgroup", "")){
				strMsg="The default group is present in audience definition page";
				
				CustomReporter.log(strMsg);
			}
			
			else{
				strMsg="The default group is NOT  present in audience definition page";
				
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8654NoDefaultGroup", "fail");
			}
			
			if(AD.isVisible("addgrouplink", "")){
				strMsg="The add group link is present in audience definition page";
				
				CustomReporter.log(strMsg);
			}
			
			else{
				strMsg="The add group link is NOT  present in audience definition page";

				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8654NoDefaultGroup", "fail");
			}
			
			
			//Step 16:Audience Definition: Select any attribute from left side
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			strMsg="The user has added the query to the default group";
			AD.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(3000);
			CustomReporter.log(strMsg);
			if(AD.getCountForSuccessMetricsOrAudienceDefinition()==1){
				strMsg="The bottom navigation bar is getting updated properly with the number of queries selected";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The bottom navigation bar is not getting selected properly with the number of queries selected";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_Numberofqueries", "fail");
			}
			
			
			//Step 17:Audience Definition: Select another attribute
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes);
			strMsg="The user has added another query to the default group";
			AD.performAction("addattribute");
			Thread.sleep(2000);
			AD.goToFirstLevelForMetricOrAttribute();
			
			Thread.sleep(3000);
			CustomReporter.log(strMsg);
			if(AD.getCountForSuccessMetricsOrAudienceDefinition()==2){
				strMsg="The bottom navigation bar is getting updated properly with the number of queries selected";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The bottom navigation bar is not getting selected properly with the number of queries selected";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_Numberofqueries", "fail");
			}
			
			String strQueryLogic=AD.getQueryLogic("1.2");
			
			if(strQueryLogic.toLowerCase().contentEquals(defaultQueryOrSubGroupLogic.toLowerCase())){
				strMsg="The default query for the added query is '"+defaultQueryOrSubGroupLogic+"'";
				CustomReporter.log(strMsg); 
				
			}
			else{
				strMsg="The default query for the added query is not '"+defaultQueryOrSubGroupLogic+"'";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_QueryLogicdefault", "fail");
			
			}
			//Step 18:Select "AND" condition for another added card
			AD.clickToChangeQueryLogic("1.2");
			Thread.sleep(2000);
			strMsg ="The user has clicked on '"+strQueryLogic+"' to change the query logic for one query";
			CustomReporter.log(strMsg); 
			String strLogic=null;
			if(defaultQueryOrSubGroupLogic.toLowerCase().contentEquals("or")){
				strLogic="and";
			}
			else{
				strLogic="or";
			}
			
			 strQueryLogic=AD.getQueryLogic("1.2");
			 
			if(strQueryLogic.toLowerCase().contentEquals(strLogic)){
				strMsg="The query logic got changed to '"+strLogic+"'";
				CustomReporter.log(strMsg); 
			}
			
			else{
				strMsg="The query logic didnt get changed to '"+strLogic+"'";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_QueryLogicChange", "fail");
			}
			
			//Step 19:Audience Definition: Click on "x" sign from any selected attribute
			
			//Get first query
			String strQuery=AD.getQueryWithLogic("1.1");
			System.out.println("strQuery:"+strQuery);
			AD.deleteQueryItem("1.1");
			Thread.sleep(3000);
			strMsg="The user has clicked on delete icon for the query";
			CustomReporter.log(strMsg); 
			
			if(AD.getQueryWithLogic("1.1").contentEquals(strQuery)){
				strMsg="The query is not got deleted successfully";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_QueryNotgotdeleted", "fail");
			}
			
			else{
				strMsg="The query got deleted successfully";
				CustomReporter.log(strMsg); 
			}
			
			
			//Step 20:Audience Definition: Click on Add New Group
			int grpCount=AD.getGroupCount();
			AD.addNewGroup();
			Thread.sleep(2000);
			strMsg="The user clicked on 'add group ' link";
			CustomReporter.log(strMsg); 
			
			if(AD.getGroupCount()==(grpCount+1)){
				strMsg="The new group has been added in the audiencebuilder page";
				CustomReporter.log(strMsg); 
			}
			
			else{
				strMsg="The new group has not been added in the audiencebuilder page";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_AddGroupissue", "fail");
			}
			
			
			//Step 21:Audience Definition: Click on Add New Sub Group
			int intsubgrp=AD.getSubGroupCount("1");
			AD.addSubGroup("1");
			Thread.sleep(2000);
			strMsg="The user added a sub group in the main group";
			CustomReporter.log(strMsg); 
			
			if(AD.getSubGroupCount("1")==(intsubgrp+1)){
				strMsg="The sub group got added to the main group";
				CustomReporter.log(strMsg); 
				
			}
			else{
				strMsg="The sub  group has not been added in the main group";
				CustomReporter.errorLog(strMsg);
			    BaseClass.testCaseStatus=false;
			    rm.captureScreenShot("8654_AddsubGroupissue", "fail");
			}
			
			
			
			//Step 22:Click on ">" icon from bottom save bar
			AD.performAction("successmetrics>arrow");
			strMsg="The user has clicked on '>' to naviagte to profile page";
			CustomReporter.log(strMsg); 
			
			Analyse_Profile_Page AP = new Analyse_Profile_Page(driver);
			
			rm.webElementSync("idiomspinningcomplete");
			Thread.sleep(3000);
			if(!rm.webElementSync(AP.homeOwnerGraph, "visibility"))
				throw new IDIOMException("Failed to land on profile page.###8654_NotAbleToNavigateToProfilePage");
			
			
			
			strMsg="The user has navigated to profile page";
			CustomReporter.log(strMsg);
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8654-Exception", "fail");
			}else{
				rm.captureScreenShot("8654-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8654-Exception", "fail");
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