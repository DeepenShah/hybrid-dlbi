/********************************************************************
Test Case Name:*1021_UserAdmin_Selected ItemsCount_Update proper
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8454.aspx
Objective: Verify that selected Items count is getting updated proper in User admin page
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 15January 2016
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.Assert;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;

public class UserAdmin8454 extends BaseClass {

  public void testUserAdmin8454() {
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

        // Step 4: Select one or more rows(Say 3 rows and users having no clients assigned)
        userAdmin.func_selectUsers(3, 0);
        CustomReporter.log("The Users got selected");

        if (userAdmin.func_ElementExist("Selected Users")) {
          CustomReporter.log("The selected users are existing");
        } else {
          CustomReporter.errorLog("The selected User rows are NOT existing");
          BaseClass.testCaseStatus = false;
        }

        // Step 4:Check the number of selected users getting updated at the first row

        if (userAdmin.func_ElementExist("Selected User Count")) {
          CustomReporter.log("The value of selected users count is appearing in header row");
        }

        else {
          CustomReporter
              .errorLog("The value of selected users count is not appearing in header row");
          BaseClass.testCaseStatus = false;
        }

      } else {
        CustomReporter.errorLog("The user list is absent");
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
