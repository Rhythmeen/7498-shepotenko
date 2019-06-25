package ru.cft.focusstart.shepotenko.Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RecordManager {
    private String leaderBoardPath;

    RecordManager(Difficulty difficulty) {
        this.leaderBoardPath = String.format("mineSweeperResources/records/%s.txt", difficulty);
    }

    public boolean isNewRecord(int newTime) {
        int recordLineCount = 0;
        String lastLine = "";
        try (Scanner scanner = new Scanner(new FileReader(this.leaderBoardPath))) {
            while (scanner.hasNext()) {
                lastLine = scanner.nextLine();
                recordLineCount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (recordLineCount < 10) {
            return true;
        } else {
            int lastRecord = Integer.parseInt(lastLine.substring(0, lastLine.indexOf(" ")));
            return newTime < lastRecord;
        }
    }

    public void updateLeaderBoard(int newRecordTime, String newRecordName) {
        String newRecordLine = newRecordTime + " " + newRecordName;
        ArrayList<String> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(leaderBoardPath))) {
            while (scanner.hasNext()) {
                String lineFromBoard = scanner.nextLine();
                int timeFromBoard = Integer.parseInt(lineFromBoard.substring(0, lineFromBoard.indexOf(" ")));
                if (newRecordTime < timeFromBoard) {
                    records.add(newRecordLine);
                    records.add(lineFromBoard);
                    while (scanner.hasNext()) {
                        records.add(scanner.nextLine());
                    }
                } else {
                    records.add(lineFromBoard);
                }
            }
            if (!records.contains(newRecordLine)) {
                records.add(newRecordLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (records.size() > 10) {
            records.remove(10);
        }
        try (FileWriter writer = new FileWriter(leaderBoardPath)) {
            for (String recordLine : records) {
                writer.write(recordLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLeaderBoardPath() {
        return this.leaderBoardPath;
    }
}
