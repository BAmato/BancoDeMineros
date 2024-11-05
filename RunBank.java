
/**
 * Main class to run the bank system.
 * This class will initialize bank users, manage their accounts, and provide user interaction options.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ListIterator;;

public class RunBank {

    // Map to store customers by their ID
    private static Map<Integer, Customer> customers = new HashMap<>();

    /*Creating a list that will hold all maps containing the database data.
    The map consists of a string holding the header (column name) and row containing all the headers data.
    Using a list allows us for dynamic sizing as the size of the excel file is assumed to be unknown*/
    private static List<Map<String, Object>> customerList = new ArrayList<>();     
    
    private static Scanner scanner = new Scanner(System.in);
    private static final String LOG_FILE = "Bank_transaction.log";//log file name

    /**
     * Main method to run the bank system.
     * This method will initialize bank customers and allow for interaction with their accounts.
     */
    public static void main(String[] args) {
        createMap();
       // bank.displayCustomerInformation();  // display all customer data
        runMenu();
    }
     /**
      * Logs a transaction with a timestamp.
      *@param logMessage The message to be logged
      */
    private static void logTransaction (String logMessage) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + logMessage + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }


    /**
     * Initializes bank users from the database file.
     * This method simulates accessing bank system information.
     */

    public static void createMap(){

        String database = "C:\\Users\\bryan\\Downloads\\BankUsersTest.csv";            //File name in folder must match this
       // String database = "BankUsersActive.csv"
        String newFile = "BankUsersNew.csv";                //New file with modified values

        /*Try-catch block to handle any issues encountered when reading the .csv file */
        try (BufferedReader br = new BufferedReader(new FileReader(database))){

            System.out.println("Reading file: "+database);
            //The bufferedReader will capture a full row as a string which we can parse and store column values
            String line;
            String[] headers = null;
            int addressIndex = 0;
            //int j = 0;

            while((line = br.readLine()) != null){          //While the current row is not null

                String[] values = line.split(",");          //Parse each cell using the comma delimiter
                
                /*If the header is null, store all header values. This condition allows us to store the header
                row only once which will help us store the data according to their type and identify the type
                of data in each column (assumed to be unknown to the user)*/
                if(headers == null){                       
                    headers = values;
                    
                    for(int i = 0;i<headers.length;i++){
                        if(headers[i].matches("Address")){
                            addressIndex = i;
                            System.out.println("Address Header Index: "+i);
                        }
                    }
                }

                //Once the header row has been stored, we can populate the hashmap  
                else{
                    Map<String, Object> customerMap = new HashMap<>();
                    
                    int i = -1;
                    int j = 0;
                    for(i = 0; i<headers.length; i++){          //iterate through all columns
                        
                        //if(i == addressIndex && i + 2 < values.length)
                        if(i== addressIndex){
                            String address = values[j] + ", " + values[j+1] + ", " + values[j+2];
                            customerMap.put(headers[i], address);
                            j += 3;
                        }
                        else if(values[j].matches("-?\\d+")){
                            System.out.println("Integer found: "+values[j]);
                            customerMap.put(headers[i], Integer.parseInt(values[j]));   // If value is an integer
                            j++;
                        }
                        else if(values[j].matches("-?\\d+\\.\\d+")){
                            System.out.println("Double found: "+values[j]);
                            customerMap.put(headers[i], Double.parseDouble(values[j])); // If value is a double 
                            j++;
                        }
                        else {
                            System.out.println("String found: "+values[j]);
                            customerMap.put(headers[i], values[j]); // Otherwise, treat it as a string
                            j++;
                        }
                        System.out.println("Header: " + headers[i] + ", Value: " + values[j-1]);
                    }
                    customerList.add(customerMap);
                }    
                
                ListIterator<Map<String, Object>> customerIterator = customerList.listIterator();
                while(customerIterator.hasNext()){
                    Map<String, Object> customerList = customerIterator.next();
                    int customerId = (int) customerList.get("Identification Number");
                    String name = (String) customerList.get("First Name") + " "+ (String) customerList.get("Last Name");
                    String address = (String) customerList.get("Address");
                    String phoneNumber = (String) customerList.get("Phone Number");
                    String dob = (String) customerList.get("Date of Birth");
                    Customer customer = new Customer(name, address, customerId, phoneNumber, dob);
                    
                    int checkingAccountNumber = (int) customerList.get("Checking Account Number");
                    double checkingStartingBalance = customerList.get("Checking Starting Balance") instanceof Integer ?
                        ((Integer) customerList.get("Checking Starting Balance")).doubleValue() : (double) customerList.get("Checking Starting Balance");
                    Checking checkingAccount = new Checking(checkingAccountNumber, customer, checkingStartingBalance);
                    customer.addAccount(checkingAccount);

                    int savingsAccountNumber = (int) customerList.get("Savings Account Number");
                    double savingsStartingBalance = customerList.get("Savings Starting Balance") instanceof Integer ? 
                        ((Integer) customerList.get("Savings Starting Balance")).doubleValue() : (double) customerList.get("Savings Starting Balance");
                    Saving savingsAccount = new Saving(savingsAccountNumber, customer, savingsStartingBalance);
                    customer.addAccount(savingsAccount);

                    int creditAccountNumber = (int) customerList.get("Credit Account Number");
                    int creditMax = (int) customerList.get("Credit Max");
                    double creditStartingBalance = customerList.get("Credit Starting Balance") instanceof Integer ? 
                        ((Integer) customerList.get("Credit Starting Balance")).doubleValue(): (double) customerList.get("Credit Starting Balance");
                    Credit creditAccount = new Credit(creditAccountNumber, customer, creditMax, creditStartingBalance);
                    customer.addAccount(creditAccount);

                    System.out.println("Customer Object and Accounts Created.");
                    customer.displayCustomerDetails();

                }
            };
            System.out.println("File read successfully.");
        }
        catch(Exception e){                     //Handle any errors encountered while reading the file
            System.out.println("Error: "+e);
        }
        System.out.println("Map created. Size: "+customerList.size());
    };

    /**
     * Displays all customers and their account details.
     */
    public static void displayCustomerInformation() {

        //TO-FIX: Call function from within Customer class instead of main
        ListIterator<Map<String, Object>> it = customerList.listIterator();
        while(it.hasNext()){
            Map<String, Object> temp = it.next();
            System.out.println("\n------------------- Customer Info ------------------------");
            System.out.println("Customer Name: "+temp.get("First Name")+" "+temp.get("Last Name"));
            System.out.println("Identification Number: "+temp.get("Identification Number"));
            System.out.println("Date of Birth: "+temp.get("Date of Birth"));
            System.out.println("Address: "+temp.get("Address"));
            System.out.println("Phone Number: "+temp.get("Phone Number"));
            System.out.println("Checking Account Number: "+temp.get("Checking Account Number"));
            System.out.println("Checking Starting Balance: "+temp.get("Checking Starting Balance"));
            System.out.println("Savings Account Number: "+temp.get("Savings Account Number"));
            System.out.println("Savings Starting Balance: "+temp.get("Savings Starting Balance"));
            System.out.println("Credit Account Number: "+temp.get("Credit Account Number"));
            System.out.println("Credit Max: "+temp.get("Credit Max"));
            System.out.println("Credit Starting Balance: "+temp.get("Credit Starting Balance"));
        }
    }

    /**
     * Finds a customer by their unique ID.
     * @param customerId The ID of the customer
     * @return The Customer object, or null if not found
     */
    public static Customer findCustomerById(int customerId) {
        for(Map<String, Object> customer : customerList){
            if((int) customer.get("Identification Number") == customerId) {
                System.out.println("Customer found: "+ customer);
                return (Customer) customer;
            }
        }
        return null;
    }

     /**
     * Menu system for bank interactions.
     * This method allows user to interact with the banking system through a menu.
     */
    public static void runMenu() {
        //TO-FIX: Flag seems redundant, review code logic.
        System.out.println("Welcome to El Paso Miners Bank!");
        System.out.print("Are you an individual or bank manager? (I/B): ");
        boolean flag = true;
        do{
            char userType = scanner.next().charAt(0);
            if (userType == 'I' || userType == 'i') {
                // Call individual menu (existing one)
                flag = false;
                individualMenu();
            } else if (userType == 'B' || userType == 'b') {
                // Call bank manager menu
                flag = false;
                bankManagerMenu();
            } else {
                scanner.nextLine();
                System.out.println("Invalid input, please select I for individual, B for bank manager.");
            }
        } while(flag);
    }

    public static void individualMenu(){
        int choice;
        do {
            System.out.println("------------- Individual Menu ----------------");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Transfer Money");
            System.out.println("4. Display Account Information");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleDeposit();
                    break;
                case 2:
                    handleWithdrawal();
                    break;
                case 3:
                    handleTransfer();
                    break;
                case 4:
                    displayCustomerInformation();
                    break;
                case 5:
                    System.out.println("Exiting the system. Thank you for using El Paso Miners Bank!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    public static void bankManagerMenu(){
        int choice;
        do {
            System.out.println("Welcome Bank Manager!");
            System.out.println("1. Inquire account by name");
            System.out.println("2. Inquire account by type/number");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
    
            switch (choice) {
                case 1:
                    inquireAccountByName();
                    break;
                case 2:
                    inquireAccountByTypeOrNumber();
                    break;
                case 3:
                    System.out.println("Exiting.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }
    public static void inquireAccountByName() {
        System.out.print("Enter customer name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        for (Customer customer : customers.values()) {
            if (customer.getName().equalsIgnoreCase(name)) {
                customer.displayCustomerDetails();
                return;
            }
        }
        System.out.println("Customer not found.");
    }
    
    public static void inquireAccountByTypeOrNumber() {
        System.out.print("Enter account type (Checking/Saving/Credit): ");
        String type = scanner.next();
        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
    
        for (Customer customer : customers.values()) {
            Account account = customer.findAccount(accountNumber);
            if (account != null && account.getClass().getSimpleName().equalsIgnoreCase(type)) {
                System.out.println("Account found:");
                System.out.println("Account Type: " + type + ", Account Number: " + accountNumber + ", Balance: $" + account.getBalance());
                return;
            }
        }
        System.out.println("Account not found.");
    }

    /**
     * Handles deposits to a customer account.
     */
    public static void handleDeposit() {
        try{ 
            System.out.print("Enter customer ID: ");
            int customerId = scanner.nextInt();
            Customer customer = findCustomerById(customerId);

        if (customer != null) {
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            Account account = customer.findAccount(accountNumber);

            if (account != null) {
                System.out.print("Enter deposit amount: ");
                double amount = scanner.nextDouble();
                account.deposit(amount);
                System.out.println("Deposit successful. New balance: $" + account.getBalance());
            
            //log the transaction
            logTransaction(customer.getName()+ "deposited $" + amount + "to account" + accountNumber + "New balance: $" + account.getBalance());
            } else {
                System.out.println("Account not found.");
            }
        } else {
            System.out.println("Customer not found.");
        }
        }catch(InputMismatchException e){
            System.out.println("Invalid input. please enter valid information");
            scanner.next();
    }
}

    /**
     * Handles withdrawals from a customer account.
     */
    public static void handleWithdrawal() {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        Customer customer = findCustomerById(customerId);

        if (customer != null) {
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            Account account = customer.findAccount(accountNumber);

            if (account != null) {
                System.out.print("Enter withdrawal amount: ");
                double amount = scanner.nextDouble();
                if (account.withdraw(amount)) {
                    System.out.println("Withdrawal successful. New balance: $" + account.getBalance());
                
                // Log the transaction
                logTransaction(customer.getName() + " withdrew $" + amount + " from Account-" + accountNumber + ". New balance: $" + account.getBalance());

                } else {
                    System.out.println("Withdrawal failed due to insufficient balance or other restrictions.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }

    /**
     * Handles transferring money between two accounts.
     */
    public static void handleTransfer() {
        System.out.print("Enter sender customer ID: ");
        int senderId = scanner.nextInt();
        Customer sender = findCustomerById(senderId);

        if (sender != null) {
            System.out.print("Enter sender's account number: ");
            int senderAccountNumber = scanner.nextInt();
            Account senderAccount = sender.findAccount(senderAccountNumber);

            if (senderAccount != null) {
                System.out.print("Enter recipient customer ID: ");
                int recipientId = scanner.nextInt();
                Customer recipient = findCustomerById(recipientId);

                if (recipient != null) {
                    System.out.print("Enter recipient's account number: ");
                    int recipientAccountNumber = scanner.nextInt();
                    Account recipientAccount = recipient.findAccount(recipientAccountNumber);

                    if (recipientAccount != null) {
                        System.out.print("Enter transfer amount: ");
                        double amount = scanner.nextDouble();

                        if (senderAccount.transferFunds(recipientAccount, amount) != null) {
                            System.out.println("Transfer successful.");


                        //log the transaction
                        logTransaction(sender.getName() + " transferred $" + amount + " to " + recipient.getName() + ". Sender's new balance: $" + senderAccount.getBalance() + ". Recipient's new balance: $" + recipientAccount.getBalance());
                        
                    } else {
                            System.out.println("Transfer failed due to insufficient funds.");
                        }
                    } else {
                        System.out.println("Recipient's account not found.");
                    }
                } else {
                    System.out.println("Recipient not found.");
                }
            } else {
                System.out.println("Sender's account not found.");
            }
        } else {
            System.out.println("Sender not found.");
        }
    }
    // public void saveBankUsers() {
    //     try (FileWriter writer = new FileWriter("BankUsersUpdated.csv")) {
    //         writer.write("ID,Name,Address,Checking Account,Saving Account,Credit Account\n");
    //         for (Customer customer : customers.values()) {
    //             writer.write(customer.toCsvString()); // Assuming the Customer class has a method to return CSV-formatted data
    //         }
    //     } catch (IOException e) {
    //         System.out.println("Error saving bank users: " + e.getMessage());
    //     }
    // }
    
    public static void exitProgram() {
        //saveBankUsers();
        System.out.println("Thank you for using El Paso Miners Bank. Goodbye!");
        System.exit(0);
    }
    
}

