package juniors.server.ext.web.xshell;

import java.util.ArrayList;

public class CommandManager {

	private static CommandManager instance;
	
	private static final ArrayList<ICommand> commands;
	
	static {
		commands = new ArrayList<ICommand>() {
			private static final long serialVersionUID = 10101L;
			{
				this.add(new ManCommand());
				this.add(new FeedLoaderCommand());
				this.add(new InfoCommand());
				this.add(new TestCommand());
				this.add(new UserCommand());
				this.add(new EventsCommand());
				this.add(new RobotCommand());
				this.add(new PrintCommand());
				this.add(new WriteCommand());
		}};
		instance = new CommandManager();
	}
	
	public static CommandManager getInstance() {
		return instance;
	}
	
	public ICommand getCommand(String name) {
		for(ICommand cmd : commands) {
			if(cmd.getName().equals(name))
				return cmd;
		}
		return null;
	}
}
