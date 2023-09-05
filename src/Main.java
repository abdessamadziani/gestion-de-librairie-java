import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Hello \n");
        System.out.println("My name is Samad \n");

        int choix;
        do {
            System.out.println("\t\t\t\t\n:::::::::::::::::::::::::: Menu Principale ::::::::::::::::::::::::::\n\n");
            System.out.println("\t\t\t 1- Introduire un livre ");
            System.out.println("\t\t\t 2- Introduire plusieurs livres ");
            System.out.println("\t\t\t 3- Emprunté un livre ");
            System.out.println("\t\t\t 4- Affichage ");
            System.out.print("\t\t\t 5- Quitter L'application\n\n");
            System.out.print("\t\t\t Donnez votre choix: ");
            choix = input.nextInt();
            input.nextLine();
        } while (choix != 5);
        System.out.print("\t\t\t Fin de programme ... \n\n");

        try (Connection connection = Connect.getConnection()) {
            String sqlQuery = "SELECT * FROM livre";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Process the result set here
            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println(); // Newline after each row
            }
            Connect.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Book bk = new Book("book mehdi", "xxxx", "samad2", "pas disponible2", 1, 1, 1);







            boolean success = Book.addLivre(bk);
            if (success) {
                System.out.println("Livre added successfully.");
            } else {
                System.out.println("Failed to add the livre.");
            }






    }


}
