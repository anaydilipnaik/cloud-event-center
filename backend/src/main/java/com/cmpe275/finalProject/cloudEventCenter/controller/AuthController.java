package com.cmpe275.finalProject.cloudEventCenter.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.LogOutRequest;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.LoginRequest;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.MessageResponse;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.SignupRequest;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.TokenRefreshRequest;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.TokenRefreshResponse;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.UpdateUserRequest;
import com.cmpe275.finalProject.cloudEventCenter.model.RefreshToken;
import com.cmpe275.finalProject.cloudEventCenter.service.RefreshTokenService;
import com.cmpe275.finalProject.cloudEventCenter.service.UserService;
import com.cmpe275.finalProject.cloudEventCenter.security.Config;
import com.cmpe275.finalProject.cloudEventCenter.security.jwt.JwtUtils;
import com.cmpe275.finalProject.cloudEventCenter.exception.TokenRefreshException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	Config urlConfig;
	
	 @Autowired
	 RefreshTokenService refreshTokenService;
	 
	 @Autowired
	  JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest request)
			throws ParseException, MessagingException, UnsupportedEncodingException

	{

		return userService.createUser(signUpRequest.getEmail(), signUpRequest.getPassword(),
				signUpRequest.getFullName(), signUpRequest.getScreenName(), signUpRequest.getGender(),
				signUpRequest.getDescription(), signUpRequest.getRole(), signUpRequest.getNumber(),
				signUpRequest.getStreet(), signUpRequest.getCity(), signUpRequest.getState(), signUpRequest.getZip(),
				getSiteURL(request));
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest
			,@PathVariable String id) {
		return userService.updateUser(id, 
				updateUserRequest.getFullName(), updateUserRequest.getScreenName(), updateUserRequest.getGender(),
				updateUserRequest.getDescription(), updateUserRequest.getNumber(),
				updateUserRequest.getStreet(), updateUserRequest.getCity(), updateUserRequest.getState(), updateUserRequest.getZip());
	}

	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		System.out.println(siteURL.replace(request.getServletPath(), ""));
		return siteURL.replace(request.getServletPath(), "");
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable String id) {
		return userService.getUser(id);
	}

	@GetMapping("/verify")
	public String verifyUser(@RequestParam("code") String code) {
		if (userService.verify(code)) {
			return "<html>\n" + "    <body>\n" + "        <p>Verification Successful.</p>\n" + "<a href="
					+ urlConfig.getFrontEndURL() +"login"+ " target='_blank'>Login</a>" + "    </body>\n" + "</html>\n";
		} else {
			return "<html>\n" + "    <body>\n" + "        <p>Verification Failed</p>\n" + "    </body>\n" + "</html>\n";
		}
	}
	
	  @PostMapping("/logout")
	  public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
	    refreshTokenService.deleteByUserId(logOutRequest.getId());
	    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
	  }
	
	@PostMapping("/refreshtoken")
	  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
	    String requestRefreshToken = request.getRefreshToken();
	    return refreshTokenService.findByToken(requestRefreshToken)
	        .map(refreshTokenService::verifyExpiration)
	        .map(RefreshToken::getUser)
	        .map(user -> {
	          String token = jwtUtils.generateTokenFromEmail(user.getEmail());
	          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
	        })
	        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
	                "Refresh token is not in database!"));
	  }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/event/{userID}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getAllEventsByUserID(@PathVariable("userID") String userID){
		return userService.getAllEventsByUserID(userID);
	}

}
