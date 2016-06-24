/********************************************************************
Test Case Name:*990_ClientAdminUser_Verify No Access granting To Other User
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8435.aspx
Objective:Verify that Client admin is Not able to give admin rights to other users
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 12 January 2016
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

public class UserAdmin8435 extends BaseClass {

  @Test
  public void test_UserAdmin8435() throws Exception {
    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("ClientAdminEmail1");
    String password = property.getProperty("ClientAdminPassword1");

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

        Thread.sleep(3000);
        // Step 3: Click on Edit button for any user
        // Step 4:Try Give admin access to some clients
        if (userAdmin.func_CheckClientAdminFunctionality(1, "ClientAdminUser_AdminAccess")) {
          CustomReporter
              .log("The client admin user is not able to give admin right to another user");
        } else {
          CustomReporter
              .errorLog("The client admin user is  able to give admin right to another user");
          BaseClass.testCaseStatus = false;
        }
      }

      else {
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
      // Step 5: Logout from application.
      try {
        pageHeader.performAction("logout");
        CustomReporter.log("Logged out successfully");
      } catch (Exception ee) {
        ee.printStackTrace();
      }
    }
    // ****************Test step ends here************************************************
    if (BaseClass.testCaseStatus == false) {
      CustomReporter.errorLog("Test case has failed");
      Assert.assertTrue(false);
    }
  }
}