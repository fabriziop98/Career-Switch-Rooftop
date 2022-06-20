/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class WebException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WebException(){}

    public WebException(String message){
        super(message);
    }

    public WebException(Throwable cause) {
		super(cause);

	}

	public WebException(String message, Throwable cause) {
		super(message, cause);

	}
    
}