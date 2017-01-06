package Object3D;

import processing.core.PApplet;

public class Grid {
    private PApplet pApplet;
    private Integer grillesOnAxis;
    public Float[] x;
    public Float[] y;
    public Float[] z;

    public Grid(PApplet pApplet, Integer gridSize, Integer grilleSize) {
        this.pApplet = pApplet;
        grillesOnAxis = gridSize / grilleSize;

        x = new Float[grillesOnAxis * grillesOnAxis];
        y = new Float[grillesOnAxis * grillesOnAxis];
        z = new Float[grillesOnAxis * grillesOnAxis];

        for (int i = 0; i < grillesOnAxis; i++) {
            for (int j = 0; j < grillesOnAxis; j++) {

                int index = i * grillesOnAxis + j;
                x[index] = i * grilleSize.floatValue();
                y[index] = j * grilleSize.floatValue();
                z[index] = 0f;
            }
        }
    }

    private float getAlphaChannel(float v) {
        if (v < 0)
            return PApplet.map(v, -150, 0, 0, 255);
        else
            return PApplet.map(v, 0, 30, 255, 0);
    }

    public void resetZ() {
        for (int i = 0; i < grillesOnAxis * grillesOnAxis; i++) z[i] = 0f;
    }

    public void setZ(float gravityX, float gravityY, float radius) {
        for (int i = 0; i < grillesOnAxis * grillesOnAxis; i++) {
            z[i] -= (radius * 50) / PApplet.sqrt(PApplet.sq(gravityX - x[i]) + PApplet.sq(gravityY - y[i]));
        }
    }

    public void draw() {
        pApplet.strokeWeight(1);

        for (int i = 0; i < grillesOnAxis - 1; i++) {
            for (int j = 0; j < grillesOnAxis - 1; j++) {

                float x1 = x[(i) * grillesOnAxis + (j + 1)];
                float y1 = y[(i) * grillesOnAxis + (j + 1)];
                float z1 = z[(i) * grillesOnAxis + (j + 1)];
                float x2 = x[(i) * grillesOnAxis + (j)];
                float y2 = y[(i) * grillesOnAxis + (j)];
                float z2 = z[(i) * grillesOnAxis + (j)];
                float x3 = x[(i + 1) * grillesOnAxis + (j)];
                float y3 = y[(i + 1) * grillesOnAxis + (j)];
                float z3 = z[(i + 1) * grillesOnAxis + (j)];

                pApplet.stroke(40, 100, 134, getAlphaChannel(PApplet.min(z1, z2, z3)));
                pApplet.noFill();
                pApplet.beginShape();
                pApplet.vertex(x1, y1, z1);
                pApplet.vertex(x2, y2, z2);
                pApplet.vertex(x3, y3, z3);
                pApplet.endShape();
            }
        }
    }
}