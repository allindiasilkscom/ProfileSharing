package com.sharing.HomeController;

import com.sharing.Request.LoginRequest;
import com.sharing.Response.AuthResponse;
import com.sharing.Service.CustomeUserService;
import com.sharing.config.JwtProvider;
import com.sharing.model.User;
import com.sharing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomeUserService customeUserService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) throws Exception{

        String email = user.getEmail();
        String password =  user.getPassword();
        String fullname =  user.getFullname();

        User isExistEmail =  userRepository.findByEmail(email);
            if(isExistEmail!=null){
                throw  new Exception("Email is Already User");
            }
            User createdUser = new User();
            createdUser.setEmail(email);
            createdUser.setFullname(fullname);
            createdUser.setPassword(passwordEncoder.encode(password));

            User savedUser = userRepository.save(createdUser);

        UsernamePasswordAuthenticationToken authentication =  new UsernamePasswordAuthenticationToken(email,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse res=  new AuthResponse();
        res.setJwt(token);
        res.setMessage("SignUp Success");
        return res;

    }

    @PostMapping("/signin")
    public AuthResponse loginHandle(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse res=  new AuthResponse();
        res.setJwt(token);
        res.setMessage("SignIn Success");

        return res;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails =  customeUserService.loadUserByUsername(username);
        if(userDetails ==null ){
            throw new BadCredentialsException("user not Found");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }


}
