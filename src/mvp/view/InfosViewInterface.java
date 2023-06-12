package mvp.view;

import metier.Infos;
import mvp.presenter.InfosPresenter;

import java.util.List;

public interface InfosViewInterface {
    public void setPresenter(InfosPresenter presenter);
    public void setListDatas(List<Infos> linfos);
    public void affMsg(String msg);
    void affList(List infos);
    public Infos selectionner(List<Infos> linfos);
}
