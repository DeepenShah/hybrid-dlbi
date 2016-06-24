/********************************************************************
Test Case Name: Create user-Super Admin> Clients assigned Manually
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8367.aspx
Objective: Create user-Super Admin> Clients assigned Manually
Module: User Admin Functionality
Created By: Abhishek Deshpande
Date: 11 JAN 2016
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

public class UserAdmin8367 extends BaseClass {

  @Test
  public void test_UserAdmin8367() throws Exception {

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

      userAdmin.func_PerformAction("AddUserClick");

      Thread.sleep(3000);

      CustomReporter
          .log("Verify that following objects- Name, email, Create button and Cancel button are present");

      userAdmin.func_PerformAction("labelCreateUserExists");
      userAdmin.func_PerformAction("AddUserNameExists");
      userAdmin.func_PerformAction("AddUserEmailExists");
      userAdmin.func_PerformAction("AddUserCreateButtonExists");
      userAdmin.func_PerformAction("AddUserCancelButtonExists");

      userAdmin.func_PerformAction("AddUserCancelClick");

      Thread.sleep(3000);
      CustomReporter.log("Enter valid first name, Last name and email ID");

      String[] userDetails = userAdmin.func_CreateUserDetails("abhi");

      System.out.println(userDetails[0] + " " + userDetails[1]);

      userAdmin.func_CreateUser(userDetails[0], userDetails[1], 2, "Add New User");

      CustomReporter.log("Search for newly created user");

      if (userAdmin.func_CheckSearchFunctionality(userDetails[0])) {
        CustomReporter.log("New User Created successfully");
      } else {
        CustomReporter.errorLog("New User NOT Created successfully");
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
