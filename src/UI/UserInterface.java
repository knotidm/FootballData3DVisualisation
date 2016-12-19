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
    public ControlP5 controlP51;
    public ControlP5 controlP52;

    private Button modeButton1;
    private Textlabel modeText1;
    private Button modeButton2;
    private Textlabel modeText2;
    public Integer modeIndex = 0;

    private Button filterBackButton1;
    private Button filterNextButton1;
    private Button filterBackButton2;
    private Button filterNextButton2;
    public Integer filterIndex = 1;

    public Button levelBackButton1;
    private Textlabel levelText1;
    public Button levelBackButton2;
    private Textlabel levelText2;
    public Integer levelIndex = 0;

    public ScrollableList teamMode1;
    public ScrollableList teamMode2;
    public float teamModeIndex;

    public ScrollableList teamField1;
    public ScrollableList teamField2;
    public float teamFieldIndex;

    public Integer clickedObjects3D = 0;
    public boolean newWindow;

    public UserInterface(PApplet pApplet) {
        this.pApplet = pApplet;
        newWindow = false;
        PApplet.runSketch(new String[]{this.getClass().getName()}, this);

        controlP51 = new ControlP5(pApplet);
        controlP51.setAutoDraw(false);

        modeButton1 = controlP51.addButton("SWITCH MODE").setPosition(10, 10);
        modeText1 = controlP51.addTextlabel("modeText").setText("ROTATE").setColor(255).setPosition(85, 15);

        filterBackButton1 = controlP51.addButton("BACK FILTER").setPosition(10, 35);
        filterNextButton1 = controlP51.addButton("NEXT FILTER").setPosition(85, 35);

        levelBackButton1 = controlP51.addButton("LEVEL BACK").setPosition(10, 60).hide();
        levelText1 = controlP51.addTextlabel("levelText").setText("COMPETITION LEVEL").setColor(255).setPosition(85, 65);

        teamMode1 = controlP51.addScrollableList("TEAM MODE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("OVERALL", "HOME", "AWAY")).setPosition(10, 85)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamField1 = controlP51.addScrollableList("TEAM FIELD").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("FIXTURES", "PLAYERS")).setPosition(10, 85)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).hide();

        setMode();

        modeButton1.onClick(callbackEvent -> {
            modeIndex++;
            if (modeIndex == 3) modeIndex = 0;
        });

        filterBackButton1.onClick(callbackEvent -> {
            if (filterIndex != 1) filterIndex--;
        });

        filterNextButton1.onClick(callbackEvent -> {
            if (levelIndex == 0) {
                if (filterIndex < 9) {
                    filterIndex++;
                }
            } else if (levelIndex == 1) {
                if (filterIndex < 4) {
                    filterIndex++;
                }
            }
        });

        levelBackButton1.onClick(callbackEvent -> {
            levelIndex--;
            if (levelIndex == 0) {
                levelBackButton1.hide();
                teamMode1.show().setOpen(false);
            }
            clickedObjects3D = 0;
            filterIndex = 1;
        });

        teamMode1.onChange(callbackEvent -> {
            teamModeIndex = teamMode1.getValue();
        });

        teamField1.onChange(callbackEvent -> {
            if (teamField1.isMouseOver()) {
                levelIndex = 1;
                filterIndex = 1;
                teamFieldIndex = teamField1.getValue();
                levelBackButton1.show();
            }
        });
    }

    public void setMode() {
        if (newWindow) {
            surface.setVisible(true);
            controlP51.hide();
//            teamMode2.setValue(teamMode1.getValue());
//            teamField2.setValue(teamField1.getValue());
        } else {
            surface.setVisible(false);
            controlP51.show();
//            teamMode1.setValue(teamMode2.getValue());
//            teamField1.setValue(teamField2.getValue());
        }
    }

    private void switchModeText() {
        switch (modeIndex) {
            case 0:
                modeText1.setText("ROTATE");
                modeText2.setText("ROTATE");
                break;
            case 1:
                modeText1.setText("DRAG");
                modeText2.setText("DRAG");
                break;
            case 2:
                modeText1.setText("CLICK");
                modeText2.setText("CLICK");
                break;
        }
    }

    private void switchLevelText() {
        switch (levelIndex) {
            case 0:
                levelText1.setText("COMPETITION LEVEL");
                levelText2.setText("COMPETITION LEVEL");
                break;
            case 1:
                levelText1.setText("TEAM LEVEL");
                levelText2.setText("COMPETITION LEVEL");
                break;
            case 2:
                if (teamFieldIndex == 0) {
                    levelText1.setText("FIXTURE LEVEL");
                    levelText2.setText("FIXTURE LEVEL");
                }
                if (teamFieldIndex == 1) {
                    levelText1.setText("PLAYER LEVEL");
                    levelText2.setText("PLAYER LEVEL");
                }
                break;
        }
    }

    public void onFrontOfPeasyCam(PeasyCam peasyCam) {
        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        peasyCam.beginHUD();
        switchModeText();
        switchLevelText();
        controlP51.draw();
        peasyCam.endHUD();
        pApplet.hint(PConstants.ENABLE_DEPTH_TEST);
    }

    @Override
    public void settings() {
        size(400, 400);
    }

    @Override
    public void setup() {
        surface.setLocation(10, 10);
        controlP52 = new ControlP5(this);


        modeButton2 = controlP52.addButton("SWITCH MODE").setPosition(10, 10);
        modeText2 = controlP52.addTextlabel("modeText").setText("ROTATE").setColor(255).setPosition(85, 15);

        filterBackButton2 = controlP52.addButton("BACK FILTER").setPosition(10, 35);
        filterNextButton2 = controlP52.addButton("NEXT FILTER").setPosition(85, 35);

        levelBackButton2 = controlP52.addButton("LEVEL BACK").setPosition(10, 60).hide();
        levelText2 = controlP52.addTextlabel("levelText").setText("COMPETITION LEVEL").setColor(255).setPosition(85, 65);

        teamMode2 = controlP52.addScrollableList("TEAM MODE").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("OVERALL", "HOME", "AWAY")).setPosition(10, 85)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false);

        teamField2 = controlP52.addScrollableList("TEAM FIELD").setType(ScrollableList.LIST)
                .addItems(Arrays.asList("FIXTURES", "PLAYERS")).setPosition(10, 85)
                .setSize(140, 100).setBarHeight(20).setItemHeight(20)
                .setOpen(false).hide();

        modeButton2.onClick(callbackEvent -> {
            modeIndex++;
            if (modeIndex == 3) modeIndex = 0;
        });

        filterBackButton2.onClick(callbackEvent -> {
            if (filterIndex != 1) filterIndex--;
        });

        filterNextButton2.onClick(callbackEvent -> {
            if (levelIndex == 0) {
                if (filterIndex < 9) {
                    filterIndex++;
                }
            } else if (levelIndex == 1) {
                if (filterIndex < 4) {
                    filterIndex++;
                }
            }
        });

        levelBackButton2.onClick(callbackEvent -> {
            levelIndex--;
            if (levelIndex == 0) {
                levelBackButton2.hide();
                teamMode2.show().setOpen(false);
            }
            clickedObjects3D = 0;
            filterIndex = 1;
        });

        teamMode2.onChange(callbackEvent -> {
            teamModeIndex = teamMode2.getValue();
        });

        teamField2.onChange(callbackEvent -> {
            if (teamField2.isMouseOver()) {
                levelIndex = 1;
                filterIndex = 1;
                teamFieldIndex = teamField2.getValue();
                levelBackButton2.show();
            }
        });
    }

    @Override
    public void draw() {
        background(0);
        switchModeText();
        switchLevelText();
    }
}