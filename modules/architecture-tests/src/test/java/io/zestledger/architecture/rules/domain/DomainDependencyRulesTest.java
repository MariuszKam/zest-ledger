package io.zestledger.architecture.rules.domain;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.zestledger.architecture.Modules;

@AnalyzeClasses(
        packages = "io.zestledger.domain..",
        importOptions = ImportOption.DoNotIncludeTests.class)
class DomainDependencyRulesTest {

    private static final String[] ALLOWED_PACKAGES = {Modules.DOMAIN.getPackageName(), "java.."};

    private static final String NOT_ALLOWED_CLASS_NAMING =
            ".*(Controller|Repository|Client|Configuration|Service)$";

    @ArchTest
    static final ArchRule basic_dependencies_on_domain =
            classes()
                    .that()
                    .resideInAPackage(Modules.DOMAIN.getPackageName())
                    .should()
                    .onlyDependOnClassesThat()
                    .resideInAnyPackage(ALLOWED_PACKAGES);

    @ArchTest
    static final ArchRule not_allowed_name_in_domain =
            noClasses()
                    .that()
                    .resideInAPackage(Modules.DOMAIN.getPackageName())
                    .should()
                    .haveNameMatching(NOT_ALLOWED_CLASS_NAMING);
}
