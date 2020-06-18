package co.com.structure;

import co.com.structure.models.Account;
import co.com.structure.models.Credentials;
import co.com.structure.models.Customer;
import co.com.structure.models.EditAccount;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
public class CustomerPage  extends BaseClass {

    public CustomerPage(WebDriver driver) {
        super(driver);
    }

    private LocatorClass locator = new LocatorClass();

    public String loginUser(Credentials credentials) {
        writeValues(credentials.getUser(), locator.locatorName("uid"));
        writeValues(credentials.getPassword(), locator.locatorName("password"));
        click(locator.locatorName("btnLogin"));
        List<WebElement> tbody = findElements(locator.locatorTag("tbody"));
        return tbody.get(1).getText();
    }

    public void getCredentials(Credentials credentials) throws InterruptedException {
        //Obtiene las credenciales para ingresar
        click(locator.locatorLink("here"));
        Thread.sleep(1000);
        writeValues(credentials.getEmailId(), locator.locatorName("emailid"));
        click(locator.locatorName("btnLogin"));
        Thread.sleep(1000);
        List<WebElement> listTable = findElements(locator.locatorTag("tr"));
        //OBtiene las credenciales que se presentan en la pantalla para ingresar
        credentials.setUser(listTable.get(3).getText().substring(10));
        credentials.setPassword(listTable.get(4).getText().substring(11));
        //Retorna a la página principal
        visit("http://demo.guru99.com/V4/index.php");
        log.info(credentials.toString());

    }

    public String newCustomer(Credentials credentials, Customer customer) throws InterruptedException {
        click(locator.locatorLink("New Customer"));
        Thread.sleep(1000);
        if (Boolean.TRUE.equals(isDisplayed(locator.locatorName("name")))) {
            structureDataNewCustomer(customer);
            Thread.sleep(1000);
            try{
                //Se valida si el usuario está repetido para generar de nuevo los datos
                Alert alert= webDriver.switchTo().alert();
                alert.accept();
                structureDataNewCustomer(customer);
            }catch (Exception alert){
                log.info("Cliente duplicado. Se intenta de nuevo");
            }

            //Se obtiene el userId que genera cuando se crea el cliente
            credentials.setUserId(findElements(locator.locatorTag("td")).get(5).getText());
            log.info(credentials.toString());
            return findElements(locator.locatorTag("td")).get(1).getText();

        } else {
            return "Cliente no registrado";
        }
    }

    public void structureDataNewCustomer(Customer customer){
        //Se estructuran los datos para la creación del cliente
        customer.setEmail("GuruTest_7"+(int) ramdon(1, 999) + "@correo.com");
        customer.setPin(String.valueOf((int) ramdon(1, 99999999)));
        writeValues(customer.getName(), locator.locatorName("name"));
        click(locator.locatorXpath("//input[@value='"+customer.getGender()+"']"));
        writeValues(customer.getDateOfbirth(), locator.locatorName("dob"));
        writeValues(customer.getAddress(), locator.locatorName("addr"));
        writeValues(customer.getCity(), locator.locatorName("city"));
        writeValues(customer.getState(), locator.locatorName("state"));
        writeValues(customer.getPin(), locator.locatorName("pinno"));
        writeValues(customer.getMobile(), locator.locatorName("telephoneno"));
        writeValues(customer.getEmail(), locator.locatorName("emailid"));
        writeValues(customer.getPassword(), locator.locatorName("password"));
        click(locator.locatorName("sub"));
    }

    public Account newAccount(Credentials credentials, Account account) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1000);
        Account accountResponse = new Account();
        scroll();
        click(locator.locatorLink("New Account"));
        if (Boolean.TRUE.equals(isDisplayed(locator.locatorName("cusid")))) {
            writeValues(credentials.getUserId(), locator.locatorName("cusid"));
            click(locator.locatorXpath("//option[@value='" + account.getTypeAccount() + "']"));
            writeValues(account.getInitDeposit(), locator.locatorName("inideposit"));
            click(locator.locatorName("button2"));
            credentials.setAccountId(findElements(locator.locatorTag("td")).get(5).getText());

            accountResponse.setUserIdAccount(findElements(locator.locatorTag("td")).get(7).getText());
            accountResponse.setInitDeposit(findElements(locator.locatorTag("td")).get(17).getText());
            accountResponse.setTypeAccount(findElements(locator.locatorTag("td")).get(13).getText());
            accountResponse.setAccountId(findElements(locator.locatorTag("td")).get(5).getText());
            accountResponse.setSuccess(findElements(locator.locatorTag("td")).get(4).getText()
                    .equals("Account ID"));


        } else {
            accountResponse.setSuccess(false);
        }
        log.info("Creando cuenta:" +accountResponse.toString());
        return accountResponse;
    }


    public String deleteAccount(Account account) throws InterruptedException {
        scroll();
        click(locator.locatorLink("Delete Account"));
        Thread.sleep(2000);
        //Se valida que se haya desplegado un elemento de la página solicitada
        if (Boolean.TRUE.equals(isDisplayed(locator.locatorName("accountno")))) {
            writeValues(account.getAccountId(), locator.locatorName("accountno"));
            click(locator.locatorName("AccSubmit"));
            //Se confirma la eliminación de la cuenta mediante la alerta que presenta el navegador
            Thread.sleep(2000);
            Alert alert = webDriver.switchTo().alert();
            alert.accept();
            String textAlert = alert.getText();
            alert.accept();
            return textAlert;
        }else{
            return "No se pudo eliminar la cuenta";
        }
    }

    public EditAccount editAccount(Account account, Customer customer) throws InterruptedException {
        EditAccount accountEdited = new EditAccount();
        Boolean validation = false;
        scroll();
        click(locator.locatorLink("Edit Account"));
        TimeUnit.MILLISECONDS.sleep(1000);
        if (Boolean.TRUE.equals(isDisplayed(locator.locatorName("accountno")))) {
            writeValues(account.getAccountId(),locator.locatorName("accountno"));
            click(locator.locatorName("AccSubmit"));

            TimeUnit.MILLISECONDS.sleep(1000);
            if (Boolean.TRUE.equals(isDisplayed(locator.locatorName("txtinitdep")))) {
                click(locator.locatorXpath("//option[@value='" + account.getTypeAccount() + "']"));
                click(locator.locatorName("AccSubmit"));


                accountEdited.setAccountId(account.getAccountId());
                accountEdited.setUserIdAccount(findElements(locator.locatorTag("td")).get(7).getText());
                accountEdited.setCustomerNamer(findElements(locator.locatorTag("td")).get(9).getText());
                accountEdited.setEmail(findElements(locator.locatorTag("td")).get(11).getText());
                accountEdited.setTypeAccount(findElements(locator.locatorTag("td")).get(13).getText());
                //CustomerID
                validation = findElements(locator.locatorTag("td")).get(7).getText()
                        .equals(account.getUserIdAccount()) ? true : false;
                //Validación de nombre del cliente
                validation = findElements(locator.locatorTag("td")).get(9).getText()
                        .equals(customer.getName()) ? true : false;
                //Validación de email
                validation = findElements(locator.locatorTag("td")).get(11).getText()
                        .equals(customer.getEmail()) ? true : false;
                //Validación de tipo de cuenta
                validation = findElements(locator.locatorTag("td")).get(13).getText()
                        .equals(account.getTypeAccount()) ? true : false;

            }
        }

        return  accountEdited;




    }

    //Se realiza scroll de la página para encontrar el elemento
    public void scroll() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,500)");
    }




}
