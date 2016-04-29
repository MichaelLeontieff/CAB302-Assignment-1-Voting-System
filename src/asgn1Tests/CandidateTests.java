package asgn1Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.Candidate;
import asgn1Election.ElectionException;

public class CandidateTests {
	
	Candidate testCandidate;

	@Before
	public void setUp() throws Exception {
		testCandidate = new Candidate("testName", "testParty", "testAbbrev", 666 );
	}

	// CONSTRUCTOR TESTS //
	
		// object creation and access //
	@Test
	public void ObjectCreation() throws ElectionException {
		// expect nothing
		Candidate test = new Candidate("testName", "testParty", "testAbbrev", 5 );
		assertNotNull(test);
	}
	
		// check if static class //
	
	@Test
	public void MultipleObjectCreation() throws ElectionException {
		Candidate one = new Candidate("testName", "testParty", "testAbbrev", 5 );
		Candidate two = new Candidate("testName", "testParty", "testAbbrev", 10 );
		assertEquals("failed independant vote counts", 15, one.getVoteCount() + two.getVoteCount());
	}
	
		// exceptions //
	
	@Test (expected = ElectionException.class)
	public void ExceptionEmptyName() throws ElectionException {
		Candidate test = new Candidate("", "testParty", "testAbbrev", 5 );
	}
	
	@Test (expected = ElectionException.class)
	public void ExceptionNullName() throws ElectionException {
		Candidate test = new Candidate(null, "testParty", "testAbbrev", 5 );
	}
	
	@Test (expected = ElectionException.class)
	public void ExceptionEmptyParty() throws ElectionException {
		Candidate test = new Candidate("testName", "", "testAbbrev", 5 );
	}
	
	@Test (expected = ElectionException.class)
	public void ExceptionNullParty() throws ElectionException {
		Candidate test = new Candidate("testName", null, "testAbbrev", 5 );
	}
	
	@Test (expected = ElectionException.class)
	public void ExceptionEmptyAbbreviation() throws ElectionException {
		Candidate test = new Candidate("testName", "testParty", "", 5 );
	}
	
	@Test (expected = ElectionException.class)
	public void ExceptionNullAbbreviation() throws ElectionException {
		Candidate test = new Candidate("testName", "testParty", null, 5 );
	}
	
	@Test (expected = ElectionException.class)
	public void ExceptionLessThanZeroVotes() throws ElectionException {
		Candidate test = new Candidate("testName", "testParty", "testAbbrev", -3 );
	}
	
	@Test
	public void NoExceptionForZeroVotes() throws ElectionException {
		Candidate test = new Candidate("testName", "testParty", "testAbbrev", 0 );
	}
	
	@Test
	public void NoExceptionForValidVotes() throws ElectionException {
		Candidate test = new Candidate("testName", "testParty", "testAbbrev", 10 );
	}
	
	
	
	// ACCESSOR TESTS //
	
		// Candidate Name
	public void AccessorCandidateName() {
		assertEquals("Candidate Name not accessed successfully", "testName", testCandidate.getName());
	}
		// Candidate Party
	public void AccessorCandidateParty() {
		assertEquals("Candidate Party not accessed successfully", "testParty", testCandidate.getParty());
	}
		// Candidate Vote Count
	@Test
	public void AccessorVoteCount() {
		assertEquals("Candidate Vote Count not accessed successfully", 666, testCandidate.getVoteCount());
	}
	
	// INCREMENT VOTE COUNT //
	
	@Test
	public void IncrementVoteCount() throws ElectionException {
		Candidate testCandidate = new Candidate("testName", "testParty", "testAbbrev", 10);
		testCandidate.incrementVoteCount();
		assertEquals("Vote not incremented successfully", 11, testCandidate.getVoteCount());
	}
	
	// COPY TEST //
	@Test
	public void CopyObject() throws ElectionException {
		Candidate copyCandidateTest = new Candidate("testName", "testParty", "testAbbrev", 666 );
		Candidate copiedCandidateTest = copyCandidateTest.copy();
		copyCandidateTest = null;
		assertEquals("Candidate not cloned successfully", 666, copiedCandidateTest.getVoteCount());
	}
	
	// GET VOTE COUNT STRING TEST //
	
	@Test
	public void GetVoteCountStringTest() {
		assertEquals("Vote count string does not match integer value", "666", testCandidate.getVoteCountString());
	}
}
