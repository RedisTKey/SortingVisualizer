package Tools;

import java.awt.*;

public class Element {
    public int value;
    public Color color;

    public Element(int value) {
        this.value = value;
        this.color = Color.GRAY;
    }

    public Color get_color() {
        return this.color;
    }

    public void reset_color() {
        this.color = Color.BLUE;
    }

    public void make_open() {
        this.color = Color.RED;
    }

    public void setFinalSortedColor() {
        this.color = Color.GREEN; // Color for sorted element
    }
}
