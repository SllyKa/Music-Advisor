package commands.command;

import commands.receiver.Actions;

public class PlaylistsCommand extends Command {
    String secondHeader;

    public PlaylistsCommand(Actions actions, String secondHeader) {
        super(actions, "playlists");
        this.secondHeader = secondHeader;
    }

    @Override
    public void execute() {
        if (getAuth()) {
            actions.playlistsAction(header, secondHeader);
        }
    }
}
