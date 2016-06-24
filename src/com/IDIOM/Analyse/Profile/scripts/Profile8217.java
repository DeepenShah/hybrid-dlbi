/********************************************************************
Test Case Name:559_Verify Graphs getting populated_For Only Unknown
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8222.aspx
Objective:Verify Graphs getting populated_For Only Unknown
Module: Analyse>Profile
Created By: Vikram Hegde
Date: 21 September 2015
 **********************************************************************/

package com.IDIOM.Analyse.Profile.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

public class Profile8217 extends BaseClass {

  @Test
  public void test_Profile8217() throws Exception {
    // ****************Variables declaration and Initialization****************************

    String emailid = property.getProperty("email");
    String password = property.getProperty("password");
    String clientName = property.getProperty("defaultClient");
    String projectName = property.getProperty("9070projectName");
    String projectDescription = property.getProperty("9070projectDescription");
    String audienceAttributeUnkown6 = property.getProperty("8222audienceUnkownAttribute6");
    String audienceAttributeUnkown7 = property.getProperty("8222audienceUnkownAttribute7");
    String audienceSomeMoreAttributes1 = property.getProperty("commonAudienceAttribute3");
    boolean bProjectCreate = false;
    Login_Page ln = new Login_Page(driver);
    ClientList_Page cl = new ClientList_Page(driver);
    AudienceBuilderPage ad = new AudienceBuilderPage(driver);
    Analyse_Profile_Page ap = new Analyse_Profile_Page(driver);
    String filter = property.getProperty("FilterCriteria");
    // ****************Test step starts here************************************************
    try {
      // Step 1: Open Site Url and

      CustomReporter.log("Browser launched and site url entered");

      // Step 2: Login as valid user
      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully as user admin / super user");

      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      Thread.sleep(5000);

      // Step 3:Select any existing client from client dropdown

      cl.selectClient(clientName);
      CustomReporter.log("The client " + clientName + " has been opened");

      // Step 4:Click edit for any project/Create a project
      Thread.sleep(3000);
      cl.func_PerformAction("New Project");
      rm.webElementSync(cl.newProjectWindow, "visibility");
      CustomReporter.log("Create New Project Window appeared successfully");
      bProjectCreate = true;
      cl.findAndSaveProjectWindow(true, "");

      // Before filling details, checking 'Audience' tab is exist or not. It should be false.
      if (cl.func_ClientListPageElementExist("audiencetab"))
        throw new IDIOMException(
            "Not able to verify new project window###SmokeScenario3-CreateProjectWindow");

      // If audience tab is not found then everything is good
      // Step 5: Input Project name and description and click on Save
      cl.fillProject(projectName, projectDescription);
      cl.func_PerformAction("Save Project");

      Thread.sleep(5000);

      if (!(cl.func_ClientListPageElementExist("audiencetab") && cl
          .func_ClientListPageElementExist("audiencetablabel")))
        throw new IDIOMException("Fails to land on Audience tab. ");

      CustomReporter.log("Project " + projectName + " saved successfully. And found Audience tab");

      Thread.sleep(3000);

      // Step 6: Launch the project
      cl.func_PerformAction("Launch Project");
      CustomReporter.log("Clicked on Launch Project");

      // Waiting till page get loaded
      ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
      rm.webElementSync(pdp.projectName, "visibility");
      Thread.sleep(5000);

      // Step 7: Click on audience definition link
      pdp.navigateTo(property.getProperty("audienceDefinition"));
      CustomReporter.log("Navigated to Audience page successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      // Step 8 : Select only unknown filter card for household size & highest education
      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown6);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown7);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      if (2 == ad.getNumberOfAddedAudienceAttributesList()) {
        CustomReporter.log("Unknown filters selected for household size and highest education");
      } else {
        CustomReporter
            .errorLog("Unknown filters selected for household size and highest education");
        BaseClass.testCaseStatus = false;
      }

      // Step 9: Select some more filters in other sections
      ad.selectAttributeOnAudienceDefinition(audienceSomeMoreAttributes1);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      if (3 == ad.getNumberOfAddedAudienceAttributesList()) {
        CustomReporter.log("Selected some more filters in other sections");
      } else {
        CustomReporter.errorLog("Some more filters in other sections are not selected");
        BaseClass.testCaseStatus = false;
      }

      // Step 10: Click on Profile link from megamenu
      pageHeader.megaMenuLinksClick("Profile");
      CustomReporter.log("Profile page loaded successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      // Step 11: Check demographics section
      if (ap.func_VerifyProfileGraphs(filter)) {
        CustomReporter.log("Demographics graphs are proper");
      } else {
        CustomReporter.errorLog("Demographics graphs are not proper");
        BaseClass.testCaseStatus = false;
      }

    } catch (Exception e) {
      e.printStackTrace();
      BaseClass.testCaseStatus = false;
      e.printStackTrace(System.out);
      CustomReporter.errorLog("Some exception is generated, " + e.getMessage()
          + ". Please check the log for more details");
      rm.captureScreenShot(rm.getScreenShotName(this.getClass().getName()), "fail");
    } finally {
      // Step 9: Logout from application.
      try {
        if (bProjectCreate) {
          pageHeader.navigateTo("projecthomepage");
          rm.webElementSync(pageHeader.personMenu, "visibility");

          cl.func_PerformAction("Close Project");

          cl.performActionOnProject("delete", projectName);
          cl.performActionOnProject("yes", "");
          Thread.sleep(5000);
          CustomReporter.log("Deleted the project");

        }
        pageHeader.performAction("logout");
        CustomReporter.log("Logged out successfully");
      } catch (Exception ee) {
        ee.printStackTrace();
      }
    }
    if (BaseClass.testCaseStatus == false) {
      CustomReporter.errorLog("Test case has failed");
      Assert.assertTrue(false);
    }
  }
}
