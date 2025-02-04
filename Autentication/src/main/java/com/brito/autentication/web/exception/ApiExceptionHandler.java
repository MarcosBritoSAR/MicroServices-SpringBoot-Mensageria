package com.brito.autentication.web.exception;

import com.brito.autentication.exceptions.AutenticacaoException;
import com.brito.autentication.exceptions.EntityNotFoundException;
import com.brito.autentication.exceptions.SendEmailException;
import com.brito.autentication.exceptions.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpHeaders;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorMessage> accessDeniedException(RuntimeException ex, HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex,
                                                                    HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
        }

        @ExceptionHandler({ UsernameUniqueViolationException.class })
        public ResponseEntity<ErrorMessage> uniqueViolationException(UsernameUniqueViolationException ex,
                                                                     HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
        }


        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
                        WebRequest request) {

                return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorMessage> internalServerErrorException(Exception ex, HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(
                                                request, HttpStatus.INTERNAL_SERVER_ERROR,
                                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }

        @ExceptionHandler(AutenticacaoException.class)
        public ResponseEntity<ErrorMessage> autenticacaoException(AutenticacaoException ex,
                                                                  HttpServletRequest request) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));

        }

        @ExceptionHandler(SendEmailException.class)
        public ResponseEntity<ErrorMessage> sendEmailException(SendEmailException ex,
                                                                  HttpServletRequest request) {

                return ResponseEntity.status(HttpStatus.MULTI_STATUS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));

        }
}
