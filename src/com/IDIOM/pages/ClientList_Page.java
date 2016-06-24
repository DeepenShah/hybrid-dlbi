/* 
   * ClientList_Page
 * List Of Methods:
 * func_ClientListPageElementExist(String Case) - This method is used to check the existence of any web element of Client list page and returns boolean value. When it is true it means element is found and when it is false it means it is not found.
 * func_CheckEmailClient(String Evalue, String EmailID) - This method determines whether mail to: link is opening the correct email client message window. E.g. On client list page 'Missing a Client' Link click will open relevant configured Outlook Message Window
 * func_DoubleClickElement(String elementName, WebDriver driver) -This method does double click on the  element which is provided as input
 * func_PerformAction(String element, WebDriver driver) - This method performs various actions depending on Switch case provided
 * func_SelectClient(String client) - This method is used to perform click operation on any client in the client list page
 * func_GetCountFromClientImage(String clientName) - This method is used to extract saved idiom count from Client Image e.g extract "113" from 113 Saved Idioms
 * func_GetListWebElementCount(String element) - This method is used to get Total saved idoms from Manage idiom page
 * func_CheckLogosPresent(WebDriver driver):This method is used to check whether all Client Cards have its Logo in it 
 * func_DoMouseHoverOnClientCard(String Case):This method is used to check the mouse hover effect of Client Card on Client List page 
 * func_getArrtibute_src_ClientImage(String attributeName):This method is used to return src for provided client name - pl. note: First get client name from img alt tage 
 * func_getClientsPresent():This method gets the clients present for useer 
 * func_getList(String element):This method is used to return list of web element available in specified listElement
 * func_GetLocalWebElement(String strWebElement): This method is used to get private WebElement out of this class.
 */
package com.IDIOM.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;

public class ClientList_Page {
		
