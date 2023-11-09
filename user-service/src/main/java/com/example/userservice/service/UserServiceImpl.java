package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.OrderResponse;
import com.example.userservice.vo.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;
//    private final RestTemplate restTemplate;
    private final OrderServiceClient orderServiceClient;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = mapper.map(userDto, User.class);
        user.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        userRepository.save(user);

        UserDto returnUserDto = mapper.map(user, UserDto.class);
        return returnUserDto;
    }

    @Override
    public UserResponse getUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        UserDto userDto = new ModelMapper().map(user, UserDto.class);

        /**
         * MicroService간 통신
         * RestTemplate과 getBody 사용
         * UserMicroService에서 OrderMicroService로 데이터 요청해서 가져옴
         */
//        String orderUrl = String.format(env.getProperty("order_service.url"), userId);
//        ResponseEntity<List<OrderResponse>> orderResponseList =  restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<OrderResponse>>() {
//        });

        /**
         * MicroService간 통신
         * Feign Client 사용
         * UserMicroService에서 OrderMicroService로 데이터 요청해서 가져옴
         */
        List<OrderResponse> orders = orderServiceClient.getOrders(userId);
        userDto.setOrders(orders);

        UserResponse userResponse = new ModelMapper().map(userDto, UserResponse.class);
        return userResponse;
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();
        users.forEach(user -> {
            responses.add(new ModelMapper().map(user, UserResponse.class));
        });
        return responses;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPwd(),
                true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByEmail(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        return userDto;
    }
}
