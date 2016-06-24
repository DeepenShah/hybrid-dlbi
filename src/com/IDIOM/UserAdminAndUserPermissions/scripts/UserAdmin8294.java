/********************************************************************
Test Case Name: *Super User Admin_Search andFiltering_View_ClearSelection
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8294.aspx
Objective:Verify that the users selected in "Selection view" are getting deselected on clicking on "clear selections"
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 3 December 2015
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

public class UserAdmin8294 extends BaseClass {

  @Test
  public void test_UserAdmin8294() throws Exception {
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

        // Step 4: Select one or more rows(Say 5 rows)
        userAdmin.func_selectUsers(5,0);
        CustomReporter.log("The Users got selected");

        if (userAdmin.func_ElementExist("Selected Users")) {
          CustomReporter.log("The selected users are existing");
        } else {
          CustomReporter.errorLog("The selected User rows are NOT existing");
          BaseClass.testCaseStatus = false;
        }

        // Verify the components of the first row
        if (userAdmin.func_Row_Components("Header Row", 5)) {
          CustomReporter.log("The Header row is Proper in User Admin Page while selecting users");
        } else {
          CustomReporter
              .errorLog("The header row is NOT proper in User Admin Page while selecting users");
          BaseClass.testCaseStatus = false;
        }

        // Step 5:Click on 'Clear selections" label (cross label ) at the first row

        userAdmin.func_ClickElement("Close Header Row");
        Thread.sleep(3000);
        if (userAdmin.func_ElementExist("Selected Users")) {
          CustomReporter
              .errorLog("The selected users are NOT geting deselected on clicking on Close icon in header row");
          BaseClass.testCaseStatus = false;
        } else {
          CustomReporter
              .log("The selected users are getting deselected on clicking on Close icon in header row");

        }

      } else {
        CustomReporter.errorLog("No  user listing is not getting displayed");
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
