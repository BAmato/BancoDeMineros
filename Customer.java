import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a customer
 * A customer can have multiple accounts (Checking, Savings, Credit)
 * the customer can perform actions like adding accounts, finding accounts, and retrieving account details.
 * @author: Bryan Amato & Team 14
 * @version: 1.0
 */

public class Customer {
    private String name;
    private String address;
    private int customerId;
    private String phoneNumber;
    private String dob;
    
    private List<Account> accounts;// list of accounts owned by the customer

    /**
     * Constructor
     * Initializes the customer with a name, address, and unique ID.
     * @param name The name of the customer
     * @param address The address of the customer
     * @param customerId Unique ID associated with the customer
     */
    public Customer(String name, String address, int customerId, String phoneNumber, String dob) {
        this.name = name;
        this.address = address;
        this.customerId = customerId;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.accounts = new ArrayList<>(); // Initialize an empty list of accounts
    }

    /**
     * Adds an account to the customer's list of accounts.
     * @param account The account to be added
     */
    public void addAccount(Account account) {
        accounts.add(account);//add the account
        System.out.println("Account added for customer " + name + ".");// display account added and the name of the account holder
    }

    /**
     * Removes an account from the customer's accounts
     * @param accountNumber The account number of the account to be removed
     */
    public void removeAccount(int accountNumber) {
        Account accountToRemove = findAccount(accountNumber);// search the account to remove
        if (accountToRemove != null) {// if the account to remove is not equal to null remove
            accounts.remove(accountToRemove);
            System.out.println("Account with number " + accountNumber);// display  the account number to remove
        } else {
            System.out.println("Account with number " + accountNumber + " not found.");// otherwise display account not found
        }
    }

    /**
     * Finds and returns an account owned based on the account number.
     * @param accountNumber The account number
     * @return Account the account found, or return null if not found
     */
    public Account findAccount(int accountNumber) {
        for (Account account : accounts) {// account is equal to account
            if (account.getAccountNumber() == accountNumber) {// if the account number is equal to the customer account number
                return account;// return the account
            }
        }
        return null; // if not return null (account not found)
    }

    /**
     * Returns a list of all accounts owned by the customer.
     * @return The list of accounts
     */
    public List<Account> getAccounts() {
        return accounts;// returns all the accounts the customer have
    }

    /**
     * get the name of the customer.
     * @return The customer's name
     */
    public String getName() {
        return name;// return customer name
    }

    /**
     * get the address of the customer.
     * @return The customer's address
     */
    public String getAddress() {
        return address;// return customer address
    }

    /**
     * get the ID of the customer.
     * @return The customer's ID
     */
    public int getCustomerId() {
        return customerId;//customer's ID
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setDOB(String dob){
        this.dob = dob;
    }

    public String getDOB(){
        return dob;
    }
    /**
     * Displays customer information name, address, and accounts.
     */
    public void displayCustomerDetails() {
        System.out.println("Identification Number: " + customerId);// display customer's ID
        System.out.println("Name: " + this.name);// display customer name
        System.out.println("Address: " + address);//display customer address
        System.out.println("Accounts:");// display customer accounts
        for (Account account : accounts) {// account is equal to account
            System.out.println(" - Account Number: " + account.getAccountNumber() + ", Balance: $" + account.getBalane(account));//display account number, and balance.
        }
    }
};

/**
 * this class represent a bank account.
 * this class contain methods for deposit, withdraw, transfer founds and pay someone.
 *
 *  @author: P.Lorena Renteria
 *  @version: 2.0
 */

class Account {
    private int accountNumber; //unique account number for each account
    //private double balance;//current balance in the account
    private Customer accountHolder;// the account holder associated with the account
    private double balance;

    /**
     * constructor for account class
     * @param accountNumber unique account number
     * @param accountHolder the customer who owns the account 
     */
    public Account(int accountNumber, Customer accountHolder){
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        //this.balance = 0;// account starts with zero balance
    }

    /**
     *  method to deposit money in the account
     * @param amount to deposit into the account
     */
    public void deposit(double amount){
        if(amount > 0){// if the amount is more than 0
        balance += amount; // add the amount to the balance
        System.out.println("Deposited: $" + amount);// print the amount deposited
        }else{// if not 
            System.out.println("Invalid deposit amount");// return invalid
        }

    }

