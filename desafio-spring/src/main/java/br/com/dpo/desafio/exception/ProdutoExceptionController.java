package br.com.dpo.desafio.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ProdutoExceptionController {
	
	@ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {
        
		ErrorResponse errorResponse = new ErrorResponse(ex, HttpStatus.BAD_REQUEST.value());
        
        if(ex instanceof MethodArgumentNotValidException ) {
        	BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
            List<FieldError> fieldErrors = result.getFieldErrors();
            List<String> stringErrors = new ArrayList<String>();
            for (FieldError fieldError : fieldErrors)
            	stringErrors.add(fieldError.getDefaultMessage());
            	
            errorResponse.setMessage(stringErrors.toString());
        }
        
        return new ResponseEntity<Object>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
