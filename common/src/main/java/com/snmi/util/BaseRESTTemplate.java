package com.snmi.util;

import com.snmi.dto.BaseResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

import static com.snmi.constants.GlobalConstants.CUSTOM_END_INDEX;
import static com.snmi.constants.GlobalConstants.CUSTOM_START_INDEX;
import static com.snmi.constants.GlobalConstants.DEFAULT_END_INDEX;
import static com.snmi.constants.GlobalConstants.DEFAULT_START_INDEX;

public class BaseRESTTemplate {

    private static final RestTemplate restTemplate = new RestTemplate();

    static {
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
    }

    public static <T> BaseResponse<T> post(
        final String token,
        final String url,
        final Object request,
        final ParameterizedTypeReference<BaseResponse<T>> responseClass
    ) {
        return exchange(
            prepareUri(url, null, null).toUriString(),
            HttpMethod.POST,
            prepareHttpEntity(prepareHeaders(token), request),
            responseClass
        );
    }

    public static <T> BaseResponse<T> put(
        final String token,
        final String url,
        final Object request,
        final ParameterizedTypeReference<BaseResponse<T>> responseClass
    ) {
        return exchange(
            prepareUri(url, null, null).toUriString(),
            HttpMethod.PUT,
            prepareHttpEntity(prepareHeaders(token), request),
            responseClass
        );
    }

    public static <T> BaseResponse<T> patch(
        final String token,
        final String url,
        final Object request,
        final ParameterizedTypeReference<BaseResponse<T>> responseClass
    ) {
        return exchange(
            prepareUri(url, null, null).toUriString(),
            HttpMethod.PATCH,
            prepareHttpEntity(prepareHeaders(token), request),
            responseClass
        );
    }

    public static <T> BaseResponse<T> get(
        final String token,
        final String url,
        final String pathSegment,
        final ParameterizedTypeReference<BaseResponse<T>> responseClass
    ) {
        return exchange(
            prepareUri(url, pathSegment, null).toUriString(),
            HttpMethod.GET,
            prepareHttpEntity(prepareHeaders(token), null),
            responseClass
        );
    }

    public static <T> BaseResponse<T> get(
        final String token,
        final String url,
        final String pathSegment,
        final String specificPath,
        final ParameterizedTypeReference<BaseResponse<T>> responseClass
    ) {
        return exchange(
            prepareUri(url, pathSegment, null).toUriString() + specificPath,
            HttpMethod.GET,
            prepareHttpEntity(prepareHeaders(token), null),
            responseClass
        );
    }

    public static <T> BaseResponse<T> get(
        final String token,
        final String url,
        final String pathSegment,
        final Map<String, Object> params,
        final ParameterizedTypeReference<BaseResponse<T>> responseClass
    ) {
        return exchange(
            prepareUri(url, pathSegment, params).toUriString(),
            HttpMethod.GET,
            prepareHttpEntity(prepareHeaders(token), null),
            responseClass
        );
    }

    public static <T> BaseResponse<T> delete(
        final String token,
        final String url,
        final String pathSegment,
        final ParameterizedTypeReference<BaseResponse<T>> responseClass
    ) {
        return exchange(
            prepareUri(url, pathSegment, null).toUriString(),
            HttpMethod.DELETE,
            prepareHttpEntity(prepareHeaders(token), null),
            responseClass
        );
    }

    private static HttpHeaders prepareHeaders(final String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        return headers;
    }

    private static UriComponentsBuilder prepareUri(
        final String url,
        final String pathSegment,
        final Map<String, Object> params
    ) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (pathSegment != null) {
            builder.pathSegment(pathSegment);
        }

        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return builder;
    }

    private static HttpEntity<?> prepareHttpEntity(final HttpHeaders httpHeaders, final Object request) {
        return request == null ? new HttpEntity<>(httpHeaders) : new HttpEntity<>(request, httpHeaders);
    }

    private static <T> BaseResponse<T> exchange(
        final String finalUrl,
        final HttpMethod httpMethod,
        final HttpEntity<?> httpEntity,
        final ParameterizedTypeReference<BaseResponse<T>> responseClass
    ) {
        try {
            return restTemplate.exchange(finalUrl, httpMethod, httpEntity, responseClass).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException clientException) {
            return BaseResponse.build(parseMessage(
                    clientException.getResponseBodyAsString()),
                    clientException.getStatusCode()
            );
        }
    }

    private static String parseMessage(final String message) {
        if (message.contains(CUSTOM_START_INDEX)) {
            return message.subSequence(
                    message.indexOf(CUSTOM_START_INDEX) + 10,
                    message.indexOf(CUSTOM_END_INDEX) - 3
            ).toString();
        } else if (message.contains(DEFAULT_START_INDEX)) {
            return message.subSequence(
                    message.indexOf(DEFAULT_START_INDEX) + 8,
                    message.indexOf(DEFAULT_END_INDEX) - 3
            ).toString();
        } else {
            return message;
        }
    }
}
