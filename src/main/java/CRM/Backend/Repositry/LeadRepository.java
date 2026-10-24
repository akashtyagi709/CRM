package CRM.Backend.Repositry;

import CRM.Backend.Entity.LeadEntity;
import CRM.Backend.Enum.LeadStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends JpaRepository<LeadEntity,Long > {
    boolean existsByEmailOrPhone(String email, String phone);


    @Query("""
            SELECT l FROM LeadEntity l
            WHERE (:status IS NULL OR l.status = :status)
              AND (LOWER(l.name) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(l.email) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<LeadEntity> search(@Param("status") LeadStatus status,
                            @Param("search") String search,
                            Pageable pageable);
}