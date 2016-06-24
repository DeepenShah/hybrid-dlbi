/********************************************************************
Test Case Name:*1008_VerifyErrorMessageForDisabledClient Admin
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8441.aspx
Objective:Verify that proper message is coming while trying assign clients using disabled client admin user from other browser
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 13January 2016
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;
import common.UserTypeEnum;

public class UserAdmin8441 extends BaseClass {

  @Test
  public void test_UserAdmin8441() throws Exception {

    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    int selectedUserRowIndex = 0;
    Login_Page ln = new Login_Page(driver);
    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("SuperAdminUser");
    String password = property.getProperty("SuperAdminpassword");
    String emailID1 = property.getProperty("ClientAdminEmail1");
    String password1 = property.getProperty("ClientAdminPassword1");

    try {
      // Step 1: Open Site Url

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

      // Step 4: Search for client admin user

      userAdmin.func_TypeSearchCriteria(emailID1, "User");
      if (userAdmin.func_ElementExist("Users List")) {
        CustomReporter.log("Client admin is listed in users list");

        selectedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.CLIENT_ADMIN, 0, false);

        if (selectedUserRowIndex > -1) {
          CustomReporter.log("Edit window appeared");
        } else {
          CustomReporter.errorLog("Edit window dit not appear");
          BaseClass.testCaseStatus = false;
        }

        userAdmin.disableEditedUser(selectedUserRowIndex);

        CustomReporter.log("Disable button clicked on selected user");

        userAdmin.saveEditedUser(selectedUserRowIndex);

        CustomReporter.log("Disabled user and saved");

        rm.webElementSync("idiomspinningcomplete");
        rm.webElementSync("pageload");

        // Step 6:Logout Super user admin
        Thread.sleep(3000);
        pageHeader.performAction("logout");

        CustomReporter.log("The user " + emailid + " is logged out");

        // Step 7:Login with disabled user
        CustomReporter.log("Try to login with The user id :" + emailID1);
        ln.func_LoginToIDIOM(emailID1, password1);

        if (ln.func_ElementExist("DisabledUserValidationMessage")) {
          CustomReporter
              .log("We are getting correct error message while trying to login with disabled user. ");
        } else {
          CustomReporter.errorLog("The error message is not proper for disbaled user");
          BaseClass.testCaseStatus = false;
        }

        // ***************Making user enabled for the future use, Not part of test
        // case****************************************

        Thread.sleep(3000);

        userAdmin.func_ClickElement("DisabledUserEdit");
        Thread.sleep(3000);
        userAdmin.func_ClickElement("Undo Button");
        userAdmin.func_ClickElement("Enable Save");

      }

      else {
        CustomReporter.errorLog("The user list is absent");
        BaseClass.testCaseStatus = false;
        userAdmin.func_ClickElement("Logout");

        CustomReporter.log("The user " + emailid + " is logged out");
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
      try {
        // Retain disabled client Admin
        ln.func_LoginToIDIOM(emailid, password);
        Thread.sleep(5000);
        rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
        pageHeader.performAction("adminaccess");
        rm.webElementSync("idiomspinningcomplete");
        rm.webElementSync("pageload");
        Thread.sleep(3000);
        userAdmin.func_TypeSearchCriteria(emailID1, "User");
        userAdmin.editAnyExistingUserForRowIndex(selectedUserRowIndex);
        userAdmin.func_ClickElement("Undo Button");
        userAdmin.saveEditedUser(selectedUserRowIndex);
        pageHeader.performAction("logout");
      } catch (Exception ee) {
        ee.printStackTrace();
      }
    }
  }
}