	WebDriver driver;
	ReusableMethods rm = new ReusableMethods(driver);	
	public ClientList_Page(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);		
		this.driver = driver;
	}
	
	@FindBy(xpath="//div[text()='Industry Verticals']/ancestor::div[contains(@class, 'client')]")
	private WebElement img_industryVerticals;
	
	@FindBy(xpath="//div[text()='Delta']/ancestor::div[contains(@class, 'client')]")
	private WebElement img_delta;
	
	@FindBy(xpath="//div[text()='ECommerce']/ancestor::div[contains(@class, 'client')]")
	private WebElement img_Ecommerce;	
	
	@FindBy(xpath="//*[@ng-click='logout()' OR @ng-click='navigation.logout()']")
	private WebElement link_Logout; 

	
	@FindBys(@FindBy(xpath="//*[@class='selected-client dropdown-parent navicon ng-isolate-scope']//*[@ng-click='logout()']"))
	private List<WebElement>  link_Logout_Exists;
	

	@FindBy(xpath="//div[text()='VS Limited']/ancestor::div[contains(@class, 'client')]//span[@class='ng-binding']")
	private WebElement saved_idioms_Client_VSLimited;
	
	
	
	@FindBy(xpath="//img[@class='logo' and @alt='IDIOM']")
	private WebElement img_IdiomLogo;
	
	@FindBy(xpath="//h2[text()='Select a Client']")
	private WebElement label_SelectaClient;
	
	@FindBy(xpath="//*[text()='Sprint Boost']")
	private WebElement SprintBoost;
	
	@FindBy(xpath="//*[@id='client-0']")
	private WebElement industry_Vertical_Client;
	
	@FindBy(xpath="//div[text()='VS Limited']/ancestor::div[contains(@class, 'client')]//span[@class='ng-binding']")
	private WebElement client_VS;
	
	@FindBys(@FindBy(xpath="//div[text()='VS Limited']/ancestor::div[contains(@class, 'client')]//span[@class='ng-binding']"))
	private  List<WebElement> client_VS_List;
	
	@FindBy(xpath="//a[@title='Help' and text()='?']")
	private WebElement icon_Help;	
	
	@FindBys(@FindBy(xpath="//a[@title='Help' and text()='?']"))
	private List<WebElement> icon_Help_lst;
	
	@FindBys(@FindBy(xpath="//*[contains(text(),'Select a Client')]"))
	private List<WebElement> label_SelectaClientlst;
	
	@FindBys(@FindBy(xpath="//img[@class='logo' and @alt='IDIOM']"))
	private List<WebElement> img_IdiomLogo_lst;
	
	@FindBys(@FindBy(xpath="//*[@ng-click='onClientClick(client)']"))
	private List<WebElement> clients_lst;
	
	@FindBy(xpath="//*[@class='help-content']//h2")
	private WebElement help_Content;	
	
	
	@FindBy(xpath="//div[@class='client-box missing-client']")
	private WebElement img_missingAClient;
	
	@FindBys(@FindBy(xpath="//div[@class='client-box missing-client']"))
	private List<WebElement> img_missingAClientLst;
	
	@FindBy(xpath="//img[contains(@src,'clients')]")
	private WebElement img_clientLogo;
	
	@FindBy(xpath="//img[contains(@src,'assets/images/clients/eba.png')]")
	private WebElement ebayClient;
	
	@FindBy(xpath="//*[@class='selected-client dropdown-parent']")
	private WebElement right_TopClient;
	
	@FindBy(xpath="html/body/main/section/section/header/div[3]/ul/li[1]")
	private WebElement icon_IdiomName;
	
	@FindBy(xpath="//div[@class='client-box ng-scope hovered']")
	private WebElement ClientCard;
	
	@FindBy(xpath="//a[text()=' Change Client']")
	private WebElement btn_changeClient;
	
	@FindBys(@FindBy(xpath="//div[@ng-click='onClientClick(client)']//img[@ng-alt]"))
	private List<WebElement> ClientLogos;
	
	@FindBy(xpath="//div[@class='client-box missing-client']//img")
	private WebElement MissingAClientLogo;
	
	
	@FindBy(xpath="//*[@class='ion ion-navicon-round' OR @class='navigation-user-menu ng-isolate-scope']")
	private WebElement MenuIcon;
	
	@FindBy(xpath="//div[text()='Industry Verticals']/ancestor::div[contains(@class, 'client')]//span[@class='ng-binding']")
	private WebElement saved_idioms_Client_Ecommerse;

	@FindBy(xpath="//*[@class='admin-access']")
	private WebElement  AdminAccess;
	
	@FindBys(@FindBy(xpath="//*[@class='admin-access']"))
	private List<WebElement>   AdminAccessPresent;
	
	@FindBy(xpath="//*[text()='Schema V2 Test Client']")
	private WebElement  SchemaV2Client;
	
	@FindBys(@FindBy(xpath="//*[@ng-click='onClientClick(client)']/*[@class='name ng-binding']"))
	private List<WebElement>   ClientNames;
	
	
	@FindBy(xpath="//*[@ui-sref='account-me']")
	private WebElement  MyAccount;
	
	@FindBys(@FindBy(xpath="//*[@class='admin-access']"))
	private List<WebElement>   MyAccountPresent;
	
	@FindBys(@FindBy(xpath="//div[@class='site-inner ng-scope']/div[not(contains(@class,'client-box missing-client'))]//div[@class='name ng-binding']"))
	private List<WebElement> listClients;
	
	@FindBys(@FindBy(xpath="//*[@class='client page-level ng-scope']"))
	private List<WebElement> ClientListPage;
	
	// IDIOM 2.3 Elements/Methods
	//################ Start ####################
	
	@FindBy(xpath="//div[@class='navigation-logo']/img")
	public WebElement idiomLogo;
	

	//Prev //div[@id='clients']/div[2]/clientv2/div

	@FindBys(@FindBy(xpath="//div[@id='clients']/div[2]/client/div"))
	public List<WebElement> clientList;
	
	//Prev //div[@id='clientsv2']/div[1]/div[3]/i
	@FindBy(xpath="//div[starts-with(@id,'clients')]/div[1]/div[3]/i")
	public WebElement clientListDropDown;
	
	@FindBy(xpath="//div[starts-with(@id,'clients')]//div[contains(@ng-click,'toggleDropdown()')]")
	public WebElement selectedClient;
	
	@FindBy(xpath="//button[@class='clientshomepage__create-project-button']")
	public WebElement newProjectBtn;
	
	@FindBy(xpath="//projects[@class='ng-isolate-scope']//project[last()]//div[@class='project__edittor']")
	public WebElement newProjectWindow;
	
	@FindBy(xpath="//projects[@class='ng-isolate-scope']")
	public WebElement projectsTable;
	
	@FindBy(xpath="//ul[@class='project__controls__subcontrol']/li[1]")
	public WebElement editProject;
	
	@FindBy(xpath="//ul[@class='project__controls__subcontrol']/li[2]")
	public WebElement duplicateProject;
	
	@FindBy(xpath="//ul[@class='project__controls__subcontrol']/li[4]")
	public WebElement deleteProject;
	
	@FindBy(xpath="//ul[@class='project__controls__subcontrol']/li[5]/span[1]")
	public WebElement yesDeleteProject;
	
	@FindBy(xpath="//ul[@class='project__controls__subcontrol']/li[5]/span[2]")
	public WebElement noDeleteProject;
	
	@FindBys(@FindBy(xpath="//div[@class='project__row']//button[@class='project__name ng-binding']"))
	public List<WebElement> projectList;
	
	@FindBys(@FindBy(xpath="//*[@class='projects']//*[not(@class='ng-hide')]/center[@class='projects__project__shim']//img[@alt='No Projects Found']"))
	public List<WebElement> noProjectImg;
	
	@FindBys(@FindBy(xpath="//*[@class='clients__dropdown__options']//*[@class='client ng-binding']"))
	List<WebElement> clientsDropdownOptions;
	
	//@FindBys(@FindBy(xpath="//*[@class='subclients']//*[@ng-click='clients.onSubClientSelect(subclient)']"))
	@FindBys(@FindBy(xpath="//*[@class='subclients']//*[contains(@ng-click,'onSubClientSelect(subclient)')]"))
	List<WebElement> subClientsList;
	
	@FindBy(xpath="//div[@class='project__row']/div[@class='project__edittor']")
	public WebElement projectWindow;
	
	@FindBy(xpath="//div[starts-with(@id,'clients')]//div[contains(@ng-click,'toggleDropdown()')]//*[contains(@class,'label__client-name ng-binding')]")
	public WebElement selectedClientName;
	
	@FindBy(xpath="//*[@class='clientsv2__dropdown__label__select-client']")
	public WebElement selectClientMsgInDropDown;
	
	@FindBy(xpath="//*[contains(@class,'wrapper')]//*[contains(@class,'dropdown--open')]")
	public WebElement clientDropDownOpen;
	
	@FindBy(xpath="//*[@class='projects__model-header__project-name']")
	public WebElement projectHeaderName;
	
	@FindBys(@FindBy(xpath="//*[contains(@class,'dropdown__options')]/*[contains(@on-client-select,'onClientSelect(c,$index)')]/div"))
	List<WebElement> clientListInDropDown;
	
	@FindBy(xpath="//*[contains(@class,'dropdown__options')]/*[contains(text(),'Request a client')]")
	public WebElement requestClient;
	
	@FindBy(xpath="//*[@class='ng-binding subclient--selected']")
	public WebElement selectedSubClient;

	@FindBy(xpath="//div[@class='projects__model-header projects__model-header--border-bottom']//div[@class='projects__model-header__project-name']")
	public WebElement nameColumn;
	
	@FindBy(xpath="//div[@class='projects__model-header projects__model-header--border-bottom']//div[@class='projects__model-header__project-audiences']")
	public WebElement audienceColumn;
	
	@FindBy(xpath="//div[@class='projects__model-header projects__model-header--border-bottom']//div[@class='projects__model-header__project-created omega-2']")
	public WebElement createdColumn;
	
	@FindBy(xpath="//div[@class='projects__model-header projects__model-header--border-bottom']//div[@class='projects__model-header__project-modified omega-1']")
	public WebElement modifiedColumn;
	
	@FindBy(xpath="//div[@class='projects__model-header projects__model-header--border-bottom']//div[@class='projects__model-header__project-controls omega']")
	public WebElement toggleOptionColumn;
	
	@FindBys(@FindBy(xpath="//div[@class='project__row']//div[@class='project__created omega-2 ng-binding']"))
	public List<WebElement> listCreatedColumn;
	
	@FindBys(@FindBy(xpath="//div[@class='projects']//div[@class='audiences__select']"))
	public List<WebElement> listAudienceColumn;
	
	@FindBys(@FindBy(xpath="//div[@class='project__row']//div[@class='project__modified omega-1 ng-binding']"))
	public List<WebElement> listModifiedColumn;
	
	@FindBys(@FindBy(xpath="//div[@class='project__row']//i[@class='project__controls__gear ion ion-ios-gear-outline pointer']"))
	public List<WebElement> listToggleOptionColumn;
	
	@FindBys(@FindBy(xpath="//div[@class='project__row']/button[@disabled='disabled']"))
	public List<WebElement> blankProjectRows;

	@FindBy(xpath="//*[@class='projects']//*[not(@class='ng-hide')]/center[@class='projects__project__shim']//span")
	public WebElement noProjectText;

	@FindBys(@FindBy(xpath="//div[@class='project__row']"))
	public List<WebElement> projectRows;
	
	@FindBy(xpath="//*[@class='projects']//*[not(@class='ng-hide')]/center[@class='projects__project__shim']//span[contains(text(),'Create projects for each of your campaigns or analyses to start gathering insights')]")
	public WebElement noProjectMsg;
	

	@FindBys(@FindBy(xpath="//div[@class='project__created omega-2 ng-binding']"))
	public List<WebElement> createdDatesList;

	@FindBys(@FindBy(xpath="//div[@class='project__modified omega-1 ng-binding']"))
	public List<WebElement> modifiedDatesList;

	@FindBy(xpath="(//center[@class='projects__project__shim']/span)[2]")
	public WebElement messageIfNoProjectAdded;

	@FindBys(@FindBy(xpath="//div[@class='project__edittor__body__audience-form']//div[@class='project__edittor__body__audience-form__body__audiences__audience__edit']"))
	public List<WebElement> listEditButtonsForEditedAudience;
	
	@FindBys(@FindBy(xpath="//div[@class='project__edittor__body__audience-form']//div[@class='project__edittor__body__audience-form__body__audiences__audience__delete']"))
	public List<WebElement> listDeleteButtonsForEditedAudience;
	
	@FindBys(@FindBy(xpath="//div[@class='project__edittor__body__audience-form']//div[@class='project__edittor__body__audience-form__body__audiences__audience__duplicate']"))
	public List<WebElement> listDuplicateButtonsForEditedAudience;

	@FindBys(@FindBy(xpath="//div[@class='project__edittor__body__audience-form']//div[@class='project__edittor__body__audience-form__body__audiences__audience__rename']"))
	public List<WebElement> listRenameButtonsForEditedAudience;

	@FindBys(@FindBy(xpath="//div[@class='project__edittor__body__audience-form']//div[@class='project__edittor__body__audience-form__body__audiences__audience__radio']"))
	public List<WebElement> listRadioButtonsForEditedAudience;

	@FindBy(xpath="//div[@class='project__edittor__body__audience-form']//div[@class='project__edittor__body__audience-form__header__controls']")
	public WebElement newAudienceButtonWithPlusIcon;
	
	@FindBy(xpath="//div[@class='project__edittor__body__audience-form']//div[@class='project__edittor__body__audience-form__header__title']")
	public WebElement editAudienceTtitle;
	
	@FindBy(xpath="//div[@class='call-out call-out--left']")
	public WebElement messageWhenNoClientSelected;
	
	public WebElement globalProjectWindow=null;
	
	/* Store all the dynamic variable as a string here....
	 * It will have two type string or string with dynamic value. That value will be replaced at the time of use.
	 * For such variable make sure two things,
	 * 1. Instead of single quote(') use quote two times ('')
	 * 2. Give appropriate name and 'Contains' as a postfix.
	 * 
	 * For example,
	 * 
	 * String strTestContains = "//project[@class=''ng-scope ng-isolate-scope'']/div[contains(.,{0})";
	 * 
	 */	
	String strProjectWindowContains = "//project[@class=''ng-scope ng-isolate-scope'']/pre[contains(.,''\"name\": \"{0}\"'')]/parent::project";
	String strProjectContains = "//project[@class=''ng-scope ng-isolate-scope'']/pre[contains(.,''\"name\": \"{0}\"'')]";									

	public String strProjectNameTxt = ".//div[@class='project__edittor__body__project-form__name__input']/input";
	public String strProjectDescTxt = ".//div[@class='project__edittor__body__project-form__description__input']/textarea";

	public String strProjectSaveBtn = ".//div[@class='project__edittor__body__project-form__save-or-cancel']/button";
	String strProjectOptionsBtnContains ="//button[contains(.,''{0}'')]/parent::div//div[@class=''project__controls omega layout center-center'']/i";
	public String strProjectRowContains ="//button[contains(.,''{0}'')]";
	public String strLaunchProjectBtn = ".//button[@class='project__edittor__header__launch-button']";
	
	String strProjectCancelBtn = ".//div[@class='project__edittor__body__project-form__save-or-cancel']/span";
	String strProjectCloseBtn = ".//div[@class='project__edittor__header__close-icon pointer']/i";
	String strProjectTab = ".//div[@class='project__edittor__header']/div[2]";
	
	String strNewAudienceBtn = ".//div[@class='project__edittor__body__audience-form__header__controls']/span";
	public String strAudienceTab = ".//div[@class='project__edittor__header']/div[3]";
	String strAudiencePageLabel = ".//div[@class='project__edittor__body__audience-form__header__title']";
	public String strTotalPopulation = ".//div[@class='project__edittor__body__audience-form__body__audiences__audience__name ng-binding']";
	
	
	String strNewAudienceCreateAndSaveBtn = ".//div[@class='project__edittor__body__audience-form__body']/div/div[last()]//button[text()='Create and Save']";
	String strNewAudienceCancelBtn = "(.//div[@class='project__edittor__body__audience-form__body']/div/div[last()]//span[text()='Cancel'])[1]";
	String strNewAudienceRadioBtn = ".//div[@class='project__edittor__body__audience-form__body']/div/div[last()]/div[@class='project__edittor__body__audience-form__body__audiences__audience__create__radio']/input";
	
	//Prev .//div[@class='project__edittor__body__audience-form__body']/div/div[last()]//input[@class='inline-edit ng-pristine ng-untouched ng-valid ng-not-empty ng-valid-maxlength']
	String strNewAudienceTxtField = ".//div[@class='project__edittor__body__audience-form__body']/div/div[last()]//div[@class='project__edittor__body__audience-form__body__audiences__audience__create__name']/input[1]";
	
	String strEditAudienceSaveBtnContains = ".//div[@class=''project__edittor__body__audience-form__body'']/div/div[contains(.,''{0}'')]//button[text()=''Save'']";
	public String strEditAudienceCancelBtnContains = "(.//div[@class=''project__edittor__body__audience-form__body'']/div/div[contains(.,''{0}'')]//span[text()=''Cancel''])[2]";
	String strEditAudienceTxtFieldContains=".//div[@class=''project__edittor__body__audience-form__body'']/div/div[contains(.,''{0}'')]/div[@class=''project__edittor__body__audience-form__body__audiences__audience__create__name'']/input[@ng-show=''a.rename'']";
	String strEditAudienceTxtField = ".//input[@class='inline-edit ng-pristine ng-untouched ng-valid ng-not-empty ng-valid-maxlength']";
	
	String strAudienceRenameLinkContains = ".//div[@class=''project__edittor__body__audience-form__body'']/div/div[contains(.,''{0}'')]//a[contains(.,''Rename'')]";
	public String strAudienceDuplicateLinkContains = ".//div[@class=''project__edittor__body__audience-form__body'']/div/div[contains(.,''{0}'')]//a[contains(.,''Duplicate'')]";
	String strAudienceDeleteLinkContains = ".//div[@class=''project__edittor__body__audience-form__body'']/div/div[contains(.,''{0}'')]//a[contains(.,''Delete'')]";
	String strAudienceEditLinkContains = ".//div[@class=''project__edittor__body__audience-form__body'']/div/div[contains(.,''{0}'')]//a[contains(.,''Edit'')]";
	String strAudienceList = ".//div[@class='project__edittor__body__audience-form__body']/div/div[@class='project__edittor__body__audience-form__body__audiences__audience ng-scope']";
	String strAudienceSelectRadioContains = ".//div[@class=''project__edittor__body__audience-form__body'']/div/div[contains(.,''{0}'')]//input[@type=''radio'']";
	
	
	String strAudienceRadioContains = ".//div[@class=''project__edittor__body__audience-form__body'']/div/div[contains(.,''{0}'')]/div[1]/input";

	String strListAudiencesText = ".//div[@class='project__edittor__body__audience-form__body__audiences__audience__name ng-binding']";
	
	//.//div[@class='project__edittor__body__audience-form__body']//div[@class='project__edittor__body__audience-form__body__audiences__audience__name ng-binding']
	String strListOfAudiencesInProject = "//button[contains(.,''{0}'')]/parent::div//audiences//div[@class=''audiences__select__option__name ng-binding'']";		
	public String strProjectNameJSON = "//pre[contains(.,''\"name\": \"{0}\"'')]/parent::project/pre";

	
	public String selectedAudienceName = ".//*[@class='project__edittor__body__audience-form__body__audiences__audience ng-scope' and descendant::div[@class='project__edittor__body__audience-form__body__audiences__audience__radio']/input[@checked='checked']]/div[@class='project__edittor__body__audience-form__body__audiences__audience__name ng-binding']";
	
	public String strAudienceDropdownOnHome = "//button[contains(.,''{0}'')]/parent::div//audiences";
	String subSubClients="//*[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]/following-sibling::*[@class=''subclients'']//*[contains(@ng-click,''onSubClientSelect(subsubclient'')]";
	String subClientName="//*[@class=''subclients'']//span[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]";

	public String strProjectNameButton="./button";
	public String strAudienceDropdownValue=".//div[@class='audiences__select__name ng-binding']";
	public String strCreatedDateValue=".//div[@class='project__created omega-2 ng-binding']";
	public String strModifiedDateValue=".//div[@class='project__modified omega-1 ng-binding']";
	public String strToggleOptionElement=".//i[@class='project__controls__gear ion ion-ios-gear-outline pointer']";

	String clientName="//*[contains(@class,''dropdown__options'')]/*[contains(@on-client-select,''onClientSelect(c,$index)'')]/div[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]";


	public String strCreateNewProjectLabel =".//div[@class='project__edittor__body__project-form__header']";
	public String strNewProjectWindowNameLabel = ".//div[@class='project__edittor__body__project-form__name__label']";
	public String strNewProjectWindowDescLabel = ".//div[@class='project__edittor__body__project-form__description__label']";
	

	
	/** This method will select the client from the drop down. 
	 *  If the current selection is same as given as argument then it won't do anything.
	 *  
	 * @param strClient - String Client name
	 * @throws Exception - Throws exception
	 * @author Deepen Shah
	 * @since 29/03/2016
	 */
	public boolean selectClient(String strClient) throws Exception{
		boolean bFound = false;	
		System.out.println("Currently selected client is : " + selectedClient.getText());
		if(!selectedClient.getText().trim().equalsIgnoreCase(strClient)){
			clientListDropDown.click();
			Thread.sleep(2000);
			
	//		for(WebElement aElement:clientList){
			for(WebElement aElement:clientListInDropDown){
				System.out.println("Client " + aElement.getText() + "--");
				if(aElement.getText().trim().equalsIgnoreCase(strClient)){				
					aElement.click();
					bFound = true;					
					break;
				}
			}
			
			if(!bFound)
				throw new IDIOMException("No client, " + strClient +" , found from the list.");
		}
		
		return bFound;
	}
	
	
	/** Current (IDIOM 2.3) design is dynamic. Hence, project window can not be stored in POM.
	 *  This method will find project window and store it as local WebElement variable.
	 * 
	 * @param bNew - Boolean true if window is for new project
	 * @param strProjectName - String. Blank in case of bNew is true.
	 * @return - Boolean. In case, given project is not found it will return false.
	 * @author Deepen Shah
	 * @since 29/03/2016
	 * 
	 */
	public boolean findAndSaveProjectWindow(boolean bNew,String strProjectName){
		boolean bStatus=true;
		
		globalProjectWindow=null;
		if(bNew){
			globalProjectWindow = newProjectWindow;
		}else{
			List<WebElement> allProjects = this.driver.findElements(By.xpath(MessageFormat.format(strProjectWindowContains, strProjectName)));
			if(allProjects.size()>0){				
				globalProjectWindow = allProjects.get(allProjects.size()-1);
			}else{			
				bStatus = false;
				System.out.println("No such project '"+ strProjectName +"' is found.");
				CustomReporter.errorLog("No such project '"+ strProjectName +"' is found.");
			}
		}
		
		return bStatus;
	}
	
	/** This is overloaded method to work with duplicate or same name projects
	 *  Pass on index for order as a 3rd argument. 
	 * 
	 * @param bNew - boolean true for new project window
	 * @param strProjectName - String project name
	 * @param iIndex - int index for order of project to be selected
	 * @return boolean . False in case given project is not found.
	 * @author Deepen Shah
	 * @since 09 Jun 2016
	 */
	public boolean findAndSaveProjectWindow(boolean bNew,String strProjectName,int iIndex){
		boolean bStatus=true;
		
		globalProjectWindow=null;
		if(bNew){
			globalProjectWindow = newProjectWindow;
		}else{
			List<WebElement> allProjects = this.driver.findElements(By.xpath(MessageFormat.format(strProjectWindowContains, strProjectName)));
			if(allProjects.size()>0){				
				globalProjectWindow = allProjects.get(iIndex-1);
			}else{			
				bStatus = false;
				System.out.println("No such project '"+ strProjectName +"' is found.");
				CustomReporter.errorLog("No such project '"+ strProjectName +"' is found.");
			}
		}
		
		return bStatus;
	}
	
	
	/** To enter project name and description for project.
	 * 
	 * @param strName - String new project name
	 * @param strDescription - String Project description
	 * @author Deepen Shah
	 * @since 29/03/2016
	 */
	public void fillProject(String strName,String strDescription){		
			
		WebElement inputElement=null;		
		
		if(!strName.equalsIgnoreCase("")){
			inputElement = globalProjectWindow.findElement(By.xpath(strProjectNameTxt));		
			
			inputElement.clear();
			inputElement.sendKeys(strName);
		}
		
		if(!strDescription.equalsIgnoreCase("")){			
			inputElement = globalProjectWindow.findElement(By.xpath(strProjectDescTxt));
			inputElement.clear();
			inputElement.sendKeys(strDescription);
		}	
		
	}
	
	/**
	 * @param strName
	 * @param strDesc
	 * @return
	 * @author Deepen Shah
	 * @since 06/04/2016
	 * 
	 */
	public boolean fillAndSaveProject(String strName,String strDesc){
		boolean bStatus;
		
		fillProject(strName, strDesc);
		
		WebElement saveBtn = globalProjectWindow.findElement(By.xpath(strProjectSaveBtn));
		bStatus = saveBtn.isEnabled();
		
		saveBtn.click();
		
		return bStatus;
	}
	
	/**
	 * @return
	 * @author Deepen Shah
	 * @since 06/04/2016
	 */
	public String[] getProjectNameAndDescriptionFromOverlay(){
		String[] retValues=new String[2];
					
		retValues[0] = globalProjectWindow.findElement(By.xpath(strProjectNameTxt)).getAttribute("value").trim();
		retValues[1] = globalProjectWindow.findElement(By.xpath(strProjectDescTxt)).getAttribute("value").trim();
		
		return retValues;
	}
	
	/** This will verify the project is available in the client home page or not. 
	 * 
	 * @param strProjectName - String. Project name to be checked
	 * @return - Boolean. True/false
	 * @since 30/03/2016
	 * @author Deepen Shah
	 */
	public boolean verfiyProjectIsExist(String strProjectName){
		boolean bStatus = true;
		
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		List<WebElement> allProjects = driver.findElements(By.xpath(MessageFormat.format(strProjectContains,strProjectName)));
		if(allProjects.size()==0)
			bStatus=false;
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return bStatus;
	}
	
	
	
	/**
	 * @param strElement - Element to be used for visibility check 'logo', 'newaudiencebtn', 'newaudiencerow','projectwindow'
	 * 'firsttimeuser','clientdropdownopen','requestclient','selectedsubclient','projecttab','audiencetab','projectsavebtn',
	 * 'projectcancelbtn',projectclosebtn,projectnamefield,projectdescfield,launchprojectbtn,deleteyes,deleteno
	 * @return - Boolean status
	 * @author Deepen Shah
	 * @since 06/04/2016
	 */
	public boolean isVisible(String strElement){
		
		boolean bStatus=true;
		
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		WebDriverWait wait = new WebDriverWait(driver, 0);
		wait.ignoring(NoSuchElementException.class);
		
		try{
			switch(strElement.toLowerCase()){
				
			case "logo": 
						wait.until(ExpectedConditions.visibilityOf(idiomLogo));						
						break;
						
			case "newaudiencebtn":
						wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strNewAudienceBtn))));
						break;
						
			case "newaudiencerow":
						wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strNewAudienceRadioBtn))));
						wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strNewAudienceTxtField))));
						wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strNewAudienceCreateAndSaveBtn))));
						wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strNewAudienceCancelBtn))));
						break;
			case "projectwindow": 
						wait.until(ExpectedConditions.visibilityOf(projectWindow));
						break;
			case "firsttimeuser":
				wait.until(ExpectedConditions.visibilityOf(selectClientMsgInDropDown));
				break;
			case "clientdropdownopen":
				wait.until(ExpectedConditions.visibilityOf(clientDropDownOpen));
				break;
			case "requestclient":
				wait.until(ExpectedConditions.visibilityOf(requestClient));
				break;
			case "selectedsubclient":
				wait.until(ExpectedConditions.visibilityOf(selectedSubClient));
				break;
			case "newproject":
				wait.until(ExpectedConditions.visibilityOf(newProjectBtn));
				break;
			case "noprojectmessage":
				wait.until(ExpectedConditions.visibilityOf(noProjectMsg));
				break;
				
			case "projecttab":
				wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strProjectTab))));
				break;
				
			case "audiencetab":
				wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strAudienceTab))));
				break;
				
			case "projectsavebtn":
				wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strProjectSaveBtn))));
				break;
				
			case "projectcancelbtn":
				wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strProjectCancelBtn))));
				break;
				
			case "projectclosebtn":
				wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strProjectCloseBtn))));
				break;
				
			case "projectnamefield":
				wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strProjectNameTxt))));
				break;
				
			case "projectdescfield":
				wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strProjectDescTxt))));
				break;
				
			case "launchprojectbtn":
				wait.until(ExpectedConditions.visibilityOf(globalProjectWindow.findElement(By.xpath(strLaunchProjectBtn))));
				break;
				
			case "deleteyes":
				wait.until(ExpectedConditions.visibilityOf(yesDeleteProject));
				break;
				
			case "deleteno":
				wait.until(ExpectedConditions.visibilityOf(noDeleteProject));
				break;
				
			case "messagewhennoclientselected":
				wait.until(ExpectedConditions.visibilityOf(messageWhenNoClientSelected));
				break;
			}
		}catch(Exception e){
			bStatus = false;
			System.out.println("ClientList_Page:isVisible-"+strElement+"###" + e.getMessage());
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return bStatus;
	}
	
	/**
	 * @param strAction
	 * @param strProjName
	 * @throws Exception
	 * @author Deepen Shah
	 * @since 20/04/2016
	 */
	public void performActionOnProject(String strAction,String strProjName) throws Exception{
		WebElement toggleButton = driver.findElement(By.xpath(MessageFormat.format(strProjectOptionsBtnContains,strProjName)));
		
		switch(strAction.toLowerCase()){
			case "edit":
					toggleButton.click();
					editProject.click();
					break;
					
			case "duplicate":
					toggleButton.click();
					duplicateProject.click();
					break;
					
			case "delete":
					toggleButton.click();					
					deleteProject.click();
					break;
					
			case "yes":
					yesDeleteProject.click();
					break;
					
			case "no":
					noDeleteProject.click();
					break;				
		}
	}
	
	
	/** This method consist of various actions on audience.
	 * 	'Create And Save' ,'CancelNew', 'Rename', 'Duplicate', 'Delete', 'CancelRename'.
 	 * 
	 * @param strAudienceName - Not required if action is for new audience
	 * @param strAction - String action name from above list
	 * 
	 * @since 21/04/2016
	 * @author Deepen Shah
	 */
	public void performActionOnAudience(String strAudienceName,String strAction){
		switch(strAction.toLowerCase()){
		case "createandsave": globalProjectWindow.findElement(By.xpath(strNewAudienceCreateAndSaveBtn)).click();
							break;
			
		case "save":globalProjectWindow.findElement(By.xpath(MessageFormat.format(strEditAudienceSaveBtnContains,strAudienceName))).click();
			break;
			
		case "rename":globalProjectWindow.findElement(By.xpath(MessageFormat.format(strAudienceRenameLinkContains,strAudienceName))).click();
			break;
			
		case "duplicate":globalProjectWindow.findElement(By.xpath(MessageFormat.format(strAudienceDuplicateLinkContains,strAudienceName))).click();
			break;
			
		case "delete":globalProjectWindow.findElement(By.xpath(MessageFormat.format(strAudienceDeleteLinkContains,strAudienceName))).click();
		//case "delete":driver.findElement(By.xpath(MessageFormat.format(strAudienceDeleteLinkContains,strAudienceName))).click();
			break;
			
		case "cancelnew":
			break;
			
		case "cancelrename":globalProjectWindow.findElement(By.xpath(MessageFormat.format(strEditAudienceCancelBtnContains, strAudienceName))).click();
			break;
			
		case "edit": globalProjectWindow.findElement(By.xpath(MessageFormat.format(strAudienceEditLinkContains,strAudienceName))).click();
			break;
			
		case "select": globalProjectWindow.findElement(By.xpath(MessageFormat.format(strAudienceSelectRadioContains,strAudienceName))).click();
			break;
		case "changeaudience":globalProjectWindow.findElement(By.xpath(MessageFormat.format(strAudienceRadioContains,strAudienceName))).click();//Added by Rohan Macwan
		break;
			
		}
	}
	
	

	/** This method checks the visibility of the elementin audience tab
	 * 	, 'Rename', 'Duplicate', 'Delete', 'Edit'.
 	 * 
	 * @param audienceName -audience name
	 * @param strCase - String action name from above list
	 * 
	 * @since 15/06/2016
	 * @author Amrutha Nair
	 */
	public boolean auidienceTabElementVisibility(String audienceName, String strCase){
	
			boolean status=true;
			
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			WebDriverWait wait = new WebDriverWait(driver, 0);
			wait.ignoring(NoSuchElementException.class);
			
			try{
				switch(strCase.toLowerCase()){
					
				case "delete": 
					WebElement deleteElement=globalProjectWindow.findElement(By.xpath(MessageFormat.format(strAudienceDeleteLinkContains,audienceName)));
					wait.until(ExpectedConditions.visibilityOf(deleteElement));						
					break;
					
				case "rename":
					WebElement renameElement=globalProjectWindow.findElement(By.xpath(MessageFormat.format(strAudienceRenameLinkContains,audienceName)));
					wait.until(ExpectedConditions.visibilityOf(renameElement));						
					break;
				case "duplicate":
					WebElement dupElement=globalProjectWindow.findElement(By.xpath(MessageFormat.format(strAudienceDuplicateLinkContains,audienceName)));
					wait.until(ExpectedConditions.visibilityOf(dupElement));						
					break;
				case "edit":
					WebElement editElement=globalProjectWindow.findElement(By.xpath(MessageFormat.format(strAudienceEditLinkContains,audienceName)));
					wait.until(ExpectedConditions.visibilityOf(editElement));						
					break;
				}
			}catch(Exception e){
					status = false;
					
				}
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				
				return status;
			
			
	}
	
	
	
	/**
	 * 
	 * 
	 * @return
	 * @author Deepen Shah
	 * @since 25/04/2016
	 */
	public int getAudienceCount(){
		return globalProjectWindow.findElements(By.xpath(strAudienceList)).size();
	}
	
	
	
	/**
	 * @param strAudienceNewName
	 * @param strAudienceOldName
	 * 
	 * @author Deepen Shah
	 * @since 25/04/2016
	 */
	public void fillAudience(String strAudienceNewName,String strAudienceOldName){
		
		WebElement audienceNameTxtField;
		
		if(strAudienceOldName.equalsIgnoreCase("")){
			audienceNameTxtField = globalProjectWindow.findElement(By.xpath(strNewAudienceTxtField));
		}else{
			System.out.println(MessageFormat.format(strEditAudienceTxtFieldContains, strAudienceOldName));
			audienceNameTxtField = globalProjectWindow.findElement(By.xpath(MessageFormat.format(strEditAudienceTxtFieldContains, strAudienceOldName)));
		}
		
		audienceNameTxtField.clear();
		audienceNameTxtField.sendKeys(strAudienceNewName);
	}
	
	
	/** This method will launch the project by clicking on project name from client home page
	 * 
	 * @param strProject - String project name
	 * @author Deepen Shah
	 * @since 25/04/2016
	 */
	public void launchProject(String strProject){
		driver.findElement(By.xpath(MessageFormat.format(strProjectRowContains, strProject))).click();
	}
	
	/**This method returns list of projects for selected client
	 * 
	 * @since 27-April-2016
	 * @return: totalProject
	 * @author Shaileh Kava
	 */
	public int totalProject(){
		int totalProject = projectList.size();
		
		return totalProject;
	}

	/**This method clicks on project by ID
	 * 
	 * @since 27-April-2016
	 * @return: void method
	 * @author Shaileh Kava
	 */
	public void clickProjectById(int projectId){
		
		projectList.get(projectId-1).click();
	}
	
	/**This method verifies whether the client home page list out the project in case if client has projects or if there is no project appropriate message comes
	 * 
	 * @since 3-May-2016
	 * @return: String
	 * @author Amrutha Nair
	 */
	
	public String verifyProjectsInClientHomePage(String client){
		String strReturnStmt=null;
		
		if (noProjectImg.size()>0){
			strReturnStmt = "There are no projects for selected project.";
		}
		else if(projectList.size()>0){
			strReturnStmt="There are projects present for the selected client :"+client;
		}
		else{
			strReturnStmt=null;
		}
		
		return strReturnStmt;
		
	}
	
	
	/** This method will create new project with name
	 *  Automation Project %TimeStamp%
	 * 
	 * @return String Project name if created or blank
	 * @throws Exception
	 * @author Deepen Shah
	 * @since 03 May 2016
	 */
	public String createNewProject(String strProjectName) throws Exception{
		
		String strMsg;
					
		if(strProjectName.equalsIgnoreCase(""))
			strProjectName="Automation Project " + BaseClass.rm.getCurrentDateTime();
		
		func_PerformAction("New Project");
		BaseClass.rm.webElementSync(newProjectWindow, "visibility");
		strMsg = "Create New Project Window appeared successfully";
		CustomReporter.log(strMsg);
		System.out.println(getClass().getSimpleName() + ": " + strMsg);
						
		findAndSaveProjectWindow(true, "");
		fillProject(strProjectName,"");				
		func_PerformAction("Save Project");
		Thread.sleep(2000);
		
		strMsg = "Project "+ strProjectName+" created successfully";
		CustomReporter.log(strMsg);
		System.out.println(getClass().getSimpleName() + ": " + strMsg);
		
		func_PerformAction("Close Project");
		
		return strProjectName;
	}
	
	/** Method to return project name by its index.
	 * 
	 * @param i Index
	 * @return String name of project
	 * @author Deepen Shah
	 * @since 03 May 2016
	 */
	public String getProjectNameById(int i){
		return projectList.get(i-1).getText().trim();
	}

	public String createNewAudience(String strAudienceName) throws Exception{
		if(strAudienceName.equalsIgnoreCase(""))
			strAudienceName = "Auto Audi " + BaseClass.rm.getCurrentDateTime();
		
		func_PerformAction("New Audience");
		CustomReporter.log("Clicked on '+New Audience' link");
					
		//Verify new elements for create new audience
		if(!isVisible("newaudiencerow"))
			throw new IDIOMException("Failed to verify new row for audience###NoNewAudienceRowFound");
		
		CustomReporter.log("Verified visibility of new audience row");
		
		//Fill the name
		fillAudience(strAudienceName,"");
		
		//Save the audience
		performActionOnAudience("", "createandsave");
		Thread.sleep(3000);
		return strAudienceName;
	}
	
	
	/** This method will return '-' separated value of audienceid-projectid-clientid
	 * 
	 * 
	 * @param strProjectName - Project Name
	 * @param strAudienceName - Audience Name, blank if called for project only
	 * @return String ids for each entity. Separate by '-'
	 * @author Deepen Shah
	 * @since 18 May 2016
	 */
	public String getAudienceProjectClientId(String strProjectName,String strAudienceName){
		String strRes="";
		
			try{
				List<WebElement> allPre = driver.findElements(By.xpath(MessageFormat.format(strProjectNameJSON, strProjectName)));
				String strPre = ((JavascriptExecutor) driver).executeScript("return jQuery(arguments[0]).text();", allPre.get(0)).toString();
				JSONObject jsonObj = new JSONObject(strPre.split("=")[1]);
				
				System.out.println("JSON : " + jsonObj.toString());
				
				int iProjectId = jsonObj.getJSONObject("model").getInt("pid");			
				int iAudiId = 0;
				if(!strAudienceName.equalsIgnoreCase("")){			
					JSONArray jArray = jsonObj.getJSONObject("model").getJSONArray("audiences");
					for(Object jO : jArray){				
						if(((JSONObject)jO).getString("name").equalsIgnoreCase(strAudienceName)){
							iAudiId = ((JSONObject)jO).getInt("aid");					
							break;
						}
					}
					strRes =iAudiId+"-";
				}
				
				int iClientId = jsonObj.getJSONObject("client").getJSONObject("model").getInt("id");		
				
				strRes = strRes+ iProjectId +"-"+iClientId;
				
				System.out.println("For project/audience, " +strProjectName + "/"+ strAudienceName+ " id:"+ strRes);
			}catch(Exception e){
				e.printStackTrace(System.out);
			}
		
		return strRes;
	}
	
	
	/** Overloaded method to work with duplicate projects 
	 * 
	 * @param strProjectName - Project Name
	 * @param strAudienceName - Audience Name, blank if called for project only
	 * @return String ids for each entity. Separate by '-'
	 * @author Deepen Shah
	 * @since 09 Jun 2016
	 */
	public String getAudienceProjectClientId(String strProjectName,String strAudienceName,int iIndex){
		String strRes="";
		
		try{
			List<WebElement> allPre = driver.findElements(By.xpath(MessageFormat.format(strProjectNameJSON, strProjectName)));
		
			String strPre = ((JavascriptExecutor) driver).executeScript("return jQuery(arguments[0]).text();", allPre.get(iIndex-1)).toString();
			JSONObject jsonObj = new JSONObject(strPre.split("=")[1]);
			
			System.out.println("JSON : " + jsonObj.toString());
			
			int iProjectId = jsonObj.getJSONObject("model").getInt("pid");			
			int iAudiId = 0;
			if(!strAudienceName.equalsIgnoreCase("")){			
				JSONArray jArray = jsonObj.getJSONObject("model").getJSONArray("audiences");
				for(Object jO : jArray){				
					if(((JSONObject)jO).getString("name").equalsIgnoreCase(strAudienceName)){
						iAudiId = ((JSONObject)jO).getInt("aid");					
						break;
					}
				}
				strRes =iAudiId+"-";
			}
			
			int iClientId = jsonObj.getJSONObject("client").getJSONObject("model").getInt("id");		
			
			strRes = strRes+ iProjectId +"-"+iClientId;
			
			System.out.println("For project/audience, " +strProjectName + "/"+ strAudienceName+ " id:"+ strRes);
			
		}catch(Exception e){
			e.printStackTrace(System.out);
		}
		return strRes;
	}
	
	
	
	/** Method to take action on duplicated audiences.
	 * 
	 * @param strCase - String type of action 'rename', 'duplicate', 'delete'
	 * @param strAudienceName - String audience name
	 * @param index - index of audience
	 * @author Deepen Shah
	 * @since 01 Jun 2016
	 */
	public void performActionOnDuplicateAudience(String strCase,String strAudienceName,int index){
		
		List<WebElement> elementsToBeUsed = null;
		
		switch(strCase.toLowerCase()){
			case "rename":elementsToBeUsed=globalProjectWindow.findElements(By.xpath(MessageFormat.format(strAudienceRenameLinkContains,strAudienceName)));
				break;
				
			case "duplicate":elementsToBeUsed=globalProjectWindow.findElements(By.xpath(MessageFormat.format(strAudienceDuplicateLinkContains,strAudienceName)));
				break;
				
			case "delete":elementsToBeUsed=globalProjectWindow.findElements(By.xpath(MessageFormat.format(strAudienceDeleteLinkContains,strAudienceName)));
				break;
		}
		
		elementsToBeUsed.get(index-1).click();
		
	}
	
	
	/** Overloaded: Method to get count of audience matching with given name
	 * 
	 * @param strAudienceName - String audience name to get count
	 * @return int
	 * @author Deepen Shah
	 * @since 01 Jun 2016
	 */
	public int getAudienceCount(String strAudienceName){
		return globalProjectWindow.findElements(By.xpath(MessageFormat.format(strAudienceDuplicateLinkContains,strAudienceName))).size();
	}
	
	/** To get watermark of new audience row
	 * @return
	 * @author Deepen Shah
	 * @since 01 Jun 2016
	 */
	public String getNewAudienceTextFieldWaterMark(){
		return globalProjectWindow.findElement(By.xpath(strNewAudienceTxtField)).getAttribute("value");
	}
	
	
	/** This method gets the selected client name. If the user is firsttime user, it will return  'select client'text
	 * @return String
	 * @author Amrutha Nair
	 * @since 02 Jun 2016
	 */
	public String getSelectedClientName(){
		String rtnStmt=null;
	
		if(isVisible("firsttimeuser")){
			rtnStmt=selectClientMsgInDropDown.getText();
		}
		else{
			rtnStmt=selectedClientName.getText();
		}
		return rtnStmt ;
	}
	
	/** This method returns the list of clients in client drop down(only main clients, No subclients)
	 * @return ArrayList
	 * @author Amrutha Nair
	 * @since 02 Jun 2016
	 */
	public List<String> returnClientsInDropDown(){
		 List<String> clientsInDropDown= new ArrayList<String>();
		 for(WebElement Client:clientListInDropDown){
			 clientsInDropDown.add(Client.getText());
			
			 }
		 
		return clientsInDropDown;
		 }
	
	

	/** This method selects 'Request Client ' from the drop down
	 * @return 
	 * @author Amrutha Nair
	 * @since 02 Jun 2016
	 */
	public void selectRequestClient(){
		requestClient.click();
	}
	
	/** This method gets the sub clients for the passed client
	 * @return Arraylist
	 * @author Amrutha Nair
	 * @since 02 Jun 2016
	 */
	
	public List<String> returnSubClientsforClient(String ClientName) throws Exception{
		 List<String> subClients= new ArrayList<String>();
		ReusableMethods rm = new ReusableMethods(driver);	
		 System.out.println("Currently selected client is : " + selectedClient.getText());
			if(!selectedClient.getText().trim().equalsIgnoreCase(ClientName)){
				clientListDropDown.click();
				Thread.sleep(2000);
				
		//		for(WebElement aElement:clientList){
				for(WebElement aElement:clientListInDropDown){
					System.out.println("Client " + aElement.getText() + "--");
					if(aElement.getText().trim().equalsIgnoreCase(ClientName)){				
						aElement.click();
					
						break;
					}
				}
			}
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
		
		 for(WebElement subClient:subClientsList){
			 subClients.add(subClient.getText());
			 }
		return subClients;
		 }
	
	/** This method gets the sub sub clients for the passed sub client
	 * @return Arraylist
	 * @author Amrutha Nair
	 * @since 02 Jun 2016
	 */
	public List<String> returnSubSubClient(String subClient){
		 List<String> subSubClient= new ArrayList<String>();
		 List<WebElement> subSubClientsList=driver.findElements(By.xpath(MessageFormat.format(subSubClients, subClient)));
		 for (WebElement element:subSubClientsList){
			 subSubClient.add(element.getText());
		 }
		return subSubClient;
		
	}
	
	
	/** This method selects sub client or sub sub client by name passed 
	 * @return 
	 * @author Amrutha Nair
	 * @since 02 Jun 2016
	 */
	public void selectsubClientByName(String subClient){
		WebElement subClientElement=driver.findElement(By.xpath(MessageFormat.format(subClientName, subClient)));
		subClientElement.click();
	}
	
	/** This method returns all the clients , sub clients , sub sub clients which are visible in client home page 
	 * @return ArrayList
	 * @author Amrutha Nair
	 * @since 03 Jun 2016
	 */
	public List<String> returnAllClientsForUser(){
		selectedClient.click();
		 List<String> clientsInDropDown= new ArrayList<String>();
		 for(WebElement Client:clientListInDropDown){
			 clientsInDropDown.add(Client.getText());
			
			 driver.findElement(By.xpath(MessageFormat.format(clientName, Client.getText().toLowerCase().trim()))).click();
			 for(WebElement subClient:subClientsList){
				 clientsInDropDown.add(subClient.getText());
				 selectsubClientByName(subClient.getText().trim().toLowerCase());
				 List<WebElement> subSubClientsList=driver.findElements(By.xpath(MessageFormat.format(subSubClients, subClient.getText().toLowerCase().trim())));
				 for (WebElement element:subSubClientsList){
					 clientsInDropDown.add(element.getText());
				 }
				}
			 selectedClient.click();
		 }
		return clientsInDropDown;
			 
			 
	}

	
	/**
	 * @param strAction
	 * @param strProjName
	 * @param iIndex
	 * @throws Exception
	 * @author Deepen Shah
	 * @since 13 Jun 2016
	 */
	public void performActionOnDuplicateProject(String strAction,String strProjName,int iIndex) throws Exception{
		List<WebElement> projects = driver.findElements(By.xpath(MessageFormat.format(strProjectOptionsBtnContains,strProjName)));
		WebElement toggleButton = projects.get(iIndex-1);
		
		switch(strAction.toLowerCase()){
			case "edit":
					toggleButton.click();
					editProject.click();
					break;
					
			case "duplicate":
					toggleButton.click();
					duplicateProject.click();
					break;
					
			case "delete":
					toggleButton.click();					
					deleteProject.click();
					break;
					
			case "yes":
					yesDeleteProject.click();
					break;
					
			case "no":
					noDeleteProject.click();
					break;				
		}
	}
	
	
	
	//################ End  IDIOM 2.3 ####################
	
	
	
	
	/** func_VerifyPasswordErrorMessage()	 * 
	 *This method is used to perform click operation on any client in the client list page
	 *Created By:Abhishek Deshpande
	 *Created On:03 Aug 2015
	 *Modified By | Description of Modification:Amrutha Nair : Added client Ebay
	 *Modified On: 12th August	
	 */
	
	
	public void func_SelectClient(String client) throws InterruptedException{
		Thread.sleep(6000);
		switch (client.toLowerCase()) {
		
	/*	case "Ecommerce":
		CustomReporter.log("Click on Industry Verticle");
		
	    img_industryVerticals.click();
		Thread.sleep(5000);
		img_Ecommerce.click();
		break;
	*/	
		case "delta":
		CustomReporter.log("Click on Delta client");
		img_delta.click();
		break;			
		
		case "ecommerce":
		CustomReporter.log("Click on ECommerce");
		img_Ecommerce.click();
		break;		
		
		case "missing a client?":
		CustomReporter.log("Click on Missing a client");
		img_missingAClient.click();
		
		break;
		
		case "vsclient":
		CustomReporter.log("Click on VS Limited Client");
		client_VS.click();
		break;
		 
		case "ebay":
		ebayClient.click();
		break;
		
		case "sprintboost":
			SprintBoost.click();
			break;
			
		case "schema v2 test client":
			SchemaV2Client.click();
		break;
		
		
		
			
		}
	}	

	/** func_ClientListPageElementExist((String Case)	 * 
	 *This method performs various actions depenidng on Switch case provided
	 *Created By:Amrutha Nair
	 *Created On:12 Aug 2015
	 *Modified By | Description of Modification:
	 *Modified On:
	 * @throws InterruptedException 
	 * @throws Exception 
	 */	
