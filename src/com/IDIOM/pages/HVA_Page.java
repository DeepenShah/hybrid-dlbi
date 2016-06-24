/**
 * HVA_Page
 * List Of Methods:
 * func_CaptureChartValues(String  behaviourValue):This method is used to capture label value pair from hva charts in a hash table 
 * func_CaptureList(String Case):This method is used to capture values from list webelement 
 * func_CheckBGColourOrTextAlighment(String Case, String Expected):This method is used to capture label value pair from hva charts in a hash table 
 * func_ClickElement(String element):This method is used to click on an elemnt in HVA page
 * func_CompareCharts(Hashtable<String, String> chart1, Hashtable<String, String> chart2):This method is used to compare hash tables
 * func_ElementExist(String elementName):This method is used to check the existence of any web element of Audience page
 * func_GetLocalWebElement(String strWebElement):This method is used to get private WebElement out of this class. 
 * func_getText(String Case):This method is used to get the text present with the webelement
 * func_SortValues(String Case):This method is used to check sort order in HVA page 
 * func_VerifyBehaviourFilters(String Filters):This method is used verify filters
 * func_VerifyChartBarWithValues():This method is used to check bar chart values and length
 * func_VerifyScroll():This method is used to check the scroll for HVA page
 * func_VerifySelectFromDropDown(ArrayList<String> InputArray, String Case, int position):This method is used to verify the select from drop down fucntionality
 */

package com.IDIOM.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.Field;

import javax.crypto.spec.PSource;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.BaseClass;

import com.reports.CustomReporter;

public class HVA_Page {
	
	WebDriver driver;
	public HVA_Page(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
		
	}
	
	@FindBys(@FindBy(xpath="//*[@class='subnav']/*[@class='active']/*[text()='High Value Actions']"))
	private List<WebElement> hvaPage;
	
	
	@FindBy(xpath="//*[@class='order']/*[@class='sorted ng-binding']")
	private WebElement sortText;
	
	
	@FindBys(@FindBy(xpath="//*[@class='action-value']/*[@class='ng-binding']"))
	private List<WebElement> HVAValues;
	
	@FindBy(xpath="//*[@class='order']/*[contains(@class, 'ion ion-arrow-down-b')]")
	private WebElement DownArrow;
	
	@FindBy(xpath="//*[@class='order']/*[contains(@class, 'ion ion-arrow-up-b')]")
	private WebElement UpArrow;
	
	@FindBys(@FindBy(xpath="//*[@id='behaviors']/option"))
	private List<WebElement> behaviourList;
	
	@FindBys(@FindBy(xpath="//*[@id='behaviors']"))
	private List<WebElement> behaviourListPreent;
	
	@FindBys(@FindBy(xpath="//div[@class='label-select']//select[@id='filters']"))
    private List<WebElement> WebAction_Filter;
    
    @FindBy(xpath="//*[@id='filters']/*[@selected='selected']")
    private WebElement Filter_None;

	
	@FindBy(xpath="//*[@id='behaviors']")
	public WebElement behaviour;
	
	@FindBy(xpath="//*[@id='behaviors']/*[@selected='selected']")
	private WebElement behaviour_Selected;
	
	@FindBys(@FindBy(xpath="//*[@class='hva-chart ng-scope']"))
	private List<WebElement> ChartPresent;
	
	
	@FindBys(@FindBy(xpath="//*[@class='action-container ng-scope positive']"))
	private List<WebElement> Chartlist;
	
	@FindBy(xpath="//*[@class='hide-hvas dropdown-parent']")
	private WebElement WebAction;
	
	@FindBys(@FindBy(xpath="//*[@class='hide-hvas dropdown-parent']/*[@class='dropdown-box open']"))
	private List<WebElement> WebActionDrpOpen;
	
	@FindBy(xpath="//*[@class='hide-hvas dropdown-parent']/label")
	private WebElement WebAction_Hide;
	
	@FindBy(xpath="//*[@class='dropdown-list-heading ng-scope']/*[@class='ng-binding']")
	private WebElement WebAction_FirstRow;
	
	@FindBys(@FindBy(xpath="//*[@class='dropdown-box open']//*[@class='ng-binding']"))
	private List<WebElement> WebActionDrpDownItems;
	
	@FindBys(@FindBy(xpath="//*[@class='hva-chart ng-scope']//*[@class='action-label']/i"))
	private List<WebElement> ChartLabels;
	
	@FindBy(xpath="//*[@class='hide-hvas dropdown-parent']//li[@class='ng-scope'][1]")
	private WebElement WebAction_DrpItem;
	
