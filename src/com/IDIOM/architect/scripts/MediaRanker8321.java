package com.IDIOM.architect.scripts;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Media Ranker Overview_1.b.i.2Display and Timeframe_.a_Check_Options_Displayed_In_Dropdown </p>
 *  <p> <b>Objective:</b> Verify Options displayed in dropdown </p>
 *  <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8321.aspx </p>
 *  <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 * @since 02/02/2016
 *
 */
public class MediaRanker8321 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyValuesInDisplayDropdown(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		
		String strProjectName="";				
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;	
		
		try{			
			String strDigitalCategory = property.getProperty("DigitalCategories");
			String strTVCategory = property.getProperty("TVCategories");
			String strChannels[] = property.getProperty("mediaRankerItems").split(",");
			//****************Test step starts here************************************************
			//Login To Selecting Client
			loginToSelectClient();			
			
			//Step4&5: Select/Create project
			if(clientListPage.totalProject()>0){
				strProjectName = clientListPage.getProjectNameById(1);
			}else{
				strProjectName = clientListPage.createNewProject("");
				strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
				bProjectCreated = true;
			}
			
			//Step6: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(1000);
			
			//Step7,8,9&10: Navigate to Media Ranker Page			
			arMediaRankerPage = new ArchitectMediaRankerPage(driver);

			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				
				String strExpVal="";
				if(strChannel.toLowerCase().contains("digital"))
					strExpVal = strDigitalCategory;
				else
					strExpVal = strTVCategory;
				
				boolean bStatus = true;
				
				try{
			
			
					//Getting & preparing display drop down
					Select displayDropDown = new Select(arMediaRankerPage.func_GetLocalWebElement("displayDropdown"));
					List<WebElement> allOptionElement = displayDropDown.getOptions();
					
					//Step8: verify options of drop down.				
					for(WebElement element:allOptionElement){
						if(strExpVal.toLowerCase().contains(element.getText().trim().toLowerCase())){
							strMsg="Verified value, " + element.getText() + ", in dropdown";
							CustomReporter.log(strMsg);
							System.out.println(strMsg);
						}else{
							bStatus=false;
							strMsg="Failed verify value, " + element.getText() + ", from expected. Expected list: " + strExpVal;
							CustomReporter.errorLog(strMsg);
							System.out.println(strMsg);						
						}
					}
					
					if(!bStatus){
						arMediaRankerPage.func_ClickOnElement("displaydropdown");
					
						throw new IDIOMException("Drop down values are not matching. Expected " + strExpVal +
								" and in application " + allOptionElement + ".###ValuesAreNotMatchingInDisplayDD");
					}
					
				}catch(Exception e){
					exceptionCode(e);
				}
			}	
			
		}catch(Exception e){
			exceptionCode(e);
		}finally{
			try{
				
				//Deleting newly created project or Audience			
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project");					
				}
				
				//Logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(strClassName + ": " + strMsg);
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
		
	public void exceptionCode(Exception ie){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(strClassName + ": " + strMsgAndFileName[0]);
		
		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot("8321-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8321-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
