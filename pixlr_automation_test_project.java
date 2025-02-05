package pixlr_automation_test_project;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class pixlr_automation_test_project 
{
		
	WebDriver driver;
	String baseurl="https://pixlr.com/";
	@BeforeTest
	public void setup()
	{
		
		driver=new ChromeDriver();
		
	}
	
	@BeforeMethod
	public void urlloading()
	{
		
		driver.get(baseurl);
		driver.manage().window().maximize();

	}
	
	
	@Test(priority=1)
	public void test1()
	{
		
        // Check if URL contains HTTPS
        if (driver.getCurrentUrl().startsWith("https://")) 
        {
            System.out.println("The link contains HTTPS");
        } 
        else 
        {
            System.out.println("The link does not contain HTTPS");
        }
	}
	
	
	@Test(priority=2)
	public void test2()
	{
		//Title verification
		String expectedTitle = "Free Online AI Photo Editor, Image Generator & Design tool";
	    String actualTitle = driver.getTitle();
	    
	    if (actualTitle.equals(expectedTitle)) 
	    {
	        System.out.println("Title verification passed: " + actualTitle);
	    } 
	    else 
	    {
	        System.out.println("Title verification failed. Expected: " + expectedTitle + ", but found: " + actualTitle);
	    }
	}
	
	@Test(priority=3)
	public void test3()
	{
		//The logo is available or not
		WebElement logo=driver.findElement(By.xpath("//*[@id=\"logo\"]/img"));
		
        if (logo.isDisplayed()) 
        {
            System.out.println("The logo is visible on the webpage.");
        } 
        else 
        {
            System.out.println("The logo is not visible on the webpage.");
        }

	}
	
	
	@Test(priority=4)
	public void test4()
	{
		//Has a pirticular set of words in the site
		String a=driver.getPageSource();
		//System.out.println(a);
		String pgsource=driver.getPageSource();
		
		
		if(pgsource.contains("pixlr"))
		{
			System.out.println("It has the word");
		}
		else
		{
			System.out.println("It does not have the word");
		}
		
	}
	
	@Test(priority=5)
    public void test5() 
   {
		//Mouse howser
	    driver.findElement(By.xpath("//*[@id=\"photo-editing-text\"]/div/a[1]")).click();
        WebElement mouse = driver.findElement(By.xpath("//*[@id=\"head-menu\"]/div[1]"));
        org.openqa.selenium.interactions.Actions act = new org.openqa.selenium.interactions.Actions(driver);
        act.moveToElement(mouse).perform();

        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(5))
        .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"head-menu-drop\"]/div/a[11]")));
        driver.findElement(By.xpath("//*[@id=\"head-menu-drop\"]/div/a[11]")).click();
    }
	
	@SuppressWarnings("deprecation")
	@Test(priority=6)
    public void test6() 
    {
		//All links are working or not
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement linkElement : links) {
            String url = linkElement.getAttribute("href");
            verifyLink(url);
        }
    }

    public void verifyLink(String link) 
    {
        if (link == null || link.isEmpty()) 
        {
            System.out.println("Empty or null link found.");
            return;
        }

        try 
        {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode >= 200 && responseCode < 400) 
            {
                System.out.println("Valid link: " + link);
            } else 
            {
                System.out.println("Invalid link (Response: " + responseCode + "): " + link);
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Error checking link: " + link + " - " + e.getMessage());
        }
    }

	@Test(priority=7)
	public void test7()
	{
		//Find a particular xpath from the page
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    By xpath = By.xpath("/html/body/section[3]/div[2]/div[1]/a");
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    for (int i = 0; i < 20; i++) 
	    {
	        List<WebElement> elements = driver.findElements(xpath);
	        if (!elements.isEmpty() && elements.get(0).isDisplayed()) 
	        {
	            WebElement element = elements.get(0);
	            element.click();
	            break;
	        }
	        js.executeScript("window.scrollBy(0, 500);");
	    }
	    
	}
		
			
	@Test(priority=8)
	public void main() throws IOException
	{
		//Data driven testing
	    driver.findElement(By.xpath("//*[@id=\"head-login\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"choose-email\"]/strong")).click();
		File f=new File("D:\\data.xlsx");
		FileInputStream fi=new FileInputStream(f);
		XSSFWorkbook wb=new XSSFWorkbook(fi);
		XSSFSheet sh=wb.getSheet("Sheet1");
		System.out.println(sh.getLastRowNum());

	for(int i=1;i<=sh.getLastRowNum();i++)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		String username=sh.getRow(i).getCell(0).getStringCellValue();
		System.out.println("username="+username);
		String pswd=sh.getRow(i).getCell(1).getStringCellValue();
		System.out.println(pswd);
		
		driver.findElement(By.xpath("//*[@id=\"entry-email\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"entry-email\"]")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id=\"entry-password\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"entry-password\"]")).sendKeys(pswd);
		
		driver.findElement(By.xpath("//*[@id=\"entry-submit\"]")).click();
		
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		//WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"vl-flyout-nav\"]/ul/li[3]/div[2]/div[1]/nav[1]/ul/li[1]/a"))); 
		//driver.findElement(By.xpath("//*[@id=\"vl-flyout-nav\"]/ul/li[3]/div[2]/div[1]/nav[1]/ul/li[1]/a")).click();
	}
	driver.navigate().refresh();
	}
	
	
	@Test(priority=9)
	public void test9()
	{
		//Open in new tab and close the new tab
		String newTabScript="window.open(arguments[0].href, '_blank');";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(newTabScript, driver.findElement(By.xpath("//*[@id=\"photo-editing-text\"]/div/a[1]")));
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.navigate().refresh();
        driver.close();
        driver.switchTo().window(tabs.get(0));
	}
	
	
	@Test(priority=10)
	public void test10() throws AWTException, InterruptedException 
	{
		//Open an Image and edit
	    driver.findElement(By.xpath("//*[@id=\"photo-editing-text\"]/div/a[1]")).click();
	    driver.findElement(By.xpath("//*[@id=\"splash-open-image\"]")).click();

	    Thread.sleep(2000);

	    Robot robot=new Robot();
	    String filePath ="D:\\ZoTaC\\Pictures\\2025-Hennessey-Venom-F5-M-Roadster-003.jpg";


	    StringSelection selection=new StringSelection(filePath);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);


	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);

	
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    
		driver.findElement(By.xpath("//*[@id=\"tool-adjust\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"auto-fix\"]")).click();
		
	    WebElement scrollbar = driver.findElement(By.xpath("//*[@id=\"adjust-content\"]/div[2]"));
	    Actions actions = new Actions(driver);
	    actions.clickAndHold(scrollbar).moveByOffset((int) 171.107, (int) 160.65).release().perform();
	
	    
	    WebElement slider1 = driver.findElement(By.xpath("//*[@id=\"brightness\"]/div[2]/div[2]/div"));
	    Actions moveSlider1 = new Actions(driver);
	    moveSlider1.clickAndHold(slider1).moveByOffset(-50,0).release().perform();
	    
	    WebElement slider2 = driver.findElement(By.xpath("//*[@id=\"exposure\"]/div[2]/div[2]/div"));
	    Actions moveSlider2 = new Actions(driver);
	    moveSlider2.clickAndHold(slider2).moveByOffset(-25,0).release().perform();
	    
	    WebElement slider3 = driver.findElement(By.xpath("//*[@id=\"contrast\"]/div[2]/div[2]/div"));
	    Actions moveSlider3 = new Actions(driver);
	    moveSlider3.clickAndHold(slider3).moveByOffset(55,0).release().perform();
	    
	    WebElement slider4 = driver.findElement(By.xpath("//*[@id=\"highlights\"]/div[2]/div[2]/div"));
	    Actions moveSlider4 = new Actions(driver);
	    moveSlider4.clickAndHold(slider4).moveByOffset(100,0).release().perform();
	    
	    driver.findElement(By.xpath("//*[@id=\"adjust-apply\"]")).click();
	    driver.findElement(By.xpath("//*[@id=\"toggle-home\"]")).click();
	    driver.findElement(By.xpath("//*[@id=\"splash-open-generator\"]")).click();
	    driver.findElement(By.xpath("//*[@id=\"logo\"]")).click();
	
	}
	

	@Test(priority=10)
	public void test11()
	{
		//Scroll to the bottom of the page and take ScreenShort
		driver.get(baseurl);
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0,9684.7998046875);");

	    try 
	    {
	        Thread.sleep(2000); 
	    } 
	    catch (InterruptedException e) 
	    {
	        e.printStackTrace();
	    }
	    File screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    try 
	    {
	        File destination = new File("D:\\ZoTaC\\Software\\work'\\Luminar\\SS\\screenshot.png");
	        FileUtils.copyFile(screenshot, destination);
	        System.out.println("Screenshot saved at: " + destination.getAbsolutePath());
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	}
	
    @AfterTest
    public void browserclose() 
    {
    	driver.quit();
    }
	
}