	@FindBy(xpath="//*[@class='hide-hvas dropdown-parent']/label/strong")
	private WebElement WebAction_NymberItem;
	
	@FindBys(@FindBy(xpath="//*[text()='There has been an internal server error!']"))
	private List<WebElement> internal_ServerError;
	
	@FindBys(@FindBy(xpath="//*[@class='admin-access']"))
	private List<WebElement>   AdminAccessPresent;
	
	@FindBy(xpath="//*[@class='ion ion-navicon-round']")
	private WebElement MenuIcon;
	
	@FindBy(xpath="//*[@class='admin-access']")
	private WebElement   AdminAccess;
	
	
	////////////IDIOM 2.3//////////
	@FindBy(xpath="//*[@class='hva-chart ng-scope']")
	private WebElement hvaChart;
	
	@FindBy(xpath="//button[@class='btn btn-muted export ng-scope']")
	public WebElement exportData_button;
	
	@FindBys(@FindBy(xpath="//*[@class='action-container ng-scope negative']/*[@class='action-label']/i"))
	public List<WebElement>  negativeValuesNames;
	
	@FindBys(@FindBy(xpath="//*[@class='action-container ng-scope negative']/*[@class='action-value']/i"))
	public List<WebElement>  negativeValuesDataValues;
	
	@FindBys(@FindBy(xpath="//*[@class='action-container ng-scope negative']/*[@class='action-value']/span"))
	public List<WebElement>  negativeValuesDataBars;
	
	@FindBys(@FindBy(xpath="//*[@class='action-container ng-scope positive']/*[@class='action-label']/i"))
	public List<WebElement>  PositiveValuesNames;
	
	@FindBys(@FindBy(xpath="//*[@class='action-container ng-scope positive']/*[@class='action-value']/i"))
	public List<WebElement>  PositiveValuesDataValues;
	
	@FindBys(@FindBy(xpath="//*[@class='action-container ng-scope positive']/*[@class='action-value']/span"))
	public List<WebElement>  PositiveValuesDataBars;
	
	@FindBy(xpath="//div[@class='']/*[@class='ng-isolate-scope']//h1[contains(text(),'Audience too narrow')]")
	public WebElement narrowMsg;
	
	@FindBy(xpath="//button[@class='audience-details-toggle']")
	public WebElement showAudienceBtn;
	/**
	* Options: 'hva_chart'
	* 
	* @param strElement - String case to verify visibility. Use value from list above.
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
		case "hva_chart": 
			wait.until(ExpectedConditions.visibilityOf(hvaChart));
			break;

		case "webactionheading":
			wait.until(ExpectedConditions.visibilityOf(WebAction_FirstRow));
			break;					
			
		case "audiencenarrow":
			wait.until(ExpectedConditions.visibilityOf(narrowMsg));
			break;
			
		case "slowdown":
			wait.withTimeout(120, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.visibilityOf(behaviour));
			wait.until(ExpectedConditions.elementToBeClickable(behaviour));
			wait.withTimeout(30, TimeUnit.SECONDS);		
			break;

	}
	}catch(Exception e){
	bStatus = false;
	}
	
	return bStatus;
	}
	////////////IDIOM 2.3 - END//////////
	
	/** func_ElementExist(String elementName)
	* This method is used to check the existence of any web element of Audience page
	* Created By: Amrutha Nair
	* Created On:22 Septemeber 2015
	* Modified By | Description of Modification:
	*/		
		
	public boolean  func_ElementExist(String elementName){
			boolean found=false;
			switch (elementName) {
			
			case "HVAPageExists":		
				found=BaseClass.rm.webElementExists(hvaPage);
			break;	
			
			case "BehaviourFilter":		
				found=BaseClass.rm.webElementExists(behaviourListPreent);
			break;	
			
			case "Barchart":		
				found=BaseClass.rm.webElementExists(ChartPresent);
			break;
			
			case "WebAction":
				found=BaseClass.rm.webElementExists(WebActionDrpOpen);
			break;
			case "ServerError":
				found=BaseClass.rm.webElementExists(internal_ServerError);
			break;
			
			case "Admin Access Present":	
				MenuIcon.click();
				found=BaseClass.rm.webElementExists(AdminAccessPresent);
			break;	
			}
			
			return found;
	}
	
