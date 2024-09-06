import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.SupportServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SupportServletTest {

    private SupportServlet supportServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;


    @BeforeEach
    public void setUp() throws Exception {
        supportServlet = new SupportServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);
        supportServlet.init();
    }

    @Test
    public void testDoGet() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        supportServlet.service(request, response);
        verify(response).setContentType("text/plain");
        String responseBody = responseWriter.toString();
        assertEquals("All good", responseBody.trim());
    }


    @Test
    public void testDoPost() throws Exception {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("phrase")).thenReturn("All fine");
        supportServlet.service(request, response);
        verify(response).setContentType("text/plain");
        String responseBody = responseWriter.toString();
        assertEquals("All fine phrase added", responseBody.trim());
    }


    @Test
    public void testDoPostWithEmptyParam() throws Exception {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("phrase")).thenReturn("");
        supportServlet.service(request, response);
        verify(response).setContentType("text/plain");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String responseBody = responseWriter.toString().trim();
        assertEquals("Sorry, please try again", responseBody.trim());
    }

}
