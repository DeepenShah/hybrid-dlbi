/********************************************************************
Test Case Name: *Disabling/Re-Enabling Users_Client Admin
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8365.aspx
Objective: Verify edit button should not appear for client admin
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 23 December 2015
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;
import common.UserTypeEnum;

public class UserAdmin8365 extends BaseClass {

  @Test
  public void test_UserAdmin8365() throws Exception {

    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("ClientAdminEmail1");
    String password = property.getProperty("ClientAdminPassword1");
    try {
      // Step 1: Open Site Url
      Login_Page ln = new Login_Page(driver);
      CustomReporter.log("Browser launched and site url entered");
      Thread.sleep(3000);

      // Step 2: Login as valid super user/ user Admin
      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully as client Admin");

      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      rm.webElementSync(pageHeader.personMenu, "visibility");
      Thread.sleep(5000);

      // Step 3: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("Admin access page loaded successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      // Step 4 - Click on edit for any user
      int seletedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.APP_USER, 0, false);
      CustomReporter.log("Edit window opened for edited user");

      // Step 5: Verify disable button not present for client Admin
      if (!userAdmin.func_CheckDisabledButtonPresentForClientAdmin(seletedUserRowIndex)) {
        CustomReporter.log("There is no disable button present for currently edited user");
      } else {
        CustomReporter.errorLog("Disable button is present for currently edited user");
        BaseClass.testCaseStatus = false;
      }

      rm.waitTime(5000);
      // Step 6 - Search for disabled user
      seletedUserRowIndex = userAdmin.editAnyExistingDisabledUser();
      CustomReporter.log("Disabled user found");

      // Step 7 - Verify edit button not present for disabled user
      if (seletedUserRowIndex == 0) {
        CustomReporter.log("Edit button not present for disabled user");
      } else {
        CustomReporter.errorLog("Edit button present for disabled user");
      }

    } catch (IDIOMException ie) {
      ie.printStackTrace();
      BaseClass.testCaseStatus = false;
      String strMsgAndFileName[] = ie.getMessage().split("###");
      System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
      CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
      rm.captureScreenShot(rm.getScreenShotName(this.getClass().getName()), "fail");

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
