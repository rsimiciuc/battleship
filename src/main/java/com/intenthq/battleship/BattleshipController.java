package com.intenthq.battleship;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BattleshipController {

    public static final String OUTPUT_ATT = "output";

    @RequestMapping("/battleship")
    public String battleship(ModelMap model) {
        return "battleship";
    }

    @RequestMapping("/battleship/exercise")
    public String exercise(@RequestParam(value = "input", required = false) String input, ModelMap model) {
        if (!StringUtils.isEmpty(input)) {
            String output = "";
            try {
                output = processInput(input);
            } catch (GameException ex) {
                output = ex.getMessage();
            } finally {
                model.addAttribute(OUTPUT_ATT, output);
            }
        }
        return "exercise";
    }

    private String processInput(String input) throws GameException {

        InputParser parser = new InputParser(input);
        List<Ship> ships = parser.getShips();

        Map<Position, Ship> positions = new HashMap<>();

        for (Ship ship : ships) {
            ship.navigate();
            positions.put(ship.getPosition(), ship);
        }

        if (positions.size() != ships.size()) {
            throw new GameException("Two or more ships on the same position");
        }

        for (Ship defender : ships) {
            for (Ship attacker : ships) {
                if (defender != attacker) {
                    defender.getAttack(attacker.getTarget());
                }
            }
        }

        String output = "";
        for (Ship ship : ships) {
            output += ship + "\n";
        }
        return output;
    }
}
