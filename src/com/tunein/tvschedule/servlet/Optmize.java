package com.tunein.tvschedule.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tunein.tvschedule.TVSchedule;
import com.tunein.tvschedule.TVTimePeriod;
import com.tunein.tvschedule.parser.TVPeriodParser;

/**
 * Servlet implementation class Optmize
 */
@WebServlet("/optmize")
public class Optmize extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Optmize() {
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("content");
		
		// Parse the input periods
		TreeSet<TVTimePeriod> periods = TVPeriodParser.parsePeriods(new ByteArrayInputStream(content.getBytes()));
		
		// Optmize periods
		List<TVTimePeriod> groups = TVSchedule.optmize(new ArrayList<TVTimePeriod>(periods));
		
		
		request.setAttribute("optmizeds", TVTimePeriod.format(groups));

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

}
