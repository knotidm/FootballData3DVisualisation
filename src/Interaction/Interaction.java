package Interaction;

import Object3D.Grid;
import Object3D.TeamObject3D;
import UI.UserInterface;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class Interaction {
    private static int indexGF = 0;
    private static int indexGrid = 0;
    private static float mouseShortestDistance;

    public static void switchMode(PApplet pApplet, UserInterface userInterface, PeasyCam peasyCam, Grid grid, ArrayList<TeamObject3D> teamObjects3D) {
        switch (userInterface.modeValue) {
            case 0:
                peasyCam.setActive(true);
                resetAllTeamObjects3DStates(teamObjects3D);
                break;
            case 1:
                peasyCam.setActive(false);
                dragTeamObject3D(pApplet, grid, teamObjects3D);
                break;
            case 2:
                peasyCam.setActive(false);
                clickTeamObject3D(pApplet, grid, teamObjects3D);
                break;
        }
    }

    private static void dragTeamObject3D(PApplet pApplet, Grid grid, ArrayList<TeamObject3D> teamObjects3D) {
        resetAllTeamObjects3DStates(teamObjects3D);
        teamObjects3D.get(indexGF).isSelected = true;
        positionInRelationToGrid(pApplet, grid);

        if (pApplet.mousePressed) {
            if (pApplet.mouseButton == PConstants.LEFT) {
                teamObjects3D.get(indexGF).location.x += (grid.x[indexGrid] - teamObjects3D.get(indexGF).location.x) / 6;
                teamObjects3D.get(indexGF).location.y += (grid.y[indexGrid] - teamObjects3D.get(indexGF).location.y) / 6;
            }
            if (pApplet.mouseButton == PConstants.RIGHT) {
                teamObjects3D.get(indexGF).location.z += pApplet.pmouseY - pApplet.mouseY;
            }
        } else {
            closestTeamObject3DInRelationToPosition(pApplet, teamObjects3D);
        }
    }

    private static void clickTeamObject3D(PApplet pApplet, Grid grid, ArrayList<TeamObject3D> teamObjects3D) {
        resetAllTeamObjects3DStates(teamObjects3D);
        teamObjects3D.get(indexGF).isSelected = true;
        positionInRelationToGrid(pApplet, grid);

        if (pApplet.mousePressed) {
            if (pApplet.mouseButton == PConstants.LEFT) {
                teamObjects3D.get(indexGF).isClicked = true;
            }
        } else {
            closestTeamObject3DInRelationToPosition(pApplet, teamObjects3D);
        }
    }

    private static void resetAllTeamObjects3DStates(ArrayList<TeamObject3D> teamObjects3D) {
        for (TeamObject3D teamObject3D : teamObjects3D) {
            teamObject3D.isSelected = false;
            teamObject3D.isClicked = false;
        }
    }

    private static void positionInRelationToGrid(PApplet pApplet, Grid grid) {
        mouseShortestDistance = PApplet.sqrt(PApplet.sq(pApplet.mouseX - pApplet.screenX(grid.x[indexGrid], grid.y[indexGrid], 0)) + PApplet.sq(pApplet.mouseY - pApplet.screenY(grid.x[indexGrid], grid.y[indexGrid], 0)));
        for (int i = 0; i < grid.x.length; i++) {
            float mouseDistance = PApplet.sqrt(PApplet.sq(pApplet.mouseX - pApplet.screenX(grid.x[i], grid.y[i], 0)) + PApplet.sq(pApplet.mouseY - pApplet.screenY(grid.x[i], grid.y[i], 0)));
            if (mouseDistance <= mouseShortestDistance) {
                indexGrid = i;
                mouseShortestDistance = mouseDistance;
            }
        }
    }

    private static void closestTeamObject3DInRelationToPosition(PApplet pApplet, ArrayList<TeamObject3D> teamObjects3D) {
        mouseShortestDistance = PApplet.sqrt(PApplet.sq(pApplet.mouseX - pApplet.screenX(teamObjects3D.get(indexGF).location.x, teamObjects3D.get(indexGF).location.y, 0)) + PApplet.sq(pApplet.mouseY - pApplet.screenY(teamObjects3D.get(indexGF).location.x, teamObjects3D.get(indexGF).location.y, 0)));
        for (TeamObject3D teamObject3D : teamObjects3D) {
            float mouseDistance = PApplet.sqrt(PApplet.sq(pApplet.mouseX - pApplet.screenX(teamObject3D.location.x, teamObject3D.location.y, 0)) + PApplet.sq(pApplet.mouseY - pApplet.screenY(teamObject3D.location.x, teamObject3D.location.y, 0)));
            if (mouseDistance <= mouseShortestDistance) {
                indexGF = teamObject3D.index;
                mouseShortestDistance = mouseDistance;
            }
        }
    }
}
