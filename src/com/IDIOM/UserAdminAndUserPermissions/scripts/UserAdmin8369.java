/********************************************************************
Test Case Name:
User admin: Unassign client to client admin user by clicking on client admin key icon
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8923.aspx
Objective: This test case checks if user is converted to normal user from clientAdmin after
unassigning all clients under him
Module: UserAdminAndUserPermissions
Created By:Vikram Hegde
Date: 3 May 2016
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

public class UserAdmin8369 extends BaseClass {

  @Test
  public void test_UserAdmin8931() throws Exception {

    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("SuperAdminUser");
    String password = property.getProperty("SuperAdminpassword");

    try {
      // Step 1: Launch browser and enter url and login with valid Super
      // user
      CustomReporter.log("Launched Browser and Enter URL");
      Login_Page ln = new Login_Page(driver);

      Thread.sleep(3000);

      // Step 2: Login as super user
      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully");

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
      rm.waitTime(2000);

      // Step 4: Click on create user button
      userAdmin.func_click("AddUserClick");
      rm.waitTime(2000);
      userAdmin.func_click("AddUserCancelClick");
      CustomReporter.log("Create user screen displayed");

      // Step 5: Verify appropriate error message
      userAdmin.createUser("", "", true);
      CustomReporter.log("Clicked on create user icon");

      if (userAdmin.verifyCreateUserValidationError()) {
        CustomReporter
            .log("Appropriate error message is appeared when nothing is entered in all fields");
      } else {
        CustomReporter
            .errorLog("Appropriate error message is not appeared when nothing is entered in all fields");
        BaseClass.testCaseStatus = false;
      }
      rm.waitTime(2000);

      // Step 6: Enter spaces in all fields and click create button
      userAdmin.createUser("   ", "   ", false);

      if (userAdmin.verifyCreateUserValidationError()) {
        CustomReporter
            .log("Appropriate error message is appeared when spaces is entered in all fields");
      } else {
        CustomReporter
            .errorLog("Appropriate error message is not appeared when spaces is entered in all fields");
        BaseClass.testCaseStatus = false;
      }
      rm.waitTime(2000);

      // Step 7: Enter one character in all fields and click create button
      userAdmin.createUser("a", "a", false);

      if (userAdmin.verifyCreateUserValidationError()) {
        CustomReporter
            .log("Appropriate error message is appeared when one character is entered in all fields");
      } else {
        CustomReporter
            .errorLog("Appropriate error message is not appeared when one character is entered in all fields");
        BaseClass.testCaseStatus = false;
      }
      rm.waitTime(2000);

      // Step 8: Enter valid first & last name and invalid email address and Click on create button
      userAdmin.createUser("firstName LastName", "invalidEmail", false);

      if (userAdmin.verifyCreateUserValidationError()) {
        CustomReporter
            .log("Appropriate error message is appeared when valid first name, valid last name and invalid email id is entered");
      } else {
        CustomReporter
            .errorLog("Appropriate error message is not appeared when valid first name, valid last name and invalid email id is entered");
        BaseClass.testCaseStatus = false;
      }
      rm.waitTime(2000);

      // Step 10: Enter valid email address, invalid first name but valid last name and Click on
      // create button
      userAdmin.createUser("fi", "validEmail@domain.com", false);

      if (userAdmin.verifyCreateUserValidationError()) {
        CustomReporter
            .log("Appropriate error message is appeared when invalid first name, valid last name and valid email id is entered");
      } else {
        CustomReporter
            .errorLog("Appropriate error message is not ppeared when invalid first name, valid last name and valid email id is entered");
        BaseClass.testCaseStatus = false;
      }
      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      rm.webElementSync(pageHeader.personMenu, "visibility");
      Thread.sleep(5000);
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
