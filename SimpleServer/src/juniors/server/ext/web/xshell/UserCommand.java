package juniors.server.ext.web.xshell;

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
			"			update - for update user's<br>" +
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
	
	
	@Override
	public String getName() {
		return "user";
	}

	@Override
	public String action(HttpServletRequest req, HttpServletResponse res,
			String... args) {
		return "Command 'user' in development";
	}

	@Override
	public String getMan() {
		return manual;
	}

}
