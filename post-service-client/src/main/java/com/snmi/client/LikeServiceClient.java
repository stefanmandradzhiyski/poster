package com.snmi.client;

import com.snmi.dto.BaseResponse;
import com.snmi.dto.LikeDTO;
import com.snmi.util.BaseRESTTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.snmi.constants.PortGlobalConstants.POST_SERVER_PORT;

@Service
public class LikeServiceClient extends BaseClient {

    public LikeServiceClient() {
        setPort(POST_SERVER_PORT);
        setBaseURL("api/v1/likes");
    }

    public BaseResponse<LikeDTO> createLike(final String token, final LikeDTO likeDTO) {
        return BaseRESTTemplate.post(token, getBaseURL(), likeDTO, new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<HttpStatus> deleteLike(final String token, final String likeId) {
        return BaseRESTTemplate.delete(token, getBaseURL(), likeId, new ParameterizedTypeReference<>(){});
    }

}