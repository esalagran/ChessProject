package Domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Domain.*;

class TaulerTest {

    private Tauler t;
    private ParInt coord;

    @BeforeEach
    void setUp() {
        t = new Tauler();
        coord = new ParInt(6,6);
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
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall,coord, Color.blanc);
        t.AfegirPeçaAt(coord, fp);
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
        ParInt aux = new ParInt(8,8);
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall,aux, Color.blanc);
        t.AfegirPeçaAt(aux, fp);
        assertNull(t.FitxaAt(aux));
    }

    @Test
    void afegirFitxaAtReiBlanc() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Rei,coord, Color.blanc);
        t.AfegirPeçaAt(coord, fp);
        FitxaProblema[][] tauler = t.getTaulell();
        for (int i = 0; i<8; ++i) {
            for (int j = 0; j<8; ++j) {
                if (i == 6 && j == 6)
                    assertSame(fp, tauler[i][j], "S'ha afegit correctament la fitxa");
                else
                    assertNull(tauler[i][j], "Les altres posicions continuen sent nul·les");
            }
        }
        assertSame(fp, t.getWhiteKing(), "Els rei s'ha d'haver assignat");
        assertNull(t.getBlackKing(), "L'altre rei ha de seguir a null");
    }

    @Test
    void afegirFitxaAtReiNegre() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Rei,coord, Color.negre);
        t.AfegirPeçaAt(coord, fp);
        FitxaProblema[][] tauler = t.getTaulell();
        for (int i = 0; i<8; ++i) {
            for (int j = 0; j<8; ++j) {
                if (i == 6 && j == 6)
                    assertSame(fp, tauler[i][j], "S'ha afegit correctament la fitxa");
                else
                    assertNull(tauler[i][j], "Les altres posicions continuen sent nul·les");
            }
        }
        assertSame(fp, t.getBlackKing(), "Els rei s'ha d'haver assignat");
        assertNull(t.getWhiteKing(), "L'altre rei ha de seguir a null");
    }

    @Test
    void fitxaMeva() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall, coord,Color.blanc);
        t.AfegirPeçaAt(coord, fp);
        ParInt pi = new ParInt(6,6);
        assertFalse(t.PeçaMeva(pi, Color.negre), "hi ha una peça, però no és del meu color");
        assertTrue(t.PeçaMeva(pi, Color.blanc), "hi ha una peça i és del meu color");
        pi.SetSecond(4);
        assertFalse(t.PeçaMeva(pi, Color.blanc), "no hi ha cap peça");
    }

    @Test
    void fitxaRival() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall, coord,Color.blanc);
        t.AfegirPeçaAt(coord, fp);
        ParInt pi = new ParInt(6,6);
        assertTrue(t.PeçaRival(pi, Color.negre), "hi ha una peça i és del color contrari");
        assertFalse(t.PeçaRival(pi, Color.blanc), "hi ha una peça i és del meu color");
        pi.SetSecond(4);
        assertFalse(t.PeçaRival(pi, Color.blanc), "no hi ha cap peça");
    }

    @Test
    void fitxaAt() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall, coord,Color.blanc);
        t.AfegirPeçaAt(coord, fp);
        assertSame(fp, t.FitxaAt(coord));
        coord.SetSecond(7);
        assertNull(t.FitxaAt(coord));
    }

    @Test
    void moureFitxa() {
        FitxaProblema fp = new FitxaProblema(TipusPeça.Cavall, coord,Color.blanc);
        t.AfegirPeçaAt(coord, fp);
        ParInt newPosition = new ParInt(4,7);
        t.moureFitxa(coord,newPosition);
        assertSame(fp, t.FitxaAt(newPosition));
        assertNull(t.FitxaAt(coord));
    }

    @Test
    void DesferJugada() {
        //REIS
        FitxaProblema peça = new FitxaProblema(TipusPeça.Rei, 4, 4, Color.blanc);
        FitxaProblema peça2 = new FitxaProblema(TipusPeça.Rei, 1, 2, Color.negre);

        //ALTRES FITXES
        FitxaProblema rival1 = new FitxaProblema(TipusPeça.Alfil, 3, 2, Color.negre);
        FitxaProblema meva2 = new FitxaProblema(TipusPeça.Peo, 2, 5, Color.blanc);
        FitxaProblema meva = new FitxaProblema(TipusPeça.Peo, 6, 3, Color.blanc);
        FitxaProblema meva3 = new FitxaProblema(TipusPeça.Cavall, 5, 6, Color.blanc);

        FitxaProblema[][] aux = new FitxaProblema[8][8];
        aux[4][4] = peça;
        aux[1][2] = peça2;
        aux[3][2] = rival1;
        aux[2][5] = meva2;
        aux[6][3] = meva;
        aux[5][6] = meva3;
        t = new Tauler(aux);
        assertEquals(peça, t.getWhiteKing());
        assertEquals(peça2, t.getBlackKing());
        assertNotNull(t.getTaulell());

        t.moureFitxa(rival1.GetCoordenades(), new ParInt(4, 3));
        t.desferJugada(rival1.GetCoordenades(), new ParInt(3, 2), null);
        assertEquals(3, rival1.GetCoordenades().GetFirst());
        assertEquals(2, rival1.GetCoordenades().GetSecond());
    }
}