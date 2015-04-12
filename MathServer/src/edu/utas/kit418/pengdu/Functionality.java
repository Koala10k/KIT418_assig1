package edu.utas.kit418.pengdu;

public class Functionality {

	private double sqrt(double x) {
		return Math.sqrt(x);
	}

	private double sin(double x) {
		return Math.sin(x);
	}

	private double cos(double x) {
		return Math.cos(x);
	}

	private double tan(double x) {
		return Math.tan(x);
	}

	private double log(double x) {
		return Math.log(x);
	}

	private boolean prime(int x) {
		return isPrime(x);
	}

	private boolean isPrime(int n) {
		if (n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;

	}

	public synchronized String calculate(String opt, String val) {
		String answer;
		double dval = Double.parseDouble(val);
		switch(opt){
		case "sqrt":
			answer = String.valueOf(sqrt(dval));
			break;
		case "sin":
			answer = String.valueOf(sin(dval));
			break;
		case "cos":
			answer = String.valueOf(cos(dval));
			break;
		case "tan":
			answer = String.valueOf(tan(dval));
			break;
		case "log":
			answer = String.valueOf(log(dval));
			break;
		case "prime":
			answer = String.valueOf(prime((int) dval));
			break;
		default:
			answer = "Error!";	
		}
		return answer;
	}
}