	/** func_getText(String Case)
	* This method is used to get the text present with the webelement
	* Created By: Amrutha Nair
	* Created On:22 Septemeber 2015
	* Modified By | Description of Modification:
	*/		
		
	
	public String func_getText(String Case){
		
		String value=null;
		switch (Case) {
		
			case "Sort":
				value=sortText.getText();
				
			break;
			
			case "Behaviour":
				value=behaviour_Selected.getText();
			break;
			
			case "FilterDefault":
                value=Filter_None.getText();
             break;
             
			case "WebAction":
				value=WebAction_Hide.getText();
				
			break;
			
			case "WebAction_FirstRow":
				value=WebAction_FirstRow.getText();
			break;
			
			case "WebActionNumberItem":
				value=WebAction_NymberItem.getText();
			break;

		}
		
		return value;
	}
	
	
	/**func_SortValues(String Case)
	* This method is used to check sort order in HVA page
	* Created By: Amrutha Nair
	* Created On:22 Septemeber 2015
	* Modified By | Description of Modification:
	*/		
		
	
	public boolean func_SortValues(String Case){
		
		boolean status=true;
		switch (Case) {
		
			case "Decending":
				String ValueString=null;
				Float pFloatValue=Float.MAX_VALUE;
				Float nFloatValue=Float.MIN_VALUE;
				Float temp=0.1f;
				for (WebElement value : HVAValues){	
					ValueString=value.getText();
					if(ValueString.contains("-")){
						ValueString=ValueString.split("-")[1];
						temp=Float.parseFloat(ValueString);
						if(temp>=nFloatValue){
							nFloatValue=temp;
							//System.out.println("DescNgtv");
						}
						else{
							status=false;
							break;
						}
					}
					else{
						temp=Float.parseFloat(ValueString);
						if (temp<=pFloatValue){
							pFloatValue=temp;
							//System.out.println("DescPstv");
						}
						else{
							status=false;
							break;
						}					
					}
					
				}
			break;
			
			case "Acending":
				String ValueString1=null;
				Float pFloatValue1=Float.MIN_VALUE;
				Float nFloatValue1=Float.MAX_VALUE;
				Float temp1=0.1f;
				for (WebElement value : HVAValues){	
					ValueString1=value.getText();
					if(ValueString1.contains("-")){
						ValueString1=ValueString1.split("-")[1];
						temp1=Float.parseFloat(ValueString1);
						if(temp1<=nFloatValue1){
							nFloatValue1=temp1;
							//System.out.println("Acendingnegative");
						}
						else{
							status=false;
							break;
						}
					}
					else{
						temp1=Float.parseFloat(ValueString1);
						if (temp1>=pFloatValue1){
							pFloatValue1=temp1;
							//System.out.println("Acendingpositve");
						}
						else{
							status=false;
							break;
						}					
					}
					
				}
				
		}
		
		return status;
	}
	

	/**This method is used to check the BG colour for componants in HVA page
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair 
	 * @since :22 Septemeber 2015
	 * Modified :25May 2015
	 * By:Amrutha 
	 */	
	public boolean func_CheckBGColour(String Case, String Expected){
		boolean status=false;
		boolean flag=true;
		switch (Case) {
		
			case "UpArrowColor":	
				
				status=UpArrow.getCssValue("color").contains(Expected);
			break;
			
			case "DownArrowColor":
				
				status=DownArrow.getCssValue("color").contains(Expected);
			break;
			
			case "PositiveBarsColour":
				
				for(int i=0;i<PositiveValuesDataBars.size();i++){
					if(!(PositiveValuesDataBars.get(i).getCssValue("background-color").contains(Expected))){
					//CustomReporter.log("The BG colour for positive bar appearing with '"+PositiveValuesNames.get(i).getText()+"'is expected");
						CustomReporter.errorLog("The BG colour for positive bar appearing with '"+PositiveValuesNames.get(i).getText()+"' is NOT expected");
						flag=false;
						System.out.println(PositiveValuesNames.get(i).getCssValue("background-color"));
					}
				
				
			}
			if(flag==true){
				status=true;
			}
			break;
			
		
			case "NegativeBarsColour":
				for(int j=0;j<negativeValuesDataBars.size();j++){
					if(!(negativeValuesDataBars.get(j).getCssValue("background-color").contains(Expected))){
						CustomReporter.errorLog("The BG colour for negative bar appearing with '"+negativeValuesNames.get(j).getText()+"' is NOT expected");
						flag=false;
						System.out.println(negativeValuesDataBars.get(j).getCssValue("background-color"));
				}
			
				
			}
			if(flag==true){
				status=true;
			}
			break;
			
		}
			
		
		return status;
	}
	
	
	/** func_ClickElement(String element)
	* This method is used to click on an elemnt in HVA page
	* Created By: Amrutha Nair
	* Created On:22 Septemeber 2015
	* Modified By | Description of Modification:
	*/		
	public void func_ClickElement(String element){
		switch (element){
		
			case "UpArrow":
				UpArrow.click();
			break;
			
			case "Behaviour":
				behaviour.click();
			break;
			
			case "WebAction":
				WebAction.click();
			break;
			
			case "WebActionDrpItemClick":
				WebAction_DrpItem.click();
			break;
			case "AdminAccess":
				MenuIcon.click();
				AdminAccess.click();
			break;
			
			case "showaudience":
				showAudienceBtn.click();
				break;		
			
		}	
	
	
	}
	
	
	