    /**
     * withdraws a specific amount from the account if the balance is sufficient
     *  @param amount to withdraw from the account
     *  @return  true if the withdraw is successful, false otherwise
     */
    public boolean withdraw(double amount){
        if(amount > balance){// if the amount if more than the current balance 
            System.out.println("Insufficient funds");// display Insufficient founds
            return false;// return false
        }else{
            balance -= amount; // idf the balance is more or equal to amount
            System.out.println("Withdrawal pf $" + amount);// display the amount desire to withdrawal
            return true;// return true
        }
    }

    /**
     * return the current balance of the account
     * @return balance (account balance)
     */
    public double getBalance(Account account){
        return account.balance;
    }

    public void setBalance(Account account, double balance){
        account.balance = balance;
    }

    

    /**
     * transfer founds from one account to another
     * @param destination the destination account for the transfer
     * @param amount amount to transfer
     * @return destination account if the transfer is successful, null otherwise
     */
    public Account transferFunds(Account destination, double amount){
        if(amount > balance){// if the amount is more than the balance
            System.out.println("Insufficient Founds");// display Insufficient founds
            return null;// return null
    }else{
        this.withdraw(amount);// withdraw the amount from this account
        destination.deposit(amount);// deposit the amount to the destination account
        System.out.println("Transfer $" + amount + "to the account " + destination.getAccountNumber());//display the desired amount to transfer and the destination account
        return destination;// return the destination account
    }
    }

    /**
     * pay another  customer by transferring funds to another account
     *  @param destination the recipient customer for a payment
     *  @param amount amount to pay
     */
    public void paySomeone(Customer destination, int accountNumber, double amount){
        Account destinationAccount = destination.findAccount(accountNumber);//(Customer class method find account) find account of the destination and transfer finds
        
        if(destination != null ){// if the destination is not null
            this.transferFunds(destinationAccount, amount);
            }else{
                System.out.println("Customer not found");// display customer not found
            }
    }

    /**
     * getter for the account number
     * @return AccountNumber the account number 
     */
    public int getAccountNumber(){
        return accountNumber;// return account number
    }

    /**
     * getter for account holder (represent the customer not just the name) 
     * @return AccountHolder the account holder
     */
    public String getAccountHolder(){
        return accountHolder.getName();// return account holder
    }
};

// MAKE LIST OF SAVING AND CHECKING ACCOUNT THEN ITERATE THROUGH LIST TO ACCESS ACCOUNTS


/**
 * this class represent the  Checking Account
 * checking account allows deposits and withdrawals, with defined overdraft limit
 * @author: P.Lorena Renteria
 * @version: 2.0
 */

class Checking extends Account{
    /**@param overDraftLimit the maximum overdraft limit allowed for the account*/
    private double overDraftLimit;// overdraft limit  to the checking account

    /**
     * constructor for checking account
     * @param accountNumber unique identifier for the account
     * @param accountHolder the customer who owns the account
     * @param overDraftLimit the maximum overdraft limit allowed for the account
     * @param checkingStartingBalance the starting balance for checking account
     * @param balance total balance of checking account
     */

    private double balance;

    public Checking(int accountNumber, Customer accountHolder, double overDraftLimit, double checkingStartingBalance) {
        super(accountNumber, accountHolder);//call the constructor of the parent class 
        this.overDraftLimit = overDraftLimit;// set the overdraft limit for the account
        this.checkingStartingBalance = checkingStartingBalance;
    }

    /**
     * deposit a specific amount into a checking account
     * this method add the amount to the balance
     * @param amount amount to deposit into the account
     */
    @Override
    public void deposit(double amount){
        if(amount > 0){//if the amount is more than 0 deposit the amount
            super.deposit(amount);// call the deposit method of the parent class
            System.out.println("Deposit of $" + amount);// display the deposit amount
        }else{
            System.out.println("Deposit amount incorrect");// if the amount is less than 0 display "deposit amount incorrect"
        }
        }

