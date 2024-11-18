package org.odc.demo.Services.Impl;

import org.odc.demo.Datas.Entity.Account;
import org.odc.demo.Datas.Entity.Role;
import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Enums.AccountType;
import org.odc.demo.Datas.Repository.AccountRepository;
import org.odc.demo.Datas.Repository.RoleRepository;
import org.odc.demo.Datas.Repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.odc.demo.Web.Dtos.Request.UserRequestDto;
import org.odc.utils.Dtos.LoginUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserDetailsService userDetailsService;

    @Autowired
    AccountRepository accountRepository;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public UserEntity signup(UserRequestDto input) {
        Account account = new Account();
        account.setType(AccountType.Standard);
        account.setPlafond(100000.00F);
        account.setSommeDepot(0.00F);
        account.setPlafonnee(false);
        account.setSolde(0.00F);

        UserEntity user = new UserEntity();
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setAdresse(input.getAdresse());
        user.setNom(input.getNom());
        user.setPrenom(input.getPrenom());
        user.setTelephone(input.getTelephone());
        user.setStatus(input.getStatus());
        user.setIdCardNumber(input.getIdCardNumber());
        user.setAccount(account);
        user.setDeleted(false);

        Role role = roleRepository.findById(input.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        user.setRole(role);

        account.setUser(user);

        return userRepository.save(user);
    }
    public UserDetails authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        // Load the UserDetails directly from the UserDetailsService
        return userDetailsService.loadUserByUsername(input.getEmail());
    }
}
