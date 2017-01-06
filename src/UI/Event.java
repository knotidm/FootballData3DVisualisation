package UI;

import Interaction.ChartInteraction;
import controlP5.Button;
import controlP5.ScrollableList;
import controlP5.Slider;
import controlP5.Textlabel;

public class Event {
    public static Integer modeIndex = 0;
    public static Integer levelIndex = 0;
    public static Integer competitionIndex = 0;
    public static Integer teamFilterModeIndex = 0;
    public static Integer filterIndex1 = 0;
    public static Boolean chartView = false;
    public static Integer chart2DTypeIndex = 0;
    public static Integer filterIndex2 = 0;
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
            filterIndex1 = 0;
            filterIndex2 = 0;
        });
    }

    static void competitionChange(ScrollableList competition) {
        competition.onChange(callbackEvent -> competitionIndex = (int) competition.getValue());
    }

    static void teamFilterModeChange(ScrollableList teamFilterMode) {
        teamFilterMode.onChange(callbackEvent -> teamFilterModeIndex = (int) teamFilterMode.getValue());
    }

    static void filterChange1(ScrollableList filter) {
        filter.onChange(callbackEvent -> filterIndex1 = (int) filter.getValue());
    }

    static void filterChange2(ScrollableList filter) {
        filter.onChange(callbackEvent -> filterIndex2 = (int) filter.getValue());
    }

    static void chartTypeChange(ScrollableList chart2DType) {
        chart2DType.onChange(callbackEvent -> chart2DTypeIndex = (int) chart2DType.getValue());
    }

    static void chartViewButtonClick(Button chartViewButton, Button modeButton, Textlabel modeText, ScrollableList chart2DType) {
        chartViewButton.onClick(callbackEvent -> {
            if (!chartView) {
                chartViewButton.setLabel("VIEW SCENE");
                modeButton.hide();
                modeText.hide();
                chart2DType.show();
                chartView = true;
            } else {
                chartViewButton.setLabel("VIEW CHART");
                modeButton.show();
                modeText.show();
                chart2DType.hide();
                chartView = false;
            }
        });
    }

    static void teamFieldChange(ScrollableList teamField, ScrollableList playerFilter) {
        teamField.onChange(callbackEvent -> {
            if (teamField.isMouseOver()) {
                levelIndex = 1;
                teamFieldIndex = (int) teamField.getValue();
                filterIndex1 = (int) playerFilter.getValue();
                filterIndex2 = (int) playerFilter.getValue();
            }
        });
    }

    static void sliderXChange(Slider sliderX) {
        sliderX.onPress(callbackEvent -> {
            ChartInteraction.tableView.updateView(null);
            setSliderX(sliderX);
        });
    }

    static void sliderYChange(Slider sliderY) {
        sliderY.onPress(callbackEvent -> {
            ChartInteraction.tableView.updateView(null);
            setSliderY(sliderY);
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

    static void switchLevel(Button modeButton, Textlabel modeText, Button levelBackButton, Textlabel levelText, ScrollableList teamFilterMode, ScrollableList teamFilter1, ScrollableList teamFilter2, ScrollableList playerFilter1, ScrollableList playerFilter2, Button chartViewButton, ScrollableList teamField) {
        switch (levelIndex) {
            case 0:
                levelBackButton.hide();
                levelText.setText("COMPETITION LEVEL");
                teamFilterMode.show();
                teamFilter1.show();
                if (chartView && chart2DTypeIndex == 1) {
                    teamFilter2.show();
                } else teamFilter2.hide();
                playerFilter1.hide();
                playerFilter2.hide();
                break;
            case 1:
                modeButton.show();
                modeText.show();
                levelBackButton.show();
                levelText.setText("TEAM LEVEL");
                teamFilterMode.hide();
                teamFilter1.hide();
                teamFilter2.hide();
                if (teamFieldIndex == 1) {
                    playerFilter1.show();
                    if (chartView && chart2DTypeIndex == 1) {
                        playerFilter2.show();
                    } else playerFilter2.hide();
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
                    playerFilter1.hide();
                    playerFilter2.hide();
                }
                chartViewButton.hide();
                break;
        }
    }

    private static void setSliderX(Slider sliderX) {
        if (ChartInteraction.filterDataSeries1 != null) {
            Integer min = new Integer(ChartInteraction.filterDataSeries1.minValue().toString());
            Integer max = new Integer(ChartInteraction.filterDataSeries1.maxValue().toString());
            sliderX.setRange(min, max);
            sliderX.setValue(max);
        }
    }

    private static void setSliderY(Slider sliderY) {
        if (ChartInteraction.filterDataSeries2 != null) {
            Integer min = new Integer(ChartInteraction.filterDataSeries2.minValue().toString());
            Integer max = new Integer(ChartInteraction.filterDataSeries2.maxValue().toString());
            sliderY.setRange(min, max);
            sliderY.setValue(max);
        }
    }
}