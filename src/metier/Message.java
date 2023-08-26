package metier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Message {
    protected int id_mess;
    protected String objet;
    protected String contenu;
    protected LocalDate dateEnvoi;
    protected Employe emetteur;
    protected int id_emp;
    protected List<Infos> l_infos = new ArrayList<>();

    /*public Message(int id_mess, String objet, String contenu, LocalDate dateEnvoi, Employe emetteur){
        this.id_mess = id_mess;
        this.objet = objet;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
        this.emetteur = emetteur;
    }
    public Message(int id_mess, String objet, String contenu, LocalDate dateEnvoi, int emetteur){
        this.id_mess = id_mess;
        this.objet = objet;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
        id_emp = emetteur;
    }*/

    public Message(MessageBuilder mb){
        this.id_mess = mb.id_mess;
        this.objet = mb.objet;
        this.contenu = mb.contenu;
        this.emetteur = mb.emetteur;
        this.dateEnvoi = mb.dateEnvoi;
        this.id_emp = mb.id_emp;
        this.l_infos = mb.l_infos;
    }

    public int getId_mess() {
        return id_mess;
    }

    public void setId_mess(int id_mess) {
        this.id_mess = id_mess;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDate dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public Employe getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(Employe emetteur) {
        this.emetteur = emetteur;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public List<Infos> getL_infos() {
        return l_infos;
    }

    public void setL_infos(List<Infos> l_infos) {
        this.l_infos = l_infos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id_mess == message.id_mess;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_mess);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id_mess=" + id_mess +
                ", objet='" + objet + '\'' +
                ", contenu='" + contenu + '\'' +
                ", dateEnvoi=" + dateEnvoi +
                ", id emetteur=" + id_emp +
                '}';
    }


    public static class MessageBuilder {
        protected int id_mess;
        protected String objet;
        protected String contenu;
        protected LocalDate dateEnvoi;
        protected Employe emetteur;
        protected int id_emp;
        protected List<Infos> l_infos = new ArrayList<>();

        public MessageBuilder setId_mess(int id_mess) {
            this.id_mess = id_mess;
            return this;
        }

        public MessageBuilder setObjet(String objet) {
            this.objet = objet;
            return this;
        }

        public MessageBuilder setContenu(String contenu) {
            this.contenu = contenu;
            return this;
        }

        public MessageBuilder setDateEnvoi(LocalDate dateEnvoi) {
            this.dateEnvoi = dateEnvoi;
            return this;
        }

        public MessageBuilder setEmetteur(Employe emetteur) {
            this.emetteur = emetteur;
            return this;
        }

        public MessageBuilder setId_emp(int id_emp) {
            this.id_emp = id_emp;
            return this;
        }

        public MessageBuilder setL_infos(List<Infos> l_infos) {
            this.l_infos = l_infos;
            return this;
        }

        public Message build() throws Exception{
            /*if(l_infos==null) throw new
                    Exception("informations de construction incomplètes");*/
            return new Message(this);
        }
    }
}
