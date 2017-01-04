package Visualisation;

import hivis.data.DataEvent;
import hivis.data.DataListener;
import hivis.data.DataTable;
import org.gicentre.utils.colour.ColourTable;
import org.gicentre.utils.stat.BarChart;
import org.gicentre.utils.stat.XYChart;
import processing.core.PApplet;

public class Chart2D implements DataListener {
    PApplet pApplet;
    public final XYChart xychart;
    public final BarChart barChart;
    private final DataTable dataTable;
    Integer type;
    private float[] xValues, yValues;
    public ColourTable colourTable;

    public Chart2D(PApplet pApplet, DataTable dataTable, Integer type) {
        this.pApplet = pApplet;
        this.dataTable = dataTable;
        dataTable.addChangeListener(this);
        this.type = type;

        barChart = new BarChart(pApplet);
        barChart.showValueAxis(true);
        barChart.setValueFormat("#");
        barChart.showCategoryAxis(true);
        barChart.setBarGap(2);
        barChart.transposeAxes(true);

        xychart = new XYChart(pApplet);
        xychart.showXAxis(true);
        xychart.showYAxis(true);
        xychart.setXFormat("#");
        xychart.setYFormat("#");

        updateData();
    }

    @Override
    public void dataChanged(DataEvent dataEvent) {
        updateData();
    }

    private void updateData() {
        switch (type) {
            case 0:
                barChart.setData(dataTable.getSeries(1).asFloatArray());
                colourTable = ColourTable.getPresetColourTable(ColourTable.YL_OR_RD, barChart.getMinValue(), barChart.getMaxValue());
                barChart.setBarColour(dataTable.getSeries(1).asFloatArray(), colourTable);
                barChart.setBarLabels(dataTable.getSeries(0).asStringArray());
                break;
            case 1:
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
                colourTable = ColourTable.getPresetColourTable(ColourTable.YL_OR_RD, xychart.getMinX(), xychart.getMaxX());
                xychart.setPointColour(xValues, colourTable);
                break;
        }
    }

    public void draw(float width, float height) {
        synchronized (dataTable) {
            switch (type) {
                case 0:
                    barChart.draw(120, 60, width - 220, height - 100);
                    break;
                case 1:
                    xychart.draw(120, 60, width - 220, height - 100);
                    break;
            }
        }
    }
}
