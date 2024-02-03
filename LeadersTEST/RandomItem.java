package LeadersTEST;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class RandomItem {
	WebDriver driver = new ChromeDriver();
	Random rand = new Random();
	Actions actions = new Actions(driver);
	String Website = "https://leaders.jo/en/";

	@BeforeTest
	public void SetUp() {
		driver.get(Website);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@Test(priority = 1)
	public void ChooseRandomProduct() {
		WebElement SearchBar = driver.findElement(By.name("s"));
		SearchBar.clear();
		SearchBar.sendKeys("Apple");
		List<WebElement> Products = driver.findElements(By.className("dgwt-wcas-suggestion-product"));
		int RandomIndex = rand.nextInt(Products.size());
		WebElement RandomProduct = Products.get(RandomIndex);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", RandomProduct);
		actions.moveToElement(RandomProduct).click().perform();
	}

	@Test(priority = 2)
	public void CheckingTheAvailibilty() throws InterruptedException, IOException {
		Thread.sleep(15);
		try {
			WebElement Availibility = driver.findElement(By.xpath("//*/div[1]/div[2]/div[2]/span/p"));
			String TheActualAvailiblity = Availibility.getText();
			if (TheActualAvailiblity.contains("In stock")) {
				Assert.assertEquals(TheActualAvailiblity, "In stock");

				TakesScreenshot src = ((TakesScreenshot) driver);
				File SrcFile = src.getScreenshotAs((OutputType.FILE));
				File Dest = new File("C:/Users/AAAA/eclipse-workspace/abdallah/src/LeadersTEST/ScreenShot.png");
				FileUtils.copyFile(SrcFile, Dest);
			} else {
				ChooseRandomProduct();
				CheckingTheAvailibilty();
			}
		} catch (NoSuchElementException e) {
			ChooseRandomProduct();
			CheckingTheAvailibilty();

		}
	}

	@AfterTest
	public void Final() {
	}

}
