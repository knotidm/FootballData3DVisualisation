package Interaction;

import Filter.FilterTeam;
import Model.Competition;
import Model.Player;
import Model.Team;
import Object3D.Grid;
import Object3D.Object3D;
import UI.UserInterface;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class Interaction<T> {
    private static int indexGrid = 0;
    private static int indexObject3D = 0;
    private static float mouseShortestDistance;

    public ArrayList<Object3D<T>> switchFilter(Competition competition, ArrayList<Object3D<T>> objects3D, FilterTeam filter, Integer indexFilter) {
        switch (indexFilter) {
            case 1:
                return setFilter(competition, objects3D, filter.position());
            case 2:
                return setFilter(competition, objects3D, filter.points());
            case 3:
                return setFilter(competition, objects3D, filter.goals());
            case 4:
                return setFilter(competition, objects3D, filter.goalsAgainst());
            case 5:
                return setFilter(competition, objects3D, filter.goalDifference());
            case 6:
                return setFilter(competition, objects3D, filter.wins());
            case 7:
                return setFilter(competition, objects3D, filter.draws());
            case 8:
                return setFilter(competition, objects3D, filter.losses());
        }
        return null;
    }

    public void switchMode(PApplet pApplet, PeasyCam peasyCam, UserInterface userInterface, Grid grid, ArrayList<Object3D<T>> objects3D) {
        switch (userInterface.indexMode) {
            case 0:
                peasyCam.setActive(true);
                resetAllObjects3DStates(objects3D);
                break;
            case 1:
                peasyCam.setActive(false);
                dragObject3D(pApplet, grid, objects3D);
                break;
            case 2:
                peasyCam.setActive(false);
                clickObject3D(pApplet, userInterface, grid, objects3D);
                break;
        }
    }

    private ArrayList<Object3D<T>> setFilter(Competition competition, ArrayList<Object3D<T>> objects3D, ArrayList<Integer> filteredValues) {
        for (Integer i = 0; i < competition.standings.size(); i++) {
            objects3D.get(i).size = filteredValues.get(i);
        }
        return objects3D;
    }

    private void dragObject3D(PApplet pApplet, Grid grid, ArrayList<Object3D<T>> objects3D) {
        resetAllObjects3DStates(objects3D);
        objects3D.get(indexObject3D).isSelected = true;
        positionInRelationToGrid(pApplet, grid);

        if (pApplet.mousePressed) {
            if (pApplet.mouseButton == PConstants.LEFT) {
                objects3D.get(indexObject3D).location.x += (grid.x[indexGrid] - objects3D.get(indexObject3D).location.x) / 6;
                objects3D.get(indexObject3D).location.y += (grid.y[indexGrid] - objects3D.get(indexObject3D).location.y) / 6;
            }
            if (pApplet.mouseButton == PConstants.RIGHT) {
                objects3D.get(indexObject3D).location.z += pApplet.pmouseY - pApplet.mouseY;
            }
        } else {
            closestObject3DInRelationToPosition(pApplet, objects3D);
        }
    }

    private void clickObject3D(PApplet pApplet, UserInterface userInterface, Grid grid, ArrayList<Object3D<T>> objects3D) {
        resetAllObjects3DStates(objects3D);
        objects3D.get(indexObject3D).isSelected = true;
        positionInRelationToGrid(pApplet, grid);

        if (pApplet.mousePressed) {
            if (pApplet.mouseButton == PConstants.LEFT) {
                objects3D.get(indexObject3D).isClicked = true;
                if (objects3D.get(indexObject3D).type.getClass() == Team.class) {
                    userInterface.competitionLevel = false;
                    userInterface.teamLevel = true;
                } else if (objects3D.get(indexObject3D).type.getClass() == Player.class) {
                    userInterface.competitionLevel = true;
                    userInterface.teamLevel = false;
                }
            }
        } else {
            closestObject3DInRelationToPosition(pApplet, objects3D);
        }
    }

    private void resetAllObjects3DStates(ArrayList<Object3D<T>> objects3D) {
        for (Object3D<T> object3D : objects3D) {
            object3D.isSelected = false;
            object3D.isClicked = false;
        }
    }

    private void positionInRelationToGrid(PApplet pApplet, Grid grid) {
        mouseShortestDistance = PApplet.sqrt(PApplet.sq(pApplet.mouseX - pApplet.screenX(grid.x[indexGrid], grid.y[indexGrid], 0)) + PApplet.sq(pApplet.mouseY - pApplet.screenY(grid.x[indexGrid], grid.y[indexGrid], 0)));
        for (int i = 0; i < grid.x.length; i++) {
            float mouseDistance = PApplet.sqrt(PApplet.sq(pApplet.mouseX - pApplet.screenX(grid.x[i], grid.y[i], 0)) + PApplet.sq(pApplet.mouseY - pApplet.screenY(grid.x[i], grid.y[i], 0)));
            if (mouseDistance <= mouseShortestDistance) {
                indexGrid = i;
                mouseShortestDistance = mouseDistance;
            }
        }
    }

    private void closestObject3DInRelationToPosition(PApplet pApplet, ArrayList<Object3D<T>> objects3D) {
        mouseShortestDistance = PApplet.sqrt(PApplet.sq(pApplet.mouseX - pApplet.screenX(objects3D.get(indexObject3D).location.x, objects3D.get(indexObject3D).location.y, 0)) + PApplet.sq(pApplet.mouseY - pApplet.screenY(objects3D.get(indexObject3D).location.x, objects3D.get(indexObject3D).location.y, 0)));
        for (Object3D<T> object3D : objects3D) {
            float mouseDistance = PApplet.sqrt(PApplet.sq(pApplet.mouseX - pApplet.screenX(object3D.location.x, object3D.location.y, 0)) + PApplet.sq(pApplet.mouseY - pApplet.screenY(object3D.location.x, object3D.location.y, 0)));
            if (mouseDistance <= mouseShortestDistance) {
                indexObject3D = object3D.index;
                mouseShortestDistance = mouseDistance;
            }
        }
    }
}