package bitxon.drools.case3;

import static bitxon.drools.model.player.Type.ASSASSIN;
import static bitxon.drools.model.player.Type.WARRIOR;
import static bitxon.drools.model.player.Type.WIZARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.stream.Stream;

import bitxon.drools.AbstractTest;
import bitxon.drools.model.player.Player;
import bitxon.drools.support.DroolsHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.kie.api.runtime.StatelessKieSession;

@DisplayName("Case3: Bonus Rule depends on Rank Rule")
class Case3Test extends AbstractTest {

    @Override
    protected StatelessKieSession newStatelessKieSession() {
        return DroolsHelper
            .createContainerFromFiles(
                "rules/case3/1-rank-calculation.drl",
                "rules/case3/2-bonus-calculation.drl",
                "rules/case3/3-damage-calculation.drl")
            .newStatelessKieSession();
    }

    static Stream<Arguments> provideTestData() {
        return Stream.of(
            of(Player.builder().type(WARRIOR).strength(100).kills(0).build(),   100 + 10 + 0),
            of(Player.builder().type(WARRIOR).strength(100).kills(100).build(), 100 + 10 + 1),
            of(Player.builder().type(WARRIOR).strength(100).kills(200).build(), 100 + 10 + 2),
            of(Player.builder().type(WARRIOR).strength(100).kills(300).build(), 100 + 10 + 3),
            of(Player.builder().type(WARRIOR).strength(100).kills(400).build(), 100 + 10 + 4),

            of(Player.builder().type(WIZARD).intellect(100).kills(0).build(),   100 + 20 + 0),
            of(Player.builder().type(WIZARD).intellect(100).kills(100).build(), 100 + 20 + 2),
            of(Player.builder().type(WIZARD).intellect(100).kills(200).build(), 100 + 20 + 4),
            of(Player.builder().type(WIZARD).intellect(100).kills(300).build(), 100 + 20 + 6),
            of(Player.builder().type(WIZARD).intellect(100).kills(400).build(), 100 + 20 + 8),

            of(Player.builder().type(ASSASSIN).agility(100).kills(0).build(),   100 + 30 + 1),
            of(Player.builder().type(ASSASSIN).agility(100).kills(100).build(), 100 + 30 + 3),
            of(Player.builder().type(ASSASSIN).agility(100).kills(200).build(), 100 + 30 + 5),
            of(Player.builder().type(ASSASSIN).agility(100).kills(300).build(), 100 + 30 + 7),
            of(Player.builder().type(ASSASSIN).agility(100).kills(400).build(), 100 + 30 + 9)
        );
    }

    @MethodSource(value = "provideTestData")
    @ParameterizedTest
    void calculateAttackDamage(Player player, Integer expectedBonus) {
        Integer damage = execute(player);

        assertEquals(expectedBonus, damage);
    }
}
