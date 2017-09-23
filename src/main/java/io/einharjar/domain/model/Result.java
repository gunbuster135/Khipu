package io.einharjar.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Data
@Accessors(fluent = true)
public class Result<T> {
    @JsonProperty("data")
    private Optional<T> result;
    private Status status;
    private String message;

    public enum Status {
        SUCCESFUL,
        FAILED,
        NOT_FOUND,
        ERROR;

        public HttpStatus getHttpStatus(){
            switch (this){
                case SUCCESFUL: return HttpStatus.OK;
                case FAILED: return HttpStatus.BAD_REQUEST;
                case NOT_FOUND: return HttpStatus.NO_CONTENT;
                case ERROR: return HttpStatus.BAD_REQUEST;
                default: return HttpStatus.OK;
            }
        }
    }
}
