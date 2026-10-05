package org.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/addData")
public class addstudent extends HttpServlet {

    // ==========================================
    // GET METHOD - DISPLAY FORM
    // ==========================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Add Student</title>");
        out.println("</head>");

        out.println("<body>");

        out.println("<h2>Add Student</h2>");

        out.println("<form action='addData' method='post'>");

        out.println("Name: ");
        out.println("<input type='text' name='name' required>");
        out.println("<br><br>");

        out.println("Branch: ");
        out.println("<input type='text' name='branch' required>");
        out.println("<br><br>");

        out.println("<input type='submit' value='Add Student'>");

        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }


    // ==========================================
    // POST METHOD - INSERT DATA
    // ==========================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String name = req.getParameter("name");
        String branch = req.getParameter("branch");

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        // ==========================================
        // MYSQL DATABASE DETAILS
        // ==========================================

        String url = "jdbc:mysql://localhost:3306/studentdb12";
        String username = "root";
        String password = "manager";

        try {

            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL
            Connection con = DriverManager.getConnection(
                    url,
                    username,
                    password
            );

            // SQL query
            //noinspection SqlNoDataSourceInspection,SqlResolve
            String sql = "INSERT INTO student (name, branch) VALUES (?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, branch);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Success</title>");
                out.println("</head>");

                out.println("<body>");

                out.println("<h2>Student Added Successfully!</h2>");

                out.println("<p>Name: " + name + "</p>");
                out.println("<p>Branch: " + branch + "</p>");

                out.println("<br>");

                out.println("<a href='addData'>Add Another Student</a>");

                out.println("</body>");
                out.println("</html>");

            } else {

                out.println("<h2>Student Not Added</h2>");
            }

            ps.close();
            con.close();

        } catch (ClassNotFoundException e) {

            out.println("<h2>MySQL Driver Not Found</h2>");
            e.printStackTrace(out);

        } catch (SQLException e) {

            out.println("<h2>Database Error</h2>");
            e.printStackTrace(out);
        }
    }
}