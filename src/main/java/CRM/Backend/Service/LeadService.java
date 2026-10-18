package CRM.Backend.Service;

import CRM.Backend.Entity.LeadEntity;
import CRM.Backend.Enum.LeadStatus;
import CRM.Backend.Repositry.LeadRepository;

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

    public LeadEntity createLead(LeadEntity lead) {
        lead.setStatus(LeadStatus.NEW);
        return leadRepository.save(lead);
    }

    public boolean isDuplicate(String email, String phone) {
        return leadRepository.existsByEmailOrPhone(email, phone);
    }

    // API 1: Get all leads
    public List<LeadEntity> getAllLeads() {
        return leadRepository.findAll();
    }

    // API 2: Change lead status
    public LeadEntity changeLeadStatus(Long leadId, LeadStatus status) {

        if (status == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Lead status is required"
            );
        }

        LeadEntity lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Lead not found"
                ));

        lead.setStatus(status);

        return leadRepository.save(lead);
    }
}