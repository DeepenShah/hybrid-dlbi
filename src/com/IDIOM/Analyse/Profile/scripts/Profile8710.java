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

public class Profile8710 extends BaseClass {

  @Test
  public void test_Profile9070() throws Exception {
    // ****************Variables declaration and Initialization****************************

    String emailid = property.getProperty("email");
    String password = property.getProperty("password");
    String clientName = property.getProperty("defaultClient");
    String projectName = property.getProperty("9070projectName");
    String projectDescription = property.getProperty("9070projectDescription");
    String audienceAttribute1 = property.getProperty("8710audienceAttribut1");
    String audienceAttribute2 = property.getProperty("8710audienceAttribut2");
    boolean bProjectCreate = false;
    Login_Page ln = new Login_Page(driver);
    ClientList_Page cl = new ClientList_Page(driver);
    AudienceBuilderPage ad = new AudienceBuilderPage(driver);
    Analyse_Profile_Page ap = new Analyse_Profile_Page(driver);

    // ****************Test step starts here************************************************
    try {
      // Step 1: Open Site Url

      CustomReporter.log("Browser launched and site url entered");

      // Step 2: Login as valid user
      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully as user admin / super user");

      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      Thread.sleep(5000);

      // Step 2:Select any existing client from client dropdown

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

      cl.createNewAudience("");

      Thread.sleep(3000);

      cl.func_getListOfAudiencesForEditedProject();

      // Step 6: Launch the project
      cl.func_PerformAction("Launch Project");
      CustomReporter.log("Clicked on Launch Project");

      // Waiting till page get loaded
      ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
      rm.webElementSync(pdp.projectName, "visibility");
      Thread.sleep(5000);

      // Step 7: Click on success metrics link

      pdp.navigateTo(property.getProperty("successMetrics"));
      CustomReporter.log("Navigated to success metrics page successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      // Step 8: Click on audience definition link

      pdp.navigateTo(property.getProperty("audienceDefinition"));
      CustomReporter.log("Navigated to Audience page successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      // Step 9 : Select filters in such a way that population must be Zero Percent(Should appear
      // like <1% but Zero Out of the total data)

      ad.selectAttributeOnAudienceDefinition(audienceAttribute1);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttribute2);
      ad.performAction("addattribute");
      Thread.sleep(2000);
      if (2 == ad.getNumberOfAddedAudienceAttributesList()) {
        CustomReporter.log("Selected two attributes and added successully");
      } else {
        CustomReporter.errorLog("Selected two attributes and not added successully");
        BaseClass.testCaseStatus = false;
      }

      // Switch to and condition to narrow audience to <1%
      ad.performAction("toggleAndOr");

      if (ad.getSelectedPopulationValue() == 0) {
        CustomReporter.log("The population percentage has updated with zero percent");
      } else {
        throw new IDIOMException("The population percentage not updated with zero percent");
      }

      // Step 10: Click on Profile link from megamenu
      pageHeader.megaMenuLinksClick("Profile");
      CustomReporter.log("Profile page loaded successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      if (!rm.webElementSync(ap.audienceTooNarrow, "visibility"))
        throw new IDIOMException(
            "Failed to land on profile page with message audienc too narrow.###8721Audienctoonarrow");

      CustomReporter
          .log("The user has navigated to profile page with message 'Audience too narrow'");

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
