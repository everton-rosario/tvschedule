package com.tunein.tvschedule.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tunein.tvschedule.TVSchedule;
import com.tunein.tvschedule.TVTimePeriod;
import com.tunein.tvschedule.parser.TVPeriodParser;
import com.tunein.tvschedule.test.TVSchedulerGenerator;

/**
 * Servlet implementation class Optmize
 */
@WebServlet("/optmize")
public class Optmize extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("content");
		
		// Parse the input periods
		TreeSet<TVTimePeriod> periods = TVPeriodParser.parsePeriods(new ByteArrayInputStream(content.getBytes()));
		
		// Optmize periods
		List<TVTimePeriod> groups = TVSchedule.optmize(new ArrayList<TVTimePeriod>(periods));
		
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        Gson gson = new Gson();
		out.write(gson.toJson(groups));

		out.flush();
		out.close();
	}

}
