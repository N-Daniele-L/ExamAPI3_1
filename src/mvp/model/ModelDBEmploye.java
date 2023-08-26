package mvp.model;

import metier.Employe;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelDBEmploye implements DAOEmploye, SpecialEmploye{
    private static final Logger logger = LogManager.getLogger(ModelDBEmploye.class);
    private Connection dbConnect;

    public ModelDBEmploye() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            // System.err.println("erreur de connexion");
            logger.error("erreur de connexion");
            System.exit(1);
        }
        logger.info("connexion établie");
    }

    @Override
    public Employe addEmploye(Employe employe) {
        Employe emp;
        String query1 = "insert into EXAMEMPLOYE (mail_emp,nom,prenom,id_bureau) VALUES (?, ?, ?, ?)";
        String query2 = "select id_employe from EXAMEMPLOYE where mail_emp= ? and nom =? and prenom =?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, employe.getMail());
            pstm1.setString(2, employe.getNom());
            pstm1.setString(3, employe.getPrenom());
            pstm1.setInt(4, employe.getBureau().getId());
            int n = pstm1.executeUpdate();
            if (n == 1) {
                pstm2.setString(1, employe.getMail());
                pstm2.setString(2, employe.getNom());
                pstm2.setString(3, employe.getPrenom());
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int id_emp = rs.getInt(1);
                    emp = new Employe.EmployeBuilder()
                            .setId(id_emp)
                            .setMail(employe.getMail())
                            .setNom(employe.getNom())
                            .setPrenom(employe.getPrenom())
                            .setId_bur(employe.getBureau().getId())
                            .build();
                    return emp;
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
    public boolean removeEmploye(Employe employe) {
        String query = "DELETE FROM EXAMEMPLOYE WHERE id_employe = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            int n = pstm.executeUpdate();
            if(n!=0) return true;
            else return false;

        } catch (SQLException e) {
            //  System.err.println("erreur sql :"+e);
            logger.error("erreur d'effacement : "+e);
            return false;
        }
    }
    @Override
    public Employe updateEmploye(Employe employe) {
        String query = "update EXAMEMPLOYE set mail_emp = ?, nom = ?, prenom = ?, id_bureau = ? WHERE id_employe = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1,employe.getMail());
            pstm.setString(2,employe.getNom());
            pstm.setString(3,employe.getPrenom());
            pstm.setInt(4, employe.getBureau().getId());
            pstm.setInt(5,employe.getId());
            int n = pstm.executeUpdate();
            if(n!=0) return readEmploye(employe.getId());
            else return null;

        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur d'update : "+e);
            return null;
        }
    }

    @Override
    public Employe readEmploye(int idEmp) {
        String query = "select * from EXAMEMPLOYE where id_employe = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idEmp);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                int id_bur = rs.getInt(5);
                return new Employe.EmployeBuilder()
                        .setId(idEmp)
                        .setMail(mail)
                        .setNom(nom)
                        .setPrenom(prenom)
                        .setId_bur(id_bur)
                        .build();

            }
            else {
                return null;
            }
        } catch (SQLException e) {
            // System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : "+e);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Employe> getEmploye() {
        List<Employe> lc = new ArrayList<>();
        String query="select * from EXAMEMPLOYE ORDER BY id_employe";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int id_emp = rs.getInt(1);
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                int id_bur = rs.getInt(5);
                Employe em = new Employe.EmployeBuilder()
                        .setId(id_emp)
                        .setMail(mail)
                        .setNom(nom)
                        .setPrenom(prenom)
                        .setId_bur(id_bur)
                        .build();
                lc.add(em);
            }
            return lc;
        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : "+e);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employe readAdresseEmploye(String adresse) {
        String query = "select * from EXAMEMPLOYE where mail_emp = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1,adresse);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                int idEmp = rs.getInt(1);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                int id_bur = rs.getInt(5);
                return new Employe.EmployeBuilder()
                        .setId(idEmp)
                        .setMail(adresse)
                        .setNom(nom)
                        .setPrenom(prenom)
                        .setId_bur(id_bur)
                        .build();

            }
            else {
                return null;
            }
        } catch (SQLException e) {
            // System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : "+e);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
