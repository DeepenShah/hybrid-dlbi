/********************************************************************
Test Case Name: *Super User Admin - ResultGrid_ExpandedView_Client Admin View
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8363.aspx
Objective: This test case is to verify the expanded  view for result grid when the 'Edit button' is  been clicked
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 21 December 2015
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

public class UserAdmin8363 extends BaseClass {

  @Test
  public void test_UserAdmin8363() throws Exception {
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

      if (userAdmin.func_ElementExist("Users List")) {
        CustomReporter.log(" User Admin page Got landed.The user listing is displayed");

        // Step3:Verify the result grid
        if (userAdmin.func_Row_Components("user Row", 0)) {
          CustomReporter.log("The result grid is proper");
        } else {
          CustomReporter.errorLog("The result grid is not proper");
          BaseClass.testCaseStatus = false;
        }

        // Step4:Click on 'Edit button' and check the view
        userAdmin.func_ClickElement("FirstEditButton");
        CustomReporter.log("The edit button is getting clicked");

        if (userAdmin.func_Row_Components("Edit Mode", 1)) {
          CustomReporter
              .log("The 'Edit mode is proper in user admin page. All componants are present");
        } else {
          CustomReporter.errorLog("The 'Edit mode is NOt proper in user admin pager");
          BaseClass.testCaseStatus = false;
        }

      } else {
        CustomReporter.errorLog("The users list is not present");
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
