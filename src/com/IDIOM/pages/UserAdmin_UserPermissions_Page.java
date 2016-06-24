/**
 * UserAdmin_UserPermissions_Page
 * List Of Methods:
 * func_AssignClients(int Position,int Count, String Case,  String ClientNames[]) :This method assigns clients and save checking on case 
 * func_PerformClientAssignment(int Count, WebDriver driver, String Case) :This method perform client assignment and checks the key icon is coming on saving the same
 *func_CheckSearchFunctionality(String Keyword) : This method verifies whether Search is working fine
 *func_CheckTheVisibilityOfLastClientForEditedUser () :This method will check the visibility of the Last client for the edited user
 *func_CheckWhetherAllCheckboxesByDefaultAreUnSelected():This method is used to check whether by default all checkboxes in User List Grid are unchecked
 *func_ClearText(String elementName ):This method is used to clear the text of Name Textbox
 * func_ClickEditUserByNumber(int iUserOrder):This will click on edit button for a particular user
 * func_ClickElement(String element):This method is used to click on an element
 * func_CompareClientNamesWithWhichAreThereInDB(String Case, String emailAddress) :This method will scroll down user list grid 
 * func_CompareText():This method will compare text of the element
 *func_GetClientListByUserNumber(int iUserOrder): This will give list of clients for a particular edited user
 *func_GetCoOrdinates(String webelement): This method will scroll down user list grid 
 * func_GetLocalWebElement(String strWebElement) :This method is used to get private WebElement out of this class.
 *func_GetNormalAppUsersCount(): This method will give total count of normal app users
 * func_PerformAction(String element) :This method performs various actions depending on Switch case provided
 * func_RetrieveAccessRights(String Case, String emailAddress): This method will fetch all AccessRights for any user
 *func_RetrieveUsersAndClick(String Case):This method will fetch all type of Users based on parameter and click its check boxes in User List Grid
 *func_Row_Components(String Case, int count):This method verifies the contnet in each row is expected
 *func_ScrollUserList():This method will scroll down user list grid
 *func_SearchClientsInClientAssignmentCallOut:This method will put text in Search Textbox in Client Assignment call out 
 *func_SearchForKeyIcon(String[][] NameEmailAddress):This method will find whether user list grid has client key icon for a particular user 
 *func_selectUsers(int count):This method selects the users depending on the number passed
 * func_SortClients(String Case): This method is used to sort the clients
 *func_SortUsers_Check(String Sort, String Case) :This method verifies Sort Functionality content
 * func_TypeSearchCriteria(String elementName):This method will put text in Search Textbox in Client Assignment call outt
 * func_Verify_HelpContent(String HelpContent, WebDriver driver):This method verifies help content
 * func_VerifyBGColor(String Case, String ExpectedColour):This method will check the BG colour for the elemnt
 * func_VerifyResetPwdMessageformat():This method will check the reset password message format
 * func_VerifyPageComponants(String User):This method verifies page components depending on user
 *
 */
package com.IDIOM.pages;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.reports.CustomReporter;
import common.BaseClass;
import common.UserTypeEnum;

public class UserAdmin_UserPermissions_Page {

  WebDriver driver;

  public UserAdmin_UserPermissions_Page(WebDriver driver) {
    PageFactory.initElements(driver, this);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@class='admin-access']")
  private WebElement AdminAccess;

  @FindBy(xpath = "//span[@class='ion ion-navicon-round']")
  private WebElement MenuIcon;

  @FindBy(xpath = "//*[@ng-click='opened=false; headerCtrl.logout();']")
  private WebElement LogoutIcon;

  @FindBys(@FindBy(xpath = "//*[@class='users-header batch-header']/*[@class='dropdown-parent ng-isolate-scope']/button[@class='ion ion-ios-briefcase-outline']"))
  private List<WebElement> list_bulk_ClientIcon;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']"))
  private List<WebElement> List_Users;

  @FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[@ng-model='temp.name']")
  private WebElement FirstNameTextBox;

  @FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope'][1]//button[text()='Save']")
  private WebElement FirstSaveButton;

  @FindBy(xpath = "//*[@ng-click='logout()']")
  private WebElement link_Logout;

  @FindBys(@FindBy(xpath = "//*[@class='errormessage']"))
  private List<WebElement> ValidationMessageForBlankName;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']"))
  private List<WebElement> UsersPresent;

  @FindBys(@FindBy(xpath = "//*[@class='header layout horizontal center ng-isolate-scope']//*[contains(text(),'Manage Team')]"))
  private List<WebElement> ManageTeam;

  @FindBys(@FindBy(xpath = "//*[@class='return-to-idiom']"))
  private List<WebElement> ReturnToIdiomLst;

  @FindBy(xpath = "//*[@class='return-to-idiom']")
  private WebElement ReturnToIdiom;

  @FindBys(@FindBy(xpath = "//*[@class='users-header ng-binding']"))
  private List<WebElement> HeaderLst;

  @FindBys(@FindBy(xpath = "//*[@class='ptor-create-user floater md-button md-fab md-primary']"))
  private List<WebElement> AddUserButtonLst;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//*[@type='checkbox']"))
  private List<WebElement> ListOfCheckBoxesForAllUsers;

  @FindBys(@FindBy(xpath = "//*[@class='users-header batch-header']/*[@ng-model='selectedUserCount']"))
  private List<WebElement> SelectedUserCount;

  @FindBy(xpath = "//*[@class='users-header batch-header']/*[@ng-model='selectedUserCount']")
  private WebElement SelectedUsers;

  @FindBy(xpath = "//*[@class='ion ion-close-round']")
  private WebElement BulkCloseIcon;

  @FindBy(xpath = "//*[@class='users-header batch-header']/*[@class='dropdown-parent ng-isolate-scope']/button[@class='ion ion-ios-briefcase-outline']")
  private WebElement BulkClientIcon;

  @FindBys(@FindBy(xpath = "//*[@class='client-search-select-list']//li"))
  private List<WebElement> ListOfClientsInBulkAssignmentCallOut;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[@class='client-search-select-list']"))
  private List<WebElement> EditPanelPresent_FirstUser;

  @FindBys(@FindBy(xpath = "*//div[@class='users-container admin-authority']//div[@class='block']/div[1]"))
  private List<WebElement> DisplayedUserCount;

  // Below element is defined for the second time as this is for determining its sequence and that
  // is why instead of using class, numbering is used in it
  @FindBys(@FindBy(xpath = "*//*[@id='userInput']"))
  private List<WebElement> SearchTextBoxForSequence;

  @FindBys(@FindBy(xpath = "*//div[@class='users-container admin-authority']//div[@class='block']/div[4]"))
  private List<WebElement> ListGridHeaderRowElements;

  @FindBys(@FindBy(xpath = "*//div[@class='users-container admin-authority']//div[@class='block']/div[1]/ng-pluralize"))
  private List<WebElement> UsersTextAtTheTop;

  @FindBys(@FindBy(xpath = "*//div[@class='users-container admin-authority']//div[@class='block']/div[4]//div[@class='th'][2]"))
  private List<WebElement> NameElementInGridHeaderRow;
  
  //Added by Sunil 25 May 2016
  @FindBys(@FindBy(xpath = "*//div[@class='users-container admin-authority']//div[@class='block']/div[4]//div[@class='th'][3]"))
  private WebElement CreatedElementInGridHeaderRow;

  
  @FindBys(@FindBy(xpath = "*//div[@class='users-container admin-authority']//div[@class='block']/div[4]//div[@class='th'][4]"))
  private WebElement AccessElementInGridHeaderRow;

  
  @FindBys(@FindBy(xpath = "*//div[@class='users-container admin-authority']//div[@class='block']/div[4]//div[@class='th'][5]"))
  private WebElement EmailElementInGridHeaderRow;
  
  @FindBys(@FindBy(xpath = "*//div[@class='users-container admin-authority']//div[@class='block']/div[4]//div[@class='th'][6]"))
  private WebElement ClientElementInGridHeaderRow;

  @FindBy(xpath = "//*[@class='header layout horizontal center ng-isolate-scope']//*[@class='logo']")
  private WebElement Idiom_Logo;

  @FindBy(xpath = "//*[@title='Help']")
  private WebElement Help_ICon;

  @FindBy(xpath = "//*[@class='help-content ng-isolate-scope']//h2")
  private WebElement Help_Header;

  @FindBy(xpath = "//*[@class='help-content ng-isolate-scope']//p")
  private WebElement Help_Content;

  @FindBy(xpath = "//*[@class='help-content ng-isolate-scope']//*[@class='help-center']")
  private WebElement Help_Button;

  @FindBy(xpath = "//*[@ng-model='sortorder']")
  private WebElement Sort_DrpDown;

  @FindBys(@FindBy(xpath = "//*[@ng-model='sortorder']/option"))
  private List<WebElement> SortItems;

  @FindBy(xpath = "//*[@class='expando']//*[@class='inner-client-search-select']//input")
  private WebElement SearchTextBoxOfClientAssignmentCallOut;

  @FindBy(xpath = "//*[@class='ng-pristine ng-valid ng-touched']/*[text()='Date Added']")
  private WebElement DateAdded_SortOption;

  @FindBy(xpath = "//*[@id='scoper']//button[@title='Create User']")
  private WebElement AddUserButton;

  @FindBys(@FindBy(xpath = "//*[@class='overlay-modal user-modal ng-scope']"))
  private List<WebElement> AddUsersOverlay;

  @FindBy(xpath = "//*[@class='overlay-modal user-modal ng-scope']//*[@class='layout horizontal']//input[@id='name']")
  private WebElement Adduser_Name;

  @FindBy(xpath = "//*[@class='overlay-modal user-modal ng-scope']//*[@class='layout horizontal']//input[@id='emailAddress']")
  private WebElement Adduser_Email;

  @FindBy(xpath = "//*[@class='overlay-modal user-modal ng-scope']//*[text()='Create']")
  private WebElement Adduser_Create;

  @FindBy(xpath = "//*[@class='overlay-modal user-modal ng-scope']//*[text()='Cancel']")
  private WebElement Adduser_Cancel;

  @FindBy(xpath = "(//div[@class='modal-actions ng-scope'])[1]//a[text()='Cancel']")
  private WebElement AdduserAssignOverlay_Cancel;

  @FindBy(xpath = "//div[@class='modal-actions'][1]//a[text()='Cancel']")
  private WebElement addUserOverlay_Cancel;

  @FindBy(xpath = "//*[@class='new-user']/i")
  private WebElement AssignClients_UserName;

  @FindBys(@FindBy(xpath = "//*[@class='overlay-modal user-modal ng-scope']//*[@class='client-search-select-list']//li/i[@class='ion ion-plus-round']"))
  private List<WebElement> AddUsers_ClientList;

  @FindBy(xpath = "//*[@class='overlay-modal user-modal ng-scope']//*[@class='modal-actions ng-scope']/*[text()='Assign']")
  private WebElement AddUser_AssignButton;

  @FindBy(xpath = "//*[@class='overlay-modal user-modal ng-scope']/*[@class='modal-header']//*[@class='active']")
  private WebElement AddUser_ActiveTab;

  @FindBy(xpath = "//*[@class='idiom-form-header']//input[@id='userInput']")
  private WebElement SearchTextBox;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//*[@class='user-selected ng-valid ng-not-empty ng-dirty ng-valid-parse ng-touched']"))
  private List<WebElement> lst_SelectedUsers;

  @FindBy(xpath = "//*[@class='users-header batch-header']/button[@class='ion ion-close-round']")
  private WebElement Header_CloseIcon;

  @FindBys(@FindBy(xpath = "//*[@class='users-header batch-header']/button[@class='ion ion-close-round']"))
  private List<WebElement> Header_CloseIcon_Exist;

  // @FindBys(@FindBy(xpath="//*[@class='users-search flex-9 start-justified']//span[@class='ion ion-ios-search-strong']"))
  @FindBys(@FindBy(xpath = "//span[@class='ion ion-ios-search-strong']"))
  private List<WebElement> Search_Icon_exist;

  @FindBys(@FindBy(xpath = "//*[@class='idiom-form-header']//input[@id='userInput']"))
  private List<WebElement> SearchTextBox_Exist;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//*[@ng-model='userentry.temp.name']"))
  private List<WebElement> ListAllUsersName;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//*[@ng-model='userentry.u.emailAddress']"))
  private List<WebElement> ListAllUsersEmailAddress;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//*[@class='td']/*[@class='book ng-binding']"))
  private List<WebElement> ListDate;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//*[@class='td centered']/*[@class='book ng-binding']"))
  private List<WebElement> ListCountClients;

  @FindBy(xpath = "//*[@id='scoper']//*/button[text()='Assign']")
  private WebElement AssignButton;
  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//*[@title='clientAdmin']"))
  private List<WebElement> ListKeyIcons;

  @FindBys(@FindBy(xpath = "//*[@class='users-header batch-header']//*[@class='dropdown-box assign-clients-box open']"))
  private List<WebElement> Lst_Bulk_Client_Open;

  @FindBys(@FindBy(xpath = "//*[@class='users-header batch-header']//*[@class='client-search-select-list']//li"))
  private List<WebElement> clientListBulkAssign;

  @FindBy(xpath = "//*[@class='dropdown-box assign-clients-box open']//button[text()='Cancel']")
  private WebElement CanCel_BulkAssignment;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//*[@class='btn btn-link edit ng-scope']"))
  private List<WebElement> EditLst;

  @FindBys(@FindBy(xpath = "*//div[@class='user-entry ng-scope ng-isolate-scope disabled-user']//div[@class='td roles']"))
  private List<WebElement> ListBlockedUsersIcon;

  @FindBys(@FindBy(xpath = "*//div[@class='user-entry ng-scope ng-isolate-scope']//div[@class='td roles']//i[@title='userAdmin']"))
  private List<WebElement> ListSuperAdminUsersIcon;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//button[text()='Edit']"))
  private List<WebElement> ListEditButtons;

  @FindBys(@FindBy(xpath = "*//div[@class='user-entry ng-scope ng-isolate-scope disabled-user']//div[@class='td roles']"))
  private List<WebElement> ListBlockedUsersIcons;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//div[@class='td roles']//i[@title='userAdmin']"))
  private List<WebElement> ListSuperAdminUsersIcons;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//div[@class='td roles']//i[@title='clientAdmin']"))
  private List<WebElement> ListClientAdminUsersIcons;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//div[@class='td roles' and not(descendant::i)]"))
  private List<WebElement> ListNormalUsersIcons;

  @FindBys(@FindBy(xpath = "*//div[@class='user-entry ng-scope ng-isolate-scope']//div[@class='td roles']"))
  private List<WebElement> UsersRoleList;
  @FindBy(xpath = "*//div[@class='users-container admin-authority']//div[@class='block']/div[1]/ng-pluralize")
  private WebElement UsersTextAtTop;

  @FindBy(xpath = "*//div[@class='users-container admin-authority']//div[@class='block']/div[4]//div[@class='th'][2]")
  private WebElement NameInGridHeaderRow;

