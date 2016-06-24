package com.IDIOM.pages;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
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

/**
 * @author Deepen Shah
 * @since 26 April 2016
 *
 */
public class AudienceBuilderPage {
	WebDriver driver;
	public AudienceBuilderPage(WebDriver driver){
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	
	@FindBy(xpath="//iu-audience-metrics-summary/div[2]")
	public WebElement noSuccessMetricText;
	
	@FindBy(xpath="//div[@class='drill-list__title ng-binding']")
	public WebElement allMetricsLabel;

	@FindBys(@FindBy(xpath="//div[@class='drill-list__title ng-binding']"))
	public List<WebElement> blackLabelList;
	
	@FindBys(@FindBy(xpath="//ul[@class='iu-block-list']/li"))
	public List<WebElement> successMetricCards;
	
	@FindBys(@FindBy(xpath="//ul[@class='iu-block-list']/li//p[@class='iu-block__block-content ng-binding']"))
	public List<WebElement> successMetricCardsName;
	
	@FindBys(@FindBy(xpath="//ul[@class='iu-block-list']/li//p[@class='iu-block__block-title']/span[1]"))
	public List<WebElement> successMetricCardsCategoryName;
	
	@FindBy(id="success_tab")
	public WebElement successMetricTab;
	
	@FindBy(id="definition_tab")
	public WebElement audienceDefinitionTab;
		
	@FindBys(@FindBy(xpath="//div[@class='merlin merlin-fade-out-in']/query/div[@dnd-allowed-types=\"['query']\"]/div"))
	public List<WebElement> groupList; 
	
	@FindBys(@FindBy(xpath="//ul[@class='drill-list__items items']//span[@class='ng-binding']"))
	public List<WebElement> metricOrAttributeCategoryList;
	
	//This element is just for final metric name not the category. For category use metricOrAttributeCategoryList
	@FindBys(@FindBy(xpath="//ul[@class='drill-list__items items']//div[@class='iu-widget-drill__attr__aname ellipsis ng-binding']"))
	public List<WebElement> metricNamesList;
	
	@FindBys(@FindBy(xpath="//ul[@class='drill-list__items items']/li"))
	public List<WebElement> attributesList;
	
	@FindBys(@FindBy(xpath="//div[@class='iu-pill__group']/div[@class='iu-pill iu-pill__item pop ng-scope']"))
	public List<WebElement> queriesList;

	@FindBy(xpath="//div[@class='iu-widget-drill iu-widget-drill__attr--btn ng-scope']//small[1]")
	public WebElement removeAllLink;
	
	@FindBy(xpath="//div[@class='iu-widget-drill iu-widget-drill__attr--btn ng-scope']//span[@class='ion ion-chevron-right']")
	public WebElement successMetricsOraudienceDefinitionArrowLink;
	
	@FindBy(xpath="//div[@class='iu-widget-drill iu-widget-drill__attr--btn ng-scope']//div[@class='iu-widget-drill__attr__aname ellipsis ng-binding']")
	public WebElement countForSuccessMetricsOraudienceDefinition;	
	
	@FindBys(@FindBy(xpath="//div[@class='iu-block']//div[@class='iu-block__block-content']//ul[@class='iu-audience-metrics-percentage']/li"))
	public List<WebElement> listSuccessMetricsLeftSide;
	
	@FindBy(xpath="(//a[@class='iu-query__add'])[last()]")
	public WebElement addNewGroupLink;	

	@FindBys(@FindBy(xpath="//*[contains(@ng-show,'projected')]/*[@class='iu-percent-bar__value']/span"))
	public List<WebElement> individualSMProjectedPercentageList;	
	
	@FindBys(@FindBy(xpath="//*[contains(@ng-show,'actual')]/*[@class='iu-percent-bar__value']/span"))
	public List<WebElement> individualSMactualPercentageList;
	
	@FindBy(xpath="//*[contains(@ng-style,'optimal.average')]")
	public WebElement optimalAudience;	
	
	@FindBys(@FindBy(xpath="//insights//*[@class='iu-tabs__tab']/a"))
	public List<WebElement> actualProjectedTabs;
	
	@FindBy(xpath="//*[@ng-show='optimal.metrics.length']")
	public WebElement optimalAudienceFullLength;
	
	@FindBy(xpath="//insights//*[@class='iu-tabs__tab']/a[text()='Actual']")
	public WebElement ActualTab;
	
	@FindBy(xpath="//*[@class='iu-population__selected ng-binding']//span")
	public WebElement selectedPopulationValue;
	
	@FindBy(xpath="//*[@class='iu-population__total ng-binding']")
	public WebElement totalPopulationValue;
	
	@FindBy(xpath="//*[@class='iu-population__percent']//span")
	public WebElement percentagePopulationValue;
	
	@FindBy(xpath="//*[@class='drill-list__title ng-binding']//*[@class='ion ion-search iu-icon-box__icon']")
	public WebElement searchButton;
	
	@FindBy(xpath="//*[@class='drill-list__search']//input[@ng-model='iuSearchCtrl.searchText']")
	public WebElement searchInput;
	
	//@FindBys(@FindBy(xpath="//*[@class='drill-list__item item ng-scope']//*[@class='iu-widget-drill__attr__aname ellipsis ng-binding']"))
	@FindBys(@FindBy(xpath="//li[@class='drill-list__layer layer ng-scope active-layer']//*[@class='drill-list__item item ng-scope']//*[@class='iu-widget-drill__attr__aname ellipsis ng-binding']"))
	public List<WebElement> successmetricsResultLst;	
	
	@FindBy(xpath="//*[@class='iu-percent-bar iu-percent-bar_type_caret']/*[contains(@ng-style,'population.population.percentage')]")
	public WebElement populationBarGraph;
	
	@FindBy(xpath="//*[@ng-if='population.population']//*[@class='iu-percent-bar iu-percent-bar_type_caret']")
	public WebElement populationComplteBarGraph;
	
	@FindBys(@FindBy(xpath="//*[@class='iu-pill iu-pill__query pop ng-scope iu-pill__query-selected']//*[@dnd-list='query.query.qitems']/*[@class='iu-pill iu-pill__item pop ng-scope']"))
	public List<WebElement> queryLstInSelectedGroup;

	//Prev Xpath= (//div[@class='iu-widget-drill__attr__aname ellipsis'])[1]
	@FindBy(xpath="//div[@class='iu-widget-drill iu-widget-drill__attr--btn ng-scope']/div[1]")
	public WebElement noSuccessMetricSelectedMessage; //
	
	//Prev Xpath = (//div[@class='iu-widget-drill__attr__aname ellipsis'])[2]
	@FindBy(xpath="//div[@class='iu-widget-drill iu-widget-drill__attr--btn ng-scope']/div[1]")
	public WebElement noAttributeSelectedMessage;
	
	@FindBy(xpath="//optimal[@class='ng-isolate-scope']//div[@class='iu-block']//h2")
	public WebElement optimalAudienceTextTitle;
	
	@FindBy(xpath="//optimal[@class='ng-isolate-scope']//*[@ng-show='!optimal.metrics.length']")
	public WebElement optimalAudienceDefaultMessage;
	
	@FindBy(xpath="//metrics//h2[@class='iu-block__block-title iu-help-available']")
	public WebElement successMetricsTextTitle;
	
	@FindBy(xpath="//metrics//p[contains(@ng-show,'metrics.metrics.length')]")
	public WebElement successMetricsDefaultMessage;
	
	@FindBy(xpath="//population//span[@class='iu-population__percent']")
	public WebElement populationNumberOfPercentWithPercentSymbol;
	
	@FindBy(xpath="//population//span[@class='iu-population__selected ng-binding']")
	public WebElement populationValueWithMeasurementUnit;
	
	@FindBy(xpath="//population//div[@class='iu-percent-bar__tick-marks']//span[1]")
	public WebElement optimalAudienceStartingRangeText;
	
	@FindBy(xpath="//population//div[@class='iu-percent-bar__tick-marks']//span[2]")
	public WebElement optimalAudienceEndingRangeText;

	@FindBy(xpath="//population//h2[@class='iu-block__block-title']")
	public WebElement populationTextTitle;
	
	@FindBy(xpath="//insights//*[@class='iu-tabs__tab']/a[text()='Projected']")
	public WebElement projectedTab;
	
	@FindBy(xpath="//ul[@class='drill-list__items items']")
	public WebElement rhsPanel;
	
	@FindBys(@FindBy(xpath="//*[@class='drill-list__items items']/li//*[@class='iu-widget-drill iu-widget-drill__group ng-scope']/span[@class='ng-binding']"))
	public List<WebElement> AudidenceDefSearchResultLst;
	
	@FindBys(@FindBy(xpath="//*[@class='drill-list__item item ng-scope']//*[@class='ng-binding']"))
	public List<WebElement>  allSuccessMetricsCategories;
	
	@FindBys(@FindBy(xpath="//ul[@class='drill-list__items items']//div[@class='toggle selected']"))
	public List<WebElement> selectedSuccessMetricsFromRight;
	
	@FindBys(@FindBy(xpath="//*[not(contains(@class,'hide'))]/iu-busy[not(contains(@class,'ng-hide'))]/span[@class='ion ion-load-d iu-spinner']"))
	public List<WebElement> loadingSymbol;	

	//Prev Xpath=//*[@class='iu-widget__dropdown-input-wrapper']/*[@class='expando-toggle']	
	@FindBy(xpath="//*[@class='drill-list__layer layer ng-scope active-layer']//*[@class='iu-widget__dropdown-input-wrapper']/*[@class='expando-toggle']")
	public WebElement dropDownElement;
	
	@FindBy(xpath="//*[@class='iu-widget__dropdown-elements-wrapper expando']")
	public WebElement selectElement;
	
	@FindBy(xpath="//li[@class='drill-list__layer layer ng-scope active-layer']//div[@class='drill-list__title ng-binding']//span[@class='ion ion-search iu-icon-box__icon']")
	public WebElement searchButtonAtAnyLevel;
	
	@FindBy(xpath="//*[@class='drill-list__search']//span[@class='ion ion-search iu-icon-box__icon']")
	public WebElement searchMegnifyIconWithSearchTextBar;
	
	@FindBy(xpath="//li[@class='drill-list__layer layer ng-scope active-layer']//div[@class='drill-list__title ng-binding']//span[@class='ion ion-search iu-icon-box__icon']/parent::span/parent::div")
	public WebElement getSelectedCategoryNameWithSearchIcon;
	
	@FindBy(xpath="//*[@class='iu-widget-drill iu-widget-drill__attr--btn ng-scope']/*[contains(@class,'toggle pointer')]")
	public WebElement togglePointer;
	
	@FindBy(xpath="//*[@class='iu-pill iu-pill__query pop ng-scope iu-pill__query-selected']")
	public WebElement defaultGroup;

	@FindBy(xpath="//div[@class='iu-widget-drill iu-widget-drill__attr--btn ng-scope']//div[@class='iu-widget-drill__attr__aname ellipsis ng-binding']")
	public WebElement getSelectedSuccessMetricsCount_rightBottom;
	
	@FindBy(xpath="//a[@class='iu-tabs__tab-link iu-tabs__tab-link_state_active' and text()='Projected']")
	public WebElement successMetrics_projectedTab_Active; 	
	
	@FindBy(xpath="//a[@class='iu-tabs__tab-link iu-tabs__tab-link_state_active' and text()='Actual']")
	public WebElement successMetrics_ActualTab_Active;

	@FindBy(xpath="//div[@class='iu-widget-drill iu-widget-drill__attr--btn ng-scope']//div[@class='toggle pointer selected']")
	public WebElement successMetricsArrowNavigateToAudienceDef; 
	
	@FindBy(xpath="//div[@ng-show='optimal.metrics.length']//div[@class='iu-percent-bar__bar']")
	public WebElement getOptimalAudienceAttribute;
	
	@FindBys(@FindBy(xpath="//ul[@class='drill-list__items items']//div[@class='toggle']"))
	public List<WebElement> listAttributesNotSelectedFromRight;	

	@FindBy(xpath="//*[@class='iu-widget__bell-curve']/*[@class='iu-widget__bell-curve--mask']/*[@ng-model='IuWidgetController.model.config.model']")
	public WebElement demiDecileGraph;	

	@FindBys(@FindBy(xpath="*//div[@class='iu-widget-drill iu-widget-drill__attr ng-scope']//div[@class='toggle']"))
	public List<WebElement> listPlusInRHS;
	
	@FindBys(@FindBy(xpath="*//div[@class='iu-widget-drill iu-widget-drill__attr ng-scope']//div[@class='toggle selected']"))
	public List<WebElement> listCrossInRHS;
	
	@FindBys(@FindBy(xpath="//ul[@class='iu-block-list']/li"))
	public List<WebElement> listofSuccessMetricsInCenterArea;		

	@FindBy(xpath="//*[@class='drill-list__layer layer ng-scope active-layer']//*[@class='iu-widget__bell-curve--mask']//*[@class='ng-pristine ng-untouched ng-valid ng-not-empty ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all']/a[1]")
	public WebElement sliderLeftValue;
	
	@FindBy(xpath="//*[@class='drill-list__layer layer ng-scope active-layer']//*[@class='iu-widget__bell-curve--mask']//*[@class='ng-pristine ng-untouched ng-valid ng-not-empty ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all']/a[2]")
	public WebElement sliderRightValue;
	
	@FindBy(xpath="//*[@class='drill-list__layer layer ng-scope active-layer']/*[@class='drill-list__title ng-binding']")
	public WebElement activeLayerHeading;
	
	@FindBy(xpath="//*[@class='drill-list__layer layer ng-scope active-layer']//*[@class='iu-widget__dropdown-elements-wrapper expando']")
	public WebElement dropDownExpandedArea;

	@FindBy(xpath="//summary[@class='main-panel flex-3 overflow-y-scroll ng-isolate-scope']//ul[@class='iu-tabs']//a[@class='iu-tabs__tab-link iu-tabs__tab-link_state_active']")
	public WebElement SelectedActiveTab;
	
	@FindBy(xpath="//*[@class='iu-pill iu-pill__query pop ng-scope iu-pill__query-selected']//*[@class='iu-query__name ng-binding']")
	public WebElement defaultGrouptext;

	@FindBys(@FindBy(xpath="//ul[@class='drill-list__items items']//div[@class='toggle selected']//following-sibling::div[@class='iu-widget-drill__attr__aname ellipsis ng-binding']"))
	public List<WebElement> listNamesOfSelectedSuccessMetricsInRHS;
	
	@FindBy(xpath = "//ul[@class='drill-list__items items']//div[@class='iu-widget__slider']/div OR //ul[@class='drill-list__items items']//input OR //ul[@class='drill-list__items items']//div[@class='widget_base iu-widget ng-scope']//button")
	public WebElement sliderDropDownAndButton;
	
	@FindBy(xpath="//*[@id='toast-container']/div")
	public WebElement errorMessage;
	
	@FindBys(@FindBy(xpath="//*[@class='iu-widget-drill iu-widget-drill__group ng-scope']/span[@class='ng-binding']"))
	public List<WebElement> successMetrics_MainCategories;
	
	@FindBy(xpath="//*[@class='iu-pill__badge iu-pill__item iu-pill__badge-and' and @title='toggle and/or']")
	public WebElement toggleAndOrButton;
	
	@FindBys(@FindBy(xpath="//*[@class='iu-pill iu-pill__item pop ng-scope']"))
	public List<WebElement> audienceAddedAttributesList;
	
	@FindBy(xpath="//*[@ng-click='logout()']")
	private WebElement right_Logout;

	@FindBy(xpath="//*[@class='ion ion-navicon-round']")
	private WebElement MenuIcon;
	
	public String strAcquiringAttributeSpinnerContains = "(//*[@class=''text ng-scope''])[{0}]";
	public String strMetricCategoryContains = "//ul[@class=''drill-list__items items'']/li[{0}]";
	public String strMetricCategoryByNameContains = "//ul[@class=''drill-list__items items'']//span[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]/ancestor::li[@class=''drill-list__item item ng-scope'']";
	public String strSliderWidget = ".//div[@class='iu-widget__slider']/div";
	
	//Prev .//button[@class='btn btn-pri']
	public String strAddAttributeBtn = ".//button[contains(.,'add attribute')]";
	
	public String strstrMetricContains="//ul[@class=''drill-list__items items'']/li[{0}]//div[@class=''toggle'']";
	public String strMetricByNameContains="//ul[@class=''drill-list__items items'']//div[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]/preceding-sibling::div";

	public String strSelectedMetricCrossContains="//ul[@class=''drill-list__items items'']//div[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]/preceding-sibling::div/span[1]";
	
	public String strQueryItemList = ".//div[@class='iu-pill__group']/div[@class='iu-pill iu-pill__item pop ng-scope']";

	public String strCategoryName = ".//span[@class='ng-binding']";

	public String strSuccessMetricCardCloseIconByNameContains="//ul[@class=''iu-block-list'']//p[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]/preceding-sibling::p//span[@class=''ion ion-close-round iu-icon-box__icon'']";

	public String strCardCategoryNameContains="//ul[@class=''iu-block-list'']//p[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]/preceding-sibling::p//span[1]";
	
	public String strSuccessMetricCardContains="//ul[@class=''iu-block-list'']//p[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]";
	
	
	public String leftSideSuccessMetricsName = "//div[@class=''iu-block'']//div[@class=''iu-block__block-content'']//ul[@class=''iu-audience-metrics-percentage'']/li[{0}]/metric/p";
	public String leftSideSuccessMetricsPercentageProjected="//div[@class=''iu-block'']//div[@class=''iu-block__block-content'']//ul[@class=''iu-audience-metrics-percentage'']/li[{0}]/metric/div[contains(@ng-show,''projected'')]/div[@class=''iu-percent-bar__value'']/span";
	public String leftSideSuccessMetricsPercentageActual = "//div[@class=''iu-block'']//div[@class=''iu-block__block-content'']//ul[@class=''iu-audience-metrics-percentage'']/li[{0}]/metric/div[contains(@ng-show,''actual'')]/div[@class=''iu-percent-bar__value'']/span";
	
	public String addNewSugbGroupByGroupIDContains="(//div[@class=''merlin merlin-fade-out-in'']/query/div[@dnd-allowed-types=\"[''query'']\"]/div[{0}]//a[@class=''iu-query__add''])[{1}]";
	public String subGroups = "./span[2]/query/div[@dnd-allowed-types=\"['query']\"]/div";
	public String addGroupLink ="./span[2]/query/a";
	
	public String strGroupName = "./span[2]/query/span[2]";
	public String strEditGroupInput = "./span[2]/query/span[3]/input";
	public String strEditGroupOk = "./span[2]/query/span[3]/span[1]";
	public String strEditGroupCancel = "./span[2]/query/span[3]/span[3]";

	//Prev Xpath //*[text()=''{0}'']//ancestor:: *[contains(@class,''iu-pill iu-pill__query pop ng-scope'')]
	public String strSelectGroupBynameContains="//*[text()=''{0}'']/parent::query/parent::span/parent::div";
	public String strSelectMetricsById="//*[@class=''drill-list__item item ng-scope''][{0}]//*[@class=''iu-widget-drill__attr__aname ellipsis ng-binding'']";
	
	//Prev xpath = //button/span[translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz'')=''{0}'']	
	public String strButtonValuesContains="//ul[@class=''drill-list__items items'']//button/span[translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz'')=''{0}'']";
	
	//Prev xpath=//li/span[text()=''{0}'']
	public String strDropDownValuesContains="//li/span[translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz'')=''{0}'']";
	
	public WebElement globalCurrentGroup = null;

	public String deleteGroupLink = "./span[2]/query/span[4]/ng-transclude/i";
	public String selectGroup = "./span[2]/query/span[1]";
	public String strQueryItemsInGroup="./span[2]/query/div[1]/div[starts-with(@class,'iu-pill iu-pill__item pop')]";
	public String strDefaultQueryItemText="./span[2]/query/div[1]/div[starts-with(@class,'iu-pill')]/span[2]";
	public String strQueryDeleteIcon ="./span[2]/span/i[1]";
	public String strQueryAndOr="//*[@class=''iu-pill iu-pill__item pop ng-scope''][{0}]//span[@class=''text ng-binding'']";
	
	//public String strQueryString="//*[@class=''iu-pill iu-pill__item pop ng-scope''][{0}]//span[contains(@class,''iu-pill__text iu-pill__hover ng-binding iu-pill'')]";
	public String strQueryString="//*[@class=''iu-pill iu-pill__item pop ng-scope''][{0}]//span[contains(@class,''iu-pill__text iu-pill__hover rrs ng-binding iu-pill'')]";
	public String successMetricsBGColorLeftPanel = "//div[@class=''iu-block'']//div[@class=''iu-block__block-content'']//ul[@class=''iu-audience-metrics-percentage'']/li[{0}]/metric/div[@class=''iu-percent-bar ng-hide'']/div[@class=''iu-percent-bar__bar'']";
	
	public String getMainGroupLogic = "./span[1]/span";
	//public String getGroupLogic = "./span[2]/query/div[1]/div[1]";
	
	public String strLogicElement ="./span[1]";
	
	public String strlayerHeading="//*[@class=''drill-list__layer layer ng-scope'']/div[contains(translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz''),''{0}'')]";
	public String strQueryEditIcon ="./span[2]/span/i[@class='ion ion-edit']";
	
	public String strButtonValuesEdit="//*[@ng-show=''i.editting'']//span[translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz'')=''{0}'']";
	
	public String strButtonEditTickMark="//*[@class='ion ion-ios-checkmark']";
	public String strButtonEditCancelMark="//*[@class='ion ion-ios-close']";
	
	public String strSelectedElementInEditPanel="//*[@class=''iu-widget__tag-selector__button ng-scope iu-widget__tag-selector__button--md iu-widget__tag-selector__button--active'']/span[translate(.,''ABCDEFGHIJKLMNOPQRSTUVWXYZ'',''abcdefghijklmnopqrstuvwxyz'')=''{0}'']";
	public String strDisabledGroupEditOKLink="//*[@class='pointer pointer-disabled']";
	
	public String strGroupDropArea = "//div[@class='merlin merlin-fade-out-in']/query/div[2]/div[@class='dndPlaceholder']";
	
	public String strPopulationSpinningBar = "//span[text()='Acquiring metric...']"; //"//h2[contains(.,'Population')]/iu-busy";
	
	/** Method is to check visibility of different element.
	 *  Add more case whenever required and add its switch value in below options.
	 * 
	 * <p>  <b>Options:</b> 'nosuccessmetrictext' 'audiencedropdown','sliderddbutton','addattributebtn','editgroupnameinput'
	 * 'editgroupnameok','editgroupnamecancel','errormsg'
	 * </p>
	 * 
	 * @param strElement - String case to verify visibility. Use value from list above.
	 * @param strElementValue TODO
	 * @author Deepen Shah
	 * @since 26 April 2016
	 * 
	 */
	public boolean isVisible(String strElement, String strElementValue){
		boolean bStatus = true;
		
		WebDriverWait wait = new WebDriverWait(driver, 2);
		try{
			switch(strElement.toLowerCase()){
				case "nosuccessmetrictext": 
					  wait.until(ExpectedConditions.visibilityOf(noSuccessMetricText));
					  break;

				case "removeiconofsuccessmetriccard"://Case Added By Rohan Macwan on 28-Apr-2016					
					  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MessageFormat.format(strSuccessMetricCardCloseIconByNameContains, strElementValue)))));					
					  break;
					
