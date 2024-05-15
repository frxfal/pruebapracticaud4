package org.iesvdm.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDAOTest {

    private BookingDAO bookingDAO;
    private Map<String, BookingRequest> bookings;

    @BeforeEach
    public void setup() {
        bookings = new HashMap<>();
        bookingDAO = new BookingDAO(bookings);
    }

    /**
     * Crea 2 peticiones de reserva (BookingRequest)
     * agrégalas a las reservas (bookings) con la que
     * construyes el objeto BookingDAO bajo testeo.
     * Comprueba que cuando invocas bookingDAO.getAllBookingRequest
     * obtienes las 2 peticiones.
     */
    @Test
    void  getAllBookingRequestsTest() {
        /* Creamos las peticiones y las metemos en el HashMap */
        BookingRequest bookingRequest1 = new BookingRequest("1", LocalDate.of(2024, 01, 01), LocalDate.of(2024, 02, 01), 4, true);
        BookingRequest bookingRequest2 = new BookingRequest("2", LocalDate.of(2024, 02, 02), LocalDate.of(2024, 03, 01), 3, false);

        bookings.put("1", bookingRequest1);
        bookings.put("2", bookingRequest2);

        /* Método de prueba */
        assertEquals(2, bookingDAO.getAllBookingRequests().size());
    }

    /**
     * Crea 2 peticiones de reserva (BookingRequest)
     * agrégalas a las reservas mediante bookingDAO.save.
     * Comprueba que cuando invocas bookingDAO.getAllUUIDs
     * obtienes las UUIDs de las 2 peticiones guardadas.
     */
    @Test
    void getAllUUIDsTest() {
        /* Creamos las peticiones y las guardamos mediante bookingDAO.save */
        BookingRequest bookingRequest1 = new BookingRequest("1", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1), 4, true);
        BookingRequest bookingRequest2 = new BookingRequest("2", LocalDate.of(2024, 2, 2), LocalDate.of(2024, 3, 1), 3, false);

        bookingDAO.save(bookingRequest1);
        bookingDAO.save(bookingRequest2);

        /* Método de prueba */
        assertEquals(2, bookingDAO.getAllUUIDs().size());
    }


    /**
     * Crea 2 peticiones de reserva (BookingRequest)
     * agrégalas a las reservas mediante bookingDAO.save.
     * Comprueba que cuando invocas bookingDAO.get con el UUID
     * obtienes las respectivas 2 peticiones guardadas.
     */
    @Test
    void getTest() {
        /* Creamos las peticiones y las guardamos mediante bookingDAO.save */
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1), 4, true);

        String uuid = bookingDAO.save(bookingRequest);

        /* Método de prueba */
        assertEquals(bookingRequest, bookingDAO.get(uuid));
    }

    /**
     * Crea 2 peticiones de reserva (BookingRequest)
     * agrégalas a las reservas mediante bookingDAO.save.
     * A continuación, borra la primera y comprueba
     * que se mantiene 1 reserva, la segunda guardada.
     */
    @Test
    void deleteTest() {
        /* Creamos las peticiones y las guardamos mediante bookingDAO.save */
        BookingRequest bookingRequest1 = new BookingRequest("1", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1), 4, true);
        BookingRequest bookingRequest2 = new BookingRequest("2", LocalDate.of(2024, 2, 2), LocalDate.of(2024, 3, 1), 3, false);

        String uuid1 = bookingDAO.save(bookingRequest1);
        String uuid2 = bookingDAO.save(bookingRequest2);

        bookingDAO.delete(uuid1);

        /* Método de prueba */
        assertEquals(1, bookingDAO.totalBookings());
        assertNull(bookingDAO.get(uuid1));
        assertNotNull(bookingDAO.get(uuid2));
    }

    /**
     * Guarda 2 veces la misma petición de reserva (BookingRequest)
     * y demuestra que en la colección de bookings están repetidas
     * pero con UUID diferente
     *
     */
    @Test
    void saveTwiceSameBookingRequestTest() {
        /* Creamos las peticiones y las guardamos mediante bookingDAO.save */
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1), 4, true);

        String uuid1 = bookingDAO.save(bookingRequest);
        String uuid2 = bookingDAO.save(bookingRequest);

        /* Método de prueba */
        assertNotEquals(uuid1, uuid2);
        assertEquals(2, bookingDAO.totalBookings());
    }

}


