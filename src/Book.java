

import java.sql.*;

public class Book {

    public String titre;
    public String isbn;
    public String auteur;
    public String statut;
    public int id_auteur;
    public int id_gestionnaire;
    public int id_emprunteur;
    Book(String titre,String isbn,String auteur,String statut,int id_auteur,int id_gestionnaire,int id_emprunteur)
    {
        this.titre=titre;
        this.isbn=isbn;
        this.auteur=auteur;
        this.statut=statut;
        this.id_auteur=id_auteur;
        this.id_gestionnaire=id_gestionnaire;
        this.id_emprunteur=id_emprunteur;
    }

    //public static final String jdbcUrl = "jdbc:mysql://localhost:3306/library";
   // public static final String username = "root";
    //public static final String password = "";


   // public static boolean addLivre(String titre, String statut, String isbn, int id_gestionnaire, int id_auteur, int id_emprunteur)
   public static boolean addLivre(Book book) {
       String insertQuery = "INSERT INTO livre (title, statut, isbn, id_gestionnaire, id_auteur, id_emprunteur) VALUES (?, ?, ?, ?, ?, ?)";

       try (Connection connection2 = Connect.getConnection();
            PreparedStatement preparedStatement = connection2.prepareStatement(insertQuery)) {

           preparedStatement.setString(1, book.titre);
           preparedStatement.setString(2, book.statut);
           preparedStatement.setString(3, book.isbn);
           preparedStatement.setInt(4, book.id_gestionnaire);
           preparedStatement.setInt(5, book.id_auteur);
           preparedStatement.setInt(6, book.id_emprunteur);

           int rowsAffected = preparedStatement.executeUpdate();
           Connect.closeConnection();
           return rowsAffected > 0; // Return true if a row was inserted
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }









}
