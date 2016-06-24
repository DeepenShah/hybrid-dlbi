/** 
 * Analyse_Pathing_Page
 * List Of Methods:
func_ElementExist(String elementName): This method is used to check the existence of provided web element of Audience_pathing page
func_ClickElement(String element): his method is used to click on an element in analyze page
func_CaptureList(String Case, WebDriver driver): This method collects the list of values depending on the condition in arraylist
func_DoubleClickAction(String elementName, WebDriver driver): This method does double click on the element which is provided as input
func_expectedColors(String element, String expectedcolor,WebDriver driver): This method sets status = false when expected color not matches with any clicked path(Please use when comparing single color)
func_pathingRightSideVerifyBGColor_Probabilities:This method sets status = false if (border color of selected path and probabilities are not in
												 sequence) or (same category not contains same border color)
func_returnElementValue(String element, WebDriver driver): This method will return name of provided element
func_compareTotalSegmentsForDiffBehaviors(WebDriver Driver): This method changes value in behavior dropdown and compares total segment count for different behaviors, this method will set status = false if segment count matches for any behavior.
func_onHover(String element, WebDriver driver): This method perform hover on provided web element
func_countOfSelectedPaths(String element, WebDriver driver): This method perform hover on provided web element
func_compareBGColorCategoryWithSubcategories(WebDriver driver): This method will compare BG color of each category with its sub category
func_verifySumOfSubCategory(WebDriver driver): This method will checks the sum of sub category and it will verify sub shuould be = 100%
 */

package com.IDIOM.pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Finder;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import com.reports.CustomReporter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import common.BaseClass;
import common.ReusableMethods;

public class Analyse_Pathing_Page {

	WebDriver driver;
	public Analyse_Pathing_Page(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
		
	}
	
	
	@FindBy(xpath="//*[@class='selected-client dropdown-parent']")
	private WebElement right_TopClient;
	
	@FindBy(xpath="//*[@ng-click='logout()']")
	private WebElement right_Logout;

	@FindBy(xpath="//*[@class='ion ion-navicon-round']")
	private WebElement MenuIcon;
	
	@FindBys(@FindBy(xpath="//*[@class='profile-container']"))
	private List<WebElement> profile_Container;
	
	//Pathing xpaths
	@FindBy(xpath="//a[text()='Pathing']")
	private WebElement pathing_tab;

	@FindBys(@FindBy(xpath="//div[@class='label-select']/select[@id='behaviors']/option"))
	private List<WebElement> list_leftTop_FlightOption;
	
	@FindBy(xpath="//div[@class='path-lock']/span[@class='ion ion-unlocked']")
	public WebElement unlockIcon;
	
	@FindBy(xpath="//div[@class='path-lock']/span[@class='ion ion-locked']")
	public WebElement lockIcon;
	
	@FindBy(xpath="//*[name()='svg']/*[name()='g']/*[name()='path'][3]")
	public WebElement innerCircleOfWheelChart;
	
	@FindBy(css="g#container>path:nth-child(6)")
	public WebElement doubleClickElement;
	
	@FindBys(@FindBy(xpath="//*[name()='svg']/*[name()='g']/*[name()='path' and contains(@style,'opacity: 1;')]"))
	private List<WebElement> listHighlightedPath;

	@FindBys(@FindBy(xpath="//ul[@class='subnav']//li[not(contains(@class,'ng-hide'))]"))
	private List<WebElement> pathing_secondaryNavigation;
	
	@FindBys(@FindBy(xpath="//div[@id='pathing-chart']"))
	public List<WebElement> pathing_chart;
	
	@FindBy(xpath="//*[name()='svg']/*[name()='g']/*[name()='path'][3]")
	public WebElement pathing_selectPathOnWheel;
	
	@FindBy(xpath="//*[name()='svg']/*[name()='g']/*[name()='path'][4]")
	private WebElement pathing_selectAnotherPathOnWheel;
	
	@FindBys(@FindBy(xpath="//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-')]"))
	public List<WebElement> list_probabilities;
	
	@FindBy(xpath="//div[@id='pathing-chart']/div[@class='path-percentage']/span[@class='path-percentage-value ng-binding']")
	private WebElement percentageInWheelChart;
	
	@FindBys(@FindBy(xpath="//div[@id='pathing-chart']/div[@class='path-percentage']/span[@class='path-percentage-value ng-binding']"))
	private List<WebElement> percentage_InWheelChart;
	
