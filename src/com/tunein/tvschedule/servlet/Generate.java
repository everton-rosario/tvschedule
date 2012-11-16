package com.tunein.tvschedule.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tunein.tvschedule.TVTimePeriod;
import com.tunein.tvschedule.test.TVSchedulerGenerator;

/**
 * Servlet implementation class Optmize
 */
@WebServlet("/generate")
public class Generate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Generate() {
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        List<TVTimePeriod> week = TVSchedulerGenerator.generateFullWeek();
        for (TVTimePeriod period : week) {
            out.write(period.getOriginalRepresentation());
            out.write("\n\n");
        }
        out.flush();
        out.close();
	}

}
