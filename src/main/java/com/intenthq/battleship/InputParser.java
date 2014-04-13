package com.intenthq.battleship;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Raul
 */
public final class InputParser {

    private final String input;

    private ArrayList<Ship> ships = new ArrayList<>();

    private Position maxCoordinates;

    InputParser(String input) throws GameException {
        this.input = input;
        parse();
    }

    protected void parse() throws GameException {

        LinkedHashMap<Position, Ship> shipsList = new LinkedHashMap<>();

        String[] lines = input.split("\n", -1);
        if (lines.length < 6 || lines.length % 2 == 1) {
            throw new GameException("Error parsing input");
        }

        // parse map size
        maxCoordinates = parsePosition(lines[0]);

        //parse ships positions
        String[] shipsPositions = lines[1].split("\\) \\(", -1);
        int i = 1;
        for (String p : shipsPositions) {
            if (i == 1) {
                p = p + ")";
            } else if (i == shipsPositions.length) {
                p = "(" + p;
            } else {
                p = "(" + p + ")";
            }

            i++;

            Position shipPosition = parsePosition(p);
            shipsList.put(shipPosition, new Ship(shipPosition, maxCoordinates));
        }

        if (shipsList.size() != shipsPositions.length) {
            throw new GameException("Duplicate position for ships");
        }

        for (i = 2; i < lines.length; i += 2) {
            Matcher matcher = Pattern.compile("^(\\(\\d+, \\d+\\)) ([MLR]+)$").matcher(lines[i]);
            Position ship;

            if (matcher.find()) {
                ship = parsePosition(matcher.group(1));
            } else {
                throw new GameException("Error parsing line: " + lines[i]);
            }

            if (shipsList.get(ship) != null) {
                shipsList.get(ship).setRoute(matcher.group(2));
            } else {
                throw new GameException("No such ship " + ship);
            }

            Position target = parsePosition(lines[i + 1]);
            shipsList.get(ship).setTarget(target);
        }

        this.ships = new ArrayList<>(shipsList.values());
    }

    public Position getMaxCoordinates() {
        return maxCoordinates;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public static Position parsePosition(String position) {

        Matcher matcher = Pattern.compile("^\\((\\d+), (\\d+)\\)$").matcher(position);
        if (matcher.find()) {
            return new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        }

        matcher = Pattern.compile("^\\((\\d+), (\\d+), ([NSEV]{1})\\)$").matcher(position);
        if (matcher.find()) {
            return new Position(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Position.Cardinals.valueOf(matcher.group(3)));
        }

        return null;
    }
}
