package bitxon.drools.case1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bitxon.drools.AbstractTest;
import bitxon.drools.model.player.Player;
import bitxon.drools.model.player.Rank;
import bitxon.drools.support.DroolsHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kie.api.runtime.StatelessKieSession;

@DisplayName("Case1: All rules in one DRL file")
class Case1Test extends AbstractTest {

    @Override
    protected StatelessKieSession newStatelessKieSession() {
        return DroolsHelper
            .createContainerFromFiles("rules/case1/rank-calculation.drl")
            .newStatelessKieSession();
    }

    @CsvSource({
        "0,   NONE",
        "100, IRON",
        "200, BRONZE",
        "300, SILVER",
        "400, GOLD",
        "500, ", // No Rules for this case
    })
    @ParameterizedTest
    void calculateRank(int kills, Rank expectedRank) {
        Player player = Player.builder().kills(kills).build();
        Rank actualRank = execute(player);

        assertEquals(expectedRank, actualRank);
    }
}