				case "arrowlinkforsuccessmetricoraudiencedefinition": 
					  wait.until(ExpectedConditions.visibilityOf(successMetricsOraudienceDefinitionArrowLink));
					  break;
					
				case "removealllink": 
					  wait.until(ExpectedConditions.visibilityOf(removeAllLink));
					  break;
					  
				case "optimalaudiencetexttitle": 
					  wait.until(ExpectedConditions.visibilityOf(optimalAudienceTextTitle));
					  break;

				case "optimalaudiencedefaultmessage": 
					  wait.until(ExpectedConditions.visibilityOf(optimalAudienceDefaultMessage));
					  break;
					  
				case "successmetricstexttitle": 
					  wait.until(ExpectedConditions.visibilityOf(successMetricsTextTitle));
					  break;
					  
				case "successmetricsdefaultmessage": 
					  wait.until(ExpectedConditions.visibilityOf(successMetricsDefaultMessage));
					  break;
					  
				case "populationnumberofpercentwithpercentsymbol": 
					  wait.until(ExpectedConditions.visibilityOf(populationNumberOfPercentWithPercentSymbol));
					  break;
					  
				case "populationvaluewithmeasurementunit": 
					  wait.until(ExpectedConditions.visibilityOf(populationValueWithMeasurementUnit));
					  break;
					  
