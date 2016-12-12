package Object3D;

import Model.Player;
import Model.Team;
import Util.Get;
import Util.Misc;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Vec3D;

public class Object3D<T> extends Particle {
    private PApplet pApplet;
    public Integer index;
    public T type;
    public Boolean isSelected;
    public Boolean isClicked;

    public Object3D(PApplet pApplet, Vec3D location, Integer size, Integer index, T type) {
        super(pApplet, location, size);
        this.pApplet = pApplet;
        this.index = index;
        this.type = type;
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

        pApplet.rotateX(-PConstants.PI / 2);
        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        pApplet.textAlign(PConstants.CENTER);
        pApplet.textSize(20);
        pApplet.fill(255);
        Misc.onFrontOfPeasyCam(pApplet, peasyCam);

        if (type.getClass() == Team.class) {
            Team team = (Team) type;
            pApplet.text(team.getName(), 0, -size, 0);
            pApplet.text(Get.getString(team.getSquadMarketValue()), 0, 4 * -size, 0);
        } else if (type.getClass() == Player.class) {
            Player player = (Player) type;
            pApplet.text(player.getName(), 0, -size, 0);
            pApplet.text(Get.getString(player.getMarketValue()), 0, 4 * -size, 0);
        }

        pApplet.popMatrix();
    }
}