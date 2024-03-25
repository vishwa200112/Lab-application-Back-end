package lasbackend.lasApplication.Repository;

import lasbackend.lasApplication.Entity.Appointment;
import lasbackend.lasApplication.Entity.Patient;
import lasbackend.lasApplication.Entity.TestReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, ObjectId> {

    Optional<List<Appointment>> findByDate(String date);
    Optional <Appointment> appointmentId(String appointmentId);
    Optional<List<Appointment>> findByPatientId(String patientId);
    Appointment findByAppointmentId(String appointmentId);


}
