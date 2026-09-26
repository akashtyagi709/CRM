package CRM.Backend.DTO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ApiResponse<T> {

    private String msg;
    private boolean success;
    private T data;
}
