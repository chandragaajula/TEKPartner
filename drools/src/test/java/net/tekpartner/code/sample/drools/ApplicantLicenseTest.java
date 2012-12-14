package net.tekpartner.code.sample.drools;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ApplicantLicenseTest {

	private KnowledgeBase kbase;

	@Before
	public void setup() {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		kbuilder.add(
				ResourceFactory
						.newClassPathResource("net/tekpartner/code/sample/drools/applicant-license.drl"),
				ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

	}

	@Test
	public void testApplicantLicense() {
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory
				.newFileLogger(ksession, "test");
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.newStatefulKnowledgeSession();

		ApplicantForm applicant1 = new ApplicantForm();
		applicant1.setApplicantId(1);

		ApplicantForm applicant2 = new ApplicantForm();
		applicant2.setApplicantId(2);

		ksession.setGlobal("applicantDao", new ApplicantDaoImpl()); // assign
																	// the
																	// global
																	// dao

		ksession.insert(applicant1);
		ksession.insert(applicant2);
		ksession.fireAllRules();

		ksession.dispose();

		Assert.assertTrue(applicant1.isEligible() == false);// John is not
															// eligible
		Assert.assertTrue(applicant2.isEligible() == true); // Peter is eligible
	}

}