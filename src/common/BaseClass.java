package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.msat.frameworks.data_driven.base.Base;
import com.reports.CustomReporter;


public class BaseClass extends Base {
	public ClientList_Page clientListPage=null;
	public static boolean testCaseStatus = true;
	
	
	 public static final String BrwserStckUN = "amruthadev1";
	 public static final String BrwserStckA_KEY = "3zvqTcFt9csx7VujpgLN";
	 public static final String URL = "http://" + BrwserStckUN + ":" + BrwserStckA_KEY + "@hub.browserstack.com/wd/hub";
	 public WebDriver driver;
	 public static String browserName = "";
	 public static Properties property=null;
	 public static PageHeader pageHeader=null;
	 public static ReusableMethods rm;
	 public static Utility util;
	 
	 // Property files path
	 public static final String strTestDataPropPath="Test Data/Idiom-TestData.properties";
	 public static final String strQAEnvDetails="Test Data/Idiom-QA-EnvironmentDetails.properties";
	 public static final String strStageEnvDetails="Test Data/Idiom-Stage-EnvironmentDetails.properties";
	 public static final String strProdEnvDetails="Test Data/Idiom-Prod-EnvironmentDetails.properties";
	 public static final String strPreDevEnvDetails="Test Data/Idiom-Pre-Dev-EnvironmentDetails.properties";
	 public static final String strDevEnvDetails="Test Data/Idiom-Dev-EnvironmentDetails.properties";
	 public static final String strMappingFile = "Test Data/Audience-Mapping.xlsx";
	 public static HashMap<String,String> strAudienceMappedValues;
	 //End
	 
	 public String strEnvironment=System.getProperty("env.ENVIRONMENT")==null?"QA":System.getProperty("env.ENVIRONMENT");
	 public String strTestType=System.getProperty("env.TESTSUITES");
	 public String strClient="";
	 public static String strCurrentUser="";
	 public static String strCurrentUserPass="";
	 public static String strDateFormat = "MM/dd/yyyy";
	 
	 public Login_Page ln=null;
	 
	 @BeforeSuite
	 public void setUp(){
		 try{
			 util = new Utility();
			 propertiesFileReader();
			//Setting client
			System.out.println("Provided client is " + System.getProperty("env.CLIENT"));
			strClient = System.getProperty("env.CLIENT")==null?property.getProperty("defaultClient"):System.getProperty("env.CLIENT");
			strAudienceMappedValues = util.getClientMappingData(strClient);
			
			property.setProperty("clientName", strClient);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	 
	@Parameters({ "browser"})
	@BeforeTest	
	
	public void startBrowser(String browser) throws MalformedURLException{		
		try{
			System.out.println(strTestType +" execution will be starting on " + strEnvironment);
			
			BaseClass.testCaseStatus=true;
			browserName = browser;		
			
			if (browser.equalsIgnoreCase("firefox"))
			{
			driver = new FirefoxDriver();
			
			}
			else if(browser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", "Browser Servers"+File.separator+"chromedriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			    capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized","--disable-popup-blocking"));
			   driver = new ChromeDriver(capabilities);
			  
			}
			else if(browser.equalsIgnoreCase("IE"))
			{
				System.setProperty("webdriver.ie.driver","Browser Servers"+File.separator+"IEDriverServer.exe");
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setPlatform(Platform.ANY);
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.ACCEPT);
				ieCapabilities.setCapability("ignoreZoomSetting", true);
				ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);			 
				
				driver = new InternetExplorerDriver(ieCapabilities);				
			}
			
			pageHeader = new PageHeader(driver);
			rm = new ReusableMethods(driver);
			clientListPage = new ClientList_Page(driver);
			ln = new Login_Page(driver);
			
			driver.manage().window().maximize();		
			driver.get(property.getProperty("URL"));
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
			
		}catch(Exception e){
			e.printStackTrace();
			CustomReporter.errorLog("Some error while launching browser and opening application. Check screenshot and log for more details");
			rm.captureScreenShot("ProblemInLaunchingBrowser", "fail");
		}
	}
	
	@AfterTest		//Updated by Deepen
	public void closeBrowser(){		
		driver.quit();		
	}	
	
