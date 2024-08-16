package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Setup implements ITestListener {

    private static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

//    public void onStart(ITestContext context) {
//        String fileName = ExtentReportManager.getReportNameWithTimestamp();
//        String fullReportPath = System.getProperty("user.dir") + "\\reports\\" + fileName;
//        extentReports = ExtentReportManager.createInstance(fullReportPath, "Test API Automation Report", "Test Execution Report");
//    }

    @Override
    public void onFinish(ITestContext context) {
        if(extentReports != null) {
            extentReports.flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        if (extentReports == null) {
            throw new IllegalStateException("ExtentReports instance is not initialized.");
        }
        ExtentTest test = extentReports.createTest("Test Name " + result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
        extentTest.set(test);
        System.out.println("extentTest" + extentTest);
    }
}
