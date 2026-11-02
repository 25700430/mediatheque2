package minishop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FormeTest {
    @Test
    void rectangleEtCarreCalculentLeurAireSansLienDeSubstitutionIncorrect() {
        Forme rectangle = new Rectangle(5, 4);
        Forme carre = new Carre(4);

        assertEquals(20.0, rectangle.aire());
        assertEquals(16.0, carre.aire());
    }
}
