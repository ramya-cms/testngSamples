package testScripts;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoteDriverTest {
	WebDriver driver;
  @Test
  public void test() throws MalformedURLException
  {
	  ChromeOptions options = new ChromeOptions();
	  options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
	
	  String strHub = "http://192.168.249.153:4444";
	  driver = new RemoteWebDriver(new URL(strHub),options);
	  
	  	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		driver.manage().window().maximize();
		driver.get("https://www.google.com/"); //get--dynamic refresh n launch again
		WebElement searchBox=driver.findElement(By.name("q"));
		searchBox.sendKeys("Java Tutorial");
		//searchBox.sendKeys(Keys.ENTER);
		searchBox.submit();
		System.out.println("Page title is "+driver.getTitle());
		Assert.assertEquals(driver.getTitle(), "Java Tutorial - Google Search");
  }
}
