package com.IDIOM.pages;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

/** Page class for Dashboard screen.
 *  It will have all the element and methods to interact with project dashboard page.
 * 
 * @author Deepen Shah
 * @since 25 April 2016
 *
 */
public class ProjectDashboardPage {
	
	WebDriver driver;
	public ProjectDashboardPage(WebDriver driver){
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	@FindBy(xpath="//header[@class='project-header layout horizontal ng-scope']/div/h2")
	public WebElement projectName;
	
	@FindBy(xpath="//header[@class='project-header layout horizontal ng-scope']/div/p")
	public WebElement projectDescription;
	
	@FindBy(xpath="//div[contains(@class,'navigation-logo')]")
	public WebElement idiomLogo;
	
	@FindBy(xpath="//button[@class='project-header__toggle']")
	public WebElement showHiddenLinksBtn;
	
	@FindBy(xpath="//button[@class='project-header__toggle']/span")
	public WebElement hiddenLinksCount;

	@FindBys(@FindBy(xpath="//div[@class='sitemap--project-home']//li[@class='project-group__link ng-scope']//span[@class='project-group__link__name ng-binding']"))
	public List<WebElement> listLinks;
	@FindBy(xpath="//*[@ng-click='logout()']")
	private WebElement right_Logout;

	@FindBy(xpath="//*[@class='ion ion-navicon-round']")
	private WebElement MenuIcon;
	
	public String strLinkContains = "//main//main//span[text()=''{0}'']";

	public String strProjectName="//*[@class=''project-header__meta'']/h2[text()=''{0}'']";
	
	public String strBoxHeaderLabel="//div[@class=''sitemap--project-home'']//h3[contains(.,''{0}'')]";
	public String strHideLink = "//main//main//span[text()=''{0}'']/parent::li/span[@class=''project-group__link__hide'']";
	public String strHideLink2 = "//main//main//span[text()=''{0}'']/parent::li//span[starts-with(@class,'project-group__link')]";
	
	/** This method will help to click any link from the dashboard.
 	 * 
	 * @param strPage - String page name For e.g. 'Success Metrics'
	 * @author Deepen Shah
	 * @since 25/04/2016
	 */
	
	public void navigateTo(String strPage){
		System.out.println(MessageFormat.format(strLinkContains, strPage));
		driver.findElement(By.xpath(MessageFormat.format(strLinkContains, strPage))).click();
	}
	
	/** This method will navigate to site pages using 'Actions class', because sometimes IE get hangs on simple clicks in that case wee need to use actions class .
 	 * 
	 * @param strPage - String page name For e.g. 'Success Metrics'
	 * @author Shailesh Kava
	 * @since 20/05/2016
	 */
	public void navigateToByActionClass(String strPage){
		System.out.println("Navigate by ACTION");
		System.out.println(MessageFormat.format(strLinkContains, strPage));
		WebElement we = driver.findElement(By.xpath(MessageFormat.format(strLinkContains, strPage)));
		Actions action = new Actions(driver);
		action.moveToElement(we).click().perform();
		System.out.println("ACTION performed");
	}
	
	
	/** This method will validate links exists in Project dash board .
 	 * 
	 * @param linkname - String page name For e.g. 'Success Metrics'
	 * @author Abhishek Deshpande
	 * @since 26/04/2016
	 */
	
	public boolean verifyLinkExist(String linkName){
		boolean bReturnValue = false;
		if (driver.findElement(By.xpath(MessageFormat.format(strLinkContains, linkName))).isDisplayed()){
			bReturnValue = true;
		}
		return bReturnValue;
	}
	
	
	/**
	 * @param strElement
	 * @return
	 * @author Deepen Shah
	 * @since 27 April 2017
	 */
	public boolean isVisible(String strCase,String strValue){
		boolean bStatus = true;
		
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.ignoring(NoSuchElementException.class);
		
		try{
			switch(strCase.toLowerCase()){
				case "link": 
							wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MessageFormat.format(strLinkContains, strValue)))));
							break;
				case "projectname":
							wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MessageFormat.format(strProjectName, strValue)))));
							break;
				case "headertext": //Added by Rohan Macwan on 15-June-2016
							wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MessageFormat.format(strBoxHeaderLabel, strValue)))));
							break;
							
				case "showhiddenlinkslabel": //Added by Deepen Shah 16 Jun 2016
					wait.until(ExpectedConditions.visibilityOf(showHiddenLinksBtn));
					break;
										
			}
		}catch(Exception e){
			bStatus = false;
		}
		
		return bStatus;
	}
	
	
	/**
	 * @return
	 * @author Deepen Shah
	 * @since 02 Jun 2016
	 */
	public String getProjectName(){
		return projectName.getText().trim();
	}
	
	/**
	 * @return
	 * @author Deepen Shah
	 * @since 02 Jun 2016
	 */
	public String getProjectDescription(){
		return projectDescription.getText().trim();
	}
	
	/** This method is used to perform some action on elements available in project dash board
	 * 
	 * @param eleName - String case. From clickidiomlogo,showhiddenlinks
	 * @author Abhishek Deshpande
	 * @since 09 JUNE 2016
	 */
	public void performActionOnElement(String eleName){
		switch(eleName.toLowerCase()){

			case "clickidiomlogo":
				idiomLogo.click();
				break;
				
			case "showhiddenlinks": showHiddenLinksBtn.click();
				break;
			case "logout":
				MenuIcon.click();
				right_Logout.click();
			break;	
		}		
	}
	
	/** Method to hide the link
	 * 
	 * @param strLinkName
	 * @author Deepen Shah
	 * @since 16 Jun 2016
	 */
	public void hideLink(String strLinkName){
		
		
		WebElement pageLink = driver.findElement(By.xpath(MessageFormat.format(strLinkContains, strLinkName)));
		WebElement hideLink = driver.findElement(By.xpath(MessageFormat.format(strHideLink, strLinkName)));
		Actions action = new Actions(driver);
		
		action.moveToElement(pageLink).build().perform();
		
		/*
		 * String str = MessageFormat.format("//main//main//span[text()=''{0}'']/parent::li", strLinkName);
		 * System.out.println("Inner html " + driver.findElement(By.xpath(str)).getAttribute("innerHTML"));		
		 * System.out.println(driver.findElements(By.xpath(MessageFormat.format(strHideLink2,strLinkName))).size());
		 * hideLink.click();
		 * */
		
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", hideLink);
		
	}
	
	/** It will return hidden link count 
	 * @return
	 * @author Deepen Shah
	 * @since 16 Jun 2016
	 */
	public int getHiddenLinksCount(){
		
		return Integer.parseInt(hiddenLinksCount.getText());
	}
	
	/**This function will return active link names
	 * 
	 * @return arrayList
	 * @author Shailesh Kava
	 * @since 16-June-2016
	 */
	public ArrayList<String> getActiveLinksName(){
		List<String> arrlinksName = new ArrayList<String>();
		
		for(WebElement link:listLinks){
			arrlinksName.add(link.getText().trim());
		}
		
		return (ArrayList<String>) arrlinksName;
	}
}
