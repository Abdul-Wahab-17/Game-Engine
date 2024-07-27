package org.project;

import org.project.Jade.Window;

public class Main {
    public static void main(String[] args) {
        Window window = Window.get();
        window.run();
    }
}

// Fix dragging in mouse listener
// have fixed dragging for now but it might be prone to errors
//add gamepad stuff