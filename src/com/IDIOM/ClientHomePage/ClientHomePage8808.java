package com.IDIOM.ClientHomePage;

import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Manage Projects 1.a.i : Check the columns displayed for project table. </p>
 * <p> <b>Objective:</b> For Projects, mainly 5 columns are displayed. 1. Project Name 2. Audience 3. Created 4. Modified 5. (Toggle Option) </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8808.aspx </p>
 * <p> <b>Module:</b> Client Home Page </p>
 * @author Rohan Macwan
 * @since 02 June 2016
 *
 */
public class ClientHomePage8808 extends BaseClass {
@Test
	
	public void	verifyColumnNames(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		
		try
		{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
					
			String strClientName=property.getProperty("clientName");
			
			String strnameColumn=property.getProperty("nameColumn");
			String strcreatedColumn=property.getProperty("createdColumn");
			String strmodifiedColumn=property.getProperty("modifiedColumn");
			
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Open appropriate environment URL.
			CustomReporter.log("Open appropriate environment URL.");
			Thread.sleep(3000);
			//Step 2 - Provide valid credential
			CustomReporter.log("Provide valid credential.");
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 3 - Select a client if not selected in previous login
			CustomReporter.log("Select a client if not selected in previous login.");
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 4 - Check all the columns for project table
			
			CustomReporter.log("Check the text that appears for Column 'Name' ");
			if(!clientListPage.nameColumn.getText().trim().equals(strnameColumn))
				throw new IDIOMException("Column Text - " + clientListPage.nameColumn.getText().trim()+ " does not match with Expected text - " + strnameColumn +" . ###8808_NameColumnIssue");
			
			CustomReporter.log("It correctly appears as " + strnameColumn);
			
			CustomReporter.log("Check the text that appears for Column 'Auidnece'. It should be nothing ");
			if(!(clientListPage.audienceColumn.getText().equals(null) || clientListPage.audienceColumn.getText().trim().equals("")))
				throw new IDIOMException("Column Text is set as - " + clientListPage.nameColumn.getText().trim()+ ". It should be nothing. ###8808_AudienceColumnIssue");
			
			CustomReporter.log("It is correctly not set");

			CustomReporter.log("Check the text that appears for Column 'Created' ");
			if(!clientListPage.createdColumn.getText().trim().equals(strcreatedColumn))
				throw new IDIOMException("Column Text - " + clientListPage.createdColumn.getText().trim()+ " does not match with Expected text - " + strcreatedColumn +" . ###8808_CreatedColumnIssue");

			CustomReporter.log("It correctly appears as " + strcreatedColumn);
			
			CustomReporter.log("Check the text that appears for Column 'Modified' ");
			if(!clientListPage.modifiedColumn.getText().trim().equals(strmodifiedColumn))
				throw new IDIOMException("Column Text - " + clientListPage.modifiedColumn.getText().trim()+ " does not match with Expected text - " + strmodifiedColumn +" . ###8808_ModifiedColumnIssue");

			CustomReporter.log("It correctly appears as " + strmodifiedColumn);
			
			CustomReporter.log("Check the text that appears for Column 'Toggle Options'. It should be nothing ");
			if(!(clientListPage.toggleOptionColumn.getText().equals(null) || clientListPage.toggleOptionColumn.getText().trim().equals("")))
				throw new IDIOMException("Column Text is set as - " + clientListPage.toggleOptionColumn.getText().trim()+ ". It should be nothing. ###8808_ToggleOptionColumnIssue");
			
			CustomReporter.log("It is correctly not set");
			
			//Step 5 - Check the alignment of data in each column
			boolean bStatus =true;
			Point pPoint=null;
			
			//Checking Alignment for Project List (Names)
			pPoint=clientListPage.projectList.get(0).getLocation();
					
			for(int i=1;i<clientListPage.projectList.size();i++)
			{
				Point pPointTemp=null;
				pPointTemp=clientListPage.projectList.get(i).getLocation();
				
				if (pPointTemp.x!=pPoint.x){
					CustomReporter.errorLog("Alignment issue is noticed for Project " + clientListPage.projectList.get(i).getText() + "Please refer screen shot for more information");
					rm.captureScreenShot("ProjectList_Row" +i +"_8808", "fail");
					bStatus=false;
				}
				else
				{
					CustomReporter.log("Alignment is correctly set for Project " + clientListPage.projectList.get(i).getText());
				}
			}
			if (!bStatus)
			{
				BaseClass.testCaseStatus=false;
			}
			
			//Checking Alignment for Audience Column
			bStatus=true;
			pPoint=clientListPage.listAudienceColumn.get(0).getLocation();
					
			for(int i=1;i<clientListPage.listAudienceColumn.size();i++)
			{
				Point pPointTemp=null;
				pPointTemp=clientListPage.listAudienceColumn.get(i).getLocation();
				
				if (pPointTemp.x!=pPoint.x){
					CustomReporter.errorLog("Alignment issue is noticed for Audience Column in Project " + clientListPage.projectList.get(i).getText() + "Please refer screen shot for more information");
					rm.captureScreenShot("AudienceList_Row" +i +"_8808", "fail");
					bStatus=false;
				}
				else
				{
					CustomReporter.log("Alignment is correctly set for Audience dropdown in Project " + clientListPage.projectList.get(i).getText());
				}
			}
			if (!bStatus)
			{
				BaseClass.testCaseStatus=false;
			}
			
			//Checking Alignment for Created Column
			bStatus=true;
			pPoint=clientListPage.listCreatedColumn.get(0).getLocation();
					
			for(int i=1;i<clientListPage.listCreatedColumn.size();i++)
			{
				Point pPointTemp=null;
				pPointTemp=clientListPage.listCreatedColumn.get(i).getLocation();
				
				if (pPointTemp.x!=pPoint.x){
					CustomReporter.errorLog("Alignment issue is noticed for Created Date in Project " + clientListPage.projectList.get(i).getText() + "Please refer screen shot for more information");
					rm.captureScreenShot("CreatedList_Row" +i +"_8808", "fail");
					bStatus=false;
				}
				else
				{
					CustomReporter.log("Alignment is correctly set for Created Date in Project " + clientListPage.projectList.get(i).getText());
				}
			}
			if (!bStatus)
			{
				BaseClass.testCaseStatus=false;
			}
			
			//Checking Alignment for Modified Column
			bStatus=true;
			pPoint=clientListPage.listModifiedColumn.get(0).getLocation();
					
			for(int i=1;i<clientListPage.listModifiedColumn.size();i++)
			{
				Point pPointTemp=null;
				pPointTemp=clientListPage.listModifiedColumn.get(i).getLocation();
				
				if (pPointTemp.x!=pPoint.x){
					CustomReporter.errorLog("Alignment issue is noticed for Modified Date in Project " + clientListPage.projectList.get(i).getText() + "Please refer screen shot for more information");
					rm.captureScreenShot("ModifiedList_Row" +i +"_8808", "fail");
					bStatus=false;
				}
				else
				{
					CustomReporter.log("Alignment is correctly set for Modified Date in Project " + clientListPage.projectList.get(i).getText());
				}
			}
			if (!bStatus)
			{
				BaseClass.testCaseStatus=false;
			}
			
			//Checking Alignment for Toggle Option Column
			bStatus=true;
			pPoint=clientListPage.listToggleOptionColumn.get(0).getLocation();
					
			for(int i=1;i<clientListPage.listToggleOptionColumn.size();i++)
			{
				Point pPointTemp=null;
				pPointTemp=clientListPage.listToggleOptionColumn.get(i).getLocation();
				
				if (pPointTemp.x!=pPoint.x){
					CustomReporter.errorLog("Alignment issue is noticed for Toggle Option in Project " + clientListPage.projectList.get(i).getText() + "Please refer screen shot for more information");
					rm.captureScreenShot("ToggleOptionList_Row" +i +"_8808", "fail");
					bStatus=false;
				}
				else
				{
					CustomReporter.log("Alignment is correctly set for Toggle Option in Project " + clientListPage.projectList.get(i).getText());
				}
			}
			if (!bStatus)
			{
				BaseClass.testCaseStatus=false;
			}
			
			//****************Test step ends here************************************************
			
			if(!BaseClass.testCaseStatus){
				CustomReporter.errorLog("Test case failed");
				Assert.assertTrue(false);
			}else{
				CustomReporter.log("Test case passed");
			}
		
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("" + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8808", "fail");
		}finally{
			try{
				//Step 6 - Click on logout
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
