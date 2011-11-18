package ar.gov.anses.seginf.intrusos;

import org.drools.ClockType;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.EventProcessingOption;
import org.drools.io.ResourceFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;

public class CEPEngine {
	public WorkingMemoryEntryPoint ep;
	public StatefulKnowledgeSession ksession;
		
	public void initEngine(){
	       // Creates a knowledge base
        final KnowledgeBase kbase = createKnowledgeBase( "sudo.drl" );
        
        // Creates a knowledge session
        this.ksession = createKnowledgeSession( kbase );
        
        // Gets the stream entry point 
        this.ep = ksession.getWorkingMemoryEntryPoint( "syslog" );
        
        System.out.println("CEP Levantado");
        // Starts to fire rules in Drools Fusion
//        ksession.fireUntilHalt();
	}

    /**
     * Creates a Drools KnowledgeBase and adds the given rules file into it
     */
    private static KnowledgeBase createKnowledgeBase( final String rulesFile ) {
        // Parses and compiles the rules file
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add( ResourceFactory.newClassPathResource( rulesFile ), ResourceType.DRL );
        
        // Configures the Stream mode
        KnowledgeBaseConfiguration conf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        conf.setOption( EventProcessingOption.STREAM );
        
        // Creates the knowledge base and adds the rules
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase( conf );
        kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
        return kbase;
    }
    
    /**
     * Creates a Drools Stateful Knowledge Session
     */
    private static StatefulKnowledgeSession createKnowledgeSession( final KnowledgeBase kbase ) {
        final KnowledgeSessionConfiguration ksconf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
        ksconf.setOption( ClockTypeOption.get( ClockType.REALTIME_CLOCK.getId() ) );
        final StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession( ksconf, null );
        return ksession;
    }
	
}
