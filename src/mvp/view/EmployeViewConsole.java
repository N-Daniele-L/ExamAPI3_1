package mvp.view;

import metier.Employe;
import mvp.presenter.EmployePresenter;
import utilitaires.Utilitaire;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class EmployeViewConsole implements EmployeViewInterface{
    private EmployePresenter presenter;
    private List<Employe> lem;
    private Scanner sc = new Scanner(System.in);

    public EmployeViewConsole(){

    }
    @Override
    public void setPresenter(EmployePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Employe> employes) throws Exception {
        this.lem = employes;
        affListe(lem);
        menu();
    }
    @Override
    public void affMsg(String msg) {
        System.out.println("information : " + msg);
    }

    @Override
    public void affList(List infos) {
        affListe(infos);
    }

    @Override
    public Employe selectionner(List<Employe> lem) {
        int choix = Utilitaire.choixListe(lem);
        return lem.get(choix - 1);
    }



    public void menu() throws Exception {
        do {

            int ch = choixListe(Arrays.asList("ajout", "retrait", "rechercher", "modifier","fin"));
            switch (ch) {
                case 1:
                    ajouter();
                    break;
                case 2:
                    retirer();
                    break;
                case 3:
                    rechercher();
                    break;
                case 4:
                    modifier();
                    break;
                case 5:
                    return;
            }
        } while (true);
    }

    private void ajouter() throws Exception {
        try{
            System.out.println("Entrez le nom de l'employé : ");
            String nomEmp = sc.nextLine();
            System.out.println("Entrez le prenom de l'employé : ");
            String prenomEmp = sc.nextLine();
            System.out.println("Entrez le mail de l'employé : ");
            String mailEmp = sc.nextLine();
            System.out.println("Voici les id de bureau disponible : ");

            presenter.addEmploye(new Employe.EmployeBuilder()
                    .setId(0)
                    .setMail(mailEmp)
                    .setNom(nomEmp)
                    .setPrenom(prenomEmp)
                    .setId_bur(0)
                    .build());
            lem = presenter.getAll();
            affListe(lem);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void retirer() {
        try{
            int nl = choixElt(lem)-1;
            Employe employe = lem.get(nl);
            presenter.removeEmploye(employe);
            lem = presenter.getAll();//rafraichissement
            affListe(lem);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void rechercher() {
        try{
            System.out.println("id de l'employé: ");
            int idEm = sc.nextInt();
            presenter.search(idEm);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void modifier() throws Exception {
        try{
            int nl = choixElt(lem) - 1;
            Employe employe = lem.get(nl);
            String mailEmp = modifyIfNotBlank("mail" , employe.getMail());
            String nomEmp = modifyIfNotBlank("nom", employe.getNom());
            String prenomEmp = modifyIfNotBlank("prénom", employe.getPrenom());
            presenter.update(new Employe.EmployeBuilder()
                    .setId(employe.getId())
                    .setMail(mailEmp)
                    .setNom(nomEmp)
                    .setPrenom(prenomEmp)
                    .setId_bur(0)
                    .build());
            lem = presenter.getAll();//rafraichissement
            affListe(lem);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void special() {
    }
}
