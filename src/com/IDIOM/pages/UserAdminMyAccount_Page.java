/**
func_getListofElementVal(String element) - This will return value of elements of provided list web element
func_onHover(String element) - This method is used to keep hover on any element
func_click(String element) - This method is used to click on any specified element
func_getText(String element) - This method is used to get text for any specified element
func_getList(String element) - This method is used to return list of web element available in specified listElement
func_elementExist(String element) - This function will check element is present with the help of reusable method(webelementexist())
func_browserBack() - Used to go back by browser back
func_GetLocalWebElement(String strWebElement) - This method is used to get private WebElement out of this class
func_getAssignedClietFromDB(String query, String dbInstance, String dbUser, String dbPass) - This method will return List of active clients which are assigned to the user
func_getTextOfElement(String element) - This method is used to get value of any provided element
 */

package com.IDIOM.pages;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.BaseClass;

public class UserAdminMyAccount_Page {

	WebDriver driver;
	public UserAdminMyAccount_Page(WebDriver driver){
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	@FindBy(xpath="//span[@class='ion ion-navicon-round']")
	private WebElement headerSandwithIcon;
	
	@FindBy(xpath="//div[@class='dropdown-box open']//ul[@class='dropdown-list']/li[contains(.,'My Account')]")
	private WebElement myAccountLink;
	
	@FindBy(xpath="//div[@class='me-details']//h1")
	private WebElement userName;
	
	@FindBy(xpath="//div[@class='me-details']/div/div[@class='me-role ng-scope']")
	private WebElement userClientAdminRole;
	
	@FindBy(xpath="//div[@class='me-details']//div[@class='me-role']")
	private WebElement userRole;//for super admin user
	
	@FindBy(xpath="//*[text()='CHANGE PASSWORD']")
	private WebElement changePassButton;
	
	@FindBy(xpath="//*[text()='RESEND CHANGE PASSWORD']")
	private WebElement resendPassButton;
	
	@FindBys(@FindBy(xpath="//div[@class='me-clients']//ul//li"))
	private List<WebElement> clientList;
	
	@FindBy(xpath="//div[@class='me-details']//p[contains(.,'@')]")
	private WebElement emailAddress;
	
	@FindBys(@FindBy(xpath="//div[@class='primary']//img[@class='logo']"))
	private List<WebElement> headerIdiomLogo;
	
	@FindBys(@FindBy(xpath="//div[@class='flex secondary']//span[text()='Profile Settings']"))
	private List<WebElement> profileSetting;
	
	@FindBys(@FindBy(xpath="//span[@class='question']//a[@title='Help']"))
	private List<WebElement> helpIcon;
	
	@FindBys(@FindBy(xpath="//span[@class='ion ion-navicon-round']"))
	private List<WebElement> headerSanwichIcon;
	
	@FindBys(@FindBy(xpath="//div[@class='dropdown-box open']//ul[@class='dropdown-list']/li[contains(.,'Logout')]"))
	private List<WebElement> logoutLink;
	
	@FindBys(@FindBy(xpath="//div[@class='dropdown-box open']//ul[@class='dropdown-list']/li[contains(.,'Admin Access')]"))
	private List<WebElement> adminAccess;
	
	@FindBys(@FindBy(xpath="//div[@class='dropdown-box']"))
	private List<WebElement> dropDownMenuOpens;
	
	@FindBy(xpath="//div[@class='dropdown-box open']//ul[@class='dropdown-list']/li[contains(.,'Logout')]")
	private WebElement clickLogout;
	
	@FindBy(xpath="//*[@class='admin-access']")
	private WebElement  AdminAccess;
	
	@FindBys(@FindBy(xpath="//*[@class='me page-level ng-scope']"))
	private List<WebElement> MyAccountPAge;
	
	@FindBys(@FindBy(xpath="//div[@class='me-clients']//div/ul/li"))
	private List<WebElement> listAssignedClients;
	
	@FindBy(xpath="//div[@class='dropdown-box']")
	private WebElement dropDownMenuOpenVisibility;
	
	@FindBy(xpath="//div[@class='me-details']//div[@ng-show='password_change_requested']")
	private WebElement passChangeMessage;
	
	
	
	@FindBys(@FindBy(xpath="//div[@class='dropdown-box open']//ul[@class='dropdown-list']/li"))
	private List<WebElement> listLinksUnderHeaderSandwichIcon;
	
	@FindBys(@FindBy(xpath="//span[@class='audience-name ng-binding']"))
	private List<WebElement> headerIdiomNameExist;
	
	@FindBy(xpath="//span[@class='question']//a[@title='Help']")
	private WebElement helpIcn;
	
	@FindBy(xpath="//a[@class='help-center']")
	private WebElement helpCenter;
	
	
	 /*****************IDIOM 2.3 Start***************/
	@FindBy(xpath="//div[@class='me-change-password layout horizontal']//div[1]/button")
		public WebElement changeResentPassButton;
	
	@FindBys(@FindBy(xpath="//div[@class='me-clients']"))
		public List<WebElement> youtClientListSection;
	@FindBy(xpath="//*[@class='me-clients']")
	public WebElement  clientsSection;
	  /**
	   * @param strElement (createuserbutton, labelcreateuser)
	   * @return
	   * @author Shailesh Shah
	   * @since 26/04/2016
	   */
		public boolean isVisible(String strElement){
			
			boolean bStatus=true;
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, 0);
			wait.ignoring(NoSuchElementException.class);
			
			try{
				switch(strElement.toLowerCase()){
					
				case "changepassword": 
							wait.until(ExpectedConditions.visibilityOf(changeResentPassButton));						
							break;
				case "clientssection":
					wait.until(ExpectedConditions.visibilityOf(clientsSection));						
					break;
					
				
				}
			}catch(Exception e){
				bStatus = false;
				e.printStackTrace();
			}
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			return bStatus;
		}
		
