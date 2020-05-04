package commands.command;

import commands.receiver.Actions;

public class CategoriesCommand extends Command {
    public CategoriesCommand(Actions actions) {
        super(actions, "categories");
    }

    @Override
    public void execute() {
        if (getAuth()){
            actions.categoriesAction(header);
        }
    }
}
