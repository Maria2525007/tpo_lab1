package tpo_lab1.domain.model;

public class Head {

    public enum Side { LEFT, RIGHT }

    private final Side side;
    private boolean smiling;
    private boolean busy;

    public Head(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public boolean isSmiling() {
        return smiling;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setSmiling(boolean smiling) {
        this.smiling = smiling;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}