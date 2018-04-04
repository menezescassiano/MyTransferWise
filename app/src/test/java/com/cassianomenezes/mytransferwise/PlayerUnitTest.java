package com.cassianomenezes.mytransferwise;

import com.cassianomenezes.mytransferwise.entries.Player;

import org.junit.Assert;
import org.junit.Test;

public class PlayerUnitTest {

    private Player player = mockPlayer();

    @Test
    public void playerIsNotNull() {
        Assert.assertNotNull(player.getName());
        Assert.assertNotNull(player.getPosition());
        Assert.assertEquals(player.getJerseyNumber(), 4);
        Assert.assertNotNull(player.getDateOfBirth());
        Assert.assertNotNull(player.getNationality());
        Assert.assertNotNull(player.getContractUntil());
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
