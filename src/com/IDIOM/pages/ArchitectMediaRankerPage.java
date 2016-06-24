package com.IDIOM.pages;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
import common.ReusableMethods;

/** Page class for Media Ranker. It has all the required elements and necessary methods.
 * @author Deepen Shah
 *
 */
public class ArchitectMediaRankerPage {
	WebDriver driver;	
	ReusableMethods rm;
	
	public ArchitectMediaRankerPage(WebDriver driver){
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	@FindBy(xpath="//div[@class='empty-message ng-scope']")
	private WebElement emptyDataMsg;

	@FindBys(@FindBy(xpath="//div[@class='empty-message ng-scope']"))
	private List<WebElement> emptyDataMsgList;
	
	@FindBy(xpath="//*[@class='ion ion-navicon-round']")
	private WebElement burgerMenu;
	
	@FindBy(linkText="Architect")
	private WebElement architectLink;
	
	@FindBy(xpath="//*[@class='selected-client dropdown-parent ng-isolate-scope']")
	private WebElement clientTopMenu;
	
	@FindBy(xpath="//*[@class='dropdown-list']/li[3]/a")
	private WebElement  manageTopMenu;

	//@FindBy(xpath="//span[text()='Show Audience']")
	@FindBy(xpath="//button[@class='audience-details-toggle']")
	public WebElement showAudienceButton;
	
	@FindBy(xpath="//div[starts-with(@class,'population-projected')]/div[@class='population']//b[1]")
	private WebElement showAudiencePopulationPercentageProjected;
	
	@FindBys(@FindBy(xpath="//div[@class='population']"))
	private List<WebElement> showAudiencePopulationPane;
	
	@FindBys(@FindBy(xpath="//div[@class='audience-attributes']"))
	private List<WebElement> showAudienceAudiencePane;
	
	@FindBys(@FindBy(xpath="//button[@class='audience-details-toggle']"))
	private List<WebElement> showAudienceToggleImageList; 
	
	@FindBy(xpath="//button[@class='audience-details-toggle']")
	private WebElement showAudienceToggleImage; 
	
	@FindBy(xpath="//li[contains(.,'TV')]")
	private WebElement tvChannel;
	
	@FindBy(xpath="//h2[contains(.,'Categories') and not(contains(.,'Sub'))]/span | //span[@class='ion ion-ios-arrow-back back']")
	private WebElement backToChannelList;

	@FindBys(@FindBy(xpath="//ul[@class='subnav']//li[@class='active']//a[contains(.,'Media Ranker')]"))
	private List<WebElement> mediaRankerText;
	
	@FindBy(xpath="//span[@class='ion ion-android-close close-open']")
	public WebElement closeCategoryBtn;
	
	@FindBy(xpath="//div[@id='media-ranker-scatterplot']")
	public WebElement mediaRankerScatteredPlot;
	
	@FindBy(xpath="//div[@class='ranker-tab-content ranker-categories active-content']")
	private WebElement categoriesOrDayPartsPanel;
	
	@FindBys(@FindBy(xpath="//div[@class='flex-1 metric-title ng-binding']"))
	private List<WebElement> individualMetricsName;
	
	@FindBys(@FindBy(xpath="//div[@class='flex-1 metric-title ng-binding']/text()"))
	private List<WebElement> individualMetricsNameWithoutLabel;
	
	@FindBys(@FindBy(xpath="//div[@class='flex-1 metric-title ng-binding']/span"))
	private List<WebElement> individualMetricsLabel;
	
	@FindBy(xpath="//span[@class='ion ion-android-checkbox-outline']")
	public WebElement toggleCheckbox;
	
	@FindBys(@FindBy(css="div#media-ranker-scatterplot>svg>g>circle"))
	private List<WebElement> scatteredPlotAllCircle;
	
	@FindBys(@FindBy(xpath="//div[@class='ranker-tab-content ranker-categories active-content']/ul/li"))
	private List<WebElement> categoryList;
	
	@FindBys(@FindBy(xpath="//div[@class='ranker-tab-content ranker-sub-categories active-content']/ul/li"))
	private List<WebElement> subCategoryList;
	
	@FindBys(@FindBy(xpath="//div[@class='ranker-tab-content ranker-items active-content']/ul/li"))
	private List<WebElement> itemCategoryList;
	
	@FindBy(xpath="//div[@class='ranker-tab-content ranker-sub-categories active-content']/h2/span")
	private WebElement backToCategory;
	
	@FindBy(xpath="//div[@class='ranker-tab-content ranker-items active-content']/h2/span")
	private WebElement backToSubCategory;
	
	
	@FindBys(@FindBy(xpath="//div[@class='ranker-tab-content ranker-categories active-content']/ul/li/div[1]"))
	private List<WebElement> categoryCheckboxList;
	
	@FindBys(@FindBy(xpath="//div[@class='ranker-tab-content ranker-sub-categories active-content']/ul/li/div[1]"))
	private List<WebElement> subCategoryCheckboxList;
	
	@FindBys(@FindBy(xpath="//div[@class='ranker-tab-content ranker-items active-content']/ul/li/div[1]"))
	private List<WebElement> itemCategoryCheckboxList;
	
	@FindBy(xpath="//div[@class='ranker-options-content flex-1']/div[1]/div[1]/h2")
	private WebElement channelHeader;
	
	@FindBy(xpath="//div[@class='ranker-tab-content ranker-categories active-content']/h2")
	private WebElement catPanelHeader;
	
	@FindBy(xpath="//div[@class='ranker-tab-content ranker-sub-categories active-content']/h2")
	private WebElement subCatPanelHeader;
	
	@FindBy(xpath="//div[@class='ranker-tab-content ranker-items active-content']/h2")
	private WebElement itemCatPanelHeader;
	
	@FindBys(@FindBy(xpath="//span[@class='ion ion-ios-arrow-back back']"))
	private List<WebElement> backLinks;
	
	@FindBy(xpath="//button[text()='Show More']")
	public WebElement showMoreBtn;
	
	@FindBy(xpath="//div[@class='chart-header']/div[1]/p/strong[1]")
	private WebElement moreCircleCount;
	
	@FindBy(xpath="//div[@class='chart-header']/div[1]/p")
	private WebElement moreCircleFrequencyCount;
	
	@FindBy(xpath="//span[@class='ion ion-android-options']")
	public WebElement reorderBtn;
	
	@FindBys(@FindBy(xpath="//div[@class='ranker-tab-content axis-organizer active-content']/ul/li"))
	private List<WebElement> reorderPane;
	
	@FindBy(xpath="//div[@class='x-axis ng-scope']/span[2]/span[1]")
	private WebElement xaxisLabel;
	
	@FindBy(xpath="//div[@class='y-axis ng-scope']/span[2]/span[1]")
	private WebElement yaxisLabel;
	
	@FindBy(css="div#metric-id-rank>svg")
	private WebElement rankMetricPlot;
	
	@FindBy(xpath="//span[@class='ion ion-android-options edit-rank-toggle']")
	public WebElement weightedRankerBtn;
	
	@FindBys(@FindBy(xpath="//div[@class='rank-weights-inner']/div[@class='layout horizontal rank-weight ng-scope']"))
	private List<WebElement> weightedRankerMetricsPanelList;
	
	@FindBy(xpath="//span[@class='ion ion-ios-list-outline list-view-toggle']")
	public WebElement viewTop10PropBtn;
	
	@FindBys(@FindBy(css=".list-view-panel.open>div>div:nth-of-type(2)>ol>li[class$='noGrandparent']"))
	private List<WebElement> top10PropList;
	
	@FindBy(xpath="//span[@class='total-value ng-binding']")
	private WebElement weightedRankerTotalValue;
	
	@FindBy(xpath="//div[@class='rank-weights-inner']//button[text()='Cancel']")
	private WebElement weightedRankerCancelBtn;
	
	@FindBy(xpath="//div[@class='rank-weights-inner']//button[text()='Save Changes']")
	private WebElement weightedRankerSaveChangesBtn;
	
	@FindBy(xpath="//div[@class='chart-header']/div[@class='world-selector layout horizontal']/div[text()='Universe']")
	public WebElement chartHeaderUniverseTab;
	
	@FindBy(xpath="//div[@class='chart-header']/div[@class='world-selector layout horizontal']/div[text()='Audience']")
	private WebElement chartHeaderAudienceTab;
	
	@FindBys(@FindBy(xpath="//div[@class='chart-header']/div[@class='world-selector layout horizontal']/div[text()='Audience' and @class='selected']"))
	private List<WebElement> chartHeaderAudienceTabSelected;

	@FindBys(@FindBy(xpath="//div[@class='chart-header']/div[@class='world-selector layout horizontal']/div[text()='Universe' and @class='selected']"))
	private List<WebElement> chartHeaderUniverseTablected;
	
	@FindBy(xpath="//span[@class='ion ion ion-android-close list-view-toggle']")
	private WebElement closePropertyOverlay;
	
	@FindBy(name="list-view-orderby")
	public WebElement orderDropdown;
	
	@FindBy(xpath="//*[@id='display-layer']")
	public WebElement displayDropdown;
	
	@FindBy(xpath="//div[@class='mr-date-picker']/span[1]")
	public WebElement datepicker;

	@FindBys(@FindBy(xpath="//div[@class='mighty-picker-calendar__day-wrapper']"))
	public List<WebElement> listDatesOnCalender;

	@FindBy(xpath="//div[@class='y-axis ng-scope']/span[@class='min']")
	private WebElement yMin;
	
	@FindBy(xpath="//div[@class='y-axis ng-scope']/span[@class='max']")
	private WebElement yMax;
	
	@FindBy(xpath="//div[@class='x-axis ng-scope']/span[@class='min']")
	private WebElement xMin;
	
	@FindBy(xpath="//div[@class='x-axis ng-scope']/span[@class='max']")
	private WebElement xMax;
	
		
//Element that are used in synchronization as well. Please do not change the name
		
	@FindBy(xpath="//li[contains(.,'Digital')]")
	private WebElement digitalChannel;
	
	@FindBy(xpath="//div[@class='chart-header']/div[2]/p")
	private WebElement chartHeaderTitle;

	@FindBy(css="div#media-ranker-scatterplot>svg>g")
	private WebElement svgElement;
	
	@FindBy(xpath="//div[@class='ranker-tab-content ranker-categories active-content']")
	public WebElement category;
	
	@FindBy(xpath="//div[@class='ranker-tab-content ranker-sub-categories active-content']")
	public WebElement subCategory;
	
	@FindBy(xpath="//div[@class='ranker-tab-content ranker-items active-content']")
	public WebElement itemCategory;
	
    @FindBy(xpath="//span[@class='actions']/button[text()='Apply']")
	private WebElement calenderApplyBtn;
	
	@FindBy(xpath="//span[@class='actions']/button[text()='Cancel']")
	public WebElement calenderCancelBtn;
	
	@FindBy(xpath="//span[@class='actions']/button[text()='Clear']")
	public WebElement calenderClearlBtn;
	
    @FindBy(xpath="//*[@class='ion ion-navicon-round']")
	private WebElement MenuIcon;
	
	@FindBy(xpath="//*[@class='admin-access']")
	private WebElement  AdminAccess;

	@FindBys(@FindBy(xpath="//div[@class='site']/div[@class='layout horizontal']//div[@class='additional-metrics ng-scope']/div[@class='layout horizontal ng-scope']"))
	private List<WebElement> listIndividualMetric;
	
	//Added Temporary - Start
	@FindBy(xpath="//div[@class='dropdown-box open']//ul[@class='dropdown-list']/li[contains(.,'My Account')]")
	private WebElement myAccount;
	
	@FindBys(@FindBy(xpath="//*[@class='admin-access']"))
	private List<WebElement>   AdminAccessPresent;
	
	@FindBys(@FindBy(xpath="//*[@id='display-layer']/option[@class='ng-binding']"))
	private List<WebElement>   dispalyDropDownItems;

	@FindBy(xpath="//*[@class='subheader ng-scope']//a[text()='Media Ranker']")
	private WebElement mediaRankerheading;
	
	@FindBys(@FindBy(xpath="//*[@class='ng-scope mighty-picker-calendar__day mighty-picker-calendar__day--selected']/div[@class='mighty-picker-calendar__day-wrapper']"))
	public List<WebElement> selectedDatesList;
	
	@FindBys(@FindBy(xpath="//*[@class='ng-scope mighty-picker-calendar__day']/*[@class='mighty-picker-calendar__day-wrapper']"))
	private List<WebElement> deSelectedDates;
	
	@FindBys(@FindBy(xpath="//*[@class='ng-scope mighty-picker-calendar__day mighty-picker-calendar__day--disabled']/*[@class='mighty-picker-calendar__day-wrapper']"))
	private List<WebElement> disabledDates;

	@FindBys(@FindBy(xpath="//td[@class='ng-scope mighty-picker-calendar__day']/div[1]"))
	public List<WebElement> clickableDateList;

	@FindBy(xpath="//button[@class='btn btn-muted export ng-scope']")
	public WebElement exportData_button;
	@FindBy(xpath="//*[@class='mr-date-picker-calendar']//*[@class='mighty-picker__wrapper ng-scope']")
	private WebElement datePickerOpenedWidget;
	
	@FindBys(@FindBy(xpath="//div[not(contains(@style,'rgb(244, 242, 241)')) and @class='toggle']/parent::li"))
	public List<WebElement> selectedVisibleCategories;
	
	@FindBy(xpath="//*[@class='world-selector layout horizontal']/*[@class='selected']")
	public WebElement defaultTab;

	@FindBys(@FindBy(xpath="//*[@class='mighty-picker__month-name ng-binding']"))
	public List<WebElement> timeWidgetMonthYearLst;
	
	@FindBy(xpath="//*[@class='mighty-picker__next-month']")
	public WebElement nextMonth;
	
	@FindBys(@FindBy(xpath="//*[@id='media-ranker-scatterplot']/*[name()='svg']//*[name()='circle']"))
	public List<WebElement>  bubblesInScatterPlot;
	
	@FindBy(xpath="//*[@id='media-ranker-scatterplot']/*[@class='tooltip']/*[@class='name']")
	public WebElement scatterPlotToolTipName;
	
	@FindBys(@FindBy(xpath="//*[@id='media-ranker-scatterplot']/*[@class='tooltip']/*[@class='value']/span[1]"))
	public List<WebElement> scatterPlotToolTipVAlues;

	@FindBy(xpath="//button[@class='reset']")
	public WebElement weightedRankerResetDefaultBtn;
	
	@FindBy(xpath="//ul[@class='subnav']/li[contains(.,'Media Ranker')]")
	public WebElement dummyClick;
	
	@FindBys(@FindBy(xpath="//div[@class='toggle']"))
	public List<WebElement> listCategory;

	@FindBy(xpath="//insights")
	public WebElement showAudiencePane;
	
	String weightRankerLabel="./div[@class='flex-1 ng-binding']";
	String rankValue="./div[@class='flex-1 rank-value']/input";
	String strRankInput = "//div[translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz'')=''{0}'']/parent::div/div[contains(@class,''rank-value'')]/input";

	String strSelectedDates="//*[@class=''ng-scope mighty-picker-calendar__day mighty-picker-calendar__day--selected''][{0}]/div[@class=''mighty-picker-calendar__day-wrapper'']";
	String strdeSelectedDates="//*[@class=''ng-scope mighty-picker-calendar__day''][{0}]/*[@class=''mighty-picker-calendar__day-wrapper'']";

	String weightedRankerPercentageBarGraph = "//div[text()=''{0}'']//following-sibling::div[@class=''flex-4 rank-bar'']/div";
	String strMetricPlotBubblesCSSContains = "div#metric-id-{0}>svg>g>circle";
	
	String strCheckMarkByCatNameContains="//div[text()=''{0}'']/preceding-sibling::div/span[@class=''ion ion-checkmark'']";
	public String strCategoryName = "./div[2]";
	public String strCategoryBGColor = "./div[1]";

	public String strCategoryToggleBtn = "./div[1]/span";	
	
    String strReorderPanWElement="//div[text()=''{0}'']/following-sibling::div[contains(@class,''flex-1 axis-name'')]";
   public String strBGColorBasedOnCategoryName = "//div[contains(.,''{0}'')]/parent::li[contains(@class,''layout horizontal'')]/div[@class=''toggle'']";

    String strDataSetElement="//*[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]/preceding-sibling::div[@ng-click=''toggleSelection(category);'']";

//End	
	
	/** This method will check the no data message on data set builder.
	 *  Before selecting any channel there will be a message for no data.
	 * @return Based on existence of message it will return true/false
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 * 
	 */
	public boolean func_CheckNoDataMsg(){
		boolean bRetFlag = false;
		
		if(emptyDataMsgList.size() != 0){
			if(emptyDataMsg.getText().equalsIgnoreCase("Select items to the right to display as a scatter plot")){
				bRetFlag = true;
			}
		}
		
		
		return bRetFlag;
	}
	
	/** This method will click on element based on argument.
	 * 
	 * @param strOption - String option to click. From 'architect','manage','showaudience','tvchannel','digitalchannel','backtochannel',
	 * 'closecatbutton',togglecheckbox','reorderbtn','weightedranker','viewscatteredplotprop','backtocategory',
	 * 'backtosubcategory','closepropetyoverlay','calenderclearbtn','calendercancelbtn','rankersavechanges',
	 * 'rankercancel','displaydropdown','resetdefault'.
	 * 
	 * @author Deepen Shah
	 * @throws InterruptedException 
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public void func_ClickOnElement(String strOption) throws InterruptedException{
		switch(strOption.toLowerCase()){
			case "architect": 
				architectLink.click();
				break;
								
			case "manage": 
				clientTopMenu.click();
				manageTopMenu.click();
				break;
							
			case "showaudience": 
				showAudienceButton.click();
				break;
				
			case "tvchannel":
				tvChannel.click();
				break;
			
			case "digitalchannel":
				digitalChannel.click();
				break;
			
			case "backtochannel":
				backToChannelList.click();
				break;
				
			case "closecatbutton":
				closeCategoryBtn.click();
				break;
				
			case "togglecheckbox":
				toggleCheckbox.click();
				break;
				
			case "reorderbtn":
				reorderBtn.click();
				break;
				
			case "weightedranker":
				weightedRankerBtn.click();
				break;
				
			case "viewscatteredplotprop":
				viewTop10PropBtn.click();
				break;
				
			case "showmore":
				showMoreBtn.click();
				break;
					
			case "chartheaderunirversetab":
				chartHeaderUniverseTab.click();
				break;
				
			case "chartheaderaudiencetab":
				chartHeaderAudienceTab.click();
				break;
				
			case "backtocategory":
				backToCategory.click();
				break;
				
			case "backtosubcategory":
				backToSubCategory.click();
				break;

				
			case "closepropetyoverlay":
				closePropertyOverlay.click();
				break;
			
			case "datepicker":
				datepicker.click();
				break;
			
			case "calenderclearbtn":
				calenderClearlBtn.click();
				break;
				
			case "calendercancelbtn":
				calenderCancelBtn.click();
				break;
			
			case "calenderapplybtn":
				calenderApplyBtn.click();
				break;
				
			case "individualmatriclastmetric":
				int lastIndividualPlot = listIndividualMetric.size()-1;
					driver.findElement(By.xpath("//div[@class='site']/div[@class='layout horizontal']//div[@class='additional-metrics ng-scope']/div[@class='layout horizontal ng-scope']["+lastIndividualPlot+"]/div/span")).click();
				break;
			

			case "adminaccess":
				MenuIcon.click();
				System.out.println("Clicking on Admin access option");
				Thread.sleep(6000);
				AdminAccess.click();
				break;
				
			case "rankersavechanges":
				weightedRankerSaveChangesBtn.click();
				break;
				
			case "rankercancel":
				weightedRankerCancelBtn.click();
				break;
				
			case "displaydropdown":
				displayDropdown.click();
				break;

			case "myaccount":
	            MenuIcon.click();
	            Thread.sleep(3000);
	            myAccount.click();
	            break;
				
			case "calenderapply":
				calenderApplyBtn.click();
				break;
				
			case "resetdefault":
				weightedRankerResetDefaultBtn.click();
				break;
			
				
			default:
				System.out.println("No matching case '" + strOption +"' available");
				break;
		}
	}
	
	/** It will return text() of the element given by strElement case.
	 * 
	 * @param strElement - String case for element. From 'showaudiencepopulationperprojected','categoryheaderpanel0','categoryheaderpanel1',
	 * 'categoryheaderpanel2','channelheader','xaxis','yaxis','weightedrankermetricname','weightedrankertotalvaluetext',
	 * 'weightedrankermetricpercentagevalue','defaultproporder','datepickerrange','xmax','xmin','ymax','ymin'
	 * 
	 * @return String text value of element.
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification 
	 */
	public String func_GetValue(String strElement){
		String strValue="";
		
		switch(strElement.toLowerCase()){
		case "showaudiencepopulationperprojected":
			strValue = showAudiencePopulationPercentageProjected.getText().trim();
			break;
			
		case "categoryheaderpanel0":
			strValue=catPanelHeader.getText().trim();
			break;
			
		case "categoryheaderpanel1":
			strValue=subCatPanelHeader.getText().trim();
			break;
			
		case "categoryheaderpanel2":
			strValue=itemCatPanelHeader.getText().trim();
			break;
			
		case "channelheader":
			strValue=channelHeader.getText().trim();
			break;
			
		case "xaxis":
			strValue = xaxisLabel.getText().trim();
			break;
			
		case "yaxis":
			strValue = yaxisLabel.getText().trim();
			break;
			
		case "weightedrankermetricname":			
			for(WebElement element:weightedRankerMetricsPanelList)
				strValue += element.findElement(By.xpath("div[1]")).getText().trim() +",";
			
			strValue=strValue.substring(0, strValue.length()-1);
			break;
			
		case "weightedrankertotalvaluetext":
			strValue = weightedRankerTotalValue.getText().trim();
			break;
		case "getcountmorecirclecount":
			strValue = moreCircleCount.getText().trim();
			break;
			
		case "totalcirclecountfromheader":
			strValue = moreCircleFrequencyCount.getText().trim();
			
			for(String val:strValue.split("\\(")){
				String splittedStr = val.trim();
				
				String[] finalString = splittedStr.split(" ");
				
				for(int i=0; i<=finalString.length-1; i++){
					strValue = finalString[0]; 
				}
			}

			break;
			
		case "weightedrankermetricpercentagevalue":
			for(WebElement element:weightedRankerMetricsPanelList){
				
				String strStyle = element.findElement(By.xpath("div[2]/div")).getAttribute("style");
				strStyle = strStyle.replaceAll("\\D+","").trim();
				strValue += strStyle +",";
			}
				
			
			strValue=strValue.substring(0, strValue.length()-1);
			
			break;
			
		case "defaultproporder":
			Select order = new Select(orderDropdown);
			strValue= order.getFirstSelectedOption().getText();
			break;

		case "datepickerrange":
			strValue= datepicker.getText();
			break;
			
		case "xmin":
			strValue=xMin.findElement(By.xpath("span[2]")).getText().trim();
			break;
			
		case "xmax":
			strValue=xMax.findElement(By.xpath("span[2]")).getText().trim();
			break;
			
		case "ymin":
			strValue=yMin.findElement(By.xpath("span[2]")).getText().trim();
			break;
			
		case "ymax":
			strValue=yMax.findElement(By.xpath("span[2]")).getText().trim();
			break;
		}
		
		
		return strValue.trim();
	}
	
	/** To verify existence of element on the page this method can be used.
	 * 
	 * @param strElement - String case to be verified. From 'showaudiencepopulationpane','showaudienceaudiencepane','metricslabel','metricbubbleplot'
	 * 'mediarankertextinmenu','categorylist','showAudiencetoggleimage'
	 * 
	 * @return Returns true/false if any of the element not found
	 * @throws Exception Throws exception
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public boolean func_VerifyElementExist(String strElement) throws Exception{
		boolean bRetFlag=false;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		switch(strElement.toLowerCase()){
			case "showaudiencepopulationpane":
				if(showAudiencePopulationPane.size() > 0)
					bRetFlag=true;
				
				break;
			case "showaudienceaudiencepane":
				if(showAudienceAudiencePane.size() > 0)
					bRetFlag=true;
				
				break;
				
			case "metricslabel":				
				boolean bTempFlag = true;				
				
				if(individualMetricsName.size()<=0)					
					break;
				
				
				for(WebElement webElementLabel:individualMetricsName){					
					if(!BaseClass.rm.webElementSync(webElementLabel, "visibility", "", null))
						bTempFlag = false;						
				}
				
				if(bTempFlag)
					bRetFlag = true;
				
				break;
				
			case "metricbubbleplot":
				bTempFlag=true;
				String strMetrics[] = BaseClass.property.getProperty("MetricplotValue").split(",");
				
				for(String strMetric:strMetrics){
					String strMetricLabelName[] = strMetric.split(":");
					if(driver.findElements(By.xpath(".//*[@id='metric-id-"+ strMetricLabelName[1]+"']")).size()<=0)
						bTempFlag = false;					
				}
				
				if(bTempFlag)
					bRetFlag = true;
				break;
				
				
			case "mediarankertextinmenu":
				bRetFlag = BaseClass.rm.webElementExists(mediaRankerText);
			break;
			
			case "categorylist":				
				bRetFlag = categoryList.size()!=0?true:false;
				
				break;
				
			case "chartheaderaudiencetabselected":
				bRetFlag = BaseClass.rm.webElementExists(chartHeaderAudienceTabSelected);	
				break;
				
			case "chartheaderuniversetablected":
				bRetFlag = BaseClass.rm.webElementExists(chartHeaderUniverseTablected);
				break;
				
			case "showaudiencetoggleimage":
				bRetFlag = BaseClass.rm.webElementExists(showAudienceToggleImageList);
				break;
				
			case "admin access present":	
				MenuIcon.click();
				bRetFlag=BaseClass.rm.webElementExists(AdminAccessPresent);
				break;
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return bRetFlag;
	}
	

	/** This method will compare attributes on 'Show Audience' panel with what is given as argument.
	 * 
	 * @param strAttributes - Colon(':') separated list of category. Category name and value separated by '_' and multiple values are separated by ','.</pre>
	 * @param strActualOrProjected - String value for 'Projected' or 'Actual' population tab.
	 * @return true/false
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public boolean func_VerifyAudienceAttributesExist(String strAttributes,String strActualOrProjected){
		boolean bRetFlag = true;
		String strDivText = strActualOrProjected.toLowerCase().equalsIgnoreCase("actual")?"population-actual":"population-projected";
		String strCategoryCode1 = BaseClass.property.getProperty("AudienceCategory1");
		String strCategoryCode2 = BaseClass.property.getProperty("AudienceCategory2");
		String strCategoryCode3 = BaseClass.property.getProperty("AudienceCategory3");
		
		
		String[] strAttributeList = strAttributes.split(":");
		for(String strAttribute:strAttributeList){
			String strCategoryCards[] = strAttribute.split("_");
			String strCategory = strCategoryCards[0];
			String strCards[] = strCategoryCards[1].split(",");
			
			for(String strCard:strCards){
				String strXpath="";
				if(strCategory.equalsIgnoreCase(strCategoryCode1)){
					strXpath= "//div[starts-with(@class,'"+ strDivText +"')]//li[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"+ strCategoryCode1.toLowerCase() +"')]//li[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"+ strCard.toLowerCase() +"')]";
				}else if( strCategory.equalsIgnoreCase(strCategoryCode2)){
					strXpath= "//div[starts-with(@class,'"+ strDivText +"')]//li[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"+ strCategoryCode2.toLowerCase() +"')]//li[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"+ strCard.toLowerCase() +"')]";
				}else{
					strXpath= "//div[starts-with(@class,'"+ strDivText +"')]//li[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"+ strCategoryCode3.toLowerCase() +"')]//li[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"+ strCategory.toLowerCase() +":"+ strCard.toLowerCase() +"')]";
				}
				System.out.println("Xpath is " + strXpath);
				List<WebElement> webEl = driver.findElements(By.xpath(strXpath));
				if(webEl.size()== 0){
					bRetFlag = false;
					System.out.println("Card '" + strCard +"' is not found under '"+ strCategory +"' category for '"+ strDivText +"'." );
				}
			}
			
		}
		
		
		return bRetFlag;
		
	}	
	
	/** This method will give the size of element on page.
	 *  Gives 'Dimension' class object. This class has 'width' and 'height' for that element.
	 * @param strElement - String case for the element
	 * @return Dimension class object
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public Dimension func_GetSizeOfElement(String strElement){
		Dimension dim = null;
		
		switch(strElement.toLowerCase()){
		
			case "scatteredplot":
				dim = mediaRankerScatteredPlot.getSize();
				break;
				
		}
		
		
		
		return dim;
		
		
	}
	
	
	/** This method will verify text for list of element.
	 *  Created specially for metrics name
	 * 
	 * @param strCase - String case from 'verifymetricslabelandname', 'weightedrankerpercentage','weightedrankermetricname', 'weightedrankermetricbarclass'
	 * @param strValue - name:value pair separated by comma(',') 
	 * @return true/false
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public boolean func_VerifyText(String strCase,String strValue){
		boolean bStatus = true;
		
		switch(strCase.toLowerCase()){
		
		case "verifymetricslabelandname":
			
			if(individualMetricsLabel.size()<=0)
				break;
			
			String strMetric[] = strValue.split(",");
			for(String strEachMetric:strMetric){
				WebElement webElementMetric=null;
				String strLabelName[] = strEachMetric.split(":"); 
				
				for(int i=0;i<individualMetricsLabel.size();i++){
					webElementMetric=individualMetricsLabel.get(i);
					if(webElementMetric.getText().toLowerCase().contains(strLabelName[1].toLowerCase()))
							break;
				}
				
				if(webElementMetric!= null){
					if(webElementMetric.findElement(By.xpath("//span")).getText().equalsIgnoreCase(strLabelName[0]))
						bStatus = false;
				}else{
					CustomReporter.errorLog("Not able to find web element for metric, " + strEachMetric +", on Architect page");
					bStatus = false;
				}
			}
			
			break;
			
		case "weightedrankerpercentage":
			for(WebElement element:weightedRankerMetricsPanelList){
				WebElement percentElement = element.findElement(By.xpath("div[3]"));
				if(!percentElement.getText().trim().equalsIgnoreCase("%")){
					bStatus = false;
					System.out.println("One of the weighted ranker metric has no %");
				}
			}
			
			break;
			
		case "weightedrankermetricname":			
			for(WebElement element:weightedRankerMetricsPanelList){
				String strElementText = element.findElement(By.xpath("div[1]")).getText().trim();
				if(!strValue.toLowerCase().contains(strElementText.toLowerCase())){
					bStatus = false;
					System.out.println("Text is not available in expected result set. Actual: " + strElementText + " and Exp: " + strValue);
				}
			}
			
			break;
					
		case "weightedrankermetricbarclass":
			for(WebElement element:weightedRankerMetricsPanelList){			
				String strElementClass = element.findElement(By.xpath("div[2]/div")).getAttribute("class");
				if(!strElementClass.equalsIgnoreCase(strValue)){
					bStatus = false;
					System.out.println("Grey bar is not present for on of metric in weighted rank. Expecte class: " + strValue + " and found " + strElementClass);
				}
			}
			
			break;
		
		
		}
		
		return bStatus;
	}
	
	/** This method will return size of list element.
	 * 
	 * @param strWebElement - String case for various element. From 'circlesinplot','categories','subcategories' & 'itemcategories'
	 * @return int list size
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public int func_GetCount(String strWebElement){
		int i=0;
		
		switch(strWebElement.toLowerCase()){
			
		case "circlesinplot":
			i = scatteredPlotAllCircle.size();
			break;
			
		case "categories":
			i = categoryList.size();
			break;
			
		case "subcategories":
			i = subCategoryList.size();
			break;
			
		case "itemcategories":
			i = itemCategoryList.size();
			break;
			
		case "weightedrankermetric":
			i = weightedRankerMetricsPanelList.size();
			break;
			
			default: System.out.println("No such case " + strWebElement + " found. in func_GetCount"  );
			break;
		}
		
		return i;		
		
	}
	
	/** Click on category/sub-category/item for any channel by its order.
	 * It will work for any level of category for any channel
	 * 
	 * @param i - int order of category
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public void func_ClickCategoryByNumber(int i){
		categoryList.get(i).findElement(By.xpath("div[2]")).click();
	}
		
	/** Click on category/sub-category/item for any channel by its order.
	 *  It will work for any level of category for any channel
	 * @param strLevel
	 * @param i
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public void func_ClickCategoryByNumber(String strLevel,int i){
		if(strLevel.equalsIgnoreCase("category"))
			categoryList.get(i).findElement(By.xpath("div[2]")).click();
		else
			subCategoryList.get(i).findElement(By.xpath("div[2]")).click();
	}
	
	/** Click on category/sub-category/item for any channel by its name.
	 * It will work for any level of category for any channel
	 * 
	 * @param strCatName - String name
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public void func_ClickCategoryByName(String strCatName){
		for(WebElement categoryElement:categoryList){
			WebElement catNameElement=categoryElement.findElement(By.xpath("div[2]"));
			if(catNameElement.getText().equalsIgnoreCase(strCatName)){
				catNameElement.click();
				break;
			}
		}
	}
	
	/** Select/Clear category by its order. 
	 *  As it has toggle effect. Clicking twice will take it to initial state.
	 *  It will work for any level of category for any channel
	 *  
	 * @param i - int order of category
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public void func_ToggleCategoryByNumber(int i) throws Exception{
		categoryList.get(i).findElement(By.xpath("div[1]")).click();		
	}
	
	
	/** Overloaded above method. As list will change at different level.
	 * 
	 * @param i
	 * @param strLevel
	 * @author Deepen Shah
	 * @since 30 May 2016
	 */
	public void func_ToggleCategoryByNumber(int i,String strLevel){
		if(strLevel.equalsIgnoreCase("categories") || strLevel.equalsIgnoreCase("category"))
			categoryList.get(i).findElement(By.xpath("div[1]")).click();
		else if(strLevel.equalsIgnoreCase("subcategories") || strLevel.equalsIgnoreCase("subcategory"))
			subCategoryList.get(i).findElement(By.xpath("div[1]")).click();
		else
			itemCategoryList.get(i).findElement(By.xpath("div[1]")).click();
	}
	
	/** Select/Clear category by its name. 
	 *  As it has toggle effect. Clicking twice will take it to initial state.
	 *  It will work for any level of category for any channel
	 * 
	 * @param strCatName - String category name
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public void func_ToggleCategoryByName(String strCatName){
		for(WebElement categoryElement:categoryList){
			WebElement catNameElement=categoryElement.findElement(By.xpath("div[2]"));
			WebElement catNameToggle=categoryElement.findElement(By.xpath("div[1]"));
			if(catNameElement.getText().trim().equalsIgnoreCase(strCatName)){
				catNameToggle.click();
				break;
			}
		}
	}
	
	/** Function will take user back to channel list page.
	 *  It will work irrespective of any category level.
	 * 
	 * @throws Exception Throws exception as Thread.sleep is used.
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public void func_GoBackToChannelList() throws Exception{
		System.out.println("Size of back links " + backLinks.size());
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.ignoring(NoSuchElementException.class);
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		for(int i=(backLinks.size()-1);i>=0;i--){
			WebElement aBackLink = backLinks.get(i);
			try{				
				wait.until(ExpectedConditions.visibilityOf(aBackLink));
				aBackLink.click();			
				
			}catch(org.openqa.selenium.TimeoutException te){
				
			}
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
	
	/** Clicks on 'Show More' till it disappear or say till scattered plot displays all the circles.
	 * 
	 * @param iCatCount - int value of total category count available
	 * @throws Exception Throws Exception
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public void func_ShowAllCircle(int iCatCount) throws Exception{
		
		if(showMoreBtn != null){
			BaseClass.rm.webElementSync(moreCircleCount, "visibility","", null);			
			String strCircleCount = moreCircleCount.getText().trim();
			while(showMoreBtn != null){				
				showMoreBtn.click();
				
				if((Integer.parseInt(strCircleCount) + 25) < iCatCount){
					BaseClass.rm.webElementSync(moreCircleCount, "visibility","", null);
					strCircleCount = moreCircleCount.getText().trim();
					System.out.println("Current circle count " + strCircleCount);
					BaseClass.rm.webElementSync(null, "listsize", strCircleCount, scatteredPlotAllCircle);
				}else{
					BaseClass.rm.webElementSync(chartHeaderTitle, "visibility", "", null);
					break;
				}			
			}
		}else{
			System.out.println("Not able to find show more button on first attempt");
		}
		
	}
	
	/** This method will check if all the categories are marked or not.
	 *  Fails if any of the category is not marked.
	 *  It will work for any level of category for any channel.
	 *  
	 * @param iCatLevel - int level of category. Category/Daypart = 0, Sub-category/Networks = 1, Domain/Programs = 2
	 * @return true/false
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public boolean func_VerifyAllCategoryChecked(int iCatLevel){
		boolean bStatus = true;
		List<WebElement> elementListToBeTest = null;
		if(iCatLevel==0){
			elementListToBeTest = categoryCheckboxList;
		}else if(iCatLevel==1){
			elementListToBeTest = subCategoryCheckboxList;
		}else if(iCatLevel==2){
			elementListToBeTest = itemCategoryCheckboxList;
		}
		
		for(WebElement element:elementListToBeTest){
			if(element.findElement(By.xpath("span[1]")).getAttribute("class").contains("ng-hide")){
				bStatus = false;
				System.out.println(element.findElement(By.xpath("parent::li")).getText().trim() + " is not checked");
			}else{
				System.out.println(element.findElement(By.xpath("parent::li")).getText().trim() + " is checked");
			}
		}
		
		return bStatus;
	}
	
	
	
	/** This method is used to get private WebElement out of this class.
	 *  Make sure that element name should be matched.
	 * 
	 * @param strWebElement - String name of the WebElement variable.
	 * @return Returns WebElement
	 * @CreatedOn 01/12/2016
	 * @author Deepen Shah
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public WebElement func_GetLocalWebElement(String strWebElement) {
		WebElement retWebElement=null;
		try{
			
		
			Field aField = this.getClass().getDeclaredField(strWebElement);
			aField.setAccessible(true);
			retWebElement =  (WebElement)aField.get(this);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return retWebElement;
	}
	
	/** This method will give metric name from metric plot.
	 * 
	 * @param strMetric - String metric label. From 'X','Y','Z',M1 to M5
	 * @return String
	 * @author Deepen Shah
	 * @since 19/01/2016
	 * @ModifiedBy|DescriptionOfModification:
	 * 
	 */
	public String func_GetIndividualMetricName(String strMetric){		
		int index=0;
		String strLabel="";
		
		for(WebElement element:individualMetricsLabel){
			strLabel = element.getText().trim();
			if(strLabel.equalsIgnoreCase(strMetric)){
				index=individualMetricsLabel.indexOf(element);
				System.out.println(element.toString() + " is found at " + index);
				break;
			}
		}		
		
		String strFullText = individualMetricsName.get(index).getText().trim();
		strFullText = strFullText.replaceFirst(strLabel, "");
		
		//System.out.println("JS Node value: " + ((JavascriptExecutor) driver).executeScript("return arguments[0].childNodes[10].nodeValue;", individualMetricsName.get(index)));
		
		return strFullText.trim();
	}
	
	/** This method will give metric name from Reorder panel.
	 * 
	 * @param strMetric - String metric label. From 'X','Y','Z',M1 to M5
	 * @return String
	 * @author Deepen Shah
	 * @since 19/01/2016
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public String func_GetIndividualMetricNameFromReOrderPane(String strMetric){
		String strValue="";
		for(WebElement element:reorderPane){
			if(element.findElement(By.xpath("div[1]")).getText().trim().equalsIgnoreCase(strMetric)){
				strValue = element.findElement(By.xpath("div[2]")).getText().trim();
				break;
			}
		}
		
		return strValue;
	}
	
	/** This method will change axis with given metric.
	 * 	It will achieve this by drag & drop on reorder panel.
	 *  Prerequisite: Reorder panel should be open before calling it.
	 * 
	 * @param strSourceMetrixName - Metric Name. From 'frequency','reach','timespent','coverage','uniqueVisitors','totalVisits','index' & 'composition'
	 * @param strTargetMetric - Axis. From 'X','Y','Z','M1','M2','M3','M4' & 'M5'
	 * @author Deepen Shah
	 * @since 19/01/2016
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public void func_DragAndDropMetric(String strSourceMetrixName,String strTargetMetric){
		WebElement sourceElement=null,targetElement=null;
		
		for(WebElement element:reorderPane){
			WebElement sourceTempElement = element.findElement(By.xpath("div[2]"));
			if(sourceTempElement.getText().trim().equalsIgnoreCase(strSourceMetrixName)){
				sourceElement = sourceTempElement;
			}
			
			if(element.findElement(By.xpath("div[1]")).getText().trim().equalsIgnoreCase(strTargetMetric)){
				targetElement = element.findElement(By.xpath("div[2]"));
			}
		}
		
		(new Actions(driver)).dragAndDrop(sourceElement, targetElement).build().perform();
		
	}
	
	
	/** This method will verify visibility of element based on given case.
	 * 
	 * @param strElement - String. 
	 * 
	 * <pre>From 'metricplot','scatteredplot','datasetbuilder', 'rankplot','weightedrankermetrics','top10proplist'
	 * 'weightedrankercancelbtn','weightedrankersavechangesbtn','verifycheckicon','verifydashicon','orderdropdown'
	 * ,'displaydropdown','datepicker','closepropoverlay','calenderapplybtn','xminmax','yminmax' </pre>
	 * 
	 * @param strValue - String. <pre> Any value required by specific case. For e.g. to verify visibility of '-' icon for 2nd entry in subcategory.
	 * 1st argument: 'verifydashicon' &  2nd argument: 'subcategory:2' </pre>
	 * 
	 * @since 20/01/2016
	 * @author Deepen Shah
	 * @return true/false
	 * @ModifiedBy|DescriptionOfModification: Deepen Shah. 01/02/2016
	 */
	public boolean func_VerifyVisibilityOfElement(String strElement, String strValue){
		boolean bResult = true;
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.ignoring(NoSuchElementException.class);
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		try{
			
			switch(strElement.toLowerCase()){
			
			case "metricplot":
				boolean bTempFlag=true;
				String strMetrics[] = BaseClass.property.getProperty("MetricplotValue").split(",");
				
				String strMetricLabelName[]={};
				String strCSSLocator ="";
				for(String strMetric:strMetrics){
					strMetricLabelName = strMetric.split(":");
					strCSSLocator = "div#metric-id-" + strMetricLabelName[1]+">svg";
					List<WebElement> elementToBeVerify = driver.findElements(By.cssSelector(strCSSLocator));
					if(elementToBeVerify.size()==0)
						bTempFlag = false;
					else
						wait.until(ExpectedConditions.visibilityOf(elementToBeVerify.get(0)));
				}
				
				if(!bTempFlag)
					bResult = false;

				break;
				
			case "scatteredplot":
				wait.until(ExpectedConditions.visibilityOf(mediaRankerScatteredPlot));
				break;
				
			case "datasetbuilder":
				wait.until(ExpectedConditions.visibilityOf(tvChannel));
				wait.until(ExpectedConditions.visibilityOf(digitalChannel));
				break;
				
			case "rankplot":
				wait.until(ExpectedConditions.visibilityOf(rankMetricPlot));
				break;
				
			case "weightedrankermetrics":
				if(weightedRankerMetricsPanelList.size()==0 || weightedRankerMetricsPanelList.size() != 8){
					bResult = false;
					System.out.println("Weighted Ranker List has not matching metric list. It is: " + weightedRankerMetricsPanelList.size());
				}
					
				for(WebElement element:weightedRankerMetricsPanelList)
					wait.until(ExpectedConditions.visibilityOf(element));
				break;
			case "top10proplist":
				int iCatCount = categoryList.size();
				if(top10PropList.size()==0){
					bResult = false;
					System.out.println("No property displayed in top 10 prop area");
				}
				if(iCatCount< 10){
					if(top10PropList.size()!=iCatCount){
						bResult = false;
						System.out.println("Top properties count are not matching. As categories are less than 10 properties count should be same as category count.");
						System.out.println("Propertis: " + top10PropList.size() +" and categories: " + iCatCount);
					}
				}
				
				for(WebElement element:top10PropList)
					wait.until(ExpectedConditions.visibilityOf(element));
				break;

			case "weightedrankercancelbtn":
				wait.until(ExpectedConditions.visibilityOf(weightedRankerCancelBtn));
				break;
			
			case "weightedrankersavechangesbtn":
				wait.until(ExpectedConditions.visibilityOf(weightedRankerSaveChangesBtn));
				break;
				
			case "showmorebuttonheader":
				wait.until(ExpectedConditions.visibilityOf(showMoreBtn));
				System.out.println("show more button is visible");
				break;

			case "verifycheckicon":
				String strValues[] = strValue.split(":");
				List<WebElement> elementToBeUsed = null;
				if(strValues[0].equalsIgnoreCase("category")){
					elementToBeUsed = categoryCheckboxList;
				}else if(strValues[0].equalsIgnoreCase("subcategory")){
					elementToBeUsed = subCategoryCheckboxList;
				}else if(strValues[0].equalsIgnoreCase("item")){
					elementToBeUsed = itemCategoryCheckboxList;
				}else{
					System.out.println("Given argument: " + strValue + " : is not valid.");
				}
				
				WebElement firstSpanElement = elementToBeUsed.get(Integer.parseInt(strValues[1])).findElement(By.xpath("span[1]"));
				WebElement secondSpanElement = elementToBeUsed.get(Integer.parseInt(strValues[1])).findElement(By.xpath("span[2]"));
				wait.until(ExpectedConditions.visibilityOf(firstSpanElement));
				wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(secondSpanElement)));
				
				
				break;
				
			case "verifydashicon":
				
				strValues = strValue.split(":");
				elementToBeUsed = null;
				if(strValues[0].equalsIgnoreCase("category")){
					elementToBeUsed = categoryCheckboxList;
				}else if(strValues[0].equalsIgnoreCase("subcategory")){
					elementToBeUsed = subCategoryCheckboxList;
				}else if(strValues[0].equalsIgnoreCase("item")){
					elementToBeUsed = itemCategoryCheckboxList;
				}else{
					System.out.println("Given argument: " + strValue + " : is not valid.");
				}
				
				firstSpanElement = elementToBeUsed.get(Integer.parseInt(strValues[1])).findElement(By.xpath("span[1]"));
				secondSpanElement = elementToBeUsed.get(Integer.parseInt(strValues[1])).findElement(By.xpath("span[2]"));
				
				String strDashIconClass = secondSpanElement.getAttribute("class");
				if(strDashIconClass.contains("ng-hide")){
					bResult = false;
					System.out.println(elementToBeUsed.get(Integer.parseInt(strValues[1])).findElement(By.xpath("parent::li")).getText().trim() + " has no dash icon");
				}
					
				
				/*
				wait.until(ExpectedConditions.visibilityOf(secondSpanElement));
				wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(firstSpanElement)));
				*/
				
				break;
			
			case "orderdropdown":
				wait.until(ExpectedConditions.visibilityOf(orderDropdown));
				break;
				
			case "displaydropdown":
				wait.until(ExpectedConditions.visibilityOf(displayDropdown));
				break;
				
			case "datepicker":
				wait.until(ExpectedConditions.visibilityOf(datepicker));
				break;
				
			case "closepropoverlay":
				wait.until(ExpectedConditions.visibilityOf(closePropertyOverlay));
				break;

			case "calenderapplybtn":
				wait.until(ExpectedConditions.visibilityOf(calenderApplyBtn));
				break;	

			case "xminmax":
				wait.until(ExpectedConditions.visibilityOf(xMax));
				wait.until(ExpectedConditions.visibilityOf(xMin));
				break;
				
			case "yminmax":
				wait.until(ExpectedConditions.visibilityOf(yMax));
				wait.until(ExpectedConditions.visibilityOf(yMin));
				break;
			case "mediarankerheading":
				wait.until(ExpectedConditions.visibilityOf(mediaRankerheading));
				break;	
			case "datepickeropenedwidget":
				wait.until(ExpectedConditions.visibilityOf(datePickerOpenedWidget));
				break;
				
			case "slowtvranker":
				wait.withTimeout(120, TimeUnit.SECONDS);
				wait.until(ExpectedConditions.visibilityOf(datepicker));
				wait.until(ExpectedConditions.elementToBeClickable(datepicker));
				wait.withTimeout(30, TimeUnit.SECONDS);				
				break;
				
				default:
					break;
			
			}
			
		}catch(Exception e){
			bResult = false;
			System.out.println(e.getMessage());
			System.out.println("Not able to verify visibility of " + strElement);
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return bResult;
	}
	
