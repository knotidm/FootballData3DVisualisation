package UI;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.ScrollableList;
import controlP5.Textlabel;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.Arrays;

public class UserInterface extends PApplet {
    private PApplet pApplet;

    public ControlP5 controlP5Foreground;
    public ControlP5 controlP5Window;

    private Button modeButtonForeground;
    private Textlabel modeTextForeground;
    private Button modeButtonWindow;
    private Textlabel modeTextWindow;

    private Button filterBackButtonForeground;
    private Button filterNextButtonForeground;
    private Button filterBackButtonWindow;
    private Button filterNextButtonWindow;

    public Button levelBackButtonForeground;
    private Textlabel levelTextForeground;
    public Button levelBackButtonWindow;
    private Textlabel levelTextWindow;

    public ScrollableList teamModeForeground;
    public ScrollableList teamModeWindow;

    public ScrollableList teamFieldForeground;
    public ScrollableList teamFieldWindow;

    public boolean newWindow;

    public UserInterface(PApplet pApplet) {
        this.pApplet = pApplet;
        newWindow = false;
        PApplet.runSketch(new String[]{this.getClass().getName()}, this);

        controlP5Foreground = new ControlP5(pApplet);
        controlP5Foreground.setAutoDraw(false);

        modeButtonForeground = controlP5Foreground.addButton("SWITCH MODE").setPosition(10, 10);
        modeTextForeground = controlP5Foreground.addTextlabel("modeText").setText("ROTATE").setColor(255).setPosition(85, 15);

        filterBackButtonForeground = controlP5Foreground.addButton("BACK FILTER").setPosition(10, 35);
        filterNextButtonForeground = controlP5Foreground.addButton("NEXT FILTER").setPosition(85, 35);

        levelBackButtonForeground = controlP5Foreground.addButton("LEVEL BACK").setPosition(10, 60).hide();
        levelTextForeground = controlP5Foreground.addTextlabel("levelText").setText("COMPETITION LEVEL").setColor(255).setPosition(85, 65);

        teamModeForeground = controlP5Foreground.addScrollableList("TEAM MODE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("OVERALL", "HOME", "AWAY")).setPosition(10, 85)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamFieldForeground = controlP5Foreground.addScrollableList("TEAM FIELD").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("FIXTURES", "PLAYERS")).setPosition(10, 85)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).hide();

        setMode();

        Event.modeButtonClick(modeButtonForeground);
        Event.filterBackButtonClick(filterBackButtonForeground);
        Event.filterNextButtonClick(filterNextButtonForeground);
        Event.levelBackButtonClick(levelBackButtonForeground, teamModeForeground);
        Event.teamModeChange(teamModeForeground);
        Event.teamFieldChange(teamFieldForeground, levelBackButtonForeground);
    }

    @Override
    public void settings() {
        size(400, 400);
    }

    @Override
    public void setup() {
        surface.setLocation(10, 10);
        controlP5Window = new ControlP5(this);

        modeButtonWindow = controlP5Window.addButton("SWITCH MODE").setPosition(10, 10);
        modeTextWindow = controlP5Window.addTextlabel("modeText").setText("ROTATE").setColor(255).setPosition(85, 15);

        filterBackButtonWindow = controlP5Window.addButton("BACK FILTER").setPosition(10, 35);
        filterNextButtonWindow = controlP5Window.addButton("NEXT FILTER").setPosition(85, 35);

        levelBackButtonWindow = controlP5Window.addButton("LEVEL BACK").setPosition(10, 60).hide();
        levelTextWindow = controlP5Window.addTextlabel("levelText").setText("COMPETITION LEVEL").setColor(255).setPosition(85, 65);

        teamModeWindow = controlP5Window.addScrollableList("TEAM MODE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("OVERALL", "HOME", "AWAY")).setPosition(10, 85)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamFieldWindow = controlP5Window.addScrollableList("TEAM FIELD").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("FIXTURES", "PLAYERS")).setPosition(10, 85)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).hide();

        Event.modeButtonClick(modeButtonWindow);
        Event.filterBackButtonClick(filterBackButtonWindow);
        Event.filterNextButtonClick(filterNextButtonWindow);
        Event.levelBackButtonClick(levelBackButtonWindow, teamModeWindow);
        Event.teamModeChange(teamModeWindow);
        Event.teamFieldChange(teamFieldWindow, levelBackButtonWindow);
    }

    @Override
    public void draw() {
        background(0);
        Event.switchModeText(modeTextWindow);
        Event.switchLevelText(levelTextWindow);
    }

    public void setMode() {
        if (newWindow) {
            surface.setVisible(true);
            controlP5Foreground.hide();
        } else {
            surface.setVisible(false);
            controlP5Foreground.show();
        }
    }

    public void onFrontOfPeasyCam(PeasyCam peasyCam) {
        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        peasyCam.beginHUD();
        Event.switchModeText(modeTextForeground);
        Event.switchLevelText(levelTextForeground);
        controlP5Foreground.draw();
        peasyCam.endHUD();
        pApplet.hint(PConstants.ENABLE_DEPTH_TEST);
    }
}