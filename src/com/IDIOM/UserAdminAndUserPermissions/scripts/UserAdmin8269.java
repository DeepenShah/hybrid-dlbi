/********************************************************************
Test Case Name: User Admin_Search and Filtering_Bulk Client Assignment Menu_ 4.2.4-C(i,ii,iii)_Verify cancel action on bulk assignment screen
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8268.aspx
Objective: Verify cancel action on bulk assignment screen
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 3 December 2015
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

public class UserAdmin8269 extends BaseClass {

  @Test
  public void test_UserAdmin8268() throws Exception {
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

        // Step 4: Select one or more rows(Say 3 rows)
        List<Integer> selectedUserIndices = userAdmin.func_selectUsers(3,0);
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

        // Step 6: Click on any client which is not assigned to any of the selected users
        userAdmin.bulkAssignAssignClients(1);

        if (userAdmin.verifyBulkAssignClients(1)) {
          CustomReporter.log("Selected Client text color turned Blue and rightmark is appearing");
        } else {
          CustomReporter
              .errorLog("Selected Client text color did not turn Blue and rightmark is not appearing");
        }

        // Step 7: Click on Cancel button from bulk assignment screen
        userAdmin.func_ClickElement("BulkAsssignmentCancelButton");

        CustomReporter.log("Bulk Assign client cancel button is clicked");

        if (userAdmin.func_ElementExist("Selected Users")) {
          CustomReporter.errorLog("The selected users are existing");
          BaseClass.testCaseStatus = false;
        } else {
          CustomReporter.log("The selected User rows are NOT existing");
        }

        if (userAdmin.verifyNoDataUpdatesAfterBulkClientAssign(selectedUserIndices)) {
          CustomReporter.log("Bulk assignment screen is closed and no data is updated");
        } else {
          CustomReporter.errorLog("Bulk assignment screen is closed and data is updated");
        }

        // Step 8: Click on Bulk assignment button
        userAdmin.func_selectUsers(3,0);
        userAdmin.func_ClickElement("Bulk Icon Click");

        // Verify whether client selected in step 5 is not appearing
        if (userAdmin.verifyBulkAssignClients(1)) {
          CustomReporter.errorLog("Clients selected in step 5 is not cleared");
        } else {
          CustomReporter.log("Clients selected in step 5 is cleared");
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
