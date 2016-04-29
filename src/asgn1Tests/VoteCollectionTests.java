package asgn1Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.Candidate;
import asgn1Election.CandidateIndex;
import asgn1Election.Collection;
import asgn1Election.Election;
import asgn1Election.ElectionException;
import asgn1Election.PrefElection;
import asgn1Election.Vote;
import asgn1Election.VoteCollection;
import asgn1Election.VoteList;
import asgn1Util.NumbersException;

public class VoteCollectionTests {
	
	// ---------- REFLECTION STUFF ---------- //
	private Class myObjectClass;
	private Field[] methods;

	private TreeMap<CandidateIndex, Candidate> cds;
	private Vote v = new VoteList(5);
	private Collection vc;
	
	private Candidate test2 = null;
	private CandidateIndex test22 = null;
	private Candidate test4 = null;
	private CandidateIndex test44 = null;
	private Election testing;
	
	@Before
	public void setUp() throws Exception {
		cds = new TreeMap<CandidateIndex, Candidate>();
	
		// populate test vote
		v.addPref(2);
		v.addPref(3);
		v.addPref(5);
		v.addPref(1);
		v.addPref(4);
		
		vc = new VoteCollection(5);
		vc.includeFormalVote(v);
		
		Candidate test1 = new Candidate("testName1", "testParty1", "testabbrev1", 55);
		CandidateIndex test11 = new CandidateIndex(1);
		
		Candidate test2 = new Candidate("testName2", "testParty2", "testabbrev2", 223);
		CandidateIndex test22 = new CandidateIndex(2);
		
		Candidate test3 = new Candidate("testName3", "testParty3", "testabbrev3", 2);
		CandidateIndex test33 = new CandidateIndex(3);
		
		Candidate test4 = new Candidate("testName4", "testParty4", "testabbrev4", 15);
		CandidateIndex test44 = new CandidateIndex(4);
		
		Candidate test5 = new Candidate("testName5", "testParty5", "testabbrev5", 6);
		CandidateIndex test55 = new CandidateIndex(5);
		
		// add elements to treemap to test
		cds.put(test11, test1);
		cds.put(test22, test2);
		cds.put(test33, test3);
		cds.put(test44, test4);
		cds.put(test55, test5);
		
		testing = new PrefElection("MinMorgulVale");
		testing.loadDefs();
		testing.loadVotes();
		
	}
	
	
	// CONSTRUCTOR TESTS //
	@Test
	public void ConstructorValidArgument() throws ElectionException {
		VoteCollection test = new VoteCollection(3);
		assertNotNull(test);
		
	}
	
	@Test (expected = ElectionException.class)
	public void ConstructorInValidLessArgument() throws ElectionException {
		VoteCollection test = new VoteCollection(0);
		
	}
	
	@Test (expected = ElectionException.class)
	public void ConstructorInValidGreaterArgument() throws ElectionException {
		VoteCollection test = new VoteCollection(16);
		
	}
	
	@Test
	public void ConstructorBorderOne() throws ElectionException {
		VoteCollection test = new VoteCollection(1);
		
	}
	
	@Test
	public void ConstructorBorderFifteen() throws ElectionException {
		VoteCollection test = new VoteCollection(15);
		
	}
	
	// INCLUDE FORMAL VOTE //
	@Test
	public void IncludeOneFormalVoteIncrementCountTest() throws ElectionException {
		Vote v = new VoteList(5);
		v.addPref(2);
		v.addPref(3);
		v.addPref(5);
		v.addPref(1);
		v.addPref(4);
		Collection vcTwo = new VoteCollection(5);
		vcTwo.includeFormalVote(v);	
		assertEquals("vote failed to be added", 1, vcTwo.getFormalCount());
	}
	
	/*
	 * Re-factored code snippet to return vote collection of election object based on given string election name
	 * @param String election name
	 * @return reflected vote collection object
	 */
	private VoteCollection reflectionElectionVoteCollection(String election) throws IllegalArgumentException, IllegalAccessException, FileNotFoundException, ElectionException, IOException, NumbersException, NoSuchFieldException, SecurityException {
		// ----- get vote collection object of election ----- //
		myObjectClass = Election.class;
		methods = myObjectClass.getFields();		
		Field privateVCField = Election.class.getDeclaredField("vc");		
		privateVCField.setAccessible(true);		
		Election objectInstance = new PrefElection(election);
		objectInstance.loadDefs();
		objectInstance.loadVotes();
				
		return (VoteCollection) privateVCField.get(objectInstance);
	}
	
