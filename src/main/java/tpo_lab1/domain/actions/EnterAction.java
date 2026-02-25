package tpo_lab1.domain.actions;

import tpo_lab1.domain.model.Scene;

public class EnterAction implements Action {

    @Override
    public void apply(Scene scene) {
        scene.getArthur().enterRoom();
    }
}