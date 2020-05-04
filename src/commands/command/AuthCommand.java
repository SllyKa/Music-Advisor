package commands.command;

import commands.receiver.Actions;
import model.userdata.UserData;

public class AuthCommand extends Command {
    UserData data;
    public AuthCommand(Actions actions, UserData data) {
        super(actions, "success");
        this.data = data;
    }

    @Override
    public void execute() {
        isAuth = actions.AuthAction(header, data);
    }
}
