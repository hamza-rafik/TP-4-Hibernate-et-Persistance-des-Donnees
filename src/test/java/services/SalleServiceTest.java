package services;

import entities.Salle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SalleServiceTest {

    private SalleService service;

    @BeforeEach
    public void setUp() {
        service = new SalleService();
    }

    @Test
    public void testCreateSalle() {
        Salle salle = new Salle();
        salle.setCode("S2");
        salle.setLibelle("Salle Réseau");

        boolean result = service.create(salle);
        Assertions.assertTrue(result, "La création de la salle doit réussir");
        Assertions.assertTrue(salle.getId() > 0, "L'identifiant généré doit être supérieur à 0");
    }

    @Test
    public void testGetByIdAndGetAll() {
        Salle salle = new Salle();
        salle.setCode("S3");
        salle.setLibelle("Salle Multimédia");
        service.create(salle);

        Salle found = service.getById(salle.getId());
        Assertions.assertNotNull(found, "La salle récupérée ne doit pas être nulle");
        Assertions.assertEquals("S3", found.getCode());

        List<Salle> list = service.getAll();
        Assertions.assertFalse(list.isEmpty(), "La liste des salles ne doit pas être vide");
    }

    @Test
    public void testUpdateSalle() {
        Salle salle = new Salle();
        salle.setCode("S4");
        salle.setLibelle("Ancien Libelle");
        service.create(salle);

        salle.setLibelle("Nouveau Libelle");
        boolean updated = service.update(salle);
        Assertions.assertTrue(updated, "La mise à jour doit réussir");

        Salle reloaded = service.getById(salle.getId());
        Assertions.assertEquals("Nouveau Libelle", reloaded.getLibelle());
    }

    @Test
    public void testDeleteSalle() {
        Salle salle = new Salle();
        salle.setCode("S5");
        salle.setLibelle("Salle à Supprimer");
        service.create(salle);
        int id = salle.getId();

        boolean deleted = service.delete(salle);
        Assertions.assertTrue(deleted, "La suppression doit réussir");

        Salle shouldBeNull = service.getById(id);
        Assertions.assertNull(shouldBeNull, "La salle supprimée doit être nulle");
    }
}
