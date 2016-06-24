/********************************************************************
Test Case Name: *1219_UserAdminPage_ClientAdmin_ClientsVisibility
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8428.aspx
Objective: Verify that client admin user sees  only clients whom the clientAdmin themselves are assigned to , in edit panel
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 12 January 2016
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.ClientRestApiUtil;
import common.IDIOMException;
import common.UserTypeEnum;

public class UserAdmin8428 extends BaseClass {

  @Test
  public void test_UserAdmin8428() throws InterruptedException, ParseException,
      ClassNotFoundException, SQLException {

    String emailid = property.getProperty("ClientAdminEmail1");
    String password = property.getProperty("ClientAdminPassword1");
    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);

    try {
      Login_Page ln = new Login_Page(driver);
      CustomReporter.log("Browser launched and site url entered");

      // Step 2: Login as valid super user/ user Admin
      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully as client admin / super user");

      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      rm.webElementSync(pageHeader.personMenu, "visibility");
      Thread.sleep(5000);

      // Step 3: Note down all the clients listed in Client home page
      List<String> clients = ClientRestApiUtil.getClientList(emailid, password);
      if (!CollectionUtils.isEmpty(clients)) {
        CustomReporter.log("Clients listed in client home page are:" + clients);
      } else {
        CustomReporter.errorLog("No clients are listed in client home page");
        BaseClass.testCaseStatus = false;
      }

      // Step 4: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("Admin access page loaded successfully");

      int selectedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.APP_USER, 0, false);
      Thread.sleep(2000);
      List<String> selectedUserClients = userAdmin.getSelectedUserClients(selectedUserRowIndex);
      if (CollectionUtils.isEqualCollection(clients, selectedUserClients)) {
        CustomReporter.log("Clients listed in client page are same as clients listed for user");
        CustomReporter.log("Clients from client page =" + clients + "<<>>"
            + "Clients from selected user's edit overlay =" + selectedUserClients);
      } else {
        CustomReporter
            .errorLog("Clients listed in client page are not same as clients listed for user");
        CustomReporter.errorLog("Clients from client page =" + clients + "<<>>"
            + "Clients from selected user's edit overlay =" + selectedUserClients);
        BaseClass.testCaseStatus = false;
      }
      userAdmin.cancelSelectedUser(selectedUserRowIndex);

      if (userAdmin.verifyClientListForAllUsers(clients)) {
        CustomReporter
            .log("Clients listed in client page are same as clients listed for all users");
      } else {
        CustomReporter
            .errorLog("Clients listed in client page are not same as clients listed for all users");
        BaseClass.testCaseStatus = false;
      }

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(5000);

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