	/** Overloaded method for func_VerifyVisibilityOfelement for only String case value.
	 * 
	 * @param strElement - String
	 * 
	 * <pre>From 'metricplot','scatteredplot','datasetbuilder', 'rankplot','weightedrankermetrics','top10proplist'
	 * 'weightedrankercancelbtn','weightedrankersavechangesbtn','verifycheckicon','verifydashicon','orderdropdown'
	 * ,'displaydropdown','datepicker','closepropoverlay','calenderapplybtn','xminmax','yminmax' </pre>
	 * 
	 * @return - boolean (true/false)
	 * @since 29/01/2016
	 * @author Deepen Shah
	 */
	public boolean func_VerifyVisibilityOfElement(String strElement){
		return func_VerifyVisibilityOfElement(strElement,"");
	}
	
	
	/** This method will check if all the categories are not marked.
	 *  Fails if any of the category is marked.
	 *  It will work for any level of category for any channel.
	 *  
	 * @param iCatLevel - int level of category. Category/Daypart = 0, Sub-category/Networks = 1, Domain/Programs = 2
	 * @return true/false
	 * @author Deepen Shah
	 * @since 21/01/2016
	 * 
	 * @ModifiedBy|DescriptionOfModification:
	 */
	public boolean func_VerifyAllCategoryUnChecked(int iCatLevel){
		boolean bStatus = true;
		List<WebElement> elementListToBeTest = null;
		if(iCatLevel==0){
			elementListToBeTest = categoryCheckboxList;
		}else if(iCatLevel==1){
			elementListToBeTest = subCategoryCheckboxList;
		}else if(iCatLevel==2){
			elementListToBeTest = itemCategoryCheckboxList;
		}
		
		for(WebElement element:elementListToBeTest){
			WebElement checkIcon = element.findElement(By.xpath("span[1]"));
			
			
			String strCheckIconClass = checkIcon.getAttribute("class");
			String strDashIconClass="";
			
			if(iCatLevel!=2){
				WebElement dashIcon = element.findElement(By.xpath("span[2]"));
				strDashIconClass = dashIcon.getAttribute("class");
			}else{
				
				//As 3rd level has no 2nd span hence no point to check.
				strDashIconClass="ng-hide";
			}
			
			if(strCheckIconClass.contains("ng-hide") && strDashIconClass.contains("ng-hide")){
				System.out.println(element.findElement(By.xpath("parent::li")).getText().trim() + " is grayed");	
				
			}else{
				bStatus = false;
				System.out.println(element.findElement(By.xpath("parent::li")).getText().trim() + " is checked or has dash icon");
			}
		}
		
		return bStatus;
	}
	
	
	/** Synchronization till List element to refresh.
	 * 
	 * @since 21/01/2016
	 * @param strCase
	 * @throws Exception
	 * @author Deepen shah
	 * 
	 */
	public void func_WaitTillListRefresh(String strCase) throws Exception{
		switch(strCase.toLowerCase()){
		
		case "categorylist":
			BaseClass.rm.webElementSync(categoryList.get(categoryList.size()-1), "staleness");
			BaseClass.rm.webElementSync(categoryList.get(categoryList.size()-1), "visibility");
			break;
			
		case "subcategorylist":
			BaseClass.rm.webElementSync(subCategoryList.get(subCategoryList.size()-1), "staleness");
			BaseClass.rm.webElementSync(subCategoryList.get(subCategoryList.size()-1), "visibility");
			break;
			
		case "itemcategorylist":
			BaseClass.rm.webElementSync(itemCategoryList.get(itemCategoryList.size()-1), "staleness");
			BaseClass.rm.webElementSync(itemCategoryList.get(itemCategoryList.size()-1), "visibility");
			break;
			
		case "overlayproperties":
			BaseClass.rm.webElementSync(top10PropList.get(top10PropList.size()-1), "staleness");
			BaseClass.rm.webElementSync(top10PropList.get(top10PropList.size()-1), "visibility");
			break;
		
		}
	}
	
	
	/** This method is to enter the weighted ranker metric percentage.
	 * 
	 * @param strMetric - String. String metric name.
	 * @param strVal - String. Percentage value.
	 * @since 29/01/2016
	 * @author Deepen Shah
	 */
	public void func_EnterWeightedRankerPercentage(String strMetric,String strVal){
		WebElement percFieldElement=null;
		
		/*for(WebElement element:weightedRankerMetricsPanelList){
			System.out.println("ys"+element.findElement(By.xpath(weightRankerLabel)).getText());
			if(element.findElement(By.xpath(weightRankerLabel)).getText().trim().equalsIgnoreCase(strMetric)){
				percFieldElement = element.findElement(By.xpath(rankValue));
			}					
		}*/
		System.out.println("Entering " + strVal + " for " + strMetric);
		percFieldElement = driver.findElement(By.xpath(MessageFormat.format(strRankInput, strMetric)));
		System.out.println("Found " + percFieldElement.getAttribute("value"));
		
		percFieldElement.clear();
		percFieldElement.sendKeys(strVal);
	}
	
