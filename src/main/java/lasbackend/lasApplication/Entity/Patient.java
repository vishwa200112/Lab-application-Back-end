package lasbackend.lasApplication.Entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "Patient")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Patient {

    @Id
    private ObjectId id;
    private String patientId;
    private String patientFName;
    private String patientLName;
    private String gender;
    private String number;
    private String email;
    private String password;
    private String birthDate;
    @DocumentReference
    private List<Appointment> appointmentIds;
    private List<TestReport>  testReportIds;



    public Patient(String patientId,
                   String patientFName,
                   String patientLName,
                   String gender,
                   String number,
                   String email,
                   String password,
                   String birthDate

    ) {

        this.patientId = patientId;
        this.patientFName = patientFName;
        this.patientLName = patientLName;
        this.gender = gender;
        this.number = number;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;


    }

//    private int age;



}
