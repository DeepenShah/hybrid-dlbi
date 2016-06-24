package com.IDIOM.architect.scripts;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> 1047_MediaRanker_OptionValue In TitleCase  </p>
 * <p> <b>Objective:</b> Verify that , in scatterplot, the Top 10 Properties option values are in title case, Not in camel case</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8432.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * @author Deepen Shah
 * @since 19 May 2016
 *
 */
public class MediaRanker8432 extends BaseClass {

	@Test
	void verifyPropertyNameInDropDownInBlackCase(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		String strDeletionDetails="";
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strMediaRankerPages = property.getProperty("mediaRankerItems");			
			//****************Test step starts here************************************************
			
			//Step1: Launch url
			strMsg = "Launched Browser and Enter URL";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step2: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step3: Select client
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
			
			//Step7,8,9&10: Navigate to Media Ranker Page
			String[] strChannels = strMediaRankerPages.split(",");
			ArchitectMediaRankerPage mediaRanker = new ArchitectMediaRankerPage(driver);
						
			for(String strChannel:strChannels){
				pdp.navigateTo(strChannel);							
				if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				
				//Waiting if element is clickable
				rm.webElementSync(mediaRanker.viewTop10PropBtn, "clickable");
								
				try{
					//Step11: Click on property icon
					mediaRanker.func_ClickOnElement("viewscatteredplotprop");
					Thread.sleep(2000);
					
					Select orderDD = new Select(mediaRanker.orderDropdown);
					List<WebElement> optionsElement = orderDD.getOptions();
					
					List<String> optionsName = new ArrayList<>();
					
					//Step12: Getting the content
					for(WebElement element:optionsElement)
						optionsName.add(element.getText().trim());				
					
					CustomReporter.log("Drop down values: " +optionsName);
					
					boolean bStatus=true;
					for(String str:optionsName)
						if(!str.equals(str.toUpperCase())){
							CustomReporter.errorLog(str + " is not in upper case");
							bStatus = false;
						}
					
					if(!bStatus)
						throw new IDIOMException("Values not in upper case.###NoValuesInDD");
					
					CustomReporter.log("All the values are in block for " + strChannel);
				}catch(IDIOMException e){
					exceptionCode(e);
				}finally{
					
					mediaRanker.func_ClickOnElement("closepropetyoverlay");
					
					//Navigating back to project dashboard page.
					pageHeader.performAction("clientlogo");
					rm.webElementSync("visibiltiybyxpath",MessageFormat.format(pdp.strLinkContains, strChannel));
					Thread.sleep(2000);
				}
					
			}			
			
		}catch(IDIOMException ie){
			exceptionCode(ie);			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8432-Exception", "fail");
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
	
	
	public void exceptionCode(IDIOMException ie){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
		CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
		
		if(strMsgAndFileName.length==1){
			rm.captureScreenShot("8432-Exception", "fail");
		}else{
			rm.captureScreenShot("8432-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