	public void propertiesFileReader(){
		if(property == null){			
			property = new Properties();
			try {
				property.load(new FileInputStream(strTestDataPropPath));
				
				
				if(strEnvironment.equalsIgnoreCase("qa")){
					property.load(new FileInputStream(strQAEnvDetails));
				}else if(strEnvironment.equalsIgnoreCase("production")){
					property.load(new FileInputStream(strProdEnvDetails));
				}else if(strEnvironment.equalsIgnoreCase("staging")){
					property.load(new FileInputStream(strStageEnvDetails));
				}else if(strEnvironment.equalsIgnoreCase("PreDev")){
					property.load(new FileInputStream(strPreDevEnvDetails));
				}else if(strEnvironment.equalsIgnoreCase("Dev")){
					property.load(new FileInputStream(strDevEnvDetails));
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/** To Launch new browser. This method is made for launching new browser from current test case browser to meet specific need 
	 * @param strBrowser
	 * @return It returns Webdriver object for dealing with new browser window
	 * @throws MalformedURLException
	 * @author Rohan Macwan
	 * @since 10-May-2016
	 */
	public WebDriver launchBrowser(String strBrowser) throws MalformedURLException{		
		WebDriver newDriver=null;
		
		try{
					
			if (strBrowser.equalsIgnoreCase("firefox"))
			{
				newDriver = new FirefoxDriver();
			
			}
			else if(strBrowser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", "Browser Servers"+File.separator+"chromedriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			    capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized","--disable-popup-blocking"));
			    newDriver = new ChromeDriver(capabilities);
			  
			}
			else if(strBrowser.equalsIgnoreCase("IE"))
			{
				System.setProperty("webdriver.ie.driver","Browser Servers"+File.separator+"IEDriverServer.exe");
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setPlatform(Platform.ANY);
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.ACCEPT);
				ieCapabilities.setCapability("ignoreZoomSetting", true);
				ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);			 
				
				newDriver = new InternetExplorerDriver(ieCapabilities);				
			}
			
			newDriver.manage().window().maximize();		
			newDriver.get(property.getProperty("URL"));
			newDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			
			
		}catch(Exception e){
			e.printStackTrace();
			CustomReporter.errorLog("Some error while launching browser and opening application. Check screenshot and log for more details");			
		}
		return newDriver;
	}
	
	/**This method will perform action from login to select a client, implemented to reduce repetitive code in each test case
	 * 
	 * @return	boolean
	 * @author	Shailesh Kava
	 * @since	26-May-2016
	 */
	public boolean loginToSelectClient(String strUserName, String strPass) throws Exception{
		boolean bProcessed = false;
		
		String strClientName=property.getProperty("clientName");
		String strMsg;		
		
		//Step1: login Launch IDIOM url and enter valid credentials 
		Thread.sleep(3000);
        ln.func_LoginToIDIOM(strUserName, strPass);		    
	    
        CustomReporter.log("Launch IDIOM url and enter valid credentials ");
        
        //Step 2: Select a client
		clientListPage = new ClientList_Page(driver);
        
		Thread.sleep(5000);
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		
		if(clientListPage.selectClient(strClientName)){
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			CustomReporter.log("Selected client ["+strClientName+"]");
		}
		
		bProcessed = true;
		
		return bProcessed;
	}
	/**Overloading to loginToSelectClient(String strUserName, String strPass), this will use if superadmin user is used for login
	 * 
	 * @return	boolean
	 * @autho	Shailesh Kava
	 * @since	26-May-2016 
	 */
	public boolean loginToSelectClient() throws Exception{
		return loginToSelectClient(property.getProperty("SuperAdminUser"),property.getProperty("SuperAdminpassword"));
	}
	
	
	/** Common code to execute when there is exception.
	 *  It is used in every class.
	 * 
	 * @param ie Exception object
	 * @param strClassName String classname. From that it will use test case id.
	 * @author Deepen Shah
	 * @since 09 Jun 2016
	 */
	public void exceptionCode(Exception ie,String strClassName){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(strClassName + ": " + strMsgAndFileName[0]);
		ie.printStackTrace(System.out);
		
		String strTestCaseId = strClassName.replaceAll("\\D+","");		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot(strTestCaseId+"-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot(strTestCaseId+"-"+strMsgAndFileName[1], "fail");	
		}		
	}	
}
