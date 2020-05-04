package commands.controller;

import commands.command.Command;

public class Controller {
    Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void run() {
        command.execute();
    }
}
