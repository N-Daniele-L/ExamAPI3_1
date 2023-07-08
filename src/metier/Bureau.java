package metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * classe metier du bureau des employes
 *
 * @author Daniele Nicolo
 * @version 1.0
 * @see Employe
 */
public class Bureau {

    private int nbr;
    /**
     * id unique du bureau
     */
    protected int id_bur;
    /**
     * sigle unique du bureau
     */
    protected String sigle;
    /**
     * numero de telephone du bureau
     */
    protected String tel;
    /**
     * liste des employes qui travaillent dans un bureau
     */
    protected List<Employe> employe = new ArrayList<>();

    /**
     * constructeur parametre
     *
     * @param bb objet de la classe BureauBuilder

     */
    private Bureau(BureauBuilder bb) {
        this.id_bur = bb.id_bur;
        this.sigle = bb.sigle;
        this.tel = bb.tel;
        this.nbr = bb.nbr;
    }

    /**
     * getter getid
     *
     * @return id unique du bureau
     */
    public int getId() {
        return id_bur;
    }



    /**
     * getter getsigle
     *
     * @return sigle unique du bureau
     */
    public String getSigle() {
        return sigle;
    }



    /**
     * getter gettel
     *
     * @return numero de telephone du bureau
     */
    public String getTel() {
        return tel;
    }



    /**
     * getter getemploye
     *
     * @return employe la liste de tout les employes du bureau
     */
    public List<Employe> getEmploye() {
        return employe;
    }



    /**
     * egalite de deux bureaux basee sur le sigle du bureau
     *
     * @param o autre bureau
     * @return egalite ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bureau bureau = (Bureau) o;
        return Objects.equals(sigle, bureau.sigle);
    }

    /**
     * calcul du hashcode du bureau base sur le sigle du bureau
     *
     * @return hashcode du bureau
     */
    @Override
    public int hashCode() {
        return Objects.hash(sigle);
    }

    /**
     * affichage des infos du bureau
     *
     * @return description complete du bureau
     */
    @Override
    public String toString() {
        return "Bureau{" +
                "id=" + id_bur +
                ", sigle='" + sigle + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    public String MyToString(){
        return "Bureau{" +
                "id=" + id_bur +
                ", sigle='" + sigle + '\'' +
                //", tel='" + tel + '\'' +
                ", nombre d'employé='" + nbr + '\'' +
                '}';
    }

    public static class BureauBuilder{
        public int nbr;
        /**
         * id unique du bureau
         */
        protected int id_bur;
        /**
         * sigle unique du bureau
         */
        protected String sigle;
        /**
         * numero de telephone du bureau
         */
        protected String tel;

        /**
         * setter setid
         *
         * @param id_bur changement de l'id unique du bureau
         */

        /**
         * liste des employes qui travaillent dans un bureau
         */
        protected List<Employe> employe = new ArrayList<>();

        public BureauBuilder setId(int id_bur) {
            this.id_bur = id_bur;
            return this;
        }

        /**
         * setter setsigle
         *
         * @param sigle changement du sigle unique du bureau
         */
        public BureauBuilder setSigle(String sigle) {
            this.sigle = sigle;
            return this;
        }

        /**
         * setter settel
         *
         * @param tel changement du numero de telephone du bureau
         */
        public BureauBuilder setTel(String tel) {
            this.tel = tel;
            return this;
        }

        /**
         * setter setemploye
         *
         * @param employe modifie la liste de tout les employes
         */
        public BureauBuilder setEmploye(List<Employe> employe) {
            this.employe = employe;
            return this;
        }
        public BureauBuilder setNbr(int nbr) {
            this.nbr = nbr;
            return this;
        }
        public Bureau build() throws Exception{
            if(!sigle.matches("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{3,6}")|| !tel.matches("0{1}[0-9]{2}[/][0-9]{2}[.][0-9]{2}[.][0-9]{2}")) throw new
                    Exception("informations de construction incomplètes");
            return new Bureau(this);
        }
    }
}
