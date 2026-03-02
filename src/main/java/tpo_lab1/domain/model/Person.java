package tpo_lab1.domain.model;

import tpo_lab1.domain.DomainException;

public class Person {

    private final String name;

    private boolean inRoom;
    private boolean shocked;
    private boolean jawDropped;

    private Head leftHead;
    private Head rightHead;

    private boolean hasLeftHand = true;

    public Person(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Person name must be non-empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isInRoom() {
        return inRoom;
    }

    public boolean isShocked() {
        return shocked;
    }

    public boolean isJawDropped() {
        return jawDropped;
    }

    public boolean hasLeftHand() {
        return hasLeftHand;
    }

    public void setHasLeftHand(boolean hasLeftHand) {
        this.hasLeftHand = hasLeftHand;
    }

    public void enterRoom() {
        this.inRoom = true;
    }

    public void observeSomethingWeird() {
        this.shocked = true;
        this.jawDropped = true;
    }

    public void makeTwoHeaded() {
        this.leftHead = new Head(Head.Side.LEFT);
        this.rightHead = new Head(Head.Side.RIGHT);
    }

    public Head getLeftHead() {
        return leftHead;
    }

    public Head getRightHead() {
        return rightHead;
    }

    public void smileLeftHead() {
        ensureTwoHeaded();
        leftHead.setSmiling(true);
    }

    public void pickTeethOfRightHeadWithLeftHand() {
        ensureTwoHeaded();
        if (!hasLeftHand) {
            throw new DomainException("Cannot pick teeth: left hand is missing");
        }
        rightHead.setBusy(true);
    }

    private void ensureTwoHeaded() {
        if (leftHead == null || rightHead == null) {
            throw new DomainException("Person is not two-headed");
        }
    }
}