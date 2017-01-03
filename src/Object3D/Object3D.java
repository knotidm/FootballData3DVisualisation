package Object3D;

import Model.Fixture;
import Model.Player;
import Model.Team;
import Util.Misc;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Vec3D;

public class Object3D<T> {
    private PApplet pApplet;
    public Vec3D location;
    public Integer index;
    public T type;
    public Integer filterValue;
    public Integer size;
    public Boolean isSelected;
    public Boolean isClicked;

    public Object3D(PApplet pApplet, Vec3D location, Integer index, T type, Integer filterValue) {
        this.pApplet = pApplet;
        this.location = location;
        this.index = index;
        this.type = type;
        this.filterValue = filterValue;
        this.size = 20;
        this.isSelected = false;
        this.isClicked = false;
    }

    public void draw(PeasyCam peasyCam, int colour) {
        pApplet.pushMatrix();

        pApplet.noFill();
        pApplet.noStroke();
        if (isSelected) pApplet.stroke(255, 150, 0);
        if (isClicked) pApplet.stroke(150, 250, 0);

        pApplet.translate(location.x, location.y, location.z);
        pApplet.box(size);

        pApplet.noStroke();
        pApplet.fill(colour);
        pApplet.sphereDetail(1);
        pApplet.sphere(size);

        pApplet.rotateX(-PConstants.PI / 2);
        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        pApplet.textAlign(PConstants.CENTER);
        pApplet.textSize(20);
        pApplet.fill(255);
        Misc.onFrontOfPeasyCam(pApplet, peasyCam);

        if (type.getClass() == Team.class) {
            Team team = (Team) type;
            pApplet.text(team.name, 0, -size, 0);
            pApplet.text(filterValue, 0, -size * 2, 0);
        } else if (type.getClass() == Fixture.class) {
            Fixture fixture = (Fixture) type;
            pApplet.text(String.format("%s vs %s", fixture.homeTeamName, fixture.awayTeamName), 0, -size, 0);
        } else if (type.getClass() == Player.class) {
            Player player = (Player) type;
            pApplet.text(player.name, 0, -size, 0);
        }

        pApplet.popMatrix();
    }


}