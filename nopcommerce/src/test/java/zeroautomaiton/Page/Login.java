package zeroautomaiton.Page;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;

import zeroautomaiton.Base.BaseTest;
import zeroautomation.common.DataUtil;
import zeroautomation.common.Xls_Reader;

public class Login extends BaseTest{
	SoftAssert softAssert;
	String sheetName="Login";
	Xls_Reader xls;
	
	
	@Test(dataProvider="getData")
	public void validateLogin(Hashtable<String,String> data) {
	  
	  test=rep.startTest("Login Test");
		 
	  test.log(LogStatus.INFO, "Start Login Test");
	  test.log(LogStatus.INFO, data.toString());
	  
	  if(data.get("Runmode").equals("N")) {
		  test.log(LogStatus.SKIP, "Skipping test as Runmode is N");
		  throw new SkipException("Skiiping test as Runmode is N");
	  }
	  
	  openBrowser(prop.getProperty("browser"));
	  
	  test.log(LogStatus.INFO, "Opened the browser");
	  
	  getURL(prop.getProperty("appurl"));
	  
	 // verifyText("signintext_xpath","signintext");
	  
	  if(!isElementPresent("txtemail_xpath")){
	  //check if email id present
	  reportFailure("Email field is not present");}
	  
	  sendKeys("txtemail_xpath",data.get("User"));
	  
	  sendKeys("txtpwd_xpath",data.get("Password"));
	  
	  clickElement("btnlogin_xpath");
	  
	  verifyTitle();
	  
	  test.log(LogStatus.PASS,"Login test is Passed");
	  takeScreenshot();
	  
  }
  
  @BeforeMethod
  public void init() {
	  softAssert=new SoftAssert();
	   }
  
  
  @AfterMethod
  public void quit() {
	  try {
		  softAssert.assertAll();
	  }catch(Error e) {
		  test.log(LogStatus.FAIL, e.getMessage());
	  }
	  rep.endTest(test);
	  rep.flush();
  }
  
  
	@DataProvider
	public Object[][] getData(){
	super.init();
	xls=new Xls_Reader("Login.xlsx");
	return DataUtil.getTestData(xls,sheetName);
	}
  
  
  
  
  
}
