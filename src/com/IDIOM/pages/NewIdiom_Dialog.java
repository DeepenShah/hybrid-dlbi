package com.IDIOM.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewIdiom_Dialog {
public WebDriver driver;
	
	public NewIdiom_Dialog(WebDriver driver) 
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="html/body/div[2]/div[1]/form/input")
	private WebElement txt_name;
	
	@FindBy(xpath="(//button[text()='Create'])[1]")
	private WebElement btn_create;
	
	public void deleteChars() throws AWTException, InterruptedException{
		Robot robot = new Robot();
		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_SHIFT);		
		robot.keyPress(KeyEvent.VK_HOME);
		robot.keyRelease(KeyEvent.VK_HOME);
		robot.keyRelease(KeyEvent.VK_SHIFT);	
		
		Thread.sleep(3000);
		robot.keyPress(KeyEvent.VK_DELETE);
		robot.keyRelease(KeyEvent.VK_DELETE);
		Thread.sleep(5000);
	}
	
	
	public void enterIdiomName(String name){		
	txt_name.sendKeys(name);
	btn_create.click();
	}	

}
