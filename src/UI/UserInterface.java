package UI;

import controlP5.*;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;

public class UserInterface {
    PApplet pApplet;
    ControlP5 controlP5;

    private Button modeButton;
    public Integer indexMode = 0;
    private Textlabel modeText;

    private Button filterBackButton;
    private Button filterNextButton;
    public Integer indexFilter = 1;

    public UserInterface(PApplet pApplet) {
        this.pApplet = pApplet;
        controlP5 = new ControlP5(pApplet);
        modeButton = controlP5.addButton("SWITCH MODE").setValue(indexMode).setPosition(10, 10);
        modeText = controlP5.addTextlabel("modeText").setText("ROTATE").setColor(255).setPosition(85, 15);

        filterBackButton = controlP5.addButton("BACK FILTER").setValue(indexMode).setPosition(10, 35);
        filterNextButton = controlP5.addButton("NEXT FILTER").setValue(indexMode).setPosition(85, 35);

        controlP5.setAutoDraw(false);

//        ButtonBar buttonBar = controlP5.addButtonBar("bar").setPosition(0, 0).setSize(400, 20).addItems(PApplet.split("a b c d e f g h i j", " "));
//        buttonBar.onMove(new CallbackListener() {
//            public void controlEvent(CallbackEvent callbackEvent) {
//                ButtonBar bar = (ButtonBar) callbackEvent.getController();
//                println("hello ", bar.hover());
//            }
//        });

        modeButton.onClick(callbackEvent -> {
            indexMode++;
            if (indexMode == 3) indexMode = 0;
        });

        filterBackButton.onClick(callbackEvent -> {
            if (indexFilter != 1) indexFilter--;
        });

        filterNextButton.onClick(callbackEvent -> {
            if (indexFilter != 8) indexFilter++;
        });
    }

    private void switchModeText() {
        switch (indexMode) {
            case 0:
                modeText.setText("ROTATE");
                break;
            case 1:
                modeText.setText("DRAG");
                break;
            case 2:
                modeText.setText("CLICK");
                break;
        }
    }

    public void onFrontOfPeasyCam(PeasyCam peasyCam) {
        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        peasyCam.beginHUD();
        switchModeText();
        controlP5.draw();
        peasyCam.endHUD();
        pApplet.hint(PConstants.ENABLE_DEPTH_TEST);
    }
}