package com.github.kuzznya.titantest.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculationScriptPreprocessor {

    private final String exitOnTimeoutFunction;

    public static final String EXECUTION_TIMEOUT_ERROR_MESSAGE = "EXECUTION TIMEOUT";

    public CalculationScriptPreprocessor(int timeoutMillis) {
        exitOnTimeoutFunction = "function exitOnTimeout(start) {\n" +
                "if (new Date().getTime() > start + " + timeoutMillis + ")\n" +
                "throw new Error('" + EXECUTION_TIMEOUT_ERROR_MESSAGE + "');\n" +
                "return false; }";
    }

    private String preprocessWhileLoops(String scriptCode) {
        Pattern pattern = Pattern.compile("while\\s*\\(");
        Matcher matcher = pattern.matcher(scriptCode);
        return matcher.replaceAll(matchResult ->
                matchResult
                        .group()
                        .replaceFirst("while\\s*\\(", "while (!exitOnTimeout(START_) && ")
        );
    }

    private String preprocessForLoops(String scriptCode) {
        Pattern pattern = Pattern.compile("for\\s*\\([^;]*?;");
        Matcher matcher = pattern.matcher(scriptCode);
        return matcher
                .replaceAll(matchResult -> matchResult.group() + "!exitOnTimeout(START_) &&")
                .replaceAll("&&\\s*;", ";");

    }

    public String preprocessScript(String code) {
        String scriptCode = code
                .replaceAll("\\r", "\n")
                .replaceAll("\\t", " ");

        scriptCode = "var START_ = new Date().getTime();\n" +
                scriptCode.replaceAll("\\{", "{ exitOnTimeout(START_);\n");

        scriptCode = preprocessWhileLoops(scriptCode);
        scriptCode = preprocessForLoops(scriptCode);

        return exitOnTimeoutFunction + "\nfunction test(idx) { " + scriptCode + " }";
    }
}
