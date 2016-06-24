/* 
 * ManageIdiom Page
 * List Of Methods:
 * func_CheckDropDown(WebDriver driver) - This method checks the drop down for RENAME, Delete and Duplicate are existing and whether its proper
 * func_CheckIdiomComponents() -This method checks whether name , date of creation are  present with idiom and checks whether format is as expected
 * func_CheckTheIdiomIsexisting(String idiom) - this method checks whether idiom with input name and date is existing
 * func_CreateButtonEnabled() -This method checks whether Create button in create idiom dialogue box is enabled
 * func_CreateNewIdiom(String Name, String Case) - This method is updated to create an idiom when we provide case as "NotBlank" and idiom name AND  check for condition with blank name
 * func_DuplicatedIdiomName(String idiomName) - This method creates the name of duplicated idiom
 * func_DuplicatedPosition(String idiomName) -This method checks whether the duplicated idiom comes on top
 *  func_Reset_val(String element)-This method resets text value of given text field
 * func_ElementExist(String client)-This method is used to check the existence of any web element of Manage Idiom Page
 * func_PerformClickAction(String Action, WebDriver driver)-This method clicks on an elements in Manage idiom Page accoring to input data provided
 * func_SelectExistingIdiomByName(String idiomName, WebDriver driver)-This method is used to click on existing Idiom by name
 * func_SelectExistingIdiomByNumber(int idiomNum, WebDriver driver)-This method is used to click on existing Idiom by number
 * func_SetValueText(String element, String updated_name)-This method This method sets the value into text box
 * func_TopClientElementExists(String item)-This method checks whether the client icon is present at the right top and whether "Logout" and "Change Client" element is there under it
 * func_GetElementMessage()-This method checks whether the  message element is existing and returns the  message depending on the case provided
 * func_SortIdioms(String Case)-This method checks whether the idiom associated with a client is sorted according to input 'case' provided
 * func_getIdiomValueByNumber(int idiomNum, WebDriver driver)-This method returns idiom value for given number
 * func_ReturnCursorPosition(String Case, WebDriver driver)-This method returns  the cursor position of an element 
 * func_CheckHoverOnComponents(String Case, WebDriver driver)-This method checks hovering effect is proper for component cases passed
 */
package com.IDIOM.pages;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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
import common.ReusableMethods;


public class ManageIdiom_Page {
	
