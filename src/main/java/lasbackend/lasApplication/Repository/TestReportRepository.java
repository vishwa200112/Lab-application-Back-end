package lasbackend.lasApplication.Repository;

import lasbackend.lasApplication.Entity.Admin;
import lasbackend.lasApplication.Entity.Appointment;
import lasbackend.lasApplication.Entity.TestReport;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestReportRepository extends MongoRepository<TestReport, ObjectId> {


    Optional<List<TestReport>> findByAppointmentId(String appointmentId);
}
