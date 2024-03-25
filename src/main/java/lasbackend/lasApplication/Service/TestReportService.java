package lasbackend.lasApplication.Service;

import lasbackend.lasApplication.Entity.Admin;
import lasbackend.lasApplication.Entity.Appointment;
import lasbackend.lasApplication.Entity.Patient;
import lasbackend.lasApplication.Entity.TestReport;
import lasbackend.lasApplication.Repository.AppointmentRepository;
import lasbackend.lasApplication.Repository.TestReportRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TestReportService {

    @Autowired
    private TestReportRepository testReportRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Optional<List<TestReport>> getTestReport(String appointmentId){
        return testReportRepository.findByAppointmentId(appointmentId);
    }
    public boolean authReport(String appointmentId, String email) {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
        return appointment != null && appointment.getEmail().equals(email);
    }

    public List<TestReport> createTestReport(
            String appointmentId,
            String testCatagory,
            String email,
            String chloride,
            String proteins,
            String sugar,
            String polymorphs,
            String lymphocytes,
            String anyother){

        List<TestReport> testReport = Collections.singletonList(testReportRepository.insert(new TestReport(
                appointmentId,
                testCatagory,
                email,
                chloride,
                proteins,
                sugar,
                polymorphs,
                lymphocytes,
                anyother)));
        System.out.println("Patient successfully entered into the database");

        String subject =("Lad Appointment System Test Report Email");
        String template = "Appointment ID :-" +appointmentId +  "\n\n"
                + "testCatagory :- " +  testCatagory +"\n"
                + "chloride :- " + chloride+ "\n"
                + "proteins :- " + proteins +"\n"
                + "sugar :- " + sugar +"\n"
                + "polymorphs :- " + polymorphs +"\n"
                + "lymphocytes :- " + lymphocytes +"\n"
                + "anyother :- " + anyother +"\n\n"
                + "We hope you're having a great day!\n\n"
                + "Best regards,\n"
                + "LAS Application\n";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("abclaboratories27@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText( template );

        mailSender.send(message);
        System.out.println("Test Report send successfully");


        return testReport;


    }

}
