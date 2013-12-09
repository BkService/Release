package juniors.server.ext.web.xshell;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserCommand implements ICommand {

	private static final String manual = "	user - command for managed users profiles (testing)<br><br>" +
			"	SYNTAX<br>" +
			"		user &ltcommand&gt [-login login] [-passwd password] [-n count] [-alias name]<br>" +
			"			[-offset offset] [-visa number] [-flags]<br><br>" +
			"	OPTIONS<br>" +
			"		&ltcommand&gt - action on profile. &ltcommand&gt can be<br>" +
			"			add - for add user(s) to system<br>" +
			"			remove - for remove user(s) from system<br>" +
			"			update - for update user<br>" +
			"		-login login - parameter for set login for new user. Not necessarily if set parameter '-alias'. Deault value = 'junior'<br>" +
			"		-passwd password - set password for new user. Not necessarily if set parameter '-alias'. Default value = 'junior'<br>" +
			"		-n count - set count users, which will be created. Necessarily set '-alias' if set '-n' Default value = 1<br>" +
			"		-alias name - set prefix of login, name, lastname and password for new user(s). Login include<br>" +
			"				next parts: prefix + current number in count. Count set in option -n. Default value = 'junior'<br>" +
			"		-offset offset - value of first postfix of name. Default value = 0.<br>" +
			"		-visa number - set number of visa. Defaul value = '0000000000000000'<br>" +
			"		-flags - options for generate users profiles. flags:<br>" +
			"			l - print results to log<br>" +
			"			s - print results to screen<br>" +
			"			f - not print messages about errors<br>" +
			"			r - rundom generated unique visa nubmer<br>" +
			"			u - update user data if user exists (command add only)<br><br>" +
			"	EXAMPLES<br>" +
			"		user add -login olivia -passwd thissisolivia -rls	<br>" +
			"				:created user with login olivia and password thisisolivia. <br>" +
			"				Number visa generate automatically, print results to log and screen.<br>" +
			"		user add -n 1000 -alias someuser -offset 15 -sfru<br>" +
			"				:created 1000 users with logins & passwords from someuser00015 to someuser01015. <br>" +
			"				Print results to log nd screen. Visa number generated automatically and if users already exists, then it replace on new user.";
	
	private String login = "junior";
	private String passwd = "junior";
	private int count = 1;
	private String alias = "junior";
	private int offset = 0;
	private String visaNumber = "0000000000000000";
	
	private boolean printToLog = false;
	private boolean printToScreen = false;
	private boolean supressWarnings = false;
	private boolean rundomGenVisa = false;
	private boolean updateUser = false;
	
	private String result = "";
	private String warning = "";
	private String error = "";
	
	@Override
	public String getName() {
		return "user";
	}

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res,
			String... args) {
		
		
		return "Command 'user' already not used";
	}

	@Override
	public String getMan() {
		return manual;
	}
	
	private void printWarning(String warningMsg) {
		if(!this.supressWarnings)
			this.warning += "<font color=\"#FF8585\"> WARNING:" + warningMsg + "</font><br>";
		if(this.printToLog){
			/* print message to log */
		}
	}
	
	private void printError(String errMsg) {
		if(this.printToLog){
			/* print message to log */
		}
		this.error += "<font color=\"#FF2020\"> ERROR:" + errMsg + "</font><br>";
	}
	
	private boolean parseArguments(String ... args) {
		boolean validateArgs = true;
		int i;
		for(i = 0; i < args.length; ++i){
			if(args[i].equals("-login")) {
				if(args.length < i + 2) {
					printError("Invalid argument: " + args[i]);
					return false;
				}
				if(args[i+1].length() < 3){
					printError("Login must be more 2 chars");
					return false;
				}
				this.login = args[i+1];
				i++;
				continue;
			}
			if(args[i].equals("-passwd")) {
				if(args.length < i + 2) {
					printError("Invalid argument: " + args[i]);
					return false;
				}
				if(args[i+1].length() < 6){
					printError("Password must be more 5 chars");
					return false;
				}
				this.passwd = args[i+1];
				i++;
				continue;
			}
			if(args[i].equals("-alias")) {
				if(args.length < i + 2) {
					printError("Invalid argument: " + args[i]);
					return false;
				}
				this.alias = args[i+1];
				i++;
				continue;
			}
			if(args[i].equals("-n")) {
				if(args.length < i + 2) {
					printError("Invalid argument: " + args[i]);
					return false;
				}
				int value = 1;
				try {
					value = Integer.parseInt(args[i+1]);
				} catch (NumberFormatException ex) {
					printWarning("value " + args[i+1] + " not carry. using default value 1");
					i++;
					continue;
				}
				if(value <= 1) {
					printWarning("value " + value + " must be more 1. using default value 1");
				} else { 
					this.count = value; 
				}
				i++;
				continue;
			}
			if(args[i].equals("-offset")) {
				if(args.length < i + 2) {
					printError("Invalid argument: " + args[i]);
					return false;
				}
				int value = 0;
				try {
					value = Integer.parseInt(args[i+1]);
				} catch (NumberFormatException ex) {
					printWarning("value " + args[i+1] + " not carry. using default value 0");
					i++;
					continue;
				}
				if(value <= 0) {
					printWarning("value " + value + " must be more 0. using default value 1");
				} else { 
					this.offset= value; 
				}
				i++;
				continue;
			}
			if(args[i].equals("-visa")) {
				if(args.length < i + 2) {
					printError("Invalid argument: " + args[i]);
					return false;
				}
				if(!Pattern.matches("^[0-9]{16}$", args[i+1])) {
					printWarning("visa '" + args[i+1] + "' must be 16 digits. using default value");
				} else {
					this.visaNumber= args[i+1];
				}
				i++;
				continue;
			}
			if(isFlags(args[i])) {
				continue;
			}
			printError("Invalid argument: " + args[i]);
			return false;
		}
		
		return validateArgs;
	}
	
	private boolean isFlags(String arg) {
		for(int i = 1; i < arg.length() - 1; ++i) {
			char c = arg.charAt(i);
			if(c == 'l') {
				this.printToLog = true;
				continue;
			}
			if(c =='s') {
				this.printToScreen = true;
				continue;
			}
			if(c == 'f') {
				this.supressWarnings = true;
			}
			if(c == 'r') {
				this.rundomGenVisa = true;
				continue;
			}
			if(c == 'u') {
				this.updateUser = true;
				continue;
			}
			printError("unknown flag: '" + c + "'");
			return false;
		}
		return true;
	}

	@Override
	public String getShortDescription() {
		return "command not used. see 'rm'";
	}
	
	

}
