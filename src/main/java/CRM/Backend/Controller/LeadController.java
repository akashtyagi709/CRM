 package CRM.Backend.Controller;

import CRM.Backend.DTO.ApiResponse;
import CRM.Backend.Entity.LeadEntity;
import CRM.Backend.Service.LeadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin("*")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createLead(
            @RequestBody LeadEntity lead) {

        if (leadService.isDuplicate(lead.getEmail(), lead.getPhone())) {
            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "Your details are already saved. We will connect with you soon.",
                            false,
                            null
                    )
            );
        }

        LeadEntity savedLead = leadService.createLead(lead);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        "Contact request submitted successfully",
                        true,
                        "Your requirement has been saved successfully. We will connect with you soon."
                ));
    }
}