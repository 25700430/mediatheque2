import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigurationMediathequeTest {
    @Test
    void conserveLesValeursFourniesAuConstructeur() {
        ConfigurationMediatheque configuration = new ConfigurationMediatheque(7, 2);

        assertEquals(7, configuration.getDureeEmpruntJours());
        assertEquals(2, configuration.getNbMaxEmprunts());
    }

    @Test
    void chaqueInstanceAUneConfigurationIndependante() {
        ConfigurationMediatheque personnalisee = new ConfigurationMediatheque(7, 2);
        ConfigurationMediatheque standard = new ConfigurationMediatheque(21, 5);

        assertEquals(7, personnalisee.getDureeEmpruntJours());
        assertEquals(21, standard.getDureeEmpruntJours());
    }
}
