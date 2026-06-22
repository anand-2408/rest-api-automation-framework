package com.sdet.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Path;

public class ExtentReportListener implements ITestListener {
    private static final ExtentReports REPORT = createReport();
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    private static ExtentReports createReport() {
        try {
            Files.createDirectories(Path.of("target", "reports"));
        } catch (Exception exception) {
            throw new IllegalStateException("Could not create report directory", exception);
        }
        ExtentSparkReporter spark = new ExtentSparkReporter("target/reports/api-test-report.html");
        spark.config().setDocumentTitle("REST API Automation Results");
        spark.config().setReportName("ReqRes API Test Suite");

        ExtentReports report = new ExtentReports();
        report.attachReporter(spark);
        report.setSystemInfo("Framework", "Rest Assured + TestNG");
        report.setSystemInfo("Java", System.getProperty("java.version"));
        return report;
    }

    @Override
    public void onTestStart(ITestResult result) {
        TEST.set(REPORT.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (TEST.get() != null) {
            TEST.get().pass("Test passed");
        }
        TEST.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (TEST.get() != null) {
            TEST.get().fail(result.getThrowable());
        }
        TEST.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (TEST.get() != null) {
            TEST.get().skip(result.getThrowable());
        }
        TEST.remove();
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        REPORT.flush();
    }
}