	/** This method will return list of properties displayed on property overlay.
	 * 
	 * @return - List. Of type String
	 * @since 29/01/2016
	 * @author Deepen Shah
	 */
	public List<String> func_GetTop10Properties(){
		List<String> propertyList = new ArrayList<String>();
		
		for(WebElement element:top10PropList){
			String strTempVal=element.getText().trim();			
			strTempVal = strTempVal.substring(strTempVal.indexOf(" ")+1,strTempVal.length());
			System.out.println("func_GetTop10Properties:: Prop: " + strTempVal);
			
			propertyList.add(strTempVal);
		}
		
			
		
		return propertyList;
	}
	
	/** This method will give name of all categories/subcategories/item.
	 *  It will work for digital or TV channel.
	 *  
	 * @param strCategory - String. Values from category/subcategory/item irrespective of channle.
	 * @return - List. Of type String.
	 * @since 29/01/2016
	 * @author Deepen Shah
	 */
	public List<String> func_GetAllCategoryName(String strCategory){
		List<String> categoryNameList = new ArrayList<String>();
		List<WebElement> listCatEle=null;
		
		if(strCategory.equalsIgnoreCase("category")){
			listCatEle = categoryList;
		}else if(strCategory.equalsIgnoreCase("subcategory")){
			listCatEle = subCategoryList;
		}else if(strCategory.equalsIgnoreCase("item")){
			listCatEle = itemCategoryList;
		}
		
		for(WebElement element:listCatEle)
			categoryNameList.add(element.findElement(By.xpath("div[2]")).getText().trim());
		
		
		return categoryNameList;
		
	}

