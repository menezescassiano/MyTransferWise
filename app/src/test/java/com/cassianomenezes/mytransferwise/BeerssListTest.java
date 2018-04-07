package com.cassianomenezes.mytransferwise;

import com.cassianomenezes.mytransferwise.entries.Beer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BeerssListTest {

    private List<Beer> beerList = new ArrayList<>();

    @Before
    public void startTest() {
        beerList.add(mockBeer());
    }

    @Test
    public void listIsNotNullOrEmpty() {
        Assert.assertNotNull(beerList);
        Assert.assertTrue(!beerList.isEmpty());
        Beer beer = beerList.get(0);
        Assert.assertNotNull(beer.getName());
        Assert.assertNotNull(beer.getDescription());
        Assert.assertNotNull(beer.getFirstBrewed());
        Assert.assertNotNull(beer.getTagline());

    }

    private Beer mockBeer() {
        Beer beer = new Beer();
        beer.setName("Heineken");
        beer.setDescription("Best beer");
        beer.setFirstBrewed("10/1900");
        beer.setTagline("My tagline");

        return beer;
    }
}
