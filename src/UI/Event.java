package UI;

import Interaction.ChartInteraction;
import controlP5.Button;
import controlP5.ScrollableList;
import controlP5.Slider;
import controlP5.Textlabel;

public class Event {
    public static Integer modeIndex = 0;
    public static Integer levelIndex = 0;
    public static Boolean customList = false;
    public static Integer competitionIndex = 0;
    public static Integer teamStats1ModeIndex = 0;
    public static Integer teamStats2ModeIndex = 0;
    public static Integer teamStats3ModeIndex = 0;
    public static Integer stats1Index = 0;
    public static Integer stats2Index = 0;
    public static Integer stats3Index = 0;
    public static Boolean chartView = false;
    public static Integer chartTypeIndex = 0;
    public static Integer teamFieldIndex = 0;
    public static Integer clickedObjects3D = 0;

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
            stats1Index = 0;
            stats2Index = 0;
        });
    }

    static void customListButtonClick(Button customListButton) {
        customListButton.onClick(callbackEvent -> {
            if (!customList) {
                customListButton.setLabel("CUSTOM LIST");
                customList = true;
            } else {
                customListButton.setLabel("LEAGUE LIST");
                customList = false;
            }
        });
    }

    static void competitionChange(ScrollableList competition) {
        competition.onChange(callbackEvent -> competitionIndex = (int) competition.getValue());
    }

    static void teamStats1ModeChange(ScrollableList teamStats1Mode) {
        teamStats1Mode.onChange(callbackEvent -> teamStats1ModeIndex = (int) teamStats1Mode.getValue());
    }

    static void teamStats2ModeChange(ScrollableList teamStats2Mode) {
        teamStats2Mode.onChange(callbackEvent -> teamStats2ModeIndex = (int) teamStats2Mode.getValue());
    }

    static void teamStats3ModeChange(ScrollableList teamStats3Mode) {
        teamStats3Mode.onChange(callbackEvent -> teamStats3ModeIndex = (int) teamStats3Mode.getValue());
    }

    static void stats1Change(ScrollableList stats1) {
        stats1.onChange(callbackEvent -> stats1Index = (int) stats1.getValue());
    }

    static void stats2Change(ScrollableList stats2) {
        stats2.onChange(callbackEvent -> stats2Index = (int) stats2.getValue());
    }

    static void stats3Change(ScrollableList stats3) {
        stats3.onChange(callbackEvent -> stats3Index = (int) stats3.getValue());
    }

    static void chartViewButtonClick(Button chartViewButton) {
        chartViewButton.onClick(callbackEvent -> {
            if (!chartView) {
                chartViewButton.setLabel("VIEW SCENE");
                chartView = true;
            } else {
                chartViewButton.setLabel("VIEW CHART");
                chartView = false;
                chartTypeIndex = 0;
            }
        });
    }

    static void chartTypeChange(ScrollableList chartType) {
        chartType.onChange(callbackEvent -> chartTypeIndex = (int) chartType.getValue());
    }

    static void teamFieldChange(ScrollableList teamField, ScrollableList playerStats) {
        teamField.onChange(callbackEvent -> {
            if (teamField.isMouseOver()) {
                levelIndex = 1;
                teamFieldIndex = (int) teamField.getValue();
                stats1Index = (int) playerStats.getValue();
                stats2Index = (int) playerStats.getValue();
            }
        });
    }

    static void sliderXChange(Slider sliderX) {
        sliderX.onPress(callbackEvent -> {
            try {
                ChartInteraction.tableView.updateView(null);

            } catch (Exception ignored) {

            } finally {
                setSliderX(sliderX);
            }
        });
    }

    static void sliderYChange(Slider sliderY) {
        sliderY.onPress(callbackEvent -> {
            try {
                ChartInteraction.tableView.updateView(null);

            } catch (Exception ignored) {

            } finally {
                setSliderY(sliderY);
            }
        });
    }


    static void sliderZChange(Slider sliderZ) {
        sliderZ.onPress(callbackEvent -> {
            try {
                ChartInteraction.tableView.updateView(null);

            } catch (Exception ignored) {

            } finally {
                setSliderZ(sliderZ);
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

    static void switchLevel(Button modeButton, Textlabel modeText,
                            Button levelBackButton, Textlabel levelText,
                            ScrollableList teamStats1Mode, ScrollableList teamStats2Mode, ScrollableList teamStats3Mode,
                            ScrollableList teamStats1, ScrollableList teamStats2, ScrollableList teamStats3,
                            ScrollableList playerStats1, ScrollableList playerStats2, ScrollableList playerStats3,
                            Button chartViewButton, ScrollableList chartType,
                            ScrollableList teamField,
                            Slider sliderX, Slider sliderY, Slider sliderZ) {
        switch (levelIndex) {
            case 0:
                levelBackButton.hide();
                levelText.setText("COMPETITION LEVEL");

                teamStats1Mode.show();
                teamStats1.show();

                playerStats1.hide();
                playerStats2.hide();
                playerStats3.hide();

                if (chartView) {
                    modeButton.hide();
                    modeText.hide();
                    switch (chartTypeIndex) {
                        case 0:
                            teamStats2Mode.hide();
                            teamStats2.hide();
                            teamStats3Mode.hide();
                            teamStats3.hide();
                            sliderX.show();
                            sliderY.hide();
                            sliderZ.hide();
                            break;
                        case 1:
                            teamStats2Mode.show();
                            teamStats2.show();
                            teamStats3Mode.hide();
                            teamStats3.hide();
                            sliderX.show();
                            sliderY.show();
                            sliderZ.hide();
                            break;
                        case 2:
                            teamStats2Mode.hide();
                            teamStats2.hide();
                            teamStats3Mode.hide();
                            teamStats3.hide();
                            sliderX.show();
                            sliderY.hide();
                            sliderZ.hide();
                            break;
                        case 3:
                            teamStats2Mode.show();
                            teamStats2.show();
                            teamStats3Mode.show();
                            teamStats3.show();
                            sliderX.show();
                            sliderY.show();
                            sliderZ.show();
                            break;
                    }

                    chartType.show();
                }
                if (!chartView) {
                    modeButton.show();
                    modeText.show();

                    chartType.hide();
                    teamStats2Mode.hide();
                    teamStats2.hide();
                    teamStats3Mode.hide();
                    teamStats3.hide();
                    sliderX.hide();
                    sliderY.hide();
                    sliderZ.hide();
                }
                break;
            case 1:
                modeButton.show();
                modeText.show();

                levelBackButton.show();
                levelText.setText("TEAM LEVEL");

                teamStats1Mode.hide();
                teamStats1.hide();
                teamStats2Mode.hide();
                teamStats2.hide();
                teamStats3Mode.hide();
                teamStats3.hide();

                playerStats1.show();

                chartViewButton.show();
                teamField.hide();

                if (chartView) {
                    modeButton.hide();
                    modeText.hide();

                    switch (chartTypeIndex) {
                        case 0:
                            playerStats2.hide();
                            playerStats3.hide();
                            sliderX.show();
                            sliderY.hide();
                            sliderZ.hide();
                            break;
                        case 1:
                            playerStats2.show();
                            playerStats3.hide();
                            sliderX.show();
                            sliderY.show();
                            sliderZ.hide();
                            break;
                        case 2:
                            playerStats2.hide();
                            playerStats3.hide();
                            sliderX.show();
                            sliderY.hide();
                            sliderZ.hide();
                            break;
                        case 3:
                            playerStats2.show();
                            playerStats3.show();
                            sliderX.show();
                            sliderY.show();
                            sliderZ.show();
                            break;
                    }

                    chartType.show();
                }
                if (!chartView) {
                    modeButton.show();
                    modeText.show();

                    chartType.hide();
                    playerStats2.hide();
                    playerStats3.hide();

                    sliderX.hide();
                    sliderY.hide();
                    sliderZ.hide();
                }

                break;
            case 2:
                modeButton.hide();
                modeText.hide();

                playerStats1.hide();
                playerStats2.hide();
                playerStats3.hide();

                chartViewButton.hide();
                chartType.hide();

                if (teamFieldIndex == 0) {
                    levelText.setText("FIXTURE LEVEL");
                }
                if (teamFieldIndex == 1) {
                    levelText.setText("PLAYER LEVEL");
                }

                break;
        }
    }

    private static void setSliderX(Slider sliderX) {
        if (ChartInteraction.integerDataSeries1 != null) {
            Integer min = new Integer(ChartInteraction.integerDataSeries1.minValue().toString());
            Integer max = new Integer(ChartInteraction.integerDataSeries1.maxValue().toString());
            sliderX.setRange(min, max);
            sliderX.setValue(max);
        }
    }

    private static void setSliderY(Slider sliderY) {
        if (ChartInteraction.integerDataSeries2 != null) {
            Integer min = new Integer(ChartInteraction.integerDataSeries2.minValue().toString());
            Integer max = new Integer(ChartInteraction.integerDataSeries2.maxValue().toString());
            sliderY.setRange(min, max);
            sliderY.setValue(max);
        }
    }

    private static void setSliderZ(Slider sliderZ) {
        if (ChartInteraction.integerDataSeries3 != null) {
            Integer min = new Integer(ChartInteraction.integerDataSeries3.minValue().toString());
            Integer max = new Integer(ChartInteraction.integerDataSeries3.maxValue().toString());
            sliderZ.setRange(min, max);
            sliderZ.setValue(max);
        }
    }
}