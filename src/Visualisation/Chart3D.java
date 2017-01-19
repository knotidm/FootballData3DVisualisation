package Visualisation;

import hivis.data.DataEvent;
import hivis.data.DataListener;
import hivis.data.DataSeries;
import hivis.data.DataTable;
import org.gicentre.utils.colour.ColourTable;
import peasy.PeasyCam;
import processing.core.PApplet;

public class Chart3D implements DataListener {
    private PApplet pApplet;
    private final DataTable dataTable;
    private DataSeries<Integer> dataSeriesX;
    private DataSeries<Integer> dataSeriesY;
    private DataSeries<Integer> dataSeriesZ;

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
            dataSeriesX = dataTable.getSeries(0).asInt();
            dataSeriesY = dataTable.getSeries(1).asInt();
            dataSeriesZ = dataTable.getSeries(2).asInt();
            colourTable = ColourTable.getPresetColourTable(ColourTable.YL_OR_RD, dataSeriesX.minValue(), dataSeriesX.maxValue());

            colours = new int[dataSeriesX.length()];

            for (int i = 0; i < dataSeriesX.length(); i++) {
                colours[i] = colourTable.findColour(dataSeriesX.getInt(i));
            }
        }
    }

    public void draw(PeasyCam peasyCam) {
        synchronized (dataTable) {
            if (dataTable.length() != 0) {

                pApplet.scale(10, 10, 10);
                pApplet.strokeWeight(0.1f);
                for (int i = 0; i < dataTable.length(); i++) {
                    float x = dataSeriesX.getInt(i);
                    float y = dataSeriesY.getInt(i);
                    float z = dataSeriesZ.getInt(i);

                    pApplet.fill(colours[i]);
                    pApplet.pushMatrix();

                    pApplet.translate(x, y, z / 2);
                    pApplet.box(1, 1, z);
                    pApplet.popMatrix();
                }
                pApplet.noFill();
                pApplet.stroke(255);
                Integer xAxis = dataSeriesX.maxValue();
                Integer yAxis = dataSeriesY.maxValue();
                Integer zAxis = dataSeriesZ.maxValue();

//                for (int x = 0; x < xAxis + 2; x++) {
//                    for (int y = 0; y < yAxis + 2; y++) {
//                        for (int z = 0; z < zAxis + 1; z++) {
//                            pApplet.point(x - 1, y - 1, z);
//                        }
//                    }
//                }
//                pApplet.translate(xAxis / 2, yAxis / 2, zAxis / 2);
//
//                pApplet.box(xAxis + 2, yAxis + 2, zAxis);

                pApplet.strokeWeight(0.4f);
//                pApplet.line(-(xAxis / 2), -(yAxis / 2), -(zAxis / 2), xAxis / 2, -(yAxis / 2), -(zAxis / 2));
                pApplet.line(0, 0, 0, xAxis + 1, 0, 0);
                pApplet.line(0, 0, 0, 0, yAxis + 1, 0);
                pApplet.line(0, 0, 0, 0, 0, zAxis + 1);

//                pApplet.scale(0.1f, 0.1f, 0.1f);
//                Misc.onFrontOfPeasyCam(pApplet, peasyCam);
                pApplet.text("X", xAxis + 1, 0, 0);
                pApplet.text("Y", 0, yAxis + 1, 0);
                pApplet.text("Z", 0, 0, zAxis + 1);
            }
        }
    }
}
