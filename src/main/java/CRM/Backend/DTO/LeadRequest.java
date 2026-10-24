package CRM.Backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LeadRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name is too long") String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid") String email,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^[0-9+\\-\\s]{7,15}$", message = "Phone number is not valid") String phone,

        @NotBlank(message = "Message is required")
        @Size(max = 2000, message = "Message must be at most 2000 characters") String message) {
}