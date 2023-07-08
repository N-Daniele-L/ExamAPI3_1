package mvp.presenter;

import metier.Bureau;
import mvp.model.DAOBureau;
import mvp.model.ModelDBBureau;
import mvp.model.SpecialBureau;
import mvp.view.BureauViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BureauPresenter {
    private static DAOBureau model;
    private BureauViewInterface view;

    private static final Logger logger = LogManager.getLogger(BureauPresenter.class);

    public BureauPresenter(DAOBureau model, BureauViewInterface view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start() throws Exception {
        view.setListDatas(getAll());
    }

    public static List<Bureau> getAll(){
        return model.getBureau();
    }
    public void addBureau(Bureau bureau) {
        Bureau bur = model.addBureau(bureau);
        if(bur!=null) view.affMsg("création de : "+bur);
        else view.affMsg("erreur de création");
    }


    public void removeBureau(Bureau bureau) {
        boolean ok = model.removeBureau(bureau);
        if(ok) view.affMsg("bureau effacé");
        else view.affMsg("bureau non effacé");
    }

    public Bureau selectionner() {
        logger.info("Appel de la sélection");
        List<Bureau> lbur = model.getBureau();
        if (lbur == null || lbur.isEmpty()) {
            view.affMsg("Aucun client");
            return null;
        }
        Bureau bur = view.selectionner(lbur);
        return bur;
    }

    public void update(Bureau bureau) {
        Bureau bur =model.updateBureau(bureau);
        if(bur==null) view.affMsg("mise à jour infrucueuse");
        else view.affMsg("mise à jour effectuée : "+bur);
    }

    public Bureau search(int idBur) {
        Bureau bur = model.readBureau(idBur);
        if(bur==null) view.affMsg("recherche infructueuse");
        else view.affMsg(bur.toString());
        return bur;
    }

    public List<Bureau> count(){
        return ((SpecialBureau)model).NbrEmployeInDep();
    }
}
