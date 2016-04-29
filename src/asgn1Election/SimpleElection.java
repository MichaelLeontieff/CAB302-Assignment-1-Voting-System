/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

import java.util.Collection;
import java.util.Map.Entry;

import asgn1Util.Strings;

/**
 * 
 * Subclass of <code>Election</code>, specialised to simple, first past the post voting
 * 
 * @author hogan
 * 
 */
public class SimpleElection extends Election {

	/**
	 * Simple Constructor for <code>SimpleElection</code>, takes name and also sets the 
	 * election type internally. 
	 * 
	 * @param name <code>String</code> containing the Election name
	 */
	public SimpleElection(String name) {
		super(name);
		this.type = SimpleVoting;
	}
	
	/*
	 * Distribution message separated into method for sake of neatness
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
		vc.countPrimaryVotes(cds);
		output += primaryVotesDistMessage();
		output += reportCountResult();
		// clearWinner requires vote count despite being irrelevant in this case
		// inserted 0 
		output += reportWinner(clearWinner(0));
		return output;
	}

	/* 
	 * (non-Javadoc)
	 * @see asgn1Election.Election#isFormal(asgn1Election.Vote)
	 */
	@Override
	public boolean isFormal(Vote v) {
		int numberFirstPref = 0;
		// Since this is a simple vote, checks if a 
		// 1st preference (only 1) exists in the vote
		// and checks that no pref exceeds numCandidates
		
		// since each vote's length is correct before reaching this method
		// the size of the vote can be used to find numCandidates 
		// I did this to make junit testing easier, as numCandidates initialises to
		// 0 in the tests and I can't access the private variable
		
		for (int pref : v) {
			// if preference out of bounds immediately return false
			if (pref > voteSize(v)) {
				return false;
			}
			// count number of first preferences
			if (pref == 1) {
				numberFirstPref++; 
			}
		} 
		// if only one first preference
		if (numberFirstPref == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * calculates vote size of vote parameter, used to aide testing
	 * 
	 */
	private int voteSize(Vote v) {
		int elementCount = 0;
		for (int pref : v) {
			elementCount++;
		}
		return elementCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = this.name + " - Simple Voting";
		return str;
	}
	
	// Protected and Private/helper methods below///

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#clearWinner(int)
	 */
	@Override
	protected Candidate clearWinner(int wVotes) {
		// set starting highest vote to first element before loop
		int highestVote = cds.firstEntry().getValue().getVoteCount();
		// initialise current lowest polling to null
		CandidateIndex currentHighestPolling = null;
			for (Entry<CandidateIndex, Candidate> entry : cds.entrySet()) {
				// if lower than current lowest then replace
				if (entry.getValue().getVoteCount() > highestVote) {
						  highestVote = entry.getValue().getVoteCount();
				}
					  
			}
				
		// check to see if it's a tie
		int highestPolling = 0;
		Candidate highestPollingCandidate = null;
		for (Entry<CandidateIndex, Candidate> entry : cds.entrySet()) {
			// if lower than current lowest then replace
			if (entry.getValue().getVoteCount() == highestVote) {
				highestPollingCandidate = entry.getValue();
				highestPolling++;
			}				  
		}
		
		if (highestPolling == 1) {
			return highestPollingCandidate;
		} else {
			for (Entry<CandidateIndex, Candidate> entry : cds.entrySet()) {
				// if lower than current lowest then replace
				if (entry.getValue().getVoteCount() == highestVote) {
					return entry.getValue();
				}				  
			}
		}
		// if we reach here then there's no distinct winner
		return null;		
	}

	/**
	 * Helper method to create a string reporting the count result
	 * 
	 * @return <code>String</code> containing summary of the count
	 */
	private String reportCountResult() {
		String str = "\nSimple election: " + this.name + "\n\n"
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
}