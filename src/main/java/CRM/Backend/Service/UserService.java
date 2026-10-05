package CRM.Backend.Service;

import CRM.Backend.DTO.UserResponse;
import CRM.Backend.Repositry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserResponse> getAll(){
        return userRepository.findAll() .stream() .map(user -> new UserResponse( user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt(), user.getCompany().getCompanyName() )) .toList();
    }
}
