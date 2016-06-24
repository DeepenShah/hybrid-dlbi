/********************************************************************
Test Case Name:
User admin: After disabeling the user account whole row should grayed out
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8925.aspx
Objective: This test case checks if user is disabled
Module: UserAdminAndUserPermissions
Created By:Vikram Hegde
Date: 5 May 2016
 **********************************************************************/
package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;

public class UserAdmin8265 extends BaseClass {

  @Test
  public void test_UserAdmin8925() throws Exception {

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

      // Step 3: Click on Admin Access link from header
      pageHeader.performAction("adminaccess");
      CustomReporter.log("Admin access page loaded successfully");

      rm.webElementSync("idiomspinningcomplete");
      rm.webElementSync("pageload");

      // Step 4: Edit any existing App user and who has clients assinged
      // and that user should be active

      if (userAdmin.func_ElementExist("Users List")) {
        CustomReporter.log("The user listing is displayed");
      }

      List<WebElement> usersList = userAdmin.getList_Users();

      for (int i = 1; i <= usersList.size(); i++) {
        String appUserWebElementXpath = UserAdmin_UserPermissions_Page.usersListXpath + "[" + +i + "]"
            + UserAdmin_UserPermissions_Page.rolesAppUserListXpath;

        if (rm.isElementPresent(appUserWebElementXpath)) {

          String appUserEditButtonXpath = UserAdmin_UserPermissions_Page.usersListXpath + "[" + +i + "]"
              + UserAdmin_UserPermissions_Page.editUserButtonXpath;
          if (rm.isElementPresent(appUserEditButtonXpath)) {
            String numOfClientsForUserXpath = UserAdmin_UserPermissions_Page.usersListXpath + "[" + +i
                + "]" + UserAdmin_UserPermissions_Page.numberOfClientsForUserXpath;
            if (rm.isElementPresent(numOfClientsForUserXpath)) {

              int numberOfClients = Integer.valueOf(driver.findElement(
                  By.xpath(numOfClientsForUserXpath)).getText());
              if (numberOfClients > 0) {
                CustomReporter.log("App User found from users list");
                rm.clickWebElement(driver.findElement(By.xpath(appUserEditButtonXpath)));

                CustomReporter.log("Edit window appeared");

                rm.waitTime(10000);

                // Step 5: Click on disable account button and
                // then click on save
                String appUserDisableButtonXpath = UserAdmin_UserPermissions_Page.usersListXpath + "[" + +i
                    + "]" + UserAdmin_UserPermissions_Page.disableUserButtonXpath;

                if (rm.isElementPresent(appUserDisableButtonXpath)) {
                  rm.clickWebElement(driver.findElement(By.xpath(appUserDisableButtonXpath)));
                  String appUserSaveButtonXpath = UserAdmin_UserPermissions_Page.usersListXpath + "[" + +i
                      + "]" + UserAdmin_UserPermissions_Page.saveUserButtonXpath;
                  if (rm.isElementPresent(appUserSaveButtonXpath)) {
                    rm.waitTime(10000);
                    rm.clickWebElement(driver.findElement(By.xpath(appUserSaveButtonXpath)));
                    CustomReporter.log("Disabled user and saved");
                  }

                  rm.waitTime(10000);

                  numberOfClients = Integer.valueOf(driver.findElement(
                      By.xpath(numOfClientsForUserXpath)).getText());
                  if (numberOfClients == 0) {
                    CustomReporter.log("Number of assigned clients is 0");

                  } else {
                    CustomReporter
                        .errorLog("Number of assigned clients still shows greater than 0");
                    throw new IDIOMException(
                        "Number of assigned clients still shows greater than 0");
                  }

                  if (rm.isElementPresent(appUserWebElementXpath)
                      && "#b6b4b3".equals(driver.findElement(By.xpath(appUserWebElementXpath))
                          .getCssValue("clor"))) {
                    CustomReporter.log("User row is greyed out");
                  } else {
                    CustomReporter.errorLog("User row is not greyed out");
                  }
                  rm.waitTime(10000);
                  // Step 6: Edit again the same user change name and save it again
                  String appUserNameInputFieldXpath = UserAdmin_UserPermissions_Page.usersListXpath + "["
                      + +i + "]" + UserAdmin_UserPermissions_Page.userNameInputFieldXpath;

                  if (rm.isElementPresent(appUserNameInputFieldXpath)) {
                    driver.findElement(By.xpath(appUserNameInputFieldXpath)).sendKeys(
                        "testUpdateUserName");
                    if (rm.isElementPresent(appUserSaveButtonXpath)) {
                      rm.waitTime(10000);
                      rm.clickWebElement(driver.findElement(By.xpath(appUserSaveButtonXpath)));
                      CustomReporter.log("Updated disabled user's username and saved");
                    }
                    rm.waitTime(10000);
                    if ("testUpdateUserName".equals(driver.findElement(By
                        .xpath(appUserNameInputFieldXpath)))) {
                      CustomReporter.errorLog("Disabled user's user name is allowed to update");
                    } else {
                      CustomReporter.log("Disabled user's user name is not updated");
                    }
                    break;
                  }
                }
              }
            }
          }

        }
      }

    } catch (IDIOMException ie) {
      ie.printStackTrace();
      BaseClass.testCaseStatus = false;
      String strMsgAndFileName[] = ie.getMessage().split("###");
      System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
      CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
      rm.captureScreenShot(strMsgAndFileName[1], "fail");

    } catch (Exception e) {
      e.printStackTrace();
      BaseClass.testCaseStatus = false;
      e.printStackTrace(System.out);
      CustomReporter.errorLog("Some exception is generated, " + e.getMessage()
          + ". Please check the log for more details");
      rm.captureScreenShot("UnassignClientsAppUser", "fail");
    } finally {
      // Step 7: Logout from application.
      try {
        pageHeader.performAction("logout");
        CustomReporter.log("Logged out successfully");
      } catch (Exception ee) {
        ee.printStackTrace();
      }
    }

  }
}
