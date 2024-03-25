package lasbackend.lasApplication.Controller;


import lasbackend.lasApplication.Entity.Appointment;
import lasbackend.lasApplication.Entity.Patient;
import lasbackend.lasApplication.Repository.AppointmentRepository;
import lasbackend.lasApplication.Repository.PatientRepository;
import lasbackend.lasApplication.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/appointment")

public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@RequestBody Map<String, String> payload) {
        String date = payload.get("date");
        String amount = payload.get("amount");
        String name = payload.get("name");
        String email =  payload.get("email");
        String patientId = payload.get("patientId");
        String appointmentId = payload.get("appointmentId");

        String subject =("Lad Appointment System, Payment Receipt");
        String template = "Hello, ! " + name +"\n\n"
                + "Payment Date :- " + date + "\n"
                + "your Appointment ID :- " + appointmentId + "\n"
                + "amount :- " + amount + "\n"
                + "         Invoice Paid         \n\n"
                + "We hope you're having a great day!\n\n"
                + "Best regards,\n"
                + "LAS Application\n";

        if (appointmentService.authPayP(patientId, appointmentId)) {
            if (appointmentService.authPayA(appointmentId, email)) {

                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("abclaboratories27@gmail.com");
                message.setTo(email);
                message.setSubject(subject);
                message.setText( template );

                mailSender.send(message);
                System.out.println("Payment  Invoice send successfully");
                return ResponseEntity.ok("Payment successful");

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
            }

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }

    }

    @GetMapping("/today/{date}")
    public ResponseEntity<Optional<List<Appointment>>> getAllAppointment(@PathVariable String date) {
        return new ResponseEntity<Optional<List<Appointment>>>(appointmentService.todatAppointment(date), HttpStatus.OK);
    }
    @GetMapping("/pAppointment/{patientId}")
    public ResponseEntity<Optional<List<Appointment>>> getSingleAppointment(@PathVariable String patientId) {
        return new ResponseEntity<Optional<List<Appointment>>>(appointmentService.getAppointment(patientId), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable String appointmentId, @RequestBody Map<String, String> payload) {
        Optional<Appointment> appointmentOptional = appointmentRepository.appointmentId(appointmentId);
        String subject =("Lad Appointment System, Appointment is cancelled");
        String template = "Hello, ! " +"\n\n"
                + "your Appointment is cancelled :- " + appointmentId + "\n"
                + "We hope you're having a great day!\n\n"
                + "Best regards,\n"
                + "LAS Application\n";

        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            String email = payload.get("email");
            String patientId = payload.get("patientId");

            if (appointment.getEmail().equals(email) && appointment.getPatientId().equals(patientId)) {
                appointmentRepository.delete(appointment);

                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("abclaboratories27@gmail.com");
                message.setTo(email);
                message.setSubject(subject);
                message.setText( template );

                mailSender.send(message);
                System.out.println("Appointment Cancelled Emaill send successfully");

                return ResponseEntity.ok("Appointment cancelled ");


            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.notFound().build(); // Patient not found
        }
    }

    @PostMapping("/make")
    public ResponseEntity<?> createAppointment(@RequestBody Map<String, String> payload) {

        if (patientRepository.findByEmail(payload.get("email")).isPresent()) {
            if (patientRepository.findByPatientId(payload.get("patientId")).isPresent()) {
                return new ResponseEntity<Appointment>(appointmentService.createAppointment(

                        payload.get("testCatagory"),
                        payload.get("doctorName"),
                        payload.get("date"),
                        payload.get("time"),
                        payload.get("email"),
                        payload.get("patientId")


                ), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Invalid patient ID", HttpStatus.NOT_ACCEPTABLE);
            }

        } else {
            return new ResponseEntity<>("Invalid email address", HttpStatus.NOT_ACCEPTABLE);
        }


    }
}
