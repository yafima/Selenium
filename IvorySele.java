package sele;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.google.common.io.Files;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IvorySele {

	private static String stringForSearch = "security cam";
	private static String siteString = "http://ivory.co.il";
	private static String IDName = "qSearch";

	public static void main(String[] args) {
		loadAndSearchOnWebSite(siteString, IDName, stringForSearch);
	}

	private static void loadAndSearchOnWebSite(String siteString, String IDName, String searchString) {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

		// Initialize browser
		WebDriver browserSite = new ChromeDriver();
		// Open ivory website
		browserSite.get(siteString);
		waitForWebLoad(browserSite, By.id(IDName));

		try {						
			// Maximize browser
			browserSite.manage().window().maximize();
			// Screenshot
			takeSnapShot(browserSite, "FirstPic.png");
			WebElement elementSearchOnSite = browserSite.findElement(By.id(IDName));
			elementSearchOnSite.sendKeys(searchString);
			elementSearchOnSite.submit();
			// Waiting for site load			
			waitForWebLoad(browserSite, By.cssSelector("[data-product-id='31535']"));
			takeSnapShot(browserSite, "SecondPic.png");
			getWebItem(siteString, browserSite);
			System.exit(0);
		} catch (Exception e) {
			System.out.println("Got Exception" + e);
			System.exit(1);
		}
	}

	private static void getWebItem(String siteString, WebDriver browserSite) {
		WebElement cameraon = browserSite.findElement(By.cssSelector("[data-product-id='31535']"));
		cameraon.click();
		try {
			// Waiting for site load
			waitForWebLoad(browserSite, By.cssSelector("[class='print-actual-price']"));
			takeSnapShot(browserSite, "ThirdPic.png");
		} catch (Exception e) {
		}
	}

	public static void takeSnapShot(WebDriver browserSite, String fileWithPath) throws Exception {
		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) browserSite);
		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		// Move image file to new destination
		File DestFile = new File(fileWithPath);
		// Copy file at destination
		Files.copy(SrcFile, DestFile);
	}

	private static void waitForWebLoad(WebDriver browserSite, By WaitPrameter) {
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(browserSite, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(WaitPrameter));
	}
}