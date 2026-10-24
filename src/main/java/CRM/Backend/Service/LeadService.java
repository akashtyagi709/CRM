package CRM.Backend.Service;

import CRM.Backend.DTO.LeadRequest;
import CRM.Backend.DTO.PageResponse;
import CRM.Backend.Entity.LeadEntity;
import CRM.Backend.Enum.LeadStatus;
import CRM.Backend.Repositry.LeadRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LeadService {

    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public LeadEntity createLead(LeadRequest request) {
        LeadEntity lead = new LeadEntity();
        lead.setName(request.name().trim());
        lead.setEmail(request.email().trim().toLowerCase());
        lead.setPhone(request.phone().trim());
        lead.setMessage(request.message().trim());
        lead.setStatus(LeadStatus.NEW);
        return leadRepository.save(lead);
    }

    public boolean isDuplicate(String email, String phone) {
        return leadRepository.existsByEmailOrPhone(email, phone);
    }

    public PageResponse<LeadEntity> getLeads(LeadStatus status, String search, Pageable pageable) {
        String term = (search == null) ? "" : search.trim();
        return PageResponse.from(leadRepository.search(status, term, pageable));
    }

    public LeadEntity changeLeadStatus(Long leadId, LeadStatus status) {
        if (status == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lead status is required");
        }
        LeadEntity lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lead not found"));
        lead.setStatus(status);
        return leadRepository.save(lead);
    }
}