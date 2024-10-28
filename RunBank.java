
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
    private Map<Integer, Customer> customers = new HashMap<>();

    /*Creating a list that will hold all maps containing the database data.
    The map consists of a string holding the header (column name) and row containing all the headers data.
    Using a list allows us for dynamic sizing as the size of the excel file is assumed to be unknown*/
    List<Map<String, Object>> customerList = new ArrayList<>();     
    
    private Scanner scanner = new Scanner(System.in);
    private static final String LOG_FILE = "Bank_transaction.log";//log file name

    /**
     * Main method to run the bank system.
     * This method will initialize bank customers and allow for interaction with their accounts.
     */
    public static void main(String[] args) {
        RunBank bank = new RunBank();
       // bank.initializeBankUsers();
        bank.createMap();
        bank.displayAllCustomers();  // display all customer data
        bank.runMenu();
    }
     /**
      * Logs a transaction with a timestamp.
      *@param logMessage The message to be logged
      */
    private void logTransaction (String logMessage) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + logMessage + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }


    /**
     * Initializes bank users from the uploaded from the file.
     * This method simulates loading users into the bank system.
     */
    public void initializeBankUsers() {
        // List of bank users (extracted from the file)
        String[][] usersData = {
            {"79", "Daniel", "A", "5-Mar-39", "500 W. University Ave, El Paso, TX 79968", "(915) 747-5042", "1078", "857.56", "2078", "3364.79", "3078", "7985", "-786.93"},
            {"49", "Derek", "Aguirre", "5-May-41", "500 W. University Ave, El Paso, TX 79968", "(915) 747-5012", "1048", "1845.56", "2048", "3352.49", "3048", "8899", "-58.57"},
            {"18", "Jafar", "Aladdin", "20-Jan-83", "1313 Disneyland Dr, Anaheim, CA 92802", "(714) 781-4636", "1017", "518.07", "2017", "2220.54", "3017", "3029", "-406.48"},
            {"76", "Nathan", "Aun", "6-Aug-94", "500 W. University Ave, El Paso, TX 79968", "(915) 747-5039", "1075", "1352.30", "2075", "132.29", "3075", "8789", "-1240.71"},
            {"38", "Alex", "Avila", "19-Oct-65", "500 W. University Ave, El Paso, TX 79968", "(915) 747-5001", "1037", "85.09", "2037", "516.33", "3037", "3484", "-1827.58"}
        };

        // Iterate through the data and create Customer and Account objects
        for (String[] userData : usersData) {
            int customerId = Integer.parseInt(userData[0]);
            String firstName = userData[1];
            String lastName = userData[2];
            String name = firstName + " " + lastName;
            String address = userData[4];
            String dob = userData[3]; 
            String phoneNumber = userData[5]; 

            // Create a new customer
            Customer customer = new Customer(name, address, customerId);

            // Create checking account
            int checkingAccountNumber = Integer.parseInt(userData[6]);
            double checkingStartingBalance = Double.parseDouble(userData[7]);
            Checking checkingAccount = new Checking(checkingAccountNumber, customer, 500); // Assuming an overdraft limit of 500
            checkingAccount.deposit(checkingStartingBalance);
            customer.addAccount(checkingAccount);

            // Create savings account
            int savingAccountNumber = Integer.parseInt(userData[8]);
            double savingStartingBalance = Double.parseDouble(userData[9]);
            Saving savingAccount = new Saving(savingAccountNumber, customer, 100); // Assuming a minimum balance of 100
            savingAccount.deposit(savingStartingBalance);
            customer.addAccount(savingAccount);

            // Create credit account
            int creditAccountNumber = Integer.parseInt(userData[10]);
            double creditLimit = Double.parseDouble(userData[11]);
            double principle = Double.parseDouble(userData[12]);
            Credit creditAccount = new Credit(creditAccountNumber, customer, creditLimit);
            creditAccount.charge(-principle); // Start with the negative balance
            customer.addAccount(creditAccount);

            // Add customer to the bank system
            customers.put(customerId, customer);
        }
    }

    public void createMap(){

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

                // for(Map.Entry<String, Object> entry: customerMap.entrySet()){
                //     String[] value = entry;

                // }
                
            };
            
            // for(Map<String, Object> customer : customerList){
            //     System.out.println(customer.get("Identification Number"));
            // }
            // for (Map<String, Object> customer : customerList) {
            //     for (Map.Entry<String, Object> entry : customer.entrySet()) {
            //         System.out.println(entry.getKey() + ": " + entry.getValue());
            //     }
            //     System.out.println("---------------------------------");
            // }
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
    public void displayAllCustomers() {
        ListIterator<Map<String, Object>> it = customerList.listIterator();
        while(it.hasNext()){
            //System.out.println(it.next());
            //System.out.println(it.next());
            Map<String, Object> temp = it.next();
            System.out.println(temp.getCustomerId);
        }
        
        // for (Map<String, Object> customer : customerList) {
        //     System.out.println("ID: "+customer.getCustomerId);
        //    // customer.displayCustomerDetails();
        //     System.out.println("--------------------------------------------");
        // }
    }

    /**
     * Finds a customer by their unique ID.
     * @param customerId The ID of the customer
     * @return The Customer object, or null if not found
     */
    public Customer findCustomerById(int customerId) {
        return customers.get(customerId);
    }

     /**
     * Menu system for bank interactions.
     * This method allows user to interact with the banking system through a menu.
     */
    public void runMenu() {
        System.out.print("Are you an individual or bank manager? (I/B): ");
        char userType = scanner.next().charAt(0);
    
        if (userType == 'I' || userType == 'i') {
            // Call individual menu (existing one)
            individualMenu();
        } else if (userType == 'B' || userType == 'b') {
            // Call bank manager menu
            bankManagerMenu();
        } else {
            System.out.println("Invalid input. Exiting.");
        }
    }
    public void individualMenu(){
        int choice;
        do {
            System.out.println("Welcome to El Paso Miners Bank!");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Transfer Money");
            System.out.println("4. Display All Customers");
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
                    displayAllCustomers();
                    break;
                case 5:
                    System.out.println("Exiting the system. Thank you for using El Paso Miners Bank!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    public void bankManagerMenu(){
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
    public void inquireAccountByName() {
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
    
    public void inquireAccountByTypeOrNumber() {
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
    public void handleDeposit() {
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
    public void handleWithdrawal() {
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
    public void handleTransfer() {
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
    
    public void exitProgram() {
        //saveBankUsers();
        System.out.println("Thank you for using El Paso Miners Bank. Goodbye!");
        System.exit(0);
    }
    
}

