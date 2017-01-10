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
    public ScrollableList competition;
    private ScrollableList teamFilterMode;
    private ScrollableList teamFilter1;
    private ScrollableList playerFilter1;
    private Button chartViewButton;
    public ScrollableList teamField;
    private ScrollableList chart2DType;
    private ScrollableList teamFilter2;
    private ScrollableList playerFilter2;

    private ControlP5 controlP5Foreground;
    public Slider sliderX;
    public Slider sliderY;
    private Slider sliderParam;

    public UserInterface(PApplet pApplet) {
        this.pApplet = pApplet;
        PApplet.runSketch(new String[]{this.getClass().getName()}, this);
        surface.setVisible(true);

        controlP5Foreground = new ControlP5(pApplet);
        controlP5Foreground.setAutoDraw(false);

        sliderX = controlP5Foreground.addSlider("sliderX")
                .setPosition(145, height - 25)
                .setSize(pApplet.width - 220, 10)
                .setLabelVisible(false);

        sliderY = controlP5Foreground.addSlider("sliderY")
                .setPosition(80, 72)
                .setSize(10, pApplet.height - 146)
                .setLabelVisible(false);

        sliderParam = controlP5Foreground.addSlider("sliderParam")
                .setRange(1, 3)
                .setValue(1.5f)
                .setPosition(15, height - 15)
                .setSize(width - 30, 10)
                .setLabelVisible(false);

        Event.sliderXChange(sliderX);
        Event.sliderYChange(sliderY);
        Event.sliderParamChange(sliderParam);
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
                .addItems(Arrays.asList("BAR CHART", "XY CHART", "PIE CHART", "3D CHART")).setPosition(10, 430)
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

    public void onFrontOfPeasyCam(PeasyCam peasyCam) {
        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        peasyCam.beginHUD();
        controlP5Foreground.draw();
        peasyCam.endHUD();
        pApplet.hint(PConstants.ENABLE_DEPTH_TEST);
    }
}