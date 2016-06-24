/********************************************************************
Test Case Name:
User admin: Unassign client to client admin user by clicking on client admin key icon
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8923.aspx
Objective: This test case checks if user is converted to normal user from clientAdmin after
unassigning all clients under him
Module: UserAdminAndUserPermissions
Created By:Vikram Hegde
Date: 3 May 2016
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;
import common.UserTypeEnum;

public class UserAdmin8923 extends BaseClass {

  @Test
  public void test_UserAdmin8931() throws Exception {

    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    List<Integer> clientRowIndices = null;
    int selectedUserRowIndex = 0;
    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("SuperAdminUser");
    String password = property.getProperty("SuperAdminpassword");

    try {
      // Step 1: Launch browser and enter url and login with valid Super
      // user
      CustomReporter.log("Launched Browser and Enter URL");
      Login_Page ln = new Login_Page(driver);

      Thread.sleep(3000);

      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully");

      Thread.sleep(5000);
      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      rm.webElementSync(pageHeader.personMenu, "visibility");
      Thread.sleep(5000);

      // Step 2: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("Admin access page loaded successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      // Step 3: Edit any existing client admin user and who has assigned
      // client and that user should active

      if (userAdmin.func_ElementExist("Users List")) {
        CustomReporter.log("The user listing is displayed");
      }
      selectedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.CLIENT_ADMIN, 1, true);

      if (selectedUserRowIndex > -1) {
        CustomReporter.log("Edit window appeared");
      } else {
        CustomReporter.errorLog("Edit window did not appear");
      }

      // Step 4: Unassign all clients and click on
      // Save button

      clientRowIndices = userAdmin.getListOfClientRowIndices(selectedUserRowIndex);

      userAdmin.assignUnAssignClientsForUser(false);

      userAdmin.saveEditedUser(selectedUserRowIndex);

      CustomReporter.log("Unassigned all clients and saved");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      // Step 5: Verify if user is converted to normal
      // user and
      // all clients are deselected
      if (userAdmin.verifySelectedUserIsClientAdmin(selectedUserRowIndex)) {
        CustomReporter.errorLog("User is not converted to normal user, still a client Admin");
      } else {
        CustomReporter.log("User successfully converted to normal user from client Admin");
      }

      userAdmin.editAnyExistingUserForRowIndex(selectedUserRowIndex);

      rm.waitTime(3000);

      if (userAdmin.verifyClientsUnassigned(clientRowIndices)) {
        CustomReporter.log("All Clients are unassigned successfully");
      } else {
        CustomReporter.errorLog("All Clients are not unassigned");
      }

      int numberOfClients = userAdmin.getNumberOfClientsForSelectedUser(selectedUserRowIndex);

      if (numberOfClients > 0) {
        CustomReporter.errorLog("Number of assigned clients still shows greater than 0");
        throw new IDIOMException("Number of assigned clients still reflects shows than 0");
      }
      rm.waitTime(3000);

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
      // Step 6: Logout from application.
      try {
        // Retaining modified user
        userAdmin.editAnyExistingUserForRowIndex(selectedUserRowIndex);
        userAdmin.assignUnAssignClientsForUser(clientRowIndices, true);
        userAdmin.saveEditedUser(selectedUserRowIndex);
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
