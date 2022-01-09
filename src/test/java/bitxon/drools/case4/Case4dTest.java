package bitxon.drools.case4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Types;

import bitxon.drools.AbstractTest;
import bitxon.drools.model.player.Player;
import bitxon.drools.model.player.Rank;
import bitxon.drools.support.DroolsHelper;
import org.drools.template.jdbc.ResultSetGenerator;
import org.h2.tools.SimpleResultSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kie.api.runtime.StatelessKieSession;

@DisplayName("Case4d: DRL template with JDBC Result Set as Data Provider")
class Case4dTest extends AbstractTest {

    @Override
    protected StatelessKieSession newStatelessKieSession() {
        InputStream template = getClass().getClassLoader()
            .getResourceAsStream("rules/case4/rank-calculation-template.drl");

        // To simplify example we use mock ResultSet, but we could use real Db connection and query execution
        ResultSet rs = createTestResultSet();

        ResultSetGenerator compiler = new ResultSetGenerator();
        String drl = compiler.compile(rs, template);
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

    private static ResultSet createTestResultSet() {
        final SimpleResultSet rs = new SimpleResultSet();
        rs.addColumn("ruleName", Types.VARCHAR, 10, 0);
        rs.addColumn("minKills", Types.INTEGER, 10, 0);
        rs.addColumn("maxKills", Types.INTEGER, 10, 0);
        rs.addColumn("resultRank", Types.VARCHAR, 10, 0);

        rs.addRow("Rank 0", 0, 100, "Rank.NONE");
        rs.addRow("Rank 1", 100, 200, "Rank.IRON");
        rs.addRow("Rank 2", 200, 300, "Rank.BRONZE");
        rs.addRow("Rank 3", 300, 400, "Rank.SILVER");
        rs.addRow("Rank 4", 400, 500, "Rank.GOLD");

        return rs;
    }
}
