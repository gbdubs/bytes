package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	TestCombination.class, 
	TestHelperFunctions.class,
	TestPredicateNumberSystem.class,
	TestTwoPowers.class,
	TestRandomFiveVariables.class,
	TestNegatedCombination.class
})

public final class AllTests {
	
}