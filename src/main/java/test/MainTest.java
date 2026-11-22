package test;

import entities.Machine;
import entities.Salle;
import services.MachineService;
import services.SalleService;

import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("       TP 4 : Hibernate et Persistance des Données - MainTest     ");
        System.out.println("=================================================================");

        SalleService salleService = new SalleService();
        MachineService machineService = new MachineService();

        // 1. Insertion de Salles
        System.out.println("\n[1] Création et insertion des Salles...");
        Salle salleA = new Salle();
        salleA.setCode("S1");
        salleA.setLibelle("Salle Informatique");
        salleService.create(salleA);

        Salle salleB = new Salle();
        salleB.setCode("S2");
        salleB.setLibelle("Salle Télécom & Réseaux");
        salleService.create(salleB);
        System.out.println("✓ Salles créées avec succès !");

        // 2. Insertion de Machines avec association
        System.out.println("\n[2] Création et insertion des Machines avec association ManyToOne...");
        Machine machine1 = new Machine();
        machine1.setRef("M-101");
        machine1.setMarque("Dell");
        machine1.setSalle(salleA);
        machineService.create(machine1);

        Machine machine2 = new Machine();
        machine2.setRef("M-102");
        machine2.setMarque("HP");
        machine2.setSalle(salleA);
        machineService.create(machine2);

        Machine machine3 = new Machine();
        machine3.setRef("M-201");
        machine3.setMarque("Dell");
        machine3.setSalle(salleB);
        machineService.create(machine3);
        System.out.println("✓ Données insérées avec succès !");

        // 3. Consultation des Salles
        System.out.println("\n[3] Liste de toutes les Salles (getAll) :");
        List<Salle> salles = salleService.getAll();
        for (Salle s : salles) {
            System.out.println("  -> [ID: " + s.getId() + "] Code: " + s.getCode() + ", Libellé: " + s.getLibelle());
        }

        // 4. Consultation des Machines
        System.out.println("\n[4] Liste de toutes les Machines (getAll) :");
        List<Machine> machines = machineService.getAll();
        for (Machine m : machines) {
            System.out.println("  -> [ID: " + m.getId() + "] Réf: " + m.getRef() + ", Marque: " + m.getMarque() + ", Salle: " + (m.getSalle() != null ? m.getSalle().getCode() : "N/A"));
        }

        // 5. Recherche par requête avancée HQL
        System.out.println("\n[5] Recherche HQL personnalisée (findByMarque 'Dell') :");
        List<Machine> dellMachines = machineService.findByMarque("Dell");
        for (Machine m : dellMachines) {
            System.out.println("  -> Machine Dell trouvée: Réf: " + m.getRef() + ", Salle: " + m.getSalle().getCode());
        }

        // 6. Mise à jour (Update)
        System.out.println("\n[6] Test de mise à jour (Update)...");
        salleA.setLibelle("Salle Informatique & IA");
        salleService.update(salleA);
        Salle updatedSalle = salleService.getById(salleA.getId());
        System.out.println("  -> Nouveau libellé pour Salle " + updatedSalle.getCode() + ": " + updatedSalle.getLibelle());

        System.out.println("\n=================================================================");
        System.out.println("       TP 4 : Exécution terminée avec succès !                   ");
        System.out.println("=================================================================");

        util.HibernateUtil.shutdown();
    }
}
