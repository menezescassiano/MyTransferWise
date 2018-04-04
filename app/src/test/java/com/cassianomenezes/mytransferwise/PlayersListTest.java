package com.cassianomenezes.mytransferwise;

import com.cassianomenezes.mytransferwise.entries.Player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayersListTest {

    private List<Player> playerList = new ArrayList<>();

    @Before
    public void startTest() {
        playerList.add(mockPlayer());
    }

    @Test
    public void listIsNotNullOrEmpty() {
        Assert.assertNotNull(playerList);
        Assert.assertTrue(!playerList.isEmpty());
        Assert.assertNotNull(playerList.get(0).getName());
        Assert.assertNotNull(playerList.get(0).getPosition());
        Assert.assertEquals(playerList.get(0).getJerseyNumber(), 4);
        Assert.assertNotNull(playerList.get(0).getDateOfBirth());
        Assert.assertNotNull(playerList.get(0).getNationality());
        Assert.assertNotNull(playerList.get(0).getContractUntil());
    }

    private Player mockPlayer() {
        Player player = new Player();
        player.setName("Cassiano Menezes");
        player.setPosition("Defender");
        player.setJerseyNumber(4);
        player.setDateOfBirth("1985-01-08");
        player.setNationality("Brazil");
        player.setContractUntil("00-00-00");

        return player;
    }
}
