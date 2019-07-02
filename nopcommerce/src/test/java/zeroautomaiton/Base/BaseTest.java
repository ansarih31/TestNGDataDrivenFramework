package zeroautomaiton.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import zeroautomation.common.ExtentManager;

public class BaseTest {
public WebDriver driver;
public Properties prop;
public ExtentReports rep=ExtentManager.getInstance();
public ExtentTest test;
//public SoftAssert softAssert;
	
	public void init() {
		if(prop==null) {
			prop=new Properties();
			try {
				FileInputStream fs=new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//ProjectConfig.Properties");
				prop.load(fs);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(prop.getProperty("appurl"));
		
	}
	
	
	
	public void openBrowser(String browser) {
	
		if(browser.equals("Chrome")) {
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Driver\\chromedriver.exe");
		driver=new ChromeDriver();}
		else if(browser.equals("ie")) {
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\Driver\\IEDriverServer.exe");
			driver=new ChromeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	public void navigateURL(String urlKey) {
		driver.navigate().to(urlKey);
	}
	
	public void getURL(String urlKey) {
		driver.get(urlKey);
	}
	
	
	public void sendKeys(String LocatorKey,String data) {
		getElement(LocatorKey).sendKeys(data);
		
	}
	
	public void clickElement(String LocatorKey) {
		getElement(LocatorKey).click();
	}
	
	//Find element and return it
	public WebElement getElement(String LocatorKey) {
		WebElement e=null;
		try {
			if(LocatorKey.endsWith("_id")) 
			e=driver.findElement(By.id(prop.getProperty(LocatorKey)));
		else if(LocatorKey.endsWith("_xpath"))
			e=driver.findElement(By.xpath(prop.getProperty(LocatorKey)));
		else if(LocatorKey.endsWith("_name"))
			e=driver.findElement(By.name(prop.getProperty(LocatorKey)));
		else {
			reportFailure("Locator not correct"+LocatorKey);
			Assert.fail("Locator not correct"+LocatorKey);
		}
			
		}
		catch(Exception ex) {
			reportFailure(ex.getMessage());
			ex.printStackTrace();
			Assert.fail("Failed Test"+ex.getMessage());
		}
		return e;
	}
	
	
	
	//*******************************ValidationsMethod******************************************************
	
	public boolean verifyTitle() {
		return true;
	}
	
	public boolean isElementPresent(String locatorKey){
		List<WebElement> elementList=null;
		if(locatorKey.endsWith("_id"))
			elementList = driver.findElements(By.id(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_name"))
			elementList = driver.findElements(By.name(prop.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_xpath"))
			elementList = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
		else{
			reportFailure("Locator not correct - " + locatorKey);
			Assert.fail("Locator not correct - " + locatorKey);
		}
		
		if(elementList.size()==0)
			return false;	
		else
			return true;
	}
	
	
	public boolean verifyText(String locatorKey,String expectedTextKey){
		String actualText=getElement(locatorKey).getText().trim();
		String expectedText=prop.getProperty(expectedTextKey);
		if(actualText.equals(expectedText))
			return true;
		else 
			return false;
		
	}
	//*************************************Reporting Methods********************
	
	public void reportPass(String msg) {
		test.log(LogStatus.PASS, msg);
		
	}
	
	public void reportFailure(String msg) {
		test.log(LogStatus.FAIL, msg);
		takeScreenshot();
		Assert.fail(msg);
	}

	public void takeScreenshot() {
		//Filename of the scressnshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":","_").replace(" ","_")+".png";
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"//Screenshots//"+screenshotFile));
		}catch(IOException e) {
			e.printStackTrace();
		}
		//Put Screesnhot file in report
		 test.log(LogStatus.INFO,"Screenshot ->"+test.addScreenCapture(System.getProperty("user.dir")+"//Screenshots//"+screenshotFile));
	}
	



}



