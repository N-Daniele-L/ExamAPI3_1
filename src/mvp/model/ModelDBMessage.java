package mvp.model;

import metier.Message;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModelDBMessage implements DAOMessage, SpecialMessage {
    private static final Logger logger = LogManager.getLogger(ModelDBMessage.class);
    private Connection dbConnect;

    public ModelDBMessage() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            // System.err.println("erreur de connexion");
            logger.error("erreur de connexion");
            System.exit(1);
        }
        logger.info("connexion établie");
    }

    @Override
    public Message addMessage(Message mes) {
        Message m;
        String query1 = "insert into EXAMMESSAGE (objet,contenu,id_employe,dateenvoi) VALUES (?, ?,?, CURRENT_DATE)";
        String query2 = "select id_mess from EXAMMESSAGE where objet= ? and contenu = ? and dateenvoi = CURRENT_DATE";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, mes.getObjet());
            pstm1.setString(2, mes.getContenu());
            pstm1.setInt(3, mes.getEmetteur().getId());
            int n = pstm1.executeUpdate();
            if (n == 1) {
                pstm2.setString(1, mes.getObjet());
                pstm2.setString(2, mes.getContenu());
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int id_mes = rs.getInt(1);
                    m = new Message.MessageBuilder()
                            .setId_mess(id_mes)
                            .setObjet(mes.getObjet())
                            .setContenu(mes.getContenu())
                            .setDateEnvoi(mes.getDateEnvoi())
                            .setId_emp(mes.getEmetteur().getId())
                            .build();
                    //m = new Message(id_mes, mes.getObjet(), mes.getContenu(), LocalDate.now(), mes.getEmetteur().getId());
                    return m;
                } else {
                    logger.error("record introuvable");
                    //  System.err.println("record introuvable");
                    return null;
                }
            } else return null;

        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur sql :" + e);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeMessage(Message mes) {
        String query = "DELETE FROM EXAMMESSAGE WHERE id_mess = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, mes.getId_mess());
            int n = pstm.executeUpdate();
            if (n != 0) return true;
            else return false;

        } catch (SQLException e) {
            //  System.err.println("erreur sql :"+e);
            logger.error("erreur d'effacement : " + e);
            return false;
        }
    }

    @Override
    public Message updateMessage(Message mes) {
        String query = "update EXAMMESSAGE set objet = ?, contenu = ?, dateenvoi = CURRENT_DATE WHERE id_mess = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, mes.getObjet());
            pstm.setString(2, mes.getContenu());
            pstm.setInt(3, mes.getId_mess());
            int n = pstm.executeUpdate();
            if (n != 0) return readMessage(mes.getId_mess());
            else return null;

        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur d'update : " + e);
            return null;
        }
    }

    @Override
    public Message readMessage(int id_mess) {
        String query = "select * from EXAMMESSAGE where id_mess = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id_mess);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String objet = rs.getString(2);
                String cont = rs.getString(3);
                LocalDate date = rs.getDate(4).toLocalDate();
                int id_emp = rs.getInt(5);
                return new Message.MessageBuilder()
                        .setId_mess(id_mess)
                        .setObjet(objet)
                        .setContenu(cont)
                        .setDateEnvoi(date)
                        .setId_emp(id_emp)
                        .build();
                //return new Message(id_mess, objet, cont, date, id_emp);
            } else {
                return null;
            }
        } catch (SQLException e) {
            // System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : " + e);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> getMessage() {
        List<Message> lm = new ArrayList<>();
        String query = "select * from EXAMMESSAGE ORDER BY id_mess";
        try (Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                int id_mess = rs.getInt(1);
                String objet = rs.getString(2);
                String cont = rs.getString(3);
                LocalDate date = rs.getDate(4).toLocalDate();
                int id_emp = rs.getInt(5);

                Message mes = new Message.MessageBuilder()
                        .setId_mess(id_mess)
                        .setObjet(objet)
                        .setContenu(cont)
                        .setDateEnvoi(date)
                        .setId_emp(id_emp)
                        .build();
                //Message mes = new Message(id_mess, objet, cont, date, id_emp);
                lm.add(mes);
            }
            return lm;
        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : " + e);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> afficherMessageNonLu(String mail_emp) {
        List<Message> lm = new ArrayList<>();
        String query = "select * from EXAMVIEW_NOTREAD WHERE mail_emp = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, mail_emp);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id_emp = rs.getInt(1);
                String nom = rs.getString(2);
                String prenom = rs.getString(3);
                int id_mess = rs.getInt(5);
                String objet = rs.getString(6);
                String cont = rs.getString(7);
                int id_emp_emet = rs.getInt(8);
                LocalDate date = rs.getDate(9).toLocalDate();

                Message mes = new Message.MessageBuilder()
                        .setId_mess(id_mess)
                        .setObjet(objet)
                        .setContenu(cont)
                        .setDateEnvoi(date)
                        .setId_emp(id_emp_emet)
                        .build();
                //Message mes = new Message(id_mess, objet, cont, date, id_emp);
                lm.add(mes);
            }
            return lm;
        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : " + e);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
