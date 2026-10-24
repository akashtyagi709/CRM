package CRM.Backend.Controller;

import CRM.Backend.DTO.ApiResponse;
import CRM.Backend.DTO.ChangeLeadStatusRequest;
import CRM.Backend.DTO.PageResponse;
import CRM.Backend.Entity.LeadEntity;
import CRM.Backend.Enum.LeadStatus;
import CRM.Backend.Service.LeadService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leads")

public class LeadDataController {

    private final LeadService leadService;
    public LeadDataController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<LeadEntity>>> getLeads(
            @RequestParam(required = false) LeadStatus status,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                "Leads found Successfully", true, leadService.getLeads(status, search, pageable)));
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