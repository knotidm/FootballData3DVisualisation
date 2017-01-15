package UI;

import Interaction.ChartInteraction;
import Visualisation.Chart3D;
import controlP5.Button;
import controlP5.ScrollableList;
import controlP5.Slider;
import controlP5.Textlabel;

public class Event {
    public static Integer modeIndex = 0;
    public static Integer levelIndex = 0;
    public static Integer competitionIndex = 0;
    public static Integer teamStats1ModeIndex = 0;
    public static Integer teamStats2ModeIndex = 0;
    public static Integer stats1Index = 0;
    public static Integer stats2Index = 0;
    public static Boolean chartView = false;
    public static Integer chartTypeIndex = 0;
    public static Integer teamFieldIndex = 0;
    public static Integer clickedObjects3D = 0;
    public static Float sliderParamValue = 0.0f;

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

    static void competitionChange(ScrollableList competition) {
        competition.onChange(callbackEvent -> competitionIndex = (int) competition.getValue());
    }

    static void teamStats1ModeChange(ScrollableList teamStats1Mode) {
        teamStats1Mode.onChange(callbackEvent -> teamStats1ModeIndex = (int) teamStats1Mode.getValue());
    }

    static void teamStats2ModeChange(ScrollableList teamStats2Mode) {
        teamStats2Mode.onChange(callbackEvent -> teamStats2ModeIndex = (int) teamStats2Mode.getValue());
    }

    static void stats1Change(ScrollableList stats1) {
        stats1.onChange(callbackEvent -> stats1Index = (int) stats1.getValue());
    }

    static void stats2Change(ScrollableList stats2) {
        stats2.onChange(callbackEvent -> stats2Index = (int) stats2.getValue());
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
                            ScrollableList teamStats1Mode, ScrollableList teamStats2Mode,
                            ScrollableList teamStats1, ScrollableList teamStats2,
                            ScrollableList playerStats1, ScrollableList playerStats2,
                            Button chartViewButton, ScrollableList chartType,
                            ScrollableList teamField,
                            Slider sliderX, Slider sliderY, Slider sliderParam) {
        switch (levelIndex) {
            case 0:
                levelBackButton.hide();
                levelText.setText("COMPETITION LEVEL");

                teamStats1Mode.show();
                teamStats1.show();

                playerStats1.hide();
                playerStats2.hide();

                if (chartView) {
                    modeButton.hide();
                    modeText.hide();
                    switch (chartTypeIndex) {
                        case 0:
                            teamStats2Mode.hide();
                            teamStats2.hide();
                            sliderX.show();
                            sliderY.hide();
                            sliderParam.hide();
                            break;
                        case 1:
                            teamStats2Mode.show();
                            teamStats2.show();
                            sliderX.show();
                            sliderY.show();
                            sliderParam.hide();
                            break;
                        case 2:
                            teamStats2Mode.hide();
                            teamStats2.hide();
                            sliderX.show();
                            sliderY.hide();
                            sliderParam.hide();
                            break;
                        case 3:
                            teamStats2Mode.show();
                            teamStats2.show();
                            sliderX.hide();
                            sliderY.hide();
                            sliderParam.show();
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
                    sliderX.hide();
                    sliderY.hide();
                    sliderParam.hide();
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

                playerStats1.show();

                chartViewButton.show();
                teamField.hide();

                if (chartView) {
                    modeButton.hide();
                    modeText.hide();

                    switch (chartTypeIndex) {
                        case 0:
                            playerStats2.hide();
                            sliderX.show();
                            sliderY.hide();
                            sliderParam.hide();
                            break;
                        case 1:
                            playerStats2.show();
                            sliderX.show();
                            sliderY.show();
                            sliderParam.hide();
                            break;
                        case 2:
                            playerStats2.hide();
                            sliderX.show();
                            sliderY.hide();
                            sliderParam.hide();
                            break;
                        case 3:
                            playerStats2.show();
                            sliderX.hide();
                            sliderY.hide();
                            sliderParam.show();
                            break;
                    }

                    chartType.show();
                }
                if (!chartView) {
                    modeButton.show();
                    modeText.show();

                    chartType.hide();
                    playerStats2.hide();
                    sliderX.hide();
                    sliderY.hide();
                    sliderParam.hide();
                }

                break;
            case 2:
                modeButton.hide();
                modeText.hide();

                playerStats1.hide();
                playerStats2.hide();

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

    static void sliderParamChange(Slider sliderParam) {
        sliderParam.onChange(callbackEvent -> {
            sliderParamValue = sliderParam.getValue();
            if (Chart3D.func != null) {
                Chart3D.func.updateView(null);
            }
        });
    }
}