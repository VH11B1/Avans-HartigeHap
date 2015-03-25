package edu.avans.hartigehap.web.it;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BrowserUtils {

    private static WebDriver webDriver = null;
    private static boolean shutdownHookEnabled = false;

    public static WebDriver getWebDriver () {
        if (webDriver == null) {
            if (!shutdownHookEnabled) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run () {
                        close();
                    }
                });
                shutdownHookEnabled = true;
            }
      /*
       * Vervang dit eventueel door een browser naar keuze.
       */

      // TODO change firefox location if needed
      //webDriver = new FirefoxDriver();
      webDriver = new FirefoxDriver(new FirefoxBinary(new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe")),new FirefoxProfile(new File("C:\\Users\\Alex\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\")));

        }

    /*
     * Stel een timeout in die aangeeft hoe lang de webDriver moet blijven proberen om een element dat op de pagina aanwezig zou moeten zijn te selecteren.
     */
        webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        return webDriver;
    }

    public static void close () {
        if (webDriver == null) {
            return;
        }
        try {
            webDriver.quit();
        } catch (Exception ex) {
            log.debug("", ex);
        }
        webDriver = null;
    }

}
