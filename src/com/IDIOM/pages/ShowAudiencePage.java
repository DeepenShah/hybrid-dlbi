package com.IDIOM.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/** Page class for 'Show Audience' panel
 * 
 * @author Deepen Shah
 *
 */
public class ShowAudiencePage {
	
		WebDriver driver;
		public ShowAudiencePage(WebDriver driver) 
		{
			PageFactory.initElements(driver, this);
			this.driver = driver;
		}

		@FindBy(xpath="//population")
		public WebElement populationPane;
		
		@FindBy(xpath="//optimal")
		public WebElement optimalPane;
		
		@FindBy(xpath="//metrics")
		public WebElement metricsPane;
		
		@FindBy(xpath="//span[@class='iu-population__percent']//span")
		public WebElement populationPercentage;
		
		@FindBy(xpath="//span[@class='iu-population__selected ng-binding']//span")
		public WebElement populationUserCount;
		
		@FindBy(xpath="//div[@ng-show='optimal.metrics.length']//div[@class='iu-percent-bar__bar']")
		public WebElement optimalAudienceBar;
		
		@FindBys(@FindBy(xpath="//div[@class='iu-block']//div[@class='iu-block__block-content']//ul[@class='iu-audience-metrics-percentage']/li"))
		public List<WebElement> successMetricsList;
		
		@FindBy(xpath="//insights//*[@class='iu-tabs__tab']/a[text()='Projected']")
		public WebElement projectedTab;
		
		@FindBy(xpath="//insights//*[@class='iu-tabs__tab']/a[text()='Actual']")
		public WebElement actualTab;
		@FindBy(xpath="//aside[@class='sliding-panel audience-profile']")
		public WebElement showAudiencePanel;
		
		@FindBy(xpath="//*[@class='ng-scope ng-isolate-scope']/section")
		public WebElement compltePage;
		
		public String strMetricName = ".//p";
		public String strMetricPercentage =".//div[@class='iu-percent-bar']/div[@class='iu-percent-bar__value']/span";
		public String strMetricColor = ".//div[@class='iu-percent-bar']/div[@class='iu-percent-bar__bar']";
		
		/** Method to verify visibility of the element. 
		 * 
		 * @param strCase - <p> <b>Options:</b>'populationpane','optimalpane','metricspane' </p>
		 * @param strVal
		 * @return
		 * @author Deepen Shah
		 * @since 25 May 2016
		 */
		public boolean isVisible(String strCase,String strVal){
			boolean bStatus = true;
			
			WebDriverWait wait = new WebDriverWait(driver, 2);
			try{
				switch(strCase.toLowerCase()){
					case "populationpane": wait.until(ExpectedConditions.visibilityOf(populationPane));
											break;
											
					case "optimalpane": wait.until(ExpectedConditions.visibilityOf(optimalPane));
											break;
					
					case "metricspane": wait.until(ExpectedConditions.visibilityOf(metricsPane));
											break;
				}
			}catch(Exception e){
				bStatus = false;
			}
			
			
			return bStatus;
		}
		
		/** Overloaded case
		 * 
		 * @param strCase - <p> <b>Options:</b>'populationpane','optimalpane','metricspane' </p>
		 * @return
		 * @author Deepen Shah
		 * @since 25 May 2016
		 */
		public boolean isVisible(String strCase){
			return isVisible(strCase, "");
		}
		
		/**
		 * @return
		 * @author Deepen Shah
		 * @since 25 May 2016
		 */
		public int getPopulationPercentage(){			
			return Integer.parseInt(populationPercentage.getText());			
		}

		/**
		 * @return
		 * @author Deepen Shah
		 * @since 25 May 2016
		 */
		public int getPopulationUserCount(){
			return Integer.parseInt(populationUserCount.getText());
		}
		
		/**
		 * @return
		 * @author Deepen Shah
		 * @since 25 May 2016
		 */
		public double getOptimalAudiencePercentage(){
			double percentageVal = 0;
			
			String[] optimalAudienceStyle = optimalAudienceBar.getAttribute("style").trim().split(":");		
			String optimalAudiencePer = optimalAudienceStyle[1].replace("%", "").trim();
			optimalAudiencePer = optimalAudiencePer.replace(";","");
			
			percentageVal = Math.round(Double.parseDouble(optimalAudiencePer));
			
			return percentageVal;		
		}
		
		/** To get success metrics name, percentage and its color
		 * @return
		 * @author Deepen Shah
		 * @since 25 May 2016
		 */
		public ArrayList<String> getSuccessMetricsDetails(){
			ArrayList<String> successMetricsAttributes = null;
			
			if(successMetricsList.size() >= 1){
				successMetricsAttributes = new ArrayList<>();
				
				for(WebElement aMetric:successMetricsList){
					String successMetricsName =aMetric.findElement(By.xpath(strMetricName)).getText().trim();					
					String percentageVal = aMetric.findElement(By.xpath(strMetricPercentage)).getText().replace("%", "").trim();	
					
					String getBGColor = aMetric.findElement(By.xpath(strMetricColor)).getCssValue("background-color").trim();
					
					
					if(getBGColor.equals("rgba(118, 116, 115, 1)"))
						getBGColor = "grey";
					else if(getBGColor.equals("rgba(255, 15, 83, 1)"))
						getBGColor = "red";
					else
						getBGColor = "unknown";	
					
					String allAttributes = successMetricsName+":"+percentageVal+":"+getBGColor;
					System.out.println("Attributes: "+allAttributes);
					
					successMetricsAttributes.add(allAttributes);
				}
				
			}else{
				System.out.println("No success metrics added");
			}
			
			return successMetricsAttributes;
		}
		
		/** To select Projected or Actual tab.
		 * 
		 * @param strProjectedOrActual
		 * @author Deepen Shah
		 * @since 25 May 2016
		 */
		public void clickTab(String strProjectedOrActual){
			if(strProjectedOrActual.equalsIgnoreCase("projected"))
				projectedTab.click();
			else
				actualTab.click();
				
		}
		
		/** This method verifies whether the show audience panel taken only a fraction of page width(checking whether its taking less than half of page width
		 * 
		 *
		 * @author Amrutha Nair
		 * @since 16 June 2016
		 */
		
		public boolean verifyShowAudiencePanelWidth(){
			boolean status=false;
			
			String panelWidth=showAudiencePanel.getAttribute("offsetWidth");
			System.out.println("Panel:"+panelWidth);
			String pageWidth=compltePage.getAttribute("offsetWidth");
			System.out.println("Page:"+pageWidth);
			
			int intPanel=Integer.parseInt(panelWidth);
			int intPage=Integer.parseInt(pageWidth);
			intPage=Math.round(intPage/2);
			if(intPanel<intPage){
				status=true;
			}
			
			return status;
			
		}
		
	
}