	@FindBy(xpath = "//div[@id='path-details']/div[@class='type-total flex-3 layout horizontal ng-scope']/h3")
	private WebElement percentageInProbability;
	
	@FindBys(@FindBy(xpath = "//div[@id='path-details']/div[@class='type-total flex-3 layout horizontal ng-scope']/h3"))
	private List<WebElement> percentage_InProbability;
	
	@FindBys(@FindBy(xpath="//select[@id='behaviors']/option"))
	public List<WebElement> list_behaviours;
	
	@FindBy(xpath="//select[@id='behaviors']")
	private WebElement behavioursDropDown;
	
	@FindBys(@FindBy(xpath="//*[name()='svg']/*[name()='g']/*[name()='path']"))
	private List<WebElement> listPathElement;
	
	@FindBy(xpath="//div[@id='pathing-main']/span[@class='arrow-marker ion ion-arrow-down-c']")
	private WebElement downArrowOnWheelChart;
	
	@FindBy(xpath="//div[@id='pathing-main']/span[@class='arrow-marker ion ion-arrow-up-c']")
	private WebElement upArrowOnWheelChart;
	
	@FindBy(xpath="//div[@class='toggle self-center']/div[@class='toggle-button']")
	private WebElement pre_toggle;
	
	@FindBy(xpath="//div[@class='toggle-wrapper layout horizontal']/span[@class='label self-center active']")
	private WebElement prePost_toggleVal;
	
	@FindBys(@FindBy(xpath="//div[@class='layout horizontal tab-content ng-scope']//li[@class='layout horizontal justified ng-scope']/span[@class='flex-1 ng-binding']"))
	private List<WebElement> right_listPercentageforSelectedProbability;
	
	@FindBys(@FindBy(xpath="//*[@class='admin-access']"))
	private List<WebElement> AdminAccessPresent;
	
	@FindBy(xpath="//*[@class='admin-access']")
	private WebElement AdminAccess;
	
	@FindBys(@FindBy(xpath="//*[@class='block flex-2 pathing-block']"))
	private List<WebElement> PathingPresent;
	
	@FindBy(xpath="//div[@class='toggle self-center']")
	private WebElement pre_post_toggleButton;
	
	////////////IDIOM 2.3//////////
	
	@FindBy(xpath="//*[name()='svg']")
	public WebElement pathingWheel;
	
	@FindBy(xpath ="//*[@class='block block--clear error-unicorn layout horizontal ng-scope']//h1[contains(text(),'Audience too narrow')]")
	public WebElement audienceTooNarrow;
	
	@FindBy(xpath ="//*[@class='block block--clear error-unicorn layout horizontal ng-scope']//a[contains(text(),'adjust your selections')]")
	public WebElement adjustyourselections;
	

	@FindBy(xpath="//button[@class='btn btn-muted export ng-scope']")
	public WebElement exportData_button;

	@FindBy(xpath ="//*[@class='site-width layout horizontal']//*[@id='behaviors']")
	public WebElement behaviourDropDown;

	@FindBy(xpath="//button[@class='audience-details-toggle']")
	public WebElement showAudienceBtn;
	
	/**
	* Options: 'pathing_wheel'
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
			case "pathing_wheel": 
				wait.until(ExpectedConditions.visibilityOf(pathingWheel));
				break;
			case "behaviourdropdown":
				wait.until(ExpectedConditions.visibilityOf(behaviourDropDown));
				break;
									
		}
	}catch(Exception e){
		bStatus = false;
	}
	
	return bStatus;
	}
	////////////IDIOM 2.3 - END//////////
	
	/** func_ElementExist(String elementName)
	* This method is used to check the existence of provided web element of Audience_pathing page
	* Created By: Abhishek Deshpande
	* Created On: 04 Aug 2015
	* Modified By | Description of Modification:Amrutha Nair|Modified Reusable method call and case statement
	*/		
		
