package mvp.view;

import metier.Employe;
import mvp.presenter.EmployePresenter;

import java.util.List;

public interface EmployeViewInterface {
    public void setPresenter(EmployePresenter presenter);
    public void setListDatas(List<Employe> employes) throws Exception;
    public void affMsg(String msg);
    void affList(List infos);
    public Employe selectionner(List<Employe> lem);
}
