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
    public ControlP5 controlP5Foreground;
    public ScrollableList teamFieldForeground;
    public ScrollableList teamFieldWindow;
    public boolean newWindow;
    private PApplet pApplet;
    private ControlP5 controlP5Window;
    private Button modeButtonForeground;
    private Textlabel modeTextForeground;
    private Button modeButtonWindow;
    private Textlabel modeTextWindow;
    private Button levelBackButtonForeground;
    private Textlabel levelTextForeground;
    private Button levelBackButtonWindow;
    private Textlabel levelTextWindow;
    private ScrollableList competitionForeground;
    private ScrollableList competitionWindow;
    private ScrollableList teamFilterModeForeground;
    private ScrollableList teamFilterModeWindow;
    private ScrollableList teamFilterForeground;
    private ScrollableList teamFilterWindow;
    private ScrollableList playerFilterForeground;
    private ScrollableList playerFilterWindow;
    private Button chartViewButtonForeground;
    private Button chartViewButtonWindow;

    public UserInterface(PApplet pApplet) {
        this.pApplet = pApplet;
        newWindow = false;
        PApplet.runSketch(new String[]{this.getClass().getName()}, this);

        controlP5Foreground = new ControlP5(pApplet);
        controlP5Foreground.setAutoDraw(false);

        modeButtonForeground = controlP5Foreground.addButton("SWITCH MODE").setPosition(10, 10);
        modeTextForeground = controlP5Foreground.addTextlabel("modeText").setText("ROTATE").setColor(255).setPosition(85, 15);

        levelBackButtonForeground = controlP5Foreground.addButton("LEVEL BACK").setPosition(10, 40).hide();
        levelTextForeground = controlP5Foreground.addTextlabel("levelText").setText("COMPETITION LEVEL").setColor(255).setPosition(85, 45);

        competitionForeground = controlP5Foreground.addScrollableList("SELECT COMPETITION").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("BUNDESLIGA", "SERIE A", "LIGUE 1")).setPosition(10, 75)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamFilterModeForeground = controlP5Foreground.addScrollableList("TEAM FILTER MODE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("OVERALL", "HOME", "AWAY")).setPosition(10, 275)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamFilterForeground = controlP5Foreground.addScrollableList("TEAM FILTER").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("PLAYED GAMES", "POINTS", "GOALS", "GOALS AGAINST", "GOAL DIFFERENCE", "WINS", "DRAWS", "LOSSES", "SQUAD MARKET VALUE", "AVERAGE AGE")).setPosition(10, 365)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        playerFilterForeground = controlP5Foreground.addScrollableList("PLAYER FILTER").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("JERSEY NUMBER", "MARKET VALUE", "AGE", "YEARS TO END CONTRACT")).setPosition(10, 275)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        chartViewButtonForeground = controlP5Foreground.addButton("VIEW CHART").setPosition(10, 475);

        teamFieldForeground = controlP5Foreground.addScrollableList("TEAM FIELD").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("FIXTURES", "PLAYERS")).setPosition(10, 505)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).hide();

        setMode();

        Event.modeButtonClick(modeButtonForeground);
        Event.levelBackButtonClick(levelBackButtonForeground);
        Event.competitionChange(competitionForeground);
        Event.teamFilterModeChange(teamFilterModeForeground);
        Event.filterChange(teamFilterForeground);
        Event.filterChange(playerFilterForeground);
        Event.chartViewButtonClick(chartViewButtonForeground, modeButtonForeground, modeTextForeground);
        Event.teamFieldChange(teamFieldForeground, playerFilterForeground);
    }

    @Override
    public void settings() {
        size(200, pApplet.height);
    }

    @Override
    public void setup() {
        surface.setLocation(10, 100);
        controlP5Window = new ControlP5(this);

        modeButtonWindow = controlP5Window.addButton("SWITCH MODE").setPosition(10, 10);
        modeTextWindow = controlP5Window.addTextlabel("modeText").setText("ROTATE").setColor(255).setPosition(85, 15);

        levelBackButtonWindow = controlP5Window.addButton("LEVEL BACK").setPosition(10, 40).hide();
        levelTextWindow = controlP5Window.addTextlabel("levelText").setText("COMPETITION LEVEL").setColor(255).setPosition(85, 45);

        competitionWindow = controlP5Window.addScrollableList("SELECT COMPETITION").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("BUNDESLIGA", "SERIE A", "LIGUE 1")).setPosition(10, 75)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamFilterModeWindow = controlP5Window.addScrollableList("TEAM FILTER MODE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("OVERALL", "HOME", "AWAY")).setPosition(10, 275)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamFilterWindow = controlP5Window.addScrollableList("TEAM FILTER").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("PLAYED GAMES", "POINTS", "GOALS", "GOALS AGAINST", "GOAL DIFFERENCE", "WINS", "DRAWS", "LOSSES", "SQUAD MARKET VALUE", "AVERAGE AGE")).setPosition(10, 365)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        playerFilterWindow = controlP5Window.addScrollableList("PLAYER FILTER").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("JERSEY NUMBER", "MARKET VALUE", "AGE", "YEARS TO END CONTRACT")).setPosition(10, 275)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        chartViewButtonWindow = controlP5Window.addButton("VIEW CHART").setPosition(10, 475);

        teamFieldWindow = controlP5Window.addScrollableList("TEAM FIELD").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("FIXTURES", "PLAYERS")).setPosition(10, 505)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).hide();

        Event.modeButtonClick(modeButtonWindow);
        Event.levelBackButtonClick(levelBackButtonWindow);
        Event.competitionChange(competitionWindow);
        Event.teamFilterModeChange(teamFilterModeWindow);
        Event.filterChange(teamFilterWindow);
        Event.filterChange(playerFilterWindow);
        Event.chartViewButtonClick(chartViewButtonWindow, modeButtonWindow, modeTextWindow);
        Event.teamFieldChange(teamFieldWindow, playerFilterWindow);
    }

    @Override
    public void draw() {
        background(0);
        Event.switchModeText(modeTextWindow);
        Event.switchLevel(modeButtonWindow, modeTextWindow, levelBackButtonWindow, levelTextWindow, teamFilterModeWindow, teamFilterWindow, playerFilterWindow, chartViewButtonWindow, teamFieldWindow);
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
        Event.switchLevel(modeButtonForeground, modeTextForeground, levelBackButtonForeground, levelTextForeground, teamFilterModeForeground, teamFilterForeground, playerFilterForeground, chartViewButtonForeground, teamFieldForeground);
        controlP5Foreground.draw();
        peasyCam.endHUD();
        pApplet.hint(PConstants.ENABLE_DEPTH_TEST);
    }
}