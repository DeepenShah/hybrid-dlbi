/** 
 * HelpAndFAQs
 * List Of Methods:
   performAction(String strAction): This method is used perform action on a particular element. e.g clicking of person icon at the top
   isVisible(String strElementValue): This method is used to check the visibility of a particular element
 */

package com.IDIOM.pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
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
import java.text.MessageFormat;
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

public class HelpAndFAQs {

	WebDriver driver;
	public HelpAndFAQs(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	
	@FindBy(xpath="//*[@class='ion ion-navicon-round'] | //*[@class='navigation-user-menu ng-isolate-scope']")
	public WebElement personMenu;
	
	@FindBy(xpath="//ul[@class='dropdown-list']/li/a[translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='help center']")
	public WebElement helpCenter;
	
	@FindBy(xpath="//a[text()='FAQs']")
	public WebElement FAQsTab;
	
	@FindBy(xpath="//a[text()='Definitions']")
	public WebElement DefinitionsTab;
	
	@FindBy(xpath="//li[@class='active']//a[text()='FAQs']")
	public WebElement FAQs_ActiveTab;
	
	@FindBy(xpath="//li[@class='active']//a[text()='Definitions']")
	public WebElement Definitions_ActiveTab;
	
	@FindBy(xpath="//div[@class='site']//div[@class='site-width layout horizontal']")
	public WebElement ActiveTabContent;
	
	
	/** This method will perform various action
	 *  <p> <b>Options</b> 'idiomlogo','helpcenter'</p>
	 * 
	 * @param strAction - String from above options
	 * @throws Exception
	 * @author Rohan Macwan
	 * @since 21/06/2016
	 */
	public void performAction(String strAction) throws Exception{
		Actions action = new Actions(driver);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		switch(strAction.toLowerCase()){
			
				
			case "helpcenter":				
				personMenu.click();
				Thread.sleep(2000);
				//driver.navigate().to(helpCenter.getAttribute("href"));
				jse.executeScript("arguments[0].click();", helpCenter);
				break;
				
			case "faqstab":
				FAQsTab.click();
			    break;
				
			case "definitionstab":
				DefinitionsTab.click();
			    break;
		}
	}
	
	/** Method is to check visibility of different element.
	 *  Add more case whenever required and add its switch value in below options.
	 * 
	 * <p>  <b>Options:</b> 'definitionsactivetab', 'faqsactivetab'
	 * </p>
	 * 
	 * @param strElement - String case to verify visibility. Use value from list above.
	 * @param strElementValue TODO
	 * @author Rohan Macwan
	 * @since 21 June 2016
	 * 
	 */
	public boolean isVisible(String strElementValue){
		boolean bStatus = true;
		
		WebDriverWait wait = new WebDriverWait(driver, 2);
		try{
			switch(strElementValue.toLowerCase()){
				case "faqsactivetab": 
					  wait.until(ExpectedConditions.visibilityOf(FAQs_ActiveTab));
					  break;

				case "definitionsactivetab":					
					  wait.until(ExpectedConditions.visibilityOf(Definitions_ActiveTab));
					  break;
			}
		}catch(Exception e){
			bStatus = false;
		}
		
		return bStatus;
	}
	
}
