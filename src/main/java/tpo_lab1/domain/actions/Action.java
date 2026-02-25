package tpo_lab1.domain.actions;

import tpo_lab1.domain.model.Scene;

@FunctionalInterface
public interface Action {
    void apply(Scene scene);
}