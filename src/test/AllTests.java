package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	TestAlphabetTranslation.class,
	TestCollapse.class,
	TestCombination.class, 
	TestExhaustiveFourVariables.class,
	TestExhaustiveThreeVariables.class,
	TestHelperFunctions.class,
	TestLargeVariableNumbers.class,
	TestManualSatisfies.class,
	TestNegatedCombination.class,
	TestPredicateNumberSystem.class,
	TestRandomFiveVariables.class,
	TestSingleByteConversions.class,
	TestSolvingVector.class,
	TestTwoPowers.class
})

public final class AllTests {
	
}