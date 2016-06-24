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

public class UserAdmin8472 extends BaseClass {

  @Test
  public void test_UserAdmin8472() throws Exception {
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

        // Step 4:Select single or multiple users from User List Grid
        userAdmin.func_selectUsers(3);

        CustomReporter.log("The user has selected 3 users from the liest");

        // Step 5:Do mouser hover and click on Bulk assignment icon button, icon from the top row
        if (userAdmin.func_ElementExist("Bulk Client Icon")) {
          CustomReporter.log("The bulk client assignment icon is existing");

          if (userAdmin.func_ElementExist("Bulk Assignment Icon MouseOver")) {
            CustomReporter
                .log("The clients drop down is getting opened on mouse over on bulk assignment icon");
          } else {
            CustomReporter
                .errorLog("The clients drop down is NOT getting opened on mouse over on bulk assignment icon");
            BaseClass.testCaseStatus = false;
          }

          // Step 6:Remove mouse cursor from it and again do mouse hover and keep on doing that.
          if (userAdmin.func_ElementExist("Bulk Assignment Icon MouseOver")) {
            CustomReporter
                .log("The clients drop down is getting opened on mouse over on bulk assignment icon second time");
          } else {
            CustomReporter
                .errorLog("The clients drop down is NOT getting opened on mouse over on bulk assignment icon second time");
            BaseClass.testCaseStatus = false;
          }

          if (userAdmin.func_ElementExist("Bulk Assignment Icon MouseOver")) {
            CustomReporter
                .log("The clients drop down is getting opened on mouse over on bulk assignment icon third time");
          } else {
            CustomReporter
                .errorLog("The clients drop down is NOT getting opened on mouse over on bulk assignment icon third time");
            BaseClass.testCaseStatus = false;
          }

        } else {

          CustomReporter.errorLog("The bulk client assignment icon is NOT existing");
          BaseClass.testCaseStatus = false;
        }
      } else {
        CustomReporter.errorLog("The user listing is Not displayed");
        BaseClass.testCaseStatus = false;
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