	public boolean func_ElementExist(String elementName){
			boolean found=false;
			switch (elementName) {
			
			case "flight_options":
				found = BaseClass.rm.webElementExists(list_leftTop_FlightOption);
			break;
			
			case "ProfilePage":
				found=BaseClass.rm.webElementExists(profile_Container);
			break;
			
			case "unlockIcon":
				found = BaseClass.rm.elementExists(unlockIcon);
			break;

			case "pathing_chart":
				found = BaseClass.rm.webElementExists(pathing_chart);
			break;
			
			case "percentage_wheelChart":
				found = BaseClass.rm.webElementExists(percentage_InWheelChart);
			break;
			
			case "percentage_probability":
				found = BaseClass.rm.webElementExists(percentage_InProbability);
			break;
			
			case "probabilities":
				found = BaseClass.rm.webElementExists(list_probabilities);
			break;
			
			case "downArrow":
				found = BaseClass.rm.elementExists(downArrowOnWheelChart);
			break;
			
			case "upArrow":
				found = BaseClass.rm.elementExists(upArrowOnWheelChart);
			break;	
			
			case "pre_toggle":
				found = BaseClass.rm.elementExists(pre_toggle);
			break;
			
			case "lockIcon":
				found = BaseClass.rm.elementExists(lockIcon);
			break;	
			
			case "Admin Access Present":	
				MenuIcon.click();
				found=BaseClass.rm.webElementExists(AdminAccessPresent);
			break;
			
			case "Pathing block":
				found=BaseClass.rm.webElementExists(PathingPresent);
				break;
				
			
			
			}
			return found;
		}
	
	/** func_ClickElement(String element)
	* This method is used to click on an element in analyse page
	* Created By: Abhishek Deshpande | Created On:04 Aug 2015
	* Modified By | Modified Date: | Description of Modification:
	*/	
	public void func_ClickElement(String element){
		switch (element){
		
		case "Logout":
			MenuIcon.click();
			right_Logout.click();
		break;	
		
		case "pathing_tab":
			pathing_tab.click();
		break;
		
		case "pathing_selectPathonWheel":
			pathing_selectPathOnWheel.click();
		break;
		
		case "LockIcon":
			lockIcon.click();
		break;
		
		case "selectAnotherPathOnWheel":
			pathing_selectAnotherPathOnWheel.click();
		break;
		
		case "prePost_toggle":
			pre_post_toggleButton.click();
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
	
	
	/** func_CaptureList(String Case, WebDriver driver)
	*This method collects the list of values depending on the condition in arraylist
	*Created By: Shailesh Kava | Created On: 11 Sep 2015
	*Modified By | Modified Date: | Description of Modification: 
	*/
	 
	public ArrayList<String> func_CaptureList(String Case) throws Exception{

			ArrayList<String> values = new ArrayList<String>();
			switch (Case) {
			
			case "getSecondaryNavigation":
				for(WebElement filter: pathing_secondaryNavigation){
					values.add(filter.getText());
				}
			break;
			
			case "pathing_getBGColorForSelectedPathonWheelChart":
				for(WebElement filter: listHighlightedPath){
					values.add(filter.getCssValue("fill"));
				}
			break;
			case "pathing_getProbabilityValues":
				//count of total probabilities at rightside
				int totalElements = list_probabilities.size();
				
				for(int i=0; i<= totalElements-1; i++){
					//clicking on each probability to get value and border color
					driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).click();
					Thread.sleep(1000);
					//String probabilityName = driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).getText();
					String probabilityBorderColor = driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).getCssValue("border-color");
					values.add(probabilityBorderColor);
				}
			break;
			
			case "listBehavirs":
				list_behaviours.size();
				
				for(WebElement listBehaviors: list_behaviours){
					values.add(listBehaviors.getText());
				}
				
			break;
			}
			return values;
			  
		  }
		
		
	/** func_DoubleClickAction(String elementName, WebDriver driver)	 * 
	*This method does double click on the element which is provided as input
	*Created By:Shailesh Kava | Created On:14 Sep 2015
	*Modified By | Modified Date: | Description of Modification: 
	 * @throws InterruptedException 
	*/	
	public void func_DoubleClickAction(String elementName,String browserName) throws InterruptedException{
					Actions action = new Actions(driver);		
					switch (elementName){
					
					case "dobleClickPath":
						action.doubleClick(doubleClickElement).doubleClick(doubleClickElement).build().perform();
					break;	
					}
		}
		

