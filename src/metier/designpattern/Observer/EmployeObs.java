package metier.designpattern.Observer;

import metier.Bureau;
import metier.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeObs extends Subject implements ObserverInterface{
    /**
     * id unique de l'EmployeObs
     */
    protected int id_emp;
    /**
     * adresse mail unique de l'EmployeObs
     */
    protected String mail;
    /**
     * nom de l'EmployeObs
     */
    protected String nom;
    /**
     * prenom de l'EmployeObs
     */
    protected String prenom;
    /**
     * bureau ou l'EmployeObs travaille
     */
    protected Bureau bureau;
    protected int id_bur;
    /**
     * message que l'EmployeObs a ecrit
     */
    protected List<Message> msg = new ArrayList<>();

    /**
     * constructeur parametre
     *
     * @param eb objet de la classe EmployeObsBuilder
     */

    public EmployeObs(EmployeObs.EmployeObsBuilder eb) {
        this.id_emp = eb.id_emp;
        this.mail = eb.mail;
        this.nom = eb.nom;
        this.prenom = eb.prenom;
        this.id_bur = eb.id_bur;
        this.bureau = eb.bureau;
    }

    /**
     * getter getid
     *
     * @return id unique de l'EmployeObs
     */
    public int getId() {
        return id_emp;
    }

    /**
     * getter getmail
     *
     * @return adresse mail unique de l'EmployeObs
     */
    public String getMail() {
        return mail;
    }

    /**
     * getter getnom
     *
     * @return nom de l'EmployeObs
     */
    public String getNom() {
        return nom;
    }

    public int getIdBur(){
        return id_bur;
    }

    /**
     * getter getprenom
     *
     * @return prenom de l'EmployeObs
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * getter getmsg
     *
     * @return la liste des messages ecrit de l'EmployeObs
     */

    public List<Message> getMsg() {
        return msg;
    }

    /**
     * getter getbureau
     *
     * @return le bureau de l'EmployeObs ou il travaille
     */
    public Bureau getBureau() {
        return bureau;
    }

    public int getId_emp() {
        return id_emp;
    }

    /**
     * egalite de deux EmployeObss basee sur l'adresse mail de l'EmployeObs
     *
     * @param o autre EmployeObs
     * @return egalite ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeObs EmployeObs = (EmployeObs) o;
        return Objects.equals(mail, EmployeObs.mail);
    }

    /**
     * calcul du hashcode du produit base sur l'adresse mail de l'EmployeObs
     *
     * @return hashcode de l'EmployeObs
     */
    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    /**
     * affichage des infos de l'EmployeObs
     *
     * @return description complete de l'EmployeObs
     */
    @Override
    public String toString() {
        return "EmployeObs{" +
                "id_emp=" + id_emp +
                ", mail='" + mail + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", bureau=" + id_bur +
                '}';
    }

    @Override
    public void update(String msg) {
        System.out.println(prenom+" "+nom+" a reçu le message : "+msg);
    }
    @Override
    public String getNotification() {
        return null;
    }

    public static class EmployeObsBuilder extends Subject {
        /**
         * id unique de l'EmployeObs
         */
        protected int id_emp;
        /**
         * adresse mail unique de l'EmployeObs
         */
        protected String mail;
        /**
         * nom de l'EmployeObs
         */
        protected String nom;
        /**
         * prenom de l'EmployeObs
         */
        protected String prenom;
        /**
         * bureau ou l'EmployeObs travaille
         */
        protected Bureau bureau;
        protected int id_bur;

        /**
         * message que l'EmployeObs a ecrit
         */
        protected List<Message> msg = new ArrayList<>();

        /**
         * setter setid
         *
         * @param id_emp changement de l'id unique de l'EmployeObs
         */
        public EmployeObs.EmployeObsBuilder setId(int id_emp) {
            this.id_emp = id_emp;
            return this;
        }
        /**
         * setter setmail
         *
         * @param mail changement du mail unique de l'EmployeObs
         */
        public EmployeObs.EmployeObsBuilder setMail(String mail) {
            this.mail = mail;
            notifyObservers();
            return this;
        }

        /**
         * setter setnom
         *
         * @param nom de l'EmployeObs
         */
        public EmployeObs.EmployeObsBuilder setNom(String nom) {
            this.nom = nom;
            return this;
        }

        /**
         * setter setprenom
         *
         * @param prenom de l'EmployeObs
         */
        public EmployeObs.EmployeObsBuilder setPrenom(String prenom) {
            this.prenom = prenom;
            return this;
        }

        /**
         * setter setmsg
         *
         * @param msg modifie la liste des messages ecrit de l'EmployeObs
         */
        public EmployeObs.EmployeObsBuilder setMsg(List<Message> msg) {
            this.msg = msg;
            return this;
        }
        /**
         * setter setbureau
         *
         * @param bureau modifie le bureau de l'EmployeObs ou il travaille
         */
        public EmployeObs.EmployeObsBuilder setBureau(Bureau bureau) {
            this.bureau = bureau;
            return this;
        }
        public EmployeObs.EmployeObsBuilder setId_bur(int id_bur) {
            this.id_bur = id_bur;
            return this;
        }
        public EmployeObs build() throws Exception{
            if(nom==null || prenom==null) throw new
                    Exception("informations de construction incomplètes");
            return new EmployeObs(this);
        }

        @Override
        public void update(String msg) {
            System.out.println();
        }
        @Override
        public String getNotification() {
            return "Le nouveau mail de " + prenom + " " + nom + " est le suivant : " + mail +"\n";
        }
    }
}