	WebDriver driver;
	public ManageIdiom_Page(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);	
		this.driver = driver;
	}
	

	
	@FindBys(@FindBy(xpath="//span[text()='Create New IDIOM']/ancestor::div[contains(@class,'menu-row')]"))
	private List<WebElement> btn_createNewIdiomLst;
	
	@FindBy(xpath="//*[@class='saved-idioms']/li[1]/div/span[1]")
	private WebElement btn_ExistingIdiom;
	
	@FindBys(@FindBy(xpath="//*[@class='overlay-modal manage-modal ng-scope']//button[1]"))
	private List<WebElement> button_create_idiom;
		
	@FindBys(@FindBy(xpath="//*[@class='overlay-modal manage-modal ng-scope']//button[1]"))
	private List<WebElement> button_cancel_idiom;
	
	@FindBy(xpath="//*[@class='saved-idioms']/li[1]/div/span[2]")
	private WebElement dateAndTimeForFirstIdiom;
	
	@FindBys(@FindBy(xpath="//*[@class='saved-idioms']/li[1]/div/span[1]"))
	private List<WebElement> idiom_Exists;
	
	@FindBy(xpath="//*[@class='selected-client dropdown-parent ng-isolate-scope']//*[@ng-href='#/clients']")
	private WebElement right_ChangeClient;
	
	@FindBys(@FindBy(xpath="//*[@class='selected-client dropdown-parent ng-isolate-scope']//*[@ng-href='#/clients']"))
	private List<WebElement> right_ChangeClient_Lst;
	
	@FindBys(@FindBy(xpath="//*[@class='saved-idioms']/li[2]/div/span[1]"))
	private List<WebElement> secondIdiom_Exists;
	
	@FindBys(@FindBy(xpath="//*[@class='saved-idioms']/li[1]/div/div[1]"))
	private List<WebElement> drpDownMenuExist;
	
	@FindBy(xpath="//*[@class='saved-idioms']/li[1]/div/div[1]//*[@class='dropdown-list']/li[1]")
	private WebElement rename_Idiom;
	
	@FindBy(xpath="//*[@class='saved-idioms']/li[1]//*[@class='idiom-menu dropdown-parent ng-isolate-scope']/span")
	private WebElement drpDownMenu;
	
	@FindBy(xpath="//*[@class='saved-idioms']/li[1]/div/div[1]//*[@class='dropdown-list']/li[3]")
	private WebElement delete_Idiom;
	
	@FindBy(xpath="//*[@class='saved-idioms']/li[1]/div/div[1]//*[@class='dropdown-list']/li[2]")
	private WebElement duplicate_Idiom;
	
	@FindBy(xpath="//*[@class='saved-idioms']/li[1]/div/div[1]//*[@class='dropdown-list']/li")
	private WebElement drpDownMenuItems;
	
	@FindBy(xpath="//*[@id='orderAudiences']")
	private WebElement sort_DrpDown;
	
	@FindBy(xpath="//*[@class='selected-client dropdown-parent ng-isolate-scope']")
	private WebElement right_TopClient;
	
	@FindBy(xpath="//*[@class='overlay-modal manage-modal ng-scope']//input")
	private WebElement input_Idiom_Name;
	
	@FindBy(xpath="//*[@class='overlay-modal manage-modal ng-scope']//button[1]")
	private WebElement click_create_idiom;
	
	@FindBy(xpath="//*[@class='overlay-modal manage-modal ng-scope']//button[2]")
	private WebElement click_cancel_idiom;
	
	@FindBys(@FindBy(xpath="//*[@class='overlay-modal manage-modal ng-scope']"))
	private List<WebElement> overlay_idiomLst;
	
	@FindBy(xpath="//*[@class='dropdown-list']/li[3]/a")
	private WebElement right_TopManage;
	
	@FindBy(xpath="//*[@class='dropdown-list']/li[2]/a")
	private WebElement right_TopRename;
	
	@FindBy(xpath="//*[@class='dropdown-list']/li[1]/a")
	private WebElement right_TopSave;
	
	@FindBy(xpath="//div[@class='modal-content']/p")
	private WebElement Rename_Popup_Title;
	
	@FindBys(@FindBy(xpath="//li[@class='ng-scope']//span[@class='idiom-name ng-binding']"))
	private List<WebElement> saved_IdiomName_Lst;
	
	@FindBys(@FindBy(xpath="//li[@class='ng-scope']//span[@class='idiom-date ng-binding']"))
	private List<WebElement> saved_IdiomDate_Lst;
	
	@FindBys(@FindBy(xpath="//*[@class='selected-client dropdown-parent ng-isolate-scope']"))
	private List<WebElement> right_TopClient_Lst;
	
	@FindBy(xpath="//*[@ng-click='logout()']")
	private WebElement right_Logout;
	

	@FindBys(@FindBy(xpath="//div[@class='block block--clear error-unicorn layout horizontal ng-scope']"))
	private List<WebElement> error_Lst;	

	
	@FindBys(@FindBy(xpath="//*[@ng-click='logout()']"))
	private List<WebElement>  right_Logout_Lst;
	
	@FindBy(xpath="//div[@ng-show='audienceRenameForm.$error.maxlength']")
	private WebElement lengthyName_Error;
	
	@FindBys(@FindBy(xpath="//span[@class='icon ion ion-edit']"))
	private List<WebElement> renameIcon_Lst;

	@FindBys(@FindBy(xpath="//span[@class='icon ion ion-ios-copy']"))
	private List<WebElement> duplicateIcon_Lst;
	
	@FindBys(@FindBy(xpath="//span[@class='icon ion ion-ios-trash']"))
	private List<WebElement> deleteIcon_Lst;
	
	@FindBys(@FindBy(xpath="//*[@class='dropdown-box open']"))
	private List<WebElement> drpDown_Open;
	
	@FindBy(xpath="//li[@class='selected-client dropdown-parent']/img[@ng-src]")
	private WebElement img_client;
	
	@FindBy(xpath="//*[@class='ion ion-navicon-round']")
	private WebElement MenuIcon;
	
	@FindBys(@FindBy(xpath="//*[@class='selected-client dropdown-parent navicon ng-isolate-scope left-border']"))
	private List<WebElement> MenuIcon_Lst;
	
	@FindBys(@FindBy(xpath="//*[@class='admin-access']"))
	private List<WebElement>   AdminAccessPresent;
	
	@FindBy(xpath="//*[@class='admin-access']")
	private WebElement  AdminAccess;

	
	@FindBy(xpath="//*[@ng-model='audienceRename']")
	private WebElement RenameText;
	
	

	@FindBy(xpath="//div[@class='dropdown-box open']//ul[@class='dropdown-list']/li[contains(.,'My Account')]")
	private WebElement myAccount;

//Element that are used in synchronization. Please do not change the name	
	
	@FindBy(xpath="//span[text()='Create New IDIOM']/ancestor::div[contains(@class,'menu-row')]")
	private WebElement btn_createNewIdiom;
	
	
