package com.IDIOM.architect.scripts;

import java.text.MessageFormat;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:</b> 1057: Media Ranker: Scatter Plot: bg color is changed when we un check category from the list </p>
<p>	<b>Objective:</b> BG color of unchecked category should grayed out. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8419.aspx </p>
<p>	<b>Module:</b> Media Ranker </p>
@author: Shailesh Kava
@since: 24-May-2016
**********************************************************************/
public class MediaRanker8419 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder;
	ArchitectMediaRankerPage mediaRanker;
	String strDigitalRanker;
	String strTVRanker;
	String arrMediaRankerChannels;
	String strDetails;
	String strDefaultAud;
	String strUnCheckedBGColor;
	
	@Test
	public void verifyUnCheckedCategoryBGColor(){
		
		strDigitalRanker = property.getProperty("digitalRanker").trim();
		strTVRanker = property.getProperty("tvRanker").trim();
		arrMediaRankerChannels = property.getProperty("mediaRankerItems").trim();
		strDefaultAud = property.getProperty("defaultaudience").trim();
		strUnCheckedBGColor = property.getProperty("uncheckedcatagorybgcolor").trim();
		String strMsg = null;
		
		String strProjectName="8419_"+rm.getCurrentDateTime();
		boolean bProjectCreate = false;
		audienceBuilder = new AudienceBuilderPage(driver);
		mediaRanker = new ArchitectMediaRankerPage(driver);
		
		try{
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 3: Create/Select project and launch the same
			CustomReporter.log("Creating new project as no project for this client");
			System.out.println("Creating new project as no project for this client");
			
			clientListPage.createNewProject(strProjectName);
			
			Thread.sleep(2000);
			clientListPage.performActionOnProject("edit", strProjectName);
			//Getting project and client id to delete through REST service
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			clientListPage.func_PerformAction("Audience Tab");
			
			Thread.sleep(2000);
			clientListPage.func_PerformAction("Launch Project");
			bProjectCreate = true;
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, strDigitalRanker.trim()));
			Thread.sleep(2000);
			
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			String[] channels = arrMediaRankerChannels.split(",");
			for(int i=0; i<channels.length; i++){
				CustomReporter.log("Navigating to ["+channels[i]+"]");
				System.out.println("Navigating to ["+channels[i]+"]");
				
				if(i==0){
					System.out.println("clicking through action");
					pdp.navigateToByActionClass(channels[i].trim());
				}else{
					System.out.println("clicking through link");
					pageHeader.megaMenuLinksClick(channels[i].trim());
				}
				
				performAction(channels[i].trim());
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
			rm.captureScreenShot("8419", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the project");
				}
				
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
	
	public void performAction(String channel) throws Exception{
		
		if(channel.equals("TV Ranker")){
			System.out.println("TV ranler wait started");
			Thread.sleep(120000);
			System.out.println("TV ranler wait completed");
		}else{
			if(!rm.webElementSync(mediaRanker.datepicker, "clickable"))
				throw new IDIOMException("Failed to open media ranker###Ranker8419-datePickerNotClickable");
			
			if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
				throw new IDIOMException("Failed to open media ranker###Ranker8419-datePickerNotVisible");
		}
		
		String catToBeUnChecked;
		System.out.println(By.xpath(mediaRanker.strCategoryName));
		catToBeUnChecked = mediaRanker.selectedVisibleCategories.get(0).findElement(By.xpath(mediaRanker.strCategoryName)).getText();
		
		String catBeforeColor = mediaRanker.getCategoryBGColor(catToBeUnChecked);
		
		System.out.println(catBeforeColor);
		
		System.out.println("Unchecking category ["+catToBeUnChecked+"] current bg color ["+catBeforeColor+"]");
		mediaRanker.selectedVisibleCategories.get(0).findElement(By.xpath(mediaRanker.strCategoryBGColor)).click();
		
		Thread.sleep(1000);
		rm.webElementSync(mediaRanker.selectedVisibleCategories.get(0).findElement(By.xpath(mediaRanker.strCategoryBGColor)), "staleness");
		
		String catAfterColor = mediaRanker.getCategoryBGColor(catToBeUnChecked);
		
		if(!catBeforeColor.equals(catAfterColor)){
			System.out.println("BG color is changed after unchecking category, before uncheck ["+catBeforeColor+"] after uncheck ["+catAfterColor+"]");
			CustomReporter.log("BG color is changed after unchecking category, before uncheck ["+catBeforeColor+"] after uncheck ["+catAfterColor+"]");
		}else{
			rm.captureScreenShot("8419_catBGColorNotChanged_"+channel, "fail");
			System.out.println("BG color is not changing after unchecking category, before uncheck ["+catBeforeColor+"] after uncheck ["+catAfterColor+"]");
			CustomReporter.log("BG color is not changing after unchecking category, before uncheck ["+catBeforeColor+"] after uncheck ["+catAfterColor+"]");
			BaseClass.testCaseStatus = false;
		}
	}	
}