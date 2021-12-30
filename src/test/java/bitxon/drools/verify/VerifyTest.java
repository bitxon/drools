package bitxon.drools.verify;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import bitxon.drools.support.DroolsHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.api.builder.Message;

@DisplayName("Verify: Validate Files")
class VerifyTest {

    @Test
    void valid() {
        List<Message> errors = DroolsHelper.verify("rules/verify/valid.drl");
        assertEquals(0, errors.size());
    }

    @Test
    void invalid() {
        List<Message> errors = DroolsHelper.verify("rules/verify/invalid-1.drl");
        assertEquals(2, errors.size());
    }
}
