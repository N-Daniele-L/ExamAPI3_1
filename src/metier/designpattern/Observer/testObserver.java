package metier.designpattern.Observer;


public class testObserver {
    public static void main(String[] args) throws Exception {

        EmployeObs e2 = new EmployeObs.EmployeObsBuilder()
                .setId(2)
                .setNom("Croft")
                .setPrenom("Laura")
                .setMail("Lara.Croft@skynet.com")
                .setId_bur(6)
                .build();
        EmployeObs e1 = new EmployeObs.EmployeObsBuilder()
                .setId(1)
                .setNom("Nicolo")
                .setPrenom("Daniele")
                .setMail("daniele.nicolo@skynet.com")
                .setId_bur(8)
                .build();
        System.out.println("cc");
        e1.addObserver(e2);
        e2.addObserver(e1);

        e2 = new EmployeObs.EmployeObsBuilder()
                .setId(e2.getId())
                .setNom(e2.getNom())
                .setPrenom(e2.getPrenom())
                .setMail("laura.croft@skynet.com")
                .setId_bur(e2.getIdBur())
                .build();
        e1 = new EmployeObs.EmployeObsBuilder()
                .setId(e1.getId())
                .setNom(e1.getNom())
                .setPrenom(e1.getPrenom())
                .setMail("daniele.nicolo@gmail.com")
                .setId_bur(e1.getIdBur())
                .build();
    }

}
