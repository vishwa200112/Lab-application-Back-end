package lasbackend.lasApplication.Repository;


import lasbackend.lasApplication.Entity.Admin;
import lasbackend.lasApplication.Entity.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, ObjectId> {

    Optional<Admin> findByEmail(String email);


    Optional<Admin> findByAdminId(String email);

    Admin findByemail(String email);
}