    /**
     * withdraws a specific amount from the checking account,respecting overdraft limit
     *  this method subtract the amount from the balance
     * if the withdrawal exceeds the balance  and , or  the overdraft limit, the transaction will be denied
     * @param amount amount to withdraw
     * @return true if the withdrawal is successful, return false otherwise
     */
    @Override
    public boolean withdraw(double amount){
        if(amount <= 0){// if the amount is less or equal to 0 
            System.out.println("Amount must be more than 0");// display amount must be more than 0
            return false;//return false
        }
        if( amount > (super.getBalance() + overDraftLimit)){// check if the withdrawal is in the overdraft limit
            System.out.println("Insufficient funds for withdrawal");// display insufficient funds for withdrawal
            return false;// return false
        } else {
            super.withdraw(amount);// call the withdraw method of the parent class and reuse it
            System.out.println("Withdrawal of $" +amount); // display the withdrawal amount
            return true;// return true
        }
    }

    /**
     * returns the overdraft limit for the checking account
     * @return overDraftLimit ove draft limit
     */
    public double getOverDraftLimit(){
        return overDraftLimit;// return the over draft limit
    }

    /**
     * sets a new overdraft limit for the checking account 
     * @param overDraftLimit the new overdraft limit
     */
    public void setOverDraftLimit(double overDraftLimit){
        if(overDraftLimit >= 0){// if the overdraft limit is more than 0 or equal
            this.overDraftLimit = overDraftLimit;// set the overdraft limit
            System.out.println("Overdraft limit updated to $" + overDraftLimit);// display the updated overdraft limit
            }else{// if the overdraft is less  than 0 
                System.out.println("Overdraft limit have to be more than 0");// display overdraft have to be more than 0
        } 
    }

    // public void setBalance(Acount account, double balance){
    //     account.setBalance(balance);
    // }

    // public double getCheckingBalance(){
    //     return this.balance;
    // }
};

/**
 * the Saving class extends Account class.
 * A Savings Account may have a minimum balance requirement, and it allows deposits and withdrawals.
 * @author:
 * @version: 1.0
 */
class Saving extends Account {

    private double minimumBalance;// minimum balance for saving account
    private double savingsStartingBalance; //Starting balance
    private double balance;

    /**
     * Constructor 
     * @param accountNumber Unique identifier for the account
     * @param accountHolder The customer who owns this account
     * @param minimumBalance The minimum balance required for this account
     * @param balance total balance of checking account
     */
    public Saving(int accountNumber, Customer accountHolder, double savingsStartingBalance) {
        super(accountNumber, accountHolder);
        //this.minimumBalance = minimumBalance;
        this.savingsStartingBalance = savingsStartingBalance;
    }

    /**
     * Deposits a specified amount into the savings account.
     * override deposit and add the amount to the current balance.
     * 
     * @param amount The amount to deposit into the account
     */
    @Override
    public void deposit(double amount) {
        if (amount > 0) {// if the amount is more than 0
            super.deposit(amount); // Reuse the deposit method from the parent class
            System.out.println("Deposit of $" + amount + " successful.");//display the deposit amount
        } else {
            System.out.println("Deposit amount must be more than $0");// idf the deposit is less than 0, display amount have to be more than 0
        }
    }

    /**
     * Withdraws a specified amount from the savings account, ensuring the minimum balance is maintained.
     * If the withdrawal causes the balance to drop below the minimum balance, the transaction is denied.
     * 
     * @param amount The amount to withdraw
     * @return true if the withdrawal is successful, false otherwise
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {//if amount is less or equal to 0
            System.out.println("Withdrawal amount must be more than $0.");//display withdrawal have to me more than 0
            return false;
        }

        // Check if the withdrawal would cause the balance to drop below the minimum balance
        if ((super.getBalance() - amount) < minimumBalance) {//if the result of the the balance is less than the minimum balance 
            System.out.println("Withdrawal denied. Minimum balance of $" + minimumBalance + " must be maintained.");// display the minimum balance and denied the transaction 
            return false;
        } else {
            super.withdraw(amount); // Reuse the withdraw method from the parent class
            System.out.println("Withdrawal of $" + amount + " successful.");// display the withdrawal amount
            return true;
        }
    }

    /**
     * Returns the minimum balance required for the account.
     * 
     * @return The minimum balance
     */
    public double getMinimumBalance() {
        return minimumBalance;//return minimum balance
    }

