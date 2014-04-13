package com.intenthq.battleship;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class InputParserTest {

    private static final String SAMPLE_INPUT = "(5, 5)\n"
            + "(1, 2, N) (3, 3, E) (2, 5, S)\n"
            + "(1, 2) LMLMLMLMM\n"
            + "(2, 3)\n"
            + "(3, 3) MRMMRMRRM\n"
            + "(1, 3)\n"
            + "(2, 5) MMLMRMRRLM\n"
            + "(2, 5)";

    private static final String SAMPLE_WRONG_INPUT = "(5, 5)\n"
            + "(1, 2, N) (3, 3, E) (2, 5, S)\n"
            + "(1, 2) LMLTGGFFGMLMLMM\n"
            + "(2, 3)\n"
            + "(3, 3) MRMMRMRRM\n"
            + "(1, 3)\n"
            + "(2, 5) MMLMRMRRLM\n"
            + "(2, 5)";

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = GameException.class)
    public void inputParserShouldThrowException() throws Exception {
        InputParser parser = new InputParser(SAMPLE_WRONG_INPUT);
    }

    @Test
    public void inputParserShouldReturnCorrectMapSize() throws Exception {
        InputParser parser = new InputParser(SAMPLE_INPUT);
        assertThat(parser.getMaxCoordinates(), is(new Position(5, 5)));
    }

    @Test
    public void inputParserShouldReturnCorrectShips() throws Exception {
        InputParser parser = new InputParser(SAMPLE_INPUT);

        assertThat(parser.getShips().size(), is(new Integer(3)));

        List<Ship> ships = parser.getShips();

        assertThat(ships.get(0).getPosition(), is(new Position(1, 2, Position.Cardinals.N)));
        assertThat(ships.get(1).getPosition(), is(new Position(3, 3, Position.Cardinals.E)));
        assertThat(ships.get(2).getPosition(), is(new Position(2, 5, Position.Cardinals.S)));

        assertThat(ships.get(0).getTarget(), is(new Position(2, 3)));
        assertThat(ships.get(1).getTarget(), is(new Position(1, 3)));
        assertThat(ships.get(2).getTarget(), is(new Position(2, 5)));

        assertThat(ships.get(0).getRoute(), is("LMLMLMLMM"));
        assertThat(ships.get(1).getRoute(), is("MRMMRMRRM"));
        assertThat(ships.get(2).getRoute(), is("MMLMRMRRLM"));
    }
}
