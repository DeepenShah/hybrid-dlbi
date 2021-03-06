/********************************************************************
Test Case Name:
User admin: After disabeling the user account whole row should grayed out
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8925.aspx
Objective: This test case checks if user is disabled
Module: UserAdminAndUserPermissions
Created By:Vikram Hegde
Date: 5 May 2016
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
import common.UserTypeEnum;

public class UserAdmin8925 extends BaseClass {

  @Test
  public void test_UserAdmin8925() throws Exception {

    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    int selectedUserRowIndex = 0;
    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("SuperAdminUser");
    String password = property.getProperty("SuperAdminpassword");
    String userNameUpdate = property.getProperty("userNameUpdate");
    String appUserEmailId = property.getProperty("8925normalAppUserEmailId");

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

      // Step 4: Edit any existing App user and who has clients assinged
      // and that user should be active

      if (userAdmin.func_ElementExist("Users List")) {
        CustomReporter.log("The user listing is displayed");
      }

      if (userAdmin.func_CheckSearchFunctionality(appUserEmailId)) {
        CustomReporter.log("The Result Grid consists of normal app user with email id "
            + appUserEmailId);
      } else {
        CustomReporter
            .errorLog("The Result Grid does not consists of normal app user with email id "
                + appUserEmailId);
        BaseClass.testCaseStatus = false;
      }

      selectedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.APP_USER, 0, false);

      if (selectedUserRowIndex > -1) {
        CustomReporter.log("Edit window appeared");
      } else {
        CustomReporter.errorLog("Edit window did not appear");
        BaseClass.testCaseStatus = false;
      }
      Thread.sleep(3000);

      userAdmin.disableEditedUser(selectedUserRowIndex);

      CustomReporter.log("Disable button clicked on selected user");

      userAdmin.saveEditedUser(selectedUserRowIndex);

      CustomReporter.log("Disabled user and saved");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(5000);

      int numberOfClients = userAdmin.getNumberOfClientsForSelectedUser(selectedUserRowIndex);

      if (numberOfClients == 0) {
        CustomReporter.log("Number of assigned clients is 0");

      } else {
        CustomReporter.errorLog("Number of assigned clients still shows greater than 0");
        BaseClass.testCaseStatus = false;
      }

      if (userAdmin.verifyUserRowIndexGreyedOut(selectedUserRowIndex)) {
        CustomReporter.log("User row is greyed out");
      } else {
        CustomReporter.errorLog("User row is not greyed out");
        BaseClass.testCaseStatus = false;
      }

      rm.waitTime(3000);
      // Step 6: Edit again the same user change name and save it again
      userAdmin.editAnyExistingUserForRowIndex(selectedUserRowIndex);
      CustomReporter.log("Edit window appeared");

      userAdmin.sendInputToUserNameFieldForSelectedUser(selectedUserRowIndex, userNameUpdate);

      userAdmin.saveEditedUser(selectedUserRowIndex);

      CustomReporter.log("Edited username and saved");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(5000);
      if (userNameUpdate.equals(userAdmin.getUserNameForSelectedUserRowIndex(selectedUserRowIndex))) {
        CustomReporter.errorLog("Disabled user's user name is allowed to update");
        BaseClass.testCaseStatus = false;
      } else {
        CustomReporter.log("Disabled user's user name is not updated");
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
      // Step 7: Logout from application.
      try {
        // Retain disabled user
        userAdmin.editAnyExistingDisabledUser();
        Thread.sleep(5000);
        userAdmin.undoDisableEditedUser(selectedUserRowIndex);
        userAdmin.saveEditedUser(selectedUserRowIndex);
        rm.webElementSync("idiomspinningcomplete");
        rm.webElementSync("pageload");
        Thread.sleep(5000);
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
