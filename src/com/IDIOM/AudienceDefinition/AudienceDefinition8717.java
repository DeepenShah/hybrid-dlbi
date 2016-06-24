package com.IDIOM.AudienceDefinition;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>*2102:Verify the focus should be on group if we remove any group/subgroup</p>
 * <p> <b>Objective:</b>Verify the focus should be on group/subgroup if we delete any focused group/subgroup. Even if we delete all query using 'Remove All' then also default group should get focused.</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8717.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Amrutha Nair
 * @since 10 May 2016
 *
 */
public class AudienceDefinition8717 extends BaseClass {

	@Test
	public void verifyGroupFocus(){
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
			String strAudienceAttributes=property.getProperty("audienceScenario5");
			
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
			
			//Step 3: Click on any project/Create a project and launch the same
			
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
				throw new IDIOMException("Not able to verify new project window###8717-CreateProjectWindow");
										
			//If audience tab is not found then everything is good
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8717-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			
			//Launch the project
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
				rm.captureScreenShot("8717-ProjectNameissue", "fail");
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
			
		
			
			//Step 5:Click on audience definition tab
			Thread.sleep(3000);
			AD.performAction("gotoaudiencedefinition");
			Thread.sleep(3000);
			strMsg="The user has navigated to audience definition page";
			CustomReporter.log(strMsg);
			
			
			//Step 6:Verify that the present group is in focus
			//Get name of defaultgroup
			String strDefaultGroupName1=AD.getDefaultGroupName();;
			
			System.out.println("strDefaultGroupName1:"+strDefaultGroupName1);
			if(AD.isVisible("defaultgroup", "")){
				strMsg="In audience builder page, the default group is in focus. The group in focus is having the name :"+strDefaultGroupName1;
				CustomReporter.log(strMsg);
				
			}
			else{
				throw new IDIOMException("In audience bulder page , the default group is not in focus .###8717-DefaultgroupInFocus");
			}
			
			
			//Step7:Add a subgroup
			AD.addSubGroup("1");
			strMsg="The sub  group got added";
			CustomReporter.log(strMsg);
			
			//Step 8:	Click on that subgroup
			AD.selectGroup("1.1");
			

			//Get the default subgroup Name 
			String strDefaultGroupName2=AD.getDefaultGroupName();
			System.out.println("strDefaultGroupName2:"+strDefaultGroupName2);
			
			strMsg="The user has selected the added sub group.So focus is moved to selected sub group:"+strDefaultGroupName2;
			CustomReporter.log(strMsg);
			
			
			
			//Step 9:Remove the sub group
			AD.deleteGroup("1.1");
			strMsg="The user has deleted the added focussed sub group :"+strDefaultGroupName2;
			CustomReporter.log(strMsg);
			if(AD.isVisible("defaultgroup", "")){	
				if(AD.getDefaultGroupName().contentEquals(strDefaultGroupName1)){
					strMsg="On deleting the sub group, the focus is moving to parent  group . ie: "+strDefaultGroupName1;
					CustomReporter.log(strMsg);
				}
				else{
					throw new IDIOMException(" On deleting the sub group, the focus is NOT moving to parent  group .###8717-ParentgroupInFocus");
				}
			}
			else{
				throw new IDIOMException(" On deleting the sub group, the focus is NOT moving to parent  group .###8717-ParentgroupInFocus");
			}
			
			
			
			//Step 10:	Now again create a groups and subgroups
			AD.addNewGroup();
			
			AD.selectGroup("2");
			strMsg="The user has added a group by name :"+AD.getDefaultGroupName()+"'and focussed the same";
			CustomReporter.log(strMsg);
			
			AD.addSubGroup("2");
			
			strMsg="The user has added a sub group in the second group with  name :'"+AD.getDefaultGroupName()+ "'and focussed the same";
			CustomReporter.log(strMsg);
			
			//Add a query in the group
			//Step 6:Create a group with attributes
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes);
			AD.performAction("addattribute");
			Thread.sleep(2000);
			
			AD.goToFirstLevelForMetricOrAttribute();
			Thread.sleep(2000);
			strMsg="The user has added queries in the sub group created ";
			CustomReporter.log(strMsg);
			int intGrpCount=AD.getGroupCount();
			//Step 11:Click on remove all from bottom navigation bar
			
			AD.removeAllSuccessMetricsOrAudienceDefinitionQueries();
			strMsg="The user has clicked on 'remove all' link from bottom navigation bar ";
			CustomReporter.log(strMsg);
			
			Thread.sleep(3000);
			if(AD.getGroupCount() ==intGrpCount){
				throw new IDIOMException(" On clicking on 'remove all' , the groups are not getting deleted .###8717GroupsNOtGettingDeleted");
			}
			else{
				strMsg="On clicking on 'Remove All' from bottom navigation bar, the groups are getting deleted";
				CustomReporter.log(strMsg);
			}
			
			if(AD.getGroupCount()==1 && AD.getTotalQueryItems()==0){
				strMsg="On clicking on 'Remove All' from bottom navigation bar,the new blank group is getting created";
				CustomReporter.log(strMsg);
			}
			else{
				throw new IDIOMException(" On clicking on 'remove all' , the new blank group is not getting created .###8717NOBlankGroup");
			}
			
			
			if(AD.isVisible("defaultgroup", "")){
				strMsg="On clicking on 'remove all', the default group is in focus. The group in focus is having the name :"+AD.getDefaultGroupName();
				CustomReporter.log(strMsg);
				
			}
			else{
				throw new IDIOMException("On clicking on 'remove all' , the default group is not in focus .###8717DefaultgroupInFocusOnRemoveAll");
			}
			
		
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8717-Exception", "fail");
			}else{
				rm.captureScreenShot("8717-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8717-Exception", "fail");
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
				
				//Step 9:Click on logout				
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