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
    public static Boolean chartView = false;

    static void modeButtonClick(Button modeButton) {
        modeButton.onClick(callbackEvent -> {
            modeIndex++;
            if (modeIndex == 3) modeIndex = 0;
        });
    }

    static void levelBackButtonClick(Button levelBackButton) {
        levelBackButton.onClick(callbackEvent -> {
            levelIndex--;
        });
    }

    static void customListButtonClick(Button customListButton) {
        customListButton.onClick(callbackEvent -> {
            if (!customList) {
                if (levelIndex == 0) {
                    customListButton.setLabel("LEAGUE LIST");
                } else if (levelIndex == 1) {
                    customListButton.setLabel("PLAYER LIST");
                }
                customList = true;
            } else {
                customListButton.setLabel("CUSTOM LIST");
                customList = false;
            }
        });
    }

    static void chartViewButtonClick(Button chartViewButton) {
        chartViewButton.onClick(callbackEvent -> {
            if (!chartView) {
                chartViewButton.setLabel("VIEW SCENE");
                chartView = true;
            } else {
                chartViewButton.setLabel("VIEW CHART");
                chartView = false;
            }
        });
    }

    static void teamFieldChange(ScrollableList teamField) {
        teamField.onChange(callbackEvent -> {
            levelIndex = 1;
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
                    switch ((int) chartType.getValue()) {
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

                    switch ((int) chartType.getValue()) {
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

                if ((int) teamField.getValue() == 0) {
                    levelText.setText("FIXTURE LEVEL");
                }
                if ((int) teamField.getValue() == 1) {
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