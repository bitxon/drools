package bitxon.drools;

import java.util.List;

import bitxon.drools.support.EventListener;
import bitxon.drools.support.Result;
import org.kie.api.runtime.StatelessKieSession;

public abstract class AbstractTest {

    protected abstract StatelessKieSession newStatelessKieSession();

    protected <R> R execute(Object... facts) {
        Result<R> result = new Result<>();

        StatelessKieSession session = newStatelessKieSession();
        session.addEventListener(new EventListener());
        session.setGlobal("result", result);
        session.execute(List.of(facts));

        return result.getValue();
    }
}
