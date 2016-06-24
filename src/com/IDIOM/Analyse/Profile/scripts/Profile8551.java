/********************************************************************
Test Case Name:Profile_Interest_CollapseFunctionality
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8551.aspx
Objective:Verify that users can collapse the sub interests by clicking the X 
Module: Analyse>Profile
Created By: Vikram Hegde
Date: 23 May 2016
 **********************************************************************/

package com.IDIOM.Analyse.Profile.scripts;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

public class Profile8551 extends BaseClass {

  @Test
  public void test_Profile8551() throws Exception {
    // ****************Variables declaration and Initialization****************************

    String emailid = property.getProperty("email");
    String password = property.getProperty("password");
    String clientName = property.getProperty("defaultClient");
    String projectName = property.getProperty("9070projectName");
    String projectDescription = property.getProperty("9070projectDescription");
    String strFirstDefaultAudience = property.getProperty("defaultaudience");
    boolean bProjectCreate = false;
    Login_Page ln = new Login_Page(driver);
    ClientList_Page cl = new ClientList_Page(driver);
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

      // Step 5: Create two audiences and click on launch project
      if (!(cl.func_ClientListPageElementExist("audiencetab") && cl
          .func_ClientListPageElementExist("audiencetablabel")))
        throw new IDIOMException("Fails to land on Audience tab. ");

      CustomReporter.log("Project " + projectName + " saved successfully. And found Audience tab");

      cl.createNewAudience("");

      Thread.sleep(3000);

      cl.createNewAudience("");

      Thread.sleep(3000);

      cl.func_PerformAction("Launch Project");
      CustomReporter.log("Clicked on Launch Project");

      // Waiting till page get loaded
      ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
      rm.webElementSync(pdp.projectName, "visibility");
      Thread.sleep(5000);

      // Step 6: Click on profile link
      pdp.navigateTo(property.getProperty("profile"));
      CustomReporter.log("Navigated to Profile page successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(2000);

      // Step 7: Scroll down and open Interests 'Show All'
      int interestsCountBeforeExpanding = ap.getInterestsListSize();
      ap.performAction("expandCollapseInterests");
      Thread.sleep(2000);
      int interestsCountAfterExpanding = ap.getInterestsListSize();
      if (interestsCountAfterExpanding > interestsCountBeforeExpanding) {
        CustomReporter.log("All interests are shown as expected");
      } else {
        CustomReporter.errorLog("All interests are not shown as expected");
        BaseClass.testCaseStatus = false;
      }

      // Step 8: Scroll up to beginning of the page and click on Drop down menu
      List<String> actualdropdownValues = ap.getActualDropDownValues();
      List<String> expectedDropdownValues = ap.getExpectedDropDownValues();

      if (!CollectionUtils.isEqualCollection(actualdropdownValues, expectedDropdownValues)) {
        CustomReporter
            .log("Dropdown values are as expected with Summary,Demographics,Local markets,Interests");
      } else {
        CustomReporter.errorLog("Dropdown values are not as expected");
        BaseClass.testCaseStatus = false;
      }

      // Step 9: Click on interest from the drop down
      ap.func_ClickElement("interests");
      CustomReporter.log("The user has selected 'Demographics' from menu options");
      if (ap.func_ElementExist("Interests")) {
        CustomReporter
            .log("The user has been navigated to interests section in analyse>Profile page");
      } else {
        CustomReporter
            .errorLog("The user has NOT been navigated to interests section in analyse>Profile page");
        BaseClass.testCaseStatus = false;
      }

      // Step 10: Switch the audience
      CustomReporter.log("Change the Audience to - '" + strFirstDefaultAudience + "'");
      pageHeader.selectAudienceFromDropDown(strFirstDefaultAudience);

      // Step 11: Click on interest from the drop down
      ap.func_ClickElement("interests");
      CustomReporter.log("The user has selected 'Demographics' from menu options");
      if (ap.func_ElementExist("Interests")) {
        CustomReporter
            .log("The user has been navigated to interests section in analyse>Profile page");
      } else {
        CustomReporter
            .errorLog("The user has NOT been navigated to interests section in analyse>Profile page");
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
