/********************************************************************
Test Case Name:Profile_Verify_Navigation_from Project Name and Client Logo from header
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/9075.aspx
Objective:Verify that the project home page is coming in view when we click from project name in profile page header
Module: Analyse>Profile
Created By: Vikram Hegde
Date: 23 May 2016
 **********************************************************************/

package com.IDIOM.Analyse.Profile.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

public class Profile9075 extends BaseClass {

  @Test
  public void test_Profile9075() throws Exception {
    // ****************Variables declaration and Initialization****************************

    String emailid = property.getProperty("email");
    String password = property.getProperty("password");
    String clientName = property.getProperty("defaultClient");
    String projectName = property.getProperty("9070projectName");
    String projectDescription = property.getProperty("9070projectDescription");
    boolean bProjectCreate = false;
    Login_Page ln = new Login_Page(driver);
    ClientList_Page cl = new ClientList_Page(driver);
    AudienceBuilderPage ad = new AudienceBuilderPage(driver);
    String audienceSomeAttributes = property.getProperty("commonAudienceAttribute3");
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

      // Step 3: Land in clients home page and select a client.

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
      cl.createNewAudience("");

      Thread.sleep(3000);
      // Step 6: Launch Project

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

      // Step 8: Select few attributes and add them
      ad.selectAttributeOnAudienceDefinition(audienceSomeAttributes);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      CustomReporter.log("Selected some more filters in other sections");

      if (1 == ad.getNumberOfAddedAudienceAttributesList()) {
        CustomReporter.log("Few attributes are selected and added successfully");
      } else {
        CustomReporter.errorLog("Few attributes are not selected and added successfully");
        BaseClass.testCaseStatus = false;
      }

      // Step 9 - Click on mega menu and click on profile link
      CustomReporter.log("Navigate to Profile page");
      pageHeader.megaMenuLinksClick(property.getProperty("profile"));

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      // Step 10 - Click on Project name or client logo in header
      CustomReporter
          .log("Click on Client logo at the top to navigate back to Project Dashboard page");

      if (!rm.webElementSync(pageHeader.clientLogo, "visibility"))
        throw new IDIOMException(
            "Client logo is not visible or there might be other issues on the page. ###9068_ClientLogo_Not_visible");

      pageHeader.clientLogo.click();

      CustomReporter.log("Check whether Project Dashboard page has loaded successfully");

      if (!(rm.webElementSync(pdp.projectName, "visibility")))
        throw new IDIOMException(
            "Project Dashboard page has not loaded or there might be other issues. ###9068_ProjectDashboardNotLoaded");

      CustomReporter.log("Project Dashboard has loaded successfully");

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
