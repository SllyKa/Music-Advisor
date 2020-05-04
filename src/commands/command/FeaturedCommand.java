package commands.command;

import commands.receiver.Actions;

public class FeaturedCommand extends Command {

    public FeaturedCommand(Actions actions) {
        super(actions, "featured");
    }

    @Override
    public void execute() {
        if (getAuth()) {
            actions.featuredAction(header);
        }
    }
}
