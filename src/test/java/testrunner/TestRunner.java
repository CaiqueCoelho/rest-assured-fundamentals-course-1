package testrunner;

import org.testng.TestNG;
import reporting.Setup;

import java.util.Arrays;

public class TestRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] { AirlinesTests.class });

        // You can add listeners programmatically
        testng.addListener(new Setup());

        // You can set an XML file to configure tests
        testng.setTestSuites(Arrays.asList("testng.xml"));

        // Run the tests
        testng.run();
    }
}