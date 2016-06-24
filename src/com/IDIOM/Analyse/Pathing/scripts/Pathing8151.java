package com.IDIOM.Analyse.Pathing.scripts;

import java.text.MessageFormat;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Test Case Name </b>Pathing_Verify_Category_highlighting_across_wheel</p>
<p><b>Test Case ID </b>http://qa.digitas.com/SpiraTest/523/TestCase/8151.aspx</p>
<p><b>Objective </b>Verify that double clicking any category block within the wheel will highlight the category across the wheel.</p>
<p><b>Module </b>Pathing</p>
@author Shailesh Kava
@since 16 September 2015
**********************************************************************/
public class Pathing8151 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;
	@Test
	public void test_Pathing8151() throws Exception{
	//****************Variables declaration and Initialization****************************
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8151_"+rm.getCurrentDateTime();
	//****************Test step starts here************************************************	
		try{	
			//Step1: Launch Browser
			loginToSelectClient();
			CustomReporter.log("Creating new project as no project for this client");
			System.out.println("Creating new project as no project for this client");
			clientListPage.createNewProject(strProjectName);
			
			//Getting project and client id to delete through REST service
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			clientListPage.launchProject(strProjectName);
			bProjectCreate = true;
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, pathingLink.trim()));
			Thread.sleep(2000);
			
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			CustomReporter.log("Navigating to Pathing page");
			System.out.println("Navigating to Pathing page");
			
			if(browserName.equalsIgnoreCase("ie")){
				System.out.println("clicking through action");
				pdp.navigateToByActionClass(pathingLink.trim());
			}else{
				System.out.println("clicking through link");
				pdp.navigateTo(pathingLink.trim());
			}
			
			Analyse_Pathing_Page pathingPage = new Analyse_Pathing_Page(driver);
			
			if(rm.webElementSync(pageHeader.loadingSpinner, "visibility"))
			     rm.webElementSync("idiomspinningcomplete");
			
			if(!pathingPage.isVisible("pathing_wheel"))
				throw new IDIOMException("Failed to open pathing page###8151-pathingWheelNotAppear");
			
			CustomReporter.log("Pathing page is appear properly");
					
			//Double click on path
			Thread.sleep(5000);
			Actions action = new Actions(driver);
			int i=0;
			while(pathingPage.list_probabilities.size()!=1 || i==10){
				System.out.println("Doublec click ["+i+"]");
				action.doubleClick(pathingPage.doubleClickElement).build().perform();
				i++;
			}
			
			//cheking if % element is present in wheel chart and probability
			if(!pathingPage.func_ElementExist("percentage_wheelChart") && pathingPage.func_ElementExist("percentage_probability"))
				throw new IDIOMException("% value is missing either of wheelChart or probability###8151-percentageMissingInWheelChargeOrProbability");
			
			CustomReporter.log("% value is appearing for wheelChart and probability");
			System.out.println("% value is appearing for wheelChart and probability");
			//getting % value from wheel chart
			String percentage_wheelChart = pathingPage.func_returnElementValue("percentage_wheelChart");
			String percentage_probability = pathingPage.func_returnElementValue("percentage_probability");
			
			System.out.println(percentage_wheelChart+"==="+percentage_probability);
			
			//comparission on both % values 
			if(!percentage_probability.contentEquals(percentage_wheelChart))
				throw new IDIOMException("% value is matching: "+percentage_wheelChart+"==="+percentage_probability+"###8151-percentageIsNotMatching");
			
			CustomReporter.log("% value of pathing wheel ["+percentage_wheelChart+"] is matched with probability percentage ["+percentage_probability+"]");
			
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
			rm.captureScreenShot("8151", "fail");
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
	}			