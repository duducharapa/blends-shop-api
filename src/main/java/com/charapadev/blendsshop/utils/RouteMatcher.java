package com.charapadev.blendsshop.utils;

import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

public class RouteMatcher {

    private static boolean comparePaths(String expected, String actual) {
        PathPattern comparator = PathPatternParser.defaultInstance.parse(expected);

        PathContainer pathContainer = PathContainer.parsePath(actual);
        return comparator.matches(pathContainer);
    }

    public static boolean match(Route expected, Route actual) {
        boolean matchMethod = expected.method().equals(actual.method());
        boolean matchPath = comparePaths(expected.path(), actual.path());

        return matchMethod && matchPath;
    }

}