	 /*****************IDIOM 2.3 End***************/
	
	/**
	 * func_getListofElementVal(String element) : This will return value of elements of provided list web element
	 * @author Shailesh Kava 
	 * @param element: name of case ('listclients', 'listlinksunderheadersandwichicon')
	 * @return : ArrayList
	 * @since 12 Jan 2016
	 * Modified By | Description of Modification:
	 */
	public ArrayList func_getListofElementVal(String element){
		
		ArrayList<String> listVal = new ArrayList();
		System.out.println(element);
		
		switch(element.toLowerCase()){
		
			case "listclients":
				for(WebElement we:listAssignedClients){
					listVal.add(we.getText().trim());
				}
			break;
			
			case "listlinksunderheadersandwichicon":
				try{
					func_click("headericon");
					Thread.sleep(1000);
					for(WebElement we:listLinksUnderHeaderSandwichIcon){
						listVal.add(we.getText().trim());
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			break;	
		}
		
		return listVal;
	}
	
	/** func_onHover(WebDriver driver, String element)
	* This method is used to keep hover on any element
	* @author Shailesh Kava
	* @param element: Name of case (headericon)
	* Created By: Shailesh Kava
	* @since 30 Dec 2015
	* Modified By | Description of Modification:
	*/	
	public void func_onHover(String element){
		Actions action = new Actions(driver);
		
		switch(element.toLowerCase()){
		
		case "headericon":
			action.moveToElement(headerSandwithIcon).build().perform();
		break;	
		
		
		
		}
	}
	
	/** func_click(String element)
	* This method is used to click on any specified element
	* @author Shailesh Kava
	* @param element: Name of cases (headericon,myaccount,logout,admin access,changepass)
	* @since 30 Dec 2015
	* Modified By | Description of Modification:
	*@throws Exception*/

	public void func_click(String element) throws Exception{
		
		switch(element.toLowerCase()){
		
		case "headericon":
			headerSandwithIcon.click();
		break;
		
		case "myaccount":
			myAccountLink.click();
		break;			
		
		
		case "logout":
			headerSandwithIcon.click();
			Thread.sleep(3000);
			clickLogout.click();
		break;	
		
		case "admin access":
			headerSandwithIcon.click();
			Thread.sleep(3000);
			AdminAccess.click();		
		break;
		
		case "changepass":
			changePassButton.click();
		break;
		
		case "helpicon":
			helpIcn.click();
		break;
		
		case "helpcenter":
			helpCenter.click();
		break;
		
		}
	}
	
	/////IDIOM 2.3/////
	/** func_getText(String element)
	* This method is used to get text for any specified element
	* @author Shailesh Kava
	* @param element: Name of case (role,clientadminrole,userName,email,changepassmessage)
	* @since 30 Dec 2015
	* Modified By | Description of Modification:
	*/
	
	public String func_getText(String element){
		String getText = null;
		
		switch(element.toLowerCase()){
		
		case "role":
			getText = userRole.getText();
		break;	
		
		case "clientadminrole":
			getText = userClientAdminRole.getText();
		break;	
		
		case "userName":
			getText = userName.getText();
		break;
		
		case "email":
			getText = emailAddress.getText();
		break;
		
		case "changepassmessage":
			getText = passChangeMessage.getText();
		break;
		}
		
		return getText.trim();
	}
	
	/** ArrayList<String> func_getList(String element)
	* This method is used to return list of web element available in specified listElement
	* @author Shailesh Kava
	* @since 11 Jan 2016
	* Modified By | Description of Modification:
	* @param element: Name of case (userclients)
	*/
	
	public ArrayList<String> func_getList(String element){
		ArrayList<String> getList = new ArrayList<String>();
		
		switch(element.toLowerCase()){
		case "userclients":
			for(WebElement clients:clientList){
				getList.add(clients.getText());
			}
		break;
		
		}
		
		return getList;
	}
	
	/** func_elementExist(String element)
	* This function will check element is present with the help of reusable method(webelementexist())
	* @author Shailesh Kava 
	* Created On: 30 Dec 2015
	* Modified By | Description of Modification:
	* @param element : Name of case
	 * @throws Exception 
	*/
	
	public boolean func_elementExist(String element) throws Exception{
		boolean exist = false;
		
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		switch(element.toLowerCase()){
		
		case "headerIdiomNameExist":
			exist = BaseClass.rm.webElementExists(headerIdiomNameExist);
		break;	
		
		case "headerlogo":
			exist = BaseClass.rm.webElementExists(headerIdiomLogo);
		break;
		
		case "profilesetting":
			exist = BaseClass.rm.webElementExists(profileSetting);
		break;
			
		case "helpicon":
			exist = BaseClass.rm.webElementExists(helpIcon);
		break;	
		
		case "headersandwichicon":
			exist = BaseClass.rm.webElementExists(headerSanwichIcon);
		break;
		
		case "logout":
			exist = BaseClass.rm.webElementExists(logoutLink);
		break;
		
		case "adminaccess":
			exist = BaseClass.rm.webElementExists(adminAccess);
		break;
		
		case "dropdownopen":
			exist = BaseClass.rm.webElementExists(dropDownMenuOpens);
		break;
		
		case "my account page exists":
			exist=BaseClass.rm.webElementExists(MyAccountPAge);
		break;
		
		case "my account link in menu":
			exist=BaseClass.rm.webElementSync(myAccountLink, "visibility");
		break;
		
		//IDIOM 2.3//
		case "youclientlistsection":
			exist=BaseClass.rm.webElementExists(youtClientListSection);
		break;
		}
		
		return exist;
	}
	
	
	/** func_browserBack()
	* @author Shailesh Kava
	* This function is used to go back in browser
	* @since 30 Dec 2015
	* Modified By | Description of Modification:
	*/
		
	public void func_browserBack(){
		driver.navigate().back();
	}
	
	
	/** This method is used to get private WebElement out of this class.
	 *  Make sure that element name should be matched.
	 * 
	 * @param strWebElement - String name of the WebElement variable.
	 * @return Returns WebElement
	 * @CreatedOn 01/12/2016
	 * @author Deepen Shah
	 * Created Date: 11 Jan 2015
	 * Modified By | Description of Modification: | Modified Date:
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
	
	
	/**This method will return List of active clients which are assigned to the user
	 * 
	 * @author Shailesh Kava
	 * @param query - SQL to get list of client for specific user
	 * @param dbInstance - Instance is mentioned in property file
	 * @param dbUser - DB user is mentioned in property file
	 * @param dbPass - DB password is mentioned in property file
	 * @return - List of clients
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 * 
	 * created date: 13 Jan 2016
	 * Modified By | Description of Modification: | Modified Date:
	 */
	public ArrayList func_getAssignedClietFromDB(String query, String dbInstance, String dbUser, String dbPass) throws ClassNotFoundException, SQLException, Exception{
		
		ArrayList<String> clients = new ArrayList();
		Class.forName("org.postgresql.Driver");
        Connection c = null;
 		c = DriverManager.getConnection(dbInstance,dbUser,dbPass);
		
 		java.sql.Statement st= c.createStatement();
 		
 		ResultSet rs = st.executeQuery(query);
        Thread.sleep(10000);
        int size=0;
        
        while (rs.next()) {
        	String val = rs.getString("name");
        	clients.add(val);
        }
 		
        c.close();
        
		return clients;
	}
	
	/**This method is used to get value of any provided element 
	 * @author Shailesh Kava
	 * @param element - Name of cases (userrole)
	 * @return - String (value of element)
	 * Created Date: 18 Jan 2016
	 * Modified By | Description of Modification: | Modified Date:
	 */
	
	/*public String func_getTextOfElement(String element){
		String elementVal = null;
		switch(element.toLowerCase()){
		case "userrole":
			elementVal = userRole.getText().trim();
		break;	
		}
		return elementVal;
	}*/
}