package io.zestledger.platform.security;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class SecurityHeadersFilterTest {

    private final SecurityHeadersFilter filter = new SecurityHeadersFilter();

    @Test
    void shouldSetBaselineHeadersWhenAbsent() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(response.containsHeader("X-Content-Type-Options")).thenReturn(false);
        when(response.containsHeader("X-Frame-Options")).thenReturn(false);
        when(response.containsHeader("Referrer-Policy")).thenReturn(false);

        filter.doFilterInternal(request, response, chain);

        verify(response).setHeader("X-Content-Type-Options", "nosniff");
        verify(response).setHeader("X-Frame-Options", "DENY");
        verify(response).setHeader("Referrer-Policy", "no-referrer");

        verify(chain).doFilter(request, response);

        InOrder inOrder = inOrder(response, chain);
        inOrder.verify(response).setHeader("X-Content-Type-Options", "nosniff");
        inOrder.verify(response).setHeader("X-Frame-Options", "DENY");
        inOrder.verify(response).setHeader("Referrer-Policy", "no-referrer");
        inOrder.verify(chain).doFilter(request, response);
    }

    @Test
    void shouldNotOverrideHeadersWhenAlreadyPresent() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(response.containsHeader("X-Content-Type-Options")).thenReturn(true);
        when(response.containsHeader("X-Frame-Options")).thenReturn(true);
        when(response.containsHeader("Referrer-Policy")).thenReturn(true);

        filter.doFilterInternal(request, response, chain);

        verify(response, never()).setHeader("X-Content-Type-Options", "nosniff");
        verify(response, never()).setHeader("X-Frame-Options", "DENY");
        verify(response, never()).setHeader("Referrer-Policy", "no-referrer");
        verify(chain).doFilter(request, response);
    }
}
