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
<p>	<b>Test Case Name:</b> Media Ranker_Verify Right Navigation on Switching Audiences[DTASIDIOM-2488] </p>
<p>	<b>Objective:</b> Verify that right navigation while switching the audiences. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8929.aspx </p>
<p>	<b>Module:</b> Media Ranker </p>
@author: Shailesh Kava
@since: 23-May-2016
**********************************************************************/
public class MediaRanker8929 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder;
	ArchitectMediaRankerPage mediaRanker;
	String strDigitalRanker;
	String strTVRanker;
	String arrMediaRankerChannels;
	String strDetails;
	String strDefaultAud;
	String strTVAud1;
	String strTVAud2;
	String strTargetAxis;
	String strSourceAxis;
	String strSourceVector;
	String strTargetVector;
	String strChangedSource;
	String strChangedTarget;
	String strSourceVal;
	String strTargetVal;
	boolean bStatus = true;
	
	@Test
	public void verifyAxisValuesAfterChangeAudiene(){
		
		strDigitalRanker = property.getProperty("digitalRanker").trim();
		strTVRanker = property.getProperty("tvRanker").trim();
		arrMediaRankerChannels = property.getProperty("mediaRankerItems").trim();
		strDefaultAud = property.getProperty("defaultaudience").trim();
		strTargetAxis = property.getProperty("8929source").trim();
		strSourceAxis = property.getProperty("8929target").trim();
		String strTarget[] = strTargetAxis.split(":");
		String strSource[] = strSourceAxis.split(":");
		strTVAud1 = "tv1_"+rm.getCurrentDateTime();
		strTVAud2 = "tv2_"+rm.getCurrentDateTime();
		
		strSourceVector = strSource[0].trim();
		strTargetVector = strTarget[0].trim(); 
		strSourceVal = strSource[1].trim();
		strTargetVal = strTarget[1].trim();
		
		String strMsg = null;
		
		String strProjectName="";
		String strAudName="";
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
			
			strProjectName = "8929_"+rm.getCurrentDateTime(); 
			clientListPage.createNewProject(strProjectName);
			
			Thread.sleep(2000);
			clientListPage.performActionOnProject("edit", strProjectName);
			//Getting project and client id to delete through REST service
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			clientListPage.func_PerformAction("Audience Tab");
			
			strAudName = "8929_"+rm.getCurrentDateTime();
			Thread.sleep(3000);
			clientListPage.createNewAudience(strAudName);
			Thread.sleep(3000);
			clientListPage.createNewAudience(strTVAud1);
			Thread.sleep(3000);
			clientListPage.createNewAudience(strTVAud1);
			
			clientListPage.performActionOnAudience(strAudName, "changeaudience");
			
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
			rm.captureScreenShot("8929", "fail");
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
		bStatus = true;
		
		if(channel.equals("TV Ranker")){
			System.out.println("TV ranler wait started");
			Thread.sleep(120000);
			System.out.println("TV ranler wait completed");
		}else{
			if(!rm.webElementSync(mediaRanker.datepicker, "clickable"))
				throw new IDIOMException("Failed to open media ranker###Ranker8929-missingScatterPlot1");
			
			if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
				throw new IDIOMException("Failed to open media ranker###Ranker8929-missingScatterPlot");
		}
		System.out.println(browserName);
		
		if(channel.equals("TV Ranker")){
			
			pageHeader.selectAudienceFromDropDown(strTVAud1);
			rm.webElementSync("idiomspinningcomplete");
			
			if(!rm.webElementSync(mediaRanker.datepicker, "clickable") && !mediaRanker.func_VerifyVisibilityOfElement("datepicker")){
				rm.captureScreenShot("8929_changeAudFailed", "fail");
				CustomReporter.errorLog("Page is not getting loaded after changing audience");
				System.out.println("Page is not getting loaded after changing audience");
				BaseClass.testCaseStatus = false;
			}else{
				CustomReporter.log("Page is loaded properly after changing audience");
				System.out.println("Page is loaded properly after changing audience");
			}
		}
		//Clicking on axis icon
		Thread.sleep(5000);
		mediaRanker.func_ClickOnElement("reorderbtn");
		CustomReporter.log("Axis values are open");
		System.out.println("Axis values are open ["+strSourceVal+"]["+strTargetVector+"]");
		Thread.sleep(2000);
		mediaRanker.func_DragAndDropMetric(strSourceVal,strTargetVector);
		
		rm.webElementSync("idiomspinningcomplete");
		CustomReporter.log("Swapping done for axis values source is ["+strSourceAxis+"], target is ["+strTargetAxis+"]");
		System.out.println("Swapping done for axis values source is ["+strSourceAxis+"], target is ["+strTargetAxis+"]");
		
		strChangedSource = mediaRanker.func_GetIndividualMetricNameFromReOrderPane(strSourceVector);
		strChangedTarget = mediaRanker.func_GetIndividualMetricNameFromReOrderPane(strTargetVector);
		
		CustomReporter.log("After swapping source vector is ["+strChangedSource+"], and target vector is ["+strChangedTarget+"]");
		System.out.println("After swapping source vector is ["+strChangedSource+"], and target vector is ["+strChangedTarget+"]");
		
		pageHeader.selectAudienceFromDropDown(strDefaultAud);
		rm.webElementSync("idiomspinningcomplete");
		if(!rm.webElementSync(mediaRanker.datepicker, "clickable") && !mediaRanker.func_VerifyVisibilityOfElement("datepicker")){
			rm.captureScreenShot("8929_changeAudFailed", "fail");
			CustomReporter.errorLog("Page is not getting loaded after changing audience");
			System.out.println("Page is not getting loaded after changing audience");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("Page is loaded properly after changing audience");
			System.out.println("Page is loaded properly after changing audience");
		}
		
		Thread.sleep(3000);
		rm.webElementSync(mediaRanker.reorderBtn, "clickable");
		mediaRanker.func_ClickOnElement("reorderbtn");
		
		CustomReporter.log("Axis values are open");
		Thread.sleep(2000);
		
		//default axis values
		String defaultAxisVal = property.getProperty("MetricplotValue");
		String[] defValues = defaultAxisVal.split(",");
		for(int i=0; i<defValues.length; i++){
			String[] strMetricAxis = defValues[i].split(":");
			String strActualVal = mediaRanker.func_GetIndividualMetricNameFromReOrderPane(strMetricAxis[0]);
			if(!strMetricAxis[1].trim().toLowerCase().equalsIgnoreCase(strActualVal.trim().toLowerCase())){
				bStatus = false;
				System.out.println(defValues[1].trim().toLowerCase()+"==="+strActualVal.trim().toLowerCase());
				CustomReporter.errorLog("Default category should match for new audience axis ["+strActualVal+"] should have value ["+strMetricAxis[1]+"]");
				System.out.println("Default category should match for new audience axis ["+strActualVal+"] should have value ["+strMetricAxis[1]+"]");
				BaseClass.testCaseStatus = false;
				
			}else{
				CustomReporter.log("Default category is matching for new audience axis ["+strActualVal+"] === ["+strMetricAxis[1]+"]");
				System.out.println("Default category is matching for new audience axis ["+strActualVal+"] === ["+strMetricAxis[1]+"]");
			}
		}
		if(bStatus==false)
			rm.captureScreenShot("8929DefaultValueNotMatched", "fail");
	}
}