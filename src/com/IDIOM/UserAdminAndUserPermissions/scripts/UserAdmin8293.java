/********************************************************************
Test Case Name: *Client_Assignment_User_Bulk Client Assignment Menu - Client listing for Client Assignment User
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8293.aspx
Objective: Verify listed clients in Bulk Assignment call out for Client Admin User
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 29 January 2016
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
import common.ReusableMethods;

public class UserAdmin8293 extends BaseClass {

  @Test
  public void test_UserAdmin8293() throws InterruptedException, ParseException,
      ClassNotFoundException, SQLException {

    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("ClientAdminEmail1");
    String password = property.getProperty("ClientAdminPassword1");

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

      List<String> clients = ClientRestApiUtil.getClientList(emailid, password);
      if (!CollectionUtils.isEmpty(clients)) {
        CustomReporter.log("Clients listed in client home page are:" + clients);
      } else {
        CustomReporter.errorLog("No clients are listed in client home page");
        BaseClass.testCaseStatus = false;
      }

      // Step 3: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("Admin access page loaded successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      if (userAdmin.func_ElementExist("Users List")) {
        CustomReporter.log(" User Admin page Got landed.The user listing is displayed");

        // Step 4: Select one or more rows(Say 3 rows)
        userAdmin.func_selectUsers(3, 0);
        CustomReporter.log("The Users got selected");

        if (userAdmin.func_ElementExist("Selected Users")) {
          CustomReporter.log("The selected users are existing");
        } else {
          CustomReporter.errorLog("The selected User rows are NOT existing");
          BaseClass.testCaseStatus = false;
        }

        // Step 5: Click on Bulk assignment button
        userAdmin.func_ClickElement("Bulk Icon Click");
        CustomReporter.log("The user has clicked on bulk assignment icon");

        if (userAdmin.func_ElementExist("Bulk Client Assignment DropDown Open")) {
          CustomReporter
              .log("The bulk client assignment drop down is getting opened on clicking on the icon ");
        }

        List<String> bulkdClientsList = userAdmin.getBulkClientsList();
        if (CollectionUtils.isEqualCollection(clients, bulkdClientsList)) {
          CustomReporter
              .log("Clients listed in client page are same as clients listed in bulk assignment screen");
          CustomReporter.log("Clients from client page =" + clients + "<<>>"
              + "Clients from bulk assign screen =" + bulkdClientsList);
        } else {
          CustomReporter
              .errorLog("Clients listed in client page are not same as clients listed in bulk assign screen");
          CustomReporter.errorLog("Clients from client page =" + clients + "<<>>"
              + "Clients from bulk assign screen =" + bulkdClientsList);
          BaseClass.testCaseStatus = false;
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
