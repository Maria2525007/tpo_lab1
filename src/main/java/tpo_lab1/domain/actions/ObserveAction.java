package tpo_lab1.domain.actions;

import tpo_lab1.domain.model.Scene;

public class ObserveAction implements Action {

    @Override
    public void apply(Scene scene) {
        scene.getArthur().observeSomethingWeird();
        scene.incrementWeirdThings();
    }
}