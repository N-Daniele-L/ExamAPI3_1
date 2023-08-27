package mvp.view;

import metier.Employe;
import metier.Infos;
import metier.Message;
import mvp.presenter.EmployePresenter;
import mvp.presenter.InfosPresenter;
import mvp.presenter.MessagePresenter;
import utilitaires.Utilitaire;

import java.time.LocalDate;
import java.util.*;

import static utilitaires.Utilitaire.*;

public class MessageViewConsole implements MessageViewInterface {
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

            int ch = choixListe(Arrays.asList("ajout", "retrait", "rechercher", "modifier", "special", "fin"));
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
                case 6:
                    return;
            }
        } while (true);
    }

    private void ajouter() throws Exception {
        try {
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
        } catch (InputMismatchException e) {
            System.out.println("erreur " + e);
        }
    }

    private void retirer() {
        try {
            int nl = choixElt(lm) - 1;
            Message message = lm.get(nl);
            presenter.removeMessage(message);
            lm = presenter.getAll();//rafraichissement
            affListe(lm);
        } catch (InputMismatchException e) {
            System.out.println("erreur " + e);
        }
    }

    private void rechercher() {
        try {
            System.out.println("id du message: ");
            int id_mess = sc.nextInt();
            presenter.search(id_mess);
        } catch (InputMismatchException e) {
            System.out.println("erreur " + e);
        }
    }

    private void modifier() throws Exception {
        try {
            int nl = choixElt(lm) - 1;
            Message message = lm.get(nl);
            LocalDate now = LocalDate.now();
            String obj = modifyIfNotBlank("objet", message.getObjet());
            String cont = modifyIfNotBlank("contenu", message.getContenu());
            presenter.update(new Message.MessageBuilder()
                    .setId_mess(message.getId_mess())
                    .setObjet(obj)
                    .setContenu(cont)
                    .setDateEnvoi(now)
                    .setId_emp(0)
                    .build());
            //presenter.update(new Message(0,obj,cont,now,null));
            lm = presenter.getAll();//rafraichissement
            affListe(lm);
        } catch (InputMismatchException e) {
            System.out.println("erreur " + e);
        }
    }

    private void special() throws Exception {
        do {
            int ch = choixListe(Arrays.asList("Envoyer un message à un employé", "Lire vos mesages non lu", "fin"));
            switch (ch) {
                case 1:
                    envoyerMessage();
                    break;
                case 2:
                    lireMessageNonLu();
                    break;
                case 3:
                    return;
            }
        } while (true);
    }

    private void envoyerMessage() {
        try {
            String choix;
            ArrayList<String> tabrecpeteur = new ArrayList<>();
            LocalDate now = LocalDate.now();
            System.out.println("Entrez votre adresse mail");
            String emetteur = sc.nextLine();
            do {
                System.out.println("Entrez l'adresse mail du recepteur");
                String recepteur = sc.nextLine();
                tabrecpeteur.add(recepteur);
                System.out.println("Voulez vous ajouter un autre recepteur ? : y/n");
                choix = sc.nextLine();
            }while (choix.equals("y"));
            System.out.println("Entrez l'objet du message': ");
            String obj = sc.nextLine();
            System.out.println("Entrez le contenu du message");
            String cont = sc.nextLine();
            Message me = new Message.MessageBuilder()
                    .setId_mess(0)
                    .setObjet(obj)
                    .setContenu(cont)
                    .setDateEnvoi(now)
                    .setId_emp(0)
                    .build();
            Infos in = new Infos(null, null);
            for (String dest : tabrecpeteur) {
                presenter.envoyerMessage(emetteur, dest, me, in);
            }
            lm = presenter.getAll();
            affListe(lm);
        } catch (Exception e) {
            System.out.println("erreur " + e);
        }
    }

    private void lireMessageNonLu() {
        ArrayList<Message> lmnl;
        try {
            System.out.println("Entrez votre adresse mail");
            String recepteur = sc.nextLine();
            Employe recept = EmployePresenter.searchAdresse(recepteur);
            lmnl = (ArrayList<Message>) presenter.getNonlu(recepteur);
            if(lmnl == null){
                System.out.println("Aucun message non lu");
            }
            else {
                for (Message me : lmnl) {
                    Employe emetteur = EmployePresenter.search(me.getId_emp());
                    LocalDate now = LocalDate.now();
                    InfosPresenter.update(new Infos(recept.getId_emp(), me.getId_mess(), now));
                }
            }

        } catch (Exception e) {
            System.out.println("erreur " + e);
        }
    }
}




