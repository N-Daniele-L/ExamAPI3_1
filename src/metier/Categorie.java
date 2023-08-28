package metier;

import java.util.HashSet;
import java.util.Set;

public class Categorie extends Element {
    private Set<Element> elements = new HashSet<>();

    public Categorie(int id, String nom) {
        super(id, nom);
    }

    @Override
    public String toString() {
        int nbrEmp = 0;
        StringBuilder aff = new StringBuilder(getId() + " | " + getNom()  + " : \n");

        for (Element e : elements) {
            if (e instanceof Employe) {
                nbrEmp += 1;
            }

            aff.append(e).append("\n");
        }
        return aff + "\t\tNombre d'employé pour cette catégorie: " + nbrEmp + "\n";
    }

    public Set<Element> getElements() {
        return elements;
    }

}
