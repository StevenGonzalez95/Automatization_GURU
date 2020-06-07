package co.com.structure.models;

import lombok.Data;

@Data
public class Account {
    private String accountId;
    private String initDeposit;
    private String typeAccount;
    private String userIdAccount;
    private Boolean success;
}
