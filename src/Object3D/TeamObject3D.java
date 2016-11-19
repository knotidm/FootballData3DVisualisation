package Object3D;

import Util.Util;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Vec3D;

public class TeamObject3D extends Particle {
    private PApplet pApplet;
    public Integer index;
    public String teamName;
    public String squadMarketValue;
    public Boolean isSelected;
    public Boolean isClicked;

    public TeamObject3D(PApplet pApplet, Integer index, Vec3D location, Integer size, String teamName, String squadMarketValue) {
        super(pApplet, location, size);
        this.pApplet = pApplet;
        this.index = index;
        this.teamName = teamName;
        this.squadMarketValue = squadMarketValue;
        this.isSelected = false;
        this.isClicked = false;
    }

    @Override
    public void draw(PeasyCam peasyCam) {
        pApplet.pushMatrix();

        pApplet.noFill();
        pApplet.stroke(255, 0, 0);
        if (isSelected) pApplet.stroke(255, 150, 0);
        if (isClicked) pApplet.stroke(150, 250, 0);
        super.draw(peasyCam);

        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        pApplet.textAlign(PConstants.CENTER);
        pApplet.textSize(20);
        pApplet.fill(255);
        Util.onFrontOfPeasyCam(pApplet, peasyCam);
        pApplet.text(teamName, 0, -size, 0);
        pApplet.text(squadMarketValue, 0, 4 * -size, 0);

        pApplet.popMatrix();
    }
}
