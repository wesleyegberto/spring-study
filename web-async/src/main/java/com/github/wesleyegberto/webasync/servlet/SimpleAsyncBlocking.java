package com.github.wesleyegberto.webasync.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executor;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(name = "simpleAsyncBlocking", urlPatterns = { "/servlet/async/blocking" }, asyncSupported = true, loadOnStartup = 1)
public class SimpleAsyncBlocking extends HttpServlet {
	private static final long serialVersionUID = 6991745705025517579L;
	
	@Autowired
	private Executor executor;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OutputStream output = resp.getOutputStream();
		AsyncContext asyncCtx = req.startAsync(req, resp);
		
		Runnable runnable = () -> {
			String json = "{\"success\":\"true\"}";
			try {
				// this will block
				output.write(json.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			// complete and return to client
			asyncCtx.complete();
		};
		// runs in a pooled thread
		executor.execute(runnable);
	}
}
