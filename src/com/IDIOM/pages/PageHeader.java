package com.IDIOM.pages;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.BaseClass;

/** Class element and methods common for all the pages.
 *  Can be use through out the application.
 * 
 * 
 * @author Deepen Shah
 * @since 29/03/2016
 *
 */
public class PageHeader{
	
	WebDriver driver;
	public PageHeader(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	
	@FindBy(xpath="//*[@class='ion ion-navicon-round'] | //*[@class='navigation-user-menu ng-isolate-scope']")
	public WebElement personMenu;
	
	@FindBy(xpath="//*[@ng-click='logout()'] | //*[@ng-click='navigation.logout()']")
	public WebElement logoutLink;

	@FindBy(xpath="//ul[@class='dropdown-list']/li[@ng-hide='!navigation.user.admin']")
	public WebElement adminAccess;
	
	//@FindBy(xpath="//ul[@class='dropdown-list']//*[@ng-show='navigation.user.logged_in']")
	//@FindBy(linkText="MY ACCOUNT")
	//@FindBy(xpath="//ul[@class='dropdown-list']/li[1]")
	//@FindBy(xpath="//li[@ng-show='navigation.user.logged_in']")
	//@FindBy(xpath="//ul[@class='dropdown-list']/li[@ng-hide='!navigation.user.admin']/preceding-sibling::li")
	@FindBy(css=".dropdown-list>li:nth-child(1)>a")
	public WebElement myAccount;
	
	/*@FindBy(xpath="//a[contains(text(),'My Account')]")
	public WebElement myAccount;*/
	
	@FindBy(xpath="//div[@ng-click='navigation.toggleMegaMenu()']")
	public WebElement megaMenuIcon;
	
	@FindBy(xpath="//span[@class='menu-icon opened']")
	public WebElement megaMenuOpen;
	
	@FindBy(xpath="//div[@class='audiences__select__name ng-binding']")
	public WebElement selectedAudience;
	
	@FindBy(xpath="//span[@class='pointer ng-binding']")
	public WebElement projectName;
	
	@FindBy(xpath="//div[@class='navigation-logo']/img")
	public WebElement idiomLogo;
	
	@FindBy(xpath="//img[@ng-click='navigation.goProjectHome()']")
	public WebElement clientLogo;
	
	@FindBy(xpath="//ul[@class='dropdown-list']/li/a[translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='help center']")
	public WebElement helpCenter;
	
	@FindBys(@FindBy(xpath="//*[@class='audiences__select__option ng-scope']/*[@class='audiences__select__option__name ng-binding']"))
	public List<WebElement> audienceList;
	
	@FindBy(xpath="//*[@class='audiences__select__ticks']")
	public WebElement audienceDropDownLink;
	
	@FindBy(id="loading-bar-spinner")
	public WebElement loadingSpinner;
	
	@FindBy(xpath="//ul[@class='subnav']/li[( starts-with(@ng-show,'whereAmI') OR starts-with(@ng-class,'whereAmI') ) and not(contains(@class,'ng-hide'))]")
	public WebElement getPageTitle;
	
	@FindBy(xpath="//ul[@class='subnav']/li[( starts-with(@ng-show,'whereAmI') OR starts-with(@ng-class,'whereAmI') ) and not(contains(@class,'ng-hide'))]/a")
	public WebElement mediaRankerPageTitle;
	
	@FindBy(xpath="//div[@class='navigation__client-project']//div[@ng-show='navigation.shouldShowClientBadge()']")
	public WebElement projectNameAlongWithLogo;
	
	
	@FindBys(@FindBy(xpath="//ul[@class='dropdown-list']/li[@ng-hide='!navigation.user.admin']"))
	public List<WebElement>  adminAccessPresent;
		
	String strLinkContainsInMegaMenu = "//span[@class=''project-group__link__name ng-binding'' and text()=''{0}'']";
	public String strAudienceContains = "//div[text()=''{0}'']";
	
	String strBaseUrl="";
	public static final String PROJECT_HOME_PAGE="/home/dashboard";
	
	
	@FindBys(@FindBy(xpath="//ul[@class='dropdown-list']/li"))
	public List<WebElement> linksInPersonMenu;
	
	public String selectedAudienceName = "//*[@class=''audiences__select'']/*[@class=''audiences__select__content'']/*[text()=''{0}'']";
	
	public String strLinkContains = "//div[@class=''sitemap--mega-menu'']//span[text()=''{0}'']";
	
	public String strBoxHeaderLabel = "//div[@class=''sitemap--mega-menu'']//h3[contains(.,''{0}'')]";
	
	/** This method will perform various action
	 *  <p> <b>Options</b> 'logout','adminaccess','myaccount','idiomlogo','helpcenter'</p>
	 * 
	 * @param strAction - String from above options
	 * @throws Exception
	 * @author Deepen Shah
	 * @since 29/03/2016
	 */
	public void performAction(String strAction) throws Exception{
		Actions action = new Actions(driver);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		switch(strAction.toLowerCase()){
			case "logout":
					action.moveToElement(personMenu).build().perform();
					Thread.sleep(2000);
					//action.moveToElement(logoutLink).click().perform();
					jse.executeScript("arguments[0].click();", logoutLink);
				break;
			
			case "adminaccess":
					personMenu.click();
					Thread.sleep(2000);
					//action.moveToElement(adminAccess).click().perform();
					jse.executeScript("arguments[0].click();", adminAccess);
				break;
				
			case "myaccount":
					personMenu.click();
					String myAccountURL = myAccount.getAttribute("href");
					System.out.println(myAccountURL);
					driver.navigate().to(myAccount.getAttribute("href"));
				break;
				
			case "idiomlogo":
				idiomLogo.click();
				break;
				
			case "helpcenter":				
				personMenu.click();
				Thread.sleep(2000);
				//driver.navigate().to(helpCenter.getAttribute("href"));
				jse.executeScript("arguments[0].click();", helpCenter);
				break;
				
			case "adminaccesspresent":
				personMenu.click();
				Thread.sleep(2000);
				break;
			case "clientlogo":
				clientLogo.click();
				break;
				
			case "audiencedropdown":
				audienceDropDownLink.click();
				break;
			case "projecthomepage":
				projectName.click();
				break;
				
		}
	}
	
	/** 
	 * Options: 'projecthomepage'
	 * @param strPage
	 * @author Deepen Shah
	 * @since 26 April 2016
	 */
	public void navigateTo(String strPage){
		strBaseUrl = driver.getCurrentUrl().substring(0, driver.getCurrentUrl().indexOf('#')+1);
		System.out.println("Base url is " + strBaseUrl);
		
		switch(strPage.toLowerCase()){
		
		case "projecthomepage": driver.navigate().to(strBaseUrl+PROJECT_HOME_PAGE);
								break;
		}
	}
	
	/**Options: 'Project Description','Success Metrics','Audience Definition','Profile','Webnet','HVA','Pathing',
	 * 'Digital Ranker','TV Ranker'  
	 * @param strPageName
	 * @since 27-April-2016
	 * @author Shailesh Kava
	 * @throws Exception 
	 */
	
	public boolean megaMenuLinksClick(String strPageName) throws Exception{
	  boolean verifyMegaMenuOpen = false;
		megaMenuIcon.click();
		System.out.println("Clicked on mega menu");
		Thread.sleep(2000);
		
		if(this.isVisible("megamenu_open")){
		  verifyMegaMenuOpen = true;
			System.out.println("Mega menu is opened");
			driver.findElement(By.xpath(MessageFormat.format(strLinkContainsInMegaMenu, strPageName))).click();
		}else{
			System.out.println("Failed to open mega menu");
		}
		return verifyMegaMenuOpen;
	}
	
	/**options: 'header_megamenu_icon','megamenu_open'
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
				case "header_megamenu_icon": 
					wait.until(ExpectedConditions.visibilityOf(megaMenuIcon));
					break;
					
				case "megamenu_open": 
					wait.until(ExpectedConditions.visibilityOf(megaMenuOpen));
					break;
					
				case "audiencedropdown":
					wait.until(ExpectedConditions.visibilityOf(selectedAudience));
					break;
					
				case "projectname":
					wait.until(ExpectedConditions.visibilityOf(projectName));
					break;
					
				case "client_logo":
					wait.until(ExpectedConditions.visibilityOf(clientLogo));
					break;
					
				case "adminaccess"://added by Sunil //modified by Abhishek
					Actions action3 = new Actions(driver);
					action3.moveToElement(personMenu).build().perform();
					Thread.sleep(2000);
					wait.until(ExpectedConditions.visibilityOf(adminAccess));
					break;					
					
				case "logoutlink":
					Actions action = new Actions(driver);					
					action.moveToElement(personMenu).build().perform();
					Thread.sleep(2000);
					wait.until(ExpectedConditions.visibilityOf(logoutLink));
					break;
					
				case "myaccount":
					Actions action2 = new Actions(driver);
					action2.moveToElement(personMenu).build().perform();
					Thread.sleep(2000);
					wait.until(ExpectedConditions.visibilityOf(myAccount));
					break;
					
				case "idiomlogo":
					wait.until(ExpectedConditions.visibilityOf(idiomLogo));
					break;
					
				case "personmenu":
					wait.until(ExpectedConditions.visibilityOf(personMenu));
					break;
					
				case "megamenuopenfast":
					wait.withTimeout(3, TimeUnit.SECONDS);
					wait.until(ExpectedConditions.visibilityOf(megaMenuOpen));
					break;
					
			}
		}catch(Exception e){
			bStatus = false;
		}finally{
			wait.withTimeout(20, TimeUnit.SECONDS);
		}
		
		return bStatus;
	}
	
	/** Method to select drop down from the header
	 * 
	 * @param strAudience - String audience name
	 * @return boolean true/false 
	 * @author Deepen Shah
	 * @since 03 May 2016
	 */
	public boolean selectAudienceFromDropDown(String strAudience){
		boolean bStatus=true;
		
		if(selectedAudience.getText().trim().equalsIgnoreCase(strAudience)){
			System.out.println("Current selection " + selectedAudience.getText());
		}else{
			selectedAudience.click();
			driver.findElement(By.xpath(MessageFormat.format(strAudienceContains, strAudience))).click();
		}
		
		return bStatus;
	}
	
	/**  Method to get name of project from the header
	 * 
	 * @return String
	 * @author Deepen Shah
	 * @since 03 May 2016
	 */
	public String getProjectName(){
		return projectName.getText().trim();
	}
	
	/**  Method splits the items with seperator 'comma' which we are passing
	 * 
	 * @return String []
	 * @author Amrutha Nair
	 * @since 18 May 2016
	 */
	public String[]  getSplittedItems(String strToBeSplitted){
		String [] strItems=strToBeSplitted.split(",");
		return strItems;
		
	}
	
	
	/**  Method verifies whether the selected audience is coming as selected  in the drop down
	 * 
	 * @return boolean
	 * @author Amrutha Nair
	 * @since 18 May 2016
	 */
	public boolean verifySelectedAudienceInDropDown(String strAudienceName)
	{
		Boolean status=true;
		WebDriverWait wait = new WebDriverWait(driver, 2);
		try{
							
		  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MessageFormat.format(selectedAudienceName, strAudienceName)))));					
		 
		
		}catch(Exception e){
			status = false;
		}
		
		return status;
	}	
	
	/**  Method returns the audiences in drop down
	 * 
	 * @return boolean
	 * @author Amrutha Nair
	 * @since 18 May 2016
	 */
	public ArrayList<String> returnAudiencesInDropDown(){
		ArrayList<String> audineces = new ArrayList<>();
		for(WebElement audienceElement:audienceList){
			audineces.add(audienceElement.getText().trim());
		}
		return audineces; 
	}
	
	/** Get the name of selected audience. 
	 *  
	 * @return String audience name
	 * @author Deepen Shah
	 * @since 24 May 2016
	 * 
	 */
	public String getSelectedAudienceName(){
		return selectedAudience.getText().trim();
	}
	
	/**options: 'header_megamenu_icon','megamenu_open'
	 * @param strCase - is an xpath to reach to a particular element. strValue is name of the element e.g. "Media Ranker"
	 * @return boolean True/False
	 * @author Rohan Macwan
	 * @since 17 June 2016
	 * 
	 */
	public boolean isVisible(String strCase,String strValue){
		boolean bStatus = true;
		
		WebDriverWait wait = new WebDriverWait(driver, 2);
		try{
			switch(strCase.toLowerCase()){
									
				case "link": 
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MessageFormat.format(strLinkContains, strValue)))));
					break;
					
				case "headertext":
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MessageFormat.format(strBoxHeaderLabel, strValue)))));
					break;
					
			}
		}catch(Exception e){
			bStatus = false;
		}finally{
			wait.withTimeout(20, TimeUnit.SECONDS);
		}
		
		return bStatus;
	}
	

	/**This method will return links name from person menu
	 * 
	 * @return: List<String>
	 * @throws Exception
	 * @author Shailesh Kava
	 * @since 20-June-2016
	 */
	public List<String> getLinksFromPersonMenu() throws Exception{
		List<String> linksUnderPersonMenu = new ArrayList<String>();
		
		personMenu.click();
		Thread.sleep(2000);
		if(linksInPersonMenu.size() >= 1){
			for(WebElement links: linksInPersonMenu){
				System.out.println("inner text=="+links.getAttribute("innerText"));
				linksUnderPersonMenu.add(links.getAttribute("innerText").trim());
			}
		}
		
		return (List<String>) linksUnderPersonMenu;
	}
	
	/**This method checks whether element is present in the page
	 * 
	 * @return: List<String>
	 * @throws Exception
	 * @author Amrutha NAir
	 * @since 21-June-2016
	 */
	public boolean verify_ElementExist(String strCase){
		boolean status=false;
		switch(strCase){
		
		case "AdminAccess":
			 status=BaseClass.rm.webElementExists(adminAccessPresent);
			 break;
		}
		return status;
		
	}
}
