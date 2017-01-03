package Util;

import Model.Competition;
import UI.UserInterface;

import java.util.ArrayList;

public class Initial implements Runnable {
    private Thread thread;
    public ArrayList<Competition> competitions;
    private UserInterface userInterface;

    public Initial(UserInterface userInterface) {
        competitions = new ArrayList<>();
        this.userInterface = userInterface;
    }

    @Override
    public void run() {
        try {
            for (int i = 6; i > 0; i--) {
                switch (i) {
                    case 6:
                        addCompetition(new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/430")));
                        break;
                    case 5:
                        addCompetition(new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/438")));
                        break;
                    case 4:
                        addCompetition(new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/434")));
                        break;
                    case 3:
                        addCompetition(new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/426")));
                        break;
                    case 2:
                        addCompetition(new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/436")));
                        break;
                    case 1:
                        addCompetition(new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/433")));
                        break;
                }
                Thread.sleep(50000);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addCompetition(Competition competition) {
        competitions.add(competition);
        userInterface.competition.addItem(competition.name, competition);
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}
