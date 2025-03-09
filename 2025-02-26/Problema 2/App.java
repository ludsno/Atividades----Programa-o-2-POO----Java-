import entities.Account;
import exceptions.AccountException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter account data");
        System.out.print("Number: ");
        int number = sc.nextInt();
        sc.nextLine(); // consume the remaining newline
        System.out.print("Holder: ");
        String holder = sc.nextLine();
        System.out.print("Initial balance: ");
        double balance = sc.nextDouble();
        System.out.print("Withdraw limit: ");
        double withdrawLimit = sc.nextDouble();
        sc.nextLine(); // consume the remaining newline



        Account account = new Account(number, holder, balance, withdrawLimit);

        System.out.println("Enter amount for withdraw: ");
        double amount = sc.nextDouble();
        try {
            account.withdraw(amount);
            System.out.println("New balance: " + account.getBalance());
        } catch (AccountException e) {
            System.out.println("Withdraw error: " + e.getMessage());
        } finally {
            sc.close();
        }

    }
}
