package org.example;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SupportServlet extends HttpServlet {
    List<String> supportPhrases = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        supportPhrases.add("All good");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        Random random = new Random();
        out.println(supportPhrases.get(random.nextInt(0, supportPhrases.size())));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        String phrase = request.getParameter("phrase");
        if(phrase != null && !phrase.trim().isEmpty()) {
            supportPhrases.add(phrase);
            response.getWriter().println(phrase + " phrase added");
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Sorry, please try again");
        }
    }
}