	/** Method is to check if element is enable or disable.
	 * 
	 * @author shakava 
	 * @param strCase - String case.
	 * @return boolean - True/false 
	 * @since 1 Feb 2016
	 */
	
	public boolean func_isEnabled(String strCase){
		boolean enabled = false;
		
		switch(strCase){
		case "savechangebutton":
			enabled = weightedRankerSaveChangesBtn.isEnabled();
			break;
		}
		
		return enabled;
	}
	
	/**This method will de select one by one selected date
	 * @author shakava
	 * @since 1 Feb 2016
	 */
	public void func_deselectSelectedDate() throws Exception{
		
		
		for(WebElement dates: listDatesOnCalender){
			System.out.println("BG color for selected date: "+dates.getCssValue("background-color"));
			if(dates.getCssValue("background-color").contentEquals("rgb(255,​ 15,​ 83)")){
				System.out.println("in if condition BG color for selected date: "+dates.getCssValue("background-color"));
				dates.click();
				Thread.sleep(1000);
			}
		}
		

	}
	/** This method will get top 10 properties based on given metric value. 
	 * 
	 * @param strMetric - String. Values from frequency,reach,timeSpent,coverage,uniqueVisitors,totalVisits,index,composition & rank
	 * @param strCategoryLevel - String. Values from category/subcategory/item
	 * @return - List of type String. 
	 * @since 01/02/2016
	 * @author Deepen Shah
	 */
	public List<String> func_GetTop10PropertiesOrderByMetric(String strMetric, String strCategoryLevel){
		
		List<WebElement> categoryWebElementList = null;
		
		if(strCategoryLevel.equalsIgnoreCase("category"))
			categoryWebElementList = categoryList;
		else if(strCategoryLevel.equalsIgnoreCase("subcategory"))
			categoryWebElementList = subCategoryList;
		else if(strCategoryLevel.equalsIgnoreCase("item"))
			categoryWebElementList = itemCategoryList;
		
		//Preparing id vs category name
		HashMap<String,String> idCateNameSet = new HashMap<String, String>();
		for(WebElement element:categoryWebElementList){
			String strClassName = element.getAttribute("class");
			strClassName = strClassName.substring(strClassName.indexOf("id"), strClassName.length());
			strClassName = strClassName.replace("-", "");
						
			String strCatName = element.findElement(By.xpath("div[2]")).getText().trim();
			idCateNameSet.put(strClassName, strCatName);
		}
		
		//Preparing metric value vs category name 
		Map<String,Double> valueIdCatSet = new HashMap<String,Double>();
		for(WebElement element:driver.findElements(By.cssSelector("div#metric-id-" + strMetric +">svg>g>circle"))){
			String strClassName[] = element.getAttribute("class").split(" ");					
			Double d = Double.parseDouble(element.getAttribute("cx"));
			
			
			
			System.out.println("ID found from circle: " + strClassName[1] +" and value: " + d);
			
			valueIdCatSet.put(idCateNameSet.get(strClassName[1]),d);
		}	
		
		MetricValueCompartor myComparator = new MetricValueCompartor(valueIdCatSet);
		Map<String,Double> map = new TreeMap<String,Double>(myComparator);
		map.putAll(valueIdCatSet);
		System.out.println("Sorted " +map);
		
		
		List<String> sortedPropList = new ArrayList<String>(map.keySet());
		
		if(sortedPropList.size()<10){
			return sortedPropList;
		}else{
			return sortedPropList.subList(0, 10) ;
		}
	}
	
	
	/** Method will help to select value from dropdown
	 * 
	 * @param strCase
	 * @param strValue
	 * @since 01/02/2016
	 * @author Deepen Shah
	 */
	public void func_SelectValueFromDropdown(String strCase,String strValue){
		Select elementDropdown;
		
		switch(strCase.toLowerCase()){
		case "propertyorder":
			elementDropdown = new Select(orderDropdown);
			elementDropdown.selectByVisibleText(strValue);
			break;
			
		case "display":
			elementDropdown = new Select(displayDropdown);
			elementDropdown.selectByVisibleText(strValue);
			break;
		}
		
		 
	}
	
