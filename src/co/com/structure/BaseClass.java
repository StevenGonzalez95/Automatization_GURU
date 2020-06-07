package co.com.structure;

import co.com.structure.models.Account;
import co.com.structure.models.Credentials;
import co.com.structure.models.Customer;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
public class BaseClass {



    public WebDriver webDriver;

    public BaseClass(WebDriver driver){
        this.webDriver = driver;
    }

    public WebDriver connectionDriver(){
        System.setProperty("webdriver.chrome.driver","./src/co/com/resources/driver/chromedriver.exe");
        webDriver = new ChromeDriver();
        return webDriver;
    }

    public WebElement findElement(By locator){
        return webDriver.findElement(locator);
    }

    public List<WebElement> findElements(By locator){
        return webDriver.findElements(locator);
    }

    public String getText(By locator){
        return webDriver.findElement(locator).getText();
    }

    public String getText(WebElement element){
        return element.getText();
    }

    public void writeValues(String inputText, By locator){
        webDriver.findElement(locator).sendKeys(inputText);
    }

    public void click(By locator){
        webDriver.findElement(locator).click();
    }

    public Boolean isDisplayed(By locator){
        try {
            return webDriver.findElement(locator).isDisplayed();
        }catch (org.openqa.selenium.NoSuchElementException e){
            return false;
        }
    }

    public Boolean isDisplayedList(By locator){
        try {
            return webDriver.findElement(locator).isDisplayed();
        }catch (org.openqa.selenium.NoSuchElementException e){
            return false;
        }
    }

    public void visit(String url){
        webDriver.get(url);
    }

    public double ramdon(int from,int tonum){
        return   Math.floor(Math.random()*(tonum-from+1)+from);
    }

    public void writeEvidences(Credentials credentials, Account account, Customer customer,String test) throws IOException {

        String fichero ="./src/co/com/resources/evidences/evidences";
       FileWriter  file = new FileWriter(fichero,true);
        String separador ="===========================================================================================";
        try(BufferedWriter bw = new BufferedWriter(file)){

                bw.write("\n"+fecha() +
                        "\nDescripción de la prueba: "+test+ "\nCredenciales: "+credentials.toString()+
                        "\nDetalles del cliente: "+customer.toString()+
                "\nDetalles de la cuenta: "+account.toString()+
                        "\n"+separador);
            }catch (Exception e) {
                e.getStackTrace();
            }
    }

    public Date fecha(){
        return new Date();
    }







}
