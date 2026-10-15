package CRM.Backend.Repositry;

import CRM.Backend.Entity.LeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends JpaRepository<LeadEntity,Long > {
    boolean existsByEmailOrPhone(String email, String phone);
}
