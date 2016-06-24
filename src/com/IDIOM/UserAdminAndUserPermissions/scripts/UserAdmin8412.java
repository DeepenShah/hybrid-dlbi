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

public class UserAdmin8412 extends BaseClass {

  @Test
  public void test_UserAdmin8412() throws Exception {

    String clientEmailid = property.getProperty("ClientAdminEmail1");
    // ****************Test step starts here************************************************

    try {
      // Step1:Access application url
      CustomReporter.log("Launched Browser and Enter URL");
      Login_Page ln = new Login_Page(driver);

      Thread.sleep(3000);

      // Step 2:Click on 'Forgot password'
      ln.func_PerformAction("Forget Pwd", "");

      CustomReporter.log("The user has clicked on forget password link");

      // Step3:Mention valid regisetered email address and click on the button
      // "SEND PASSWORD RESET EMAIL"
      Thread.sleep(3000);
      ln.func_PerformAction("Input Email", clientEmailid);

      CustomReporter.log("Valid email address is sent to email id field");

      ln.func_PerformAction("Password Reset Email", null);
      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      CustomReporter.log("Password reset email button is clicked");

      if (ln.func_ElementExist("Password Reset Success")) {
        CustomReporter.log("Reset password email Success message is appeared");
      } else {
        CustomReporter.errorLog("Reset password email Success message is not appeared");
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

    // ****************Test step ends here************************************************

    if (BaseClass.testCaseStatus == false) {
      CustomReporter.errorLog("Test case has failed");
      Assert.assertTrue(false);
    }
  }

}
