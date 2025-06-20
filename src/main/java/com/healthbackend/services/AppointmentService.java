package com.healthbackend.services;

import com.healthbackend.entities.Appointment;
import com.healthbackend.repositories.AppointmentRepository;
import com.healthbackend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmailSendDoc emailSendDoc;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment saveAppointment(Appointment appointment) {
        Appointment saved = appointmentRepository.save(appointment);

        clientRepository.findById(saved.getClientId()).ifPresent(client -> {
            String toEmail = client.getEmail();
            String subject = "Appointment Confirmation";
            String body = "Hi " + client.getFirstName() + ",\n\nYour appointment is confirmed for: "
                    + saved.getDate() + ".\n\nThank you,\nHealth Center";

            // Generate ICS content
            String start = formatICSDateTime(saved.getDate());
            String end = calculateEndTime(start); // Add 1 hour
            String ics = generateICS("Health Appointment", body, "Health Center", start, end);

            emailSendDoc.sendCalendarInvite(toEmail, subject, body, ics);
        });

        return saved;
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsByClientId(Long clientId) {
        return appointmentRepository.findByClientId(clientId);
    }

    private String formatICSDateTime(String dateStr) {
        LocalDateTime dateTime = LocalDateTime.parse(dateStr);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
    }

    private String calculateEndTime(String start) {
        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
        LocalDateTime endTime = startTime.plusHours(1);
        return endTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
    }

    private String generateICS(String summary, String description, String location, String startDateTime, String endDateTime) {
        return "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "PRODID:-//HealthBackend//Appointment Scheduler//EN\n" +
                "BEGIN:VEVENT\n" +
                "UID:" + java.util.UUID.randomUUID() + "\n" +
                "DTSTAMP:" + getCurrentTimestamp() + "\n" +
                "DTSTART:" + startDateTime + "\n" +
                "DTEND:" + endDateTime + "\n" +
                "SUMMARY:" + summary + "\n" +
                "DESCRIPTION:" + description + "\n" +
                "LOCATION:" + location + "\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR";
    }

    private String getCurrentTimestamp() {
        return java.time.ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
    }
}
