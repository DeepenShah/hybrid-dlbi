/********************************************************************
Test Case Name:*967_Super_User_Admin_Verify_Mouse_Hover_On_Bulk_Assignment_Icon
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8472.aspx
 Objective: Verify Mouse hover on Bulk Assignment icon
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 30 December 2015
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

public class UserAdmin8422 extends BaseClass {

  @Test
  public void test_UserAdmin8422() throws Exception {
    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("SuperAdminUser");
    String password = property.getProperty("SuperAdminpassword");

    try {
      // Step 1: Open Site Url
      Login_Page ln = new Login_Page(driver);
      CustomReporter.log("Browser launched and site url entered");

      // Step 2: Login as valid super user/ user Admin
      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully as user admin / super user");

      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      rm.webElementSync(pageHeader.personMenu, "visibility");
      Thread.sleep(5000);

      // Step 3: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("Admin access page loaded successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      if (userAdmin.func_ElementExist("Users List")) {
        CustomReporter.log(" User Admin page Got landed.The user listing is displayed");

        // Step 4:Select any user and click on Edit link
        Thread.sleep(3000);
        int selectedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.APP_USER, 0, false);
        CustomReporter.log("Edit window appeared for user");
        Thread.sleep(5000);
        userAdmin.resetPasswordSelectedUser(selectedUserRowIndex);
        CustomReporter.log("The user has clicked on Reset password button on user ");

        userAdmin.saveEditedUser(selectedUserRowIndex);
        CustomReporter.log("The user has clicked on Save button on user");

        // step 5: Again click on edit link for the same user and check the date time format beside
        // Reset Password Button

        Thread.sleep(10000);
        userAdmin.editAnyExistingUserForRowIndex(selectedUserRowIndex);
        CustomReporter.log("The user has clicked on edit button for the user:");
        Thread.sleep(10000);
        if (userAdmin.func_VerifyResetPwdMessageformat()) {
          CustomReporter.log("Reset password message format is user friendly");
        }

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
      // Step 7: Logout from application.
      try {
        // Retain disabled user
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