  @FindBys(@FindBy(xpath = "//*[@class='ng-binding ng-scope assigned']"))
  private List<WebElement> AssignedClients;

  @FindBys(@FindBy(xpath = "//div[@class='user-actions']//span[text()='Disable']"))
  private List<WebElement> ListDisabledButtons;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope' or @class='user-entry ng-scope ng-isolate-scope disabled-user']"))
  private List<WebElement> ListBlockedUsersAndEnabledUsers;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope disabled-user']"))
  private List<WebElement> ListBlockedUsers;
  @FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[contains(@ng-click ,'disableUser')]")
  private WebElement DisableButton;
  @FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[contains(@ng-click ,'saveQueue')]")
  private WebElement SaveButton;

  @FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[contains(@ng-click ,'clearQueue')]")
  private WebElement cancelButton;

  @FindBys(@FindBy(xpath = "//*[@class='client-search-select-list']//*[@class='ion ion-close-round' or @class='ion ion-plus-round']"))
  public List<WebElement> AssignOrAnassignClientsLst;

  @FindBys(@FindBy(xpath = "//*[contains(text(),'There was an error on save')]"))
  private List<WebElement> ErrorMsgLst;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//*[@class='client-search-select-list']"))
  private List<WebElement> EditSelectionBox;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//*[@class='ion ion-key']"))
  private List<WebElement> NoKeyIconSelected;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope']//button[text()='Save']"))
  private List<WebElement> SaveButtonlst;

  @FindBys(@FindBy(xpath = "//*[@class='btn btn-link reset-password-button active-disabled']"))
  private List<WebElement> DisabledResetPwdst;

  @FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[contains(@class,'btn btn-link reset-password-button')]")
  private WebElement ResetPwdButton;

  @FindBys(@FindBy(xpath = "//*[@class='btn btn-link ng-scope active-disabled']"))
  private List<WebElement> DisabledDisableAccntst;

  @FindBys(@FindBy(xpath = "//*[@class='admin users-admin page-level ng-scope']"))
  private List<WebElement> UserAdminPageExist;

  @FindBy(xpath = "*//div[@class='scroll-box']")
  private WebElement ScrollBox;

  @FindBy(xpath = "//div[@class='user-entry user-entry-header layout center-center horizontal wrap']")
  private WebElement HeaderRow;

  @FindBy(xpath = "//*[@class='user-status layout horizontal center-justified']//*[contains(@ng-click,'userentry.enableUser(userentry.u)')]")
  private WebElement UndoButton;

  @FindBys(@FindBy(xpath = "//*[@class='user-status layout horizontal center-justified']//*[contains(@ng-click,'userentry.enableUser(userentry.u)')]"))
  private List<WebElement> UndoButtonlst;

