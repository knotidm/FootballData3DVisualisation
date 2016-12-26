package Object3D;

import Model.Fixture;
import Model.Player;
import Model.Team;
import Util.Misc;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Vec3D;

public class Object3D<T> extends Particle {
    public Integer index;
    public T type;
    public Integer filterValue;
    public Boolean isSelected;
    public Boolean isClicked;
    TexturedHemesh texturedHemesh;
    private PApplet pApplet;

    public Object3D(PApplet pApplet, Vec3D location, Integer index, T type, Integer filterValue) {
        super(pApplet, location);
        this.pApplet = pApplet;
        this.index = index;
        this.type = type;
        this.filterValue = filterValue;
        this.isSelected = false;
        this.isClicked = false;
        this.texturedHemesh = new TexturedHemesh(pApplet, size);
        texturedHemesh.he_Mesh = texturedHemesh.setHemeshType();
    }

    @Override
    public void draw(PeasyCam peasyCam) {
        pApplet.pushMatrix();

        pApplet.noFill();
        pApplet.noStroke();
        if (isSelected) pApplet.stroke(255, 150, 0);
        if (isClicked) pApplet.stroke(150, 250, 0);

        super.draw(peasyCam);

        pApplet.fill((filterValue * 10), 255 - (filterValue * 10), 0);

//        texturedHemesh.modify(filterValue.floatValue(), filterValue.floatValue(), filterValue.floatValue());
        texturedHemesh.wb_render3D.drawFaces(texturedHemesh.he_Mesh);
//        pApplet.sphere(size);

        pApplet.rotateX(-PConstants.PI / 2);
        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        pApplet.textAlign(PConstants.CENTER);
        pApplet.textSize(20);
        pApplet.fill(255);
        Misc.onFrontOfPeasyCam(pApplet, peasyCam);

        if (type.getClass() == Team.class) {
            Team team = (Team) type;
//            PShape emblem = pApplet.loadShape(team.getEmblem());
            pApplet.text(team.getName(), 0, -size, 0);
            pApplet.text(filterValue, 0, -size * 2, 0);
//            pApplet.shape(emblem, 100, 100);
        } else if (type.getClass() == Fixture.class) {
            Fixture fixture = (Fixture) type;
            pApplet.text(String.format("%s vs %s", fixture.getHomeTeamName(), fixture.getAwayTeamName()), 0, -size, 0);
        } else if (type.getClass() == Player.class) {
            Player player = (Player) type;
            pApplet.text(player.getName(), 0, -size, 0);
        }

        pApplet.popMatrix();
    }
}