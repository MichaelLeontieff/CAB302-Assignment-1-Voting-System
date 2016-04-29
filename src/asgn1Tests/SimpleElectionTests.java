package asgn1Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.Election;
import asgn1Election.ElectionException;
import asgn1Election.SimpleElection;
import asgn1Election.VoteList;
import asgn1Util.NumbersException;

public class SimpleElectionTests {

	private Election e;
	
	private String MorgulValeSimpleOutputLog = "Results for election: MorgulValeSimple\n" + 
			"Enrolment: 83483\n" + 
			"\n" + 
			"Shelob              Monster Spider Party          (MSP)\n" + 
			"Gorbag              Filthy Orc Party              (FOP)\n" + 
			"Shagrat             Stinking Orc Party            (SOP)\n" + 
			"Black Rider         Nazgul Party                  (NP)\n" + 
			"Mouth of Sauron     Whatever Sauron Says Party    (WSSP)\n" + 
			"\n" + 
			"\n" + 
			"Counting primary votes; 5 alternatives available\n" + 
			"\n" + 
			"Simple election: MorgulValeSimple\n" + 
			"\n" + 
			"Shelob (MSP)                10\n" + 
			"Gorbag (FOP)                 5\n" + 
			"Shagrat (SOP)                4\n" + 
			"Black Rider (NP)             9\n" + 
			"Mouth of Sauron (WSSP)       3\n" + 
			"\n" + 
			"Informal                     0\n" + 
			"\n" + 
			"Votes Cast                  31\n" + 
			"\n" + 
			"\n" + 
			"Candidate Shelob (Monster Spider Party) is the winner with 10 votes...\n";
	
	private String MinMorgulValeTieSimpleOutputLog = "Results for election: MinMorgulValeTieSimple\n" + 
			"Enrolment: 25\n" + 
			"\n" + 
			"Shelob              Monster Spider Party          (MSP)\n" + 
			"Gorbag              Filthy Orc Party              (FOP)\n" + 
			"Shagrat             Stinking Orc Party            (SOP)\n" + 
			"\n" + 
			"\n" + 
			"Counting primary votes; 3 alternatives available\n" + 
			"\n" + 
			"Simple election: MinMorgulValeTieSimple\n" + 
			"\n" + 
			"Shelob (MSP)                 8\n" + 
			"Gorbag (FOP)                 7\n" + 
			"Shagrat (SOP)                3\n" + 
			"\n" + 
			"Informal                     3\n" + 
			"\n" + 
			"Votes Cast                  21\n" + 
			"\n" + 
			"\n" + 
			"Candidate Shelob (Monster Spider Party) is the winner with 8 votes...\n";
	
	private String MinMorgulValeSimpleOutputLog = "Results for election: MinMorgulValeSimple\n" + 
			"Enrolment: 25\n" + 
			"\n" + 
			"Shelob              Monster Spider Party          (MSP)\n" + 
			"Gorbag              Filthy Orc Party              (FOP)\n" + 
			"Shagrat             Stinking Orc Party            (SOP)\n" + 
			"\n" + 
			"\n" + 
			"Counting primary votes; 3 alternatives available\n" + 
			"\n" + 
			"Simple election: MinMorgulValeSimple\n" + 
			"\n" + 
			"Shelob (MSP)                 8\n" + 
			"Gorbag (FOP)                 8\n" + 
			"Shagrat (SOP)                3\n" + 
			"\n" + 
			"Informal                     4\n" + 
			"\n" + 
			"Votes Cast                  23\n" + 
			"\n" + 
			"\n" + 
			"Candidate Shelob (Monster Spider Party) is the winner with 8 votes...\n";
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void TypeTest() {
		e = new SimpleElection("MinMorgulVale");
		assertEquals("Failed to get correct voting type", 0, e.getType());
	}
	

	// IS FORMAL TESTS //
	
	@Test
	public void FormalSixPrefs() {
		e = new SimpleElection("test");
		VoteList v = new VoteList(5);
		v.addPref(1);
		v.addPref(2);
		v.addPref(2);
		v.addPref(3);
		v.addPref(4);
		assertTrue("failed to register invalid vote", e.isFormal(v));
	}
	
	@Test
	public void FormalVoteTest() {
		e = new SimpleElection("test");
		VoteList v = new VoteList(3);
		v.addPref(1);
		v.addPref(2);
		v.addPref(3);
		assertTrue("failed to register valid vote", e.isFormal(v));
	}
	
	@Test
	public void FormalVoteTestTwo() {
		e = new SimpleElection("test");
		VoteList v = new VoteList(3);
		v.addPref(1);
		v.addPref(2);
		v.addPref(2);
		assertTrue("failed to register valid vote", e.isFormal(v));	
	}
	
	@Test
	public void InFormalDuplicateFirst() {
		e = new SimpleElection("test");
		VoteList v = new VoteList(3);
		v.addPref(1);
		v.addPref(1);
		v.addPref(2);
		assertFalse("failed to register invalid vote", e.isFormal(v));
	}
	
	@Test
	public void InFormalNoFirst() {
		e = new SimpleElection("test");
		VoteList v = new VoteList(3);
		v.addPref(2);
		v.addPref(3);
		v.addPref(2);
		assertFalse("failed to register invalid vote", e.isFormal(v));
	}
	
	@Test
	public void InFormalOutOfBounds() {
		e = new SimpleElection("test");
		VoteList v = new VoteList(3);
		v.addPref(1);
		v.addPref(7);
		v.addPref(2);
		assertFalse("failed to register invalid vote", e.isFormal(v));
	}
	
	// FIND WINNER //
	@Test
	public void findWinnerMorgulValeSimple() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election e = new SimpleElection("MorgulValeSimple");
		e.loadDefs();
		e.loadVotes();
		String output = e.findWinner();
		boolean match = false;
		if (MorgulValeSimpleOutputLog.equals(output)) {
			match = true;
		}
		assertTrue(match);
	}
	
	@Test
	public void findWinnerMinMorgulValeTieSimple() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election e = new SimpleElection("MinMorgulValeTieSimple");
		e.loadDefs();
		e.loadVotes();
		String output = e.findWinner();
		boolean match = false;
		if (MinMorgulValeTieSimpleOutputLog.equals(output)) {
			match = true;
		}
		assertTrue(match);
	}
	
	@Test
	public void findWinnerMinMorgulValeSimple() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election e = new SimpleElection("MinMorgulValeSimple");
		e.loadDefs();
		e.loadVotes();
		String output = e.findWinner();
		boolean match = false;
		if (MinMorgulValeSimpleOutputLog.equals(output)) {
			match = true;
		}
		assertTrue(match);
	}
}
