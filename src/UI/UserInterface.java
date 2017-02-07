package UI;

import controlP5.*;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.Arrays;

public class UserInterface extends PApplet {
    private PApplet pApplet;

    private ControlP5 controlP5;

    private Button modeButton;
    private Textlabel modeText;

    private Button levelBackButton;
    private Textlabel levelText;

    private Button customListButton;

    public ScrollableList competition;

    public ScrollableList teamStats1Mode;
    public ScrollableList teamStats2Mode;
    public ScrollableList teamStats3Mode;
    public ScrollableList teamStats1;
    public ScrollableList teamStats2;
    public ScrollableList teamStats3;

    public ScrollableList playerStats1;
    public ScrollableList playerStats2;
    public ScrollableList playerStats3;

    private Button chartViewButton;

    public ScrollableList chartType;

    public ScrollableList teamField;

    private ControlP5 controlP5Foreground;
    public Slider sliderX;
    public Slider sliderY;
    public Slider sliderZ;

    public UserInterface(PApplet pApplet) {
        this.pApplet = pApplet;

        controlP5Foreground = new ControlP5(pApplet);
        controlP5Foreground.setAutoDraw(false);

        sliderX = controlP5Foreground.addSlider("sliderX")
                .setPosition(152, pApplet.height - 25)
                .setSize(pApplet.width - 240, 10)
                .setLabelVisible(false);

        sliderY = controlP5Foreground.addSlider("sliderY")
                .setPosition(10, 75)
                .setSize(10, pApplet.height - 155)
                .setLabelVisible(false);

        sliderZ = controlP5Foreground.addSlider("sliderZ")
                .setPosition(pApplet.width - 20, 75)
                .setSize(10, pApplet.height - 155)
                .setLabelVisible(false);

        PApplet.runSketch(new String[]{this.getClass().getName()}, this);
        surface.setVisible(true);

        Event.sliderXChange(sliderX);
        Event.sliderYChange(sliderY);
        Event.sliderZChange(sliderZ);
    }

    @Override
    public void settings() {
        size(325, pApplet.height);
    }

    @Override
    public void setup() {
        surface.setLocation(10, 113);
        controlP5 = new ControlP5(this);

        modeButton = controlP5.addButton("SWITCH MODE").setPosition(10, 10);
        modeText = controlP5.addTextlabel("MODE TEXT").setText("ROTATE").setColor(255).setPosition(85, 15);

        levelBackButton = controlP5.addButton("LEVEL BACK").setPosition(10, 40).hide();
        levelText = controlP5.addTextlabel("LEVEL TEXT").setText("COMPETITION LEVEL").setColor(255).setPosition(85, 45);

        customListButton = controlP5.addButton("CUSTOM LIST").setPosition(190, 40);

        competition = controlP5.addScrollableList("COMPETITION").setType(ScrollableList.LIST)
                .setPosition(10, 75)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamStats1Mode = controlP5.addScrollableList("TEAM STATS 1 MODE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("OVERALL", "HOME", "AWAY")).setPosition(10, 190)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).setValue(0);

        teamStats2Mode = controlP5.addScrollableList("TEAM STATS 2 MODE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("OVERALL", "HOME", "AWAY")).setPosition(160, 190)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).setValue(0);

        teamStats3Mode = controlP5.addScrollableList("TEAM STATS 3 MODE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("OVERALL", "HOME", "AWAY")).setPosition(160, 430)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).setValue(0);

        teamStats1 = controlP5.addScrollableList("TEAM STATS 1").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("PLAYED GAMES", "POINTS", "GOALS", "GOALS AGAINST", "GOAL DIFFERENCE", "WINS", "DRAWS", "LOSSES", "SQUAD MARKET VALUE", "AVERAGE AGE")).setPosition(10, 285)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).setValue(1);

        teamStats2 = controlP5.addScrollableList("TEAM STATS 2").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("PLAYED GAMES", "POINTS", "GOALS", "GOALS AGAINST", "GOAL DIFFERENCE", "WINS", "DRAWS", "LOSSES", "SQUAD MARKET VALUE", "AVERAGE AGE")).setPosition(160, 285)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).setValue(0);

        teamStats3 = controlP5.addScrollableList("TEAM STATS 3").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("PLAYED GAMES", "POINTS", "GOALS", "GOALS AGAINST", "GOAL DIFFERENCE", "WINS", "DRAWS", "LOSSES", "SQUAD MARKET VALUE", "AVERAGE AGE")).setPosition(160, 525)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).setValue(0);

        playerStats1 = controlP5.addScrollableList("PLAYER STATS 1").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("JERSEY NUMBER", "MARKET VALUE", "AGE", "YEARS TO END CONTRACT")).setPosition(10, 285)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).setValue(0);

        playerStats2 = controlP5.addScrollableList("PLAYER STATS 2").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("JERSEY NUMBER", "MARKET VALUE", "AGE", "YEARS TO END CONTRACT")).setPosition(160, 285)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).setValue(0);

        playerStats3 = controlP5.addScrollableList("PLAYER STATS 3").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("JERSEY NUMBER", "MARKET VALUE", "AGE", "YEARS TO END CONTRACT")).setPosition(160, 525)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).setValue(0);

        chartViewButton = controlP5.addButton("VIEW CHART").setPosition(10, 400);

        chartType = controlP5.addScrollableList("CHART TYPE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("BAR CHART", "XY CHART", "PIE CHART", "3D CHART")).setPosition(10, 430)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).hide();

        teamField = controlP5.addScrollableList("TEAM FIELD").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("FIXTURES", "PLAYERS")).setPosition(10, 430)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).hide();

        Event.modeButtonClick(modeButton);
        Event.levelBackButtonClick(levelBackButton);
        Event.customListButtonClick(customListButton);
        Event.chartViewButtonClick(chartViewButton);
        Event.teamFieldChange(teamField);
    }

    @Override
    public void draw() {
        background(0);
        Event.switchModeText(modeText);
        Event.switchLevel(modeButton, modeText,
                levelBackButton, levelText,
                teamStats1Mode, teamStats2Mode, teamStats3Mode,
                teamStats1, teamStats2, teamStats3,
                playerStats1, playerStats2, playerStats3,
                chartViewButton, chartType,
                teamField,
                sliderX, sliderY, sliderZ);
    }

    public void onFrontOfPeasyCam(PeasyCam peasyCam) {
        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        peasyCam.beginHUD();
        controlP5Foreground.draw();
        peasyCam.endHUD();
        pApplet.hint(PConstants.ENABLE_DEPTH_TEST);
    }
}