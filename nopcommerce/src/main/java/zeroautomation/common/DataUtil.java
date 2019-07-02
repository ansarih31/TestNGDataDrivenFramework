package zeroautomation.common;

import java.util.Hashtable;

public class DataUtil {
	
	public static Object[][] getTestData(Xls_Reader xls,String sheetName){
		
		int colStartRowNum=1;
		int dataStartRowNum=2;
		
		//calculate rows of data
		int rows=0;
		while(!xls.getCellData(sheetName, 0, dataStartRowNum+rows).equals("")) {
			rows++;
		}
		
		System.out.println("Total Rows are: "+rows);

		//Total columns
		int cols=0;
		while(!xls.getCellData(sheetName, cols, dataStartRowNum).equals("")) {
			cols++;
		}
		System.out.println("Total Columns are: "+cols);
		Object[][] data=new Object[rows][1];
		int dataRow=0;
		Hashtable<String,String>table=null;
		for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++) {
			table=new Hashtable<String,String>();
			for(int cNum=0;cNum<cols;cNum++) {
				String key=xls.getCellData(sheetName, cNum, colStartRowNum);
				String value=xls.getCellData(sheetName, cNum, rNum);
				table.put(key, value);
				}
			data[dataRow][0]=table;
			dataRow++;
		}
		
		return data;
	}

}