	/** Not ready to use.
	 * 
	 * @param strLevel
	 * @return
	 */
	public boolean func_VerifyTop10PropertiesLevel(String strLevel){
		boolean bStatus = true;
		
		for(WebElement element:top10PropList){
			String strClasses[] = element.getAttribute("class").split(" ");
			if(strClasses.length!=3){
				bStatus=false;
				System.out.println("func_VerifyTop10PropertiesLevel:: 3 classes to be present ");
			}else{
				
			}
			
		}
		
		
		return bStatus;
	}
	
	/** This method will return rank values for each metric.
	 *  Metric Name will be in lower case.
	 *  Value will also be in string.
	 *  
	 * @return - HashMap. Map with key as metric name and value as rank.
	 * @since 02/02/2016
	 * @author Deepen Shah
	 * 
	 */
	public HashMap<String,String> func_GetWeightedRankerMetricAndValue(){
		HashMap<String,String> rankerMetricNameAndValue = new HashMap<String,String>();
		
		for(WebElement element:weightedRankerMetricsPanelList){
			
			String strMetricName = element.findElement(By.xpath("div[1]")).getText().trim().toLowerCase();
			
			String strVal = element.findElement(By.xpath("div[2]/div")).getAttribute("style").replaceAll("\\D+","").trim();
			
			rankerMetricNameAndValue.put(strMetricName, strVal);
		}
		
		
		return rankerMetricNameAndValue;
	}

