package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;

import common.BaseClass;

/** This test case is to verify that media ranker page should 
 *  not display any data if channel is not selected.
 * 
 * @author Deepen Shah
 *
 */
public class MediaRanker8301 extends BaseClass{
		
	@Test
	public void testMediaRanker8301(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
		String strMsg="";
		ManageIdiom_Page mi=null;
		boolean bIdiomCreation=false;
		String strIdiomName="";
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strFilters=property.getProperty("MRFilterCriteria8301");			
			
			
			//****************Test step starts here************************************************
			//Step1: Open URL
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
		    
		    //Step3: Select a particular Client from Client Listing page
		    ClientList_Page cl = new ClientList_Page(driver);
			cl.func_SelectClient(strClientName);
			strMsg = "The client "+strClientName+" has been opened";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Thread.sleep(3000);
			
			//Step4: Creating new idiom
			mi = new ManageIdiom_Page(driver);
			Thread.sleep(8000);
			strIdiomName=mi.func_CreateIdiomName("MR8301","New");
			mi.func_CreateNewIdiomOrRename(strIdiomName, "NotBlank");
			strMsg = "The idiom '"+strIdiomName+"' is created.";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
						
			//Step5: Selecting cards on Audience page
			Audience_Page ad= new Audience_Page(driver);
			ad.func_SelectFilters(strFilters);
			strMsg = "The filters, "+strFilters+ ", are selected";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);

			//Clicking on Analyze button
			ad.func_ClickElement("AnalyzeAudience");
			strMsg = "The user has clicked on Analyse button";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Thread.sleep(10000);
			bIdiomCreation=true;
			
			//Select6: Select architect tab
			arMediaRankerPage = new ArchitectMediaRankerPage(driver);
			arMediaRankerPage.func_ClickOnElement("architect");
			strMsg = "Clicked on Architect";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Thread.sleep(3000);
			
			//Step7: Verifying message which appears when no channel is selected.
			if(!arMediaRankerPage.func_CheckNoDataMsg()){
				BaseClass.testCaseStatus = false;
				strMsg = "Message is not displayed for no channel selection ";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}else{
				strMsg = "Message verified";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}			
			
		}catch(Exception e){
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
		}finally{
			
		}
		
		try{
			
			if(bIdiomCreation){
				//Deleting newly created IDIOM
				System.out.println("Now deleting newly created IDIOM");
				arMediaRankerPage.func_ClickOnElement("manage");
				Thread.sleep(3000);
				mi.func_PerformClickAction("Delete");
			}
			//Step8: Logout from the application
			mi.func_PerformClickAction("Logout");
			strMsg = "The user has been logged out";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
		}catch(Exception e){
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is occured, " + e.getMessage() + ", while deleting newly created idiom '"+strIdiomName +"'. Please check the log for more details");
		}
		
		if(!BaseClass.testCaseStatus){
			Assert.assertTrue(false);
		}
	}

}
