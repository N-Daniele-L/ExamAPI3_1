package metier;

import java.time.LocalDate;

public class Infos {
    private Employe recepteur;
    private int id_emp;
    private Message mess;
    private int id_mess;
    private LocalDate dateLecture = null;

    public Infos(Employe recepteur, Message mess){
        this.recepteur = recepteur;
        this.mess = mess;
    }

    public Infos(int id_emp, int id_mess){
        this.id_emp = id_emp;
        this.id_mess = id_mess;
    }

    public Employe getRecepteur() {
        return recepteur;
    }

    public void setRecepteur(Employe recepteur) {
        this.recepteur = recepteur;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public Message getMess() {
        return mess;
    }

    public void setMess(Message mess) {
        this.mess = mess;
    }

    public int getId_mess() {
        return id_mess;
    }

    public void setId_mess(int id_mess) {
        this.id_mess = id_mess;
    }

    public LocalDate getDateLecture() {
        return dateLecture;
    }

    public void setDateLecture(LocalDate dateLecture) {
        this.dateLecture = dateLecture;
    }

    @Override
    public String toString() {
        return "Infos{" +

                "Id du recepteur=" + id_emp +
                ", Id du message=" + id_mess +
                ", Date de lecture=" + dateLecture +
                '}';
    }

}
