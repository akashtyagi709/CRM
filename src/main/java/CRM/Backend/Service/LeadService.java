package CRM.Backend.Service;

import CRM.Backend.Entity.LeadEntity;
import CRM.Backend.Repositry.LeadRepository;
import org.springframework.stereotype.Service;

@Service
public class LeadService {

    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public LeadEntity createLead(LeadEntity lead) {
        lead.setStatus(CRM.Backend.Enum.LeadStatus.NEW);
        return leadRepository.save(lead);
    }

    public boolean isDuplicate(String email, String phone) {
        return leadRepository.existsByEmailOrPhone(email, phone);
    }
}