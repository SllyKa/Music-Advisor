package commands.command;

import commands.receiver.Actions;

public class ExitCommand extends Command {
    public ExitCommand(Actions actions) {
        super(actions, "goodbye!");
    }

    @Override
    public void execute() {
        actions.exitAction(header);
    }
}
