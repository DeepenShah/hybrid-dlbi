package common;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

/** Class to have common methods but not depended on WebDriver(driver)
 *  
 * @author Deepen Shah
 * @since 23 May 2016
 *
 */
public class Utility {
	
	List<Cookie> appCookies = new ArrayList<>();
	public static final String strProjectPath = "idiom-services/clients/{1}/projects/{0}";
	public static final String strLoginPath = "idiom-services/security/auth";
	public static final String strAudiencePath = "idiom-services/clients/{2}/projects/{1}/audiences/{0}";
	public static String strWSUser="";
	public static long lCookiGenTime=0;
	
	/** Method will read audience data mapping file. 
	 * 
	 * @param strClientName - Name of client for which data needs to be read
	 * @return HashMap<String,String>
	 * @throws Exception
	 * @author Deepen Shah
	 * @since 23 May 2016
	 */
	public HashMap<String,String> getClientMappingData(String strClientName) throws Exception{
		HashMap<String,String> returnVal = new HashMap<String,String>();
	
		FileInputStream inputStream = new FileInputStream(BaseClass.strMappingFile);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheet(strClientName.toLowerCase());
	
		for (int i=1;i<=sheet.getLastRowNum();i++) {
			XSSFRow row = sheet.getRow(i);
			String strBaseVal = row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue().toLowerCase();			
			String strNewClientVal= row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue().toLowerCase();
			
			returnVal.put(strBaseVal, strNewClientVal);
			
		}
		
		return returnVal;
	}
	
  public static boolean isSorted(List<String> list) {
    boolean sorted = true;
    for (int i = 1; i < list.size(); i++) {
      if (list.get(i - 1).compareTo(list.get(i)) > 0)
        sorted = false;
    }
    return sorted;
  }
  
 
	/** Method to login to application using web services
	 * 
	 * @author Deepen Shah
	 * @since 02 Jun 2016
	 * 
	 */
	public void loginToApplicationUsingWebService(){
		  	String credential = "{\"username\":\""+BaseClass.strCurrentUser+ "\",\"password\":\""+BaseClass.strCurrentUserPass+"\"}";
			
			String strURL=BaseClass.property.getProperty("URL");
			strURL=strURL.substring(0, (strURL.indexOf("/", strURL.indexOf("/")+2))+1);
			String strLoginURL = strURL + strLoginPath;
			Client client = Client.create();
			WebResource login = client.resource(strLoginURL);
			
			//Login to the application		
			try{
				ClientResponse resp = login.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,credential);
			
			
				if (resp.getStatus() != 200) 
					   throw new RuntimeException("Login Failed : HTTP error code : "	+ resp.getStatus());
			
				System.out.println("Response is  " + resp.getEntity(String.class));
				
				//Setting time
				lCookiGenTime = new Date().getTime();
				
				List<String> cookies = resp.getHeaders().get("Set-Cookie");
				
				String strCookie="";
				String strAwsleb="";
				for(String str:cookies){
					System.out.println(str);
					
					if(str.contains("JSESSIONID"))
						strCookie = str;
						
					
					if(str.contains("AWSELB"))
						strAwsleb = str;
						
				}
				
				String[] session = strCookie.split(";");				
				String[] sesssionNameValue = session[0].split("=");
				
				Cookie ckAwsleb=null;
				String[] awslebValue={};
				if(!strAwsleb.equalsIgnoreCase("")){
					String[] awsleb = strAwsleb.split(";");
					awslebValue = awsleb[0].split("=");					
					ckAwsleb = new Cookie("AWSELB", awslebValue[1].trim());
					appCookies.add(ckAwsleb);
				}
								
				appCookies.add(new Cookie("JSESSIONID", sesssionNameValue[1].trim()));
				
				strWSUser = BaseClass.strCurrentUser;
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
	  }

