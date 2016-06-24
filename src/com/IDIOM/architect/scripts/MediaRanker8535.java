package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Test Case Name</b></p> *1217_Media_Ranker_Verifty_Top_Number_Is_EqualTo_Or_LessThan_Total
<p><b>Test Case ID</b></p> http://qa.digitas.com/SpiraTest/523/TestCase/8535.aspx
<p><b>Objective</b></p> Verify whether Top Number Is Equal To Or Less than Total
<p><b>Module</b></p> Media Ranker
@author Shailesh Kava
@since 20 Jan 2016
**********************************************************************/
public class MediaRanker8535 extends BaseClass{
	
	@Test
	public void testMediaRanker8535(){
		ArchitectMediaRankerPage mediaRanker;
		String strMsg="";
		ManageIdiom_Page mi=null;
		boolean bIdiomCreation = false;
		boolean bCompare = true;
		boolean bProjectCreate = false;
		String strProjectName="";
		String strIdiomName="";
		String strClassName = getClass().getSimpleName();
		String mediaRankerChannels;
		
		try{
			//****************Test step starts here*************************
			String strFilters=property.getProperty("MRFilterCriteria8301");	
			mediaRankerChannels = property.getProperty("mediaRankerItems");
			//****************Test step ends here*************************
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
			mediaRanker = new ArchitectMediaRankerPage(driver);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Clicking on Digital Ranker link
			
			String[] channels = mediaRankerChannels.split(",");
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
				
				if(channels[i].trim().toLowerCase().contains("tv"))
					mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "");
				
				if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
					throw new IDIOMException("Failed to open channel###MediaRanker8535-missingScatterPlot");
				
				if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
					throw new IDIOMException("Failed to open channel###MediaRanker8535-missingScatterPlot");
				
				int totalCategories = mediaRanker.listCategory.size();
				System.out.println("Total categories are ["+totalCategories+"]");
				if(totalCategories>25){
					String currentShowBubbles = mediaRanker.func_GetValue("getcountmorecirclecount");
					String totalBubbles = mediaRanker.func_GetValue("totalcirclecountfromheader");
					
					System.out.println("show bubbles ["+currentShowBubbles+"] total bubbles ["+totalBubbles+"]");
					
					int getcountmorecirclecount = Integer.parseInt(currentShowBubbles);
					System.out.println(getcountmorecirclecount);
					int totalCircles = Integer.parseInt(totalBubbles);
					
					mediaRanker.func_ShowAllCircle(totalCircles);
					//int totalClicksOnShowMore = Math.round(totalCategories/25);
					
					if(getcountmorecirclecount > totalCircles){
						rm.captureScreenShot("8535_freqValue", "fail");
						CustomReporter.errorLog("Frequency value is less than currently displaying value, currently display ="+getcountmorecirclecount+" frequency = "+totalCircles);
						BaseClass.testCaseStatus = false;
					}else{
						CustomReporter.log("Frequency value is greater than currently displaying value, currently display ="+getcountmorecirclecount+" frequency = "+totalCircles);
					}
					
					
					//This will click on show more till count >= 1
					if(getcountmorecirclecount > totalCircles){
						rm.captureScreenShot("8535_circleCount", "fail");
						CustomReporter.errorLog("Frequency value is less than currently displaying value, currently display ="+getcountmorecirclecount+" frequency = "+totalCircles);
						BaseClass.testCaseStatus = false;
					}else{
						CustomReporter.errorLog("Frequency value is greater than currently displaying value, currently display ="+getcountmorecirclecount+" frequency = "+totalCircles);
					}
					
				}else{
					CustomReporter.log("Category list should be more than 25 to visible/click Show More button current count is ["+totalCategories+"]");
					System.out.println("Category list should be more than 25 to visible/click Show More button current count is ["+totalCategories+"]");
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
			rm.captureScreenShot("8535", "fail");
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
}
