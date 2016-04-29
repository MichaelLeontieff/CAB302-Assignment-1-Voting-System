package asgn1Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.Candidate;
import asgn1Election.CandidateIndex;
import asgn1Election.Election;
import asgn1Election.ElectionException;
import asgn1Election.PrefElection;
import asgn1Election.Vote;
import asgn1Election.VoteCollection;
import asgn1Election.VoteList;
import asgn1Util.NumbersException;

public class PrefElectionTests {
	
	private Election testingNoIssues;
	private TreeMap<CandidateIndex, Candidate> manualCDS;
	private Election e;
	
	// ---------- REFLECTION STUFF ---------- //
	private Class myObjectClass;
	private Field[] methods;
	
	// ---------- STRING OUTPUT TESTS ---------- //
	private final String MorgulValeOutputLog = "Results for election: MorgulVale\n" + 
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
			"Preferential election: MorgulVale\n" + 
			"\n" + 
			"Shelob (MSP)                 9\n" + 
			"Gorbag (FOP)                 5\n" + 
			"Shagrat (SOP)                4\n" + 
			"Black Rider (NP)             9\n" + 
			"Mouth of Sauron (WSSP)       3\n" + 
			"\n" + 
			"Informal                     0\n" + 
			"\n" + 
			"Votes Cast                  30\n" + 
			"\n" + 
			"\n" + 
			"Preferences required: distributing Mouth of Sauron: 3 votes\n" + 
			"\n" + 
			"Preferential election: MorgulVale\n" + 
			"\n" + 
			"Shelob (MSP)                 9\n" + 
			"Gorbag (FOP)                 5\n" + 
			"Shagrat (SOP)                6\n" + 
			"Black Rider (NP)            10\n" + 
			"\n" + 
			"Informal                     0\n" + 
			"\n" + 
			"Votes Cast                  30\n" + 
			"\n" + 
			"\n" + 
			"Preferences required: distributing Gorbag: 5 votes\n" + 
			"\n" + 
			"Preferential election: MorgulVale\n" + 
			"\n" + 
			"Shelob (MSP)                12\n" + 
			"Shagrat (SOP)                7\n" + 
			"Black Rider (NP)            11\n" + 
			"\n" + 
			"Informal                     0\n" + 
			"\n" + 
			"Votes Cast                  30\n" + 
			"\n" + 
			"\n" + 
			"Preferences required: distributing Shagrat: 7 votes\n" + 
			"\n" + 
			"Preferential election: MorgulVale\n" + 
			"\n" + 
			"Shelob (MSP)                14\n" + 
			"Black Rider (NP)            16\n" + 
			"\n" + 
			"Informal                     0\n" + 
			"\n" + 
			"Votes Cast                  30\n" + 
			"\n" + 
			"\n" + 
			"Candidate Black Rider (Nazgul Party) is the winner with 16 votes...\n";
	
	private final String MorgulValeTieOutputLog = "Results for election: MinMorgulValeTie\n" + 
			"Enrolment: 25\n" + 
			"\n" + 
			"Shelob              Monster Spider Party          (MSP)\n" + 
			"Gorbag              Filthy Orc Party              (FOP)\n" + 
			"Shagrat             Stinking Orc Party            (SOP)\n" + 
			"\n" + 
			"\n" + 
			"Counting primary votes; 3 alternatives available\n" + 
			"\n" + 
			"Preferential election: MinMorgulValeTie\n" + 
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
			"Preferences required: distributing Shagrat: 3 votes\n" + 
			"\n" + 
			"Preferential election: MinMorgulValeTie\n" + 
			"\n" + 
			"Shelob (MSP)                 9\n" + 
			"Gorbag (FOP)                 9\n" + 
			"\n" + 
			"Informal                     3\n" + 
			"\n" + 
			"Votes Cast                  21\n" + 
			"\n" + 
			"\n" + 
			"Preferences required: distributing Shelob: 9 votes\n" + 
			"\n" + 
			"Preferential election: MinMorgulValeTie\n" + 
			"\n" + 
			"Gorbag (FOP)                18\n" + 
			"\n" + 
			"Informal                     3\n" + 
			"\n" + 
			"Votes Cast                  21\n" + 
			"\n" + 
			"\n" + 
			"Candidate Gorbag (Filthy Orc Party) is the winner with 18 votes...\n";
	
