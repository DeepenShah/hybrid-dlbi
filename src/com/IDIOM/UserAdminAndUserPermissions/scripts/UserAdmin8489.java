/********************************************************************
Test Case Name:*1011_Verify client admin should not able to revoke/assign rights to super admin
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8489.aspx
Objective: Verify client admin should not able to revoke/assign rights to super admin
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 24 December 2015
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
import common.UserTypeEnum;

public class UserAdmin8489 extends BaseClass {

  @Test
  public void test_UserAdmin8489() throws Exception {

    String emailid = property.getProperty("ClientAdminEmail1");
    String password = property.getProperty("ClientAdminPassword1");
    String SuperAdminID = property.getProperty("SuperAdminUser");
    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);

    try {
      // Step1: Open URL
      CustomReporter.log("Launched Browser and Enter URL");
      Login_Page ln = new Login_Page(driver);

      Thread.sleep(3000);

      // Step 2:Login as a client admin user
      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully");

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

        // Step4: Search for any super admin user
        Thread.sleep(3000);
        if (userAdmin.func_CheckSearchFunctionality(SuperAdminID)) {
          CustomReporter.log("The Result Grid consists of user admin with email id" + SuperAdminID);
        } else {
          CustomReporter.errorLog("The Result Grid does not consists of user admin with email id"
              + SuperAdminID);
          BaseClass.testCaseStatus = false;
        }

        if (userAdmin.func_ElementExist("Users List")) {
          CustomReporter.log("The super admin user " + SuperAdminID + " is existing ");
          // Step 5: Click on edit button
          int selectedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.USER_ADMIN, 0,
              false);
          CustomReporter
              .log("The user has clicked on the edit button for the user " + SuperAdminID);
          Thread.sleep(3000);

          // Step 6:Try to assign or un-assign clients
          userAdmin.assignUnAssignClientsForUser(true);
          CustomReporter
              .log("The user has clicked on assign or unassign clients for the super admin user "
                  + SuperAdminID);
          Thread.sleep(2000);

          userAdmin.saveEditedUser(selectedUserRowIndex);
          CustomReporter.log("The user has clicked on Save button for the user " + SuperAdminID);

          if (userAdmin.func_ElementExist("Error Message")) {
            CustomReporter
                .log("While trying to assign or unassign clients for Super admin user, the expected error message is coming");
          } else {
            CustomReporter
                .errorLog("The expected error message is not coming for Super Admin User while trying to assign or unassign client");
            BaseClass.testCaseStatus = false;
          }

        } else {
          CustomReporter.errorLog("The super admin user " + SuperAdminID + " is NOT existing");
          BaseClass.testCaseStatus = false;
        }
      }

      else {
        CustomReporter.errorLog("The users list is NOT existing");
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
