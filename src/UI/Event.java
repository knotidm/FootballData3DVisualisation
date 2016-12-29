package UI;

import controlP5.Button;
import controlP5.ScrollableList;
import controlP5.Textlabel;

public class Event {
    public static Integer modeIndex = 0;
    public static Integer filterIndex = 0;
    public static Integer levelIndex = 0;
    public static Integer clickedObjects3D = 0;
    public static Integer teamFilterModeIndex = 0;
    public static Integer teamFieldIndex = 0;
    public static Boolean chartView = false;
    public static Integer competitionIndex = 0;

    static void modeButtonClick(Button modeButton) {
        modeButton.onClick(callbackEvent -> {
            modeIndex++;
            if (modeIndex == 3) modeIndex = 0;
        });
    }

    static void levelBackButtonClick(Button levelBackButton) {
        levelBackButton.onClick(callbackEvent -> {
            levelIndex--;
            clickedObjects3D = 0;
            filterIndex = 0;
        });
    }

    static void competitionChange(ScrollableList competition) {
        competition.onChange(callbackEvent -> competitionIndex = (int) competition.getValue());
    }

    static void teamFilterModeChange(ScrollableList teamFilterMode) {
        teamFilterMode.onChange(callbackEvent -> teamFilterModeIndex = (int) teamFilterMode.getValue());
    }

    static void filterChange(ScrollableList filter) {
        filter.onChange(callbackEvent -> filterIndex = (int) filter.getValue());
    }

    static void teamFieldChange(ScrollableList teamField, ScrollableList playerFilter) {
        teamField.onChange(callbackEvent -> {
            if (teamField.isMouseOver()) {
                levelIndex = 1;
                teamFieldIndex = (int) teamField.getValue();
                filterIndex = (int) playerFilter.getValue();
            }
        });
    }

    static void switchModeText(Textlabel modeText) {
        switch (modeIndex) {
            case 0:
                modeText.setText("ROTATE");
                break;
            case 1:
                modeText.setText("DRAG");
                break;
            case 2:
                modeText.setText("CLICK");
                break;
        }
    }

    static void switchLevel(Button modeButton, Textlabel modeText, Button levelBackButton, Textlabel levelText, ScrollableList teamFilterMode, ScrollableList teamFilter, ScrollableList playerFilter, Button chartViewButton, ScrollableList teamField) {
        switch (levelIndex) {
            case 0:
                levelBackButton.hide();
                levelText.setText("COMPETITION LEVEL");
                teamFilterMode.show();
                teamFilter.show();
                playerFilter.hide();
                break;
            case 1:
                modeButton.show();
                modeText.show();
                levelBackButton.show();
                levelText.setText("TEAM LEVEL");
                teamFilterMode.hide();
                teamFilter.hide();
                if (teamFieldIndex == 1) {
                    playerFilter.show();
                }
                chartViewButton.show();
                teamField.hide();
                break;
            case 2:
                modeButton.hide();
                modeText.hide();
                if (teamFieldIndex == 0) {
                    levelText.setText("FIXTURE LEVEL");
                }
                if (teamFieldIndex == 1) {
                    levelText.setText("PLAYER LEVEL");
                    playerFilter.hide();
                }
                chartViewButton.hide();
                break;
        }
    }

    static void chartViewButtonClick(Button chartViewButton, Button modeButton, Textlabel modeText) {
        chartViewButton.onClick(callbackEvent -> {
            if (!chartView) {
                chartViewButton.setLabel("VIEW SCENE");
                modeButton.hide();
                modeText.hide();
                chartView = true;
            } else {
                chartViewButton.setLabel("VIEW CHART");
                modeButton.show();
                modeText.show();
                chartView = false;
            }
        });
    }
}