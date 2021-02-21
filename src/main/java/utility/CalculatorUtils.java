package utility;

import com.opencsv.CSVWriter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CalculatorUtils {
	public static String fetchExecutableEquation(String equation) throws Exception {

		String executableEquation = "'"+equation+"='.split('').forEach(\n" +
				"  function(item, index){\n" +
				"   var keycode = item.charCodeAt(0);\n" +
				"    document.getElementById('canvas').dispatchEvent(\n" +
				"       new KeyboardEvent(\n" +
				"         'keypress',{which: keycode, keyCode: keycode, bubbles:true}\n" +
				"       )\n" +
				"    )\n" +
				"  }\n" +
				")";

		return executableEquation;
	}

	public static String fetchEquation(String value1, String value2, String operation) throws Exception {

		String equation = "";
		switch (operation) {
			case "addition":
				equation = new StringBuilder().append(value1).append("+").append(value2).toString();
				break;
			case "subtraction":
				equation = new StringBuilder().append(value1).append("-").append(value2).toString();
				break;
			case "division":
				equation = new StringBuilder().append(value1).append("/").append(value2).toString();
				break;
		}

		return equation;
	}

}