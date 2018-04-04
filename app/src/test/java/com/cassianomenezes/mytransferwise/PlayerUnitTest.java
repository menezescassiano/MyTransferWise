package com.cassianomenezes.mytransferwise;

import com.cassianomenezes.mytransferwise.entries.Player;
import com.cassianomenezes.mytransferwise.viewmodel.MainViewModel;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerUnitTest {

    private List<Player> playerList = new ArrayList<>();
    private Player player = new Player();

    @Mock
    private MainViewModel viewModel;

    @Test
    public void list_isCorrect() {
        when(viewModel.getItemsList().get().get(0)).thenReturn(mock(Player.class));
        Assert.assertNotNull(viewModel.getItemsList().get().get(0).getName());
    }
}
