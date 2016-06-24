package com.IDIOM.AudienceDefinition;



import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.msat.frameworks.data_driven.base.Base;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
/** 
 * <p> <b>Test Case Name:</b> *Audience Definition - a (Categories).i.2.d - Verify whether entire demi decile graph is selected for all future behaviours by default</p>
 * <p> <b>Objective:</b>Objective:Verify whether entire demi decile graph is selected for all future behaviours by default</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8621.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Amrutha Nair
 * @since 06-May-2016
 *
 */
public class AudienceDefinition8621 extends BaseClass {
		
	@Test
	public void verifyAllDemiDecileGraph(){
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
			
			String strFuturebehaviour=property.getProperty("audienceCategory");
			
			String strSubFuturebehaviour=property.getProperty("audienceSubCategory");
		
			//****************Test step starts here************************************************
			
			//Step1 :	Login the application with valid credentials
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 2:Select a client
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
			
			//Step 3: Select a project/create new and launch the project
			
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
				throw new IDIOMException("Not able to verify new project window###8621-CreateProjectWindow");
										
			//If audience tab is not found then everything is good
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8621-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(3000);
			//Launch the project
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
				rm.captureScreenShot("8621-ProjectNameissue", "fail");
			}
			
			
			//Step 4:Click on destination link success metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			
			//Step 5:Click on Audience definition tab
			AD.performAction("gotoaudiencedefinition");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on Audience Definition tab");
			
			
			Thread.sleep(3000);
		
			//Step 6:Expand Future behavior section under All Attributes dropdown/search bar at the right hand side and check the future behaviors that are displayed			
			
			AD.selectAttributeOnAudienceDefinition(strFuturebehaviour);
			
			strMsg="The user has selected Future behaviours";
			CustomReporter.log(strMsg);
			AD.goToFirstLevelForMetricOrAttribute();
			
			
			//Step 7: Click on any of the added Future behavior
			AD.selectAttributeOnAudienceDefinition(strSubFuturebehaviour);
			
			if(AD.isVisible("demidecilegraph", "")){
				strMsg="The demi decile graph is present for the selected future behaviour";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The demi decile graph is NOT present for the selected future behaviour";
				throw new IDIOMException(strMsg+" .###8621-DemiDecileGraphNotPresnt");
			}
			
			//Step 8:Verify entire demidecile area is selected
			
			int[] sliderData=AD.returnSliderData();
			
			if(sliderData[0]==0 && sliderData[1]==100){
				strMsg="By default, the demidecile selected values are '0' and '100' So the entire demi decile are is selected. ";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="By default, the demidecile selected values are not '0' and '100'. By default , '"+sliderData[0]+"'is selected at left side and '"+sliderData[1]+"' is selected at right side. So the entire demi decile are is NOT selected";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus = false;
				rm.captureScreenShot("8621-DemiDecileGraph", "fail");
			}
			AD.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(2000);
			
			
			//Step 9:Repeat step 7 for all future behaviours
			String queryString =null;
			String subQueryString=null;
			String subQueryString1=null;
			List<String> categories=AD.getCategoryNamesAtAnyLevel();
			int intCatCount=categories.size();
			System.out.println("categoryCount"+intCatCount);
			queryString=categories.get(0).toLowerCase();
			System.out.println("queryString:"+queryString);
			for(int i=0; i<intCatCount;i++){
				queryString=categories.get(i).toLowerCase();
				System.out.println("queryString:"+queryString);
				if (queryString.contains("future behaviors")){
					AD.selectCategory(queryString);
					Thread.sleep(2000);
					List<String> subCategories=AD.getCategoryNamesAtAnyLevel();
					for (int j=0;j<subCategories.size();j++){
						subQueryString=subCategories.get(j).toLowerCase();
						System.out.println("subQueryString:"+subQueryString);
						AD.selectCategory(subQueryString);
						Thread.sleep(2000);
						List<String> subCategories1=AD.getCategoryNamesAtAnyLevel();
						for(int k=0;k<subCategories1.size();k++){
							subQueryString1=subCategories1.get(k).toLowerCase();
							System.out.println("subQueryString1:"+subQueryString1);
							AD.selectCategory(subQueryString1);
							Thread.sleep(2000);
							
							sliderData=AD.returnSliderData();
							
							if(sliderData[0]==0 && sliderData[1]==100){
								strMsg="By default, the demidecile selected values are '0' and '100' So the entire demi decile are is selected for category:"+subCategories1.get(k);
								CustomReporter.log(strMsg);
							}
							else{
								strMsg="By default, the demidecile selected values are not '0' and '100'. By default , '"+sliderData[0]+"'is selected at left side and '"+sliderData[1]+"' is selected at right side. So the entire demi decile are is NOT selected for category:"+subCategories1.get(k);
								CustomReporter.errorLog(strMsg);
								BaseClass.testCaseStatus = false;
								rm.captureScreenShot("8621-DemiDecileGraphfor"+subQueryString1, "fail");
							}
							
							Thread.sleep(2000);
							AD.clickOnAttributeOrMetriclayerHeading(subQueryString);
							Thread.sleep(3000);
						}
						
						AD.clickOnAttributeOrMetriclayerHeading(queryString);
						Thread.sleep(3000);
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
			rm.captureScreenShot("8621", "fail");
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
				
				//Step 8:Click on logout				
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