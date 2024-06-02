package com.wesleyegberto.ecommerce.orders.management;

public class OrderNumberGenerator {
	private OrderNumberGenerator() {
	}

	public static String generateOrderNumber() {
		var orderNumber = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			if (i == 4) {
				orderNumber.append("-");
			}
			orderNumber.append((char) (65 + (int) (Math.random() * 26)));
		}
		return orderNumber.toString();
	}
}
