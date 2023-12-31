

import javax.swing.*;
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

   public static void afficherLivres()
    {
        String sqlQuery = "SELECT l.titre, l.statut, l.isbn, g.nom_gestionnaire , a.nom_auteur as auteur, em.nom_emprunteur  FROM livre AS l LEFT JOIN auteur AS a ON l.id_auteur = a.id LEFT JOIN gestionnare AS g ON l.id_gestionnaire = g.id LEFT JOIN emprunteur AS em ON l.id_emprunteur = em.id ";

        try (Connection connection = Connect.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);


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




    public static boolean addLivre(Book book) {
       String insertQuery = "INSERT INTO livre (titre, statut, isbn, id_gestionnaire, id_auteur, id_emprunteur) VALUES (?, ?, ?, ?, ?, ?)";

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
           Connect.closeConnection();
           return false;
       }
   }


    public static boolean updateLivre(String titre,Integer id_auteur,String isbn) {
        String insertQuery = "UPDATE livre set titre=?, id_auteur=? where isbn=?";

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
             preparedStatement.setString(1,titre);
             preparedStatement.setInt(2,id_auteur);
             preparedStatement.setString(3,isbn);
             int rowsAffected = preparedStatement.executeUpdate();
            Connect.closeConnection();
             return rowsAffected > 0; // Return true if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            Connect.closeConnection();
            return false;
        }
    }

/*    public static boolean updateLivreStatus(String statut,int id_emprunteur,String isbn) {
        boolean dejaEmprunte = true;
        String checkQuery = "select statut from livre  where isbn=? and statut='disponible'";
        try (Connection connection = Connect.getConnection();
             Statement statement = connection.createStatement();

             //preparedStatement.setString(1,isbn);
             ResultSet resultSet = statement.executeQuery(checkQuery)) {

            if (resultSet.next()) {
                //lastEmprunteurId = resultSet.getInt("id");
                dejaEmprunte = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            Connect.closeConnection();
        }


        String insertQuery = "UPDATE livre set statut=?, id_emprunteur=?  where isbn=?";


        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, statut);
            preparedStatement.setInt(2, id_emprunteur);
            preparedStatement.setString(3, isbn);
            if (dejaEmprunte == false) {
                int rowsAffected = preparedStatement.executeUpdate();
                Connect.closeConnection();
                return rowsAffected > 0; // Return true if a row was inserted
            } else
            {
                System.out.println("Ce livre déja empunté");
                return false;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }*/








    public static boolean checkLivreEmprunte(String isbn) {
        String checkQuery = "SELECT statut FROM livre WHERE isbn=? AND statut='disponible'";
        boolean statut=true;

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                statut=true; // Book is available
            } else {
                // Book is not available
                statut=false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection();
        }
        return statut;

    }





    public static boolean updateLivreStatus(String statut, int id_emprunteur, String isbn) {
        String checkQuery = "SELECT statut FROM livre WHERE isbn=? AND statut='disponible'";
        boolean alreadyEmprunt =true;  // itialize to false

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                alreadyEmprunt =false; // Book is already borrowed
                String updateQuery = "UPDATE livre SET statut=?, id_emprunteur=? WHERE isbn=?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, statut);
                    updateStatement.setInt(2, id_emprunteur);
                    updateStatement.setString(3, isbn);
                    int rowsAffected = updateStatement.executeUpdate();
                    System.out.println("Livre mis à jour.");
                    return rowsAffected > 0; // Return true if a row was updated
                }
            } else {
                // Book is available, proceed with the update
                System.out.println("Ce livre est déjà emprunté");


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connect.closeConnection();
        }
        return alreadyEmprunt; // Return true if book is already borrowed, false if there was an error
    }


    public static boolean returnLivre(String statut,String cin, String isbn,int id_emprunteur ) {
        String checkQuery = "SELECT cin FROM emprunteur WHERE cin=?";
        boolean borrower =false;  // itialize to false

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
            preparedStatement.setString(1,cin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                borrower =true; // Book is already borrowed
                String updateQuery = "UPDATE livre SET statut=?, id_emprunteur=null WHERE isbn=?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, statut);
                    updateStatement.setString(2, isbn);

                    int rowsAffected = updateStatement.executeUpdate();
                    System.out.println("Livre mis à jour.");
                    return rowsAffected > 0; // Return true if a row was updated
                }
            } else {
                // Book is available, proceed with the update
                System.out.println("Ce livre est déjà emprunté");


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connect.closeConnection();
        }
        return borrower; // Return true if book is already borrowed, false if there was an error
    }

    public static boolean checkDate() {
        String checkQuery = "SELECT l.statut, em.cin FROM emprunteur em INNER JOIN livre l ON l.id_emprunteur = em.id WHERE CURRENT_DATE > em.date_return AND l.statut = 'emprunté' ";
        boolean borrower =false;  // itialize to false

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
            //preparedStatement.setString(1,cin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                borrower =true; // Book is already borrowed
                String updateQuery = "UPDATE livre l INNER JOIN emprunteur em ON l.id_emprunteur = em.id SET l.statut = 'perdu' WHERE l.statut = 'emprunté' AND CURRENT_DATE > em.date_return ";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    int rowsAffected = updateStatement.executeUpdate();
                    System.out.println("Livres perdu mis à jour.");
                    return rowsAffected > 0; // Return true if a row was updated
                }
            } else {
                // Book is available, proceed with the update
                System.out.println("ilya pas des livre perdu");


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connect.closeConnection();
        }
        return borrower; // Return true if book is already borrowed, false if there was an error
    }



    public static boolean chercherLivreParTitre(String titre)
        {
            String selectQuery = "SELECT l.titre ,l.statut, l.isbn,  a.nom_auteur FROM livre as l INNER JOIN auteur as a ON l.id_auteur = a.id WHERE l.titre LIKE ?";
            boolean found=false;

            try (Connection connection = Connect.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                // Set the parameter
                preparedStatement.setString(1,titre); // Using LIKE with wildcard '%' to match partial titles

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Check if there are any rows in the result set

                    while (resultSet.next()) {
                        found=true;
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

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                Connect.closeConnection();

              }
            return found;


        }


    public static boolean chercherLivreParAuteur(String auteur)
    {
        String selectQuery = "SELECT l.titre ,l.statut, a.nom_auteur FROM livre as l INNER JOIN auteur as a ON l.id_auteur = a.id WHERE a.nom_auteur LIKE ?";
        boolean found=false;

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            // Set the parameter
            preparedStatement.setString(1,auteur); // Using LIKE with wildcard '%' to match partial titles

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Check if there are any rows in the result set

                while (resultSet.next()) {
                    found=true;
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

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connect.closeConnection();

        }
        return found;


    }









    public static boolean deleteLivre(String isbn) {
        String sqlQuery = "DELETE FROM livre WHERE isbn=?";

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, isbn);
            int rowsAffected = preparedStatement.executeUpdate();
            Connect.closeConnection();
            return rowsAffected > 0; // Return true if a row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public static void raportStatistiques()
    {
        String sqlQuery = "SELECT COUNT(CASE WHEN statut = 'disponible' THEN 1 ELSE NULL END) AS 'Livres Disponible', COUNT(CASE WHEN statut = 'emprunte' THEN 1 ELSE NULL END) AS 'Livres Emprunté', COUNT(CASE WHEN statut = 'perdu' THEN 1 ELSE NULL END) AS 'Livres Perdu',COUNT(*) AS 'Total Livres' FROM livre";

        try (Connection connection = Connect.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);


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










}
