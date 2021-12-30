package bitxon.drools.support;

import static org.kie.api.builder.Message.Level.ERROR;
import static org.kie.api.builder.Message.Level.WARNING;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.drools.decisiontable.DecisionTableProviderImpl;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

public final class DroolsHelper {

    private DroolsHelper() {
    }

    public static List<Message> verify(String... files) {
        final KieHelper kieHelper = new KieHelper();
        Arrays.stream(files).map(ResourceFactory::newClassPathResource).forEach(kieHelper::addResource);
        final Results results = kieHelper.verify();
        return results.getMessages(ERROR, WARNING);
    }

    public static KieContainer createContainerFromFiles(String... files) {
        final KieServices kieServices = KieServices.Factory.get();
        final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        Arrays.stream(files).map(ResourceFactory::newClassPathResource).forEach(kieFileSystem::write);
        final KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        final List<Message> errors = kieBuilder.buildAll().getResults().getMessages(ERROR, WARNING);
        if (errors.size() > 0) {
            throw new RuntimeException(errors.stream().map(Objects::toString).collect(Collectors.joining("\n")));
        }
        final KieRepository kieRepository = kieServices.getRepository();
        final ReleaseId releaseId = kieRepository.getDefaultReleaseId();
        final KieContainer kieContainer = kieServices.newKieContainer(releaseId);
        return kieContainer;
    }

    public static KieBase createKieBaseFromFiles(String... files) {
        final KieHelper kieHelper = new KieHelper();
        Arrays.stream(files).map(ResourceFactory::newClassPathResource).forEach(kieHelper::addResource);
        return kieHelper.build();
    }

    public static StatelessKieSession createSessionFromDrl(String... drls) {
        final KieHelper kieHelper = new KieHelper();
        Arrays.stream(drls).forEach(drl -> kieHelper.addContent(drl, ResourceType.DRL));
        return kieHelper.build().newStatelessKieSession();
    }

    public static String generateDrlFromExcel1(String excelFile) {
        final Resource resource = ResourceFactory.newClassPathResource(excelFile);
        final SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        return compiler.compile(resource, InputType.XLS);
    }

    public static String generateDrlFromExcel2(String excelFile) {
        final Resource resource = ResourceFactory.newClassPathResource(excelFile);
        final DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLSX);
        final DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();
        return decisionTableProvider.loadFromResource(resource, configuration);
    }
}
