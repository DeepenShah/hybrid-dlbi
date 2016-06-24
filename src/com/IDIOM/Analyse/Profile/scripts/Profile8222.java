/********************************************************************
Test Case Name:Profile Page_Verify graph appears correctly after following below steps
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8222.aspx
Objective:Verify graph appears correctly after following below steps
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

public class Profile8222 extends BaseClass {

  @Test
  public void test_Profile9070() throws Exception {
    // ****************Variables declaration and Initialization****************************

    String emailid = property.getProperty("email");
    String password = property.getProperty("password");
    String clientName = property.getProperty("defaultClient");
    String projectName = property.getProperty("9070projectName");
    String projectDescription = property.getProperty("9070projectDescription");
    String audienceAttributeUnkown1 = property.getProperty("8222audienceUnkownAttribute1");
    String audienceAttributeUnkown2 = property.getProperty("8222audienceUnkownAttribute2");
    String audienceAttributeUnkown3 = property.getProperty("8222audienceUnkownAttribute3");
    String audienceAttributeUnkown4 = property.getProperty("8222audienceUnkownAttribute4");
    String audienceAttributeUnkown5 = property.getProperty("8222audienceUnkownAttribute5");
    String audienceAttributeUnkown6 = property.getProperty("8222audienceUnkownAttribute6");
    String audienceAttributeUnkown7 = property.getProperty("8222audienceUnkownAttribute7");
    String audienceAttributeUnkown8 = property.getProperty("8222audienceUnkownAttribute8");
    String audienceAttributeUnkown9 = property.getProperty("8222audienceUnkownAttribute9");
    String audienceAttributeUnkown10 = property.getProperty("8222audienceUnkownAttribute10");
    String filter = property.getProperty("FilterCriteria");
    boolean bProjectCreate = false;
    Login_Page ln = new Login_Page(driver);
    ClientList_Page cl = new ClientList_Page(driver);
    AudienceBuilderPage ad = new AudienceBuilderPage(driver);
    Analyse_Profile_Page ap = new Analyse_Profile_Page(driver);
    // ****************Test step starts here************************************************
    try {
      // Step 1: Open Site Url and Login as valid user

      CustomReporter.log("Browser launched and site url entered");

      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully as user admin / super user");

      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      Thread.sleep(5000);

      // Step 2:Select any existing client from client dropdown

      cl.selectClient(clientName);
      CustomReporter.log("The client " + clientName + " has been opened");

      // Step 3:Click edit for any project/Create a project
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
      // Step 4: Input Project name and description and click on Save
      cl.fillProject(projectName, projectDescription);
      cl.func_PerformAction("Save Project");

      Thread.sleep(5000);

      if (!(cl.func_ClientListPageElementExist("audiencetab") && cl
          .func_ClientListPageElementExist("audiencetablabel")))
        throw new IDIOMException("Fails to land on Audience tab. ");

      CustomReporter.log("Project " + projectName + " saved successfully. And found Audience tab");

      cl.createNewAudience("");

      Thread.sleep(3000);

      cl.func_getListOfAudiencesForEditedProject();

      // Step 5: Launch the project
      cl.func_PerformAction("Launch Project");
      CustomReporter.log("Clicked on Launch Project");

      // Waiting till page get loaded
      ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
      rm.webElementSync(pdp.projectName, "visibility");
      Thread.sleep(5000);

      // Step 6: Click on audience definition link
      pdp.navigateTo(property.getProperty("audienceDefinition"));
      CustomReporter.log("Navigated to Audience page successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      // Step 7 : Add a query in such a way that only Unknown should be there in query
      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown1);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown2);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown3);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown4);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown5);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown6);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown7);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown8);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown9);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown10);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      if (10 == ad.getNumberOfAddedAudienceAttributesList()) {
        CustomReporter.log("10 Unknown attributes added successfully");
      } else {
        CustomReporter.errorLog("10 Unknown attributes are not added successfully");
        BaseClass.testCaseStatus = false;
      }

      // Step 8: Click on Profile link from megamenu
      pageHeader.megaMenuLinksClick("Profile");
      CustomReporter.log("Profile page loaded successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      // Step 9: Verify the graphs
      if (ap.func_VerifyProfileGraphs(filter)) {
        CustomReporter.log("All graphs in profile page are proper");
      } else {
        CustomReporter.errorLog("All graphs in profile page are not proper");
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