	private final String MinMorgulValeOutputLog = "Results for election: MinMorgulVale\n" + 
			"Enrolment: 25\n" + 
			"\n" + 
			"Shelob              Monster Spider Party          (MSP)\n" + 
			"Gorbag              Filthy Orc Party              (FOP)\n" + 
			"Shagrat             Stinking Orc Party            (SOP)\n" + 
			"\n" + 
			"\n" + 
			"Counting primary votes; 3 alternatives available\n" + 
			"\n" + 
			"Preferential election: MinMorgulVale\n" + 
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
			"Preferences required: distributing Shagrat: 3 votes\n" + 
			"\n" + 
			"Preferential election: MinMorgulVale\n" + 
			"\n" + 
			"Shelob (MSP)                10\n" + 
			"Gorbag (FOP)                 8\n" + 
			"\n" + 
			"Informal                     3\n" + 
			"\n" + 
			"Votes Cast                  21\n" + 
			"\n" + 
			"\n" + 
			"Candidate Shelob (Monster Spider Party) is the winner with 10 votes...\n";
	
	@Before
	public void setUp() throws FileNotFoundException, IOException, ElectionException, NumbersException {
		testingNoIssues = new PrefElection("MinMorgulVale");
		testingNoIssues.loadDefs();
		testingNoIssues.loadVotes();	
	}
	
	// LOAD DEFS //
	
	// EXCEPTIONS //
	
