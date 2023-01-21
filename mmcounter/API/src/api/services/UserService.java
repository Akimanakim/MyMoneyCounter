package api.services;

import api.data.Account;
import api.data.Transaction;

import java.sql.Connection;
import java.util.ArrayList;

public interface UserService {
    int getValidAccount(Account account);

    void setCurrentAccount(Account account);
    Account getCurrentAccount();

    void writeToDatabase(Account account);

    void setNewTransaction(Transaction transaction);

    ArrayList<Transaction> writeToTable();

    void deleteFromDatabase(Transaction transaction);
}
