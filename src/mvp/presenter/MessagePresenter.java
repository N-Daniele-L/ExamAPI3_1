package mvp.presenter;

import metier.Employe;
import metier.Infos;
import metier.Message;
import mvp.model.*;
import mvp.view.MessageViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class MessagePresenter {
    private static DAOMessage model;
    private static DAOInfos modelinf;
    private MessageViewInterface view;
    private EmployePresenter employePresenter;
    private InfosPresenter infosPresenter;

    private static final Logger logger = LogManager.getLogger(MessagePresenter.class);

    public void setEmployepresenter(EmployePresenter employePresenter) {
        this.employePresenter = employePresenter;
    }
    public void InfosPresenter(DAOInfos model,InfosPresenter infosPresenter){
        modelinf = model;
        this.infosPresenter = infosPresenter;
    }
    public MessagePresenter(DAOMessage model,MessageViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }
    public void start() throws Exception {
        view.setListDatas(getAll());
    }

    public List<Message> getAll(){
        return model.getMessage();
    }

    public void addMessage(Message message) throws Exception {
        Message me;
        Employe employe = employePresenter.selectionner();
        if(employe == null){
            view.affMsg("Erreur employe null");
            return;
        }
        me = new Message.MessageBuilder()
                .setId_mess(0)
                .setObjet(message.getObjet())
                .setContenu(message.getContenu())
                .setDateEnvoi(message.getDateEnvoi())
                .setEmetteur(employe)
                .build();
        //me = new Message(0, message.getObjet(), message.getContenu(), message.getDateEnvoi(),0);
        //me.setEmetteur(employe);
        Message m = model.addMessage(me);
        if(m==null) view.affMsg("erreur de creation");
        else view.affMsg("création de : " + m);
    }

    public void removeMessage(Message message) {
        boolean ok = model.removeMessage(message);
        if(ok) view.affMsg("message effacé");
        else view.affMsg("message non effacé");
    }

    public Message selectionner() {
        logger.info("appel de sélection");
        Message me = view.selectionner(model.getMessage());
        return me;
    }

    public void update(Message message) {
        Message me =model.updateMessage(message);
        if(me==null) view.affMsg("mise à jour infrucueuse");
        else view.affMsg("mise à jour effectuée : "+me);
    }

    public Message search(int id_mess) {
        Message me = model.readMessage(id_mess);
        if(me==null) view.affMsg("recherche infructueuse");
        else view.affMsg(me.toString());
        return me;
    }

    public void envoyerMessage(String adresse_emet, String adresse_recept, Message message, Infos infos) throws Exception {
        Employe emetteur = employePresenter.searchAdresse(adresse_emet);
        Employe recepteur = employePresenter.searchAdresse(adresse_recept);

        Message me;

        me = new Message.MessageBuilder()
                .setId_mess(0)
                .setObjet(message.getObjet())
                .setContenu(message.getContenu())
                .setDateEnvoi(message.getDateEnvoi())
                .setEmetteur(emetteur)
                .build();

        Message m = model.addMessage(me);
        if(m==null) view.affMsg("erreur de creation");
        else view.affMsg("création de : " + m);



        infos.setRecepteur(recepteur);
        infos.setMess(m);
        if(infos.getRecepteur().getId_emp() == message.getId_emp()){
            view.affMsg(" erreur un message ne peut pas être envoyé a soi même");
        }
        else {
            Infos i = ((DAOInfos)modelinf).addInfos(infos);
            if (i != null) view.affMsg("création de : " + i);
            else view.affMsg("erreur de création");
        }
    }

    public List<Message> getNonlu(String mail){
        return ((SpecialMessage)model).afficherMessageNonLu(mail);
    }

}