	/**func_VerifyBehaviourFilters(String Filters)
	* This method is used verify filters
	* Created By: Amrutha Nair
	* Created On:22 Septemeber 2015
	* Modified By | Description of Modification:
	*/		
	public boolean func_VerifyBehaviourFilters(String Filters){
		boolean status=true;
		
		String[] filter=Filters.split(",");
		
		if(filter.length==behaviourList.size()){
			for(WebElement behaviour:behaviourList){
				if(Arrays.asList(filter).contains(behaviour.getText())){
					CustomReporter.log("The '"+behaviour.getText()+"'  presnet in behaviour drop down present in HVA page ");
				}
				else{
					CustomReporter.errorLog("The '"+behaviour.getText()+"' is  present in behaviour drop down present in HVA page is NOT expected");
					status=false;
				}
			}
		}
		else if(filter.length<behaviourList.size()){
			CustomReporter.errorLog("The behaviour drop down is  having extra  values than expected");
			status=false;
		}
		else{
			CustomReporter.errorLog("The behaviour drop down is not having all  values as expected");
			status=false;
		}
		return status;
	}	
	
	
	/** func_CaptureList(String Case)
	* This method is used to capture values from list webelement
	* Created By: Amrutha Nair
	* Created On:22 Septemeber 2015
	* Modified By | Description of Modification:
	*/	
	public ArrayList<String> func_CaptureList(String Case){

		ArrayList<String> values = new ArrayList<String>();
		switch (Case) {
		
		case "Behaviour":
			for (WebElement behaviour:behaviourList){	
				values.add(behaviour.getText());
			}
		break;
		
		case "WebActionDropDown":
			for(WebElement drpDown:WebActionDrpDownItems){
				values.add(drpDown.getText());
			}
			
		break;
		
		case "ChartLabels":
			for(WebElement label:ChartLabels){
				values.add(label.getText());
			}
			
		break;
		}
		return values;
	}
	

	
	/**This method is used to capture label value pair from hva charts in a hash table 
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair 
	 * @since :23 Septemeber 2015
	 * Modified :25May 2015
	 * By:Amrutha 
	 */	
	public Hashtable<String, String> func_CaptureChartValues(String  behaviourValue) throws InterruptedException{
		
		Hashtable<String, String> ChartData = new Hashtable<String, String>();
		 
		Select dropdown = new Select(behaviour);
		dropdown.selectByVisibleText(behaviourValue);
		Thread.sleep(1000);
		String label=null;
		String value=null;
		
		for(int i=1;i<PositiveValuesNames.size();i++){
			
			label=PositiveValuesNames.get(i).getText();
			value=PositiveValuesDataValues.get(i).getText();
			ChartData.put(label, value);
		}
		for(int j=1;j<negativeValuesNames.size();j++){
			
			label=negativeValuesNames.get(j).getText();
			value=negativeValuesDataValues.get(j).getText();
			ChartData.put(label, value);
		}
		return ChartData;
		
		
	}
	
	
	/** func_CompareCharts(Hashtable<String, String> chart1, Hashtable<String, String> chart2)
	* This method is used to compare hash tables
	* Created By: Amrutha Nair
	* Created On:23 Septemeber 2015
	* Modified By | Description of Modification:
	*/	
	public boolean func_CompareCharts(Hashtable<String, String> chart1, Hashtable<String, String> chart2){
		boolean status=false;
		for (Map.Entry<String, String> chartEntries : chart1.entrySet()) {
			   if(chart2.containsKey(chartEntries.getKey()) && chart2.get(chartEntries.getKey()).equals(chartEntries.getValue())){
				  }
			   else
			     {
				   status = true;
			     }
			  }
		return status;
		
		
		
	}
	
	
	/** func_VerifySelectFromDropDown(ArrayList<String> behaviourArray)
	* This method is used to verify the select from drop down fucntionality
	* Created By: Amrutha Nair
	* Created On:23 Septemeber 2015
	* Modified By | Description of Modification:
	*/	
	public boolean func_VerifySelectFromDropDown(ArrayList<String> InputArray, String Case, int position){
		boolean status=true;
		
		switch (Case) {		
			case "Behaviour":
				Select dropdown = new Select(behaviour);		
				for(int i=0;i<InputArray.size();i++){
					try{
						dropdown.selectByVisibleText(InputArray.get(i));
					}
					catch  (Exception e) {
						status=false;
					}
				}
			break;
			
			case "WebAction":
				if(position==0){
					for(int i=1;i<=InputArray.size();i++){
						try{
							driver.findElement(By.xpath("//*[@class='hide-hvas dropdown-parent']//li[@class='ng-scope']["+i+"]")).click();
						}
						catch (org.openqa.selenium.ElementNotVisibleException e){
							func_ClickElement("WebAction");	
							driver.findElement(By.xpath("//*[@class='hide-hvas dropdown-parent']//li[@class='ng-scope']["+i+"]")).click();
						}
					
					}
				}
				else{						
					driver.findElement(By.xpath("//*[@class='hide-hvas dropdown-parent']//li[@class='ng-scope']["+position+"]")).click();
				}
				
			break;
		}
				
		return status;
		
	}
	
