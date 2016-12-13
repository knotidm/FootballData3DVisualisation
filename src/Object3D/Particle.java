package Object3D;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Vec3D;

import java.util.ArrayList;

public class Particle {
    private PApplet pApplet;
    public Vec3D location;
    private Vec3D acceleration;
    private Vec3D speed;
    private Vec3D gravity;
    public Integer size;
    private TexturedHemesh texturedHemesh;

    public Particle(PApplet pApplet, Vec3D location) {
        this.pApplet = pApplet;
        this.location = location;
        this.size = 10;
        this.texturedHemesh = new TexturedHemesh(pApplet, size);
    }

    public void draw(PeasyCam peasyCam) {
        pApplet.translate(location.x, location.y, location.z);
//        pApplet.sphereDetail(6);
        pApplet.box(size);

        pApplet.shader(texturedHemesh.matCapShader);
        pApplet.shape(texturedHemesh.pShape);
        pApplet.resetShader();
    }

    public void move() {
        this.speed.addSelf(acceleration);
        this.speed.limit(8);
        location.addSelf(speed);
        this.acceleration.clear();
    }

    public void bounce() {
        if (location.x > pApplet.width) speed.x = speed.x * -1;
        if (location.x < 0) speed.x = speed.x * -1;
        if (location.y > pApplet.height) speed.y = speed.y * -1;
        if (location.y < 0) speed.y = speed.y * -1;
        if (location.z > pApplet.height) speed.z = speed.z * -1;
        if (location.z < 0) speed.z = speed.z * -1;
    }

    public void gravity() {
        this.speed.addSelf(gravity);
    }

    public void separate(ArrayList<Particle> particles, float separate, Integer minDistance) {
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

    public void cohesion(ArrayList<Particle> particles, float cohesion, Integer minDistance) {
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

    public void align(ArrayList<Particle> particles, float align, Integer minDistance) {
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

    public void lineBetween(ArrayList<Particle> particles, Integer minDistance) {
        for (Particle other : particles) {
            float distance = location.distanceTo(other.location);
            if (distance > 0 && distance < minDistance) {
                pApplet.stroke(255);
                pApplet.strokeWeight(0.5f);
                pApplet.line(location.x, location.y, location.z, other.location.x, other.location.y, other.location.z);
            }
        }
    }

    public void shapeBetween(ArrayList<Particle> particles, Integer minDistance) {
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