package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	TestAlphabetTranslation.class,
	TestCombination.class, 
	TestExhaustiveFourVariables.class,
	TestExhaustiveThreeVariables.class,
	TestHelperFunctions.class,
	TestLargeVariableNumbers.class,
	TestNegatedCombination.class,
	TestPredicateNumberSystem.class,
	TestRandomFiveVariables.class,
	TestTwoPowers.class
})

public final class AllTests {
	
}