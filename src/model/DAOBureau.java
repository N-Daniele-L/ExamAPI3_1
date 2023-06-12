package model;

import metier.Bureau;

import java.util.List;

public interface DAOBureau {
    Bureau addBureau(Bureau bureau);

    boolean removeBureau(Bureau bureau);

    Bureau updateBureau(Bureau bureau);

    Bureau readBureau(int idBur);

    List<Bureau> getBureau();
}
