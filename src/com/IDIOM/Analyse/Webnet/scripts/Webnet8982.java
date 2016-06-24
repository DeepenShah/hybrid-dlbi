package com.IDIOM.Analyse.Webnet.scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name</b> 612_Web Net: Export Data functionality should export data in csv file for selected transition window </p>
<p>	<b>Objective</b> Verify that export data exports correct data in csv file also file size should be more than 5KB. </p>
<p>	<b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8982.aspx </p>
<p>	<b>Module</b> WebNet </p>
@author: Shailesh Kava
@since: 31-May-2016
**********************************************************************/
public class Webnet8982 extends BaseClass {
	
	Analyse_Webnet_Page webnetPage;
	//HVA_Page hvaPage;
	String WebNetLink;
	String strDetails;
	String fileLocation;
	Actions action;
	String exportdatafilename;
	boolean bVerifiedData = true;
	@Test
	public void WebNet_verifyExportData(){
		
		WebNetLink = property.getProperty("webnet");
		exportdatafilename = property.getProperty("webnetexportdatafilename"); 
				
		String strMsg = null;
		String strProjectName="8982_"+rm.getCurrentDateTime();
		boolean bProjectCreate = false;
		
		webnetPage = new Analyse_Webnet_Page(driver);
		fileLocation = System.getProperty("user.dir");
		action = new Actions(driver);
		
		try{
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 3: Create/Select project and launch the same 
			CustomReporter.log("Creating new project as no project for this client");
			System.out.println("Creating new project as no project for this client");
			clientListPage.createNewProject(strProjectName);
			
			//Getting project and client id to delete through REST service
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			clientListPage.launchProject(strProjectName);
			bProjectCreate = true;
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, WebNetLink.trim()));
			Thread.sleep(2000);
			
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			CustomReporter.log("Navigating to webnet page");
			System.out.println("Navigating to webnet page");
			
			if(browserName.equalsIgnoreCase("ie")){
				System.out.println("clicking through action");
				pdp.navigateToByActionClass(WebNetLink.trim());
			}else{
				System.out.println("clicking through link");
				pdp.navigateTo(WebNetLink.trim());
			}
			
			rm.webElementSync("idiomspinningcomplete");
			rm.webElementSync(webnetPage.webNetImage, "visible");
			if(!webnetPage.isVisible("webnetimage"))
				throw new IDIOMException("Failed to open webnet page###webnet8982-failurewebnetLoading");
			
			CustomReporter.log("WebNet page is open successfully");
			
			//Get total transition window items from drop down
			ArrayList<String> listTransitionWindow = (ArrayList<String>) webnetPage.returnTransitionWindowItems();
			System.out.println(listTransitionWindow);
			
