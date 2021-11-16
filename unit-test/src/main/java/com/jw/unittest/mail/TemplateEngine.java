package com.jw.unittest.mail;

import com.jw.unittest.mail.exception.MissingValueException;

import java.util.HashMap;
import java.util.Map;

/**
 * 替换指定变量的值
 * @author jw
 * @date 2021/11/15 15:07
 */
public class TemplateEngine {

    private Map<String, String> variables;

    private String templateText;

    public TemplateEngine(String templateText) {
        this.templateText = templateText;
        this.variables = new HashMap<>();
    }

    public void set(String key, String value) {
        this.variables.put(key, value);
    }

    public String evaluate() {
        String result = replaceTemplate(templateText);
        checkMissingValue(result);
        return result;
    }

    private String replaceTemplate(String text) {
        String result = text;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replaceAll("\\$\\{" + entry.getKey() + "\\}", entry.getValue());
        }
        return result;
    }

    private void checkMissingValue(String text) {
        if (text.matches(".*\\$\\{.+\\}.*")) {
            throw new MissingValueException();
        }
    }
}
