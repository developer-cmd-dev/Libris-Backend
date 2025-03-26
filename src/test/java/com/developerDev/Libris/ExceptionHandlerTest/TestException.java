package com.developerDev.Libris.ExceptionHandlerTest;

import com.developerDev.Libris.ExceptionHandler.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class TestException {


    @Disabled
    @Test
    public void testCustomException(){
        Assertions.assertNotNull("this");
        throw new CustomException("This is test exception", HttpStatus.BAD_REQUEST);
    }


}
