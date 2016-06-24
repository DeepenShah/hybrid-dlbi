package common;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import com.google.common.base.Predicate;
import com.reports.CustomReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ReusableMethods {
	
	WebDriver driver;	
	Robot robot ;
	public ReusableMethods(WebDriver driver){
		this.driver= driver;		
	}
	
	public static String ParentWindowID = "";
	public static Date date = new Date();
	
	
/*
	public static void switchToNewWindow()
	{		
		ParentWindowID = driver.getWindowHandle();

		for (String winHandle : driver.getWindowHandles()) {	    	
			if(!ParentWindowID.equalsIgnoreCase(winHandle))
			{
				driver.switchTo().window(winHandle);
			}
		}
	}

	public static void switchToOriginalWindow()
	{		
		driver.switchTo().window(ParentWindowID);	
	}


	*/

	
	public static void createTextFile(String path) throws InterruptedException, IOException
	{
		 //Create File In D: Driver.  
		  String TestFile = path;
		  File FC = new File(TestFile);//Created object of java File class.
		  FC.createNewFile();//Create file.	
		  
		  //Writing In to file.
		  //Create Object of java FileWriter and BufferedWriter class.
		  FileWriter FW = new FileWriter(TestFile);
		  BufferedWriter BW = new BufferedWriter(FW);
		  BW.write("This Is First Line."); //Writing In To File.
		  BW.newLine();//To write next string on new line.
		  BW.write("This Is Second Line."); //Writing In To File.
		  BW.close();		  
		
	}
	
	public static void deleteTextFile(String path) throws InterruptedException, IOException
	{
		System.out.println("Deleting Text File Created in Temp Folder");
		 //Create File In D: Driver.  
		  String TestFile = path;
		  File FC = new File(TestFile);//Created object of java File class.
		  FC.delete();   
		  
		
	}

	
	// Generate random number
	public static String randomNumber()
	{	
		String randomNumber = "";
		randomNumber = String.valueOf(date.getTime());
		
		return randomNumber;
	}
	
	
	
	public static void selectDropDrownValue(WebElement element, String value){
		try{
			Select select= new Select(element);
			select.selectByVisibleText(value);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	
	
	public static void selectDropDrown(WebElement element, String value){
		try{
			Select select= new Select(element);
			select.selectByValue(value);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}	
	
	
	/*
	public static void DeleteEmail(String subject)
    {           
          driver.findElement(By.xpath(property.getProperty("button_Promotions"))).isDisplayed();
          driver.findElement(By.xpath(property.getProperty("button_Promotions"))).click();
          driver.findElement(By.xpath("//div[@class = 'y6']/span[contains(., '"+subject+"')]")).click();
        
          driver.findElement(By.xpath(property.getProperty("button_Delete"))).click();                      
                     
    }*/
	
	public static String monthFormat(int month)
    {    String  monthString; 
    
		switch (month) {
        case 1:  monthString = "Jan";
                 break;
        case 2:  monthString = "Feb";
                 break;
        case 3:  monthString = "Mar";
                 break;
        case 4:  monthString = "Apr";
                 break;
        case 5:  monthString = "May";
                 break;
        case 6:  monthString = "Jun";
                 break;
        case 7:  monthString = "Jul";
                 break;
        case 8:  monthString = "Aug";
                 break;
        case 9:  monthString = "Sep";
                 break;
        case 10: monthString = "Oct";
                 break;
        case 11: monthString = "Nov";
                 break;
        case 12: monthString = "Dec";
                 break;
        default: monthString = "Invalid month";
                 break;
    }  
		return monthString;
                     
    }
	
	
/* Deepen	
	public static void highlightElement(WebElement element) {
        for (int i = 0; i <3; i++) {
        	JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
            }
        }
	*/
	
	public boolean elementExists(WebElement element){
		boolean value = false;
		String elementName = element.getText();
		try{
		if (element.isDisplayed()){			
			value=true;
		}
		
		else{
			value=false;
			CustomReporter.errorLog(elementName+" is NOT displayed");			
			BaseClass.testCaseStatus=false;
		}
		
		} catch(Exception e){
			e.printStackTrace();
		}
		return value;
		
		
		
	}
	//Updated by Amutha Nair
	public Boolean webElementExists(java.util.List<WebElement> input){
		
		Boolean value;
		if (input.size()!=0){			
			value=true;
		}else{
			value=false;
		}
		return value;		
		
	}
	
	//This method retrieves data from DB based on query provided
	//Date:27 july 2015
	//Created by: Amrutha Nair
	public Hashtable RetrieveValuesFromDB(String Query)
	{
		Statement stmt;
		Integer ValueOutput=0; 
		Float ValueOut=(float) 0;
	
		try {
 
			Class.forName("org.postgresql.Driver");
 
		} catch (ClassNotFoundException e) {
 
		
			e.printStackTrace();
			//return;
 
		}
		
		Connection connection = null;
 
		try {
 
			
			  connection = DriverManager.getConnection("jdbc:postgresql://idiomqapsql04.c1dw3w2xwiv0.us-east-1.rds.amazonaws.com:5432/IDPSQL01","psqladm", "psqladmqa04_92");
            
              stmt = connection.createStatement();
              ResultSet res = stmt.executeQuery(Query);
              Hashtable<String, Integer> ht = new Hashtable<String, Integer>();


       while (res.next())
       {
	
	ValueOut=res.getFloat(2);	
	ValueOutput=(Integer) Math.round(ValueOut);
	ht.put(res.getString(1), ValueOutput);
	
	 }
return ht;
		} catch (SQLException e) {
			 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
 
		if (connection != null) {
		
		} else {
			System.out.println("Failed to make connection!");
		}
		
		return null;
		
	}
	
	
	
	//This method inserts data into DB based on query provided
	//Date:5th August 2015
	//Created by: Amrutha Nair
	public Boolean InsertDataInDB(String Query)
	{
			Statement stmt;			
			Boolean done= true;
			try {
	 
				Class.forName("org.postgresql.Driver");
	 
			} catch (ClassNotFoundException e) {
	 
			
				e.printStackTrace();
				//return;
	 
			}
			
			Connection connection = null;
	 
			try {
	 
				
				  connection = DriverManager.getConnection("jdbc:postgresql://idiomqapsql02.c1dw3w2xwiv0.us-east-1.rds.amazonaws.com:5432/IDQAPSQL01","psqladm", "IPJ3oHIBx28miyJVUoTh1wtZ");
	            
	              stmt = connection.createStatement();
	              
	              stmt.executeUpdate(Query);
	             // stmt.executeQuery(Query);
	           
	return done;
			} catch (SQLException e) {
				 
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				done=false;
				
			}
	 
			if (connection != null) {
			
			} else {
				System.out.println("Failed to make connection!");
				done=false;
			}
			
			return done;
			
		}
		
		public void clickWebElement(WebElement element){
			
			element.click();		
			
		}
		
		public void waitTime(long seconds){
			try {
				Thread.sleep(seconds);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//This method gets the previous year
		//Date:7th August 2015
		//Created by: Amrutha Nair
		
		public String  getPreviousYear() {
			
			String pYear=null;
		    Calendar prevYear = Calendar.getInstance();
		    prevYear.add(Calendar.YEAR, -1);
		    pYear=String.valueOf(prevYear.get(Calendar.YEAR));
		   // return prevYear.get(Calendar.YEAR);
			return pYear;
		
		}
		
		//This method gets the months 
		//Date:7th August 2015
		//Created by:Amrutha Nair
		public boolean  checkMonth(String month) {
			boolean found=false;
		String[] months = new DateFormatSymbols().getMonths();
	    for (int i = 0; i < months.length; i++) {
	        if(months[i].contentEquals(month))
	        {
	        	CustomReporter.log("The month is "+months[i]);
	        	found = true;
	        	break;
	        }
	    }
		return found;
		}
		
	
	//This method checks whether the date given is in the date range 
	// Date:7th Augus 2015
	//Created by Amrutha Nair
		public boolean dateRange(String date, String month){
			boolean found=false;
			int dateInt= Integer.parseInt(date);
			if ((month.contentEquals("January")||month.contentEquals("March")||month.contentEquals("May")||month.contentEquals("July")||month.contentEquals("August")||month.contentEquals("October")||month.contentEquals("December"))&&(dateInt>0 && dateInt<32)){
				CustomReporter.log("The date is valid ");
				found= true;
			}
			else if((month.contentEquals("April")||month.contentEquals("June")||month.contentEquals("September")||month.contentEquals("November")||month.contentEquals("February"))&&(dateInt>0 && dateInt<31)){
				CustomReporter.log("The date is valid ");
				found= true;
			}
			else{
				CustomReporter.errorLog("The date is  not valid ");
			}
			return found;
					
		}
		
		//This method checks whether the time is valid 
		//Date:7th August 2015
		//Created by Amrutha Nair
		public boolean timeRange(String time){
			boolean found=false;
		    String[] timeArray =time.split(":");
		    
		    int hour=Integer.parseInt(timeArray[0]);
		    int minute=Integer.parseInt(timeArray[1]);
		    if(hour>0 && hour<13){
		    	if(minute>0 && minute<60)
		    	{
		    		CustomReporter.log("Time is valid "+time);
		    		found=true;
		    	}
		    }
		    else{
		    	CustomReporter.errorLog("Invalid Time:"+time);
		    }
			return found;
					
		}

		//This method compares two strings with different lengths 
		//Date:7th August 2015
		//Created by Amrutha Nair
		public boolean compareArray(ArrayList<String> selectedFilter, ArrayList<String> dupFilters){
			boolean status=true;
	
			for (String sf : selectedFilter) {
				
			    if (dupFilters.contains(sf)) {
			    	status=true;
			    }
			    else{
			    	status=false;
					break;
			    	}
			    }
		return status;
			}
		
		
		//This method checks whether data in an array list is sorted or NOT
		//Date:17 August 2015
		//Created by :Amrutha Nair
		public boolean isSorted(ArrayList<String> inputData, String Case) throws ParseException
		{
			
			boolean status=true;
			switch(Case){
				case "Name Ascending":
					String previous = "";
					for (String current: inputData) {
						current=current.toLowerCase();
						if (current.compareTo(previous) < 0){
							status=false;	
							break;
						}
						previous = current;
						}
					break; 
					
				case "Name Descending":
					int i=0;
					String first=null;

					for (String current: inputData) {
						current=current.toLowerCase();
						if(i>0){
						
							if (current.compareTo(first) > 0){
								
								status=false;	
								break;
							}
						}
						i=1;
						first = current;
						}
					break;
			
				case "Date Descending":
					Date date;
					
					Date pDate=Calendar.getInstance().getTime();
					Date currentTime =pDate;
				    for (String current: inputData) {
				    	
			          date = new SimpleDateFormat("MM/dd/yyyy").parse(current);
			          System.out.println(date+":date");
			          System.out.println(pDate+":pDate");
			          if(pDate.compareTo(date) == -1 && pDate.compareTo(currentTime)==0){
			        	 
			        	
			          }else if(pDate.compareTo(date) == -1){
			        	 
			        	 status=false;	
			        	 break;
			          }
			         
		        	
			        
			        pDate=date;
				    }
				  break; 
				  
				case "Date Ascending":
					int j=0;
					Date date1;
					
					Date pDate1=Calendar.getInstance().getTime();
					Date currentTime1 =pDate1;
				    for (String current: inputData) {
				    	
				    	date1 = new SimpleDateFormat("MM/dd/yyyy").parse(current);
				    	
			          if(j>0){
				          if(pDate1.compareTo(date1) >0 && pDate1.compareTo(currentTime1)==0){
				        	 
				        	  
				          }else if(pDate1.compareTo(date1)>0){
				        	  
				        	 status=false;	
				        	 break;
				          }
				          
				        }
			          j=1;
			          pDate1=date1;
				    }
				  break; 
			}
			return status;
			
		}
		
		
		//This method compares two hashtables
		//Date:18 Sepetember 2015
		//Created by :Amrutha Nair
		public boolean CompareHashTables(Hashtable<String, String> hashtable1, Hashtable<String, String> hashtable2){
			boolean result=true;
			for (Map.Entry<String, String> hashtableData : hashtable1.entrySet()) {
				   if(hashtable2.containsKey(hashtableData.getKey()) && hashtable2.get(hashtableData.getKey()).equals(hashtableData.getValue())){
					  }
				   else
				     {
				          result = false;
				     }
				  }
			return result;
		}
		
		
		//This method scroll till bottom
		//Date:21 Sepetember 2015
		//Created by :Amrutha Nair
		public static void scrollToBottom(WebDriver driver) {
	        ((JavascriptExecutor) driver)
	                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
	    }
		
		
		//This method scroll till provided element
		//Date:21 Sepetember 2015
		//Created by :Amrutha Nair
		public static void scrollTo(WebDriver driver, WebElement element) {
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView();", element);
	    }
		
		
				
		/** This method is for synchronization. Based on various condition it will wait for further action
		 *  to be done. Add any new case in below list
		 *  <p> <b>Conditions</b> are 'visibility', 'visibilitynowait','visibiltiybyxpath','visibilitynowaitbyxpath', 'clickable', 'pageload',
		 *  'jqueryload', 'verifytext', 'staleness', 'idiomspinningcomplete','idiomspinningvisible'  & 'listsize'. </p>
		 * @param webElement - WebElement type object to be used in condition. Null for 'pageload','idiomspinningcomplete' & 'idiomspinningvisible'
		 * @param strCondition - String condition. Condition to be met till that further execution delayed. Options 'visibility','clickable','pageload',
		 *			 'verifytext','staleness','idiomspinningcomplete','idiomspinningvisible','listsize'.
		 * 
		 * @param strConditionValue - If condition is 'verifytext' then pass the text to be matched.
		 * @param elementList
		 * @return Returns flat for the status of condition.
		 * @throws Exception
		 * @author Deepen Shah
		 */
		public boolean webElementSync(WebElement webElement, String strCondition, final String strConditionValue, final List<WebElement> elementList) throws Exception {	
			boolean bFlag = false;
			
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.ignoring(NoSuchElementException.class);

			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			
			try {
				switch (strCondition.toLowerCase()) {
				case "visibility":
					wait.until(ExpectedConditions.visibilityOf(webElement));
					break;
				
				case "visibilitynowait":
					wait.withTimeout(3, TimeUnit.SECONDS);
					wait.until(ExpectedConditions.visibilityOf(webElement));					
					break;
					
				case "visibiltiybyxpath":
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(strConditionValue))));
					break;
					
				case "visibilitynowaitbyxpath":
					wait.withTimeout(5, TimeUnit.SECONDS);
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(strConditionValue))));					
					break;
					
				case "clickable":
					wait.until(ExpectedConditions.elementToBeClickable(webElement));
					break;
				case "pageload":
					System.out.println("Current Window State : "
							+ String.valueOf(((JavascriptExecutor) driver)
									.executeScript("return document.readyState")));
					wait.until(new Predicate<WebDriver>() {

						@Override
						public boolean apply(WebDriver driver) {
							// TODO Auto-generated method stub
							System.out.println("Current Window State : "
									+ String.valueOf(((JavascriptExecutor) driver)
											.executeScript("return document.readyState")));
							return String
									.valueOf(
											((JavascriptExecutor) driver)
													.executeScript("return document.readyState"))
									.equals("complete");
						}
					});
					break;
				
				case "jqueryload":
					wait.until(new Predicate<WebDriver>(){
						
						@Override
						public boolean apply(WebDriver driver) {
							
							System.out.println("Current JQuery State : "
									+ String.valueOf(((JavascriptExecutor) driver)
											.executeScript("return jQuery.active")));
							
							return String
									.valueOf(
											((JavascriptExecutor) driver)
													.executeScript("return jQuery.active"))
									.equals("0");				            
				        }
					});
					break;
					
				case "verifytext":
					System.out.println("Verifying veriftext, " + strConditionValue + " , for element " + webElement.toString());
					wait.until(ExpectedConditions.textToBePresentInElement(webElement, strConditionValue));
					break;			
				
						
				case "staleness":
					wait.until(ExpectedConditions.stalenessOf(webElement));
					break;
				
				case "idiomspinningcomplete":
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loading-bar-spinner")));				
					driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
					wait.withTimeout(60, TimeUnit.SECONDS);
					wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.id("loading-bar-spinner"))));
					
					break;
					
				case "idiomspinningvisible":
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loading-bar-spinner")));	
					break;
						
				case "spinningtocomplete":
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(strConditionValue))));				
					driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
					wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath(strConditionValue))));
					
					break;			
					
				case "listsize":
					wait.until(new Predicate<WebDriver>() {

						@Override
						public boolean apply(WebDriver driver) {
							// TODO Auto-generated method stub							
							return elementList.size()==Integer.parseInt(strConditionValue);
						}
					});
					break;
					
				case "refreshed": wait.until(ExpectedConditions.stalenessOf(webElement));
								wait.until(ExpectedConditions.visibilityOf(webElement));
								break;
								
				case "tillelementisstale":
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(strConditionValue))));
					wait.until(new Predicate<WebDriver>(){
						
						@Override
						public boolean apply(WebDriver driver) {
							boolean bStatus = false;
							WebDriverWait wait = new WebDriverWait(driver, 2);
							try{
								wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(strConditionValue))));
							}catch(Exception e){
								bStatus = true;
							}
							
							return bStatus;            
				        }
					});
					break;
								
				default:
					System.out.println("No such case  "+ strCondition +" found in webElementSync ");
					break;

				}
				if(webElement!=null){
					System.out.println("Successfully tested " + strCondition +" for element " + webElement.toString());
				}else{
					System.out.println("Successfully tested " + strCondition);
				}
				bFlag = true;
			}catch(NoSuchElementException ne){
				ne.printStackTrace(System.out);
				bFlag = false;
			}catch (Exception e) {
				e.printStackTrace(System.out);				
				bFlag = false;
				//throw e; //Do not want to throw exception here. Next step will get failed automatically
			}finally{
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				wait.withTimeout(30, TimeUnit.SECONDS);
			}
			
			return bFlag;
			
		}
		
		
		/** This method is for synchronization. Based on various condition it will wait for further action
		 *  to be done. Add any new case in below list
		 *  <p> <b>Conditions</b> are 'visibility', 'visibilitynowait','visibiltiybyxpath','visibilitynowaitbyxpath', 'clickable', 'pageload',
		 *  'jqueryload', 'verifytext', 'staleness', 'idiomspinningcomplete','idiomspinningvisible'  & 'listsize'. </p>
		 * @param strCondition
		 * @return
		 * @throws Exception
		 */
		public boolean webElementSync(String strCondition) throws Exception {
			
			return webElementSync(null,strCondition,"",null);
		}
		
		/** This method is for synchronization. Based on various condition it will wait for further action
		 *  to be done. Add any new case in below list
		 *  <p> <b>Conditions</b> are 'visibility', 'visibilitynowait','visibiltiybyxpath' 'clickable', 'pageload',
		 *  'jqueryload', 'verifytext', 'staleness', 'idiomspinningcomplete','idiomspinningvisible'  & 'listsize'. </p>
		 * @param element
		 * @param strCondition
		 * @return
		 * @throws Exception
		 */
		public boolean webElementSync(WebElement element, String strCondition) throws Exception {
			
			return webElementSync(element,strCondition,"",null);
		}
		
		/** This method is for synchronization. Based on various condition it will wait for further action
		 *  to be done. Add any new case in below list
		 *  <p> <b>Conditions</b> are 'visibility', 'visibilitynowait','visibiltiybyxpath' 'clickable', 'pageload',
		 *  'jqueryload', 'verifytext', 'staleness', 'idiomspinningcomplete','idiomspinningvisible'  & 'listsize'. </p>
		 * @param strCondition
		 * @param strVal
		 * @param elementList
		 * @return
		 * @throws Exception
		 */
		public boolean webElementSync(String strCondition, String strVal, List<WebElement> elementList) throws Exception {
			
			return webElementSync(null,strCondition,strVal,elementList);
		}

		
		/** This method is for synchronization. Based on various condition it will wait for further action
		 *  to be done. Add any new case in below list
		 *  <p> <b>Conditions</b> are 'visibility', 'visibilitynowait','visibiltiybyxpath' 'clickable', 'pageload',
		 *  'jqueryload', 'verifytext', 'staleness', 'idiomspinningcomplete','idiomspinningvisible'  & 'listsize'. </p>
		 * @param strCondition
		 * @param strVal
		 * @return
		 * @throws Exception
		 * @author Deepen Shah
		 * @since 26 April 2016
		 */
		public boolean webElementSync(String strCondition, String strVal) throws Exception {
			return webElementSync(null,strCondition,strVal,null);
		}
		
		/** This method is for synchronization. Based on various condition it will wait for further action
		 *  to be done. Add any new case in below list
		 *  <p> <b>Conditions</b> are 'visibility', 'visibilitynowait','visibiltiybyxpath' 'clickable', 'pageload',
		 *  'jqueryload', 'verifytext', 'staleness', 'idiomspinningcomplete','idiomspinningvisible'  & 'listsize'. </p>
		 * @param element
		 * @param strCondition
		 * @param strVal
		 * @return
		 * @throws Exception
		 * @author Deepen Shah
		 * @since 26 April 2016
		 */
		public boolean webElementSync(WebElement element,String strCondition, String strVal) throws Exception {
			return webElementSync(element,strCondition,strVal,null);
		}
		
		public boolean isClickable(WebElement element) {
			// TODO Auto-generated method stub
			try
			{
			   WebDriverWait wait = new WebDriverWait(driver, 5);
			   wait.until(ExpectedConditions.elementToBeClickable(element));
			   return true;
			}
			catch (Exception e)
			{
			  return false;
			}
			}
		
		public WebDriver  func_OpenNewwindow(String Url) throws AWTException, InterruptedException{
			    Thread.sleep(3000);
			  robot.keyPress(KeyEvent.VK_CONTROL);
		      robot.keyPress(KeyEvent.VK_N);
		      robot.keyRelease(KeyEvent.VK_CONTROL);
		      robot.keyRelease(KeyEvent.VK_N);  

		      // Perform the click operation that opens new window

		      // Switch to new window opened
		      for(String winHandle : driver.getWindowHandles()){
		          driver.switchTo().window(winHandle);
		      }
		      driver.get(Url);
			return driver;

		     
			}
		
		/**
		 * @param testName - String. This will be used as file name.
		 * @param status - String. pass/fail
		 */
		public void captureScreenShot(String testName, String status){	
			String destDir = "";
			//String passfailMethod = result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
			//To capture screenshot.
			
//Deepen			File scrFile = ((TakesScreenshot) BaseClass.driver).getScreenshotAs(OutputType.FILE);
			
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    	DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
	    	//If status = fail then set folder name "screenshots/Failures"
	    	if(status.equalsIgnoreCase("fail")){
	    		destDir = "Screenshots/Failures";
	    	}
	    	//If status = pass then set folder name "screenshots/Success"
	    	else if (status.equalsIgnoreCase("pass")){
	    		destDir = "Screenshots/Success";
	    	}
	    	
	    	//To create folder to store screenshots
	    	new File(destDir).mkdirs();
	    	//Set file name with combination of test class name + date time.
	    	String destFile = testName+" - "+dateFormat.format(new Date()) + ".png";
	    	
	        try {
	        	//Store file at destination folder location
	     	   FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
	        }
	        catch (IOException e) {
	             e.printStackTrace();
	        }
	   }
		
		
		/** Method to take screen shot of page open through new web driver.
		 *  This method will help to take screen shot when multiple window is opened.
		 * 
		 * @param testName
		 * @param status
		 * @param newDriver
		 * @author Deepen Shah
		 * @since 03 Jun 2016
		 */
		public void captureScreenShotOnWithDifferentDriver(String testName, String status,WebDriver newDriver){	
			String destDir = "";
			
			
			File scrFile = ((TakesScreenshot) newDriver).getScreenshotAs(OutputType.FILE);
	    	DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
	    	//If status = fail then set folder name "screenshots/Failures"
	    	if(status.equalsIgnoreCase("fail")){
	    		destDir = "Screenshots/Failures";
	    	}
	    	//If status = pass then set folder name "screenshots/Success"
	    	else if (status.equalsIgnoreCase("pass")){
	    		destDir = "Screenshots/Success";
	    	}
	    	
	    	//To create folder to store screenshots
	    	new File(destDir).mkdirs();
	    	//Set file name with combination of test class name + date time.
	    	String destFile = testName+" - "+dateFormat.format(new Date()) + ".png";
	    	
	        try {
	        	//Store file at destination folder location
	     	   FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
	        }
	        catch (IOException e) {
	             e.printStackTrace();
	        }
	   }
		
		/** This is common method to check if List of type String contains some strings.
		 * 
		 * @param strChildList - List. Of Type String. Subset to be check.
		 * @param strParentList - List. Of Type String. Super set to be look inside.
		 * @return - boolean. True/false
		 * @since 29/01/2016
		 * @author Deepen Shah
		 */
		public boolean verifyListContainsList(List<String> strChildList,List<String> strParentList ){
			boolean bStatus = true;
			
			for(String strTest:strChildList){
				if(!strParentList.contains(strTest)){
					bStatus = false;
				}
			}
			
			return bStatus;
		}
		
		/** This method will match two list with one by one value in order.
		 *  Both should be exactly same.
		 *  
		 * @param l1 - List of type String
		 * @param l2 - List of type String
		 * @return - boolean. True/False
		 * @since 01/02/2016
		 * @author Deepen Shah
		 */
		public boolean compareListWithOrder(List<String> l1, List<String> l2){
			boolean bStatus = true;
			
			if(l1.size() != l2.size()){
				bStatus = false;
				System.out.println("Reusable_Methods: Size of the list is not the same");
			}else{
				for(int i=0;i<l1.size();i++){
					if(!l1.get(i).equalsIgnoreCase(l2.get(i))){
						bStatus = false;
						System.out.println("Values is not matching: l1: " + l1.get(i) + " and l2:" + l2.get(i));
					}
				}
			}
			return bStatus;
		}
		
		
		/** This method will return current date time
		 * 
		 * @return String
		 * @author Deepen Shah
		 * @since 03 May 2016
		 */
		public String getCurrentDateTime(){
			return new SimpleDateFormat("MM-dd-yyyy HH-mm-ss").format(new Date());
		}
		
		/**
		 * To verify if webelement exists
		 * 
		 * @author Vikram Hegde
		 * 
		 * @param strElement
		 * @return
		 */
		public boolean isElementPresent(String xpath) {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			try {
				driver.findElement(By.xpath(xpath));
				return true;
			} catch (NoSuchElementException e) {
				return false;
			} finally {
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			}
		}

		public void func_OpenNewtab(String property) {
			// TODO Auto-generated method stub
			
		}
		
		/** This method will delete project or audience using web service
		 *   
		 * 
		 * @param strAudienceProjClientId - <p> String - separated value for audienceid-projectid-clientid.
		 * No need to provide audienceid if to be deleted item is project. So value would be projectid-clientid.
		 * 
		 * To get these ids use getAudienceProjectClientId method of ClientList_Page
		 *  </p>
		 * @param bIsProject - boolean True if given value is only project
		 * @return - Status of deletion
		 * @author Deepen Shah
		 * @since 18 May 2016
		 */
		public boolean deleteProjectOrAudience(String strAudienceProjClientId,boolean bIsProject){
			return BaseClass.util.deleteProjectOrAudience(strAudienceProjClientId, bIsProject);
		}
		
		/**This method will download file, added conditions for browsers as required 
		 * @author Shailesh Kava
		 * @since 20-May-2016
		 */
		public void downloadFile(){
			BaseClass bClass = new BaseClass();
			String brwoserName = bClass.browserName;
			System.out.println("Download verify in browser ["+brwoserName+"]");
			Robot robot;
			try {
				robot = new Robot();
				
				if(!brwoserName.equalsIgnoreCase("chrome")){
		            System.out.println("Clicking on ALT+S key");
		            robot.keyPress(KeyEvent.VK_ALT);
		            robot.keyPress(KeyEvent.VK_S);
		            System.out.println("Relesing ALS+S");
		            robot.keyRelease(KeyEvent.VK_S);
		            robot.keyRelease(KeyEvent.VK_ALT);
		            System.out.println("Clicked on ALT+S key");
		        	
		        	if(!brwoserName.equalsIgnoreCase("ie")){
		        		System.out.println("This is not IE");
			            robot.keyPress(KeyEvent.VK_ENTER);
			            robot.keyRelease(KeyEvent.VK_ENTER);
		        	}
	            }
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public String getScreenShotName(String className) {
		  String screenShotName = "TC"+className.replaceAll("\\D+","")+System.currentTimeMillis();
      	  return screenShotName;
		}
		
		public void setRobot(Robot robot) throws AWTException {
      		this.robot = new Robot();
    	}
		

		
		/** To get xpath version in current selenium
		 * 
		 * @param context
		 * @return
		 * @author Deepen Shah
		 * @since 2 Jun 2016
		 */
		public String getXPathVersion(WebDriver context) {
		    try {
		        By.xpath("/nobody[@attr=('A'||'')]").findElement(context);
		        return "3.0";
		    } catch (Exception e) {
		        try {
		            By.xpath("/nobody[@attr=lower-case('A')]").findElement(context);
		            return "2.0";
		        } catch (Exception iee) {
		            return "1.0";
		        }
		    }
		}
		
		/**
		 * @return
		 * @throws Exception
		 * @author Deepen Shah
		 * @since 14 Jun 2016
		 */
		public Date getTodaysDate() throws Exception{
			Calendar cal = Calendar.getInstance();
		      // You cannot use Date class to extract individual Date fields
		      int year = cal.get(Calendar.YEAR);
		      int month = cal.get(Calendar.MONTH);      // 0 to 11
		      int day = cal.get(Calendar.DAY_OF_MONTH);
		      
		      return new SimpleDateFormat(BaseClass.strDateFormat).parse((month+1)+"/"+day+"/"+year);
		}
		
}