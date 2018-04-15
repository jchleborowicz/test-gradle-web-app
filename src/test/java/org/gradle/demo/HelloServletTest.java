package org.gradle.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HelloServletTest {

    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;
    @Mock
    private RequestDispatcher requestDispatcherMock;

    private final HelloServlet helloServlet = new HelloServlet();

    @Test
    public void doGet() throws IOException {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);

        when(responseMock.getWriter()).thenReturn(printWriter);

        helloServlet.doGet(requestMock, responseMock);

        assertThat(stringWriter.toString()).isEqualTo("Hello, World!");
    }

    @Test
    public void doPostWithoutName() throws ServletException, IOException {
        when(requestMock.getRequestDispatcher("response.jsp"))
                .thenReturn(requestDispatcherMock);

        helloServlet.doPost(requestMock, responseMock);

        verify(requestMock).setAttribute("user", "World");
        verify(requestDispatcherMock).forward(requestMock, responseMock);
    }

    @Test
    public void doPostWithName() throws ServletException, IOException {
        when(requestMock.getParameter("name")).thenReturn("Dolly");
        when(requestMock.getRequestDispatcher("response.jsp"))
                .thenReturn(requestDispatcherMock);

        helloServlet.doPost(requestMock, responseMock);

        verify(requestMock).setAttribute("user", "Dolly");
        verify(requestDispatcherMock).forward(requestMock, responseMock);
    }
}
