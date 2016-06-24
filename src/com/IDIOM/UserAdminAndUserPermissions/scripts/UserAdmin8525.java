/** *******************************************************************
Test Case Name: *978_UserAdminPage_CreateUser_NameCheck
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8525.aspx
Objective: Verify that the name  mentioned in Assign clients pop up is coming exactly as the name mentioned in Create user part
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

public class UserAdmin8525 extends BaseClass {

  @Test
  public void test_UserAdmin8525() throws Exception {

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

      // Step 4 - Verify create user button is available in bottom right corner
      rm.waitTime(5000);

      // Step - 5 - Click on create new user button
      // Step - 6 - Verify following is present in create user:- Name- Email Address- Create Button
      // - Cancel Button
      // Step - 7 - Enter valid first, last name and email address
      // Step - 8 - Click on create button
      // Step - 9 - Verify the name present in Assign clients page
      String[] UsrDetails = userAdmin.func_CreateUserDetails("RRR");

      System.out.println(UsrDetails[0] + " " + UsrDetails[1]);

      userAdmin.func_CreateUser(UsrDetails[0], UsrDetails[1], 2, "Add New User For Name Check");

      rm.waitTime(5000);

      // Following steps have been given additionally to be more sure
      if (userAdmin.func_CheckSearchFunctionality(UsrDetails[0].substring(3))) {
        CustomReporter.log("Searching is working as expected for Name Field. Search Criteria - '"
            + UsrDetails[0].substring(3) + "' has been used");
      } else {
        BaseClass.testCaseStatus = false;
        CustomReporter
            .errorLog("Seaching is not working as expected for Name Field. Search Criteria - '"
                + UsrDetails[0].substring(3) + "' has been used");

      }

      rm.waitTime(5000);
      userAdmin.func_ClearText("SearchTextBox");

      rm.waitTime(5000);

      if (userAdmin.func_CheckSearchFunctionality(UsrDetails[1].substring(3))) {
        CustomReporter
            .log("Searching is working as expected for Email Address Field. Search Criteria - '"
                + UsrDetails[1].substring(3) + "' has been used");
      } else {
        BaseClass.testCaseStatus = false;
        CustomReporter
            .errorLog("Seaching is not working as expected for Email Address Field. Search Criteria - '"
                + UsrDetails[1].substring(3) + "' has been used");
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
