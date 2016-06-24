/********************************************************************
Test Case Name:*1009_Verifyno Clients ForDisabledClient Admin
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8451.aspx
Objective:Verify that no clients is coming for disabled client admin user in 'Edit panel' and bulk assignment user
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 18 January 2016
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
import common.UserTypeEnum;

public class UserAdmin8451 extends BaseClass {

  @Test
  public void test_UserAdmin8451() throws Exception {
    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    Login_Page ln = new Login_Page(driver);
    int selectedUserRowIndex = 0;
    String winHandleBefore = null;
    String winHandleAfter = null;
    List<Integer> selectedClientRowIndices = null;
    String clientEmailid = property.getProperty("ClientAdminEmail1");
    String clientPassword = property.getProperty("ClientAdminPassword1");
    String SuperEmailid = property.getProperty("SuperAdminUser");
    String SuperPassword = property.getProperty("SuperAdminpassword");

    try {
      // Step 1: Open Site Url
      rm.setRobot(null);
      CustomReporter.log("Browser launched and site url entered");

      Thread.sleep(3000);

      // Step 2: Login as valid clientAdmin user
      ln.func_LoginToIDIOM(clientEmailid, clientPassword);
      CustomReporter.log("Logged in successfully as client Admin");

      Thread.sleep(5000);
      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      rm.webElementSync(pageHeader.personMenu, "visibility");
      Thread.sleep(5000);
      Thread.sleep(5000);

      // Step 3: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("Admin access page loaded successfully for clientAdmin login");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      // step 4:Open URl in other browser

      winHandleBefore = driver.getWindowHandle();
      rm.func_OpenNewwindow(property.getProperty("URL"));

      CustomReporter.log("The URL is opened in another window");

      // Step 5: Login as valid super user/ user Admin in new window
      ln.func_LoginToIDIOM(SuperEmailid, SuperPassword);
      CustomReporter.log("Logged in successfully as user admin / super user");

      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      rm.webElementSync(pageHeader.personMenu, "visibility");
      Thread.sleep(5000);

      // Step 6: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("Admin access page loaded successfully for super user");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      // Step 7:Search for client admin user mentioned in Step 1
      Thread.sleep(3000);

      if (userAdmin.func_CheckSearchFunctionality(clientEmailid)) {
        CustomReporter.log("Client Admin user found in users list");
      } else {
        CustomReporter.errorLog("The result grid is not as expected when providing Search string");
      }

      Thread.sleep(3000);

      // Step 8:Click on edit
      selectedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.CLIENT_ADMIN, 0, false);
      selectedClientRowIndices = userAdmin.getListOfClientRowIndices(selectedUserRowIndex);
      if (selectedUserRowIndex > -1) {
        CustomReporter.log("Edit window appeared for user " + clientEmailid);
      } else {
        CustomReporter.errorLog("Edit window dit not appear for user " + clientEmailid);
        BaseClass.testCaseStatus = false;
      }

      Thread.sleep(3000);

      // Step 9:Click on 'Disable Account' and save the same
      userAdmin.disableEditedUser(selectedUserRowIndex);

      CustomReporter.log("Disable button clicked on selected user");

      userAdmin.saveEditedUser(selectedUserRowIndex);

      CustomReporter.log("Disabled user and saved");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      // Step 10:Logout Super user admin

      // Step 11:Go back to client admin -user admin page me mentioned in Step2
      winHandleAfter = driver.getWindowHandle();
      driver.switchTo().window(winHandleBefore);
      CustomReporter
          .log("The user has navigated back to the client admin page mentioned in step 2");

      // Step 12:Click on 'edit ' for a user
      selectedUserRowIndex = userAdmin.editAnyExistingUser(UserTypeEnum.APP_USER, 0, false);
      CustomReporter.log("The user has clicked on edit button on an app user");
      Thread.sleep(3000);

      if (userAdmin.func_ElementExist("ClientsInEditPAnel")) {
        CustomReporter.errorLog("The clients are present for the disabled user");
        BaseClass.testCaseStatus = false;

      } else {
        CustomReporter.log("The clients are not present for disabled user");
      }

      userAdmin.cancelSelectedUser(selectedUserRowIndex);

      // Step 13:Select some users
      userAdmin.func_selectUsers(3, 0);
      CustomReporter.log("The Users got selected");

      // Click on Bulk assignment button
      userAdmin.func_ClickElement("Bulk Icon Click");
      CustomReporter.log("The user has clicked on bulk assignment icon");

      if (userAdmin.func_ElementExist("Bulk Client Assignment DropDown Open")) {
        CustomReporter
            .log("The bulk client assignment drop down is getting opened on clicking on the icon ");
      }

      // Step 14:Hover on bulk assignment
      userAdmin.func_ClickElement("Bulk Icon Click");
      CustomReporter.log("The user has opened bulk assignment window");

      if (userAdmin.func_ElementExist("Bulk assignment Clients Present")) {
        CustomReporter
            .errorLog("The clients are present for the disabled user in bulk assignment panel");
        BaseClass.testCaseStatus = false;

      } else {
        CustomReporter
            .log("The clients are not present for disabled user in bulk assignment panel");
      }

      // Step 14:Click on assign
      userAdmin.func_ClickElement("BulkAsssignmnet AssignButton");
      CustomReporter.log("The user has clicked on bulk assignment Assign button");
      Thread.sleep(3000);

      if (userAdmin.func_ElementExist("Internal ServerError")) {
        CustomReporter
            .errorLog("The  internal server error is coming on clicking on the assign button using disabled use");
        BaseClass.testCaseStatus = false;
      } else {
        // To be updated with error message
      }

      // //Navigate to another window and enable the user

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
      driver.switchTo().window(winHandleAfter);
      CustomReporter
          .log("The user has navigated to window with 'super admin credentials to enable the client admin user for future testing porpose");
      
      selectedUserRowIndex = userAdmin.editAnyExistingDisabledUser();
      Thread.sleep(5000);
      userAdmin.undoDisableEditedUser(selectedUserRowIndex);
      userAdmin.saveEditedUser(selectedUserRowIndex);
      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(5000);
      userAdmin.editAnyExistingUserForRowIndex(selectedUserRowIndex);
      Thread.sleep(5000);
      userAdmin.assignClientsAdminAccessForUser(selectedClientRowIndices);
      userAdmin.saveEditedUser(selectedUserRowIndex);
      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");
      Thread.sleep(5000);
      try {
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
