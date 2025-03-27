import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {

        System.out.print("Enter your username: ");

        Scanner loginScanner = new Scanner(System.in);
        String username_from_input = loginScanner.nextLine();

        System.out.print("Enter your password: ");
        String password_from_input = loginScanner.nextLine();

        System.out.println("Your username is: " + username_from_input);
        System.out.println("Your password is: " + password_from_input);
        
        User me = new User(username_from_input, password_from_input);
        File myfile = new File("accounts.txt");

        if (myfile.exists()){
            System.out.println("File exists\n");
        }else{
            System.out.println("File doesn't exist\n");
        }

        // Scanner fileScanner = new Scanner(myfile);
        // while(fileScanner.hasNextLine()){
        //     String filedata = fileScanner.nextLine();
        //     System.out.println(filedata);
        //     String checker;
        //     checker = username_from_input + "," + password_from_input;
        //     if (checker.equals(filedata)){
        //         System.out.println("login successful");
        //         break;
                
        //     }else{
        //         System.out.println("login unsuccessful");
                
        //     }
            
        // }
        Boolean account_exists = false;

        Scanner fileScanner = new Scanner(myfile);
        while(fileScanner.hasNextLine()){
            String filedata = fileScanner.nextLine();
            
            String usercheck = filedata.split(",")[0];
            String passcheck = filedata.split(",")[1];
            if (username_from_input.equals(usercheck) && password_from_input.equals(passcheck)){
                account_exists = true;
                break;
            }
    
            
                        
                        
            
        }
        if (account_exists){
            System.out.println("login successful, Hello " + me.getUsername() );
        }else{
            System.out.println("login unsuccessful");
        }
        

        fileScanner.close();
    }
}
