package menu;

import commands.command.*;
import commands.controller.Controller;
import commands.receiver.Actions;
import model.userdata.UserData;
import view.PrintView;
import view.ViewObject;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final UserData data;
    private final Scanner scan;
    private final Controller controller;
    private final Actions actions;
    Command command;

    public Menu(Scanner scan, UserData data, Integer pageSize) {
        this.scan = scan;
        this.data = data;
        controller = new Controller();
        actions = new Actions();
        PrintView.setPageSize(pageSize);
    }
    public void run() {
        String[] item = scan.nextLine().split(" ");

        do {
            switch (item[0]) {
                case "featured":
                    command = new FeaturedCommand(actions);
                    break;
                case "new":
                    command = new NewCommand(actions);
                    break;
                case "categories":
                    command = new CategoriesCommand(actions);
                    break;
                case "playlists":
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < item.length; i++) {
                        sb.append(item[i]).append(" ");
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    command = new PlaylistsCommand(actions, sb.toString());
                    break;
                case "auth":
                    command = new AuthCommand(actions, data);
                    break;
                case "exit":
                    command = new ExitCommand(actions);
                    controller.setCommand(command);
                    controller.run();
                    return;
                default:
                    System.out.println("Wrong choice.");
                    item = scan.nextLine().split(" ");
                    continue;
            }
            controller.setCommand(command);
            controller.run();
            if (ViewObject.isUpdate()) {
                List<String> objs = ViewObject.makeViewObj();
                ViewObject.setUpdate(false);
                item = new PrintView(objs).print(scan).split(" ");
                continue;
            }
            item = scan.nextLine().split(" ");
        } while (true);
    }
}
