package lasbackend.lasApplication.Repository;


import lasbackend.lasApplication.Entity.Admin;
import lasbackend.lasApplication.Entity.Appointment;
import lasbackend.lasApplication.Entity.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<Patient, ObjectId> {

    Optional <Patient> findByPatientId(String patientId);
    Optional<Patient> findByEmail(String email);
    Patient findByemail(String email);


    Optional<Patient> findEmailByPatientId(String patientId);

    Optional<List<Patient>> findPatientByPatientId(String patientId);


}

