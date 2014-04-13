package com.intenthq.battleship;

/**
 *
 * @author Raul
 */
public class Position {

    private Integer x, y, orientation;

    public static enum Cardinals {
        N, S, E, V
    };

    public static enum Directions {
        L, R
    };
    Cardinals[] cardinals = {Cardinals.N, Cardinals.V, Cardinals.S, Cardinals.E};

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Position(int x, int y, Cardinals orientation) {
        this(x, y);
        setOrientation(orientation);
    }

    public void move() {
        switch (cardinals[orientation]) {
            case N:
                y++;
                break;
            case S:
                y--;
                break;
            case E:
                x++;
                break;
            case V:
                x--;
                break;
        }
    }

    public void turn(Directions direction) {
        switch (direction) {
            case L:
                orientation = (orientation + 1) % 4;
                break;
            case R:
                orientation = (orientation + 3) % 4;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cardinals getOrientation() {
        if (orientation != null) {
            return cardinals[orientation];
        }
        return null;
    }

    private void setOrientation(Cardinals cardinal) {
        switch (cardinal) {
            case N:
                orientation = 0;
                break;
            case V:
                orientation = 1;
                break;
            case S:
                orientation = 2;
                break;
            case E:
                orientation = 3;
                break;
        }
    }

    @Override
    public String toString() {
        if (orientation != null) {
            return String.format("(%d, %d, %s)", x, y, cardinals[orientation].name());
        } else {
            return String.format("(%d, %d)", x, y);
        }
    }

    @Override
    public boolean equals(Object position) {
        return position instanceof Position
                && ((Position) position).getX() == x
                && ((Position) position).getY() == y;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + this.x;
        hash = 43 * hash + this.y;
        return hash;
    }
}
