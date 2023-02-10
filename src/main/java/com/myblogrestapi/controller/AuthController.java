package com.myblogrestapi.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblogrestapi.entity.Role;
import com.myblogrestapi.entity.User;
import com.myblogrestapi.payload.JWTAuthResponse;
import com.myblogrestapi.payload.LoginDto;
import com.myblogrestapi.payload.SignUpDto;
import com.myblogrestapi.repositories.RoleRepository;
import com.myblogrestapi.repositories.UserRepository;
import com.myblogrestapi.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	

	@PostMapping("/signin")
	public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDto.getUsernameOrEmail(),
						loginDto.getPassword())
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//get token from tokenProvider
		String token = tokenProvider.generateToken(authentication);
		
		 return ResponseEntity.ok(new JWTAuthResponse(token));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
		
		// add check for username exists in a DB
		if(userRepository.findByUsername(signUpDto.getUsername()).isPresent() ) {
			return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
		}
		
		// add check for email exist in DB
		if(userRepository.findByEmail(signUpDto.getEmail()).isPresent() ) {
			return new ResponseEntity<>("Email is already taken !", HttpStatus.BAD_REQUEST);
		}
		
		//create user object
		User user = new User();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		
		//Will Set ADMIN role for user by default
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(roles));
		
		userRepository.save(user);
		
		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
	}
}
