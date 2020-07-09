package co.com.structure.data;

import co.com.structure.models.Account;
import co.com.structure.models.Credentials;
import co.com.structure.models.Customer;

public class DataTest {


    public Credentials modelCredentials(){
        Credentials credentials = new Credentials();

        credentials.setEmailId("feauture/ConflictoGitchangeAfterStash@correo.com");
        return  credentials;
    }


    public Account modelAccount() {
        Account account = new Account();
        account.setTypeAccount("Savings");
        account.setInitDeposit("102433");
        return account;

    }


    public Customer modelCustomer(){
        Customer customer = new Customer();
         customer.setName("Steven");
         customer.setGender("m");
         customer.setDateOfbirth("03062020");
         customer.setAddress("Cll Falsa 123");
         customer.setCity("MDE");
         customer.setState("ANT");
         customer.setMobile("57123313121");
         customer.setPassword("admin");
         return customer;




    }





}
