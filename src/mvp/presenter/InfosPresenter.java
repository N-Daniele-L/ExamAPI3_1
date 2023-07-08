package mvp.presenter;


import metier.Infos;
import metier.Employe;
import metier.Message;
import mvp.model.DAOInfos;
import mvp.view.InfosViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class InfosPresenter {
    private static DAOInfos model;
    private InfosViewInterface view;
    private EmployePresenter employePresenter;
    private MessagePresenter messagePresenter;

    private static final Logger logger = LogManager.getLogger(MessagePresenter.class);

    public void setEmployepresenter(EmployePresenter employePresenter) {
        this.employePresenter = employePresenter;
    }
    public void setMessagePresenter(MessagePresenter messagePresenter) {
        this.messagePresenter = messagePresenter;
    }

    public InfosPresenter(DAOInfos model,InfosViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }
    public void start() {
        view.setListDatas(getAll());
    }

    public List<Infos> getAll(){
        return model.getInfos();
    }

    public void addInfos(Infos infos) {
        Employe employe = employePresenter.selectionner();
        Message message = messagePresenter.selectionner();
        System.out.println("\tRecepteur");
        employePresenter.search(employe.getId_emp());
        System.out.println("\tEmetteur");
        employePresenter.search(message.getId_emp());
        if(employe == null){
            view.affMsg("Erreur employe null");
            return;
        }
        infos.setRecepteur(employe);
        infos.setMess(message);
        if(infos.getRecepteur().getId_emp() == message.getId_emp()){
            view.affMsg(" erreur un message ne peut pas être envoyé a soi même");
        }
        else {
            Infos i = model.addInfos(infos);
            if (i != null) view.affMsg("création de : " + i);
            else view.affMsg("erreur de création");
        }
    }

    public void removeInfos(Infos infos) {
        Employe employe = employePresenter.search(infos.getId_emp());
        Message message= messagePresenter.search(infos.getId_mess());
        if(employe == null || message == null){
            view.affMsg("Erreur employe ou message null");
            return;
        }
        infos.setRecepteur(employe);
        infos.setMess(message);
        boolean ok = model.removeInfos(infos);
        if(ok) view.affMsg("infos effacé");
        else view.affMsg("infos non effacé");
    }

    public Infos selectionner() {
        logger.info("appel de sélection");
        Infos i = view.selectionner(model.getInfos());
        return i;
    }

    public void update(Infos infos) {
        Employe employe = employePresenter.search(infos.getId_emp());
        Message message= messagePresenter.search(infos.getId_mess());
        if(employe == null || message == null){
            view.affMsg("Erreur employe ou message null");
            return;
        }
        infos.setRecepteur(employe);
        infos.setMess(message);
        Infos i =model.updateInfos(infos);
        if(i==null) view.affMsg("mise à jour infrucueuse");
        else view.affMsg("mise à jour effectuée : "+ i);
    }

    public Infos search(int id_emp, int id_mess) {
        Infos i = model.readInfos(id_emp, id_mess);
        if(i==null) view.affMsg("recherche infructueuse");
        else view.affMsg(i.toString());
        return i;
    }
}
