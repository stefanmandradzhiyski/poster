package com.snmi.service;

import com.snmi.client.PostServiceClient;
import com.snmi.client.UserServiceClient;
import com.snmi.dto.BaseResponse;
import com.snmi.dto.BatchPostDisplayDTO;
import com.snmi.dto.PostDTO;
import com.snmi.dto.PostDisplayDTO;
import com.snmi.dto.PostSearchRequestDTO;
import com.snmi.dto.UserActiveDTO;
import com.snmi.dto.UserDTO;
import com.snmi.dto.UserProfileDTO;
import com.snmi.mapper.DomainMapper;
import com.snmi.util.AccessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainUserService {

    private final DomainMapper domainMapper;
    private final PostServiceClient postServiceClient;
    private final UserServiceClient userServiceClient;

    public UserProfileDTO getUserProfile(final String token, final String username) {
        return domainMapper.toUserProfileDTO(
                userServiceClient.getUserByUsername(token, username).getData(),
                searchPosts(token, username)
        );
    }

    public UserProfileDTO activateUserProfile(final String token, final String username) {
        return manageUserActiveFlag(token, username, true);
    }

    public UserProfileDTO deactivateUserProfile(final String token, final String username) {
        return manageUserActiveFlag(token, username, false);
    }

    public HttpStatus deleteUserProfile(final String token, final String username) {
        BaseResponse<HttpStatus> status = userServiceClient.deleteUser(token, username);
        if (status.getStatusCode() != HttpStatus.OK) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }

        return postServiceClient.deletePostsByUsername(token, username).getData();
    }

    private List<PostDTO> searchPosts(final String token, final String username) {
        final PostSearchRequestDTO searchRequest = PostSearchRequestDTO.builder()
                .username(username)
                .inactiveIncluded(AccessValidator.isYours(username))
                .build();

        return postServiceClient.searchPosts(token, searchRequest).getData();
    }

    private UserProfileDTO manageUserActiveFlag(final String token, final String username, final boolean active) {
        final UserDTO user = userServiceClient.patchUserActiveFlag(
                token,
                UserActiveDTO.builder().username(username).active(active).build()
        ).getData();

        final List<String> postIds = searchPosts(token, username).stream().map(PostDTO::getId).toList();
        final List<PostDisplayDTO> requests = postIds.stream().map(postId -> new PostDisplayDTO(postId, active)).toList();
        final BatchPostDisplayDTO generalRequest = new BatchPostDisplayDTO(new HashSet<>(requests));
        final List<PostDTO> posts = postServiceClient.patchPostsDisplays(token, generalRequest).getData();

        return domainMapper.toUserProfileDTO(user, posts);
    }

}
