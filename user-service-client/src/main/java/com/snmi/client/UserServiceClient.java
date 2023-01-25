package com.snmi.client;

import com.snmi.dto.BaseResponse;
import com.snmi.dto.UserActiveDTO;
import com.snmi.dto.UserDTO;
import com.snmi.dto.UserPasswordDTO;
import com.snmi.dto.UserSearchRequestDTO;
import com.snmi.util.BaseRESTTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.snmi.constants.PortGlobalConstants.USER_SERVER_PORT;

@Service
public class UserServiceClient extends BaseClient {

    public UserServiceClient() {
        setPort(USER_SERVER_PORT);
        setBaseURL("api/v1/users");
    }

    public BaseResponse<UserDTO> createUser(final String token, final UserDTO userDTO) {
        return BaseRESTTemplate.post(token, getBaseURL(), userDTO, new ParameterizedTypeReference<>() {});
    }

    public BaseResponse<UserDTO> patchUserPassword(final String token, final UserPasswordDTO userPasswordDTO) {
        return BaseRESTTemplate.patch(
                token,
                getBaseURL() + "/password",
                userPasswordDTO,
                new ParameterizedTypeReference<>() {}
        );
    }

    public BaseResponse<UserDTO> patchUserActiveFlag(final String token, final UserActiveDTO userActiveDTO) {
        return BaseRESTTemplate.patch(
                token,
                getBaseURL() + "/active",
                userActiveDTO,
                new ParameterizedTypeReference<>() {}
        );
    }

    public BaseResponse<UserDTO> getUserByUsername(final String token, final String username) {
        return BaseRESTTemplate.get(token, getBaseURL(), username, new ParameterizedTypeReference<>() {});
    }

    public BaseResponse<HttpStatus> deleteUser(final String token, final String username) {
        return BaseRESTTemplate.delete(token, getBaseURL(), username, new ParameterizedTypeReference<>() {});
    }

    public BaseResponse<List<UserDTO>> searchUsers(final String token, final UserSearchRequestDTO searchRequestDTO) {
        return BaseRESTTemplate.post(
                token,
                getBaseURL() + "/search",
                searchRequestDTO,
                new ParameterizedTypeReference<>() {}
        );
    }

}
