package com.IDIOM.architect.scripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Media Ranker_5 Top 10 Properties_Verify top 10 properties overlay </p>
 *  <p> <b>Objective:</b> Verify top 10 properties overlay </p>
 *  <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8318.aspx </p>
 *  <p> <b>Module:</b> Media Ranker</p>
 *  
 * @author Deepen Shah
 * @since 29/01/2016
 *
 */
public class MediaRanker8318 extends BaseClass{
		
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyTop10PropertyOverlay(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		
		String strProjectName="";				
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;		
		
		try{
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
				
				boolean bStatus = true;
				
				try{
			
					//Step7: click on top 10 property icon.
					arMediaRankerPage.func_ClickOnElement("viewscatteredplotprop");				
					Thread.sleep(2000);
					
					//Step8: Verifying various elements
					
					//X icon
					if(!arMediaRankerPage.func_VerifyVisibilityOfElement("closepropoverlay")){
						throw new IDIOMException("Not able to verify X icon for property overlay.###NoXForPropOverlay");						
					}else{
						strMsg = "Verified X (close) icon for property overlay";
						System.out.println(strClassName +": " + strMsg);
						CustomReporter.log(strMsg);
						
						//Display dropdown
						if(arMediaRankerPage.func_VerifyVisibilityOfElement("displaydropdown")){					
							strMsg = "Verified display dropdown for property overlay";
							System.out.println(strClassName +": " + strMsg);
							CustomReporter.log(strMsg);
						}else{
							bStatus = false;
							strMsg = "Not able to verify display dropdown for property overlay";
							System.out.println(strClassName +": " + strMsg);
							CustomReporter.errorLog(strMsg);
						}
						
						//Order dropdown
						if(arMediaRankerPage.func_VerifyVisibilityOfElement("orderdropdown")){
							strMsg = "Verified order dropdown for property overlay";
							System.out.println(strClassName +": " + strMsg);
							CustomReporter.log(strMsg);
							
							//Step9: Verfying first value in ORDER as 'RANK'
							String firstOrder = arMediaRankerPage.func_GetValue("defaultproporder");
							if(firstOrder.equalsIgnoreCase("RANK")){
								strMsg = "Verified first value as 'RANK' in order dropdown";
								System.out.println(strClassName +": " + strMsg);
								CustomReporter.log(strMsg);
							}else{
								BaseClass.testCaseStatus = false;
								strMsg = "Failed to verify first value as 'RANK' in order dropdown. Found : " + firstOrder;
								System.out.println(strClassName +": " + strMsg);
								CustomReporter.errorLog(strMsg);
							}
						}else{
							bStatus = false;
							strMsg = "Not able to verify order dropdown for property overlay";
							System.out.println(strClassName +": " + strMsg);
							CustomReporter.errorLog(strMsg);
						}
						
						//Date picker for time frame
						if(arMediaRankerPage.func_VerifyVisibilityOfElement("datepicker")){
							strMsg = "Verified date picker for property overlay";
							System.out.println(strClassName +": " + strMsg);
							CustomReporter.log(strMsg);
						}else{
							bStatus = false;
							strMsg = "Not able to verify date picker for property overlay";
							System.out.println(strClassName +": " + strMsg);
							CustomReporter.errorLog(strMsg);
						}
						
						
						if(!bStatus)
							exceptionCode(new IDIOMException("Some element missing. Check other messages.###Missing"));
						
						//Step10: Properties should be displayed based what user is looking. Category, Sub-category or item.
						bStatus = true;
						
						
						//Verifying for category
						List<String> categoryNameList = arMediaRankerPage.func_GetAllCategoryName("category");
						List<String> top10PropNameList = arMediaRankerPage.func_GetTop10Properties();
						if(rm.verifyListContainsList(top10PropNameList, categoryNameList)){
							strMsg = "Verified: Properties are displayed from the category list";
							System.out.println(strClassName +": " + strMsg);
							CustomReporter.log(strMsg);
						}else{
							exceptionCode(new IDIOMException("Failed to verify. Properties are not displayed from category list.###PropertyNotFromCategory"));							
						}
						
						bStatus = true;
						
						//Drill down to sub-category					
						arMediaRankerPage.func_ClickCategoryByNumber(0);
						Thread.sleep(2000);
						
						categoryNameList = arMediaRankerPage.func_GetAllCategoryName("subcategory");
						top10PropNameList = arMediaRankerPage.func_GetTop10Properties();
						if(rm.verifyListContainsList(top10PropNameList, categoryNameList)){
							strMsg = "Verified: Properties are displayed from the sub-category list";
							System.out.println(strClassName +": " + strMsg);
							CustomReporter.log(strMsg);
						}else{
							exceptionCode(new IDIOMException("Properties are not displayed from sub-category list.###PropertyNotFromSubCategory"));							
						}					
						
						
						bStatus = true;
						//Drill down to item					
						arMediaRankerPage.func_ClickCategoryByNumber("subcategory",0);
						Thread.sleep(2000);
						
						categoryNameList = arMediaRankerPage.func_GetAllCategoryName("item");
						top10PropNameList = arMediaRankerPage.func_GetTop10Properties();
						if(rm.verifyListContainsList(top10PropNameList, categoryNameList)){
							strMsg = "Verified: Properties are displayed from the item list";
							System.out.println(strClassName +": " + strMsg);
							CustomReporter.log(strMsg);
						}else{
							exceptionCode(new IDIOMException("Properties are not displayed from item list.###PropertyNotFromItemCategory"));
						}				
						
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
			rm.captureScreenShot("8318-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8318-"+strMsgAndFileName[1], "fail");	
		}		
	}
	
	
	
}
