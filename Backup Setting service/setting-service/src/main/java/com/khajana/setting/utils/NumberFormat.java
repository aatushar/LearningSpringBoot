package com.khajana.setting.utils;

import java.text.DecimalFormat;

public class NumberFormat {

    public static Double get2DigitDecimal(Double data) {
        try {
            // return new DecimalFormat("##.##").format(data);
            DecimalFormat decfor = new DecimalFormat("0.00");
            return Double.parseDouble(decfor.format(data));
        } catch (Exception e) {
            return 0.0;
        }
    }

//public static Double get2DigitDecimal(Double data) {
//	try {
//		DecimalFormat decfor = new DecimalFormat("0.00");
//		decfor.setMinimumFractionDigits(2); // Ensure at least 2 digits after the decimal point
//		decfor.setMaximumFractionDigits(2); // Ensure at most 2 digits after the decimal point
//		return Double.parseDouble(decfor.format(data));
//	} catch (Exception e) {
//		return 0.0;
//	}
//}
//public static Double get2DigitDecimal(Double data) {
//	try {
//		DecimalFormat decfor = new DecimalFormat("#0.00");
//		return Double.parseDouble(decfor.format(data).replace(",", ".")); // Ensure the correct decimal separator is used
//	} catch (Exception e) {
//		return 0.0;
//	}
//}
//public static String formatDecimal(Double value) {
//	// Format the value with exactly two digits after the decimal point
//	return String.format(Locale.US, "%.2f", value);
//}

}
