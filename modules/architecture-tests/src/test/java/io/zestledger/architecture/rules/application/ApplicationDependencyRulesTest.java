package io.zestledger.architecture.rules.application;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.zestledger.architecture.Modules;

@AnalyzeClasses(
        packages = "io.zestledger.application..",
        importOptions = ImportOption.DoNotIncludeTests.class)
class ApplicationDependencyRulesTest {

    private static final String[] ALLOWED_PACKAGES = {
        Modules.DOMAIN.getPackageName(), Modules.APPLICATION.getPackageName(), "java.."
    };

    @ArchTest
    static final ArchRule allowed_dependencies_on_application =
            classes()
                    .that()
                    .resideInAPackage(Modules.APPLICATION.getPackageName())
                    .should()
                    .onlyDependOnClassesThat()
                    .resideInAnyPackage(ALLOWED_PACKAGES);
}
