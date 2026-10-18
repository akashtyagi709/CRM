package CRM.Backend.DTO;

import CRM.Backend.Enum.LeadStatus;

public class ChangeLeadStatusRequest {

    private LeadStatus status;

    public LeadStatus getStatus() {
        return status;
    }

    public void setStatus(LeadStatus status) {
        this.status = status;
    }
}