/********************************************************************
Test Case Name:*Super User Admin - Search and Filtering - Views - Verify Selection View
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8266.aspx
Objective:: This test case is for verifying Selection view of the Search and Filtering section. The aim is to check whether no components of this section is missing.
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 2 December 2015
 **********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;

public class UserAdmin8266 extends BaseClass {

  @Test
  public void test_UserAdmin8266() throws Exception {
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
        userAdmin.func_selectUsers(3);
        CustomReporter.log("The Users got selected");

        // Step 5: Verify whether any rows selected in Search and Filtering section

        if (userAdmin.func_ElementExist("Selected Users")) {
          CustomReporter.log("The selected users are existing");
        } else {
          CustomReporter.errorLog("The selected User rows are NOT existing");
          BaseClass.testCaseStatus = false;
        }

        // Step 6:Verify the components of the first row

        if (userAdmin.func_Row_Components("Header Row", 3)) {
          CustomReporter.log("The Header row is Proper in User Admin Page while selecting users");
        } else {
          CustomReporter
              .errorLog("The header row is NOT proper in User Admin Page while selecting users");
          BaseClass.testCaseStatus = false;
        }

        // Step 7:Verify the components of the second row
        if (userAdmin.func_Row_Components("Second Row", 1)) {
          CustomReporter.log("The second row componants are proper in user admin page");
        } else {

          CustomReporter.errorLog("The second row componants are  not proper in User admin page");
          BaseClass.testCaseStatus = false;
        }

        // Step 8:Verify the icon displayed for users in Result Set
        List<WebElement> usersList = userAdmin.getList_Users();
        boolean verifyClientAdminIcon = false;
        boolean verifyDisabledUserIcon = false;
        boolean verifySuperUserIcon = false;
        for (int i = 1; i <= usersList.size(); i++) {
          String clientAdminWebElementXpath = UserAdmin_UserPermissions_Page.usersListXpath + "["
              + +i + "]" + UserAdmin_UserPermissions_Page.rolesClientAdminListXpath;
          String disabledUserWebElementXpath = UserAdmin_UserPermissions_Page.usersListXpath + "["
              + +i + "]" + UserAdmin_UserPermissions_Page.disabledUsersListXpath;
          String superUserWebElementXpath = UserAdmin_UserPermissions_Page.usersListXpath + "["
              + +i + "]" + UserAdmin_UserPermissions_Page.rolesClientAdminListXpath;

          if (rm.isElementPresent(clientAdminWebElementXpath)) {
            if ("#ffc60f".equals(driver.findElement(By.xpath(clientAdminWebElementXpath))
                .getAttribute("color"))) {
              verifyClientAdminIcon = true;
            } else {
              break;
            }
          }

          if (rm.isElementPresent(disabledUserWebElementXpath)) {
            if ("#ff0f53".equals(driver.findElement(By.xpath(disabledUserWebElementXpath))
                .getAttribute("color"))) {
              verifyClientAdminIcon = true;
            } else {
              break;
            }
          }

          if (rm.isElementPresent(superUserWebElementXpath)) {
            if ("#4cabeb".equals(driver.findElement(By.xpath(superUserWebElementXpath))
                .getAttribute("color"))) {
              verifyClientAdminIcon = true;
            } else {
              break;
            }
          }
        }

        if (verifyClientAdminIcon || verifyDisabledUserIcon || verifySuperUserIcon) {
          CustomReporter
              .errorLog("Proper icons are not present for super user, client Admin and disabled users");
        } else {
          CustomReporter
              .log("Proper icons are present for super user, client Admin and disabled users");
        }

      } else {
        CustomReporter.errorLog("The users list is not existing");
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
