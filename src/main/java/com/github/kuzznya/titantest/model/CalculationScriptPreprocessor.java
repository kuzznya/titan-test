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

    public String preprocessScript(String code) {
        String scriptCode = code
                .replaceAll("\\r", "\n")
                .replaceAll(";;", ";")
                .replaceAll("\\t", " ");

        scriptCode = "var START_ = new Date().getTime();\n" + scriptCode;

        Pattern pattern = Pattern.compile("while\\s*\\(.*\\)");
        Matcher matcher = pattern.matcher(scriptCode);
        scriptCode = matcher.replaceAll(matchResult ->
                matchResult.group()
                        .replaceFirst("while",
                                "while (!exitOnTimeout(START_) && ")
                        .concat(")")
        );

        return exitOnTimeoutFunction + "\nfunction test(idx) { " + scriptCode + " }";
    }
}
