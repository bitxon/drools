package bitxon.drools.case4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.util.List;

import bitxon.drools.AbstractTest;
import bitxon.drools.model.player.Player;
import bitxon.drools.model.player.Rank;
import bitxon.drools.support.DroolsHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.drools.template.ObjectDataCompiler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kie.api.runtime.StatelessKieSession;

@DisplayName("Case4: DRL template with Object Data Provider")
class Case4bTest extends AbstractTest {

    @Override
    protected StatelessKieSession newStatelessKieSession() {
        InputStream template = getClass().getClassLoader()
            .getResourceAsStream("rules/case4/rank-calculation-template.drl");
        List<RuleMetadata> dataProvider = List.of(
            new RuleMetadata("Rank 0", 0, 100, Rank.NONE),
            new RuleMetadata("Rank 1", 100, 200, Rank.IRON),
            new RuleMetadata("Rank 2", 200, 300, Rank.BRONZE),
            new RuleMetadata("Rank 3", 300, 400, Rank.SILVER),
            new RuleMetadata("Rank 4", 400, 500, Rank.GOLD)
        );

        ObjectDataCompiler compiler = new ObjectDataCompiler();
        String drl = compiler.compile(dataProvider, template);
        return DroolsHelper.createSessionFromDrl(drl);
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

    @Getter
    @AllArgsConstructor
    // This class must be 'public' to be visible in DRL file
    public static class RuleMetadata {
        private String ruleName;
        private int minKills;
        private int maxKills;
        private Rank resultRank;

        public String getResultRank() {
            // We need to provide value in 'Rank.SILVER' format
            return Rank.class.getSimpleName() + "." + resultRank.toString();
        }
    }
}
