package bitxon.drools.case4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import bitxon.drools.AbstractTest;
import bitxon.drools.model.player.Player;
import bitxon.drools.model.player.Rank;
import bitxon.drools.support.DroolsHelper;
import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kie.api.runtime.StatelessKieSession;

@DisplayName("Case4c: DRL template with Microsoft Excel Data Provider")
class Case4cTest extends AbstractTest {

    @Override
    protected StatelessKieSession newStatelessKieSession() {
        InputStream template = getClass().getClassLoader()
            .getResourceAsStream("rules/case4/rank-calculation-template.drl");
        InputStream data = getClass().getClassLoader()
            .getResourceAsStream("rules/case4/rank-calculation-attributes.xlsx");

        ExternalSpreadsheetCompiler compiler = new ExternalSpreadsheetCompiler();
        String drl = compiler.compile(data, template, 2, 1); // Start with Row #2 to skip header
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
