package commands.command;

import commands.receiver.Actions;

public abstract class Command {
    Actions actions;
    String header;
    protected static boolean isAuth = false;

    Command(Actions actions, String header) {
        this.actions = actions;
        this.header = header;
    }
    public abstract void execute();

    public boolean getAuth() {
        if (!isAuth) {
            authErrorMsg();
        }
        return isAuth;
    }

    public void authErrorMsg() {
        System.out.println("Please, provide access for application.");
    }
}
