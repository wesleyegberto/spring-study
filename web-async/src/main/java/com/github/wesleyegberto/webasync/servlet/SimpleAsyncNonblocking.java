package com.github.wesleyegberto.webasync.servlet;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "simpleAsyncNonblocking", urlPatterns = { "/servlet/async/nonblocking" }, asyncSupported = true, loadOnStartup = 1)
public class SimpleAsyncNonblocking extends HttpServlet {
	private static final long serialVersionUID = 6991745705025517579L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletOutputStream output = resp.getOutputStream();
		AsyncContext asyncCtx = req.startAsync(req, resp);

		output.setWriteListener(new WriteListener() {
			public void onWritePossible() {
				while (output.isReady()) {
					byte[] buffer = "{\"success\":\"true\"}".getBytes();
					if (buffer != null) {
						try {
							// non-blocking
							output.write(buffer);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						asyncCtx.complete();
					}
				}
			}

			public void onError(Throwable throwable) {
				System.out.println("======= Erro " + throwable.getMessage());
			}
		});
	}
}
