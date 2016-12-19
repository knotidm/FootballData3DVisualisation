package UI;

import controlP5.Button;
import controlP5.ScrollableList;
import controlP5.Textlabel;

public class Event {
    public static Integer modeIndex = 0;
    public static Integer filterIndex = 1;
    public static Integer levelIndex = 0;
    public static Integer clickedObjects3D = 0;
    public static Integer teamModeIndex;
    public static Integer teamFieldIndex;

    static void modeButtonClick(Button modeButton) {
        modeButton.onClick(callbackEvent -> {
            modeIndex++;
            if (modeIndex == 3) modeIndex = 0;
        });
    }

    static void filterBackButtonClick(Button filterBackButton) {
        filterBackButton.onClick(callbackEvent -> {
            if (filterIndex != 1) filterIndex--;
        });
    }

    static void filterNextButtonClick(Button filterNextButton) {
        filterNextButton.onClick(callbackEvent -> {
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
    }

    static void levelBackButtonClick(Button levelBackButton, ScrollableList teamMode) {
        levelBackButton.onClick(callbackEvent -> {
            levelIndex--;
            if (levelIndex == 0) {
                levelBackButton.hide();
                teamMode.show().setOpen(false);
            }
            clickedObjects3D = 0;
            filterIndex = 1;
        });
    }

    static void teamModeChange(ScrollableList teamMode) {
        teamMode.onChange(callbackEvent -> teamModeIndex = (int) teamMode.getValue());
    }

    static void teamFieldChange(ScrollableList teamField, Button levelBackButton) {
        teamField.onChange(callbackEvent -> {
            if (teamField.isMouseOver()) {
                levelIndex = 1;
                filterIndex = 1;
                teamFieldIndex = (int) teamField.getValue();
                levelBackButton.show();
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

    static void switchLevelText(Textlabel textlabel) {
        switch (levelIndex) {
            case 0:
                textlabel.setText("COMPETITION LEVEL");
                break;
            case 1:
                textlabel.setText("TEAM LEVEL");
                break;
            case 2:
                if (teamFieldIndex == 0) {
                    textlabel.setText("FIXTURE LEVEL");
                }
                if (teamFieldIndex == 1) {
                    textlabel.setText("PLAYER LEVEL");
                }
                break;
        }
    }
}