/* 
 * Login Page
 * List Of Methods:
 * func_LoginToIDIOM() - This method accepts two string arguments, username and password. This method is called to enter username, password and click on Login to Idiom button.
 *func_PerformAction(String Condition, String value):This method performs action according to input case
 * func_IsLoginButtonEnabled(String cred, String value)- This method checks whether the login button is enabled on entering only email id or only password
 *func_BGColorCheck(String Condition, String Color):This method checks BG colour for the element in input
 *func_ElementExist(String elementName):This method checks whether the element is existing 
 *func_GetLocalWebElement(String strWebElement):This method is used to get private WebElement out of this class.
 *func_ReturnErrorMsg(String strCondition):This method returns the error message based on the condition passed
 *  
 */

package com.IDIOM.pages;


import java.lang.reflect.Field;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.BaseClass;



public class Login_Page {
	
	WebDriver driver;
	public Login_Page(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
	
	}	
	
	@FindBy(id="email")
	private WebElement txt_emailID;	
	
	@FindBy(id="password")
	private WebElement txt_password;
	
	@FindBy(xpath="//*[@id='forgot-password']/a")
	public WebElement forgetPwd;
	
	@FindBy(xpath="//*[@ng-model='email']")
	public WebElement EmailID;
	
	@FindBy(xpath="//*[@ value='Send Password Reset Email']")
	private WebElement Send_EmailID;
	
	@FindBy(id="submit")
	private WebElement btn_LogintoIDIOM;
	
	@FindBys(@FindBy(xpath="*//div[@class='message']"))
	private List<WebElement> DisabledUserValidationMessage;
	
	@FindBys(@FindBy(xpath="//*[text()=' Unknown error ']"))
	private List<WebElement>  unknownError_List;
	 
	 @FindBy(xpath="//*[text()=' Unknown error ']")
	 public WebElement  unknownError;
	
	@FindBys(@FindBy(xpath="//input[@id='submit']"))
	private List<WebElement> btn_LogintoIDIOMList;
	
	@FindBys(@FindBy(xpath="//*[@class='error'] | //*[@class='bright-red error']"))
	private List<WebElement> Invalid_CredError_List;
	
	@FindBy(xpath="//*[@class='error'] | //*[@class='bright-red error']")
	public WebElement Invalid_CredError;
	
	@FindBy(xpath ="//*[@value='Send Password Reset Email']")
	public WebElement passwordResetEmailButton;
	
	@FindBy(xpath ="//*[@value='Password Reset Email Sent']")
	public WebElement passwordResetEmailSent;
	
	@FindBy(xpath="//a[@ng-click=\"go('login')\"]")
	public WebElement returnToIdiomLink;
	
	@FindBy(xpath="//span[@class='bright-red']")
	public WebElement forgotPasswordRedErrorMessage;
	
	@FindBys(@FindBy(xpath="//span[@class='bright-red']"))
	public List<WebElement> forgotPasswordRedErrorMessageList;
	
	@FindBy(xpath="//center[@class='logo']")
	public WebElement forgotPasswordIdiomLogo;
	
	@FindBy(xpath="//span[@ng-show='valid.account == 0']")
	public WebElement forgotPasswordTitleMessage;
	
	@FindBy(xpath="//span[@ng-show='valid.account == 2']")
	public WebElement forgotPasswordSuccessMessage;
	
	@FindBy(xpath="//span[@class='ion ion-checkmark-round success']")
	public WebElement greenCheckmark;
	
	/** func_LoginToIDIOM (String username, String password)
	* Enters username, password and clicks on login to Idiom button
	* Username - Id of the user
	* Password - password of the user
	* Created By: Abhishek Deshpande
	* Created On: 27th July 2015
	* Modified By | Description of Modification:
	*/	
	public boolean func_LoginToIDIOM(String username, String password){	
			boolean bStatus;
			
			//Setting username and password for deletion of project and audience
			BaseClass.strCurrentUser = username;
			BaseClass.strCurrentUserPass = password;
			
		        txt_emailID.clear();
				txt_emailID.sendKeys(username);
				
				txt_password.clear();
				txt_password.sendKeys(password);	
								
				bStatus = btn_LogintoIDIOM.isEnabled();
				btn_LogintoIDIOM.click();
				
				return bStatus;
	}
	
