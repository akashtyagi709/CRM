package CRM.Backend.Controller;

import CRM.Backend.DTO.ApiResponse;
import CRM.Backend.DTO.LoginRequest;
import CRM.Backend.DTO.RegisterRequest;
import CRM.Backend.Service.AuthService;
import CRM.Backend.Service.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.authService=authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request){
        boolean created=authService.saveUser(request);
        if (!created){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("Email already registered.",false,null));
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( new ApiResponse("User registered successfully.",true,null));
    }

    @PostMapping("/user-register")
    public ResponseEntity<?> createEndUser(@RequestBody RegisterRequest request){
        boolean created=authService.createEndUser(request);
        if (!created){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("Email already registered.",false,null));
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( new ApiResponse("User registered successfully.",true,null));
    }

    @PostMapping("/login")
    public  ResponseEntity<ApiResponse> loginUser(@RequestBody LoginRequest request){
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );
        String jwtToken=jwtUtil.generateToken(request.email());
        return  ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse("Login Successfully",true, (Map.of("Bearer", jwtToken))));
     }

}
