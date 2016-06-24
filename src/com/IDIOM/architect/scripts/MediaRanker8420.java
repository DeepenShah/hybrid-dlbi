package com.IDIOM.architect.scripts;

import java.text.MessageFormat;
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
<p>	<b>Test Case Name:</b> 1059: Media Ranker: Category Section : Query: different category have same background color </p>
<p>	<b>Objective:</b> Verify different category should have unique background color. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8420.aspx </p>
<p>	<b>Module:</b> Media Ranker </p>
@author: Shailesh Kava
@since: 24-May-2016
**********************************************************************/
public class MediaRanker8420 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder;
	ArchitectMediaRankerPage mediaRanker;
	String strDigitalRanker;
	String strTVRanker;
	String arrMediaRankerChannels;
	String strDetails;
	String strDefaultAud;
	
	@Test
	public void verifyBGDuplicateColorsForSelectedCategories(){
		
		strDigitalRanker = property.getProperty("digitalRanker").trim();
		strTVRanker = property.getProperty("tvRanker").trim();
		arrMediaRankerChannels = property.getProperty("mediaRankerItems").trim();
		strDefaultAud = property.getProperty("defaultaudience").trim();
		String strDigitalCategories = property.getProperty("DigitalCategories"); 
		String strTVCategories = property.getProperty("TVCategories"); 
		
		String strMsg = null;
		
		String strProjectName="";
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
			
			strProjectName = "8420_"+rm.getCurrentDateTime(); 
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
				
				String[] strLevels={};
			    if(channels[i].toLowerCase().contains("digital")){
			     strLevels = strDigitalCategories.split(",");
			    }else{
			     strLevels = strTVCategories.split(",");
			    }

			    for(int j=0; j<strLevels.length; j++){
			    	rm.captureScreenShot("8420_"+rm.getCurrentDateTime(), "fail");
			    	performAction(channels[i].trim());
			    	
			    	if(j!=2){       
		    	       if(j==0){
		    	        mediaRanker.func_ClickCategoryByNumber(0);
		    	        rm.webElementSync(mediaRanker.subCategory, "visibility");
		    	       }else if(j==1){
		    	        mediaRanker.func_ClickCategoryByNumber("subcategory",0);
		    	        rm.webElementSync(mediaRanker.itemCategory, "visibility");
		    	       }
			    	}
			    }
			}
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot("8420_"+strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8420", "fail");
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
			Thread.sleep(70000);
			System.out.println("TV ranler wait completed");
		}else{
			Thread.sleep(3000);
			if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
				throw new IDIOMException("Failed to open media ranker###Ranker8420-missingDatePicker");
			
			if(!rm.webElementSync(mediaRanker.datepicker, "clickable"))
				throw new IDIOMException("Failed to open media ranker###Ranker8420-datePickerNotClickable");
		}
		
		boolean bDuplicate = mediaRanker.verifyDuplicateColorOfSelectedCategory();
		
		if(bDuplicate){
			CustomReporter.log("All categories have unique back ground color");
			System.out.println("All categories have unique back ground color");
		}else{
			rm.captureScreenShot("8420_dupliCateCatFound_"+channel, "fail");
			BaseClass.testCaseStatus = false;
		}
	}	
}