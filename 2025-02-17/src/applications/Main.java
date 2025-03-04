package applications;

import entities.Contribuinte;
import entities.PessoaFisica;
import entities.PessoaJuridica;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n;
        ArrayList<Contribuinte> contribuintes = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of tax payer: ");
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Tax payer #" + (i + 1) + " data:");
            System.out.println("Individual or company (i/c)? ");
            char ch = sc.next().charAt(0);
            sc.nextLine(); // consume the remaining newline
            System.out.println("Name: ");
            String name = sc.nextLine();
            System.out.println("Anual income: ");
            double income = sc.nextDouble();
            if (ch == 'i') {
                System.out.println("Health expenditures: ");
                double healthExpenditures = sc.nextDouble();
                PessoaFisica pf = new PessoaFisica(name, income, healthExpenditures);
                contribuintes.add(pf);
            } else {
                System.out.println("Number of employees: ");
                int numberOfEmployees = sc.nextInt();
                PessoaJuridica pj = new PessoaJuridica(name, income, numberOfEmployees);
                contribuintes.add(pj);
            }
        }
        sc.close();

        System.out.println("TAXES PAID:");
        double sumTaxes = 0;
        for (Contribuinte contribuinte : contribuintes) {
            System.out.println(contribuinte.getNome() + ": $ " + String.format("%.2f", contribuinte.calcularImposto()));
            sumTaxes += contribuinte.calcularImposto();
        }
        System.out.println("TOTAL TAXES: $ " + String.format("%.2f", sumTaxes));
    }
}

/*
package applications;

import entities.Contribuinte;
import entities.PessoaFisica;
import entities.PessoaJuridica;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n;
        ArrayList<Contribuinte> contribuintes = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of tax payer: ");
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Tax payer #" + (i + 1) + " data:");
            System.out.println("Individual or company (i/c)? ");
            char ch = sc.next().charAt(0);
            sc.nextLine(); // consume the remaining newline
            System.out.println("Name: ");
            String name = sc.nextLine();
            System.out.println("Anual income: ");
            double income = sc.nextDouble();
            if (ch == 'i') {
                System.out.println("Health expenditures: ");
                double healthExpenditures = sc.nextDouble();
                PessoaFisica pf = new PessoaFisica(name, income, healthExpenditures);
                contribuintes.add(pf);
            } else {
                System.out.println("Number of employees: ");
                int numberOfEmployees = sc.nextInt();
                PessoaJuridica pj = new PessoaJuridica(name, income, numberOfEmployees);
                contribuintes.add(pj);
            }
        }
        sc.close();

        System.out.println("TAXES PAID:");
        double sumTaxes = 0;
        for (Contribuinte contribuinte : contribuintes) {
            System.out.println(contribuinte.getNome() + ": $ " + String.format("%.2f", contribuinte.calcularImposto()));
            sumTaxes += contribuinte.calcularImposto();
        }
        System.out.println("TOTAL TAXES: $ " + String.format("%.2f", sumTaxes));
    }
}

*/