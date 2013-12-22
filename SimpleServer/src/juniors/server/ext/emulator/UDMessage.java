package juniors.server.ext.emulator;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @description Сообщение 'User Data'
 * Содержит всю необходимую информацию
 * о пользователях роботов. От указанных тут 
 * логинов и паролей робот делает ставки.
 * 
 * @author alex
 * 
 * */
public class UDMessage implements Serializable {

	private static final long serialVersionUID = 321321L;
	
	private Map<String, String> users;// login & password
	
	public Map<String, String> getUsers() {
		return this.users;
	}
	
	public void setUsers(Map<String, String> usrs) {
		this.users = usrs;
	}
	
}