	/**
	 * @param strProjAudienceDetails
	 * @param bIsProject
	 * @return
	 * @author Deepen Shah
	 * @since 02 Jun 2016
	 */
	public boolean deleteProjectOrAudience(String strProjAudienceDetails,boolean bIsProject){
		boolean bStatus=false;
		
		String[] ids = strProjAudienceDetails.split("-");
		String strURL=BaseClass.property.getProperty("URL");
		strURL=strURL.substring(0, (strURL.indexOf("/", strURL.indexOf("/")+2))+1);
		
		if(bIsProject){
			strURL = strURL + MessageFormat.format(strProjectPath, ids[0],ids[1]);				
		}else{
			strURL = strURL + MessageFormat.format(strAudiencePath, ids[0],ids[1],ids[2]);
		}
		System.out.println("Final URL " + strURL);
	
		try{
			Client client = Client.create();
			WebResource webResource = client.resource(strURL);
			
			WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
			long lCurrentTime = new Date().getTime();
			if(appCookies.size()==0 || !strWSUser.equalsIgnoreCase(BaseClass.strCurrentUser) || Math.abs(lCurrentTime-lCookiGenTime) > TimeUnit.MINUTES.toMillis(14)){
				System.out.println("No cookie found");
				loginToApplicationUsingWebService();
			}
			
			for(Cookie ck:appCookies)
				builder = builder.cookie(ck);
			
			ClientResponse resp = builder.delete(ClientResponse.class);
			
			if (resp.getStatus() != 200) 
				   throw new RuntimeException("Deletion Failed : is it project - "+ bIsProject + 
			" Details " + strProjAudienceDetails + " HTTP error code : "	+ resp.getStatus() + resp.getEntity(String.class));
								
			
			String output = resp.getEntity(String.class);
			System.out.println("Deletion result is " + output + " for " + strProjAudienceDetails);
			bStatus = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		return bStatus;
	}

	/**
	 * @param strProjectClientIds
	 * @param strProjectName
	 * @param strProjectDesc
	 * @return
	 */
	public boolean updateProjectNameAndDescription(String strProjectClientIds, String strProjectName,String strProjectDesc){
		boolean bStatus = false;
		
		String strInJson = "{\"name\":\""+strProjectName+"\",\"description\":\""+strProjectDesc+"\"}";
		
		String[] ids = strProjectClientIds.split("-");
		String strURL=BaseClass.property.getProperty("URL");
		strURL=strURL.substring(0, (strURL.indexOf("/", strURL.indexOf("/")+2))+1);
		
		strURL = strURL + MessageFormat.format(strProjectPath, ids[0],ids[1]);	
		
		System.out.println("Final URL " + strURL);
		System.out.println("Input json " + strInJson);
		
		try{
			DefaultClientConfig config = new DefaultClientConfig();
		    config.getProperties().put(URLConnectionClientHandler.PROPERTY_HTTP_URL_CONNECTION_SET_METHOD_WORKAROUND, true);
			Client client = Client.create(config);
			
			WebResource webResource = client.resource(strURL);
			
			WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
			long lCurrentTime = new Date().getTime();
			if(appCookies.size()==0 || !strWSUser.equalsIgnoreCase(BaseClass.strCurrentUser) || Math.abs(lCurrentTime-lCookiGenTime) > TimeUnit.MINUTES.toMillis(14)){
				System.out.println("No cookie found");
				loginToApplicationUsingWebService();
			}
			
			for(Cookie ck:appCookies)
				builder = builder.cookie(ck);
			
			ClientResponse resp = builder.method("Patch", ClientResponse.class, strInJson);
			//ClientResponse resp = builder.put(ClientResponse.class, strInJson);
			
			if (resp.getStatus() != 200) 
				   throw new RuntimeException("Modification Failed : Project Ids " + strProjectClientIds+" HTTP error code : "	+ resp.getStatus() + resp.getEntity(String.class));
								
			
			String output = resp.getEntity(String.class);
			System.out.println("Deletion result is " + output);
			bStatus = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return bStatus;
	}
	
	/** Method to call PATCH service to update project name and description
	 * 
	 * @param strProjectClientIds
	 * @param strProjectName
	 * @param strProjectDesc
	 * @return
	 * @author Deepen Shah
	 * @since 15 Jun 2016
	 */
	public boolean updateProjectNameAndDescriptionByHttpClient(String strProjectClientIds, String strProjectName,String strProjectDesc){
		boolean bStatus = false;
		
		RestTemplate restTemplate = new RestTemplate();
		
		//Snippet to work with PATCH
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(10000);
		requestFactory.setReadTimeout(10000);
		
		restTemplate.setRequestFactory(requestFactory);
		//End: Snippet
		
		String strInJson = "{\"name\":\""+strProjectName+"\",\"description\":\""+strProjectDesc+"\"}";
		
		String[] ids = strProjectClientIds.split("-");
		String strURL=BaseClass.property.getProperty("URL");
		strURL=strURL.substring(0, (strURL.indexOf("/", strURL.indexOf("/")+2))+1);
		
		String strLoginURL = strURL + strLoginPath;
		strURL = strURL + MessageFormat.format(strProjectPath, ids[0],ids[1]);	
		
		System.out.println("Final URL " + strURL);
		System.out.println("Input json " + strInJson);
		
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));
	    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
	    
	    //Login
	    String request = "{\"username\":\""+BaseClass.strCurrentUser+"\",\"password\":\""+BaseClass.strCurrentUserPass+"\"}";
	    HttpEntity<String> entity = new HttpEntity<String>(request, headers);
	    ResponseEntity<String> responseEntity = restTemplate.exchange(strLoginURL, HttpMethod.POST, entity,String.class);
	    //Login:End
	    
	    //Setting cookie
	    headers.add("Cookie", responseEntity.getHeaders().getFirst("Set-Cookie"));
	    entity = new HttpEntity<String>(strInJson, headers);
	    
	    ResponseEntity<String> updateProjectResp = restTemplate.exchange(strURL,HttpMethod.PATCH, entity, String.class);
	 
	    System.out.println("Resp " + updateProjectResp.toString());
	    
		
		return bStatus;
	}

}
