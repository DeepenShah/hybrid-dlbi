package Deepen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import common.BaseClass;

public class DLoveToCode {

	static List<Cookie> appCookies = new ArrayList<>();
	public static final String strProjectPath = "idiom-services/clients/{1}/projects/{0}";
	public static final String strLoginPath = "idiom-services/security/auth";
	public static final String strAudiencePath = "idiom-services/clients/{2}/projects/{1}/audiences/{0}";
	public static final String strProjects = "idiom-services/clients/{0}/projects";
	public static String strWSUser="";
	static String strAppUrl = "https://idiom.digitaslbi.com/";
	static String strUser="admin.test@digitaslbi.com";
	static String strPass="P@ssword";
	static int iClientId = 2;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//mapExpFailure();
		//deleteProjects();	
	}
	
	public static void mapExpFailure() throws Exception{
		List<String> strExpFailure = new ArrayList<String>();
		/*List<String> strAcualFailure = new ArrayList<String>();
		NavigableSet<String> set = new TreeSet<String>();*/
		
		String strFile = "C:/Users/deeshah/Desktop/FailureMapping.xlsx";
		
		
		FileInputStream inputStream = new FileInputStream(strFile);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheet("Failures");
	
		for (int i=0;i<=sheet.getLastRowNum();i++) {
			XSSFRow row = sheet.getRow(i);
			String strA = row.getCell(0,Row.CREATE_NULL_AS_BLANK).getStringCellValue().toLowerCase();		
			strExpFailure.add(strA);			
		}
		
		System.out.println("Exp failure " + strExpFailure);
		
		for (int i=0;i<=sheet.getLastRowNum();i++) {			
			XSSFRow row = sheet.getRow(i);
			String strB= row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue().toLowerCase();
			boolean bStatus = false;
			
			System.out.println("Looking for " + strB);
			for(String str:strExpFailure){
				
				if(!str.equalsIgnoreCase(""))
					if(strB.contains(str))
						bStatus = true;
			
			}
			
			if(bStatus){
				System.out.println("Match found " + strB);
				row.getCell(3,Row.CREATE_NULL_AS_BLANK).setCellValue("Yes");				
			}



		}		
		inputStream.close(); //Close the InputStream         
        FileOutputStream output_file =new FileOutputStream(new File(strFile));  //Open FileOutputStream to write updates           
        workbook.write(output_file); //write changes           
        output_file.close();  //c		
	}
	
	public static void deleteProjects(){
		
				
		//String strURL = strURL + MessageFormat.format(strProjectPath, ids[0],iClientId);	
		try{
			Client client = Client.create();
			
			JSONArray allProjects = getListOfProjects(MessageFormat.format(strAppUrl+strProjects, iClientId));
			
			for(int i=0;i<allProjects.length();i++){
				String name =allProjects.getJSONObject(i).getString("name");
				if(StringUtils.countMatches(name, "-") >=3 || name.contains("Automation Project")){
				
					int pid = allProjects.getJSONObject(i).getInt("pid");
					System.out.println("Deleting project " + pid);
					WebResource webResource = client.resource(MessageFormat.format(strAppUrl+strProjectPath,String.valueOf(pid),iClientId));
					
					WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
					
					for(Cookie ck:appCookies)
						builder = builder.cookie(ck);
					
					ClientResponse resp = builder.delete(ClientResponse.class);
					
					if (resp.getStatus() != 200) 
						   throw new RuntimeException("Deletion Failed for project: " + name+" HTTP error code : "	+ resp.getStatus() + resp.getEntity(String.class));
										
					
					String output = resp.getEntity(String.class);
					System.out.println("Delete " + name + " is " + output);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static void loginToApplication(){
		
		  	String credential = "{\"username\":\""+strUser+ "\",\"password\":\""+strPass+"\"}";	
			
			String strLoginURL = strAppUrl + strLoginPath;
			Client client = Client.create();
			WebResource login = client.resource(strLoginURL);
			
			//Login to the application		
			try{
				ClientResponse resp = login.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,credential);
			
			
				if (resp.getStatus() != 200) 
					   throw new RuntimeException("Login Failed : HTTP error code : "	+ resp.getStatus());
			
				System.out.println("Response is  " + resp.getEntity(String.class));
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
	
	public static JSONArray getListOfProjects(String strListProjUrl){
		Client client = Client.create();
		WebResource projectsWS = client.resource(strListProjUrl);
		JSONArray output = null;
		try{
			
			WebResource.Builder builder = projectsWS.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
			if(appCookies.size()==0 || !strWSUser.equalsIgnoreCase(BaseClass.strCurrentUser)){
				System.out.println("No cookie found");
				loginToApplication();
			}
			
			for(Cookie ck:appCookies)
				builder = builder.cookie(ck);
			
			ClientResponse resp = builder.get(ClientResponse.class);
			
			if (resp.getStatus() != 200) 
				   throw new RuntimeException("Failed to fetch list of projects. HTTP error code : "	+ resp.getStatus() + resp.getEntity(String.class));
								
			
			output = new JSONArray(resp.getEntity(String.class));
			//System.out.println("Projects " + output.toString());			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return output;
	}

}
