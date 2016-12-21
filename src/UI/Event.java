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

    static void switchModeText(Textlabel textlabel) {
        switch (modeIndex) {
            case 0:
                textlabel.setText("ROTATE");
                break;
            case 1:
                textlabel.setText("DRAG");
                break;
            case 2:
                textlabel.setText("CLICK");
                break;
        }
    }

    static void switchLevel(Textlabel textlabel, Button levelBackButton, ScrollableList teamFilterMode, ScrollableList teamFilter, ScrollableList teamField, ScrollableList playerFilter) {
        switch (levelIndex) {
            case 0:
                textlabel.setText("COMPETITION LEVEL");
                levelBackButton.hide();
                teamFilterMode.show();
                teamFilter.show();
                playerFilter.hide();
                break;
            case 1:
                textlabel.setText("TEAM LEVEL");
                levelBackButton.show();
                teamFilterMode.hide();
                teamFilter.hide();
                teamField.hide();
                if (teamFieldIndex == 1) {
                    playerFilter.show();
                }
                break;
            case 2:
                if (teamFieldIndex == 0) {
                    textlabel.setText("FIXTURE LEVEL");
                }
                if (teamFieldIndex == 1) {
                    textlabel.setText("PLAYER LEVEL");
                    playerFilter.hide();
                }
                break;
        }
    }
}