import java.io.*;
import java.util.Scanner;

public class Methods {
    protected String filePath = "files/test_user_list.txt";
    protected int lineInUserList;
    public void greetUser(Scanner userInput) {
        System.out.println("Welcome on this website.");
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        loginOrRegister(userInput);
    }

    public void loginOrRegister (Scanner userInput) {

        System.out.println("type in: login or register to login to your account or register to join.");
        if (userInput.hasNextLine()) {
            switch (userInput.next().toLowerCase()) {
                case "register" -> registerAccount(userInput);
                case "login" -> login(userInput);
                default -> loginOrRegister(userInput);
            }
        } else {
            System.out.println("Error: Only use login or register");
            loginOrRegister(userInput);
        }
    }

    public void registerAccount (Scanner userInput) {
        System.out.println("Enter your Username here: ");
        String username = userInput.next();
        if (doesUsernameExist(username)) {
            System.out.println("Username is already taken. Please try another one.");
            registerAccount(userInput);
        } else {
            writeFile(username, userInput);
            System.out.println("Congrats on creating an account. You will be redirected to the login screen.");
            login(userInput);
        }

    }
    public boolean doesUsernameExist (String username) {
        try {
            File userList = new File("files/test_user_list.txt");
            Scanner fileScanner = new Scanner(userList);
            fileScanner.useDelimiter("\\n");
            while (fileScanner.hasNext()) {
                lineInUserList++;
                if (fileScanner.nextLine().contains("User: " + username)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    protected void writeFile (String username, Scanner userInput) {
        userInput.useDelimiter("\n");
        try (FileWriter output = new FileWriter(filePath, true)) {
            output.write("User: " + username + " | ");
            System.out.println("Please enter a secure password: ");
            output.write("Password: " + userInput.next() + "\n");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    protected void login(Scanner userInput) { //TODO: new Method for less nesting. Maybe add password loop (3 times)
        System.out.println("Please enter your Username: ");
        String username = userInput.next();
        if (doesUsernameExist(username)) {
            System.out.println("Please enter your Password: ");
            userInput.useDelimiter("\\n");
            String password = userInput.next();
            if(isPasswordCorrect(password)) {
                profileOptions();
            } else {
                lineInUserList = 0;
                System.out.println("Wrong Password. Please try again.");
                login(userInput);
            }
        } else {
            lineInUserList = 0;
            System.out.println("Username not found. Please try again.");
            login(userInput);
        }
    }

    protected boolean isPasswordCorrect (String password) {
        try {
            File userList = new File("files/test_user_list.txt");
            Scanner fileScanner = new Scanner(userList);
            fileScanner.useDelimiter("\\n");

            int i = 1;
            do {
                String wholeLine = fileScanner.next();
                if (i == lineInUserList) {
                    if (wholeLine.contains("Password: " + password)) {
                        return true;
                    }
                }
                i++;
            } while (i <= lineInUserList);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    protected void profileOptions() {
        System.out.println("You have successfully logged in.");
        System.out.println("We'll log you out now.");
    }
}
