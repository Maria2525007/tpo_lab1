package tpo_lab1.domain.actions;

import tpo_lab1.domain.model.Scene;

public class TouchAction implements Action {

    @Override
    public void apply(Scene scene) {
        // "ноги на пульте"
        scene.getControlPanel().putLegsOnPanel();
        scene.incrementWeirdThings();
    }
}