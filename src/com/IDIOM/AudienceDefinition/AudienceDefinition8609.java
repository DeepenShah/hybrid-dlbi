package com.IDIOM.AudienceDefinition;

import java.util.HashMap;
import java.util.List;

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
 * <p> <b>Test Case Name:</b> Audience Definition - a (Categories).i.1 - Verify that it it displays all future behaviors (Not only the ones which are selected and shown in Success Metrics)  </p>
 * <p> <b>Objective:</b> Verify that it it displays all future behaviors (Not only the ones which are selected and shown in Success Metrics)</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8609.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 06 May 2016
 *
 */
public class AudienceDefinition8609 extends BaseClass {

	@Test
	void verifChangeLogicForGroupAndSubGroup(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		boolean bAudienceCreated = false;
		String strAudienceName = "";
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strCommonMetric = property.getProperty("commonSuccessMetrics");
			String strAudDefCategory1 = property.getProperty("audienceDefinitionCategory1");
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
			
			//Create new audience
			if(!bProjectCreated){
				clientListPage.performActionOnProject("edit", strProjectName);
				clientListPage.findAndSaveProjectWindow(false, strProjectName);
				
				clientListPage.func_PerformAction("Audience Tab");
				
				strAudienceName = clientListPage.createNewAudience("");
				clientListPage.func_PerformAction("Close Project");
				Thread.sleep(2000);
				bAudienceCreated= true;
			}
			
			//Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			
			//Step4: Click on success metric link
			pdp.navigateTo(property.getProperty("successMetrics"));
			CustomReporter.log("Navigated to success metric page");
			
			
			AudienceBuilderPage abPage = new AudienceBuilderPage(driver);
			
			//Preparing List
			List<String> strFirstLevelCat = abPage.getCategoryNamesAtAnyLevel();
			
			HashMap<String,List<String>> strExpData = new HashMap<>();
			
			for(String strCat : strFirstLevelCat){				
				abPage.selectMetricByName(strCat);				
				strExpData.put(strCat, abPage.getCategoryNamesAtAnyLevel());
			}
			
			CustomReporter.log("Prepared expected data. First levels :" + strFirstLevelCat);
			
			//Step5: Add success metric
			abPage.selectMetricByName(strCommonMetric);
			
			//Step6: Navigate to audience
			abPage.performAction("gotoaudiencedefinition");
			rm.webElementSync(abPage.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Navigated to audience definition");
			
			//Preparing Actual Data.
			abPage.selectMetricByName(strAudDefCategory1);
			
			strFirstLevelCat = abPage.getCategoryNamesAtAnyLevel();
			HashMap<String,List<String>> strActualData = new HashMap<>();
			
			
			for(String strCat : strFirstLevelCat){
				abPage.goToFirstLevelForMetricOrAttribute();
				Thread.sleep(2000);
				abPage.selectAttributeOnAudienceDefinition(strAudDefCategory1+"|"+strCat+"|ss");;				
				strActualData.put(strCat, abPage.getCategoryNamesAtAnyLevel());
			}
			
			CustomReporter.log("Fetched actual data");
			
			boolean bStatus=true;
			for(String strKey:strExpData.keySet()){
				if(!strExpData.get(strKey).equals(strActualData.get(strKey))){
					bStatus = false;
					CustomReporter.errorLog("Not able to match metrics. Exp: " + strExpData.get(strKey) + " and Actual: " + strActualData.get(strKey));
				}else{
					CustomReporter.log("Verified metrics, "+ strExpData.get(strKey));	
				}
			}
			
			if(!bStatus)
				throw new IDIOMException("Failed to verify metrics as behaviors ###FailedToVerifyMetrics");
			
			CustomReporter.log("Successfully verified the metrics");
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			
			if(strMsgAndFileName.length==1){
				rm.captureScreenShot("8609-Exception", "fail");
			}else{
				rm.captureScreenShot("8609-"+strMsgAndFileName[1], "fail");	
			}
				
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8609-Exception", "fail");
		}finally{
			try{
				
				//Deleting newly created project or Audience								
				pageHeader.performAction("idiomlogo");;
				rm.webElementSync(pageHeader.personMenu,"visibility");
				if(bProjectCreated){		
				
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");					
				}
				
				if(bAudienceCreated && !bProjectCreated){
					clientListPage.performActionOnProject("edit", strProjectName);
					clientListPage.findAndSaveProjectWindow(false, strProjectName);
					
					clientListPage.func_PerformAction("Audience Tab");
					clientListPage.performActionOnAudience(strAudienceName, "delete");
				}		
				
				//Step8: Logout
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
}
