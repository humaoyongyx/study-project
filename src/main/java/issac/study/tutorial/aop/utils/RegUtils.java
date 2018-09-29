package issac.study.tutorial.aop.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by issac.hu on 2018/9/29.
 */
public class RegUtils {

    public static boolean isMatch(String pattern, String line) {
        if (StringUtils.isAnyBlank(pattern, line)) {
            return false;
        }
        if (Pattern.matches(pattern, line)) {
            return true;
        }
        return false;
    }
}