	/**
	 * @author Shailesh Kava
	 * @param strCase - string - "showaudiencetoggleimage"
	 * @return  Point
	 * @since 4-Feb-216
	 */
	public Point func_getLocation(String strCase){
		Point p = null;
		switch(strCase.toLowerCase()){
		case "showaudiencetoggleimage":
				p= showAudienceToggleImage.getLocation();
			break;
			
		}
		return p;	
	}

	/** This method will clear all percentage for weighted ranker.
	 * 
	 * @author Deepen Shah
	 * @since 04/02/2016
	 * 
	 */
	public void func_ClearAllRankerPercentage(){
		for(WebElement element:weightedRankerMetricsPanelList){			
			element.findElement(By.xpath("div[3]/input")).clear();
		}
	
	}
	
	/**This method will return width of weighted ranker bar for mentioned ranker
	 * 
	 * @param strWeightedRankerName (e.g. 'reach','coverage','timeSpent'..)
	 * @return Weighted ranker width % value
	 * @author Shailesh Kava
	 * @throws Exception 
	 * @since 17-May-2016
	 */
	public int func_getWeightedRankerBarWidth(String strWeightedRankerName) throws Exception{
		
		if(BaseClass.browserName.equals("IE"))
			Thread.sleep(8000);
		else
			Thread.sleep(2000);
		int rankerWidth = 0;
		WebElement we = driver.findElement(By.xpath(MessageFormat.format(weightedRankerPercentageBarGraph, strWeightedRankerName)));
		we.click();
		//String[] weightedRankerWidth = driver.findElement(By.xpath(MessageFormat.format(weightedRankerPercentageBarGraph, strWeightedRankerName))).getAttribute("style").split(":");
		String[] weightedRankerWidth = we.getAttribute("style").split(":");
		rankerWidth = Integer.parseInt(weightedRankerWidth[1].replace("%", "").replace(";", "").trim()); 
		
		return rankerWidth;
	}
	