	/**func_expectedColors(String element, String expectedcolor,WebDriver driver) 	 * 
	*This method sets status = false when expected color not matches with any clicked path(Please use when comparing single color)
	*Created By:Shailesh Kava | Created On: 14 Sep 2015
	*Modified By | Modified Date: | Description of Modification: 
	*/	
	public boolean func_expectedColors(String element, String expectedcolor){
			
			boolean status = true;
			
			switch(element){
			case "selectedpathbydoubleclick":
				for(WebElement colors: listHighlightedPath){
					System.out.println("found color ["+colors.getCssValue("fill")+" expected color]"+expectedcolor);
					if(!colors.getCssValue("fill").contains(expectedcolor)){
						status = false;
						break;
					}
				}
			break;
			
			case "lockIconColor":
				
				if(!lockIcon.getCssValue("color").contentEquals(expectedcolor)){
					status=false;
				}
				
			break;	
			}
			
			return status;
		}
	/**func_pathingRightSideVerifyBGColor_Probabilities(String element, WebDriver driver) 	 * 
	*This method sets status = false if (border color of selected path and probabilities are not in
	* 					sequence) or (same category not contains same border color)
	*Created By: Shailesh Kava | Created On: 16 Sep 2015
	*Modified By | Modified Date: | Description of Modification: 
	*/	
	public boolean func_pathingRightSideVerifyBGColor_Probabilities(String element) throws Exception
		{
			boolean status = true;
			
			switch(element){
			
			case "Profile_rightSideVerifySameColorForSameCategory":
				
				//getting bg color in arrayList from selected path on wheel
				ArrayList<String> getBorderColorFromPathWheel = 
						func_CaptureList("pathing_getBGColorForSelectedPathonWheelChart");
				
				HashMap<String,String> hm=new HashMap<String,String>(); 
				
				list_probabilities.size();
				
				int i = 0;
				for(WebElement probabilities: list_probabilities){
						//getting BorderColor from existing arraylist
						String selectedPpathBorderColor = getBorderColorFromPathWheel.get(i);
						
						//Clicking on each probability at right panel
						driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).click();
						Thread.sleep(1000);
						
						//getting 
						String probabilityName = driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).getText();
						String probabilityBorderColor = driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).getCssValue("border-color");
						
