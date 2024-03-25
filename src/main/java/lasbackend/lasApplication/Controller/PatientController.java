package lasbackend.lasApplication.Controller;


import lasbackend.lasApplication.Entity.Appointment;
import lasbackend.lasApplication.Entity.Patient;
import lasbackend.lasApplication.Repository.PatientRepository;
import lasbackend.lasApplication.Service.PatientService;
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
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/")
    public ResponseEntity<List<Patient>> getAllPatient(){
        return new ResponseEntity<List<Patient>>(patientService.allPatient(), HttpStatus.OK);
    }
    @GetMapping("/id/{patientId}")
    public ResponseEntity<Optional<List<Patient>>> getPatientById(@PathVariable String patientId) {
        return new ResponseEntity<Optional<List<Patient>>>(patientService.singlePatientById(patientId), HttpStatus.OK);
    }
    @GetMapping("/email/{patientId}")
    public  ResponseEntity<Optional<Patient>> getSingleEmailPatient(@PathVariable String patientId){
        return new ResponseEntity<Optional<Patient>>(patientService.singleEmailByPatientId(patientId), HttpStatus.OK);
    }
    @PutMapping("/update/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String patientId, @RequestBody Patient updatedPatient) {
        Patient patient = patientService.updatePatient(patientId, updatedPatient);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if patient is not found
        }
    }

    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable String patientId, @RequestBody Map<String, String> credentials) {
        Optional<Patient> patientOptional = patientRepository.findByPatientId(patientId);
        String subject =("Lad Appointment System Patient Account is Deleted ");
        String template = "Hello, ! " +"\n\n"
                + "your LAS Account is Deleted :- " + patientId + "\n"
                + "We hope you're having a great day!\n\n"
                + "Best regards,\n"
                + "LAS Application\n";

        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            String email = credentials.get("email");
            String password = credentials.get("password");

            if (patient.getEmail().equals(email) && patient.getPassword().equals(password)) {
                patientRepository.delete(patient);

                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("abclaboratories27@gmail.com");
                message.setTo(email);
                message.setSubject(subject);
                message.setText( template );

                mailSender.send(message);
                System.out.println("Patient Delete Maill send successfully");

                return ResponseEntity.ok("Patient deleted");


            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.notFound().build(); // Patient not found
        }
    }


    @PostMapping("/Reg")
    public ResponseEntity<?> createPatient(@RequestBody Map<String, String> payload){

        if (patientRepository.findByEmail( payload.get("email")).isPresent()){

            return new ResponseEntity<>("This email already registered" , HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            return new ResponseEntity<List<Patient>>(patientService.createPatient(

                    payload.get("patientFName"),
                    payload.get("patientLName"),
                    payload.get("gender"),
                    payload.get("birthDate"),
                    payload.get("number"),
                    payload.get("email"),
                    payload.get("password")
                   ), HttpStatus.CREATED);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> payload) {
        String email =  payload.get("email");
        String password = payload.get("password");

        if (patientService.auth(email, password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @GetMapping
    public String klk(){
        return "I am fine";
    }

}
