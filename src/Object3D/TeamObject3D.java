package Object3D;

import Util.IDraggable;
import Util.Util;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Vec3D;

public class TeamObject3D extends Particle implements IDraggable {
    private PApplet pApplet;
    public Integer index;
    private boolean isSelected;
    public String teamName;
    public String squadMarketValue;

    public TeamObject3D(PApplet pApplet, Integer index, Vec3D location, Integer size, String teamName, String squadMarketValue) {
        super(pApplet, location, size);
        this.pApplet = pApplet;
        this.index = index;
        this.teamName = teamName;
        this.squadMarketValue = squadMarketValue;
    }

    @Override
    public void isSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public void draw(PeasyCam peasyCam) {

        pApplet.pushMatrix();

        pApplet.noFill();
        pApplet.stroke(255, 0, 0);
        if (isSelected) pApplet.stroke(255, 150, 0);
        super.draw(peasyCam);

        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        pApplet.textAlign(PConstants.CENTER);
        pApplet.textSize(20);
        pApplet.fill(255);
        Util.alwaysOnFrontOfPeasyCam(pApplet, peasyCam);
        pApplet.text(teamName, 0, -size, 0);
        pApplet.text(squadMarketValue, 0, 4 * -size, 0);

        pApplet.popMatrix();
    }
}
