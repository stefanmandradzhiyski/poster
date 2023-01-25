package com.snmi.service;

import com.snmi.component.UserComponent;
import com.snmi.dto.KafkaUserCountDTO;
import com.snmi.dto.UserActiveDTO;
import com.snmi.dto.UserDTO;
import com.snmi.dto.UserPasswordDTO;
import com.snmi.dto.UserSearchRequestDTO;
import com.snmi.mapper.UserMapper;
import com.snmi.model.User;
import com.snmi.util.AccessValidator;
import com.snmi.util.UserValidator;
import com.snmi.wrapper.UserSearchRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.snmi.constants.GlobalConstants.CREATE_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.DEFAULT_COUNTER;
import static com.snmi.constants.GlobalConstants.DELETE_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.GET_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.LOG_REQUEST;
import static com.snmi.constants.GlobalConstants.LOG_RESPONSE;
import static com.snmi.constants.GlobalConstants.PATCH_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.SEARCH_FUNCTIONALITY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private static final String USER = "user";
    private static final String MANIPULATE_COUNTERS = "Manipulate user counters using";

    private final UserMapper userMapper;
    private final UserComponent userComponent;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserDTO createUser(final UserDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(CREATE_FUNCTIONALITY, USER), dto));

        UserValidator.validateUsername(dto.getUsername());
        UserValidator.validatePassword(dto.getPassword());
        userComponent.throwExceptionIfThereIsUsernameConflict(dto.getUsername());
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        final UserDTO response = userMapper.toDTO(userComponent.save(userMapper.toModel(dto)));

        LOG.info(String.format(LOG_RESPONSE, String.format(CREATE_FUNCTIONALITY, USER), response));

        return response;
    }

    public UserDTO getUserByUsername(final String username) {
        LOG.info(String.format(LOG_REQUEST, String.format(GET_FUNCTIONALITY, USER), username));
        final UserDTO response = userMapper.toDTO(userComponent.getActiveUserByUsername(username));
        LOG.info(String.format(LOG_RESPONSE, String.format(GET_FUNCTIONALITY, USER), response));

        return response;
    }

    @Transactional
    public UserDTO patchUserPassword(final UserPasswordDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(PATCH_FUNCTIONALITY, USER), dto));

        AccessValidator.controlYourData(dto.getUsername());
        UserValidator.validatePassword(dto.getPassword());
        User user = userComponent.getActiveUserByUsername(dto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        final UserDTO response = userMapper.toDTO(userComponent.save(user));

        LOG.info(String.format(LOG_RESPONSE, String.format(PATCH_FUNCTIONALITY, USER), response));

        return response;
    }

    @Transactional
    public UserDTO patchUserActiveFlag(final UserActiveDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(PATCH_FUNCTIONALITY, USER), dto));

        AccessValidator.controlYourData(dto.getUsername());
        User user = userComponent.getByUsername(dto.getUsername());
        user.setActive(dto.isActive());
        final UserDTO response = userMapper.toDTO(userComponent.save(user));

        LOG.info(String.format(LOG_RESPONSE, String.format(PATCH_FUNCTIONALITY, USER), response));

        return response;
    }

    @Transactional
    public void deleteUser(final String username) {
        LOG.info(String.format(LOG_REQUEST, DELETE_FUNCTIONALITY, username));
        AccessValidator.controlYourData(username);
        userComponent.deleteByUsername(username);
        LOG.info(String.format(LOG_RESPONSE, DELETE_FUNCTIONALITY, username));
    }

    public List<UserDTO> searchUsers(final UserSearchRequestDTO searchRequest) {
        LOG.info(String.format(LOG_REQUEST, String.format(SEARCH_FUNCTIONALITY, USER), searchRequest));
        List<UserDTO> response = userComponent.search(new UserSearchRequestWrapper(searchRequest))
                .stream()
                .map(userMapper::toDTO)
                .toList();
        LOG.info(String.format(LOG_RESPONSE, String.format(SEARCH_FUNCTIONALITY, USER), response));

        return response;
    }

    @Transactional
    public void managePostCounters(final KafkaUserCountDTO dto) {
        LOG.info(String.format(LOG_REQUEST, MANIPULATE_COUNTERS, dto));

        User user = userComponent.getActiveUserByUsername(dto.getUsername());
        switch (dto.getVisiblePost()) {
            case INCREASE -> user.setVisiblePostCount(user.getVisiblePostCount() + DEFAULT_COUNTER);
            case DECREASE -> user.setVisiblePostCount(user.getVisiblePostCount() - DEFAULT_COUNTER);
            case NO_MOVEMENT -> user.setVisiblePostCount(user.getVisiblePostCount());
        }
        switch (dto.getTotalPost()) {
            case INCREASE -> user.setTotalPostCount(user.getTotalPostCount() + DEFAULT_COUNTER);
            case DECREASE -> user.setTotalPostCount(user.getTotalPostCount() - DEFAULT_COUNTER);
            case NO_MOVEMENT -> user.setTotalPostCount(user.getTotalPostCount());
        }
        userComponent.save(user);

        LOG.info(String.format(LOG_RESPONSE, MANIPULATE_COUNTERS, dto));
    }

}