public void func_PerformAction(String element) throws InterruptedException{
	
	switch (element){	
	
	
	case "ClientLogo":
	      BaseClass.rm.clickWebElement(img_clientLogo);
	      break;
	
	case "ChangeClient":
	     BaseClass.rm.clickWebElement(btn_changeClient);	
	     break;
	
	case "AdminAccess":
		MenuIcon.click();
		MenuIcon.click();
		System.out.println("Clicking on Admin access option");
		Thread.sleep(6000);
		AdminAccess.click();
		break;
	
	case "Logout":		
		MenuIcon.click();
		Thread.sleep(5000);
		link_Logout.click();
	    break;
	
	case "Missing a client?":
	     BaseClass.rm.clickWebElement(img_missingAClient);
	     break;
	
	case "Help":
		 icon_Help.click();
	     break;
	
	case "IdiomIcon":
		  BaseClass.rm.clickWebElement(icon_IdiomName);
		  break;
		
	case "My Account":
		 MenuIcon.click();
		 Thread.sleep(3000);
		 MyAccount.click();
		 break;
		
		//IDIOM 2.3
	case "New Project": 
		  newProjectBtn.click();
		  Thread.sleep(2000);
		  break;
						
	case "Save Project": 
		  globalProjectWindow.findElement(By.xpath(strProjectSaveBtn)).click();
		  break;
						 
	case "Cancel Project": 
		  globalProjectWindow.findElement(By.xpath(strProjectCancelBtn)).click();
	 	  break;
	 					
	case "Close Project": 
		  globalProjectWindow.findElement(By.xpath(strProjectCloseBtn)).click();
		  break;
						
	case "New Audience": 
		  globalProjectWindow.findElement(By.xpath(strNewAudienceBtn)).click();
		  break;
						
	case "Audience Tab": 
		  globalProjectWindow.findElement(By.xpath(strAudienceTab)).click();
		  break;
						
	case "Launch Project":
		  try {
			BaseClass.rm.webElementSync(globalProjectWindow.findElement(By.xpath(strLaunchProjectBtn)), "visibility");
			globalProjectWindow.findElement(By.xpath(strLaunchProjectBtn)).click();
			
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  break;
    case "Project Tab":
		globalProjectWindow.findElement(By.xpath(strProjectTab)).click();
		break;
	}
}
	
	
	
/** func_ClientListPageElementExist((String Case)	 * 
 *This method is used to check the existence of any web element of Client list page and returns boolean value. When it is true it means element is found and when it is false it means it is not found.
 *Created By:Rohan Macwan
 *Created On:06 Aug 2015
 *Modified By | Description of Modification:Amrutha Nair | Updated with new method call from ReusableMethods and  added few new switch statements
 *Modified On: 11th August	
 */	
public boolean func_ClientListPageElementExist(String Case){
	
	boolean result=false;
	
	switch (Case) {
	
	case "Missing a client?":	
		result=BaseClass.rm.webElementExists(img_missingAClientLst);	
	break;
	
	case "Help Icon":
		result=BaseClass.rm.webElementExists(icon_Help_lst);		
	break;
	
	case "Help Content":
		result=help_Content.isDisplayed();
		break;
		
	case "IDIOM Logo":
		result=BaseClass.rm.webElementExists(img_IdiomLogo_lst);	
	break;
	
	case "Select a Client":
		result=BaseClass.rm.webElementExists(label_SelectaClientlst);	
	break;
	
	case "Logout":
		result=BaseClass.rm.webElementExists(link_Logout_Exists);		
		break;
	
	case "VSClient":
		result=BaseClass.rm.webElementExists(client_VS_List);
		break;
		
	case "Clients":
		result=BaseClass.rm.webElementExists(clientList);//changed by Sunil 10 May 2016
		break;
		
	//Modified By|Description of Modification:Rohan Macwan |Added case for "ClientCard"	
	case "ClientCard":
		result=BaseClass.rm.elementExists(ClientCard);
		break;
				
	//Modified By|Description of Modification:Rohan Macwan |Added case for "Missing A Client? Logo"	
	case "Missing A Client? Logo":
		result=BaseClass.rm.elementExists(MissingAClientLogo);
		break;
		
	case "Admin Access Present":	
		MenuIcon.click();
		result=BaseClass.rm.webElementExists(AdminAccessPresent);
	break;
	
	case "MyAccountExist":
		MenuIcon.click();
		result=BaseClass.rm.webElementExists(MyAccountPresent);
	break;
		
	case "ClientList Page Exists":
		result=BaseClass.rm.webElementExists(ClientListPage);
		break;
		
		//IDIOM 2.3
	case "audiencetab": try{
								result = BaseClass.rm.webElementSync(globalProjectWindow.findElement(By.xpath(strAudienceTab)), "visibilitynowait");								
							}catch(Exception e){
								System.out.println("Fails to check visibility of Audience tab in project window");
								e.printStackTrace();								
							}
						break;
						
	case "audiencetablabel": try{
									result = BaseClass.rm.webElementSync(globalProjectWindow.findElement(By.xpath(strAudiencePageLabel)), "visibility");
									
								}catch(Exception e){
									System.out.println("Fails to verif landed page is Audience page.");
									e.printStackTrace();																		
								}
								break;
		
	}
	return result;
}

/** func_CheckEmailClient(String Evalue, String EmailID, boolean BCCStatus) * 
 *This method determines whether mail to: link is opening the correct email client message window. E.g. On client list page 'Missing a Client' Link click will open relevant configured Outlook Message Window
 *Created By:Rohan Macwan
 *Created On:07 Aug 2015
 *Modified By | Description of Modification:	
 */	
public boolean func_CheckEmailClient(String Evalue, String EmailID, boolean BCCStatus)throws InterruptedException, AWTException 
{
	int endStep = 0;
	
	Robot robot = new Robot();

    Thread.sleep(3000);
    robot.keyPress(KeyEvent.VK_SHIFT);
    robot.keyPress(KeyEvent.VK_TAB);
    
    robot.delay(100);
    robot.keyRelease(KeyEvent.VK_SHIFT);
    robot.keyRelease(KeyEvent.VK_TAB);

    robot.delay(100);
    robot.keyPress(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_A);

    robot.delay(100);
    robot.keyRelease(KeyEvent.VK_CONTROL);
    robot.keyRelease(KeyEvent.VK_A);
    
    robot.delay(100);
    robot.keyPress(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_C);
    robot.delay(100);
    robot.keyRelease(KeyEvent.VK_CONTROL);
    robot.keyRelease(KeyEvent.VK_C);
    robot.delay(100);
    
    Thread.sleep(3000);


    Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
    
    try {
    	
    	String wintext = (String) t.getTransferData(DataFlavor.stringFlavor);

        if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) 
        {

	        if(Evalue.equals(wintext))
	        {
	        	CustomReporter.log("Email Client is successfully opened with proper subject line");
	        	
	        }
	        else
	        {
	        	BaseClass.testCaseStatus=false;
	        	CustomReporter.errorLog("Email Client is successfully opened but subject line is not proper");
	        }
	    
        }
        
        if(BCCStatus == true){
        	endStep = 3;
        }else{
        	endStep = 2;
        }
       
        
        for (int i=0;i<endStep;i++)
        {      	
        	Thread.sleep(3000);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_TAB);
            
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_TAB);
            
            if (i==2){
            	
            	robot.delay(100);
	            robot.keyPress(KeyEvent.VK_CONTROL);
	            robot.keyPress(KeyEvent.VK_A);

	            robot.delay(100);
	            robot.keyRelease(KeyEvent.VK_CONTROL);
	            robot.keyRelease(KeyEvent.VK_A);
	            
	            robot.delay(100);
	            robot.keyPress(KeyEvent.VK_CONTROL);
	            robot.keyPress(KeyEvent.VK_C);
	            robot.delay(100);
	            robot.keyRelease(KeyEvent.VK_CONTROL);
	            robot.keyRelease(KeyEvent.VK_C);
	            robot.delay(100);
	            
	            Thread.sleep(3000);
	            
	            Transferable t1 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            	String Emlid = (String) t1.getTransferData(DataFlavor.stringFlavor);
	            CustomReporter.log("Email Address is: "+Emlid+" Passed email id: "+EmailID);
	            
	            if(Emlid.toLowerCase().contains(EmailID.toLowerCase())){
	            	CustomReporter.log("Correct Email Id is present");
	            }else{
	            	CustomReporter.errorLog("Wrong Email Id is mentioned");
		        	BaseClass.testCaseStatus=false;
		        }
	            
	            if(Evalue.equals(wintext) && EmailID.equals(Emlid)){
	            	BaseClass.testCaseStatus=true;
	            }
	           
            }
            
        }
        
      //Below code will help us to close outlook!!
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_F4);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_F4);
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_ENTER);
        
       
        
    } catch (Exception e) {
    }
    return BaseClass.testCaseStatus;
	
}	


