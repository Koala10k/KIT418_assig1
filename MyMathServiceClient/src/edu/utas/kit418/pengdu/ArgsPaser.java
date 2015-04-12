package edu.utas.kit418.pengdu;


public class ArgsPaser {

	private String errReport;
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private static final String PORT_PATTERN = "^([1-5][0-9][0-9][0-9][0-9]|6[0-5][0-5][0-3][0-5]|102[4-9]|10[3-9][0-9]|1[1-9][0-9][0-9]|[2-9][0-9][0-9][0-9])$";
	private static final String OPERATION_PATTERN = "^(sqrt|sin|cos|tan|log|prime)$";
	private static final String DOUBLE_PATTERN = "^([\\d]+|[\\d]+\\.[\\d]+)$";
	public int amountOfArgs;

	public boolean parser(String[] args) {
		amountOfArgs = args.length > 4 ? 4 : args.length;
		if(amountOfArgs == 0) {
			errReport = "No args";
			return true;
		}
		if (0 < amountOfArgs && !args[0].matches(IPADDRESS_PATTERN)) {
			errReport = "<hostname> must be a valid IP address";
			return false;
		}
		if (1 < amountOfArgs && !args[1].matches(PORT_PATTERN)) {
			errReport = "<port> must be a integer ranging from 1024 to 65535";
			return false;
		}
		if (2 < amountOfArgs && !args[2].matches(OPERATION_PATTERN)){
			errReport = "<operation> supports [sqrt|sin|cos|tan|log|prime] only";
			return false;
		}
		if( 3 < amountOfArgs && !args[3].matches(DOUBLE_PATTERN)){
			errReport = "<value> must be a numeric";
			return false;
		}
		return true;
	}

	public void printHelp(){
		MyMathServiceClient.printError(errReport);
		MyMathServiceClient.printUsage("java MyMathServiceClient <hostname> <port> <operation> <value>");
	}
}
