package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Media Ranker_3.2 Date Set Builder_Verify icon states and effect  </p>
 * <p> <b>Objective:</b> Verify icon states and effect </p>
 * <p> <b> Description: </b> This test case will verify icon before category after check-uncheck few sub-categories and item.
 *  For TV & Digital.   </br> 
 *  
 *  - Deslecting few of sub-categories icon should be dash. </br>
 *  - Deselecting it should be grayed without any icon</br> </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8313.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * 
 * <p><b> Last Modified By: </b> Deepen Shah </p>
 * <p><b> Last Modified Date: </b> 26/05/2016 </p> 
 * @since 21/01/2016
 * @author Deepen Shah 
 *
 */
public class MediaRanker8313 extends BaseClass{
	
	ArchitectMediaRankerPage arMediaRankerPage=null;
	String strMsg="";
	String strClassName = getClass().getSimpleName();
		
	@Test
	public void verifyDifferentIconForCategory(){
		String strProjectName="";
		String strAudienceName="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;
		boolean bAudienceCreated = false;
		
		
		try{
			String[] strChannels = property.getProperty("mediaRankerItems").split(",");			
			
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
			
			//Create new audience
			if(!bProjectCreated){
				clientListPage.performActionOnProject("edit", strProjectName);
				clientListPage.findAndSaveProjectWindow(false, strProjectName);
				
				clientListPage.func_PerformAction("Audience Tab");
				
				strAudienceName = clientListPage.createNewAudience("");
				clientListPage.func_PerformAction("Close Project");
				Thread.sleep(2000);
				strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, strAudienceName);
				bAudienceCreated= true;
			}
			
			//Step6: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(1000);
			
			//Step7,8,9&10: Navigate to Media Ranker Page
			arMediaRankerPage = new ArchitectMediaRankerPage(driver);
			
			//Verifying icon and various actions.
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");				
				
				try{//Dash icon verification for category
					if(verifyDashIconAndRevertBackFeature(0)){				
						if(verifyUncheckParentCategoryFeature(0)){
							
							//Now verifying same for sub category
							arMediaRankerPage.func_ClickCategoryByNumber(0);
							Thread.sleep(2000);
							
							if(verifyDashIconAndRevertBackFeature(1)){				
								if(verifyUncheckParentCategoryFeature(1)){
									
								}else{
									BaseClass.testCaseStatus=false;
								}
							}else{
								BaseClass.testCaseStatus=false;
							}
						}else{
							BaseClass.testCaseStatus=false;
						}
					}else{
						BaseClass.testCaseStatus = false;			
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
				
				if(bAudienceCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, false);
					CustomReporter.log("Deleted the audience");					
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
		ie.printStackTrace(System.out);
		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot("8313-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8313-"+strMsgAndFileName[1], "fail");	
		}		
	}
	

	public boolean verifyDashIconAndRevertBackFeature(int iLevel) throws Exception{
		boolean bStatus = true;
		String strLevel=iLevel==0?"category":"subcategory";
		String strSubLevel=iLevel==0?"subcategory":"itemcategory";
		int iCatId;
		
		//Going inside of 1st category.
		iCatId = arMediaRankerPage.getCategoryIdHavingMultipleSubCategory(strLevel, 3);
		arMediaRankerPage.func_ClickCategoryByNumber(strLevel,iCatId);
		Thread.sleep(2000);
		
		//Uncheck first 2 sub-categories
		arMediaRankerPage.func_ToggleCategoryByNumber(0,strSubLevel);
		arMediaRankerPage.func_ToggleCategoryByNumber(1,strSubLevel);
		//arMediaRankerPage.func_ToggleCategoryByNumber(2);
		CustomReporter.log("Uncheck few categories level " + strLevel);
				
		//Come back to category
		if(iLevel==0){
			//Waiting for list to refresh
			arMediaRankerPage.func_WaitTillListRefresh("subcategorylist");
			
			//Come back to category
			arMediaRankerPage.func_ClickOnElement("backtocategory");			
			
		}else if(iLevel==1){
			//Waiting for list to refresh
			arMediaRankerPage.func_WaitTillListRefresh("itemcategorylist");
			
			//Come back to sub-category
			arMediaRankerPage.func_ClickOnElement("backtosubcategory");
		}
		Thread.sleep(2000);
		CustomReporter.log("Navigated back to parent to verify 'dash' icon level " + strLevel);
		
		//Check the icon of first category now. It should be dash.
		if(iLevel==0){			
			if(!arMediaRankerPage.func_VerifyVisibilityOfElement("verifydashicon", "category:"+iCatId))
				bStatus = false;			
		
		}else if(iLevel==1){			
			if(!arMediaRankerPage.func_VerifyVisibilityOfElement("verifydashicon", "subcategory:"+iCatId))
				bStatus = false;
		}
		
		if(bStatus){
			
			strMsg ="Successfully verified 'dash' icon on " + strLevel;
			CustomReporter.log(strMsg);
			System.out.println(strClassName + ": " + strMsg);
			
			//Checking category back. To get on original state.
			arMediaRankerPage.func_ToggleCategoryByNumber(iCatId,strLevel);
			
			
			arMediaRankerPage.func_ClickCategoryByNumber(strLevel,iCatId);
			
			if(iLevel==0){
				//Waiting for list to refresh
				arMediaRankerPage.func_WaitTillListRefresh("subcategorylist");
				
				if(!arMediaRankerPage.func_VerifyAllCategoryChecked(1))
					bStatus = false;			
			
			}else if(iLevel==1){
				//Waiting for list to refresh
				arMediaRankerPage.func_WaitTillListRefresh("itemcategorylist");
				
				if(!arMediaRankerPage.func_VerifyAllCategoryChecked(2))
					bStatus = false;
			}
			
			if(bStatus){
				strMsg ="Successfully verified clicking on 'dash' should select all sub level category as well on " + strLevel;
				CustomReporter.log(strMsg);
				System.out.println(strClassName + ": " + strMsg);
			}else{
				bStatus = false;
				strMsg ="Faile to verify all sub level categories are marked after clicking on 'dash' icon for " + strLevel;
				CustomReporter.errorLog(strMsg);
				System.out.println(strClassName + ": " + strMsg);
			}
			
			//Come back to category
			if(iLevel==0){
				//Come back to category
				arMediaRankerPage.func_ClickOnElement("backtocategory");			
				
			}else if(iLevel==1){
				//Come back to sub-category
				arMediaRankerPage.func_ClickOnElement("backtosubcategory");
			}
			Thread.sleep(2000);
			
			
		}else{
			rm.captureScreenShot("8313-DashIconNotVerified-"+strLevel, "fail");
			strMsg ="Failed to verify 'dash' icon on " + strLevel;
			CustomReporter.errorLog(strMsg);
			System.out.println(strClassName + ": " + strMsg);
		}
		
		
		
		return bStatus;				
	}
	

	public boolean verifyUncheckParentCategoryFeature(int iLevel) throws Exception{
		boolean bStatus = true;
		String strLevel=iLevel==0?"category":"subcategory";
		
	//Deselect first category and check all sub-category status should be grayed.
		
		//Uncheck first category
		arMediaRankerPage.func_ToggleCategoryByNumber(0,strLevel);
		Thread.sleep(2000);
		CustomReporter.log("Unchecked first category for level " +strLevel);
		
		//Go inside the category
		arMediaRankerPage.func_ClickCategoryByNumber(strLevel,0);
		Thread.sleep(3000);
		CustomReporter.log("Navigated inside the category for level " +strLevel);
		
		//Check all the sub level categories are unchecked.
		
		if(iLevel==0){
			//Waiting for list to refresh
			//arMediaRankerPage.func_WaitTillListRefresh("subcategorylist");
			
			if(!arMediaRankerPage.func_VerifyAllCategoryUnChecked(1))
				bStatus = false;
			
		}else if(iLevel==1){
			//Waiting for list to refresh
			//arMediaRankerPage.func_WaitTillListRefresh("itemcategorylist");
			
			if(!arMediaRankerPage.func_VerifyAllCategoryUnChecked(2))
				bStatus = false;
		}
		if(bStatus){
			strMsg ="Successfully verified sub level categories are unchecked after unchecking parent category for " + strLevel;
			CustomReporter.log(strMsg);
			System.out.println(strClassName + ": " + strMsg);
		}else{			
			bStatus = false;
			strMsg ="Failed to verify sub level categories are unchecked after unchecking parent category for " + strLevel;
			CustomReporter.log(strMsg);
			System.out.println(strClassName + ": " + strMsg);
			rm.captureScreenShot("8313-SubCategoriesAreNotUncheckedAfterUncheckingParent-"+strLevel, "fail");
		}
		
		//Come back to category
		if(iLevel==0){
			//Come back to category
			arMediaRankerPage.func_ClickOnElement("backtocategory");			
			
		}else if(iLevel==1){
			//Come back to sub-category
			arMediaRankerPage.func_ClickOnElement("backtosubcategory");
		}
		Thread.sleep(2000);
		CustomReporter.log("Navigated back to parent category for level " + strLevel);
		
		//Check first category back
		arMediaRankerPage.func_ToggleCategoryByNumber(0,strLevel);
		Thread.sleep(2000);
		
		
		return bStatus;
	}
	
}
