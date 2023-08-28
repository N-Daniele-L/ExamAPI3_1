package metier;

public class testComposite {
    public static void main(String[] args) throws Exception {
        Employe e1 = new Employe.EmployeBuilder()
                .setId(1)
                .setNom("Nicolo")
                .setPrenom("Daniele")
                .setMail("daniele.nicolo@skynet.com")
                .setId_bur(8)
                .build();
        Employe e2 = new Employe.EmployeBuilder()
                .setId(2)
                .setNom("Pluto")
                .setPrenom("Astrid")
                .setMail("astrid.pluto@skynet.com")
                .setId_bur(6)
                .build();
        Employe e3 = new Employe.EmployeBuilder()
                .setId(3)
                .setNom("Plutar")
                .setPrenom("Harold")
                .setMail("harold.plutar@skynet.com")
                .setId_bur(6)
                .build();
        Employe e4 = new Employe.EmployeBuilder()
                .setId(4)
                .setNom("Czktzk")
                .setPrenom("Wiktor")
                .setMail("wiktor.czktzk@skynet.com")
                .setId_bur(5)
                .build();
        Employe e5 = new Employe.EmployeBuilder()
                .setId(10)
                .setNom("Valent")
                .setPrenom("Polly")
                .setMail("polly.valent@hotmail.com")
                .setId_bur(10)
                .build();
        Categorie c0 = new Categorie(0, "programmeur");
        Categorie c1 = new Categorie(1, "programmeur java");
        Categorie c2 = new Categorie(2, "programmeur C#");
        Categorie c3 = new Categorie(3, "informaticien");
        Categorie c4 = new Categorie(4, "analyste");
        c0.getElements().add(e1);
        c0.getElements().add(e2);
        c0.getElements().add(e3);
        c0.getElements().add(c2);
        c0.getElements().add(c1);
        c0.getElements().add(e5);
        c1.getElements().add(e1);
        c1.getElements().add(e5);
        c2.getElements().add(e2);
        c2.getElements().add(e3);
        c2.getElements().add(e5);
        c3.getElements().add(e4);
        System.out.println(c0);
        System.out.println(c3);
        System.out.println(c4);
    }
}




