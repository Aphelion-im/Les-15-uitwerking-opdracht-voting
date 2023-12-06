package com.example.les15security.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class VoteController {

    // https://www.youtube.com/watch?v=H62Jfv1DJlU (Coding with John, Map and HashMap in Java - Full Tutorial)
    private final HashMap<String, Integer> votes = new HashMap<>();

    @PostMapping("/votes")
    public String addVote(@RequestParam("party") String party) {
        if (votes.containsKey(party)) {
            votes.replace(party, votes.get(party), votes.get(party) + 1); // hashmap.replace(K key, V oldValue, V newValue)
        } else {
            votes.put(party, 1);
        }
        return "Thank you. Your vote on " + party + " has been placed.";
    }

    @GetMapping("/votes")
    public HashMap<String, Integer> getVotes() {
        return votes;
    }
}
