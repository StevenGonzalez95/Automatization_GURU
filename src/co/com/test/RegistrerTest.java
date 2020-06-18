package co.com.test;

import co.com.structure.CustomerPage;
import co.com.structure.data.DataTest;
import co.com.structure.models.Account;
import co.com.structure.models.Credentials;
import co.com.structure.models.Customer;
import co.com.structure.models.EditAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.JVM)
public class RegistrerTest {


    private  WebDriver driver;
    private  CustomerPage customerPage;
    private  Credentials credentials;
    private  Account account;
    private  DataTest dataTest;
    private  Customer customer;
    private static final String  URL ="http://demo.guru99.com/V4/index.php";


    @Before
    public  void setUp(){
        System.setProperty("webdriver.chrome.driver","./src/co/com/resources/driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
        customerPage = new CustomerPage(driver);
        account = new Account();
        credentials = new Credentials();
        dataTest= new DataTest();
        customer = new Customer();
    }

    @After
    public  void after(){
        driver.quit();
    }




    //Caso de prueba Registro de usuario
    //Se obtienen las credenciales para ingresar a la página de guruTest
    // 1) Con las credenciales obtenidas, se procede a realizar el inicio de sesión del usuario.
    // 2) Se valida que la página de gurú retorne un texto con el usuario que se autenticó.
    // 3) Se guarda la información del registro en la carpeta /resources/evidences

    @Test
    public void registrer() throws InterruptedException, IOException {
        credentials = dataTest.modelCredentials();
        customerPage.getCredentials(credentials);
        String text =customerPage.loginUser(credentials);
        int index = text.indexOf(credentials.getUser());
        if(index>0){
            index =1;
        }
        customerPage.writeEvidences(credentials,account,customer,"Registro de usuario");
        assertEquals(1,index);
       }


    //Caso de prueba Creación de un cliente
    //Se obtienen las credenciales para ingresar a la página de guruTest
    // 1) Con las credenciales obtenidas, se procede a realizar el inicio de sesión del usuario.
    // 2) Se valida que la página de gurú retorne un texto con el usuario que se autenticó.
    // 3) Se elige la opción "new Customer" en la página web. Se valida que esta despliegue
    // 4) Se llena los datos del cliente a crear y se envía la petición
    // 5) Se valida si el cliente fue creado exitosamente o si los datos se deben volver a llenar
    // 6) Se guarda la información de la creación del cliente  en la carpeta /resources/evidences

    @Test
    public void newCustomer() throws InterruptedException, IOException {
        credentials = dataTest.modelCredentials();
        customerPage.getCredentials(credentials);
        customerPage.loginUser(credentials);
        customer = dataTest.modelCustomer();
        customerPage.writeEvidences(credentials,account,customer,"Creación de cliente");
        String text = customerPage.newCustomer(credentials,customer);
        assertEquals("Customer Registered Successfully!!!",text);

    }

    //Caso de prueba Creación de una cuenta
    //Se obtienen las credenciales para ingresar a la página de guruTest
    // 1) Con las credenciales obtenidas, se procede a realizar el inicio de sesión del usuario.
    // 2) Se valida que la página de gurú retorne un texto con el usuario que se autenticó.
    // 3) Se elige la opción "new Customer" en la página web. Se valida que esta despliegue
    // 4) Se llena los datos del cliente a crear y se envía la petición
    // 5) Se valida si el cliente fue creado exitosamente o si los datos se deben volver a llenar
    // 6) Se elige la opción de crear una cuenta.
    // 7) Se envía el monto inicial del depósito y el tipo de cuenta que se requiere
    // 8) Se valida que se haya creado correctamente con el usuario previamente creado. Se obtiene el # de cuenta
    // 9) Se guarda la información de la creación de la  cuenta  en la carpeta /resources/evidences

    @Test
    public void newAccount() throws InterruptedException, IOException {
        credentials = dataTest.modelCredentials();
        customerPage.getCredentials(credentials);
        customerPage.loginUser(credentials);
        customer = dataTest.modelCustomer();
        customerPage.newCustomer(credentials, customer);
        account = dataTest.modelAccount();
        Account accountResponse =customerPage.newAccount(credentials,account);
        account.setUserIdAccount(accountResponse.getUserIdAccount());
        account.setAccountId(accountResponse.getAccountId());
        customerPage.writeEvidences(credentials,accountResponse,customer,"Creación de nueva cuenta");

        assertEquals(true,accountResponse.getSuccess());
        assertEquals(account.getInitDeposit(),accountResponse.getInitDeposit());
        assertEquals(account.getTypeAccount(),accountResponse.getTypeAccount());

    }

    //Caso de prueba Creación de una cuenta
    //Se obtienen las credenciales para ingresar a la página de guruTest
    // 1) Con las credenciales obtenidas, se procede a realizar el inicio de sesión del usuario.
    // 2) Se valida que la página de gurú retorne un texto con el usuario que se autenticó.
    // 3) Se elige la opción "new Customer" en la página web. Se valida que esta despliegue
    // 4) Se llena los datos del cliente a crear y se envía la petición
    // 5) Se valida si el cliente fue creado exitosamente o si los datos se deben volver a llenar
    // 6) Se elige la opción de crear una cuenta.
    // 7) Se envía el monto inicial del depósito y el tipo de cuenta que se requiere
    // 8) Se valida que se haya creado correctamente con el usuario previamente creado. Se obtiene el # de cuenta
    // 9) Se elige la opción de "Delete account" y se le envía la cuenta previamente creada
    // 10) Se valida que se haya eliminado correctamente la cuenta
    // 11) Se guarda la información de la eliminación de la  cuenta  en la carpeta /resources/evidences

    @Test
    public void deleteAccount() throws InterruptedException, IOException {
        newAccount();
        String responseDeleted=customerPage.deleteAccount(account);
        customerPage.writeEvidences(credentials,account,customer,"Eliminación de cuenta");
        assertEquals("Account Deleted Sucessfully",responseDeleted);


    }

    //Caso de prueba editar  una cuenta
    //Se obtienen las credenciales para ingresar a la página de guruTest
    // 1) Con las credenciales obtenidas, se procede a realizar el inicio de sesión del usuario.
    // 2) Se valida que la página de gurú retorne un texto con el usuario que se autenticó.
    // 3) Se elige la opción "new Customer" en la página web. Se valida que esta despliegue
    // 4) Se llena los datos del cliente a crear y se envía la petición
    // 5) Se valida si el cliente fue creado exitosamente o si los datos se deben volver a llenar
    // 6) Se elige la opción de crear una cuenta.
    // 7) Se envía el monto inicial del depósito y el tipo de cuenta que se requiere
    // 8) Se valida que se haya creado correctamente con el usuario previamente creado. Se obtiene el # de cuenta
    // 9) Se elige la opción de editar una cuenta
    // 10)Se ingresa la cuenta a editar y se envía. Se presentan los datos en pantalla y se cambia el tipo de cuenta
    // 11)Se confirma la modificación y se validan los datos del cliente y del tipo de cuenta retornads en pantalla

    @Test
    public void editAccount() throws IOException, InterruptedException {
        newAccount();
        account.setTypeAccount("Current");
        EditAccount editAccount =customerPage.editAccount(account, customer);
        customerPage.writeEvidences(credentials,account,customer,"Caso de prueba de editar cuenta");
        assertEquals(account.getUserIdAccount(),editAccount.getUserIdAccount());
        assertEquals(customer.getName(),editAccount.getCustomerNamer());
        assertEquals(customer.getEmail(),editAccount.getEmail());
        assertEquals(account.getTypeAccount(),editAccount.getTypeAccount());

    }





}
