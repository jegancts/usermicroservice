package com.userdetail.microservice.handler;

import com.userdetail.microservice.dto.ErrorDTO;
import com.userdetail.microservice.exceptions.UserIdvalidationException;
import com.userdetail.microservice.exceptions.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public List<String> generateUserNotFoundException(UserNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return errors;
    }

//    @ExceptionHandler(NullPointerException.class)
//    public ResponseEntity<ErrorDTO> generateNotFoundException(NullPointerException ex) {
//        ErrorDTO errorDTO = new ErrorDTO();
//        errorDTO.setMessage(ex.getMessage());
//        errorDTO.setStatus("419");
//        errorDTO.setTime(new Date().toString());
//
//        return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.CONFLICT);
//    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserIdvalidationException.class)
    public ResponseEntity<ErrorDTO> generateUserIDsNotFoundException(UserIdvalidationException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setStatus("404");
        errorDTO.setTime(new Date().toString());
        return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> generateNotFoundException(NoSuchElementException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setStatus("404");
        errorDTO.setTime(new Date().toString());
        return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.NOT_FOUND);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Map<String, String> handleDuplicatePhoneNo(HttpServletRequest req, DataIntegrityViolationException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Duplicate phone no ","Please enter unique phone no");
        return errors;
    }
}
