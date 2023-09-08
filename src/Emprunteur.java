import java.sql.*;
import java.time.LocalDate;

public class Emprunteur {

        public String nom;
        public String prenom;
        public String cin;

    public LocalDate date_emprunt;
    public LocalDate date_retour;
    public int id_gestionnaire;


    Emprunteur(String nom,String prenom, String cin,LocalDate date_emprunte,LocalDate date_retour)
        {
            this.nom=nom;
            this.prenom=prenom;
            this.cin=cin;
            this.date_emprunt=date_emprunte;
            this.date_retour=date_retour;
        }
    Emprunteur(String nom,String prenom, String cin,LocalDate date_emprunte,LocalDate date_retour,int id_gestionnaire)
    {
        this.nom=nom;
        this.prenom=prenom;
        this.cin=cin;
        this.date_emprunt=date_emprunte;
        this.date_retour=date_retour;
        this.id_gestionnaire=id_gestionnaire;

    }


    public static boolean addEmprunteur(Emprunteur emprunteur) {
        String insertQuery = "INSERT INTO emprunteur (nom_emprunteur,prenom,cin, date_borrow,date_return,id_gestionnaire) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, emprunteur.nom);
            preparedStatement.setString(2, emprunteur.prenom);
            preparedStatement.setString(3, emprunteur.cin);
            preparedStatement.setDate(4, Date.valueOf(emprunteur.date_emprunt));
            preparedStatement.setDate(5, Date.valueOf(emprunteur.date_retour));
            //preparedStatement.setInt(6, book.id_emprunteur == 0 ? null : book.id_emprunteur);
            // Handle id_gestionnaire
            if (emprunteur.id_gestionnaire == 0) {
                preparedStatement.setNull(6, Types.INTEGER);
            } else {
                preparedStatement.setInt(6, emprunteur.id_gestionnaire);
            }

            int rowsAffected = preparedStatement.executeUpdate();
            Connect.closeConnection();
            return rowsAffected > 0; // Return true if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static int getIdOfLastEmprunteur() {
        String sqlQuery = "SELECT id FROM emprunteur ORDER BY id DESC LIMIT 1";
        int lastEmprunteurId =0; // Initialize with a default value

        try (Connection connection = Connect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            if (resultSet.next()) {
                lastEmprunteurId = resultSet.getInt("id");
            }

            Connect.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastEmprunteurId;
    }


}
