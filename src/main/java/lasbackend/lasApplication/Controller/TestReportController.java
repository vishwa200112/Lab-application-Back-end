package lasbackend.lasApplication.Controller;


import lasbackend.lasApplication.Entity.Appointment;
import lasbackend.lasApplication.Entity.Patient;
import lasbackend.lasApplication.Entity.TestReport;
import lasbackend.lasApplication.Repository.AppointmentRepository;
import lasbackend.lasApplication.Service.TestReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/testReport")
public class TestReportController {

    @Autowired
    private TestReportService testReportService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/search/{appointmentId}")
    public ResponseEntity<Optional<List<TestReport>>> getAllAppointment(@PathVariable String appointmentId) {
        return new ResponseEntity<Optional<List<TestReport>>>(testReportService.getTestReport(appointmentId), HttpStatus.OK);
    }
    @PostMapping("/reportSubmit")
    public ResponseEntity<?> createPatient(@RequestBody Map<String, String> payload){
        String email =  payload.get("email");
        String appointmentId = payload.get("appointmentId");

        if (testReportService.authReport(appointmentId, email)) {
            return new ResponseEntity<List<TestReport>>(testReportService.createTestReport(

                    payload.get("appointmentId"),
                    payload.get("testCatagory"),
                    payload.get("email"),
                    payload.get("chloride"),
                    payload.get("proteins"),
                    payload.get("sugar"),
                    payload.get("polymorphs"),
                    payload.get("lymphocytes"),
                    payload.get("anyother")
            ), HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or Appointment ID not found");
        }

    }
}
