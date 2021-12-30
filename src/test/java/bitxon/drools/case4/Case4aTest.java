package bitxon.drools.case4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import bitxon.drools.AbstractTest;
import bitxon.drools.model.player.Player;
import bitxon.drools.model.player.Rank;
import bitxon.drools.support.DroolsHelper;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kie.api.runtime.StatelessKieSession;

@DisplayName("Case4: DRL template with String Array Data Provider")
class Case4aTest extends AbstractTest {

    @Override
    protected StatelessKieSession newStatelessKieSession() {
        InputStream template = getClass().getClassLoader()
            .getResourceAsStream("rules/case4/rank-calculation-template.drl");
        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
            new String[]{"Rank 0", "0", "100", "Rank.NONE"},
            new String[]{"Rank 1", "100", "200", "Rank.IRON"},
            new String[]{"Rank 2", "200", "300", "Rank.BRONZE"},
            new String[]{"Rank 3", "300", "400", "Rank.SILVER"},
            new String[]{"Rank 4", "400", "500", "Rank.GOLD"},
        });

        DataProviderCompiler compiler = new DataProviderCompiler();
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
}
