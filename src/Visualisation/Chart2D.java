package Visualisation;

import hivis.common.HVDraw;
import hivis.data.DataEvent;
import hivis.data.DataListener;
import hivis.data.DataSeries;
import hivis.data.DataTable;
import org.gicentre.utils.colour.ColourTable;
import org.gicentre.utils.stat.BarChart;
import org.gicentre.utils.stat.XYChart;
import processing.core.PApplet;

import static processing.core.PConstants.PIE;

public class Chart2D implements DataListener {
    private PApplet pApplet;
    private final DataTable dataTable;
    private Integer type;
    private final BarChart barChart;
    private DataSeries dataSeries;
    private ColourTable colourTable;
    private float[] xValues, yValues;
    private float[] angles;
    private final XYChart xychart;
    private int[] colours;

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
                dataSeries = dataTable.getSeries(0);
                barChart.setData(dataSeries.asFloatArray());
                colourTable = ColourTable.getPresetColourTable(ColourTable.YL_OR_RD, barChart.getMinValue(), barChart.getMaxValue());
                barChart.setBarColour(dataSeries.asFloatArray(), colourTable);
                barChart.setBarLabels(dataTable.getSeries(1).asStringArray());

                colours = new int[dataSeries.length()];

                for (int i = 0; i < dataSeries.length(); i++) {
                    colours[i] = colourTable.findColour(dataSeries.getInt(i));
                }

                break;
            case 1:
                dataSeries = dataTable.getSeries(0);
                int size = dataTable.length();
                if (xValues == null || xValues.length != size) {
                    xValues = new float[size];
                    yValues = new float[size];
                }
                xValues = dataSeries.asFloatArray();
                yValues = dataTable.getSeries(1).asFloatArray();
                xychart.setXAxisLabel(dataTable.getSeriesLabel(0));
                xychart.setYAxisLabel(dataTable.getSeriesLabel(1));
                xychart.setData(xValues, yValues);
                colourTable = ColourTable.getPresetColourTable(ColourTable.YL_OR_RD, xychart.getMinY(), xychart.getMaxX());

                colours = new int[dataSeries.length()];

                for (int i = 0; i < dataSeries.length(); i++) {
                    colours[i] = colourTable.findColour(dataSeries.getInt(i));
                }

                xychart.setPointColour(xValues, colourTable);
                break;
            case 2:
                dataSeries = dataTable.getSeries(0);
                float min = PApplet.min(dataSeries.asFloatArray()) - 1;
                float max = PApplet.max(dataSeries.asFloatArray());
                colourTable = ColourTable.getPresetColourTable(ColourTable.YL_OR_RD, min, max);

                colours = new int[dataSeries.length()];
                angles = new float[dataSeries.length()];

                int seriesSum = 0;
                for (int i = 0; i < dataSeries.length(); i++) {
                    seriesSum += dataSeries.getInt(i);
                }

                for (int i = 0; i < dataSeries.length(); i++) {
                    colours[i] = colourTable.findColour(dataSeries.getInt(i));
                    angles[i] = (dataSeries.getFloat(i) / seriesSum) * 360;
                }

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
                case 2:
                    HVDraw.pie(pApplet, dataSeries, 600, width / 2f, height / 2f, colours, 0);
                    pApplet.noFill();
                    pApplet.stroke(0);
                    float start = 0;
                    float stop = 0;
                    for (int i = 0; i < dataSeries.length(); i++) {
                        stop += PApplet.radians(angles[i]);
                        pApplet.arc(width / 2, height / 2, 600, 600, start, stop, PIE);
                        start = stop;
                    }

                    pApplet.textSize(14);
                    for (int i = 0; i < colours.length; i++) {
                        pApplet.fill(colours[i]);
                        int y = i * 20;
                        pApplet.rect(20, 20 + y, 40, 20);
                        pApplet.text(dataTable.getSeries(1).asStringArray()[i], 140, 37 + y);
                    }
                    break;
            }
        }
    }
}