	public ArrayList<String> getDisplayDropDownItems(){
		ArrayList<String> displayDropDownItems=new ArrayList<>();
		for (WebElement displayDropDown:dispalyDropDownItems){
			displayDropDownItems.add(displayDropDown.getText());
		}
		return displayDropDownItems;

	}
	
	/** This method will give bubble count for given metric plot.
	 *  
	 * @param strMetricPlotName String metric plot name (User property 'MetricplotValue')
	 * @return int
	 * @author Deepen Shah
	 * @since 19 May 2016
	 */
	public int getMetricPlotBubbleCount(String strMetricPlotName){
		return driver.findElements(By.cssSelector(MessageFormat.format(strMetricPlotBubblesCSSContains, strMetricPlotName))).size();
	}


/**This method will deselect dates a provided in input
 * 
 * @param 
 * @return 
 * @author Amrutha Nair
 * @throws InterruptedException 
 * @since 19-May-2016
 */

public boolean  func_deselectSelectedDate(int count, String strCondition) throws InterruptedException{

	boolean status=true;
	try{
		if(strCondition.contentEquals("all")){
			count=selectedDatesList.size();
			System.out.println("count"+count);
		}
	if(selectedDatesList.size()>=count){
		
		for(int k=1;k<=count;k++){				
			
				driver.findElement(By.xpath(MessageFormat.format(strSelectedDates, 1))).click();
				
				////selectedDates.get(k).click();
				Thread.sleep(3000);
				System.out.println("k"+k);
			
			
		}
	}
	else{
		CustomReporter.log("Since provided dates to be deslected count is greater than the actual selected dates present, no dates will be deselected");
	}

	}catch(Exception e){
		System.out.println(e);							
			status=false;
		}
	return status;

}	
		
/**This method will select dates a provided in input
 * 
 * @param 
 * @return 
 * @author Amrutha Nair
 * @throws InterruptedException 
 * @since 19-May-2016
 */

	public boolean func_selectDate(int count,String strCondition) throws InterruptedException{
		Boolean status=true;
		

		try{
			if(strCondition.contentEquals("all")){
				count=deSelectedDates.size();
				System.out.println(count);
			}
			if(deSelectedDates.size()>=count){
				for(int k=1;k<=count;k++){
						driver.findElement(By.xpath(MessageFormat.format(strdeSelectedDates,1))).click();
						Thread.sleep(3000);
				}
			}
			else{
				CustomReporter.log("Since provided dates to be selected count is greater than the actual selected dates present, no dates will be deselected");
			}
		}catch(Exception e){
			System.out.println(e);							
				status=false;
			}
		return status;
	
	}
	
	/**This method will select dates with respect to   the position provided by the user
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair
	 * @throws InterruptedException 
	 * @since 19-May-2016
	 */
	public boolean func_selectDateWRTPosition(String position) throws InterruptedException{
		Boolean status=true;
		int intPosition=0;
		String [] strPosition=position.split(",");
		try{
		for (int i=0;i<strPosition.length;i++){
			intPosition=Integer.parseInt(strPosition[i]);
			System.out.println(intPosition);
			if(intPosition <deSelectedDates.size()){
				driver.findElement(By.xpath(MessageFormat.format(strdeSelectedDates, intPosition-i))).click();
				//deSelectedDates.get(intPosition-1).click();
				CustomReporter.log("The user has selected date at position '"+intPosition+"'");
			}
			else{
				CustomReporter.log("Since there is no date present at the position '"+intPosition+"' which can be selected, the user is not selecting any dates");
			}

		}
		
		}catch(Exception e){
			System.out.println(e);							
				status=false;
			}
		return status;
	
	}
	
			
	/** To check if this category/subcategory/item is selected or not
	 *  It will work for both the channel
	 * 
	 * @param strCatName
	 * @return
	 * @author Deepen Shah
	 * @since 20 May 2016
	 */
	public boolean isCategorySelected(String strCatName){
		int iSize = driver.findElements(By.xpath(MessageFormat.format(strCheckMarkByCatNameContains, strCatName))).size();
		
		if(iSize > 0){
			return true;
		}else{
			return false;
		}
	}
			
	/**This method is used to click on Arrow of date picker drop down
	 * 
	 * @throws Exception
	 * @author Shailesh Kava
	 * @since 20-May-2016 
	 */
	public void closeDatePickerByArrow() throws Exception{
		WebElement element= driver.findElement(By.cssSelector("div.mr-date-picker"));

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	
	/**This method checks whether future dates are allowed to get selected
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair
	 * @throws InterruptedException 
	 * @since 24-May-2016
	 */
	
	public boolean isFuturedateAllowed(){
		boolean status=false;
		//checking for FutureDate
		
		Calendar c = Calendar.getInstance();
		String strCurrentMonth=c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ).toLowerCase();
		int intCurrentyear = Calendar.getInstance().get(Calendar.YEAR);
		String strCurrenrYear=Integer.toString(intCurrentyear);
		System.out.println("1month:"+strCurrentMonth);
		System.out.println("1year:"+intCurrentyear);
		
		WebElement monthAndYear=timeWidgetMonthYearLst.get(0);
		
		String strMonth=monthAndYear.getText().toLowerCase();
		boolean bstatus=true;
		
		while (bstatus){
			if(!(strMonth.contains(strCurrenrYear))){
			    nextMonth.click();
			   
			   
			     monthAndYear=timeWidgetMonthYearLst.get(0);
			     strMonth=monthAndYear.getText().toLowerCase();
			     System.out.println("yearnextclick:"+strMonth);
			}
			else{
				if(!(monthAndYear.getText().toLowerCase().contains(strCurrentMonth))){
					 nextMonth.click();
					 System.out.println("Monthextclick");
				     monthAndYear=timeWidgetMonthYearLst.get(0);
				     strMonth=monthAndYear.getText().toLowerCase();
				     System.out.println("Monthextclick:"+strMonth);
				}
				else{
					nextMonth.click();
					bstatus=false;
					System.out.println("Outof loop");
				}
			}
		}
		if(selectedDatesList.size()>0 || deSelectedDates.size()>0){
			status=false;
			System.out.println("Future Dates can be selected");
		}
		
	
		return status;
		
	}
	