				case "optimalaudiencestartingrangetext": 
					  wait.until(ExpectedConditions.visibilityOf(optimalAudienceStartingRangeText));
					  break;
					  
				case "optimalaudienceendingrangetext": 
					  wait.until(ExpectedConditions.visibilityOf(optimalAudienceEndingRangeText));
					  break;
				
				case "totalpopulationvalue": 
					  wait.until(ExpectedConditions.visibilityOf(totalPopulationValue));
					  break;
					  
				case "populationtexttitle": 
					  wait.until(ExpectedConditions.visibilityOf(populationTextTitle));
					  break; 
					  
				case "populationbargraph": 
					  wait.until(ExpectedConditions.visibilityOf(populationBarGraph));
					  break;
					  
				case "populationcompltebargraph": 
					  wait.until(ExpectedConditions.visibilityOf(populationComplteBarGraph));
					  break;
					  
				case "optimalaudience": 
					  wait.until(ExpectedConditions.visibilityOf(optimalAudience));
					  break;
					  
				case "optimalaudiencefulllength": 
					  wait.until(ExpectedConditions.visibilityOf(optimalAudienceFullLength));
					  break;
					
				case "listSuccessmetricsleftside": 
					
					  for (int i=0; i<listSuccessMetricsLeftSide.size();i++)
					  {
						wait.until(ExpectedConditions.visibilityOf(listSuccessMetricsLeftSide.get(i)));
					  }
						  
					break;
					
				case "actualtab": 
					  wait.until(ExpectedConditions.visibilityOf(ActualTab));
					  break;
					  
				case "projectedtab": 
					  wait.until(ExpectedConditions.visibilityOf(projectedTab));
					  break;
					  
				case "allmetricslabel": // This is for Search bar and All Metrics and All Attributes Title at RHS for Success metrics and Audience Definition 
					  wait.until(ExpectedConditions.visibilityOf(allMetricsLabel));
					  break;
					  
				case "rhspanel": // This is for Search bar and All Metrics and All Attributes Title at RHS for Success metrics and Audience Definition 
					  wait.until(ExpectedConditions.visibilityOf(rhsPanel));
					  break;
					  
				case "searchbutton"://Clickable button at any level
					  wait.until(ExpectedConditions.visibilityOf(searchButtonAtAnyLevel));
					  break;
					
				case "searchmegnifyicon": //search icon at any level
					  wait.until(ExpectedConditions.visibilityOf(searchMegnifyIconWithSearchTextBar));
					  break;
					
				case "searchtextbar":
					  wait.until(ExpectedConditions.visibilityOf(searchInput));
					  break;
					
				case "defaultgroup":
					wait.until(ExpectedConditions.visibilityOf(defaultGroup));
					break;
					
				case "addgrouplink":
					wait.until(ExpectedConditions.visibilityOf(addNewGroupLink));
					break;
				
				case "projectedactivetab":
					wait.until(ExpectedConditions.visibilityOf(successMetrics_projectedTab_Active));
					break;
					
				case "successmetreicsarrowtoaudiencedef":
					wait.until(ExpectedConditions.visibilityOf(successMetricsArrowNavigateToAudienceDef));
				    break;

				case "demidecilegraph":
					wait.until(ExpectedConditions.visibilityOf(demiDecileGraph));
					break;
					
				case "audiencedropdown": 
					wait.until(ExpectedConditions.visibilityOf(dropDownElement));
					break;
					
				case "rightpaneldropdownexpendedcontent":
					wait.until(ExpectedConditions.visibilityOf(dropDownExpandedArea));
					break;
					
				case "noattributeselectedmsg":
					wait.until(ExpectedConditions.visibilityOf(noAttributeSelectedMessage));
					break;
				
				case "sliderddbutton":
					wait.until(ExpectedConditions.visibilityOf(sliderDropDownAndButton));
					break;
					
