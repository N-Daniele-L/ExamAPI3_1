package mvp.view;

import metier.Infos;
import mvp.presenter.InfosPresenter;
import utilitaires.Utilitaire;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class InfosViewConsole implements InfosViewInterface{

    private InfosPresenter presenter;
    private List<Infos> linfos;
    private Scanner sc = new Scanner(System.in);
    @Override
    public void setPresenter(InfosPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Infos> infos) {
        this.linfos = infos;
        affListe(linfos);
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
    public Infos selectionner(List<Infos> linfos) {
        int choix = Utilitaire.choixListe(linfos);
        return linfos.get(choix - 1);
    }
    private void menu() {
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

    private void ajouter() {
        try{
            presenter.addInfos(new Infos(null,null));
            linfos = presenter.getAll();
            affListe(linfos);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void retirer() {
        try{
            int nl = choixElt(linfos)-1;
            Infos i = linfos.get(nl);
            presenter.removeInfos(i);
            linfos = presenter.getAll();//rafraichissement
            affListe(linfos);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void rechercher() {
        try{
            System.out.println("id de l'employé: ");
            int id_emp = sc.nextInt();
            System.out.println("id du message: ");
            int id_mess = sc.nextInt();
            presenter.search(id_emp,id_mess);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void modifier() {
        try{
            int nl = choixElt(linfos) - 1;
            Infos inf= linfos.get(nl);
            String date = modifyIfNotBlank("date de lecture (YYYY-MM-DD)",inf.getDateLecture()+"");
            LocalDate datelect = !date.equals("null")?LocalDate.parse(date):null;
            presenter.update(new Infos(inf.getId_emp(), inf.getId_mess(),datelect));
            linfos = presenter.getAll();//rafraichissement
            affListe(linfos);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void special() {
    }
}
