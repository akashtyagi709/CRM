package CRM.Backend.Service;

import CRM.Backend.DTO.RegisterRequest;
import CRM.Backend.Entity.UserEntity;
import CRM.Backend.Enum.Role;
import CRM.Backend.Repositry.CompanyRepository;
import CRM.Backend.Repositry.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, CompanyRepository companyRepository,PasswordEncoder passwordConfig){
        this.userRepository=userRepository;
        this.companyRepository=companyRepository;
        this.passwordEncoder=passwordConfig;
    }

    public boolean saveUser(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
          return false;
        }
            UserEntity user = new UserEntity();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );

            user.setCompany(
                    companyRepository.findById(1L)
                            .orElseThrow(()->new RuntimeException("Company not present"))
            );
            user.setRole(Role.ADMIN);
            userRepository.save(user);
            return  true;

    }

    public boolean createEndUser(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            return false;
        }
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setCompany(
                companyRepository.findById(1L)
                        .orElseThrow(()->new RuntimeException("Company not present"))
        );
        user.setRole(Role.USER);
        userRepository.save(user);
        return  true;

    }

}
