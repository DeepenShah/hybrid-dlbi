
/**
 * Analyse_Webnet_Page
 * List Of Methods:
 * func_ClickElement(String element):This method is used to click on an element in analyse page
 * func_CompareWebnetValues(String Query):This method  compares values in webnet table  with data getting retrieved from database table  according to the DB query passed 
 * func_ElementExist(String elementName):This method is used to check the existence of any web element of webnet page
 * func_GetLocalWebElement(String strWebElement):This method is used to get private WebElement out of this class
 */

package com.IDIOM.pages;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.BaseClass;

public class Analyse_Webnet_Page {
	
	private int i=0;
	private int j=0;
	private int k=0;
	
	private int wb_Cell_IntValue=0;
	WebDriver driver;
	
	public Analyse_Webnet_Page(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	@FindBy(xpath="//*[text()='Web Net']")
	private WebElement lnk_webnet;	
	
	@FindBys(@FindBy(xpath="//*[@class='webnet page-level ng-scope']"))
	private List<WebElement> webNet_Page;
	
	@FindBy(xpath="//*[@class='ion ion-navicon-round']")
	private WebElement MenuIcon;
	
	@FindBys(@FindBy(xpath="//*[@class='admin-access']"))
	private List<WebElement>   AdminAccessPresent;
	
	@FindBy(xpath="//*[@class='admin-access']")
	private WebElement   AdminAccess;
	
	////////////IDIOM 2.3//////////
	
	@FindBy(xpath="//*[name()='svg']")
	public WebElement webNetImage;

	@FindBy(xpath="//*[@class='webnet page-level']//*[@id='chart1']")
	public WebElement chart;
	

	@FindBy(xpath="//*[@id='window']")
	public WebElement transitionWindowDropDown;
	
	@FindBys(@FindBy(xpath="//*[@id='window']/option"))
	private List<WebElement> transitionWindowDrpItems;
	
	@FindBys(@FindBy(xpath="//*[name()='svg']/*[name()='g'][@class='highcharts-axis-labels highcharts-xaxis-labels']/*[name()='text']//*[name()='tspan']"))
	private List<WebElement> xAxisValues;
	
	@FindBys(@FindBy(xpath="//*[name()='svg']/*[name()='g'][@class='highcharts-axis-labels highcharts-yaxis-labels']/*[name()='text']"))
	private List<WebElement> yAxisValues;
	
	@FindBys(@FindBy(xpath="//*[name()='svg']/*[name()='g'][contains(@class,'highcharts-data-labels highcharts-series')]//*[name()='tspan']"))
	private List<WebElement> chartValues;
	
	@FindBy(xpath="//button[@class='btn btn-muted export ng-scope']")
	public WebElement exportData_button;
	
	@FindBy(xpath ="//*[@class='block block--clear error-unicorn layout horizontal ng-scope']//h1[contains(text(),'Audience too narrow')]")
	public WebElement audienceTooNarrow;
	
	String strtransitionWindowSelectedItem="//*[@id=''window'']/option[@selected=''selected''][{0}]";

	@FindBy(xpath ="//span[text()='Show Audience']/ancestor::button")
	public WebElement btn_ShowAudience;
	
	@FindBy(xpath ="//div[@class='highcharts-tooltip']//span")
	public WebElement toolTip_CellValue;
	
	
	/**
	 * 
	 * @param strElement - 'webnetimage','webnetchart'
	 * @return boolean True/False
	 * @author Shailesh Kava
	 * @since 27 April 2016
	 * 
	 */
	public boolean isVisible(String strElement){
		boolean bStatus = true;
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		try{
			switch(strElement.toLowerCase()){
				case "webnetimage": 
					wait.until(ExpectedConditions.visibilityOf(webNetImage));
					break;

				case "webnetchart":
				     wait.until(ExpectedConditions.visibilityOf(chart));
				     break;

			}
		}catch(Exception e){
			bStatus = false;
		}
		
		return bStatus;
	}
	
	
	/**
	 * This method returns selected transition window item
	 * 
	 * @return String
	 * @author Amrutha Nair
	 * @since 31 May 2016
	 * 
	 */
	public String getSelectedTransitionWindowItem(int id){
		WebElement transitionWindowSelectedItem= driver.findElement(By.xpath(MessageFormat.format(strtransitionWindowSelectedItem, id)));
		return transitionWindowSelectedItem.getText();
	}
	
	/**
	 * This method returns all the items in transition window 
	 * 
	 * @return List<String>
	 * @author Amrutha Nair
	 * @since 31 May 2016
	 * 
	 */
	public List<String> returnTransitionWindowItems(){
		 List<String> windowItems= new ArrayList<String>();
		for(WebElement item:transitionWindowDrpItems){
			windowItems.add(item.getText().trim().toLowerCase());
		}
		return windowItems;
	}

		


	
	/**
	 * This method select the specified item from transition window 
	 * 
	 * @return 
	 * @author Amrutha Nair
	 * @since 31 May 2016
	 * 
	 */
	
	public void selectTransitionWindowItem(String name){
		Select elementDropdown = new Select(transitionWindowDropDown);
		int index = 0;
	    for (WebElement option :transitionWindowDrpItems){
	        if (option.getText().equalsIgnoreCase(name))
	            break;
	        index++;
	    }
		elementDropdown.selectByIndex(index);
	}

	
	/**
	 * This method gets the data from webnet chart in a hash table
	 * 
	 * @return 
	 * @author Amrutha Nair
	 * @since 31 May 2016
	 * 
	 */
	public Hashtable<String, String> captureDataInWindow(){
		Hashtable<String, String> ChartData = new Hashtable<String, String>();
		int count=xAxisValues.size()*yAxisValues.size();
		System.out.println(count);
		String xAxisValue=null;
		String yAxisValue=null;
		String combinedAxisValue=null;
		String cell_Value=null;
		int j=0;
		int k=0;
		for ( int i=0; i<count;i++) {
			xAxisValue=xAxisValues.get(j).getText();
			yAxisValue=yAxisValues.get(k).getText();
			combinedAxisValue=xAxisValue+"||"+yAxisValue;
			System.out.println(combinedAxisValue);
			
			cell_Value=chartValues.get(i).getText();
			System.out.println(cell_Value);
			ChartData.put(combinedAxisValue, cell_Value);	
			j=j+1;
			if(j==xAxisValues.size()){
				j=0;
				k=k+1;
			}
			
		}
		return ChartData;
	}
	
	/**
	 * This method compares two hash tables
	 * 
	 * @return 
	 * @author Amrutha Nair
	 * @since 31 May 2016
	 * 
	 */
	
	public boolean CompareCharts(Hashtable<String, String> chart1, Hashtable<String, String> chart2){
		boolean status=false;
		for (Map.Entry<String, String> chartEntries : chart1.entrySet()) {
			   if(chart2.containsKey(chartEntries.getKey()) && chart2.get(chartEntries.getKey()).equals(chartEntries.getValue())){
				   status = true;
				   System.out.println(chartEntries.getKey());
				   System.out.println(chart2.get(chartEntries.getKey()));
					 System.out.println(chartEntries.getValue());
					 System.out.println(status);
			   }
			  }
		return status;
		
	}
	////////////IDIOM 2.3 - END//////////
	/**func_CompareWebnetValues(String Query) 	 * 
	 *This method  compares values in webnet table  with data getting retrieved from database table  according to the DB query passed
	 *Created By:Amrutha Nair
	 *Created On:27 July 2015
	 *Modified By | Description of Modification: 
	 *Modified On: 
	 */		 
	public Boolean  func_CompareWebnetValues(String Query) throws InterruptedException{
		
		lnk_webnet.click();
		Thread.sleep(3000);
		//String test_Status=null;
		Boolean result = true;
		//To retrieve data from webnet site
		
		String cell_Value=null;
		String vrtcal_Value=null;
		String hrizntal_Value=null;
		String JoinedData=null;
		int Cell_ItemValue=0;
		int j=0;
		int k=1;
		Hashtable<String, Integer> webnetdata = new Hashtable<String, Integer>();
		for ( int i=1; i<170;i++) {
			
			    j=j+1;			 
			    vrtcal_Value="//*[name()='svg']/*[name()='g'][9]/*[name()='text']["+j+"]";
			    hrizntal_Value="//*[name()='svg']/*[name()='g'][8]/*[name()='text']["+k+"]";
				JoinedData=driver.findElement(By.xpath(vrtcal_Value)).getText()+"||"+driver.findElement(By.xpath(hrizntal_Value)).getText();
				cell_Value="//*[name()='svg']/*[name()='g'][6]/*[name()='g']["+i+"]/*[name()='text']";
				Cell_ItemValue =Integer.parseInt(driver.findElement(By.xpath(cell_Value)).getText());
				webnetdata.put(JoinedData, Cell_ItemValue);				
				if(j ==13)
				{
					j=0;
					k=k+1;
				}
				
		}	
	
	  //To retrieve data from Db	
		
	   Hashtable<String, Integer> Dbdata = new Hashtable<String, Integer>();			
	   Dbdata=BaseClass.rm.RetrieveValuesFromDB(Query);
			
      //To compare the values from Db with Web net data
	   
	  for (Map.Entry<String, Integer> htEntries : Dbdata.entrySet()) {
		   if(webnetdata.containsKey(htEntries.getKey()) && webnetdata.get(htEntries.getKey()).equals(htEntries.getValue())){
			  }
		   else
		     {
		          result = false;
		     }
		  }
		return result;	

	}
	
	/**func_ElementExist(String elementName)
	* This method is used to check the existence of any web element of webnet page
	* Created By: Amrutha Nair
	* Created On:28 Sept 2015
	* Modified By | Description of Modification:
	*/		
		
	public boolean  func_ElementExist(String elementName){
			boolean found=false;
			switch (elementName) {
			
			case "webNet_Page":		
				found=BaseClass.rm.webElementExists(webNet_Page);
			break;
			case "Admin Access Present":	
				MenuIcon.click();
				found=BaseClass.rm.webElementExists(AdminAccessPresent);
			break;
			}
		return found;
	}
	
	/** func_ClickElement(String element)
	* This method is used to click on an element in analyse page
	* Created By: Amrutha Nair
	* Modified By | Modified Date: | Description of Modification:
	*/	
	public void func_ClickElement(String element){
		switch (element){
		
		case "AdminAccess":
			MenuIcon.click();
			AdminAccess.click();
		break;
		case "transitionwindow":
			transitionWindowDropDown.click();
		break;
		
		case "showaudience":
		
	    	btn_ShowAudience.click();
	    	break;
		}
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
	
	
	public Hashtable<String, String> captureDataInHoverToolTip() throws InterruptedException{
		Hashtable<String, String> ChartData = new Hashtable<String, String>();
		
		int count=chartValues.size();

		System.out.println(count);
		String xvalue1=null;
		
		String[] tooltipData=new String[3];
		String combinedValue=null;
		String yvalue2=null;
		String cellValue=null;
		Actions action = new Actions(driver);
		for(WebElement cellValueElement:chartValues){
			action.moveToElement(cellValueElement).perform();
			
			tooltipData=toolTip_CellValue.getText().split("PEOPLE VISITED");
			cellValue=tooltipData[0].trim();
			
			
			yvalue2=tooltipData[1].split("THEN")[0].trim();
			
			xvalue1=tooltipData[1].split("THEN")[1].trim();
			
			combinedValue=xvalue1+"||"+yvalue2;
			System.out.println("Hover:"+combinedValue);
			System.out.println("Hover:"+cellValue);
			ChartData.put(combinedValue, cellValue);
			
		}
		return ChartData;
	}
}
