package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.auth.config.JwtAuthenticationEntryPoint;
import com.deviget.minesweeper.auth.service.impl.JwtUserDetailsService;
import com.deviget.minesweeper.auth.util.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

@WithMockUser(value = "minesweeper-api-core-user")
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc apiMock;

    @MockBean
    protected JwtTokenUtil jwtTokenUtil;

    @MockBean
    protected JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    protected JwtUserDetailsService jwtUserDetailsService;

    protected String mapToJson(final Object obj) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(final String json, final Class<T> clazz)
            throws JsonParseException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper.readValue(json, clazz);
    }
}