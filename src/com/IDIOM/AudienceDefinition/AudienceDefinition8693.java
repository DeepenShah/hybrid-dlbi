package com.IDIOM.AudienceDefinition;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>Audience Definition - DTASIDIOM-1987: Verify category should not be blank. </p>
 * <p> <b>Objective:</b> To check if category is there then it should have some attributes.</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8693.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 10 May 2016
 *
 */
public class AudienceDefinition8693 extends BaseClass {

	AudienceBuilderPage abPage=null;
	boolean bStatus = true;
	
	@Test
	void verifyAttributeSelectionElementInEachCategory(){
		
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;	
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			
			//****************Test step starts here************************************************
			
			strMsg = "Launched Browser and Enter URL";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step2: Select client
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
			
			
			//Step3: Select/Create project
			if(clientListPage.totalProject()>0){
				strProjectName = clientListPage.getProjectNameById(1);
			}else{
				strProjectName = clientListPage.createNewProject("");
				bProjectCreated = true;
			}	
			
			//Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			//Step4: Navigate to Audience Definition
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			
			abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			Thread.sleep(2000);
			CustomReporter.log("Navigated to audience definition page");
			
			
			//Step5: Drilling through category
			List<String> firstLevel = abPage.getCategoryNamesAtAnyLevel();
			
			for(String strLevel : firstLevel){
				abPage.selectCategory(strLevel);
				verifyAttributeSelection("", strLevel,strLevel,strLevel);			
			}
			
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8693-Exception", "fail");
			}else{
				rm.captureScreenShot("8693-"+strMsgAndFileName[1], "fail");	
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8693-Exception", "fail");
		}finally{
			try{								
				//Deleting newly created project				
				if(bProjectCreated){		
					pageHeader.performAction("idiomlogo");;
					rm.webElementSync(pageHeader.personMenu,"visibility");
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
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
	
	public boolean verifyAttributeSelection(String strLevel,String strParent,String strGrandParent,String strStart) throws Exception{
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		System.out.println("Level " + strLevel + " and parent " + strParent + " and grand " + strGrandParent);
		List<String> strSubCat = abPage.getCategoryNamesAtAnyLevel();
		
		if(strSubCat.size()==0){
			strLevel = strParent;
			System.out.println("Inside " + strLevel);
			
			//Verification code
			if(!abPage.isVisible("sliderddbutton", "")){
				bStatus = false;
				CustomReporter.errorLog("Failed to verify any element for " + strLevel);
				rm.captureScreenShot("8693-" + strLevel.replaceAll(" ", "").replaceAll(",", "-"), "fail");
			}else{
				CustomReporter.log("Verified attribute component for " + strLevel);
			}
			
			
			//Moving ahead & Go Back to parent					
			abPage.clickOnAttributeOrMetriclayerHeading(strGrandParent);
			Thread.sleep(2000);
			
			strParent = strGrandParent;
			return verifyAttributeSelection(strLevel, strParent,strGrandParent,strStart);
			
		}else{
			int iCatIndex =  strSubCat.indexOf(strLevel);
			System.out.println("Index is " + iCatIndex + " and size " + strSubCat.size());
			if(iCatIndex < 0){
				strGrandParent = strParent;
				strParent = strSubCat.get(0);
				abPage.selectCategory(strParent);
				return verifyAttributeSelection("", strParent,strGrandParent,strStart);
			}else if(iCatIndex == (strSubCat.size()-1)){
				System.out.println("Size matched");
				
				if(strParent.equalsIgnoreCase(strStart)){
					abPage.clickOnParentCategory();
					Thread.sleep(2000);
					return true;
				}else{
					abPage.clickOnParentCategory();
					Thread.sleep(2000);
					
					strLevel = strParent;
					strParent = strGrandParent = abPage.getCurrentCategoryHead();
					return verifyAttributeSelection(strLevel, strParent, strGrandParent, strStart);
				}
			}
			else{			
				//Clicking on next category			
				strParent = strSubCat.get(iCatIndex+1);	
				abPage.selectCategory(strParent);
				return verifyAttributeSelection("", strParent,strGrandParent,strStart);
			}
		}
			
		
	}
}
