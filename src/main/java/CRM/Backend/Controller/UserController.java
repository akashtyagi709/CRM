package CRM.Backend.Controller;

import CRM.Backend.DTO.ApiResponse;
import CRM.Backend.DTO.UserResponse;
import CRM.Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        List<UserResponse> user= userService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("User found Successfully",true,user));
    }
}
