package CRM.Backend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "company")
public class CompanyEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String companyName;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate(){
        createdAt= LocalDateTime.now();
    }
}
