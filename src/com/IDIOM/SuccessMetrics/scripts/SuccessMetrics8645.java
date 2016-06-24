package com.IDIOM.SuccessMetrics.scripts;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Success Metric_3.a.i. Success Metric Card - Verify card design on board. </p>
 * <p> <b>Objective:</b> To verify '+', 'X' sign for Success Metrics and once icon changed then background </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8645.aspx </p>
 * <p> <b>Module:</b> Audience Builder - Success Metrics</p>
 * @author Rohan Macwan
 * @since 06 May 2016
 *
 */
public class SuccessMetrics8645 extends BaseClass{

@Test
	
	public void	SuccessMetrics8645(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName=property.getProperty("projectName8700");
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			
			String strCommonRHSPlusBackroundColor=property.getProperty("CommonRHSPlusBackroundColor");
			String strCommonRHSCrossBackroundColor=property.getProperty("CommonRHSCrossBackroundColor");
			
			String strClientName=property.getProperty("clientName");
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Open IDIOM URL and login with valid credentials
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 2 Select any existing client from client dropdown
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 3 - Click on any project/Create a project and launch the same
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			clientListPage.launchProject(strProjectName);
			
				
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 4 - Click on destination link success metrics
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			
			
			CustomReporter.log("Select few Success Metrics");
			
			List<String> listCategories = new ArrayList<String>();
			
			listCategories=audienceBuilder.getCategoryNamesAtAnyLevel();
			
			//Step 5 - Go inside any category from RHS
			//Step 6 - Click on any '+' icon for any metric
			for(String strCat: listCategories){
				audienceBuilder.selectMetricByName(strCat);
				Thread.sleep(3000);
				
				List<String> subCat = audienceBuilder.getCategoryNamesAtAnyLevel();
				
				int totalSubCategories = subCat.size();
				
				System.out.println("subCat.size() - " + subCat.size());
								
				CustomReporter.log("Total Sub Categories Count for Category - '" + strCat + "' is - " + subCat.size());
				CustomReporter.log("Total Sub Categories having Plus icon Count for Category - '" + strCat + "' is - " + audienceBuilder.getCountForSubCategoriesHavingPlusIcon());
				
				if (!(totalSubCategories==audienceBuilder.getCountForSubCategoriesHavingPlusIcon()))
					throw new IDIOMException("Total Count for Sub Categories having Plus Icon does not match with Actual Number of Sub Categories. ###8645_SubCategoriesCount");				
				
				CustomReporter.log("Total Count for Sub Categories having Plus Icon matches with Actual Total Number of Sub Categories.");
				
				CustomReporter.log("Select any Metric - " + strCat.trim().toLowerCase()+"-" + subCat.get(1).trim().toLowerCase());
				
				audienceBuilder.selectMetricByName(strCat.trim().toLowerCase()+"-" + subCat.get(1).trim().toLowerCase());
				
				int totalPlusIcons=audienceBuilder.getCountForSubCategoriesHavingPlusIcon();
				int totalCrossIcons= audienceBuilder.getCountForSubCategoriesHavingCorssIcon();
				
				CustomReporter.log("Total Sub Categories Count for Category - '" + strCat + "' is - " + subCat.size());
				CustomReporter.log("Total Sub Categories having Plus icon Count for Category - '" + strCat + "' is - " + totalPlusIcons);
				CustomReporter.log("Total Sub Categories having Cross icon Count for Category - '" + strCat + "' is - " + totalCrossIcons);
				
				if (!(totalPlusIcons+totalCrossIcons==totalSubCategories))
					throw new IDIOMException("Total Counts for Sub Categories having Plus Icon and Cross Icon do not match. ###8645_SubCategoriesCrossPlusCount");
				
				CustomReporter.log("Total Plus Icon + Total Total Cross count) matches with Total Sub Categories Count.");
				
				String getBGColor="";
				int i=1;
				CustomReporter.log("Check background color for Selected Metrics");
				for(WebElement eCross: audienceBuilder.listCrossInRHS)
				{
					getBGColor="";
					getBGColor = eCross.getCssValue("background-color").trim();
					System.out.println("getBgColor = " + getBGColor);
					if (!(getBGColor.equals(strCommonRHSCrossBackroundColor)))
						throw new IDIOMException("Sub Category " +i +" Background Color does not match. ###8645_BackgroundColorRed");

					i++;
				}
				
				CustomReporter.log("Background color for Selected Metrics is correctly set as " + strCommonRHSCrossBackroundColor);
				
				i=1;
				CustomReporter.log("Check background color for Metrics Metrics which are not selected");
				for(WebElement ePlus: audienceBuilder.listPlusInRHS)
				{
					getBGColor="";
					getBGColor = ePlus.getCssValue("background-color").trim();
					System.out.println("getBgColor = " + getBGColor);
					if (!(getBGColor.equals(strCommonRHSPlusBackroundColor)))
						throw new IDIOMException("Sub Category " +i +" Background Color does not match. ###8645_BackgroundColorGrey");
				
					i++;
				}
				CustomReporter.log("Background color for Metrics which are not selected is correctly set as " + strCommonRHSPlusBackroundColor);
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
			rm.captureScreenShot("8645", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");					
					Thread.sleep(3000);
					clientListPage.func_PerformAction("Close Project");
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");
				}
				
				//Step 7 - Click on logout 
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
