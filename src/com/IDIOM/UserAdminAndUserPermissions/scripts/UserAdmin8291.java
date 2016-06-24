/********************************************************************
Test Case Name:*Super_Admin_User_Bulk Client Assignment Menu - Client listing for super admin user
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8291.aspx
Objective: Verify client listing for super admin user in Bulk Client Assignment screen
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 30 December 2015
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

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

public class UserAdmin8291 extends BaseClass {

  @Test
  public void test_UserAdmin8291() throws Exception {
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
        // Step 4:Select more than one User from user listing
        userAdmin.func_selectUsers(3);

        CustomReporter.log("The user has selected 3 users from the liest");

        // Step 4:Click on "Bulk Client Assignment" menu from Top Right corner
        userAdmin.func_ClickElement("Bulk Icon Click");
        CustomReporter.log("The user has clicked on bulk assignment icon");

        if (userAdmin.func_ElementExist("Bulk Client Assignment DropDown Open")) {
          CustomReporter
              .log("The bulk client assignment drop down is getting opened on clicking on the icon ");
        } else {
          CustomReporter
              .errorLog("The clients drop down is NOT getting opened on clicking on bulk assignment icon");
          BaseClass.testCaseStatus = false;
        }

        // Step 5: Verify all the clients should appear in Bulk client assignment list
        List<String> clientsList = ClientRestApiUtil.getClientList(emailid, password);
        List<String> bulkdClientsList = userAdmin.getBulkClientsList();
        if (CollectionUtils.isEqualCollection(clientsList, bulkdClientsList)) {
          CustomReporter
              .log("Clients listed in client page are same as clients listed in bulk assignment screen");
          CustomReporter.log("Clients from client page =" + clientsList + "<<>>"
              + "Clients from bulk assign screen =" + bulkdClientsList);
        } else {
          CustomReporter
              .errorLog("Clients listed in client page are not same as clients listed in bulk assign screen");
          CustomReporter.errorLog("Clients from client page =" + clientsList + "<<>>"
              + "Clients from bulk assign screen =" + bulkdClientsList);
          BaseClass.testCaseStatus = false;
        }

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