			for(int i=0; i<listTransitionWindow.size(); i++){
				
				System.out.println(listTransitionWindow.get(i)+"==="+webnetPage.getSelectedTransitionWindowItem(1).toLowerCase());
				
				if(!listTransitionWindow.get(i).equalsIgnoreCase(webnetPage.getSelectedTransitionWindowItem(1))){
					CustomReporter.log("Current selected transition window is ["+listTransitionWindow.get(i)+"]");
					webnetPage.selectTransitionWindowItem(listTransitionWindow.get(i));
					
					rm.webElementSync("idiomspinningcomplete");
					rm.webElementSync(webnetPage.webNetImage, "visible");
					if(!webnetPage.isVisible("webnetimage"))
						throw new IDIOMException("Failed to open webnet page###webnet8982-failurewebnetLoading");
				}
				
				CustomReporter.log("Downloading export data file for transition window ["+listTransitionWindow.get(i)+"]");
			
				//Export data code
				
				if(browserName.equalsIgnoreCase("ie")){
					System.out.println("IE browser");
					action.moveToElement(webnetPage.exportData_button).click().perform();
				}else{
					System.out.println("Not IE browser");
					webnetPage.exportData_button.click();
				}
				
				Thread.sleep(8000);
				
				//Calling method to perform download action
				rm.downloadFile();
				Thread.sleep(1000);
				
				String home = System.getProperty("user.home");
		        System.out.println("Dir: "+home);
		        File file = new File(home+"/Downloads/"+exportdatafilename);
		        
		        String filename = file.getAbsolutePath();
		        if(!file.exists()){
		        	rm.captureScreenShot("8982downloadFail_"+browserName, "fail");
		        	CustomReporter.errorLog("Failed to download file in browser ["+browserName+"]");
		        	System.out.println("Failed to download file in browser ["+browserName+"]");
		        	BaseClass.testCaseStatus = false;
		        }else{
		        	
		        	CustomReporter.log("File is successfully download in Browser ["+browserName+"]");
		        	System.out.println("File is successfully download in Browser ["+browserName+"]");
		        	System.out.println("Checking file size");
		        	Long filesize = file.length()/1024;
		        	
		        	System.out.println("File size is ["+filesize+"]");
		        	if(filesize <= 10){
		        		CustomReporter.errorLog("File size is not expected ["+filesize+" bytes], expected size is 10000 bytes");
		        		System.out.println("File size is not expected ["+filesize+" bytes], expected size is 10000 bytes");
		        		BaseClass.testCaseStatus = false;
		        	}else{
		        		CustomReporter.log("File is not emplty and size is ["+filesize+" bytes]");
		        	}
		        	
		        	//Verifying that data should be exported for selected transition window
		        	
		        	if(transitionWindowEntries(filename, listTransitionWindow.get(i))){
		        		
		        		CustomReporter.log("File contains data only for transition window ["+listTransitionWindow.get(i)+"]");
		        		System.out.println("File contains data only for transition window ["+listTransitionWindow.get(i)+"]");
		        	}else{
		        		bVerifiedData = false;
		        		CustomReporter.errorLog("File contains more than the transition window than expected");
		        	}
		        	
		        	System.out.println("Deleting file");
		            file.delete();
		            if(!file.exists())
		            	CustomReporter.log("File is deleted successfully from download directory");
		            	System.out.println("File is deleted successfully from download directory");
		        }
			}
			if(!bVerifiedData)
				throw new IDIOMException("WebNet window contains other than selected windows too###webnet8982-csvContainsOtherWindowsAlso");
			
			
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
			rm.captureScreenShot("8982", "fail");
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
	
	/**Method to verify record in downloaded csv file from webnet window
	 * 
	 * @param strFileName
	 * @param strSelectedWindow
	 * @return
	 */
	public boolean transitionWindowEntries(String strFileName, String strSelectedWindow){
		boolean bMoreThanOneWindow = true;
		ArrayList<String> webNetWindow = new ArrayList<String>();  
		
		String csvFile = strFileName;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		
		try {

			webNetWindow = new ArrayList<String>();
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				
				// use comma as separator
				String[] country = line.split(cvsSplitBy);
				String strFinal = country[0].replace("\"", "");
				
				//Adding unique windows in array List
				if(!webNetWindow.contains(strFinal.toLowerCase())){
					webNetWindow.add(strFinal.toLowerCase());
				}
			}

			//Removing first value as it is "Transaction Window"
			webNetWindow.remove(0);
			
			if(webNetWindow.contains(strSelectedWindow.toLowerCase())){
				CustomReporter.log("Selected Transition window ["+strSelectedWindow+"] is available in export data file");
				System.out.println("Selected Transition window ["+strSelectedWindow+"] is available in in export data file");
			}else{
				CustomReporter.errorLog("Selected Transition window ["+strSelectedWindow+"] is not available in in export data file");
				System.out.println("Selected Transition window ["+strSelectedWindow+"] is not available in in export data file");
				BaseClass.testCaseStatus = false;
			}
			
			System.out.println("Total webnet windows ["+webNetWindow.size()+"]");
			if(webNetWindow.size() > 1){
				CustomReporter.errorLog("Downloaded file contains more than the selected window ["+webNetWindow+"]");
				System.out.println("Downloaded file contains more than the selected window ["+webNetWindow+"]");
				bMoreThanOneWindow = false;
				BaseClass.testCaseStatus = false;
			}
			
			for(int i=1; i<webNetWindow.size(); i++){
				System.out.println(webNetWindow.get(i));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(webNetWindow);
	  return bMoreThanOneWindow;
	}
}