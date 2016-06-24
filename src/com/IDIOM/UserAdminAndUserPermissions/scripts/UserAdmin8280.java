/********************************************************************
Test Case Name: *User Admin_Search_General Search Functionality_1.aTod_Verify searched text available only in Single field
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8272.aspx
Objective: Verify searched text available only in Single field
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 04 December 2015
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

public class UserAdmin8280 extends BaseClass {

  @Test
  public void test_UserAdmin8280() throws Exception {

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

      if (userAdmin.func_ElementExist("Users List")) {
        CustomReporter.log("The user listing is displayed");

        String firstName = userAdmin.getFirstNameFromUsersList();
        String lastName = userAdmin.getLastNameFromUsersList();
        String email = userAdmin.getEmailFromUsersList();

        // Step 4:In 'Search' box, input the search criteria with which search has to be performed
        if (userAdmin.func_CheckSearchFunctionality(firstName)) {
          CustomReporter
              .log("The  'Result Grid consists of users/users list with respect to the user first name");
        } else {
          CustomReporter
              .errorLog("The result grid is not as expected when providing Search string");
        }

        if (userAdmin.func_CheckSearchFunctionality(lastName)) {
          CustomReporter
              .log("The  'Result Grid consists of users/users list with respect to the user last name");
        } else {
          CustomReporter
              .errorLog("The result grid is not as expected when providing Search string");
        }

        if (userAdmin.func_CheckSearchFunctionality(email)) {
          CustomReporter
              .log("The  'Result Grid consists of users/users list with respect to the user email");
        } else {
          CustomReporter
              .errorLog("The result grid is not as expected when providing Search string");
        }
      } else {
        CustomReporter.errorLog("The users list is not existing");
        BaseClass.testCaseStatus = false;
      }

      CustomReporter.log("The user has been logged out");

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
      // Step 5: Logout from application.
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
