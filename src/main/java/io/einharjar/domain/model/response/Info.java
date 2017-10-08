package io.einharjar.domain.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.UUID;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    @JsonProperty("status")
    private Status status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("request_id")
    private String requestId = UUID.randomUUID().toString();
    @JsonProperty("timestamp")
    private long timestamp = Instant.now().getEpochSecond();

    public enum Status {
        SUCCESFUL,
        FAILED,
        NOT_FOUND,
        NOT_ALLOWED,
        INVALID_AUTHENTICATION,
        ERROR;

        public HttpStatus getHttpStatus(){
            switch (this){
                case SUCCESFUL:              return HttpStatus.OK;
                case FAILED:                 return HttpStatus.BAD_REQUEST;
                case NOT_FOUND:              return HttpStatus.NO_CONTENT;
                case INVALID_AUTHENTICATION: return HttpStatus.BAD_REQUEST;
                case ERROR:                  return HttpStatus.BAD_REQUEST;
                default: return HttpStatus.OK;
            }
        }
    }
}
