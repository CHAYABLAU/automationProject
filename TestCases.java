package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.xml.sax.SAXException;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utilities.UsefulMethods;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static utilities.Constants.HOME_URL;
import static utilities.Constants.LOGIN_URL;

public class TestCases {
    private static WebDriver driver;
    LoginPage loginPage =new LoginPage(driver);
    HomePage homePage =new HomePage(driver);
    ProductPage productPage=new ProductPage(driver);
    UsefulMethods usefulMethods = new UsefulMethods(driver);
    private static ExtentReports extentReports =new ExtentReports();
    private static ExtentSparkReporter extentSparkReporter =new ExtentSparkReporter("index.html");

    String currentTime=String.valueOf(System.currentTimeMillis());

    @BeforeClass
    public static void beforeClass(){
        System.out.println("start");
        ChromeOptions options=new ChromeOptions() ;
        options.addArguments("-incognito");
        driver =new ChromeDriver(options);
        driver.get(LOGIN_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        extentReports.attachReporter(extentSparkReporter);
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.config().setReportName("My report");
        extentSparkReporter.config().setDocumentTitle("My new test report");
    }
    //test login to next site
    @Test
    public void loginNext() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        ExtentTest loginTest = extentReports.createTest("loginTest");
        //try to enter to login page
        try {
            driver.findElement(By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[2]/div[2]")).click();
            //*[@id="platform_modernisation_header"]/header/div[1]/nav/div[2]/div[2]/div[2]/div/a
            loginTest.info("Enter to page login");
            loginTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());
        } catch (Exception e) {
            loginTest.fail("fail");
        }
        //try to enter email address to the filed in order to login
        try {
            loginPage.enterUserEmail(usefulMethods.getDataItem("email", 0));
            loginTest.info("Enter email to the filed");
            loginTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());
        } catch (Exception e) {
            loginTest.fail("fail");
        }
        //try to enter password  to the filed in order to login
        try {
            loginPage.enterPassword(usefulMethods.getDataItem("password", 0));
            loginTest.info("Enter email to the filed");
            loginTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());
        } catch (Exception e) {
            loginTest.fail("fail");
        }
        //try to signIn
        try {
            loginPage.signIn();
            loginTest.info("signIn");
            loginTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());

        }catch (Exception e) {
            loginTest.fail("fail");

    }

       try {
         driver.navigate().to(LOGIN_URL);
         Thread.sleep(2000);
       }catch (Exception e){
         loginTest.fail("fail");
        }
    }

    @Test
    public void homePageLogin() throws InterruptedException {
        ExtentTest homeTest = extentReports.createTest("enterToHomePage");
        try {
            //try to enter to Home Tab
            usefulMethods.goToMenuTabs("HOME");
            Thread.sleep(500);
                   if (HOME_URL.equals(driver.getCurrentUrl())) {
                       homeTest.pass("The url is the same");
                       homeTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());
                   }else homeTest.pass("the url are not the same");
        } catch (Exception e) {
            homeTest.fail("fail");}
        driver.navigate().to(LOGIN_URL);

        //try to enter to some tab and some category it's belongs to
        try{
            usefulMethods.goToMenuTabs("SHOES","Girls");
            homeTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());
            Thread.sleep(500);
        }catch (Exception e) {
            homeTest.fail("fail");}

        driver.navigate().to(HOME_URL);
        //try to enter to some category you are currently in
        try {
            usefulMethods.goToMenuCategories("lighting");
            homeTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());
        }catch (Exception e) {
            homeTest.fail("fail");}

        //try to change a language
        try {
            usefulMethods.changeAlanguge("עברית ");
            homeTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());

        }catch (Exception e) {
            homeTest.fail("fail");}
       try {
           usefulMethods.changeAlanguge("English");
           homeTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());

       }catch (Exception e) {
           homeTest.fail("fail");}

        Thread.sleep(500);

        //try to enter to the links on the left side in homepage
        driver.navigate().to(HOME_URL);
        try {
            homePage.goToLinksInHomePage("Bedroom");
            homeTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());
        }catch (Exception e) {
            homeTest.fail("fail");}

    }
    @Test
    public void searchPage(){
        ExtentTest searchTest = extentReports.createTest("enterToProductPage");
        //try to search a product in the search line for example pigama
       try {
           driver.findElement(By.id("header-big-screen-search-box")).sendKeys("pigama");
           searchTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());

       }catch (Exception e) {
           searchTest.fail("fail");}
       //try to click on the button search
       try {
           driver.findElement(By.xpath("//button[@data-ga-v1='Search']")).click();
           searchTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());

       }catch (Exception e){
           searchTest.fail("fail");
       }
       //try to choose a product
        try {
            driver.findElement(By.xpath("//div//a[@title='Pink/White 3 Pack 100% Cotton Snuggle Pyjamas (9mths-12yrs) (Q96752) | ₪96 - ₪134']")).click();
        }catch (Exception e){

        }
        // driver.get("https://www.next.co.il/en/style/su131605/q96752#q96752");

    // try to choose a color for the product
        try {
            productPage.chooseColorForTheProduct("Neutral/Yellow");
            searchTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());

        } catch (Exception e){
            searchTest.fail("fail");
        }
        //try to choose size for the product
        try{
            productPage.chooseSize("12 - 18 Months (80 - 86cm) - ₪ 96 ");
            searchTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());

        }catch (Exception e){
            searchTest.fail("fail");
        }
        //try to add the product to the bag
        try{
            productPage.addToBag();
            searchTest.pass("PASS", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("spark\\pictures\\" + currentTime)).build());

        }catch (Exception e){
            searchTest.fail("fail");
        }
    }
    @Test
    public  void paymentPage() throws InterruptedException {
   //  WebElement checkoutButton = driver.findElement(By.xpath("//div//a[text()='CHECKOUT']"));
      WebElement checkoutButton=  driver.findElement(By.xpath("//div[@data-testid='header-adaptive-checkout']"));
     checkoutButton.click();
        Thread.sleep(1000);
//try to enter to pat as a guest
        driver.navigate().to("https://account.next.co.il/en/login/Checkout");
        driver.findElement(By.id("Next")).click();
        ////div//span[text()='CONTINUE AS GUEST']
    }
    @AfterClass
        public static void afterClass() throws InterruptedException {
            Thread.sleep(5000);
            driver.quit();
            extentReports.flush();
        }
    public static String takeScreenShot(String ImagesPath) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath+".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ImagesPath+".png";
    }

}

