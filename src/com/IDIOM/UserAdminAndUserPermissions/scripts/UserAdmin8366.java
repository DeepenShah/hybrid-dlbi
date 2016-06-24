/********************************************************************
Test Case Name: *Disabling/Re-Enabling Users_Disabled user
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8366.aspx
Objective: Verify disabled user unable to login
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 24 December 2015
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.text.ParseException;

import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.google.common.base.Strings;
import com.reports.CustomReporter;
import common.BaseClass;
import common.ReusableMethods;

public class UserAdmin8366 extends BaseClass {

  @Test
  public void test_UserAdmin8366() throws InterruptedException, ParseException {

    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("DisableUserEmail");
    String password = property.getProperty("DisabledUserPassword");
    try {
      // Step 1: Open Site Url
      Login_Page ln = new Login_Page(driver);
      CustomReporter.log("Browser launched and site url entered");
      Thread.sleep(3000);

      // Step 2: Login as valid super user/ user Admin
      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully as user admin / super user");

      String errormessage = ln.func_ReturnMsg("Invalid Credential");
      if (Strings.isNullOrEmpty(errormessage)) {
        CustomReporter.log("Disabled user is not able to login");
      } else {
        CustomReporter.errorLog("Disabled user is able to login");
      }

    } catch (Exception e) {
      e.printStackTrace();
      BaseClass.testCaseStatus = false;
      e.printStackTrace(System.out);
      CustomReporter.errorLog("Some exception is generated, " + e.getMessage()
          + ". Please check the log for more details");
      rm.captureScreenShot(rm.getScreenShotName(this.getClass().getName()), "fail");
    }
  }

}
