package Interaction;

import Model.Filter;
import Model.Player;
import Model.Team;
import Object3D.Object3D;
import UI.UserInterface;
import Visualisation.Chart2D;
import hivis.common.HV;
import hivis.data.DataSeries;
import hivis.data.DataTable;
import hivis.data.view.TableView;
import processing.core.PApplet;

import java.util.ArrayList;

public class ChartInteraction<T> {
    private DataSeries<String> nameDataSeries;
    private DataSeries<Integer> filterDataSeries;
    public static DataSeries<Integer> filterDataSeries1;
    public static DataSeries<Integer> filterDataSeries2;
    private DataTable dataTable;
    public static TableView tableView;

    public Chart2D getChart2D(PApplet pApplet, UserInterface userInterface, ArrayList<Object3D<T>> objects3D1, ArrayList<Object3D<T>> objects3D2, Filter filter1, Filter filter2, Integer chart2DType) {
        switch (chart2DType) {
            case 0:
                nameDataSeries = HV.newSeries();
                filterDataSeries = HV.newIntegerSeries();

                CreateDataSeries(objects3D1);

                dataTable = HV.newTable()
                        .addSeries("name", nameDataSeries)
                        .addSeries(filter1.name, filterDataSeries);

                return new Chart2D(pApplet, dataTable, chart2DType);

            case 1:
                filterDataSeries1 = HV.newIntegerSeries();
                filterDataSeries2 = HV.newIntegerSeries();

                for (Object3D<T> object3D : objects3D1) {
                    filterDataSeries1.append(object3D.filterValue);
                }
                for (Object3D<T> object3D : objects3D2) {
                    filterDataSeries2.append(object3D.filterValue);
                }

                if (filter1.name.equals(filter2.name)) {
                    filter2.name = filter2.name + '.';
                }

                dataTable = HV.newTable()
                        .addSeries(filter1.name, filterDataSeries1)
                        .addSeries(filter2.name, filterDataSeries2);

//                tableView = dataTable.selectRows((input, index) -> input.getSeries(0).getInt(index) > userInterface.sliderX.getValue());
                tableView = dataTable.selectRows((input, index) -> input.getSeries(1).getInt(index) > userInterface.sliderY.getValue());

                return new Chart2D(pApplet, tableView, chart2DType);
            case 2:
                nameDataSeries = HV.newSeries();
                filterDataSeries = HV.newIntegerSeries();

                CreateDataSeries(objects3D1);

                dataTable = HV.newTable()
                        .addSeries("name", nameDataSeries)
                        .addSeries(filter1.name, filterDataSeries);

                return new Chart2D(pApplet, dataTable, chart2DType);
            default:
                return null;
        }
    }

    private void CreateDataSeries(ArrayList<Object3D<T>> objects3D1) {
        for (Object3D<T> object3D : objects3D1) {
            if (object3D.type.getClass() == Team.class) {
                Team team = (Team) object3D.type;
                nameDataSeries.append(team.name);
            } else if (object3D.type.getClass() == Player.class) {
                Player player = (Player) object3D.type;
                nameDataSeries.append(player.name);
            }
            filterDataSeries.append(object3D.filterValue);
        }
    }
}