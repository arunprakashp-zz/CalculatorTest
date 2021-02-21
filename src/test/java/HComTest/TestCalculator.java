package HComTest;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.TestNGException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import utility.CalculatorUtils;
import utility.Constants;
import utility.ExcelUtils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestCalculator {

	public static final String Price_Sort_Order = "Price (high to low)";

	public static WebDriver driver;

	private Assertion hardAssert = new Assertion();
	private SoftAssert softAssert = new SoftAssert();

	@BeforeMethod
	public void beforeMethod() {
		//System.setProperty("webdriver.firefox.marionette","../resources/");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(Constants.URL);
	}

	@DataProvider(name = "CalculatorInputs")
	public Object[][] CalculatorInputs() {

		Object[][] testObjArray = null;
		try {
			testObjArray = ExcelUtils.getTableArray(Constants.TestDataPath + Constants.TestDataFile,
					Constants.TestSheet);
		} catch (Exception e) {
			System.out.println("Cannot load the TestData from the specified location:" + Constants.TestDataPath);
			e.printStackTrace();
		}
		return testObjArray;
	}

	@Test(dataProvider = "CalculatorInputs")
	public void TestCalculator(String testCaseID, String testCaseName, String value1, String value2, String operation, String equation, String expectedResult)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			System.out.println("Currently executing :::" + testCaseName);
			Thread.sleep(2000);
			//Framing the equation to send to the calculator
			if(equation.equals("N")){
				equation = CalculatorUtils.fetchEquation(value1, value2, operation);
			}
			System.out.println("equation is:::" + equation);
			String executableEquation = CalculatorUtils.fetchExecutableEquation(equation);
			//Executing the framed equation using JSExecutor
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(executableEquation);
			Thread.sleep(2000);
			//Fetching screenshot to compare the results
			Screenshot sShot = new AShot().takeScreenshot(driver);
			ImageIO.write(sShot.getImage(), "png",
					new File(System.getProperty("user.dir")+"/screenShot/"+testCaseID+".png"));

			//Image comparison
			BufferedImage expectedImage = ImageIO.read(new File(System.getProperty("user.dir")+"/expectedScreen/"+expectedResult));
			System.out.println("expectedImage"+expectedImage);
			BufferedImage actualImage = ImageIO.read(new File(System.getProperty("user.dir")+"/screenShot/"+testCaseID+".png"));
			System.out.println(actualImage);
			ImageDiffer imgDifference = new ImageDiffer();
			ImageDiff imgDiff = imgDifference.makeDiff(actualImage, expectedImage);
			if(imgDiff.hasDiff()) {
				Assert.fail("Result is not as expected");
			} else {
				System.out.println("Images are same");
			}
		} catch (IOException e) {
			hardAssert.fail("Exception during IO operation");
			e.printStackTrace();
		}catch (TimeoutException e) {
			hardAssert.fail("Timeout Exception while waiting for the element to be visible or respond");
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			hardAssert.fail("No such element found, please check the stacktrace for more info");
			e.printStackTrace();
		} catch (StaleElementReferenceException e) {
			hardAssert.fail("Element reference is stale, please check the stacktrace for more info");
			e.printStackTrace();
		} catch (TestNGException e) {
			hardAssert.fail("TestNG Exception, please check the stacktrace for more info");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			hardAssert.fail("NumberFormatException caused during conversion from String to Int");
			e.printStackTrace();
		} catch (Exception e) {
			hardAssert.fail("Exception has occured, please check the stacktrace for more info");
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void afterMethod() {
		// Close the driver
		driver.quit();
	}

}
