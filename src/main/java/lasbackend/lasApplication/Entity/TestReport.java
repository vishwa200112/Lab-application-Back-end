package lasbackend.lasApplication.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TestReport")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestReport {

    @Id
    private ObjectId id;

    private String appointmentId;
    private String testCatagory;
    private String email;
    private String chloride;
    private String proteins;
    private String sugar;
    private String polymorphs;
    private String lymphocytes;
    private String anyother;

    public TestReport(String appointmentId, String testCatagory, String email, String chloride, String proteins, String sugar, String polymorphs, String lymphocytes, String anyother) {
        this.appointmentId = appointmentId;
        this.testCatagory = testCatagory;
        this.email = email;
        this.chloride = chloride;
        this.proteins = proteins;
        this.sugar = sugar;
        this.polymorphs = polymorphs;
        this.lymphocytes = lymphocytes;
        this.anyother = anyother;
    }
}