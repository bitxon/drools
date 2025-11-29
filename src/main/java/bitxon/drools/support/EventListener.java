package bitxon.drools.support;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EventListener extends DefaultAgendaEventListener {

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        String triggeredRuleName = event.getMatch().getRule().getName();
        List<Object> triggeredByObjects = event.getMatch().getObjects();

        log.info("""
                AfterMatchFiredEvent:
                Rule Triggered: '{}'
                Triggered by facts: \n\t{}
                """,
            triggeredRuleName,
            triggeredByObjects.stream().map(Object::toString).collect(Collectors.joining("\n\t"))
        );
    }
}
