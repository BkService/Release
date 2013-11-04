package juniors.server.test.xshell;

import juniors.server.ext.web.xshell.CommandManager;
import juniors.server.ext.web.xshell.ICommand;
import static org.junit.Assert.*;

import org.junit.Test;

public class CommandManagerTest {
	
	@Test
	public void shouldFindCommand() {
		String testData = "man";
		ICommand cmd = CommandManager.getInstance().getCommand(testData);
		assertNotNull(cmd);
	}

}
