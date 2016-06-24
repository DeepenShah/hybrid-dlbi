package com.IDIOM.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Deepen Shah
 * @since 09 May 2016
 *
 */
public class HelpCenterPage {

	WebDriver driver;
	public HelpCenterPage(WebDriver driver){
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	
	@FindBy(linkText="FAQs")
	public WebElement faqTab;
	
	@FindBy(linkText="Definitions")
	public WebElement definitionTab;
	
	/**This method is used to check visibility of element
	 * 
	 * @param strWebElement
	 * @return boolean
	 * 
	 * @author Shailesh Kava
	 * @since 17 June 2016 
	 */
	public boolean isVisible(String strWebElement){
		boolean bVisible = true;
		
		WebDriverWait wait = new WebDriverWait(driver, 0);
		
		switch(strWebElement.toLowerCase().trim()){
		case "faqtab":
			wait.until(ExpectedConditions.visibilityOf(faqTab));
			break;
		}
		
		return bVisible;
	}
}
