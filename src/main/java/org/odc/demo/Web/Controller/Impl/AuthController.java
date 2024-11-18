package org.odc.demo.Web.Controller.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Repository.UserRepository;
import org.odc.demo.Services.Impl.AuthenticationService;
import org.odc.demo.Services.Impl.EmailServiceImpl;
import org.odc.demo.Services.Impl.QRCodeCardServiceImpl;
import org.odc.demo.Services.Interfaces.EmailService;
import org.odc.demo.Web.Dtos.Request.UserRequestDto;
import org.odc.demo.Web.Dtos.Response.LoginResponse;
import org.odc.utils.Dtos.LoginUserDto;
import org.odc.utils.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @Autowired
    QRCodeCardServiceImpl qrCodeCardService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private Cloudinary cloudinary;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody UserRequestDto registerUserDto) throws IOException {
        UserEntity registeredUser = authenticationService.signup(registerUserDto);

        byte[] Carte =qrCodeCardService.generateQRCodeCard(registeredUser.getTelephone());

        String base64Image = Base64.getEncoder().encodeToString(Carte);
        String dataUrl = "data:image/png;base64," + base64Image;

        Map<String, Object> uploadParams = new HashMap<>();
        uploadParams.put("folder", "user_qr_codes");
        Map uploadResult = cloudinary.uploader().upload(dataUrl, uploadParams);

        String qrCodeUrl = (String) uploadResult.get("url");

        registeredUser.setQrCode(qrCodeUrl);
        userRepository.save(registeredUser);

        emailService.sendAuthenticationEmail(registeredUser.getEmail(),registeredUser.getEmail(),registeredUser.getPassword(),Carte);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
        UserDetails authenticatedUser = authenticationService.authenticate(loginUserDto);


        String jwtToken = jwtService.generateToken((UserDetails) authenticatedUser);

        UserEntity user = userRepository.findByEmail(authenticatedUser.getUsername()).orElse(null);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setSolde(user.getAccount().getSolde());

        loginResponse.setUser(user);

        return ResponseEntity.ok(loginResponse);

        }catch (Exception error){
            System.out.println(error);
            LoginResponse loginResponse = new LoginResponse();

            loginResponse.setToken(null);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        }

    }

    @PostMapping("/test")
    public Map<String, String> getData() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from Spring Boot");
        return response;
    }
}
