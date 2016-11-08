import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Vec3D;

import java.util.ArrayList;

public class Particle {
    PApplet pApplet;
    Vec3D location = new Vec3D();
    Vec3D acceleration;
    Vec3D speed;
    Vec3D gravity;
    Integer size;
    String text;
    String squadMarketValue;

    Particle(PApplet pApplet, Vec3D location, Integer size, String text, String squadMarketValue) {
        this.pApplet = pApplet;
        this.location = location;
        this.size = size;
        this.text = text;
        this.squadMarketValue = squadMarketValue;
    }

    void display(PeasyCam peasyCam) {
        pApplet.pushMatrix();

        pApplet.translate(location.x, location.y, location.z);
        pApplet.noFill();
        pApplet.stroke(255, 0, 0);
        pApplet.sphereDetail(10);
        pApplet.sphere(size);

        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        pApplet.textAlign(PConstants.CENTER);
        pApplet.textSize(20);
        pApplet.fill(255);
        pApplet.rotateX(peasyCam.getRotations()[0]);
        pApplet.rotateY(peasyCam.getRotations()[1]);
        pApplet.rotateZ(peasyCam.getRotations()[2]);
        pApplet.text(text, 0, -size, 0);
        pApplet.text(squadMarketValue, 0, 4 * -size, 0);
        pApplet.popMatrix();
    }

    void move() {
        this.speed.addSelf(acceleration);
        this.speed.limit(8);
        location.addSelf(speed);
        this.acceleration.clear();
    }

    void bounce() {
        if (location.x > pApplet.width) speed.x = speed.x * -1;
        if (location.x < 0) speed.x = speed.x * -1;
        if (location.y > pApplet.height) speed.y = speed.y * -1;
        if (location.y < 0) speed.y = speed.y * -1;
        if (location.z > pApplet.height) speed.z = speed.z * -1;
        if (location.z < 0) speed.z = speed.z * -1;
    }

    void gravity() {
        this.speed.addSelf(gravity);
    }

    void separate(ArrayList<Particle> particles, float separate, Integer minDistance) {
        Vec3D steer = new Vec3D();
        Integer count = 0;
        for (Particle other : particles) {
            float distance = location.distanceTo(other.location);
            if (distance > 0 && distance < minDistance) {
                Vec3D difference = location.sub(other.location);
                difference.normalizeTo(1.0f / distance);
                steer.addSelf(difference);
                count++;
            }
        }
        if (count > 0) {
            steer.scaleSelf(1.0f / count);
        }
        steer.scaleSelf(separate);
        acceleration.addSelf(steer);
    }

    void cohesion(ArrayList<Particle> particles, float cohesion, Integer minDistance) {
        Vec3D sum = new Vec3D();
        Integer count = 0;
        for (Particle other : particles) {
            float distance = location.distanceTo(other.location);
            if (distance > 0 && distance < minDistance) {
                sum.addSelf(other.location);
                count++;
            }
        }
        if (count > 0) {
            sum.scaleSelf(1.0f / count);
        }
        Vec3D steer = sum.sub(location);
        steer.scaleSelf(cohesion * 0.1f);
        acceleration.addSelf(steer);
    }

    void align(ArrayList<Particle> particles, float align, Integer minDistance) {
        Vec3D steer = new Vec3D();
        Integer count = 0;
        for (Particle other : particles) {
            float distance = location.distanceTo(other.location);
            if (distance > 0 && distance < minDistance) {
                steer.addSelf(other.speed);
                count++;
            }
        }
        if (count > 0) {
            steer.scaleSelf(1.0f / count);
        }
        steer.scaleSelf(align);
        acceleration.addSelf(steer);
    }

    void lineBetween(ArrayList<Particle> particles, Integer minDistance) {
        for (Particle other : particles) {
            float distance = location.distanceTo(other.location);
            if (distance > 0 && distance < minDistance) {
                pApplet.stroke(255);
                pApplet.strokeWeight(0.5f);
                pApplet.line(location.x, location.y, location.z, other.location.x, other.location.y, other.location.z);
            }
        }
    }

    void shapeBetween(ArrayList<Particle> particles, Integer minDistance) {
        for (Integer i = 0; i < particles.size(); i++) {
            Particle other1 = particles.get(i);
            float distance1 = location.distanceTo(other1.location);
            for (Integer j = 0; j < particles.size() - 1; j++) {
                Particle other2 = particles.get(j);
                float distance2 = location.distanceTo(other2.location);
                if (distance1 > 0 && distance1 < minDistance && distance2 > 0 && distance2 < minDistance) {
                    pApplet.fill(150, 28, 32);
                    pApplet.beginShape();
                    pApplet.noStroke();
                    pApplet.vertex(location.x, location.y, location.z);
                    pApplet.vertex(other1.location.x, other1.location.y, other1.location.z);
                    pApplet.vertex(other2.location.x, other2.location.y, other2.location.z);
                    pApplet.endShape(PConstants.CLOSE);
                }
            }
        }
    }
}
