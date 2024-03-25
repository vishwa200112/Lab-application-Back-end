package lasbackend.lasApplication.Service;


import lasbackend.lasApplication.Entity.Appointment;
import lasbackend.lasApplication.Repository.AppointmentRepository;
import lasbackend.lasApplication.Entity.Patient;
import lasbackend.lasApplication.Repository.PatientRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<List<Appointment>> todatAppointment(String date){
        return appointmentRepository.findByDate(date);
    }

    public Optional<List<Appointment>> getAppointment(String patientId){
        return appointmentRepository.findByPatientId(patientId);
    }

    public boolean authPayP(String patientId, String appointmentId) {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
        return appointment != null && appointment.getPatientId().equals(patientId);

    } public boolean authPayA(String appointmentId, String email) {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
        return appointment != null && appointment.getEmail().equals(email);
    }

    public  Appointment createAppointment( String testCatagory, String doctorName, String date, String time, String email, String patientId){


        String appointmentId = RandomStringUtils.random(8, "123456789AP");
        Appointment appointment = appointmentRepository.insert(new Appointment (appointmentId, testCatagory, doctorName, date, time, email, patientId));

        mongoTemplate.update(Patient.class)
                .matching(Criteria.where("patientId").is(patientId))
                .apply(new Update().push("appointmentId").value(appointment))
                .first();

        String subject =("Lad Appointment System Appointment ID Email");
        String template = "Hello, ! " + "\n\n"
                + "This is your Appointment ID :- " + appointmentId + "\n"
                + "This is your testCatagory :- " + testCatagory + "\n"
                + "We hope you're having a great day!\n\n"
                + "Best regards,\n"
                + "LAS Application\n";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("abclaboratories27@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText( template );

        mailSender.send(message);
        System.out.println("Appointment Maill send successfully" );


        return appointment;

    }





}
