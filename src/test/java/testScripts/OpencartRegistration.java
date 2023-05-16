package testScripts;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OpencartRegistration 
{
	WebDriver driver;
	@BeforeMethod
	
		public void setup()
		{
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
			driver.manage().window().maximize();
			
		}
  @Test(priority=1)
  public void register()
  {
	  driver.get("https://demo.opencart.com//");
	  driver.findElement(By.linkText("My Account")).click();
	  driver.findElement(By.xpath("//a[@class='dropdown-item'][normalize-space()='Register']")).click();
  
  
	  driver.findElement(By.id("input-firstname")).sendKeys("Ram");
	  driver.findElement(By.id("input-lastname")).sendKeys("S");
	  driver.findElement(By.id("input-email")).sendKeys("test@");
	  
  

  
	  driver.findElement(By.id("input-password")).sendKeys("Password@1");
	  

		JavascriptExecutor js2 =(JavascriptExecutor)driver;
		js2.executeScript("window.scrollBy(10, 500)");
		
		
  		WebElement subscribe = driver.findElement(By.xpath("//input[@id='input-newsletter-no']"));
		if(!subscribe.isSelected()) 
		{
			subscribe.click();
		}
  
		
		
		WebElement elem = driver.findElement(By.xpath("//input[@name='agree']"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", elem);
	
		WebElement cont = driver.findElement(By.cssSelector("button[type='submit']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", cont);
		
		
		String exp = "";
		
		WebElement m= driver.findElement(By.id("input-email"));
		  String act = m.getText();
	      System.out.println("Please enter a part following '@', 'test@' is incomplete "+ act);
	      //verify error message with Assertion
	      Assert.assertEquals(exp, act);
	      
	   

		
  }
  
 @AfterMethod
  public void teardown()
  {
	  driver.close();
  }
}
