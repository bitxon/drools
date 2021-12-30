package bitxon.drools.model.event;

import bitxon.drools.model.player.Rank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankCalculated {
    private Rank rank;
}
