package com.qa.util;

public class CustomReport {
	private String methodName;
	private String result;
	private String actualvalue;
	private String expactedValue;
	private String screenshot;
	private int serial;
	private String startTime;

	public CustomReport(
			String MethodName,
			String Result,
			String Actualvalue,
			String ExpectedValue,
			String Screenshot,
			int Serial,
			String StartTime
	) {
		methodName = MethodName;
		result = Result;
		actualvalue = Actualvalue;
		expactedValue = ExpectedValue;
		screenshot = Screenshot;
		serial = Serial;
		startTime = StartTime;
	}
	
	public String htmlCodeBlock() {
		String template = "<tr> 	\n" + 
				"    <td>"+ serial +"</td> 	\n" + 
				"    <td > "+ methodName +"</td> 	\n" + 
				"    <td >" + expactedValue + "</td> 	\n" + 
				"    <td>" + result + "</td> 	\n" + 
				"    <td> </td> 	\n" + 
				"    <td>"+ startTime +"</td> 	\n" + 
				"    <td> 	\n" + 
				"        <a href='" + screenshot + "' target='_blank'> 	\n" + 
				"            <img class='screenshot' src='"+ screenshot +"' alt='Screenshot at available at the designated path '>\n" + 
				"        </a>\n" + 
				"    </td>\n" + 
				"</tr>";
		return template;
	}
	
	public String toString() {
		return "MethodName : "+ methodName + ", Result : "+ result +", Actual value : " +
					actualvalue + ", Expected value : "+ expactedValue + ", Screenshot : "+
						screenshot;
	}
}