    /**
     * Sets a new minimum balance for this account.
     * 
     * @param minimumBalance The new minimum balance to set
     */
    public void setMinimumBalance(double minimumBalance) {
        if (minimumBalance >= 0) {//if the minimum balance is more or equal to 0
            this.minimumBalance = minimumBalance;
            System.out.println("Minimum balance updated to $" + minimumBalance);//display the updated balance 
        } else {
            System.out.println("Minimum balance cannot be less than $0");// otherwise display minimum amount can not be less than 0
        }
    }

    public void setSavingsStartingBalance(double savingsStartingBalance){
        this.savingsStartingBalance = savingsStartingBalance;
    }

    public double getSavingsStartingBalance(){
        return this.savingsStartingBalance;
    }
};

/**
 * The class credit represent a Credit Account, which extends Account class.
 * A Credit Account allows customers to charge amounts to their account and make payments.
 * 
 * @author: Bryan Amato & Team 14
 * @version: 1.0
 */
class Credit extends Account {

    
    private double creditMax;// credit limit for credit account
    private double startingCreditBalance;

    /**
     * Constructor
     * Initializes the account with an account number, customer (account holder), and a credit limit.
     * @param accountNumber Unique identifier for the account
     * @param accountHolder The customer who owns this account
     * @param creditMax The maximum credit limit for this account
     */
    public Credit(int accountNumber, Customer accountHolder, int creditMax, double startingCreditBalance) {
        super(accountNumber, accountHolder);
        this.startingCreditBalance = startingCreditBalance;
        this.creditMax = creditMax;
    }

    /**
     * Charges a specified amount to the credit account.
     * charge class allows the customer to charge an amount up to their available credit limit.
     * 
     * @param amount The amount to charge
     * @return true if the charge is successful, false if it exceeds the credit limit
     */
    public boolean charge(double amount) {
        if (amount <= 0) {//if the amont is less or equal to 0
            System.out.println("Charge amount must be more than 0.");//display amount have to be more that 0
            return false;// return false if is not positive amount
        }

        
        if (Math.abs(super.getBalance()) + amount > creditMax) {//if the amount exceeds the credit limit 
            System.out.println("Charge denied. Exceeds credit limit.");// display amount exceeds credit limit
            return false;
        } else {
            super.deposit(-amount); // Increase the debt by making a negative deposit 
            System.out.println("Charge of $" + amount + " successful.");//display the charge amount 
            return true;
        }
    }

    /**
     * Makes a payment to the credit account balance.
     * pay credit allows the customer to reduce their debt by paying back an amount.
     * 
     * @param amount The amount to pay towards the balance
     * @return true if the payment is successful, false otherwise
     */
    public boolean payCredit(double amount) {
        if (amount <= 0) {//if the amount is less or equal to 0
            System.out.println("Payment amount must be more than $0");//display payment have to be more than 0
            return false;
        }


        super.deposit(amount); // decrease the debt by making a payment
        System.out.println("Payment of $" + amount + " successful.");//display the payment amount 
        return true;
    }

    /**
     * Overrides the withdraw method from the Account class.
     * Withdrawals are not allowed from a credit account, so this method will always return false.
     * 
     * @param amount The amount to withdraw 
     * @return false as withdrawals are not allowed
     */
    @Override
    public boolean withdraw(double amount) {
        System.out.println("Withdrawals are not allowed from a credit account.");
        return false;
    }

    /**
     * Returns the credit limit for this account.
     * 
     * @return The credit limit
     */
    public double getCreditMax() {
        return creditMax;//return credit limit
    }

    /**
     * Sets a new credit limit for this account.
     * 
     * @param creditMax The new credit limit to set
     */
    public void setCreditMax(double creditMax) {
        if (creditMax >= 0) {// //if credit limit is more or equal to 0
            this.creditMax = creditMax;
            System.out.println("Credit limit updated to $" + creditMax);//display the new credit limit
        } else {
            System.out.println("Credit limit cannot be less than 0.");// display credit limit can not be lees than 0
        }
    }

    /**
     * Returns the available credit based on the credit limit and current balance.
     * 
     * @return The available credit
     */
    public double getAvailableCredit() {
        return creditMax + super.getBalance(); // return the credit limit  and the balance to know the real credit limit
    }

    public void setStartingCreditBalance(double startingCreditBalance){
        this.startingCreditBalance = startingCreditBalance;
    }

    public double getStartingCreditBalance(){
        return this.startingCreditBalance;
    }
};