						if(!selectedPpathBorderColor.contains(probabilityBorderColor)){
				        	CustomReporter.errorLog("colors are not matching with wheel "+probabilityBorderColor+"==="+selectedPpathBorderColor);
				        	status=false;
				        	break;
				        }else{
				        	//CustomReporter.log("colors are matching with wheel "+probabilityBorderColor+"==="+selectedPpathBorderColor);
				        	
				        	//Comparing bg color with stored probabilities in hashmap if we found same probability
							if(i>=1){
								
								//getting value from hashmap
								Set mapSet = (Set) hm.entrySet();
								
								Iterator mapIterator = mapSet.iterator();
								
								//runtime verifying value from hashmap table
								while(mapIterator.hasNext()){
									Map.Entry mapEntry = (Map.Entry) mapIterator.next();
									
									String key = (String) mapEntry.getKey();
							        String value = (String) mapEntry.getValue();
							        
							        if(key.contentEquals(probabilityName)){
							        	if(!value.contentEquals(probabilityBorderColor)){
							        		CustomReporter.errorLog("Same probability does not contains the same border color");
							        		status = false;
							        		break;
							        	}else{
							        		CustomReporter.log("Same probability has same color: "+key+":"+value+"==="+probabilityName+":"+probabilityBorderColor);
							        	}
							        }
							    }
							}
							hm.put(probabilityName, probabilityBorderColor);
				        }
					i++;		
				}
				
			break;	
			}
			
			return status;
		}
		
	/**func_returnElementValue(String element, WebDriver driver) 	 * 
	*This method will return name of provided element
	*Created By: Shailesh Kava | Created On: 16 Sep 2015 
	*Modified By | Modified Date: |Description of Modification: 
	*/	
	public String func_returnElementValue(String element){
			String elementName = null;
			
			switch(element){
			case "percentage_wheelChart":
				elementName = percentageInWheelChart.getText();
			break;
			
			case "percentage_probability":
				elementName = percentageInProbability.getText();
			break;
			
			case "prePost_toggleVal":
				elementName = prePost_toggleVal.getText();
			break;	
			}
			
			return elementName;
		}
		
	/**<p>This method changes value in behavior dropdown and compares total segment count for different behaviors, this method will set status = false if segment count matches for any behavior.<p>  
	*
	*@author Shailesh Kava | 
	*@since 21 Sep 2015
	*Modified By | Modified Date: | Description of Modification: 	
	*/	
	public boolean func_compareTotalSegmentsForDiffBehaviors() throws Exception{
			
			boolean status = true;
			
			//defining arrayList to store total segments available on different behaviors
			ArrayList<Integer> totalSegmentsInWheelChart = new ArrayList<Integer>();
			
			//List of values available in dropdown
			ArrayList<String> listBehaviours = func_CaptureList("listBehavirs");
			System.out.println("List behaviours ["+listBehaviours+"]");
			new Select(behavioursDropDown);
			
			//selecting values in drop down using listBehaviours arraylist
			for(String behaviours: listBehaviours){
				
				ReusableMethods.selectDropDrownValue(behavioursDropDown, behaviours);
				//select.selectByVisibleText(behaviours);
				Thread.sleep(10000);
			}
			System.out.println(totalSegmentsInWheelChart);
			//hashset to get unique values from List
			Set<Integer> uniqueList = new HashSet<>(totalSegmentsInWheelChart);
			System.out.println(uniqueList.size());
			System.out.println(totalSegmentsInWheelChart.size());
			//comparission for arraysize of unique values and and total values 
			if(uniqueList.size() != totalSegmentsInWheelChart.size()){
				status = false;
			}
			
			return status;
		}
		
	/**func_onHover(String element, WebDriver driver) 	 * 
	*This method perform hover on provided web element 
	*Created By: Shailesh Kava | Created On: 21 Sep 2015
	*Modified By | Modified Date:  | Description of Modification: 
	*/	
	public void func_onHover(String element){
			
			Actions action = new Actions(driver);
			
			switch(element){
				case "hoverOnSingleSegment":
					System.out.println("Hovering on wheel");
					action.moveToElement(innerCircleOfWheelChart).build().perform();
					System.out.println("Hovered on wheel");
				break;	
			}
			
		}
		
	/**func_countOfSelectedPaths(String element, WebDriver driver) 	 * 
	*This method perform hover on provided web element 
	*Created By: Shailesh Kava | Created On: 21 Sep 2015 
	*Modified By | Description of Modification: 
	*Modified On: 
	*/	
	public int func_countOfSelectedPaths(){
			
			int totalSelectedPathsInChart = 0;
			
				totalSelectedPathsInChart = listHighlightedPath.size();
				
			return totalSelectedPathsInChart;
	}
	
	/**func_compareBGColorCategoryWithSubcategories(WebDriver driver)
	 * This method will compare BG color of each category with its sub category 
	 * Created By: Shailesh Kava | Created Date: 24-Sep-2015
	 * Modified By: | Modified Date: 
	 */
	public boolean func_compareBGColorCategoryWithSubcategories() throws Exception{
		boolean bBGColorMatched = true;
		int i = 0;
		
		for(WebElement probabilities: list_probabilities){
			
			//click on Main category
			driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).click();
			
			String categoryName = driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).getText();
			
			String categoryBGColor = driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).getCssValue("background");
			
			int indexofSpecificChar = categoryBGColor.indexOf(")");
			
			//Get category bg color
			categoryBGColor = categoryBGColor.substring(0, indexofSpecificChar+1);
			
			String subCategoryBGColor = driver.findElement(By.xpath("//div[@class='layout horizontal tab-content ng-scope']//ul")).getCssValue("background");
			
			indexofSpecificChar = subCategoryBGColor.indexOf(")");
			
			//Get sub-category bg color
			subCategoryBGColor = subCategoryBGColor.substring(0, indexofSpecificChar+1); 
			
			//comparing both the bg colors
			if(!categoryBGColor.contentEquals(subCategoryBGColor)){
				CustomReporter.errorLog(categoryName+ " category bg color does not match with subcategoty bg color "+categoryBGColor+"==="+subCategoryBGColor);
				bBGColorMatched = false;
			}
			
			Thread.sleep(1000);
			
		i++;	
		}
		return bBGColorMatched;
	}
	
	/**func_verifySumOfSubCategory(WebDriver driver)
	 * This method will checks the sum of sub category and it will verify sub shuould be = 100% 
	 * Created By: Shailesh Kava | Created Date: 24-Sep-2015
	 * Modified By: | Modified Date: | Description of Modification: 
	 */
	
	public boolean func_verifySumOfSubCategory(){
		boolean bVerifySubCatGory = true;
		int i=0;
		
		for(WebElement probabilities: list_probabilities){
			
			String probabilityName=driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).getText();
			
			if(i>=1){
				driver.findElement(By.xpath("//div[@id='path-details']/div[@class='path-titles flex-2']/div[contains(@id,'path-tab-title-"+i+"')]/h3")).click();
			}
			
			double initialPercenageVal, finalPercentageVal = 0;
			
			for(WebElement subcategories: right_listPercentageforSelectedProbability){
				
				initialPercenageVal = finalPercentageVal;
				
				String subCategoryStr = subcategories.getText();
				
				//eliminating % character from end of string
				subCategoryStr = subCategoryStr.replace(subCategoryStr.substring(subCategoryStr.length()-1), "");   
				
				//converting string to double
				double percentageVal = Double.parseDouble(subCategoryStr);
				 
				finalPercentageVal = Math.round(initialPercenageVal+percentageVal);
				System.out.println(finalPercentageVal);
			}
			 
			 if(finalPercentageVal!=100.0 || finalPercentageVal!=100){
				 CustomReporter.errorLog("Sum of "+probabilityName+" is "+finalPercentageVal+"%");
				 bVerifySubCatGory = false;
			 }else{
				 CustomReporter.log("Sum of "+probabilityName+" is "+finalPercentageVal+"%");
			 }
		i++;	
		}
		
		return bVerifySubCatGory;
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
	
	/**<p>This methos is used to change all behaviour and get screenshot for all pathing wheel after that it will compare all pathing images with each other.</p>
	 * 
	 * @throws Exception
	 * @author Shailesh, Deepen
	 * @since 2 June 2016
	 */
	public boolean compareImageOfPathingWheelOfAvailableBehaviour() throws Exception{
		boolean bCompared = true;
		ArrayList<String> listBehaviours = func_CaptureList("listBehavirs");
		System.out.println(listPathElement.size());
		Screen screen = new Screen();
		File newSikuliFolder = new File("Sikuli");
		try{
			
			List<Pattern> allPattern = new ArrayList<>();
			newSikuliFolder.mkdir();
			
			for(int i=0; i<listBehaviours.size(); i++){
				System.out.println("selecting behavior ["+listBehaviours.get(i)+"]");
				ReusableMethods.selectDropDrownValue(behavioursDropDown, listBehaviours.get(i));
				
				//Visibility
				if(BaseClass.rm.webElementSync(BaseClass.pageHeader.loadingSpinner, "visibility"))
					BaseClass.rm.webElementSync("idiomspinningcomplete");
				
				isVisible("pathing_wheel");
					
				Point p = pathingWheel.getLocation();
				Dimension d = pathingWheel.getSize();
				
				File sc = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				BufferedImage bi = ImageIO.read(sc);
				BufferedImage screenshot = bi.getSubimage(p.x, p.y, d.width, d.height);
				ImageIO.write(screenshot,"PNG",sc);
				FileUtils.copyFile(sc, new File(newSikuliFolder.getAbsolutePath()+"/"+listBehaviours.get(i)+".png"));
				
			}
			File fDir = new File("Sikuli");
			File[] allImages = fDir.listFiles();
			
			for(int i=0;i<allImages.length;i++){
				Finder f = new Finder(allImages[i].getAbsolutePath());
				System.out.println("Matching file " + allImages[i].getAbsolutePath());
				
				for(int j=0;j<allImages.length;j++){
					if(i!=j){
						System.out.println("with " + allImages[j]);
						f.findAll(allImages[j].getAbsolutePath());
						
						if(f.hasNext()){
							CustomReporter.log("Following images are matched ["+listBehaviours.get(j)+"] with ["+listBehaviours.get(i)+"]");
							System.out.println("Fail: Match found");
							bCompared = false;
						}else{
							//CustomReporter.log(paramString);
							System.out.println("Not matched");
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			System.out.println("Finally");
			System.out.println("before delete=="+newSikuliFolder.exists());
			
			if(newSikuliFolder.exists())
				FileUtils.forceDelete(newSikuliFolder);
			
			
			System.out.println("after delete=="+newSikuliFolder.exists());
		}
		
		return bCompared;
	}
}