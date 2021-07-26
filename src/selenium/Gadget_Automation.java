package selenium;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Gadget_Automation {
	static WebDriver driver;
    static String url;
    static String browser;
    @Parameters({"browser"})
    @BeforeMethod
	public static void invokeBrowser(String browser) throws Exception{
		
		//Reading data from property file
		
				Properties prop = new Properties();
				InputStream readFile = null;
			
				try {
					readFile = new FileInputStream("configure.properties");
				 prop.load(readFile);
                 prop.getProperty("chrome_driver");
				 prop.getProperty("chromeDriverLocation");
			     prop.getProperty("gecko_driver");
				 prop.getProperty("geckoDriverLocation");
					url = prop.getProperty("url");
					
				} catch (IOException io) {
					io.printStackTrace();
				} finally {
					if (readFile != null) {
						readFile.close();
					}
				}
		//configure the browser
		if(browser.equalsIgnoreCase("chrome")){
			System.setProperty("chrome_driver","chromeDriverLocation");
			System.out.println("new branch is created");
		     driver=new ChromeDriver(); //To open in Chrome
		}else if(browser.equalsIgnoreCase("gecko")){
				System.setProperty("gecko_driver","geckoDriverLocation");
			     driver=new FirefoxDriver();// To open in Fire fox
		}else {
			throw new Exception("Web Driver not supported");
		}
	
		 driver.manage().window().maximize(); //To maximize the browser
		 driver.manage().timeouts().implicitlyWait(25,TimeUnit.SECONDS); 
    }
    @AfterMethod
	public static void closeBrowser() {
	 driver.close(); //To close the browser
	}
	
	public void goToURl() {
		driver.get(url);
	}
	public void search() {
		//2.Enter “Bluetooth headphone” in search textbox  
		driver.findElement(By.name("keyword")).sendKeys("Bluetooth headphones");
		//3.Click search button
				driver.findElement(By.className("searchTextSpan")).click();
	}
	public void sort() {
		//4.Click sortBy Relevance list box 
				driver.findElement(By.xpath("//span[contains(text(),'Sort by:')]")).click();
		//5.Select popularity from list box 
		driver.findElement(By.cssSelector("div.col-xs-24.reset-padding.marT22:nth-child(29) div.col-xs-19.reset-padding div.comp.comp-right-wrapper.ref-freeze-reference-point.clear div.search-result-header:nth-child(1) div.seach-msg-sec.clearfix div.sorting-sec.animBounce:nth-child(7) ul.sort-value > li.search-li:nth-child(2)")).click();
		
	}
	public void setPrice() {
		//6.Select price range between 700 to 1400
				driver.findElement(By.name("fromVal")).clear();
				driver.findElement(By.name("fromVal")).sendKeys("700");
				driver.findElement(By.name("toVal")).clear();
				driver.findElement(By.name("toVal")).sendKeys("1400");
				//7.click go button 
				driver.findElement(By.xpath("//div[contains(text(),'GO')]")).click();
		
	}
	
	@Test
	public static void GadgetAutomation() {
		Gadget_Automation obj = new Gadget_Automation();
		
		obj.goToURl();
		obj.search();
		obj.sort();
		obj.setPrice();
		
    	WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content_wrapper\"]/div[7]/div[4]/div[3]/div[6]/div[1]/div/a")));
		//8.Get and Print the first 5 bluetooth headphones name and price in the console
		printProductInfo("//*[@id=\"products\"]/section[1]/div",4);
		printProductInfo("//*[@id=\"products\"]/section[2]/div",1);
		
	}		
	
	private static void printProductInfo(String string, int i) {
		List<WebElement> ele = driver.findElements(By.xpath(string));
		
		for(WebElement e : ele) {
			if(i==0) {
				break;
			}
			String[] infoarr = e.getText().split("\n");
			if(infoarr[0].contains("!")) {
				String info = "Product Name: "+infoarr[1]+";Price: "+infoarr[3];
				System.out.println(info);
				i--;
			}
			else {
			//Arrays.asList(infoarr).stream().forEach((str)->System.out.println(str));
			//System.out.println(infoarr.length);
			String info = "Product Name: "+infoarr[0]+";Price: "+infoarr[2];
			System.out.println(info);
			i--;
			}
		}
		
	}
	public static void main(String[] args) {
		//to run in multiple browsers
		//1.for chrome 
		try {
			invokeBrowser("chrome");
			GadgetAutomation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeBrowser();
		}
		// for firefox
		try {
			invokeBrowser("gecko");
			GadgetAutomation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeBrowser();
		}
	}

}
