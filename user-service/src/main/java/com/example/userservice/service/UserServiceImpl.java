package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.OrderResponse;
import com.example.userservice.vo.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = mapper.map(userDto, User.class);
        System.out.println(userDto.getPwd());
        user.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        userRepository.save(user);

        UserDto returnUserDto = mapper.map(user, UserDto.class);
        System.out.println(returnUserDto.getPwd());
        return returnUserDto;
    }

    @Override
    public UserResponse getUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        UserDto userDto = new ModelMapper().map(user, UserDto.class);

        List<OrderResponse> orders = new ArrayList<>();
        userDto.setOrders(orders);

        UserResponse userResponse = new ModelMapper().map(userDto, UserResponse.class);
        return userResponse;
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> responseList = new ArrayList<>();
        userList.forEach(v -> {
            responseList.add(new ModelMapper().map(v, UserResponse.class));
        });
        return responseList;
    }
}
