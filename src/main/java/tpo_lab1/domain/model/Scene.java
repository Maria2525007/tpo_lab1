package tpo_lab1.domain.model;

public class Scene {

    private final Person arthur;
    private final Person strangeMan;
    private final Chair chair;
    private final ControlPanel controlPanel;

    private int weirdThingsCount;

    public Scene(Person arthur, Person strangeMan, Chair chair, ControlPanel controlPanel) {
        this.arthur = arthur;
        this.strangeMan = strangeMan;
        this.chair = chair;
        this.controlPanel = controlPanel;
        this.weirdThingsCount = 0;
    }

    public Person getArthur() {
        return arthur;
    }

    public Person getStrangeMan() {
        return strangeMan;
    }

    public Chair getChair() {
        return chair;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public int getWeirdThingsCount() {
        return weirdThingsCount;
    }

    public void incrementWeirdThings() {
        weirdThingsCount++;
    }
}
