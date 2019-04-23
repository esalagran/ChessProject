package Domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Domain.*;

class TaulerTest {

    private Tauler t;

    @BeforeEach
    void setUp() {
        t = new Tauler();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTaulell() {
        FitxaProblema[][] tauler = t.getTaulell();
        assertEquals(8, tauler.length, "la mida de la fila ha d'estar fixada");
        for (FitxaProblema[] fila: tauler) {
            assertEquals(8, fila.length, "la mida de la columna ha d'estar fixada");
            for (FitxaProblema col:fila) {
                assertNull(col);
            }
        }
    }

    @Test
    void afegirFitxaAt() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall,6,6, Color.blanc);
        t.AfegirPeçaAt(6,6, fp);
        FitxaProblema[][] tauler = t.getTaulell();
        for (int i = 0; i<8; ++i) {
            for (int j = 0; j<8; ++j) {
                if (i == 6 && j == 6)
                    assertSame(fp, tauler[i][j], "S'ha afegit correctament la fitxa");
                else
                    assertNull(tauler[i][j], "Les altres posicions continuen sent nul·les");
            }
        }
    }

    @Test
    void afegirFitxaAtIncorrecte() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall,6,6, Color.blanc);

        try {
            t.AfegirPeçaAt(8,8, fp);
            assertSame(2, 4, "Si arriba aquí vol dir que el programa està malfet");
        }
        catch (IndexOutOfBoundsException ex){
            assertSame(1,1, "si arriba aquí vol dir que s'ha llançat l'excepció correcte");
        }
    }

    @Test
    void fitxaMeva() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall, 5,5,Color.blanc);
        t.AfegirPeçaAt(5,5, fp);
        ParInt pi = new ParInt(5,5);
        assertFalse(t.PeçaMeva(pi, Color.negre), "hi ha una peça, però no és del meu color");
        assertTrue(t.PeçaMeva(pi, Color.blanc), "hi ha una peça i és del meu color");
        pi.SetSecond(4);
        assertFalse(t.PeçaMeva(pi, Color.blanc), "no hi ha cap peça");
    }

    @Test
    void fitxaRival() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall, 5,5,Color.blanc);
        t.AfegirPeçaAt(5,5, fp);
        ParInt pi = new ParInt(5,5);
        assertTrue(t.PeçaRival(pi, Color.negre), "hi ha una peça i és del color contrari");
        assertFalse(t.PeçaRival(pi, Color.blanc), "hi ha una peça i és del meu color");
        pi.SetSecond(4);
        assertFalse(t.PeçaRival(pi, Color.blanc), "no hi ha cap peça");
    }

    @Test
    void fitxaAt() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall, 5,5,Color.blanc);
        t.AfegirPeçaAt(5,5, fp);
        assertSame(fp, t.FitxaAt(5,5));
        assertNull(t.FitxaAt(5,6));
    }

    @Test
    void moureFitxa() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall, 5,5,Color.blanc);
        t.AfegirPeçaAt(5,5, fp);
        t.moureFitxa(new ParInt(5,5),new ParInt(6,7));
        assertSame(fp, t.FitxaAt(6,7));
        assertNull(t.FitxaAt(5,5));
    }
}