  @FindBys(@FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope disabled-user']//*[@class='btn btn-link edit ng-scope']"))
  private List<WebElement> DisabledUser_Editlst;

  @FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope disabled-user'][1]//button[text()='Save']")
  private WebElement Enable_SaveButton;
  @FindBys(@FindBy(xpath = "//*[@class='ng-binding ng-scope']//*[@class='ion ion-plus-round']"))
  private List<WebElement> AssignButtonLst;

  // @FindBys(@FindBy(xpath="//*[@class='client-search-select-list']//li[@class='ng-binding ng-scope batch-select']"))
  @FindBys(@FindBy(xpath = "//*[@class='client-search-select-list']//li[contains(@class,'ng-binding ng-scope batch-select')]"))
  private List<WebElement> ListClientsInEditedUserInUserListGrid;

  @FindBy(xpath = "//*[@class='user-status layout horizontal ng-scope']//p[@class='status-message ng-binding']")
  private WebElement ResetPwdMessage;

  @FindBys(@FindBy(xpath = "//div[@class='user-entry ng-scope ng-isolate-scope']"))
  private List<WebElement> userList;

  @FindBys(@FindBy(xpath = "//header[@class='layout center-center horizontal wrap']/parent::div/footer/div[3]/div/div[1]/div[2]/ul/li"))
  private List<WebElement> clientList;

  @FindBy(xpath = "//div[@class='modal-content']//p[@class='new-user']//i[@class='book ng-binding']")
  private WebElement AddUser_AssignOverlay_NameField;

  @FindBy(xpath = "//div[@class='toast toast-success']")
  private WebElement ResetPasswordMessageBox;

  @FindBy(xpath = "//div[@class='toast toast-success']//div[@class='toast-message']")
  private WebElement ResetPasswordMessage;

  @FindBy(xpath = "*//p[@class='status-message ng-binding']")
  private WebElement ResetPasswordDateTimeInEditPanel;

  @FindBys(@FindBy(xpath = "//*[@class='ng-binding ng-scope assigned' or @class='ng-binding ng-scope']"))
  private List<WebElement> ClientsInEditPAnel;

  @FindBys(@FindBy(xpath = "//*[@class='ng-binding ng-scope batch-select' ]"))
  private List<WebElement> blkAssignmentClients;

  @FindBy(xpath = "//*[@ ng-click='batchAssignClients(clients, true);resetCount()']")
  private WebElement bulkAsssignmnet_AssignButton;

  @FindBys(@FindBy(xpath = "//*[text()='There has been an internal server error!']"))
  private List<WebElement> internal_ServerError;

  @FindBys(@FindBy(xpath = "//*[@class='dropdown-box help-box open']/*[@class='help-content ng-isolate-scope']"))
  private List<WebElement> help_DrpDownOpen;

  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'name')]/span[contains(@class,'ion ion-arrow-up-b')]"))
  private List<WebElement> name_AcendingExist;

  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'name')]/span[contains(@class,'ion ion-arrow-down-b')]"))
  private List<WebElement> name_descendingExist;

  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'created')]/span[contains(@class,'ion ion-arrow-up-b')]"))
  private List<WebElement> Date_AcendingExist;

  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'created')]/span[contains(@class,'ion ion-arrow-down-b')]"))
  private List<WebElement> Date_descendingExist;

  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'emailAddress')]/span[contains(@class,'ion ion-arrow-up-b')]"))
  private List<WebElement> Email_AcendingExist;

  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'emailAddress')]/span[contains(@class,'ion ion-arrow-down-b')]"))
  private List<WebElement> Email_descendingExist;

  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'numClients')]/span[contains(@class,'ion ion-arrow-up-b')]"))
  private List<WebElement> Clients_AcendingExist;

  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'numClients')]/span[contains(@class,'ion ion-arrow-down-b')]"))
  private List<WebElement> Clients_descendingExist;

  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'perm')]/span[contains(@class,'ion ion-arrow-up-b')]"))
  private List<WebElement> Role_AcendingExist;

  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'perm')]/span[contains(@class,'ion ion-arrow-down-b')]"))
  private List<WebElement> Role_descendingExist;

  @FindBy(xpath = "//*[contains(@ng-click,'name')]/span[contains(@class,'ion ion-arrow-up-b')]")
  private WebElement name_AcendingIcon;

  @FindBy(xpath = "//*[contains(@ng-click,'name')]/span[contains(@class,'ion ion-arrow-down-b')]")
  private WebElement name_descendingIcon;

  @FindBy(xpath = "//*[contains(@ng-click,'created')]/span[contains(@class,'ion ion-arrow-up-b')]")
  private WebElement Date_AcendingIcon;

  @FindBy(xpath = "//*[contains(@ng-click,'created')]/span[contains(@class,'ion ion-arrow-down-b')]")
  private WebElement Date_descendingIcon;

  @FindBy(xpath = "//*[contains(@ng-click,'emailAddress')]/span[contains(@class,'ion ion-arrow-up-b')]")
  private WebElement Email_AcendingIcon;

  @FindBy(xpath = "//*[contains(@ng-click,'emailAddress')]/span[contains(@class,'ion ion-arrow-down-b')]")
  private WebElement Email_descendingIcon;

  @FindBy(xpath = "//*[contains(@ng-click,'numClients')]/span[contains(@class,'ion ion-arrow-up-b')]")
  private WebElement Clients_AcendingIcon;

  @FindBy(xpath = "//*[contains(@ng-click,'numClients')]/span[contains(@class,'ion ion-arrow-down-b')]")
  private WebElement Clients_descendingIcon;

  @FindBy(xpath = "//*[contains(@ng-click,'perm')]/span[contains(@class,'ion ion-arrow-up-b')]")
  private List<WebElement> Role_AcendingIcon;

  @FindBy(xpath = "//*[contains(@ng-click,'perm')]/span[contains(@class,'ion ion-arrow-down-b')]")
  private List<WebElement> Role_descendingIcon;

  @FindBys(@FindBy(xpath = "//*[@ng-model='temp.name']"))
  private List<WebElement> NameLst;

  @FindBys(@FindBy(xpath = "//*[contains(@class,'user-entry ng-scope ng-isolate-scope')]//*[@class='td']/*[@class='book ng-binding']"))
  private List<WebElement> DateLst;
  
  //added by Sunil 26 May 2016

  @FindBys(@FindBy(xpath = "//*[contains(@class,'user-entry ng-scope ng-isolate-scope')]//*[@class='td roles']/*[@class='ion ion-ios-people ng-scope']"))
  private List<WebElement> AccessLst;
  
  @FindBys(@FindBy(xpath = "//*[contains(@class,'user-entry ng-scope ng-isolate-scope')]//*[@class='td']/*[@ng-model='userentry.u.emailAddress']"))
  private List<WebElement> EmailLst;
  
  @FindBys(@FindBy(xpath = "//*[contains(@class,'user-entry ng-scope ng-isolate-scope')]//*[@class='td centered']/*[@class='book ng-binding']"))
  private List<WebElement> ClientLst;
  
  @FindBys(@FindBy(xpath = "//*[contains(@ng-click,'name')]/span[@class='ion ion-arrow-up-b active']"))
  private List<WebElement> NameAscendingIconLst;

  /***************** IDIOM 2.3 Start ***************/
  @FindBys(@FindBy(xpath = ".//*[@id='scoper']/div/button"))
  public List<WebElement> CreateUserButton;
  @FindBy(xpath = ".//*[@id='scoper']/div/button")
  public WebElement createNewUser;
  @FindBy(xpath = "//div[@class='modal-header']//li[text()='Create User']")
  public WebElement label_CreateUser;
  @FindBys(@FindBy(xpath = "(//header[@class='layout center-center horizontal wrap'])[1]/div[5]/input"))
  public WebElement firstUsersEmailToWait;
  @FindBy(xpath = "//button[@class='btn btn-muted flex-6']")
  private WebElement cancelBulkClientAssignButton;

  @FindBys(@FindBy(xpath = "//*[@class='form-errors']//span[not(@class='ng-hide') and contains(@ng-show,'createUser')]"))
  private List<WebElement> formErrors;
  
  @FindBy(xpath = "//*[@class='expando']//*[@class='user-status layout horizontal ng-scope']")
  public WebElement  expandedUserArea;
  
  @FindBy(xpath = "//*[@class='user-entry ng-scope ng-isolate-scope disabled-user'][1]")
  private WebElement  disabledUser;
  

  @FindBy(xpath = "//*[@class='dropdown-parent ng-isolate-scope']//*[@class='inner-client-search-select']//input")
  private WebElement SearchTextBoxOfBulkAssignmentCallOut;
  

  public static final String selectClientIconBulkAssignXpath = "//i[@class='ion batch-select ion-checkmark-round']";

  public static final String selectUserCheckbox = "//*[@class='user-selected ng-pristine ng-untouched ng-valid ng-empty' and @type='checkbox']";

  public static final String usersListXpath = "//*[@class='user-entry ng-scope ng-isolate-scope']";

  public static final String rolesSuperUserListXpath = "//div[@class='td roles']//i[@title='userAdmin']";

  public static final String rolesClientAdminListXpath = "//div[@class='td roles']//i[@title='clientAdmin']";

  public static final String editUserButtonXpath = "//*[@class='btn btn-link edit ng-scope']";

  public static final String numberOfClientsForUserXpath = "//div[@class='td centered']/span";

  public static final String clientListForUserXpath = "//*[@class='client-search-select-list']//li";

  public static final String unAssignUserClientIcon = "/i[@class='ion potr-client-assignment ion-close-round']";

  public static final String assignUserClientIcon = "/i[@class='ion potr-client-assignment ion-plus-round']";

  public static final String saveUserButtonXpath = "//*[@class='btn btn-pri']";

  public static final String rolesAppUserListXpath = "//div[@class='td roles' and not(descendant::i)]";

  public static final String disableUserButtonXpath = "//*[@class='btn btn-link ng-scope' and descendant::span[contains(text(),'Disable')]]";

  public static final String undoUserButtonXpath = "//*[@class='user-status layout horizontal center-justified']//*[contains(@ng-click,'userentry.enableUser(userentry.u)')]";

  public static final String disabledUsersListXpath = "//*[@class='user-entry ng-scope ng-isolate-scope disabled-user']";

  public static final String userNameInputFieldXpath = "//input[@class='book ng-pristine ng-untouched ng-valid ng-not-empty ng-valid-maxlength']";

  public static final String userEmailInputFieldXpath = "//input[@class='book ng-pristine ng-untouched ng-valid ng-not-empty']";

  public static final String userResetPasswordButtonXpath = "//*[contains(@class,'btn btn-link reset-password-button')]";

  public static final String firstNameLastRegex = "^[a-zA-Z0-9]+\\s[a-zA-Z0-9]+$";

  public static final String resetPasswordBoxXpath = "//p[@class='status-message ng-binding']";

  public static final String clientAdminKeyIconActiveXpath = "//*[@class='ion ion-key active']";

  public static final String userCancelButtonXpath = "//*[contains(@ng-click ,'clearQueue')]";

  public WebElement getReturnToIdiom() {
    return ReturnToIdiom;
  }

  /**
   * @param strElement
   *          (createuserbutton, labelcreateuser)
   * @return
   * @author Shailesh Shah
   * @since 26/04/2016
   */
  public boolean isVisible(String strElement) {

    boolean bStatus = true;

    WebDriverWait wait = new WebDriverWait(driver, 0);
    wait.ignoring(NoSuchElementException.class);

    try {
      switch (strElement.toLowerCase()) {

      case "createuserbutton":
        wait.until(ExpectedConditions.visibilityOf(createNewUser));
        break;
      case "labelcreateuserincreateuserdialogue":
        wait.until(ExpectedConditions.visibilityOf(label_CreateUser));
        break;
      case "returnToIdiom":
        wait.until(ExpectedConditions.visibilityOf(ReturnToIdiom));
        break;
      case "userEditPanel":
    	  
          wait.until(ExpectedConditions.visibilityOf(expandedUserArea));
          break;
        
      case "disableduser":
    	  wait.until(ExpectedConditions.visibilityOf(disabledUser));
          break;
      case "resetpassword":
    	  wait.until(ExpectedConditions.visibilityOf(ResetPwdButton));
          break;
      case "disablebutton":
    	  wait.until(ExpectedConditions.visibilityOf(DisableButton));
          break;
    	  
    	  
      }
    } catch (Exception e) {
      bStatus = false;
      e.printStackTrace();
    }
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    return bStatus;
  }

  /**
   * Click on element
   * 
   * @param strCase
   *          (createuser)
   * @author shakava
   * @since 24/4/2016
   */
  public void func_click(String strCase) {
    try {
      switch (strCase) {

      case "createuser":
        createNewUser.click();
        break;

      }
    } catch (Exception e) {
      System.out.println("Problem occured in func_click function");
      e.printStackTrace();
    }
  }

  /***************** IDIOM 2.3 End ***************/

  /**
   * func_ClickElement(String element) This method is used to click on an element Created By: Rohan
   * Macwan Created On:24 November 2015 Modified By | Description of Modification:
   */
  public void func_ClickElement(String element) throws InterruptedException {

    switch (element) {

    case "AdminAccess":
      MenuIcon.click();
      Thread.sleep(3000);
      AdminAccess.click();
      break;

    case "logout":
      Actions act = new Actions(driver);
      act.moveToElement(MenuIcon);
      Thread.sleep(3000);
      MenuIcon.click();
      System.out.println("Inside Logout");
      Thread.sleep(3000);
      LogoutIcon.click();
      break;

    case "FirstEditButton":
      Thread.sleep(3000);
      for (WebElement Edit : EditLst) {
        Edit.click();
        break;
      }
      break;

    case "FirstNameTextBox":
      System.out.println(FirstNameTextBox.getText());
      CustomReporter.log("Name Textbox Value - " + FirstNameTextBox.getText());
      Thread.sleep(3000);
      FirstNameTextBox.click();
      break;

    case "FirstSaveButton":
      Thread.sleep(3000);
      FirstSaveButton.click();
      break;

    case "Logout":
      MenuIcon.click();
      Thread.sleep(3000);
      link_Logout.click();
      break;

    case "Bulk Close Icon":
      Thread.sleep(3000);
      BulkCloseIcon.click();
      break;

    case "Bulk Icon Click":
      Thread.sleep(3000);
      BulkClientIcon.click();
      break;

    case "Return To Idiom":
      ReturnToIdiom.click();
      break;

    case "Idiom Logo":
      Idiom_Logo.click();
      break;

    case "Sort":
      Sort_DrpDown.click();
      break;

    case "Date Added":
      Select dropdown = new Select(Sort_DrpDown);
      dropdown.selectByVisibleText("Date Added");
      break;

    case "Close Header Row":
      Header_CloseIcon.click();
      break;

    case "DisableButton":
      DisableButton.click();
      break;

    case "Save Button":
      SaveButton.click();
      break;

    case "Cancel Button":
      cancelButton.click();
      break;

    case "Assign Or Unassign Clients":
      for (WebElement cl : AssignOrAnassignClientsLst) {
        cl.click();
        Thread.sleep(1000);
      }
      break;

    case "Reset Password":
      ResetPwdButton.click();
      break;

    case "Undo Button":
      UndoButton.click();
      break;

    case "DisabledUserEdit":
      for (WebElement edit : DisabledUser_Editlst) {
        edit.click();
        break;
      }
      break;

    case "Enable Save":
      Enable_SaveButton.click();
      break;

    case "BulkAsssignmnet AssignButton":
      bulkAsssignmnet_AssignButton.click();
      break;

    case "BulkAsssignmentCancelButton":
      cancelBulkClientAssignButton.click();
      break;

    case "userSearch":
      SearchTextBoxForSequence.get(0).click();
      break;
    }

  }

  /**
   * func_selectUsers(int count, WebDriver driver) This method selects the users depending on the
   * number passed Created By: Amrutha Nair Created On:24 November 2015 Modified By | Description of
   * Modification:
   */

  public void func_selectUsers(int count) throws InterruptedException {
    for (int i = 1; i <= count; i++) {
      Thread.sleep(1000);
      ListOfCheckBoxesForAllUsers.get(i).click();
    }
  }

  /**
   * func_ElementExist(String elementName) This method checks whether the element is existing
   * Created By: Amrutha Nair Created On:24 November 2015 Modified By | Description of Modification:
   */

  public boolean func_ElementExist(String elementName) throws Exception {
    boolean found = false;
    switch (elementName) {

    case "Bulk Client Icon":
      found = BaseClass.rm.webElementExists(list_bulk_ClientIcon);
      break;

    case "Users List":
      found = BaseClass.rm.webElementExists(List_Users);
      break;

    case "Validation Message For BlankName":
      found = BaseClass.rm.webElementExists(ValidationMessageForBlankName);
      break;
    case "Selected User Count":
      found = BaseClass.rm.webElementExists(SelectedUserCount);
      break;
    case "Create User Button":
      found = BaseClass.rm.webElementExists(CreateUserButton);
      break;

    case "EDit Panel for First User":
      found = BaseClass.rm.webElementExists(EditPanelPresent_FirstUser);
      break;

    case "Manage Team":
      found = BaseClass.rm.webElementExists(ManageTeam);
      break;

    case "Add Users Overlay Exist":
      found = BaseClass.rm.webElementExists(AddUsersOverlay);
      break;

    case "Selected Users":
      found = BaseClass.rm.webElementExists(lst_SelectedUsers);
      break;

    case "Header Close Icon":
      found = BaseClass.rm.webElementExists(Header_CloseIcon_Exist);
      break;

    case "Search Icon":
      found = BaseClass.rm.webElementExists(Search_Icon_exist);
      break;
    case "Search TextBox":
      found = BaseClass.rm.webElementExists(SearchTextBox_Exist);
      break;
    case "Bulk Client Assignment DropDown Open":
      found = BaseClass.rm.webElementExists(Lst_Bulk_Client_Open);
      break;
    case "ListKeyIcons":
      found = BaseClass.rm.webElementExists(ListKeyIcons);
      break;

    case "ListAllUsersEmailAddress":
      found = BaseClass.rm.webElementExists(ListAllUsersEmailAddress);
      break;
    case "ListEditButtons":
      found = BaseClass.rm.webElementExists(ListEditButtons);
      break;

    case "ListBlockedUsersIcons":
      found = BaseClass.rm.webElementExists(ListBlockedUsersIcons);
      break;

    case "ListSuperAdminUsersIcons":
      found = BaseClass.rm.webElementExists(ListSuperAdminUsersIcons);
      break;

    case "ListClientAdminUsersIcons":
      found = BaseClass.rm.webElementExists(ListClientAdminUsersIcons);
      break;

    case "ListNormalUsersIcons":
      found = BaseClass.rm.webElementExists(ListNormalUsersIcons);
      break;

    case "DisplayedUserCount":
      found = BaseClass.rm.webElementExists(DisplayedUserCount);
      break;

    case "SearchTextBoxForSequence":
      found = BaseClass.rm.webElementExists(SearchTextBoxForSequence);
      break;

    case "ListGridHeaderRowElements":
      found = BaseClass.rm.webElementExists(ListGridHeaderRowElements);
      break;

    case "UsersTextAtTheTop":
      found = BaseClass.rm.webElementExists(UsersTextAtTheTop);
      break;

    case "NameElementInGridHeaderRow":
      found = BaseClass.rm.webElementExists(NameElementInGridHeaderRow);
      break;
    case "ListDisabledButtons":
      found = BaseClass.rm.webElementExists(ListDisabledButtons);
      break;

    case "ListBlockedUsers":
      found = BaseClass.rm.webElementExists(ListBlockedUsers);
      break;
    case "Error Message":
      found = BaseClass.rm.webElementExists(ErrorMsgLst);
      break;

    case "Reset Password Disabled":
      found = BaseClass.rm.webElementExists(DisabledResetPwdst);
      break;

    case "Disable Account Button Disabled?":
      found = BaseClass.rm.webElementExists(DisabledDisableAccntst);
      break;

    case "Bulk Assignment Icon MouseOver":
      Actions builder1 = new Actions(driver);
      builder1.moveToElement(SearchTextBoxForSequence.get(0)).build().perform();
      Thread.sleep(5000);
      builder1.moveToElement(BulkClientIcon).build().perform();
      found = BaseClass.rm.webElementExists(Lst_Bulk_Client_Open);
      break;
    case "User Admin Page Exists":
      found = BaseClass.rm.webElementExists(UserAdminPageExist);
      break;
    case "Undo Button Exists":
      found = BaseClass.rm.webElementExists(UndoButtonlst);
      break;

    case "Reset Password Messagebox":
      found = BaseClass.rm.webElementSync((ResetPasswordMessageBox), "visibility");

      break;

    case "Reset Password Message":
      found = BaseClass.rm.webElementSync((ResetPasswordMessage), "visibility");

      break;

    case "AssignedClients":
      found = BaseClass.rm.webElementExists(AssignedClients);
      break;

    case "ClientsInEditPAnel":
      found = BaseClass.rm.webElementExists(ClientsInEditPAnel);
      break;
    case "Bulk assignment Clients Present":
      found = BaseClass.rm.webElementExists(blkAssignmentClients);
      break;

    case "Internal ServerError":

      found = BaseClass.rm.webElementExists(internal_ServerError);
      break;

    case "Mouseover On Help":
      Actions builder2 = new Actions(driver);
      builder2.moveToElement(Help_ICon).build().perform();
      found = BaseClass.rm.webElementExists(help_DrpDownOpen);
      break;
    case "Name Ascending Icon Exists":
      found = BaseClass.rm.webElementExists(name_AcendingExist);
      break;
    case "Name Descending Icon Exists":
      found = BaseClass.rm.webElementExists(name_descendingExist);
      break;

    case "Date Ascending Icon Exists":
      found = BaseClass.rm.webElementExists(Date_AcendingExist);
      break;
    case "Date Descending Icon Exists":
      found = BaseClass.rm.webElementExists(Date_descendingExist);
      break;

    case "Email Ascending Icon Exists":
      found = BaseClass.rm.webElementExists(Email_AcendingExist);
      break;
    case "Email Descending Icon Exists":
      found = BaseClass.rm.webElementExists(Email_descendingExist);
      break;
    case "Number of Clients Ascending Icon Exists":
      found = BaseClass.rm.webElementExists(Clients_AcendingExist);
      break;
    case "Number of Clients Descending Icon Exists":
      found = BaseClass.rm.webElementExists(Clients_descendingExist);
      break;

    case "Role Ascending Icon Exists":
      found = BaseClass.rm.webElementExists(Role_AcendingExist);
      break;
    case "Role Descending Icon Exists":
      found = BaseClass.rm.webElementExists(Role_descendingExist);
      break;

    }

    return found;

  }

  /**
   * func_ClearText() This method is used to clear the text of Name Textbox Created By: Rohan Macwan
   * Created On:24 November 2015 Modified By | Description of Modification: By Rohan Macwan on
   * 04-Dec-2015. Added Switch Case
   */
  public void func_ClearText(String elementName) throws InterruptedException {

    switch (elementName) {

    case "FirstNameTextBox":
      FirstNameTextBox.clear();
      break;

    case "SearchTextBox":
      SearchTextBox.clear();
      break;

    }

  }

  /**
   * func_AssignClients(int Position,int Count, WebDriver driver, String Case) This method assigns
   * clients and save checking on case Created By: Amrutha Nair Created On:26 November 2015 Modified
   * By | Description of Modification:
   */
  public boolean func_AssignClients(int Position, int Count, String Case, String ClientNames[])
      throws InterruptedException {

    boolean status = false;
    switch (Case) {

    case "Specific Number of Clients":
      int i = 1;
      int j = 1;
      for (WebElement User : UsersPresent) {

        // Get number of clients assigned
        String ClientPresent1 = driver.findElement(
            By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + i
                + "]//*[@class='td centered']/*[@class='book ng-binding']")).getText();
        int clPresent1 = Integer.parseInt(ClientPresent1);

        // Click on 'Edit ' button for the user at given position
        driver.findElement(
            By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + i
                + "]//*[@class='btn btn-link edit ng-scope']")).click();
        Thread.sleep(3000);
        // Check the number of clients present for the user and assign the clients as per provided
        // List <WebElement> allClients
        // =driver.findElements(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[contains(@class,'ng-binding ng-scope')]"));

        List<WebElement> notSelectedClients = driver.findElements(By
            .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + i
                + "]//*[@class='ng-binding ng-scope']"));
        // List <WebElement> selectedClients
        // =driver.findElements(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[@class='ng-binding ng-scope assigned']"));

        if (notSelectedClients.size() >= Count) {

          for (int k = 0; k < Count; k++) {
            driver.findElement(
                By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + i
                    + "]//*[@class='ng-binding ng-scope'][" + j
                    + "]//*[@class='ion ion-plus-round']")).click();
            j = j + 1;
          }

          // Click on Save

          driver.findElement(
              By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + i
                  + "]//*[@class='btn btn-pri']")).click();

          Thread.sleep(3000);
          String ClientPresent2 = driver.findElement(
              By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + i
                  + "]//*[@class='td centered']/*[@class='book ng-binding']")).getText();

          int clPresent2 = Integer.parseInt(ClientPresent2);
          if (clPresent2 == clPresent1 + Count) {
            status = true;

          }
          break;
        } else {
          driver.findElement(
              By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + i
                  + "]//*[@class='btn btn-pri']")).click();

          Thread.sleep(3000);
        }

        i = i + 1;

      }
      break;

    case "Only Save":

      String ClientPresent1 = driver
          .findElement(
              By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[@class='book ng-binding']"))
          .getText().split(" Client")[0];
      int clPresent1 = Integer.parseInt(ClientPresent1);

      driver
          .findElement(
              By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[@class='btn btn-link edit ng-scope']"))
          .click();
      Thread.sleep(3000);

      // Click on Save
      driver
          .findElement(
              By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[@class='btn btn-pri']"))
          .click();
      String ClientPresent2 = driver
          .findElement(
              By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[@class='book ng-binding']"))
          .getText().split(" Client")[0];
      int clPresent2 = Integer.parseInt(ClientPresent2);

      if (clPresent1 == clPresent2) {
        status = true;

      }
      break;
    case "Send Client Names And Save":

      for (int k = 0; k < ClientNames.length; k++) {
        int l = 1;
        for (WebElement Client : ListOfClientsInBulkAssignmentCallOut) {
          String cl = Client.getText();

          if (cl.toLowerCase().contains(ClientNames[k].toLowerCase())) {
            // Client.click();
            driver.findElement(By.xpath("//*[@class='client-search-select-list']//li[" + l + "]"))
                .click();
            CustomReporter.log("Client " + cl + " is clicked");
            break;
          }

          l++;
        }
      }
      AssignButton.click();
      break;
    case "Bulk Assignmnet_No Save":

      int m = 1;
      int n = 1;
      String[] client = new String[10];
      for (WebElement User : UsersPresent) {
        // Click on 'Edit ' button for the user at given position
        driver.findElement(
            By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + m
                + "]//*[@class='btn btn-link edit ng-scope']")).click();
        Thread.sleep(3000);
        // Check the number of clients present for the user and assign the clients as per provided
        // List <WebElement> allClients
        // =driver.findElements(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[contains(@class,'ng-binding ng-scope')]"));

        List<WebElement> notSelectedClients = driver.findElements(By
            .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + m
                + "]//*[@class='ng-binding ng-scope']"));
        // List <WebElement> selectedClients
        // =driver.findElements(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[@class='ng-binding ng-scope assigned']"));

        if (notSelectedClients.size() >= Count) {

          for (int k = 0; k < Count; k++) {
            client[k] = driver.findElement(
                By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + m
                    + "]//*[@class='ng-binding ng-scope'][" + n + "]")).getText();
            System.out.println("Client Edit Panel:" + client[k]);
            n = n + 1;
          }
          break;

        }
        m = m + 1;

      }
      Thread.sleep(3000);
      driver.findElement(
          By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + m
              + "]//input[@ng-click='selectUser(u)']")).click();

      Thread.sleep(3000);
      BulkClientIcon.click();
      for (int l = 1; l <= client.length; l++) {
        driver.findElement(
            By.xpath("//*[@class='dropdown-box assign-clients-box open']//*[contains(text(),'"
                + client[l] + "')]")).click();
      }
      CustomReporter.log("The clients are selected in bulk assignmnet drop down");
      CanCel_BulkAssignment.click();
      CustomReporter.log("The Cancel button is clicked form the bulk assignmnet drop down");

      // Checking whether

      break;

    case "Unassign AllClients":

      // Click on 'Edit ' button for the user at given position
      driver
          .findElement(
              By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[@class='btn btn-link edit ng-scope']"))
          .click();
      Thread.sleep(3000);

      List<WebElement> AssignedClients = driver
          .findElements(By
              .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][1]//*[@class='ng-binding ng-scope assigned']/*[@class='ion ion-close-round']"));

      for (WebElement AssigndCl : AssignedClients) {
        AssigndCl.click();

        Thread.sleep(1000);
      }

    }
    return status;

  }

  /**
   * func_VerifyPageComponants(String User) This method verifies page components depending on user
   * Created By: Amrutha Nair Created On:26 November 2015 Modified By | Description of Modification:
   */
  public boolean func_VerifyPageComponants(String User) {
    boolean status = true;

    if (BaseClass.rm.webElementExists(ManageTeam)) {
      CustomReporter.log("The page header contains heading as Manage Team");
    } else {
      CustomReporter.errorLog("Manage Team is not present in header");
      status = false;
    }

    if (BaseClass.rm.webElementExists(ReturnToIdiomLst)) {

      CustomReporter.log("The page header contains Return to Idiom Link");
    } else {
      CustomReporter.errorLog("Return to Idiom Link is not present in header");
      status = false;
    }

    if (BaseClass.rm.webElementExists(HeaderLst)) {

      CustomReporter.log("The User Admin page contains header row ");
    } else {
      CustomReporter.errorLog("The User Admin page does not contain header row ");
      status = false;
    }

    if (BaseClass.rm.webElementExists(UsersPresent)) {

      CustomReporter.log("The User Admin page contains users' rows user panel");
    } else {
      CustomReporter.errorLog("The User Admin page does not contain users' rows in user panel ");
      status = false;
    }

    if (User.contains("Super Admin User")) {
      if (BaseClass.rm.webElementExists(AddUserButtonLst)) {

        CustomReporter
            .log("The User Admin page contains 'Add User button' in case of super admin user");
      } else {
        CustomReporter
            .errorLog("The User Admin page does not  contain 'Add User button' in case of super admin user ");
        status = false;
      }
    }

    if (User.contains("Client Admin User")) {
      if (BaseClass.rm.webElementExists(AddUserButtonLst)) {

        CustomReporter
            .errorLog("The User Admin page contains 'Add User button' in case of Client Admin User");
        status = false;
      } else {
        CustomReporter.log("The User Admin page correctly does not  contain 'Add User button'");

      }
    }

    return status;

  }

  /**
   * func_SortClients This method is used to sort the clients Created By: Rohan Macwan Created On:26
   * November 2015 Modified By | Description of Modification:
   */
  public boolean func_SortClients(String Case) throws InterruptedException, ParseException {
    boolean status = false;
    /*
     * for (int i=0; i<ListOfClientsInBulkAssignmentCallOut.size(); i++) {
     * System.out.println(ListOfClientsInBulkAssignmentCallOut.get(i).getText()); }
     */

    ArrayList<String> BulkAssignmentClients = new ArrayList<String>();

    switch (Case) {
    case "Clients In Bulk Assignment Call Out":
      for (WebElement Client : ListOfClientsInBulkAssignmentCallOut) {
        BulkAssignmentClients.add(Client.getText());
      }
      status = BaseClass.rm.isSorted(BulkAssignmentClients, "Name Ascending");
      break;

    }

    return status;

  }

  /**
   * func_PerformClientAssignment(int Count, WebDriver driver, String Case) This method perform
   * client assignment and checks the key icon is coming on saving the same Created By: Amrutha Nair
   * Created On:26 November 2015 Modified By | Description of Modification:
   */
  public boolean func_CheckClientAdminFunctionality(int Count, String Case)
      throws InterruptedException {

    boolean status = false;
    switch (Case) {
    case "Client Assignment And Save":
      int i = 1;
      int j = 1;
      for (WebElement User : UsersPresent) {
        // Click on 'Edit ' button for the user at given position
        // ////
        // driver.findElement(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope']["+i+"]//*[@class='btn btn-link edit ng-scope']")).click();
        EditLst.get(i - 1).click();
        CustomReporter
            .log("The user has clicked on edit button foe the user at " + i + " position");

        Thread.sleep(3000);
        // List <WebElement> EditPanel=
        // driver.findElements(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope']["+i+"]//*[@class='client-search-select-list']"));
        if (BaseClass.rm.webElementExists(EditSelectionBox)) {
          CustomReporter.log("The edit panel is  coming for the user at " + i
              + " position on clicking on 'Edit' button");
          // /// List <WebElement> NoKeyIconSelected=
          // driver.findElements(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope']["+i+"]//*[@class='ion ion-key']"));

          if (Count <= NoKeyIconSelected.size()) {

            for (WebElement Client : NoKeyIconSelected) {
              Client.click();
              if (j == Count) {
                // Save the same
                // ///driver.findElement(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope']["+i+"]//*[@class='btn btn-pri']")).click();
                SaveButtonlst.get(i - 1).click();
                CustomReporter.log("The user has clicked on the 'Save ' button");
                Thread.sleep(3000);
                break;
              }
              j = j + 1;

            }
            // Checking the key icon for user after saving

            List<WebElement> KeyIconPresent = driver.findElements(By
                .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + i
                    + "]//*[@class='ion ion-key ng-scope']"));
            if (BaseClass.rm.webElementExists(KeyIconPresent)) {
              CustomReporter.log("The key icon is present for the client admin user");
              status = true;
            }
            break;
          }
        } else {
          CustomReporter.errorLog("The edit panel is not coming for the user at " + i
              + " position on clicking on 'Edit' button");
        }
        i = i + 1;
      }

      Thread.sleep(3000);
      break;
    case "Client Icon Removal":
      int k = 1;
      boolean flag = false;
      int l = 1;
      for (WebElement User : UsersPresent) {
        List<WebElement> ClientAdminUserLst = driver.findElements(By
            .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + k
                + "]//*[@title='clientAdmin']"));

        if (BaseClass.rm.webElementExists(ClientAdminUserLst)) {

          flag = true;
          // Click on 'Edit ' button for the clientAdminUser
          driver.findElement(
              By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + k
                  + "]//*[@class='btn btn-link edit ng-scope']")).click();
          CustomReporter.log("The user has clicked on edit button foe the user at " + k
              + " position which is a client admin user");
          Thread.sleep(3000);
          List<WebElement> EditPanel = driver.findElements(By
              .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + k
                  + "]//*[@class='client-search-select-list']"));
          if (BaseClass.rm.webElementExists(EditPanel)) {
            CustomReporter.log("The edit panel is  coming for the user at " + k
                + " position on clicking on 'Edit' button");
            List<WebElement> KeyIconSelectedlst = driver
                .findElements(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + k
                    + "]//*[@class='ng-binding ng-scope assigned']/*[@class='ion ion-key active']"));
            for (WebElement keyicon : KeyIconSelectedlst) {
              keyicon.click();
              Thread.sleep(1000);

            }
            // Save the same
            driver.findElement(
                By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + k
                    + "]//*[@class='btn btn-pri']")).click();

            Thread.sleep(3000);

            // Verify whether key icon is presnet with the user
            if (!BaseClass.rm.webElementExists(ClientAdminUserLst)) {
              CustomReporter
                  .log("The user changed to normal user on removing all client assinments frm it");
              status = true;
            } else {
              CustomReporter
                  .errorLog("The user is NOT changed to normal user on removing all client assinments frm it");

            }

          } else {
            CustomReporter.errorLog("The edit panel is not coming for the user at " + k
                + " position on clicking on 'Edit' button");

          }
          break;
        }
        k = k + 1;

      }
      if (!flag) {
        CustomReporter.errorLog("	There is NO client admin users to check this functionality");

      }
      break;

    case "ClientAdminUser_AdminAccess":
      int x = 1;

      for (WebElement User : UsersPresent) {
        List<WebElement> AdminUsers = driver.findElements(By
            .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + x
                + "]//*[contains(@title,'clientAdmin') or contains(@title,'userAdmin')]"));
        if (BaseClass.rm.webElementExists(AdminUsers)) {

          x = x + 1;
        } else {
          EditLst.get(x - 1).click();
          CustomReporter.log("The user has clicked on Edit button for the user at " + x
              + "'th location");
          Thread.sleep(3000);

          if (BaseClass.rm.webElementExists(AssignButtonLst)) {
            AssignButtonLst.get(0).click();

          }
          if (!BaseClass.rm.isClickable(NoKeyIconSelected.get(0))) {
            status = true;
          }
          break;

        }

      }
      break;
    }
    return status;

  }

  /*
   * func_Verify_HelpContent(String HelpContent, WebDriver driver) This method verifies help content
   * Created By: Amrutha Nair Created On:30 November 2015 Modified By | Description of Modification:
   */
  public boolean func_Verify_HelpContent(String HelpContent) throws InterruptedException {

    boolean status = true;

    Help_ICon.click();
    Thread.sleep(1000);

    if (Help_Header.getText().contentEquals("Help")) {
      CustomReporter.log(" The heading in the help content box is proper");
    } else {
      CustomReporter
          .errorLog("The content in help content box is NOT proper. In help content box , heading is:'"
              + Help_Header.getText() + "', not 'Help'");
      status = false;
    }

    if (Help_Content.getText().contentEquals(HelpContent)) {
      CustomReporter.log("The content in help content box is matching as expected");
    } else {
      CustomReporter
          .errorLog("The content in help content box is NOt matching with expected. In help content box , content given is:'"
              + Help_Content.getText() + "'");
      status = false;
    }

    if (Help_Button.getText().contentEquals("HELP CENTER")) {
      CustomReporter.log(" The Help Center button in the help content box is proper");
    } else {
      CustomReporter
          .errorLog("The Help Center button  in help content box is NOT proper. In help content box , heading is:'"
              + Help_Button.getText() + "', not 'Help Center'");
      status = false;
    }

    return status;

  }

  /*
   * func_SortUsers_Check(String Sort, String Case) This method verifies Sort Functionality content
   * Created By: Amrutha Nair Created On:30 November 2015 Modified By | Description of Modification:
   */
  public boolean func_SortUsers_Check(String Sort, String Case) throws Exception {

    boolean status = true;
    switch (Case) {
    case "Verify Sort Drop Down":
      String[] sortItems = Sort.split(",");
      if (sortItems.length == SortItems.size()) {
        for (WebElement s : SortItems) {
          if (Arrays.asList(sortItems).contains(s.getText())) {
            CustomReporter.log(s.getText() + " is present in Sort drop down as expected");
          } else {
            CustomReporter.errorLog(s.getText() + " present in sort drop down is Not expected ");
            status = false;
          }
        }
      } else if (sortItems.length > SortItems.size()) {
        CustomReporter.errorLog("The sort drop down doesn't contain all expected sort items");
        status = false;
      } else {
        CustomReporter
            .errorLog("The sort drop down contain extra drop down item than expected ones");
        status = false;
      }

      break;

    case "Date Added":
      String[] UserDetails = func_CreateUserDetails("T");

      // Create a new user and verify whether the created user is coming at top
      if (func_CreateUser(UserDetails[0], UserDetails[1], 2, "Add New User")) {
        CustomReporter.log("The user '" + UserDetails[0] + "' has beemn created");

        // Select 'Sort ' by date added and check whether the created idiom is coming at top

        Thread.sleep(3000);
        Select dropdown = new Select(Sort_DrpDown);
        dropdown.selectByVisibleText("Date Added");
        CustomReporter.log("Clicked on 'Date Added'");

        // Check whether created user is coming at top
        if (FirstNameTextBox.getAttribute("value").contentEquals(UserDetails[0])) {
          CustomReporter.log("The newly added user is coming  on top");
        } else {
          CustomReporter.errorLog("The newly added user is not coming on top");
          status = false;
        }

      } else {
        CustomReporter.errorLog("The user didn't get created");
        status = false;
      }
      break;
    }

    return status;
  }

  /*
   * func_TypeSearchCriteria(String elementName) This method will put text in Search Textbox in
   * Client Assignment call out Created By: Rohan Macwan Created On:30 November 2015 Modified By |
   * Description of Modification:
   */

  public void func_TypeSearchCriteria(String ClientOrUserNM, String WhetherClientOrUser)
      throws InterruptedException, ParseException {
    switch (WhetherClientOrUser) {

    case "Client":
      SearchTextBoxOfClientAssignmentCallOut.click();
      SearchTextBoxOfClientAssignmentCallOut.sendKeys(ClientOrUserNM);
      break;
    case "User":
      SearchTextBox.click();
      SearchTextBox.sendKeys(ClientOrUserNM);
      break;
    case "bulkClient":
    	SearchTextBoxOfBulkAssignmentCallOut.click();
    	
    	SearchTextBoxOfBulkAssignmentCallOut.sendKeys(ClientOrUserNM);
         break;
    }

  }

  /**
   * func_SearchClientsInClientAssignmentCallOut(String elementName) This method will put text in
   * Search Textbox in Client Assignment call out Created By: Rohan Macwan Created On:01 December
   * 2015 Modified By | Description of Modification:
   */

  public boolean func_SearchClientsInClientAssignmentCallOut(String SearchCriteria)
      throws InterruptedException, ParseException {
    boolean status = true;

    for (WebElement Client : ListOfClientsInBulkAssignmentCallOut) {
      String cl = Client.getText();
      if (cl.toLowerCase().contains(SearchCriteria.toLowerCase())) {
        CustomReporter.log("Search Criteria '" + SearchCriteria + "' found in Client name '" + cl
            + "'");
      } else {
        CustomReporter.log("Search Criteria '" + SearchCriteria + "' not found in Client name '"
            + cl + "'");
        status = false;
      }

    }
    return status;
  }

  // /////////////////IDIOM 2.3//////////////////////////////
  /**
   * func_CreateUser(String Name, String EmailAddress, int Count, String Case) This method creates
   * user Created By: Amrutha Nair Created On:2 December 2015 Modified By | Description of
   * Modification:
   */
  public boolean func_CreateUser(String Name, String EmailAddress, int Count, String Case)
      throws Exception {

    // String[] Clients= new String[10];
    boolean status = true;
    AddUserButton.click();
    CustomReporter.log("The button 'Add User' is clicked");

    if (func_ElementExist("Add Users Overlay Exist")) {
      CustomReporter.log("The Add Users over lay is getting opened");

      switch (Case) {

      case "Add New User":
        Adduser_Name.sendKeys(Name);
        Adduser_Email.sendKeys(EmailAddress);
        Adduser_Create.click();
        CustomReporter
            .log("The user has entered User Name and email Address in Add User overlay and clicked on Create button");
        Thread.sleep(10000);
        int i = 0;
        if (AddUsers_ClientList.size() > 0) {
          for (WebElement Client : AddUsers_ClientList) {
            if (i == Count) {
              break;
            }
            Client.click();
            Thread.sleep(3000);
            i = i + 1;
          }
          CustomReporter.log("The clients are selected from Add User Overlay");
        } else {
          CustomReporter.log("No Clients present in add user client list panel to select");
        }

        Thread.sleep(3000);
        AddUser_AssignButton.click();
        CustomReporter.log("The user has clicked on 'Assign User' button in Add User overlay");
        break;
      // Case added by Rohan Macwan on 19-Jan-2016
      case "Add New User For Name Check":

        if (BaseClass.rm.webElementSync((Adduser_Name), "visibility")) {
          CustomReporter.log("Name Text Box is found");
        } else {
          CustomReporter.errorLog("Name Text Box is not found");
          status = false;
        }

        if (BaseClass.rm.webElementSync((Adduser_Email), "visibility")) {
          CustomReporter.log("Email Address Text Box is found");
        } else {
          CustomReporter.errorLog("Email Address Text Box is not found");
          status = false;
        }

        if (BaseClass.rm.webElementSync((Adduser_Create), "visibility")) {
          CustomReporter.log("Create Button is found");
        } else {
          CustomReporter.errorLog("Create Button is not found");
          status = false;
        }

        if (BaseClass.rm.webElementSync((Adduser_Cancel), "visibility")) {
          CustomReporter.log("Cancel Button is found");
        } else {
          CustomReporter.errorLog("Cancel Button is not found");
          status = false;
        }

        if (status == true) {
          Adduser_Name.sendKeys(Name);
          Adduser_Email.sendKeys(EmailAddress);
          Adduser_Create.click();
          CustomReporter
              .log("The user has entered User Name and email Address in Add User overlay and clicked on Create button");
          Thread.sleep(10000);
          int k = 0;

          if (BaseClass.rm.webElementSync((AddUser_AssignButton), "visibility")) {
            CustomReporter.log("Assign Button is found");
          } else {
            CustomReporter.errorLog("Assign Button is not found");
            status = false;
          }

          if (BaseClass.rm.webElementSync((AddUser_AssignOverlay_NameField), "visibility")) {
            CustomReporter.log("Name field on Assign Overlay is found");

            if (AddUser_AssignOverlay_NameField.getText().trim().toLowerCase()
                .equals(Name.trim().toLowerCase())) {
              CustomReporter
                  .log("Name provided on Create User Overlay matches with Name displayed on Assign Overlay");
            } else {
              CustomReporter
                  .errorLog("Name provided on Create User Overlay does not match with Name displayed on Assign Overlay");
              status = false;
            }
          } else {
            CustomReporter.errorLog("Name field on Assign Overlay is found");
            status = false;
          }

          if (status == true) {
            if (AddUsers_ClientList.size() > 0) {
              for (WebElement Client : AddUsers_ClientList) {
                if (k == Count) {
                  break;
                }
                Client.click();
                Thread.sleep(3000);
                i = k + 1;

              }
              CustomReporter.log("The clients are selected from Add User Overlay");
            } else {
              CustomReporter.log("No Clients present in add user client list panel to select");
            }

            Thread.sleep(3000);
            AddUser_AssignButton.click();
            CustomReporter.log("The user has clicked on 'Assign' button in Add User overlay");
          }

        }

        break;
      // case added by Rohan Macwan on 20-Jan-2016
      case "Add New User Click Cancel Button Instead of Assign":
        Adduser_Name.sendKeys(Name);
        Adduser_Email.sendKeys(EmailAddress);
        Adduser_Create.click();
        CustomReporter
            .log("The user has entered User Name and email Address in Add User overlay and clicked on Create button");
        Thread.sleep(10000);
        int j = 0;
        if (AddUsers_ClientList.size() > 0) {
          for (WebElement Client : AddUsers_ClientList) {
            if (j == Count) {
              break;
            }
            Client.click();
            Thread.sleep(3000);
            j = j + 1;

          }
          CustomReporter.log("The clients are selected from Add User Overlay");
        } else {
          CustomReporter.log("No Clients present in add user client list panel to select");
        }

        Thread.sleep(3000);

        BaseClass.rm.webElementSync(AdduserAssignOverlay_Cancel, "visibility");
        AdduserAssignOverlay_Cancel.click();
        CustomReporter.log("The user has clicked on 'Cancel' button in Add User overlay");
        break;
      }

    } else {
      CustomReporter
          .errorLog("The Add users overlay is NOT getting opened on clicking on the 'Add user Button");
      status = false;
    }

    return status;

  }

  /**
   * func_CreateUserDetails(String Name) This method returns unique name for new user and unique
   * email id as well Created By: Amrutha Nair Created On:2 December 2015 Modified By | Description
   * of Modification:
   */
  public String[] func_CreateUserDetails(String Name) {
    Date now = new Date();
    String[] UserDetails = new String[2];
    String timeNow = new SimpleDateFormat("MMM d, yyyy h:mm:ss a").format(now);
    timeNow = timeNow.replaceAll("\\s+", "");
    timeNow = timeNow.replaceAll(":", "");
    timeNow = timeNow.replaceAll(",", "");
    UserDetails[0] = Name + timeNow;

    UserDetails[1] = Name + timeNow + "@gmail.com";

    return UserDetails;

  }

  /**
   * func_CheckSearchFunctionality(String Keyword) This method verifies whether Search is working
   * fine Created By: Amrutha Nair Created On:2 December 2015 Modified By | Description of
   * Modification:
   */

  public boolean func_CheckSearchFunctionality(String keyword) throws InterruptedException {

    boolean status = true;
    SearchTextBox.clear();
    SearchTextBox.sendKeys(keyword);

    Thread.sleep(3000);
    for (int i = 1; i <= List_Users.size(); i++) {
      String appUserNameInputFieldXpath = usersListXpath + "[" + +i + "]" + userNameInputFieldXpath;
      String appUserEmailInputFieldXpath = usersListXpath + "[" + +i + "]"
          + userEmailInputFieldXpath;
      if (BaseClass.rm.isElementPresent(appUserNameInputFieldXpath)
          && BaseClass.rm.isElementPresent(appUserEmailInputFieldXpath)) {
        String name = driver.findElement(By.xpath(appUserNameInputFieldXpath))
            .getAttribute("value");
        String emailId = driver.findElement(By.xpath(appUserEmailInputFieldXpath)).getAttribute(
            "value");
        if (!(StringUtils.containsIgnoreCase(name, keyword) || StringUtils.containsIgnoreCase(
            emailId, keyword))) {
          status = false;
          CustomReporter
              .errorLog("Both Name and Email Id field is not  containing Search String for the row at "
                  + i + " position");
        }
      }
    }
    return status;
  }

  /**
   * func_Row_Components(String Case, int count) This method verifies the contnet in each row is
   * expected Created By: Amrutha Nair Created On:3 December 2015 Modified By | Description of
   * Modification:
   */

  public boolean func_Row_Components(String Case, int count) throws Exception {

    boolean status = true;
    switch (Case) {

    case "Header Row":
      if (func_ElementExist("Header Close Icon")) {
        CustomReporter.log("The close icon is existing at the first row");
      } else {
        status = false;
        CustomReporter.errorLog("The close icon is NOT existing in firt row");
      }
      int SelectedUser = Integer.parseInt(SelectedUsers.getText());
      if (func_ElementExist("Selected User Count")) {
        CustomReporter.log("The selected users count is  existing in header");
        if (SelectedUser == count) {
          CustomReporter
              .log("The Number of selected users in header row is matching with the actual selected users");
        } else {
          CustomReporter
              .errorLog("TheNumber of selected users in header row is NOT matching with the actual selected users");
          status = false;
        }
      } else {
        CustomReporter.errorLog("The selected users count is NOT existing in header");
        status = false;
      }

      if (func_ElementExist("Bulk Client Icon")) {
        CustomReporter.log("The bulk client assignment icon is existing at the header");
      } else {

        CustomReporter.errorLog("The bulk client assignment icon is NOT existing at the header");
        status = false;
      }
      break;

    case "Second Row":
      if (func_ElementExist("Search Icon")) {
        CustomReporter.log("Search icon is existing in second row");
      } else {
        CustomReporter.errorLog("The Search icon is NOT existing in second row");
        status = false;
      }

      if (func_ElementExist("Search TextBox")) {
        CustomReporter.log("Search text box is existing in second row");
      } else {
        CustomReporter.errorLog("The search text box is not existing in second row");
        status = false;
      }
      break;
    case "user Row":
      if (func_ElementExist("Users List")) {
        CustomReporter.log("The users are existing in result grid");

        if (List_Users.size() == ListAllUsersName.size()) {
          CustomReporter.log("The 'Name' is existing for all  users");
        } else {
          CustomReporter.errorLog("The name is not present for all users");
          status = false;
        }

        if (List_Users.size() == ListDate.size()) {

          CustomReporter.log("The 'Date' is existing for all  users");
        } else {
          CustomReporter.errorLog("The Date is not present for all users");
          status = false;
        }
        if (List_Users.size() == ListAllUsersEmailAddress.size()) {

          CustomReporter.log("The 'Email Address' is existing for all  users");
        } else {
          CustomReporter.errorLog("The Email Address is not present for all users");
          status = false;
        }

        if (List_Users.size() == ListCountClients.size()) {
          CustomReporter.log("The 'number of clients ' is existing for all  users");
        } else {
          CustomReporter.errorLog("The 'number of clients' link is not present for all users");
          status = false;
        }

        if (List_Users.size() == EditLst.size()) {
          CustomReporter.log("The 'Edit Link' is existing for all  users");
        } else {
          CustomReporter.errorLog("The 'Edit link is not present for all users");
          status = false;
        }
      }

      else {
        CustomReporter.errorLog("The users are not present in result grid");
        status = false;
      }

      break;

    case "Edit Mode":
      List<WebElement> CheckBox = driver.findElements(By
          .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + count
              + "]//input[@type='checkbox']"));
      if (BaseClass.rm.webElementExists(CheckBox)) {
        CustomReporter.log("The check box is existing for selected user");
      } else {
        CustomReporter.errorLog("The check box is NOT existing for selected user");
        status = false;
      }
      List<WebElement> name = driver.findElements(By
          .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + count
              + "]//input[@ng-model='userentry.temp.name']"));
      if (BaseClass.rm.webElementExists(name)) {
        CustomReporter.log("The Name is existing for selected user");
      } else {
        CustomReporter.errorLog("The Name is NOT existing for selected user");
        status = false;
      }
      List<WebElement> Email = driver.findElements(By
          .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + count
              + "]//input[@ng-model='userentry.u.emailAddress']"));
      if (BaseClass.rm.webElementExists(Email)) {
        CustomReporter.log("The Email is existing for selected user");
      } else {
        CustomReporter.errorLog("The Email is NOT existing for selected user");
        status = false;
      }
      List<WebElement> Date = driver.findElements(By
          .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + count
              + "]//*[@class='td']/*[@class='book ng-binding']"));
      if (BaseClass.rm.webElementExists(Date)) {
        CustomReporter.log("The Date is existing for selected user");
      } else {
        CustomReporter.errorLog("The Date is NOT existing for selected user");
        status = false;
      }

      List<WebElement> Count = driver.findElements(By
          .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + count
              + "]//*[@class='td centered']/*[@class='book ng-binding']"));
      if (BaseClass.rm.webElementExists(Count)) {
        CustomReporter.log("The client's count is existing for selected user");
      } else {
        CustomReporter.errorLog("The client's count is NOT existing for selected user");
        status = false;
      }

    }

    return status;
  }

  /**
   * func_RetrieveClientAdminsAndClick(String elementName) This method will fetch all type of Users
   * based on parameter and click its check boxes in User List Grid Created By: Rohan Macwan Created
   * On:01 December 2015 Modified By | Description of Modification:
   */

  public String[][] func_RetrieveUsersAndClick(String Case) throws InterruptedException,
      ParseException, ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    Connection c = null;
    c = DriverManager.getConnection(
        "jdbc:postgresql://idiomqapsql04.c1dw3w2xwiv0.us-east-1.rds.amazonaws.com/IDPSQL01",
        "psqladm", "psqladmqa04_92");
    Thread.sleep(10000);

    java.sql.Statement st = c.createStatement();
    java.sql.Statement st1 = c.createStatement();

    String Query = "";

    if (Case == "ClientAdmin") {
      Query = "SELECT users.id, users.name, users.email_address, group_members.user_client_id,client_id,group_members.group_id,groups.group_name FROM idiom.users INNER JOIN idiom.user_client ON users.id = user_client.user_id INNER JOIN group_members ON user_client.user_client_id = group_members.user_client_id INNER JOIN groups ON groups.group_id = group_members.group_id WHERE user_client.status='ACTIVE' AND groups.group_name = 'clientAdmin' AND client_id = 11 limit 4";
    } else if (Case == "GeneralUser") {
      Query = "SELECT users.id, users.name, users.email_address, group_members.user_client_id,client_id,group_members.group_id,groups.group_name FROM idiom.users INNER JOIN idiom.user_client ON users.id = user_client.user_id INNER JOIN group_members ON user_client.user_client_id = group_members.user_client_id INNER JOIN groups ON groups.group_id = group_members.group_id WHERE user_client.status='ACTIVE' AND user_client.user_id not in (Select user_id from Idiom.Group_members, Idiom.Groups where group_members.group_id= groups.group_id and group_name in ('clientAdmin', 'userAdmin') and group_members.status='ACTIVE') AND client_id = 11 limit 4";
    }

    ResultSet rs = st.executeQuery(Query);
    Thread.sleep(10000);
    int size = 0;
    ResultSet rs1 = st1.executeQuery(Query);

    while (rs1.next()) {
      ++size;
      // Get data from the current row and use it
    }

    String[][] users = new String[size][2];

    int k = 0;
    while (rs.next()) {
      int i = 0;

      users[k][0] = rs.getString(2).trim();
      users[k][1] = rs.getString(3).toString();

      for (WebElement User : ListAllUsersEmailAddress) {
        if (rs.getString(3).equals(ListAllUsersEmailAddress.get(i).getAttribute("value"))) {
          ListOfCheckBoxesForAllUsers.get(i).click();
          break;
        }
        i++;
      }
      k++;
    }
    rs.close();
    rs1.close();
    st.close();
    st1.close();
    c.close();

    return users;
  }

  /**
   * func_SearchForKeyIcon(String elementName) This method will find whether user list grid has
   * client key icon for a particular user Created By: Rohan Macwan Created On:02 December 2015
   * Modified By | Description of Modification:
   */

  public boolean func_SearchForKeyIcon(String[][] NameEmailAddress) throws InterruptedException,
      ParseException {
    boolean status = true;

    for (int i = 0; i < NameEmailAddress.length; i++) {
      int k = 0;
      for (WebElement User : ListAllUsersName) {
        String usr = User.getText();

        if ((usr.toLowerCase().contains(NameEmailAddress[i][0].toLowerCase()) && usr.toLowerCase()
            .contains(NameEmailAddress[i][1].toLowerCase()))) {
          List<WebElement> keyicons = driver.findElements(By
              .xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + k
                  + "]//*[@class='ion ion-key ng-scope']"));

          if (BaseClass.rm.webElementExists(keyicons)) {
            CustomReporter.log("Key Icon for User Name '" + NameEmailAddress[i][0]
                + "' found and Email Address '" + NameEmailAddress[i][1]
                + "' found in User List Grid");
          } else {
            CustomReporter.errorLog("Key Icon for User Name '" + NameEmailAddress[i][0]
                + "', Email Address '" + NameEmailAddress[i][1] + "' not found in User List Grid");
            status = false;
          }

        }

        k++;
      }

    }
    return status;
  }

  /**
   * func_CompareText() This method will compare text of the element Created By: Rohan Macwan
   * Created On:22 December 2015 Modified By | Description of Modification:
   */
  public boolean func_CompareText(String elementName, int data) throws InterruptedException {

    boolean status = false;
    switch (elementName) {

    case "UsersTextAtTheTop":
      if (UsersTextAtTop.getText().contentEquals("users")) {
        status = true;
      }
      break;

    case "NameElementInGridHeaderRow":

      if (NameInGridHeaderRow.getText().contains("Name")) {
        status = true;
      }
      break;

    case "Selected Users":

      if (data == Integer.parseInt(SelectedUsers.getText())) {
        status = true;
      }

      // case "ResetPasswordMessageInEditUserPanel":
      // if (ResetPasswordAdTimeInEditPanel.getText().substring(19).contentEquals("users"))
      // {
      // status=true;
      // }

    default:
      break;

    }
    return status;
  }

  public boolean func_CompareText(String elementName, String strTextToCompare)
      throws InterruptedException {

    boolean status = false;
    switch (elementName) {

    case "ResetPasswordMessageInEditUserPanel":
      if (ResetPasswordDateTimeInEditPanel.getText().substring(19).toLowerCase()
          .contentEquals(strTextToCompare.toLowerCase())) {
        CustomReporter.log("When it was set at that time system time was : "
            + strTextToCompare.toLowerCase());
        CustomReporter.log("The time which is shown on Edit User Panel is : "
            + ResetPasswordDateTimeInEditPanel.getText().substring(19).toLowerCase());
        status = true;
      } else {
        System.out.println("strTextToCompare.charAt(0) = " + strTextToCompare.charAt(0));
        System.out.println("strTextToCompare.charAt(0) = " + strTextToCompare.charAt(1));
        CustomReporter.errorLog("When it was set at that time system time was : "
            + strTextToCompare.toLowerCase());
        CustomReporter.errorLog("The time which is shown on Edit User Panel is : "
            + ResetPasswordDateTimeInEditPanel.getText().substring(19));
      }

    }
    return status;
  }

  /**
   * func_CheckWhetherAllCheckboxesByDefaultAreUnSelected This method is used to check whether by
   * default all checkboxes in User List Grid are unchecked Created By: Rohan Macwan Created On: 21
   * December 2015 Modified By | Description of Modification:
   */
  public boolean func_CheckWhetherAllCheckboxesByDefaultAreUnSelected() throws InterruptedException {

    int i = 0;
    boolean status = true;
    for (WebElement User : ListOfCheckBoxesForAllUsers) {

      if (User.isSelected()) {
        status = false;
        CustomReporter.errorLog("User + " + ListAllUsersEmailAddress.get(i).getText()
            + " is selected by default when page got loaded");
      }
      i++;
    }
    return status;
  }

  public String[] func_GetAssignedClients() throws InterruptedException {

    String[] clients = new String[20];
    int i = 0;
    // Click on 'edit'
    driver.findElement(By.xpath("//*[@class='btn btn-link edit ng-scope']")).click();
    Thread.sleep(3000);
    for (WebElement Client : AssignedClients) {
      clients[i] = Client.getText();
      System.out.println("clients[i]:" + clients[i]);
      i = i + 1;
    }

    return clients;
  }
  
  //added by Sunil 28 May 2016
  public int func_GetAssignedClientscount() throws InterruptedException {

	    String[] clients = new String[20];
	    int i = 0;
	    // Click on 'edit'
	    //driver.findElement(By.xpath("//*[@class='btn btn-link edit ng-scope']")).click();
	    Thread.sleep(3000);
	    for (WebElement Client : AssignedClients) {
	      clients[i] = Client.getText();
	      System.out.println("clients["+i+"]:" + clients[i]);
	      i = i + 1;
	    }

	    return i-1;
	  }

  /**
   * func_CheckDisabledButtonPresentForClientAdmin This method checks whether Disable button is
   * present while editing user Created By: Rohan Macwan Created On: 21 December 2015 Modified By |
   * Description of Modification:
   */
  public boolean func_CheckDisabledButtonPresentForClientAdmin(int selectedUserRowIndex)
      throws InterruptedException {
    String appUserDisableButtonXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + disableUserButtonXpath;
    return BaseClass.rm.isElementPresent(appUserDisableButtonXpath);
  }

  /**
   * func_PerformAction(String Case) * This method performs various actions depending on Switch case
   * provided Created By:Abhishek Deshpande Created On:11 JAN 2016 Modified By | Description of
   * Modification: Modified On:
   */
  public void func_PerformAction(String element) throws InterruptedException {
    switch (element) {

    case "AddUserClick":
      AddUserButton.click();
      break;

    case "labelCreateUserExists":
      BaseClass.rm.elementExists(label_CreateUser);
      break;

    case "AddUserNameExists":
      BaseClass.rm.elementExists(Adduser_Name);
      break;

    case "EnterUserName":
      Adduser_Name.sendKeys("Test");
      break;

    case "AddUserEmailExists":
      BaseClass.rm.elementExists(Adduser_Email);
      break;

    case "AddUserCreateButtonExists":
      BaseClass.rm.elementExists(Adduser_Create);
      break;

    case "AddUserCancelButtonExists":
      BaseClass.rm.elementExists(Adduser_Cancel);
      break;

    case "AddUserCancelClick":
      Adduser_Cancel.click();
      break;

    }
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

  /**
   * func_ScrollUserList() This method will scroll down user list grid Created By: Rohan Macwan
   * Created On:11 January 2016 Modified By | Description of Modification:
   */

  public void func_ScrollUserList() throws InterruptedException, ParseException {

    // WebElement webelement = driver.findElement(By.xpath(strXpath));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0,15000);", ScrollBox);

  }

  /**
   * func_GetCoOrdinates() This method will scroll down user list grid Created By: Rohan Macwan
   * Created On:11 January 2016 Modified By | Description of Modification:
   */
  // Included the conditions for search box and user grid.
  public Point func_GetCoOrdinates(String webelement) throws InterruptedException, ParseException {
    Point P = null;
    switch (webelement.toLowerCase()) {

    case "headerrow":
      P = HeaderRow.getLocation();

      break;

    case "searchbar":
      P = SearchTextBox.getLocation();
      break;

    case "scrollbox":
      P = ScrollBox.getLocation();
      break;

    }
    return P;

  }

  /**
   * func_VerifyBGColor(String Case, String ExpectedColour) This method will check the BG colour for
   * the elemnt Created By: Amrutha NAir Created On: 15 January 2016 Modified By | Description of
   * Modification:
   */
  public boolean func_VerifyBGColor(int selectedUserRowIndex, String ExpectedColour) {
    boolean status = false;

    String appUserUndoButtonXpath = disabledUsersListXpath + "[" + selectedUserRowIndex + "]"
        + undoUserButtonXpath;
    if (BaseClass.rm.isElementPresent(appUserUndoButtonXpath)
        && ExpectedColour.contentEquals(driver.findElement(By.xpath(appUserUndoButtonXpath))
            .getCssValue("background-color"))) {
      status = true;
    }

    return status;
  }

  /**
   * func_VerifyResetPwdMessageformat() This method will check the reset password message format
   * Created By: Amrutha NAir Created On: 15 January 2016 Modified By | Description of Modification:
   */

  public boolean func_VerifyResetPwdMessageformat() {
    return ResetPwdMessage.getText().contains("Reset last sent at ");
  }

  /**
   * func_CheckTheVisibilityOfLastClientForEditedUser This method will check the visibility of the
   * Last client for the edited user Created By: Rohan Macwan Created On: 18 January 2016 Modified
   * By | Description of Modification:
   */
  public boolean func_CheckTheVisibilityOfLastClientForEditedUser() throws InterruptedException,
      Exception {

    boolean status = true;

    String strXpath = "(//header[@class='layout center-center horizontal wrap'])[1]/parent::div/footer/div[3]/div/div[1]/div[2]/ul/li";

    func_ClickEditUserByNumber(0);
    // driver.findElement(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" + i +
    // "]//*[@class='btn btn-link edit ng-scope']")).click();
    Thread.sleep(5000);

    List<WebElement> allClients = func_GetClientListByUserNumber(0);
    int iClientCount = allClients.size();
    System.out.println("Total clients " + iClientCount);

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",
        allClients.get(iClientCount - 1));

    if (BaseClass.rm.webElementSync(allClients.get(iClientCount - 1), "visibility")) {
      CustomReporter.log("Client - " + allClients.get(iClientCount - 1).getText() + " is visible");
    } else {
      System.out.println(BaseClass.rm.webElementSync(
          ListClientsInEditedUserInUserListGrid.get(iClientCount - 1), "visibility"));
      status = false;
    }

    return status;
  }

  /**
   * func_ClickEditUserByNumber This will click on edit button for a particular user Created By:
   * Rohan Macwan Created On: 18 January 2016 Modified By | Description of Modification:
   */
  public void func_ClickEditUserByNumber(int iUserOrder) {
    userList.get(iUserOrder).findElement(By.xpath("header/div[7]/button")).click();
  }

  /**
   * func_GetClientListByUserNumber This will give list of clients for a particular edited user
   * Created By: Rohan Macwan Created On: 18 January 2016 Modified By | Description of Modification:
   */
  public List<WebElement> func_GetClientListByUserNumber(int iUserOrder) {
    return userList.get(iUserOrder).findElements(By.xpath("footer/div[3]/div/div[1]/div[2]/ul/li"));
    // return clientList.get(iUserOrder);
  }

  /**
   * func_ClickEditUserByEmailId This will click on edit button for a particular user by its Email
   * Id Created By: Rohan Macwan Created On: 20 January 2016 Modified By | Description of
   * Modification:
   * 
   * @throws ParseException
   * @throws InterruptedException
   */
  public void func_ClickEditUserByEmailId(String UserEmailId) throws InterruptedException,
      ParseException {
    func_TypeSearchCriteria(UserEmailId, "User");
    func_ClickElement("FirstEditButton");
  }

  /**
   * func_GetSystemTime This will give system time in the format which is sent in parameter of this
   * method Created By: Rohan Macwan Created On: 22 January 2016 Modified By | Description of
   * Modification:
   * 
   * @throws ParseException
   * @throws InterruptedException
   */
  public String func_GetSystemTime(String DateTimeFormat) throws InterruptedException,
      ParseException {

    String strDateTime = "";
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat);
    strDateTime = sdf.format(cal.getTime()).toString();

    return strDateTime;
  }

  /**
   * func_VerifyElementReadonly(String strCase, int count, String StrValue) This method will verify
   * whether passed element is readonly Created By: Amrutha NAir Created On:29 January 2016 Modified
   * By | Description of Modification:
   */
  public boolean func_VerifyElementReadonly(String strCase, int count, String StrValue) {
    boolean status = false;
    switch (strCase) {
    case "Name":
      try {

        WebElement userName = ListAllUsersName.get(count);

        Thread.sleep(3000);
        userName.click();
        Thread.sleep(3000);

        userName.sendKeys(StrValue);

        if (userName.getAttribute("value").contains(StrValue)) {
          status = true;
        }
      } catch (Exception e) {
        String msg = e.getMessage();
        System.out.println(msg);
        if (msg.contains("Element is read-only")) {
          status = false;
          System.out.println("msg ::::" + msg);
        }

      }
      break;
    }
    return status;

  }

  public boolean func_VerifySortFunctionality(String strCase) throws ParseException {
    boolean status = false;
    ArrayList<String> data = new ArrayList<String>();
    switch (strCase) {
    case "Name Ascending":

      if (!BaseClass.rm.webElementExists(NameAscendingIconLst)) {
        name_AcendingIcon.click();
      }
      for (WebElement Name : NameLst) {
        // System.out.println("NAme Ascding:"+Name.getAttribute("value"));
        data.add(Name.getAttribute("value"));
      }
      // status=BaseClass.rm.isSorted(data,strCase);

      break;

    case "Name Descending":
      name_descendingIcon.click();
      for (WebElement Name : NameLst) {
        // System.out.println("NAme descding:"+Name.getAttribute("value"));
        data.add(Name.getAttribute("value"));
      }
      // status=BaseClass.rm.isSorted(data,strCase);

      break;

    case "Date Ascending":
      Date_AcendingIcon.click();
      for (WebElement date : DateLst) {
        data.add(date.getText());
        System.out.println(date.getText());
      }
      // status=BaseClass.rm.isSorted(data,strCase);

      break;

    case "Date Descending":
      Date_descendingIcon.click();
      for (WebElement date : DateLst) {
        data.add(date.getText());
        System.out.println(date.getText());
      }

      break;
    }
    status = BaseClass.rm.isSorted(data, strCase);
    return status;
  }

  public boolean func_VerifyApplicationDataWithDB(String strCase, String Query, String Connection,
      String UserName, String Password) {
    boolean status = false;
    try {
      Class.forName("org.postgresql.Driver");
      Connection c = null;
      c = DriverManager.getConnection(Connection, UserName, Password);
      // c =
      // DriverManager.getConnection("jdbc:postgresql://idiomqapsql04.c1dw3w2xwiv0.us-east-1.rds.amazonaws.com/IDPSQL01","psqladm",
      // "psqladmqa04_92");
      Thread.sleep(10000);
      System.out.println("Opened database successfully");
      java.sql.Statement st = c.createStatement();
      ResultSet rs = st.executeQuery(Query);

      switch (strCase) {

      case "Check Clients from DB":

        Thread.sleep(10000);

        while (rs.next()) {
          int i = 0;
          int flag = 0;
          for (WebElement Client : ListOfClientsInBulkAssignmentCallOut) {
            if (rs.getString(1).trim().equals(Client.getText().trim())) {
              flag = 1;
              CustomReporter.log("Client " + rs.getString(1)
                  + " found in Client Assignment call out");
              break;
            }
            i++;
          }
          if (flag == 0) {
            status = false;
            CustomReporter.errorLog("Client " + rs.getString(1)
                + " not found in Client Assignment call out");
            break;
          }

        }

        break;

      case "Verify Access Right for User":

        Thread.sleep(10000);
        int k = 0;
        rs.next();
        if (rs.getInt(1) != 0) {
          status = true;
        }

        break;
      case "Compare Client Names with DB":
        Thread.sleep(10000);

        System.out.println(Query);
        int i = 1;
        int DbRecordsCount = 0;
        for (WebElement User : List_Users) {
          while (rs.next())

          {
            if (i == 1) {
              EditLst.get(i - 1).click();
              // driver.findElement(By.xpath("//*[@class='user-entry ng-scope ng-isolate-scope'][" +
              // i + "]//*[@class='btn btn-link edit ng-scope']")).click();
              Thread.sleep(3000);
              int flag = 0;

              for (WebElement usrClient : ListClientsInEditedUserInUserListGrid) {
                if (rs.getString(1).toLowerCase().contains(usrClient.getText().toLowerCase())) {
                  CustomReporter.log("Client " + rs.getString(1)
                      + " fetched from DB is found in User List Grid for the edited user");
                  flag = 1;
                }

              }
              if (flag == 0) {
                status = false;
                CustomReporter.errorLog("Client " + rs.getString(1)
                    + " from DB is not found in User List Grid for the edited user");
              }
              flag = 0;
            }

            DbRecordsCount++;
          }
          i++;
          if (DbRecordsCount != ListClientsInEditedUserInUserListGrid.size()) {
            status = false;
            CustomReporter
                .errorLog("Number of Clients from DB and number of Clients in User List Grid for the edited user do not match");
          }
          if (i > 1) {
            break;
          }
        }
        break;

      case "Bulk Assignment Clients Comparison":
        Thread.sleep(10000);
        while (rs.next()) {
          int flag = 0;
          for (WebElement Client : ListOfClientsInBulkAssignmentCallOut) {

            if (rs.getString(1).trim().equals(Client.getText().trim())) {
              flag = 1;
              CustomReporter.log("Client " + rs.getString(1)
                  + " found in Client Assignment call out");
              break;
            }

          }
          if (flag == 0) {
            status = false;
            CustomReporter.errorLog("Client " + rs.getString(1)
                + " not found in Client Assignment call out");
            break;
          }

        }
        break;

      }

      rs.close();
      st.close();
      c.close();

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return status;

  }

  /**
   * This method is used to assign or unassign all clients for user
   * 
   * @author Vikram Hegde
   * @param assignClientsIndicator
   */
  public void assignUnAssignClientsForUser(boolean assignClientsIndicator) {
    for (int k = 1; k <= getListOfClientsInBulkAssignmentCallOut().size() + 1; k++) {
      String assignClientIconXpath = clientListForUserXpath + "[" + k + "]"
          + (assignClientsIndicator ? assignUserClientIcon : unAssignUserClientIcon);
      if (BaseClass.rm.isElementPresent(assignClientIconXpath)) {
        BaseClass.rm.clickWebElement(driver.findElement(By.xpath(assignClientIconXpath)));
        CustomReporter.log("Clicked Unassign client "
            + driver.findElement(By.xpath(clientListForUserXpath + "[" + k + "]")).getText()
            + " From user");
      }
    }
  }

  /**
   * This method is used to assign or unassign selected list of clients
   * 
   * @author Vikram Hegde
   * @param selectedClientRowIndices
   */
  public void assignUnAssignClientsForUser(List<Integer> selectedClientRowIndices,
      boolean assignClientsIndicator) {
    for (Integer selectedClientRowIndex : selectedClientRowIndices) {
      String assignClientIconXpath = clientListForUserXpath + "[" + selectedClientRowIndex + "]"
          + (assignClientsIndicator ? assignUserClientIcon : unAssignUserClientIcon);
      if (BaseClass.rm.isElementPresent(assignClientIconXpath)) {
        BaseClass.rm.clickWebElement(driver.findElement(By.xpath(assignClientIconXpath)));
      }
    }
  }

  /**
   * This method is used to assign client admin access selected list of clients
   * 
   * @author Vikram Hegde
   * @param selectedClientRowIndices
   */
  public void assignClientsAdminAccessForUser(List<Integer> selectedClientRowIndices) {
    for (Integer selectedClientRowIndex : selectedClientRowIndices) {
      String assignClientIconXpath = clientListForUserXpath + "[" + selectedClientRowIndex + "]"
          + assignUserClientIcon;
      if (BaseClass.rm.isElementPresent(assignClientIconXpath)) {
        BaseClass.rm.clickWebElement(NoKeyIconSelected.get(selectedClientRowIndex));
      }
    }
  }

  /**
   * This method is used to verify if selected clients are unassigned
   * 
   * @author Vikram Hegde
   * @param selectedClientRowIndices
   */
  public boolean verifyClientsUnassigned(List<Integer> selectedClientRowIndices) {
    boolean clientsUnassignedInd = false;
    for (Integer selectedClientRowIndex : selectedClientRowIndices) {
      String assignClientIconXpath = clientListForUserXpath + "[" + selectedClientRowIndex + "]"
          + assignUserClientIcon;
      if (BaseClass.rm.isElementPresent(assignClientIconXpath)) {
        clientsUnassignedInd = true;
      }
    }
    return clientsUnassignedInd;
  }

  /**
   * This method is used to click bulk assign or unassign clients
   * 
   * @author Vikram Hegde
   * @param numOfClientsToBeSelected
   *          Number of clients to be selected
   * @return
   * 
   */
  public List<String> bulkAssignAssignClients(int numOfClientsToBeSelected) {
    int i = 0;
    List<String> selectedClients = new ArrayList<String>();
    for (WebElement webElement : getClientListBulkAssign()) {
      i++;
      BaseClass.rm.clickWebElement(webElement);
      selectedClients.add(webElement.getText());
      if (i == numOfClientsToBeSelected)
        break;
    }
    return selectedClients;
  }

  /**
   * This method is used to verify selection of bulk clients
   * 
   * @author Vikram Hegde
   * @param numOfClientsToBeSelected
   *          Number of clients to be selected
   * @return
   * 
   */
  public boolean verifyBulkAssignClients(int numOfClientsToBeVerified) {
    boolean isClientSelectedWithSelectIcon = false;
    for (int i = 0; i < getClientListBulkAssign().size(); i++) {
      if (BaseClass.rm.isElementPresent(selectClientIconBulkAssignXpath)) {
        isClientSelectedWithSelectIcon = true;
        if (i == numOfClientsToBeVerified)
          break;
      }
    }
    return isClientSelectedWithSelectIcon;
  }

  /**
   * This method is used to select users who are having no clients assigned
   * 
   * @author Vikram Hegde
   * @param count
   * @throws InterruptedException
   * @author Vikram Hegde
   */
  public List<Integer> func_selectUsers(int count, int assignedNumberOfClients)
      throws InterruptedException {
    int selectedUsersCount = 0;
    List<Integer> selectedUsersIndices = new ArrayList<Integer>();
    for (int listIteratorCount = 1; listIteratorCount < getList_Users().size(); listIteratorCount++) {
      String userSelectCheckBoxXpath = usersListXpath + "[" + listIteratorCount + "]"
          + selectUserCheckbox;
      if (BaseClass.rm.isElementPresent(userSelectCheckBoxXpath)) {
        String numOfClientsForUserXpath = usersListXpath + "[" + listIteratorCount + "]"
            + numberOfClientsForUserXpath;
        if (BaseClass.rm.isElementPresent(numOfClientsForUserXpath)) {
          int numberOfClients = Integer.valueOf(driver.findElement(
              By.xpath(numOfClientsForUserXpath)).getText());
          if (numberOfClients == assignedNumberOfClients) {
            driver.findElement(By.xpath(userSelectCheckBoxXpath)).click();
            selectedUsersIndices.add(listIteratorCount);
            selectedUsersCount++;
          }
        }
      }
      if (selectedUsersCount >= count)
        break;
    }
    return selectedUsersIndices;
  }

  /**
   * This method is used to verify if no data is updated after bulk client assign close
   * 
   * @author Vikram Hegde
   * @param selectedUserIndices
   * @return
   */
  public boolean verifyNoDataUpdatesAfterBulkClientAssign(List<Integer> selectedUserIndices) {
    boolean noDataUpdatesInd = false;
    for (Integer selectedUserIndex : selectedUserIndices) {
      String numOfClientsForUserXpath = usersListXpath + "[" + selectedUserIndex + "]"
          + numberOfClientsForUserXpath;
      if (BaseClass.rm.isElementPresent(numOfClientsForUserXpath)) {
        int numberOfClients = Integer.valueOf(driver
            .findElement(By.xpath(numOfClientsForUserXpath)).getText());
        if (numberOfClients == 0) {
          noDataUpdatesInd = true;
        } else {
          noDataUpdatesInd = false;
        }
      }
    }
    return noDataUpdatesInd;
  }

  /**
   * Get user firstName dynamically from Users List
   * 
   * @author Vikram Hegde
   * @return
   */
  public String getFirstNameFromUsersList() {
    String firstName = null;
    for (int i = 1; i < getList_Users().size(); i++) {
      String appUserNameInputFieldXpath = usersListXpath + "[" + +i + "]" + userNameInputFieldXpath;
      if (BaseClass.rm.isElementPresent(appUserNameInputFieldXpath)
          && driver.findElement(By.xpath(appUserNameInputFieldXpath)).getAttribute("value")
              .matches(firstNameLastRegex)) {
        firstName = driver.findElement(By.xpath(appUserNameInputFieldXpath)).getAttribute("value")
            .split("\\s+")[0];
        break;
      }
    }
    return firstName;
  }

  /**
   * Get user lastName dynamically from Users List
   * 
   * @author Vikram Hegde
   * @return
   */
  public String getLastNameFromUsersList() {
    String lastName = null;
    for (int i = 1; i < getList_Users().size(); i++) {
      String appUserNameInputFieldXpath = usersListXpath + "[" + +i + "]" + userNameInputFieldXpath;
      if (BaseClass.rm.isElementPresent(appUserNameInputFieldXpath)
          && driver.findElement(By.xpath(appUserNameInputFieldXpath)).getAttribute("value")
              .matches(firstNameLastRegex)) {
        lastName = driver.findElement(By.xpath(appUserNameInputFieldXpath)).getAttribute("value")
            .split("\\s+")[1];
        break;
      }
    }
    return lastName;
  }

  /**
   * Get user email dynamically from Users List
   * 
   * @author Vikram Hegde
   * @return
   */
  public String getEmailFromUsersList() {
    String email = null;
    for (int i = 1; i < getList_Users().size(); i++) {
      String appUserEmailInputFieldXpath = usersListXpath + "[" + +i + "]"
          + userEmailInputFieldXpath;
      if (BaseClass.rm.isElementPresent(appUserEmailInputFieldXpath)
          && !StringUtils.isEmpty(driver.findElement(By.xpath(appUserEmailInputFieldXpath))
              .getAttribute("value"))) {
        email = driver.findElement(By.xpath(appUserEmailInputFieldXpath)).getAttribute("value");
        break;
      }
    }
    return email;
  }

  public void func_enteruserdetails(String user, String email, int numclicks) throws Exception {
    System.out.println("The fn_parameter user name is " + user + " and the email is " + email);
    Adduser_Name.click();
    Adduser_Name.sendKeys(user);
    Adduser_Email.click();
    Adduser_Email.sendKeys(email);
    for (int i = 1; i <= numclicks; i++) {
      Adduser_Create.click();
      Thread.sleep(200);
    }
  }

  /* added by Sunil 09 May 2016 */
  public boolean func_VerifyPageComponents(String User) {
    boolean status = true;

    if (BaseClass.rm.webElementExists(ReturnToIdiomLst)) {

      CustomReporter.log("The page header contains Return to Idiom Link");
    } else {
      CustomReporter.errorLog("Return to Idiom Link is not present in header");
      status = false;
    }

    if (BaseClass.rm.webElementExists(HeaderLst)) {

      CustomReporter.log("The User Admin page contains header row ");
    } else {
      CustomReporter.errorLog("The User Admin page does not contain header row ");
      status = false;
    }

    if (BaseClass.rm.webElementExists(UsersPresent)) {

      CustomReporter.log("The User Admin page contains users' rows user panel");
    } else {
      CustomReporter.errorLog("The User Admin page does not contain users' rows in user panel ");
      status = false;
    }

    if (User.contains("Super Admin User")) {
      if (BaseClass.rm.webElementExists(AddUserButtonLst)) {

        CustomReporter
            .log("The User Admin page contains 'Add User button' in case of super admin user");
      } else {
        CustomReporter
            .errorLog("The User Admin page does not  contain 'Add User button' in case of super admin user ");
        status = false;
      }
    }

    if (User.contains("Client Admin User")) {
      if (BaseClass.rm.webElementExists(AddUserButtonLst)) {

        CustomReporter
            .errorLog("The User Admin page contains 'Add User button' in case of Client Admin User");
        status = false;
      } else {
        CustomReporter.log("The User Admin page correctly does not  contain 'Add User button'");

      }
    }

    return status;

  }
  
  public boolean func_sortelement(String strCase) throws ParseException {
	    boolean status = false;
	    ArrayList<String> data = new ArrayList<String>();
	    switch (strCase) {
	    case "Name Ascending":

	      for (WebElement Name : NameLst) {        
	        data.add(Name.getAttribute("value"));
	      }
	      
	      break;
	      
	    case "Name Descending":

		      for (WebElement Name : NameLst) {        
		        data.add(Name.getAttribute("value"));
		      }
		      
		      break;
		  
	    case "Date Ascending":

	      for (WebElement date : DateLst) {
	        //data.add(date.getAttribute("value"));
	        data.add(date.getText());
	        System.out.println(date.getText());
	        }
	      break;
	      
	    case "Date Descending":
	    	
		    for (WebElement date : DateLst) {
		    	data.add(date.getText());
		        }
		      break; 

	    case "Access":
	      AccessElementInGridHeaderRow.click();
	      for (WebElement access : AccessLst) {
	        data.add(access.getAttribute("value"));
			}
	      break;

		case "Email Ascending":
	      for (WebElement email : EmailLst) {
	        data.add(email.getText());
			}
	      break;

	    case "Clients Ascending":
	      for (WebElement client : ClientLst) {
	        data.add(client.getText());
			}
	      break;

	    }
	    status = BaseClass.rm.isSorted(data, strCase);
	    System.out.println(status);
	    return status;
	  }

  /**
   * Edit any existing user based on userType(AppUser, UserAdmin or clientAdmin) and total number of
   * clients assigned to that user
   * 
   * @author Vikram Hegde
   * @param userType
   * @param numberOfClientsAssigned
   * @return
   */
  public int editAnyExistingUser(UserTypeEnum userType, int numberOfClientsAssigned,
      boolean numberOfClientsCheckRequired) {
    int editedUserRowIndex = -1;
    for (int i = 0; i <= getList_Users().size(); i++) {
      String userWebElementXpath = usersListXpath
          + "["
          + i
          + "]"
          + (UserTypeEnum.APP_USER.equals(userType) ? rolesAppUserListXpath
              : UserTypeEnum.CLIENT_ADMIN.equals(userType) ? rolesClientAdminListXpath
                  : UserTypeEnum.USER_ADMIN.equals(userType) ? rolesSuperUserListXpath
                      : rolesAppUserListXpath);

      if (BaseClass.rm.isElementPresent(userWebElementXpath)) {
        String appUserEditButtonXpath = usersListXpath + "[" + i + "]" + editUserButtonXpath;
        if (BaseClass.rm.isElementPresent(appUserEditButtonXpath)) {
          String numOfClientsForUserXpath = usersListXpath + "[" + i + "]"
              + numberOfClientsForUserXpath;
          if (BaseClass.rm.isElementPresent(numOfClientsForUserXpath)) {
            int numberOfClients = Integer.valueOf(driver.findElement(
                By.xpath(numOfClientsForUserXpath)).getText());
            if (!numberOfClientsCheckRequired
                || (numberOfClientsCheckRequired && numberOfClients == numberOfClientsAssigned)) {
              BaseClass.rm.clickWebElement(driver.findElement(By.xpath(appUserEditButtonXpath)));
              editedUserRowIndex = i;
              break;
            }
          }
        }
      }
    }
    return editedUserRowIndex;
  }

  /**
   * This method is used to edit Any existing disbaled user
   * 
   * @return
   */
  public int editAnyExistingDisabledUser() {
    int editedUserRowIndex = -1;
    for (int i = 0; i <= getList_Users().size(); i++) {
      String userWebElementXpath = disabledUsersListXpath;
      if (BaseClass.rm.isElementPresent(userWebElementXpath)) {
        String appUserEditButtonXpath = disabledUsersListXpath + "[" + i + "]"
            + editUserButtonXpath;
        if (BaseClass.rm.isElementPresent(appUserEditButtonXpath)) {
          BaseClass.rm.clickWebElement(driver.findElement(By.xpath(appUserEditButtonXpath)));
          editedUserRowIndex = i;
          break;
        }
      }
    }
    return editedUserRowIndex;
  }

  /**
   * Edit any existing user based on selected user row index
   * 
   * @author Vikram Hegde
   * @param userType
   * @param numberOfClientsAssigned
   * @return
   */
  public void editAnyExistingUserForRowIndex(int selectedUserRowIndex) {
    String appUserEditButtonXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + editUserButtonXpath;
    if (BaseClass.rm.isElementPresent(appUserEditButtonXpath)) {
      BaseClass.rm.clickWebElement(driver.findElement(By.xpath(appUserEditButtonXpath)));
    }
  }

  /**
   * Edit any existing disabled user based on selected user row index
   * 
   * @author Vikram Hegde
   * @param userType
   * @param numberOfClientsAssigned
   * @return
   */
  public void editAnyExistingDisabledUserForRowIndex(int selectedUserRowIndex) {
    String appUserEditButtonXpath = disabledUsersListXpath + "[" + selectedUserRowIndex + "]"
        + editUserButtonXpath;
    if (BaseClass.rm.isElementPresent(appUserEditButtonXpath)) {
      BaseClass.rm.clickWebElement(driver.findElement(By.xpath(appUserEditButtonXpath)));
    }
  }

  /**
   * This method is used to click disable on selected user row index
   * 
   * @author Vikram Hegde
   * @param selectedUserRowIndex
   * @throws Exception
   */
  public void disableEditedUser(int selectedUserRowIndex) throws Exception {
    String appUserDisableButtonXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + disableUserButtonXpath;
    if (BaseClass.rm.isElementPresent(appUserDisableButtonXpath)) {
      BaseClass.rm.webElementSync(driver.findElement(By.xpath(appUserDisableButtonXpath)),
          "visibility");
      BaseClass.rm.clickWebElement(driver.findElement(By.xpath(appUserDisableButtonXpath)));
    }
  }

  /**
   * This method is used to click undo on selected user row index
   * 
   * @author Vikram Hegde
   * @param selectedUserRowIndex
   * @throws Exception
   */
  public void undoDisableEditedUser(int selectedUserRowIndex) throws Exception {
    String appUserUndoButtonXpath = disabledUsersListXpath + "[" + selectedUserRowIndex + "]"
        + undoUserButtonXpath;
    if (BaseClass.rm.isElementPresent(appUserUndoButtonXpath)) {
      BaseClass.rm.clickWebElement(driver.findElement(By.xpath(appUserUndoButtonXpath)));
    }
  }

  /**
   * This method is used to save on selected user row index
   * 
   * @author Vikram Hegde
   * @param selectedUserRowIndex
   */
  public void saveEditedUser(int selectedUserRowIndex) {
    String appUserSaveButtonXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + saveUserButtonXpath;
    if (BaseClass.rm.isElementPresent(appUserSaveButtonXpath)) {
      BaseClass.rm.waitTime(3000);
      BaseClass.rm.clickWebElement(driver.findElement(By.xpath(appUserSaveButtonXpath)));
      CustomReporter.log("Disabled user and saved");
    }
  }

  /**
   * Used to get number of clients assigned on selected user row index
   * 
   * @author Vikram Hegde
   * @param selectedUserRowIndex
   * @return
   */
  public int getNumberOfClientsForSelectedUser(int selectedUserRowIndex) {
    int numberOfClients = -1;
    String numOfClientsForUserXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + numberOfClientsForUserXpath;
    if (BaseClass.rm.isElementPresent(numOfClientsForUserXpath)) {
      numberOfClients = Integer.valueOf(driver.findElement(By.xpath(numOfClientsForUserXpath))
          .getText());
    }
    return numberOfClients;
  }

  /**
   * Used to get verify if user row is greyed out on selected user row index
   * 
   * @author Vikram Hegde
   * @param selectedUserRowIndex
   * @return
   */
  public boolean verifyUserRowIndexGreyedOut(int selectedUserRowIndex) {
    boolean isUserRowIndexGreyedOut = false;
    String userWebElementXpath = usersListXpath + "[" + selectedUserRowIndex + "]";
    if (BaseClass.rm.isElementPresent(userWebElementXpath)
        && "rgba(182, 180, 179, 1)".equals(driver.findElement(By.xpath(userWebElementXpath))
            .getCssValue("color"))) {
      isUserRowIndexGreyedOut = true;

    }
    return isUserRowIndexGreyedOut;
  }

  /**
   * Used to get verify reset password message box
   * 
   * @author Vikram Hegde
   * @param selectedUserRowIndex
   * @return
   */
  public boolean verifyResetPasswordPanel(int selectedUserRowIndex, String strTextToCompare) {
    boolean isResetPasswordMessageVerified = false;
    String userWebElementXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + resetPasswordBoxXpath;
    if (BaseClass.rm.isElementPresent(userWebElementXpath)) {
      if (driver.findElement(By.xpath(userWebElementXpath)).getText().substring(19).toLowerCase()
          .contentEquals(strTextToCompare.toLowerCase())) {
        CustomReporter.log("When it was set at that time system time was : "
            + strTextToCompare.toLowerCase());
        CustomReporter.log("The time which is shown on Edit User Panel is : "
            + driver.findElement(By.xpath(userWebElementXpath)).getText().substring(19)
                .toLowerCase());
        isResetPasswordMessageVerified = true;
      } else {
        System.out.println("strTextToCompare.charAt(0) = " + strTextToCompare.charAt(0));
        System.out.println("strTextToCompare.charAt(0) = " + strTextToCompare.charAt(1));
        CustomReporter.errorLog("When it was set at that time system time was : "
            + strTextToCompare.toLowerCase());
        CustomReporter.errorLog("The time which is shown on Edit User Panel is : "
            + driver.findElement(By.xpath(userWebElementXpath)).getText().substring(19));
      }

    }
    return isResetPasswordMessageVerified;
  }

  /**
   * Send input string to username field text box for selected user row index
   * 
   * @author Vikram Hegde
   * @param selectedUserRowIndex
   * @param userNameUpdate
   */
  public void sendInputToUserNameFieldForSelectedUser(int selectedUserRowIndex,
      String userNameUpdate) {
    String appUserNameInputFieldXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + userNameInputFieldXpath;
    if (BaseClass.rm.isElementPresent(appUserNameInputFieldXpath)) {
      driver.findElement(By.xpath(appUserNameInputFieldXpath)).sendKeys();
    }
  }

  /**
   * Get username from username text box for selected user row index
   * 
   * @author Vikram Hegde
   * @param selectedUserRowIndex
   * @param userNameUpdate
   */
  public String getUserNameForSelectedUserRowIndex(int selectedUserRowIndex) {
    String userName = null;
    String appUserNameInputFieldXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + userNameInputFieldXpath;
    if (BaseClass.rm.isElementPresent(appUserNameInputFieldXpath)) {
      userName = driver.findElement(By.xpath(appUserNameInputFieldXpath)).getAttribute("value");
    }
    return userName;
  }

  /**
   * Used to get list of Selected Client Row indices for selected user
   * 
   * @author Vikram Hegde
   * @param selectedUserRowIndex
   * @return
   */
  public List<Integer> getListOfClientRowIndices(int selectedUserRowIndex) {
    List<Integer> clientSelectedRowIndices = new ArrayList<Integer>();
    for (int k = 1; k <= getListOfClientsInBulkAssignmentCallOut().size() + 1; k++) {
      String assignClientIconXpath = clientListForUserXpath + "[" + k + "]"
          + (unAssignUserClientIcon);
      if (BaseClass.rm.isElementPresent(assignClientIconXpath)) {
        clientSelectedRowIndices.add(k);
      }
    }
    return clientSelectedRowIndices;
  }

  /**
   * This method is used to save on selected user row index
   * 
   * @author Vikram Hegde
   * @param selectedUserRowIndex
   */
  public void resetPasswordSelectedUser(int selectedUserRowIndex) {
    String appUserResetPasswordButtonXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + userResetPasswordButtonXpath;
    if (BaseClass.rm.isElementPresent(appUserResetPasswordButtonXpath)) {
      BaseClass.rm.clickWebElement(driver.findElement(By.xpath(appUserResetPasswordButtonXpath)));
    }
  }

  /**
   * Used to get long username from users list
   * 
   * @author Vikram Hegde
   * @return
   */
  public String getLongUserNameFromUserList() {
    String longUserName = null;
    for (int i = 1; i <= getList_Users().size(); i++) {
      String appUserNameInputFieldXpath = usersListXpath + "[" + i + "]" + userNameInputFieldXpath;
      if (BaseClass.rm.isElementPresent(appUserNameInputFieldXpath)) {
        String userName = driver.findElement(By.xpath(appUserNameInputFieldXpath)).getAttribute(
            "value");
        if (!StringUtils.isEmpty(userName) && userName.length() > 40) {
          longUserName = driver.findElement(By.xpath(appUserNameInputFieldXpath)).getAttribute(
              "value");
        }
      }
    }
    return longUserName;
  }

  /**
   * Get list of clients for selected user
   * 
   * @param selectedUserRowIndex
   * @return
   */
  public List<String> getSelectedUserClients(int selectedUserRowIndex) {
    List<String> clientList = new ArrayList<String>();
    for (int k = 1; k <= getListOfClientsInBulkAssignmentCallOut().size(); k++) {
      String clientRowXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
          + clientListForUserXpath + "[" + k + "]";
      if (BaseClass.rm.isElementPresent(clientRowXpath)) {
        clientList.add(driver.findElement(By.xpath(clientRowXpath)).getText());
      }
    }
    return clientList;

  }

  /**
   * Get list of clients from bulk clients overlay
   * 
   * @param selectedUserRowIndex
   * @return
   */
  public List<String> getBulkClientsList() {
    List<String> clientList = new ArrayList<String>();
    for (WebElement bulkAssignClient : getClientListBulkAssign()) {
      if (!StringUtils.isEmpty(bulkAssignClient.getText())) {
        clientList.add(bulkAssignClient.getText());
      }
    }
    return clientList;

  }

  /**
   * Verify client list for all users
   * 
   * @param clients
   * @return
   * @throws InterruptedException
   */
  public boolean verifyClientListForAllUsers(List<String> clients) throws InterruptedException {
    boolean verifyClientListForAllUsers = false;
    for (int i = 1; i <= getList_Users().size(); i++) {
      editAnyExistingUserForRowIndex(i);
      Thread.sleep(2000);
      List<String> selectedUserClients = getSelectedUserClients(i);
      cancelSelectedUser(i);
      if (!CollectionUtils.isEqualCollection(clients, selectedUserClients)) {
        break;
      } else {
        verifyClientListForAllUsers = true;
      }
    }
    return verifyClientListForAllUsers;
  }

  /**
   * Click cancel button for selected user index
   * 
   * @param selectedUserRowIndex
   */
  public void cancelSelectedUser(int selectedUserRowIndex) {
    String userCancelButtnXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + userCancelButtonXpath;
    if (BaseClass.rm.isElementPresent(userCancelButtnXpath)) {
      BaseClass.rm.clickWebElement(driver.findElement(By.xpath(userCancelButtnXpath)));
    }
  }

  /**
   * Create user with name and email
   * 
   * @param name
   * @param emailAddress
   * @param clickCreateUserIcon
   * @throws Exception
   */
  public void createUser(String name, String emailAddress, boolean clickCreateUserIcon)
      throws Exception {
    if (clickCreateUserIcon)
      AddUserButton.click();
    if (func_ElementExist("Add Users Overlay Exist")) {
      Adduser_Name.clear();
      Adduser_Name.sendKeys(name);
      Adduser_Email.clear();
      Adduser_Email.sendKeys(emailAddress);
      Adduser_Create.click();
    }
  }

  public boolean verifyCreateUserValidationError() {
    boolean verifyCreateUserValidationError = false;
    if (BaseClass.rm.webElementExists(formErrors)) {
      verifyCreateUserValidationError = true;
    }
    return verifyCreateUserValidationError;
  }

  public boolean verifySelectedUserIsClientAdmin(int selectedUserRowIndex) {
    String userWebElementXpath = usersListXpath + "[" + selectedUserRowIndex + "]"
        + rolesClientAdminListXpath;
    return BaseClass.rm.isElementPresent(userWebElementXpath);
  }

  public WebElement getAddUserOverlay_Cancel() {
    return addUserOverlay_Cancel;
  }

  public List<WebElement> getList_Users() {
    return List_Users;
  }

  public List<WebElement> getListOfClientsInBulkAssignmentCallOut() {
    return ListOfClientsInBulkAssignmentCallOut;
  }

  public List<WebElement> getClientListBulkAssign() {
    return clientListBulkAssign;
  }

}
