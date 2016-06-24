/**
 * Audience_Page
 * List Of Methods:
 * func_AudienceCheckPopulationCssAttibutesWhetherPrper():This method is used to check the position of Separator in Population section
 * func_AudienceCheckPostionOfUnknownFilter():This method is used to check the position of Unknown Filter Card in Demographic Sub sections 
 * func_AudiencePageElementExist(String elementName):This method is used to check the existence of any web element of Audience page
 * func_Behavour_Gauge(String filter):This method verifies the behaviour population gauge in right side
 * func_capture_IndividualFilter(String filter):This method captures each subfilter for provided filter card
 * func_CaptureList(String Case):This method collects the list of values depending on the condition in arraylist
 * func_CheckFilterElementExists(String Case,String Item):This method confirms whether the demographics drop down selected is proper 
 * func_CheckHoverOnComponents(String Case):This method checks hovering effect is proper for component cases passed
 * func_CheckIdiomCreated(String IdiomName):This method checks whether the idiom with passed name has been created really
 * func_func_ClickElement(String element):This method is used to click on elements present in Audience page
 * func_getCSSSVal(String cardName, String cssValueType):This method clicks on given card name retuns the cssValue on hover as of now "background-color" and "border-color"
 * func_GetLocalWebElement(String strWebElement):This method is used to get private WebElement out of this class.
 * func_getValue(String Case):This method gets the string value of the element case passed
 * func_OpenNewwindow(String Url):This method opens new window and control is passing  to the new window
 * func_RClientElementExists(String item):This method checks whether the client icon is present at the right top and whether "Logout","Save", "Change Client", "Manage" and "Rename" are existing
 *func_returnID(String Filter):This method returns id of the filter card which can be used by other methods in Audience page
 *func_SelectAll_Then_DeselectOneByOne() :This method is used to Verify whether Deselect All link is present on the page when User manually deselects all the Cards for each section on the Audience page
 *func_selectCardByName(String cardName):This method clicks on given card name
 *func_selectFilterAndPreserveNames():This method selects Filters and stores name of selected filter in Array List
 *func_SelectFilters(String filter):This method select filters depending on input provided
 *func_TopMenuElementExists(String item):This method checks whether the menu icon is present at the right top and whether "Logout" "Admin Access" and My account are under it
 *func_VerifyCSSComponent(String FilterCards, String CSSType, String expected):This method checks whether the css values are exected for each filter data passed
 *func_verifyExpectedBreadCrumbandValuess(String[] expectedSecondaryMenu):This method will verify breadcrumb values and its position of Secondry Navigation
 *func_verifyIdiomName(String idiomName):This methiod checks whether idiom with name in input is existing
 *func_VerifyStyleChange_OnHover(String filter, String css_type, String expected) :This method verifies card's style change on hover

 */
package com.IDIOM.pages;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.awt.AWTException;
import java.awt.Robot;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.reports.CustomReporter;

import common.BaseClass;



public class Audience_Page {
	
