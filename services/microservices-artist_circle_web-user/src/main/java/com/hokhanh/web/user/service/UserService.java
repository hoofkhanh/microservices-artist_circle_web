package com.hokhanh.web.user.service;

import org.springframework.stereotype.Service;

import com.hokhanh.common.constant.RoleConstants;
import com.hokhanh.common.util.StringUtils;
import com.hokhanh.web.user.constant.ExpirationConstants;
import com.hokhanh.web.user.email.UserEmailService;
import com.hokhanh.web.user.mapper.UserMapper;
import com.hokhanh.web.user.model.Role;
import com.hokhanh.web.user.model.User;
import com.hokhanh.web.user.redis.UserRedisService;
import com.hokhanh.web.user.repository.RoleRepository;
import com.hokhanh.web.user.repository.UserRepository;
import com.hokhanh.web.user.request.UserRequest;
import com.hokhanh.web.user.response.UserApiResponse;
import com.hokhanh.web.user.response.MessageOption;
import com.hokhanh.web.user.response.UserStatusType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserMapper mapper;
	private final UserRedisService userRedisService;
	private final UserEmailService userEmailService;

	public UserApiResponse register(UserRequest userRequest) {
		UserApiResponse error = validateBeforeRegister(userRequest);
		if(error != null) {
			return error;
		}

		handleUserOtpFlow(userRequest);

		return new UserApiResponse(true, "OTP sent to your gmail", null, null,
				new MessageOption(ExpirationConstants.OTP_MINUTES, ExpirationConstants.USER_MINUTES));
	}

	public UserApiResponse verifyRegistrationOtp(String otp, String email) {
		UserRequest userRequest = userRedisService.getCachedUserRequest(email);

		UserApiResponse error = validateBeforeVerifyRegistrationOtp(otp, userRequest);
		if(error != null) {
			return error;
		}

		userRedisService.deleteCachedOtpAndUserRequest(email);
				
		Role role = roleRepository.findById(userRequest.roleId()).orElse(null);
		
		User user = mapper.toUser(userRequest, role);
		user.setFullName(StringUtils.cleanBlank(user.getFullName()));
		user.setEmail(StringUtils.cleanBlank(user.getEmail()));
		user.setPassword(StringUtils.cleanBlank(user.getPassword()));
		
		user = userRepository.save(user);
		
		return new UserApiResponse(true, "Register successfully", null, mapper.toUserResponse(user), null);
	}

	public UserApiResponse resendOtp(String email) {
		UserApiResponse error = validateBeforeResendOtp(email);
		if(error != null) {
			return error;
		}
		
		generateAndCacheAndSendOtp(email);

		return new UserApiResponse(true, "OTP resend to your gmail", null, null,
				new MessageOption(ExpirationConstants.OTP_MINUTES, null));
	}
	
	public boolean checkUserExistsInternal(Long id) {
		if(userRepository.existsByIdAndRole_Name(id, RoleConstants.ARTIST_ROLE)) {
			return true;
		}
		
		return false;
	}

	private void handleUserOtpFlow(UserRequest userRequest) {
		userRedisService.cacheUserRequest(userRequest);
		generateAndCacheAndSendOtp(userRequest.email());
	}

	private void generateAndCacheAndSendOtp(String email) {
		String otp = userRedisService.generateAndCacheOtp(email);
		userEmailService.sendOtpToGmail(email, otp);
	}

	private UserApiResponse validateUserRequest(UserRequest userRequest) {
		if (userRequest == null) {
			return new UserApiResponse(false, "Let's re-enter all your registration information",
					UserStatusType.REGISTRATION_SESSION_INVALID, null, null);
		}

		return null;
	}

	private UserApiResponse validateBeforeRegister(UserRequest userRequest) {
		if (roleRepository.findById(userRequest.roleId()).orElse(null) == null) {
			return new UserApiResponse(false, "Role id is not exists", UserStatusType.ROLE_NOT_EXISTS, null, null);
		}

		if (userRepository.existsByEmail(userRequest.email())) {
			return new UserApiResponse(false, "Email already exists", UserStatusType.EMAIL_ALREADY_EXISTS, null, null);
		}
		
		return null;
	}
	
	private UserApiResponse validateBeforeVerifyRegistrationOtp(String otp, UserRequest userRequest) {
		UserApiResponse userRequestError = validateUserRequest(userRequest);
		if (userRequestError != null) {
			return userRequestError;
		}

		UserApiResponse otpError = validateOtp(userRequest.email(), otp);
		if (otpError != null) {
			return otpError;
		}
		
		return null;
	}
	
	private UserApiResponse validateOtp(String email, String otp) {
		String otpFromRedis = userRedisService.getCachedOtp(email);
		if (otpFromRedis == null) {
			return new UserApiResponse(false, "OTP is expired", UserStatusType.OTP_EXPIRED, null, null);
		}

		if (!otpFromRedis.equals(otp)) {
			return new UserApiResponse(false, "OTP is invalid", UserStatusType.OTP_INVALID, null, null);
		}

		return null;
	}

	private UserApiResponse validateBeforeResendOtp(String email) {
		UserRequest userRequest = userRedisService.getCachedUserRequest(email);

		UserApiResponse userRequestError = validateUserRequest(userRequest);
		if (userRequestError != null) {
			return userRequestError;
		}

		String otpFromRedis = userRedisService.getCachedOtp(email);
		if (otpFromRedis != null) {
			return new UserApiResponse(false, "OTP is still valid", UserStatusType.OTP_STILL_VALID, null,
					new MessageOption(null, null));
		}
		
		return null;
	}

	

}
