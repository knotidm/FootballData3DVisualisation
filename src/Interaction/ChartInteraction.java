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
import processing.core.PApplet;

import java.util.ArrayList;

public class ChartInteraction<T> {
    private DataSeries<String> nameDataSeries;
    private DataTable dataTable;
    public static DataSeries<Integer> filterDataSeries1;
    public static DataSeries<Integer> filterDataSeries2;
    public static TableView tableView;

    public Chart2D getChart2D(PApplet pApplet, UserInterface userInterface, ArrayList<Object3D<T>> objects3D1, ArrayList<Object3D<T>> objects3D2, Stats stats1, Stats stats2, Integer chart2DType) {
        switch (chart2DType) {
            case 0:
                CreateDataSeries1(objects3D1);
                dataTable = HV.newTable().addSeries(stats1.name, filterDataSeries1).addSeries("name", nameDataSeries);
                tableView = dataTable.selectRows((input, index) -> input.getSeries(0).getInt(index) > userInterface.sliderX.getValue());
                if (tableView.length() != 0) {
                    return new Chart2D(pApplet, tableView, chart2DType);
                } else return new Chart2D(pApplet, dataTable, chart2DType);
            case 1:
                CreateDataSeries2(objects3D1, objects3D2);
                if (stats1.name.equals(stats2.name)) {
                    stats2.name = stats2.name + '.';
                }
                dataTable = HV.newTable().addSeries(stats1.name, filterDataSeries1).addSeries(stats2.name, filterDataSeries2);
//                tableView = dataTable.selectRows((input, index) -> input.getSeries(0).getInt(index) > userInterface.sliderX.getValue());
                tableView = dataTable.selectRows((input, index) -> input.getSeries(1).getInt(index) > userInterface.sliderY.getValue());
                return new Chart2D(pApplet, tableView, chart2DType);
            case 2:
                CreateDataSeries1(objects3D1);
                dataTable = HV.newTable().addSeries(stats1.name, filterDataSeries1).addSeries("name", nameDataSeries);
                tableView = dataTable.selectRows((input, index) -> input.getSeries(0).getInt(index) > userInterface.sliderX.getValue());
                if (tableView.length() != 0) {
                    return new Chart2D(pApplet, tableView, chart2DType);
                } else return new Chart2D(pApplet, dataTable, chart2DType);
            default:
                return null;
        }
    }

    public Chart3D getChart3D(PApplet pApplet, ArrayList<Object3D<T>> objects3D1, ArrayList<Object3D<T>> objects3D2, Stats stats1, Stats stats2, Integer chart2DType) {
        if (chart2DType == 3) {
            CreateDataSeries2(objects3D1, objects3D2);
            if (stats1.name.equals(stats2.name)) {
                stats2.name = stats2.name + '.';
            }
            dataTable = HV.newTable().addSeries(stats1.name, filterDataSeries1).addSeries(stats2.name, filterDataSeries2);
            return new Chart3D(pApplet, dataTable);
        } else return null;
    }

    private void CreateDataSeries1(ArrayList<Object3D<T>> objects3D1) {
        filterDataSeries1 = HV.newIntegerSeries();
        nameDataSeries = HV.newSeries();

        for (Object3D<T> object3D : objects3D1) {
            filterDataSeries1.append(object3D.filterValue);
            if (object3D.type.getClass() == Team.class) {
                Team team = (Team) object3D.type;
                nameDataSeries.append(team.name);
            } else if (object3D.type.getClass() == Player.class) {
                Player player = (Player) object3D.type;
                nameDataSeries.append(player.name);
            }
        }
    }

    private void CreateDataSeries2(ArrayList<Object3D<T>> objects3D1, ArrayList<Object3D<T>> objects3D2) {
        filterDataSeries1 = HV.newIntegerSeries();
        filterDataSeries2 = HV.newIntegerSeries();

        for (Object3D<T> object3D : objects3D1) {
            filterDataSeries1.append(object3D.filterValue);
        }

        for (Object3D<T> object3D : objects3D2) {
            filterDataSeries2.append(object3D.filterValue);
        }
    }
}