/** func_DoubleClickElement(String elementName, WebDriver driver)	 * 
 *This method does double click on the  element which is provided as input
 *Created By:Amutha Nair
 *Created On:07 Aug 2015
 *Modified By | Description of Modification:Abhishek Deshpande|Made the method generic by inputing "Element" to be double clicked at run time 
 */	

public void func_DoubleClickElement(String elementName)
{
			Actions action = new Actions(driver);		
			switch (elementName){
			
			case "Logout":
			action.moveToElement(link_Logout).doubleClick().build().perform();
			break;
			}
}

/** func_GetCountFromClientImage(String clientName)	  
 *This method is used to extract saved idiom count from Client Image e.g extract "113" from 113 Saved Idioms
 *Created By: Shailesh Kava
 *Created On: 17 Aug 2015
 *Modified By | Description of Modification:	
 */	
public int func_GetCountFromClientImage(String clientName){
	
	Integer totalSavedIdioms = 0;
	
	switch(clientName){
	
	case "VS Client":
		String getSavedIdiomNameAndCount = saved_idioms_Client_VSLimited.getText().trim();
		
		if(getSavedIdiomNameAndCount.contains(" ")){
			getSavedIdiomNameAndCount = getSavedIdiomNameAndCount.substring(0, getSavedIdiomNameAndCount.indexOf(" "));
			totalSavedIdioms = Integer.parseInt(getSavedIdiomNameAndCount);
		}
	break;
	case "Ecommerce":
		 getSavedIdiomNameAndCount = saved_idioms_Client_Ecommerse.getText().trim();
		
		if(getSavedIdiomNameAndCount.contains(" ")){
			getSavedIdiomNameAndCount = getSavedIdiomNameAndCount.substring(0, getSavedIdiomNameAndCount.indexOf(" "));
			totalSavedIdioms = Integer.parseInt(getSavedIdiomNameAndCount);
		}
	break;
	
	}
	
	return totalSavedIdioms;
}

