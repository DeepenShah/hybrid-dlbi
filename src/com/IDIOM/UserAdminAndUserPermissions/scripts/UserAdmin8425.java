/********************************************************************
Test Case Name: *1022_StickyHeaderRow
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8425.aspx
Objective: Verify that sticky header is coming at the top
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 08 January 2016
 **********************************************************************/
package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.text.ParseException;

import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;

public class UserAdmin8425 extends BaseClass {

  @Test
  public void test_UserAdmin8425() throws InterruptedException, ParseException {

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

      // Step 4:Scroll down and check the header row

      Point HeaderRowCoOrdinatesBeforeScrolling = null;
      HeaderRowCoOrdinatesBeforeScrolling = userAdmin.func_GetCoOrdinates("headerrow");

      Thread.sleep(5000);

      userAdmin.func_ScrollUserList();

      Thread.sleep(5000);

      Point HeaderRowCoOrdinatesAfterScrolling = null;
      HeaderRowCoOrdinatesAfterScrolling = userAdmin.func_GetCoOrdinates("headerrow");

      if ((HeaderRowCoOrdinatesBeforeScrolling.x == HeaderRowCoOrdinatesAfterScrolling.x)
          && (HeaderRowCoOrdinatesBeforeScrolling.y == HeaderRowCoOrdinatesAfterScrolling.y)) {

        CustomReporter.log("Header is sticky");
      } else {
        CustomReporter.errorLog("Header is not sticky");
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
