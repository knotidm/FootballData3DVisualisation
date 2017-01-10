package Visualisation;

import UI.Event;
import hivis.data.DataEvent;
import hivis.data.DataListener;
import hivis.data.DataSeries;
import hivis.data.DataTable;
import hivis.data.view.CalcSeries;
import processing.core.PApplet;

public class Chart3D implements DataListener {
    private PApplet pApplet;
    private DataTable dataTable;
    private DataSeries<Double> x;
    private DataSeries<Double> y;
    public static CalcSeries.DoubleSeries func;

    public Chart3D(PApplet pApplet, DataTable dataTable) {
        this.pApplet = pApplet;
        this.dataTable = dataTable;
        dataTable.addChangeListener(this);

        x = dataTable.getSeries(0).asDouble();
        y = dataTable.getSeries(1).asDouble();

        updateData();
    }

    @Override
    public void dataChanged(DataEvent dataEvent) {
        updateData();
    }

    private void updateData() {
        func = new CalcSeries.DoubleSeries<Double>(x, y) {
            public double calcDouble(int index) {
                double x = inputSeries.get(0).get(index);
                double y = inputSeries.get(1).get(index);

                double xPow = Math.pow(x, Event.sliderParamValue);
                double yPow = Math.pow(y, Event.sliderParamValue);

                return 1 / (Math.sin(xPow) + Math.cos(yPow) + 1);
            }
        };
    }

    public void draw(float width, float height) {
        synchronized (dataTable) {
            pApplet.scale(10, 10);

            for (int row = 0; row < dataTable.length(); row++) {
                float xCoord = x.getFloat(row);
                float yCoord = y.getFloat(row);
                float value = func.getFloat(row);

                pApplet.fill(PApplet.abs(value % 1), 1, 1);

                pApplet.pushMatrix();

                pApplet.translate(xCoord, yCoord, value);
                pApplet.rect(0, 0, 0.01f, 0.01f);
                pApplet.popMatrix();
            }
        }
    }
}
