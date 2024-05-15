package org.iesvdm.booking;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentCaptor.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public class BookingServiceTest {

    @Mock
    private PaymentService paymentService;
    @Mock
    private  RoomService roomService;
    @Spy
    private  BookingDAO bookingDAO = new BookingDAO(new HashMap<>());
    @Mock
    private  MailSender mailSender;
    @InjectMocks
    private BookingService bookingService;
    @Captor
    private ArgumentCaptor<BookingRequest> bookingRequestCaptor;
    @Captor
    private ArgumentCaptor<String> roomIdCaptor;
    @Captor
    private ArgumentCaptor<String> bookingIdCaptor;
    @Captor
    private ArgumentCaptor<Double> priceCaptor;
    @Spy
    private BookingRequest bookingRequest1 = new BookingRequest("1"
            , LocalDate.of(2024,6, 10)
            , LocalDate.of(2024, 6, 16)
            ,4
            ,false
            );

    @Spy
    private BookingRequest bookingRequest2 = new BookingRequest("2"
            , LocalDate.of(2024,8, 3)
            , LocalDate.of(2024, 9, 9)
            ,3
            ,true
    );

    @BeforeEach
    public void setup() {

        MockitoAnnotations.initMocks(this);


    }

    /**
     * Crea un stub para roomService.getAvailableRoom
     * que devuelva una lista de 3 habitaciones disponibles
     * con un total de plazas libres de 10 para la invocación
     * de getAvailablePlaceCount.
     */
    @Test
    void getAvailablePlaceCountTest() {
        /* Creamos las habitaciones y las metemos en una lista*/
        Room room1 = Mockito.spy(new Room("1", 1));
        Room room2 = Mockito.spy(new Room("2", 3));
        Room room3 = Mockito.spy(new Room("3", 6));

        List<Room> list = Arrays.asList(room1, room2, room3);
        when(roomService.getAvailableRooms()).thenReturn(list);

        /* Método de prueba */
        assertThat(bookingService.getAvailablePlaceCount()).isEqualTo(10);
    }

    /**
     * Verifica que para bookingRequest1 se invocan
     * los métodos getDateFrom, getDateTo y getGuestCount
     * y que el precio en dólares (bookingService.calculatePrice) es el que corresponde para
     * el número de noches de la reserva de bookingRequest1.
     */
     @Test
    void calculatePriceTest() {
         /* Creamos las solicitudes */
         BookingRequest bookingRequest1 = new BookingRequest("1", LocalDate.of(2024, 01, 01), LocalDate.of(2024, 01, 04), 4, true);

         /* Métodos de prueba para los métodos */
         assertThat(bookingRequest1.getDateFrom()).isEqualTo(LocalDate.of(2024, 01, 01));
         assertThat(bookingRequest1.getDateTo()).isEqualTo(LocalDate.of(2024, 01, 04));
         assertThat(bookingRequest1.getGuestCount()).isEqualTo(4);
         assertThat(bookingService.calculatePrice(bookingRequest1)).isEqualTo(600);
     }


    /**
     * Crea un stub para roomService.findAvailableRoomId
     * que cuando se pase la bookingRequest2 devuelva el roomId
     * 101.
     * Verfica si la bookingRequest2 es de prepago (isPrepaid)
     * , se invoca paymentService.pay pasándosele los argumentos
     * bookingRequest2 y el precio esperado. Para ello, captura el bookingRequest
     * el precio en paymentService.pay con los captors necesarios.
     *
     */
    @Test
    void makeBookingTest1() {
        /* Creamos los captors y las solicitudes */

        ArgumentCaptor<BookingRequest> bookingRequestCaptor = ArgumentCaptor.forClass(BookingRequest.class);
        ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);

        BookingRequest bookingRequest2 = Mockito.spy(new BookingRequest("1", LocalDate.of(2024, 01, 01), LocalDate.of(2024, 01, 04), 1, true));
        Room room1 = Mockito.spy(new Room("101", 1));

        when(roomService.findAvailableRoomId(bookingRequest2)).thenReturn("101");

        if (bookingRequest2.isPrepaid()) {
            bookingService.makeBooking(bookingRequest2);
            verify(paymentService).pay(bookingRequestCaptor.capture(), priceCaptor.capture());
            assertThat(priceCaptor.getValue()).isEqualTo(bookingService.calculatePrice(bookingRequest2));
        }
    }

    /**
     * Igual que antes, crea un stub para roomService.findAvailableRoomId
     * que cuando se pase la bookingRequest2 devuelva el roomId
     * 101.
     * Ahora verifica que se llaman los métodos roomService.bookRoom
     * con el argumento (a capturar por captor) 101.
     * Verifica, también, que se invoca a mailSender.sendBookingConfirmation
     * con el bookingId esperado (es decir, el que devuelve makeBooking).
     * En este caso la verificación tiene que cumplir el orden de invocación
     * bookRoom 1º, sendBookingConfirmation 2º
     */
    @Test
    void makeBookingTest2() {
        /* Creamos los captors y las solicitudes */
        ArgumentCaptor<String> roomIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bookingIdCaptor = ArgumentCaptor.forClass(String.class);

        BookingRequest bookingRequest2 = new BookingRequest("1", LocalDate.of(2024, 01, 01), LocalDate.of(2024, 01, 04), 1, true);
        when(roomService.findAvailableRoomId(bookingRequest2)).thenReturn("101");

        /* Prueba para bookRoom y captura del roomId */
        doNothing().when(roomService).bookRoom(roomIdCaptor.capture());
        bookingService.makeBooking(bookingRequest2);

        /* Verifica el orden de invocación */
        InOrder inOrder = inOrder(roomService, mailSender);
        inOrder.verify(roomService).bookRoom(roomIdCaptor.getValue());
        inOrder.verify(mailSender).sendBookingConfirmation(bookingIdCaptor.capture());
    }
}