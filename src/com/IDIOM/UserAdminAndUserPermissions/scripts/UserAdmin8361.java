/********************************************************************
Test Case Name: *Super User Admin - ResultGrid_CollapsedView
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8361.aspx
Objective:This test case is to verify the collapsed view for result grid when the 'Edit button' is not been clicked
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 22 December 2015
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

public class UserAdmin8361 extends BaseClass {

  @Test
  public void test_UserAdmin8361() throws Exception {

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

        // Step 4 - Verify the first/header row in Search and Filtering section
        if (userAdmin.func_ElementExist("ListKeyIcons")) {
          CustomReporter.log("Key icons are present on the page");
        } else {
          CustomReporter.errorLog("Key Icons for Client Admins are not found");
          BaseClass.testCaseStatus = false;
        }

        if (userAdmin.func_ElementExist("ListAllUsersEmailAddress")) {
          CustomReporter.log("Email Addresses are available");
        } else {
          CustomReporter.errorLog("Email Addresses are not found");
          BaseClass.testCaseStatus = false;
        }

        if (userAdmin.func_ElementExist("DisplayedUserCount")) {
          CustomReporter.log("User Count is present");
        } else {
          CustomReporter.errorLog("User Count is not present");
          BaseClass.testCaseStatus = false;
        }

        if (userAdmin.func_ElementExist("NameElementInGridHeaderRow")) {
          CustomReporter.log("Name Element is present in Grid Header Row");
        } else {
          CustomReporter.errorLog("Name Element is not present in Grid Header Row");
          BaseClass.testCaseStatus = false;
        }

        if (userAdmin.func_ElementExist("ListEditButtons")) {
          CustomReporter.log("Edit buttons are present on the page");
        } else {
          CustomReporter.errorLog("Edit buttons are not present on the page");
          BaseClass.testCaseStatus = false;
        }

        if (userAdmin.func_ElementExist("ListBlockedUsersIcons")) {
          CustomReporter.log("Blocked User's Icons are present on the page");
        } else {
          CustomReporter.errorLog("Blocked User's Icons are not present on the page");
          BaseClass.testCaseStatus = false;
        }

        if (userAdmin.func_ElementExist("ListSuperAdminUsersIcons")) {
          CustomReporter.log("Super Admin Icons are present on the page");
        } else {
          CustomReporter.errorLog("Super Admin Icons are not present on the page");
          BaseClass.testCaseStatus = false;
        }

        if (userAdmin.func_ElementExist("ListClientAdminUsersIcons")) {
          CustomReporter.log("Client Admin Icons are present on the page");
        } else {
          CustomReporter.errorLog("Client Admin Icons are not present on the page");
          BaseClass.testCaseStatus = false;
        }

        if (userAdmin.func_ElementExist("ListNormalUsersIcons")) {
          CustomReporter.log("Normal app users are not having any icons");
        } else {
          CustomReporter.errorLog("Normal app users having icons");
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