/** func_DoMouseHoverOnClientCard((String Case)	 * 
 *This method is used to check the mouse hover effect of Client Card on Client List page 
 *Created By:Rohan Macwan
 *Created On:18 Aug 2015
 *Modified By 
 *Modified On: 	
 */	
public boolean func_DoMouseHoverOnClientCard(String Case){
	
	boolean result=false;
	
	switch (Case) {
	
	case "VSClient":	
		result=BaseClass.rm.elementExists(client_VS);
		
		if (result==true)
		{
			Actions action = new Actions(driver);
			action.moveToElement(client_VS).build().perform();
			//action.moveToElement(client_VS);
						
		}
	
	break;
	
	case "Ecommerce":	
		result=BaseClass.rm.elementExists(img_industryVerticals);
		
		if (result==true)
		{
			Actions action = new Actions(driver);
			action.moveToElement(img_industryVerticals).build().perform();
			//action.moveToElement(client_VS);
						
		}
	
	break;
			
	}
	return result;
}

/** func_CheckLogosPresent(WebDriver driver) * 
 *This method is used to check whether all Client Cards have its Logo in it 
 *Created By:Rohan Macwan
 *Created On:19 Aug 2015
 *Modified By 
 *Modified On: 	
 */	
public boolean func_CheckLogosPresent(){
	
	boolean result=false;
	boolean Logosresult=false;
	boolean MisAClientLogoresult=false;
		
	Integer Clg = ClientLogos.size();
	Integer Cll = clients_lst.size();
	
	
	if (Clg.equals(Cll)){
		
		CustomReporter.log("Client Logos are present for All Clients");
		Logosresult=true;
			
	}
	else
	{
		CustomReporter.errorLog("Client Logos is/are missing for One/Multiple/All Clients");
		Logosresult=false;
	}
	
	
	if (func_ClientListPageElementExist("Missing A Client? Logo")){
		CustomReporter.log("Missing a Client Logo is present");
		MisAClientLogoresult=true;
	}
	else
	{
		CustomReporter.errorLog("Missing a Client Logo is missing");
		MisAClientLogoresult=false;
	}
	
	if ((MisAClientLogoresult==true) && (Logosresult==true)){
		result=true;
	}
	else
	{
		result=false;
		BaseClass.testCaseStatus=false;
	}
	
	return result;
}

