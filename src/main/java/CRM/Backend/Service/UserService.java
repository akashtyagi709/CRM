package CRM.Backend.Service;

import CRM.Backend.DTO.ApiResponse;
import CRM.Backend.DTO.UpdateUserRequest;
import CRM.Backend.DTO.UserResponse;
import CRM.Backend.Entity.UserEntity;
import CRM.Backend.Repositry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserResponse> getAll(){
        return userRepository.findAll() .stream()
                .map(user -> new UserResponse( user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt(), user.getCompany().getCompanyName(), user.isActive() )) .toList();
    }

    public Optional<UserResponse> getByID(long id){
        return userRepository.findById(id)
                .map(user -> new UserResponse( user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt(), user.getCompany().getCompanyName(),user.isActive()  ));
    }

    public Optional<UserResponse> updateUser(long id , UpdateUserRequest request){
        Optional<UserEntity> optionalUser  = userRepository.findById(id);
        if (optionalUser .isEmpty()){
            return Optional.empty();
        }
        UserEntity user = optionalUser.get();
         boolean existingEmail =
                 userRepository.existsByEmailAndIdNot(request.email(), id);
         if (existingEmail){
             throw new IllegalArgumentException("Email already exists in records");
         }
         user.setEmail(request.email());
         user.setName(request.name());
         UserEntity updatedUser= userRepository.save(user);
        return Optional.of(new  UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getRole(),
                updatedUser.getCreatedAt(),
                updatedUser.getUpdatedAt(),
                updatedUser.getCompany().getCompanyName(),
                updatedUser.isActive()
        ));
    }

    public Optional<UserResponse>  updateStatus(long id , boolean active){
        Optional<UserEntity> optionalUser  = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            return Optional.empty();
        }
        UserEntity user = optionalUser.get();
        user.setActive(active);
        UserEntity updatedUser= userRepository.save(user);
        return Optional.of(new UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getRole(),
                updatedUser.getCreatedAt(),
                updatedUser.getUpdatedAt(),
                updatedUser.getCompany().getCompanyName(),
                updatedUser.isActive()
        ));

    }

}
