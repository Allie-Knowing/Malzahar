package com.foreveryone.knowing.security;

import com.foreveryone.knowing.error.ErrorCode;
import com.foreveryone.knowing.error.ErrorResponse;
import com.foreveryone.knowing.error.KnowingException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (KnowingException e) {
            responseError(response, ErrorResponse.of(e.getErrorCode(), e.getMessage()));
        } catch (Exception e) {
            responseError(response, ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, null));
        }
    }

    private void responseError(HttpServletResponse response, ErrorResponse errorResponse) throws IOException {
        response.setStatus(errorResponse.getStatus());
        response.setContentType("application/json");
        response.getWriter().write(errorResponse.toString());
    }

}
