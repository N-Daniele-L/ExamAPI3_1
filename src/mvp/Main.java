package mvp;

import mvp.model.*;
import mvp.presenter.*;
import mvp.view.*;
import utilitaires.Utilitaire;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class Main {
    private DAOBureau bm;
    private BureauPresenter bp;
    private BureauViewInterface bv;
    private DAOEmploye cm;
    private EmployePresenter cp;
    private EmployeViewInterface cv;
    private DAOMessage mm;
    private MessagePresenter mp;
    private MessageViewInterface mv;
    private DAOInfos im;
    private InfosPresenter ip;
    private InfosViewInterface iv;



    public void gestion() throws Exception {
        bm = new ModelDBBureau();
        bv = new BureauViewConsole();
        bp = new BureauPresenter(bm,bv);

        cm = new ModelDBEmploye();
        cv = new EmployeViewConsole();
        cp = new EmployePresenter(cm,cv);
        cp.setBureauPresenter(bp);

        im = new ModelDBInfos();
        iv = new InfosViewConsole();
        ip = new InfosPresenter(im,iv);

        mm = new ModelDBMessage();
        mv = new MessageViewConsole();
        mp = new MessagePresenter(mm,mv);

        mp.setEmployepresenter(cp);
        mp.InfosPresenter(im,ip);


        ip.setEmployepresenter(cp);
        ip.setMessagePresenter(mp);




        List<String> loptions = Arrays.asList("Bureau","Employe","Messages","Infos","Fin");
        try{
            do {
                int ch = Utilitaire.choixListe(loptions);
                switch (ch) {
                    case 1:
                        bp.start();
                        break;
                    case 2:
                        cp.start();
                        break;
                    case 3:
                        mp.start();
                        break;
                    case 4:
                        ip.start();
                        break;
                    case 5:
                        System.exit(0);
                }

            }while(true);
        }catch (InputMismatchException e){
            System.out.println("erreur " + e);
        }
    }
    public static void main(String[] args) throws Exception {
        Main ges = new Main();
        ges.gestion();
    }
}