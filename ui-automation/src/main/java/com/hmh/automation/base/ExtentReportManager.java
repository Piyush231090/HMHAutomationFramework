package com.hmh.automation.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
	private static ExtentReports extent;
	private static ExtentTest test;

	// Initialize Extent Report
	public static ExtentReports getInstance() {
		if (extent == null) {
			String reportPath = "target/extent-reports/extent-report.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			extent = new ExtentReports();
			extent.attachReporter(spark);
		}
		return extent;
	}

	// Create a test entry in Extent Report
	public static ExtentTest createTest(String testName) {
		test = getInstance().createTest(testName);
		return test;
	}

	// Flush the report (Save changes)
	public static void flushReport() {
		if (extent != null) {
			extent.flush();
		}
	}
}
