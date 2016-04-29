/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import asgn1Util.Strings;

/**
 * 
 * Subclass of <code>Election</code>, specialised to preferential, but not optional
 * preferential voting.
 * 
 * @author hogan
 * 
 */
public class PrefElection extends Election {
	
	/**
	 * Simple Constructor for <code>PrefElection</code>, takes name and also sets the 
	 * election type internally. 
	 * 
	 * @param name <code>String</code> containing the Election name
	 */
	public PrefElection(String name) {
		super(name);
		this.type = PrefVoting;
	}
	
	/*
	 * 
	 * Distribution message, removed from find winner for sake of neatness
	 * 
	 */
	private String primaryVotesDistMessage() {
		return "Counting primary votes; " + 
				cds.size() + " alternatives available\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#findWinner()
	 */
	@Override
	public String findWinner() {
		String output = "";
		output += showResultHeader();
		// find the number of votes required to win (50%)
		float half = (numVotes / 2);
		int requiredVotes = (int) half;
		// assign primary votes
		vc.countPrimaryVotes(cds);
		output += primaryVotesDistMessage();
		output += reportCountStatus();	
		// while there's no clear winner
		while (clearWinner(requiredVotes) == null) {
			// find lowest Candidate
			CandidateIndex currentLowest = selectLowestCandidate().copy();
			output += prefDistMessage(cds.get(selectLowestCandidate())) + "\n";				
			vc.countPrefVotes(cds, currentLowest);
			cds.remove(selectLowestCandidate());			
			output += reportCountStatus();			
		}
		
		// if it reaches here, a winner is found
		output += reportWinner(clearWinner(requiredVotes));
		return output;		
	}


	/* 
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#isFormal(asgn1Election.Vote)
	 */
	@Override
	public boolean isFormal(Vote v) {
		if (v == null) {
			return false;
		}
		int counter = 1;
		for (int pref : v) {
			if (!TestExistence(v, counter)) {
				return false;
			}
			counter++;
		}
		return true;
	}
	
	/*
	 * Private helper method to return true if given preference exists in vote
	 */
	private boolean TestExistence(Vote v, int pref) {
		for (int preference : v) {		
			if (preference == pref) {
				return true;
			}
		}
		return false;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        String str = this.name + " - Preferential Voting";
		return str;
	}
	
	// Protected and Private/helper methods below///


	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#clearWinner(int)
	 */
	@Override
	protected Candidate clearWinner(int winVotes) {
		for(Map.Entry<CandidateIndex,Candidate> entry : cds.entrySet()) {
			  CandidateIndex key = entry.getKey();
			  Candidate value = entry.getValue();
			  if (value.getVoteCount() >= winVotes) {
				  return value;
			  }
			}
		return null;
	}

	

	/**
	 * Helper method to create a preference distribution message for display 
	 * 
	 * @param c <code>Candidate</code> to be eliminated
	 * @return <code>String</code> containing preference distribution message 
	 */
	private String prefDistMessage(Candidate c) {
		String str = "\nPreferences required: distributing " + c.getName()
				+ ": " + c.getVoteCount() + " votes";
		return str;
	}

	/**
	 * Helper method to create a string reporting the count progress
	 * 
	 * @return <code>String</code> containing count status  
	 */
	private String reportCountStatus() {
		String str = "\nPreferential election: " + this.name + "\n\n"
				+ candidateVoteSummary() + "\n";
		String inf = "Informal";
		String voteStr = "" + this.vc.getInformalCount();
		int length = ElectionManager.DisplayFieldWidth - inf.length()
				- voteStr.length();
		str += inf + Strings.createPadding(' ', length) + voteStr + "\n\n";

		String cast = "Votes Cast";
		voteStr = "" + this.numVotes;
		length = ElectionManager.DisplayFieldWidth - cast.length()
				- voteStr.length();
		str += cast + Strings.createPadding(' ', length) + voteStr + "\n\n";
		return str;
	}

	/**
	 * Helper method to select candidate with fewest votes
	 * 
	 * @return <code>CandidateIndex</code> of candidate with fewest votes
	 */
	private CandidateIndex selectLowestCandidate() {
		// set lowest vote to first element before loop
		int lowestVote = cds.firstEntry().getValue().getVoteCount();
		CandidateIndex currentLowestPolling = cds.firstEntry().getKey();
		// loop through treemap storing the Candidate index of the current lowest
		for (Entry<CandidateIndex, Candidate> entry : cds.entrySet()) {
			// if lower than current lowest then replace
			if (entry.getValue().getVoteCount() <= lowestVote) {
				  currentLowestPolling = entry.getKey();
				  lowestVote = entry.getValue().getVoteCount();
			}	  
		}
		
		int lowestCandCount = 0;
		// once the lowest value is found, count how many people have that value in case of a tie
		for (Entry<CandidateIndex, Candidate> entry : cds.entrySet()) {
			if (entry.getValue().getVoteCount() == lowestVote) {
				lowestCandCount++;	  
			}		  
		}

		// if there's only one lowest candidate
		if (lowestCandCount == 1) {
			return currentLowestPolling;
		} 
		if (lowestCandCount > 1) {
		// if there's multiple, return the one with the lowest candidate id
			for (Entry<CandidateIndex, Candidate> entry : cds.entrySet()) {
				// if lower than current lowest then replace
				if (entry.getValue().getVoteCount() == lowestVote) {
					return entry.getKey();		  
				}		  
			}
		} 	
	// code should never reach here
	return null;
	}	
}