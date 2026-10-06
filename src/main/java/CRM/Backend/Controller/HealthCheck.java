package CRM.Backend.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@CrossOrigin("*")
public class HealthCheck {

    @GetMapping("")
    public boolean htc(HttpServletRequest request) {
        String forwardedIp = request.getHeader("X-Forwarded-For");
        if (forwardedIp != null && !forwardedIp.isEmpty()) {
            System.out.println("X-Forwarded-For: " + forwardedIp);
        }
        System.out.println("Remote IP: " + request.getRemoteAddr());
        return true;
    }
}
