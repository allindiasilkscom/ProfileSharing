package com.sharing.Service;

import com.sharing.config.JwtProvider;
import com.sharing.model.User;
import com.sharing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service

public class UserImplemantation implements UserService  {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserById(Long userId) throws Exception {

        Optional<User> opt = userRepository.findById(userId);
        if(opt.isPresent()){

            return opt.get();
        }
        throw new Exception("user Not Found With ID"+userId);
    }

    @Override
    public User finduserByJwt(String jwt) throws Exception {
        String email  = jwtProvider.getEmailFromJwtToken(jwt);
        if(email==null){
            throw new Exception("PLease provide the Valide token");
        }
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("Provide the Valide Token"+email);
        }

        return user;
    }
}
