package ee.ttu.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.tidy.Tidy;

public class Main {

	public static void main(String[] args) {
		String text = "ll ee";

		System.out.println(text.matches("\\s{2,}"));
		System.out.println(text);
		System.out.println(text.replaceAll("\\s{2,}", " ").trim());
	}
	
}
