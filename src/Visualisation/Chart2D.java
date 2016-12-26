package Visualisation;

import hivis.data.DataEvent;
import hivis.data.DataListener;
import hivis.data.DataTable;
import org.gicentre.utils.stat.BarChart;
import org.gicentre.utils.stat.XYChart;
import processing.core.PApplet;

public class Chart2D implements DataListener {
    final XYChart xychart;
    final BarChart barChart;
    private final DataTable dataTable;
    Integer type;
    private float[] xValues, yValues;

    public Chart2D(PApplet pApplet, DataTable dataTable, Integer type) {
        this.dataTable = dataTable;
        dataTable.addChangeListener(this);
        this.type = type;

        xychart = new XYChart(pApplet);
        xychart.showXAxis(true);
        xychart.showYAxis(true);

        barChart = new BarChart(pApplet);
        barChart.showValueAxis(true);
        barChart.setValueFormat("#");
        barChart.showCategoryAxis(true);
        barChart.setBarColour(pApplet.color(200, 80, 80, 150));
        barChart.setBarGap(2);

        barChart.transposeAxes(true);

        updateData();
    }

    @Override
    public void dataChanged(DataEvent dataEvent) {
        updateData();
    }

    private void updateData() {
        switch (type) {
            case 0:
                int size = dataTable.length();
                if (xValues == null || xValues.length != size) {
                    xValues = new float[size];
                    yValues = new float[size];
                }
                xValues = dataTable.getSeries(0).asFloatArray();
                yValues = dataTable.getSeries(1).asFloatArray();
                xychart.setXAxisLabel(dataTable.getSeriesLabel(0));
                xychart.setYAxisLabel(dataTable.getSeriesLabel(1));
                xychart.setData(xValues, yValues);
                break;
            case 1:
                barChart.setData(dataTable.getSeries(1).asFloatArray());
                barChart.setBarLabels(dataTable.getSeries(0).asStringArray());
                break;
        }
    }

    public void draw(float width, float height) {
        synchronized (dataTable) {
            switch (type) {
                case 0:
                    xychart.draw(200, 20, width - 220, height - 40);
                    break;
                case 1:
                    barChart.draw(200, 20, width - 220, height - 40);
                    break;
            }
        }
    }
}
