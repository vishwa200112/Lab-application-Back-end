package lasbackend.lasApplication.Service;

import lasbackend.lasApplication.Entity.Admin;
import lasbackend.lasApplication.Entity.Appointment;
import lasbackend.lasApplication.Entity.Patient;
import lasbackend.lasApplication.Repository.PatientRepository;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Patient> allPatient(){
        return patientRepository.findAll();
    }


    public Optional<List<Patient>> singlePatientById(String patientId){
        return patientRepository.findPatientByPatientId(patientId);
    }
    public Optional<Patient> singleEmailByPatientId(String patientId){
        return patientRepository.findEmailByPatientId(patientId);
    }
    public Patient updatePatient(String patientId, Patient updatedPatient) {
        Optional<Patient> optionalPatient = patientRepository.findByPatientId(patientId);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            // Update patient details
            patient.setPatientFName(updatedPatient.getPatientFName());
            patient.setPatientLName(updatedPatient.getPatientLName());
            patient.setGender(updatedPatient.getGender());
            patient.setBirthDate(updatedPatient.getBirthDate());
            patient.setNumber(updatedPatient.getNumber());
            patient.setEmail(updatedPatient.getEmail());
            patient.setPassword(updatedPatient.getPassword());
            // Save and return updated patient
            return patientRepository.save(patient);
        } else {
            return null; // Patient not found
        }
    }

    public  List<Patient> createPatient( String patientFName, String patientLName, String gender, String birthDate, String number, String email, String password){


            String patientId = RandomStringUtils.random(6, "0123456789P");
            List<Patient> patient = Collections.singletonList(patientRepository.insert(new Patient(patientId, patientFName, patientLName, gender, number, email, password, birthDate)));
            System.out.println("Patient successfully entered into the database");

            String subject =("Lad Appointment System Patient ID Email");
            String template = "Hello, ! " +patientFName +" " + patientLName + "\n\n"
                    + "This is your Patient ID :- " + patientId + "\n"
                    + "We hope you're having a great day!\n\n"
                    + "Best regards,\n"
                    + "LAS Application\n";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("abclaboratories27@gmail.com");
            message.setTo(email);
            message.setSubject(subject);
            message.setText( template );

            mailSender.send(message);
            System.out.println("Patient Reg Maill send successfully");


            return patient;


    }
    public boolean auth(String email, String password) {
        Patient patient = patientRepository.findByemail(email);
        return patient != null && patient.getPassword().equals(password);
    }

//    public Patient loginPatient(String email, String password){
//        return patientRepository.findByEmail(email).get();
//    }


}
