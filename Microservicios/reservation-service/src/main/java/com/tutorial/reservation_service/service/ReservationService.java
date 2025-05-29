package com.tutorial.reservation_service.service;

import com.tutorial.reservation_service.entity.Reservation;
import com.tutorial.reservation_service.model.Client;
import com.tutorial.reservation_service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id).orElse(null);
    }

    private void calcWeekendDiscount(Reservation reservation) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = reservation.getYear() + "-" + reservation.getMonth() + "-" + reservation.getDay();
        Date dt = sdf.parse(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
            reservation.setDiscountWeekend(10);
        } else {
            reservation.setDiscountWeekend(0);
        }
    }

    private List<String> obtainSpecialDays() {
        List<String> feriados = new ArrayList<>();
        feriados.add("2025-01-01");
        feriados.add("2025-04-18");
        feriados.add("2025-04-19");
        feriados.add("2025-05-01");
        feriados.add("2025-05-21");
        feriados.add("2025-06-20");
        feriados.add("2025-06-29");
        feriados.add("2025-07-16");
        feriados.add("2025-08-15");
        feriados.add("2025-09-18");
        feriados.add("2025-09-19");
        feriados.add("2025-10-12");
        feriados.add("2025-10-31");
        feriados.add("2025-11-01");
        feriados.add("2025-11-16");
        feriados.add("2025-12-08");
        feriados.add("2025-12-14");
        feriados.add("2025-12-25");
        return feriados;
    }

    private void calcSpecialDayDiscount(Reservation reservation) {
        String date = reservation.getYear() + "-" + reservation.getMonth() + "-" + reservation.getDay();
        List<String> specialDays = obtainSpecialDays();
        if (specialDays.contains(date)){
            reservation.setDiscountSpecialDay(15);
        } else {
            reservation.setDiscountSpecialDay(0);
        }
    }

    private void calculateDiscounts(Client client, Reservation reservation) throws ParseException, InterruptedException {
        //Aplicación descuento por fidelidad del cliente
        switch (client.getFidelityLevel()){
            case 0: reservation.setDiscountFidelity(0);
                break;
            case 1: reservation.setDiscountFidelity(10);
                break;
            case 2: reservation.setDiscountFidelity(20);
                break;
            case 3: reservation.setDiscountFidelity(30);
                break;
        }

        //Aplicación de descuento por cantidad de personas
        int quantity = reservation.getPeopleQuantity();
        if (3 <= quantity && quantity <= 5) {
            reservation.setDiscountQuantity(10);
        } else if (6 <= quantity && quantity <= 10) {
            reservation.setDiscountQuantity(20);
        } else if (11 <= quantity && quantity <= 15) {
            reservation.setDiscountQuantity(30);
        } else {
            reservation.setDiscountQuantity(0);
        }

        //Aplicación de descuento por día feriado
        calcSpecialDayDiscount(reservation);

        //Aplicación de descuento por fin de semana
        calcWeekendDiscount(reservation);

        //Aplicación de descuento por cumpleaños
        String reservationMMdd = reservation.getMonth() + "-" + reservation.getDay();
        if (reservationMMdd.equals(client.getBirthday())) {
            reservation.setDiscountBirthday(50);
        } else {
            reservation.setDiscountBirthday(0);
        }
    }

    private void applyDiscount(Reservation reservation) {
        Long precio = reservation.getBasePrice();
        int totalDiscount = reservation.getDiscountQuantity()
                + reservation.getDiscountFidelity()
                + reservation.getDiscountSpecialDay()
                + reservation.getDiscountWeekend()
                + reservation.getDiscountBirthday();
        if (totalDiscount >= 100) {
            reservation.setTotalPrice(0L);
        } else {
            Long descuento = precio * totalDiscount / 100;
            Long total = precio - descuento;
            Long iva = (long) (total * 0.19);
            reservation.setIva(iva);
            total += iva;
            reservation.setTotalPrice(total);
        }
    }

    public Reservation save(Reservation reservation) {
        Client client = restTemplate.getForObject("http://client-service/client/" + );

        Long price = 0L;
        int time = 0, duration = reservation.getTrackTime();

        switch(duration){
            case 10:
                price = 15000L;
                time = 30;
                break;
            case 20:
                price = 20000L;
                time = 35;
                break;
            case 30:
                price = 25000L;
                time = 40;
                break;
        }
        reservation.setTotalTime(time);
        reservation.setEnd(reservation.getStart().plusMinutes(time));
        reservation.setBasePrice(price);


        applyDiscount(reservation);

        return reservationRepository.save(reservation);
    }

    public Reservation update(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public boolean delete(int id) {
        reservationRepository.deleteById(id);
        return true;
    }
}
