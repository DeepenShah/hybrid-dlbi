/********************************************************************
Test Case Name:*1017_Send Password Reset Email_Default button
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8449.aspx
Objective: Verify that the button colour is grey by default for "Send Password Reset Email" button
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 15January 2016
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class UserAdmin8449 extends BaseClass {

  @Test
  public void test_UserAdmin8449() throws Exception {

    // ****************Test step starts here************************************************

    // Step1:Access application url
    try {
      CustomReporter.log("Launched Browser and Enter URL");
      Login_Page ln = new Login_Page(driver);

      Thread.sleep(3000);

      // Step 2:Click on 'Forgot password'
      ln.func_PerformAction("Forget Pwd", "");

      CustomReporter.log("The user has clicked on forget password link");

      // Step3:Check 'Send Password Reset Password" button

      if (ln.func_BGColorCheck("Send password button", "rgba(255, 15, 83, 1)")) {
        CustomReporter
            .log("The back ground color is expected for the 'Send Reset password button'");
      } else {

        CustomReporter
            .errorLog("The back ground color is not expected for the 'Send Reset password button'");
        BaseClass.testCaseStatus = false;
      }

      // step 4:Enter valid email id in the text box
      ln.func_PerformAction("Input Email", "test@gmail.com");

      CustomReporter.log("The email has been added in the email id text box");

      if (ln.func_BGColorCheck("Send password button", "rgba(255, 15, 83, 1)")) {
        CustomReporter
            .log("The back ground color is expected for the 'Send Reset password button' after entering email id");
      } else {

        CustomReporter
            .errorLog("The back ground color is not expected for the 'Send Reset password button' after entering email id");
        BaseClass.testCaseStatus = false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      BaseClass.testCaseStatus = false;
      e.printStackTrace(System.out);
      CustomReporter.errorLog("Some exception is generated, " + e.getMessage()
          + ". Please check the log for more details");
      rm.captureScreenShot(rm.getScreenShotName(this.getClass().getName()), "fail");
    }

    if (BaseClass.testCaseStatus == false) {
      CustomReporter.errorLog("Test case has failed");
      Assert.assertTrue(false);
    }
  }

}