/** func_getArrtibute_src_ClientImage(String attributeName) * 
 *This method is used to return src for provided client name - pl. note: First get client name from img alt tage  
 *Created By: Shailesh Kava
 *Created On: 24 Aug 2015
 *Modified By 
 *Modified On: 	
 */	
public String func_getArrtibute_src_ClientImage(String attributeName){
	String attributeVal = null;
	
	try{
		WebElement clientXPath = driver.findElement(By.xpath("//img[contains(@ng-alt,'"+attributeName+"')]"));
		
		boolean f = clientXPath.isDisplayed();
		attributeVal = clientXPath.getAttribute("src");
		Thread.sleep(2000);
		clientXPath.click();
		
	}catch(Exception E){
		CustomReporter.errorLog("Element not found");
		E.printStackTrace();
	}
	
	return attributeVal;
}


/**func_getClientsPresent() * 
 *This method gets the clients present for useer 
 *Created By: Rohan
 *Created On: 24 Aug 2015
 *Modified By 
 *Modified On: 	
 */	
public String[] func_getClientsPresent(){
	String[] clients= new String[20];
	int i=0;
	
	 List<WebElement>   ClientName=driver.findElements(By.xpath("//*[@ng-click='onClientClick(client)']/*[@class='name ng-binding']"));
	for (WebElement client: ClientName){
		clients[i]=client.getText();
		System.out.println(clients[i]);
		i=i+1;
	}
	return clients;
}

