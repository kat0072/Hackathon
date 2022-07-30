package test.java;


import com.pcloudyhackathon.Runner;
import com.ssts.pcloudy.Connector;
import com.ssts.pcloudy.exception.ConnectError;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scenario4 extends Runner {

    @Test
    public void ClickLoginAndCompareImages() throws IOException, ConnectError, ParseException {

        LoginButtonScreenShot();

        Connector con = new Connector("https://device.pcloudy.com/");

        //To get the Access key - Login to pCloudy platform->Go to Profile and click on Settings->Copy the access key
        String authToken = con.authenticateUser("katwal.raman@gmail.com", "dcgjgxfy89gyqnzz355dprss");
        File fileToBeUploaded = new File("./screenshot/LoginButton.png");
        String baseImageId = con.getImageId(authToken, fileToBeUploaded);

        Map< String, Object> params = new HashMap<>();
        params.put("imageId", baseImageId);
        params.put("word", "LOGIN");
        System.out.println(driver.executeScript("mobile:ocr:coordinate",params));
        String response = driver.executeScript("mobile:ocr:coordinate",params).toString();

        ClickLogin(response);

        BaseImageClick();

        BaseScreenShot();

        driver.navigate().back();

        SecondaryImageClick();

        SecondaryScreenShot();

        CompareImages();
    }


    //Capture screenshot for Login Page
    public void LoginButtonScreenShot() throws IOException {
        String folder_name = "screenshot";
        File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //copy screenshot file into screenshot folder.
        FileUtils.copyFile(f, new File(folder_name + "/" + "LoginButton.png"));

        byte[] fileContent = FileUtils.readFileToByteArray(new File(folder_name + "/" + "LoginButton.png"));
        String encodedString = Base64
                .getEncoder()
                .encodeToString(fileContent);

        File imgFile = new File("./screenshot/LoginButton.png");
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64(encodedString)));
        ImageIO.write(img, "png", imgFile);
    }

    //Duble Click
    public void BaseImageClick() throws IOException {
        List<WebElement> eleDouble = driver.findElements(By.xpath("//android.widget.TextView[@resource-id='com.pcloudyhackathon:id/tv_name']"));
        eleDouble.get(3).click();
        captureScreenShots();
        BaseScreenShot();
    }


    //Capture screenshot for Login Page
    public void BaseScreenShot() throws IOException {
        String folder_name = "screenshot";
        File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //copy screenshot file into screenshot folder.
        FileUtils.copyFile(f, new File(folder_name + "/" + "Base.png"));

        byte[] fileContent = FileUtils.readFileToByteArray(new File(folder_name + "/" + "Base.png"));
        String encodedString = Base64
                .getEncoder()
                .encodeToString(fileContent);

        File imgFile = new File("./screenshot/Base.png");
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64(encodedString)));
        ImageIO.write(img, "png", imgFile);
    }

    //Capture screenshot for Login Page
    public void SecondaryScreenShot() throws IOException {
        String folder_name = "screenshot";
        File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //copy screenshot file into screenshot folder.
        FileUtils.copyFile(f, new File(folder_name + "/" + "Secondary.png"));

        byte[] fileContent = FileUtils.readFileToByteArray(new File(folder_name + "/" + "Secondary.png"));
        String encodedString = Base64
                .getEncoder()
                .encodeToString(fileContent);

        File imgFile = new File("./screenshot/Secondary.png");
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64(encodedString)));
        ImageIO.write(img, "png", imgFile);
    }

    //Duble Click
    public void SecondaryImageClick() throws IOException {
        List<WebElement> eleDouble = driver.findElements(By.xpath("//android.widget.TextView[@resource-id='com.pcloudyhackathon:id/tv_name']"));
        eleDouble.get(4).click();
        captureScreenShots();
        SecondaryScreenShot();
    }


    //Compare Images
    public void CompareImages() throws IOException, ConnectError {
        Connector con = new Connector("https://device.pcloudy.com/");

        //To get the Access key - Login to pCloudy platform->Go to Profile and click on Settings->Copy the access key
        String authToken = con.authenticateUser("katwal.raman@gmail.com", "dcgjgxfy89gyqnzz355dprss");
        File baseFileToUpload = new File("./screenshot/Base.png");
        File secondaryFileToUpload = new File("./screenshot/Secondary.png");
        String baseImageId = con.getImageId(authToken, baseFileToUpload);
        String secondaryImageId = con.getImageId(authToken, secondaryFileToUpload);
        Map< String, Object> params = new HashMap<>();
//Declare Image ID of Base image
        params.put("baseImageId", baseImageId);
//declare Image ID of second image
        params.put("secondImageId", secondaryImageId);
        //Find the difference between two image
        String base64=(String) driver.executeScript("mobile:visual:imageDiff",params);
        //Enter path
        File imgFile = new File("./screenshot/diff.png");
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64(base64)));
        ImageIO.write(img, "png", imgFile);
    }

    public void ClickLogin(String response) throws ParseException, IOException {
        JSONParser parse = new JSONParser();

        JSONObject jobj = (JSONObject)parse.parse(response);

        JSONArray jsonarr_0 = (JSONArray) jobj.get("data");
        JSONObject jsonobj_0 = (JSONObject)jsonarr_0.get(0);
        System.out.println(jsonobj_0.get("Top"));
        System.out.println(jsonobj_0.get("Left"));
        long x = (long) jsonobj_0.get("Top");
        long y = (long) jsonobj_0.get("Left");
        //Click on Accept button
        driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.pcloudyhackathon:id/login']")).click();
        captureScreenShots();
    }
}