//End	


	/** func_CreateNewIdiom(String Name, String Case)
	 *This method is updated to create an idiom when we provide case as "NotBlank" and idiom name AND  check for condition with blank name
	 *Created By:Amrutha Nair
	 *Creation Date:11th August 2011
	 *Modified By | Description of Modification: 
	 *Modified On: 
	 */	
	public void func_CreateNewIdiomOrRename(String Name, String Case) throws InterruptedException{	
		switch (Case){
		
		case "NotBlank":
			btn_createNewIdiom.click();
			Thread.sleep(5000);
			input_Idiom_Name.clear();
			input_Idiom_Name.sendKeys(Name);
			click_create_idiom.click();
		break;
		
		case "Blank":
			btn_createNewIdiom.click();
			input_Idiom_Name.clear();
		break;
		
		case "Space":
			btn_createNewIdiom.click();
			input_Idiom_Name.clear();
			input_Idiom_Name.sendKeys("    ");
		break;
		
		case "LengthyName":
			btn_createNewIdiom.click();
			input_Idiom_Name.clear();
			input_Idiom_Name.sendKeys(Name);
		break;
		
		case "Rename":		
			input_Idiom_Name.clear();
			input_Idiom_Name.sendKeys(Name);
			click_create_idiom.click();
			break;
			
		case "CursorPosition":
			btn_createNewIdiom.click();
			input_Idiom_Name.click();
		break;
	
		}
		
	}	
		


	
	
	/** func_TopClientElementExists(String item)
	 *This method checks whether the client icon is present at the right top and whether "Logout" and "Change Client" element is there under it
	 *Created By:Amrutha Nair
	 *Creation Date:Date: 04 Aug 2015
	 *Modified By | Description of Modification: The position of logout is under Menu icon now. So conditions are changed
	 *Modified On: 
	 */	
	public boolean func_TopClientElementExists(String item){
		boolean found = true;	
		
		if(BaseClass.rm.webElementExists(right_TopClient_Lst) == true)
			{
			CustomReporter.log("The client icon at the right top is existing");			
			right_TopClient.click();
		
			switch (item) {		
				
			  case "ChangeClient" :
				 
						if(BaseClass.rm.webElementExists(right_ChangeClient_Lst) == true)
							{
								CustomReporter.log("The ChangeClient element is existing under client icon at right top");	
							}
						else{
							CustomReporter.errorLog("The ChangeClient element is NOT existing under client icon at right top");
							found= false;
						}
					
			  break;
			
			  case  "Manage":
				  found=right_TopManage.isDisplayed();
			  break;
			  
			  case   "Save":
				  found=right_TopSave.isDisplayed();
			  break;
			  
			  case    "Rename":
			       found=right_TopRename.isDisplayed();
			  break;
			}
			}
		  else {
			  CustomReporter.errorLog("The client icon at the right top is NOT existing");
				found= false;
		  }
		
		
		return found;
		
   }
	
	 /**func_SelectExistingIdiomByNumber(int idiomNum, WebDriver driver)
	 *This method is used to click on existing Idiom by number
	 *Created By:Abhishek Deshpande
	 *Creation Date: 04 Aug 2015
	 *Modified By | Description of Modification:
	 *Modified On: 
	 */	 
	
	public void func_SelectExistingIdiomByNumber(int idiomNum){
		
		driver.findElement(By.xpath("(//div[@class='menu-row']/span[contains(@class,'idiom-name')])["+idiomNum+"]")).click();
	}
	
	
	  /**func_SelectExistingIdiomByName(String idiomName, WebDriver driver)
		 *This method is used to click on existing Idiom by name
		 *Created By:Abhishek Deshpande
		 *Creation Date: 04 Aug 2015
		 *Modified By | Description of Modification: Amrutha Nair|updated with try catch to include the negative case
		 *Modified On: 10 Aug 2015
	*/	 

	
	public boolean func_SelectExistingIdiomByName(String idiomName){
		boolean status=true;
		try{
			
		driver.findElement(By.xpath("//div[@class='menu-row']/span[contains(@class,'idiom-name') and text()='"+idiomName+"']")).click();
		return status;
		}
		catch(NullPointerException e)
		{
			status = false;
			return status;
		}
		
		catch(org.openqa.selenium.NoSuchElementException e)
		{
			status = false;
			return status;
		}
		
	}
	
	
	  /**func_CheckIdiomComponents()
		 *This method checks whether name , date of creation are  present with idiom and checks whether format is as expected
		 *Created By:Amrutha Nair
		 *Creation Date:Date: 07 Aug 2015
		 *Modified By | Description of Modification: 
		 *Modified On: 
		 */	
	public boolean func_CheckIdiomComponents() {
		String prevYear= BaseClass.rm.getPreviousYear();
		boolean dateformat=true;
		String [] indData=null;
		if(btn_ExistingIdiom.getText().isEmpty()){
			CustomReporter.errorLog("The Name of idiom is not present");
			BaseClass.testCaseStatus=false;
		}
		else{
			CustomReporter.log("The idiom name is present. The idiom name is:"+btn_ExistingIdiom.getText());
		}
		if(dateAndTimeForFirstIdiom.getText().isEmpty()){
			CustomReporter.errorLog("The Creation Date for idiom is not present");
			BaseClass.testCaseStatus=false;
		}
		else{
			CustomReporter.log("The Creation Date for idiom is  present. The Creation Date is:"+dateAndTimeForFirstIdiom.getText());
			
			
			if(dateAndTimeForFirstIdiom.getText().contains("Today at")){
				CustomReporter.log("The idiom created is today. Time is :"+dateAndTimeForFirstIdiom.getText());
				
				indData=dateAndTimeForFirstIdiom.getText().split(" ");
				
				if(indData[0].contentEquals("Today")){
					CustomReporter.log("The idiom first componant is Today ");
					
				}
				else{
					CustomReporter.errorLog("The idiom first componant is NOt Today ");
					dateformat=false;
					BaseClass.testCaseStatus=false;
				}
				if(indData[1].contentEquals("at")){
					CustomReporter.log("The idiom second componant is 'at'");
					
				}
				else{
					CustomReporter.errorLog("The idiom second componant is NOT 'at'");
					BaseClass.testCaseStatus=false;
					dateformat=false;
				}
				
				if(BaseClass.rm.timeRange(indData[2])){
					CustomReporter.log("The idiom third componant is valid time:"+indData[2]);
				}
				else{
					CustomReporter.errorLog("The idiom third componant is NOT valid time"+indData[2]);
					BaseClass.testCaseStatus=false;
					dateformat=false;
				}
				if(indData[3].contentEquals("AM")||indData[3].contentEquals("PM")){
					CustomReporter.log("The idiom fifth componant is correct:"+indData[3]);
					
				}
				else{
					CustomReporter.errorLog("The idiom fifth componant is NOT correct:"+indData[3]);
					BaseClass.testCaseStatus=false;
					dateformat=false;
				}
			}
			
			else if(dateAndTimeForFirstIdiom.getText().contains(prevYear)) {			
				
			
				//String[] prvYearData= BaseClass.rm.splitString(dateAndTimeForFirstIdiom.getText(), "coma");
				String[] prvYearData= dateAndTimeForFirstIdiom.getText().split(",");
				int i=0;
				for (String item : prvYearData){ 
					  //indData=BaseClass.rm.splitString(item, "space");
					indData=item.split(" ");
					if(i==0){
						if(BaseClass.rm.checkMonth(indData[0])){
							CustomReporter.log("The idiom first componant is month :"+indData[0]);
							
						}
						else{
							CustomReporter.errorLog("The idiom first componant is NOT month :"+indData[0]);
							BaseClass.testCaseStatus=false;
							dateformat=false;
						}
						if(BaseClass.rm.dateRange(indData[1], indData[0]))
						{
							CustomReporter.log("The idiom second componant is valid day:"+indData[1]);
							
						}
						else
						{
							CustomReporter.errorLog("The idiom sepond  componant is NOT valid day:"+indData[1]);
							BaseClass.testCaseStatus=false;
							dateformat=false;
						}
						i=1;
					}
					else{
						if(indData[0].contentEquals(prevYear)){
							CustomReporter.log("The idiom third componant is valid year"+prevYear);
							
						}
						else{
							CustomReporter.errorLog("The idiom sepond  componant is NOT valid year:"+indData[1]);
							BaseClass.testCaseStatus=false;
							dateformat=false;
						}
						if(indData[1].contentEquals("at")){
							CustomReporter.log("The idiom forth componant is 'at'");
							
						}
						else{
							CustomReporter.errorLog("The idiom forth componant is NOT 'at'");
							BaseClass.testCaseStatus=false;
							dateformat=false;
						}
						if(indData[2].contentEquals("AM")||indData[2].contentEquals("PM")){
							CustomReporter.log("The idiom fifth componant is correct:"+indData[3]);
							
						}
						else{
							CustomReporter.errorLog("The idiom fifth componant is NOT correct:"+indData[2]);
							BaseClass.testCaseStatus=false;
							dateformat=false;
						}
					}
					
				CustomReporter.log("The idiom created in last year:" +prevYear);
				}
			}
			else
			{
				//   indData=BaseClass.rm.splitString(dateAndTimeForFirstIdiom.getText(), "space");
				indData=dateAndTimeForFirstIdiom.getText().split(" ");
				   if(BaseClass.rm.checkMonth(indData[0])){
						CustomReporter.log("The idiom first componant is month :"+indData[0]);
						
					}
					else{
						CustomReporter.errorLog("The idiom first componant is NOT month :"+indData[0]);
						BaseClass.testCaseStatus=false;
						dateformat=false;
					}
					if(BaseClass.rm.dateRange(indData[1], indData[0]))
					{
						CustomReporter.log("The idiom second componant is valid day:"+indData[1]);
						
					}
					else
					{
						CustomReporter.errorLog("The idiom sepond  componant is NOT valid day:"+indData[1]);
						BaseClass.testCaseStatus=false;
						dateformat=false;
					}
					if(indData[2].contentEquals("at")){
						CustomReporter.log("The idiom third componant is 'at'");
						
					}
					else{
						CustomReporter.errorLog("The idiom third componant is NOT 'at'");
						BaseClass.testCaseStatus=false;
						dateformat=false;
					}
					if(BaseClass.rm.timeRange(indData[3])){
						CustomReporter.log("The idiom forth componant is valid time:"+indData[3]);
					}
					else{
						CustomReporter.errorLog("The idiom forth componant is NOT valid time"+indData[3]);
						BaseClass.testCaseStatus=false;
						dateformat=false;
					}
					if(indData[4].contentEquals("AM")||indData[4].contentEquals("PM")){
						CustomReporter.log("The idiom fifth componant is correct:"+indData[4]);
						
					}
					else{
						CustomReporter.errorLog("The idiom fifth componant is NOT correct:"+indData[4]);
						BaseClass.testCaseStatus=false;
						dateformat=false;
					}
				   
			}
				
				
			}
		if (dateformat){
			CustomReporter.log("The idiom Date time format is proper: "+dateAndTimeForFirstIdiom.getText());
		}else{
			CustomReporter.errorLog("The idiom date time format is not proper: "+dateAndTimeForFirstIdiom.getText());
		}

		return BaseClass.testCaseStatus;
		
	}
	
	 
	  /**func_PerformClickAction(String Action, WebDriver driver)
		 * This method clicks on an elements in Manage idiom Page according to input data provided
		 *Created By:Amrutha Nair
		 *Creation Date: 06 Aug 2015
		 *Modified By | Description of Modification: 
		 *Modified On: 
	*/		
	public String  func_PerformClickAction(String Action) throws InterruptedException{

	
		String inputText= null;
		String outputTime=null;

		String outputText=null;
		Actions builder = new Actions(driver);
		
			
		if(func_VerifyVisibilityOfElement(btn_ExistingIdiom))
			inputText=btn_ExistingIdiom.getText();
		
		
		if(func_VerifyVisibilityOfElement(dateAndTimeForFirstIdiom))
			outputTime=dateAndTimeForFirstIdiom.getText();
		
		
		
		switch (Action) {
		
		case "Delete":
			CustomReporter.log("Delete Idiom: "+inputText);
			
			builder.moveToElement(drpDownMenu).build().perform();
			//drpDownMenu.click();
			Actions builder1 = new Actions(driver);
			builder1.moveToElement(delete_Idiom).build().perform();
			delete_Idiom.click();
			Thread.sleep(4000);
			outputText=inputText+"||"+outputTime;
			break;
		
		case "Duplicate":
			CustomReporter.log("Duplicate Idiom: "+inputText);
			builder.moveToElement(drpDownMenu).build().perform();
			Actions builder2 = new Actions(driver);
			builder2.moveToElement(duplicate_Idiom).build().perform();
			duplicate_Idiom.click();
			outputText=func_CreateIdiomName(inputText, "Duplicate");
					
			break;
			
		case "Rename":
			builder.moveToElement(drpDownMenu).build().perform();
			Actions builder1_rename = new Actions(driver);
			builder1_rename.moveToElement(rename_Idiom).build().perform();
			rename_Idiom.click();
			
			outputText = input_Idiom_Name.getAttribute("value");
			break;
			
		case "cancel":
			click_cancel_idiom.click();
			break;
			
		case "ChangeClient":
			builder.moveToElement(right_TopClient).build().perform();
			right_ChangeClient.click();
			break;
			
		case "Logout":
			
			
			builder.moveToElement(MenuIcon).build().perform();
			right_Logout.click();	
			break;
			
		case "SortByDate":
			ReusableMethods.selectDropDrownValue(sort_DrpDown,"Date");
			break;
			
		case "SortByName":
			ReusableMethods.selectDropDrownValue(sort_DrpDown,"Name");
			break;
			
		case "SelectFirstIdiom":
			outputText=inputText;
			btn_ExistingIdiom.click();
			break;
			
		case "Save":
			click_create_idiom.click();
			 break;	
		case "AdminAccess":
			MenuIcon.click();
			AdminAccess.click();
		break;
		
		case "myaccount":
			MenuIcon.click();
			myAccount.click();
		break;
			
		}
		
			return outputText;
	}
	
	 /**func_CheckTheIdiomIsexisting(String idiom)
	 *this method checks whether idiom with input name and date is existing
	 *Created By:Amrutha Nair
	 *Creation Date:Date: 06 Aug 2015
	 *Modified By | Description of Modification: 
	 *Modified On: 
	 */	
	 public boolean func_CheckTheIdiomIsexisting(String idiom) {
		 boolean status= true;
		 String name=idiom.split("||")[0];
		 String date=idiom.split("||")[1];		
		 if(btn_ExistingIdiom.getText().contentEquals(name)){
				 if(dateAndTimeForFirstIdiom.getText().contentEquals(date)){
					 status= false;
					 CustomReporter.errorLog("The idiom "+name+"which is created at "+date+"  is present ");
				 }
				
				 }
		return status;
	 }
		
	 
	  /**func_CheckDropDown(WebDriver driver)
		 *This method checks the drop down for RENAME, Delete and Duplicate are existing and whether its proper
		 *Created By:Amrutha Nair
		 *Creation Date:Date: 07 Aug 2015
		 *Modified By | Description of Modification: 
		 *Modified On: 
		 */	
	 public	 boolean func_CheckDropDown(String Case ){
		 boolean found=true;
		 switch (Case) {
			
			case "IdiomComp":
			 if(BaseClass.rm.webElementExists(drpDownMenuExist)){
					CustomReporter.log("The Drop down menu button is present");
					String dropdwnItem=null;
					Actions builder = new Actions(driver);
					builder.moveToElement(drpDownMenu).build().perform();
					if(rename_Idiom.getText().contentEquals("RENAME") && BaseClass.rm.webElementExists(renameIcon_Lst)){
						CustomReporter.log("The drop down item :'RENAME' text and icon are present");
					}else{
						CustomReporter.errorLog("The drop down doesn't contain RENAME");
						found =false;
					}
					if(duplicate_Idiom.getText().contentEquals("DUPLICATE") && BaseClass.rm.webElementExists(duplicateIcon_Lst)){
						CustomReporter.log("The drop down item :'DUPLICATE' text and icon are present");
					}
					else{
						CustomReporter.errorLog("The drop down doesn't contain DUPLICATE");
						found =false;;
					}
					if(delete_Idiom.getText().contentEquals("DELETE") && BaseClass.rm.webElementExists(deleteIcon_Lst)){
						CustomReporter.log("The drop down item :'DELETE'is present and icon are present");
					}
					else{
						CustomReporter.errorLog("The drop down doesn't contain DELETE");
						found =false;
					}
					
					
				}
			 break;
			 
			case "Sort":
				//sort_DrpDown.click();
				String dropdwnItem=null;
				for(int i = 1; i<=2;i++)
				{
					dropdwnItem=driver.findElement(By.xpath("//select[@id='orderAudiences']//option["+i+"]")).getText();
				
					if(dropdwnItem.contentEquals("Date")||dropdwnItem.contentEquals("Name")){
						CustomReporter.log("The drop down item :"+dropdwnItem+"is present");
						}
					else
					{
						CustomReporter.errorLog("The drop down doesn't contain drop down :"+dropdwnItem);
						found =false;
					}
				}
			}
					
			return found;			 
		 
	 }
	 
	 
	  /**func_ElementExist(String client)
		 *This method is used to check the existence of any web element of Manage Idiom Page
		 *Created By:Abhishek Deshpande
		 *Creation Date:  07 Aug 2015
		 *Modified By | Description of Modification: Amrutha Nair|updated with return type and added new cases
		 *Modified On: 12 Aug 2015
	*/	 

	 public boolean func_ElementExist(String client){
	 		boolean found=false;
	 		switch (client) { 		
	 		
	 		case "CreateNewIdiom":	 		
	 			found=BaseClass.rm.webElementExists(btn_createNewIdiomLst);
	 			
	 			
	 			break;
	 		case"AnyIdiom":
	 			found= BaseClass.rm.webElementExists(idiom_Exists);
	 			break;
	 		case "PopUp":
	 		 found=BaseClass.rm.webElementExists(overlay_idiomLst);
	 		break;
	 		case "SecondIdiom":
	 		  found =BaseClass.rm.webElementExists(secondIdiom_Exists);
	 		break;
	 		
	 		case "Error":
	 			found=BaseClass.rm.webElementExists(error_Lst);
	 		break;
	 		
	 		case "saveIdiomButtom":
	 			found = BaseClass.rm.webElementExists(button_create_idiom);
	 			break;
	 				 		
	 			case "cancelIdiomButton":
	 			found = BaseClass.rm.webElementExists(button_cancel_idiom);
	 			break;
	 			case "Admin Access Present":	
					MenuIcon.click();
					found=BaseClass.rm.webElementExists(AdminAccessPresent);
				break;

	 		}
			return found;
	 	}
	 
	
	 
	 
	  /**func_DuplicatedIdiomName(String idiomName)
			 *This method creates the name of  idiom dynamically . It creates the name of duplicated idiom also depending on the CASE provided
			 *Created By:Amrutha Nair
			 *Creation Date: 10 Aug 2015
			 *Modified By | Description of Modification:
			 *Modified On: 
		*/	 

	 	
	 public String func_CreateIdiomName(String idiomName, String Case){
		Date now = new Date();
		String opIdiomName=null;
		String timeNow = new SimpleDateFormat("MMM d, yyyy h:mm:ss a").format(now);
	 
		 switch (Case) { 
		 	case "Duplicate":		    
			opIdiomName= idiomName+" [Copy - "+timeNow+"]";
			
			break;
			
		 	case "New":
			opIdiomName= idiomName+"_"+timeNow;
			break; 
			
		 }
			return opIdiomName;
		 
	 }
	 
	 
	  /**func_DuplicatedPosition(String idiomName)
		 *This method checks whether the duplicated idiom comes on top
		 *Created By:Amrutha Nair
		 *Creation Date: 10 Aug 2015
		 *Modified By | Description of Modification:
		 *Modified On: 
	*/	
	 	
	 public boolean func_IdiomPosition(String idiomName){
		 boolean status= false;
		 if(idiomName.contentEquals(btn_ExistingIdiom.getText())){
			 status= true;
		 }
		return status;
		 
	 }
	 
	
	 
	 /**func_Reset_val(String element)
	 *This method resets text value of given text field
	 *Created By: Shailesh Kava
	 *Creation Date:  10 Aug 2015
	 *Modified By | Description of Modification: 
	 *Modified On: 
    */
	 public void func_Reset_val(String element){
		 
		 switch(element){
		 	
		 case "reset_idiom":
		 input_Idiom_Name.clear();
		 break;
		 }
	}
	
	 
	
	 /**func_SetValueText(String element, String updated_name)
		 *This method This method sets the value into text box
		 *Created By: Shailesh Kava
		 *Creation Date:  10 Aug 2015
		 *Modified By | Description of Modification: 
		 *Modified On: 
	    */	 
		public void func_SetValueText(String element, String updated_name){
			
			switch(element){
			
			case "input_idiom":
	 		input_Idiom_Name.clear();
			input_Idiom_Name.sendKeys(updated_name);
			break;
			}
		}
	
	 /**func_CreateButtonEnabled()
	 *This method checks whether Create button in create idiom dialogue box is enabled
	 *Created By: Amrutha Nair
	 *Creation Date:  10 Aug 2015
	 *Modified By | Description of Modification: 
	 *Modified On: 12 Aug 2015
    */		
		public boolean func_CreateButtonEnabled()
		{
			boolean status = true;
			status= click_create_idiom.isEnabled();
			return status;
					
		}	
		
		 /**func_GetElementMessage(String Case)
		 *This method checks whether the error message element is existing and returns the error message
		 *Created By: Amrutha Nair
		 *Creation Date:  14 Aug 2015
		 *Modified By | Description of Modification:Luv Datta|Added case for popup title
		 *Modified On: 
	    */		
			public String func_GetElementMessage(String Case)
			{
				String message=null;			
				switch(Case){
					case "LenghtyIdiom":
					message=lengthyName_Error.getText();
					break;
					case "Popup_Title":
					message=Rename_Popup_Title.getText();
					break;
					case "IdiomName":
						message=RenameText.getAttribute("value");
					break;
				}
				return message;
						
						
			}	
			
			/**func_SortIdioms(String Case)
			 *This method checks whether the idiom associated with a client is sorted according to input 'case' provided
			 *Created By: Amrutha Nair
			 *Creation Date:  17 Aug 2015
			 *Modified By | Description of Modification: 
			 *Modified On:
		    */		
		public boolean func_SortIdioms(String Case) throws ParseException{
			boolean status=false;
			

			ArrayList<String> savedIdioms = new ArrayList<String>();
			
			switch(Case){
			case "Name":
				for (WebElement savedIdiom : saved_IdiomName_Lst){	
					savedIdioms.add(savedIdiom.getText());
				}
				status=BaseClass.rm.isSorted(savedIdioms, "Name Ascending");
				break;
			case "Date":
				String tmp=null;
				for (WebElement savedIdiom : saved_IdiomDate_Lst){
					tmp=savedIdiom.getText();
					if(tmp.contains("Today")){
						Calendar mCalendar = Calendar.getInstance();    
						String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
						int date=mCalendar.get(Calendar.DAY_OF_MONTH);
						
						String tday=month+" "+date;
						
						tmp=tmp.replace("Today",tday);
					}
					tmp=tmp.replace(" at", ", 2015");
					savedIdioms.add(tmp);
					
				}
			status=BaseClass.rm.isSorted(savedIdioms, "Date");
				
				
				  break;
			}
			
		return status;
	
		}
		
		/** func_GetListWebElementCount(String element)
		 *This method is used to get Total saved idoms from Manage idiom page
		 *Created By: Shailesh Kava
		 *Creation Date:  17 Aug 2015
		 *Modified By | Description of Modification: 
		 *Modified On: 
	    */	
		public int func_GetListWebElementCount(String element){
				
			Integer totalCount=0;
				
			switch(element){
			
			case "Total Saved Idioms":
			totalCount = saved_IdiomName_Lst.size();	
			break;
			
			}
				
			return totalCount;
		}
		
		/**
		 * func_getIdiomValueByNumber(int idiomNum, WebDriver driver)
		 * This method returns idiom value for given number
		 * Created By : Shailesh Kava
		 * Created Date : 19-Aug-2015
		 * Modified By | Description of Modification:
		 * Modified On:
		 */
		public String func_getIdiomValueByNumber(int idiomNum){
			
			String idiomName = null;
			idiomName = driver.findElement(By.xpath("(//div[@class='menu-row']/span[contains(@class,'idiom-name')])["+idiomNum+"]")).getText().trim();
			
			return idiomName;
		}
		
		
		 /**func_ReturnCursorPosition(String Case, WebDriver driver)
		 *This method returns  the cursor position of an element 
		 *Created By: Amrutha Nair
		 *Creation Date:  20 Aug 2015
		 *Modified By | Description of Modification:
		 *Modified On: 
	    */	
		public boolean func_ReturnCursorPosition(String Case){
			boolean status=false;
			switch(Case){
			case "CreateIdiomTextBox":
				status = input_Idiom_Name.equals(driver.switchTo().activeElement());
				break;				
			}
			
			return status;
			
		}
		
		 /**func_CheckHoverOnComponents(String Case, WebDriver driver)
		 *This method checks hovering effect is proper for component cases passed
		 *Created By: Amrutha Nair
		 *Creation Date:  20 Aug 2015
		 *Modified By | Description of Modification:
		 *Modified On: 
	    */	
		public boolean func_CheckHoverOnComponents(String Case){
			boolean status=false;
			Actions builder = new Actions(driver);
			switch(Case){
			case "IdiomDropDown":
				builder.moveToElement(drpDownMenu).build().perform();
				status = BaseClass.rm.webElementExists(drpDown_Open);
				break;				
			}
			
			return status;
			
		}
		

		 /**func_getArrtibuteValues(String attributeName, WebDriver driver)
		 *This method is used to get image from Right hand side - Top clinent image 
		 *Created By: Shailesh Kava
		 *Creation Date:  24 Aug 2015
		 *Modified By | Description of Modification:
		 *Modified On: 
	    */	
		public String func_getArrtibuteValues(String attributeName){
			String attributeVal = null;
			
			try{
				switch(attributeName){
				
				case "clientImage":
				attributeVal = img_client.getAttribute("src");
				break;
				
			}
			}catch(NoSuchElementException E){
				CustomReporter.errorLog("Element not found ");
				E.printStackTrace();
			}
			
			return attributeVal;
		}
		
		
		/** func_TopMenuElementExists(String item)
		 *This method checks whether the menu icon is present at the right top and whether "Logout" "Admin Access" and My account are under it
		 *Creation Date:Date:03 November 2015
		 *Modified By | Description of Modification: 
		 *Modified On: 
		 */	
		public boolean func_TopMenuElementExists(String item){
			boolean found = true;	
			
			
				if(BaseClass.rm.webElementExists(MenuIcon_Lst)){
				CustomReporter.log("Menu icon is existing at the header in Manage iDiom Page");
				
					switch (item) {		
						case "Logout":
						
						
							if(BaseClass.rm.webElementExists(right_Logout_Lst) == true)
							{
						
								CustomReporter.log("The Logout element is existing under client icon at right top");	
							}
							else{
								CustomReporter.errorLog("The Logout element is NOT existing under client icon at right top");
								found= false;
							}
						
						
						break;
					}
				}
				else{
					CustomReporter.errorLog("Menu icon is NOT existing at the header in Manage iDiom Page");
					found= false;
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
		
		
		/** This method is to check visibility of the element.
		 * 
		 * @param elementToBeVerify - WebElement to check visibility
		 * @return true/false
		 * @since 20/01/2016
		 * @author Deepen Shah
		 */
		public boolean func_VerifyVisibilityOfElement(WebElement elementToBeVerify){
			boolean bResult = true;
			
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.ignoring(NoSuchElementException.class);
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			try{				
				wait.until(ExpectedConditions.visibilityOf(elementToBeVerify));
			}catch(Exception e){
				bResult = false;
			}
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			return bResult;
		}
		
}