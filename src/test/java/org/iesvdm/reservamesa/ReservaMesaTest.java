package org.iesvdm.reservamesa;

//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ReservaMesaTest {

    @Test
    void buscarPrimeraMesaVaciaTest() {
        ReservaMesa reserva = new ReservaMesa();
        reserva.setTamanioMesa(4);

        int[] mesas = {2, 0, 3, 0};

        reserva.setMesas(mesas);
        int primeraMesaVacia = reserva.buscarPrimeraMesaVacia();

        /* Método de prueba */
        assertThat(primeraMesaVacia).isEqualTo(1);
    }

    @Test
    void buscarMesaParaCompartirTest() {
        ReservaMesa reserva = new ReservaMesa();
        reserva.setTamanioMesa(6);

        int[] mesas = {2, 4, 3, 0, 1};

        reserva.setMesas(mesas);
        int mesaParaCompartir = reserva.buscarMesaParaCompartir(3);

        /* Método de prueba */
        assertThat(mesaParaCompartir).isEqualTo(1);
    }
    @Test
    void buscarMesaCompartirMasCercaDeTest() {
        ReservaMesa reservaMesa = new ReservaMesa();
        reservaMesa.setTamanioMesa(8);

        int[] mesas = {2, 4, 1, 0, 3};

        reservaMesa.setMesas(mesas);

        /* Método de prueba */
        assertEquals(3, reservaMesa.buscarMesaCompartirMasCercaDe(2, 2));
    }

    @Test
    void buscarMesaCompartirMasAlejadaDeTest() {
        ReservaMesa reservaMesa = new ReservaMesa();
        reservaMesa.setTamanioMesa(6);

        int[] mesas = {2, 4, 1, 0};

        reservaMesa.setMesas(mesas);

        /* Método de prueba */
        assertEquals(1, reservaMesa.buscarMesaCompartirMasAlejadaDe(0, 2));
    }

    @Test
    void buscarCompartirNMesasConsecutivasTest() {
        ReservaMesa reservaMesa = new ReservaMesa();
        reservaMesa.setTamanioMesa(6);

        int[] mesas = {2, 4, 1, 0};

        reservaMesa.setMesas(mesas);
        
        /* Método de prueba */
        assertEquals(0, reservaMesa.buscarCompartirNMesasConsecutivas(2, 2));
    }
    @Test
    void comensalesTotalesTest() {
        ReservaMesa reservaMesa = new ReservaMesa();
        int[] mesas = {2, 4, 1, 0}; // Array de ocupación de mesas
        reservaMesa.setMesas(mesas);

        assertEquals(7, reservaMesa.comensalesTotales());
    }
}
