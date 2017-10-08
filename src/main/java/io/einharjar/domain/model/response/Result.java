package io.einharjar.domain.model.response;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    @JsonProperty("data")
    private Optional<T> result;
    @JsonProperty("info")
    private Info info = new Info();



    public ResponseEntity<Result<T>> getResponseEntity(){
        if(info == null || info.status() == null){
            return new ResponseEntity<>(this, HttpStatus.OK);
        }
        return new ResponseEntity<>(this, info.status().getHttpStatus());
    }
}
