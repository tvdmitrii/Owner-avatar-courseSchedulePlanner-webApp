package com.turygin.servlet;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseDTO;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(
        name = "courseServlet",
        urlPatterns = { "/courseServlet" }
)
public class CourseServlet extends HttpServlet {

    /** Empty constructor. */
    public CourseServlet() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RestClient client = new RestClient();
        List<CourseDTO> courses = client.getAllCourse();
        request.setAttribute("courses", courses);

        String url = "/index.jsp";

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
