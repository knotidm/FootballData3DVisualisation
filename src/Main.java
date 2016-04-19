import http.requests.GetRequest;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.data.JSONObject;
import toxi.geom.Vec3D;

import java.util.ArrayList;

public class Main extends PApplet {
    PeasyCam peasyCam;
    ArrayList<Particle> particles;
    SoccerSeason Bundesliga;
    Filter BundesligaFilter;

    Integer view = 1;

    float separate;
    float cohesion;
    float align;
    int minDistance;
    float speedX, speedY, speedZ, accelerationX, accelerationY, accelerationZ, gravityX, gravityY, gravityZ;

    public void setup() {
        peasyCam = new PeasyCam(this, 120);

        Bundesliga = new SoccerSeason(GetRequestToJSONObject("http://api.football-data.org/v1/soccerseasons/394"));
        BundesligaFilter = new Filter(Bundesliga);
        view = 1;

        ChangeView();
        particles = ChangeFilter(Bundesliga, BundesligaFilter.Points());
    }

    public void draw() {
        background(0);
        lights();

        textSize(32);

//        for (Team team : Bundesliga.teams) {
//            textSize(20);
//            text(team.name, 100, i * 25);
//            text(team.squadMarketValue, 400, i * 25);
//            //text(player.nationality, 500, i *25);
//            i++;
//        }

//        for (Fixture fixture: BayernMunchen.fixtures) {
//            text(fixture.date, 100, i *30);
//            text(fixture.awayTeamName, 400, i *30);
//            text(fixture.homeTeamName, 500, i *30);
//            i++;
//        }

        translate(-width / 2, -height / 2, -height / 2);
        for (Particle particle : particles) {
            particle.display();
//            particles.get(i).move();
//            particles.get(i).bounce();
//            particles.get(i).gravity();
//            particles.get(i).separate(separate, minDistance);
//            particles.get(i).cohesion(cohesion, minDistance);
//            particles.get(i).align(align, minDistance);
//            particles.get(i).lineBetween(minDistance);
//            particles.get(i).shapeBetween(minDistance);
        }
    }

    private class Particle {
        Vec3D location = new Vec3D();
        Vec3D acceleration = new Vec3D(accelerationX, accelerationY, accelerationZ);
        Vec3D speed = new Vec3D(speedX, speedY, speedZ);
        Vec3D gravity = new Vec3D(gravityX, gravityY, gravityZ);
        Integer size;
        String text;

        Particle(Vec3D location, Integer size, String text) {
            this.location = location;
            this.size = size;
            this.text = text;
        }

        void display() {
            pushMatrix();

            translate(location.x, location.y, location.z);
            noFill();
            stroke(255, 0, 0);

            sphereDetail(10);
            sphere(size);

            hint(DISABLE_DEPTH_TEST);
            textAlign(CENTER);
            text(text, 0, 0, 0);

            popMatrix();
        }

        void move() {
            this.speed.addSelf(acceleration);
            this.speed.limit(8);
            location.addSelf(speed);
            this.acceleration.clear();
        }

        void bounce() {
            if (location.x > width) speed.x = speed.x * -1;
            if (location.x < 0) speed.x = speed.x * -1;
            if (location.y > height) speed.y = speed.y * -1;
            if (location.y < 0) speed.y = speed.y * -1;
            if (location.z > height) speed.z = speed.z * -1;
            if (location.z < 0) speed.z = speed.z * -1;
        }

        void gravity() {
            this.speed.addSelf(gravity);
        }

        void separate(float separate, int minDistance) {
            Vec3D steer = new Vec3D();
            int count = 0;
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

        void cohesion(float cohesion, int minDistance) {
            Vec3D sum = new Vec3D();
            int count = 0;
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

        void align(float align, int minDistance) {
            Vec3D steer = new Vec3D();
            int count = 0;
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

        void lineBetween(int minDistance) {
            for (Particle other : particles) {
                float distance = location.distanceTo(other.location);
                if (distance > 0 && distance < minDistance) {
                    stroke(255);
                    strokeWeight(0.5f);
                    line(location.x, location.y, location.z, other.location.x, other.location.y, other.location.z);
                }
            }
        }

        void shapeBetween(int minDistance) {
            for (int i = 0; i < particles.size(); i++) {
                Particle other1 = particles.get(i);
                float distance1 = location.distanceTo(other1.location);
                for (int j = 0; j < particles.size() - 1; j++) {
                    Particle other2 = particles.get(j);
                    float distance2 = location.distanceTo(other2.location);
                    if (distance1 > 0 && distance1 < minDistance && distance2 > 0 && distance2 < minDistance) {
                        fill(150, 28, 32);
                        beginShape();
                        noStroke();
                        vertex(location.x, location.y, location.z);
                        vertex(other1.location.x, other1.location.y, other1.location.z);
                        vertex(other2.location.x, other2.location.y, other2.location.z);
                        endShape(CLOSE);
                    }
                }
            }
        }
    }

    private void ChangeView() {
        switch (view) {
            case 1:
                particles = ChangeFilter(Bundesliga, BundesligaFilter.Points());
                break;
            case 2:
                particles = ChangeFilter(Bundesliga, BundesligaFilter.Goals());
                break;
            case 3:
                particles = ChangeFilter(Bundesliga, BundesligaFilter.GoalsAgainst());
                break;
            case 4:
                particles = ChangeFilter(Bundesliga, BundesligaFilter.GoalDifference());
                break;
        }
    }

    public void keyPressed() {
        switch (key) {
            case ',':
                if (view != 1) view--;
                ChangeView();
                break;
            case '.':
                if (view != 4) view++;
                ChangeView();
                break;
        }
    }

    private ArrayList<Particle> ChangeFilter(SoccerSeason soccerSeason, ArrayList<Integer> filteredValues) {
        particles = new ArrayList<Particle>();
        for (int i = 0; i < soccerSeason.leagueTable.standings.size(); i++) {
            particles.add(new Particle(
                    new Vec3D(random(width), random(height), random(height)),
                    filteredValues.get(i),
                    soccerSeason.leagueTable.standings.get(i).teamName
            ));
        }
        return particles;
    }

    static JSONObject GetRequestToJSONObject(String link) {
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token", "324794156b594490a7c6244a6a10a034");
        getRequest.send();
        return JSONObject.parse(getRequest.getContent());
    }

    public void settings() {
        size(800, 600, P3D);
        smooth();
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "Main"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}