/********************************************************************
Test Case Name:Profile_VerifyswitchingAudience is proper
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/9071.aspx
Objective:verify that data is changing accordingly when we switch the audiences from profile page
Module: Analyse>Profile
Created By: Vikram Hegde
Date: 23 May 2016
 **********************************************************************/

package com.IDIOM.Analyse.Profile.scripts;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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

public class Profile9071 extends BaseClass {

  @Test
  public void test_Profile9071() throws Exception {
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
    Analyse_Profile_Page ap = new Analyse_Profile_Page(driver);
    String strSuccessMetrics = property.getProperty("CommonSelectingSuccessMetric");
    String audienceAttributeUnkown7 = property.getProperty("8222audienceUnkownAttribute7");
    String audienceAttributeUnkown8 = property.getProperty("8222audienceUnkownAttribute8");
    String strFirstDefaultAudience = property.getProperty("defaultaudience");
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

      // Step 5: Click on new audience link ,provide name and click on 'create and save'
      if (!(cl.func_ClientListPageElementExist("audiencetab") && cl
          .func_ClientListPageElementExist("audiencetablabel")))
        throw new IDIOMException("Fails to land on Audience tab. ");

      CustomReporter.log("Project " + projectName + " saved successfully. And found Audience tab");

      String selectedAudienceName = cl.createNewAudience("");

      Thread.sleep(3000);

      ArrayList<String> audiecnesInEditOverlay = cl.func_getListOfAudiencesForEditedProject();

      // Step 6: Launch the project
      cl.func_PerformAction("Launch Project");
      CustomReporter.log("Clicked on Launch Project");

      // Waiting till page get loaded
      ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
      rm.webElementSync(pdp.projectName, "visibility");
      Thread.sleep(5000);

      // Step 7: Click on Success metrics link
      pdp.navigateTo(property.getProperty("successMetrics"));
      CustomReporter.log("Navigated to Success metrics page successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      // Step 8: Select a few success metrices and navigate to audience definition

      CustomReporter.log("Select one Success Metric");
      ad.selectMetricByName(strSuccessMetrics);

      pdp.navigateTo(property.getProperty("audienceDefinition"));
      CustomReporter.log("Navigated to Success metrics page successfully");

      Thread.sleep(2000);

      // Step 9: Add a few filters in audience drop down

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown7);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      ad.selectAttributeOnAudienceDefinition(audienceAttributeUnkown8);
      ad.performAction("addattribute");
      Thread.sleep(2000);

      if (2 == ad.getNumberOfAddedAudienceAttributesList()) {
        CustomReporter.log("Attributes added successfully");
      } else {
        CustomReporter.errorLog("Attributes are not added successfully");
        BaseClass.testCaseStatus = false;
      }

      // Step 10: Switch the attribute from audience drop down
      CustomReporter.log("Change the Audience to - '" + strFirstDefaultAudience + "'");
      pageHeader.selectAudienceFromDropDown(strFirstDefaultAudience);

      if (!pageHeader.verifySelectedAudienceInDropDown(strFirstDefaultAudience))
        throw new IDIOMException("Currently selected Audience is not '" + strFirstDefaultAudience
            + "' which was by default selected.");
      CustomReporter.log("Audience '" + strFirstDefaultAudience
          + "' is correctly selected in Header Dropdown on Profile Page");

      if (100 == ad.getPopulationPercentage()) {
        CustomReporter.log("No filters added and population percentage is 100%");
      } else {
        CustomReporter.errorLog("Population percentage is not 100%");
        BaseClass.testCaseStatus = false;
      }

      // Step 11: Select profile destination link from megamenu

      pdp.navigateTo(property.getProperty("profile"));
      CustomReporter.log("Navigated to Profile page successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      // Step 12: Check the default audience selected in profile page
      if (!pageHeader.verifySelectedAudienceInDropDown(strFirstDefaultAudience))
        throw new IDIOMException("Default Audience does not match with the Audience '"
            + selectedAudienceName + "' which was created and selected. ");

      CustomReporter
          .log("Audience '"
              + selectedAudienceName
              + "' created on Client Home page is correctly selected in Header Dropdown on Profile Page");

      List<String> summaryValuesBeforeAudienceSwitch = ap.func_CaptureList("getSummaryList");
      List<String> demographicValuesBeforeAudienceSwitch = ap.func_CaptureList("Demographics");

      // Step 13: Click on audience drop down and verify the audiences present

      pageHeader.audienceDropDownLink.click();
      ArrayList<String> audiencesInProfile = pageHeader.returnAudiencesInDropDown();
      if (!CollectionUtils.isEqualCollection(audiecnesInEditOverlay, audiencesInProfile)) {
        throw new IDIOMException(
            "Audiences listed in Edit project overlay and which are there in dropdown in header on Profile page do not match.");
      } else {
        CustomReporter
            .log("Audiences listed in Edit project overlay are same as audiences listed in Profile page.");
      }

      // Step 14: Switch the audience
      CustomReporter.log("Change the Audience to - '" + selectedAudienceName + "'");
      pageHeader.selectAudienceFromDropDown(selectedAudienceName);

      List<String> summaryValuesAfterAudienceSwitch = ap.func_CaptureList("getSummaryList");
      List<String> demographicValuesAfterAudienceSwitch = ap.func_CaptureList("Demographics");

      if (CollectionUtils.isEqualCollection(summaryValuesAfterAudienceSwitch,
          summaryValuesBeforeAudienceSwitch)
          && CollectionUtils.isEqualCollection(demographicValuesAfterAudienceSwitch,
              demographicValuesBeforeAudienceSwitch)) {
        CustomReporter.errorLog("Data is still same before and after audience switch");
        BaseClass.testCaseStatus = false;
      } else {
        CustomReporter.log("Data is different before and after audience switch");
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
