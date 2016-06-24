/** *******************************************************************
Test Case Name: *1084_Super_User_Admin_Verify_Click_Of_ResetPasswordButton
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8413.aspx
Objective: Verify Click on Reset Password Button
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 19 January 2016
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

public class UserAdmin8413 extends BaseClass {

  @Test
  public void test_UserAdmin8413() throws Exception {

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

      // Step 3: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("Admin access page loaded successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      
      Thread.sleep(5000);
      if (userAdmin.func_ElementExist("Users List")) {
        CustomReporter.log(" User Admin page Got landed.The user listing is displayed");

        // Step 4 Select any active user and click on Edit link
        CustomReporter.log("Click on Edit link for particular User");
        int selectedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.APP_USER, 0, false);

        Thread.sleep(5000);

        // Step 5 - Click on "Reset Password" button and click on "Save" to send reset password
        userAdmin.resetPasswordSelectedUser(selectedUserRowIndex);
        CustomReporter.log("Click on Reset Password button");
        Thread.sleep(5000);

        CustomReporter.log("Click on Save button to commit the Reset Password");
        userAdmin.saveEditedUser(selectedUserRowIndex);

        Thread.sleep(3000);

        if (userAdmin.func_ElementExist("Reset Password Messagebox")) {
          CustomReporter.log("Reset Password Message Box is noticed");
        } else {
          CustomReporter.errorLog("Reset Password Message Box is not noticed");
          BaseClass.testCaseStatus = false;
        }

        if (userAdmin.func_ElementExist("Reset Password Message")) {
          CustomReporter.log("Reset Password Message is noticed");
        } else {
          CustomReporter.errorLog("Reset Password Message is not noticed");
          BaseClass.testCaseStatus = false;
        }
        
        rm.webElementSync("idiomspinningcomplete");
        rm.webElementSync("pageload");
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
