package testScripts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFAnchor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class LoginTest {

	WebDriver driver;
	Properties prop;
	/*@Parameters("browser")
	@BeforeMethod
	public void setUp(String strBrowser) {
		if(strBrowser.equalsIgnoreCase("chrome")) {
	     driver=new ChromeDriver() ;
		}
		else if(strBrowser.equalsIgnoreCase("edge")) {
	     driver=new EdgeDriver();
		}
		  driver.manage().window().maximize();
		
	}*/
	
	
	@BeforeMethod
	public void setup() throws IOException {
		
		String path=System.getProperty("user.dir")+"//src//test//resources//configFiles//config.properties";
		FileInputStream fin = new FileInputStream(path);
		prop = new Properties();
		prop.load(fin);
		String strBrowser = prop.getProperty("browser");
		if(strBrowser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		}
		else if (strBrowser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
		driver.manage().window().maximize();
	}
	
	
	
	
	
	@Test(dataProvider="logindata")
	public void vaildLogin(String strUser, String strpwd) {
	
		    driver.get(prop.getProperty("url"));
		    driver.findElement(By.id(readObjPath("user_name"))).sendKeys(strUser);
		    driver.findElement(By.id(readObjPath("user_pwd"))).sendKeys(strpwd);
		    driver.findElement(By.tagName(readObjPath("loginBtn"))).click();
		    
//		    
//		  boolean isDisp =   driver.findElement(By.cssSelector(readObjPath("SuccessMsg"))).isDisplayed();
//		  Assert.assertTrue(isDisp);
	}  
		  
	//To Use excel as objectrepo
		  
	public String readObjPath(String objName) {
		String objPath = "";
		String path = System.getProperty("user.dir")+"//src//test//resources//dataFiles/loginPage.xlsx";
		
		//HSSF...-> .xls
		
		//XSSFAnchor -> .xlsx
		FileInputStream fin;
		XSSFWorkbook workbook = null;
		try {
			fin = new FileInputStream(path);
			workbook = new XSSFWorkbook(fin);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XSSFSheet loginSheet = workbook.getSheet("loginSheet");
		int numRows = loginSheet.getLastRowNum();
		for(int i=1;i<=numRows;i++) {
			XSSFRow row = loginSheet.getRow(i);
			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(objName)) {
				objPath = row.getCell(1).getStringCellValue();
			}
				
		}
		
		return objPath;
	}
		
	
	@DataProvider(name="logindata")
	public Object [][] getData() throws CsvValidationException, IOException{
		String path=System.getProperty("user.dir")+"//src//test//resources//dataFiles/loginData.csv";
		CSVReader reader=new CSVReader (new FileReader(path));
		String cols[];
		ArrayList <Object> dataList=new ArrayList<Object>();
		while((cols=reader.readNext()) !=null){
			Object record[]= {cols[0],cols[1]};
			dataList.add(record);
		}
		reader.close();
		return dataList.toArray(new Object[dataList.size()][]);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}