	/** func_VerifyChartBarWithValues()
	* This method is used to check bar chart values and length
	* Created By: Amrutha Nair
	* Created On:25 Septemeber 2015
	* Modified By | Description of Modification:
	*/	
	public boolean func_VerifyChartBarWithValues(){
		boolean status=true;
		String BarWidth=null;
		String ba=null;
		int actionValueData=0;
		
		int WidthData=0;
		String ActionValue=driver.findElement(By.xpath("//*[@class='action-container ng-scope positive'][1]/*[@class='action-value']/i")).getText();
		Float ActionValuef=Float.parseFloat(ActionValue);

		Float ActionValuef1=0.1f;
		Float BarWidth1=Float.MAX_VALUE;
		Float BarWidth2=0.1f;
		ba=driver.findElement(By.xpath("//*[@class='action-container ng-scope positive'][1]/*[@class='action-value']/span")).getCssValue("width");
		BarWidth1=Float.parseFloat(ba.split("px")[0]);
		
		for(int i=2;i<=Chartlist.size();i++){					
			
			BarWidth=driver.findElement(By.xpath("//*[@class='action-container ng-scope positive']["+i+"]/*[@class='action-value']/span")).getCssValue("width");
			
			BarWidth2=Float.parseFloat(BarWidth.split("px")[0]);
			
			ActionValuef1=Float.parseFloat(driver.findElement(By.xpath("//*[@class='action-container ng-scope positive']["+i+"]/*[@class='action-value']/i")).getText());
			
			if(ActionValuef<ActionValuef1){
				if(BarWidth1<BarWidth2){
					System.out.println("When the action values are going bigger graph is also going bigger");
					
					actionValueData= Math.round(ActionValuef / ActionValuef1);					
					WidthData=Math.round(BarWidth1/BarWidth2);
	
					if(actionValueData==WidthData){
						System.out.println("When the action values and bar graph lengths are proportional ");
					}
					else{
						System.out.println("When the action values and bar graph lengths are not proportional ");
						status=false;
					}
					
				}
				else{
					System.out.println("When the action values are NOT going bigger graph is also going bigger");
					status=false;
				}
			}
			else{
				if(BarWidth1>BarWidth2){
					System.out.println("When the action values are going smaller graph is also going smaller");
					actionValueData= Math.round(ActionValuef / ActionValuef1);
				
					WidthData=Math.round(BarWidth1/BarWidth2);
					
					if(actionValueData==WidthData){
						System.out.println("When the action values and bar graph lengths are proportional ");
					}
					else{
						System.out.println("When the action values and bar graph lengths are not proportional ");
						status=false;
					}
					
				}
				else{
					System.out.println("When the action values are NOT going smaller graph is also going smaller");
					status=false;
				}
			}
			
		}
		
		return status;
	}
	
