package com.IDIOM.ClientHomePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Manage Projects 2.a : Verify create new project window.</p>
 *  <p> <b>Objective:</b>This test case will verify elements of Create New Project window.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8820.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 09 Jun 2016
 *
 */
public class ClientHomePage8820 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyNewProjectWindow(){		
		String strMsg="";
		boolean bStatus = true;
		
		try{
			
			String strExpCreateNewProjectLabel = property.getProperty("createNewProjectLabel");
			String strExpNewProjectNameLabel = property.getProperty("newProjectNameLabel");
			String strExpNewProjectDescLabel = property.getProperty("newProjectDescLabel");
			String strExpNewProjectNameWatermark = property.getProperty("newProjectNameWatermark");
			String strExpNewProjectDescWatermark = property.getProperty("newProjectDescWatermark");
			String strExpSaveBtnColor = property.getProperty("saveBtnColor");
			String strExpLaunchProjectBtnColor = property.getProperty("launchProjectBtnColor");
			//****************Test step starts here************************************************
						
			//Login To Selecting Client
			loginToSelectClient();			
			
			//Clicking on 'New Project'
			clientListPage.func_PerformAction("New Project");
			if(!rm.webElementSync(clientListPage.newProjectWindow, "visibility"))
				throw new IDIOMException("No new project window found.###NoNewProjectWindow");
			
			CustomReporter.log("New project window found");
			
			//Verifying elements
			clientListPage.findAndSaveProjectWindow(true, "");
						
			if(clientListPage.isVisible("projecttab")){
				CustomReporter.log("Project tab found on window");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to find 'Project' tab");
			}
			
			if(!clientListPage.isVisible("audiencetab")){
				CustomReporter.log("Audience tab is not visible");
			}else{
				bStatus = false;
				CustomReporter.errorLog("'Audience' tab found without creating project");
			}
			
			if(clientListPage.isVisible("projectsavebtn")){
				CustomReporter.log("Save button is visible");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to find 'Save' button");
			}
			
			if(clientListPage.isVisible("projectcancelbtn")){
				CustomReporter.log("Cancel button is visible.");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to find 'Cancel' button");
			}
			
			if(clientListPage.isVisible("projectclosebtn")){
				CustomReporter.log("Close button is visible.");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to find 'Close' button");
			}
			
			if(clientListPage.isVisible("projectnamefield")){
				CustomReporter.log("Name textfield is visible.");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to find 'Name' textfield");
			}
			
			if(clientListPage.isVisible("projectdescfield")){
				CustomReporter.log("Description field is visible. And it is a textarea");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to find 'Description' field");
			}
			
			//Verifying labels
			String strActualCreateNewProjectLabel = clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strCreateNewProjectLabel)).getText().trim();
			String strActualNewProjectNameLabel = clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strNewProjectWindowNameLabel)).getText().trim();
			String strActualNewProjectDescLabel = clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strNewProjectWindowDescLabel)).getText().trim();
			String strActualNewProjectNameWatermark = clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strProjectNameTxt)).getAttribute("placeholder").trim();
			String strActualNewProjectDescWatermark = clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strProjectDescTxt)).getAttribute("placeholder").trim();
			
			System.out.println("-"+strActualCreateNewProjectLabel+"-");
			
			if(strActualCreateNewProjectLabel.equalsIgnoreCase(strExpCreateNewProjectLabel)){
				CustomReporter.log("Verified create new project window label: " + strActualCreateNewProjectLabel);
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to compare new project window label. Expected " + strExpCreateNewProjectLabel +
						" and found " + strActualCreateNewProjectLabel);
			}
			
			if(strActualNewProjectNameLabel.equalsIgnoreCase(strExpNewProjectNameLabel)){
				CustomReporter.log("Verified name field label: " + strActualNewProjectNameLabel);
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to compare name field label. Expected " + strExpNewProjectNameLabel +
						" and found " + strActualNewProjectNameLabel);
			}
			
			if(strActualNewProjectDescLabel.equalsIgnoreCase(strExpNewProjectDescLabel)){
				CustomReporter.log("Verified description field label: " + strActualNewProjectDescLabel);
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to compare description field label. Expected " + strExpNewProjectDescLabel +
						" and found " + strActualNewProjectDescLabel);
			}
			
			if(strActualNewProjectNameWatermark.equalsIgnoreCase(strExpNewProjectNameWatermark)){
				CustomReporter.log("Verified name field watermark: " + strActualNewProjectNameWatermark);
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to compare name field watermark. Expected " + strExpNewProjectNameWatermark +
						" and found " + strActualNewProjectNameWatermark);
			}
			
			if(strActualNewProjectDescWatermark.equalsIgnoreCase(strExpNewProjectDescWatermark)){
				CustomReporter.log("Verified description field watermark: " + strActualNewProjectDescWatermark);
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to compare description field watermark. Expected " + strExpNewProjectDescWatermark +
						" and found " + strActualNewProjectDescWatermark);
			}
			
			
			//Verifying 'Save' and 'Launch Project' button color and enable
			WebElement saveBtn = clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strProjectSaveBtn));
			WebElement launchProjectBtn = clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strLaunchProjectBtn));
			String strActualSaveBtnColor = saveBtn.getCssValue("background-color");
			String strActualLaunchProjectBtnColor = launchProjectBtn.getCssValue("background-color");
			
			if(strExpSaveBtnColor.equalsIgnoreCase(strActualSaveBtnColor)){
				CustomReporter.log("Verified save button color and it is " + strActualSaveBtnColor);
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to verify save button color. Expected " +
						strExpSaveBtnColor + " and found " + strActualSaveBtnColor);				
			}
			
			if(strExpLaunchProjectBtnColor.equalsIgnoreCase(strActualLaunchProjectBtnColor)){
				CustomReporter.log("Verified Launch Project button color and it is " + strActualLaunchProjectBtnColor);
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to verify Launch Project button color. Expected " +
						strExpLaunchProjectBtnColor + " and found " + strActualLaunchProjectBtnColor);				
			}
			
			if(saveBtn.isEnabled()){
				bStatus = false;
				CustomReporter.errorLog("Save button found enabled");
			}else{
				CustomReporter.log("Save button is disabled");
			}
			
			if(launchProjectBtn.isEnabled()){
				bStatus = false;
				CustomReporter.errorLog("Launch Project button found enabled");
			}else{
				CustomReporter.log("Launch Project button is disabled");
			}
			
			if(!bStatus)
				throw new IDIOMException("Validation failed. Check above messages.###NewProjectWindowValidationFailed");
				
		}catch(Exception e){
			exceptionCode(e);
		}finally{
			try{
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
		
		String strTestCaseId = strClassName.replaceAll("\\D+","");		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot(strTestCaseId+"-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot(strTestCaseId+"-"+strMsgAndFileName[1], "fail");	
		}		
	}	
}
