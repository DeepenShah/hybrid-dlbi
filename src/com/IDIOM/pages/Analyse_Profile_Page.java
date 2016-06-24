package com.IDIOM.pages;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.StringUtils;

import com.reports.CustomReporter;
import common.BaseClass;
import common.ReusableMethods;

public class Analyse_Profile_Page {

  private int wb_Cell_IntValue = 0;
  WebDriver driver;

  public Analyse_Profile_Page(WebDriver driver) {
    PageFactory.initElements(driver, this);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[text()='Web Net']")
  private WebElement lnk_webnet;

  @FindBy(xpath = "//*[@class='selected-client dropdown-parent ng-isolate-scope']")
  private WebElement right_TopClient;

  @FindBys(@FindBy(xpath = "//*[@class='selected-client dropdown-parent']"))
  private List<WebElement> right_TopClient_Lst;

  @FindBy(xpath = "//*[@ng-click='logout()']")
  private WebElement right_Logout;

  @FindBys(@FindBy(xpath = "//*[@ng-click='logout()']"))
  private List<WebElement> right_Logout_Lst;

  @FindBys(@FindBy(xpath = "//*[@class='profile page-level vertical layout ng-scope']"))
  private List<WebElement> analysePage_exists;

  @FindBy(xpath = "//span[text()='Show Audience']/ancestor::button")
  private WebElement btn_ShowAudience;

  @FindBy(xpath = "//img[contains(@src,'clients')]")
  private WebElement img_clientLogo;

  @FindBy(xpath = "//a[text()='Audience']")
  private WebElement audience_Tab;

  @FindBy(xpath = "//a[text()=' Logout']")
  private WebElement link_Logout;
  @FindBy(xpath = "//*[@class='dropdown-list']/li[3]/a")
  private WebElement right_Manage;

  @FindBys(@FindBy(xpath = "//*[@class='block block-summary ng-scope']"))
  private List<WebElement> SummaryList;

  @FindBy(xpath = "//*[@id='summary-anchor']/header/div/ng-transclude/h2")
  private WebElement list_Summary;

  @FindBys(@FindBy(xpath = "//ul[@class='dropdown-list dropdown-scroll']/li[@class='dropdown-list-heading']/span"))
  private List<WebElement> listSummaryValues;

  @FindBy(xpath = "//a[contains(text(),'?')]")
  private WebElement helpIcon;

  @FindBys(@FindBy(xpath = "//div[@class='dropdown-box sticky-jumplinks open']"))
  private List<WebElement> check_summaryPopUpOpen;

  @FindBys(@FindBy(xpath = "//*[@class='profile-container']"))
  public List<WebElement> profile_Container;

  @FindBy(xpath = "//*[@id='summary-anchor']//span")
  private WebElement summary_DownArrow;

  @FindBys(@FindBy(xpath = "//*[@class='dropdown-box sticky-jumplinks open']"))
  private List<WebElement> DropDownOpen;

  @FindBy(xpath = "//li[@class='dropdown-list-heading']/span[text()='Demographics']")
  private WebElement demographics_Menu;

  @FindBys(@FindBy(xpath = "//*[@class='chart-container layout horizontal wrap ng-scope']"))
  private List<WebElement> demographics_Exist;

  @FindBys(@FindBy(xpath = "//*[@class='interests__wrapper']"))
  private List<WebElement> interests_Exist;

  @FindBys(@FindBy(xpath = "//*[@id='summary-anchor']//*[@class='block block-summary ng-scope']/h3"))
  private List<WebElement> summary_Grid_lst;

  @FindBys(@FindBy(xpath = "//*[@class='block chart--block ng-binding ng-scope']"))
  private List<WebElement> Demographics_sections;

  @FindBy(xpath = "//a[text()='Pathing']")
  private WebElement link_pathing;

  @FindBy(xpath = "id('local-markets-anchor')")
  private WebElement Footer;

  @FindBys(@FindBy(xpath = "//*[@class='mbPopup']/p"))
  private List<WebElement> localMarcket_Values;

  @FindBys(@FindBy(xpath = "//*[@class='leaflet-marker-pane']/div"))
  private List<WebElement> Localmarket_MapIcon;

  @FindBy(xpath = "//*[@class='leaflet-marker-pane']/div[1]")
  private WebElement Localmarket_Icon;

  @FindBy(xpath = "//*[@class='leaflet-popup-pane']//*[@class='leaflet-popup-close-button']")
  private WebElement popUpClose;

  @FindBys(@FindBy(xpath = "//div[@class='block chart--block ng-binding ng-scope']"))
  private List<WebElement> TotalDemographicGraphs;

  @FindBys(@FindBy(xpath = "//*[@class='subheader ng-scope']//li/a"))
  private List<WebElement> secondaryNavigation;

  @FindBy(xpath = "//*[@class='subnav']//*[text()='High Value Actions']")
  private WebElement HVA;

  @FindBys(@FindBy(xpath = "//*[text()='There has been an internal server error!']"))
  private List<WebElement> internal_ServerError;

  @FindBys(@FindBy(xpath = "//*[@class='selected-client dropdown-parent navicon ng-isolate-scope left-border']"))
  private List<WebElement> MenuIcon_Lst;

  @FindBy(xpath = "//*[@class='ion ion-navicon-round']")
  private WebElement MenuIcon;

  @FindBys(@FindBy(xpath = "//*[@class='admin-access']"))
  private List<WebElement> AdminAccessPresent;

  @FindBy(xpath = "//ul[@class='nav']/li[contains(.,'Architect')]")
  private WebElement lnk_Architect;

  @FindBy(xpath = "//div[@class='dropdown-box open']//ul[@class='dropdown-list']/li[contains(.,'My Account')]")
  private WebElement myAccount;

  // //////////////IDIOM 2.3////////////////

  @FindBys(@FindBy(xpath = "//section[@id='summary-anchor']"))
  public List<WebElement> summarySection;

  @FindBys(@FindBy(xpath = "//section[@id='demographics-anchor']"))
  public List<WebElement> demographicsSection;

  @FindBys(@FindBy(xpath = "//section[@id='local-markets-anchor']"))
  public List<WebElement> localMarketsSection;

  @FindBys(@FindBy(xpath = "//section[@id='interests-anchor']"))
  public List<WebElement> interestSection;

  @FindBy(css = "div#Demographics_Homeowner>svg")
  public WebElement homeOwnerGraph;

  @FindBy(xpath = "//*[@class='block block--clear error-unicorn layout horizontal ng-scope']//h1[contains(text(),'Audience too narrow')]")
  public WebElement audienceTooNarrow;

  @FindBy(xpath = "//*[@class='block block--clear error-unicorn layout horizontal ng-scope']//a[contains(text(),'adjust your selections')]")
  public WebElement adjustyourselections;

  @FindBy(xpath = "//*[@class='interests__footer ng-binding']")
  private WebElement expandCollapseInterests;

  @FindBys(@FindBy(xpath = "//*[@class='interests ng-scope']"))
  private List<WebElement> interestsList;

  @FindBy(xpath = "//*[@class='cursor-pointer ng-scope ng-isolate-scope']//*[@class='ion ion-chevron-down'][1]")
  private WebElement dropdownXpath;

  @FindBys(@FindBy(xpath = "//ul[@class='dropdown-list dropdown-scroll']/li[@class='dropdown-list-heading']/span"))
  private List<WebElement> dropdownOptions;

  @FindBy(xpath = "//li[@class='dropdown-list-heading']/span[text()='Interests']")
  private WebElement interestDropDownOption;

  @FindBys(@FindBy(xpath = "//*[@class='interests ng-scope']//*[@class='ng-binding ng-scope']"))
  private List<WebElement> interestsPercentageList;
  
  @FindBy(xpath="//ul[@class='subnav']/li[contains(.,'Profile')]")
  public WebElement pageTitle;
  
  /**
   * @param strElement
   *          - String case to verify visibility. Use value from list above.
   * @return boolean True/False
   * @author Shailesh Kava
   * @since 27 April 2016
   * 
   */
  public boolean isVisible(String strElement) {
    boolean bStatus = true;

    WebDriverWait wait = new WebDriverWait(driver, 20);
    try {
      switch (strElement.toLowerCase()) {
      case "summary":
        wait.until(ExpectedConditions.visibilityOfAllElements(summarySection));
        break;
      case "interest":
        wait.until(ExpectedConditions.visibilityOfAllElements(interestSection));
        break;
      case "demographics":
        wait.until(ExpectedConditions.visibilityOfAllElements(demographicsSection));
        break;
      case "local market":
        wait.until(ExpectedConditions.visibilityOfAllElements(localMarketsSection));
        break;
      case "pagetitle":
    	  wait.until(ExpectedConditions.visibilityOf(pageTitle));
          break;
       
      }
    } catch (Exception e) {
      bStatus = false;
    }

    return bStatus;
  }

  /**
   * @param strElement
   *          - String case to click on a value.
   * @return boolean True/False
   * @author Amrutha Nair
   * @since 16May 2016
   * 
   */
  public void performAction(String strElement) {

    switch (strElement.toLowerCase()) {
    case "adjustyourselection":
      adjustyourselections.click();
      break;
    case "expandCollapseInterests":
      expandCollapseInterests.click();
      break;
      
    case "showaudience":
    	btn_ShowAudience.click();
    	break;
    }
  }

  // ////////////IDIOM 2.3-end//////////////

  /*
   * func_RclientElementExists(String elementName) *This method checks whether the client icon is
   * present at the right top and whether "Logout", "Change Client", "Manage" and "Rename" are
   * existingCreated By:Amrutha NairCreated On:04 Aug 2015Modified By | Description of Modification:
   * Modified On:
   */
  public boolean func_RclientElementExists(String elementName) {
    boolean found = true;
    if (BaseClass.rm.webElementExists(right_TopClient_Lst) == true) {
      CustomReporter.log("The client icon at the right top is existing");
      right_TopClient.click();
      switch (elementName) {
      case "ChangeClient":
        // We have to include logic for "Change client" here"
        break;
      case "Rename":
        // We have to include logic for "Change client" here"

        break;
      case "manage":
        // We have to include logic for "Change client" here"
        break;
      }
    } else {
      CustomReporter.errorLog("The client icon at the right top is NOT existing");
      found = false;
    }
    return found;
  }

  /*
   * func_ElementExist(String elementName) This method is used to check the existence of any web
   * element of Audience page Created By: Abhishek Deshpande Created On:04 Aug 2015 Modified By |
   * Description of Modification:Amrutha Nair|Modified Reusable method call and case statement
   */

  public boolean func_ElementExist(String elementName) {
    boolean found = false;
    switch (elementName) {

    case "AnalysePageExists":
      found = BaseClass.rm.webElementExists(analysePage_exists);
      break;
    case "listSummaryExist":
      found = BaseClass.rm.webElementExists(check_summaryPopUpOpen);
      break;

    case "ProfilePage":
      found = BaseClass.rm.webElementExists(profile_Container);
      break;

    case "DropDownOpen":
      found = BaseClass.rm.webElementExists(DropDownOpen);
      break;

    case "Demographics":
      found = BaseClass.rm.webElementExists(demographics_Exist);
      break;

    case "Interests":
      found = BaseClass.rm.webElementExists(interests_Exist);
      break;

    case "FooterSpace":
      found = BaseClass.rm.elementExists(Footer);
      break;

    case "Local Market Map Icon":
      found = BaseClass.rm.webElementExists(Localmarket_MapIcon);
      break;

    case "ServerError":
      found = BaseClass.rm.webElementExists(internal_ServerError);
      break;

    case "Admin Access Present":
      MenuIcon.click();
      found = BaseClass.rm.webElementExists(AdminAccessPresent);
      break;
    }
    return found;
  }

  /*
   * func_ClickElement(String element) This method is used to click on an element in analyse page
   * Created By: Abhishek Deshpande Created On:04 Aug 2015 Modified By | Description of
   * Modification:Amrutha Nair|Added case statement Date:12th Aug 2015
   */
  public void func_ClickElement(String element) throws InterruptedException {
    Thread.sleep(10000);
    switch (element) {

    case "ClientLogo":
      BaseClass.rm.clickWebElement(img_clientLogo);
      break;

    case "Logout":
      MenuIcon.click();
      right_Logout.click();
      break;

    case "Manage":
      right_TopClient.click();
      right_Manage.click();

      break;
    case "Audience":
      audience_Tab.click();
      break;
    case "SummaryList":
      list_Summary.click();
      break;

    case "helpIcon":
      helpIcon.click();
      break;
    case "SummaryDownArrow":
      summary_DownArrow.click();
      break;

    case "Demographics":
      demographics_Menu.click();
      break;
    case "pathing":
      link_pathing.click();
      break;

    case "LocalMarketNode":
      Localmarket_Icon.click();
      break;

    case "HVA":
      HVA.click();
      break;

    case "webnet":
      lnk_webnet.click();
      break;

    case "Architect":
      lnk_Architect.click();
      break;

    case "myaccount":
      MenuIcon.click();
      myAccount.click();
      break;

    case "interests":
      interestDropDownOption.click();
      break;
    }
  }

  /*
   * func_VerifySummaryTableValue*This method checks value in summary table : This method will be
   * changedCreated By:Amrutha NairCreated On:08 September 2015Modified By | Description of
   * Modification:Modified On:
   */
  public String[] func_VerifySummaryTableValue(String filter) {
    String[] value = new String[2];

    for (int i = 1; i <= SummaryList.size(); i++) {

      if (driver
          .findElement(
              By.xpath("//*[@class='block block-summary ng-scope'][" + i
                  + "]//*[@class='contain-text ng-binding']")).getText().contentEquals(filter)) {
        value[0] = driver.findElement(
            By.xpath("//*[@class='block block-summary ng-scope'][" + i
                + "]//*[@class='summary-value ng-binding']")).getText();

        value[1] = driver.findElement(
            By.xpath("//*[@class='block block-summary ng-scope'][" + i
                + "]//*[@class='summary-label contain-text ng-binding']")).getText();

      }
    }
    return value;

  }

  /*
   * func_VerifyProfileDropDown(String dropDown, WebDriver driver)*This method verifies the drop
   * down values present in Analyse >Profile pageCreated By:Amrutha NairCreated On:14 September 2015
   * Modified By | Description of Modification:Modified On:
   */
  public boolean func_VerifyProfileDropDown(String dropDown) throws InterruptedException {
    boolean status = true;

    String[] dropDownArray = dropDown.split(",");
    int i = 1;
    for (String value : dropDownArray) {
      value = value.toUpperCase();
      func_ClickElement("SummaryDownArrow");
      System.out
          .println(driver
              .findElement(
                  By.xpath("//*[@class='dropdown-list dropdown-scroll']/li[@class='dropdown-list-heading']["
                      + i + "]/span")).getText());
      if (value
          .contentEquals(driver
              .findElement(
                  By.xpath("//*[@class='dropdown-list dropdown-scroll']/li[@class='dropdown-list-heading']["
                      + i + "]/span")).getText())) {
        CustomReporter.log("The expected dropdown item :" + value + "  is present is site");
      } else {
        CustomReporter
            .errorLog("The expected dropdown item :" + value + "  is NOT present is site");
        status = false;
      }
      i = i + 1;
    }
    return status;
  }

  /**
   * func_CaptureList(String Case) This method collects the list of values depending on the
   * condition in arraylist Created By: Shailesh Kava Created On: 11 Sep 2015 Modified By |
   * Description of Modification: Modified On:
   */
  public ArrayList<String> func_CaptureList(String Case) {

    ArrayList<String> values = new ArrayList<String>();
    switch (Case) {

    case "getSummaryList":
      list_Summary.click();
      for (WebElement filter : listSummaryValues) {
        values.add(filter.getText());

      }

      break;

    case "Demographics":
      for (WebElement filter : Demographics_sections) {
        values.add(filter.getText());
      }
      break;

    case "SecondaryNavigation":
      for (WebElement secondaryNav : secondaryNavigation) {
        values.add(secondaryNav.getText());
      }
      break;

    }
    return values;

  }

  /*
   * func_VerifyProfileGraphValues(String Case, String filter, WebDriver driver)*This verifies
   * Summary section, demographics sectionCreated By:Amrutha NairCreated On:14 September 2015
   * Modified By | Description of Modification:Modified On:
   */

  public boolean func_VerifyProfileGraphValues(String Case, String filter) {
    boolean status = true;
    String filterName = null;
    boolean flag1 = false;
    boolean flag2 = false;
    switch (Case) {
    case "Summary":
      String[] filterItem = filter.split(":");
      for (int i = 0; i < filterItem.length; i++) {
        for (int j = 1; j <= SummaryList.size(); j++) {
          String[] SubfilterItem = filterItem[i].split("_");
          String[] SubFilterValues = SubfilterItem[1].split(",");
          filterName = SubfilterItem[0].toUpperCase();

          if (driver
              .findElement(
                  By.xpath("//*[@class='block block-summary ng-scope'][" + j
                      + "]//*[@class='contain-text ng-binding']")).getText()
              .contentEquals(filterName)) {
            flag1 = true;
            for (int k = 0; k < SubFilterValues.length; k++) {
              if (driver
                  .findElement(
                      By.xpath("//*[@class='block block-summary ng-scope'][" + j
                          + "]//*[@class='summary-label contain-text ng-binding']")).getText()
                  .contentEquals(SubFilterValues[k])) {
                flag2 = true;
                CustomReporter.log("'" + filterName + "'_'" + SubFilterValues[k]
                    + "'is present in Summary table");
                break;
              }
            }
            break;
          }

        }
        if (!flag1) {
          CustomReporter.errorLog("The filter '" + filterName + "'is not present in Summar table");
          status = false;
        }
        if (!flag2) {
          CustomReporter.errorLog("The data present in summary table for '" + filterName
              + "' is not according to filter selected in Audience page");
          status = false;
        }
        flag1 = false;
        flag2 = false;
      }

      break;

    case "Demographics":
      filterItem = filter.split(":");
      boolean flag3 = false;
      for (int i = 0; i < filterItem.length; i++) {

        String[] SubfilterItem = filterItem[i].split("_");
        for (WebElement demo : Demographics_sections) {

          String[] SubFilterValues = SubfilterItem[1].split(",");
          String[] Demoitem = demo.getText().split("\\n");
          if (Arrays.asList(Demoitem).contains(SubfilterItem[0])) {
            for (int k = 0; k < SubFilterValues.length; k++) {
              if (Arrays.asList(Demoitem).contains(SubFilterValues[k])) {
                CustomReporter.log("The '" + SubfilterItem[0] + "'_'" + SubFilterValues[k]
                    + "'is present in demographics graph section in profile page");
              } else {
                CustomReporter.errorLog("The '" + SubfilterItem[0] + "'_'" + SubFilterValues[k]
                    + "'is NOT present in demographics graph section in profile page");
                status = false;
              }
            }
            flag3 = true;
            break;
          }
        }
        if (!flag3) {
          CustomReporter.errorLog("The '" + SubfilterItem[0]
              + "' is not present in profile>Demographics");
          status = false;
        }
      }

      break;

    case "Unknown":
      for (int j = 1; j <= SummaryList.size(); j++) {
        if (driver
            .findElement(
                By.xpath("//*[@class='block block-summary ng-scope'][" + j
                    + "]//*[@class='summary-label contain-text ng-binding']")).getText()
            .contentEquals("Unknown")) {
          status = false;
          CustomReporter
              .errorLog("The summary table is getting updated with bigges value for unknown for Filter item:"
                  + driver.findElement(
                      By.xpath("//*[@class='block block-summary ng-scope'][" + j
                          + "]//*[@class='contain-text ng-binding']")).getText());
        }
      }

    }

    return status;

  }

  /**
   * @param filter
   * @return
   */
  public boolean func_VerifyProfileGraphs(String filter) {
    boolean status = false;
    String id = null;
    for (int j = 1; j <= SummaryList.size(); j++) {
      String summaryElementXpath = "//*[@class='block block-summary ng-scope'][" + j
          + "]//*[@class='contain-text ng-binding']";
      String summaryElementValueXpath = "//*[@class='block block-summary ng-scope'][" + j
          + "]//*[@class='summary-value ng-binding']";
      if (!(BaseClass.rm.isElementPresent(summaryElementXpath)
          && !StringUtils.isEmpty(driver.findElement(By.xpath(summaryElementXpath)).getText()) && !StringUtils
            .isEmpty(driver.findElement(By.xpath(summaryElementValueXpath)).getText()))) {
        CustomReporter.errorLog("The filter '"
            + driver.findElement(By.xpath(summaryElementXpath)).getText()
            + "'is not having any value in Summary section");
        status = false;
        break;
      } else {
        status = true;
      }
    }

    String[] subfilterItem = filter.split(":");

    for (int i = 0; i < subfilterItem.length; i++) {
      id = func_returnID(subfilterItem[i]);
      String demographicsElementXpath = "//*[@id='" + id + "']";
      if (!BaseClass.rm.isElementPresent(demographicsElementXpath)) {
        status = false;
        break;
      } else {
        status = true;
      }
    }

    return status;

  }

  /*
   * func_returnID(String Filter)This function retunrs id for each item in profile>Demographics
   * Created By:Amrutha NairCreated On:14 September 2015Modified By | Description of Modification:
   * Modified On:
   */

  private String func_returnID(String Filter) {
    String id = null;
    switch (Filter) {
    case "Age":
      id = "Demographics_Age";
      break;

    case "Has Child":
      id = "Demographics_Has-Child";
      break;

    case "Household Size":
      id = "Demographics_Household-Size";
      break;

    case "Household Income":
      id = "Demographics_Household-Income";
      break;
    case "Location":
      id = "Demographics_Location";
      break;
    case "Highest Education":
      id = "Demographics_Highest-Education";
      break;

    case "Demographics":
      id = "demographics-anchor";
      break;
    case "Summary":
      id = "summary-anchor";
      break;
    case "Local Markets":
      id = "local-markets-anchor";
      break;
    case "Gender":
      id = "gender-anchor";
      break;
    case "Ethnicity":
      id = "Demographics_Ethnicity";
      break;

    }
    return id;
  }

  /*
   * func_VerifySummaryWithBiggestValue(String filter, WebDriver driver)This verifies whether
   * summary table is updated with biggest valueCreated By:Amrutha NairCreated On:16 September 2015
   * Modified By | Description of Modification:Modified On:
   */
  public boolean func_VerifySummaryWithBiggestValue(String filter) {
    boolean status = true;

    Actions builder = new Actions(driver);
    String[] filterItem = filter.split(":");
    String Id = null;
    String PercentageValue = null;
    String ItemName = null;
    // Traverse through each Filter data provided.
    for (int i = 0; i < filterItem.length; i++) {

      String[] SubfilterItem = filterItem[i].split("_");
      // Return Id for the filter provided
      Id = func_returnID(SubfilterItem[0]);
      // Return provided subfilter data for each Filter in input
      String[] SubFilterValues = SubfilterItem[1].split(",");
      // Temporary variables for percentage data transformation
      Float pFloatValue = 0.1f;
      int pIntValue = 0;
      String filterV = null;
      String[] summaryData = new String[2];

      // Traverse through each subfilter data for particular filter
      for (int k = 1; k <= SubFilterValues.length; k++) {

        String tmp = null;
        String Style = null;
        String[] PValue = new String[2];

        // If subfilter selected is less than 3 (excluding Unknown) the graph will be doughnut graph
        // To capture data from doughnut graph
        if (SubFilterValues.length < 3) {
          WebElement elemetArc = driver
              .findElement(By
                  .xpath("//*[@id='"
                      + Id
                      + "']//*[name()='svg']//*[name()='g'][@class='c3-chart']/*[name()='g'][@class='c3-chart-arcs']/*[name()='g']["
                      + k + "]"));

          ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",
              elemetArc);

          builder.moveToElement(elemetArc).build().perform();
          PValue = driver
              .findElement(
                  By.xpath("//*[@id='" + Id + "']//*[@class='c3-tooltip']//*[@class='value']"))
              .getText().split("%");
          // PValue=driver.findElement(By.xpath("//*[@id='"+Id+"']/*[name()='svg']/*[name()='g'][1]/*[name()='g'][@class='c3-chart']/*[name()='g'][@class='c3-chart-arcs']/*[name()='g']["+k+"]/*[name()='text']")).getText().split("%");

          Style = driver
              .findElement(
                  By.xpath("//*[@id='"
                      + Id
                      + "']//*[name()='svg']/*[name()='g'][1]/*[name()='g'][@class='c3-chart']/*[name()='g'][@class='c3-chart-arcs']/*[name()='g']["
                      + k + "]/*[name()='g']/*[name()='path']")).getAttribute("style").split(";")[0];
          for (int m = 1; m <= SubFilterValues.length; m++) {
            if (driver
                .findElement(
                    By.xpath("//*[@id='" + Id
                        + "']//*[name()='svg']/*[name()='g'][3]/*[name()='g'][" + m
                        + "]/*[name()='rect'][2]")).getAttribute("style").contains(Style)) {
              tmp = driver.findElement(
                  By.xpath("//*[@id='" + Id + "']//*[name()='svg']/*[name()='g'][3]/*[name()='g']["
                      + m + "]/*[name()='text']")).getText();
            }
          }

        }

        // If subfilter selected is equal to or more than 3 (excluding Unknown) the graph will be
        // bar graph
        // To capture data from bar graph
        else {
          WebElement element = driver
              .findElement(By
                  .xpath("//*[@id='"
                      + Id
                      + "']/*[name()='svg']/*[name()='g'][1]/*[name()='g'][3]/*[name()='g'][1]/*[name()='rect']["
                      + k + "]"));
          ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",
              element);
          builder.moveToElement(element).build().perform();
          PValue = driver
              .findElement(
                  By.xpath("//*[@id='" + Id
                      + "']//*[@class='c3-tooltip']//*[@class='c3-tooltip-y']/*[@class='value']"))
              .getText().split("%");

          tmp = driver.findElement(
              By.xpath("//*[@id='" + Id + "']//*[@class='c3-tooltip']//*[@colspan='1']")).getText();
        }

        // If percentage value is greater than one before, update percentage value with new and
        // update respective filter name as well
        pFloatValue = Float.parseFloat(PValue[0]);
        if (pIntValue < Math.round(pFloatValue)) {
          pIntValue = Math.round(pFloatValue);
          ItemName = tmp;
        }

      }

      // Convert percentage value to string and append with % sign

      PercentageValue = String.valueOf(pIntValue);

      PercentageValue = PercentageValue + "%";
      filterV = SubfilterItem[0].toUpperCase();

      // Retrieve data for respective filter from summary table
      summaryData = func_VerifySummaryTableValue(filterV);

      // Compare Data with data from Summary table
      if (summaryData[0].contentEquals(PercentageValue) && summaryData[1].contentEquals(ItemName)) {
        CustomReporter
            .log("The summary table is getting updated with the biggest percentage value for :"
                + SubfilterItem[0]);
        CustomReporter.log("The item having greatest percentage value for '" + SubfilterItem[0]
            + "' is :'" + ItemName + " and the respective value is :" + PercentageValue);
      } else {
        CustomReporter
            .errorLog("The summary table is NOT getting updated with the biggest percentage value for :"
                + SubfilterItem[0]);
        CustomReporter.errorLog("In Summary table data coming for '" + SubfilterItem[0] + "' is :'"
            + summaryData[0] + ":" + summaryData[1] + ". And in demographics chart data coming is "
            + ItemName + ":" + PercentageValue);
        status = false;
      }

    }
    return status;
  }

  /*
   * func_PrintSpaceMeasurementAtTheBottom() This method is used to click on an element in analyse
   * page Created By: Rohan Macwan Created On:16th Sep 2015 Modified By |
   */
  public void func_PrintSpaceMeasurementAtTheBottom() {
    CustomReporter.errorLog("Currently " + Footer.getCssValue("margin-bottom").toString()
        + " is/are set");

  }

  /*
   * RetrievelocalmarketData(WebDriver driver)This method retrives data from local market section
   * Created By:Amrutha NairCreated On:18 September 2015Modified By | Description of Modification:
   * Modified On:
   */
  public Hashtable RetrievelocalmarketData() throws InterruptedException {
    Hashtable<String, String> localMarket = new Hashtable<String, String>();
    String city = null;
    String percentage = null;
    // Go through each node icon in local market
    System.out.println("Localmarket_MapIcon.size()" + Localmarket_MapIcon.size());
    for (WebElement map : Localmarket_MapIcon) {

      // click on node icon
      map.click();
      // Collect data for each node item value in hash table
      System.out.println(localMarcket_Values.size());
      for (int i = 1; i <= localMarcket_Values.size(); i++) {

        city = driver.findElement(
            By.xpath("//*[@class='mbPopup']/p[" + i + "]/span[@class='town']")).getText();
        percentage = driver.findElement(
            By.xpath("//*[@class='mbPopup']/p[" + i + "]/span[@class='calc']")).getText();
        localMarket.put(city, percentage);

      }

      // close the opened overlay
      if (localMarcket_Values.size() > 0) {
        popUpClose.click();
      }
      // Thread.sleep(500);

    }
    return localMarket;

  }

  /*
   * VerifyAxisValues(String Case, String Filter, WebDriver driver)This method verifies the axis
   * values for demographics graphCreated By:Amrutha NairCreated On:18 September 2015Modified By |
   * Description of Modification:Modified On:
   */

  public boolean func_VerifyAxisValues(String Filter) {
    boolean status = true;
    String id = null;
    String[] filterItem = Filter.split(":");
    // Checking Y axis values
    for (int i = 0; i < filterItem.length; i++) {

      String[] SubfilterItem = filterItem[i].split("_");
      id = func_returnID(SubfilterItem[0]);
      String[] SubFilterValues = SubfilterItem[1].split(",");
      String[] valuesArray = new String[SubFilterValues.length];

      if (SubFilterValues.length >= 3) {

        for (int j = 1; j <= SubFilterValues.length; j++) {
          valuesArray[j - 1] = driver.findElement(
              By.xpath("//*[@id='" + id
                  + "']//*[name()='g'][@class='c3-axis c3-axis-x']/*[name()='g'][" + j
                  + "]//*[name()='tspan']")).getText();
        }

        if (Arrays.equals(valuesArray, SubFilterValues)) {
          CustomReporter.log("The values coming in Y axis is proper for : " + SubfilterItem[0]);
        } else {
          CustomReporter.errorLog("The values coming in Y axis is NOT proper for :"
              + SubfilterItem[0]);
          status = false;
        }

        // Checking X axis values
        String[] xaxisValues = new String[5];
        String[] xAxisexpected = { "20%", "40%", "60%", "80%", "100%" };
        for (int k = 1; k < 6; k++) {
          xaxisValues[k - 1] = driver.findElement(
              By.xpath("//*[@id='" + id
                  + "']/*[name()='svg']//*[name()='g'][@class='c3-axis c3-axis-y']/*[name()='g']["
                  + k + "]//*[name()='tspan']")).getText();

        }
        if (Arrays.equals(xaxisValues, xAxisexpected)) {
          CustomReporter.log("The values coming in X axis is proper ");
        } else {
          CustomReporter.errorLog("The values coming in X axis is NOT proper ");
          status = false;
        }
      } else {
        CustomReporter.log("The graph for " + SubfilterItem[0]
            + " is doughnut graph which does not have x and y axis");
      }
    }

    return status;

  }

  /*
   * public boolean func_Verify_HighLightElement(String Filter, WebDriver driver) throws
   * InterruptedException{ boolean status =true; String id=null; String[]
   * filterItem=Filter.split(":");
   * 
   * Actions builder = new Actions(driver); //Checking Y axis values for(int
   * i=0;i<filterItem.length;i++){
   * 
   * String[] SubfilterItem=filterItem[i].split("_"); id=func_returnID(SubfilterItem[0]); String []
   * SubFilterValues=SubfilterItem[1].split(","); String[] valuesArray=new
   * String[SubFilterValues.length];
   * 
   * if(SubFilterValues.length<3) { for (int j=1;j<=SubFilterValues.length;j++){
   * System.out.println(SubFilterValues[j-1]); WebElement element=
   * driver.findElement(By.xpath("//*[@id='"
   * +id+"']/*[name()='svg']/*[name()='g'][1]/*[name()='g'][3]/*[name()='g'][4]/*[name()='g']["
   * +j+"]/*[name()='g']/*[name()='path']")); String style=element.getAttribute("style");
   * if(SubFilterValues.length==2 && j==1){ j=j+1; WebElement element2=
   * driver.findElement(By.xpath("//*[@id='"
   * +id+"']/*[name()='svg']/*[name()='g'][1]/*[name()='g'][3]/*[name()='g'][4]/*[name()='g']["
   * +j+"]/*[name()='g']/*[name()='path']")); String textColor =element2.getCssValue("color");
   * String bkgColor = element2.getCssValue("background-color");
   * System.out.println("Background Color : " + bkgColor); System.out.println("TextColor : " +
   * textColor ); j=j-1; } else if(j==2){ j=j-1; WebElement element2=
   * driver.findElement(By.xpath("//*[@id='"
   * +id+"']/*[name()='svg']/*[name()='g'][1]/*[name()='g'][3]/*[name()='g'][4]/*[name()='g']["
   * +j+"]/*[name()='g']/*[name()='path']")); String textColor =element2.getCssValue("color");
   * String bkgColor = element2.getCssValue("background-color");
   * System.out.println("Background Color : " + bkgColor); System.out.println("TextColor : " +
   * textColor ); j=j+1; } System.out.println("d:"+element.getAttribute("d"));
   * 
   * System.out.println("style : " + style ); }
   * 
   * Thread.sleep(3000); for (int k=1;k<=SubFilterValues.length;k++){
   * System.out.println("Inside"+SubFilterValues[k-1]); WebElement element1=
   * driver.findElement(By.xpath("//*[@id='"+id+
   * "']/*[name()='svg']/*[name()='g'][1]/*[name()='g'][3]/*[name()='g'][4]/*[name()='g']["
   * +k+"]/*[name()='g']/*[name()='path']")); builder.moveToElement(element1).build().perform();
   * if(SubFilterValues.length==2 && k==1){ k=k+1; WebElement element2=
   * driver.findElement(By.xpath("//*[@id='"
   * +id+"']/*[name()='svg']/*[name()='g'][1]/*[name()='g'][3]/*[name()='g'][4]/*[name()='g']["
   * +k+"]/*[name()='g']/*[name()='path']")); String textColor =element2.getCssValue("color");
   * String bkgColor = element2.getCssValue("background-color");
   * System.out.println("Background Color : " + bkgColor); System.out.println("TextColor : " +
   * textColor ); k=k-1; } else if(k==2){ k=k-1; WebElement element2=
   * driver.findElement(By.xpath("//*[@id='"
   * +id+"']/*[name()='svg']/*[name()='g'][1]/*[name()='g'][3]/*[name()='g'][4]/*[name()='g']["
   * +k+"]/*[name()='g']/*[name()='path']")); String textColor =element2.getCssValue("color");
   * System.out.println("TextColor : " + textColor );
   * 
   * 
   * String bkgColor = element2.getCssValue("background-color");
   * System.out.println("Background Color : " + bkgColor); k=k+1; }
   * 
   * String style=element1.getAttribute("style");
   * 
   * 
   * }
   * 
   * } } return status;
   * 
   * }
   */

  public boolean func_Verify_HighLightElement(String Filter) throws InterruptedException {
    boolean status = true;
    String id = null;
    String[] filterItem = Filter.split(":");

    Actions builder = new Actions(driver);
    // Checking Y axis values
    for (int i = 0; i < filterItem.length; i++) {
      String[] SubfilterItem = filterItem[i].split("_");
      id = func_returnID(SubfilterItem[0]);
      String[] SubFilterValues = SubfilterItem[1].split(",");
      String[] valuesArray = new String[SubFilterValues.length];
      if (SubFilterValues.length < 3) {
        for (int j = 1; j <= SubFilterValues.length; j++) {
          WebElement element1 = driver
              .findElement(By
                  .xpath("//*[@id='"
                      + id
                      + "']/*[name()='svg']/*[name()='g'][1]/*[name()='g'][3]/*[name()='g'][4]/*[name()='g']["
                      + j + "]/*[name()='g']/*[name()='path']"));
          builder.moveToElement(element1).build().perform();
          if (SubFilterValues.length == 2) {
            if (driver.findElements(
                By.xpath("//*[@id='" + id
                    + "']/*[name()='svg']//*[name()='g'][contains(@class, 'c3-focused')]")).size() > 0
                && driver.findElements(
                    By.xpath("//*[@id='" + id
                        + "']/*[name()='svg']//*[name()='g'][contains(@class, 'c3-defocused')]"))
                    .size() > 0) {
              CustomReporter.log("The '" + SubfilterItem[0] + "':'" + SubFilterValues[j - 1]
                  + "' is in focus  and other element is not in focus on selecting the first one");
            } else {
              CustomReporter
                  .errorLog("The '"
                      + SubfilterItem[0]
                      + "':'"
                      + SubFilterValues[j - 1]
                      + "' is in NOT focus and other element is not in focus on selecting the  first one");
              status = false;
            }
          } else {
            if (driver.findElements(
                By.xpath("//*[@id='" + id
                    + "']/*[name()='svg']//*[name()='g'][contains(@class, 'c3-focused')]")).size() > 0) {
              CustomReporter.log("The '" + SubfilterItem[0] + "':'" + SubFilterValues[j - 1]
                  + "' is in focus on selecting the same");
            } else {
              CustomReporter.errorLog("The '" + SubfilterItem[0] + "':'" + SubFilterValues[j - 1]
                  + "' is in NOT focus on selecting the same");
              status = false;
            }
          }

        }
      } else {
        CustomReporter.log("The '" + SubfilterItem[0] + "' graph is bar graph");
      }

    }

    return status;

  }

  public boolean func_VerifyStickyheaderOnScroll(String heading) {
    boolean status = true;
    String id = func_returnID(heading);
    // Scroll till the provided section
    WebElement element = driver.findElement(By.xpath("//*[@id='" + id + "']"));
    ReusableMethods.scrollTo(driver, element);

    List<WebElement> elements = driver.findElements(By.xpath("//section[@id='" + id
        + "']//*[@class='filter-group ng-isolate-scope scrolled-past']//h2"));
    // Verify whether on scroll the page focus is moved to the mentioned section
    if (elements.size() > 0) {
      CustomReporter.log("On scroll, page focus has been moved to '" + heading + "'section");
    } else {
      CustomReporter.errorLog("On scroll, page focus has NOT been moved to '" + heading
          + "'section");
      status = false;
    }

    return status;
  }

  /*
   * func_barChartSelector() This method is used to access various chart elements to ultimately
   * check tool tip displayed on Bars of Charts Created By: Rohan Macwan Created On:18th Sep 2015
   * Modified By |
   */
  public void func_barChartSelector() {
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    int TotalGraphs = TotalDemographicGraphs.size();

    String ChartTitles[] = func_ChartTitles();

    for (int i = 1; i <= TotalGraphs; i++) {
      WebElement we = driver.findElement(By.xpath("//div[@class='chart-container ng-scope']/div["
          + i + "]/div[1]"));

      CustomReporter.log("Graph - " + ChartTitles[i - 1]);

      String idVal = we.getAttribute("id");
      System.out.println(idVal);

      List<WebElement> ListBarsOfCharts = driver
          .findElements(By
              .xpath("//*[@id='"
                  + idVal
                  + "']/*[name()='svg']//*[name()='g'][@class='c3-event-rects c3-event-rects-single']/*[name()='rect']"));
      Actions action = new Actions(driver);

      if (ListBarsOfCharts.size() > 2) {

        for (WebElement Bar : ListBarsOfCharts) {
          System.out.println("Bar - " + Bar);
          action.moveToElement(Bar).doubleClick().build().perform();

          WebElement CaptionInToolTip = driver.findElement(By.xpath("//*[@id='" + idVal
              + "']//div[@class='c3-tooltip-container']//tr[1]"));
          WebElement PercentInToolTip = driver.findElement(By.xpath("//*[@id='" + idVal
              + "']//div[@class='c3-tooltip-container']//tr[2]"));

          if (BaseClass.rm.elementExists(CaptionInToolTip)) {
            CustomReporter.log("The Bar Caption is - " + CaptionInToolTip.getText());
          } else {
            CustomReporter.errorLog("The Bar Caption is not found");
            BaseClass.testCaseStatus = false;
          }
          if (BaseClass.rm.elementExists(PercentInToolTip)) {
            CustomReporter.log("And Tool Tip is - " + PercentInToolTip.getText());

          } else {
            CustomReporter.errorLog("The Tool Tip is not found");
            BaseClass.testCaseStatus = false;
          }
        }
      } else {
        // CustomReporter.log("Graph - "+ListBarsOfCharts.get(i).getText());
        for (WebElement Bar : ListBarsOfCharts) {
          System.out.println("Bar - " + Bar);

          // This gives a bar in Doughnut graph
          List<WebElement> BarsInDoughnutGraph = driver
              .findElements(By
                  .xpath("//*[@id='"
                      + idVal
                      + "']//*[name()='svg']//*[name()='g']//*[name()='g']//*[name()='g']//*[name()='g']//*[name()='g']//*[name()='path']"));
          // This gives tool tips for a particular Doughnut bar
          List<WebElement> BarTooltip = driver
              .findElements(By
                  .xpath("//*[@id='"
                      + idVal
                      + "']//*[name()='svg']//*[name()='g']//*[name()='g']//*[name()='g']//*[name()='g']//*[name()='text']"));
          // This gives caption of Bar Categories displayed below Doughnut graph. E.g. For Has Child
          // - Yes, No
          List<WebElement> BarCategoryCaption = driver.findElements(By.xpath(".//*[@id='" + idVal
              + "']//*[name()='svg']//*[contains(@class,'c3-legend-item c3-legend-item')]"));
          // *[@id='Demographics_Has-Child']//*[name()='svg']//*[name()='g']//*[name()='g'][@class='c3-chart-texts']

          for (int k = 0; k < BarsInDoughnutGraph.size(); k++) {
            if (BaseClass.rm.elementExists(BarsInDoughnutGraph.get(k))) {
              action.moveToElement(BarsInDoughnutGraph.get(k)).doubleClick().build().perform();
              CustomReporter.log("The Bar Caption is - " + BarCategoryCaption.get(k).getText());
            } else {
              CustomReporter.errorLog("The Bar Caption is not found");
              BaseClass.testCaseStatus = false;
            }
            if (BaseClass.rm.elementExists(BarTooltip.get(k))) {
              CustomReporter.log("And Tool Tip is - " + BarTooltip.get(k).getText());

            } else {
              CustomReporter.errorLog("The Tool Tip is not found");
              BaseClass.testCaseStatus = false;
            }
          }

        }
      }

    }

  }

  /*
   * func_ChartTitles() This method is used to return all Charts Title Created By: Rohan Macwan
   * Created On:21st Sep 2015 Modified By |
   */
  public String[] func_ChartTitles() {
    String ChartT[] = new String[TotalDemographicGraphs.size()];
    int i = 0;
    for (WebElement Bar : TotalDemographicGraphs) {
      String strInnerHtml = Bar.getAttribute("innerHTML");
      // System.out.println("HTML " + strInnerHtml);
      String strValue = strInnerHtml.substring(0, strInnerHtml.indexOf("<div"));
      System.out.println("Found value " + strValue);
      ChartT[i] = strValue;
      i++;
    }
    return ChartT;

  }

  /*
   * func_DetermineChart() This method is used to determine whether chart is Doughnut or Bar Chart
   * and accordingly will access X and Y Axis if bar chart Created By: Rohan Macwan Created On:18th
   * Sep 2015 Modified By |
   */
  public void func_DetermineChart() {
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    int TotalGraphs = TotalDemographicGraphs.size();

    String ChartTitles[] = func_ChartTitles();

    for (int i = 1; i <= TotalGraphs; i++) {
      try {
        // This element is meant to fetch the Graph Class
        WebElement we = driver.findElement(By.xpath("//div[@class='chart-container ng-scope']/div["
            + i + "]/div[1]"));

        // This is where the Graph Class is exactly getting fetched
        String idVal = we.getAttribute("id");
        System.out.println(idVal);

        // This will fetch total number of bars for a particular Chart. Take a look - idVal variable
        // determines graph dynamically
        List<WebElement> ListBarsOfCharts = driver
            .findElements(By
                .xpath("//*[@id='"
                    + idVal
                    + "']/*[name()='svg']//*[name()='g'][@class='c3-event-rects c3-event-rects-single']/*[name()='rect']"));

        // This will print Chart Title
        CustomReporter.log("Graph - " + ChartTitles[i - 1]);

        Actions action = new Actions(driver);
        int XAxis = 1;
        int YAxis = 1;
        int flag = 0;
        int flag1 = 0;
        if (ListBarsOfCharts.size() > 2) {
          try {
            // This will give the list of all Y Axis values for a Particular graph
            List<WebElement> YAxisVals = driver
                .findElements(By
                    .xpath("//*[@id='"
                        + idVal
                        + "']//*[@class='c3-axis c3-axis-x']/*[name()='g']//*[name()='text']//*[name()='tspan']"));
            for (WebElement YAxisVal : YAxisVals) {
              CustomReporter.log("Y Axis Value " + YAxis + " - " + YAxisVal.getText());
              YAxis++;
            }

          } catch (NoSuchElementException e) {
            e.printStackTrace();
            // CustomReporter.errorLog("Either Graph is not present or Graph is not Bar Chart or X Axis elements are missing/not found on graph");
            BaseClass.testCaseStatus = false;
            flag = 1;
            break;
          }

          try {
            // This will give the list of all Y Axis values for a Particular graph
            List<WebElement> XAxisValues = driver
                .findElements(By
                    .xpath("//*[@id='"
                        + idVal
                        + "']//*[name()='svg']//*[@class='c3-axis c3-axis-y']/*[name()='g']//*[name()='text']//*[name()='tspan']"));
            for (WebElement XAxisValue : XAxisValues) {
              CustomReporter.log("X Axis Value " + XAxis + " - " + XAxisValue.getText());
              XAxis++;
            }

          } catch (NoSuchElementException e) {
            e.printStackTrace();
            // CustomReporter.errorLog("Either Graph is not present or Graph is not Bar Chart or X Axis elements are missing/not found on graph");
            BaseClass.testCaseStatus = false;
            flag = 1;
            break;
          }

          if (flag == 0) {
            CustomReporter.log("This Graph is correctly shown as Bar Graph");
          } else {
            CustomReporter
                .errorLog("Either Graph is not present or Graph is not Bar Chart or Elements are missing/not found on graph");
            BaseClass.testCaseStatus = false;
          }

        } else if (ListBarsOfCharts.size() != 0) {
          // CustomReporter.log("Graph - "+ListBarsOfCharts.get(i).getText());
          for (WebElement Bar : ListBarsOfCharts) {

            try {
              System.out.println("Bar - " + Bar);

              // This gives a bar in Doughnut graph
              List<WebElement> BarsInDoughnutGraph = driver
                  .findElements(By
                      .xpath("//*[@id='"
                          + idVal
                          + "']//*[name()='svg']//*[name()='g']//*[name()='g']//*[name()='g']//*[name()='g']//*[name()='g']//*[name()='path']"));

              // This gives caption of Bar Categories displayed below Doughnut graph. E.g. For Has
              // Child - Yes, No
              List<WebElement> BarCategoryCaption = driver.findElements(By.xpath(".//*[@id='"
                  + idVal
                  + "']//*[name()='svg']//*[contains(@class,'c3-legend-item c3-legend-item')]"));

              flag1 = 1;
              for (int k = 0; k < BarsInDoughnutGraph.size(); k++) {
                if (BaseClass.rm.elementExists(BarsInDoughnutGraph.get(k))) {
                  action.moveToElement(BarsInDoughnutGraph.get(k)).doubleClick().build().perform();
                  flag1 = 0;
                  CustomReporter.log("The Bar Caption is - " + BarCategoryCaption.get(k).getText());
                } else {
                  CustomReporter.errorLog("The Bar Caption is not found");
                  BaseClass.testCaseStatus = false;
                }

              }
            } catch (NoSuchElementException e) {
              // CustomReporter.errorLog("Either Graph is not present or Graph is not Doughnut Chart or Some elements are missing on graph");
              BaseClass.testCaseStatus = false;
              e.printStackTrace();
              flag1 = 1;
              break;
            }

          }
          if (flag1 == 0) {
            CustomReporter.log("This Graph is correctly shown as Doughnut Graph");
          } else {
            CustomReporter
                .errorLog("Either Graph is not present or Graph is not Doughnut graph or Some elements are missing on graph");
            BaseClass.testCaseStatus = false;
          }

        } else {
          CustomReporter.errorLog("Bars on Graphs are not found");
          BaseClass.testCaseStatus = false;
        }

      } catch (Exception e) {
        System.out.println("Error Found");
        CustomReporter.errorLog("No Graphs found");
        e.printStackTrace();
        BaseClass.testCaseStatus = false;
        break;
      }

    }

  }

  /*
   * func_TopMenuElementExists(String item)This method checks whether the menu icon is present at
   * the right top and whether "Logout" "Admin Access" and My account are under itCreation
   * Date:Date:03 November 2015Modified By | Description of Modification:Modified On:
   */
  public boolean func_TopMenuElementExists(String item) {
    boolean found = true;

    // if(BaseClass.rm.webElementExists(right_TopClient_Lst) == true)
    // {
    // CustomReporter.log("The client icon at the right top is existing");
    // right_TopClient.click();
    if (BaseClass.rm.webElementExists(MenuIcon_Lst)) {
      CustomReporter.log("Menu icon is existing at the header in Manage iDiom Page");

      switch (item) {
      case "Logout":

        if (BaseClass.rm.webElementExists(right_Logout_Lst) == true) {

          CustomReporter.log("The Logout element is existing under client icon at right top");
        } else {
          CustomReporter
              .errorLog("The Logout element is NOT existing under client icon at right top");
          found = false;
        }

        break;
      }
    } else {
      CustomReporter.errorLog("Menu icon is NOT existing at the header in Manage iDiom Page");
      found = false;
    }
    return found;

  }

  /**
   * This method is used to get private WebElement out of this class. Make sure that element name
   * should be matched.
   * 
   * @param strWebElement
   *          - String name of the WebElement variable.
   * @return Returns WebElement
   * @CreatedOn 01/12/2016
   * @author Deepen Shah
   */
  public WebElement func_GetLocalWebElement(String strWebElement) {
    WebElement retWebElement = null;
    try {

      Field aField = this.getClass().getDeclaredField(strWebElement);
      aField.setAccessible(true);
      retWebElement = (WebElement) aField.get(this);

    } catch (Exception e) {

    }
    return retWebElement;
  }

	//added by Sunil 01 Jun 2016
	public ArrayList func_getSummaryvalues() {
		ArrayList<String> listvalues = new ArrayList<String>();
		try{
			
		    for(WebElement value:listSummaryValues){
		    	listvalues.add(value.getText());
		    }
			/*Field aField = this.getClass().getDeclaredField(strWebElement);
			aField.setAccessible(true);
			retWebElement = (WebElement) aField.get(this);*/
		
		}catch(Exception e){
			
		}
		return listvalues;
	}
	
  /**
   * Get number of interests displayed
   * 
   * @author Vikram Hegde
   * @return
   */
  public int getInterestsListSize() {
    return interestsList.size();
  }

  /**
   * Get list of drop down values
   * 
   * @author Vikram Hegde
   * @return
   */
  public List<String> getActualDropDownValues() {
    List<String> dropDownOptions = new ArrayList<String>();
    dropdownXpath.click();
    for (WebElement dropdownOption : dropdownOptions) {
      dropDownOptions.add(dropdownOption.getText());
    }
    return dropDownOptions;
  }

  /**
   * Get list of drop down values
   * 
   * @author Vikram Hegde
   * @return
   */
  public List<String> getExpectedDropDownValues() {
    List<String> dropDownOptions = new ArrayList<String>();
    dropDownOptions.add("Summary");
    dropDownOptions.add("Demographics");
    dropDownOptions.add("Local Markets");
    dropDownOptions.add("Interests");
    return dropDownOptions;
  }

  /**
   * Get list of drop down values
   * 
   * @author Vikram Hegde
   * @return
   */
  public List<String> getInterestsPercentageList() {
    List<String> interestsPercentList = new ArrayList<String>();
    for (WebElement interestsPercentage : interestsPercentageList) {
      interestsPercentList.add(interestsPercentage.getText());
    }
    return interestsPercentList;
  }
}
