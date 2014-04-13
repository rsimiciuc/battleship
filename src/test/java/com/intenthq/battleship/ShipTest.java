package com.intenthq.battleship;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class ShipTest {

    protected final Position mapSize = new Position(5,5);
    protected Ship sampleShipOne, sampleShipTwo;

    @Before
    public void setUp() throws Exception {
        sampleShipOne = new Ship(new Position(1, 2, Position.Cardinals.N), mapSize);
        sampleShipTwo = new Ship(new Position(1, 2, Position.Cardinals.N), mapSize);

    }

    @Test
    public void shipNavigateSetsCorrectDestinationTestOne() throws Exception {
        Ship ship = new Ship(new Position(1, 2, Position.Cardinals.N), mapSize);
        ship.setRoute("LMLMLMLMM");
        ship.navigate();
        assertThat(ship.getPosition(), is(new Position(1, 3)));
    }

    @Test
    public void shipNavigateSetsCorrectDestinationTestTwo() throws Exception {
        Ship ship = new Ship(new Position(2, 5, Position.Cardinals.S), mapSize);
        ship.setRoute("MMLMRMRRLM");
        ship.navigate();
        assertThat(ship.getPosition(), is(new Position(2, 2)));
    }

    @Test(expected = GameException.class)
    public void shipNavigateThrowsExceptionWhenOutOfMap() throws Exception {
        Ship ship = new Ship(new Position(4, 2, Position.Cardinals.V), mapSize);
        ship.setRoute("LMMLMMR");
        ship.navigate();
    }

    @Test
    public void shipGetAttackSetsCorrectStatus() throws Exception {
        Ship ship = new Ship(new Position(2, 5, Position.Cardinals.S), mapSize);
        ship.getAttack(new Position(2, 5));
        assertThat(ship.getStatus(), is("SUNK"));
    }
}
