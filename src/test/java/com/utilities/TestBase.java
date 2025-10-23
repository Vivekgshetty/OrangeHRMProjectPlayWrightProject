package com.utilities;

import com.microsoft.playwright.*;

import java.io.File;
import java.nio.file.Paths;

import org.testng.annotations.*;

public class TestBase {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    public String baseUrl = Readconfig.readPropertyFileData("baseUrl", "config");
    public String userName = Readconfig.readPropertyFileData("userName", "config");
    public String password = Readconfig.readPropertyFileData("password", "config");

    @Parameters("browser")
    @BeforeMethod
    
    public void setUp(@Optional("chromium") String browserName ){
        playwright = Playwright.create();

        switch (browserName.toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chromium").setHeadless(false));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        context = browser.newContext();
        page = context.newPage();
        page.navigate(baseUrl);
    }
    
    public static String captureScreenshot(String testName) {
        String path = "./target/screenshots/" + testName + ".png";
        new File("./target/screenshots").mkdirs(); // Ensure folder exists
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)));
        return path;
    }
 

    @AfterMethod
    public void tearDown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    public Page getPage() {
        return page;
    }
}
