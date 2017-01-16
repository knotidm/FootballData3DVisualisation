package Visualisation;

import hivis.data.DataEvent;
import hivis.data.DataListener;
import hivis.data.DataSeries;
import hivis.data.DataTable;
import hivis.data.view.CalcSeries;
import org.gicentre.utils.colour.ColourTable;
import processing.core.PApplet;

public class Chart3D implements DataListener {
    private PApplet pApplet;
    private final DataTable dataTable;
    private DataSeries<Integer> x;
    private DataSeries<Integer> y;
    private DataSeries<Integer> z;

    public static CalcSeries.DoubleSeries func;
    private ColourTable colourTable;
    private int[] colours;

    public Chart3D(PApplet pApplet, DataTable dataTable) {
        this.pApplet = pApplet;
        this.dataTable = dataTable;
        dataTable.addChangeListener(this);

        updateData();
    }

    @Override
    public void dataChanged(DataEvent dataEvent) {
        updateData();
    }

    private void updateData() {
        if (dataTable.length() != 0) {
            x = dataTable.getSeries(0).asInt();
            y = dataTable.getSeries(1).asInt();
            z = dataTable.getSeries(2).asInt();
            colourTable = ColourTable.getPresetColourTable(ColourTable.YL_OR_RD, x.minValue(), x.maxValue());

            colours = new int[x.length()];

            for (int i = 0; i < x.length(); i++) {
                colours[i] = colourTable.findColour(x.getInt(i));
            }

//            func = new CalcSeries.DoubleSeries<Integer>(x, y) {
//                public double calcDouble(int index) {
//                    double x = inputSeries.get(0).get(index);
//                    double y = inputSeries.get(1).get(index);
//
//                    double xPow = Math.pow(x, Event.);
//                    double yPow = Math.pow(y, Event.sliderParamValue);
//
//                    return 1 / (Math.sin(xPow) + Math.cos(yPow) + 1);
//                }
//            };
        }
    }

    public void draw(float width, float height) {
        if (dataTable.length() != 0) {
            synchronized (dataTable) {
                pApplet.scale(10, 10);

                for (int row = 0; row < dataTable.length(); row++) {
                    float xCoord = x.getInt(row);
                    float yCoord = y.getInt(row);
                    float zCoord = z.getInt(row);


//                    float value = func.getFloat(row);

                    pApplet.fill(colours[row]);
                    pApplet.pushMatrix();

                    pApplet.translate(xCoord, yCoord, zCoord * 2);
                    pApplet.box(1, 1, zCoord * 4);
//                pApplet.rect(0, 0, 0.1f, 0.1f);
                    pApplet.popMatrix();
                }
            }
        }
    }
}