	/**This method will verify duplicate Background color with each category and will return boolean val
	 * 
	 * @return bVerify (false if duplicate found)
	 * @author Shailesh Kava
	 * @since 24-May-2016
	 */
	public boolean verifyDuplicateColorOfSelectedCategory(){
		
		boolean bVerify = true;
		String strFailureMessage = "unique";
		HashMap<String,String> hm=new HashMap<String,String>();
		HashMap<String,String> hmNew=new HashMap<String,String>();
		
		for(WebElement categoty:selectedVisibleCategories){
			WebElement we = categoty.findElement(By.xpath(strCategoryBGColor));
			System.out.println(we);
			String bgColor = categoty.findElement(By.xpath(strCategoryBGColor)).getCssValue("background-color").trim();
			String catName = categoty.findElement(By.xpath(strCategoryName)).getText().trim();
			
			if(hm.containsValue(bgColor)){
				strFailureMessage = "BG color is same for cagegory ["+catName+"==="+bgColor+"] with category ["+hmNew.get(bgColor)+"]";
				CustomReporter.errorLog("BG color is same for cagegory ["+catName+"==="+bgColor+"] with category ["+hmNew.get(bgColor)+"]");
				System.out.println("BG color is same for cagegory ["+catName+"==="+bgColor+"] with category ["+hmNew.get(bgColor)+"]");
				bVerify = false;
				break;
			}else{
				hm.put(catName,bgColor);
				hmNew.put(bgColor,catName);
			}
		}
		
		return bVerify;
	}
	
	/**This method will return bg color of any specified category name
	 * 
	 * @param strCatName
	 * @return
	 * @author Shailesh Kava
	 * @since 14-May-2016
	 */
	public String getCategoryBGColor(String strCatName){
		String bgColor = null;
		
		bgColor = driver.findElement(By.xpath(MessageFormat.format(strBGColorBasedOnCategoryName, strCatName))).getCssValue("background-color").trim();
		
		return bgColor;
	}


	
	/**This method verifies the axis values and bubble on hover values are same
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair
	 * @throws InterruptedException 
	 * @since 24-May-2016
	 */
	public boolean verifyAxisValuesWithBubbleOnHover(){
		boolean Status=true;
		String [] axisValues=new String[3];
		axisValues[0]=driver.findElement(By.xpath(MessageFormat.format(strReorderPanWElement, "X"))).getText().trim().toLowerCase();
		System.out.println("X Axis:"+axisValues[0]);
		axisValues[1]=driver.findElement(By.xpath(MessageFormat.format(strReorderPanWElement, "Y"))).getText().trim().toLowerCase();
		System.out.println("X Axis:"+axisValues[1]);
		axisValues[2]=driver.findElement(By.xpath(MessageFormat.format(strReorderPanWElement, "Z"))).getText().trim().toLowerCase();
		System.out.println("X Axis:"+axisValues[2]);
		Actions action = new Actions(driver);
		String toolTipName=null;
		String[] toolTipValues=new String[3];
		String toolTipTempValue=null;
		
		for(WebElement bubble:bubblesInScatterPlot){
			action.moveToElement(bubble).perform();
			toolTipName =scatterPlotToolTipName.getText();
			for(int i=0;i<3;i++){
				//toolTipValues[i]=scatterPlotToolTipVAlues.get(i).getText().trim().toLowerCase();
				
				toolTipTempValue=scatterPlotToolTipVAlues.get(i).getText().split(":")[0];
				toolTipValues[i]=toolTipTempValue.trim().toLowerCase();
				System.out.println("ToolTip:"+toolTipValues[i]);
			}
			for(int j=0;j<3;j++){
				if(!(axisValues[j].contentEquals(toolTipValues[j]))){
					CustomReporter.errorLog("The Axis values are not matching with values getting populated in tool tip for the bubble '"+toolTipName+"'");
					Status=false;
				}
				else{
					CustomReporter.log("The Axis values are  matching with values getting populated in tool tip for the bubble '"+toolTipName+"'");
				}
			}
			
		}
		return Status;
		
		
	}
	
	/**This method verifies the bubbles' design  on hover
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair
	 * @throws InterruptedException 
	 * @since 24-May-2016
	 */
	public boolean verifyBubblesDesignOnHover() throws InterruptedException{
		boolean status=true;
		boolean bstatus=true;
		Actions action = new Actions(driver);
		String toolTipName=null;
		String strOpacity=null;
		for(int k=0;k<bubblesInScatterPlot.size();k++){
	
			bstatus=true;
	
			action.moveToElement(bubblesInScatterPlot.get(k)).perform();
			Thread.sleep(1500);	
			toolTipName =scatterPlotToolTipName.getText();
			Thread.sleep(1500);	
			for(int i=0;i<bubblesInScatterPlot.size();i++){
				strOpacity=bubblesInScatterPlot.get(i).getAttribute("style");
				strOpacity=strOpacity.split("opacity:")[1];
				strOpacity=strOpacity.split(";")[0].trim();			
				if(!(strOpacity.contentEquals("0.3"))){
					if(strOpacity.contentEquals("1")){
						CustomReporter.log("The bubble for '"+toolTipName+"' is getting highlighted on hovering on the same");
					}
					else{
						CustomReporter.errorLog("The other bubbles are not getting dim on hovering on the bubble '"+toolTipName+"'");
						status=false;
						bstatus=false;
						break;
					}
				}
				
			}
			if(bstatus){
				CustomReporter.log("The other bubble are getting dim  on hightlighting bubble  for '"+toolTipName+"'");
			}
			}
		return status;
		
	}
	
	
	/**This method double clicks on the bubble considering the id passed
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair 
	 * @since 24-May-2016
	 */
	public void performDoubleClick(int id){
		Actions action = new Actions(driver);
		WebElement bubbleElement=bubblesInScatterPlot.get(id);
		action.doubleClick(bubbleElement).perform();	
		
	}
	
	/**This method returns tool tip name for the bubble with passed id 
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair 
	 * @since 24-May-2016
	 */
	
	public String performMouseOverBubbleAndGetToolTipName(int id){
		String name=null;
		Actions action = new Actions(driver);
		action.moveToElement(bubblesInScatterPlot.get(id)).perform();
		name =scatterPlotToolTipName.getText();
		return name;
	}
	
	/**This method verifies whether bubble with passed name is present in scatterplot 
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair 
	 * @since 24-May-2016
	 */	
	
	public boolean verifyBubblePresent(String name){
		boolean status=false;
		Actions action = new Actions(driver);
		String toolTipName=null;
		for(WebElement bubble:bubblesInScatterPlot){
			action.moveToElement(bubble).perform();
			toolTipName =scatterPlotToolTipName.getText();
			if(toolTipName.trim().toLowerCase().contentEquals(name)){
				status=true;
				break;
				}
		}
		return status;
	}
	

	/** Method to get currently selected drop down value
	 * @return
	 * @author Deepen Shah
	 * @since 25 May 2016
	 */
	public String getSelectedDisplayDropdownValue(){
		Select selectDD = new Select(displayDropdown);
		return selectDD.getFirstSelectedOption().getText().trim();
	}
	/**This method selects the data set element by name
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair 
	 * @since 25-May-2016
	 */	
	public void toggleDataSetElement(String Name){
		
		 driver.findElement(By.xpath(MessageFormat.format(strDataSetElement, Name))).click();

	}
	
	/** Method to close or open weighted ranker.
 	 * 
	 * 
	 * @param bOpen - boolean true to open weighted ranker and false to close weighted ranker
	 * @throws Exception
	 * @author Deepen Shah
	 * @since 29 May 2016
	 */
	public void openCloseWeightedRanker(boolean bOpen) throws Exception{
		
		WebDriverWait wait = new WebDriverWait(driver, 2);
		boolean bState=false;
		
		try{
			 wait.until(ExpectedConditions.visibilityOf(weightedRankerMetricsPanelList.get(0)));
			 bState = true;
		}catch(Exception e){
			System.out.println("Weighted ranker is closed");
		}
		
		if(bOpen){
			if(!bState){
				weightedRankerBtn.click();
				Thread.sleep(2000);
			}
			
		}else{
			if(bState){
				weightedRankerBtn.click();
				Thread.sleep(2000);
			}
		}
	}
	
	
	/** To find particular category. Category which has at least 'iHowMany' number of
	 *  sub category.
	 *  
	 * @param strLevel - String level from 'category' or 'subcategory'
	 * @param iHowMany - int count, number of subcategory
	 * @return int category id
	 * @throws Exception
	 * @author Deepen Shah
	 * @since 30 May 2016
	 */
	public int getCategoryIdHavingMultipleSubCategory(String strLevel,int iHowMany) throws Exception{		
		List<WebElement> cateElement=null;
		List<WebElement> subCateElement=null;
		WebElement syncElement = null;
		WebElement goBack = null;
		
		if(strLevel.equalsIgnoreCase("category")){
			System.out.println("in cat");
			cateElement = categoryList;
			subCateElement = subCategoryList;
			syncElement = subCategory;
			goBack = backToCategory;
		}else if(strLevel.equalsIgnoreCase("subcategory")){
			System.out.println("in subcat");
			cateElement = subCategoryList;
			subCateElement = itemCategoryList;
			syncElement = itemCategory;
			goBack = backToSubCategory;
		}
		
		int i=0;
		for(i=0;i<cateElement.size();i++){
			cateElement.get(i).click();
			BaseClass.rm.webElementSync(syncElement, "visibility");
			
			if(subCateElement.size() >= iHowMany){
				goBack.click();
				break;
			}else{
				goBack.click();
			}
			
			
		}
		
		
		return i;
	}
}
			



/** Comparator class for sorting of categories based on metric's value (Double)
 *    
 * 
 * @author Deepen Shah
 * @since 01/02/2016
 *
 */
class MetricValueCompartor implements Comparator<Object> {

	Map<String,Double> categoriesValueMap;

	public MetricValueCompartor(Map<String,Double> map) {
	    this.categoriesValueMap = map;
	}

	public int compare(Object o1, Object o2) {
	
	    return (categoriesValueMap.get(o2)).compareTo(categoriesValueMap.get(o1));
	
	}
	
}
