package com.IDIOM.SuccessMetrics.scripts;

import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:<b> 1961_Population_IndividualSuccessMatrics Percentage
<p>	<b>Objective:<b> Verify percentage given for individual success matrics in left panel for Projected population and actual
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8685.aspx
<p>	<b>Module:<b> Success Metrics
@author:<b> Shailesh Kava
@since:<b> 04-May-2016
**********************************************************************/
public class SuccessMetrics8685 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder = null;
	
	@Test
	public void successMetricsLeftSideSuccessMetricsAttributes(){
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		audienceBuilder = new AudienceBuilderPage(driver);
		
		try{
			loginToSelectClient();
			
			//Step 3: Creating new project and launch it
			strProjectName = clientListPage.createNewProject("");
			System.out.println("Clicking on project name: "+strProjectName);
			CustomReporter.log("Clicking on project name: "+strProjectName);
			bProjectCreate = true;

			Thread.sleep(3000);
			clientListPage.launchProject(strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			//Step4: Click on destination link success metrics
			CustomReporter.log("Navigate to success metrics page");
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###SuccessMetrics8685-pageIsNotVisible");
			
			CustomReporter.log("Verify success metrics page is open, adding metrics");
			
			//Step 5: Verifying that Project tab is selected and Selecting few metrics
			if(!audienceBuilder.isVisible("projectedactivetab", ""))
				throw new IDIOMException("By default projected tab should be selected###SuccessMetrics8685-projectedTabIsNotSelected");
				
			audienceBuilder.selectMetricByName(property.getProperty("SelectingSuccessMetrics8685"));
			
			CustomReporter.log("User is on <b>Projected</b> tab");
			System.out.println("User is on Projected tab");
			//Step 6: Verifying added success metrics from left side in 'Project' Tab
			getSuccessMetricsAttributes("projected");
			
			//Step 7: Clicking on Actual Tab
			audienceBuilder.performAction("actualpopulationtab");
			Thread.sleep(5000);
			CustomReporter.log("User is on <b>Actual</b> tab");
			System.out.println("User is on Actual tab");
			
			//Step 8: Verifying added success metrics from left side in 'Actual' Tab
			getSuccessMetricsAttributes("actual");
			
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
			rm.captureScreenShot("8685", "fail");
		}finally{
			try{
				
				//Deleting newly created project
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
	
	@SuppressWarnings("unchecked")
	public void getSuccessMetricsAttributes(String tabName) throws Exception{
		
		ArrayList<String> successMetricsAttributes = new ArrayList<String>();
		successMetricsAttributes =audienceBuilder.successMetricsLeftSideGetAllAttributes(tabName);
		
		System.out.println(successMetricsAttributes);
		String[] successMetricsAtributes = successMetricsAttributes.toString().split(",");
		
		
		int arrSize = successMetricsAtributes.length;
		int prevMetricsPercentageVal = 0;
		
		if(arrSize >= 1){
			for(int i=0; i<arrSize; i++){
				String[] splitAttributes = successMetricsAtributes[i].split(":");
				
				int percentageVal = Integer.parseInt(splitAttributes[1]);
				String bgColor = splitAttributes[2];
				
				if(i==0){
					if(!bgColor.equals("red")){
						System.out.println("BG color should be red for first metrics");
						throw new IDIOMException("BG color should be red for first metrics###SuccessMetrics8685-bgColor"+i+"metrics");
					}else{
						CustomReporter.log("BG color is red for first metrics");
					}
				}else{
					if(prevMetricsPercentageVal < percentageVal && !bgColor.equals("grey")){
						System.out.println("Percentage value should be less than previouls one and bgcolor should be grey");
						throw new IDIOMException("Percentage value should be less than previouls one and bgcolor should be gray###SuccessMetrics8685-bgColorPercentage"+i+"metrics");
					}else{
						CustomReporter.log("Percentage value is less than previous one and bgcolor is grey");
					}
				}
				
				prevMetricsPercentageVal = percentageVal;
			}
		}
	}
}