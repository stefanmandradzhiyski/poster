package com.snmi.component;

public interface UniqueInteraction {

    void throwExceptionIfTheInteractionAlreadyExists(final String username, final String postId);

}
