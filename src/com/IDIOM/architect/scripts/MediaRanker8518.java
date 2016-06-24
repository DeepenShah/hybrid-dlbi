package com.IDIOM.architect.scripts;

import java.text.MessageFormat;
import org.openqa.selenium.WebElement;
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
 * <p> <b>Test Case Name:</b> 1042: Media Ranker: Date picker: there should be some highlighted dates/date ranges in which data is available  </p>
 * <p> <b>Objective:</b> In date picker date range should be pre selected to view data in scatter plot and ranker</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8518.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * @author Deepen Shah
 * @since 19 May 2016
 *
 */
public class MediaRanker8518 extends BaseClass {

	@Test
	void verifyClickableDates(){
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
			String strDateColor = property.getProperty("clickableDateColor");
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
				rm.webElementSync(mediaRanker.datepicker, "clickable");
				
				mediaRanker.func_ClickOnElement("datepicker");				
				if(!mediaRanker.func_VerifyVisibilityOfElement("calenderapplybtn"))
					throw new IDIOMException("Failed to open date picker###DatePickerNotOpen");
				
				CustomReporter.log("Clicked on datepicker");
				
				//Fetch all selected dates. Due to data problem
				int iBeforeCount = mediaRanker.selectedDatesList.size();
				System.out.println("Total selected dates are before " + iBeforeCount );
				
				int iBeforeClickableCount = mediaRanker.clickableDateList.size();
				System.out.println("Total clickable date count " + iBeforeClickableCount);
				
				//Now de-select any date
				WebElement anyDate = mediaRanker.selectedDatesList.get(0);
				anyDate.click();
				Thread.sleep(1000);
				
				//Getting count again
				int iAfterCount = mediaRanker.selectedDatesList.size();
				System.out.println("Total selected dates are after " + iAfterCount );
				
				//Putting in try/catch to move ahead with another channel
				try{
					if(iAfterCount != (iBeforeCount-1))
						throw new IDIOMException("Not able to deselecte the date.###NotAbleToDeselectDate");
					
					//Comparing count of clickable dates
					int iAfterClickableDate = mediaRanker.clickableDateList.size();
					System.out.println("Total clickable date count After " + iBeforeClickableCount);
					
					if(iAfterClickableDate != (iBeforeClickableCount+1))
						throw new IDIOMException("Clickable date counts are not matching. Expected " +
					(iBeforeClickableCount+1) + " and found " + iAfterClickableDate +".###CountIsNotMatching");
					
					CustomReporter.log("Date are highlighted those who have data.");
					
					//Getting color of de-selected date.
					String strActualColor = mediaRanker.clickableDateList.get(0).getCssValue("color");
					System.out.println("Found color " + strActualColor);
					
					if(!strActualColor.equalsIgnoreCase(strDateColor))
						throw new IDIOMException("Date color is not matching. Expected: " + 
					strDateColor + " and found " + strActualColor +".###ColorIsNotMatching");
					
					CustomReporter.log("Verified clickable date is highlighted and not the greyed out for " + strChannel);
				}catch(IDIOMException e){
					exceptionCode(e);
				}finally{
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
			rm.captureScreenShot("8518-Exception", "fail");
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
			rm.captureScreenShot("8518-Exception", "fail");
		}else{
			rm.captureScreenShot("8518-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
