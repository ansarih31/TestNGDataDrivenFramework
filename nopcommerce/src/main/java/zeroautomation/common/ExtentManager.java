package zeroautomation.common;

import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if(extent==null) {
			Date d=new Date();
			String filename=d.toString().replace(":","_").replace(" ","_")+".html";
			extent=new ExtentReports(System.getProperty("user.dir")+"//Reports//"+filename,true,DisplayOrder.NEWEST_FIRST);
			extent.loadConfig(new File(System.getProperty("user.dir")+"//ReportsConfig.xml"));
			
			extent.addSystemInfo("Selenium Version", "2.53.0").addSystemInfo(
					"Environment", "QA");
		}
		
		
		return extent;
	
	}
}