	WebDriver driver;
	public Audience_Page(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	@FindBy(xpath="//a[text()='Audience']")
	private WebElement label_Audience;
	
	@FindBy(xpath="//a[text()='Analyze']")
	private WebElement label_Analyze;
	
	@FindBy(xpath="//a[text()='Architect']")
	private WebElement label_Architect;
	
	@FindBy(xpath="//a[text()='Activate']")
	private WebElement label_Activate;
	
	@FindBy(xpath="//a[@title='Help']")
	private WebElement icon_Help;
	
	@FindBys(@FindBy(xpath="//a[@title='Help']"))
	private List<WebElement> icon_Help_list;
	
	@FindBys(@FindBy(xpath="//a[text()='Audience']"))
	private List<WebElement> label_Audience_list;
	
	@FindBy(xpath="//*[@ng-click='onAnalyze()']")
	private WebElement btn_AnalyzeAudience;	
	
	@FindBys(@FindBy(xpath="//section[@id='g-2']//ng-transclude"))
    private List<WebElement> Demographics;

	
	@FindBy(xpath="//span[@class='audience-name ng-binding']")
	private WebElement created_Idiom_Name;
	
	@FindBy(xpath="//*[@class='selected-client dropdown-parent ng-isolate-scope']")
	private WebElement right_TopClient;
	
	@FindBy(xpath="//*[@class='dropdown-list']/li[3]/a")
	private WebElement right_Manage;
	
	@FindBys(@FindBy(xpath="//*[@class='selected-client dropdown-parent ng-isolate-scope']"))
	private List<WebElement> right_TopClient_Lst;
	
	@FindBys(@FindBy(xpath="//*[@class='selected-client dropdown-parent ng-isolate-scope']//*[@ng-href='#/clients']"))
	private List<WebElement> right_ChangeClient_Lst;
	
	@FindBys(@FindBy(xpath="//*[@class='dropdown-list']/li[3]/a"))
	private List<WebElement> right_Manage_Lst;
	
	@FindBy(xpath="//*[@class='selected-client dropdown-parent ng-isolate-scope']//*[@ng-href='#/clients']")
	private WebElement link_ChangeClient;
	
	@FindBy(xpath="//*[@class='dropdown-list']/li[1]/a")
	private WebElement save_idiom;
	
	@FindBys(@FindBy(xpath="//*[@class='options-box ng-scope chosen']/p"))
	private List<WebElement> audience_Filters;
	
	@FindBy(xpath="//*[@ng-click='logout()']")
	private WebElement right_Logout;
	
	@FindBys(@FindBy(xpath="//*[@ng-click='logout()']"))
	private List<WebElement>  right_Logout_Lst;
	
	@FindBy(xpath="id('sg-2-1')//*[text()='No']")
	private WebElement hasChild_No;
	
	@FindBy(xpath="//p[text()='View Swimwear']")
	private WebElement view_SwimWear;
	
	@FindBy(xpath="//p[text()='View Clothing']")
	private WebElement view_Clothing;
	
	@FindBy(xpath="id('sg-2-2')//*[text()='Less than 25k']")
	private WebElement hhIncome_lssthan25K;
	
	@FindBy(xpath="id('sg-2-2')//*[text()='25k-39.999k']")
	private WebElement hhIncome_25Kto39;	
	
	@FindBys(@FindBy(xpath="//a[text()='Audience']"))
	private List<WebElement> label_Audiencelst;
	
	@FindBys(@FindBy(xpath="//a[text()='Analyze']"))
	private List<WebElement> label_Analyzelst;
	
	@FindBys(@FindBy(xpath="//a[text()='Architect']"))
	private List<WebElement> label_Architectlst;
	
	@FindBys(@FindBy(xpath="//a[text()='Activate']"))
	private List<WebElement> label_Activatelst;
	
	@FindBys(@FindBy(xpath="//a[@title='Help']"))
	private List<WebElement> icon_Helplst;	
	
	@FindBy(xpath="//*[@class='sliding-panel audience-profile']//*[@class='population-projected show']//*[@class='percent']/span")
	 private WebElement label_percent_Projected;
	 
	 @FindBy(xpath="//*[@class='sliding-panel audience-profile']//*[@class='population-projected show']//*[@class='ng-binding']/span")
	 private WebElement label_binding_Projected;
	
	 @FindBys(@FindBy(xpath="//div[@class='dropdown-box open']"))
	 private List<WebElement> dropDown_Open;
	 
	@FindBys(@FindBy(xpath="//*[text()='Audience saved.']"))
	private List<WebElement> audience_SavedText;
	
	@FindBys(@FindBy(xpath="//div[@class='dropdown-box open']"))
	private List<WebElement> drpDown_Open;
	
	@FindBys(@FindBy(xpath="//*[@class='icon ion ion-checkmark-round']"))
	private List<WebElement> save_Icon;
	
	@FindBys(@FindBy(xpath="//*[@class='icon ion ion-edit']"))
	private List<WebElement> rename_Icon;
	
	@FindBys(@FindBy(xpath="//*[@class='icon ion ion-clipboard']"))
	private List<WebElement> manage_Icon;
	
	@FindBys(@FindBy(xpath="//*[@class='icon ion ion-arrow-swap']"))
	private List<WebElement> changeClient_Icon;
	
	@FindBys(@FindBy(xpath="//*[@class='icon ion ion-android-exit']"))
	private List<WebElement> logout_Icon;
	
	@FindBys(@FindBy(xpath="//*[@class='dropdown-list']/li[1]/a"))
	private List<WebElement> save_idiom_lst;
	
	@FindBys(@FindBy(xpath="//*[@class='dropdown-list']/li[2]/a"))
	private List<WebElement> rename_idiom_lst;
	
	@FindBy(xpath="//section[@id='g-0']//h2")
	private WebElement drpDown;
	
	@FindBys(@FindBy(xpath="//span[@class='ng-binding ng-isolate-scope']"))
	private List<WebElement> dropDown_filter;
	
	
	@FindBy(xpath="//*[@class='dropdown-list dropdown-scroll']//span[@smooth-scroll='g-0']")
	private WebElement futureBehaviour;
	
	@FindBys(@FindBy(xpath="//div[@class='meter-wrapper layout horizontal ng-scope']//div[@class='meter behavior']"))
    private List<WebElement> rightHandSide_futureBars;

	@FindBy(xpath="//*[@class='dropdown-list dropdown-scroll']//span[@smooth-scroll='g-1']")
	private WebElement past_Actions;
	
	@FindBy(xpath="//*[@class='dropdown-list dropdown-scroll']//span[@smooth-scroll='g-2']")
	private WebElement demographicsDropDown;
	
	@FindBy(xpath="//*[@class='dropdown-list dropdown-scroll']//span[@smooth-scroll='sg-2-0']")
	private WebElement age;
	
	@FindBy(xpath="//*[@class='dropdown-list dropdown-scroll']//span[@smooth-scroll='sg-2-1']")
	private WebElement hasChild;
	
	@FindBy(xpath="//*[@class='dropdown-list dropdown-scroll']//span[@smooth-scroll='sg-2-2']")
	private WebElement hh_Income;
	
	@FindBy(xpath="//*[@class='dropdown-list dropdown-scroll']//span[@smooth-scroll='sg-2-3']")
	private WebElement hh_Size;
	
	@FindBy(xpath="//*[@class='dropdown-list dropdown-scroll']//span[@smooth-scroll='sg-2-4']")
	private WebElement highest_edu;
	
	@FindBy(xpath="//*[@class='dropdown-list dropdown-scroll']//span[@smooth-scroll='sg-2-5']")
	private WebElement location;
	

	
	

	@FindBy(xpath="//p[text()='Checkout From VS Limited']")
	private WebElement Checkout_From_VS_Limited;
	
	@FindBys(@FindBy(xpath="//button[@class='select']//span[@class='deselect-all']"))
	private List<WebElement> deselect_all;

	@FindBys(@FindBy(xpath="//button[@class='select']//*[@class='select-all']"))
	private List<WebElement> select_All;	
	
	@FindBys(@FindBy(xpath="//*[@class='options-box ng-scope']"))
	private List<WebElement> DeselectedFilters;
	
	@FindBys(@FindBy(xpath="//*[@id='sg-2-0']//span[@class='select-all']"))
	private List<WebElement> selectAll_Age;
	
	@FindBys(@FindBy(xpath="//div[@class='flex secondary ng-binding']/ul[@class='nav']/li"))
	private List<WebElement> secondaryNavigation;
	
	@FindBy(xpath="//div[@class='audience-container']//b[1]")
	  private WebElement Population;
	
	@FindBy(xpath="//*[@ng-if='gaugePopulation']")
	 private WebElement population_Gauge;
	
	@FindBys(@FindBy(xpath="//*[text()='There has been an internal server error!']"))
	private List<WebElement> internal_ServerError;
	
	@FindBys(@FindBy(xpath="//*[text()='Page Not Found']"))
	private List<WebElement> pageNotFound_Error;
	
	@FindBys(@FindBy(xpath="//div[@class='population']//p[@class='ng-scope']/b[@class='percent']"))
	private List<WebElement> rightHandSide_populationElement;
	
	@FindBys(@FindBy(xpath="//div[@class='population']//p[@class='ng-scope']/b[@class='ng-binding']"))
	private List<WebElement> rightHandSide_populationElementInMillion;
	
	@FindBys(@FindBy(xpath="//div[@class='population']//small[@class='ng-binding']"))
	private List<WebElement> rightHandSide_populationElementTotal;
	
	@FindBys(@FindBy(xpath="//div[@class='optimal']//div[@class='meter-wrapper score']"))
	private List<WebElement> rightHandSide_optimalAudience;
	
	@FindBys(@FindBy(xpath="//div[@class='meter-wrapper layout horizontal ng-scope']/div[@class='grid-lines flex-8']/div[@class='name ng-binding']"))
	private List<WebElement> rightHandSide_futureBehaviours;
	
	@FindBys(@FindBy(xpath="//div[@class='options-box ng-scope chosen']"))
	private List<WebElement> TotalSelectedCards;

	@FindBy(xpath="//a[contains(text(),'?')]")
	private WebElement helpIcon;

	@FindBys(@FindBy(xpath="//div[@class='dropdown-box help-box open']"))
	private List<WebElement> list_helpIcon;
	
	@FindBys(@FindBy(xpath="//div[@class='meter-wrapper layout horizontal ng-scope']//div[@class='name ng-binding']"))
    private List<WebElement> rightHandSide_futureValues;

	@FindBy(xpath="//*[@class='ion ion-navicon-round']")
	private WebElement MenuIcon;
	
	@FindBys(@FindBy(xpath="//*[@class='selected-client dropdown-parent navicon ng-isolate-scope left-border']"))
	private List<WebElement> MenuIcon_Lst;
	
	@FindBys(@FindBy(xpath="//*[@class='admin-access']"))
	private List<WebElement>   AdminAccessPresent;
	
	@FindBy(xpath="//*[@class='admin-access']")
	private WebElement   AdminAccess;
	
	@FindBys(@FindBy(xpath="//*[@class='audience-container']"))
	private List<WebElement>   AudiencePagePresent;
	
	@FindBy(xpath="//div[@class='dropdown-box open']//ul[@class='dropdown-list']/li[contains(.,'My Account')]")
	private WebElement myAccount;

//Element that are used in synchronization. Please do not change the name
	@FindBy(xpath=".//*[@id='summary-anchor']/div/div")
	private WebElement analyzeSummaryTable;
	
//End	


	/**func_RClientElementExists(String item)	 * 
	 *This method checks whether the client icon is present at the right top and whether "Logout","Save", "Change Client", "Manage" and "Rename" are existing 
	 *Created By:Amrutha Nair
	 *Created On:04 Aug 2015
	 *Modified By | Description of Modification: 
	 *Modified On: 
	 */			
 
	public boolean func_RClientElementExists(String item){
		boolean found = true;	
		if(BaseClass.rm.webElementExists(right_TopClient_Lst) == true)
		{
		CustomReporter.log("The client icon at the right top is existing");			
		right_TopClient.click();
		switch (item) {		
		
			case "ChangeClient":
					if(BaseClass.rm.webElementExists(right_ChangeClient_Lst)&&BaseClass.rm.webElementExists(changeClient_Icon))
					{
						  CustomReporter.log("Both 'change client' text and icon are present under client icon at right top ");	
					}
					else{
						CustomReporter.errorLog("Both 'change client' text and icon are NOT present under client icon at right top");
						found= false;
					}

			break;
			case "Manage":
				if(BaseClass.rm.webElementExists(right_Manage_Lst)&&BaseClass.rm.webElementExists(manage_Icon)){
					CustomReporter.log("Both 'Manage' text and icon are present under client icon at right top ");
				}
				else{
					CustomReporter.errorLog("Both 'Manage' text and icon are NOT present under client icon at right top");
					found= false;
				}
				break;
			case "Save":
				if(BaseClass.rm.webElementExists(save_Icon)&&(BaseClass.rm.webElementExists(save_idiom_lst))){
					CustomReporter.log("Both 'Save' text and icon are present under client icon at right top ");
				}
				else{
					CustomReporter.errorLog("Both 'Save' text and icon are NOt present under client icon at right top");
					found= false;
				}
				break;
			case "Rename":
				if(BaseClass.rm.webElementExists(rename_idiom_lst)&&(BaseClass.rm.webElementExists(rename_Icon))){
					CustomReporter.log("Both 'Rename' text and icon are present under client icon at right top ");
				}
				else{
					CustomReporter.errorLog("Both 'Rename' text and icon are NOt present under client icon at right top");
					found= false;
				}
				break;
		}
		}
		else
		{
			CustomReporter.errorLog("The client icon at the right top is NOT existing"); 
			found= false;
		}
		return found;
	}
	
	/**func_AudiencePageElementExist(String elementName)	 * 
	 *This method is used to check the existence of any web element of Audience page
	 *Created By:Abhishek Deshpande
	 *Created On:04 Aug 2015
	 *Modified By | Description of Modification: Amrutha Nair: changed Reusable methiod call and added return type
	 *Modified On: 11th August	
	 */	
			
	public boolean func_AudiencePageElementExist(String elementName){
			boolean found=false;
			switch (elementName) {
			
			case "AudienceLabel":		
				found= BaseClass.rm.webElementExists(label_Audiencelst);
			break;	
			
			case "AnalyzeLabel":
				found=BaseClass.rm.webElementExists(label_Analyzelst);
			break;
			
			case "ArchitectLabel":
				found=BaseClass.rm.webElementExists(label_Architectlst);
			break;
			
			case "ActivateLabel":
				found=BaseClass.rm.webElementExists(label_Activatelst);
			break;			
			
			case "HelpIcon":
				found=BaseClass.rm.webElementExists(icon_Helplst);
				break;
			case "DropDownOpen":
				found=BaseClass.rm.webElementExists(dropDown_Open);
				break;
			case "SavedIdiomText":
				found=BaseClass.rm.webElementExists(audience_SavedText);
				break;
		    case "deselectAll":
						found = BaseClass.rm.webElementExists(deselect_all);
		    break;
		    case "DeselectedFilters": 
		    	found = BaseClass.rm.webElementExists(DeselectedFilters);
			break;
			case "SelectedFilters": 
				found = BaseClass.rm.webElementExists(audience_Filters);
			break;
			case "DeselectAllText":
				if(BaseClass.rm.webElementExists(deselect_all)){
					for (WebElement deselect : deselect_all){	
						if(deselect.getText().contentEquals("Deselect All")){
							found=true;
						}
						else{
							found=false;
							break;
						}
								
					}
				}
				else{
					found=false;
				}
						
			break;
			    
			case "SelectAllText":
				if(BaseClass.rm.webElementExists(select_All)){
					for (WebElement select : select_All){	
						if(select.getText().contentEquals("Select All")){
							found=true;
						}
						else{
							found=false;
							break;
						}
								
					}
				}
				else{
					found=false;
				}
						
			break;
			
			case "SelectAll":
				found = BaseClass.rm.webElementExists(selectAll_Age);
			break;
			
			case "InternalServerError":
				found=BaseClass.rm.webElementExists(internal_ServerError);
			break;
			
			case "PageNotFoundError":
				found=BaseClass.rm.webElementExists(pageNotFound_Error);
			break;
			
			case "rightHandSide_populationElementPercentage":
				found = BaseClass.rm.webElementExists(rightHandSide_populationElement);
			break;
			
			case "rightHandSide_populationElementValueInMillion":
				found = BaseClass.rm.webElementExists(rightHandSide_populationElementInMillion);
			break;
			
			case "rightHandSide_populationElementValueTotal":
				found = BaseClass.rm.webElementExists(rightHandSide_populationElementTotal);
			break;
			
			case "rightHandSide_optimalAudience":
				found = BaseClass.rm.webElementExists(rightHandSide_optimalAudience);
			break;
			
			case "rightHandSide_FutureBehaviours":
				found = BaseClass.rm.webElementExists(rightHandSide_futureBehaviours);
			break;
			case "TotalSelectedCards":
			       found = BaseClass.rm.webElementExists(TotalSelectedCards);
			       break;
			case "helpPopupPresent":
				found = BaseClass.rm.webElementExists(list_helpIcon);
				break;	
			case "Admin Access Present":	
				MenuIcon.click();
				found=BaseClass.rm.webElementExists(AdminAccessPresent);
			break;

			case "Audience Page":
				found=BaseClass.rm.webElementExists(AudiencePagePresent);
				break;
				

			}
			
			return found;
		}
	
	
	/** func_CaptureList()	 * 
	 *This method collects the list of values depending on the condition in arraylist
	 *Created By:Amrutha Nair
	 *Created On:07 Aug 2015
	 *Modified By | Description of Modification: Modified by adding case to make the method reusbale
	 *Modified On: 24 August2015
	 */	
		
public ArrayList<String> func_CaptureList(String Case) throws InterruptedException{

	ArrayList<String> values = new ArrayList<String>();
	switch (Case) {
	
	case "FiltersSelected":
		for (WebElement filter : audience_Filters){	
			values.add(filter.getText());
			
		}
	break;
	case "DropDownItems":
		 for (WebElement filter : dropDown_filter){	
			 values.add(filter.getText());
			}
		
		 
    break;

    case "getFutureBehavioursValue":
        for(WebElement filter: rightHandSide_futureValues){
         values.add(filter.getText());
         Thread.sleep(3000);
        }
      break;

    case "getFutureBehavioursBars":
        for(WebElement filter: rightHandSide_futureBars){
         values.add(filter.getText());
        }
      break; 


	}
	return values;
	  
  }
  
  
	/** func_ClickElement(String element)        * 
	 *This method is used to click on elements present in Audience page
	*Created By:Abhishek Deshpande
	*Created On:07 Aug 2015
	*Modified By | Description of Modification: 
	*Modified On:
	*/	
 
  
  public void func_ClickElement(String element) throws InterruptedException{
	  Thread.sleep(8000);
	  int flag=0;
		switch (element){
		
		case "AnalyzeAudience":
			BaseClass.rm.clickWebElement(btn_AnalyzeAudience);
			
		break;
		
		case "AnalyzeLabel":
			BaseClass.rm.clickWebElement(label_Analyze);
		break;
		
		case "ClientIcon":
			BaseClass.rm.clickWebElement(right_TopClient);
		break;
		
		case "ChangeClient":
			right_TopClient.click();
			BaseClass.rm.clickWebElement(link_ChangeClient);		
		break;
		
		case "Logout":  
			MenuIcon.click();
			right_Logout.click();
		break;	
			  
		case "Manage":
			
			right_TopClient.click();
			right_Manage.click();
		break;
		
		case "Save":
			right_TopClient.click();
			save_idiom.click();
			break;
		case "futureBehaviour":
			drpDown.click();
			Thread.sleep(1000);
			futureBehaviour.click();
		break;
		
		case "PastAction":
			drpDown.click();
			Thread.sleep(1000);
			past_Actions.click();
		break;
		
		case "Demographics":
			drpDown.click();
			Thread.sleep(1000);
			demographicsDropDown.click();
		break;
		
		case "Age":
			drpDown.click();
			Thread.sleep(1000);
			age.click();
		break;
		
		case "hasChild":
			drpDown.click();
			Thread.sleep(1000);
			hasChild.click();
		break;
		
		case "HouseHoldIncome":
			drpDown.click();
			Thread.sleep(1000);
			hh_Income.click();
		break;
		
		case "HouseHoldSize":
			drpDown.click();
			Thread.sleep(1000);
			hh_Size.click();
		break;
		
		case "HighestEducation":
			drpDown.click();
			Thread.sleep(1000);
			highest_edu.click();
		break;
		
		case "Location":
			drpDown.click();
			Thread.sleep(1000);
			location.click();
		break;
		
		case "AnalyseIcon":
			btn_AnalyzeAudience.click();
		break;
		
		case "DeselectAll":
			for (WebElement Deselect : deselect_all){	
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", Deselect);
				Deselect.click();
				Thread.sleep(3000);
			}
		break;
		case "SelectAll":
			
			for (WebElement select : select_All){	
				select.click();
				Thread.sleep(3000);
			}
		break;
		case "TotalSelectedCards":
		       for (WebElement select : TotalSelectedCards){   
		              select.click();
		              Thread.sleep(1500);
		       }
		 break;
		case "AdminAccess":
			MenuIcon.click();
			AdminAccess.click();
		break;
		
		case "myaccount":  
			MenuIcon.click();
			myAccount.click();
		break;	
		}
	}
		
  /**func_CheckIdiomCreated(String IdiomName)   *
   * This method checks whether the idiom with passed name has been created really
   * Created By:Amrutha Nair
   * Created On:14 Aug 2015
   *Modified By | Description of Modification: 
   *Modified On:
   */
  
  public boolean func_CheckIdiomCreated(String IdiomName){
	  boolean found=false;
	  if(IdiomName.contentEquals(created_Idiom_Name.getText())){
		  found=true;
	  }
	return found;
	  
  }

  
  /**func_OpenNewwindow(WebDriver driver)   *
   * This method opens new window and control is passing  to the new window
   * Created By:Amrutha Nair
   * Created On:18 Aug 2015
   *Modified By | Description of Modification: 
   *Modified On:
   */
  
  public WebDriver  func_OpenNewwindow(String Url) throws AWTException, InterruptedException{
	  Robot robot = new Robot();

	    Thread.sleep(3000);
	  robot.keyPress(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_N);
      robot.keyRelease(KeyEvent.VK_CONTROL);
      robot.keyRelease(KeyEvent.VK_N);  

      // Perform the click operation that opens new window

      // Switch to new window opened
      for(String winHandle : driver.getWindowHandles()){
          driver.switchTo().window(winHandle);
      }
      driver.get(Url);
	return driver;

     
	}
  
  /**func_selectFilterAndPreserveNames()
   * This method selects Filters and stores name of selected filter in Array List
   * Created By:Shailesh Kava
   * Created On:18 Aug 2015
   *Modified By | Description of Modification: 
   *Modified On:
   */
  public ArrayList<String> func_selectFilterAndPreserveNames() throws InterruptedException{
	  
	  ArrayList<String> setFilterNames = new ArrayList<String>();
	  
	  hasChild_No.click();
	  String filterNo = hasChild_No.getText();
	  
	  Thread.sleep(2000);
	  
	  hhIncome_lssthan25K.click();
	  String filterLessthan25k = hhIncome_lssthan25K.getText();
	  
	  setFilterNames.add(filterNo);
	  setFilterNames.add(filterLessthan25k);
	  
	  return setFilterNames;
  }
  
  /**
   * String func_getValue(String Case)
   * This method gets the string value of the element case passed
   * Created By: Luv Datta
   * Created On: 19 Aug 2015
   *Modified By | Description of Modification: This method capture the population value in audience page 
   *Modified On:
   */
  public String func_getValue(String Case)
  {
   String value=null;
   switch (Case.toLowerCase()){
  
  case "percent":
   value=label_percent_Projected.getText();
  break;  
  
  case "binding":
   value=label_binding_Projected.getText();
  break; 
  case "population":
	  value=population_Gauge.getText();
	  break;
  case "populationper":
	  value=Population.getText().trim();
	  break;
  }
 return value;
  }
  
  /**func_CheckHoverOnComponents(String Case, WebDriver driver)
	 *This method checks hovering effect is proper for component cases passed
	 *Created By: Amrutha Nair
	 *Creation Date:  20 Aug 2015
	 *Modified By | Description of Modification:
	 *Modified On: 
  */	
	public boolean func_CheckHoverOnComponents(String Case){
		boolean status=false;
		Actions builder = new Actions(driver);
		switch(Case){
		case "TopClientDrpDown":
			builder.moveToElement(right_TopClient).build().perform();
			
			status = BaseClass.rm.webElementExists(drpDown_Open);
			break;	
		case "Audience":
			builder.moveToElement(btn_AnalyzeAudience).build().perform();
			
			break;	
		case "helpIcon":
			builder.moveToElement(helpIcon).build().perform();
			status = BaseClass.rm.webElementExists(list_helpIcon);
			break;	

		}
		
		return status;
		
	}
	
	
	 /**func_CheckFilterElementExists(String Case,String Item)
		 *This method confirms whether the demographics drop down selected is proper
		 *Created By: Amrutha Nair
		 *Creation Date:  24 Aug 2015
		 *Modified By | Description of Modification:
		 *Modified On: 
	  */

	public boolean func_CheckFilterElementExists(String Case,String Item){
		boolean status=false;
		
		switch(Case){
		case "MainGroupElement":
			WebElement element=driver.findElement(By.xpath(""));
		break;
		case "SubGroupElementexist":
			List<WebElement> element1 = driver.findElements(By.xpath("//header[@class='filter-sub-group ng-isolate-scope scrolled-past']//*[text()='"+Item+"']"));
			status=BaseClass.rm.webElementExists(element1);
			break;
		}
	return status;
	}
	
	
	 /**func_SelectFilters(String data1, String data2, WebDriver driver)
		 *This method select filters depending on input provided
		 *Created By: Amrutha Nair
		 *Creation Date:  24 Aug 2015
		 *Modified By | Description of Modification:
		 *Modified On: 
	  */
	public void func_SelectFilters(String filter) throws InterruptedException{
		Thread.sleep(10000);
		String id=null;
		String[] filterdata1=filter.split(":");
		for(int i=0;i<filterdata1.length;i++){
			String[] filterData2=filterdata1[i].split("_");
				id=func_returnID(filterData2[0]);
				String [] subfilterData=filterData2[1].split(",");
				for(int k=0;k<subfilterData.length;k++){				
					
					
					String strXpath = "//*[contains(.,'"+ filterData2[0] +"')]//ancestor::section//p[text()='"+ subfilterData[k] +"']";
					WebElement webelement = driver.findElement(By.xpath(strXpath));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", webelement);
					webelement.click();
					
					Thread.sleep(2000);
				}			}
		
	
		
		
	
	}
	
	/** func_AudienceCheckPostionOfUnknownFilter()      * 
     *This method is used to check the position of Unknown Filter Card in Demographic Sub sections
    *Created By:Rohan Macwan
    *Created On:25 Aug 2015
    *Modified By | 
     *Modified On:       
     */    
                  
	public boolean func_AudienceCheckPostionOfUnknownFilter(){
        
        List<WebElement> FilterCards;
        
        int flag=0;
        int flag1=0;
        boolean result = true; 
        
        for (int i=1; i<Demographics.size();i++)
        {
              flag=0;
              flag1=0;
              CustomReporter.log("Unknown filter will be checked for Demographics - " + Demographics.get(i).getText());
              int SubCategoryLength=0;
          
                    
                     FilterCards= driver.findElements(By.xpath("//section[@id='g-2']//div[@id='sg-2"+"-" + (i-1) +"']//div[@class='options']/div"));
                     
                   
                     Demographics.get(i).getText();
                     SubCategoryLength=FilterCards.size();
                     
                     
                     for(int k=0; k<=FilterCards.size() -1; k++){
                            
                           
                            
                            if(k==SubCategoryLength-1)
                            {
                                   if (FilterCards.get(k).getText().equals("Unknown"))
                                   {
                                          CustomReporter.log("Unknown filter is correctly set at the last position for the Demographics - " + Demographics.get(i).getText());
                                          flag1=1;
                                   }
                                   
                            }
                            else
                            {
                                   if (FilterCards.get(k).getText().equals("Unknown"))
                                   {
                                          CustomReporter.errorLog("Unknown filter is not set at the last position for the Demographics - " + Demographics.get(i).getText());
                                          //BaseClass.testCaseStatus=false;
                                          result = false;
                                          flag=1;
                                          break;
                                   }
                            }
                     }
                     if (flag==0 && flag1==0){
                            CustomReporter.log("Unknown filter is not available/required for the Demographics - " + Demographics.get(i).getText());
                     }
              //}
        }
        return result;
        
 }

	/**
	   * String func_selectCardByName(String cardName, WebDriver driver)
	   * This method clicks on given card name
	   * Created By: Shailesh Kava
	   * Created On: 03 Sep 2015
	   * Modified By | Description of Modification:  
	   * Modified On:
	   */
	public void func_selectCardByName(String cardName){
		WebElement cardSelect = null;
		String selectCard = cardName;
		
		cardSelect = driver.findElement(By.xpath("//p[text()='"+cardName+"']/ancestor::div[contains(@class, 'options-box')]"));
		
		cardSelect.click();
	}
	
	/**
	   * String func_verifyExpectedBreadCrumbandValuess(String[] expectedSecondaryMenu)
	   * This method will verify breadcrumb values and its position of Secondry Navigation 
	   * Created By: Shailesh Kava
	   * Created On: 03 Sep 2015
	   * Modified By | Description of Modification:  
	   * Modified On:
	   */
	public boolean func_verifyExpectedBreadCrumbandValuess(String[] expectedSecondaryMenu){
		
		String[] secondaryNavigationMenu = expectedSecondaryMenu;
		boolean navigationPresent = true;
		String navigationName = null;
		String expectedNavigationName = null;
		
		if(BaseClass.rm.webElementExists(secondaryNavigation)){
			
			Integer totalWebElements = secondaryNavigation.size();
			Integer definedArraySize = secondaryNavigationMenu.length;
			
			//System.out.println(totalWebElements+"====="+definedArraySize);
			
			if(!totalWebElements.equals(definedArraySize)){
				CustomReporter.errorLog("Count does not match with Expected count is: "+definedArraySize+" actual count is: "+totalWebElements);
				navigationPresent = false;
			}else{
				int i = 0;
				for(WebElement we : secondaryNavigation){
					navigationName = we.getText();
					expectedNavigationName = secondaryNavigationMenu[i];
					
					if(!navigationName.contentEquals(expectedNavigationName)){
						CustomReporter.errorLog("navigation menu not matching with existing menu");
						navigationPresent = false;
						break;
					}
					i++;
				}
			}
		}
		
		return navigationPresent;
	}
	/** func_AudienceCheckPopulationCssAttibutesWhetherPrper()      * 
     *This method is used to check the position of Separator in Population section
    *Created By:Rohan Macwan
    *Created On:03 Sep 2015
    *Modified By | 
     *Modified On:       
     */    
    
    public boolean func_AudienceCheckPopulationCssAttibutesWhetherPrper() throws InterruptedException{
                  
      boolean result = true;
      System.out.println("Starting func_AudienceCheckPopulationCssAttibutesWhetherPrper method ");
      
      int flag=0;
      int flag1=0;
      int flag2=0;
      
      if (BaseClass.rm.elementExists(Population)){
    	  Thread.sleep(3000);
    	  Population.click();
    	  Thread.sleep(3000);
    	  
    	  
    	  System.out.println("Margin-right"+Population.getCssValue("margin-right"));
      		Thread.sleep(1000);
      		System.out.println("border-right"+Population.getCssValue("border-right-width"));
      		Thread.sleep(1000);
      		System.out.println("padding-right"+Population.getCssValue("padding-right"));
      		
      		if (Population.getCssValue("border-right-width").equals("1px"))
      		{
      			CustomReporter.log("Population Border/Separator is found with proper alignment and style");
      			flag=1;
      		}
      		
      		if (Population.getCssValue("margin-right").equals("10px"))
      		{
      			CustomReporter.log("Rigt margin is properly set");
      		    flag1=1;
      		}
      		
      		
      		
      		if (Population.getCssValue("padding-right").equals("10px"))
      		{
      			CustomReporter.log("Right Padding is properly set");
      		    flag2=1;
      		}
       	  
    	  if (flag==0 || flag1==0 || flag2==0){
    		  result=false;
    		  if (flag==0){
    			  CustomReporter.errorLog("Either proper Separator attributes are not set or separator is not found");  
    		  }
    		  if (flag1==0){
    			  CustomReporter.errorLog("Rigt margin is not properly set");  
    		  }
    		  if (flag2==0){
    			  CustomReporter.errorLog("Right Padding is not properly set");  
    		  }
    		  
    	  }
    	  
      }
      
      
      System.out.println("End of func_AudienceCheckPopulationCssAttibutesWhetherPrper method");
      return result;
      
    }	

    /**
	   * String func_getCSSSVal(String cardName, String cssValueType)
	   * This method clicks on given card name retuns the cssValue on hover as of now "background-color" and "border-color"
	   * Created By: Shailesh Kava
	   * Created On: 03 Sep 2015
	   *Modified By | Description of Modification:  
	   *Modified On:
	   */
	
	public String func_getCSSSVal(String cardName, String cssValueType) throws Exception{
		
		/******Variable Intialization*******/
		String getValueFromCSS = null;
		String selectCard = cardName;
		
		/******Defining webelement for provided card*******/
		
		WebElement selectedCard = driver.findElement(By.xpath("//p[text()='"+cardName+"']/ancestor::div[contains(@class, 'options-box')]"));
		//WebElement selectedCard1 = driver.findElement(By.xpath("//p[text()='View Bras']/ancestor::div[contains(@class, 'options-box')]"));
		
		
		Actions builder = new Actions(driver);
		
		/******Cases to get CSSValue for selected card on hover*******/
		switch(cssValueType){
		
			case "get_bg_color":
				//builder.moveToElement(selectedCard1).build().perform();
				Thread.sleep(2000);
				builder.moveToElement(selectedCard).build().perform();
				Thread.sleep(2000);
				getValueFromCSS = selectedCard.getCssValue("background-color");
				//System.out.println("background-color   "+getValueFromCSS);
			break;
			
			case "get_border_color":
				//builder.moveToElement(selectedCard1).build().perform();
				Thread.sleep(2000);
				builder.moveToElement(selectedCard).build().perform();
				Thread.sleep(2000);
				getValueFromCSS = selectedCard.getCssValue("border-color");
				//System.out.println("border-color   "+getValueFromCSS);
			break;
		
		}
		
		return getValueFromCSS;		
	}
  
	
	/**func_VerifyCSSComponant(String FilterCards, String CSSType, String expected, WebDriver driver)
	 *This method checks whether the css values are exected for each filter data passed
	 *Created By: Amrutha Nair
	 *Creation Date:  8th Septemeber 2015
	 *Modified By | Description of Modification:
	 *Modified On: 
 */
public boolean func_VerifyCSSComponent(String FilterCards, String CSSType, String expected) throws Exception{
	String id= null;
	
	boolean status=true;
	Actions builder = new Actions(driver);
	String[] filterdata1=FilterCards.split(":");
	for(int i=0;i<filterdata1.length;i++){
		String[] filterData2=filterdata1[i].split("_");
			//id=func_returnID(filterData2[0]);
			String [] subfilterData=filterData2[1].split(",");
			for(int k=0;k<subfilterData.length;k++){
				String strXpath = "//*[contains(.,'"+ filterData2[0] +"')]//ancestor::section//p[text()='"+ subfilterData[k] +"']/ancestor::div[contains(@class, 'options-box')]";
				WebElement selectedCard = driver.findElement(By.xpath(strXpath));
				//WebElement selectedCard =driver.findElement(By.xpath("//*[@id='"+id+"']//p[text()='"+subfilterData[k]+"']/ancestor::div[contains(@class, 'options-box')]"));
				
				
				if(CSSType.contentEquals("get_bg_color")){
					
					Thread.sleep(2000);
					builder.moveToElement(selectedCard).build().perform();
					Thread.sleep(2000);
					System.out.println(selectedCard.getCssValue("background-color"));
					if(selectedCard.getCssValue("background-color").contentEquals(expected)){
						
						CustomReporter.log("The background colour for '"+subfilterData[k]+"'is expected");
						
					}
					else{
						
						CustomReporter.errorLog("The background colour for '"+subfilterData[k]+"'is NOT expected");
						
						status=false;
					}
				}
				else if (CSSType.contentEquals("get_border_color")){
					
					builder.moveToElement(selectedCard).build().perform();
					Thread.sleep(1000);
					System.out.println(selectedCard.getCssValue("border-color"));
					if(selectedCard.getCssValue("border-color").contentEquals(expected)){
						CustomReporter.log("The border colour for '"+subfilterData[k]+"'is expected");
						
					}
					else{
						CustomReporter.errorLog("The border colour for '"+subfilterData[k]+"'is NOT expected");
					
						status=false;
					}
						
				}
			}
	}
		
	return status;
}
		
	
	


/**func_returnID(String Filter)
	 *This method returns id of the filter card which can be used by other methods in Audience page
	 *Created By: Amrutha Nair
	 *Creation Date:  8th Septemeber 2015
	 *Modified By | Description of Modification:
	 *Modified On: 
 */
private String func_returnID(String Filter){
	String id=null;
	switch(Filter){
	case "Age":
		id="sg-2-1";
	break;
	case "Has Child":
		id="sg-2-4";
	break;
	case "FutureBehaviors":
		id="sg-0-0";
	break;
	case "Household Size":
		id="sg-2-5";
	break;
	case  "Household Income":
		id="sg-2-2";
	break;
	case  "Location":
		id="sg-2-0";
		break;
	case  "Highest Education":
		id="sg-2-3";
		break;
	case "Past Actions":
		id="sg-1-0";
	break;
	case "stageAge": id="sg-2-12";
					break;
	case "stageHouseholdSize": id="sg-2-6";
	break;
	
		
		
}
	return id;
	
}

/**func_verifyIdiomName(String idiomName)
 * This methiod checks whether idiom with name in input is existing
 *Created By:Rohan Macwan
 *Created On:09 Sep 2015
 *Modified By |    
 */ 

public boolean func_verifyIdiomName(String idiomName){
	boolean status=false;
	List<WebElement> Idiom=driver.findElements(By.xpath("//*[text()='"+idiomName+"']"));
	if(Idiom.size()!=0){
		status=true;
	}
	return status;
			
}

/** func_SelectAll_Then_DeselectOneByOne 
 *This method is used to Verify whether Deselect All link is present on the page when User manually deselects all the Cards for each section on the Audience page
*Created By:Rohan Macwan
*Created On:09 Sep 2015
*Modified By | 
 *Modified On:       
 */    
public void func_SelectAll_Then_DeselectOneByOne() throws InterruptedException{
              
	 int flag=0;
	 int flag1=0;
	 //This piece of code will select all Cards for each section present on Audience Page
	 for (WebElement SelectAll : select_All)
	 {	
		 SelectAll.click();
		 flag=1;
		 
	 }
	 
	 if (flag==1)
	 {
		 CustomReporter.log("Select All link is clicked for all sections");
	 }
	 else
	 {
		 CustomReporter.errorLog("Select All links seem to not present for any of the sections");
	 }
	  
	 //This piece of code will deselect all Cards present on the page one by one manually
	 for (WebElement SelectedCards : TotalSelectedCards)
	 {	
		 SelectedCards.click();
		 flag1=1;
		 
	 }
  
	 if (flag1==1)
	 {
		 CustomReporter.log("All Cards have been deselected one by one");
	 }
	 else
	 {
		 CustomReporter.errorLog("There seems to be no Card present on the page");
	 }
	
	  
  }


/** func_Behavour_Gauge(String filter)
 *This method verifies the behaviour population gauge in right side
*Created By:Amrutha
*Created On:10 Sep 2015
*Modified By | 
 *Modified On:       
 */    

public  boolean func_Behavour_Gauge(String filter){
	boolean status =true;
	int[] percentValue=new int[2];
	int[] percentStyle= new int[2];
	String percent=null;
	//check whether both the behaviour filters are present at right
	String[] behaviour=filter.split(",");
	for(int i=0;i<behaviour.length;i++)
	{
		List<WebElement> behaviourfilter=driver.findElements(By.xpath("//*[ @ng-show='gaugeBehaviors']//*[text()='"+behaviour[i]+"']"));
		if(BaseClass.rm.webElementExists(behaviourfilter)){
			CustomReporter.log("The selected behaviour'" +behaviour[i]+"'is presnet at right side gauge");
			percent=driver.findElement(By.xpath("//*[ @ng-show='gaugeBehaviors']/div["+i+"]//div[@class='value flex-2']/span")).getText();
		}
		else
		{
			CustomReporter.errorLog("The selected behaviour'" +behaviour[i]+"'is presnet at right side gauge");
			status=false;
			
		}
		
		
	}
	return status;
	
	
}

/** func_VerifyStyleChange_OnHover(String filter, String css_type, String expected) 
 *This method verifies card's style change on hover
*Created By:Amrutha
*Created On:10 Sep 2015
*Modified By | 
 *Modified On:       
 */    


public boolean func_VerifyStyleChange_OnHover(String filter, String css_type, String expected) throws InterruptedException{
	
	
	boolean status=true;
	Actions builder = new Actions(driver);
	String[] filterData=filter.split("_");
	String id=func_returnID(filterData[0]);

		
		WebElement selectedCard =driver.findElement(By.xpath("//*[@id='"+id+"']//p[text()='"+filterData[1]+"']/ancestor::div[contains(@class, 'options-box')]"));
		
		
		if(css_type.contentEquals("get_bg_color")){
			
			Thread.sleep(2000);
			builder.moveToElement(selectedCard).build().perform();
			Thread.sleep(2000);
		//	System.out.println(selectedCard.getCssValue("background-color"));
			//System.out.println(expected);
			if(selectedCard.getCssValue("background-color").contentEquals(expected)){
				
				CustomReporter.log("The background colour for '"+filterData[1]+"'is expected");
				
			}
			else{
				
				CustomReporter.log("The background colour for '"+filterData[1]+"'is NOT expected");
				
				status=false;
			}
		}
		else if (css_type.contentEquals("get_border_color")){
			
			builder.moveToElement(selectedCard).build().perform();
			Thread.sleep(1000);
			if(selectedCard.getCssValue("border-color").contentEquals(expected)){
				CustomReporter.log("The border colour for '"+filterData[1]+"'is expected");
				
			}
			else{
				CustomReporter.log("The border colour for '"+filterData[1]+"'is NOT expected");
			
				status=false;
			}
				
		}
		
	
	return status;
	
}

/**String func_capture_IndividualFilter(String filter)
 *This method captures each subfilter for provided filter card
*Created By:Amrutha Nair
*Created On:14 Sep 2015
*Modified By | 
 *Modified On:       
 */   
public String func_capture_IndividualFilter(String filter){
	String Value=filter;
	
	String Id=func_returnID(filter);
	List<WebElement> items=driver.findElements(By.xpath("//*[@id='"+Id+"']//*[@class='options-box ng-scope']/p"));
	for(WebElement item:items){
		if(!item.getText().contentEquals("Unknown")){
			if(Value.contentEquals(filter)){
				Value=Value+"_"+item.getText();
			}
			else{
				Value=Value+","+item.getText();
			}
		}
	}
	return Value;
}


/** func_TopMenuElementExists(String item)
 *This method checks whether the menu icon is present at the right top and whether "Logout" "Admin Access" and My account are under it
 *Creation Date:Date:03 November 2015
 *Modified By | Description of Modification: 
 *Modified On: 
 */	
public boolean func_TopMenuElementExists(String item){
	boolean found = true;	
	
	//if(BaseClass.rm.webElementExists(right_TopClient_Lst) == true)
		//{
		//CustomReporter.log("The client icon at the right top is existing");			
		//right_TopClient.click();
		if(BaseClass.rm.webElementExists(MenuIcon_Lst)){
		CustomReporter.log("Menu icon is existing at the header in Manage iDiom Page");
		
			switch (item) {		
				case "Logout":
				
				
					if(BaseClass.rm.webElementExists(right_Logout_Lst) == true)
					{
				
						CustomReporter.log("The Logout element is existing under client icon at right top");	
					}
					else{
						CustomReporter.errorLog("The Logout element is NOT existing under client icon at right top");
						found= false;
					}
				
				
				break;
			}
		}
		else{
			CustomReporter.errorLog("Menu icon is NOT existing at the header in Manage iDiom Page");
			found= false;
		}
return found;

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


}
