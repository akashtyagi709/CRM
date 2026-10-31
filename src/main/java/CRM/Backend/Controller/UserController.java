package CRM.Backend.Controller;

import CRM.Backend.DTO.ApiResponse;
import CRM.Backend.DTO.UpdateUserRequest;
import CRM.Backend.DTO.UserResponse;
import CRM.Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")

public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        List<UserResponse> user= userService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Users found Successfully",true,user));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUSerByID(@PathVariable long id){
        Optional<UserResponse> user = userService.getByID(id);
        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("User not found",false,null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("User found successfully",true,user.get()));
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(@PathVariable long id , @RequestBody UpdateUserRequest request){
        Optional<UserResponse> user= userService.updateUser(id, request);
        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("User not found",false,null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("User updated successfully",true,user.get()));
    }

    @PatchMapping("/user/{id}/status")
    public ResponseEntity<ApiResponse<UserResponse>> changeStatus(@PathVariable  long id , @RequestParam boolean active){
        Optional<UserResponse> user= userService.updateStatus(id, active);
        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("User not found",false,null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("User Status updated successfully",true,user.get()));
    }
}