	@Test
	public void IncludeOneFormalVoteAdditionandValidationTest() throws ElectionException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, FileNotFoundException, IOException, NumbersException {
		VoteCollection vc = (VoteCollection) reflectionElectionVoteCollection("MorgulVale");
		// ----- create votelist to append to current votes ----- //
		Vote v = new VoteList(5);
		v.addPref(2);
		v.addPref(3);
		v.addPref(5);
		v.addPref(1);
		v.addPref(4);
		// ----- add vote to vote collection object of MorgulVale election ----- //
		int previousVoteCount = vc.getFormalCount();
		vc.includeFormalVote(v);
		int currentVoteCount = vc.getFormalCount();
		// second layer, return arryalist field of vote collection object	
		Field privateVoteListField = VoteCollection.class.getDeclaredField("voteList");		
		privateVoteListField.setAccessible(true);
		ArrayList<Vote> voteList = (ArrayList<Vote>) privateVoteListField.get(vc);

		// check if last vote in vote collection equals that of the manually created and added one
		assertEquals("First vote doesn't match up", "2 3 5 1 4 ", voteList.get(vc.getFormalCount() - 1).toString());		
	}
	
	// UPDATE INFORMAL COUNT //

	@Test
	public void IncrementFormalCount() {
		vc.updateInformalCount();
		assertEquals(1, vc.getInformalCount());
	}
	
	// EMPTY COLLECTION //
	@Test
	public void EmptyVoteCollection() throws ElectionException {
		Collection vcTwo = new VoteCollection(5);
		vcTwo.includeFormalVote(v);
		vcTwo.emptyTheCollection();
		assertEquals(0, vcTwo.getFormalCount());
	}
	
	// COUNT PREF VOTES TESTS //
	
	@Test
	public void CountPrefVotesFirstReDistributionTest() throws NoSuchFieldException, SecurityException, FileNotFoundException, ElectionException, IOException, NumbersException, IllegalArgumentException, IllegalAccessException {
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
		VoteCollection vc = (VoteCollection) reflectionElectionVoteCollection("MinMorgulVale");
		
		// another layer, return cds from vote
		Field privateCDSField = Election.class.
	            getDeclaredField("cds");	
		privateCDSField.setAccessible(true);

		TreeMap<CandidateIndex, Candidate> cdsReflect = (TreeMap<CandidateIndex, Candidate>) privateCDSField.get(objectInstance);
		// count primary votes in preparation for countprefvotes()
		vc.countPrimaryVotes(cdsReflect);
		
		// count pref votes of manually set lowest candidate
		vc.countPrefVotes(cdsReflect, new CandidateIndex(3));
		//test equivalence against manually calculated value
		assertEquals(10, cdsReflect.get(new CandidateIndex(1)).getVoteCount());
	}
	
	@Test
	public void CountPrefVotesSecondReDistributionTest() throws NoSuchFieldException, SecurityException, FileNotFoundException, ElectionException, IOException, NumbersException, IllegalArgumentException, IllegalAccessException {
		// --------REFLECTION ---------- //
		
		// first layer, return vote collection object
		myObjectClass = Election.class;
		methods = myObjectClass.getFields();		
		Field privateVCField = Election.class.
	            getDeclaredField("vc");		
		privateVCField.setAccessible(true);		
		Election objectInstance = new PrefElection("MinMorgulValeTie");
		objectInstance.loadDefs();
		objectInstance.loadVotes();		
		VoteCollection vc = (VoteCollection) privateVCField.get(objectInstance);
		
		// another layer, return cds from vote
		Field privateCDSField = Election.class.
	            getDeclaredField("cds");	
		privateCDSField.setAccessible(true);

		TreeMap<CandidateIndex, Candidate> cdsReflect = (TreeMap<CandidateIndex, Candidate>) privateCDSField.get(objectInstance);
		// count primary votes in preparation for countprefvotes()
		vc.countPrimaryVotes(cdsReflect);
		
		// count pref votes of manually set lowest candidate then remove
		vc.countPrefVotes(cdsReflect, new CandidateIndex(3));
		cdsReflect.remove(new CandidateIndex(3));
		// round two manually set lowest candidate
		// count pref votes of manually set lowest candidate
		vc.countPrefVotes(cdsReflect, new CandidateIndex(1));
		//test equivalence against manually calculated value
		assertEquals(18, cdsReflect.get(new CandidateIndex(2)).getVoteCount());
	}
		
	// COUNT PRIMARY VOTE TESTS //
	
	@Test
	public void CountPrimaryVotesTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, FileNotFoundException, ElectionException, IOException, NumbersException {
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
		
		// another layer, return cds from vote
		Field privateCDSField = Election.class.
	            getDeclaredField("cds");	
		privateCDSField.setAccessible(true);

		TreeMap<CandidateIndex, Candidate> cdsReflect = (TreeMap<CandidateIndex, Candidate>) privateCDSField.get(objectInstance);
		vc.countPrimaryVotes(cdsReflect);
		// if the first candidate has 8 votes then primary vote distribution was successful
		assertEquals(8, cdsReflect.get(new CandidateIndex(1)).getVoteCount());
	}
	

}
