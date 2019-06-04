import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import jdk.internal.org.xml.sax.SAXException;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.not;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)


public class BuyMe {
    private static WebDriver driver;
    private static ExtentReports extent = new ExtentReports();
    private static ExtentTest test = extent.createTest(Report.testName, Report.description);
    private static String reportPath = BuyMeConstants.HTML_LOCATION;
    String currentTime = String.valueOf(System.currentTimeMillis());
    Actions actions = new Actions(driver);


    @BeforeClass
    public static void main() throws org.xml.sax.SAXException, ParserConfigurationException, SAXException, IOException, InterruptedException {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
        extent.attachReporter(htmlReporter);
        System.setProperty((getData(BuyMeConstants.WEBDRIVER_KIND)), BuyMeConstants.WEBDRIVER_LOCATION);//Define a driver
        driver = new ChromeDriver();
        driver.manage().window().maximize();//Maximize the browser's window
        driver.navigate().to(getData(BuyMeConstants.URL));//Open browser on the url in an XML file
        // add custom system info
        extent.setSystemInfo(Report.environment, Report.environmentKind);
        extent.setSystemInfo(Report.tester, Report.testerName);
        test.log(Status.INFO, Report.logInfo1);
        // add screenshot
        test.pass(Report.screenshot1, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(BuyMeConstants.PNG_LOCATION + new Random())).build());
        }

    @Test
    public void A_logIn () throws IOException, InterruptedException {
        driver.findElement(BuyMeRegistrationScreen.ENTRANCE_BUTTON_LOCATOR).click();
        // add screenshot
        test.pass(Report.screenshot2, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(BuyMeConstants.PNG_LOCATION + currentTime)).build());
        driver.findElement((BuyMeRegistrationScreen.REGISTRATION_BUTTON_LOCATOR)).click();
        // add screenshot
        test.pass(Report.screenshot3, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(BuyMeConstants.PNG_LOCATION + currentTime)).build());
        List<WebElement> textField = driver.findElements(BuyMeRegistrationScreen.TEXT_BUTTON_LOCATOR);
        textField.get(0).sendKeys(BuyMeRegistrationScreen.myName);
        textField.get(1).sendKeys(BuyMeRegistrationScreen.email);
        textField.get(2).sendKeys(BuyMeRegistrationScreen.password);
        textField.get(3).sendKeys(BuyMeRegistrationScreen.password);
        List<WebElement> checkbox = driver.findElements(BuyMeRegistrationScreen.CHECKBOX_LOCATOR);
        actions.moveToElement(checkbox.get(0)).click().build().perform();
        test.log(Status.PASS, Report.logInfo2);
        driver.findElement(BuyMeRegistrationScreen.REGISTRATION_FINAL_BUTTON_LOCATOR).click();
    }

    @Test
    public void B_searchAGift () throws IOException, InterruptedException {
        //add screenshot
        test.pass(Report.screenshot4, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(BuyMeConstants.PNG_LOCATION + currentTime)).build());
        WebDriverWait urlChange = new WebDriverWait(driver,3);
        urlChange.until(ExpectedConditions.visibilityOfElementLocated(BuyMeHomeScreen.DROPDOWN_LOCATOR));//Wait until registration screen closed
        List<WebElement> dropdown = driver.findElements(BuyMeHomeScreen.DROPDOWN_LOCATOR);
        dropdown.get(0).click();
        List<WebElement> dropdownSelection = driver.findElements(BuyMeHomeScreen.DROPDOWN_SELECTION_LOCATOR);
        actions.moveToElement(dropdownSelection.get(3)).click().build().perform();
        dropdown.get(1).click();
        List<WebElement> dropdownAppear = driver.findElements(BuyMeHomeScreen.DROPDOWN_APPEAR_LOCATOR);
        actions.moveToElement(dropdownAppear.get(1));
        List<WebElement> dropdownAreaSelection = driver.findElements(BuyMeHomeScreen.DROPDOWN_AREA_SELECTION_LOCATOR);
        actions.moveToElement(dropdownAreaSelection.get(3)).click().build().perform();
        dropdown.get(2).click();
        List<WebElement> dropdownAppearCategory = driver.findElements(BuyMeHomeScreen.DROPDOWN_APPEAR_LOCATOR);
        actions.moveToElement(dropdownAppearCategory.get(2));
        List<WebElement> dropdownCategorySelection = driver.findElements(BuyMeHomeScreen.DROPDOWN_CATEGORY_SELECTION_LOCATOR);
        actions.moveToElement(dropdownCategorySelection.get(3)).click().build().perform();
        driver.findElement(BuyMeHomeScreen.SEARCH_BOTTON_LOCATOR).click();
    }

    @Test
    public void C_chooseAGift () throws InterruptedException, IOException {
        WebDriverWait urlChange = new WebDriverWait(driver,1);
        urlChange.until(ExpectedConditions.visibilityOfElementLocated(BuyMeChooseAGiftScreen.ELEMENT_FOR_WAIT));//Wait until the url is change
        test.log(Status.PASS, Report.logInfo3);
        //add screenshot
        test.pass(Report.screenshot5, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(BuyMeConstants.PNG_LOCATION + currentTime)).build());
        Assert.assertThat(driver.getCurrentUrl(),not(BuyMeChooseAGiftScreen.HomePageUrl));//Checking url is not home page
        List<WebElement> business = driver.findElements(BuyMeChooseAGiftScreen.BUSINESS_LOCATOR);
        business.get(2).click();
    }

    @Test
    public void D_chooseGiftAmount() throws IOException {
        WebDriverWait pageChange = new WebDriverWait(driver,1);
        pageChange.until(ExpectedConditions.visibilityOfElementLocated(BuyMeChooseAGiftScreen.AMOUNT_FIELD_LOCATOR));
        //add screenshot
        test.pass(Report.screenshot6, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(BuyMeConstants.PNG_LOCATION + currentTime)).build());
        driver.findElement(BuyMeChooseAGiftScreen.AMOUNT_FIELD_LOCATOR).sendKeys(BuyMeChooseAGiftScreen.chosenAmount);
        driver.findElement(BuyMeChooseAGiftScreen.SELECT_BUTTON_LOCATOR).click();

    }

    @Test
    public void E_GiftInformation () throws IOException {
        //add screenshot
        test.pass(Report.screenshot7, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(BuyMeConstants.PNG_LOCATION + currentTime)).build());
        driver.findElement(BuyMeGiftInformation.RADIO_WHO_BUTTON_LOCATOR).click();
        List<WebElement> textField = driver.findElements(BuyMeGiftInformation.TEXT_FIELD_LOCATOR);
        textField.get(2).sendKeys(BuyMeGiftInformation.receiverName);
        textField.get(3).sendKeys(BuyMeGiftInformation.senderName);
        driver.findElement(BuyMeGiftInformation.BLESSING_LOCATOR).sendKeys(BuyMeGiftInformation.blessing);
        textField.get(4).sendKeys(BuyMeGiftInformation.picture);
        List<WebElement> dropdown = driver.findElements(BuyMeGiftInformation.DROPDOWN_LOCATOR);
        dropdown.get(6).click();
        List<WebElement> dropdownSelection = driver.findElements(BuyMeGiftInformation.DROPDOWN_SELECTION_LOCATOR);
        actions.moveToElement(dropdownSelection.get(3)).click().build().perform();
        driver.findElement(BuyMeGiftInformation.RADIO_PAY_LOCATOR).click();
        driver.findElement(BuyMeGiftInformation.SEND_BY_MAIL_LOCATOR).click();
        driver.findElement(BuyMeGiftInformation.TEXT_MAIL_LOCATOR).sendKeys(BuyMeGiftInformation.mailAdress);
        driver.findElement(BuyMeGiftInformation.SAVE_BUTTON_LOCATOR).click();
        driver.findElement(BuyMeGiftInformation.SUBMIT_BUTTON_LOCATOR).click();
        //add screenshot
        test.pass(Report.screenshot8, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(BuyMeConstants.PNG_LOCATION + currentTime)).build());
    }

    @AfterClass
    public static void finish() {
        extent.flush();// end test and save data into report file
//        driver.quit();
    }

    //Read from xml file
    private static String getData(String keyName) throws ParserConfigurationException, IOException, SAXException, org.xml.sax.SAXException {
        File configXmlFile = new File(BuyMeConstants.XML_LOCATION);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(configXmlFile);

        if (doc != null) {
            doc.getDocumentElement().normalize();
        }
        assert doc != null;
        return doc.getElementsByTagName(keyName).item(0).getTextContent();
    }

    // take screenshot and return picture path
    private static String takeScreenShot(String ImagesPath) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath + ".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ImagesPath + ".png";
    }
}
