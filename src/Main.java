import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        int choix,id_gestionnaire,id_auteur,id_emprunteur ;
        boolean success;
        LocalDate dateEmprunt,dateRetour;
        String titre,isbn,statut,auteur;
        do {
            System.out.println("\t\t\t\t\n:::::::::::::::::::::::::: Menu Principale ::::::::::::::::::::::::::\n\n");
            System.out.println("\t\t\t 1- Introduire un livre ");
            System.out.println("\t\t\t 2- Introduire plusieurs livres ");
            System.out.println("\t\t\t 3- Modifier un livre ");
            System.out.println("\t\t\t 4- Affichage ");
            System.out.println("\t\t\t 5- Supprimer un livre ");
            System.out.println("\t\t\t 6- Chercher un livre ");
            System.out.println("\t\t\t 7- Emprunter un livre ");
            System.out.print("\t\t\t 8- Quitter L'application\n\n");
            System.out.print("\t\t\t Donnez votre choix: ");
            choix = input.nextInt();
            input.nextLine();
            switch (choix)
            {
                case 1:
                    System.out.println("donner titre de livre");
                    titre = input.nextLine();
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

                    System.out.println("Veuillez entre le isbn de livre que vous voulez Modifier");
                    isbn = input.nextLine();
                    System.out.println("Veuillez entre le nouveau titre");
                    titre = input.nextLine();
                    System.out.println("Veuillez entre le nouveau auteur");
                    id_auteur = input.nextInt();

                    success = Book.updateLivre(titre,id_auteur,isbn);

                    break;
                case 4:
                    Book.afficherLivres();
                    break;
                case 5:
                    String is;
                    System.out.println("donner le isbn de livre que vous voulez supprimer");
                    is = input.nextLine();
                    success = Book.deleteLivre(is);
                    if (success) {
                        System.out.println("Livre supprimer successfully.");
                    } else {
                        System.out.println("Failed to  the livre.");
                    }
                    break;
                case 6:
                    do{
                        System.out.println("1 - Chercher par titre");
                        System.out.println("2 - Chercher par auteur");
                        System.out.println("3 - Retour sur le menu principal");
                        choix = input.nextInt();
                        input.nextLine();
                        switch (choix)
                            {
                                case 1:
                                {
                                    System.out.println("Veuillez entre le titre");
                                    titre=input.nextLine();
                                    success = Book.chercherLivreParTitre(titre);
                                    if (!success) {
                                        System.out.println("Il ya aucun livre avec ce titre");
                                    }

                                }

                                    break;
                                case 2:
                                    System.out.println("Veuillez entre l'auteur");
                                    auteur=input.nextLine();
                                    success = Book.chercherLivreParAuteur(auteur);
                                    if (!success) {
                                        System.out.println("Il ya aucun livre crier par ce auteur");
                                    }

                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Choix incorrect");
                            }
                    }while(choix != 3);
                case 7:
                     dateEmprunt = LocalDate.now();
                     dateRetour = LocalDate.now().plusDays(7);
                     String emprunteur_nom,emprunteur_prenom,cin;
                    System.out.println("entre le nom de l'emprunteur");
                    emprunteur_nom=input.nextLine();
                    System.out.println("entre le prenom de l'emprunteur");
                    emprunteur_prenom=input.nextLine();
                    System.out.println("entre le cin de l'emprunteur ");
                    cin=input.nextLine();
                    Emprunteur emp = new Emprunteur(emprunteur_nom,emprunteur_prenom,cin,dateEmprunt,dateRetour);
                    success = Emprunteur.addEmprunteur(emp);
                    if (success) {
                        System.out.println("Emprunteur added successfully.");
                        System.out.println("Veuillez enter le isbn que vous voulez emprunté: ");
                        isbn=input.nextLine();
                        int emp_id;
                        emp_id=Emprunteur.getIdOfLastEmprunteur();
                        Book.updateLivreStatus("emprunté",emp_id,isbn);
                        System.out.println(emp_id);


                    } else {
                        System.out.println("Failed to add the emprunteur (cin already existe)");
                    }

                default:

            }
        } while (choix != 8);


        System.out.print("\t\t\t Fin de programme ... \n\n");



















    }


}
