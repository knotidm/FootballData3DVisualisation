package Util;

import peasy.PeasyCam;
import processing.core.PApplet;

public class Misc {
    public static void onFrontOfPeasyCam(PApplet pApplet, PeasyCam peasyCam) {
        pApplet.rotateX(peasyCam.getRotations()[0]);
        pApplet.rotateY(peasyCam.getRotations()[1]);
        pApplet.rotateZ(peasyCam.getRotations()[2]);
    }
}