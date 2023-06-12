package mvp.view;

import metier.Message;
import mvp.presenter.MessagePresenter;
import utilitaires.Utilitaire;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class MessageViewConsole implements MessageViewInterface{
    private MessagePresenter presenter;
    private List<Message> lm;
    private Scanner sc = new Scanner(System.in);

    public MessageViewConsole() {

    }

    @Override
    public void setPresenter(MessagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Message> messages) throws Exception {
        this.lm = messages;
        affListe(lm);
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
    public Message selectionner(List<Message> lm) {
        int choix = Utilitaire.choixListe(lm);
        return lm.get(choix - 1);
    }
    private void menu() throws Exception {
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
            LocalDate now = LocalDate.now();
            System.out.println("Entrez l'objet du message': ");
            String obj = sc.nextLine();
            System.out.println("Entrez le contenu du message");
            String cont = sc.nextLine();

            presenter.addMessage(new Message.MessageBuilder()
                    .setId_mess(0)
                    .setObjet(obj)
                    .setContenu(cont)
                    .setDateEnvoi(now)
                    .setId_emp(0)
                    .build());
            //presenter.addMessage(new Message(0,obj,cont,now,null));
            lm = presenter.getAll();
            affListe(lm);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void retirer() {
        try{
            int nl = choixElt(lm)-1;
            Message message = lm.get(nl);
            presenter.removeMessage(message);
            lm = presenter.getAll();//rafraichissement
            affListe(lm);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void rechercher() {
        try{
            System.out.println("id du message: ");
            int id_mess = sc.nextInt();
            presenter.search(id_mess);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void modifier() throws Exception {
        try{
            int nl = choixElt(lm) - 1;
            Message message= lm.get(nl);
            LocalDate now = LocalDate.now();
            String obj = modifyIfNotBlank("objet" , message.getObjet());
            String cont = modifyIfNotBlank("contenu", message.getContenu());
            presenter.update(new Message.MessageBuilder()
                    .setId_mess(message.getId_mess())
                    .setObjet(obj)
                    .setContenu(cont)
                    .setDateEnvoi(now)
                    .setId_emp(0)
                    .build());
            //presenter.addMessage(new Message(0,obj,cont,now,null));
            lm = presenter.getAll();//rafraichissement
            affListe(lm);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }

    private void special() {
    }
}
