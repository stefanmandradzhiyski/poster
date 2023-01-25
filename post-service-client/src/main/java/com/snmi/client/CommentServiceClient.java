package com.snmi.client;

import com.snmi.dto.BaseResponse;
import com.snmi.dto.CommentDTO;
import com.snmi.util.BaseRESTTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.snmi.constants.PortGlobalConstants.POST_SERVER_PORT;

@Service
public class CommentServiceClient extends BaseClient {

    public CommentServiceClient() {
        setPort(POST_SERVER_PORT);
        setBaseURL("api/v1/comments");
    }

    public BaseResponse<CommentDTO> createComment(final String token, final CommentDTO commentDTO) {
        return BaseRESTTemplate.post(token, getBaseURL(), commentDTO, new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<CommentDTO> updateComment(final String token, final CommentDTO commentDTO) {
        return BaseRESTTemplate.put(token, getBaseURL(), commentDTO, new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<HttpStatus> deleteComment(final String token, final String commentId) {
        return BaseRESTTemplate.delete(token, getBaseURL(), commentId, new ParameterizedTypeReference<>(){});
    }

}