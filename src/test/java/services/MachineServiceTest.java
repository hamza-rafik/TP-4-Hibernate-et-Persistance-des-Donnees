package services;

import entities.Machine;
import entities.Salle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MachineServiceTest {

    private SalleService salleService;
    private MachineService machineService;

    @BeforeEach
    public void setUp() {
        salleService = new SalleService();
        machineService = new MachineService();
    }

    @Test
    public void testCreateMachineWithSalle() {
        Salle salle = new Salle("LAB-1", "Laboratoire IA");
        salleService.create(salle);

        Machine machine = new Machine("M-500", "Lenovo", salle);
        boolean result = machineService.create(machine);

        Assertions.assertTrue(result, "La création de la machine doit réussir");
        Assertions.assertTrue(machine.getId() > 0, "L'id de la machine doit être supérieur à 0");
    }

    @Test
    public void testFindByMarque() {
        Salle salle = new Salle("LAB-2", "Laboratoire Cloud");
        salleService.create(salle);

        Machine m1 = new Machine("MAC-01", "Apple", salle);
        Machine m2 = new Machine("MAC-02", "Apple", salle);
        Machine m3 = new Machine("PC-01", "Asus", salle);

        machineService.create(m1);
        machineService.create(m2);
        machineService.create(m3);

        List<Machine> appleMachines = machineService.findByMarque("Apple");
        Assertions.assertTrue(appleMachines.size() >= 2, "Doit trouver au moins 2 machines Apple");
        for (Machine m : appleMachines) {
            Assertions.assertEquals("Apple", m.getMarque());
        }
    }
}
