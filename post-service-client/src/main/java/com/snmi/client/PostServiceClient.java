package com.snmi.client;

import com.snmi.dto.BaseResponse;
import com.snmi.dto.BatchPostDisplayDTO;
import com.snmi.dto.CommentDTO;
import com.snmi.dto.LikeDTO;
import com.snmi.dto.PostDTO;
import com.snmi.dto.PostDisplayDTO;
import com.snmi.dto.PostSearchRequestDTO;
import com.snmi.util.BaseRESTTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.snmi.constants.PortGlobalConstants.POST_SERVER_PORT;

@Service
public class PostServiceClient extends BaseClient {

    public PostServiceClient() {
        setPort(POST_SERVER_PORT);
        setBaseURL("api/v1/posts");
    }

    public BaseResponse<PostDTO> createPost(final String token, final PostDTO postDTO) {
        return BaseRESTTemplate.post(token, getBaseURL(), postDTO, new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<PostDTO> updatePost(final String token, final PostDTO postDTO) {
        return BaseRESTTemplate.put(token, getBaseURL(), postDTO, new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<PostDTO> patchPostDisplay(final String token, final PostDisplayDTO postDisplayDTO) {
        return BaseRESTTemplate.patch(token, getBaseURL() + "/display", postDisplayDTO, new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<List<PostDTO>> patchPostsDisplays(final String token, final BatchPostDisplayDTO batchPostDisplayDTO) {
        return BaseRESTTemplate.patch(token, getBaseURL() + "/displays", batchPostDisplayDTO, new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<PostDTO> getPostById(final String token, final String postId) {
        return BaseRESTTemplate.get(token, getBaseURL(), postId, new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<HttpStatus> deletePost(final String token, final String postId) {
        return BaseRESTTemplate.delete(token, getBaseURL() + "/specific", postId, new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<HttpStatus> deletePostsByUsername(final String token, final String username) {
        return BaseRESTTemplate.delete(token, getBaseURL() + "/several", username, new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<List<CommentDTO>> findAllPostComments(final String token, final String postId) {
        return BaseRESTTemplate.get(token, getBaseURL(), postId, "/comments", new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<List<LikeDTO>> findAllPostLikes(final String token, final String postId) {
        return BaseRESTTemplate.get(token, getBaseURL(), postId, "/likes", new ParameterizedTypeReference<>(){});
    }

    public BaseResponse<List<PostDTO>> searchPosts(final String token, final PostSearchRequestDTO searchRequestDTO) {
        return BaseRESTTemplate.post(token, getBaseURL() + "/search", searchRequestDTO, new ParameterizedTypeReference<>(){});
    }

}
