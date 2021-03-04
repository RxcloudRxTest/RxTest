package com.qa.base;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.annotations.MarkupIgnore;

public class MyCustomLog {
	private List<String> names = Arrays.asList("Anshoo", "Extent", "Klov");
    private Object[] favStack = new Object[]{"Java", "C#", "Angular"};
    @MarkupIgnore
    private List<String> ignored = Arrays.asList("Anshoo/Ignore", "Extent/Ignore", "Klov/Ignore");
    private Map<Object, Object> items = new HashMap<Object, Object>() {
        {
            put("Item1", "Value1");
            put("Item2", "Value2");
            put("Item3", "Value3");
        }
    };
}
