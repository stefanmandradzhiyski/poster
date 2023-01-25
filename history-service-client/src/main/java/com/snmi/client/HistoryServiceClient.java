package com.snmi.client;

import com.snmi.dto.BaseResponse;
import com.snmi.dto.HistoryDTO;
import com.snmi.enums.HistoryType;
import com.snmi.util.BaseRESTTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.snmi.constants.PortGlobalConstants.HISTORY_SERVER_PORT;

@Service
public class HistoryServiceClient extends BaseClient {

    public HistoryServiceClient() {
        setPort(HISTORY_SERVER_PORT);
        setBaseURL("api/v1/history");
    }

    public BaseResponse<List<HistoryDTO>> findByUsernameAndType(final String token, final String username, final HistoryType type) {
        return BaseRESTTemplate.get(token, getBaseURL(), username, Map.of("type", type), new ParameterizedTypeReference<>(){});
    }

}
