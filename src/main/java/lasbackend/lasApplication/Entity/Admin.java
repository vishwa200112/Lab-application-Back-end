package lasbackend.lasApplication.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    private ObjectId id;
    private String adminId;
    private String adminName;
    private String email;
    private String password;
    public Admin(String adminId, String adminName, String email, String password) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.email = email;
        this.password = password;
    }


}
