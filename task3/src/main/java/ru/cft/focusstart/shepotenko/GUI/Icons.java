package ru.cft.focusstart.shepotenko.GUI;

import ru.cft.focusstart.shepotenko.MainClass;

import javax.swing.*;
import java.util.HashMap;

class Icons {
    private static HashMap<Integer, ImageIcon> icons = fillIconsMap();

    private static HashMap<Integer, ImageIcon> fillIconsMap() {
        HashMap<Integer, ImageIcon> icons = new HashMap<>();
        icons.put(0, new ImageIcon(MainClass.class.getResource("/icons/zero.png")));
        icons.put(1, new ImageIcon(MainClass.class.getResource("/icons/one.png")));
        icons.put(2, new ImageIcon(MainClass.class.getResource("/icons/two.png")));
        icons.put(3, new ImageIcon(MainClass.class.getResource("/icons/three.png")));
        icons.put(4, new ImageIcon(MainClass.class.getResource("/icons/four.png")));
        icons.put(5, new ImageIcon(MainClass.class.getResource("/icons/five.png")));
        icons.put(6, new ImageIcon(MainClass.class.getResource("/icons/six.png")));
        icons.put(7, new ImageIcon(MainClass.class.getResource("/icons/seven.png")));
        icons.put(8, new ImageIcon(MainClass.class.getResource("/icons/eight.png")));
        icons.put(9, new ImageIcon(MainClass.class.getResource("/icons/mine.png")));
        icons.put(10, new ImageIcon(MainClass.class.getResource("/icons/mined.png")));
        icons.put(11, new ImageIcon(MainClass.class.getResource("/icons/no_mine.png")));
        icons.put(12, new ImageIcon(MainClass.class.getResource("/icons/closed.png")));
        icons.put(13, new ImageIcon(MainClass.class.getResource("/icons/flag.png")));
        icons.put(14, new ImageIcon(MainClass.class.getResource("/icons/question.png")));
        icons.put(15, new ImageIcon(MainClass.class.getResource("/icons/icon.png")));
        return icons;
    }

    static ImageIcon getNumberedIcon(int num) {
        return icons.get(num);
    }

    static ImageIcon getBombedIcon(){
        return  icons.get(10);
    }

    static ImageIcon getMistakeIcon(){
        return  icons.get(11);
    }

    static ImageIcon getClosedIcon(){
        return  icons.get(12);
    }

    static ImageIcon getFlagIcon() {
        return icons.get(13);
    }

    static ImageIcon getQuistionIcon(){
        return  icons.get(14);
    }

    static ImageIcon getGameIcon(){
        return  icons.get(15);
    }
}
