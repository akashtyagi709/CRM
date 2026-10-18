package CRM.Backend.Controller;

import CRM.Backend.DTO.ApiResponse;
import CRM.Backend.DTO.ChangeLeadStatusRequest;
import CRM.Backend.Entity.LeadEntity;
import CRM.Backend.Service.LeadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leads")

public class LeadDataController {

    private final LeadService leadService;
    public LeadDataController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<LeadEntity>>> getAllLeads() {
        List<LeadEntity> leads = leadService.getAllLeads();
        ApiResponse<List<LeadEntity>> response = new ApiResponse<>();
        response.setMsg("Leads found Successfully");
        response.setSuccess(true);
        response.setData(leads);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{leadId}/status")
    public ResponseEntity<ApiResponse<LeadEntity>> changeLeadStatus(
            @PathVariable Long leadId,
            @RequestBody ChangeLeadStatusRequest request) {

        LeadEntity updatedLead = leadService.changeLeadStatus(
                leadId,
                request.getStatus()
        );

        ApiResponse<LeadEntity> response = new ApiResponse<>();
        response.setMsg("Lead status updated Successfully");
        response.setSuccess(true);
        response.setData(updatedLead);

        return ResponseEntity.ok(response);
    }

}