package com.IDIOM.Analyse.HVA.scripts;


import java.util.ArrayList;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;

import com.reports.CustomReporter;


import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>*HVA_1.A_Verify Negative Values Bars</p>
 * <p> <b>Objective:</b> The HVA bar chart contains several negative (0 <) values. Positive values are allowed also but irrelevant for this scenario.</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/7992.aspx</p>
 * <p> <b>Module:</b> HVA</p>
 * @author Amrutha Nair
 * @since 25-May-2016
 *
 */
public class HVA7992 extends BaseClass {
		
	@Test
	public void verifyNegativeValuesInHVAGraph(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
	
		String strDetails=null;
		try{
			
			
		
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			String strAudienceAttributes1=property.getProperty("commonAudienceAttribute2");
			String BGColourBar=property.getProperty("backgroundcoloNegativeBars");
			String BarAligment=	property.getProperty("backgroundAlignmentNegativeBars");
			String LabelAligment=property.getProperty("backgroundAlignmentNegativeLabels");
			String ValuesAligment=property.getProperty("backgroundAlignmentNegativevalues");
			//****************Test step starts here************************************************
			
			//Step1 :	Launch browser and enter IDIOM Url
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
			//Step 2:	Enter valid username and password Click on 'login to idiom' button
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 3:In client home page, select a client from dropdown
	        clientListPage = new ClientList_Page(driver);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Verifies whether all project list or No project msg is coming for selected client
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			//Step 4:Click on new project button
			
			//create New Project
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Fill the project
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###7992-CreateProjectWindow");
										
			//Step 5:Enter valid name and description for project and click on Save button
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###7992-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
		
			//Step 7:Click on launch project
			clientListPage.func_PerformAction("Launch Project");
			strMsg = "Clicked on Launch Project";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Waiting till page get loaded
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			if(pdp.isVisible("projectname", strProjectName)){
				strMsg="The project home page has been launched and it is getting updated with selected project name";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The seleced project name is not coming in project home page";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("7992_projectname", "fail");
			}
			
			
			//Step 7:Click on Success Metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			
			
			
			//sTEP 9:Click on Audience Definition tab
			
			AD.performAction("successmetrics>arrow");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on '>' to navigate to audience definition tab");
			
			
			Thread.sleep(3000);
			
			//Step 9:Select few attributes and add them
			
			AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			AD.performAction("addattribute");
			Thread.sleep(2000);
		
			
			strMsg="The user has added a query in default group for the project  '"+strProjectName+"'";
			CustomReporter.log(strMsg);
			//step 10:	Click on HVA link from header mega menu
			PageHeader PH= new PageHeader(driver);
			PH.megaMenuLinksClick(property.getProperty("hva"));
			
			
			HVA_Page hva = new HVA_Page(driver);
			
			rm.webElementSync("idiomspinningcomplete");			
			if(rm.webElementSync(hva.behaviour,"visibility")){
				strMsg="The HVA page is getting loaded successfully";
				CustomReporter.log(strMsg);
			}
			
			//Step 11:Check bars color and on which side it extends when values < 0 
			ArrayList<String> behaviours= hva.func_CaptureList("Behaviour");
			for(int i=0;i<behaviours.size();i++){
				hva.selectBehaviorWithID(i);
				rm.webElementSync("idiomspinningcomplete");
				Thread.sleep(3000);
				strMsg="The user has selected behaviour  '"+behaviours.get(i)+"' from drop down";
				CustomReporter.log(strMsg);
				if(hva.negativeValuesNames.size()>0){
					strMsg="The negative bars are present for the behaviour  '"+behaviours.get(i)+"' ";
					CustomReporter.log(strMsg);
					if(hva.func_CheckBGColour("NegativeBarsColour", BGColourBar)){
						strMsg="The back ground colour for all negative bars are as expected";
						CustomReporter.log(strMsg);
					}
					else{
						strMsg="The back ground colour for all negative bars are not as expected";
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("7992_BGColour_"+i, "fail");
					}
					
					if(hva.verifyAlignment("NegativeBarsAlignment", BarAligment)){
						strMsg="The bar alignment  for all negative bars are as expected for '"+behaviours.get(i)+"'";
						CustomReporter.log(strMsg);
					}
					else{
						strMsg="The bar alignment for all negative bars are not as expected '"+behaviours.get(i)+"'";
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("7992_BarAlignment_"+i, "fail");
					}
					
					
				//Step 12:Check for the actions' name labels locations in the page when values < 0 
					if(hva.verifyAlignment("NegativeLabelAlignment", LabelAligment)){
						strMsg="The label  alignment  for all negative bars are as expected '"+behaviours.get(i)+"'";
						CustomReporter.log(strMsg);
					}
					else{
						strMsg="The label alignment for all negative bars are not as expected '"+behaviours.get(i)+"'";
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("7992_labelAlignment_"+i, "fail");
					}
					
					
				//Step 13:Check for the actions' numerical value labels positions in the page, when vales < 0
					if(hva.verifyAlignment("NegativeValuesAlignment", ValuesAligment)){
						strMsg="The values  alignment  for all negative bars are as expected '"+behaviours.get(i)+"'";
						CustomReporter.log(strMsg);
					}
					else{
						strMsg="The values alignment for all negative bars are not as expected '"+behaviours.get(i)+"'";
						CustomReporter.errorLog(strMsg);
						BaseClass.testCaseStatus=false;
						rm.captureScreenShot("7992_valueAlignment_"+i, "fail");
					}
					
				}
				
				else{
					strMsg="The negative bars are  not present for the behaviour  '"+behaviours.get(i)+"' ";
					CustomReporter.log(strMsg);
				}
			}
			
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
				rm.captureScreenShot("7992", "fail");
			}finally{
				try{
					
					//Deleting newly created project
					if(bProjectCreate){
						///////////pageHeader.navigateTo("projecthomepage");
						rm.deleteProjectOrAudience(strDetails, true);
						Thread.sleep(2000);
						
						CustomReporter.log("Deleted the project");
					
					}
					
					//Step 13:Click on logout				
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
