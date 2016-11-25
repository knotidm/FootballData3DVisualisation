package Interaction;

import Model.Competition;
import Model.Filter;
import Object3D.Grid;
import Object3D.TeamObject3D;
import UI.UserInterface;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class Interaction {
    private static int indexGrid = 0;
    private static int indexTeamObject3D = 0;
    private static float mouseShortestDistance;
    public static float x;
    public static float z;

    public static ArrayList<TeamObject3D> switchFilter(Competition competition, ArrayList<TeamObject3D> teamObjects3D, Filter filter, Integer indexFilter) {
        switch (indexFilter) {
            case 1:
                return setFilter(competition, teamObjects3D, filter.position());
            case 2:
                return setFilter(competition, teamObjects3D, filter.points());
            case 3:
                return setFilter(competition, teamObjects3D, filter.goals());
            case 4:
                return setFilter(competition, teamObjects3D, filter.goalsAgainst());
            case 5:
                return setFilter(competition, teamObjects3D, filter.goalDifference());
            case 6:
                return setFilter(competition, teamObjects3D, filter.wins());
            case 7:
                return setFilter(competition, teamObjects3D, filter.draws());
            case 8:
                return setFilter(competition, teamObjects3D, filter.losses());
        }
        return null;
    }

    public static void switchMode(PApplet pApplet, PeasyCam peasyCam, UserInterface userInterface, Grid grid, ArrayList<TeamObject3D> teamObjects3D) {
        switch (userInterface.indexMode) {
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
            case 3:
                peasyCam.setActive(false);
                pApplet.translate(x, z, 0);
                break;
        }
    }

    private static ArrayList<TeamObject3D> setFilter(Competition competition, ArrayList<TeamObject3D> teamObjects3D, ArrayList<Integer> filteredValues) {
        for (Integer i = 0; i < competition.standings.size(); i++) {
            teamObjects3D.get(i).size = filteredValues.get(i);
        }
        return teamObjects3D;
    }

    private static void dragTeamObject3D(PApplet pApplet, Grid grid, ArrayList<TeamObject3D> teamObjects3D) {
        resetAllTeamObjects3DStates(teamObjects3D);
        teamObjects3D.get(indexTeamObject3D).isSelected = true;
        positionInRelationToGrid(pApplet, grid);

        if (pApplet.mousePressed) {
            if (pApplet.mouseButton == PConstants.LEFT) {
                teamObjects3D.get(indexTeamObject3D).location.x += (grid.x[indexGrid] - teamObjects3D.get(indexTeamObject3D).location.x) / 6;
                teamObjects3D.get(indexTeamObject3D).location.y += (grid.y[indexGrid] - teamObjects3D.get(indexTeamObject3D).location.y) / 6;
            }
            if (pApplet.mouseButton == PConstants.RIGHT) {
                teamObjects3D.get(indexTeamObject3D).location.z += pApplet.pmouseY - pApplet.mouseY;
            }
        } else {
            closestTeamObject3DInRelationToPosition(pApplet, teamObjects3D);
        }
    }

    private static void clickTeamObject3D(PApplet pApplet, Grid grid, ArrayList<TeamObject3D> teamObjects3D) {
        resetAllTeamObjects3DStates(teamObjects3D);
        teamObjects3D.get(indexTeamObject3D).isSelected = true;
        positionInRelationToGrid(pApplet, grid);

        if (pApplet.mousePressed) {
            if (pApplet.mouseButton == PConstants.LEFT) {
                teamObjects3D.get(indexTeamObject3D).isClicked = true;
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
        mouseShortestDistance = PApplet.sqrt(PApplet.sq(pApplet.mouseX - pApplet.screenX(teamObjects3D.get(indexTeamObject3D).location.x, teamObjects3D.get(indexTeamObject3D).location.y, 0)) + PApplet.sq(pApplet.mouseY - pApplet.screenY(teamObjects3D.get(indexTeamObject3D).location.x, teamObjects3D.get(indexTeamObject3D).location.y, 0)));
        for (TeamObject3D teamObject3D : teamObjects3D) {
            float mouseDistance = PApplet.sqrt(PApplet.sq(pApplet.mouseX - pApplet.screenX(teamObject3D.location.x, teamObject3D.location.y, 0)) + PApplet.sq(pApplet.mouseY - pApplet.screenY(teamObject3D.location.x, teamObject3D.location.y, 0)));
            if (mouseDistance <= mouseShortestDistance) {
                indexTeamObject3D = teamObject3D.index;
                mouseShortestDistance = mouseDistance;
            }
        }
    }
}
