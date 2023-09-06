

import java.sql.*;

public class Book {

    public String titre;
    public String isbn;
    public String auteur;
    public String statut;
    public int id_auteur;
    public int id_gestionnaire;
    public int id_emprunteur;
    Book(String titre,String statut,String isbn,int id_gestionnaire,int id_auteur,int id_emprunteur)
    {
        this.titre=titre;
        this.statut=statut;
        this.isbn=isbn;
        this.id_gestionnaire=id_gestionnaire;
        this.id_auteur=id_auteur;
        this.id_emprunteur=id_emprunteur;
    }
    Book(String titre,String statut,String isbn,int id_gestionnaire,int id_auteur)
    {
        this.titre=titre;
        this.statut=statut;
        this.isbn=isbn;
        this.id_gestionnaire=id_gestionnaire;
        this.id_auteur=id_auteur;

    }

    //public static final String jdbcUrl = "jdbc:mysql://localhost:3306/library";
   // public static final String username = "root";
    //public static final String password = "";
    public static void afficherLivres()
    {
        String sqlQuery = "SELECT * FROM livre";

        try (Connection connection = Connect.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Process the result set here
         /*   while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println() ;// Newline after each row
                System.out.println(columnCount);

            }*/


            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();

                // Define the border characters
                char horizontalBorder = '-';
                char verticalBorder = '|';
                char cornerBorder = '+';

                // Print the top border
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(cornerBorder);
                    for (int j = 0; j <20; j++) { // Adjust the width (e.g., 20 characters)
                        System.out.print(horizontalBorder);
                    }
                }
                System.out.println(cornerBorder); // End of the top border

                // Print column headers and data
                for (int i = 1; i <= columnCount; i++) {
                    String columnHeader = resultSet.getMetaData().getColumnName(i);
                    System.out.printf("%c %-18s ", verticalBorder, columnHeader); // Adjust the width (e.g., 20 characters)

                }
                System.out.println(verticalBorder); // End of the headers row

                // Print rows
                do {


                    // Print the top border
                    for (int k = 1; k <= columnCount; k++) {
                        System.out.print(cornerBorder);
                        for (int j = 0; j <20; j++) { // Adjust the width (e.g., 20 characters)
                            System.out.print(horizontalBorder);
                        }
                    }
                    System.out.println(cornerBorder); // End of the top border


                    for (int i = 1; i <= columnCount; i++) {
                        String columnValue = resultSet.getString(i);
                        System.out.printf("%c %-18s ", verticalBorder, columnValue); // Adjust the width (e.g., 20 characters)
                    }
                    System.out.println(verticalBorder); // End of each data row

                } while (resultSet.next());

                // Print the bottom border
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(cornerBorder);
                    for (int j = 0; j < 20; j++) { // Adjust the width (e.g., 20 characters)
                        System.out.print(horizontalBorder);
                    }
                }
                System.out.println(cornerBorder); // End of the bottom border
            }



            Connect.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

   // public static boolean addLivre(String titre, String statut, String isbn, int id_gestionnaire, int id_auteur, int id_emprunteur)
   public static boolean addLivre(Book book) {
       String insertQuery = "INSERT INTO livre (title, statut, isbn, id_gestionnaire, id_auteur, id_emprunteur) VALUES (?, ?, ?, ?, ?, ?)";

       try (Connection connection = Connect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
           preparedStatement.setString(1, book.titre);
           preparedStatement.setString(2, book.statut);
           preparedStatement.setString(3, book.isbn);
           preparedStatement.setInt(4, book.id_gestionnaire);
           preparedStatement.setInt(5, book.id_auteur);
           //preparedStatement.setInt(6, book.id_emprunteur == 0 ? null : book.id_emprunteur);
           // Handle id_emprunteur
           if (book.id_emprunteur == 0) {
               preparedStatement.setNull(6, Types.INTEGER);
           } else {
               preparedStatement.setInt(6, book.id_emprunteur);
           }

           int rowsAffected = preparedStatement.executeUpdate();
           Connect.closeConnection();
           return rowsAffected > 0; // Return true if a row was inserted
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }


    public static boolean supprimerLivre(String isbn) {
        String sqlQuery = "DELETE FROM livre WHERE isbn=?";

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, isbn);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if a row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }










}
