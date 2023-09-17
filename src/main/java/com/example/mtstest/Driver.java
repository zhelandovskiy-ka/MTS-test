package com.example.mtstest;

import com.example.mtstest.dto.Tariff;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    private WebDriver driver;

    public Driver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
    }

    public List<Tariff> getTariffsList() {
        List<Tariff> tariffs = new ArrayList<>();

        try {
            String url = "https://moskva.mts.ru/personal/mobilnaya-svyaz/tarifi/vse-tarifi/mobile-tv-inet";
            driver.get(url);
            try {
                driver.findElement(By.cssSelector("button.btn:nth-child(2)")).click();
                driver.findElement(By.cssSelector("body > div.g-page-wrapper > div > div.mts16-footer__to-bottom-content > div.content__wrap > mts-tariffs-catalog > div.tabs.tabs_relative.tabs_combined > div > div > div > mts-actual-tariffs-catalog > div > div.filters__content.filters__content-full-width > mts-actual-tariffs > div.filters__content-item > mts-actual-tariffs-group > div > button")).click();
            } catch (NoSuchElementException e) {

            }

            List<WebElement> elements = driver.findElements(By.tagName("mts-universal-card"));
            for (WebElement webElement : elements) {
                String name = getTextByClassName(webElement, "card-title");
                String description = getTextByClassName(webElement, "card-description");
                String options = getTextByClassName(webElement, "card-features__margin");
                String price = getTextByClassName(webElement, "price-text");
                price = price.replaceAll("\\s", "");

                tariffs.add(new Tariff(name, options, description, Double.parseDouble(price)));
            }
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return tariffs;
    }

    public String getTextByClassName(WebElement webElement, String name) {
        try {
            return webElement.findElement(By.className(name)).getText();
        } catch (NoSuchElementException e) {

        }

        return "";
    }
}
