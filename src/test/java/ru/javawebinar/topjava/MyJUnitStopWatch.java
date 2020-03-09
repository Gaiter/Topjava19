package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyJUnitStopWatch extends Stopwatch {

    private static final Logger log = LoggerFactory.getLogger(MyJUnitStopWatch.class);

    private static Map<String, Long> testsTime = new HashMap<>();

    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        log.info(String.format("Test %s %s, spent %d ms",
                testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
        testsTime.put(testName, TimeUnit.NANOSECONDS.toMillis(nanos));
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
    }

    public static void report() {
        for (String test : testsTime.keySet()) {
            log.info(String.format("Test %s, spent %d ms", test, testsTime.get(test)));
        }
    }
}

