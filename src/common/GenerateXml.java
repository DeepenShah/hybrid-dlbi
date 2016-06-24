package common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.BeforeSuite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class GenerateXml{
	
	@BeforeSuite   
	public void generateXMLForSuite() 
	{
		System.out.println("generateXMLForSuit- started");	
		String browserName[] = { "firefox", "chrome", "IE" };
		Element browserParameterElement;
		Element propertyParameterElement;	
		Element listenerOneElement;
		Element listenerTwoElement;
		Element platformParameterElement;
		Element listenersElement;
		Element testElement;
		Element rootElement;
		Element classesElement;
		Element classElement;			
		FileInputStream inputStream = null;			 
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		try {
			
			inputStream = new FileInputStream("Test Data"+File.separator+"testcasesheet.xls");
			workbook = new HSSFWorkbook(inputStream);
			sheet = workbook.getSheet("TestcasesList");
	
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			rootElement = doc.createElement("suite");
			rootElement.setAttribute("name", "IDIOM-RegressionSuite");
			//rootElement.setAttribute("parallel", "none");
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {				
				
				HSSFRow row = sheet.getRow(i);
				String testCaseName = row.getCell(0, Row.CREATE_NULL_AS_BLANK)
						.getStringCellValue();			
				String packageName= row.getCell(4, Row.CREATE_NULL_AS_BLANK)
						.getStringCellValue();			
				
				
				for (int j =1; j <= 3; j++) {					
						
					if (row.getCell(j, Row.CREATE_NULL_AS_BLANK)
								.getStringCellValue().equalsIgnoreCase("Y")) {				
							
								
								testElement = doc.createElement("test");								
								classElement = doc.createElement("class");								
								testElement.setAttribute("name", testCaseName + "_"
										+ browserName[j-1]);
								browserParameterElement = doc
										.createElement("parameter");
								browserParameterElement.setAttribute("name",
										"browser");
								browserParameterElement.setAttribute("value",
										browserName[j-1]);								
								platformParameterElement = doc
										.createElement("parameter");
								platformParameterElement.setAttribute("name",
										"platform");
								platformParameterElement.setAttribute("value",
										"Windows");					
							
								propertyParameterElement = doc
										.createElement("parameter");
							
								
								propertyParameterElement.setAttribute("name",
										"properties");
								propertyParameterElement.setAttribute("value",
										BaseClass.strTestDataPropPath);
								
								classesElement = doc.createElement("classes");
								classElement.setAttribute("name", packageName+"."+testCaseName);
								classesElement.appendChild(classElement);							
								testElement.appendChild(classesElement);
								testElement.appendChild(browserParameterElement);
								testElement.appendChild(platformParameterElement);
								testElement.appendChild(propertyParameterElement);
								rootElement.appendChild(testElement);
								
							}

						}
					
						
					}
				
	  listenersElement = doc.createElement("listeners");
	  listenerOneElement = doc
				.createElement("listener");
	  listenerOneElement.setAttribute("class-name",
				"com.testng.listeners.TestExecutionListener");
	  listenerTwoElement = doc
				.createElement("listener");
	  listenerTwoElement.setAttribute("class-name",
				"com.testng.listeners.TestInvokeListener");
	  listenersElement.appendChild(listenerOneElement);
	  listenersElement.appendChild(listenerTwoElement);
	  rootElement.appendChild(listenersElement);					  
	  doc.appendChild(rootElement);
	  TransformerFactory transFacroty = TransformerFactory
				.newInstance();						
	  Transformer transformer = transFacroty.newTransformer();
	  DOMSource source = new DOMSource(doc);
	  StreamResult result = new StreamResult(new File("testng.xml"));
	  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	  transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
	  transformer.transform(source, result);
					
		
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (ParserConfigurationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (TransformerConfigurationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (TransformerException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	
	}

	
}