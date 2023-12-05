package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    FlightPlaneService flightPlaneService;
    @Autowired
    BaggageService baggageService;
    @Autowired
    SeatService seatService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    TicketService ticketService;
    @Autowired
    TicketClassService ticketClassService;
    @Autowired
    CustomerTypeService customerTypeService;
    @Autowired
    AccountService accountService;
    @GetMapping(value = {"", "/"})
    public String booking(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();

        if (session.getAttribute("flight1Id") == null || session.getAttribute("ticketClass1Id") == null)
            return "redirect:/";
        boolean isReturn = (boolean) session.getAttribute("isReturn");

        FlightPlane flight1 = flightPlaneService.findById((int) session.getAttribute("flight1Id"));
        FlightPlane flight2 = isReturn ? flightPlaneService.findById((int) session.getAttribute("flight2Id")) : null;
        List<Baggage> baggages = baggageService.getAllBaggages();
        TicketClass ticketClass1 = ticketClassService.getById((int) session.getAttribute("ticketClass1Id"));
        TicketClass ticketClass2 = isReturn ? ticketClassService.getById((int) session.getAttribute("ticketClass2Id")) : null;

        BookingDetail form = new BookingDetail();
        List<PassengerDTO> passengerDTOS = new ArrayList<>();
        String[] passengerInfos = ((String) session.getAttribute("passengerNumInfo")).split("/");
        int adult = Integer.parseInt(passengerInfos[0]);
        int child = Integer.parseInt(passengerInfos[1]);
        int infant = Integer.parseInt(passengerInfos[2]);

        for (int i = 0; i < adult; i++) {
            PassengerDTO passengerDTO = new PassengerDTO();
            passengerDTO.setType("adult");
            passengerDTOS.add(passengerDTO);
        }

        for (int i = 0; i < child; i++) {
            PassengerDTO passengerDTO = new PassengerDTO();
            passengerDTO.setType("child");
            passengerDTOS.add(passengerDTO);
        }

        for (int i = 0; i < infant; i++) {
            PassengerDTO passengerDTO = new PassengerDTO();
            passengerDTO.setType("infant");
            passengerDTOS.add(passengerDTO);
        }
        form.setPassengerDTOS(passengerDTOS);

        model.addAttribute("form", form);
        model.addAttribute("flight1", new FlightDTO(flight1));
        model.addAttribute("ticketClass1", ticketClass1);
        if (isReturn) {
            model.addAttribute("flight2", new FlightDTO(flight2));
            model.addAttribute("ticketClass2", ticketClass2);
        }
        model.addAttribute("isReturn", isReturn);
        return "booking";
    }

    @PostMapping(value = {"", "/"})
    public String postBookingDetail(HttpServletRequest req, BookingDetail form) {
        HttpSession session = req.getSession();
        session.setAttribute("bookingDetail", form);
        return "redirect:/booking/review";
    }


    @GetMapping("/review")
    public String review(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("bookingDetail") == null)
            return "redirect:/booking";

        boolean isReturn = (boolean) session.getAttribute("isReturn");

        BookingDetail bookingDetail = (BookingDetail) session.getAttribute("bookingDetail");
        FlightPlane flight1 = flightPlaneService.findById((int) session.getAttribute("flight1Id"));
        FlightPlane flight2 = isReturn ? flightPlaneService.findById((int) session.getAttribute("flight2Id")) : null;
        List<Baggage> baggages = baggageService.getAllBaggages();
        TicketClass ticketClass1 = ticketClassService.getById((int) session.getAttribute("ticketClass1Id"));
        TicketClass ticketClass2 = isReturn ? ticketClassService.getById((int) session.getAttribute("ticketClass2Id")) : null;

        int total = 0;
        total += flight1.getFlight().getFlightPrice() * bookingDetail.getPassengerDTOS().size() * ticketClass1.getRate();
        if (isReturn)
            total += flight2.getFlight().getFlightPrice() * bookingDetail.getPassengerDTOS().size() * ticketClass2.getRate();

        if (!bookingDetail.isHasExtraBaggage1() && !bookingDetail.isHasExtraBaggage2()) {
            bookingDetail.setBaggageIds1(new ArrayList<>());
            bookingDetail.setBaggageIds2(new ArrayList<>());

            for (PassengerDTO p : bookingDetail.getPassengerDTOS()) {
                bookingDetail.getBaggageIds1().add(0);
                if (isReturn)
                    bookingDetail.getBaggageIds2().add(0);
            }
        }
        else {
            List<Baggage> baggageList1 = new ArrayList<>();
            int baggage1Sum = 0;
            int baggage2Sum = 0;
            for (int id : bookingDetail.getBaggageIds1()) {
                if (id != 0) {
                    Baggage baggage = baggageService.getById(id);
                    baggageList1.add(baggage);
                    baggage1Sum += baggage.getPrice();
                }
                else
                    baggageList1.add(null);

            }
            if (isReturn) {
                List<Baggage> baggageList2 = new ArrayList<>();
                for (int id : bookingDetail.getBaggageIds2()) {
                    if (id != 0) {
                        Baggage baggage = baggageService.getById(id);
                        baggageList2.add(baggage);
                        baggage2Sum += baggage.getPrice();
                    }
                    else
                        baggageList2.add(null);
                }
                model.addAttribute("baggageList2", baggageList2);
            }

            total += baggage1Sum + baggage2Sum;
            model.addAttribute("baggageList1", baggageList1);
            model.addAttribute("baggage1Sum", baggage1Sum);
            model.addAttribute("baggage2Sum", baggage2Sum);
        }

        if (!bookingDetail.isHasChosenSeat1() && !bookingDetail.isHasChosenSeat2()) {
            bookingDetail.setSeatDetailIds1(new ArrayList<>());
            bookingDetail.setSeatDetailIds2(new ArrayList<>());

            for (PassengerDTO p : bookingDetail.getPassengerDTOS()) {
                bookingDetail.getSeatDetailIds1().add(0);
                bookingDetail.getSeatDetailIds2().add(0);
            }
        }
        else {
            List<SeatDetail> chosenSeats1 = new ArrayList<>();
            for (int id : bookingDetail.getSeatDetailIds1()) {
                System.out.println(id);
                if (id != 0)
                    chosenSeats1.add(seatService.getSeatDetailById(id));
                else
                    chosenSeats1.add(null);

            }
            List<SeatDetail> chosenSeats2 = new ArrayList<>();
            if (isReturn) {
                for (int id : bookingDetail.getSeatDetailIds2()) {
                    if (id != 0)
                        chosenSeats2.add(seatService.getSeatDetailById(id));
                    else
                        chosenSeats2.add(null);

                }
                model.addAttribute("chosenSeats2", chosenSeats2);
            }
            model.addAttribute("chosenSeats1", chosenSeats1);
        }


        List<SeatDetail> seatDetails1 = seatService.getSeatDetailsByFlightPlane(flight1);
        List<SeatDetail> seatDetails2 = seatService.getSeatDetailsByFlightPlane(flight2);

        model.addAttribute("total", total);
        model.addAttribute("seats1", new SeatContainer(seatDetails1));
        model.addAttribute("seats2", new SeatContainer(seatDetails2));
        model.addAttribute("bookingDetail", bookingDetail);
        model.addAttribute("flight1", new FlightDTO(flight1));
        if (isReturn)
            model.addAttribute("flight2", new FlightDTO(flight2));
        model.addAttribute("ticketClass1", ticketClass1);
        if (isReturn)
            model.addAttribute("ticketClass2", ticketClass2);
        model.addAttribute("baggages", baggages);
        model.addAttribute("isReturn", (boolean) session.getAttribute("isReturn"));
        return "booking-review";
    }

    @PostMapping("/review/baggage")
    public String postBaggage(HttpServletRequest req, BookingDetail newBookingDetail) {
        HttpSession session = req.getSession();
        if (session.getAttribute("bookingDetail") == null)
            return "redirect:/booking";
        boolean isReturn = (boolean) session.getAttribute("isReturn");

        BookingDetail bookingDetail = (BookingDetail) session.getAttribute("bookingDetail");

        for (int id : newBookingDetail.getBaggageIds1()) {
            if (id != 0) bookingDetail.setHasExtraBaggage1(true);
        }
        if (isReturn) {
            for (int id : newBookingDetail.getBaggageIds2()) {
                if (id != 0) bookingDetail.setHasExtraBaggage2(true);
            }
        }
        else bookingDetail.setHasExtraBaggage2(false);
        bookingDetail.setBaggageIds1(newBookingDetail.getBaggageIds1());
        if (isReturn)
            bookingDetail.setBaggageIds2(newBookingDetail.getBaggageIds2());
        session.setAttribute("bookingDetail", bookingDetail);

        return "redirect:/booking/review";
    }

    @PostMapping("/review/seat")
    public String postSeat(HttpServletRequest req, BookingDetail newBookingDetail) {
        HttpSession session = req.getSession();
        if (session.getAttribute("bookingDetail") == null)
            return "redirect:/booking";

        boolean isReturn = (boolean) session.getAttribute("isReturn");


        BookingDetail bookingDetail = (BookingDetail) session.getAttribute("bookingDetail");

        for (int id : newBookingDetail.getSeatDetailIds1()) {
            System.out.println(id);
            if (id != 0) bookingDetail.setHasChosenSeat1(true);
        }
        if (isReturn) {
            for (int id : newBookingDetail.getSeatDetailIds2()) {
                System.out.println(id);
                if (id != 0) bookingDetail.setHasChosenSeat2(true);
            }
        }
        else bookingDetail.setHasChosenSeat2(false);


        bookingDetail.setSeatDetailIds1(newBookingDetail.getSeatDetailIds1());
        bookingDetail.setSeatDetailIds2(newBookingDetail.getSeatDetailIds2());
        session.setAttribute("bookingDetail", bookingDetail);


        return "redirect:/booking/review";
    }

    @GetMapping("/complete")
    public String complete(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("bookingDetail") == null)
            return "redirect:/booking";

        boolean isReturn = (boolean) session.getAttribute("isReturn");
        FlightPlane flightPlane1 = flightPlaneService.getFlightPlaneById((int)session.getAttribute("flight1Id"));
        FlightPlane flightPlane2 = isReturn ? flightPlaneService.getFlightPlaneById((int) session.getAttribute("flight2Id")) : null;

        TicketClass ticketClass1 =  ticketClassService.getById((int) session.getAttribute("ticketClass1Id"));
        TicketClass ticketClass2 = isReturn ?  ticketClassService.getById((int) session.getAttribute("ticketClass2Id")) : null;

        BookingDetail bookingDetail = (BookingDetail) session.getAttribute("bookingDetail");
        Reservation reservation = new Reservation();
        reservation.setTickets(new ArrayList<>());
        int total = 0;
        System.out.println("1");
        for (int i = 0; i < bookingDetail.getPassengerDTOS().size(); i++) {
            int ticketTotal = flightPlane1.getFlight().getFlightPrice() * ticketClass1.getRate();
            PassengerDTO passengerDTO = bookingDetail.getPassengerDTOS().get(i);
            Ticket ticket = new Ticket();
            ticket.setReservation(reservation);
            ticket.setTicketClass(ticketClass1);
            if (bookingDetail.getBaggageIds1().get(i) != 0) {
                Baggage baggage = baggageService.getById(bookingDetail.getBaggageIds1().get(i));
                ticket.setBaggage(baggage);
                ticketTotal += baggage.getPrice();
            }


            SeatDetail seatDetail = null;
            if (bookingDetail.getSeatDetailIds1().get(i) == 0) { // not choose seat
                // get next available seat
                List<SeatDetail> seatList = seatService.getSeatDetailsByFlightPlane(flightPlane1);
                for (SeatDetail s : seatList) {
                    if (bookingDetail.getSeatDetailIds1().contains(s.getId())) continue;
                    if (s.isTaken()) continue;
                    seatDetail = s;
                }
            }
            else
                seatDetail = seatService.getSeatDetailById(bookingDetail.getSeatDetailIds1().get(i));
            if (seatDetail == null) {
                // continue
            }
            ticket.setSeatDetail(seatDetail);
            ticket.setDayOrder(new Date());
            ticket.setTotalPrice(ticketTotal);
            if (seatDetail != null) {
                seatDetail.setTaken(true);
                seatDetail.setTicket(ticket);
            }
            Passenger passenger = new Passenger();
            passenger.setType(customerTypeService.getCustomerTypeByName(passengerDTO.getType()));
            passenger.setGender(passengerDTO.getGender());
            passenger.setCountry(passengerDTO.getNationality());
            passenger.setDateOfBirth(passengerDTO.getDateOfBirth());
            passenger.setFirstName(passengerDTO.getFirstName());
            passenger.setLastName(passengerDTO.getLastName());

            ticket.setPassenger(passenger);
            ticket.setDayPay(new Date());
            //ticket = ticketService.create(ticket);
            reservation.getTickets().add(ticket);

            total += ticketTotal;
        }
        System.out.println("2");

        if (isReturn) {
            for (int i = 0; i < bookingDetail.getPassengerDTOS().size(); i++) {
                int ticketTotal = flightPlane2.getFlight().getFlightPrice() * ticketClass2.getRate();
                PassengerDTO passengerDTO = bookingDetail.getPassengerDTOS().get(i);
                Ticket ticket = new Ticket();
                ticket.setReservation(reservation);
                if (bookingDetail.getBaggageIds2().get(i) != 0) {
                    Baggage baggage = baggageService.getById(bookingDetail.getBaggageIds2().get(i));
                    ticket.setBaggage(baggage);
                    ticketTotal += baggage.getPrice();
                }
                ticket.setTicketClass(ticketClass2);

                SeatDetail seatDetail = null;
                if (bookingDetail.getSeatDetailIds2().get(i) == 0) { // not choose seat
                    // get next available seat
                    List<SeatDetail> seatList = seatService.getSeatDetailsByFlightPlane(flightPlane2);
                    for (SeatDetail s : seatList) {
                        if (bookingDetail.getSeatDetailIds2().contains(s.getId())) continue;
                        if (s.isTaken()) continue;
                        seatDetail = s;
                    }
                }
                else
                    seatDetail = seatService.getSeatDetailById(bookingDetail.getSeatDetailIds2().get(i));
                if (seatDetail == null) {
                    // continue
                }
                ticket.setSeatDetail(seatDetail);
                ticket.setDayOrder(new Date());
                ticket.setTotalPrice(ticketTotal);
                if (seatDetail != null) {
                    seatDetail.setTaken(true);
                    seatDetail.setTicket(ticket);
                }
                Passenger passenger = new Passenger();
                passenger.setType(customerTypeService.getCustomerTypeByName(passengerDTO.getType()));
                passenger.setGender(passengerDTO.getGender());
                passenger.setCountry(passengerDTO.getNationality());
                passenger.setDateOfBirth(passengerDTO.getDateOfBirth());
                passenger.setFirstName(passengerDTO.getFirstName());
                passenger.setLastName(passengerDTO.getLastName());
                ticket.setDayPay(new Date());
                ticket.setPassenger(passenger);
                //ticket = ticketService.create(ticket);
                reservation.getTickets().add(ticket);
                total += ticketTotal;
            }
        }
        System.out.println("3");

        if (session.getAttribute("sessionAccount") != null) { // book with account
            Account account = (Account) session.getAttribute("sessionAccount");
            reservation.setAccount(account);
        }
        reservation.setTimeCreated(new Date());
        reservation.setTotal(total);
        System.out.println(total);
        reservation.setContactEmail(bookingDetail.getContactDetail().getEmail());
        reservation.setContactName(bookingDetail.getContactDetail().getLastName() + ' ' + bookingDetail.getContactDetail().getLastName());
        reservation.setContactPhone(bookingDetail.getContactDetail().getPhoneNumber());
        reservation = reservationService.create(reservation);
        model.addAttribute("reservation", new ReservationDTO(reservation));
        return "booking-complete";
    }

}
