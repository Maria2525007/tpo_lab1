package tpo_lab1.domain;

import tpo_lab1.domain.actions.Action;
import tpo_lab1.domain.model.Scene;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private final List<Action> actions = new ArrayList<>();

    public Scenario add(Action action) {
        actions.add(action);
        return this;
    }

    public void run(Scene scene) {
        for (Action action : actions) {
            action.apply(scene);
        }
    }

    public int size() {
        return actions.size();
    }
}