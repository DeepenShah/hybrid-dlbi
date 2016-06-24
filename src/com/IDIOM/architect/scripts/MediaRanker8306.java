package com.IDIOM.architect.scripts;

import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Media Ranker_4.3 a,b,c_Verify Check box icon behaviour and actions in Right Side Navigation </p>
 * <p> <b>Objective:</b> Verify Check box icon behaviour and actions in Right Side Navigation of Media Ranker page </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8306.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * 
 * @author Deepen Shah
 *
 */
public class MediaRanker8306 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyCheckUncheck(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		ClientList_Page clientListPage=null;
		
		String strProjectName="";
		String strAudienceName="";		
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;
		boolean bAudienceCreated = false;
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strMediaRankerPages = property.getProperty("mediaRankerItems");	
			String[] strExpDigTitle = property.getProperty("DigitalCategories").split(",");
			String[] strExpTvTitle = property.getProperty("TVCategories").split(",");
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
			String[] strChannels = strMediaRankerPages.split(",");
			arMediaRankerPage = new ArchitectMediaRankerPage(driver);
			
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				try{			
				
					// Verifying size of scattered plot before and after.
					Dimension beforeDim = arMediaRankerPage.func_GetSizeOfElement("scatteredplot");
					
					//Clicking on Checkbox
					rm.webElementSync(arMediaRankerPage.toggleCheckbox, "visibility");
					rm.webElementSync(arMediaRankerPage.toggleCheckbox, "clickable");
					arMediaRankerPage.func_ClickOnElement("togglecheckbox");
					
					Dimension afterDim = arMediaRankerPage.func_GetSizeOfElement("scatteredplot");
					if(afterDim.width <= beforeDim.width)
						throw new IDIOMException("Fails to expand scattered plot. Current width: "+ afterDim.width + " and before it was: " + beforeDim.width +
								".###FailedToExpandScatterPlot");
					
					CustomReporter.log("Scattered plot expanded after clicking checkbox");
					
					
					//Clicking on checkbox again
					arMediaRankerPage.func_ClickOnElement("togglecheckbox");
					Thread.sleep(1000);
					
					afterDim = arMediaRankerPage.func_GetSizeOfElement("scatteredplot");
					
					//Now width should be same. As coming back to normal state for scattered plot
					if(afterDim.width!=beforeDim.width)
						throw new IDIOMException("Checkbox doesn't affect scattered plot. Current width "+ afterDim.width + " and earlier "+
					beforeDim.width + ".###FailedToOpenCategoryPanel");
					
					CustomReporter.log("Scattered plot retain to its original size after checking checkbox again");
														
					//Verifying check uncheck on categories and drilling down.					
					String strTitle="";
					boolean bStatus = true;
					for(int j =0;j<3;j++){
						
						//Matching title name
						if(strChannel.toLowerCase().contains("digital")){
							strTitle = strExpDigTitle[j];
						}else{
							strTitle=strExpTvTitle[j];
						}
						
						if(!verifyCirclesAfterCheckUncheckCategory(0, arMediaRankerPage,j)){
							bStatus = false;
							strMsg = "Check Uncheck doesn't work properly on categories for "+ strChannel;
							CustomReporter.errorLog(strMsg);
							System.out.println(strMsg);
						}else{
							strMsg = "Successfully tested check un-check of categories. It is affecting on circles displayed on scattered plot";
							CustomReporter.log(strMsg);
							System.out.println(strMsg);
						}
						 
						if(!arMediaRankerPage.func_GetValue("categoryheaderpanel"+j).equalsIgnoreCase(strTitle)){
							bStatus=false;
							strMsg="Title is not matching. Expected: " + strTitle + " and actual: " + arMediaRankerPage.func_GetValue("categoryheaderpanel" + j);
							CustomReporter.errorLog(strMsg);
							System.out.println(strMsg);
						}else{
							strMsg="Title is matched. Expected: " + strTitle + " and actual: " + arMediaRankerPage.func_GetValue("categoryheaderpanel" + j);
							CustomReporter.log(strMsg);
							System.out.println(strMsg);
						}
						
						//Drill down to category(Daypart)
						int iCatId;
						if(j!=2){							
							if(j==0){
								iCatId = arMediaRankerPage.getCategoryIdHavingMultipleSubCategory("category", 2);
								arMediaRankerPage.func_ClickCategoryByNumber("category",iCatId);
								rm.webElementSync(arMediaRankerPage.func_GetLocalWebElement("subCategory"), "visibility", "", null);
								CustomReporter.log("Navigated to subcategory");
							}else if(j==1){
								iCatId = arMediaRankerPage.getCategoryIdHavingMultipleSubCategory("subcategory", 2);
								arMediaRankerPage.func_ClickCategoryByNumber("subcategory",iCatId);
								rm.webElementSync(arMediaRankerPage.func_GetLocalWebElement("itemCategory"), "visibility", "", null);
								CustomReporter.log("Navigated to item category");
							}
						}
				
					
					}
					if(!bStatus)
						throw new IDIOMException("Verification failed for " +strChannel +".###DontCheckThis");
				
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
	
	public void exceptionCode(Exception ie){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
		ie.printStackTrace(System.out);
		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot("8306-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8306-"+strMsgAndFileName[1], "fail");	
		}		
	}	

	public boolean verifyCirclesAfterCheckUncheckCategory(int iCatNum, ArchitectMediaRankerPage arMRPage,int iCatLevel) throws Exception{
		boolean bRes = true;
		String strMsg;
		String strCat[] = {"categories","subcategories","itemcategories"};
		
		//Compare no. of categories VS displayed circle.
		int iCatCount = arMRPage.func_GetCount(strCat[iCatLevel]);
		
		//Showing all circles.
		if(iCatCount > 25){
			arMRPage.func_ShowAllCircle(iCatCount);
			rm.webElementSync(arMRPage.func_GetLocalWebElement("chartHeaderTitle"), "visibility", "", null);
		}
		
		int iCircleCountBefore=arMRPage.func_GetCount("circlesinplot");		
		int iMatchCount=(iCircleCountBefore==iCatCount)?iCatCount:0;
		
		if(iMatchCount==0){
			bRes=false;
			strMsg = "Circle count in scattered plot and categories in list are not matching. Circles: " + iCircleCountBefore + " and Categories: "+ iCatCount;			
			System.out.println(strClassName + ": " + strMsg);
			CustomReporter.errorLog(strMsg);
		}
		
		//Uncheck on first category
		Thread.sleep(5000);
		arMRPage.func_ToggleCategoryByNumber(iCatNum,strCat[iCatLevel]);		
		rm.webElementSync(arMRPage.func_GetLocalWebElement("chartHeaderTitle"), "verifytext", "Currently displaying " + (iCatCount-1), null);
		
		//Now matching count
		int iCircleCountAfter = arMRPage.func_GetCount("circlesinplot");
		if(iCircleCountAfter!=(iCircleCountBefore-1)){
			bRes=false;
			strMsg = "Unchecking category doesn't affect on scattered plot circles. Circles: " + iCircleCountAfter + " and Earlier: "+ iCircleCountBefore;			
			System.out.println(strClassName + ": " + strMsg);
			CustomReporter.errorLog(strMsg);
			rm.captureScreenShot("8306-NotAbleToUnCheck-"+strCat[iCatLevel], "fail");
		}
		
		//Check first category
		arMRPage.func_ToggleCategoryByNumber(iCatNum,strCat[iCatLevel]);
		rm.webElementSync(arMRPage.func_GetLocalWebElement("chartHeaderTitle"), "verifytext", "Currently displaying " + iCatCount, null);
		
		//Now matching count
		iCircleCountAfter = arMRPage.func_GetCount("circlesinplot");
		if(iCircleCountAfter!=iCircleCountBefore){
			bRes=false;
			strMsg = "Category doesn't affect on scattered plot circles after checking back. Circles: " + iCircleCountAfter + " and Earlier: "+ iCircleCountBefore;			
			System.out.println(strClassName + ": " + strMsg);
			CustomReporter.errorLog(strMsg);
			rm.captureScreenShot("8306-NotAbleToCheckBack-"+strCat[iCatLevel], "fail");
		}
		
		return bRes;
	}
	
}
