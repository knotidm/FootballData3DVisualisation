package Interaction;

import Model.Stats;
import Model.Player;
import Model.Team;
import Object3D.Object3D;
import UI.UserInterface;
import Visualisation.Chart2D;
import Visualisation.Chart3D;
import hivis.common.HV;
import hivis.data.DataSeries;
import hivis.data.DataTable;
import hivis.data.view.TableView;
import org.gicentre.utils.colour.ColourTable;
import processing.core.PApplet;

import java.util.ArrayList;

public class ChartInteraction<T> {
    private DataSeries<String> nameDataSeries;
    private DataTable dataTable;
    public static DataSeries<Integer> integerDataSeries1;
    public static DataSeries<Integer> integerDataSeries2;
    public static DataSeries<Integer> integerDataSeries3;
    public static TableView tableView;
    private ColourTable colourTable;
    public static int[] colours;

    public Chart2D getChart2D(PApplet pApplet, UserInterface userInterface, ArrayList<Object3D<T>> objects3D1, Stats stats1, Stats stats2, Integer chart2DType) {
        switch (chart2DType) {
            case 0:
                createDataSeries1(objects3D1);
                createColours();

                dataTable = HV.newTable().addSeries(stats1.name, integerDataSeries1).addSeries("name", nameDataSeries);
                tableView = dataTable.selectRows((input, index) -> input.getSeries(0).getInt(index) > userInterface.sliderX.getValue());
                if (tableView.length() != 0) {
                    return new Chart2D(pApplet, tableView, chart2DType);
                } else return new Chart2D(pApplet, dataTable, chart2DType);
            case 1:
                createDataSeries2(stats1, stats2);
                createColours();

                if (stats1.name.equals(stats2.name)) {
                    stats2.name = stats2.name + '.';
                }
                dataTable = HV.newTable().addSeries(stats1.name, integerDataSeries1).addSeries(stats2.name, integerDataSeries2);
                tableView = dataTable.selectRows((input, index) -> input.getSeries(0).getInt(index) > userInterface.sliderX.getValue());
                tableView = tableView.selectRows((input, index) -> input.getSeries(1).getInt(index) > userInterface.sliderY.getValue());
                return new Chart2D(pApplet, tableView, chart2DType);
            case 2:
                createDataSeries1(objects3D1);
                createColours();

                dataTable = HV.newTable().addSeries(stats1.name, integerDataSeries1).addSeries("name", nameDataSeries);
                tableView = dataTable.selectRows((input, index) -> input.getSeries(0).getInt(index) > userInterface.sliderX.getValue());
                if (tableView.length() != 0) {
                    return new Chart2D(pApplet, tableView, chart2DType);
                } else return new Chart2D(pApplet, dataTable, chart2DType);
            default:
                return null;
        }
    }

    public Chart3D getChart3D(PApplet pApplet, UserInterface userInterface, Stats stats1, Stats stats2, Stats stats3, Integer chart2DType) {
        if (chart2DType == 3) {
            createDataSeries2(stats1, stats2);
            integerDataSeries3 = HV.newIntegerSeries();
            for (Integer stat : stats3.values) {
                integerDataSeries3.append(stat);
            }

            if (stats1.name.equals(stats2.name)) {
                stats2.name = stats2.name + '.';
            }
            if (stats1.name.equals(stats3.name) || stats2.name.equals(stats3.name)) {
                stats3.name = stats3.name + "..";
            }
            dataTable = HV.newTable().addSeries(stats1.name, integerDataSeries1).addSeries(stats2.name, integerDataSeries2).addSeries(stats3.name, integerDataSeries3);
            tableView = dataTable.selectRows((input, index) -> input.getSeries(0).getInt(index) > userInterface.sliderX.getValue());
            tableView = tableView.selectRows((input, index) -> input.getSeries(1).getInt(index) > userInterface.sliderY.getValue());
            tableView = tableView.selectRows((input, index) -> input.getSeries(2).getInt(index) > userInterface.sliderZ.getValue());

            return new Chart3D(pApplet, tableView);
        } else return null;
    }

    private void createDataSeries1(ArrayList<Object3D<T>> objects3D1) {
        integerDataSeries1 = HV.newIntegerSeries();
        nameDataSeries = HV.newSeries();

        for (Object3D<T> object3D : objects3D1) {
            integerDataSeries1.append(object3D.statsValue);
            if (object3D.type.getClass() == Team.class) {
                Team team = (Team) object3D.type;
                nameDataSeries.append(team.name);
            } else if (object3D.type.getClass() == Player.class) {
                Player player = (Player) object3D.type;
                nameDataSeries.append(player.name);
            }
        }
    }

    private void createDataSeries2(Stats stats1, Stats stats2) {
        integerDataSeries1 = HV.newIntegerSeries();
        integerDataSeries2 = HV.newIntegerSeries();

        for (Integer stat : stats1.values) {
            integerDataSeries1.append(stat);
        }

        for (Integer stat : stats2.values) {
            integerDataSeries2.append(stat);
        }
    }

    private void createColours() {
        colourTable = ColourTable.getPresetColourTable(ColourTable.YL_OR_RD, integerDataSeries1.minValue(), integerDataSeries1.maxValue());
        colours = new int[integerDataSeries1.length()];
        for (int i = 0; i < integerDataSeries1.length(); i++) {
            colours[i] = colourTable.findColour(integerDataSeries1.getInt(i));
        }
    }
}