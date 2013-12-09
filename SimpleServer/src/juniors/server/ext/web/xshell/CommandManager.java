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
	
	public String[] getCommandsDescriptions() {
		String[] cmds = new String[commands.size() - 1];
		for(int i = 0; i < commands.size() - 1; ++i) {
			cmds[i] = "	" + commands.get(i).getName() + "		- " + commands.get(i).getShortDescription();
		}
		return cmds;
	}
	
	private static String NAME_SHELL = "WebShell (C) Juniors Simple Server";
	private static String VERSION_SHELL = "2.2.0";
	private static String DATE_BUILD = "8 Dec 2013";
	private static String VERSION_BUILD = "00073";
	private static final String AUTHOR = "Pismak Alexey";
	
	public String aboutHTML() {
		return "info: wsh: " + NAME_SHELL + "<br><br>" +
				"	Version: " + VERSION_SHELL + "<br>" +
				"	Build : " + VERSION_BUILD + "<br>" +
				"	Date : " + DATE_BUILD + "<br>" +
				"	Author: " + AUTHOR;
	}
}
