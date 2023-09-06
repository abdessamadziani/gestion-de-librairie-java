import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        int choix,id_gestionnaire,id_auteur,id_emprunteur ;
        boolean success;
        String titre,isbn,statut;
        do {
            System.out.println("\t\t\t\t\n:::::::::::::::::::::::::: Menu Principale ::::::::::::::::::::::::::\n\n");
            System.out.println("\t\t\t 1- Introduire un livre ");
            System.out.println("\t\t\t 2- Introduire plusieurs livres ");
            System.out.println("\t\t\t 3- Emprunté un livre ");
            System.out.println("\t\t\t 4- Affichage ");
            System.out.println("\t\t\t 5- Supprimer un livre ");
            System.out.print("\t\t\t 6- Quitter L'application\n\n");
            System.out.print("\t\t\t Donnez votre choix: ");
            choix = input.nextInt();
            input.nextLine();
            switch (choix)
            {
                case 1:
                    System.out.println("donner titre de livre");
                    titre = input.nextLine();
                    System.out.println("donner statut de livre");
                    statut = input.nextLine();
                    System.out.println("donner isbn de livre");
                    isbn = input.nextLine();
                    System.out.println("donner id_gestionnaire");
                     id_gestionnaire= input.nextInt();
                    System.out.println("donner id_auteur");
                    id_auteur = input.nextInt();
                    Book bk = new Book(titre,"disponible", isbn, id_gestionnaire, id_auteur);
                     success = Book.addLivre(bk);
                    if (success) {
                        System.out.println("Livre added successfully.");
                    } else {
                        System.out.println("Failed to add the livre.");
                    }

                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    Book.afficherLivres();
                    break;
                case 5:
                    String is;
                    System.out.println("donner le isbn de livre que vous voulez supprimer");
                    is = input.nextLine();
                    success = Book.supprimerLivre(is);
                    if (success) {
                        System.out.println("Livre supprimer successfully.");
                    } else {
                        System.out.println("Failed to  the livre.");
                    }
                    break;
                default:

            }
        } while (choix != 6);


        System.out.print("\t\t\t Fin de programme ... \n\n");



















    }


}
