 package CRM.Backend.Controller;

import CRM.Backend.DTO.ApiResponse;
import CRM.Backend.DTO.LeadRequest;
import CRM.Backend.Entity.LeadEntity;
import CRM.Backend.Service.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")

public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createLead(
            @Valid @RequestBody LeadRequest request) {

        if (leadService.isDuplicate(request.email(), request.phone())) {
            return ResponseEntity.ok(new ApiResponse<>(
                    "Your details are already saved. We will connect with you soon.", false, null));
        }

        leadService.createLead(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        "Contact request submitted successfully",
                        true,
                        "Your requirement has been saved successfully. We will connect with you soon."
                ));
    }
}