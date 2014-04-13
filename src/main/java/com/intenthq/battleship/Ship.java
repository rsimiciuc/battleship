package com.intenthq.battleship;

/**
 *
 * @author Raul
 */
public class Ship {

    private Position position, maxCoordinates, target;
    private String status = "";
    private String route = "";

    Ship(Position position, Position maxCoordinates) {
        this.position = position;
        this.maxCoordinates = maxCoordinates;
    }

    Ship(Position position, Position maxCoordinates, Position target) {
        this(position, maxCoordinates);
        this.target = target;
    }

    public void navigate() throws GameException {
        for (char direction : route.toCharArray()) {
            if (direction == 'M') {
                position.move();
            } else {
                position.turn(Position.Directions.valueOf(Character.toString(direction)));
            }

            if (position.getX() > maxCoordinates.getX() || position.getY() > maxCoordinates.getY()
                    || position.getX() < 0 || position.getY() < 0) {
                throw new GameException("Wrong directions");
            }
        }
    }

    public Position getPosition() {
        return position;
    }

    public void getAttack(Position attack) {
        if (position.equals(attack)) {
            status = "SUNK";
        }
    }

    public void setTarget(Position target) {
        this.target = target;
    }

    public Position getTarget() {
        return target;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        if (!status.isEmpty()) {
            return position + " " + status;
        } else {
            return position.toString();
        }
    }
}
