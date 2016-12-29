package Util;

import Model.Competition;

import java.util.ArrayList;

public class Initial implements Runnable {
    private Thread thread;
    public ArrayList<Competition> competitions;

    public Initial() {
        competitions = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            for (int i = 4; i > 0; i--) {
                switch (i) {
                    case 4:
                        competitions.add(new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/430")));
                        break;
                    case 3:
                        competitions.add(new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/438")));
                        break;
                    case 2:
                        competitions.add(new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/434")));
                        break;
                    case 1:
                        break;
                }
                Thread.sleep(50000);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}
