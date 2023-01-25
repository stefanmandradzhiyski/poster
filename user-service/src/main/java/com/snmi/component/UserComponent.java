package com.snmi.component;

import com.snmi.exception.ConflictException;
import com.snmi.exception.NotFoundException;
import com.snmi.model.User;
import com.snmi.projection.UserProjection;
import com.snmi.repository.UserRepository;
import com.snmi.util.QueryParamUtil;
import com.snmi.wrapper.UserSearchRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.snmi.constants.GlobalConstants.USERNAME;

@Component
@RequiredArgsConstructor
public class UserComponent {

    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public User save(final User entity) {
        return entity == null ? null : userRepository.saveAndFlush(entity);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public User getByUsername(final String username) {
        return userRepository.findByUsername(QueryParamUtil.getStringOrEmpty(username))
                .orElseThrow(() -> new NotFoundException(User.class, USERNAME, QueryParamUtil.getStringOrEmpty(username)));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public User getActiveUserByUsername(final String username) {
        return userRepository.findByUsernameAndActiveTrue(QueryParamUtil.getStringOrEmpty(username))
                .orElseThrow(() -> new NotFoundException(User.class, USERNAME, QueryParamUtil.getStringOrEmpty(username)));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteByUsername(final String username) {
        userRepository.deleteByUsername(QueryParamUtil.getStringOrEmpty(username));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<UserProjection> search(final UserSearchRequestWrapper searchRequestWrapper) {
        return userRepository.search(
                searchRequestWrapper.getUsername(),
                searchRequestWrapper.getSortType(),
                searchRequestWrapper.getSortDirection()
        );
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void throwExceptionIfThereIsUsernameConflict(final String username) {
        if (userRepository.existsByUsername(QueryParamUtil.getStringOrEmpty(username))) {
            throw new ConflictException(User.class, USERNAME, username);
        }
    }

}
