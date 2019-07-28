package ScientificCalculator.ScientificCalculator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.jacob.com.LibraryLoader;

import autoitx4java.AutoItX;

public class SciCalculator {
	final String JACOB_DLL_TO_USE = System.getProperty("sun.arch.data.model").contains("32") ? "jacob-1.18-x86.dll"
			: "jacob-1.18-x64.dll";
	final String APPLICATION_TITLE = "Calculator";
	final String APPLICATION = "calc.exe";

	private AutoItX control;
	private Map<Integer, String> calcNumPad_ObjectRepository;
	private Map<String, String> calcOperPad_ObjectRepository;
	{
		// Calculator Numbers
		calcNumPad_ObjectRepository = new HashMap<Integer, String>();
		calcNumPad_ObjectRepository.put(0, "130");
		calcNumPad_ObjectRepository.put(1, "131");
		calcNumPad_ObjectRepository.put(2, "132");
		calcNumPad_ObjectRepository.put(3, "133");
		calcNumPad_ObjectRepository.put(4, "134");
		calcNumPad_ObjectRepository.put(5, "135");
		calcNumPad_ObjectRepository.put(6, "136");
		calcNumPad_ObjectRepository.put(7, "137");
		calcNumPad_ObjectRepository.put(8, "138");
		calcNumPad_ObjectRepository.put(9, "139");

		// Calculator Operator
		calcOperPad_ObjectRepository = new HashMap<String, String>();
		calcOperPad_ObjectRepository.put("/", "91");
		calcOperPad_ObjectRepository.put("*", "92");
		calcOperPad_ObjectRepository.put("+", "93");
		calcOperPad_ObjectRepository.put("-", "94");
		calcOperPad_ObjectRepository.put("=", "121");
		calcOperPad_ObjectRepository.put("x2", "111");

		File file = new File(System.getProperty("user.dir") + "\\lib", JACOB_DLL_TO_USE);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		control = new AutoItX();
	}

	public static void main(String[] args) throws InterruptedException {
		Scanner Calc = new Scanner(System.in);
		SciCalculator ct = new SciCalculator();
		int method;
		System.out.println("Enter the method number");
		method = Calc.nextInt();
		method = (method == 2) ? ct.trigonometry(Calc, ct) : ct.operation(Calc, ct);
		ct.control.winClose("Calculator");
	}

	// This method is used to Calculation of two number
	public int operation(Scanner Calc, SciCalculator ct) throws InterruptedException {

		System.out.println("Enter the first number: ");
		int n1 = Calc.nextInt();

		System.out.println("Select the Second number: ");
		int n2 = Calc.nextInt();

		System.out.println("Enter the Sign:");
		String operator = Calc.next().toString();

		ct.control.run("calc.exe");
		ct.control.winActivate("Calculator");
		ct.control.winWaitActive("Calculator");

		performOperation("clear");
		clickNumber(n1);
		performOperation(operator);
		clickNumber(n2);
		performOperation("=");
		return Integer.parseInt(getResult().trim());
	}

	// This method is used to trigonometry function
	public int trigonometry(Scanner Calc, SciCalculator ct) throws NumberFormatException, InterruptedException {

		System.out.println("Enter the number: ");
		int n1 = Calc.nextInt();
		
		//Enter the trigonometry Sign like x2
		System.out.println("Enter the trigonometry Sign:");
		String operator = Calc.next().toString();

		ct.control.run("calc.exe");
		ct.control.winActivate("Calculator");
		ct.control.winWaitActive("Calculator");

		performOperation("clear");
		clickNumber(n1);
		for (int i = 0; i < 3; i++) {
			performOperation(operator);
		}
		performOperation("=");
		return Integer.parseInt(getResult().trim());

	}

	private String getResult() throws InterruptedException {
		Thread.sleep(1000);
		return control.winGetTitle(APPLICATION_TITLE);
	}

	// This method is used to click on number
	private void clickNumber(int number) throws InterruptedException {
		String sNumber = String.valueOf(number);
		for (int i = 0; i < sNumber.length(); i++) {
			control.controlClick(APPLICATION_TITLE, "",
					calcNumPad_ObjectRepository.get(Character.digit(sNumber.charAt(i), 10)));
			Thread.sleep(1000);
		}

	}

	// This method is used to particular operation
	private void performOperation(String controlID) throws InterruptedException {
		control.controlClick(APPLICATION_TITLE, "", calcOperPad_ObjectRepository.get(controlID));
		Thread.sleep(1000);
	}
}
