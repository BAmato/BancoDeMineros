import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class bankMain{

    static Customer customer = new Customer();
    static int selection = 0;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){

        boolean exitProgram = false;

        createMap();        

        customer.setName("Bryan","Amato");
        while(!exitProgram){
            System.out.println("\n-------------El Paso Miners Bank---------------");
            System.out.println("Please make a selection:\n1) Customer\n2) Manager\n3) Exit Program");

            selection = scanner.nextInt();
            exitProgram = mainMenu(selection);
        }
        
        
        scanner.close();
    };

    static boolean mainMenu(int selection){

        switch(selection){
            case 1:                 //Enter customer menu
                while(!customerMenu());
                break;
            case 2:                 //Enter manager menu
                managerMenu();
                break;
            case 3:                 //Exit Program
                System.out.println("Exiting program.");
                return true;
            default:
                System.out.println("Invalid selection.");
                break;
        }
        return false;
    }

    static boolean customerMenu(){
        System.out.println("\n-------Customer Menu----------");
        System.out.println("Please make a selection:\n1) Function1\n2) Function2\n3) Function3\n4) Exit customer menu");
        selection = scanner.nextInt();

        switch(selection){
            case 1:
                System.out.println(customer.getName());
                break;
            case 2:
                function2();
                break;
            case 3:
                function3();
                break;
            case 4:
                System.out.println("Exiting customer menu.");
                return true;
            default:
                System.out.println("Invalid selection.");
                break;
        }
        return false;
    }

    static void managerMenu(){
        System.out.println("Manager menu not available. Returning to main menu.");
    }

    static void function1(){
        System.out.println("Missing function 1 implementation");
    }
    static void function2(){
        //System.out.println("Missing function 2 implementation");
        inquiryAccount("Arae", "Zarzosa");
        inquiryAccount("Daniel", "B");
        inquiryAccount("John", "Doe");
    }
    static void function3(){
        System.out.println("Missing function 3 implementation");
    }

    static void inquiryAccount(String firstName, String lastName){
        
        try {
            File database = new File("BankUsersActive.csv");
            Scanner fileScanner = new Scanner(database);
            boolean userFound = false;

            fileScanner.useDelimiter(",");      // Setting the delimiter to a comma (csv file)
            while(fileScanner.hasNextLine() && !userFound){
                
                fileScanner.nextLine();                 // Reading the next line
                fileScanner.next();                     // Skipping first cell containing ID
                String currentToken = fileScanner.next();       // Saving first name for comparison with user query
                if(currentToken.equals(firstName)){
                    currentToken = fileScanner.next();
                    if(currentToken.equals(lastName)) {
                        System.out.println("user found"+fileScanner.next());
                        userFound = true;
                    }
                }
                else if (userFound = false){
                    System.out.println("User not found");
                }
            };
            fileScanner.close();
            
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
    }

    static void createMap(){

        String database = "BankUsersActive.csv";            //File name in folder must match this
        String newFile = "BankUsersNew.csv";                //New file with modified values

        /*Creating a list that will hold all maps containing the database data.
        The map consists of a string holding the header (column name) and row containing all the headers data.
        Using a list allows us for dynamic sizing as the size of the excel file is assumed to be unknown*/
        List<Map<String, String>> myMap = new ArrayList<>();     

        /*Try-catch block to handle any issues encountered when reading the .csv file */
        try (BufferedReader br = new BufferedReader(new FileReader(database))){

            System.out.println("Reading file: "+database);
            //The bufferedReader will capture a full row as a string which we can parse and store column values
            String line;
            String[] headers = null;

            while((line = br.readLine()) != null){          //While the current row is not null

                String[] values = line.split(",");          //Parse each cell using the comma delimiter
                
                /*If the header is null, store all header values. This condition allows us to store the header
                row only once which will help us store the data according to their type and identify the type
                of data in each column (assumed to be unknown to the user)*/
                if(headers == null){                       
                    headers = values;
                }

                //Once the header row has been stored, we can populate the hashmap 
                else{
                    Map<String, String> row = new HashMap<>();
                    for(int i = 0; i<headers.length; i++){
                        row.put(headers[i], values[i]);
                    }
                    myMap.add(row);
                }
            };
            System.out.println("File read successfully.");
        }
        catch(Exception e){                     //Handle any errors encountered while reading the file
            System.out.println("Error: "+e);
        }

    };
};