import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;


public class Verify_Login_Func {
    public static WebDriver driver;
    public static int counter = 0;

    @BeforeMethod
    public void Setup(){
//        WebDriverManager.edgedriver().setup();
//        driver = new EdgeDriver();

        EdgeOptions options=new EdgeOptions();
        options.addArguments("headless");
        driver=new EdgeDriver(options);
        // Implicit wait timeout for 20seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        options.addArguments("--headless");
    }
    @Test(dataProvider = "LoginDataProvider",dataProviderClass =Util.class )
    public void Verify_Login(String username, String password) throws Exception {
        Reporter.log("-- Open Base URL");
        driver.get(Util.Base_URL);
        WebElement username_webelement = driver.findElement(By.xpath("//input[@name='uid']"));
        WebElement pass_webelement = driver.findElement(By.xpath("//input[@name='password']"));
        Reporter.log("-- Trying Enter Credentials");
        username_webelement.sendKeys(username);
        pass_webelement.sendKeys(password);
        WebElement Login_button = driver.findElement(By.xpath("//input[@name='btnLogin']"));
        Reporter.log("-- Click Login Button");
        Login_button.click();
        if(counter==0){
            String tab_title=driver.getTitle();
            if (tab_title.equals(Util.Expected_title) ){
                Reporter.log("-- Reaching HomePage");
                WebElement managerTitle = driver.findElement(By.cssSelector("tr[class='heading3'] td"));
                takeSnapShot(driver);
                if(managerTitle.getText().contains(Util.User_Name)){
                    Reporter.log("-- Manager ID is shown in o/p");
                    Reporter.log("-- Passed Test case");
                }else{
                    Reporter.log("-- Manager ID is not shown in o/p");
                    Reporter.log("-- Failed Test case");
                    Assert.fail();
                }

            }else{
                Reporter.log("-- Can Not Reaching HomePage");
                Reporter.log("-- Failed Test case");
                Assert.fail();
            }
        }else{

            String popup_text = driver.switchTo().alert().getText();

            if (popup_text.equals(Util.Popup_Window)){
                Reporter.log("-- Pop-up window show with error text");
                Reporter.log("-- Passed Test case");
            }else{
                Reporter.log("-- No Pop-up window with error text");
                Reporter.log("-- Failed Test case");
                Assert.fail();
            }
        }

        counter++;
    }

    @AfterMethod
    public void Finish(){
        driver.quit();
    }

    public static void takeSnapShot(WebDriver webdriver) throws Exception{
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        //Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        //
        String timeStamp;
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String fileWithPath ="test-output\\screenshots\\"+timeStamp+".png";
        //
        File DestFile=new File(fileWithPath);
        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);

        String image_as_webElement = "<img src='"+"screenshots\\"+timeStamp+".png"+"' alt='screenshot' style=\"width:500px;height:500px;\" />";
        Reporter.log(image_as_webElement);
    }






}
