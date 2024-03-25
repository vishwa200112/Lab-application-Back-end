package lasbackend.lasApplication.Controller;

import lasbackend.lasApplication.Entity.Admin;
import lasbackend.lasApplication.Repository.AdminRepository;
import lasbackend.lasApplication.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;
//    public LoginController(AdminService adminService) {
//        this.adminService = adminService;
//    }

    @PostMapping("/Reg")
    private ResponseEntity<?> creatAdmin(@RequestBody Map<String, String> payload){

        if (adminRepository.findByEmail( payload.get("email")).isPresent()){
            return new ResponseEntity<>("This email already registered" , HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            return new ResponseEntity<Admin>(adminService.creatAdmin(

                    payload.get("adminName"),
                    payload.get("email"),
                    payload.get("password") ), HttpStatus.CREATED);
        }

    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> payload) {
        String email =  payload.get("email");
        String password = payload.get("password");

        if (adminService.auth(email, password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

}
