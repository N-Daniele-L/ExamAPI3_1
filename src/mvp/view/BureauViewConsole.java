package mvp.view;

import metier.Bureau;
import mvp.presenter.BureauPresenter;
import utilitaires.Utilitaire;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class BureauViewConsole implements BureauViewInterface{
    private BureauPresenter presenter;
    private List<Bureau> lb;
    private Scanner sc = new Scanner(System.in);

    public BureauViewConsole() {

    }
    @Override
    public void setPresenter(BureauPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Bureau> bur) throws Exception {
        this.lb = bur;
        affListe(bur);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("information:" + msg);
    }

    @Override
    public void affList(List infos) {
        affListe(infos);
    }

    @Override
    public Bureau selectionner(List<Bureau> lb) {
        int choix = Utilitaire.choixListe(lb);
        return lb.get(choix - 1);
    }

    private void menu() throws Exception {
        try{
            do {
                int ch = choixListe(Arrays.asList("ajout", "retrait", "rechercher", "modifier","special","fin"));
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
                        special();
                        break;
                    case 6:
                        return;
                }
            } while (true);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void ajouter() throws Exception {
        try{
            System.out.println("Entrez le sigle du bureau : ");
            String sigle = sc.nextLine();
            System.out.println("Entrez le numéro de téléphone du bureau");
            String tel = sc.nextLine();
            presenter.addBureau(new Bureau.BureauBuilder()
                    .setId(0)
                    .setSigle(sigle)
                    .setTel(tel)
                    .build());
            lb = presenter.getAll();
            affListe(lb);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void retirer() {
        try{
            int nl = choixElt(lb)-1;
            Bureau bureau = lb.get(nl);
            presenter.removeBureau(bureau);
            lb = presenter.getAll();//rafraichissement
            affListe(lb);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }
    private void rechercher() {
        try{
            System.out.println("id du bureau: ");
            int idBur = sc.nextInt();
            presenter.search(idBur);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }
    private void modifier() throws Exception {
        try{
            int nl = choixElt(lb) - 1;
            Bureau bureau = lb.get(nl);
            String sigle = modifyIfNotBlank("mail" , bureau.getSigle());
            String tel = modifyIfNotBlank("nom", bureau.getTel());
            presenter.update((new Bureau.BureauBuilder()
                    .setId(bureau.getId()))
                    .setSigle(sigle)
                    .setTel(tel)
                    .build());
            lb = presenter.getAll();//rafraichissement
            affListe(lb);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void special() {
        lb = presenter.count();
        int i =0;
        for(Object o :lb) {
            System.out.println((i+1)+"."+lb.get(i).MyToString());
            i++;
        }
    }
}
