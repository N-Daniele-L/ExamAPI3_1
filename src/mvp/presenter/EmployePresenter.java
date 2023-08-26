package mvp.presenter;

import metier.Bureau;
import metier.Employe;
import mvp.model.DAOBureau;
import mvp.model.DAOEmploye;
import mvp.model.SpecialBureau;
import mvp.model.SpecialEmploye;
import mvp.view.EmployeViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class EmployePresenter {
    private static DAOEmploye model;
    private DAOBureau bureaumodel;
    private static EmployeViewInterface view;
    private BureauPresenter bureauPresenter;
    private static final Logger logger = LogManager.getLogger(EmployePresenter.class);

    public void setBureauPresenter(BureauPresenter bureauPresenter){
        this.bureauPresenter = bureauPresenter;
    }
    public EmployePresenter(DAOEmploye model, EmployeViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() throws Exception {
        view.setListDatas(getAll());
    }

    public List<Employe> getAll(){
        return model.getEmploye();
    }
    public void addEmploye(Employe employe) throws Exception {
        Employe emp;
        Employe e;
        Bureau bureau = bureauPresenter.selectionner();
        if (bureau == null){
            view.affMsg("Erreur bureau null");
            return;
        }
        emp = new Employe.EmployeBuilder()
                .setId(0)
                .setMail(employe.getMail())
                .setPrenom(employe.getPrenom())
                .setNom(employe.getNom())
                .setBureau(bureau)
                .build();
        e = model.addEmploye(emp);
        if(e!=null) view.affMsg("création de : "+ e);
        else view.affMsg("erreur de création : " + e);

    }


    public void removeEmploye(Employe employe) {
        boolean ok = model.removeEmploye(employe);
        if(ok) view.affMsg("employé effacé");
        else view.affMsg("employé non effacé");
    }

    public Employe selectionner() {
        logger.info("appel de sélection");
        Employe em = view.selectionner(model.getEmploye());
        return em;
    }

    public void update(Employe employe) throws Exception {
        Employe emp;
        Bureau bureau = bureauPresenter.selectionner();
        emp = new Employe.EmployeBuilder()
                .setId(employe.getId())
                .setNom(employe.getNom())
                .setPrenom(employe.getPrenom())
                .setMail(employe.getMail())
                .setId_bur(employe.getIdBur())
                .setBureau(bureau)
                .build();
        Employe em = model.updateEmploye(emp);
        if(em==null) view.affMsg("mise à jour infrucueuse");
        else view.affMsg("mise à jour effectuée : "+em);
    }

    public static Employe search(int idEmp) {
        Employe em = model.readEmploye(idEmp);
        if(em==null) view.affMsg("recherche infructueuse");
        else view.affMsg(em.toString());
        return em;
    }

    public static Employe searchAdresse(String adresse){
        Employe em = ((SpecialEmploye)model).readAdresseEmploye(adresse);
        if(em==null) view.affMsg("recherche infructueuse");
        else view.affMsg(em.toString());
        return em;
    }
}
