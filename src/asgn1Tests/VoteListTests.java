package asgn1Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.CandidateIndex;
import asgn1Election.Vote;
import asgn1Election.VoteList;

public class VoteListTests {
	
	private static final int NumberOfCandidates = 3;
	VoteList original;

	@Before
	public void setUp() throws Exception {
	}
	
	// CONSTRUCTOR TESTS //
	@Test
	public void ObjectCreation() {
		original = new VoteList(NumberOfCandidates);
	}
	
	// ADD PREF TESTS //
	
		// test if the expected true, false is returned//
	@Test
	public void AddPreferenceBoolTrueTest() {
		VoteList test = new VoteList(NumberOfCandidates);
		assertTrue("failed to return true on valid added pref", test.addPref(2));
	}
	
	@Test
	public void AddPreferenceBoolFalseTest() {
		VoteList test = new VoteList(NumberOfCandidates);
		// fill up the VoteList to the number of candidates so another shouldn't be added
		test.addPref(1);
		test.addPref(2);
		test.addPref(3);
		assertFalse("failed to return false on un-needed preference", test.addPref(2));
	}
	
	@Test
	public void AddPreferenceTest() {
		VoteList test = new VoteList(NumberOfCandidates);
		test.addPref(1);
		test.addPref(2);
		test.addPref(3);
		assertEquals("Failed to add preferences", "1 2 3 ", test.toString());
	}
	
	// GET PREFERENCE TESTS //
	
	@Test
	public void GetPreference() {
		original = new VoteList(NumberOfCandidates);
		original.addPref(3);
		original.addPref(1);
		original.addPref(2);
		CandidateIndex comparison = new CandidateIndex(2);
		assertEquals("Preference doesn't match", comparison.toString(), original.getPreference(1).toString());
	}
	
	// COPY VOTE TEST //
	
	@Test
	public void CopyVote() {
		VoteList original = new VoteList(NumberOfCandidates);
		original.addPref(2);
		original.addPref(3);
		original.addPref(1);
		VoteList copy = (VoteList) original.copyVote();
		original = null;
		assertEquals("deep copy wasn't successful", "2 3 1 ", copy.toString());
	}
	
	// INVERT LIST TEST //

	@Test
	public void InvertListTest() {
		// Create VoteLists 
		Vote normal = new VoteList(NumberOfCandidates);
		Vote inverted = new VoteList(NumberOfCandidates);
		// Populate normal Vote
		normal.addPref(2);
		normal.addPref(3);
		normal.addPref(1);
		// Invert Normal Vote
		inverted = normal.invertVote();
		// normal vote is [2, 3, 1]
		// inverted vote is [3, 1, 2]
		assertEquals("3 1 2 ", inverted.toString());
		
	}
	
	@Test
	public void InvertListTestTwo() {
		// Create VoteLists 
		Vote normal = new VoteList(6);
		Vote inverted = new VoteList(6);
		// Populate normal Vote
		normal.addPref(6);
		normal.addPref(5);
		normal.addPref(2);
		normal.addPref(3);
		normal.addPref(1);
		normal.addPref(4);
		// Invert Normal Vote
		inverted = normal.invertVote();
		// normal vote is [6, 5, 2, 3, 1, 4]
		// inverted vote is [5, 3, 4, 6, 2, 1]
		assertEquals("5 3 4 6 2 1 ", inverted.toString());
		
	}

	// ITERATOR //
	@Test
	public void IteratorNotNull() {
		Vote test = new VoteList(3);
		// populating just in case //
		test.addPref(1);
		test.addPref(2);
		test.addPref(3);
		assertNotNull(test.iterator());
	}
}
