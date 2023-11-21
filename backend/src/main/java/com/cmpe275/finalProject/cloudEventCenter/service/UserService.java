package com.cmpe275.finalProject.cloudEventCenter.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.JwtResponse;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.MessageResponse;
import com.cmpe275.finalProject.cloudEventCenter.mail.bean.Mail;
import com.cmpe275.finalProject.cloudEventCenter.model.Address;
import com.cmpe275.finalProject.cloudEventCenter.model.ERole;
import com.cmpe275.finalProject.cloudEventCenter.model.EventParticipant;
import com.cmpe275.finalProject.cloudEventCenter.model.RefreshToken;
import com.cmpe275.finalProject.cloudEventCenter.model.Role;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.RoleRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;
import com.cmpe275.finalProject.cloudEventCenter.security.jwt.JwtUtils;
import com.cmpe275.finalProject.cloudEventCenter.mail.service.MailService;
import net.bytebuddy.utility.RandomString;
import com.cmpe275.finalProject.cloudEventCenter.mail.service.NotificationMailService;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	EventRepository eventRepository;


	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	NotificationMailService notificationMailService;
	
	@Transactional
	public ResponseEntity<?> createUser(String email, String password, String fullName, String screenName,
			String gender, String description, Set<String> strRoles, String number, String street, String city,
			String state, String zip, String siteURL)
			throws ParseException, MessagingException, UnsupportedEncodingException {

		if (userRepository.existsByEmail(email)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
		if (userRepository.existsByScreenName(screenName)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Screen Name is already in use!"));
		}
		
		if(strRoles==null || strRoles.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: No roles are specified for the user"));
		}
		
		Address address = new Address(street, number, city, state, zip);

		User user = new User(null, email, fullName, screenName, passwordEncoder.encode(password), gender, description,
				null, true, null, address, null, null, null);

		Set<Role> roles = new HashSet<>();
		if (strRoles != null) {
			strRoles.forEach(role -> {
				switch (role) {
				case "organization":

					Role adminRole = roleRepository.findByName(ERole.ROLE_ORGANIZATION)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "person":
					Role userRole = roleRepository.findByName(ERole.ROLE_PERSON)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
					break;
				}
			});
		}

		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		 user.setEnabled(false);
		user.setRoles(roles);
		userRepository.save(user);
		notificationMailService.sendNotificationEmail(user.getEmail(),"signup",null);
		 sendVerificationEmail(user, siteURL);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

	}
	
	@Transactional
	public ResponseEntity<?> login(String email, String password) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println(userDetails.getId());
		System.out.println(userDetails.getUsername());
		System.out.println(userDetails.getEmail());

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		System.out.println("userDetails.getId(): " + userDetails.getId());
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		String city="";
		Address address=userRepository.getById(userDetails.getId()).getAddress();
		if(address!=null) {
		city=address.getCity();
		}
		System.out.println(roles);
		JwtResponse jwtResp = new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
				userDetails.getUsername(), userDetails.getEmail(),city, roles);
		return ResponseEntity.ok(jwtResp);

	}
	
	@Transactional
	public ResponseEntity<?> updateUser(String userId, String fullName, String screenName,
			String gender, String description, String number, String street, String city, String state, String zip) {

		if (!userRepository.existsById(userId)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error:user does not exist!"));
		}

		User user = userRepository.getById(userId);
		Address address = new Address(street, number, city, state, zip);
		user.setScreenName(screenName);
		
		user.setFullName(fullName);
		user.setFullName(fullName);
		user.setGender(gender);
		user.setAddress(address);
		user.setDescription(description);

		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User updated successfully!"));

	}

	public ResponseEntity<?> getUser(String userId) {
		
		if (!userRepository.existsById(userId)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error:user does not exist!"));
		}
		
		return ResponseEntity.ok( userRepository.getById(userId));

	}

	private void sendVerificationEmail(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		
		Mail mail = new Mail();
		mail.setMailFrom("cmpe275Zhangproject@gmail.com");
		 mail.setMailTo(user.getEmail());
		 mail.setMailSubject("Confirm Registration");

		 
		String fName = user.getFullName() != null ? user.getFullName() : "";
		 String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
					+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "CEC";
		 content = content.replace("[[name]]", fName + " ");
			String verifyURL = siteURL + "/api/auth/verify?code=" + user.getVerificationCode();

			content = content.replace("[[URL]]", verifyURL);
			 mail.setMailContent(content);
			mailService.sendEmail(mail);

	}

	public boolean verify(String verificationCode) {
		System.out.println("verificationCode: "+verificationCode);
		User user = userRepository.findByVerificationCode(verificationCode);
		
		System.out.println("user : "+user.getEmail());
		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			userRepository.save(user);
			notificationMailService.sendNotificationEmail(user.getEmail(),"verify",null);
			return true;
		}

	}
	
	
	//TEST this after event registration
	@Transactional
	public ResponseEntity<?> getAllEventsByUserID(String userID) {
		
		try {
		User user = userRepository.findById(userID).get();
		if(user==null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error:user not found!"));

		}
		List<EventParticipant> attending_events = user.getEvents();
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(attending_events);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse(e.toString()));

		}
		
	}
}