	/**func_VerifyScroll
	* This method is used to check the scroll for HVA page
	* Created By: Amrutha Nair
	* Created On:25 Septemeber 2015
	* Modified By | Description of Modification:
	*/	
	public boolean func_VerifyScroll(){
		boolean status=true;
		if(Chartlist.size()>25){
			 JavascriptExecutor javascript = (JavascriptExecutor) driver; 
			 status = (Boolean) javascript.executeScript("return document.documentElement.scrollHeight>=document.documentElement.clientHeight;"); 
		}
		else{
			CustomReporter.log("Scroll is NOt needed as the number of rows in HVA page is less");
		}
		return status;
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
	
	
	/**This method is used to verify alignment of each componant in HVA bar graph 
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair 
	 * @since :25May 2015
	 */	
	
	public boolean verifyAlignment(String Case, String Expected){
		boolean status=false;
		boolean flag=true;
		switch (Case) {
		case "PositiveBarsAlignment":
			flag=true;
			for(int i=0;i<PositiveValuesDataBars.size();i++){
				
				 if(!(PositiveValuesDataBars.get(i).getCssValue("text-align").contains(Expected))){
					 CustomReporter.errorLog("The bars alignment for positive values for   '"+PositiveValuesNames.get(i).getText()+"' is NOT  to "+Expected+"'");
					  flag=false;
					  System.out.println(negativeValuesNames.get(i).getCssValue("text-align"));
				  }
				
				}
				if(flag==true){
					status=true;
				}
		break;
		
		case "PositiveLabelAlignment":
			flag=true;
			for(int j=0;j<PositiveValuesNames.size();j++){
				
				 if(!(PositiveValuesNames.get(j).getCssValue("text-align").contains(Expected))){
					  CustomReporter.errorLog("The label alignment for positive values for   '"+PositiveValuesNames.get(j).getText()+"' is NOT  to "+Expected+"'");
					  flag=false;
					  System.out.println(negativeValuesNames.get(j).getCssValue("text-align"));
				  }
				  
					
				}
				if(flag==true){
					status=true;
				}
		break;
		
		
		case "PositiveValuesAlignment":
			flag=true;
			for(int k=0;k<PositiveValuesDataValues.size();k++){
				
				 if(!(PositiveValuesDataValues.get(k).getCssValue("text-align").contains(Expected))){
					 CustomReporter.errorLog("The values alignment for positive values for   '"+PositiveValuesNames.get(k).getText()+"' is NOT  to "+Expected+"'");
					  flag=false;
					  System.out.println(negativeValuesNames.get(k).getCssValue("text-align"));
					  }
				  
					
				}
				if(flag==true){
					status=true;
				}
		break;
		
		case "NegativeValuesAlignment":
			flag=true;
			for(int m=0;m<negativeValuesDataValues.size();m++){
		
			  if(!(negativeValuesDataValues.get(m).getCssValue("text-align").contains(Expected))){
				  CustomReporter.errorLog("The values alignment for negative values for   '"+negativeValuesNames.get(m).getText()+"' is NOT  to "+Expected+"'");
				  flag=false;
				  System.out.println(negativeValuesDataValues.get(m).getCssValue("text-align"));
				}
			  
				
			}
			if(flag==true){
				status=true;
			}
		 break;
		 case "NegativeBarsAlignment":
			 flag=true;
				for(int p=1;p<negativeValuesDataBars.size();p++){
					
					 if(!(negativeValuesDataBars.get(p).getCssValue("text-align").contains(Expected))){
						 CustomReporter.errorLog("The bars alignment for negative values for   '"+negativeValuesNames.get(p).getText()+"' is NOT  to "+Expected+"'");
						  flag=false;
						  System.out.println(negativeValuesDataBars.get(p).getCssValue("text-align"));
						}
						
					}
					if(flag==true){
						status=true;
					}
			break;
			
			case "NegativeLabelAlignment":
				flag=true;
				for(int o=0;o<negativeValuesNames.size();o++){
					
					 if(!(negativeValuesNames.get(o).getCssValue("text-align").contains(Expected))){
						 CustomReporter.errorLog("The label alignment for negative values for   '"+negativeValuesNames.get(o).getText()+"' is NOT  to "+Expected+"'");
						  flag=false;
						  System.out.println(negativeValuesNames.get(o).getCssValue("text-align"));
						}
						
					}
					if(flag==true){
						status=true;
					}
			break;
		}
		return status;
	}
	/**This method selects behaviour filter by id passed
	 * 
	 * @param 
	 * @return 
	 * @author Amrutha Nair 
	 * @since :1 June 2015
	 */	
	public void selectBehaviorWithID(int id){
		Select dropdown = new Select(behaviour);
		dropdown.selectByIndex(id);
	}
}
		
