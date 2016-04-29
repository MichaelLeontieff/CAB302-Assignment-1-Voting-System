package asgn1Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import asgn1Election.CandidateIndex;

public class CandidateIndexTests {
	

	// COPY TESTS //
	@Test
	public void ObjectCopyTest() {
		CandidateIndex test = new CandidateIndex(4);
		CandidateIndex testCopy = test.copy();
		test = null;
		assertTrue("failed to create deep copy", testCopy != null);
	}
	
	// COMPARE TO TESTS
	@Test
	public void CompareEqualCandidateIndexes() {
		CandidateIndex test1 = new CandidateIndex(4);
		CandidateIndex test2 = new CandidateIndex(4);
		assertTrue("failed to match equal candidate indexes", test1.compareTo(test2) == 0);
	}
	
	@Test
	public void CompareLesserCandidateIndex() {
		CandidateIndex test1 = new CandidateIndex(4);
		CandidateIndex test2 = new CandidateIndex(6);
		assertTrue("failed to detect greater candidate index", test2.compareTo(test1) == 1);
	}
	
	@Test
	public void CompareGreaterCandidateIndex() {
		CandidateIndex test1 = new CandidateIndex(4);
		CandidateIndex test2 = new CandidateIndex(3);
		assertTrue("failed to detect lesser candidate index", test2.compareTo(test1) == -1);
	}
	
	// SET VALUE TESTS //
	@Test
	public void SetValueTest() {
		CandidateIndex test = new CandidateIndex(4);
		test.setValue(10);
		CandidateIndex compare = new CandidateIndex(10);
		assertEquals("Set Value failed", compare.toString(), test.toString());
	}
	
	// INCREMENT INDEX TESTS //
	@Test
	public void IncrementIndexTest() {
		CandidateIndex test = new CandidateIndex(4);
		CandidateIndex testCompare = new CandidateIndex(5);
		test.incrementIndex();
		assertEquals("unexpected output after increment", testCompare.toString(), test.toString());
	}
	
	
	// IN RANGE TESTS //
	@Test
	public void WithinRangeTest() {
		assertTrue("failed to register within range candidate", CandidateIndex.inRange(6));
	}
	
	@Test
	public void BelowMinimumRangeThresholdTest() {
		assertFalse("failed to register out-of-range candidate", CandidateIndex.inRange(0));
	}
	
	@Test
	public void AboveMaximumRangeThresholdTest() {
		assertFalse("failed to register out-of-range candidate", CandidateIndex.inRange(16));
	}
	
	@Test
	public void MaxBorderRangeThresholdTest() {
		assertTrue("failed to register out-of-range candidate", CandidateIndex.inRange(15));
	}
	
	@Test
	public void MinBorderRangeThresholdTest() {
		assertTrue("failed to register out-of-range candidate", CandidateIndex.inRange(1));
	}

}
