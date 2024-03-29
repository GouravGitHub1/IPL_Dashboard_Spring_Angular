package com.example.ipldashboard.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ipldashboard.model.Match;
import com.example.ipldashboard.model.Team;
import com.example.ipldashboard.repository.MatchRepository;
import com.example.ipldashboard.repository.TeamRepository;

@RestController
@CrossOrigin
@RequestMapping("iplApi/")
public class TeamController {

	@Autowired
    private TeamRepository teamRepository;
	@Autowired
    private MatchRepository matchRepository;


    @GetMapping("/team")
    public Iterable<Team> getAllTeam() {
        return this.teamRepository.findAll();
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Team team = this.teamRepository.findByTeamName(teamName);
        team.setMatches(matchRepository.findLatestMatchesbyTeam(teamName,4));
        return team;
    }

    @GetMapping("/team/{teamName}/{year}")
    public List<Match> getMatchesByTeam(@PathVariable String teamName, @PathVariable int year) 
    {
		LocalDate start = LocalDate.of(year,1,1);
        LocalDate end = LocalDate.of(year+1,1,1);
		//List<Match> matches = matchRepository.getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(teamName, start, end, teamName, start, end);
		List<Match> matches = matchRepository.getMatchesByTeamByYear(teamName, start, end);

		return matches;
    }
	
}    
