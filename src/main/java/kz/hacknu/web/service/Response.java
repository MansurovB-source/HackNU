package kz.hacknu.web.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private int status;

    private String response;

    private String errorResponse;

    public Response(int status, String response, String errorResponse){
        this.status = status;
        this.response = response;
        this.errorResponse = errorResponse;
    }

}
