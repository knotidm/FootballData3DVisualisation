package UI;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.ScrollableList;
import controlP5.Textlabel;
import processing.core.PApplet;

import java.util.Arrays;

public class UserInterface extends PApplet {
    private PApplet pApplet;
    private ControlP5 controlP5;

    private Button modeButton;
    private Textlabel modeText;
    private Button levelBackButton;
    private Textlabel levelText;
    public ScrollableList competition;
    private ScrollableList teamFilterMode;
    private ScrollableList teamFilter1;
    private ScrollableList playerFilter1;
    private Button chartViewButton;
    public ScrollableList teamField;
    private ScrollableList chart2DType;
    private ScrollableList teamFilter2;
    private ScrollableList playerFilter2;

    public UserInterface(PApplet pApplet) {
        this.pApplet = pApplet;
        PApplet.runSketch(new String[]{this.getClass().getName()}, this);
        surface.setVisible(true);
    }

    @Override
    public void settings() {
        size(200, pApplet.height);
    }

    @Override
    public void setup() {
        surface.setLocation(40, 100);
        controlP5 = new ControlP5(this);

        modeButton = controlP5.addButton("SWITCH MODE").setPosition(10, 10);
        modeText = controlP5.addTextlabel("modeText").setText("ROTATE").setColor(255).setPosition(85, 15);

        levelBackButton = controlP5.addButton("LEVEL BACK").setPosition(10, 40).hide();
        levelText = controlP5.addTextlabel("levelText").setText("COMPETITION LEVEL").setColor(255).setPosition(85, 45);

        competition = controlP5.addScrollableList("COMPETITION").setType(ScrollableList.LIST)
                .setPosition(10, 75)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamFilterMode = controlP5.addScrollableList("TEAM FILTER MODE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("OVERALL", "HOME", "AWAY")).setPosition(10, 190)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamFilter1 = controlP5.addScrollableList("TEAM FILTER 1").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("PLAYED GAMES", "POINTS", "GOALS", "GOALS AGAINST", "GOAL DIFFERENCE", "WINS", "DRAWS", "LOSSES", "SQUAD MARKET VALUE", "AVERAGE AGE")).setPosition(10, 285)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        playerFilter1 = controlP5.addScrollableList("PLAYER FILTER 1").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("JERSEY NUMBER", "MARKET VALUE", "AGE", "YEARS TO END CONTRACT")).setPosition(10, 285)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        chartViewButton = controlP5.addButton("VIEW CHART").setPosition(10, 400);

        teamField = controlP5.addScrollableList("TEAM FIELD").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("FIXTURES", "PLAYERS")).setPosition(10, 430)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).hide();

        chart2DType = controlP5.addScrollableList("CHART 2D TYPE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("BAR CHART", "XY CHART", "CIRCLE")).setPosition(10, 430)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).hide();

        teamFilter2 = controlP5.addScrollableList("TEAM FILTER 2").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("PLAYED GAMES", "POINTS", "GOALS", "GOALS AGAINST", "GOAL DIFFERENCE", "WINS", "DRAWS", "LOSSES", "SQUAD MARKET VALUE", "AVERAGE AGE")).setPosition(10, 545)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        playerFilter2 = controlP5.addScrollableList("PLAYER FILTER 2").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("JERSEY NUMBER", "MARKET VALUE", "AGE", "YEARS TO END CONTRACT")).setPosition(10, 545)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        Event.modeButtonClick(modeButton);
        Event.levelBackButtonClick(levelBackButton);
        Event.competitionChange(competition);
        Event.teamFilterModeChange(teamFilterMode);
        Event.filterChange1(teamFilter1);
        Event.filterChange1(playerFilter1);
        Event.chartViewButtonClick(chartViewButton, modeButton, modeText, chart2DType);
        Event.teamFieldChange(teamField, playerFilter1);
        Event.chartTypeChange(chart2DType);
        Event.filterChange2(teamFilter2);
        Event.filterChange2(playerFilter2);
    }

    @Override
    public void draw() {
        background(0);
        Event.switchModeText(modeText);
        Event.switchLevel(modeButton, modeText, levelBackButton, levelText, teamFilterMode, teamFilter1, teamFilter2, playerFilter1, playerFilter2, chartViewButton, teamField);
    }
}