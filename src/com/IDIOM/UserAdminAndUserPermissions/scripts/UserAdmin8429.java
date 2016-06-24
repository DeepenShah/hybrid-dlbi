/** *******************************************************************
Test Case Name: *1223_ResetPassword_mailSentTime
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8429.aspx
Objective: Reset password message time and system time must be matching
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 22 January 2016
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

public class UserAdmin8429 extends BaseClass {

  @Test
  public void test_UserAdmin8429() throws Exception {

    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("SuperAdminUser");
    String password = property.getProperty("SuperAdminpassword");

    try {
      // Step 1: Open Site Url
      Login_Page ln = new Login_Page(driver);
      CustomReporter.log("Browser launched and site url entered");

      Thread.sleep(3000);

      // Step 2: Login as valid super user/ user Admin
      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully as user admin / super user");

      Thread.sleep(5000);
      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      rm.webElementSync(pageHeader.personMenu, "visibility");
      Thread.sleep(5000);

      // Step 3: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("Admin access page loaded successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      // Step 4 Select any active user and click on Edit link
      CustomReporter.log("Click on Edit link for particular User");
      int selectedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.APP_USER, 0, false);

      Thread.sleep(5000);

      // Step 4 - Click on "Reset Password" button and click on "Save" to send reset password
      CustomReporter.log("Click on Reset Password button");
      userAdmin.resetPasswordSelectedUser(selectedUserRowIndex);
      CustomReporter.log("Click on Save button to commit the Reset Password");
      // String strDateTime=userAdmin.func_GetSystemTime ("HH:mm a MM.DD.YY");
      String strDateTime = userAdmin.func_GetSystemTime("hh:mm a MM.DD.YY");

      if (strDateTime.startsWith("0")) {
        strDateTime = strDateTime.substring(1);

      }
      userAdmin.saveEditedUser(selectedUserRowIndex);
      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(5000);
      // Step 5 Click on Edit and check the time in message string "Reset last..."

      CustomReporter.log("Click on Edit link for the same User for which Password has been reset");
      userAdmin.editAnyExistingUserForRowIndex(selectedUserRowIndex);

      CustomReporter
          .log("Now compare the time which is displayed in edit expand mode with what was actual system time when it was reset");

      if (userAdmin.verifyResetPasswordPanel(selectedUserRowIndex, strDateTime)) {
        CustomReporter
            .log("Reset Password date and time has correctly been set and saved taking current system time");

      } else {
        CustomReporter
            .errorLog("Reset Password date and time has not set and saved taking current system time");
        BaseClass.testCaseStatus = false;
      }

      Thread.sleep(3000);

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
      // Step 7: Logout from application.
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
