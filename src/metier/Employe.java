package metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * classe metier des employes
 *
 * @author Daniele Nicolo
 * @version 1.0
 * @see Bureau
 * @see Infos
 */
public class Employe {
    /**
     * id unique de l'employe
     */
    protected int id_emp;
    /**
     * adresse mail unique de l'employe
     */
    protected String mail;
    /**
     * nom de l'employe
     */
    protected String nom;
    /**
     * prenom de l'employe
     */
    protected String prenom;
    /**
     * bureau ou l'employe travaille
     */
    protected Bureau bureau;
    protected int id_bur;
    /**
     * message que l'employe a ecrit
     */
    protected List<Message> msg = new ArrayList<>();

    /**
     * constructeur parametre
     *
     * @param eb objet de la classe EmployeBuilder
     */

    public Employe(EmployeBuilder eb) {
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
     * @return id unique de l'employe
     */
    public int getId() {
        return id_emp;
    }

    /**
     * getter getmail
     *
     * @return adresse mail unique de l'employe
     */
    public String getMail() {
        return mail;
    }

    /**
     * getter getnom
     *
     * @return nom de l'employe
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
     * @return prenom de l'employe
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * getter getmsg
     *
     * @return la liste des messages ecrit de l'employe
     */

    public List<Message> getMsg() {
        return msg;
    }

    /**
     * getter getbureau
     *
     * @return le bureau de l'employe ou il travaille
     */
    public Bureau getBureau() {
        return bureau;
    }

    public int getId_emp() {
        return id_emp;
    }

    /**
     * egalite de deux employes basee sur l'adresse mail de l'employe
     *
     * @param o autre employe
     * @return egalite ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employe employe = (Employe) o;
        return Objects.equals(mail, employe.mail);
    }

    /**
     * calcul du hashcode du produit base sur l'adresse mail de l'employe
     *
     * @return hashcode de l'employe
     */
    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    /**
     * affichage des infos de l'employe
     *
     * @return description complete de l'employe
     */
    @Override
    public String toString() {
        return "Employe{" +
                "id_emp=" + id_emp +
                ", mail='" + mail + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", bureau=" + id_bur +
                '}';
    }
    public static class EmployeBuilder{
        /**
         * id unique de l'employe
         */
        protected int id_emp;
        /**
         * adresse mail unique de l'employe
         */
        protected String mail;
        /**
         * nom de l'employe
         */
        protected String nom;
        /**
         * prenom de l'employe
         */
        protected String prenom;
        /**
         * bureau ou l'employe travaille
         */
        protected Bureau bureau;
        protected int id_bur;

        /**
         * message que l'employe a ecrit
         */
        protected List<Message> msg = new ArrayList<>();

        /**
         * setter setid
         *
         * @param id_emp changement de l'id unique de l'employe
         */
        public EmployeBuilder setId(int id_emp) {
            this.id_emp = id_emp;
            return this;
        }
        /**
         * setter setmail
         *
         * @param mail changement du mail unique de l'employe
         */
        public EmployeBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        /**
         * setter setnom
         *
         * @param nom de l'employe
         */
        public EmployeBuilder setNom(String nom) {
            this.nom = nom;
            return this;
        }

        /**
         * setter setprenom
         *
         * @param prenom de l'employe
         */
        public EmployeBuilder setPrenom(String prenom) {
            this.prenom = prenom;
            return this;
        }

        /**
         * setter setmsg
         *
         * @param msg modifie la liste des messages ecrit de l'employe
         */
        public EmployeBuilder setMsg(List<Message> msg) {
            this.msg = msg;
            return this;
        }
        /**
         * setter setbureau
         *
         * @param bureau modifie le bureau de l'employe ou il travaille
         */
        public EmployeBuilder setBureau(Bureau bureau) {
            this.bureau = bureau;
            return this;
        }
        public EmployeBuilder setId_bur(int id_bur) {
            this.id_bur = id_bur;
            return this;
        }
        public Employe build() throws Exception{
            /*if(nom==null || prenom==null) throw new
                    Exception("informations de construction incomplètes");*/
            return new Employe(this);
        }
    }
}
