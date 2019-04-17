package Domain;

import static org.junit.jupiter.api.Assertions.*;

class TaulerTest {

    private Tauler t;
    private FitxaProblema[] fp;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
         t = new Tauler();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test

    void getTaulell(){
        assertSame(t, t.getTaulell());


    }

    @org.junit.jupiter.api.Test
    void afegirPeçaAt() {
    }

    @org.junit.jupiter.api.Test
    void peçaMeva() {
    }

    @org.junit.jupiter.api.Test
    void peçaRival() {
    }

    @org.junit.jupiter.api.Test
    void fitxaAt() {
    }

    @org.junit.jupiter.api.Test
    void moureFitxa() {
    }
}