	/**
	 * @param strCondition Invalid Credential, Unknown Error, rederrormessage, forgotpasswordtitlemsg, forgotpasswordsentbtntext, forgotpasswordsuccessmsg
	 * @return
	 * @author Amrutha Nair
	 * @since 2/11/2015
	 */
	public String func_ReturnMsg(String strCondition){
		String message=null;
		switch (strCondition) {
		
			case "Invalid Credential":
				if(BaseClass.rm.webElementExists(Invalid_CredError_List)){				
					message=Invalid_CredError.getText();
				}
			break;
			
			case "Unknown Error":
				if(BaseClass.rm.webElementExists(unknownError_List)){
					message=unknownError.getText();
				}
			break;
			
			case "rederrormessage":
				message = forgotPasswordRedErrorMessage.getText().trim();
				break;
				
			case "forgotpasswordtitlemsg":
				message = forgotPasswordTitleMessage.getText().trim();
				break;
				
			case "forgotpasswordsentbtntext":
				message = passwordResetEmailSent.getAttribute("value").trim();
				break;
				
			case "forgotpasswordsuccessmsg":
				message = forgotPasswordSuccessMessage.getText().trim();
				break;
		}
		return message;
		
	}
	
	/** func_IsLoginButtonEnabled(String cred, String value)
	 *This method checks whether the login button is enabled on entering only email id or only password
	 *Created By:Amrutha Nair
	 *Creation Date:27th July
	 *Modified By | Description of Modification: 
	 *Modified On: 
	 */	
	public boolean func_IsLoginButtonEnabled(String cred, String value){
		if (cred.equals("UN")){			
			txt_emailID.sendKeys(value);}
		else  if(cred.equals("PWD"))
			txt_password.sendKeys(value);
		else{
			txt_emailID.clear();
			txt_password.clear();
		}
		
		boolean status=btn_LogintoIDIOM.isEnabled();
		return status;
	}
	
	
	
	
	
	/**func_ElementExist(String elementName)
	 * This method checks whether the element is existing 
	 * Created By: Rohan Macwan
	 * Created On:24 December 2015
	 * Modified By | Description of Modification:
	 */
	 
  public boolean func_ElementExist(String elementName) {
    boolean found = false;
    switch (elementName) {

    case "DisabledUserValidationMessage":
      found = BaseClass.rm.webElementExists(unknownError_List);
      break;
    case "Login Button":
      found = BaseClass.rm.webElementExists(btn_LogintoIDIOMList);
      break;

    case "Password Reset Success":
      found = BaseClass.rm.elementExists(passwordResetEmailSent);
      break;

    }
    return found;

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
	
	 /** This method performs action according to input case
	 * @param Condition - String cases from: Forget Pwd, Input Email, Password Reset Email
	 * @param value
	 * @author Amrutha Nair
	 * @since 15 January 2016
	 */
	public void func_PerformAction(String Condition, String value) {
	    switch (Condition) {
	    case "Forget Pwd":
	      forgetPwd.click();
	      break;
	    case "Input Email":
	    	EmailID.clear();
	      EmailID.sendKeys(value);
	      break;
	    case "Password Reset Email":
	      passwordResetEmailButton.click();
	      break;
	    }
	  }
	
	
	/** func_BGColorCheck(String Condition, String Color)
	 *This method checks BG colour for the element in input
	 *Created By:Amrutha Nair
	 *Creation Date:15January 2016
	 *Modified By | Description of Modification: 
	 *Modified On: 
	 */	
	public boolean func_BGColorCheck(String Condition, String Color)
	{
		boolean status=false;
		switch(Condition) {
        
		 case "Send password button":
			 if(Color.contentEquals(Send_EmailID.getCssValue("background-color"))){
			   		status=true;
			   	 }
       break;
		}
		return status;
		
	}
	
	/**
	 * @param strCase - String case. forgotpasswordidiomlogo,forgotpasswordtitlemsg,returntoidiomlink, forgotpasswordemail,forgotpasswordsendbtn, 
	 * greencheckmark, forgotpasswordsuccessmsg
	 *  
	 * @return
	 * @author Deepen Shah
	 * @since 20 Jun 2016
	 */
	public boolean isVisible(String strCase){
		boolean bStatus = true;
		
		WebDriverWait wait = new WebDriverWait(driver, 3);
		try{
			switch(strCase.toLowerCase()){
				case "forgotpasswordidiomlogo":
					wait.until(ExpectedConditions.visibilityOf(forgotPasswordIdiomLogo));
					break;
					
				case "forgotpasswordtitlemsg": 
					wait.until(ExpectedConditions.visibilityOf(forgotPasswordTitleMessage));
					break;
					
				case "returntoidiomlink":
					wait.until(ExpectedConditions.visibilityOf(returnToIdiomLink));
					break;
					
				case "forgotpasswordemail":
					wait.until(ExpectedConditions.visibilityOf(EmailID));
					break;
					
				case "forgotpasswordsendbtn":
					wait.until(ExpectedConditions.visibilityOf(passwordResetEmailButton));
					break;
					
				case "greencheckmark":
					wait.until(ExpectedConditions.visibilityOf(greenCheckmark));
					break;
					
				case "forgotpasswordsuccessmsg":
					wait.until(ExpectedConditions.visibilityOf(forgotPasswordSuccessMessage));
					break;
					
			
			}
		}catch(Exception e){
			bStatus = false;
			e.printStackTrace(System.out);
		}
		
		
		return bStatus;
	}
}
