package com.pcloudyhackathon;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
public class Runner {
    AppiumDriverLocalService service;
    public AppiumDriver<WebElement> driver;
    String folder_name;
    DateFormat df;

    @BeforeTest
    public void setUpSuite() throws Exception {

    }

    @BeforeMethod
    public void prepareTest() throws IOException, InterruptedException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("pCloudy_Username", "katwal.raman@gmail.com");
        capabilities.setCapability("pCloudy_ApiKey", "dcgjgxfy89gyqnzz355dprss");
        capabilities.setCapability("pCloudy_ApplicationName", "PCloudyHackathon.apk");
        capabilities.setCapability("pCloudy_DurationInMinutes", 10);
        capabilities.setCapability("pCloudy_DeviceManafacturer", "Samsung");
        capabilities.setCapability("pCloudy_DeviceVersion", "12.0.0");
//        capabilities.setCapability("pCloudy_DeviceFullName", "Pixel 5");
        capabilities.setCapability("appium:deviceName", "784");
        capabilities.setCapability("automationName", "uiautomator2");
        capabilities.setCapability("platformVersion", "12.0.0");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("newCommandTimeout", 600);
        capabilities.setCapability("launchTimeout", 90000);
        capabilities.setCapability("automationName", "uiautomator2");
        capabilities.setCapability("appPackage", "com.pcloudyhackathon");
//        capabilities.setCapability("appActivity", "com.pcloudyhackathon");
        driver = new AndroidDriver(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), capabilities);

    }



    @AfterMethod
    public void endTest() throws IOException {

        driver.quit();
    }

    //Capture screenshot
    public void captureScreenShots() throws IOException {
        folder_name = "screenshot";
        File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //Date format for screenshot file name
        df = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        //create dir with given folder name
        new File(folder_name).mkdir();
        //Setting file name
        String file_name = df.format(new Date()) + ".png";
        //copy screenshot file into screenshot folder.
        FileUtils.copyFile(f, new File(folder_name + "/" + file_name));
    }
}