	// TEST 1 null election line
	@Test (expected = ElectionException.class) 
	public void loadDefsNullElectionLine() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionOne = new PrefElection("test1");
		testExceptionOne.loadDefs();
	}
	
	// TEST 2 invalid election line
	@Test (expected = ElectionException.class) 
	public void loadDefsInvalidElectionLine() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionTwo = new PrefElection("test2");
		testExceptionTwo.loadDefs();
		
	}
	
	// TEST 3 missing value at election line
	@Test (expected = ElectionException.class) 
	public void loadDefsMissingValueAtElectionLine() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionThree = new PrefElection("test3");
		testExceptionThree.loadDefs();
		
	}
	
	// TEST 10 null parameter line
	@Test (expected = ElectionException.class) 
	public void loadDefsNullParameterLine() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionFour = new PrefElection("test10");
		testExceptionFour.loadDefs();	
	}
	
	// TEST 4 invalid parameter line
	@Test (expected = ElectionException.class) 
	public void loadDefsInvalidParameterLine() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionFour = new PrefElection("test4");
		testExceptionFour.loadDefs();	
	}

	// TEST 5 invalid value at label
	@Test (expected = NumbersException.class) 
	public void loadDefsInvalidValueAtLabel() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionFive = new PrefElection("test5");
		testExceptionFive.loadDefs();	
	}
	
	// TEST 6 invalid candidate line
	@Test (expected = ElectionException.class) 
	public void loadDefsInvalidCandidateLine() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionSix = new PrefElection("test6");
		testExceptionSix.loadDefs();	
	}
	
	// TEST 9 null candidate line
	@Test (expected = ElectionException.class) 
	public void loadDefsNullCandidateLine() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionNine = new PrefElection("test9");
		testExceptionNine.loadDefs();	
	}
	
	// TEST 7 missing candidate values
	@Test (expected = ElectionException.class) 
	public void loadDefsMissingCandidateValues() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionSeven = new PrefElection("test7");
		testExceptionSeven.loadDefs();	
	}
	
	// TEST 8 invalid electorate file
	@Test (expected = ElectionException.class) 
	public void loadDefsInvalidElectorateFile() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionEight = new PrefElection("test8");
		testExceptionEight.loadDefs();	
	}
	
	// name check //
	@Test
	public void loadDefsNameCheck() {
		assertEquals(testingNoIssues.getName(), "MinMorgulVale");
	}
	
	// valid type check //
	
	@Test
	public void validTypeSimpleElection() {
		assertTrue(Election.isValidType(0));	
	}
	
	@Test
	public void validTypePrefElection() {
		assertTrue(Election.isValidType(1));	
	}
	
	@Test
	public void invalidTypeElection() {
		assertFalse(Election.isValidType(2));	
	}
	
	
	// treemap loaded candidates check
	
	@Test
	public void loadDefsTreeMapCheck() throws ElectionException {
		manualCDS = new TreeMap<CandidateIndex, Candidate>();
		manualCDS.put(new CandidateIndex(1), new Candidate("Shelob", "Monster Spider Party", "MSP", 0));
		manualCDS.put(new CandidateIndex(2), new Candidate("Gorbag", "Filthy Orc Party", "FOP", 0));
		manualCDS.put(new CandidateIndex(3), new Candidate("Shagrat", "Stinking Orc Party", "SOP", 0));
		assertEquals(manualCDS.values().toString(), testingNoIssues.getCandidates().toString());
	}
	
	// election settings //
	
	@Test
	public void CorrectTypeElection() {
		assertEquals("Failed to fetch correct voting type", 1, testingNoIssues.getType());
	}
	
	// LOAD VOTES //
	
	// load votes exceptions //
	
	// TEST 1 invalid vote value on line
	@Test (expected = NumbersException.class) 
	public void loadVotesInvalidVoteValueOnLine() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionOne = new PrefElection("validtestone");
		testExceptionOne.loadDefs();	
		testExceptionOne.loadVotes();
	}
	
	
	// TEST 3 invalid vote line
	@Test (expected = ElectionException.class) 
	public void loadVotesInvalidVoteLine() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election testExceptionThree = new PrefElection("validtestthree");
		testExceptionThree.loadDefs();	
		testExceptionThree.loadVotes();
	}
	
	// other tests //
	
	@Test
	public void loadVotesCorrectFormalCount() throws FileNotFoundException, ElectionException, IOException, NumbersException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// --------REFLECTION ---------- //
		myObjectClass = Election.class;
		methods = myObjectClass.getFields();
		Field privateVCField = Election.class.
	            getDeclaredField("vc");
		privateVCField.setAccessible(true);
		Election objectInstance = new PrefElection("MinMorgulVale");
		objectInstance.loadDefs();
		objectInstance.loadVotes();
		VoteCollection vc = (VoteCollection) privateVCField.get(objectInstance);
		assertEquals("incorrect number of formal votes in vote collection", 18, vc.getFormalCount());
	}
	
	@Test
	public void loadVotesCorrectInformalCount() throws FileNotFoundException, ElectionException, IOException, NumbersException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// --------REFLECTION ---------- //
		myObjectClass = Election.class;
		methods = myObjectClass.getFields();
		Field privateVCField = Election.class.
	            getDeclaredField("vc");
		privateVCField.setAccessible(true);
		Election objectInstance = new PrefElection("MinMorgulVale");
		objectInstance.loadDefs();
		objectInstance.loadVotes();
		
		VoteCollection vc = (VoteCollection) privateVCField.get(objectInstance);
		
		assertEquals("incorrect number of informal votes in vote collection", 3, vc.getInformalCount());
	}
	
	@Test
	public void loadVotesCorrectTotalCount() throws FileNotFoundException, ElectionException, IOException, NumbersException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// --------REFLECTION ---------- //
		myObjectClass = Election.class;
		methods = myObjectClass.getFields();	
		Field privateVCField = Election.class.
	            getDeclaredField("vc");	
		privateVCField.setAccessible(true);	
		Election objectInstance = new PrefElection("MinMorgulVale");
		objectInstance.loadDefs();
		objectInstance.loadVotes();	
		VoteCollection vc = (VoteCollection) privateVCField.get(objectInstance);
		
		assertEquals("incorrect number of total votes in vote collection", 21, vc.getInformalCount() + vc.getFormalCount());
	}

	@Test
	public void loadVotesFirstVoteMatch() throws FileNotFoundException, ElectionException, IOException, NumbersException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		// --------REFLECTION ---------- //
		
		// first layer, return vote collection object
		myObjectClass = Election.class;
		methods = myObjectClass.getFields();		
		Field privateVCField = Election.class.
	            getDeclaredField("vc");		
		privateVCField.setAccessible(true);		
		Election objectInstance = new PrefElection("MinMorgulVale");
		objectInstance.loadDefs();
		objectInstance.loadVotes();		
		VoteCollection vc = (VoteCollection) privateVCField.get(objectInstance);
		
		// second layer, return arryalist field of vote collection object	
		Field privateVoteListField = VoteCollection.class.
	            getDeclaredField("voteList");		
		privateVoteListField.setAccessible(true);
		ArrayList<Vote> voteList = (ArrayList<Vote>) privateVoteListField.get(vc);
		assertEquals("First vote doesn't match up", "3 2 1 ", voteList.get(0).toString());		
	}
	
	@Test
	public void loadVotesLastVoteMatch() throws FileNotFoundException, ElectionException, IOException, NumbersException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		// --------REFLECTION ---------- //
		
		// first layer, return vote collection object
		myObjectClass = Election.class;
		methods = myObjectClass.getFields();		
		Field privateVCField = Election.class.
	            getDeclaredField("vc");		
		privateVCField.setAccessible(true);		
		Election objectInstance = new PrefElection("MinMorgulVale");
		objectInstance.loadDefs();
		objectInstance.loadVotes();		
		VoteCollection vc = (VoteCollection) privateVCField.get(objectInstance);
		
		// second layer, return arryalist field of vote collection object	
		Field privateVoteListField = VoteCollection.class.
	            getDeclaredField("voteList");		
		privateVoteListField.setAccessible(true);
		ArrayList<Vote> voteList = (ArrayList<Vote>) privateVoteListField.get(vc);
		assertEquals("Last vote doesn't match up", "2 1 3 ", voteList.get(voteList.size() - 1).toString());	
	}
	
	

	// ISFORMAL TESTS //
	
	@Test
	public void formalVote() {
		VoteList v = new VoteList(3);
		Election e = new PrefElection("test");
		// Populate with formal vote
		v.addPref(1); 
		v.addPref(2);
		v.addPref(3);
		
		assertTrue("failed to detect formal vote", e.isFormal(v));
	}
	
	@Test
	public void inFormalVoteOutOfBoundsPref() {
		VoteList v = new VoteList(3);
		Election e = new PrefElection("test");
		// Populate with informal vote
		v.addPref(1); 
		v.addPref(2);
		v.addPref(9);
		
		assertFalse("failed to detect informal vote", e.isFormal(v));
	}
	
	@Test
	public void inFormalVoteSequential() {
		VoteList v = new VoteList(3);
		Election e = new PrefElection("test");
		// Populate with informal vote
		v.addPref(4); 
		v.addPref(5);
		v.addPref(6);
		
		assertFalse("failed to detect informal vote", e.isFormal(v));
	}
	
	@Test
	public void inFormalVoteDuplicatePrefs() {
		VoteList v = new VoteList(3);
		Election e = new PrefElection("test");
		// Populate with informal vote
		v.addPref(1); 
		v.addPref(1);
		v.addPref(3);
		
		assertFalse("Failed to detect non-sequential informal vote", e.isFormal(v));
	}
	
	// FIND WINNER //
	@Test
	public void findWinnerMorgulVale() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election e = new PrefElection("MorgulVale");
		e.loadDefs();
		e.loadVotes();
		String output = e.findWinner();
		boolean match = false;
		// used object equals 
		if (MorgulValeOutputLog.equals(output)) {
			match = true;
		}
		assertTrue(match);
	}
	
	@Test
	public void findWinnerMinMorgulValeTie() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election e = new PrefElection("MinMorgulValeTie");
		e.loadDefs();
		e.loadVotes();
		String output = e.findWinner();
		boolean match = false;
		if (MorgulValeTieOutputLog.equals(output)) {
			match = true;
		}
		assertTrue(match);
	}
	
	@Test
	public void findWinnerMinMorgulVale() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election e = new PrefElection("MinMorgulVale");
		e.loadDefs();
		e.loadVotes();
		String output = e.findWinner();
		boolean match = false;
		if (MinMorgulValeOutputLog.equals(output)) {
			match = true;
		}
		assertTrue(match);
	}
}