/** ArrayList<String> func_getList(String element)
* This method is used to return list of web element available in specified listElement
* Created By: Shailesh Kava
* Created On: 11 Jan 2016
* Modified By | Description of Modification:
*/

public ArrayList<String> func_getList(String element){
	ArrayList<String> getList = new ArrayList<String>();
	
	switch(element){
	case "userclients":
		for(WebElement clients:listClients){
			getList.add(clients.getText());
		}
	break;
	
	}
	
	return getList;
}

/** This method is used to get private WebElement out of this class.
 *  Make sure that element name should be matched.
 * 
 * @param strWebElement - String name of the WebElement variable.
 * @return Returns WebElement
 * @CreatedOn 01/12/2016
 * @author Deepen Shah
 */
public WebElement func_GetLocalWebElement(String strWebElement) {
	WebElement retWebElement=null;
	try{
		
	
		Field aField = this.getClass().getDeclaredField(strWebElement);
		aField.setAccessible(true);
		retWebElement = (WebElement) aField.get(this);
	
	}catch(Exception e){
		
	}
	return retWebElement;
}

/** ArrayList<String> func_getListOfAudiencesForEditedProject()
* This method is used for getting list of Audiences present in Edited Project overlay
* Created By: Rohan Macwan
* Created On: 20 May 2016
* Modified By | Description of Modification:
*/

