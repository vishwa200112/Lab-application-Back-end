package lasbackend.lasApplication.Service;

import lasbackend.lasApplication.Entity.Admin;
import lasbackend.lasApplication.Repository.AdminRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private JavaMailSender mailSender;

    public Admin creatAdmin(String adminName, String email, String password){

        String adminId = RandomStringUtils.random(5, "123456789A");
        Admin admin = adminRepository.insert(new Admin
                (adminId, adminName, email, password));

        String subject =("Lad Appointment System Admin ID Email");
        String template = "Hello, ! " +adminName+"\n\n"
                + "This is your Admin ID :- " + adminId + "\n"
                + "We hope you're having a great day!\n\n"
                + "Best regards,\n"
                + "LAS Application\n";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("abclaboratories27@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText( template );

        mailSender.send(message);
        System.out.println("Admin Reg Maill send successfully");
        return admin;

    }

    public boolean auth(String email, String password) {
        Admin admin = adminRepository.findByemail(email);
        return admin != null && admin.getPassword().equals(password);
    }
}
