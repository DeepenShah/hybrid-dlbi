/********************************************************************
Test Case Name: *Super User Admin - Search and Filtering - Views - Verify Basic View
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8259.aspx
Objective:This test case is for verifying Basic view of the Search and Filtering section. The aim is to check whether no components of this section is missing.
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 21 December 2015
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;

public class UserAdmin8259 extends BaseClass {

  @Test
  public void test_UserAdmin8259() throws Exception {
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
        // Step 3 - Verify whether any rows selected in Search and Filtering section
        if (userAdmin.func_CheckWhetherAllCheckboxesByDefaultAreUnSelected()) {
          CustomReporter.log("No User's checkbox is selected by default when page got loaded");
        } else {
          CustomReporter
              .errorLog("Single user's or few users's check boxes have appeared to be selected by default");
          BaseClass.testCaseStatus = false;
        }

        // Step 4 - Verify the first/header row in Search and Filtering section
        if (userAdmin.func_ElementExist("DisplayedUserCount")) {
          CustomReporter.log("User Count is displayed in proper sequence (first postion)");
          if (userAdmin.func_ElementExist("UsersTextAtTheTop")) {
            if (userAdmin.func_CompareText("UsersTextAtTheTop", 0)) {
              CustomReporter.log("Text 'users' is present");
            } else {
              CustomReporter
                  .errorLog("Text 'users' seems to be not present in  User Count at the top");
              BaseClass.testCaseStatus = false;
            }
          } else {
            CustomReporter
                .errorLog("Text 'users' seems to be not present in  User Count at the top");
            BaseClass.testCaseStatus = false;
          }
        } else {
          CustomReporter
              .errorLog("Either User count at the top is not present or it is not in sequence which is mentioned in the test case");
          BaseClass.testCaseStatus = false;
        }

        rm.waitTime(3000);

        if (userAdmin.func_ElementExist("SearchTextBoxForSequence")) {
          CustomReporter.log("Search Text Box is displayed in proper sequence (Second postion)");
        } else {
          CustomReporter
              .errorLog("Either Search Textbox is not present or it is not in sequence which is mentioned in the test case");
          BaseClass.testCaseStatus = false;
        }

        rm.waitTime(3000);
        // Step 5 - Verify the second/header row in Search and Filtering section
        if (userAdmin.func_ElementExist("ListGridHeaderRowElements")) {
          CustomReporter.log("Grid's header row is displayed in proper sequence (third postion)");
          if (userAdmin.func_ElementExist("NameElementInGridHeaderRow")) {
            if (userAdmin.func_CompareText("NameElementInGridHeaderRow", 0)) {
              CustomReporter.log("Element Name is present");
            } else {
              CustomReporter.errorLog("Element Name seems to be not present");
              BaseClass.testCaseStatus = false;
            }

          } else {
            CustomReporter.errorLog("Element Name seems to be not present");
            BaseClass.testCaseStatus = false;
          }
        } else {
          CustomReporter
              .errorLog("Either Grid Header row is not present or it is not in sequence which is mentioned in the test case");
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
      // Step 9: Logout from application.
      try {
        pageHeader.performAction("logout");
        CustomReporter.log("Logged out successfully");
      } catch (Exception ee) {
        ee.printStackTrace();
      }
    }
  }
}
