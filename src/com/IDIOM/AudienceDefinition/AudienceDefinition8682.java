package com.IDIOM.AudienceDefinition;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> Audience Definition - DTASIDIOM-1995: Check colour of AND/OR label. </p>
 * <p> <b>Objective:</b> To verify colour for all 'AND'  should be same and colour for all 'OR' should be same but different to each other.</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8682.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 10 May 2016
 *
 */
public class AudienceDefinition8682 extends BaseClass {

	@Test
	void verifyHelpForSuccessMetricAndAudienceDefinition(){
		
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;	
		boolean bAudienceCreated = false;
		String strAudienceName = "";
		
		AudienceBuilderPage abPage=null;		
	
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String[] strAudienceDefs={property.getProperty("cmmonAudienceAttribute"),property.getProperty("commonAudienceAttribute2"),property.getProperty("commonAudienceAttribute3")};
			
			String strDefaultGroupLogic = property.getProperty("defaultGroupLogic");
			String strDefaultQueryLogic = property.getProperty("defaultQueryOrSubGroupLogic");
			
			String strAndColor = property.getProperty(strDefaultGroupLogic+"Color");
			String strOrColor = property.getProperty(strDefaultQueryLogic+"Color");
			//****************Test step starts here************************************************
			
			strMsg = "Launched Browser and Enter URL";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step2: Select client
			clientListPage = new ClientList_Page(driver);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");		
			
			
			//Step3: Select/Create project
			if(clientListPage.totalProject()>0){
				strProjectName = clientListPage.getProjectNameById(1);
			}else{
				strProjectName = clientListPage.createNewProject("");
				bProjectCreated = true;
			}			
			
			//Create new audience
			if(!bProjectCreated){
				clientListPage.performActionOnProject("edit", strProjectName);
				clientListPage.findAndSaveProjectWindow(false, strProjectName);
				
				clientListPage.func_PerformAction("Audience Tab");
				
				strAudienceName = clientListPage.createNewAudience("");
				clientListPage.func_PerformAction("Close Project");
				Thread.sleep(2000);
				bAudienceCreated= true;
			}
			
			
			//Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			//Step4&5: Navigate to Audience Definition
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			
			abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			Thread.sleep(2000);
			CustomReporter.log("Navigated to audience definition page");
			
			
			//Step6: Adding queries and creating new groups
			
			//Adding queries to first group
			for(String strQuery:strAudienceDefs){
				abPage.selectAttributeOnAudienceDefinition(strQuery);
				abPage.performAction("addattribute");
				Thread.sleep(2000);
			}
			
			//Now adding subgroups to 1st group
			abPage.addSubGroup("1");
			abPage.addSubGroup("1");
			
			//Adding a groups
			abPage.addNewGroup();
			abPage.addNewGroup();
			
			//Now verifying all the data we have added
			int iQueryCount = abPage.getQueryItemCountInGroup("1");
			int iSubGroupCount = abPage.getSubGroupCount("1");
			int iGroupCount = abPage.getGroupCount();
			
			if(iQueryCount != strAudienceDefs.length)
				throw new IDIOMException("Not able to adde queries in the group. ###FailedToAddQueryToGroup");
			
			CustomReporter.log("Successfully adde the queries. " +Arrays.toString(strAudienceDefs));
			
			if(iSubGroupCount !=2)
				throw new IDIOMException("Failed to add subgroups to group. Found count " + iSubGroupCount + ".###FailedToAddSubGroup");
			
			CustomReporter.log("Added subgroups to first group");
			
			if(iGroupCount != 3)
				throw new IDIOMException("Not able to add group. Found count " + iGroupCount + ".###FaileToAddGroup");
			
			
			//Checking color.
			
			//Fetching color for query items
			String[] strQueryLogicColor = new String[strAudienceDefs.length];
			
			abPage.findAndSaveGroup("1");
			
			List<WebElement> queryItems = abPage.globalCurrentGroup.findElements(By.xpath(abPage.strQueryItemsInGroup));
			for(int i=0;i<strQueryLogicColor.length;i++)
				strQueryLogicColor[i] = queryItems.get(i).findElement(By.xpath(abPage.strLogicElement)).getCssValue("background-color").trim();
			
			System.out.println("Logic for query " + Arrays.toString(strQueryLogicColor));
			
			//Fetching color for group
			List<WebElement> subGroups = abPage.globalCurrentGroup.findElements(By.xpath(abPage.subGroups));
			
			String strSubGroup1Color = subGroups.get(0).findElement(By.xpath(abPage.strLogicElement)).getCssValue("background-color").trim();
			String strSubGroup2Color = subGroups.get(1).findElement(By.xpath(abPage.strLogicElement)).getCssValue("background-color").trim();
			
			System.out.println("Logic color for subgroup " + strSubGroup1Color + " and " + strSubGroup2Color);
			
			//Comparing query color
			for(int i=0;i<strAudienceDefs.length;i++)
				if(i!=0)
				if(!strQueryLogicColor[i].equalsIgnoreCase(strOrColor))
					throw new IDIOMException("Not able to match color for " + strAudienceDefs[i]+ " found " +
					strQueryLogicColor[i] +" instead of " +
							strOrColor + ".###QueryColorMismatch");
			
			CustomReporter.log("Verified query items OR color as " + strOrColor);
			
			//Comparing subgroup color
			if(!strSubGroup1Color.equalsIgnoreCase(strSubGroup2Color) && !strSubGroup2Color.equalsIgnoreCase(strOrColor))
				throw new IDIOMException("Not able to match color for subgroup OR. Found " + strSubGroup1Color + 
						" and expected " + strOrColor + ".###SubgroupColorMismatch");
			
			CustomReporter.log("Verified subgroup OR logic color " + strOrColor);
			
			//Fetching group color
			
			abPage.findAndSaveGroup("2");
			String strGroup2Color = abPage.globalCurrentGroup.findElement(By.xpath(abPage.strLogicElement)).getCssValue("background-color").trim();
			
			abPage.findAndSaveGroup("3");
			String strGroup3Color = abPage.globalCurrentGroup.findElement(By.xpath(abPage.strLogicElement)).getCssValue("background-color").trim();
			
			System.out.println("Group2 " + strGroup2Color + " and Group3 " + strGroup3Color);
			
			//Verifying group AND logic color
			if(!strGroup2Color.equalsIgnoreCase(strGroup3Color) && !strGroup3Color.equalsIgnoreCase(strAndColor))
				throw new IDIOMException("Not able to match color for group AND. Found " + strGroup2Color + 
						" and expected " + strAndColor + ".###GroupColorMismatch");
			
			CustomReporter.log("Verified group's AND color " + strAndColor);
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8682-Exception", "fail");
			}else{
				rm.captureScreenShot("8682-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8682-Exception", "fail");
		}finally{
			try{								
				//Deleting newly created project or Audience								
				pageHeader.performAction("idiomlogo");;
				rm.webElementSync(pageHeader.personMenu,"visibility");
				if(bProjectCreated){		
				
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");					
				}
				
				if(bAudienceCreated && !bProjectCreated){
					clientListPage.performActionOnProject("edit", strProjectName);
					clientListPage.findAndSaveProjectWindow(false, strProjectName);
					
					clientListPage.func_PerformAction("Audience Tab");
					clientListPage.performActionOnAudience(strAudienceName, "delete");
					
					CustomReporter.log("Deleted the audience " + strAudienceName);
				}								
				
				//Logout
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
