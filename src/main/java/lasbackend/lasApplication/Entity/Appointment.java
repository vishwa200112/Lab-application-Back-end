package lasbackend.lasApplication.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "Appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    private ObjectId id;
    private String appointmentId;
    private String testCatagory;
    private String doctorName;
    private String date;
    private String time;
    private String email;
    private String patientId;


//    private String testReportIds;

    public Appointment(String appointmentId, String testCatagory, String doctorName, String date, String time, String email, String patientId) {
        this.appointmentId = appointmentId;
        this.testCatagory = testCatagory;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
        this.email = email;
        this.patientId = patientId;


    }
//    @DateTimeFormat(iso = ISO.DATE_TIME)
//    private Date createdDate;

}
