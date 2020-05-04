package commands.command;

import commands.receiver.Actions;

public class NewCommand extends Command {

    public NewCommand(Actions actions) {
        super(actions, "new releases");
    }

    @Override
    public void execute() {
        if (getAuth()) {
            actions.newAction(header);
        }
    }
}
