package juniors.server.ext.emulator;

import java.io.Serializable;

/**
 * 
 * @description Сообщение 'MakeBet'
 * Содержит всю необходимую информацию
 * для создания ставки робота
 * 
 * @author alex
 * 
 * */
public class MBMessage implements Serializable {

	private static final long serialVersionUID = 321326L;
	
	int idOutcome;
	int summ;
	String userLogin;
	String userPasswd;
	
	public MBMessage(String login, String passwd, int summ, int idOutcome) {
		this.idOutcome = idOutcome;
		this.summ = summ;
		this.userLogin = login;
		this.userPasswd = passwd;
	}
	
}
