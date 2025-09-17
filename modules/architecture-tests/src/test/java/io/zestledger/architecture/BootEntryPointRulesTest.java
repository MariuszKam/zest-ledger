package io.zestledger.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;


@AnalyzeClasses(packages = "io.zestledger", importOptions = ImportOption.DoNotIncludeTests.class)
public class BootEntryPointRulesTest {

	private static final String SPRING_BOOT_APPLICATION = "org.springframework.boot.autoconfigure.SpringBootApplication";

	@ArchTest
	static final ArchRule zestledger_application_only_in_platform =
		classes().that().haveSimpleName("ZestLedgerApplication")
			.should().resideInAPackage(Modules.PLATFORM.getPackageName());

	@ArchTest
	static final ArchRule no_application_suffix_outside_platform =
		classes().that()
			.haveSimpleNameEndingWith("Application")
			.should().resideInAPackage(Modules.PLATFORM.getPackageName());

	@ArchTest
	static final ArchRule spring_boot_annotation_inside_platform =
		classes().that().areAnnotatedWith(SPRING_BOOT_APPLICATION)
			.should().resideInAPackage(Modules.PLATFORM.getPackageName());

	@ArchTest
	static final ArchRule spring_boot_annotation_should_not_be_outside_platform =
		noClasses().that().areAnnotatedWith(SPRING_BOOT_APPLICATION)
			.should().resideOutsideOfPackage(Modules.PLATFORM.getPackageName());
}
