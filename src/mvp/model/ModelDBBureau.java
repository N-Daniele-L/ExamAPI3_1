package mvp.model;

import metier.Bureau;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelDBBureau implements DAOBureau{
    private static final Logger logger = LogManager.getLogger(ModelDBBureau.class);
    private Connection dbConnect;
    public ModelDBBureau() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            // System.err.println("erreur de connexion");
            logger.error("erreur de connexion");
            System.exit(1);
        }
        logger.info("connexion établie");
    }
    @Override
    public Bureau addBureau(Bureau bureau) {
        Bureau buro;
        String query1 = "insert into EXAMBUREAU (sigle,tel) VALUES (?, ?)";
        String query2 = "select id_bureau from EXAMBUREAU where sigle= ? and tel =?  ";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, bureau.getSigle());
            pstm1.setString(2, bureau.getTel());
            int n = pstm1.executeUpdate();
            if (n == 1) {
                pstm2.setString(1, bureau.getSigle());
                pstm2.setString(2, bureau.getTel());
                ResultSet rs = pstm2.executeQuery();

                if (rs.next()) {
                    int id_bur = rs.getInt(1);
                    buro = new Bureau.BureauBuilder()
                            .setId(id_bur)
                            .setSigle(bureau.getSigle())
                            .setTel(bureau.getTel())
                            .build();
                    return buro;
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
    public boolean removeBureau(Bureau bureau) {
        String query = "DELETE FROM EXAMBUREAU WHERE id_bureau = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, bureau.getId());
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
    public Bureau updateBureau(Bureau bureau) {
        String query = "update EXAMBUREAU set sigle = ?, tel = ? WHERE id_bureau = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1,bureau.getSigle());
            pstm.setString(2,bureau.getTel());
            pstm.setInt(3,bureau.getId());
            int n = pstm.executeUpdate();
            if(n!=0) return readBureau(bureau.getId());
            else return null;

        } catch (SQLException e) {
            // System.err.println("erreur sql :" + e);
            logger.error("erreur d'update : "+e);
            return null;
        }
    }

    @Override
    public Bureau readBureau(int idBur) {
        String query = "select * from EXAMBUREAU where id_bureau = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idBur);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String sigle = rs.getString(2);
                String tel = rs.getString(3);
                return new Bureau.BureauBuilder()
                        .setId(idBur)
                        .setSigle(sigle)
                        .setTel(tel)
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
    public List<Bureau> getBureau() {
        List<Bureau> lb = new ArrayList<>();
        String query="select * from EXAMBUREAU  ORDER BY id_bureau";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int id_bur = rs.getInt(1);
                String sigle = rs.getString(2);
                String tel = rs.getString(3);
                Bureau bur = new Bureau.BureauBuilder()
                        .setId(id_bur)
                        .setSigle(sigle)
                        .setTel(tel)
                        .build();
                lb.add(bur);
            }
            return lb;
        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur SQL : "+e);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
