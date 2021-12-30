package bitxon.drools.model.player;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Player {
    private Type type;
    private int strength;
    private int intellect;
    private int agility;
    private int kills;
}
