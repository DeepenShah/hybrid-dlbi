package com.IDIOM.architect.scripts;

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
<p>	<b>Test Case Name:</b> Media Ranker_Verify Switching Rankers[DTASIDIOM-2606, DTASIDIOM-2474,DTASIDIOM-2646] </p>
<p>	<b>Objective:</b> Verify that media ranker is functioning properly while switching the rankers from mega menu. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8912.aspx </p>
<p>	<b>Module:</b> Media Ranker </p>
@author: Shailesh Kava
@since: 18-May-2016
**********************************************************************/
public class MediaRanker8912 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder;
	ArchitectMediaRankerPage mediaRanker;
	String strDigitalRanker;
	String strTVRanker;
	String rankerPercentageVal = "80";
	String coveragePercentageVal = "30";
	String coveragePercentageVal1 = "20";
	String ranker1;
	String ranker2;
	
	int metricPlotBubbleInDigitalRanker;
	int metricPlotBubbleInTVRanker;
	
	@Test
	public void mediaRankerFunctioning(){
		
		strDigitalRanker = property.getProperty("digitalRanker");
		strTVRanker = property.getProperty("tvRanker");
		ranker1 = property.getProperty("raneker1");
		ranker2 = property.getProperty("raneker2");
		
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
			if(totalProjects == 0){
				CustomReporter.log("Creating new project as no project for this client");
				System.out.println("Creating new project as no project for this client");
				strProjectName = clientListPage.createNewProject("");
				clientListPage.func_PerformAction("Launch Project");
				bProjectCreate = true;
				
			}else{
				CustomReporter.log("Selecting existing project");
				System.out.println("Selecting existing project");
				int selectProjectId = totalProjects;
				clientListPage.clickProjectById(selectProjectId);
			}
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 4: Navigate to digital ranker 
			CustomReporter.log("Navigating to [Digital Ranker]");
			pdp.navigateTo(strDigitalRanker);
			performAction(strDigitalRanker);
			
			//Step 5: Switch to TV ranker from megamenu
			CustomReporter.log("Navigating to [TV Ranker]");
			pageHeader.megaMenuLinksClick(strTVRanker);
			performAction(strTVRanker);
			
			//Step 8: [DTASIDIOM-2474] Verify the scatterplot section
			if(metricPlotBubbleInDigitalRanker == metricPlotBubbleInTVRanker)
				throw new IDIOMException("bubbles are same in "+strTVRanker+" ["+metricPlotBubbleInTVRanker+"] and in "+strDigitalRanker+" ["+metricPlotBubbleInDigitalRanker+"] channels###MediaRanker8912-sameMetricPlot");
			
			CustomReporter.log("Bubble count are differed in "+strTVRanker+" ["+metricPlotBubbleInTVRanker+"] and in "+strDigitalRanker+" ["+metricPlotBubbleInDigitalRanker+"]");
			System.out.println("Bubble count are differed in "+strTVRanker+" ["+metricPlotBubbleInTVRanker+"] and in "+strDigitalRanker+" ["+metricPlotBubbleInDigitalRanker+"]");
			
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
			rm.captureScreenShot("8912", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");					
					Thread.sleep(4000);
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
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
	
	public void performAction(String strChannel) throws Exception{
		
		if(strChannel.toLowerCase().contains("tv"))
			mediaRanker.func_VerifyVisibilityOfElement("slowtvranker");
		
		if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
			throw new IDIOMException("Failed to open channel###MediaRanker8912-missingScatterPlot");
		
		CustomReporter.log(strChannel+" is appearing properly");
		
		int iCatCount = mediaRanker.func_GetCount("categories");
		System.out.println("Total category count ["+iCatCount+"]");
		
		if(iCatCount > 25){
			System.out.println("Clicking on Show More button to display more categories");
			CustomReporter.log("Clicking on Show More button to display more categories");
			mediaRanker.func_ShowAllCircle(iCatCount);
		}
		
		if(strChannel.toLowerCase().contains("digital")){
			metricPlotBubbleInDigitalRanker = mediaRanker.func_GetCount("circlesinplot");
			System.out.println("Bubbles in Digital Ranker are ["+metricPlotBubbleInDigitalRanker+"]");
			CustomReporter.log("Bubbles in Digital Ranker are ["+metricPlotBubbleInDigitalRanker+"]");
			
		}else if(strChannel.toLowerCase().contains("tv")){
			String TVURL = driver.getCurrentUrl();
			String[] url = TVURL.split("/");
			
			metricPlotBubbleInTVRanker = mediaRanker.func_GetCount("circlesinplot");
			//CustomReporter.log("Bubbles in tv Ranker are ["+metricPlotBubbleInTVRanker+"]");
			
			//Step 6: [DTASIDIOM-2606] Verify the url
			if(!url[url.length-1].toLowerCase().contains("tv")){
				CustomReporter.errorLog("TV URL is not expected ["+TVURL+"]");
				System.out.println("TV URL is not expected ["+TVURL+"]");
				BaseClass.testCaseStatus = false;
			}else{
				CustomReporter.log("TV tanker URL is expected ["+TVURL+"]");
				System.out.println("TV tanker URL is expected ["+TVURL+"]");
			}
			
			//Step 7:Verify the page got landed properly and The project name and audience drop down is coming with header
			if(pageHeader.isVisible("projectname") && pageHeader.isVisible("audiencedropdown")){
				CustomReporter.log("Project name and audience dropdown are appering in header");
				System.out.println("Project name and audience dropdown are appering in header");
			}else{
				CustomReporter.errorLog("Project name or audience dropdown is missing");
				System.out.println("Project name or audience dropdown is missing");
				BaseClass.testCaseStatus = false;
			}
			
		}else
			throw new IDIOMException("Unexpected channel name is added ["+strChannel+"]###MediaRanker8912-unexpectedChannelName");
	}
}