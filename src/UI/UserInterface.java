package UI;

import controlP5.Button;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;

public class UserInterface {
    PApplet pApplet;
    ControlP5 controlP5;

    private Button modeButton;
    public Integer modeValue = 0;

    public UserInterface(PApplet pApplet) {
        this.pApplet = pApplet;
        controlP5 = new ControlP5(pApplet);
        modeButton = controlP5.addButton("mode").setValue(modeValue).setPosition(10, 10);
        controlP5.setAutoDraw(false);

//        ButtonBar buttonBar = controlP5.addButtonBar("bar").setPosition(0, 0).setSize(400, 20).addItems(PApplet.split("a b c d e f g h i j", " "));
//        buttonBar.onMove(new CallbackListener() {
//            public void controlEvent(CallbackEvent callbackEvent) {
//                ButtonBar bar = (ButtonBar) callbackEvent.getController();
//                println("hello ", bar.hover());
//            }
//        });

        modeButton.onClick(new CallbackListener() {
            @Override
            public void controlEvent(CallbackEvent callbackEvent) {
                modeValue++;
                if (modeValue == 3) modeValue = 0;
            }
        });
    }

    public void enableUserInterfaceOnTopOfPeasyCam(PeasyCam peasyCam) {
        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        peasyCam.beginHUD();
        controlP5.draw();
        peasyCam.endHUD();
        pApplet.hint(PConstants.ENABLE_DEPTH_TEST);
    }
}