				case "addattributebtn":
					String strTemp = MessageFormat.format(strMetricCategoryByNameContains, 1) + "/"+strAddAttributeBtn ;
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(strTemp))));
					break;


				case "successmetrictab":
					wait.until(ExpectedConditions.visibilityOf(successMetricTab));
					break;
					
				case "audiencedefinitiontab":
					wait.until(ExpectedConditions.visibilityOf(audienceDefinitionTab));
					break;

				case "selectedactivetab":
					wait.until(ExpectedConditions.visibilityOf(SelectedActiveTab));
					break;

				case "editgroupnameinput":
					wait.until(ExpectedConditions.visibilityOf(globalCurrentGroup.findElement(By.xpath(strEditGroupInput))));
					break;
					
				case "editgroupnameok":
					wait.until(ExpectedConditions.visibilityOf(globalCurrentGroup.findElement(By.xpath(strEditGroupOk))));
					break;
					
				case "editgroupnamecancel":
					wait.until(ExpectedConditions.visibilityOf(globalCurrentGroup.findElement(By.xpath(strEditGroupCancel))));
					break;
					
				case "errormsg":
					wait.until(ExpectedConditions.visibilityOf(errorMessage));

					
				case "editgroupdisbaledbutton":
					WebElement element=globalCurrentGroup.findElement(By.xpath(strDisabledGroupEditOKLink));
					wait.until(ExpectedConditions.visibilityOf(element));

					break;
					
				case "successmetricsactualtabactive":
					wait.until(ExpectedConditions.visibilityOf(successMetrics_ActualTab_Active));
					break;
				case "populationpercentagevalue":
					wait.until(ExpectedConditions.visibilityOf(percentagePopulationValue));
					break;
					
			}
		}catch(Exception e){
			bStatus = false;
		}
		
		return bStatus;
	}
	
	/** Overloaded above method for one argument.
	 * <p>  <b>Options:</b> 'nosuccessmetrictext' 'audiencedropdown','sliderddbutton','addattributebtn','editgroupnameinput'
	 * 'editgroupnameok','editgroupnamecancel','errormsg'
	 * </p> 
	 * 
	 * @param strElement
	 * @return boolean
	 * @author Deepen Shah
	 * @since 11 May 2016
	 */
	public boolean isVisible(String strElement){
		return isVisible(strElement,"");
	}
	
			
	/** Method will count the number metric cards on the page
	 * 
	 * @return int Total number of cards
	 * @author Deepen Shah
	 * @since 26 April 2016
	 */
	public int getTotalMetricCards(){
		return successMetricCards.size();
	}
	
	
	
	/** To perform various kind of actions on the success metric page
	 * 
	 * <p><b>Options:</b> 'gotoaudiencedefinition','gotosuccessmetric','addattribute','actualpopulationtab','searchbutton', 'successmetrics>arrow'
	 * 'projectedtab','activelayerheading', 'righpaneldropdownclick','toggleandor'</p>
	 * @param strAction
	 * @author Deepen Shah
	 * @since 26 April 2016
	 */
	public void performAction(String strAction){
		switch(strAction.toLowerCase()){
			case "gotoaudiencedefinition": 
				  audienceDefinitionTab.click();
				  break;
				  
			case "gotosuccessmetric": 
				  successMetricTab.click();
				  break;
										
			case "addattribute":
				  driver.findElement(By.xpath(MessageFormat.format(strMetricCategoryContains,1))).findElement(By.xpath(strAddAttributeBtn)).click();
				  break;
				  
			case "actualpopulationtab":
				  ActualTab.click();
			      break;
			      
			case "searchbutton":
				  searchButtonAtAnyLevel.click();
			      break;
			      
			case "successmetrics>arrow":
				  togglePointer.click();
				  break;
				  
			case "projectedtab":
				  projectedTab.click();
			      break;
			      
			case "activelayerheading":
				  activeLayerHeading.click();
			      break;
			
			case "righpaneldropdownclick":
				  dropDownElement.click();
			      break;
			 
			case "toggleandor":
			    toggleAndOrButton.click();
			      break;
			      
			case "logout":
				MenuIcon.click();
				right_Logout.click();
			break;	
		}
	}
	
	/** This method will return total number of groups are available.
	 * 	It will just count groups at the first level not the subgroups.
	 * 
	 * @return int Total group at the first level
	 * @author Deepen Shah
	 * @since 26 April 2016
	 */
	public int getGroupCount(){
		return groupList.size();		
	}
	
	/** This method will return name of each group.
	 * 	It will just return group name at the first level not the subgroups.
	 * 
	 * @return Array of group names
	 * @author Abhishek Deshpande
	 * @since 04 May 2016
	 */
	public String[] getCategoryNames(){	
		int j = getGroupCount();
		String getGroupNames[] = new String[j];
		for(int i=0;i<j;i++){
			getGroupNames[i]=groupList.get(i).getText();			
		}
		return getGroupNames;
		
	}
	
	/** This method is to select Metric by clicking on '+' on success metric page
	 * 
	 * @param strFullPath - String , separated value with full hierarchy of metric.
	 * @throws Exception throws exception if further element is not loaded
	 * @author Deepen Shah
	 * @since 27 April 2016
	 * @LastModifiedBy Deepen Shah - To support execution on any client without property change
	 */
	public void selectMetricByName(String strFullPath) throws Exception{		
		
		String[] strGroupValues= strFullPath.split(":");
		String strCategoryName="";
		String strAttribute="";
		String strTemp="";
		
		for (int i=0;i<strGroupValues.length;i++){
			String[] strCategoryValues =strGroupValues[i].split("-");
			
			//Going back to main category
			allMetricsLabel.click();
			BaseClass.rm.webElementSync("listsize", "1",blackLabelList);
			
			System.out.println(i+": "+strGroupValues[i]);	
			
			strTemp= BaseClass.strAudienceMappedValues.get(strCategoryValues[0].toLowerCase());
			strCategoryName = strTemp==null?strCategoryValues[0].toLowerCase():strTemp;
			driver.findElement(By.xpath(MessageFormat.format(strMetricCategoryByNameContains, strCategoryName))).click();
			BaseClass.rm.webElementSync("visibiltiybyxpath", MessageFormat.format(strMetricCategoryContains, i+2));
			//Thread.sleep(5000);
			if(strCategoryValues.length >1 ){
				String[] strSubCategoryValues=strCategoryValues[1].split(",");
				
				for (int j=0;j<strSubCategoryValues.length;j++){
					strTemp= BaseClass.strAudienceMappedValues.get(strSubCategoryValues[j].toLowerCase());
					strAttribute = strTemp==null?strSubCategoryValues[j].toLowerCase():strTemp;
					driver.findElement(By.xpath(MessageFormat.format(strMetricByNameContains, strAttribute))).click();					
					Thread.sleep(3000);
				}
			}
			
		}
	}
	
	/** This method is to add attribute in the group
	 *  Provide full path to reach to attribute
	 *  
	 * @param strFullPathToAttribute - String , separated path to reach to attribute
	 * @throws Exception throws exception if further element is not loaded
	 * @author Deepen Shah
	 * @since 27 April 2016
	 */
	public void selectAttributeOnAudienceDefinition(String strFullPathToAttribute) throws Exception{
		String[] strCategoryValues = strFullPathToAttribute.split("\\|");
		String strCategory="";		
		String strTemp="";
		
		//Going back to main category
		if(strCategoryValues.length > 1){
			allMetricsLabel.click();
			BaseClass.rm.webElementSync("listsize", "1",blackLabelList);
		}
		
		
		for(int i=0;i<strCategoryValues.length-1;i++){
			strTemp = BaseClass.strAudienceMappedValues.get(strCategoryValues[i].toLowerCase());
			strCategory = strTemp==null?strCategoryValues[i].toLowerCase():strTemp;
			driver.findElement(By.xpath(MessageFormat.format(strMetricCategoryByNameContains, strCategory))).click();
			BaseClass.rm.webElementSync("visibilitynowaitbyxpath", MessageFormat.format(strAcquiringAttributeSpinnerContains, i+2));
			Thread.sleep(1000);

			if(i != (strCategoryValues.length-2))
				BaseClass.rm.webElementSync("visibiltiybyxpath", MessageFormat.format(strMetricCategoryContains, 1));
		}
		
		String[] strLastAttributes = strCategoryValues[strCategoryValues.length-1].split("_");
		if(strLastAttributes.length>1){
			selectValueForDifferentComponent(strLastAttributes[0], strLastAttributes[1]);
		}
		
	}
	
	
	/** This method is to select various type of attribute
	 * 	Add more case for different type
	 * 
	 * @param strComponentType - String type of component. For e.g 'slider', 'button' or 'dropdown'
	 * @param strValues - String : separated values 
	 * @author Deepen Shah
	 * @throws InterruptedException 
	 * @since 27 April 2016
	 */
	public void selectValueForDifferentComponent(String strComponentType,String strValues) throws Exception{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String[] strElementValue = strValues.split(":");
				
		switch(strComponentType.toLowerCase()){
		case "slider":
					WebElement lastElement = driver.findElement(By.xpath(MessageFormat.format(strMetricCategoryContains, 1)));
					
					System.out.println("Found value " + strElementValue[0] + " and " + strElementValue[1]);
			
					Actions action = new Actions(driver);
					WebElement leftBar = lastElement.findElement(By.xpath(strSliderWidget+"/a[1]"));
					WebElement rightBar = lastElement.findElement(By.xpath(strSliderWidget+"/a[2]"));
					if(!strElementValue[0].equalsIgnoreCase(""))
						action.dragAndDropBy(leftBar,Integer.parseInt(strElementValue[0]),0).build().perform();
					
					if(!strElementValue[1].equalsIgnoreCase(""))
						action.dragAndDropBy(rightBar,-Integer.parseInt(strElementValue[1]),0).build().perform();
					
					break;
				/* Added cases for drop down and button
				 * Updated by:Amrutha Nair	
				 */
		case "button":
					
			for(int i=0;i<strElementValue.length;i++){
				System.out.println(strElementValue[i]);	
				driver.findElement(By.xpath(MessageFormat.format(strButtonValuesContains, strElementValue[i].toLowerCase()))).click();
				Thread.sleep(2000);
			}
			break;
			
		case "dropdown":
			
			System.out.println(BaseClass.rm.webElementSync(dropDownElement, "visibility"));
			dropDownElement.click();
			Thread.sleep(2000);
			
			for(int j=0;j<strElementValue.length;j++){
				System.out.println(strElementValue[j]);
				driver.findElement(By.xpath(MessageFormat.format(strDropDownValuesContains, strElementValue[j].toLowerCase()))).click();
				Thread.sleep(2000);
			}
			
			break;
		}
		
	}
	
	/** This method will return all the query items added on the page.
	 * 	Count is irrespective of groups
	 * 
	 * @return int - Total number of queries added on the page
	 * @author Deepen Shah
	 * @since 27 April 2016
	 */
	public int getTotalQueryItems(){
		return queriesList.size();
	}

	/** To remove all Success Metrics in Success Metrics Tab or to remove all Queries created in Audience Definition Tab
	 * 
	 * @author Rohan Macwan
	 * @since 28 April 2016
	 */
	public void removeAllSuccessMetricsOrAudienceDefinitionQueries(){
		removeAllLink.click();
	}
	
	/** This method will click Arrow Link for either Succees Metrics or Audience Definition Tab 
	 * 
	 * @author Rohan Macwan
	 * @since 28 April 2016
	 */
	public void arrowLinkClickForSuccessMetricsOrAudienceDefinition(){
		successMetricsOraudienceDefinitionArrowLink.click();
	}

	/** This method will click Arrow Link for either Succees Metrics or Audience Definition Tab 
	 * 
	 * @return int - Returns total number of Success Metrics or Queries
	 * @author Rohan Macwan
	 * @since 28 April 2016
	 */
	public int getCountForSuccessMetricsOrAudienceDefinition(){
		int count=0;
		String[] forCount=countForSuccessMetricsOraudienceDefinition.getText().trim().split(" ");
		count=  Integer.parseInt(forCount[0]);
		return count;
	}

	/** This method is for removing Success Metric Card by its Name
	 * 
	 * @param strCardName - String , This is the Success Metric Card Name which will be removed.
	 * @throws Exception throws exception if further element is not loaded
	 * @author Rohan Macwan
	 * @since 28 April 2016
	 */
	public void removeSuccessMetricCardByName(String strCardName) throws Exception{
		System.out.println(MessageFormat.format(strSuccessMetricCardCloseIconByNameContains, strCardName));
		
		driver.findElement(By.xpath(MessageFormat.format(strCardCategoryNameContains, strCardName.toLowerCase()))).click();
		driver.findElement(By.xpath(MessageFormat.format(strSuccessMetricCardCloseIconByNameContains, strCardName.toLowerCase()))).click();
	}
	
	/** This method will return success metrics name and percentage values from left side.
	 * @param 'projected','actual'
	 * @return ArrayList
	 * @author Shailesh Kava
	 * @since 04 May 2016
	 */
	public ArrayList<String> successMetricsLeftSideGetAllAttributes(String strTabName){
		ArrayList<String> successMetricsAttributes = new ArrayList<>();
		
		if(listSuccessMetricsLeftSide.size() >= 1){
			
			for(int i=1; i<=listSuccessMetricsLeftSide.size(); i++){
				String successMetricsName = driver.findElement(By.xpath(MessageFormat.format(leftSideSuccessMetricsName, i))).getText().trim();
				System.out.println(successMetricsName);
				String percentageVal = "";
				
				if(strTabName.toLowerCase().equals("projected")){
					percentageVal = driver.findElement(By.xpath(MessageFormat.format(leftSideSuccessMetricsPercentageProjected, i))).getText().replace("%", "").trim();
					System.out.println(percentageVal);
				}else{
					percentageVal = driver.findElement(By.xpath(MessageFormat.format(leftSideSuccessMetricsPercentageActual, i))).getText().replace("%", "").trim();
					System.out.println(percentageVal);
				}
				
				String getBGColor = driver.findElement(By.xpath(MessageFormat.format(successMetricsBGColorLeftPanel, i))).getCssValue("background-color").trim();
				System.out.println(getBGColor);
				
				if(getBGColor.equals("rgba(118, 116, 115, 1)"))
					getBGColor = "grey";
				else if(getBGColor.equals("rgba(255, 15, 83, 1)"))
					getBGColor = "red";
				else
					getBGColor = "unknown";	
				
				String allAttributes = successMetricsName+":"+percentageVal+":"+getBGColor;
				System.out.println("Attributes: "+allAttributes);
				
				successMetricsAttributes.add(allAttributes);
			}
			
		}else{
			System.out.println("No success metrics added");
		}
		
		return successMetricsAttributes;
	}
	
	/** This method will add new group
	 * 
	 * @return void
	 * @author Shailesh Kava
	 * @since 28 April 2016
	 */
	public void addNewGroup(){
		addNewGroupLink.click();
		
	}
	
	/**
	 * @param groupID
	 * @param subGroupID
	 * @author Shailesh Kava
	 * @since 28 April 2016
	 */
	public void addSubGroup(String subGroupID){
		findAndSaveGroup(subGroupID);
		globalCurrentGroup.findElement(By.xpath(addGroupLink)).click();
		
	}

	/** This method will return cards name only
	 * @return List of String. Name of all the cards available on page.
	 * @author Deepen Shah
	 * @since 28 April 2016
	 */
	public List<String> getNameOfAllMetricCards(){
		List<String> strRet = new ArrayList<String>();
		
		for(WebElement element:successMetricCardsName)
			strRet.add(element.getText().trim());
			
		return strRet;
	}
	
	
	/** This method will return cards name with its parent category separated by '-'
	 * 
	 * @return List of Category-Name values for each cars
	 * @author Deepen Shah
	 * @since 28 April 2016
	 */
	public List<String> getNameOfAllMetricCardsWithCategory(){
		List<String> strRet = new ArrayList<String>();
		
		for(int i=0;i<successMetricCardsName.size();i++){
			String strFullName=successMetricCardsCategoryName.get(i).getText().trim() + "-" + successMetricCardsName.get(i).getText().trim();
			strRet.add(strFullName);
			
		}
			
		return strRet;
	}
	
	/** This method is to de-select Metric by clicking on 'X' on success metric page
	 *  Provide '-' separated values for multiple metrics from same category
	 *  For e.x. Cat A,Metric B-Metric C To delete Metric B & C from category A.
	 * 
	 * @param strFullPath - String , separated value with full hierarchy of metric.
	 * @throws Exception throws exception if further element is not loaded
	 * @author Deepen Shah
	 * @since 28 April 2016
	 */
	public void deSeletMetricByNameFromMetricList(String strFullPath) throws Exception{
		String[] strCategoryValues = strFullPath.split(",");
		
		int i;
		for(i=0;i<strCategoryValues.length-1;i++){
			driver.findElement(By.xpath(MessageFormat.format(strMetricCategoryByNameContains, strCategoryValues[i].toLowerCase()))).click();
			if(i != (strCategoryValues.length-2))
				BaseClass.rm.webElementSync("visibiltiybyxpath", MessageFormat.format(strMetricCategoryContains, i+2));
		}
		
		String strMetricValues[] = strCategoryValues[i].split("-");
		
		for(String strVal:strMetricValues)
			driver.findElement(By.xpath(MessageFormat.format(strMetricByNameContains, strVal.toLowerCase()))).click();
	}
	
	/** To navigate to first level while we are adding metric or attribute
	 * 
	 * @author Deepen Shah
	 * @since 28 April 2016
	 */
	public void goToFirstLevelForMetricOrAttribute(){
		allMetricsLabel.click();
	}
	
	
	/** This method is used internally to find and save group.
	 *  The variable, saved globally after execution of this method, will be used for all other purpose
	 * 
	 * @param groupID
	 * @param subGroupID
	 * @author Shailesh & Deepen
	 * @since 28 April 2016
	 */

	public void findAndSaveGroup(String subGroupID){		
		
		String[] groupLevels = subGroupID.split("\\.");
		
		WebElement currentGroup = groupList.get(Integer.parseInt(groupLevels[0])-1);
		
		if(groupLevels.length > 1){		
			
			for(String strLevel:groupLevels){
				if(strLevel == groupLevels[0]){
					/*
					 * Do Nothing as first value is first level group name
					 * This value is already used in above code. 
					*/
				}else{
					System.out.println("findAndSaveGroup: " +strLevel);
					List<WebElement> elementTemp = currentGroup.findElements(By.xpath(subGroups));
					System.out.println("findAndSaveGroup: subgroup found at level " + elementTemp.size());
					currentGroup = elementTemp.get(Integer.parseInt(strLevel)-1);
					
					System.out.println("loop");
				}
			}			
		}
		globalCurrentGroup = currentGroup;
	}
	
	/** Click to rename the group
	 *  This method can click on any group name at any level.
	 *  For example if user wants to click on First Group -> Second Group -> Third Group name
	 *  Then give argument as iGroupID = 1 and strSubGroupID = "2.3"
	 * 
	 * 	Make sure before calling group, you are passing correct depth and value otherwise it will throw
	 *  ArrayIndexOutOfBoundException 
	 * 
	 * @param iGroupID - int group index at first level
	 * @param strSubGroupID - String depth of sub group.
	 * @author Deepen Shah
	 * @since 28 April 2016
	 */
	public void clickToEditGroupName(String strSubGroupID ){
		findAndSaveGroup(strSubGroupID);
		globalCurrentGroup.findElement(By.xpath(strGroupName)).click();		
		
	}
	
	/**This method verifies optimal audience
	 * 
	 * @since 28-April-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 * @throws InterruptedException
	 *   
	 * <p> <b>modified Date / by<b> : 6 May 2016 / Shailesh Kava</p>
	 * <p> <b>modified comment<b> : Change variables type int to double and used saperate method to get optimal audience </p>
	 */
	public boolean verifyOptimalAudience() throws InterruptedException{
		boolean status=false;

		double totalProjected=0;
		double totalActual=0;
		System.out.println("verifyOptimalAudience");
		double avgProjected=0;
		double avgActual=0;

		String percentageValue=null;
		
		Thread.sleep(3000);
		//Checking projected percentage optimal audience
		for (WebElement percentageProjected:individualSMProjectedPercentageList){
			percentageValue =percentageProjected.getText();
			
			//System.out.println("percentageValue  :"+percentageValue);
			percentageValue=percentageValue.split("%")[0];
			//System.out.println("percentageValue int  :"+percentageValue);
			totalProjected = totalProjected+Double.parseDouble(percentageValue);
			//totalProjected= totalProjected+Integer.parseInt(percentageValue);
			System.out.println("total :"+totalProjected);
			
		}
	

		avgProjected = Math.round(totalProjected/individualSMProjectedPercentageList.size());
		
		//avgProjected=(int)(Math.round(totalProjected/individualSMProjectedPercentageList.size()));
		System.out.println("avgProjectedVal ["+avgProjected+"]");
		
		double barWidthIntProjected = getOptimalAudiencePercentage();
		Thread.sleep(5000);
		System.out.println("Clicking on Actial Tab");
		ActualTab.click();
		System.out.println("Actual tab click!!!!!");
		Thread.sleep(5000);
		for (WebElement percentageActual:individualSMactualPercentageList){
			percentageValue =percentageActual.getText();
			System.out.println("percentageValue  :"+percentageValue);
			percentageValue=percentageValue.split("%")[0];
			System.out.println("percentageValue int  :"+percentageValue);
			totalActual = totalActual+Double.parseDouble(percentageValue);
			System.out.println("total:"+totalActual);
		}
	
		avgActual = Math.round(totalActual/individualSMactualPercentageList.size());
		
		//avgActual=(int)(Math.round(totalActual/individualSMactualPercentageList.size()));
		System.out.println("avgActualVal ["+avgActual+"]");
		
		double barWidthIntActual = getOptimalAudiencePercentage();
		System.out.println("barWidthInt:"+barWidthIntActual);
		
		if(avgProjected == barWidthIntProjected)
		{
			if(avgActual == barWidthIntActual){
				status=true;
				CustomReporter.log("Projected Tab Optimual Audience Value is '"+barWidthIntProjected+"', and Actual Tab Optimal Audience Value is '" + barWidthIntActual + "'");
			}
			else{
				CustomReporter.errorLog(" Actual Tab:The  optimal audience selected is coming as '"+barWidthIntActual+"',  but the average of successmetrics values is '"+avgActual+"'");
			}
		}
		else{
			CustomReporter.errorLog("Projected Tab:The  optimal audience selected is coming as '"+barWidthIntProjected+"', but the average of successmetrics values is '"+avgProjected+"'");
		}
		return status;
		
	}
	
	/**This method verifies the visibility of actual tab and projected tab
	 * 
	 * @since 28-April-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 */
	
	public boolean verifyActualAndProjectedTabs(String values){
		boolean status=false;
		int i=0;
		for(WebElement element:actualProjectedTabs)
		{
			
			System.out.println("element.getText():"+element.getText());
			System.out.println("value"+values.split(",")[i]);
			if(element.getText().contains(values.split(",")[i]));
			{
				status=true;
			}
			i=i+1;
		}
		return status;
		
	}
		
	/**This method verifies the population percentage in actual and projected tab
	 * 
	 * @since 28-April-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 */
	
	public boolean verifyPopulationPercentage(){
		boolean status=false;
		
		int intSelectedPopulation = Integer.parseInt(selectedPopulationValue.getText());
		System.out.println("intSelectedPopulation:"+intSelectedPopulation);
		int intPopulationPercentage =Integer.parseInt(percentagePopulationValue.getText());
		System.out.println("intPopulationPercentage:"+intPopulationPercentage);
		String strTotalPopulation =totalPopulationValue.getText().replaceAll("out of ", "");
		strTotalPopulation=strTotalPopulation.split(",")[0];
		System.out.println("strTotalPopulation:"+strTotalPopulation);
		
		int intTotalPopulation=Integer.parseInt(strTotalPopulation);
		System.out.println("intTotalPopulation:"+intTotalPopulation);
		int intCalcPercentage=(int)((intSelectedPopulation/intTotalPopulation)*100);
		System.out.println("intCalcPercentage:"+intCalcPercentage);
		if(intCalcPercentage==intPopulationPercentage){
			status=true;
			System.out.println(status);
			
		}
		
		
		return status;
	}
	
	/**This method verifies the search functionality in success metrics page
	 * 
	 * @since 28-April-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 */
	
	public boolean verifySuccessMetricsSearchfunctionality(String value){
		
		boolean status=true;
		
		
		for (WebElement result:successmetricsResultLst){
			System.out.println("search result:"+result.getText());
			if(!(result.getText().toLowerCase().contains(value.toLowerCase()))){
				status=false;
				break;
			}
			System.out.println(status);
		}
		return status;
		
	}

	/** To fill group name use this method.
	 * 
	 * @param strGroupName - String group name
	 * @author Deepen Shah
	 * @since 28 April 2016
	 */
	public void fillGroupName(String strGroupName){
		WebElement editGroupNameInputField = globalCurrentGroup.findElement(By.xpath(strEditGroupInput));
		editGroupNameInputField.clear();
		editGroupNameInputField.sendKeys(strGroupName);
	}
	
	/** This method will perform Ok/Cancel on edit group area
	 * 
	 * @param bOk - Boolean value. True for ok and false for Cancel
	 * @author Deepen Shah
	 * @since 28 April 2016
	 */
	public void performActionOnEditGroupName(boolean bOk){
		if(bOk){
			globalCurrentGroup.findElement(By.xpath(strEditGroupOk)).click();
		}else{
			globalCurrentGroup.findElement(By.xpath(strEditGroupCancel)).click();
		}
	}

	
	/** This method will return the number of queries in a selected group
	 * 
	 * @return int - Total number of queries added on the page
	 * @author Amrutha Nair
	 * @since 29 April 2016
	 */
	public int getTotalQueriesForSelectedGroup(){
		return queryLstInSelectedGroup.size();
	}
	
	/**This method will select the group by name
	 * 
	 * @since 29-April-2016
	 * @return: void
	 * @author Amrutha Nair
	 */
		
 public void selectGroupByName(String strGroupName){

	driver.findElement(By.xpath(MessageFormat.format(strSelectGroupBynameContains, strGroupName))).findElement(By.xpath(selectGroup)).click();
			
 }
	/**This method verifies the population bar graph  in actual and projected tab
	 * 
	 * @since 29-April-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 */
	
	public boolean verifyPopulationGraph(){
		boolean status=false;
		
		
		int intPopulationPercentage =Integer.parseInt(percentagePopulationValue.getText());
		System.out.println("intPopulationPercentage:"+intPopulationPercentage);
		
		String strPopulationBarGraph=populationBarGraph.getCssValue("width");
		System.out.println("strPopulationBarGraph:"+strPopulationBarGraph);
	
		String strCompltePopBarGraph=populationComplteBarGraph.getCssValue("width");
		System.out.println("populationComplteBarGraph:"+strCompltePopBarGraph);
		
		float barWidth=Float.parseFloat(strPopulationBarGraph.split("px")[0]);
		System.out.println("barWidth:"+barWidth);
		float barFullWidth=Float.parseFloat(strCompltePopBarGraph.split("px")[0]);
		System.out.println("barFullWidth:"+barFullWidth);
		float barPercentage=(barWidth/barFullWidth)*100;
		barPercentage=Math.round(barPercentage);
		System.out.println("barPercentage:"+barPercentage);
		if(intPopulationPercentage==barPercentage){
			
				status=true;
		}
		System.out.println(status);
		
		
		return status;
	}
	

	/**This method input the search value in search text box
	 * 
	 * @since 02-May-2016
	 * @return: void
	 * @author Amrutha Nair
	 */
	
	public void inputSearchValue(String value){
		searchInput.clear();
		searchInput.sendKeys(value);
	}
	
	/**This method selects the success metrics in search result based on id
	 * 
	 * @since 02-May-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 */
	
	public boolean selectSuccessMetricsByRowID(int id){
		boolean status=false;
		if(successmetricsResultLst.size()>id){
			driver.findElement(By.xpath(MessageFormat.format(strSelectMetricsById, id))).click();
			status=true;
		}
		
		return status;
	}

	/**This method verifies the search functionality in audience definition page
	 * 
	 * @since 02-May-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 */
	
	public boolean verifyAudienceDefSearchfunctionality(String value){
		
		boolean status=true;
		
		
		for (WebElement result:AudidenceDefSearchResultLst){
			System.out.println("search result:"+result.getText());
			if(!(result.getText().toLowerCase().contains(value.toLowerCase()))){
				status=false;
				break;
			}
			System.out.println(status);
		}
		return status;
		
	}
	
	/**This method selects all the success metrics in success metrics page
	 * 
	 * @since 02-May-2016
	 * @return: void
	 * @author Amrutha Nair
	 * @throws Exception 
	 */
	
	public void selectAllSuccessMetrics() throws Exception{
		
		for (int i=1;i<=allSuccessMetricsCategories.size();i++){
			BaseClass.rm.webElementSync("visibiltiybyxpath", MessageFormat.format(strMetricCategoryContains, i));
			driver.findElement(By.xpath(MessageFormat.format(strMetricCategoryContains, i))).click();
			
			
				for (int j=1;j<=successmetricsResultLst.size();j++)
				{
					
					BaseClass.rm.webElementSync("visibiltiybyxpath", MessageFormat.format(strstrMetricContains, j));
					driver.findElement(By.xpath(MessageFormat.format(strstrMetricContains, j))).click();
					Thread.sleep(3000);
				
				}
			
			allMetricsLabel.click();
			
		}
	}
	
	/**This method finds the total selected categories
	 * 
	 * @since 02-May-2016
	 * @return: int
	 * @author Amrutha Nair
	 * @throws Exception 
	 */
	
	public int getTotalCategoriesSelectedFromRightSide() throws Exception{
		int intSelectedMetrics=0;
		
		for (int i=1;i<=allSuccessMetricsCategories.size();i++){
			allMetricsLabel.click();
			BaseClass.rm.webElementSync("visibiltiybyxpath", MessageFormat.format(strMetricCategoryContains, i));
			driver.findElement(By.xpath(MessageFormat.format(strMetricCategoryContains, i))).click();
			
			Thread.sleep(2000);
			intSelectedMetrics=intSelectedMetrics+selectedSuccessMetricsFromRight.size();
			
		}
		
		return intSelectedMetrics;				
		
		
	}
	
	/**This method verifies that the loading symbol is not coming in success metrics/audience definition page
	 * 
	 * @since 02-May-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 * @throws Exception 
	 */
	
	public boolean verifyNoLoadingSymbolPresent() throws Exception{
		
		Thread.sleep(3000);
		boolean status=false;
		System.out.println(loadingSymbol.size());
		if(loadingSymbol.size() >0){
			status=true;
		}
		System.out.println(status);
		return status;					
		
		
	}
	


	/** This method checks whether correct no success metrics selected message appears by default at the bottom right of the screen 
	 * 
	 * @return String - Returns the message set when there is no success metric selected
	 * @author Rohan Macwan
	 * @since 02 May 2016
	 */
	public String getNoSuccessMetricsSelectedMessage(){
		String strGetNoSuccessMetricsSelectedMessage="";
		strGetNoSuccessMetricsSelectedMessage=noSuccessMetricSelectedMessage.getText().trim();
		
		return strGetNoSuccessMetricsSelectedMessage;
	}
	
	/** This method checks whether correct no attributes selected message appears by default at the bottom right of the screen 
	 * 
	 * @return String - Returns the message set when there is no attributes selected
	 * @author Rohan Macwan
	 * @since 02 May 2016
	 */
	public String getNoAttributeSelectedMessage(){
		String strGetNoAttributeSelectedMessage="";
		strGetNoAttributeSelectedMessage=noAttributeSelectedMessage.getText().trim();
				
		return strGetNoAttributeSelectedMessage;
	}
	
	/** This method will remove group
	* @param (subGroupID (e.g 1.2.1))
	* @return void
	* @author Shailesh Kava,Deepen Shah
	* @since 28 April 2016
	*/
	public void deleteGroup(String GroupID){
		
		findAndSaveGroup(GroupID);
		globalCurrentGroup.findElement(By.xpath(deleteGroupLink)).click();
		
	}
	/** This method will select group based on provided group id
	* @param (subGroupID (e.g 1.2.1))
	* @return void
	* @author Shailesh Kava
	* @since 28 April 2016
	*/
	public void selectGroup(String GroupID){
		
		findAndSaveGroup(GroupID);
		globalCurrentGroup.findElement(By.xpath(selectGroup)).click();
	}
	
	/** This method is to delete query from the group
	 * 
	 * @param strFullQueryPath - String full path to query. For example,
	 * 
	 * 3rd query in subgroup of first group. Then value is,
	 * 1.1.3
	 * 
	 * @return boolean True/False. False if given value is not found from group.
	 * @author Deepen Shah
	 * @since 03 May 2016
	 */
	public boolean deleteQueryItem(String strFullQueryPath){
		boolean bFound =false;
		
		int iLastDot = strFullQueryPath.lastIndexOf(".");
		
		String strGroupPath = strFullQueryPath.substring(0, iLastDot);
		int iQueryIndex = Integer.parseInt(strFullQueryPath.substring(iLastDot+1));
		
		System.out.println("Group to be used: " + strGroupPath);
		System.out.println("Query to be deleted: " + iQueryIndex);
		System.out.println("strGroupPath:"+strGroupPath);
		findAndSaveGroup(strGroupPath);
		
		List<WebElement> queryItems = globalCurrentGroup.findElements(By.xpath(strQueryItemsInGroup));
		System.out.println("Total queries " + queryItems.size());
		if(queryItems.size()>0 && queryItems.size() >= iQueryIndex){
			bFound = true;
						
			WebElement deleteIcon = queryItems.get(iQueryIndex-1).findElement(By.xpath(strQueryDeleteIcon));
			
			
			
			Actions action = new Actions(driver);
			action.moveToElement(queryItems.get(iQueryIndex-1)).build().perform();
			deleteIcon.click();
		}
		
		return bFound;
		
	}
	
	/** This method will return total queries available in the group.
	 *  It will not consider any queries available in subgroup. For that call this method again 
	 *  providing path to subgroup and so on.
	 *  
	 * @param strFullGroupPath - String path to group. For example "1", "2.1","1.2.3"
	 * @return int Query item count
	 * @author Deepen Shah
	 * @since 03 May 2016
	 */
	public int getQueryItemCountInGroup(String strFullGroupPath){
			
		findAndSaveGroup(strFullGroupPath);
		return globalCurrentGroup.findElements(By.xpath(strQueryItemsInGroup)).size();
		
	}
	

	/** This method will return selected category name
	* @param String
	* @return String
	* @author Shailesh Kava
	* @since 03 May 2016
	*/
	public String getSelectedCategoryName(){
		String selectedCategoryName = null;
		
		selectedCategoryName = getSelectedCategoryNameWithSearchIcon.getText();
		
		return selectedCategoryName.trim();
	}
	
	/** This method will type provided search string in search text box
	* @param searchString
	* @return void
	* @author Shailesh Kava
	* @since 03 May 2016
	*/
	public void inputSearchString(String searchString){
		searchInput.sendKeys(searchString);
	}
	
	/** This method will return searched string
	* @param 
	* @return String
	* @author Shailesh Kava
	* @since 03 May 2016
	*/
	public String getSearchedStringFromTextBox(){
		String searchedString = searchInput.getAttribute("value");
		
		return searchedString.trim();
	}
	
	
	/** This method will return group logic. Method can be use for group or subgroup
	 *  <br/>
	 *  For subgroup, pass appropriate id in the argument. For example,
	 *  3rd subgroup of 2nd main group then argument will be 2.3
	 *  
	 *  <br/>
	 *  If targeted group is first group(subgroup) then it will return blank
	 *  value as no group logic is assigned to that group.
	 * 
	* @param String
	* @return String
	* @author Shailesh Kava
	* @since 03 May 2016
	*/
	public String getGroupLogic(String strGroupId){
		
		String groupLogic = null;
		
		findAndSaveGroup(strGroupId);
		groupLogic = globalCurrentGroup.findElement(By.xpath(getMainGroupLogic)).getText();
		System.out.println("***"+groupLogic+"***");
		
		return groupLogic.trim();
	}
	

	/**This method clicks on a particular category in audience definition right panel
	 * 
	 * @since 03-May-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 * @throws InterruptedException 
	 * 
	 */
	public void selectCategory(String strcategory) throws InterruptedException{
		
		String [] strCategoryLst=strcategory.split("\\|");
		for (int i=0;i<strCategoryLst.length;i++){
			WebElement element = driver.findElement(By.xpath(MessageFormat.format(strMetricCategoryByNameContains, strCategoryLst[i].toLowerCase())));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			element.click();
			Thread.sleep(2000);
		}
	}
	
	/**This method returns population percenatge in the right panel
	 * 
	 * @since 03-May-2016
	 * @return: int
	 * @author Amrutha Nair
	 * @throws InterruptedException 
	 * 
	 */
	public int getPopulationPercentage(){
		
		return Integer.parseInt(percentagePopulationValue.getText());
		
	}
	
	/**This method verifies whether the query structure is proper
	 * 
	 * @since 03-May-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 * @throws InterruptedException 
	 * 
	 */
	public boolean verifyQueryStructure(int id, String strQueryStr){
		boolean status=false;
		String strItems []=strQueryStr.split("\\|");
		int queryLength=strItems.length;
		String query=strItems[queryLength-2]+": [";
		String[] subQueryList=strItems[queryLength-1].split("_");
		String[] subQueryList1=subQueryList[1].split(":");
		for (int i=0;i<subQueryList1.length;i++){
			query=query+subQueryList1[i];
			if(i!=subQueryList1.length-1){
				query=query+", ";
			}
			
		}
		query=query+"]";
		
		String strAndOr=driver.findElement(By.xpath(MessageFormat.format(strQueryAndOr, id))).getText();
		
		String strQuery=driver.findElement(By.xpath(MessageFormat.format(strQueryString, id))).getText();
		
		if(strAndOr.toLowerCase().contains("and") || strAndOr.toLowerCase().contains("or")){
			if(strQuery.toLowerCase().contains(query.toLowerCase())){
				status=true;
			}
			
		}
		
		return status;
		
		
	}
	
	/**This method returns all the sub categories under a specific category in the success metrics right panel
	 * 
	 * @since 04-May-2016
	 * @return: String []
	 * @author Amrutha Nair
	 * @throws  
	 * 
	 */
	public List<String> returnSuccessMetricsSubCategories(){
		List<String> subCategories = new ArrayList<String>();
	
		String tmpCategory=null;
		for (WebElement metric:successmetricsResultLst){
			tmpCategory=metric.getText().toLowerCase();		
			
			subCategories.add(tmpCategory);			
		
		}
		return subCategories;
	}
	
	

	/**This method verifies whether the subcategories in success metrics page for the selected category is expected
	 * 
	 * @since 04-May-2016
	 * @return: boolean
	 * @author Amrutha Nair
	 * @throws  
	 * 
	 */
	public boolean verifySubcategoriesForSelectedSuccessMetrics(List<String> subCategories, String expectedCategories){
		boolean status=true;
		int flag=0;
		String[]  expCategories=expectedCategories.split(",");
		
		if(subCategories.size()==expCategories.length){
			for(int i=0;i<expCategories.length;i++){
				if(!subCategories.contains(expCategories[i])){				
	
					
					status=false;
					break;
				}
					
			}
		}
		else{
			status=false;
		}
					
			
		return status;		
	}

	/**This method returns the group name with selected group ID
	 * 
	 * @since 04-May-2016
	 * @return: String
	 * @author Amrutha Nair
	 * @throws  
	 * 
	 */
	public String getGroupName(String ID){
		
		findAndSaveGroup(ID);
		
		return globalCurrentGroup.findElement(By.xpath(strGroupName)).getText();
	}
	
	
	/**
	 * @throws InterruptedException This method verifies whether the group name is editable and whether OK and cancel button are present on clicking on the group name
	 * 
	 * @since 04-May-2016
	 * @return: String
	 * @author Amrutha Nair
	 * @throws  
	 * 
	 */
	public boolean verifyGroupEditableMode(String ID) throws InterruptedException{
		boolean status=true;
		WebDriverWait wait = new WebDriverWait(driver, 2);
		findAndSaveGroup(ID);
		try{
			WebElement editGroupNameInputField = globalCurrentGroup.findElement(By.xpath(strEditGroupInput));
			editGroupNameInputField.clear();
			editGroupNameInputField.sendKeys("a");
			Thread.sleep(1000);
			if(!editGroupNameInputField.getAttribute("value").contentEquals("a")){
				
				status=false;
			}
			
			
			 wait.until(ExpectedConditions.visibilityOf(globalCurrentGroup.findElement(By.xpath(strEditGroupOk))));
			 
			 wait.until(ExpectedConditions.visibilityOf(globalCurrentGroup.findElement(By.xpath(strEditGroupCancel))));
			  
			
	 	}
		 catch(Exception e){
			 status = false;
		}
		
		return status;
	}
	
	/** This methid will return whole query as displayed on screen.
	 *  For e.g or Age [16 -62]. Value will be separated by \n (new line).
	 *  <br/>
	 *  Input is string path to query item. For example,
	 *  First query item from second group then input is 2.1
	 *  <br/>
	 *  Also it will return blank if no query found at this index.
	 *  And only attribute if trying fetch first query in the group where no
	 *  And/or logic is applied
	 * 
	 * @param strFullQueryPath String full path to query item
	 * @return String Query (Logic+attribute)
	 * @author Deepen Shah
	 * @since 04 May 2016
	 */
	public String getQueryWithLogic(String strFullQueryPath){
		String strRetVal="";
		
		int iLastDot = strFullQueryPath.lastIndexOf(".");
		
		String strGroupPath = strFullQueryPath.substring(0, iLastDot);
		int iQueryIndex = Integer.parseInt(strFullQueryPath.substring(iLastDot+1));
		
		System.out.println("Group to be used: " + strGroupPath);
		System.out.println("Query to be fetched: " + iQueryIndex);
		
		findAndSaveGroup(strGroupPath);
		
		List<WebElement> queryItems = globalCurrentGroup.findElements(By.xpath(strQueryItemsInGroup));
		System.out.println("Total queries " + queryItems.size());
		if(queryItems.size()>0 && queryItems.size() >= iQueryIndex){
			strRetVal = queryItems.get(iQueryIndex-1).getText().trim();
		}
		
		return strRetVal;

	}
	
	/** This method checks whether correct message is displayed in center area of when there is no success metric is added 
	 * 
	 * @return String - Returns the message set in center area when there is no success metric selected 
	 * @author Rohan Macwan
	 * @since 04 May 2016
	 */
	public String getSuccessMetricsMsgOfCenterArea(){
		String strCenterAreaMessage ="";
		strCenterAreaMessage=noSuccessMetricText.getText().trim();
		return strCenterAreaMessage.replaceAll("\\n\\n"," ");
		
	}
	
	/** To get a count of subgroup. 
	 * 	Pass group path if you want to go in deep level.
	 *  For example, to get count of subgroup under 3rd subgroup for 2nd group
	 *  
	 *  Argument: 2.3
	 * 
	 * @param strFullGroupPath String group index
	 * @return int Count of subgroup in that give group
	 * @author Deepen Shah
	 * @since 05 May 2016
	 */
	public int getSubGroupCount(String strFullGroupPath){
		findAndSaveGroup(strFullGroupPath);
		return globalCurrentGroup.findElements(By.xpath(subGroups)).size();
	}
	
	/** Method will return query logic. It will work for added attribute only. 
	 *  <br/>
	 *  Follow getQueryWithLogic method for argument details.
	 * 
	 * @param strFullQueryPath - String
	 * @return String And/Or or ""
	 * @author Deepen Shah
	 * @since 05 May 2016
	 */
	public String getQueryLogic(String strFullQueryPath){
		String strFullQueryVal = getQueryWithLogic(strFullQueryPath);
		String[] strLogicAndVal =  strFullQueryVal.split("\\n");
		if(strLogicAndVal.length ==1){
			System.out.println("Value " + strLogicAndVal[0]);
			return "";
		}else{
			System.out.println("Logic " + strLogicAndVal[0]);
			System.out.println("Value " + strLogicAndVal[1]);
			return strLogicAndVal[0];
		}
	}
	
	/** This method will help to change logic of the query
	 * 	<br/>
	 * Provide query index following full hierarchy.
	 * For example 2nd query in the 1st subgroup of 2nd group then,
	 * Argument: 2.1.2
	 * 
	 * @param strFullPathToQuery
	 * @author Deepen Shah
	 * @since 05 May 2016
	 */
	public void clickToChangeQueryLogic(String strFullPathToQuery){
		int iLastDot = strFullPathToQuery.lastIndexOf(".");
		
		String strGroupPath = strFullPathToQuery.substring(0, iLastDot);
		int iQueryIndex = Integer.parseInt(strFullPathToQuery.substring(iLastDot+1));
		
		System.out.println("Group to be used: " + strGroupPath);
		System.out.println("Query to be fetched: " + iQueryIndex);
		
		findAndSaveGroup(strGroupPath);
		
		List<WebElement> queryItems = globalCurrentGroup.findElements(By.xpath(strQueryItemsInGroup));
		System.out.println("Total queries " + queryItems.size());
		if(queryItems.size()>0 && queryItems.size() >= iQueryIndex){
			queryItems.get(iQueryIndex-1).findElement(By.xpath(strLogicElement)).click();
		}
	}

	/**This method will return count of selected cards from bottom right
	 * @return: int (count)
	 * @author Shailesh Kava
	 * @since 5-May-2016
	 * 
	 */
	public int getSelectedSuccessMetricsCountFromBottomRight(){
		int totalSelectedMetrics = 0;
		
		String[] selectedMetricsString = getSelectedSuccessMetricsCount_rightBottom.getText().trim().split(" ");
		totalSelectedMetrics = Integer.parseInt(selectedMetricsString[0]);
		
		return totalSelectedMetrics;
	}	

	/** This method will help to change logic of any group
	 * 	<br/>
	 * Provide group index following full hierarchy.
	 * For example 3rd subgroup of 2nd group then,
	 * Argument: 2.3
	 * 
	 * @param strFullPathToGroup - String
	 * @author Deepen Shah
	 * @since 05 May 2016
	 */
	public void clickToChangeGroupLogic(String strFullPathToGroup){		
		
		findAndSaveGroup(strFullPathToGroup);
		
		globalCurrentGroup.findElement(By.xpath(strLogicElement)).click();
		
	}
	
	/** This method will help to fetch any category/attribute name.
	 *  This will work for both success metric as well as audience definition.
	 *  
	 *  
	 * @return List<String>
	 * @author Deepen Shah
	 * @since 05 May 2016
	 */
	public List<String> getCategoryNamesAtAnyLevel(){
		List<String> strCatNames = new ArrayList<>();

		if(metricOrAttributeCategoryList.size() > 0){
			for(WebElement element:metricOrAttributeCategoryList)
				strCatNames.add(element.getText().trim());
		
		}else{
			for(WebElement element:metricNamesList)
				strCatNames.add(element.getText().trim());
		}
		return strCatNames;
	}
	/**This methos is used to click on Arrow in bottom save bar from success metrics tab 
	 * @return: 
	 * @author Shailesh Kava
	 * @since 5-May-2016
	 * 
	 */
	public void clickSuccessMetricsBottomSaveBarArrow(){
		successMetricsArrowNavigateToAudienceDef.click();
	}
	

	/**<p><b>This method will return optimal audience percentage value</b></p> 
	 * @return double (percentageVal)
	 * @author Shailesh Kava
	 * @since 5-May-2016
	 * 
	 */
	public double getOptimalAudiencePercentage(){
		double percentageVal = 0;
		
		String[] optimalAudienceStyle = getOptimalAudienceAttribute.getAttribute("style").trim().split(":");		
		String optimalAudiencePer = optimalAudienceStyle[1].replace("%", "").trim();
		optimalAudiencePer = optimalAudiencePer.replace(";","");
		
		percentageVal = Math.round(Double.parseDouble(optimalAudiencePer));
		
		return percentageVal;		
	}
	/** Method will verify query logic for queries in selected group . 
	 *  <br/>
	 *  We have to pass parameter as strFullgroupPath :1 for  the first group
	 *  1.1 as the first subgroup in the  group
	 *  .Param logic : is expected logic, ie AND or OR
	 * 
	 * @param strFullQueryPath - String, logic
	 * @return boolean
	 * @author Amrutha Nair
	 * @since 05 May 2016
	 */
	
	public boolean verifyQueryLogic(String strFullgroupPath, String logic){
		boolean status=true;
		
		String query=null;
		findAndSaveGroup(strFullgroupPath);
		
		List<WebElement> queryItems = globalCurrentGroup.findElements(By.xpath(strQueryItemsInGroup));
		
		int i=1;
		for(WebElement queryItem:queryItems){
			if(i>1){
				query=queryItem.getText().trim();
				
				query=query.split("\\n")[0];
				
				i=i+1;
				if(!(query.contentEquals(logic)))
				{
					status=false;
					break;
				}
			}
		}
		
		
		return status;

	}

	
	/** This method will return the count of attributes(sub category) with icon '+' under selected category in success metrics page
	 * 	
	 *
	 * @return count of attributes
	 * @author Abhishek Deshpande
	 * @since 05 May 2016
	 */
	public int countAttributesNotSelected(){
		int count = listAttributesNotSelectedFromRight.size();
		return count;		
		
	}
	

	/** Method will return the selected slider data from future behaviours demidecile graph . 
	 *  <br/>
	 *  
	 * 
	 * @param 
	 * @return int []
	 * @author Amrutha Nair
	 * @since 06 May 2016
	 */
	
	
	public int [] returnSliderData(){
		
		
		int[] intSliderData = new int[2];
		
		
		String [] sliderData = sliderLeftValue.getAttribute("style").trim().split("left: ");
		String strSliderValue = sliderData[1].replace("%", "").trim();
		strSliderValue = strSliderValue.replace(";","");
		intSliderData[0] =Integer.parseInt(strSliderValue);
		System.out.println("leftvalue:"+strSliderValue);
		
		sliderData = sliderRightValue.getAttribute("style").trim().split("left: ");
		 strSliderValue = sliderData[1].replace("%", "").trim();
		strSliderValue = strSliderValue.replace(";","");
		intSliderData[1] =Integer.parseInt(strSliderValue);
		System.out.println("Rightvalue:"+strSliderValue);
		

		return intSliderData;
	}

	
	/** Method will will verify whether the demi decile graph is graggable in the entire demi decile area . 
	 *  <br/>
	 *  
	 * 
	 * @param 
	 * @return boolean
	 * @author Amrutha Nair
	 * @since 06 May 2016
	 */
	public boolean verifyDemidecileGraphClickableOrDraggable(){
		boolean status=true;
		int leftData=0;
		
		int rightData=100;
		try{
			for (int i=0;i<20;i++){
				
				WebElement lastElement = driver.findElement(By.xpath(MessageFormat.format(strMetricCategoryContains, 1)));
				
			
				Actions action = new Actions(driver);
				if(i<10){
					WebElement leftBar = lastElement.findElement(By.xpath(strSliderWidget+"/a[1]"));
					action.dragAndDropBy(leftBar,leftData,0).build().perform();	
					leftData=leftData+5;
				}
				else{
				
				WebElement rightBar = lastElement.findElement(By.xpath(strSliderWidget+"/a[2]"));			
				action.dragAndDropBy(rightBar,rightData,0).build().perform();				
				rightData=rightData-5;
				}
						
					
					
			}
		}
		catch(Exception e) {
			status =false;
			System.out.println("exception:"+e);
			
		}
		return status;
		
		
	}


	/** This method will check for background color of Success Metric Card/Cards and accordingly will give result as boolean. 
	 * 
	 * @return boolean - Returns true if Success Metric Card/Cards has/have Red color as background else will return false
	 * @author Rohan Macwan
	 * @since 06 May 2016
	 */
	public boolean getBgColorOfSuccessMetricCard_s(){
		boolean result=false;
		if(successMetricCards.size()>0)
		{
			result=true;
			String getBGColor="";
			for (int i=0; i<successMetricCards.size(); i++)
			{
				getBGColor="";
				getBGColor = successMetricCards.get(i).getCssValue("background-color").trim();
				System.out.println("getBgColor = " + getBGColor);
				if(!(getBGColor.equals("rgba(255, 15, 83, 1)")))
				{
					result=false;
				}					
			}
			System.out.println(getBGColor);
		}
		return result;
	}

	
	
	/** Method will click on an level heading in right panel directly . 
	 * eg: If we give strHeading: 'account creation' , when any of it's sub category is open, it will directly click on 'account creation'
	 *  <br/>
	 *  
	 * 
	 * @param strHeading , 
	 * @return void
	 * @author Amrutha Nair
	 * @since 06 May 2016
	 */
	public void clickOnAttributeOrMetriclayerHeading(String strHeading){
		 
		 driver.findElement(By.xpath(MessageFormat.format(strlayerHeading, strHeading.toLowerCase()))).click();
		
	}
	


	/** This method will return total number of count for SubCategories having Plus in RHS 
	 * 
	 * @return int - Returns total number of count for SubCategories having Plus in RHS
	 * @author Rohan Macwan
	 * @since 06 May 2016
	 */
	public int getCountForSubCategoriesHavingPlusIcon(){
		int count=0;
		count =listPlusInRHS.size();
		return count;
	}
	
	
	/** This method will return total number of count for SubCategories having Cross in RHS 
	 * 
	 * @return int - Returns total number of count for SubCategories having Cross in RHS
	 * @author Rohan Macwan
	 * @since 06 May 2016
	 */
	public int getCountForSubCategoriesHavingCorssIcon(){
		int count=0;
		count =listCrossInRHS.size();
		return count;
	}
	
	
	/** This method will return total number of Success Metrics added in the center area 
	 * 
	 * @return int - Returns total number of Success Metrics available in center area
	 * @author Abhishek Deshpande
	 * @since 06 May 2016
	 */
	public int getCountOfSuccessMetricsAddedInCenter(){
		int count=0;
		count =listofSuccessMetricsInCenterArea.size();
		return count;
	}
	
	
	/** This method will return the name of Success Metric added in the center area
	 * 
	 * @param strSuccessMetricName - String , This is the Success Metric Card Name.
	 * @throws Exception throws exception if further element is not loaded
	 * @author Abhishek Deshpande
	 * @since 06 May 2016
	 */
	public boolean verifySuccessMetricNameInCenterArea(String strSuccessMetricName) throws Exception{	
		
		WebElement successMetricName = driver.findElement(By.xpath(MessageFormat.format(strSuccessMetricCardContains, strSuccessMetricName)));
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.visibilityOf(successMetricName));
		boolean status = successMetricName.isDisplayed();
		return status;
		
		
	}
	

	/** This method will return total number of count for SubCategories having Cross in RHS 
	 * 
	 * @return int - Returns total number of count for SubCategories having Cross in RHS
	 * @author Rohan Macwan
	 * @since 06 May 2016
	 */
	public List<String> getNamesOfSelectedSuccessMetricsRHS(){

		List<String> strNames = new ArrayList<String>();
		
		for(WebElement element:listNamesOfSelectedSuccessMetricsInRHS)
			strNames.add(element.getText().trim());
		
		return strNames;
	}
	
	/** This method will help to drag and drop group on audience definition page
	 *  Either it is query or group this function will work.
	 *  
	 *  3rd argument (boolean) will decide if 1st argument is group or query
	 *  
	 * 
	 * @param strSource - String id for group or subgroup
	 * @param strTarget - This will always be a group or outside area. String value for group id or 'outside'.
	 * @param bSourceIsGroup
	 * @return
	 * @throws Exception
	 * @author Deepen Shah
	 * @since 12 May 2016
	 */
	public boolean performDragAndDrop(String strSource, String strTarget,boolean bSourceIsGroup) throws Exception{
		boolean bStatus=false;
		WebElement sourceElement=null;
		String strXpath = "";
		
		//Setup and verifying correctness of argument.
		if(bSourceIsGroup){
			sourceElement = getGroupOrQueryItemElement(strSource, bSourceIsGroup);
			if(sourceElement==null){
				CustomReporter.errorLog("No group/subgroup found at " + strSource + " position");
				BaseClass.rm.captureScreenShot("NoGroupSubgroup-" + strSource.replaceAll(".", "-"), "fail");
				return false;
			}else{
				sourceElement = sourceElement.findElement(By.xpath(strLogicElement));
			}
			strXpath = "./span[contains(.,'Add any group')]";
		}else{
			//Verifying if source=query and target=outside then it is not possible
			if(strTarget.equalsIgnoreCase("outside")){
				CustomReporter.errorLog("Query can not be dropped outside. As given target is " + strTarget +" and source is not a group");
				BaseClass.rm.captureScreenShot("QueryCanNotBeDropOutSide", "fail");
				return false;
			}else{
				sourceElement = getGroupOrQueryItemElement(strSource, bSourceIsGroup);
				if(sourceElement == null){
					CustomReporter.errorLog("Not able to drag as there is no query item at " + strSource + " position");
					BaseClass.rm.captureScreenShot("NoQuery-" + strSource.replaceAll(".", "-"), "fail");
					return false;
				}
				strXpath = "./span[contains(.,'Add any items')]";
			}
		}		
		
		WebElement tGroup=null;
		WebElement targetGroupElement=null;
		
		if(strTarget.equalsIgnoreCase("outside")){
			strXpath = strGroupDropArea;
			targetGroupElement = addNewGroupLink;
		}else{
			tGroup = getGroupOrQueryItemElement(strTarget, true);
			if(bSourceIsGroup){
				targetGroupElement= tGroup.findElement(By.xpath(addGroupLink));
			}else{			
				List<WebElement> targetGroupQueryItems = tGroup.findElements(By.xpath(strDefaultQueryItemText));
				targetGroupElement = targetGroupQueryItems.get(0);			
			}	
		}
		
		
		
		Point dSouce = sourceElement.getLocation();
		Point dTarget = targetGroupElement.getLocation();
		
		WebElement groupCanvas = driver.findElement(By.xpath("//summary"));
		
		Dimension dGroupCanvas = groupCanvas.getSize();	
				
		int summaryX = dGroupCanvas.width / 2;
		int summaryY = dGroupCanvas.height - 50;	
		
		//USing robot class for drag and drop
		Robot robot = new Robot();
		Actions action = new Actions(driver);
				
		//Taking mouse to source & holding that element
	 	robot.mouseMove(dSouce.x, dSouce.y+90);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		//action.clickAndHold(sourceElement).perform();;
		
		//Moving at the end of canvas
		robot.mouseMove(summaryX, summaryY);
		//action.moveToElement(groupCanvas).perform();
		Thread.sleep(2000);
		
		//Moving to target
		//robot.mouseMove(dTarget.x,dTarget.y);
		
		//Moving little down
		int tempY = dTarget.y+50;
		robot.mouseMove(dTarget.x,tempY);
		
		tempY = 50;
		//action.moveToElement(targetGroupElement, 0, tempY).perform();
		
		//Starting for loop to look for destination area
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		int i=0;
		System.out.println("Starting for " + System.currentTimeMillis());
		WebElement element=null;
		
		for(i=0;i<5;i++){
			try{
				if(strTarget.equalsIgnoreCase("outside")){
					element = driver.findElement(By.xpath(strXpath));
				}else{
					element = tGroup.findElement(By.xpath(strXpath));
				}
				System.out.println(element.isDisplayed());
								
				Thread.sleep(2000);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				//action.release(sourceElement).perform();
				break;
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("in catch " + tempY + "-" + System.currentTimeMillis());
				tempY = tempY - 20;
				robot.mouseMove(dTarget.x,tempY);
				//action.moveToElement(targetGroupElement, 0, tempY).perform();
			}
		}
		
		if(i<5){
			bStatus = true;
		}else{
			robot.mouseMove(dSouce.x, dSouce.y+90);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(2000);
		
		return bStatus;
		
	}
	
	/** This method will confirm whether drag and drop is functioning properly or not
	 * 
	 * 	 * 
	 * @param strSource: 
	 * @throws 
	 * @author Amrutha Nair
	 * @since 09 May 2016
	 */	
	//Please donot change it. It is in progress now.
	public boolean verifyDragAndDrop(String strSource, String strTarget){
		boolean status=false;
		System.out.println("strTarget"+strTarget);
		findAndSaveGroup(strSource);
		WebElement source =globalCurrentGroup;
		String Source=source.getText().trim().replace("\n", "");
		System.out.println("source"+Source);
		
		if(Source.contains(strTarget)){
			status=true;
		}
		return status;
		
		
		
	}	

	/** This method  clicks on Edit link for the passed querystring or clicks on Tick marje for passed query String
	 * strFullQueryPath: 1.1 means first query in first group
	 * strCase:'editquery' for clicking on edit icon and "savequery" for clicking on tick icon for saving
	 * 	 * 
	 * @param : 
	 * @throws 
	 * @author Amrutha Nair
	 * @since 10 May 2016
	 */
	public boolean clickEditQueryItemlcon(String strFullQueryPath, String strCase){
		boolean bFound =false;
		
		int iLastDot = strFullQueryPath.lastIndexOf(".");
		
		String strGroupPath = strFullQueryPath.substring(0, iLastDot);
		int iQueryIndex = Integer.parseInt(strFullQueryPath.substring(iLastDot+1));
	
		findAndSaveGroup(strGroupPath);
		
		List<WebElement> queryItems = globalCurrentGroup.findElements(By.xpath(strQueryItemsInGroup));
		System.out.println("Total queries " + queryItems.size());
		if(queryItems.size()>0 && queryItems.size() >= iQueryIndex){
			bFound = true;
			String strAction=null;
			if(strCase.toLowerCase().contentEquals("editquery"))	
			{
				strAction=strQueryEditIcon;
			}
			else if(strCase.toLowerCase().contentEquals("savequery")){
				strAction=strButtonEditTickMark;
			}
			else if(strCase.toLowerCase().contentEquals("cancelquery")){
				strAction=strButtonEditCancelMark;
			}
			WebElement editIcon = queryItems.get(iQueryIndex-1).findElement(By.xpath(strAction));			
			
			
			Actions action = new Actions(driver);
			action.moveToElement(queryItems.get(iQueryIndex-1)).build().perform();
			editIcon.click();
		}
		
		return bFound;
		
	}
	
	
	
	/** This method  edits the query passed in middle area
	 * strFullQueryPath: 1.1 means first query in first group
	 * strQuery: The query with pattern "button_testdata1,testdata2,etc
	 * 	 * 
	 * @param : 
	 * @throws 
	 * @author Amrutha Nair
	 * @since 10 May 2016
	 */
	
	public void editQueryItem(String strFullQueryPath, String strQuery) throws InterruptedException{
		
		String[] strElementValue= strQuery.split("_");		
		String[] strItems=strElementValue[1].split(":");		
		
		int iLastDot = strFullQueryPath.lastIndexOf(".");
		
		String strGroupPath = strFullQueryPath.substring(0, iLastDot);
		int iQueryIndex = Integer.parseInt(strFullQueryPath.substring(iLastDot+1));
		
		findAndSaveGroup(strGroupPath);
		
		List<WebElement> queryItems = globalCurrentGroup.findElements(By.xpath(strQueryItemsInGroup));
		
		
		
		switch(strElementValue[0].toLowerCase()){
			case "button":
				String element=null;
				if(queryItems.size()>0 && queryItems.size() >= iQueryIndex){
					
					for(int i=0;i<strItems.length;i++){
					
						element=MessageFormat.format(strButtonValuesEdit, strItems[i].toLowerCase());
						queryItems.get(iQueryIndex-1).findElement(By.xpath(element)).click();
						
						Thread.sleep(2000);
					}
				}
				break;
		
	}
	
	}
	
	
	/** This method gets the default group name
	 * 	 * 
	 * @param : 
	 * @throws 
	 * @author Amrutha Nair
	 * @since 10 May 2016
	 */
	public String getDefaultGroupName(){
		return defaultGrouptext.getText();
	}
	
	/** Get the name of currently selected categor name.
	 *  Name in the black label
	 *  
	 * @return
	 * @author Deepen Shah
	 * @since 10 May 2016
	 */
	public String getCurrentCategoryHead(){
		return blackLabelList.get(blackLabelList.size()-1).getText().trim();
	}
	
	/** To click on parent category on right hand side
	 * 
	 * @author Deepen Shah
	 * @since 10 May 2016
	 */
	public void clickOnParentCategory(){
		System.out.println(getCurrentCategoryHead());
		int iSize = blackLabelList.size();
		
		if(iSize > 1)
			blackLabelList.get(iSize-2).click();
	
	}

	/** This method verifies the edited query is properly visible in edit panel
	 * strFullQueryPath: 1.1 means first query in first group
	 * strQuery: The query with pattern "button_testdata1,testdata2,etc
	 * 	 * 
	 * @param : 
	 * @throws InterruptedException 
	 * @throws 
	 * @author Amrutha Nair
	 * @since 11 May 2016
	 */
	
	public boolean verifyVisibilityOfEditedElementsInQuery(String strFullQueryPath, String strQuery) throws InterruptedException{
		boolean status=true;
	
		String[] strElementValue= strQuery.split("_");		
		String[] strItems=strElementValue[1].split(":");			
		int iLastDot = strFullQueryPath.lastIndexOf(".");		
		String strGroupPath = strFullQueryPath.substring(0, iLastDot);
		int iQueryIndex = Integer.parseInt(strFullQueryPath.substring(iLastDot+1));		
		findAndSaveGroup(strGroupPath);		
		List<WebElement> queryItems = globalCurrentGroup.findElements(By.xpath(strQueryItemsInGroup));
		
		switch(strElementValue[0].toLowerCase()){
			case "button":
				String element=null;
				
					for(int i=0;i<strItems.length;i++){
						
						WebDriverWait wait = new WebDriverWait(driver, 2);
						element=MessageFormat.format(strSelectedElementInEditPanel, strItems[i].toLowerCase());
						try{						
							wait.until(ExpectedConditions.visibilityOf(queryItems.get(iQueryIndex-1).findElement(By.xpath(element))));
						}catch(Exception e){
							status = false;
							break;
						}
						
						Thread.sleep(2000);
					
				}
				break;
		}
		return status;
	}
	
	
	/** Same method as findAndSaveGroup but it will return web element
	 *  instead of storing it to common place. Also it will help find query
	 *  item web element as well based on boolean parameter
	 *  
	 *  Method is specifically created for drag and drop.
	 * 
	 * @param strFullPathToGroup - String group or query path.
	 * @param bElementIsGroup boolean True if looking for group.
	 * @return
	 * @author Deepen Shah
	 * @since 12 May 2016
	 */
	public WebElement getGroupOrQueryItemElement(String strFullPathToGroup, boolean bElementIsGroup){
		String[] groupLevels={};
		String strGroupPath="";
		int iQueryIndex=0;
		
		if(bElementIsGroup){
			groupLevels = strFullPathToGroup.split("\\.");
		}else{
			int iLastDot = strFullPathToGroup.lastIndexOf(".");
			
			strGroupPath = strFullPathToGroup.substring(0, iLastDot);
			iQueryIndex = Integer.parseInt(strFullPathToGroup.substring(iLastDot+1));
			
			System.out.println("getGroupOrQueryItemElement: Group to be used: " + strGroupPath);
			System.out.println("getGroupOrQueryItemElement: Query to be fetched: " + iQueryIndex);
			
			groupLevels = strGroupPath.split("\\.");
		}
		
		int iLevel = Integer.parseInt(groupLevels[0]);
		
		//Returning null if requested group id is not available in group list
		if(groupList.size() < iLevel)
			return null;
		
		WebElement currentGroup = groupList.get(Integer.parseInt(groupLevels[0])-1);
		
		if(groupLevels.length > 1){		
			
			for(String strLevel:groupLevels){
				if(strLevel == groupLevels[0]){
					/*
					 * Do Nothing as first value is first level group name
					 * This value is already used in above code. 
					*/
				}else{
					iLevel =  Integer.parseInt(strLevel);
					System.out.println("getGroupOrQueryItemElement: " +strLevel);
					List<WebElement> elementTemp = currentGroup.findElements(By.xpath(subGroups));
					System.out.println("getGroupOrQueryItemElement: subgroup count " + elementTemp.size());
					if(elementTemp.size() > 0 && elementTemp.size() >= iLevel){
						currentGroup = elementTemp.get(iLevel-1);
					}else{
						//Returning null if requested subgroup id is not available in group the group
						currentGroup = null;
						break;
					}
					
					System.out.println("loop");
				}
			}			
		}		
		
		if(!bElementIsGroup){
			List<WebElement> queryItems = currentGroup.findElements(By.xpath(strQueryItemsInGroup));
			System.out.println("getGroupOrQueryItemElement: Total queries " + queryItems.size());
			if(queryItems.size()>0 && queryItems.size() >= iQueryIndex){
				currentGroup = queryItems.get(iQueryIndex-1);
			}else{
				currentGroup = null;
			}
		}
			
		
		
		return currentGroup;
	}
	
	
	/** This method returns the population percnetage value, NOT percentage
	 * @param : 
	 * @throws 
	 * @author Amrutha Nair
	 * @since 16 May 2016
	 */
	
	public int getSelectedPopulationValue(){
		return Integer.parseInt(selectedPopulationValue.getText());
	}
	
	
	
	/**This method will return the location of webelement
	 * @param eleName
	 * @return
	 * @author Abhishek Deshpande
	 * @since 16 May 2016
	 */
	public Point getPositionOfElement(String eleName){
		Point loc = null;
		switch(eleName.toLowerCase()){
		
		case "populationvalue": 
			  loc=percentagePopulationValue.getLocation();
			  CustomReporter.log("X and Y co ordinates of population is "+loc.x+" "+loc.y);
			  break;		
	}
		return loc;
	}
	
  /**
   * Get total number of added audience attribute list
   * 
   * @author Vikram Hegde
   * @return
   */
  public int getNumberOfAddedAudienceAttributesList() {
    return audienceAddedAttributesList.size();
  }
	
}


