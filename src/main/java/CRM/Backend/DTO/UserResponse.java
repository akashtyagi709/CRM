package CRM.Backend.DTO;

import CRM.Backend.Enum.Role;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String companyName
) {
}
