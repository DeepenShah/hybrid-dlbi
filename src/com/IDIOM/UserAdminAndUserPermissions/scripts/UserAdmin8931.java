/********************************************************************
Test Case Name:
Return to IDIOM link from header should not accessible while create user pop up is open in admin access page
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8931.aspx
Objective: This test case checks returnToIdiom link is not clicked when user pop up is open
Module: UserAdminAndUserPermissions
Created By:Vikram Hegde
Date: 2 May 2016
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

public class UserAdmin8931 extends BaseClass {

  @Test
  public void test_UserAdmin8931() throws Exception {

    UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
    ReusableMethods rm = new ReusableMethods(driver);
    String emailid = property.getProperty("SuperAdminUser");
    String password = property.getProperty("SuperAdminpassword");

    try {
      // Step 1: Launch browser and enter url
      CustomReporter.log("Launched Browser and Enter URL");
      Login_Page ln = new Login_Page(driver);

      Thread.sleep(3000);

      // Step 2: Login to Idiom as Super user/ UserAdmin
      ln.func_LoginToIDIOM(emailid, password);
      CustomReporter.log("Logged in successfully");

      Thread.sleep(5000);
      rm.webElementSync("pageload");
      rm.webElementSync("jqueryload");
      rm.webElementSync(pageHeader.personMenu, "visibility");
      Thread.sleep(5000);

      // Step 3: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("The user has clicked on 'Admin Access'");

      Thread.sleep(5000);
      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      // Step 4: Click on create user bottom from right most
      if (!userAdmin.isVisible("createuserbutton")) {
        throw new IDIOMException(
            "Create user button is missing###createNewUser-createUserButtonIsMissing");
      } else {
        CustomReporter.log("Admin access page is open, clicking on create new user");
        userAdmin.func_PerformAction("AddUserClick");
      }

      /**
       * <pre>
       *     Step 5: Click on Return to IDIOM link from Admin access page
       * 	    without closing create user popup
       * </pre>
       **/
      if (userAdmin.isVisible("returnToIdiom") && !rm.isClickable(userAdmin.getReturnToIdiom())) {
        userAdmin.func_ClickElement("Return To Idiom");
        throw new IDIOMException("Returned to Idiom Page while user popup is open");
      } else {
        Thread.sleep(5000);
        CustomReporter
            .log("User is unable to click on return to Idiom, when create user popup is open which is working as expected");
        BaseClass.rm.webElementSync(userAdmin.getAddUserOverlay_Cancel(), "visibility");
        if (userAdmin.func_ElementExist("Add Users Overlay Exist")) {
          rm.clickWebElement(userAdmin.getAddUserOverlay_Cancel());
          CustomReporter.log("Clicked cancel button in add new user overlay");
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
      // Step 6: Logout from application.
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
