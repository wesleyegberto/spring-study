package com.wesleyegberto.modulithevents;

public class Logger {
	public static void log(String message) {
		System.out.printf("[%s] %s%n", Thread.currentThread().getName(), message);
	}
}