public ArrayList<String> func_getListOfAudiencesForEditedProject()
{
	ArrayList<String> getList = new ArrayList<String>();
	
	List<WebElement> listAud = globalProjectWindow.findElements(By.xpath(strListAudiencesText));
	
	for(WebElement Audiences:listAud){
		getList.add(Audiences.getText().trim());
	}
	
	return getList;
}

/** ArrayList<String> func_getListOfAudiencesForSelectedProject(String projectName)
 * Param: projectName - send the project name
 * return:  list of audiences for the selected project
 * This method is used for getting list of Audiences present in selected project in client home page
 * Created By: Abhishek Deshpande
 * Created On: 03 JUNE 2016
 * Modified By | Description of Modification:
*/

public ArrayList<String> func_getListOfAudiencesForSelectedProject(String projectName)
{
	ArrayList<String> getList = new ArrayList<String>();
	
	List<WebElement> listAud = driver.findElements(By.xpath(MessageFormat.format(strListOfAudiencesInProject,projectName)));
	System.out.println("No of audiences are "+listAud.size());
	
	for(WebElement Audiences:listAud){		
		getList.add(Audiences.getAttribute("innerText"));
	}	
	
	return getList;
}
/** <p>This Method is used to verify that hover effect is present on Add New Audience link<p>
 * @return boolean
 * @author Shailesh Kava
 * @throws Exception 
 * @since 10June2016
 */
public boolean verifyMouseHoveEffectOnAddAudience() throws Exception{
	boolean bHover = true;
	
	Thread.sleep(3000);
	Actions action = new Actions(driver);
	
	action.moveToElement(globalProjectWindow.findElement(By.xpath(strNewAudienceBtn))).build().perform();
	
	System.out.println(globalProjectWindow.findElement(By.xpath(strNewAudienceBtn)).getCssValue("cursor"));
	
	if(!globalProjectWindow.findElement(By.xpath(strNewAudienceBtn)).getCssValue("cursor").equalsIgnoreCase("pointer"))
		 bHover = false;	
	
	 return bHover;
}

	/**
	 * @return
	 * @author Deepen Shah
	 * @since 14 Jun 2016
	 */
	public int getOldProjectId(){
		int i=0;
		try{
				
		      Date todaysDate = BaseClass.rm.getTodaysDate();
					    
		      for(i=0;i<createdDatesList.size();i++){
		    	  Date tempD = new SimpleDateFormat(BaseClass.strDateFormat).parse(createdDatesList.get(i).getText().trim());
		    	  
		    	  if(tempD.before(todaysDate))
		    		  break;
		      }    
		      
		      if(i>createdDatesList.size() || createdDatesList.size()==0){
		    	  i=-1;
		    	  CustomReporter.errorLog("Not able to find any existing project created before than today");
		      }else{
		    	  i = i+1;
		    	  CustomReporter.log("Found project created before than today. ID: " + i);
		      }
			
		}catch(Exception e){
			e.printStackTrace(System.out);
			i = -1;
		}
		
		return i;
	}
	
	/**
	 * @return
	 * @author Deepen Shah
	 * @since 15 Jun 2016
	 */
	public int getOldModifiedProject(){
		int i=0;
		try{
				
		      Date todaysDate = BaseClass.rm.getTodaysDate();
					    
		      for(i=0;i<modifiedDatesList.size();i++){
		    	  Date tempD = new SimpleDateFormat(BaseClass.strDateFormat).parse(modifiedDatesList.get(i).getText().trim());
		    	  
		    	  if(tempD.before(todaysDate))
		    		  break;
		      }    
		      
		      if(i>modifiedDatesList.size() || modifiedDatesList.size()==0){
		    	  i=-1;
		    	  CustomReporter.errorLog("Not able to find any existing project modified before than today");
		      }else{
		    	  i = i+1;
		    	  CustomReporter.log("Found project modified before than today. ID: " + i);
		      }
			
		}catch(Exception e){
			e.printStackTrace(System.out);
			i = -1;
		}
		
		return i;
	}
	
	/** This method will give created date and modifed date for particular project
	 * 
	 * @param strProjectName - String project name
	 * @param iIndex - index to work with duplicate project
	 * @return String createddate#modifieddate
	 * @author Deepen Shah
	 * @since 14 Jun 2016
	 */
	public String getCreatedModifiedDateByProjectName(String strProjectName,int iIndex){
		
		String strCreated="";
		String strModified = "";
		int iFound=0;
		
		for(WebElement aRow:projectRows){
			if(aRow.findElement(By.xpath(strProjectNameButton)).getText().trim().equalsIgnoreCase(strProjectName)){
				iFound++;
				if(iFound==iIndex){
					strCreated=aRow.findElement(By.xpath(strCreatedDateValue)).getText().trim();
					strModified=aRow.findElement(By.xpath(strModifiedDateValue)).getText().trim();
				}				
			}
			
		}
		
		if(strCreated.equalsIgnoreCase("") || strModified.equalsIgnoreCase(""))
			CustomReporter.errorLog("No such project " + strProjectName + " found in the list");
		
		return strCreated+"#"+strModified;
	}
	
	/** This will verify that duplicate project is available in the client home page or not. 
	 * 
	 * @param strProjectName - String. Project name to be checked
	 * @return - Boolean. True/false
	 * @since 15/06/2016
	 * @author Abhishek Deshpande
	 */
	public boolean verfiyDuplicateProjectExist(String strProjectName){
		boolean bStatus = false;
		
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		List<WebElement> allProjects = driver.findElements(By.xpath(MessageFormat.format(strProjectContains,strProjectName)));
		if(allProjects.size()==2)
			bStatus=true;
		
		CustomReporter.log("No of projects with name "+strProjectName+ " are "+allProjects.size()+ " present in client home page");
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return bStatus;
	}
	
	/**This method will return selected audience name
	 * 
	 * @return strSelectedAudName
	 * @author Shailesh Kava
	 * @since 20-June-2016
	 */
	public String getSelectedAudNameFromOpenProjectWindow(){
		String strSelectedAudName = null;
		
		strSelectedAudName = globalProjectWindow.findElement(By.xpath(selectedAudienceName)).getText();
		
		return strSelectedAudName;
	}

}
