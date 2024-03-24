package launcher.menu;

import launcher.GameId;

import java.util.ArrayList;
import java.util.List;

public class MenuGameState {

    private int selectedIndex;
    private boolean commit;
    private boolean quit;
    private boolean joystickToggled;
    private List<GameId> selections = new ArrayList<>();

    public MenuGameState() {
        commit = false;
        quit = false;
        joystickToggled = false;

        for(GameId gameId : GameId.values()) {
            if (GameId.MENU != gameId) {
                selections.add(gameId);
            }
        }

        select(GameId.DEBUGGER);
    }

    private void select(GameId gameId) {
        // Ensure the DEBUGGER
        for(int i=0; i<selections.size(); i++) {
            if (selections.get(i).equals(gameId)) {
                this.selectedIndex = i;
                break;
            }
        }
    }

    public GameId getSelectedGameId() {
        return selections.get(selectedIndex);
    }

    public boolean isCommit() {
        return commit;
    }

    public void setCommit(boolean commit) {
        this.commit = commit;
    }

    public boolean isQuit() {
        return quit;
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    public boolean isJoystickToggled() {
        return joystickToggled;
    }

    public void setJoystickToggled(boolean joystickToggled) {
        this.joystickToggled = joystickToggled;
    }

    public void selectNext() {
        selectedIndex = (selectedIndex + 1) % selections.size();
    }

    public void selectPrev() {
        selectedIndex = (selectedIndex + selections.size()-1)  % selections.size();
    }

}
