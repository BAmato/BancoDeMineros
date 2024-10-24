/**
 * this class represent a bank account.
 * this class contain methods for deposit, withdraw, transfer founds and pay someone.
 *
 *  @author: P.Lorena Renteria
 *  @version: 2.0
 */

public class Account {
    private int accountNumber; //unique account number for each account
    private double balance;//current balance in the account
    private Customer accountHolder;// the account holder associated with the account

/**
 * constructor for account class
 * @param accountNumber unique account number
 * @param accountHolder the customer who owns the account 
 */
public Account(int accountNumber, Customer accountHolder){
    this.accountNumber = accountNumber;
    this.accountHolder = accountHolder;
    this.balance = 0;// account starts with zero balance
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
public double getBalance(){
    return balance;
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
public void paySomeone(Customer destination, double amount){
    Account destinationAccount = destination.findAccount();//(Customer class method find account) find account of the destination and transfer finds
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
    return accountHolder;// return account holder
}
}
