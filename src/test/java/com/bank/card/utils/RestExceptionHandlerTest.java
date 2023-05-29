package com.bank.card.utils;

import com.bank.card.domain.error.model.ConflictException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        request.setRequestURI("");
    }

    @Test
    void handleConflictExceptions() {
        var conflictException = mock(ConflictException.class);
        Assertions.assertDoesNotThrow(() -> restExceptionHandler.handleConflictExceptions(conflictException, request));
    }

    @Test
    void handleAllExceptions() {
        Assertions.assertDoesNotThrow(() -> restExceptionHandler.handleAllExceptions(new Exception(), request));
    }

    @Test
    void handleArgumentNotValidExceptions() {
        var parameter = mock(MethodParameter.class);
        var bindingResult = mock(BindingResult.class);
        var methodArgumentNotValidException =
                new MethodArgumentNotValidException(parameter, bindingResult);
        Assertions.assertDoesNotThrow(() ->
                restExceptionHandler.handleArgumentNotValidExceptions(methodArgumentNotValidException, request));
    }

    @Test
    void handlehandleMethodArgumentTypeMismatchException() {
        var exception = Mockito.mock(MethodArgumentTypeMismatchException.class);
        var throwableMock = mock(Throwable.class);
        when(throwableMock.getCause()).thenReturn(throwableMock);
        when(exception.getCause()).thenReturn(throwableMock);

        Assertions.assertDoesNotThrow(() ->
                restExceptionHandler.handleMethodArgumentTypeMismatchException(exception, request));
    }


}
