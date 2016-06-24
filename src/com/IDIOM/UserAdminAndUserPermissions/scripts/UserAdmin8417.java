/********************************************************************
Test Case Name:*1194_User Admin_Check Undo button state
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8417.aspx
 Objective: Verify Undo button colour change
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 11 January 2016
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

public class UserAdmin8417 extends BaseClass {

  @Test
  public void test_UserAdmin8417() throws Exception {

    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    int selectedUserRowIndex = 0;
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

      Thread.sleep(3000);

      if (userAdmin.func_ElementExist("Users List")) {
        CustomReporter.log(" User Admin page Got landed.The user listing is displayed");

        // Step 4: Edit on any existing disabled user
        Thread.sleep(3000);
        selectedUserRowIndex = userAdmin.editAnyExistingDisabledUser();
        CustomReporter.log("Edit window opened for disabled user");

        // Step 5: Verify default background color of undo button is white
        if (userAdmin.func_VerifyBGColor(selectedUserRowIndex, "rgba(255, 255, 255, 1)")) {
          CustomReporter.log("Default background color of undo button is white");
        } else {
          CustomReporter.errorLog("Default background color of undo button is not white");
          BaseClass.testCaseStatus = false;
        }

        // Step 6: Click undo button
        userAdmin.undoDisableEditedUser(selectedUserRowIndex);
        CustomReporter.log("The user has clicked on Undo button for the user ");

        if (userAdmin.func_VerifyBGColor(selectedUserRowIndex, "rgba(255, 15, 83, 1)")) {
          CustomReporter.log("Background color of undo button is changed to Red");
        } else {
          CustomReporter.errorLog("Background color of undo button is not changed to Red");
          BaseClass.testCaseStatus = false;
        }

        // Step 7: Click undo button
        userAdmin.undoDisableEditedUser(selectedUserRowIndex);
        CustomReporter.log("The user has clicked on Undo button for the user ");

        if (userAdmin.func_VerifyBGColor(selectedUserRowIndex, "rgba(255, 255, 255, 1)")) {
          CustomReporter.log("Background color of undo button changed to white");
        } else {
          CustomReporter.errorLog("Background color of undo button did not change to white");
          BaseClass.testCaseStatus = false;
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
