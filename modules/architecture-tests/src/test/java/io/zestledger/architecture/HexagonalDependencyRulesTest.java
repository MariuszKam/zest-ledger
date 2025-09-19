package io.zestledger.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "io.zestledger", importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalDependencyRulesTest {

    @ArchTest
    static final ArchRule domain_should_be_pure =
            noClasses()
                    .that()
                    .resideInAPackage(Modules.DOMAIN.getPackageName())
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            Modules.APPLICATION.getPackageName(),
                            Modules.ADAPTERS_IN.getPackageName(),
                            Modules.ADAPTERS_OUT.getPackageName(),
                            Modules.PLATFORM.getPackageName());

    @ArchTest
    static final ArchRule application_should_depend_on_domain =
            classes()
                    .that()
                    .resideInAPackage(Modules.APPLICATION.getPackageName())
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage(Modules.DOMAIN.getPackageName());

    @ArchTest
    static final ArchRule application_should_not_depend_on_modules =
            noClasses()
                    .that()
                    .resideInAPackage(Modules.APPLICATION.getPackageName())
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            Modules.ADAPTERS_IN.getPackageName(),
                            Modules.ADAPTERS_OUT.getPackageName(),
                            Modules.PLATFORM.getPackageName());

    @ArchTest
    static final ArchRule adapters_in_should_not_depend_on_adapters_out =
            noClasses()
                    .that()
                    .resideInAPackage(Modules.ADAPTERS_IN.getPackageName())
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage(Modules.ADAPTERS_OUT.getPackageName());

    @ArchTest
    static final ArchRule adapters_out_should_not_depend_on_adapters_in =
            noClasses()
                    .that()
                    .resideInAPackage(Modules.ADAPTERS_OUT.getPackageName())
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage(Modules.ADAPTERS_IN.getPackageName());

    @ArchTest
    static final ArchRule adapters_should_depend_on_application_and_domain =
            classes()
                    .that()
                    .resideInAnyPackage(
                            Modules.ADAPTERS_IN.getPackageName(),
                            Modules.ADAPTERS_OUT.getPackageName())
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            Modules.DOMAIN.getPackageName(), Modules.APPLICATION.getPackageName());

    @ArchTest
    static final ArchRule no_modules_should_depend_on_platform =
            noClasses()
                    .that()
                    .resideOutsideOfPackage(Modules.PLATFORM.getPackageName())
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage(Modules.PLATFORM.getPackageName());
}
